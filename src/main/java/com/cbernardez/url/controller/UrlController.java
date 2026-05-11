package com.cbernardez.url.controller;

import com.cbernardez.url.model.UrlEntry;
import com.cbernardez.url.service.UrlService;
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
    public String getAlias(@RequestBody String url) {
        return urlService.shortenUrl(url);
    }

    @GetMapping("/{alias}")
    public ResponseEntity<Void> getUrl(@PathVariable String alias){
        String url = urlService.getLongUrl(alias);
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(url))
                .build();
    }
}
