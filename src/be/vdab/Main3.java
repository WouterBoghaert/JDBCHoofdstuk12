package be.vdab;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main3 {
    private static final String URL = "jdbc:mysql://localhost/tuincentrum?useSSL=false";
    private static final String USER = "cursist";
    private static final String PASSWORD = "cursist";
    private static final String SELECT_RODE_PLANTEN =
        "select p.naam as plantnaam, l.naam as leveranciersnaam "
        + "from planten p inner join leveranciers l on p.leverancierid = l.id "
        + "where kleur = 'rood'";
    public static void main(String[] args) {
        try(Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            Statement statement = connection.createStatement()){
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            connection.setAutoCommit(false);
            try(ResultSet resultSet = statement.executeQuery(SELECT_RODE_PLANTEN)){
                while(resultSet.next()){
                    System.out.println(resultSet.getString("plantnaam") + " " + 
                     resultSet.getString("leveranciersnaam"));
                }
            }
            connection.commit();
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    
}
