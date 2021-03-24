import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Scanner;
import java.util.StringTokenizer;

public class ClientState extends WarehouseState{
    private static ClientState clientState;
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static Warehouse warehouse;
    private static final int LOGOUT = 0;
    private static final int SHOW_CLIENT_INFO = 1;
    private static final int SHOW_PRODUCT_LIST = 2;
    private static final int SHOW_CLIENT_TRANSACTIONS = 3;
    private static final int MODIFY_SHOPPING_CART = 4;
    private static final int SHOW_WAIT_LIST = 5;
    private static final int HELP = 6;
    
    public static ClientState instance() {
        if (clientState == null) {
            return clientState = new ClientState();
        } else {
            return clientState;
        }
    }

    public void help() {
        System.out.println("Enter a number between 0 and 6 as explained below:");
        System.out.println(LOGOUT + " to Logout.");
        System.out.println(SHOW_CLIENT_INFO + " to display account info.");
        System.out.println(SHOW_PRODUCT_LIST + " to display the product list.");
        System.out.println(SHOW_CLIENT_TRANSACTIONS + " to display client transaction history.");
        System.out.println(MODIFY_SHOPPING_CART + " to make changes to your shopping cart.");
        System.out.println(SHOW_WAIT_LIST + " to view your waitlist.");
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


                case LOGOUT              :  addProduct();
                    break;

                case ADD_SUPPLIER          :  addSupplier();
                    break;

                case SHOW_SUPPLIER_LIST  :  showSupplier();
                    break;

                case SUPPLIERS_FOR_PRODUCT :  listSuppliersOfProduct();
                    break;

                case HELP               :  help();
                    break;

                case PRODUCTS_FOR_SUPPLIERS :  listProductsBySupplier();
                    break;

                case UPDATE_PRODUCTS : updateProducts();
                    break;

                case BECOME_SALESCLERK : becomeSalesclerk();
                    break;
            }
        }
    }

	public void run() {
		process();
	}
    
}
