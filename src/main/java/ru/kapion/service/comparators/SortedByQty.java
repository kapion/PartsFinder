package ru.kapion.service.comparators;

import ru.kapion.model.Part;

import java.util.Comparator;

/**
 * @author Aleksandr Kuznetsov (kapion) on 26.06.2018
 * Comparator сортировка по количеству
 */
public class SortedByQty implements Comparator<Part> {
    public int compare(Part obj1, Part obj2)
    {
        return Integer.compare(obj1.getQty(), obj2.getQty());
    }
}
