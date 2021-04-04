import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.StringTokenizer;

public class QueryState extends WarehouseState {
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static Warehouse warehouse;
    private static final int LOGOUT = 0;
    private static final int DISPLAY_ALL_CLIENTS = 1;
    private static final int CLIENTS_WITH_BALANCE = 2;
    private static final int CLIENTS_WITH_NO_TRANSACTIONS = 3;
    private static final int HELP = 4;



    private static QueryState queryState;


    public static QueryState instance() {
        if (queryState == null) {
            return queryState = new QueryState();
        } else {
            return queryState;
        }
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

    public void help() {
        System.out.println("Enter a number between 0 and 3 as explained below:");
        System.out.println(LOGOUT + " to Logout.");
        System.out.println(DISPLAY_ALL_CLIENTS + " to view all clients.");
        System.out.println(CLIENTS_WITH_BALANCE + " to view clients with a balance.");
        System.out.println(CLIENTS_WITH_NO_TRANSACTIONS + " to view clients with no transactions.");
    }

    public void displayClients(){

        Iterator<Client> allClient = warehouse.getMembers();
        System.out.println("---------------------------------------------------------------");
        while (allClient.hasNext()){
            Client client = allClient.next();
            System.out.println(client.toString());
        }
        System.out.println("---------------------------------------------------------------\n");

    }

    public void clientsWithBalance(){

        Iterator <Client> unpaidClient =  warehouse.getMembers();
        System.out.println("---------------------------------------------------------------");
        while (unpaidClient.hasNext()){
            Client client = unpaidClient.next();
            if (client.getBalance() > 0)
                System.out.println(client.toString());
        }
        System.out.println("---------------------------------------------------------------\n");
    }

    public void clientsWithNoTransactions(){

    }


    public void process() {
        int command;
        help();
        while ((command = getCommand()) != LOGOUT) {
            switch (command) {


                case DISPLAY_ALL_CLIENTS              :  displayClients();
                    break;

                case CLIENTS_WITH_BALANCE          :  clientsWithBalance();
                    break;

                case CLIENTS_WITH_NO_TRANSACTIONS          :  clientsWithNoTransactions();
                    break;

                case HELP          :  help();
                    break;
            }
        }
    }

    public void run() {
        process();
    }
}
