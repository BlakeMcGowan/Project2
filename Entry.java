import java.io.Serializable;
import java.util.*;

class Entry implements Serializable
{
    public Entry(String product, int quantity)
    {
        productID = product;
        count = quantity;
    }
    String productID;
    int count;
}
