package com.cbernardez.url.service;

import com.cbernardez.url.model.UrlEntry;
import com.cbernardez.url.repository.UrlRepository;

import com.cbernardez.url.dto.ShortenRequest;

import com.cbernardez.url.exception.AliasAlreadyTaken;
import com.cbernardez.url.exception.ShortUrlNotFoundException;
import com.cbernardez.url.exception.UrlExpiredException;
import com.cbernardez.url.exception.InvalidUrlException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.cbernardez.url.validation.UrlCheck.isBlocked;
import static com.cbernardez.url.validation.UrlCheck.isHttp;

@Service
public class UrlService {
    private final UrlRepository urlRepository;
    @Value("${url.expiration.minutes}")
    private long expirationMinutes;

    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    private static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    private static String toBase62(Long n){
        StringBuilder result = new StringBuilder();
        while (true){
            long quotient = (long) n / 62;
            long remainder = (long) n % 62;
            n = quotient;
            int index = (int) remainder;
            char character = ALPHABET.charAt(index);
            result.append(character);
            if (quotient == 0){
                break;
            }
        }
        return result.reverse().toString();
    }

    public String shortenUrl(ShortenRequest request) {
        //separate url from the request body
        String longUrl = request.getUrl();

        //check the url is http(s) and isn't blacklisted
        if (!isHttp(longUrl) || isBlocked(longUrl)) {
            throw new InvalidUrlException();
        }

        //check if the url already exists
        List<UrlEntry> entries = this.urlRepository.findByLongUrl(longUrl);
        for (UrlEntry entry : entries){
            if(Instant.now().isBefore(entry.getExpiresAt())){
                return entry.getShortUrl();
            }
        }

        //if url doesn't exist, check if custom alias is not null
        String shortUrl = null;
        if (request.getCustomAlias() != null) {
            urlRepository.findByShortUrl(request.getCustomAlias())
                    .ifPresent(e -> { throw new AliasAlreadyTaken(); });
            shortUrl = request.getCustomAlias();
        }

        //create entry
        UrlEntry entry = new UrlEntry();
        entry.setLongUrl(longUrl);
        Instant createdAt = Instant.now();
        entry.setCreatedAt(createdAt);
        entry.setExpiresAt(createdAt.plus(expirationMinutes, ChronoUnit.MINUTES));
        UrlEntry saved = this.urlRepository.save(entry);
        Long id = saved.getId();

        if(shortUrl == null){
            shortUrl = toBase62(id);
        }

        saved.setShortUrl(shortUrl);
        this.urlRepository.save(saved);
        return shortUrl;
    }

    public String getLongUrl(String shortUrl){
        UrlEntry entry = urlRepository.findByShortUrl(shortUrl)
                .orElseThrow(() -> new ShortUrlNotFoundException());

        if(Instant.now().isAfter(entry.getExpiresAt())){
            throw new UrlExpiredException();
        }
        entry.incrementClickCount();
        this.urlRepository.save(entry);
        return entry.getLongUrl();
    }
}
