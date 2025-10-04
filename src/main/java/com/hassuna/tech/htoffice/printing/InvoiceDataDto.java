package com.hassuna.tech.htoffice.printing;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.mustangproject.Invoice;
import org.mustangproject.ZUGFeRD.IZUGFeRDExportableItem;
import org.mustangproject.ZUGFeRD.TransactionCalculator;

public class InvoiceDataDto {

  private String invoiceNumber;
  private BigDecimal totalNet;
  private BigDecimal totalVat;
  private BigDecimal totalGross;
  private List<InvoiceItemDto> lineItems;

  private String sellerName;
  private String sellerPostcode;
  private String sellerCity;
  private String sellerCountry; // z.B. "DE"

  private String buyerName;
  private String buyerPostcode;
  private String buyerCity;
  private String buyerCountry; // z.B. "DE"

  public InvoiceDataDto(Invoice invoice) {
    this.invoiceNumber = invoice.getNumber();

    TransactionCalculator calc = new TransactionCalculator(invoice);
    this.totalNet = calc.getGrandTotal();
    this.totalVat = calc.getTaxBasis();
    this.totalGross = calc.getGrandTotal();

    this.lineItems = new ArrayList<>();
    for (IZUGFeRDExportableItem x : invoice.getZFItems()) {
      lineItems.add(new InvoiceItemDto(x.getId(), x.getQuantity(), x.getPrice()));
    }
    this.sellerCity = invoice.getShipToLocation();

    this.setSellerName(invoice.getSender().getName());
    this.setSellerPostcode(invoice.getSender().getZIP());
    this.setSellerCity(invoice.getSender().getLocation());
    this.setSellerCountry(invoice.getSender().getCountry());

    // Käufer (Recipient)
    this.setBuyerName(invoice.getRecipient().getName());
    this.setBuyerPostcode(invoice.getRecipient().getZIP());
    this.setBuyerCity(invoice.getRecipient().getLocation());
    this.setBuyerCountry(invoice.getRecipient().getCountry());
  }

  public String getInvoiceNumber() {
    return invoiceNumber;
  }

  public void setInvoiceNumber(String invoiceNumber) {
    this.invoiceNumber = invoiceNumber;
  }

  public BigDecimal getTotalNet() {
    return totalNet;
  }

  public void setTotalNet(BigDecimal totalNet) {
    this.totalNet = totalNet;
  }

  public BigDecimal getTotalVat() {
    return totalVat;
  }

  public void setTotalVat(BigDecimal totalVat) {
    this.totalVat = totalVat;
  }

  public BigDecimal getTotalGross() {
    return totalGross;
  }

  public void setTotalGross(BigDecimal totalGross) {
    this.totalGross = totalGross;
  }

  public List<InvoiceItemDto> getLineItems() {
    return lineItems;
  }

  public void setLineItems(List<InvoiceItemDto> lineItems) {
    this.lineItems = lineItems;
  }

  public String getSellerName() {
    return sellerName;
  }

  public void setSellerName(String sellerName) {
    this.sellerName = sellerName;
  }

  public String getSellerPostcode() {
    return sellerPostcode;
  }

  public void setSellerPostcode(String sellerPostcode) {
    this.sellerPostcode = sellerPostcode;
  }

  public String getSellerCity() {
    return sellerCity;
  }

  public void setSellerCity(String sellerCity) {
    this.sellerCity = sellerCity;
  }

  public String getSellerCountry() {
    return sellerCountry;
  }

  public void setSellerCountry(String sellerCountry) {
    this.sellerCountry = sellerCountry;
  }

  public String getBuyerName() {
    return buyerName;
  }

  public void setBuyerName(String buyerName) {
    this.buyerName = buyerName;
  }

  public String getBuyerPostcode() {
    return buyerPostcode;
  }

  public void setBuyerPostcode(String buyerPostcode) {
    this.buyerPostcode = buyerPostcode;
  }

  public String getBuyerCity() {
    return buyerCity;
  }

  public void setBuyerCity(String buyerCity) {
    this.buyerCity = buyerCity;
  }

  public String getBuyerCountry() {
    return buyerCountry;
  }

  public void setBuyerCountry(String buyerCountry) {
    this.buyerCountry = buyerCountry;
  }
}
