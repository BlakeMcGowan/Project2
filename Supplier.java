import java.util.*;
import java.io.*;

public class Supplier implements Serializable {

    private static final long serialVersionUID = 1L;
    private String name;
    private String address;
    private String phone;
    private String id;
    private List<Product> Product = new LinkedList<Product>();
    private static final String MEMBER_STRING = "M";
    private List transactions = new LinkedList();

    public Supplier(String name, String address, String phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        id = MEMBER_STRING + (SupplierIDServer.instance()).getId();
    }

    public Iterator<Product> getProducts(){
        return Product.iterator();
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getId() {
        return id;
    }

    public void setName(String newName) {
        name = newName;
    }

    public void setAddress(String newAddress) {
        address = newAddress;
    }

    public void setPhone(String newPhone) {
        phone = newPhone;
    }

    public boolean equals(String id) {
        return this.id.equals(id);
    }

    @Override
    public String toString() {
        String string = "Supplier " + name + " address " + address + " id " + id + " phone " + phone;
        return string;
    }
}
