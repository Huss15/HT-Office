package com.hassuna.tech.htoffice.customer.remote;

import org.springframework.http.ResponseEntity;

import com.hassuna.tech.htoffice.customer.remote.payload.B2bCustomerPayload;
import com.hassuna.tech.htoffice.customer.remote.payload.B2cCustomerPayload;
import com.hassuna.tech.htoffice.customer.remote.payload.CreateB2bCustomerPayload;
import com.hassuna.tech.htoffice.customer.remote.payload.CreateB2cCustomerPayload;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Customer", description = "Operations for B2B and B2C customers")
public interface CustomerRestController {

  @Operation(
      summary = "Get customer by ID",
      description =
          "Fetch a B2B or B2C customer using its custom ID. The format decides which type is"
              + " returned.",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Customer found (B2B or B2C) see schema",
            content =
                @Content(
                    mediaType = "application/json",
                    schema =
                        @Schema(oneOf = {B2bCustomerPayload.class, B2cCustomerPayload.class}))),
        @ApiResponse(responseCode = "404", description = "Customer not found", content = @Content),
        @ApiResponse(responseCode = "400", description = "Invalid ID format", content = @Content)
      })
  ResponseEntity<?> getCustomerByCustomerId(String customerId);

  @Operation(
      summary = "Create B2B customer",
      requestBody =
          @RequestBody(
              required = true,
              description = "Data to create a B2B customer",
              content =
                  @Content(schema = @Schema(implementation = CreateB2bCustomerPayload.class))),
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "B2B customer created",
            content = @Content(schema = @Schema(implementation = B2bCustomerPayload.class))),
        @ApiResponse(
            responseCode = "409",
            description = "Conflict (duplicate)",
            content = @Content),
        @ApiResponse(responseCode = "400", description = "Validation error", content = @Content)
      })
  ResponseEntity<B2bCustomerPayload> createB2bCustomer(CreateB2bCustomerPayload requestBody);

  @Operation(
      summary = "Create B2C customer",
      requestBody =
          @RequestBody(
              required = true,
              description = "Data to create a B2C customer",
              content =
                  @Content(schema = @Schema(implementation = CreateB2cCustomerPayload.class))),
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "B2C customer created",
            content = @Content(schema = @Schema(implementation = B2cCustomerPayload.class))),
        @ApiResponse(
            responseCode = "409",
            description = "Conflict (duplicate)",
            content = @Content),
        @ApiResponse(responseCode = "400", description = "Validation error", content = @Content)
      })
  ResponseEntity<B2cCustomerPayload> createB2cCustomer(CreateB2cCustomerPayload requestBody);
}
