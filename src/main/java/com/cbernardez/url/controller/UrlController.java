package com.cbernardez.url.controller;

import com.cbernardez.url.dto.ShortenRequest;
import com.cbernardez.url.service.UrlService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
public class UrlController {
    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("/shorten")
    public String getAlias(@RequestBody @Valid ShortenRequest request) {
        return urlService.shortenUrl(request);
    }

    @GetMapping("/{alias}")
    public ResponseEntity<Void> getUrl(@PathVariable String alias){
        String url = urlService.getLongUrl(alias);
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(url))
                .build();
    }
}
