package com.hassuna.tech.htoffice.base.remote.paylaod;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Payload for paginated responses")
public record PagePayload<T>(
    @Schema(description = "List of items on the current page") List<T> content,
    @Schema(description = "True if this is the last page") boolean last) {}
