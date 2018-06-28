package ru.kapion.model;

import java.lang.reflect.Field;
import java.util.Optional;

/**
 * @author Aleksandr Kuznetsov (kapion) on 24.06.2018
 * Интерфейс класса хранения параметров http запроса
 */
public interface IFormBean {

    /**
     * Проверяется корректность данных
     */
    boolean isValid();

    /**
     * Объект определяет !заполненность полей формы
     * @return true - ни одно поле не заполнено
     */
    default boolean isEmpty() {
        boolean present = false;
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAccessible())
                continue;

            field.setAccessible(true);
            try {
                Object value = field.get(this);
                if (Optional.ofNullable(value).isPresent()){
                    present = true;
                    break;
                }

            } catch (IllegalAccessException e) {
                e.printStackTrace();

            }
        }

        return !present;
    }

}
