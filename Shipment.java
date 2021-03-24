import java.io.*;

public class Shipment implements Serializable {

    private static final long serialVersionUID = 1L;
    private Supplier s;
    private double price;

    public Shipment(Supplier s, double p) {
        this.s = s;
        this.price = p;

    }

    public String getId() {
        return m.getId();
    }

    public String supplierName() {
        return m.getName();
    }

    public String supplierId() {
        return m.getId();
    }

    public double supplierPrice() {
        return price;
    }

    @Override
    public String toString() {
        return ("Id: " + supplierId() + " Supplier: " + supplierName() + " Price: " + supplierPrice());
    }
}