import java.io.Serializable;
import java.util.Date;

public class Transaction implements Serializable {

    private static final long serialVersionUID = 1L;
    private Date date;
    private String description;
    private double balance;

    public Transaction(double balance2) {
    }

    public void Transaction(Date date, String description, double balance) {
        this.date = date;
        this.description = description;
        this.balance = balance;
    }

    public Date getDate(){
        return date;
    }

    public String getDescription(){
        return description;
    }

    public double getBalance(){
        return balance;
    }

    @Override
    public String toString(){
        return (" Date: " + date + "Description: "+ description + "Balance: "+ balance + "  ");
    }
}