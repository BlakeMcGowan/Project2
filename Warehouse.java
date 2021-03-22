import java.util.*;
import java.io.*;

public class Warehouse implements Serializable{
    private static final long serialVersionUID = 1L;
    public static final int SUPPLIER_NOT_FOUND = 1;
    public static final int PRODUCT_NOT_FOUND = 2;
    public static final int CLIENT_NOT_FOUND = 3;
    public static final int NO_SUCH_PRODUCT = 4;
    public static final int NO_SUCH_SUPPLIER = 5;
    public static final int NO_SUCH_CLIENT = 6;

    private Inventory inventory;
    private SupplierList supplierList;
    private ClientList clientList;
    private OrderList orderList;
    private static Warehouse warehouse;

    private Warehouse() {
        inventory = Inventory.instance();
        supplierList = SupplierList.instance();
        clientList = ClientList.instance();
        orderList = OrderList.instance();
    }

    public static Warehouse instance() {
        if (warehouse == null) {
            SupplierIDServer.instance(); //instatiates singletons
            ClientIDServer.instance(); //instatiates singletons
            return (warehouse = new Warehouse());
        }
        else{
            return warehouse;
        }
    }

    public Product addProduct(int price, int quantity, String name){
        Product product = new Product(price, quantity, name);
        if (inventory.insertProduct(product)) {
            return (product);
        }
        return null;
    }

    public Supplier addSupplier(String name, String address, String description){
        Supplier supplier = new Supplier (name, address, description);
        if (supplierList.insertSupplier(supplier)){
            return(supplier);
        }
        return null;
    }

    public Client addClient(String name, String phone, String address){
        Client client = new Client (name, phone, address);
        if (clientList.insertClient(client)){
            return(client);
        }
        return null;
    }

    public Product assignProdToSupplier (String productId, String supplierId, double price){
        Product product = inventory.search(productId);
        if (product == null)
        {
            return null;
        }

        Supplier supplier = supplierList.search(supplierId);
        if (supplier == null)
        {
            return null;
        }

        boolean success = product.link(supplier);
        success = supplier.assignProduct(product);
        if (success) {
            return product;
        }
        else {
            return null;
        }
    }

    public Product removeProdFromSupplier (String productId, String supplierId){
        Product product = inventory.search(productId);
        if (product == null)
        {
            return null;
        }

        Supplier supplier = supplierList.search(supplierId);
        if (supplier == null)
        {
            return null;
        }

        boolean success = product.unlink(supplier);
        success = supplier.removeProduct(product);
        if (success) {
            return product;
        }
        else {
            System.out.println("Error 4");
            return null;
        }
    }

    public Product searchProduct(String productId){
        return inventory.search(productId);
    }

    public Supplier searchSupplier(String supplierId){
        return supplierList.search(supplierId);
    }

    public boolean addSuppOrder(Order order)
    {
        return OrderList.addOrder(order);
    }

    public Iterator getProducts() {
        return inventory.getProducts();
    }

    public Iterator getSuppliers() {
        return supplierList.getSuppliers();
    }

    public Iterator getClients() {
        return clientList.getClients();
    }

    public Iterator<Supplier> getSuppliersOfProduct (Product p){
        return p.getSupplier();
    }

    public Iterator<Product> getProductBySupplier (Supplier s){
        return s.getProducts();
    }

    public Iterator<Float> getProductPrices (Product p){
        return p.getPrices();
    }

    public Supplier searchProductSupplier(Product product, Supplier supp){
        return product.SearchSupplyList(supp);
    }

    public boolean AddProductsToSuppOrder(Product prod, int q, Order o)
    {
        return o.addProductToOrder(prod, q);
    }

//    public boolean AddOrderToSupplier(Supplier s, Order o)
//    {
//        return s.add_Order(o);
//    }

    public Order CreateOrder(Supplier s)
    {
        Order order = new Order(s);
        return order;
    }

//    public Iterator<Order> getSuppOrders(Supplier s)
//    {
//        return s.getOrders();
//    }

    public Order searchSuppOrders(String oID)
    {
        return OrderList.search(oID);
    }
    public static Warehouse retrieve() {
        try {
            FileInputStream file = new FileInputStream("WarehouseData");
            ObjectInputStream input = new ObjectInputStream(file);
            input.readObject();
            ClientIDServer.retrieve(input);
            return warehouse;
        } catch(IOException ioe) {
            ioe.printStackTrace();
            return null;
        } catch(ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
            return null;
        }
    }

    public static  boolean save() {
        try {
            FileOutputStream file = new FileOutputStream("WarehouseData");
            ObjectOutputStream output = new ObjectOutputStream(file);
            output.writeObject(warehouse);
            output.writeObject(ClientIDServer.instance());
            return true;
        } catch(IOException ioe) {
            ioe.printStackTrace();
            return false;
        }
    }

    private void writeObject(java.io.ObjectOutputStream output) {
        try {
            output.defaultWriteObject();
            output.writeObject(warehouse);
        } catch(IOException ioe) {
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
        } catch(IOException ioe) {
            ioe.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    public String toString() {
        return clientList + "\n" + supplierList + "\n" + inventory + "\n" + orderList;
    }
}
