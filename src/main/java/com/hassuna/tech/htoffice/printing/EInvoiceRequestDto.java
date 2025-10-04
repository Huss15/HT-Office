package com.hassuna.tech.htoffice.printing;

public class EInvoiceRequestDto {
  private String htmlContent;
  private InvoiceDataDto invoiceData;

  public EInvoiceRequestDto() {}

  public EInvoiceRequestDto(String htmlContent, InvoiceDataDto invoiceData) {
    this.htmlContent = htmlContent;
    this.invoiceData = invoiceData;
  }

  public String getHtmlContent() {
    return htmlContent;
  }

  public void setHtmlContent(String htmlContent) {
    this.htmlContent = htmlContent;
  }

  public InvoiceDataDto getInvoiceData() {
    return invoiceData;
  }

  public void setInvoiceData(InvoiceDataDto invoiceData) {
    this.invoiceData = invoiceData;
  }
}
