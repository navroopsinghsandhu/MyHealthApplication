package com.example.myhealth.model;
import java.util.prefs.Preferences;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import java.sql.*;

// to update the person details in the database for the respective users
public class UpdatePersonalDetailsModel {
    public static void UpdateUserDetails(ActionEvent event, String firstname, String lastname){
        Connection connection = null;
        PreparedStatement psUpdate = null;
        PreparedStatement psCheckUserExists = null;
        ResultSet resultSet = null;

        try{

            Preferences userPreferences = Preferences.userRoot();
            String username = userPreferences.get("username", "username");
            connection = DriverManager.getConnection("jdbc:sqlite:health.db", "", "");
            psCheckUserExists = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
            psCheckUserExists.setString(1, username);
            resultSet = psCheckUserExists.executeQuery();

            // retrieve the user and then update the details
            if(resultSet.isBeforeFirst()){
                psUpdate = connection.prepareStatement("UPDATE users SET firstname = ?, lastname = ? WHERE username = ?");
                psUpdate.setString(1, firstname);
                psUpdate.setString(2, lastname);
                psUpdate.setString(3, username);
                psUpdate.executeUpdate();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Details updated");
                alert.show();
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

            if(psUpdate != null){
                try {
                    psUpdate.close();
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
