import java.io.Serializable;
import java.util.Date;

public class Transaction implements Serializable {

    private Date date;
    private String description;
    private int amount;

    public void Transaction(Date date, String description, int amount) {
        this.date = date;
        this.description = description;
        this.amount = amount;
    }

    public Date getDate(){
        return date;
    }

    public String getDescription(){
        return description;
    }

    public int getAmount(){
        return amount;
    }

    public String toString(){
        String string = " Date: " + date + "Description: "+ description + "Amount: "+ amount + "  ";
        return string;
    }
}

