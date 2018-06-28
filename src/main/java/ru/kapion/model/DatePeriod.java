package ru.kapion.model;

import java.util.Date;

/**
 * @author Aleksandr Kuznetsov (kapion) on 24.06.2018
 * Класс для хранения периода: from - to
 */
public class DatePeriod {
    private Date from;
    private Date to;

    public DatePeriod(Date from, Date to) {
        this.from = from;
        this.to = to;
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }
}
