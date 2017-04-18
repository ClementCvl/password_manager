/*
 * Copyright (c) 2017. Cuvillier Clément
 */

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Clément on 23/03/2017.
 */

/**
 * This class is used to create, update and manage an user.
 * @author Cuvillier Clement
 * @version 1.0
 */
public class User {
    private int id;
    private String login;
    private String password;
    private Connexion connexion;

    /**
     * Constructor of an user.
     * @param _login The login.
     * @param _password For an user creation the password isn't hashed. If you create an user from the database, this password is hashed.
     * @param _connexion Instance of the database.
     */
    public User(String _login, String _password, Connexion _connexion){
        login = _login;
        password = _password;
        connexion = _connexion;
    }

    /**
     *
     * @return the id of an user.
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @return the login of an user.
     */
    public String getLogin() {
        return login;
    }

    /**
     *
     * @return The hashed password of an user.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Used to assign an id to the user. This id shouldn't be assigned manually, this function is only use in the function addUser to assign the generated id to the attribute.
     * @param _id
     */
    public void setId(int _id) {
        id = _id;
    };

    /**
     *
     * @param login
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }


    /**
     * This function is used in the addUser function to retrieve the id after the insertion.
     * @return the id of the couple login-password
     */
    public int selectLastId(){
        int id = 0;
        String query="SELECT id FROM User ORDER BY id DESC LIMIT 1";
        ResultSet resultSet = this.connexion.query(query);
        try{
            id = resultSet.getInt("id");
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return id;
    }

    /**
     * Add an user to the database. This function use the value contains in the attribute.
     */
    public void addUser(){
        SHA512 passhash = new SHA512(this.getPassword());
        String query = "INSERT INTO User(login, password) VALUES(";
        query += "'" + this.getLogin() +"',";
        query += "'" + passhash.getPass() +"');";
        this.connexion.update(query);
        this.setId(selectLastId());
    }


    /**
     * Retrieve user information with user ID.
     */
    public void selectUserInfo(){
        ResultSet resultSet = connexion.query("SELECT * FROM User WHERE id="+this.getId());
        try {
            while (resultSet.next()) {
                System.out.println("id : "+resultSet.getInt("id"));
                System.out.println("identifiant : "+resultSet.getString("login"));
                System.out.println("mot de passe : "+resultSet.getString("password"));
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    /**
     * Retrieve passwords created by the user.
     */
    public vector<Password> selectAllPasswordByUser(){
        ResultSet resultSet = this.connexion.query("SELECT * FROM Password WHERE idUser="+this.getId()+";");
        vector<Password> listPass = new vector();
        try {
            while (resultSet.next()) {
                System.out.println("id : "+resultSet.getInt("id"));
                System.out.println("name : "+resultSet.getString("name"));
                try{
                    System.out.println("mot de passe : "+Encryption.decrypt(this.getPassword(), resultSet.getString("pass")));
                    Password pass = new Password(Encryption.decrypt(this.getPassword(), resultSet.getString("pass")), resultSet.getString("name"), resultSet.getString("note"), resultSet.getString("idUser"), this.connexion);
                    vector.pushback(pass);
                }catch(Exception e){
                    e.printStackTrace();
                }
                System.out.println("note : "+resultSet.getString("note"));
                System.out.println("id utilisateur : "+resultSet.getString("idUser"));
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}
