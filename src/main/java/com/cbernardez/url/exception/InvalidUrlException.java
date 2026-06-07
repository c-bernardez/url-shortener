package com.cbernardez.url.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "URL should be HTTP or HTTPS")
public class InvalidUrlException extends RuntimeException {
}