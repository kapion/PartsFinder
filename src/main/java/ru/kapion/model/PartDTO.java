package ru.kapion.model;

/**
 * @author Aleksandr Kuznetsov (kapion) on 25.06.2018
 * Класс DTO для отображения строки таблицы parts_table на форме
 */
public class PartDTO {

    private String  name;
    private String  number;
    private String  vendor;
    private Integer qty;
    private String  shipped;
    private String  receive;

    public PartDTO(String name, String number, String vendor, Integer qty, String shipped, String receive) {
        this.name = name;
        this.number = number;
        this.vendor = vendor;
        this.qty = qty;
        this.shipped = shipped;
        this.receive = receive;
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

    public String getShipped() {
        return shipped;
    }

    public void setShipped(String shipped) {
        this.shipped = shipped;
    }

    public String getReceive() {
        return receive;
    }

    public void setReceive(String receive) {
        this.receive = receive;
    }
}
