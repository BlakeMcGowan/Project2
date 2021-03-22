import java.util.*;
import java.io.*;
import java.lang.*;

public class Order implements Serializable{
    private List<Entry> items;
    private float total;
    private static final long serialVersionUID = 1L;
    public static final String ORDER_STRING = "O";
    private String ID;
    private Supplier supplier;
    private List<Product> products = new LinkedList<Product>();
    private List<Integer> quantities = new LinkedList<Integer>();

   /* Order(ShoppingCart cart, Warehouse warehouse)
    {
        total = 0;
        items = cart.getItems();
        Inventory inventory = warehouse.Getinventory();
        for (Entry entry : items) {
            Product product = inventory.search(entry.productID);
            total = total + (product.getPurchasePrice() * entry.count);
        }
    }
    */

    public Order(Supplier s){
        this.supplier = s;
        ID = ORDER_STRING + (OrderIDServer.instance()).getID();
    }

    public boolean addProductToOrder(Product p, int q){
        boolean success = products.add(p);
        success = quantities.add(q);
        return success;
    }

    public String getID(){
        return ID;
    }

    public Iterator<Product> getProds()
    {
        return products.iterator();
    }

    public Iterator<Integer> getQs()
    {
        return quantities.iterator();
    }

    public Supplier getSupplier()
    {
        return supplier;
    }

    public String GenerateInvoice()
    {
        String invoice = "Purchased Items";

        for (Entry entry : items) {
            invoice = invoice + "\n"
                    + "Item: " + entry.productID + " Count: " + entry.count;
        }
        invoice = invoice + "\n\n"
                + "Total: " + total;

        return invoice;
    }
}