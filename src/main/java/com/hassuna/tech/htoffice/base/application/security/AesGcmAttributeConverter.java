package com.hassuna.tech.htoffice.base.application.security;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Converter()
@Component
public class AesGcmAttributeConverter implements AttributeConverter<String, String> {

    private final EncryptionService encryptionService;

    @Autowired
    public AesGcmAttributeConverter(EncryptionService encryptionService) {
        this.encryptionService = encryptionService;
    }


    @Override
    public String convertToDatabaseColumn(String attribute) {
        return attribute == null ? null : encryptionService.encrypt(attribute);
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        try {
            return encryptionService.decrypt(dbData);
        } catch (Exception e) {
            throw new IllegalStateException("Decrypt failed", e);
        }
    }
}