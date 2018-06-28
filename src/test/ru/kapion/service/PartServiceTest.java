package ru.kapion.service;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class PartServiceTest {

    @Test
    public void getAllParts() {
        assertTrue(new PartService().getAllParts().size()>0);
    }
}