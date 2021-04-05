public class Security {

    public boolean verifyPassword(String userID, String password, int state) {
        boolean verified = verifyPasswordHidden(userID, password, state);
        return verified;
    }

    private boolean verifyPasswordHidden(String userID, String password, int state) {
        if (state == WarehouseContext.MANAGER_STATE) {
            if (userID.equals("manager") && password.equals("manager")) {
                System.out.println("Return true.");
                return true;
            }
        }//End of if statement
        else if (state == WarehouseContext.CLERK_STATE) {
            if (userID.equals("clerk") && password.equals("clerk")) {
                return true;
            }
        }//End of else if statement

        return false;
    }//End of verifyPassword

}