package com.hassuna.tech.htoffice.bill.elements;

import org.mustangproject.ZUGFeRD.IZUGFeRDPaymentDiscountTerms;
import org.mustangproject.ZUGFeRD.IZUGFeRDPaymentTerms;

import java.util.Date;

public class PaymentTerm implements IZUGFeRDPaymentTerms {

    private final String description;
    private final Date dueDate;
    private final PaymentDiscountTerms discountTerms;

    public PaymentTerm(String description, Date dueDate, PaymentDiscountTerms discountTerms) {
        this.description = description;
        this.dueDate = dueDate;
        this.discountTerms = discountTerms;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public Date getDueDate() {
        return dueDate;
    }

    @Override
    public IZUGFeRDPaymentDiscountTerms getDiscountTerms() {
        return discountTerms;
    }
}
