import java.sql.*;
import java.util.Properties;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws SQLException {
       String user="root";
       String password="alex2007@1234";

        Properties properties=new Properties();
        properties.setProperty("user",user);
        properties.setProperty("password",password);

        Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/soft_uni", properties);

   //     connection.setAutoCommit(false);
        PreparedStatement query = connection.prepareStatement("SELECT * FROM employees WHERE salary> ? LIMIT 10");




        query.setDouble(1,70000);
        ResultSet resultSet = query.executeQuery();

        while (resultSet.next()){

            System.out.printf("User #%d - %s %s - %.2f\n",
                                                 resultSet.getInt("employee_id"),
                                                 resultSet.getString("first_name"),
                                                 resultSet.getString("last_name"),
                                                 resultSet.getDouble("salary"));
        }
    }
}