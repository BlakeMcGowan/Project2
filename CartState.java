import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

public class CartState extends WarehouseState {
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static Warehouse warehouse;
    private static CartState cartState;
    private static ShoppingCart cart;
    private List<Entry> items = new LinkedList<Entry>();
    private static final int LOGOUT = 0;
    private static final int VIEW_CART = 1;
    private static final int ADD_PRODUCT = 2;
    private static final int REMOVE_PRODUCT = 3;
    private static final int EDIT_CART = 4;
    private static final int HELP = 5;


    public List<Entry> getItems()
    {
        return items;
    }

    public void addToCart(String productID, int quantity)
    {
        cart.addProduct(productID, quantity);
    }

    public void removeFromCart(String productID, int quantity)
    {
        cart.removeProduct(productID, quantity);
    }

    public static CartState instance() {
        if (cartState == null) {
            return cartState = new CartState();
        } else {
            return cartState;
        }
    }

    public void help() {
        System.out.println("Enter a number between 0 and 4 as explained below:");
        System.out.println(LOGOUT + " to Logout.");
        System.out.println(VIEW_CART + " to view current cart.");
        System.out.println(ADD_PRODUCT + " to add a product.");
        System.out.println(REMOVE_PRODUCT + " to remove a product.");
        System.out.println(EDIT_CART + " to edit the cart.");
    }

    public String getToken(String prompt) {
        do {
            try {
                System.out.println(prompt);
                String line = reader.readLine();
                StringTokenizer tokenizer = new StringTokenizer(line);
                if (tokenizer.hasMoreTokens()) {
                    return tokenizer.nextToken();
                }
            } catch (IOException ioe) {
                System.exit(0);
            }
        } while (true);
    }

    public int getCommand() {
        do {
            try {
                int value = Integer.parseInt(getToken("Enter command: " + HELP + " for help"));
                if (value >= LOGOUT && value <= HELP) {
                    return value;
                }
            } catch (NumberFormatException nfe) {
                System.out.println("Enter a number");
            }
        } while (true);
    }





    public void process() {
        int command;
        help();
        while ((command = getCommand()) != LOGOUT) {
            switch (command) {

                case VIEW_CART              :  getItems();
                    break;

//                case ADD_PRODUCT              :  addProduct();
//                    break;
//
//                case REMOVE_PRODUCT          :  removeProduct();
//                    break;
            }
        }
    }
    public void run() {
        process();
    }
}
