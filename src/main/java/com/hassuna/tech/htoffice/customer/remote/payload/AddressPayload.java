package com.hassuna.tech.htoffice.customer.remote.payload;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Address information")
public record AddressPayload(
    @Schema(example = "Musterstraße 12") String street,
    @Schema(example = "Berlin") String city,
    @Schema(example = "10115") String zipCode) {}
