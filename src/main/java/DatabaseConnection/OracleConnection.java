/**
 * Created by Tomasz Motyka on 2017-01-20.
 */
package DatabaseConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class OracleConnection {

    public Connection GetConnection() {
        Connection connection = null;

        try
        {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        }
        catch(ClassNotFoundException e)
        {
            e.printStackTrace();
            return null;
        }

        try
        {
            connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "Motykson", "siekiera15");

        }
        catch(SQLException e)
        {
            System.out.println("Connection failed");
            e.printStackTrace();
            return null;
        }

        return connection;
    }
}
