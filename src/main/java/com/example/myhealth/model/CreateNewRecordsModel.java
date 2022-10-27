package com.example.myhealth.model;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import java.sql.*;
import java.time.Instant;
import java.util.prefs.Preferences;

// model to create a new record, storing it in the database
public class CreateNewRecordsModel {
    public static void createNewRecord(ActionEvent event, String weight, String temperature, String bloodPressure, String note){
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckUserExists = null;
        ResultSet resultSet = null;

        try{
            Preferences userPreferences = Preferences.userRoot();
            String username = userPreferences.get("username", "username");
            connection = DriverManager.getConnection("jdbc:sqlite:health.db", "", "");
            psInsert = connection.prepareStatement("INSERT INTO records(weight, temperature, bloodPressure, note, username, time) VALUES(?, ?, ?, ?, ?, ?)");
            long now = Instant.now().toEpochMilli();
            // each record is mapped to the unique username in the database table
            psInsert.setString(1, weight);
            psInsert.setString(2, temperature);
            psInsert.setString(3, bloodPressure);
            psInsert.setString(4, note);
            psInsert.setString(5, username);
            // to store the time of the record when created
            psInsert.setString(6, String.valueOf(now));
            psInsert.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Record created");
            alert.show();
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
