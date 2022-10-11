import java.sql.SQLException;
import java.util.Scanner;

import static java.lang.System.out;

public class Order {
    public static void readMenu(int cid) throws SQLException {
        DbHandler.db_read_menu();
        out.println("\n Please take your time and place your order.");
        takeOrder(cid);
  }

  public static void takeOrder(int cid) throws SQLException {
        String orderString; char choice; float amt;
        out.println("\n Please Enter the items numbers (comma separated).");
        Scanner scanner = new Scanner(System.in);
        orderString = scanner.nextLine();
        out.println("Making sure we got it right...\n"+
                    "You've placed an order for the following items:-");
        out.println(orderString);
        amt = billCalculator(orderString);
        out.println("Your bill will be: " + amt);
        out.println("Confirm order (y/n) ?");
        // Insert record of order in orders database.active orders.
        choice = scanner.next().charAt(0);
        if(choice == 'y')
        {
            DbHandler.place_order(cid,orderString,amt);
            out.println("Your Order has been placed!");
            Main.menu();

        }
        else {
            out.println("order cancelled");
            takeOrder(cid);
        }
  }

  public static void displayOrder(int cid) throws SQLException {

      // Print order for cid
      DbHandler.read_active_order(cid);

  }

  public static float billCalculator(String orderString) throws SQLException {
     return DbHandler.bill_amt(orderString);
  }

  public static void print_receipt(int cid) throws SQLException {
      DbHandler.read_active_order(cid);
  }

}
