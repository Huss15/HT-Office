package com.hassuna.tech.htoffice.base.remote.paylaod;

import java.time.Instant;

public record ErrorPayload(String timestamp,
                           int status,
                           String error,
                           String message,
                           String path) {
}