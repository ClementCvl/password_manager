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
        ResultSet resultSet = connexion.query("SELECT * FROM Book WHERE Title LIKE 'Programmation JAVA' ");
        try {
            while (resultSet.next()) {
                System.out.println("Titre : "+resultSet.getString("Title"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        connexion.close();
    }
}