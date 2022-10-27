package com.example.myhealth.model;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Base64;
import java.util.prefs.Preferences;
import java.security.MessageDigest;

// to store the user details in the database when a new user signs up for the application
public class SignUpModel {
    public static void signUpUser(ActionEvent event, String username, String password, String firstname, String lastname){
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckUserExists = null;
        ResultSet resultSet = null;

        try{

            connection = DriverManager.getConnection("jdbc:sqlite:health.db", "", "");
            psCheckUserExists = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
            psCheckUserExists.setString(1, username);
            resultSet = psCheckUserExists.executeQuery();

            if(resultSet.isBeforeFirst()){
                // to make sure a unique username is assigned to each new user
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Username already taken");
                alert.show();
            } else {
                psInsert = connection.prepareStatement("INSERT INTO users VALUES(?, ?, ?, ?)");
                psInsert.setString(1, username);

                // to store the password as SHA256 hash string in the database
                try {
                    MessageDigest digest = MessageDigest.getInstance("SHA-256");
                    byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
                    String encoded = Base64.getEncoder().encodeToString(hash);
                    psInsert.setString(2, encoded);
                } catch (NoSuchAlgorithmException e){
                    e.printStackTrace();
                }

                psInsert.setString(3, firstname);
                psInsert.setString(4, lastname);
                psInsert.executeUpdate();

                Preferences userPreferences = Preferences.userRoot();
                userPreferences.put("username", username);
                userPreferences.put("firstname", firstname);
                userPreferences.put("lastname", lastname);
                DBUtils.changeScene(event, "logged-in.fxml", firstname + " " + lastname, username);
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            if(resultSet != null){
                try {
                    resultSet.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }

            if(psCheckUserExists != null){
                try {
                    psCheckUserExists.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }

            if(psInsert != null){
                try {
                    psInsert.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }

            if(connection != null){
                try {
                    connection.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }
}

