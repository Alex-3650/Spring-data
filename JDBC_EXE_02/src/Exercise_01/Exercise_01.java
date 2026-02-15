package Exercise_01;

import DB_Connection.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Exercise_01 {
    public static void main(String[] args) throws SQLException {
        Connection connection = DBConnection.getConnection("root", "Your pass");
        ResultSet resultSet;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("""
                    SELECT v.name,COUNT(v.id) AS minion_count
                    FROM villains v
                    JOIN minions_villains mv
                    ON mv.villain_id=v.id
                    GROUP BY v.name
                    HAVING COUNT(v.id)>15
                    ORDER BY COUNT(v.id) DESC;
                    """);
            resultSet=preparedStatement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        while (resultSet.next() ){

            System.out.printf("%s %d\n",
                                        resultSet.getString("name"),
                                        resultSet.getInt("minion_count"));

        }

    }
}
