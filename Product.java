import java.util.*;
import java.io.*;

public class Product implements Serializable {

    private static final long serialVersionUID = 1L;
    private String product;
    private String id;
    private List supplierList = new LinkedList();
    private double price;
    private int quantity;
    private List<Float> productPrices = new LinkedList<Float>();
    private List<Shipment> productSupplier = new LinkedList<Shipment>();
    private List<Wait> waitlistedClients = new LinkedList<Wait>();
    private List waitList = new LinkedList();

    public Product(String title, String id, double price, int quantity) {
        this.product = title;
        this.id = id;
        this.price = price;
        this.quantity = quantity;
    }

    public String getProduct() {
        return product;
    }

    public Iterator getWaitList() {
        return waitList.iterator();
    }

    public Iterator getSupplierList() {
        return supplierList.iterator();
    }

    public Iterator<Shipment> getSupplier() {
        return productSupplier.iterator();
    }
    public boolean link(Supplier Shipment, double p){
        Shipment Ship = new Shipment (Shipment, p);
        return productSupplier.add(Ship) ? true: false;
    }

    public Iterator getPrices(){
        return productPrices.iterator();
    }

    public String getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void updateQuantity(int q) {
        quantity += q;
    }

    public void setQuantity(int q) {
        quantity = q;
    }

    public void addToWaitList(Wait item) {
        waitList.add(item);
    }

    public Iterator<Wait> getWaitlistedClients()
    {
      return waitlistedClients.iterator();
    }

    public void addToSupplierList(Shipment supplier) {
        supplierList.add(supplier);
    }

    public void deleteFromSupplierList(Shipment supplier) {
        supplierList.remove(supplier);
    }

    public boolean fulfillOrder(Client c, int q) {
        Iterator allWaitlist = waitList.iterator();
        while (allWaitlist.hasNext()) {
            Wait wait = (Wait) (allWaitlist.next());
            if (c.equals(wait.getClient())) {
                if (wait.getQuantity() == q) {
                    //allWaitlist.remove();
                } else {
                    wait.updateQuantity(q);
                }
                return true;
            }
        }
        return false;
    }

    public Shipment find(String sId) {
        for (Iterator iterator = supplierList.iterator(); iterator.hasNext();) {
            Shipment s = (Shipment) iterator.next();
            if (sId.equals(s.getId())) {
                return s;
            }
        }
        return null;
    }

    public Shipment SearchSupplyList(Supplier shipment)
    {
        int i = 0;
        for (; i <= productSupplier.size()-1; i++)
        {
            if((productSupplier.get(i).getSupplier()) == shipment)
            {
                return productSupplier.get(i);
            }
        }
        return null;
    }


    @Override
    public String toString() {
        return "id " + id + " product " + product + " price " + price + " quantity " + quantity;
    }
}
