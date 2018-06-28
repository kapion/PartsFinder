package ru.kapion.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Aleksandr Kuznetsov (kapion) on 25.06.2018
 * Интерфейс маппера ResultSet
 */
public interface Mapper<T> {
    T rsMapper(ResultSet rs) throws SQLException;
}
