package ru.kapion.model;

/**
 * @author Aleksandr Kuznetsov (kapion) on 25.06.2018
 *  Бин для хранения параметров http запроса
 */
public class PartFB implements IFormBean {

    public PartFB(){}

    public PartFB(String part_name, String part_number, String vendor, String qty,
                  String shipped_from, String shipped_to, String receive_from, String receive_to) {
        this.part_name = part_name;
        this.part_number = part_number;
        this.vendor = vendor;
        this.qty = qty;
        this.shipped_from = shipped_from;
        this.shipped_to = shipped_to;
        this.receive_from = receive_from;
        this.receive_to = receive_to;
    }

    private String  error;
    private String  sortCellIndex;

    private String  part_name;
    private String  part_number;
    private String  vendor;
    private String  qty;
    private String  shipped_from;
    private String  shipped_to;
    private String  receive_from;
    private String  receive_to;


    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getSortCellIndex() {
        return sortCellIndex;
    }

    public String getPart_name() {
        return part_name;
    }

    public String getPart_number() {
        return part_number;
    }

    public String getVendor() {
        return vendor;
    }

    public String getQty() {
        return qty;
    }

    public String getShipped_from() {
        return shipped_from;
    }

    public String getShipped_to() {
        return shipped_to;
    }

    public String getReceive_from() {
        return receive_from;
    }

    public String getReceive_to() {
        return receive_to;
    }

    /**
     * Проверка корректности данных формы
     * @return
     */
    @Override
    public boolean isValid() {
        if (qty !=null && !qty.trim().isEmpty() && !qty.trim().matches("-?\\d+")) {
            setError("Qty must be contains digits only");
        }
        return getError() == null || getError().isEmpty();
    }

}
