package Exercise_02;

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

        PreparedStatement ps = connection.prepareStatement(" SELECT name FROM  villains v WHERE v.id = ? ;");

        int villainID=Integer.parseInt(scanner.nextLine());
        ps.setInt(1,villainID);

        ResultSet resultSet = ps.executeQuery();
        boolean hasRowInResult=resultSet.next();


       if(!hasRowInResult){
           System.out.printf("No villain with ID %d exists in the database.\n",villainID);
           return;
       }

        System.out.printf("Villain: %s\n",resultSet.getString("name"));


        PreparedStatement ps2 = connection.prepareStatement("""
                SELECT name,age
                FROM minions m
                JOIN minions_villains mv
                ON m.id=mv.minion_id
                WHERE mv.villain_id=?; \s""");
        ps2.setInt(1,villainID);

        ResultSet resultSet2 = ps2.executeQuery();

        int index=1;
        while (resultSet2.next()){

            System.out.printf("%d. %s %s\n",index,
                                            resultSet2.getString("name"),
                                            resultSet2.getInt("age"));
            index++;
        }

    }
}
