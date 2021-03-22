import java.io.Serializable;
import java.util.List;

public class Client implements Serializable {
    private static final long serialVersionUID = 1L;
    private String clientid;
    private String name;
    private String phone;
    private String address;
    private double balance;
    private static final String MEMBER_STRING = "C";
    private ShoppingCart cart;
    private List<Order> orders;
    private Warehouse warehouse;

    //Constructor
    public Client(String name, String phone, String address){
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.balance = 0.00;
        clientid = MEMBER_STRING + (ClientIDServer.instance()).getId();
        cart = new ShoppingCart();
    }

    public String getClientId() {
        return clientid;
    }

    public String getName(){
        return name;
    }

    public String getPhone(){
        return phone;
    }

    public String getAddress(){
        return address;
    }

    public void setName(String newName){
        name = newName;
    }

    public void setPhone(String newPhone){
        phone = newPhone;
    }

    public void setAddress(String newAddress){
        address = newAddress;
    }

    public boolean equals(String id) {
        return this.clientid.equals(id);
    }
    public String toString() {
        String string = "Client name " + name + " address " + address + " id " + clientid + " phone " + phone + " Balance";
        return string;
    }

    public void addToCart(String productID, int quantity)
    {
        cart.addProduct(productID, quantity);
    }

    public void removeFromCart(String productID, int quantity)
    {
        cart.removeProduct(productID, quantity);
    }

    public double getBalance(){
        return balance;
    }

    public void setBalance(double expense){
        balance += expense;
    }

    /*
    public void checkOut()
    {
        orders.add(new Order(cart, warehouse));
    }
    */
}
