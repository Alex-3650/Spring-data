package Exercise_03;

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

        String[] minionData=scanner.nextLine().split(" ");
        String minionName=minionData[1];
        int minionAge=Integer.parseInt(minionData[2]);
        String minionTown=minionData[3];

        String[] villainData=scanner.nextLine().split(" ");
        String villainName=villainData[1];

        Integer townId = getMinionTownId(connection, minionTown);
        if(townId==null){
            insertTown(connection,minionTown);
            System.out.printf("Town %s was added to the database.\n",minionTown);
        }

        Integer villainId = getVillainByName(connection, villainName);
        if(villainId==null){
            insertVillainByName(connection,villainName);
            System.out.printf("Villain %s was added to the database.\n",villainName);
        }
         townId=getMinionTownId(connection, minionTown);
        addMinion(connection,minionName,minionAge,townId);
        Integer minionId = getMinionId(connection, minionName);
        insertVillainToMinionById(connection,minionId,villainId);
        System.out.printf("Successfully added %s to be minion of %s.",minionName,villainName);


    }

    private static void insertVillainToMinionById(Connection connection, int minionId, Integer villainId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO minions_villains (minion_id,villain_id) VALUES (?,?)");
        preparedStatement.setInt(1,minionId);
        preparedStatement.setInt(2,villainId);
        preparedStatement.executeUpdate();
    }

    private static Integer getMinionId(Connection connection, String minionName) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT id FROM minions WHERE name = ?");
        preparedStatement.setString(1,minionName);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(!resultSet.next()) return null;
        return resultSet.getInt("id");
    }

    private static void addMinion(Connection connection, String minionName, int minionAge, Integer townId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO minions (name,age,town_id) VALUES (?,?,?)");
        preparedStatement.setString(1,minionName);
        preparedStatement.setInt(2,minionAge);
        preparedStatement.setInt(3,townId);
        preparedStatement.executeUpdate();
    }

    private static void insertVillainByName(Connection connection, String villainName) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO villains (name,evilness_factor) VALUES(?,\"evil\")");
        preparedStatement.setString(1,villainName);
        preparedStatement.executeUpdate();


    }

    private static Integer getVillainByName(Connection connection, String villainName) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT id FROM villains WHERE name = ?");
        preparedStatement.setString(1,villainName);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(!resultSet.next()) return null;
        return resultSet.getInt("id");

    }

    private static void insertTown(Connection connection, String minionTown) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO towns (name) VALUES (?)");
        preparedStatement.setString(1,minionTown);
        preparedStatement.executeUpdate();
    }

    private static Integer getMinionTownId(Connection connection, String minionTown) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT id  FROM towns WHERE name = ?;");
        preparedStatement.setString(1,minionTown);
        ResultSet resultSet = preparedStatement.executeQuery();

        if(!resultSet.next()) return null;
        return resultSet.getInt("id");

    }
}
