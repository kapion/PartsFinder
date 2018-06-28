package ru.kapion.service.comparators;

import ru.kapion.model.Part;

import java.util.Comparator;

/**
 * @author Aleksandr Kuznetsov (kapion) on 26.06.2018
 * Comparator сортировка по названию
 */
public class SortedByName implements Comparator<Part> {
    public int compare(Part obj1, Part obj2){
        return obj1.getName().compareTo(obj2.getName());
    }
}
