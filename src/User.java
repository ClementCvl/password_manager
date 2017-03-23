/*
 * Copyright (c) 2017. Cuvillier Clément
 */

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Clément on 23/03/2017.
 */
public class User {
    private int id;
    private String login;
    private String password;
    private Connexion connexion;

    public User(String _login, String _password, Connexion _connexion){
        login = _login;
        password = _password;
        connexion = _connexion;
    }

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setId(int _id) {
        id = _id;
    };

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }


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
    public void addUser(){
        String query= "INSERT INTO User(login, password) VALUES(";
        query += "'" + this.getLogin() +"',";
        query += "'" + this.getPassword() +"');";
        this.connexion.update(query);
        this.setId(selectLastId());
    }


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

    public void selectAllPasswordByUser(){
        ResultSet resultSet = this.connexion.query("SELECT * FROM Password WHERE idUser="+this.getId()+";");
        try {
            while (resultSet.next()) {
                System.out.println("id : "+resultSet.getInt("id"));
                System.out.println("name : "+resultSet.getString("name"));
                System.out.println("mot de passe : "+resultSet.getString("pass"));
                System.out.println("note : "+resultSet.getString("note"));
                System.out.println("id utilisateur : "+resultSet.getString("idUser"));
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}
