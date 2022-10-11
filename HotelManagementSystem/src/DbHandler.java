import java.sql.*;
import static java.lang.System.out;

public class DbHandler {

    static Connection connection_menu = null;
    static Connection connection_order = null;
    static String jdbcURL_menu = "jdbc:sqlite:C:\\HotelData\\menu.sqlite";
    static String jdbcURL_order = "jdbc:sqlite:C:\\HotelData\\orders.sqlite";

    public static void connect()
    {
        try {
            connection_menu = DriverManager.getConnection(jdbcURL_menu);
            connection_order = DriverManager.getConnection(jdbcURL_order);
        } catch (SQLException e) {
            out.println("Error connecting");
            e.printStackTrace();
        }
    }
    public static void db_read_menu() throws SQLException {

            String query = "SELECT * FROM menu";
            Statement s = connection_menu.createStatement();
            ResultSet set = s.executeQuery(query);
            out.println("Item No" + "|\t" + "Item" + "|\t" + "Price");
            while(set.next())
            {
                out.println(set.getString("sno") + " |\t "
                        + set.getString("item") + " |\t "
                        + set.getString("price"));
            }
    }

    public static void read_active_order(int cid) throws SQLException {
        String query = String.format("SELECT * FROM activeorders WHERE cid = %d;",cid);
        Statement s = connection_order.createStatement();
        ResultSet set = s.executeQuery(query);
        out.println("cid" +  "|\t" + "Bill");
        out.println(set.getString("cid") + " |\t " + set.getString("amount"));

        String query2 = "SELECT sno, item FROM menu WHERE sno in ("+set.getString("placedorder")+");";
        Statement s2 = connection_menu.createStatement();
        ResultSet set2 = s2.executeQuery(query2);
        out.println("sno"+"|\t"+"item");
        while(set2.next())
        {
            out.println(String.format("%d |\t %s",set2.getInt("sno"),set2.getString("item")));
        }
    }



    public static float bill_amt(String orderString) throws SQLException {
        String query = "SELECT sum(price) as bill from menu where sno in ("+orderString+");";
        Statement s = connection_menu.createStatement();
        ResultSet set = s.executeQuery(query);
        return set.getFloat("bill");
    }

    public static void place_order(int cid,String orderString,float amt) throws SQLException
    {
        String insertString = "INSERT INTO activeorders VALUES(?,?,?)";
        PreparedStatement psmt = connection_order.prepareStatement(insertString);
        psmt.setInt(1,cid);
        psmt.setString(2, orderString);
        psmt.setFloat(3,amt);
        psmt.executeUpdate();
    }

    public static void read_active_order() throws SQLException
    {
        String query = "SELECT * FROM activeorders;";
        Statement s = connection_order.createStatement();
        ResultSet set = s.executeQuery(query);
        out.println("cid"+"\t|"+"placedorder" +"\t|" +"amount");
        while(set.next())
        {
            out.println(String.format("%d \t| %s \t| %f",set.getInt("cid"),set.getString("placedorder"),set.getFloat("amount")));
        }
    }
}
