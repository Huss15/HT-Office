package com.hassuna.tech.htoffice.bill.elements;

import org.mustangproject.ZUGFeRD.IZUGFeRDPaymentDiscountTerms;

import java.math.BigDecimal;
import java.util.Date;

public class PaymentDiscountTerms implements IZUGFeRDPaymentDiscountTerms {

    private final BigDecimal calculationPercentage;
    private final Date baseDate;
    private final int basePeriodMeasure;
    private final String basePeriodUnitCode;

    public PaymentDiscountTerms(BigDecimal calculationPercentage, Date baseDate, int basePeriodMeasure, String basePeriodUnitCode) {
        this.calculationPercentage = calculationPercentage;
        this.baseDate = baseDate;
        this.basePeriodMeasure = basePeriodMeasure;
        this.basePeriodUnitCode = basePeriodUnitCode;
    }


    @Override
    public BigDecimal getCalculationPercentage() {
        return calculationPercentage;
    }

    @Override
    public Date getBaseDate() {
        return baseDate;
    }

    @Override
    public int getBasePeriodMeasure() {
        return basePeriodMeasure;
    }

    @Override
    public String getBasePeriodUnitCode() {
        return basePeriodUnitCode;
    }
}
