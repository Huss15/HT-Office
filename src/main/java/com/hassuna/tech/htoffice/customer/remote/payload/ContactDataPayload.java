package com.hassuna.tech.htoffice.customer.remote.payload;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Contact data")
public record ContactDataPayload(
    @Schema(example = "contact@acme.de") String email,
    @Schema(example = "+49-30-1234567") String phoneNumber) {}
