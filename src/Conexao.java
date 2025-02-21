import java.sql.*;
public class Conexao {
    static Connection connection = null;
    String url = "jdbc:mysql://localhost:3306/Projeto";
    String user = "root";
    String password = "password";

    public Conexao() {
        try {
            connection = DriverManager.getConnection(url, user, password);
            //System.out.println("\nConexao realizada com sucesso");
        } 
        catch (SQLException e) {
            System.out.println("\nProblema na conexao: " + e.getMessage());
        }
    }

    public static Connection getConexao() {
        return connection;
    }
}