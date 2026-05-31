package com.cbernardez.url.service;

import com.cbernardez.url.dto.ShortenRequest;
import com.cbernardez.url.exception.AliasAlreadyTaken;
import com.cbernardez.url.exception.ShortUrlNotFoundException;
import com.cbernardez.url.model.UrlEntry;
import com.cbernardez.url.repository.UrlRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UrlService {
    private final UrlRepository urlRepository;

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

        //check if the url already exists
        Optional<UrlEntry> existing = this.urlRepository.findByLongUrl(longUrl);
        if (existing.isPresent()){
            return existing.get().getShortUrl();
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
        //redirect to the original url
        UrlEntry entry = urlRepository.findByShortUrl(shortUrl)
                .orElseThrow(() -> new ShortUrlNotFoundException());
        entry.incrementClickCount();
        this.urlRepository.save(entry);
        return entry.getLongUrl();
    }
}
