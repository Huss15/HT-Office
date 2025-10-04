package com.hassuna.tech.htoffice.printing;

import java.math.BigDecimal;

public class InvoiceItemDto {
  private String name;
  private BigDecimal quantity;
  private BigDecimal unitPrice;

  public InvoiceItemDto() {}

  public InvoiceItemDto(String name, BigDecimal quantity, BigDecimal unitPrice) {
    this.name = name;
    this.quantity = quantity;
    this.unitPrice = unitPrice;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public BigDecimal getQuantity() {
    return this.quantity;
  }

  public void setQuantity(BigDecimal quantity) {
    this.quantity = quantity;
  }

  public BigDecimal getUnitPrice() {
    return unitPrice;
  }

  public void setUnitPrice(BigDecimal unitPrice) {
    this.unitPrice = unitPrice;
  }
}
