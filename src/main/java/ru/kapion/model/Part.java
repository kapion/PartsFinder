package ru.kapion.model;

import java.util.Date;

/**
 * @author Aleksandr Kuznetsov (kapion) on 24.06.2018
 * Класс-cущность строки таблицы parts_table
 */
public class Part {

    private int id;

    private String  name;
    private String  number;
    private String  vendor;
    private Integer qty;
    private Date    shipped;
    private Date    receive;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Date getShipped() {
        return shipped;
    }

    public void setShipped(Date shipped) {
        this.shipped = shipped;
    }

    public Date getReceive() {
        return receive;
    }

    public void setReceive(Date receive) {
        this.receive = receive;
    }

    @Override
    public String toString() {
        return "Part{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", number='" + number + '\'' +
                ", vendor='" + vendor + '\'' +
                ", qty=" + qty +
                ", shipped=" + shipped +
                ", receive=" + receive +
                '}';
    }
}
