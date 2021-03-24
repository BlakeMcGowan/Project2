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
        return s.getId();
    }

    public String supplierName() {
        return s.getName();
    }

    public String supplierId() {
        return s.getId();
    }

    public double supplierPrice() {
        return price;
    }

    @Override
    public String toString() {
        return ("Id: " + supplierId() + " Supplier: " + supplierName() + " Price: " + supplierPrice());
    }
}