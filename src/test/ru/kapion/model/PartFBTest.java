package ru.kapion.model;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PartFBTest {

    @Test
    public void isValid() {
        PartFB bean =  new PartFB(null,null,null,"10",
                                null,null,null,null);
        assertTrue(bean.isValid());
    }

    @Test
    public void isEmpty() {
        PartFB bean =  new PartFB();
        assertTrue(bean.isEmpty());

        PartFB bean2 =  new PartFB(null,null,null,"10",
                null,null,null,null);
        assertFalse(bean2.isEmpty());

    }
}