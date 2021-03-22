import java.util.*;
import java.lang.*;
import java.io.*;

public class OrderList implements Serializable {
    private static final long serialVersionUID = 1L;
    private static List<Order> Orders = new LinkedList<Order>();
    private static OrderList orderList;

    private OrderList() {}

    public static boolean addOrder(Order order){
        return Orders.add(order);
    }

    public static OrderList instance() {
        if (orderList == null) {
            return (orderList = new OrderList());
        }
        else {
            return orderList;
        }
    }

    public static Order search(String orderID)
    {
        for (Iterator iterator = Orders.iterator(); iterator.hasNext(); )
        {
            Order Order = (Order) iterator.next();
            if (Order.getID().equals(orderID))
            {
                return Order;
            }
        }
        return null;
    }

    private void writeObject(java.io.ObjectOutputStream output) {
        try {
            output.defaultWriteObject();
            output.writeObject(orderList);
        }
        catch(IOException ioe) {
            System.out.println(ioe);
        }
    }

    private void readObject(java.io.ObjectInputStream input) {
        try {
            if (orderList != null) {
                return;
            }
            else {
                input.defaultReadObject();
                if (orderList == null) {
                    orderList = (OrderList) input.readObject();
                }
                else {
                    input.readObject();
                }
            }
        }
        catch(IOException ioe) {
            System.out.println("in Orders readObject \n" + ioe);
        }
        catch(ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }
    }
}