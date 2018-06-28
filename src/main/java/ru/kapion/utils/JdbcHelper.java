package ru.kapion.utils;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.kapion.dao.ConnectionFactory;
import ru.kapion.dao.Mapper;
import ru.kapion.model.DatePeriod;
import ru.kapion.model.Part;

import java.math.BigDecimal;
import java.sql.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Aleksandr Kuznetsov (kapion) on 28.06.2018
 * Класс-утилита для работы с SQL запросами
 */
public final class JdbcHelper {
    private static final Logger LOG = LogManager.getLogger(JdbcHelper.class);

    /**
     * Выбор данных в таблице parts_table по фильтру, если есть
     * @param query  - SQL запрос
     * @param params -"сырой" список для передачи значений в WHERE SQL запроса
     *      *               k - имя поля в БД, v - значение
     * @param mapper - маппер ResultSet
     * @return List<Part> - результат запроса
     */
    public static List<Part> SelectAndReturnList(String query, Map<String,?> params, Mapper<Part> mapper) {
        List<Part> resultList = null;
        Connection conn = ConnectionFactory.getInstance().getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(query);

            //Если есть параметры, формируем запрос
            if (Optional.ofNullable(params).isPresent()){
                int i = 0;
                for(String key: params.keySet()) {
                    if (params.get(key) instanceof DatePeriod)  {
                        DatePeriod period = (DatePeriod)params.get(key);
                        JdbcHelper.setParameter(ps,period.getFrom(),++i);
                        JdbcHelper.setParameter(ps,period.getTo(),++i);
                    } else {
                        JdbcHelper.setParameter(ps,params.get(key),++i);
                    }

                }
            }
            LOG.info(ps.toString());
            rs = ps.executeQuery();

            resultList = new ArrayList<>();
            while (rs.next()) {
                resultList.add(mapper.rsMapper(rs));
            }
        } catch (SQLException e) {
            LOG.error("Failed SQL execute." +e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                LOG.error("Failed close connection. ", e);
            }
        }
        if (resultList == null) {
            resultList = Collections.emptyList();
        }
        return resultList;
    }

    /**
     * Передача значений в PreparedStatement в соответствии с типом переменной
     * @param ps - PreparedStatement
     * @param value - значение
     * @param index - порядковый номер в условии
     * @throws SQLException
     */
    private static void setParameter(PreparedStatement ps, Object value,
                                     int index) throws SQLException {
        if (value != null) {
            if (value instanceof String) {
                if (value.toString().isEmpty()) {
                    ps.setNull(index, Types.VARCHAR);
                } else {
                    ps.setString(index, value.toString());
                }
            } else if (value instanceof Integer) {
                ps.setInt(index, (Integer) value);
            } else if (value instanceof BigDecimal) {
                ps.setBigDecimal(index, (BigDecimal) value);
            } else if (value instanceof java.util.Date) {
                ps.setDate(index, Date.valueOf(new SimpleDateFormat(
                        "yyyy-MM-dd").format((java.util.Date) value)));
            } else if (value instanceof Long) {
                ps.setLong(index, (Long) value);
            } else {
                throw new IllegalArgumentException(
                        "Invalid type for argument: " + value.getClass());
            }
        } else {
            ps.setNull(index, Types.INTEGER);
        }
    }
}
