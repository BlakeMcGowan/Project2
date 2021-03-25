import java.util.*;
import java.io.*;

public class Order implements Serializable {

    private static final long serialVersionUID = 1L;
    private String clientId;
    private Supplier supplier;
    private Boolean RECEIVED_FLAG;
    private List itemList = new LinkedList();
    private List<Product> products = new LinkedList<Product>();
    private List<Integer> quantities = new LinkedList<Integer>();

    public Order(String clientId) {
        this.clientId = clientId;
    }
    public boolean getOrderStatus(){
        return RECEIVED_FLAG;
    }
    
    public boolean receiveOrder(){
        this.RECEIVED_FLAG = true;
        return RECEIVED_FLAG;
    }

    public Iterator getItemList() {
        return itemList.iterator();
    }

    public List getItemLists() {
        return itemList;
    }

    public void addItem(Item i) {
        itemList.add(i);
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

    public String getClientId() {
        return clientId;
    }

    public Item find(String clientId) {
        for (Iterator iterator = itemList.iterator(); iterator.hasNext();) {
            Item item = (Item) iterator.next();
            if (clientId.equals(item.getClientID())) {
                return item;
            }
        }
        return null;
    }

    public void printOrder() {
        for (int i = 0; i < itemList.size(); i++) {
            System.out.println(itemList.get(i));
        }
    }
}