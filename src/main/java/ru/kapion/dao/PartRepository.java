package ru.kapion.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.kapion.model.DatePeriod;
import ru.kapion.model.Part;

import java.util.List;
import java.util.Map;

import static ru.kapion.utils.JdbcHelper.SelectAndReturnList;

/**
 * @author Aleksandr Kuznetsov (kapion) on 25.06.2018
 * Класс, реализующий обработку и подготовку данных для SQL запросов
 */
public class PartRepository {
    private Logger LOG = LogManager.getRootLogger();

    private static final String SQL_SELECT_ALL = "SELECT * FROM parts_table";
    private static final String SQL_SELECT_BY = "SELECT * FROM parts_table WHERE ";

    private static final String OP_AND = " AND ";
    private static final String OP_LIKE = " LIKE ";
    private static final String OP_EQ = " = ";
    private static final String OP_GT = " > ";
    private static final String OP_GT_EQ = " >= ";
    private static final String OP_LT_EQ = " <= ";
    private static final String OP_BETWEEN = " BETWEEN ";


    /**
     * Выбор всех записей в таблице parts_table
     * @return List<Part> - результат запроса
     */
    public List<Part> findAll() {
        return  SelectAndReturnList(SQL_SELECT_ALL,null,new MapPartEntry());
    }

    /**
     * Выбор данных в таблице parts_table по фильтру
     * @param params -"сырой" список для формирования WHERE SQL запроса
     *               k - имя поля в БД, v - значение
     * @return List<Part> - результат запроса
     */
    public List<Part> findBy(Map<String,?> params) {

      String whereCause = makeWhereCause(params);

       if (!whereCause.isEmpty()) {
           LOG.info("whereCause: "+whereCause);
           return SelectAndReturnList(SQL_SELECT_BY+whereCause,params,new MapPartEntry());
       }else {
           return findAll();
       }
    }

    /**
     * Формирование условия WHERE
     * @param params
     * @return
     */
    private String makeWhereCause(Map<String,?> params) {
        StringBuilder formatWhere = new StringBuilder();

        //формируем условие WHERE
        if (params!= null && params.size() > 0) {
            params.forEach((k,v) -> {
                String operand = null;
                StringBuilder causePeriod = new StringBuilder();

                //String ищем по совпадению подстроки
                if (v instanceof String) {
                    operand = OP_LIKE;

                    //Integer - по значению "не меньше"
                }else if (v instanceof Integer)  {
                    operand = OP_GT_EQ;

                    //DatePeriod - по периоду
                }else if (v instanceof DatePeriod) {
                    causePeriod.append(k).append(OP_BETWEEN).append("?").append(OP_AND).append("?");

                }else{
                    //просто прерываем foreach
                    return;
                }

                if (formatWhere.toString().length() > 0) {
                    //добавляем AND, если WHERE не пустой
                    formatWhere.append(OP_AND);
                }

                if (causePeriod.length() > 0) {
                    //период
                    formatWhere.append(causePeriod);
                }else if (operand!=null) {
                    //остальные поля
                    formatWhere.append(k).append(operand).append("?");
                }
            });

        }

        return formatWhere.toString();
    }



}
