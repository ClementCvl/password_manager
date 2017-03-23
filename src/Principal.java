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
        User user = new User("iClemich", "azerty123", connexion);
        user.addUser();
        Password pass1 = new Password("testAdPass1234556789--)çé", "facebook", "Premier mot de passe entré", user.getId(), connexion);
        pass1.addPassword();
        user.selectAllPasswordByUser();

        connexion.close();
    }
}