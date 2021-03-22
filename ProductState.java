import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.StringTokenizer;


public class ProductState {
    private static ProductState productState;
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static Warehouse warehouse;
    private static final int LOGOUT = 0;
    private static final int ADD_PRODUCT = 1;
    private static final int ADD_SUPPLIER = 2;
    private static final int SHOW_SUPPLIER_LIST = 3;
    private static final int SUPPLIERS_FOR_PRODUCT = 4;
    private static final int PRODUCTS_FOR_SUPPLIERS = 5;
    private static final int UPDATE_PRODUCTS = 6;
    private static final int BECOME_SALESCLERK = 7;
    private static final int HELP = 8;

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

    public static ProductState instance() {
        if (productState == null) {
            return productState = new ProductState();
        } else {
            return productState;
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
                int value = Integer.parseInt(getToken("Enter command:" + HELP + " for help"));
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

    }

    public void addProduct() {
        Product result;
        do {
            String name = getToken("Enter Product Name: ");
            String Quantity = getToken("Enter quantity: ");
            String Price = getToken("Enter the price: ");
            int quantity = Integer.parseInt(Quantity);
            int price = Integer.parseInt(Price);
            result = warehouse.addProduct(price, quantity, name);
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
            String description = getToken("Enter Description: ");
            result = warehouse.addSupplier(name, address, description);
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
        Product product = warehouse.searchProduct(pID);
        if (product != null)
        {
            Iterator<Supplier> s_traversal = warehouse.getSuppliersOfProduct(product);
            Iterator<Float> price_traversal = warehouse.getProductPrices(product);
            while (((s_traversal.hasNext())) && ((price_traversal.hasNext())))
            {
                supplier = s_traversal.next();
                price = price_traversal.next();
                System.out.println("Supplier: " + supplier.getSupplierName() + ". Supply Price: $" + price);
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
        Supplier supplier = warehouse.searchSupplier(s);
        if (supplier != null)
        {
            Product p_temp;
            Iterator<Product> p_traversal = warehouse.getProductBySupplier(supplier);
            while (p_traversal.hasNext() != false)
            {
                p_temp = p_traversal.next();
                System.out.println(p_temp.getSupplier());
            }
        }
        else
        {
            System.out.println("Supplier doesn't exist");
        }
    }

    public void updateProducts(){


    }

    public void becomeSalesclerk(){

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

    public static void main(String[] args) {
        ProductState.instance().process();
    }

}
