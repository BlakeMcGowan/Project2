import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.text.*;
import java.io.*;
public class LoginState extends WarehouseState implements ActionListener{
    private static final int MANAGER_LOGIN = 0;
    private static final int CLERK_LOGIN = 1;
    private static final int CLIENT_LOGIN = 2;
    private static final int EXIT = 3;
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private WarehouseContext context;
    private static LoginState instance;
    private JFrame frame;
    private AbstractButton userButton, logoutButton, clerkButton, managerButton;
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
        String enteredID = JOptionPane.showInputDialog(
            frame,"Please input the clerk id: ");
        WarehouseContext.setClerk(enteredID);
        
        (WarehouseContext.instance()).setLogin(WarehouseContext.IsClerk);
        (WarehouseContext.instance()).changeState(WarehouseContext.CLERK_STATE);
    }

    private void user(){
        String enteredID = JOptionPane.showInputDialog(
            frame,"Please input the user id: ");
        WarehouseContext.setClient(enteredID);
        
        (WarehouseContext.instance()).setLogin(WarehouseContext.IsClient);
        (WarehouseContext.instance()).changeState(WarehouseContext.CLIENT_STATE);
    }

    private void manager(){
        System.out.println("Enter ManagerID \n");
        String enteredID = JOptionPane.showInputDialog(
            frame,"Please input the manager id: ");
        WarehouseContext.setManager(enteredID);
         
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
        frame = WarehouseContext.instance().getFrame();
        frame.getContentPane().removeAll();
        frame.getContentPane().setLayout(new FlowLayout());
        userButton = new JButton("User");
        clerkButton =  new JButton("Clerk");
        managerButton = new JButton("Manager"); 
        logoutButton = new JButton("Logout");  
        userButton.addActionListener(this);
        clerkButton.addActionListener(this);
        managerButton.addActionListener(this);
        logoutButton.addActionListener(this);
        //clerkButton.addActionListener(this);      
        frame.getContentPane().add(this.userButton);
        frame.getContentPane().add(this.clerkButton);
        frame.getContentPane().add(this.managerButton);
        frame.getContentPane().add(this.logoutButton);
        frame.setVisible(true);
        frame.paint(frame.getGraphics()); 
        //frame.repaint();
        frame.toFront();
        frame.requestFocus();
        process();
    }

    public void clear() { //clean up stuff
        frame.getContentPane().removeAll();
        frame.paint(frame.getGraphics());   
      }

      public void actionPerformed(ActionEvent event) {
        if (event.getSource().equals(this.userButton)) 
            user();
        else if (event.getSource().equals(this.logoutButton)) 
            (WarehouseContext.instance()).changeState(2);
        else if (event.getSource().equals(this.clerkButton)) 
            clerk();
        else if (event.getSource().equals(this.managerButton))
            manager();
      }
}
