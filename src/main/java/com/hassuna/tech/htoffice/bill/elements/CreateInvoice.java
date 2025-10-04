package com.hassuna.tech.htoffice.bill.elements;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import org.mustangproject.BankDetails;
import org.mustangproject.Invoice;
import org.mustangproject.Item;
import org.mustangproject.Product;
import org.mustangproject.TradeParty;

public class CreateInvoice {

  public Invoice getExample() {
    Calendar cal = Calendar.getInstance();
    cal.set(2025, Calendar.JANUARY, 15);
    Date dueDate = cal.getTime();

    PaymentTerm paymentTerm =
        new PaymentTerm(
            "Zahlbar innerhalb von 30 Tagen ohne Abzug.",
            dueDate,
            null // or provide PaymentDiscountTerms if needed
            );

    Invoice invoice =
        new Invoice()
            .setNumber("RE-2025-123")
            .setDeliveryDate(dueDate) // 15.01.2025
            .setIssueDate(new Date())
            .setCurrency("EUR")
            .setDueDate(new Date(2025 - 1900, 1, 15)) // 15.02.2025
            .setPaymentReference("Payment for invoice RE-2025-123")
            .setPaymentTerms(paymentTerm)
            .setSellerOrderReferencedDocumentID("Bestellung 4711");

    // Absender (Lieferant)
    TradeParty sender =
        new TradeParty("Bananen-Lieferservice GmbH", "Musterstraße 1", "54321", "Musterstadt", "DE")
            .setTaxID("DE123456789")
            .setVATID("DE123456789")
            .setName("Max Mustermann")
            .setEmail("info@bananen-lieferservice.de")
            .addBankDetails(new BankDetails("DE12500105170648489890", "COBADEFFXXX"));
    invoice.setSender(sender);

    // Empfänger (Kunde)
    TradeParty recipient =
        new TradeParty("Testkunde AG", "Kundenweg 8", "12345", "Berlin", "DE")
            .setName("Frau Erika Kunde")
            .setEmail("erika.kunde@testkunde.de");
    invoice.setRecipient(recipient);

    // Rechnungsposition
    Product bananaProduct =
        new Product(
            "B001", "Premium Bananen aus Ecuador", "C62", new BigDecimal("50.00")); // C62 = Stück
    Item bananaItem =
        new Item(bananaProduct, new BigDecimal("50.00"), new BigDecimal("19.00")).setId("1");
    invoice.addItem(bananaItem);
    invoice.setBuyerOrderReferencedDocumentID("PO-2025-987");

    return invoice;
  }
}
