package com.cbernardez.url.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Short URL Not Found")
public class ShortUrlNotFoundException extends RuntimeException {
}
