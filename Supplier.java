import java.io.Serializable;
import java.util.*;
public class Supplier implements Serializable
{
    private static final long serialVersionUID = 1L;
    private String id;
    private String supplierDescription;
    private String supplierAddress;
    private String supplierName;
    private List SupplierList = new LinkedList();
    private static final String SUPPLIER_STRING = "S";
    private List<Product> Product = new LinkedList<Product>();
   // private List<Order> orders = new LinkedList<Order>();

    //Constructor
    public Supplier(String supplierName, String supplierAddress, String supplierDescription){
        this.supplierName = supplierName;
        this.supplierAddress = supplierAddress;
        this.supplierDescription = supplierDescription;
     //   id = SUPPLIER_STRING + (SupplierIDServer.instance()).getId();
    }

    public boolean assignProduct(Product product) {
        if (Product.add(product))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean removeProduct(Product product){
        if (Product.remove(product))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public Iterator<Product> getProducts(){
        return Product.iterator();
    }

    public String getSupplierDescription(){
        return supplierDescription;
    }

    public String getSupplierAddress(){
        return supplierAddress;
    }

    public String getSupplierName(){
        return supplierName;
    }

    public String getId(){
        return id;
    }

    public void setSupplierDescription(String newDescription){
        supplierDescription = newDescription;
    }

    public void setSupplierAddress(String newSupplierAddress){
        supplierAddress = newSupplierAddress;
    }

    public void setSupplierName(String newSupplierName){
        supplierName= newSupplierName;
    }

    public boolean equals(String id) {
        return this.id.equals(id);
    }

//    public Boolean add_Order(Order o)
//    {
//        return orders.add(o);
//    }
//
//    public Iterator<Order> getOrders()
//    {
//        return orders.iterator();
//    }
    public String toString() {
        String string = " Name:" + supplierName + "   address:"+ supplierAddress + "    description:"+ supplierDescription +"  ID:"+ id +"  ";
        return string;
    }
}