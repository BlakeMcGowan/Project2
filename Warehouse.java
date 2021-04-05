import java.util.*;
import java.io.*;

public class Warehouse implements Serializable {

    private static final long serialVersionUID = 1L;
    private ProductList productList;
    private ClientList clientList;
    private SupplierList SupplierList;
    private OrderList orderList;
    private Order order;
    private static Warehouse warehouse;

    private Warehouse() {
        productList = ProductList.instance();
        clientList = ClientList.instance();
        SupplierList = SupplierList.instance();
        orderList = OrderList.instance();

    }

    public static Warehouse instance() {
        if (warehouse == null) {
            ClientIdServer.instance(); // instantiate all singletons
            SupplierIDServer.instance(); // instantiate all singletons
            return (warehouse = new Warehouse());
        } else {
            return warehouse;
        }
    }

    public Product addProduct(String title, String id, double price, int quantity) {

        Product product = new Product(title, id, price, quantity);
        if (productList.insertProduct(product)) {
            return (product);
        }
        return null;
    }

    public Client addMember(String name, String address, String phone) {
        Client member = new Client(name, address, phone);
        if (clientList.insertMember(member)) {
            return (member);
        }
        return null;
    }

    public Supplier addSupplier(String name, String address, String phone) {
        Supplier supp = new Supplier(name, address, phone);
        if (SupplierList.insertSupplier(supp)) {
            return (supp);
        }
        return null;
    }

    public Product assignProdToSupplier (String productId, String supplierId, double price, int quantity){
        Product product = productList.find(productId);
        if (product == null)
        {
          return null;
        }
    
        Supplier supplier = SupplierList.find(supplierId);
        if (supplier == null)
        {
          return null;
        }
    
        Shipment Ship = product.SearchSupplyList(supplier);
        if (Ship != null)
        {
          return null;
        }
        boolean success = product.link(supplier, price);
        success = supplier.assignProduct(product);
        if (success) {
          return product;
        }
        else {
          return null;
        }
      }

    public String ProductListToString()
    {
        return productList.toString();
    }

    public Iterator getProducts() {
        return productList.getProducts();
    }

    public Iterator getMembers() {
        return clientList.getMembers();
    }

    public Iterator getSuppliers() {
        return SupplierList.getSuppliers();
    }

    public Product searchProduct(String productId){
        return productList.search(productId);
    }

    public Supplier searchSupplier(String supplierId){
        return SupplierList.search(supplierId);
    }

    public Iterator getWaitList(String pId) {
        // get product waitlist
        Product p = productList.find(pId);
        return p.getWaitList();
    }

    public Iterator getSupplierList(String pId) {
        Product p = productList.find(pId);
        return p.getSupplierList();
    }

    public Iterator getOrderList() {
        return order.getItemList();
    }

   /* public static Warehouse retrieve() {
        try {
            FileInputStream file = new FileInputStream("WarehouseData");
            ObjectInputStream input = new ObjectInputStream(file);
            input.readObject();
            ClientIdServer.retrieve(input);
            SupplierIDServer.retrieve(input);
            return warehouse;
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return null;
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
            return null;
        }
    }
    */

    public static boolean save() {
        try {
            FileOutputStream file = new FileOutputStream("WarehouseData");
            ObjectOutputStream output = new ObjectOutputStream(file);
            output.writeObject(warehouse);
            output.writeObject(ClientIdServer.instance());
            output.writeObject(SupplierIDServer.instance());

            return true;
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return false;
        }
    }

    public boolean addToOrderList() {
        return orderList.addOrder(order);
    }

    private void writeObject(java.io.ObjectOutputStream output) {
        try {
            output.defaultWriteObject();
            output.writeObject(warehouse);
        } catch (IOException ioe) {
            System.out.println(ioe);
        }
    }

    private void readObject(java.io.ObjectInputStream input) {
        try {
            input.defaultReadObject();
            if (warehouse == null) {
                warehouse = (Warehouse) input.readObject();
            } else {
                input.readObject();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean findClient(String clientID) {
        return clientList.find(clientID) != null;
    }

    public Client getClient(String clientID) {
        return clientList.find(clientID);
    }

    public Order findOrder(String clientID) {
        return orderList.find(clientID);
    }

    public void printOrder(String clientId) {
        Order order = warehouse.findOrder(clientId);;
        order.printOrder();

    }

    public Product findProduct(String pId) {
        return productList.find(pId);
    }

    public Supplier findSupplier(String sId) {
        return SupplierList.find(sId);
    }

    public void updateClientBalance(String clientId, double total) {
        Client client = clientList.find(clientId);
        client.addBalance(total);
    }

    public double getClientBalance(String clientId) {
        Client client = clientList.find(clientId);
        return client.getBalance();
    }

    // creates an order for a client
    public void createOrder(String clientId) {
        order = new Order(clientId);
    }

    public void addToOrder(String cId, String pId, int quantity) {
        Product product = productList.find(pId);
        if (product != null) {
            Item newItem = new Item(pId, quantity, (product.getPrice() * quantity));
            order.getItemLists().add(newItem);
        }
    }

    public void processOrder(String cId) {
        Client client = clientList.find(cId);
        double totalPrice = 0;
        int quantity;
        Iterator allItems = warehouse.getOrderList();
        System.out.println("Invoice");
        System.out.println("---------");

        while (allItems.hasNext()) {
            Item i = (Item) (allItems.next());
            // System.out.println(i.print());
            quantity = i.getQuantity();
            totalPrice += i.getTotal();

            Product product = productList.find(i.getProductId());
            if (product != null) {
                if (quantity > product.getQuantity()) {
                    int waitListQuantity = quantity - product.getQuantity();
                    //Item newItem = new Item(pId, quantity, (product.getPrice() * quantity));
                    Item newWaitListItem = new Item(i.getProductId(), waitListQuantity, product.getPrice() * waitListQuantity);
                    newWaitListItem.associateClientID(cId);
                    //  order.getItemLists().add(newItem);
                    Wait test = new Wait(client, waitListQuantity);
                    System.out.println(product.getQuantity() + " " + product.getProduct() + " fulfilled.");
                    System.out.println(waitListQuantity + " " + product.getProduct() + " added to wait list.");
                    product.addToWaitList(test);
                    //waitList.addItem(newWaitListItem);
                    product.setQuantity(0); // Set it to 0 since its out of stock

                } else if (quantity <= product.getQuantity()) {
                    //Item newItem = new Item(pId, quantity, (product.getPrice() * quantity));
                    //order.addItem(newItem);
                    int newQuantity = product.getQuantity() - quantity;
                    product.setQuantity(newQuantity);

                    System.out.println(quantity + " " + product.getProduct() + " fulfilled. ");
                    // System.out.println("Quantity remaining: " + newQuantity);
                }
            } else {
                System.out.println("Product not found.");
            }
        }
        System.out.println("Total order = " + totalPrice);

    }

    public void updateQuantity(Product p, int q) {
        p.updateQuantity(q);
    }

    public void addSupplierToProduct(Product p, Supplier m, double price) {
        Shipment s = new Shipment(m, price);
        p.addToSupplierList(s);
    }

    public Shipment searchProductSupplier(Product product, Supplier supp){
        return product.SearchSupplyList(supp);
    }
    public Iterator<Supplier> getSuppliersOfProducts (Product p){
        return p.getSupplierList();
    }

    public Iterator<Shipment> getSuppliersOfProduct (Product p){
        return p.getSupplier();
    }

    public Iterator<Product> getProductBySupplier (Supplier s){
        return s.getProducts();
    }

    public Iterator<Float> getProductPrices (Product p){
        return p.getPrices();
    }

    public void deleteSupplierFromProduct(Product p, Supplier m) {
        // find supplier first
        Shipment s = p.find(m.getId());

        p.deleteFromSupplierList(s);
    }

    public void FulfillWaitlist(Product p, int NewQ, Shipment shipment){
        Iterator<Wait> iterator = p.getWaitlistedClients();
        Wait w;
        Client c;
        int WaitlistedQ;
        while ((iterator.hasNext()) && (NewQ >= 0))
        {
          w = iterator.next();
          WaitlistedQ = w.getQuantity();
          c = w.getClient();
          if ((NewQ - WaitlistedQ) >= 0)
          {
            NewQ = NewQ - WaitlistedQ;
            double price = WaitlistedQ * shipment.supplierPrice();
            c.subBalance(price);
            iterator.remove(); 
            Wait Wlist = c.searchWaitListOnProduct(p);
            boolean success = c.removeWaitlistedProduct(Wlist);
          }
          else
          {
            double price = NewQ * shipment.supplierPrice();
            w.updateQuantity(WaitlistedQ - NewQ);
            NewQ = NewQ - NewQ;
            c.subBalance(price);
          }
    
        }
        shipment.setNewQuantity(shipment.getQuantity() - NewQ);
      }

    public String toString() {
        return warehouse + "\n" + clientList;
    }

    public Iterator getTransactions(String memberId) {
        Client client = clientList.find(memberId);
        if (client == null) {
            return (null);
        }
        return client.getTransactions(memberId);
    }

    public void addToTransactions(String clientId, double balance) {
        Client client = clientList.find(clientId);
        client.addToTransactionList(balance);
    }
}
