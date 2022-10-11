import java.sql.SQLException;
import java.util.Scanner;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static java.lang.System.out;
public class ChefConsole {



    public static void main(String[] args) throws SQLException {
        DbHandler.connect();
        out.println("Successfully connected to database");
        console_menu();

    }

    public static void view_active_orders() throws SQLException {

        /*ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        executor.scheduleAtFixedRate(() -> {
            try {

                DbHandler.read_active_order();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }, 2, 5, TimeUnit.SECONDS);*/
        DbHandler.read_active_order();

    }

    public static void console_menu() throws SQLException {
        System.out.print("\033[H\033[2J"); //clear screen
        System.out.flush();
        Scanner scanner = new Scanner(System.in);
        char choice;
        int cid_order_done;
        out.println("Press 'a' to view active orders\nPress 'c' to enter completed order\nPress 'e' to return to main menu\n");
        choice = scanner.next().charAt(0);

        if(choice == 'a')
        {
            view_active_orders();
        }
        else if(choice == 'c')
        {
            Scanner scanner2 = new Scanner(System.in);
            out.println("Enter cid of completed order");
            cid_order_done = scanner2.nextInt();
            out.printf("Remove cid = %d\n", cid_order_done);

            DbHandler.remove_completed(cid_order_done);
            console_menu();
        }
    }
}
