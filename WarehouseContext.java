import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.text.*;
import java.io.*;
public class WarehouseContext {

    private int currentState;
    private static Warehouse warehouse;
    private static WarehouseContext context;
    private int currentUser;
    static private String userID;
    static private String ManagerID;
    static private String ClerkID;
    static private String ClientID;
    private BufferedReader reader = new BufferedReader(new
            InputStreamReader(System.in));
    public static final int IsClerk = 0;
    public static final int IsClient = 1;
    public static final int IsManager = 2;
    public static final int MANAGER_STATE= 0;
    public static final int CLERK_STATE = 1;
    public static final int CLIENT_STATE = 2;
    public static final int LOGIN_STATE = 3;
    public static final int SHOPPING_CART_STATE = 4;
    public static final int QUERY_STATE = 5;
    private static JFrame LibFrame; 


    private WarehouseState[] states;
    private int[][] nextState;

/*    private void retrieve() {
        try {
            Warehouse tempWarehouse = Warehouse.retrieve();
            if (tempWarehouse != null) {
                System.out.println(" The warehouse has been successfully retrieved from the file WarehouseData \n" );
                warehouse = tempWarehouse;
            } else {
                System.out.println("File doesnt exist; creating new warehouse" );
                warehouse = Warehouse.instance();
            }
        } catch(Exception cnfe) {
            cnfe.printStackTrace();
        }
    }
    */

    public void setLogin(int code)
    {currentUser = code;}

    static public void setUser(String uID)
    { userID = uID;}

    public int getLogin()
    { return currentUser;}

    public static String getUser()
    { return userID;}

    public static void setManager(String uID)
    {ManagerID = uID;}

    public static void setClerk(String uID)
    {ClerkID = uID;}

    public static void setClient(String uID)
    {ClientID = uID;}

    public static String getManager()
    { return ManagerID;}

    public static String getClerk()
    { return ClerkID;}

    public static String getClient()
    { return ClientID;}

    public JFrame getFrame()
    { return LibFrame;}

    private WarehouseContext() { //constructor
        // retrieve();

        // set up the FSM and transition table;
        states = new WarehouseState[6];
        states[0] =  ManagerState.instance();
        states[1] =  Clerkstate.instance();
        states[2] =  ClientState.instance();
        states[3] =  LoginState.instance();
        states[4] =  ShoppingCartState.instance();
        states[5] = QueryState.instance();


        nextState = new int[6][6];
        nextState[MANAGER_STATE][MANAGER_STATE] = -1;
        nextState[MANAGER_STATE][CLERK_STATE] = CLERK_STATE;
        nextState[MANAGER_STATE][CLIENT_STATE] = CLIENT_STATE;
        nextState[MANAGER_STATE][LOGIN_STATE] = LOGIN_STATE;
        nextState[CLIENT_STATE][SHOPPING_CART_STATE] = SHOPPING_CART_STATE;
        nextState[CLERK_STATE][QUERY_STATE] = QUERY_STATE;

        nextState[LOGIN_STATE][MANAGER_STATE] = MANAGER_STATE;
        nextState[LOGIN_STATE][CLERK_STATE] = CLERK_STATE;
        nextState[LOGIN_STATE][CLIENT_STATE] = CLIENT_STATE;
        nextState[LOGIN_STATE][LOGIN_STATE] = -1;

        nextState[CLIENT_STATE][SHOPPING_CART_STATE] = SHOPPING_CART_STATE;
        nextState[SHOPPING_CART_STATE][CLIENT_STATE] = CLIENT_STATE;

        currentState = LOGIN_STATE;


        LibFrame = new JFrame("Library GUI");
        LibFrame.setSize(200,200);
    }

    public void changeState(int transition)
    {
        //System.out.println("current state " + currentState + " \n \n ");
        currentState = nextState[currentState][transition];
        if (currentState == -2)
        {System.out.println("Error has occurred"); terminate();}
        if (currentState == -1)
            terminate();
        //System.out.println("current state " + currentState + " \n \n ");
        states[currentState].run();
    }

    private void terminate()
    {
        if (IOHelper.yesOrNo("Save data?")) {
            if (warehouse.save()) {
                System.out.println(" The warehouse has been successfully saved in the file WarehouseData \n" );
            } else {
                System.out.println(" There has been an error in saving \n" );
            }
        }
        System.out.println(" Goodbye \n "); System.exit(0);
    }

    public static WarehouseContext instance() {
        if (context == null) {
            System.out.println("calling constructor");
            context = new WarehouseContext();
        }
        return context;
    }

    public void process(){
        states[currentState].run();
    }

    public static void main (String[] args){
        WarehouseContext.instance().process();
    }
}
