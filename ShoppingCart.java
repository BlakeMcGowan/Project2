import java.io.Serializable;
import java.util.*;

public class ShoppingCart implements Serializable {
    private String client;
    private Warehouse currentWarehouse;
    private List<Entry> items = new LinkedList<Entry>();

    public void addProduct(String productID, int quantity){
        items.add(new Entry(productID, quantity));
    }

    public void removeProduct(String productID, int quantity){
        Iterator<Entry> i = items.iterator();
        while (i.hasNext()) {
            Entry entry = i.next();
            if(productID == entry.productID)
                entry.count = entry.count - quantity;
            if(entry.count <= 0)
                i.remove();
        }
    }

    public String GetClient()
    {
        return client;
    }

    public List<Entry> getItems()
    {
        return items;
    }
}