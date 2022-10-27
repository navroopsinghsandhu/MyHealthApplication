package com.example.myhealth.model;

import com.example.myhealth.UserRecord;
import javafx.scene.control.Alert;
import java.sql.*;
import java.util.ArrayList;
import java.util.prefs.Preferences;

// to retrieve and update the records in the database
public class UpdateRecordModel {

    // retrieves all the records from the database to display in the table
    public static ArrayList<UserRecord> extractRecords(){
        ArrayList<UserRecord> allUserRecords = new ArrayList<>();
        Connection connection = null;
        PreparedStatement psGet = null;
        PreparedStatement psCheckUserExists = null;
        ResultSet resultSet = null;

        try{
            Preferences userPreferences = Preferences.userRoot();
            String username = userPreferences.get("username", "username");
            connection = DriverManager.getConnection("jdbc:sqlite:health.db", "", "");
            psGet = connection.prepareStatement("SELECT * from records WHERE username = ?");
            psGet.setString(1, username);
            resultSet = psGet.executeQuery();
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String weight = resultSet.getString("weight");
                String temperature = resultSet.getString("temperature");
                String bloodPressure = resultSet.getString("bloodPressure");
                String note = resultSet.getString("note");
                String time = resultSet.getString("time");
                // creates a "UserRecord" object and add to the tableView
                UserRecord record = new UserRecord(id, weight, temperature, bloodPressure, note);
                allUserRecords.add(record);
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

            if(psGet != null){
                try {
                    psGet.close();
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
        return allUserRecords;
    }

    // updates the record in the database
    // all the checks have been performed in the controller
    public static void updateDataTable(int id, String weight, String temperature, String bloodPressure, String note){
        Connection connection = null;
        PreparedStatement psUpdate = null;
        PreparedStatement psCheckUserExists = null;
        ResultSet resultSet = null;

        try{
            Preferences userPreferences = Preferences.userRoot();
            String username = userPreferences.get("username", "username");
            connection = DriverManager.getConnection("jdbc:sqlite:health.db", "", "");
            psUpdate = connection.prepareStatement("UPDATE records SET weight = ?, temperature = ?, bloodPressure = ?, note = ? WHERE username = ? AND id = ?");
            psUpdate.setString(1, weight);
            psUpdate.setString(2, temperature);
            psUpdate.setString(3, bloodPressure);
            psUpdate.setString(4, note);
            psUpdate.setString(5, username);
            psUpdate.setString(6, String.valueOf(id));
            psUpdate.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Record updated");
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

    // to delete a particular record
    // delete operation is performed using the record id
    public static void deleteRecord(int id){
        Connection connection = null;
        PreparedStatement psDelete = null;
        PreparedStatement psCheckUserExists = null;
        try{
            Preferences userPreferences = Preferences.userRoot();
            String username = userPreferences.get("username", "username");
            connection = DriverManager.getConnection("jdbc:sqlite:health.db", "", "");
            psDelete = connection.prepareStatement("DELETE FROM records WHERE id = ?");
            psDelete.setString(1, String.valueOf(id));
            psDelete.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Record deleted");
            alert.show();
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            if(psDelete != null){
                try {
                    psDelete.close();
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