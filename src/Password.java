import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Clément on 23/03/2017.
 */

/**
 * This class is used to create, update and manage password. Each password of the database equal a new object Password.
 * @author Cuvillier Clement
 * @version 1.0
 */
public class Password {
    private int id;
    private String pass;
    private String name;
    private String note;
    private int idUser;
    private Connexion connexion;

    /**
     * Constructor of a new password, it can be use to create a password and add it in the database or to be associated to a password in the database.
     * @param _pass String which contain the password.
     * @param _name Name of the password to recognize it easily.
     * @param _note A note about the time to live of the password or some information like an associate login.
     * @param _idUser Owner of the password.
     * @param _connexion Instance of the database.
     */
    public Password(String _pass,String _name, String _note, int _idUser, Connexion _connexion){
        pass = _pass;
        note = _note;
        name = _name;
        idUser = _idUser;
        connexion = _connexion;
    }

    /**
     *
     * @return the id of a password.
     */
    public int getId(){
        return id;
    }

    /**
     *
     * @return the string which contain the password.
     */
    public String getPass() {
        return pass;
    }

    /**
     *
     * @return the note about a password.
     */
    public String getNote() {
        return note;
    }

    /**
     *
     * @return the name of a password.
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return the owner's id of a password.
     */
    public int getIdUser() {
        return idUser;
    }

    /**
     * Used to assign a password to the object.
     * @param pass The new password
     */
    public void setPass(String pass) {
        this.pass = pass;
    }

    /**
     * Used to assign a note to the object.
     * @param note The new note.
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * Used to assign a name to the object.
     * @param name The new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Used to assign the object to an user.
     * @param idUser The user id.
     */
    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    /**
     * Add a password to the database. This function use the value contains in the attribute.
     */
    public void addPassword(User user){
        String encryptpass = new String();
        try{
            encryptpass = Encryption.encrypt(user.getPassword(), this.getPass());
        }catch(Exception e){
            e.printStackTrace();
        }
        String query= "INSERT INTO Password(pass, note, name, idUser) VALUES(";
        query += "'" + encryptpass +"',";
        query += "'" + this.getNote() +"',";
        query += "'" + this.getName() +"',";
        query += "'" + this.getIdUser() + "');";
        this.connexion.update(query);

    }


}

