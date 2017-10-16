package be.vdab;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {
    private static final String URL = "jdbc:mysql://localhost/tuincentrum?useSSL=false";
    private static final String USER = "cursist";
    private static final String PASSWORD = "cursist";
//    private static final String SELECT_ALLE_LEVERANCIERS = 
//            "select id, naam, woonplaats from leveranciers";
    private static final String SELECT_LEVERANCIERS_UIT_WEVELGEM =
            "select id, naam, woonplaats from leveranciers where woonplaats = 'Wevelgem'";
    public static void main(String[] args) {
        try(Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            Statement statement = connection.createStatement()){
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            try(ResultSet resultSet = statement.executeQuery(SELECT_LEVERANCIERS_UIT_WEVELGEM)){
                int aantalLeveranciers = 0;
                while(resultSet.next()){
                    //if("Wevelgem".equals(resultSet.getString("woonplaats"))){
                        ++aantalLeveranciers;
                        System.out.println(resultSet.getInt("id") + " " +
                            resultSet.getString("naam"));
                    //}
                }
                System.out.println(aantalLeveranciers + " leverancier(s)");
            }
            connection.commit();
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    
}
