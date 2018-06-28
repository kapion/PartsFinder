package ru.kapion.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author Aleksandr Kuznetsov (kapion) on 25.06.2018
 * Класс-утилита для преобразования входящих-исходящих дат
 */
public final class DateHelper {

    static final String FORMAT_DATE = "MMM dd, yyyy"; //Jul 25, 2018

    static public Date convertTextToJavaDate(String strDate){
        if(strDate.isEmpty()){
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat(FORMAT_DATE, Locale.ENGLISH);
        try {
            return formatter.parse(strDate);
        } catch (ParseException e) {
            return null;
        }
    }

    static public String convertJavaDateToText(Date javaDate){
        if(javaDate != null){
            SimpleDateFormat formatter = new SimpleDateFormat(FORMAT_DATE, Locale.ENGLISH);
            return formatter.format(javaDate);
        }else{
            return "";
        }
    }

}
