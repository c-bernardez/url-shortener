package com.cbernardez.url.model;

import jakarta.persistence.*;

@Entity
@Table(name = "urls")
public class UrlEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "counter_generator")
    @SequenceGenerator(name = "counter_generator", sequenceName = "counter_generator_name", allocationSize = 10)
    private Long id;
    @Column(unique = true)
    private String shortUrl;
    private String longUrl;
    private int clickCount;

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
}
