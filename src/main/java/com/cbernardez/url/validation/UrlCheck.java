package com.cbernardez.url.validation;

import java.net.URI;
import java.util.Set;

public class UrlCheck {
    private static Set<String> blacklist = Set.of(
            "malware.com",
            "phishing.com",
            "tinyurl.com",
            "bit.ly",
            "t.co",
            "localhost",
            "127.0.0.1"
    );

    public static boolean isHttp(String url) {
       try {
            URI uri = new URI(url);
            String scheme = uri.getScheme();
            return "http".equals(scheme) || "https".equals(scheme);
       }
       catch (Exception e) {
           return false;
       }
    }

    public static boolean isBlocked(String url){
        try{
            URI uri = new URI (url);
            String host = uri.getHost();
            host = host.replaceFirst("^www\\.", "");
            return blacklist.contains(host);
        }
        catch (Exception e) {
            return true;
        }
    }
}
