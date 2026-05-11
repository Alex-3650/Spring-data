package Exercise_08;

import DB_Connection.DBConnection;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        Connection connection = DBConnection.getConnection("root", "Your pass");

        CallableStatement callableStatement = connection.prepareCall("CALL usp_get_older(?);");
        callableStatement.setInt(1,1);
        callableStatement.execute();

    }
}
