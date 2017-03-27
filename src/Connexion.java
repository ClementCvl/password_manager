/**
 * Created by Clément on 20/03/2017.
 */

import javax.swing.plaf.nimbus.State;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class Connexion {
    private String DBPath;
    private Connection connection = null;
    private Statement statement = null;

    /**
     *
     * @param dBPath = chemin vers la database
     */
    public Connexion(String dBPath) {
        DBPath = dBPath;
    }

    public void createIfNotExist() {
        String query1 = "CREATE TABLE IF NOT EXISTS User (id integer PRIMARY KEY, login text NOT NULL, password text NOT NULL);";
        String query2 = "CREATE TABLE IF NOT EXISTS Password ( id integer PRIMARY KEY, pass text NOT NULL, name text NOT NULL, note text, idUser integer, FOREIGN KEY (idUser) REFERENCES User(id));";
        SHA512 hashpass = new SHA512("azerty123");
        String query3 = "INSERT INTO User(login, password) VALUES('iClemich', '"+hashpass.getPass()+"');";
        String query4 = "INSERT INTO Password(pass, name, note, idUser) VALUES('testAdPass1234556789--)çé', 'facebook', 'Premier mdp entré', 1)";
        String query5 = "SELECT COUNT(id) AS nbid FROM User";

        try{
            statement.executeUpdate(query1);
            statement.executeUpdate(query2);
            ResultSet resultSet = this.query(query5);
            if(resultSet.getInt("nbid") == 0){
                statement.executeUpdate(query3);
                statement.executeUpdate(query4);
            }



        } catch (SQLException e){
            e.printStackTrace();
        }
    }


    /**
     * Connexion a la database du DBPath
     */
    public void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + DBPath);
            statement = connection.createStatement();
            System.out.println("Connexion a " + DBPath + " avec succès");
            createIfNotExist();
        } catch (ClassNotFoundException notFoundException) {
            notFoundException.printStackTrace();
            System.out.println("Erreur de connexion");
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            System.out.println("Erreur de connexion");
        }
    }

    /**
     * Fermeture de la connexion
     */
    public void close() {
        try {
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Execute une requete sql de recherche
     * @param requet
     */
    public ResultSet query(String query) {
        ResultSet resultat = null;
        try {
            resultat = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur dans la requête : " + query);
        }
        return resultat;
    }

    /**
     * Execute un update
     *
     */
    public void update(String query){
        try{
            statement.executeUpdate(query);
        }catch(SQLException sqlException) {
            sqlException.printStackTrace();
            System.out.println("Erreur dans la requête : " + query);
        }
    }

    /**
     *
     * @param query
     * @return l'id de l'update
     */
    public int updateRetId(String query){
        int id=0;
        try{
            id = statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
        }catch(SQLException sqlException) {
            sqlException.printStackTrace();
            System.out.println("Erreur dans la requête : " + query);
        }
        return id;
    }

    public User connectUser(String UserLogin, String UserPassword){
        SHA512 passhash = new SHA512(UserPassword);
        new String("").equals(passhash.getPass());
        String query="SELECT id FROM User WHERE login=" +"'"+ UserLogin +"'"+" AND password=" +"'"+ passhash.getPass() +"';";
        ResultSet resultSet = this.query(query);
        try {
            if (resultSet.next()) {
                User user = new User(UserLogin, passhash.getPass(), this);
                user.setId(resultSet.getInt("id"));
                return user;

            } else {

                System.out.println("Mauvais mot de passe ou pseudo");
                return null;
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return null;
    }
}