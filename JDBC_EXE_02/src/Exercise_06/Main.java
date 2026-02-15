package Exercise_06;

import DB_Connection.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        Connection connection = DBConnection.getConnection("root", "Your pass");

       List<String> names= getAllMinionsName(connection);
       printNames(names);
    }

    private static void printNames(List<String> names) {

        for (int i = 0; i < (names.size() + 1) / 2; i++) {
            System.out.println(names.get(i));
            if (i != names.size() - 1 - i) {  // Avoid printing middle element twice
                System.out.println(names.get(names.size() - 1 - i));
            }
        }
    }

    private static List<String> getAllMinionsName(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT name FROM minions");
        ResultSet resultSet = preparedStatement.executeQuery();
        List<String> names= new ArrayList<>();

        while (resultSet.next()){
            names.add(resultSet.getString("name"));
        }
        return names;
    }
}
