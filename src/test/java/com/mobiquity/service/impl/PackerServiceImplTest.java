package com.mobiquity.service.impl;

import com.mobiquity.dto.Item;
import com.mobiquity.dto.Package;
import com.mobiquity.service.PackerService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This test class contains the service layer tests which takes the parsed data and process to return the desired output
 * It contains all possible scenarios tests required to test this complete feature at service layer
 * */

class PackerServiceImplTest {

    @Test
    public void returnItemsWithMaxCostAndLessWeightValue(){
        Package p = new Package( 56);
        p.getItems().add(new Item(1, 90.72, 13));
        p.getItems().add(new Item(2, 33.80, 40));
        p.getItems().add(new Item(3, 43.15, 10));
        p.getItems().add(new Item(4, 37.97, 16));
        p.getItems().add(new Item(5, 46.81, 36));
        p.getItems().add(new Item(6, 48.77, 79));
        p.getItems().add(new Item(7, 81.80, 45));
        p.getItems().add(new Item(8, 19.36, 79));
        p.getItems().add(new Item(9, 6.76, 64));

        PackerService serviceUnderTest = new PackerServiceImpl();
        String response = serviceUnderTest.sendPackages(List.of(p));

        assertNotNull(response);
        assertEquals(response, "8, 9");
    }

    @Test
    public void returnItemWithLessWeightWhenItemsHaveSameCost() {
        Package p = new Package( 12);
        p.getItems().add(new Item(1,11.70,80));
        p.getItems().add(new Item(2, 8.55, 80));

        PackerService serviceUnderTest = new PackerServiceImpl();
        String response = serviceUnderTest.sendPackages(List.of(p));

        assertNotNull(response);
        assertEquals(response, "2");
    }

    @Test
    public void returnItemWithHighCostWhenItemsHaveSameWeight(){
        Package p = new Package( 12);
        p.getItems().add(new Item(1,11.00,100));
        p.getItems().add(new Item(2, 11.00, 90));

        PackerService serviceUnderTest = new PackerServiceImpl();
        String response = serviceUnderTest.sendPackages(List.of(p));

        assertNotNull(response);
        assertEquals(response, "1");
    }

    @Test
    public void returnEmptyResultWhenItemsHaveWeightMoreThanAllowed(){
        Package p = new Package( 12);
        p.getItems().add(new Item(1,13.00,100));
        p.getItems().add(new Item(2, 15.00, 90));

        PackerService serviceUnderTest = new PackerServiceImpl();
        String response = serviceUnderTest.sendPackages(List.of(p));

        assertNotNull(response);
        assertEquals(response, "-");
    }
}