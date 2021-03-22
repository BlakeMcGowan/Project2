import java.io.Serializable;
import java.util.*;
import java.io.*;

public class Product implements Serializable {
    private String id;
    private int purchasePrice;
    private int quantityAvailable;
    private String productName;
    private List<Supplier> productSupplier = new LinkedList<Supplier>();
    private List<Float> productPrices = new LinkedList<Float>();
    private static final String PRODUCT_STRING = "P";

    //Constructor
    public Product(int purchasePrice, int quantityAvaliable, String productName){
        this.purchasePrice = purchasePrice;
        this.quantityAvailable = quantityAvaliable;
        this.productName = productName;
        id = PRODUCT_STRING + (ProductIDServer.instance()).getId();
    }

    public int getPurchasePrice(){
        return purchasePrice;
    }

    public int getQuantityAvailable(){
        return quantityAvailable;
    }

    public String getProductName(){
        return productName;
    }

    public String getId() {
        return id;
    }

    public void setPurchasePrice(int newPurchasePrice){
        purchasePrice = newPurchasePrice;
    }

    public void setQuantityAvailable(int newQuantityAvailable){
        quantityAvailable = newQuantityAvailable;
    }

    public void setProductName(String newProductName){
        productName = newProductName;
    }

    public boolean equals(String id) {
        return this.id.equals(id);
    }

    public boolean link(Supplier supplier){
        return productSupplier.add(supplier) ? true: false;
    }

    public boolean unlink(Supplier supplier){
        return productSupplier.remove(supplier) ? true: false;
    }

    public Iterator<Supplier> getSupplier() {
        return productSupplier.iterator();
    }

    public Supplier SearchSupplyList(Supplier supplier)
    {
        int i = 0;
        for (; i <= productSupplier.size()-1; i++)
        {
            if((productSupplier.get(i)) == supplier)
            {
                return productSupplier.get(i);
            }
        }
        return null;
    }

    public Boolean addPrice(Float price){
        return productPrices.add(price) ? true : false;
    }

    public Boolean removePrice(int position){
        if (productPrices.remove(position) >= 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public List<Supplier> getList(){
        return productSupplier;
    }

    public Iterator<Float> getPrices(){
        return productPrices.iterator();
    }

    public double moneyRound(float num) {
        return Math.round(num * 100.00) / 100.00;
    }

    public String toString() {
        return "Product: " + productName + " ID: " + id + " Qty: " + quantityAvailable;
    }

}
