package com.cbernardez.url.model;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "urls")
public class UrlEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "counter_generator")
    @SequenceGenerator(name = "counter_generator", sequenceName = "counter_generator_name", allocationSize = 10)
    private Long id;
    @Column(unique = true)
    private String shortUrl;
    @Column(updatable = false)
    private String longUrl;
    private int clickCount;
    @Column(updatable = false)
    private Instant createdAt;
    private Instant expiresAt;

    public UrlEntry() {}

    public UrlEntry(String shortUrl, String longUrl) {
        this.shortUrl = shortUrl;
        this.longUrl = longUrl;
    }

    public void  setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public void  setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    public void incrementClickCount() { this.clickCount++; }

    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public void setExpiresAt(Instant expiresAt) { this.expiresAt = expiresAt; }

    public long getId() {
        return id;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public int getClickCount() { return clickCount; }

    public Instant getCreatedAt() { return createdAt; }

    public Instant getExpiresAt() { return expiresAt; }
}
