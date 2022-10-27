package com.example.myhealth.model;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.prefs.Preferences;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

import java.sql.*;

// model handling the login of the application
public class LoginUserModel {
    public static void logInUser(ActionEvent event, String username, String password){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:health.db", "", "");
            preparedStatement = connection.prepareStatement("SELECT * from users WHERE username = ?");
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            if(!resultSet.isBeforeFirst()){
                System.out.println("User not found");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Credentials are wrong");
                alert.show();
            } else {
                while (resultSet.next()){
                    String retrievedPassword = resultSet.getString("password");
                    String firstname = resultSet.getString("firstname");
                    String lastname = resultSet.getString("lastname");

                    // converts the entered password into a hash string as hashed password is stored in the table
                    String encoded = "";
                    try {
                        MessageDigest digest = MessageDigest.getInstance("SHA-256");
                        byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
                        encoded = Base64.getEncoder().encodeToString(hash);
                    } catch (NoSuchAlgorithmException e){
                        e.printStackTrace();
                    }

                    if(retrievedPassword.equals(encoded)){
                        // update the values in the userPreferences whenever a new user logs in
                        Preferences userPreferences = Preferences.userRoot();
                        userPreferences.put("username",username);
                        userPreferences.put("firstname",firstname);
                        userPreferences.put("lastname",lastname);
                        DBUtils.changeScene(event, "logged-in.fxml", firstname + " " + lastname, username);
                    } else{
                        System.out.println("Passwords did not match");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Credentials are wrong");
                        alert.show();
                    }
                }

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

            if(preparedStatement != null){
                try {
                    preparedStatement.close();
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
