package DB_Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

    public static Connection getConnection(String user, String password) {
        Properties properties=new Properties();
        properties.setProperty("user",user);
        properties.setProperty("password",password);

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/minions_db", properties);
            return connection;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

    }
}
