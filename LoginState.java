import java.util.*;
import java.text.*;
import java.io.*;
public class LoginState extends WarehouseState{
    private static final int MANAGER_LOGIN = 0;
    private static final int CLERK_LOGIN = 1;
    private static final int CLIENT_LOGIN = 2;
    private static final int EXIT = 3;
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private WarehouseContext context;
    private static LoginState instance;
    private LoginState() {
        super();
        // context = LibContext.instance();
    }

    public static LoginState instance() {
        if (instance == null) {
            instance = new LoginState();
        }
        return instance;
    }

    public int getCommand() {
        do {
            try {
                int value = Integer.parseInt(getToken("Enter command:" ));
                if (value <= EXIT && value >= CLERK_LOGIN) {
                    return value;
                }
            } catch (NumberFormatException nfe) {
                System.out.println("Enter a number");
            }
        } while (true);
    }

    public String getToken(String prompt) {
        do {
            try {
                System.out.println(prompt);
                String line = reader.readLine();
                StringTokenizer tokenizer = new StringTokenizer(line,"\n\r\f");
                if (tokenizer.hasMoreTokens()) {
                    return tokenizer.nextToken();
                }
            } catch (IOException ioe) {
                System.exit(0);
            }
        } while (true);
    }

    private boolean yesOrNo(String prompt) {
        String more = getToken(prompt + " (Y|y)[es] or anything else for no");
        if (more.charAt(0) != 'y' && more.charAt(0) != 'Y') {
            return false;
        }
        return true;
    }

    private void clerk(){
        System.out.println("Enter ClerkID \n");
        try{
            String line = reader.readLine();
            WarehouseContext.setClerk(line);
        } catch (IOException ioe) {
            System.exit(0);
        }
        (WarehouseContext.instance()).setLogin(WarehouseContext.IsClerk);
        (WarehouseContext.instance()).changeState(0);
    }

    private void user(){
        System.out.println("Enter ClientID \n");
        try{
            String line = reader.readLine();
            WarehouseContext.setClient(line);
        } catch (IOException ioe) {
            System.exit(0);
        }
        (WarehouseContext.instance()).setLogin(WarehouseContext.IsClient);
        (WarehouseContext.instance()).changeState(1);
    }

    private void manager(){
        System.out.println("Enter ManagerID \n");
        try{
            String line = reader.readLine();
            WarehouseContext.setManager(line);
        } catch (IOException ioe) {
            System.exit(0);
        } 
        (WarehouseContext.instance()).setLogin(WarehouseContext.IsManager);
        (WarehouseContext.instance()).changeState(WarehouseContext.MANAGER_STATE);
    }

    public void process() {
        int command;
        System.out.println("Enter a command \n"+
                "input 1 to login as a manager\n" +
                "input 2 to login as clerk\n"+
                "input 3 to login as a client\n");
        while ((command = getCommand()) != EXIT) {

            switch (command) {
                case MANAGER_LOGIN:       manager();
                break;
                case CLERK_LOGIN:       clerk();
                    break;
                case CLIENT_LOGIN:        user();
                    break;
                default:                System.out.println("Invalid choice");

            } 
        }
        (WarehouseContext.instance()).changeState(2);
    }

    public void run() {
        process();
    }
}
