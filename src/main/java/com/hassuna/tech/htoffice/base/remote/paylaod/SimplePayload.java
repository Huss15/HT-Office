package com.hassuna.tech.htoffice.base.remote.paylaod;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Simple payload wrapper for single values")
public record SimplePayload(@Schema(description = "Wrapped value") Object value) {}
