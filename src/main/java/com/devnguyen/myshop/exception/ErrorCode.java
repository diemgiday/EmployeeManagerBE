package com.devnguyen.myshop.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ErrorCode {
    INVALID_EMAIL_FORMAT,
    EMAIL_ALREADY_EXISTS,
    USERNAME_ALREADY_EXISTS,
    ACCOUNT_INCORRECT,
    EMAIL_NOT_FOUND,
    USER_NOT_FOUND,
    INVALID_TOKEN
   
}
