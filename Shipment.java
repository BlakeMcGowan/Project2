import java.io.*;

public class Shipment implements Serializable {

    private static final long serialVersionUID = 1L;
    private Supplier s;
    private Supplier supp;
    private int quantity;
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

    public Supplier getSupplier()
    {
      return supp;
    }

    public String supplierId() {
        return s.getId();
    }

    public double supplierPrice() {
        return price;
    }

    public int getQuantity()
    {
      return quantity;
    }

    public void setNewQuantity(int q)
    {
      this.quantity = quantity - q;
    }

    @Override
    public String toString() {
        return ("Id: " + supplierId() + " Supplier: " + supplierName() + " Price: " + supplierPrice());
    }
}