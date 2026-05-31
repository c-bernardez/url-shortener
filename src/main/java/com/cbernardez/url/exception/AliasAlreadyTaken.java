package com.cbernardez.url.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Custom Alias is Already in Use")
public class AliasAlreadyTaken extends RuntimeException {
}