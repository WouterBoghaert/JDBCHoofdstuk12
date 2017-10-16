package be.vdab;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main2 {
    private static final String URL = "jdbc:mysql://localhost/tuincentrum?useSSL=false";
    private static final String USER = "cursist";
    private static final String PASSWORD = "cursist";
    private static final String SELECT_RODE_PLANTEN = 
        "select naam, leverancierid from planten where kleur = 'rood'";
    private static final String SELECT_LEVERANCIER =
        "select naam from leveranciers where id = ?";
    public static void main(String[] args) {
        try(Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            Statement statementPlanten = connection.createStatement();
            PreparedStatement statementLeverancier =
                connection.prepareStatement(SELECT_LEVERANCIER)){
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            connection.setAutoCommit(false);
            try (ResultSet resultSetPlanten = 
                statementPlanten.executeQuery(SELECT_RODE_PLANTEN)){
                while(resultSetPlanten.next()){
                    System.out.print(resultSetPlanten.getString("naam"));
                    System.out.print(" ");
                    statementLeverancier.setLong(1, 
                        resultSetPlanten.getLong("leverancierid"));
                    try(ResultSet resultSetLeverancier =
                        statementLeverancier.executeQuery()){
                        System.out.println(resultSetLeverancier.next() ?
                            resultSetLeverancier.getString("naam") : 
                            "leverancier niet gevonden");
                    }
                }
            }
            connection.commit();
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    
}
