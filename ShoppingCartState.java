import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.swing.event.ChangeEvent;
import javax.swing.text.View;

public class ShoppingCartState extends WarehouseState{
    private static ShoppingCartState shoppingCartState;
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static Warehouse warehouse;
    private static final int RETURN = 0;
    private static final int VIEW_CART = 1;
    private static final int ADD_ITEM = 2;
    private static final int REMOVE_ITEM = 3;
    private static final int CHANGE_QUANTITY = 4;
    private static final int HELP = 5;

    public static ShoppingCartState instance() {
        if(shoppingCartState == null){
            return shoppingCartState = new ShoppingCartState();
        }else{
            return shoppingCartState;
        }
    }

    private boolean yesOrNo(String prompt) {
        String more = getToken(prompt + " (Y|y)[es] or anything else for no");
        if (more.charAt(0) != 'y' && more.charAt(0) != 'Y') {
            return false;
        }
        return true;
    }

    public void help() {
        System.out.println("Enter a number between 0 and 5 as explained below:");
        System.out.println(RETURN + " to return to menu.");
        System.out.println(VIEW_CART + " to view your cart.");
        System.out.println(ADD_ITEM + " to add an item to your cart.");
        System.out.println(REMOVE_ITEM + " to remove an item from your cart.");
        System.out.println(CHANGE_QUANTITY + " to change the quantity of an item.");
        System.out.println(HELP + " to display this message.");
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
                if (value >= RETURN && value <= HELP) {
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
        while ((command = getCommand()) != RETURN) {
            switch (command) {

                case VIEW_CART          :  ViewCart();
                    break;
                case ADD_ITEM           :  AddItem();
                    break;
                case REMOVE_ITEM        :  RemoveItem();
                    break;
                case CHANGE_QUANTITY    :  ChangeQuantity();
                    break;
                case HELP               :  help();
                    break;
            }
        }
        ReturnToMenu();
    }

    public void ViewCart()
    {
        //logic for viewing a cart
    }

    public void AddItem()
    {
        //prompt for item id
        //prompt for quantity
        //logic for adding an item
    }

    public void RemoveItem()
    {
        //prompt for item id
        //prompt for quantity
        //logic for adding an item
    }

    public void ChangeQuantity()
    {
        //prompt for item id
        //prompt for quantity
        //logic for changing quantity
    }

    public void run() {
		process();
	}

    public void ReturnToMenu()
    {
        (WarehouseContext.instance()).changeState(2);
    }
}
