package ru.kapion.model;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;

/**
 * @author Aleksandr Kuznetsov (kapion) on 24.06.2018
 * Класс-фабрика для создания экземпляров классов bean
 * для прaвильного биндинга имена полей должны совпадать (регистр важен)
 * и быть String
 */
public class FBFactory {
    public static <T> T initFB(Class<T> clazz, HttpServletRequest request) throws IllegalAccessException, InstantiationException {
        T object = clazz.newInstance();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAccessible())
                continue;

            field.setAccessible(true);
            if (field.getType().equals(String.class)) {
                field.set(object, request.getParameter(field.getName()));
            }
        }

        return object;
    }
}
