package com.nagarro.remotelearning.service;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class AttributeConverterTest {

    @Test
    public void convertStringToLocalDateTest() {
        AttributeConverter attributeConverter = new AttributeConverter();
        LocalDate expectedLocalDate = LocalDate.of(2012,10,02);
        assertEquals(expectedLocalDate,attributeConverter.convertStringToLocalDate("2012-10-02"));
    }

}