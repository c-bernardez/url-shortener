package com.cbernardez.url.repository;

import com.cbernardez.url.model.UrlEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UrlRepository extends JpaRepository<UrlEntry, Long> {
    Optional<UrlEntry> findByShortUrl(String shortUrl);
}
