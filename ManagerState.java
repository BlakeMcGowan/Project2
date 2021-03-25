import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Scanner;
import java.util.StringTokenizer;


public class ManagerState extends WarehouseState{
    private static ManagerState managerState;
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static Warehouse warehouse;
    private WarehouseContext context;
    private static final int LOGOUT = 0;
    private static final int ADD_PRODUCT = 1;
    private static final int ADD_SUPPLIER = 2;
    private static final int SHOW_SUPPLIER_LIST = 3;
    private static final int SUPPLIERS_FOR_PRODUCT = 4;
    private static final int PRODUCTS_FOR_SUPPLIERS = 5;
    private static final int UPDATE_PRODUCTS = 6;
    private static final int BECOME_SALESCLERK = 7;
    private static final int HELP = 8;


   /* private ManagerState() {
        if (yesOrNo("Look for saved data and  use it?")) {
        //    retrieve();
        } else {
            warehouse = Warehouse.instance();
        }
    }
    */

    /*private void retrieve() {
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
    }*/

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

    public static ManagerState instance() {
        if (managerState == null) {
            return managerState = new ManagerState();
        } else {
            return managerState;
        }
    }

    private boolean yesOrNo(String prompt) {
        String more = getToken(prompt + " (Y|y)[es] or anything else for no");
        if (more.charAt(0) != 'y' && more.charAt(0) != 'Y') {
            return false;
        }
        return true;
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
        System.out.println("Enter a number between 0 and 4 as explained below:");
        System.out.println(LOGOUT + " to Logout.");
        System.out.println(ADD_PRODUCT + " to add a product.");
        System.out.println(ADD_SUPPLIER + " to add a supplier.");
        System.out.println(SHOW_SUPPLIER_LIST + " to show the supplier list.");
        System.out.println(SUPPLIERS_FOR_PRODUCT + " to access the list of suppliers for products.");
        System.out.println(PRODUCTS_FOR_SUPPLIERS + " to access the list of products for suppliers.");
        System.out.println(UPDATE_PRODUCTS + " to update product prices.");
        System.out.println(BECOME_SALESCLERK + " to access the salesclerk menu.");

    }

    public void addProduct() {
        Product result;
        Product product;
        do {
            String title = getToken("Enter Product Name: ");
            String pID = getToken("Please enter productID");
            String Quantity = getToken("Enter quantity: ");
            String Price = getToken("Enter the price: ");
            product = warehouse.findProduct(pID);
            int quantity = Integer.parseInt(Quantity);
            double price = Double.parseDouble(Price);
            result = warehouse.addProduct(title, pID, price, quantity);
            if (result != null) {
                System.out.println(result);
            } else {
                System.out.println("Product could not be added. ");
            }
            if (!yesOrNo("Add more product?")) {
                break;
            }
        } while (true);
    }

    public void addSupplier() {
        Supplier result;
        do {
            String name = getToken("Enter Supplier Name: ");
            String address = getToken("Enter Address: ");
            String phone = getToken("Enter Description: ");
            result = warehouse.addSupplier(name, address, phone);
            if (result != null) {
                System.out.println(result);
            } else {
                System.out.println("Supplier could not be added. ");
            }
            if (!yesOrNo("Add more suppliers?")) {
                break;
            }
        } while (true);
    }

    public void showSupplier() {

        Iterator<Supplier> allSuppliers = warehouse.getSuppliers();
        System.out.println("---------------------------------------------------------------");
        while (allSuppliers.hasNext()){
            Supplier supplier = allSuppliers.next();
            System.out.println(supplier.toString());
        }
        System.out.println("---------------------------------------------------------------\n");
    }

    public void listSuppliersOfProduct()
    {
        Supplier supplier;
        float price;
        String pID = getToken("Enter the product ID: ");
        Product product = warehouse.findProduct(pID);
        if (product != null)
        {
            Iterator<Supplier> s_traversal = warehouse.getSuppliersOfProducts(product);
            Iterator<Float> price_traversal = warehouse.getProductPrices(product);
            while (((s_traversal.hasNext())) && ((price_traversal.hasNext())))
            {
                supplier = s_traversal.next();
                price = price_traversal.next();
                System.out.println("Supplier: " + supplier.getName() + ". Supply Price: $" + price);
            }
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
            while (p_traversal.hasNext())
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


    public void updateProducts(){
        Product result;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter 1 to add a product, 2 to remove a product, or 3 to edit a current product");
        int choice = scanner.nextInt();

        if (choice == 1){
            addProduct();
        }

        else if (choice == 2){



        }

        else{

        }
    }

    public void becomeSalesclerk(){
        (WarehouseContext.instance()).changeState(0);
    }

    public void process() {
        int command;
        help();
        while ((command = getCommand()) != LOGOUT) {
            switch (command) {


                case ADD_PRODUCT              :  addProduct();
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
