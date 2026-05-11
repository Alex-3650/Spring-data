package Exercise_04;

import DB_Connection.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) throws SQLException {
         Scanner scanner = new Scanner(System.in);
         Connection connection = DBConnection.getConnection("root", "Your pass");

         String country=scanner.nextLine();

        int updateCount = updateTownNames(connection, country);

        if(updateCount==0){
            System.out.println("No town names were affected.");
            return;
        }
        List<String> townNames=getAllTownsByCountry(connection,country);
        System.out.printf("%d town names were affected.\n",updateCount);
        System.out.println(townNames);
    }

    private static List<String> getAllTownsByCountry(Connection connection, String country) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(Queries.SELECT_TOWN_FROM_COUNTRY_NAME);
        preparedStatement.setString(1,country);
        ResultSet resultSet = preparedStatement.executeQuery();

        List<String>names=new ArrayList<>();
        while (resultSet.next()){

        names.add(resultSet.getString("name"));
        }
        return names;
    }

    private static int updateTownNames(Connection connection, String country) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("""
         UPDATE towns
         SET name = UPPER(name)
         WHERE country = ?; \s""");

        preparedStatement.setString(1,country);

        return preparedStatement.executeUpdate();
    }
}
