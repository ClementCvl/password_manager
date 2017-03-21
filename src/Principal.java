/**
 * Created by Clément on 20/03/2017.
 */
import java.sql.ResultSet;
import java.sql.SQLException;

public class Principal
{
    public static void main(String[] args) {
        Connexion connexion = new Connexion("database/Database.db");
        connexion.connect();
        //connexion.createIfNotExist();
        ResultSet resultSet = connexion.query("SELECT * FROM User");
        try {
            while (resultSet.next()) {
                System.out.println("id : "+resultSet.getString("id") + " login: " +resultSet.getString("login") + " password: " +resultSet.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

       // connexion.close();
    }
}