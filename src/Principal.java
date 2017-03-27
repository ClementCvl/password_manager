/**
 * Created by Clément on 20/03/2017.
 */
import java.sql.ResultSet;
import java.sql.SQLException;

public class Principal
{
    static User actualUser;
    public static void main(String[] args) {
        Connexion connexion = new Connexion("database/Database.db");
        connexion.connect();

        actualUser = connexion.connectUser("iClemich","azerty123");
        if(actualUser != null)
            actualUser.selectAllPasswordByUser();
        connexion.close();
    }
}