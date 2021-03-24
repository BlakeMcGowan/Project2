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
    
    private ClientState() {
        if (yesOrNo("Look for saved data and  use it?")) {
            retrieve();
        } else {
            warehouse = Warehouse.instance();
        }
    }

    private void retrieve() {
        try {
            Warehouse tempWarehouse = Warehouse.retrieve();
            if (tempWarehouse != null) {
                System.out.println("The warehouse has been successfully retrieved from the file WarehouseData \n" );
                warehouse = tempWarehouse;
            } else {
                System.out.println("File doesn't exist; creating new warehouse" );
                warehouse = Warehouse.instance();
            }
        } catch(Exception cnfe) {
            cnfe.printStackTrace();
        }
    }

    public static ClientState instance() {
        if (clientState == null) {
            return clientState = new ClientState();
        } else {
            return clientState;
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


                case LOGOUT              :  logout();
                    break;

                case SHOW_CLIENT_INFO          :  showClientInfo();
                    break;

                case SHOW_PRODUCT_LIST  :  showProductList();
                    break;

                case SHOW_CLIENT_TRANSACTIONS :  showClientTransactions();
                    break;

                case MODIFY_SHOPPING_CART :  modifyShoppingCart();
                    break;

                case SHOW_WAIT_LIST :  showWaitList();
                    break;

                case HELP               :  help();
                    break;
            }
        }
    }

	public void run() {
		process();
	}

    private void logout()
    {
        WarehouseContext.instance().setLogin(WarehouseContext.IsUser);
        //WarehouseContext.instance().changeState(); Needs state for login state
    }

    private void showClientInfo()
    {
        Client client = warehouse.getClient(WarehouseContext.getUser());
        System.out.println(client.toString());
        System.out.println(client.toStringBalance());
    }

    private void showProductList()
    {
        System.out.println(warehouse.ProductListToString());
    }
    
    private void showClientTransactions()
    {
        Client client = warehouse.getClient(WarehouseContext.getUser());
        //transactions to string out
    }

    private void modifyShoppingCart()
    {
        //WarehouseContext.instance().changeState(); Needs state for shopping cart
    }

    private void showWaitList()
    {
        //functionaility for showing client's waitlist
    }
}
