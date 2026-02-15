package Exercise_07;

import DB_Connection.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

 record Minion(int id, String name, int age){}
public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        Connection connection = DBConnection.getConnection("root", "Your pass");

        String[] input=scanner.nextLine().split("\\s+");
        List<Integer> minionsId = Arrays.stream(input).map(Integer::parseInt).toList();

        increaseAgeForMinionID(connection,minionsId);
        List<Minion> minionList = getAllMinions(connection);
        printMinions(minionList);

    }

    private static void printMinions(List<Minion> minionList) {

        for (Minion minion : minionList) {
            System.out.printf("%s %d\n",minion.name(),minion.age());
        }
    }

    private static List<Minion> getAllMinions(Connection connection) throws SQLException {
        List<Minion> minions=new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT id,name,age FROM minions");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            minions.add(new Minion(resultSet.getInt("id"),
                                    resultSet.getString("name"),
                                    resultSet.getInt("age")));
        }
        return  minions;

    }


    private static void increaseAgeForMinionID(Connection connection, List<Integer> minionsId) throws SQLException {

        String paramTemplate=minionsId.stream().map(id -> "?").collect(Collectors.joining(","));
        PreparedStatement preparedStatement = connection.prepareStatement(" UPDATE minions\n" +
                " SET age=age+1,\n" +
                " name=LOWER(name)\n" +
                " WHERE id IN (" + paramTemplate + ")");

        for (int i = 0; i < minionsId.size(); i++) {
            preparedStatement.setInt(i+1,minionsId.get(i));

        }

        preparedStatement.executeUpdate();
    }

}
