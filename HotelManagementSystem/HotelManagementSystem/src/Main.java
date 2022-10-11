import static java.lang.System.out;
import java.sql.SQLException;
import java.util.Scanner;
public class Main {

    public static void main(String[] g) throws SQLException {
        menu();
    }

    public static void menu() throws  SQLException
    {
        out.println("Hotel Management System version 1.0");
        out.println("To place order press 'o' \nTo enter management console press 'a'");
        char order_or_admin;
        int cid;
        Scanner scanner = new Scanner(System.in);
        order_or_admin = scanner.next().charAt(0);

        if(order_or_admin == 'o')
        {
            // Display Menu and Take order
            DbHandler.connect();
            out.println("Enter your table number");
            cid = scanner.nextInt();
            Order.readMenu(cid);
        }
        else
        {

            //Launch Management Console
        }
    }
}
