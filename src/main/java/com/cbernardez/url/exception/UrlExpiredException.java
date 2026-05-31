package com.cbernardez.url.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.GONE, reason = "Custom Alias is Already in Use")
public class UrlExpiredException extends RuntimeException {
}