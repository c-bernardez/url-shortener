package com.cbernardez.url.dto;

import jakarta.validation.constraints.NotBlank;

public class ShortenRequest {
    @NotBlank
    private String url;
    private String customAlias;

    public ShortenRequest() {
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setCustomAlias(String customAlias) {
        this.customAlias = customAlias;
    }

    public String getUrl() {
        return url;
    }

    public String getCustomAlias() {
        return customAlias;
    }
}
