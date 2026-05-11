package Exercise_05;

import DB_Connection.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
         Scanner scanner = new Scanner(System.in);
        Connection connection = DBConnection.getConnection("root", "Your pass");

        int villainId=Integer.parseInt(scanner.nextLine());

        String villainName = getVillainNameById(connection, villainId);

        if(villainName==null){
            System.out.println("No such villain was found");
            return;
        }
        connection.setAutoCommit(false);
        try {
            int minionsCount= releaseMinionsFromVillainId(connection, villainId);
            deleteVillainById(connection, villainId);


            connection.commit();

            System.out.printf("%s was deleted\n",villainName);
            System.out.printf("%d minions released\n",minionsCount);
        }catch (SQLException e){
            System.out.println("There was a problem");

            connection.rollback();
        }
    }

    private static int releaseMinionsFromVillainId(Connection connection, int villainId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM minions_villains WHERE villain_id = ?");
        preparedStatement.setInt(1,villainId);
        return preparedStatement.executeUpdate();
    }

    private static void deleteVillainById(Connection connection, int villainId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM villains WHERE id = ?;");
        preparedStatement.setInt(1,villainId);
        preparedStatement.executeUpdate();
    }


    private static String getVillainNameById(Connection connection, int villainId) throws SQLException {

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT name FROM villains WHERE id = ?");
        preparedStatement.setInt(1,villainId);
        ResultSet resultSet = preparedStatement.executeQuery();

        boolean hasVillain = resultSet.next();
        if(!hasVillain){
          return null;
        }
        return resultSet.getString("name");

    }
}
