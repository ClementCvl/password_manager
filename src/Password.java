import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Clément on 23/03/2017.
 */
public class Password {
    private int id;
    private String pass;
    private String name;
    private String note;
    private int idUser;
    private Connexion connexion;

    public Password(String _pass,String _name, String _note, int _idUser, Connexion _connexion){
        pass = _pass;
        note = _note;
        name = _name;
        idUser = _idUser;
        connexion = _connexion;
    }

    public int getId(){
        return id;
    }

    public String getPass() {
        return pass;
    }

    public String getNote() {
        return note;
    }

    public String getName() {
        return name;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public void addPassword(){
        String query= "INSERT INTO Password(pass, note, name, idUser) VALUES(";
        query += "'" + this.getPass() +"',";
        query += "'" + this.getNote() +"',";
        query += "'" + this.getName() +"',";
        query += "'" + this.getIdUser() + "');";
        this.connexion.update(query);

    }


}

