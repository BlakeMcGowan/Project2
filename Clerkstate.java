import java.util.*;
import java.text.*;
import java.io.*;
public class Clerkstate extends WarehouseState {
  private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
  private static Warehouse warehouse;
  private WarehouseContext context;
  private static Clerkstate instance;
  private static final int LOG_OUT = 0;
  private static final int ADD_CLIENT = 1;
  private static final int ADD_PRODUCT = 2;
  private static final int VIEW_CLIENTS = 3;
  private static final int VIEW_PRODUCTS = 4;
  private static final int VIEW_SUPPLIERS = 5;
  private static final int LIST_SUPP_OF_PROD = 6;
  private static final int LIST_PROD_BY_SUPP = 7;
  private static final int RECEIVE_A_SHIPMENT = 8;
  private static final int USERMENU = 9;
  private static final int HELP = 10;
  private Clerkstate() {
      super();
      warehouse = Warehouse.instance();
      //context = WarehouseContext.instance();
  }

  public static Clerkstate instance() {
    if (instance == null) {
      instance = new Clerkstate();
    }
    return instance;
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
  public int getNumber(String prompt) {
    do {
      try {
        String item = getToken(prompt);
        Integer num = Integer.valueOf(item);
        return num.intValue();
      } catch (NumberFormatException nfe) {
        System.out.println("Please input a number ");
      }
    } while (true);
  }
  public Calendar getDate(String prompt) {
    do {
      try {
        Calendar date = new GregorianCalendar();
        String item = getToken(prompt);
        DateFormat df = SimpleDateFormat.getDateInstance(DateFormat.SHORT);
        date.setTime(df.parse(item));
        return date;
      } catch (Exception fe) {
        System.out.println("Please input a date as mm/dd/yy");
      }
    } while (true);
  }
  public int getCommand() {
    do {
      try {
        int value = Integer.parseInt(getToken("Enter command:" + HELP + " for help"));
        if (value >= LOG_OUT && value <= HELP) {
          return value;
        }
      } catch (NumberFormatException nfe) {
        System.out.println("Enter a number");
      }
    } while (true);
  }

  public void help() {
    System.out.println("Enter a number between 0 and 10 as explained below:");
    System.out.println(LOG_OUT + " to Exit.");
    System.out.println(ADD_CLIENT + " to add a client.");
    System.out.println(ADD_PRODUCT + " to add a product.");
    System.out.println(VIEW_CLIENTS + " to show the client list.");
    System.out.println(VIEW_PRODUCTS + " to show the product list.");
    System.out.println(VIEW_SUPPLIERS + " to show the suppliers list.");
    System.out.println(LIST_SUPP_OF_PROD + " to list suppliers of a specified product");
    System.out.println(LIST_PROD_BY_SUPP + " to list products supplied by a specified supplier");
    System.out.println(RECEIVE_A_SHIPMENT + " to receive a Shipment from the supplier");
    System.out.println(USERMENU + " to  switch to the user menu");
    System.out.println(HELP + " for help");
  }

  public void addClient()
  {
    String Name = getToken("Enter client name: ");
    String Address = getToken("Enter client address: ");
    String Phone = getToken("Enter client phone: ");
    double Billing;
    while (true)
    {
      String balance = getToken("Enter client billing info(balance): ");
      try {
        Billing = Double.parseDouble(balance);
        break; // will only get to here if input was a double
        } catch (NumberFormatException ignore) {
        System.out.println("Invalid input");
      }
    }
    Client result;
    result = warehouse.addMember(Name, Address, Phone);
    if (result == null)
    {
      System.out.println("Could not add client.");
    }
    else
    {
      System.out.println(result);
    }
  }

  public void addProduct()
  {
    String Name = getToken("Enter product name: ");
    String id = getToken("Enter product id: ");
    double price = getNumber("Enter product price: ");
    int quantity = getNumber("Enter product quantity: ");
    Product result;
    result = warehouse.addProduct(Name, id, price, quantity);
    if (result == null)
    {
      System.out.println("Could not add product.");
    }
    else
    {
      System.out.println(result);
    }
  }
  
  public void showSupplier()   {
    Iterator<Shipment>  allSuppliers = warehouse.getSuppliers();
    if(allSuppliers.hasNext() == false){
      System.out.println("No Supplier in the System.\n");
      return;
    }else{
      System.out.println("-------------------------------------------------");
      while ((allSuppliers.hasNext()) != false)
      {
        Shipment supplier = allSuppliers.next();
        System.out.println(supplier.toString());
      }
      System.out.println("-------------------------------------------------\n");
    }
  }
   
   public void showProducts()   {
    Iterator<Product> allProducts = warehouse.getProducts();
    if(allProducts.hasNext()== false){
      System.out.println("No products in the System.\n");
      return;
    }else{
      System.out.println("------------------------");
        while ((allProducts.hasNext()) != false)
        {
          Product product = allProducts.next();
          System.out.println(product.toString());
        }
        System.out.println("------------------------\n");
      }
  }
   
  public void showClient()   {
    Iterator<Client>  allClients = warehouse.getMembers();
    if(allClients.hasNext()==false){
      System.out.println("No Clients exist in the system. \n");
    }else{
      System.out.println("------------------------------------------------------------------------------------");
        while ((allClients.hasNext()) != false)
        {
          Client client = allClients.next();
          System.out.println(client.toString());
        }
        System.out.println("------------------------------------------------------------------------------------\n");
      }
  }

  
  public void listSuppliersOfProduct()
  {
    Double price;
    String pID = getToken("Enter the product ID: ");
    Product product = warehouse.findProduct(pID);
    System.out.println("-----------------------------------------------");
    if (product != null)
    {
      Shipment shipment;
      Iterator<Shipment> allSuppliers = warehouse.getSuppliersOfProduct(product);
      while ((allSuppliers.hasNext()) != false)
      {
        shipment = allSuppliers.next();
        System.out.println("Supplier: " + shipment.getSupplier().getName() + ". Price: $" + shipment.supplierPrice() + " Quantity: " + shipment.getQuantity());
      }
      System.out.println("-----------------------------------------------\n");
    }
    else
    {
      System.out.println("Product not found");
    }
  }
  
  public void listProductsBySupplier()
  {
    String s = getToken("Please enter supplier ID: ");
    Supplier supplier = warehouse.findSupplier(s);
    if (supplier != null)
    {
      Product p_temp;
      Iterator<Product> p_traversal = warehouse.getProductBySupplier(supplier);
      while (p_traversal.hasNext() != false)
      {
          p_temp = p_traversal.next();
          System.out.println(p_temp.getSupplierList());
      }
    }
    else
    {
      System.out.println("Supplier doesn't exist");
    }
  }

  private void ReceiveShipment()
  {
    int O_count = 1;
    String oID = getToken("Enter the order ID: ");
    Order order ;
    while((order = warehouse.findOrder(oID)) == null){
      System.out.println("No such order found. ");
      if(O_count++ == 3){
        System.out.println("You have reached the maximum try. Try next time.\n");
      }
      oID = getToken("Enter the valild order ID: ");
    }

    if (order.getOrderStatus() == true)
    {
      System.out.println("Order has already been processed and received\n");
      return;
    }

    Supplier supplier = order.getSupplier();
    Iterator<Product> allProducts = order.getProds();
    Product p;
    Iterator<Integer> quantities = order.getQs();
    int q;
    System.out.println("Order details");
    System.out.println("-------------");
    while (allProducts.hasNext() && quantities.hasNext())
    {
      int j = 1;
      p = allProducts.next();
      q = quantities.next();
      System.out.println("Product: " + p.getId() + ", Quantity: " + q);
      j++;
    }
    boolean add = yesOrNo("Accept order?");
    if (add)
    {
      Iterator<Product> products = order.getProds();
      Product prod;
      Iterator<Integer> qtys = order.getQs();
      int quant;
      Shipment shipment;
      while (products.hasNext() && qtys.hasNext())
      {
        int j = 1;
        prod = products.next();
        quant = qtys.next();
        shipment = warehouse.searchProductSupplier(prod, supplier);
        if (shipment.getQuantity() == 0)
        {
          shipment.setNewQuantity(-1 * quant);
          //fullfill the waitlist first
          warehouse.FulfillWaitlist(prod, quant, shipment);
        }
        else
        {
          shipment.setNewQuantity(-1 * quant);
        }
        j++;
      }
      order.receiveOrder();//shipment has been received
    }
    System.out.println("Remaining products successfully added to inventory.\n");
  }

  /*
  public void usermenu()
  {
    String userID = getToken("Please input the user id: ");
    if (Warehouse.instance().searchMembership(userID) != null){
      (WarehouseContext.instance()).setUser(userID);      
      (WarehouseContext.instance()).changeState(1);
    }
    else 
      System.out.println("Invalid user id."); 
  }
  */

  public void logout()
  {
    if ((WarehouseContext.instance()).getLogin() == WarehouseContext.IsManager) {
      System.out.println(" going to Manager \n ");
      (WarehouseContext.instance()).changeState(WarehouseContext.MANAGER_STATE); // exit with a code 1

    } else if (WarehouseContext.instance().getLogin() == WarehouseContext.IsClerk) {
      System.out.println(" going to login \n");
      (WarehouseContext.instance()).changeState(WarehouseContext.LOGIN_STATE); // exit with a code 2

    } else {
      (WarehouseContext.instance()).changeState(WarehouseContext.CLERK_STATE); // exit code 2, indicates error
    } 
  }
 
  public void switchtoUser(){
    System.out.println("Enter ClientID \n");
    try{
      String line = reader.readLine();
      WarehouseContext.setClient(line);
    } catch (IOException ioe) {
      System.exit(0);
    }
    (WarehouseContext.instance()).changeState(WarehouseContext.CLIENT_STATE);
  }

  public void process() {
    int command;
    help();
    while ((command = getCommand()) != LOG_OUT) {
      switch (command) {
        case ADD_CLIENT:        addClient();
                                break;
        case ADD_PRODUCT:       addProduct();
                                break;
        case VIEW_CLIENTS      :  showClient();
                                break;                             
        case VIEW_PRODUCTS     :  showProducts();
                                break;
        case VIEW_SUPPLIERS    :  showSupplier();
                                break;
        case LIST_SUPP_OF_PROD  :  listSuppliersOfProduct();
                                break;                         
        case LIST_PROD_BY_SUPP  :  listProductsBySupplier();
                                break;    
        case RECEIVE_A_SHIPMENT   : ReceiveShipment();
                                  break;
        case USERMENU   : switchtoUser();
                                   break;
        case HELP:              help();
                                break;
      }
    }
    logout();
  }
  public void run() {
    process();
  }
}
