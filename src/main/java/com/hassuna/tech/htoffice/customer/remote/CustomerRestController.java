package com.hassuna.tech.htoffice.customer.remote;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.hassuna.tech.htoffice.base.remote.paylaod.ErrorPayload;
import com.hassuna.tech.htoffice.base.remote.paylaod.PagePayload;
import com.hassuna.tech.htoffice.base.remote.paylaod.SimplePayload;
import com.hassuna.tech.htoffice.customer.remote.payload.B2bCustomerPayload;
import com.hassuna.tech.htoffice.customer.remote.payload.B2cCustomerPayload;
import com.hassuna.tech.htoffice.customer.remote.payload.CreateB2bCustomerPayload;
import com.hassuna.tech.htoffice.customer.remote.payload.CreateB2cCustomerPayload;
import com.hassuna.tech.htoffice.customer.remote.payload.CustomerDtoPayload;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Customer", description = "Operations for B2B and B2C customers")
public interface CustomerRestController {

  @Operation(
      summary = "Get all customers (paginated)",
      description = "Returns a paginated list of all customers (B2B and B2C).",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Paginated list of customers",
            content = @Content(schema = @Schema(implementation = PagePayload.class))),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid pagination parameters",
            content = @Content(schema = @Schema(implementation = ErrorPayload.class))),
        @ApiResponse(
            responseCode = "500",
            description = "Error retrieving customers",
            content = @Content(schema = @Schema(implementation = ErrorPayload.class)))
      })
  PagePayload<CustomerDtoPayload> getAllCustomers(Pageable pageable);

  @Operation(
      summary = "Get total customer count (B2B & B2C)",
      description = "Returns the total number of customers.",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Total customer count",
            content = @Content(schema = @Schema(implementation = SimplePayload.class))),
        @ApiResponse(
            responseCode = "500",
            description = "Error retrieving count",
            content = @Content(schema = @Schema(implementation = ErrorPayload.class)))
      })
  ResponseEntity<SimplePayload> getTotalCustomerCount();

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
        @ApiResponse(
            responseCode = "404",
            description = "Customer not found",
            content = @Content(schema = @Schema(implementation = ErrorPayload.class))),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid ID format",
            content = @Content(schema = @Schema(implementation = ErrorPayload.class))),
        @ApiResponse(
            responseCode = "500",
            description = "Error retrieving customer",
            content = @Content(schema = @Schema(implementation = ErrorPayload.class)))
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
            description = "Customer created",
            content = @Content(schema = @Schema(implementation = B2bCustomerPayload.class))),
        @ApiResponse(
            responseCode = "400",
            description = "Validation error",
            content = @Content(schema = @Schema(implementation = ErrorPayload.class))),
        @ApiResponse(
            responseCode = "500",
            description = "Error creating customer",
            content = @Content(schema = @Schema(implementation = ErrorPayload.class)))
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
            description = "Customer created",
            content = @Content(schema = @Schema(implementation = B2cCustomerPayload.class))),
        @ApiResponse(
            responseCode = "400",
            description = "Validation error",
            content = @Content(schema = @Schema(implementation = ErrorPayload.class))),
        @ApiResponse(
            responseCode = "500",
            description = "Error creating customer",
            content = @Content(schema = @Schema(implementation = ErrorPayload.class)))
      })
  ResponseEntity<B2cCustomerPayload> createB2cCustomer(CreateB2cCustomerPayload requestBody);
}
