package ru.kapion.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.kapion.dao.PartRepository;
import ru.kapion.model.*;
import ru.kapion.service.comparators.*;
import ru.kapion.utils.DateHelper;

import java.util.*;

/**
 * @author Aleksandr Kuznetsov (kapion) on 27.06.2018
 * Сервисный слой
 */
public class PartService {
    private Logger LOG = LogManager.getRootLogger();
    private final static String FROM_EMPTY = "Jan 01, 1900";
    private final static String TO_EMPTY   = "Dec 31, 2099";

    private PartRepository repository;

    public PartService() {
        this.repository = new PartRepository();
    }

    public List<Part> getAllParts() {
        return  repository.findAll();
    }

    /**
     * Маппинг параметров форм-бина в список параметров для SQL запроса
     * и запрос к БД по этим условиям
     * Используется регистронезависимый поиск по тексту
     * @param bean - PartFB параметры полученные в http запросе
     * @return List<Part> результат выборки SQL запроса
     */
    public List<Part> getPartsByFilter(PartFB bean) {
        Map<String, Object> params = new HashMap<>();
        if (isPresent(bean.getPart_name())) {
              params.put("UPPER(part_name)","%"+bean.getPart_name().toUpperCase()+"%");
        }

        if (isPresent(bean.getPart_number())) {
                params.put("UPPER(part_number)","%"+bean.getPart_number().toUpperCase()+"%");
        }

        if (isPresent(bean.getQty())) {
            params.put("qty",Integer.valueOf(bean.getQty()));
        }

        if (isPresent(bean.getVendor())) {
            params.put("UPPER(vendor)","%"+bean.getVendor().toUpperCase()+"%");
        }

        if (isPresent(bean.getShipped_from()) || isPresent(bean.getShipped_to())) {
            String s_from = bean.getShipped_from();
            String s_to =   bean.getShipped_to();

            // DATE_TRUNC отсекаем время, чтобы учитывались крайние даты
            // если какая-то из дат отстутствует, то заполняем крайним значением, чтобы отработал between
            params.put("DATE_TRUNC('day',shipped)",
                    new DatePeriod(DateHelper.convertTextToJavaDate(isPresent(s_from) ? s_from : FROM_EMPTY),
                            DateHelper.convertTextToJavaDate(isPresent(s_to) ? s_to : TO_EMPTY)));

        }

        if (isPresent(bean.getReceive_from()) || isPresent(bean.getReceive_to())) {
            String r_from = bean.getReceive_from();
            String r_to = bean.getReceive_to();

            // DATE_TRUNC отсекаем время, чтобы учитывались крайние даты
            // если какая-то из дат отстутствует, то заполняем крайним значением, чтобы отработал between
            params.put("DATE_TRUNC('day',receive)",
                    new DatePeriod(DateHelper.convertTextToJavaDate(isPresent(r_from) ? r_from : FROM_EMPTY),
                            DateHelper.convertTextToJavaDate(isPresent(r_to) ? r_to : TO_EMPTY)));
        }

        return new PartRepository().findBy(params);
    }

    private boolean isPresent(String param) {
       return Optional.ofNullable(param).isPresent() && !param.isEmpty();
    }

    /**
     * Метод, реализующий сортировку по выбранному столбцу
     * @param parts - List<Part> список объектов Part выбранных по фильтру на форме
     * @param currentThIndex - int текущий индекс столбца (нумерация с 1)
     * @param sortOrderASC - boolean порядок сортировки: true - прямой/false обратный
     * @return - boolean порядок сортировки, противоположный входящему sortOrderASC
     */
    public boolean sortByColumn(List<Part> parts, int currentThIndex, boolean sortOrderASC) {
        Comparator<Part> comparator = null;
        switch (currentThIndex) {
            default:
                return sortOrderASC;
            case PartColumns.PART_NUMBER:
                comparator = new SortedByNumber();
                break;
            case PartColumns.PART_NAME:
                comparator = new SortedByName();
                break;
            case PartColumns.VENDOR:
                comparator = new SortedByVendor();
                break;
            case PartColumns.QTY:
                comparator = new SortedByQty();
                break;
            case PartColumns.SHIPPED:
                comparator = new SortedByShipped();
                break;
            case PartColumns.RECEIVE:
                comparator = new SortedByReceive();
                break;
        }

        if (sortOrderASC) {
            //прямой порядок
            parts.sort(comparator);
        }else{
            //обратный
            parts.sort(Collections.reverseOrder(comparator));
        }

        //изменяем флаг порядка сортировки
        return !sortOrderASC;
    }

    /**
     * Метод создания DTO (data transfer object) для вывода результата на экран
     * с правильным форматированием дат
     * @param - список объектов Part
     * @return - список объектов PartDTO
     */
    public List<PartDTO> correspondingPartToDTO(List<Part> parts) {
        if (parts.isEmpty()) {
            return Collections.emptyList();
        }
        List<PartDTO> partDTOList = new ArrayList<>();
        parts.forEach(part -> {
            partDTOList.add(
               new PartDTO(part.getName(),
                           part.getNumber(),
                           part.getVendor(),
                           part.getQty(),
                           DateHelper.convertJavaDateToText(part.getShipped()),
                           DateHelper.convertJavaDateToText(part.getReceive()))
            );
        });

        return partDTOList;
    }

}
