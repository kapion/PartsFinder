package ru.kapion.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import ru.kapion.model.Part;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;

public class PartRepositoryTest {
    private Logger LOG = LogManager.getRootLogger();

    @Test
    public void findAll() {
        List<Part> all = new PartRepository().findAll();
        all.forEach(p->LOG.info(p));
        assertTrue(all.size() > 0);
    }

    @Test
    public void findBy() {
        Map<String, Object> params = new HashMap<>();
//        params.put("UPPER(part_name)","%dis%");
          params.put("qty",Integer.valueOf("5"));
//        params.put("shipped",
//                new DatePeriod(DateHelper.convertTextToJavaDate("May 21, 2018"),
//                               DateHelper.convertTextToJavaDate("Jul 25, 2018")));
        List<Part> partList = new PartRepository().findBy(params);
        partList.forEach(p-> LOG.info(p));

        assertTrue(partList.size() >0);
    }
}