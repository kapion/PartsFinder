package ru.kapion.dao;

import ru.kapion.model.Part;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Aleksandr Kuznetsov (kapion) on 25.06.2018
 * Маррер ResultSet для QueryWrapper
 */
public class MapPartEntry implements Mapper<Part> {
    @Override
    public Part rsMapper(ResultSet rs) throws SQLException {
        Part part = new Part();
        part.setId(rs.getInt("id"));
        part.setNumber(rs.getString("part_number"));
        part.setName(rs.getString("part_name"));
        part.setVendor(rs.getString("vendor"));
        part.setQty(rs.getInt("qty"));
        part.setShipped(rs.getDate("shipped"));
        part.setReceive(rs.getDate("receive"));
        return part;
    }
}
