package com.example.myhealth.controller;

import com.example.myhealth.UserRecord;
import com.example.myhealth.model.DBUtils;
import com.example.myhealth.model.UpdateRecordModel;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class UpdateRecordsController implements Initializable {

    @FXML
    private TableView<UserRecord> records;

    @FXML
    private TableColumn<UserRecord, Integer> idCol;

    @FXML
    private TableColumn<UserRecord, String> weightCol;

    @FXML
    private TableColumn<UserRecord, String> temperatureCol;

    @FXML
    private TableColumn<UserRecord, String> bloodpressureCol;

    @FXML
    private TableColumn<UserRecord, String> noteCol;

    @FXML
    private TextField inputId;

    @FXML
    private TextField weight;

    @FXML
    private TextField temperature;


    @FXML
    private TextField bloodPressure;

    @FXML
    private TextField note;

    @FXML
    private Button button_delete;

    @FXML
    private Button button_back;

    @FXML
    private Button button_export;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idCol.setCellValueFactory(new PropertyValueFactory<UserRecord, Integer>("id"));
        weightCol.setCellValueFactory(new PropertyValueFactory<UserRecord, String>("weight"));
        temperatureCol.setCellValueFactory(new PropertyValueFactory<UserRecord, String>("temperature"));
        bloodpressureCol.setCellValueFactory(new PropertyValueFactory<UserRecord, String>("bloodPressure"));
        noteCol.setCellValueFactory(new PropertyValueFactory<UserRecord, String>("note"));
        setupTable();

        button_back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Preferences userPreferences = Preferences.userRoot();
                String username = userPreferences.get("username", "username");
                String firstname = userPreferences.get("firstname", "firstname");
                String lastname = userPreferences.get("lastname", "lastname");
                DBUtils.changeScene(actionEvent, "handle-records.fxml", firstname + " " + lastname, username);
            }
        });

        button_delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                UpdateRecordModel.deleteRecord(Integer.parseInt(inputId.getText()));
                for(int i = 0; i < records.getItems().size(); i++){
                    int currentId = records.getItems().get(i).getId();
                    if(inputId.getText() != "" && currentId == Integer.parseInt(inputId.getText())){
                        records.getItems().remove(i);
                    }
                }
                inputId.setText("");
                weight.setText("");
                temperature.setText("");
                bloodPressure.setText("");
                note.setText("");
            }
        });


        // to export the record to a txt file, each line representing one record
        // name of the file is username.txt
        // delimiter used to separate each field for a particular record -  "|"
        button_export.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String username = null;
                try {
                    Preferences userPreferences = Preferences.userRoot();
                    username = userPreferences.get("username", "username");
                    File myObj = new File("./" + username + ".txt");
                    if (myObj.createNewFile()) {
                        System.out.println("File created: " + myObj.getName());
                    } else {
                        PrintWriter writer = new PrintWriter(myObj);
                        writer.print("");
                        writer.close();
                    }
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }

                String labels = "Id | Weight | Temperature | Blood Pressure | Note";
                try (PrintWriter output = new PrintWriter(new FileWriter(username + ".txt", false));) {
                    output.flush();
                    // iterate through the list of houses to find the house which is to be updated
                    output.print(labels + "\n");
                    for (int i = 0; i < records.getItems().size(); i++) {
                        UserRecord record = records.getItems().get(i);

                        String delimeter = " | ";
                        String recordInfo = record.getId() + delimeter + record.getWeight()+ delimeter + record.getTemperature() + delimeter + record.getBloodPressure() + delimeter + record.getNote();
                        output.print(recordInfo + "\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                if(records.getItems().size() == 0){
                    alert.setContentText("No records present to export!");
                } else {
                    alert.setContentText("File " + username + ".txt has all your current records!");
                }
                alert.show();

            }
        });
    }

    // updates the record
    @FXML
    void submit(ActionEvent event) {
        ObservableList<UserRecord> currentTableData = records.getItems();
        int currentUserRecordId = 0;
        if(!inputId.getText().trim().isEmpty()){
            currentUserRecordId = Integer.parseInt(inputId.getText());
        }
        for (UserRecord UserRecords : currentTableData) {
            if(UserRecords.getId() == currentUserRecordId) {
                Alert alert = new Alert(Alert.AlertType.ERROR);

                // checks for the value of weight entered
                // should be a positive number only
                if (!weight.getText().trim().isEmpty()) {
                    String wt = weight.getText();
                    boolean isNumeric = wt.chars().allMatch( Character::isDigit );
                    if(!isNumeric){
                        alert.setContentText("Weight can only be a positive number");
                        alert.show();
                        break;
                    }
                }

                // checks for the value of temperature entered
                // should be a positive number only
                if (!temperature.getText().trim().isEmpty()) {
                    String tmp = temperature.getText();
                    boolean isNumeric = tmp.chars().allMatch( Character::isDigit );
                    if(!isNumeric){
                        alert.setContentText("Blood Pressure can only be a positive number");
                        alert.show();
                        break;
                    }
                }

                // checks for the value of blood pressure entered
                // should be a positive number only
                if (!bloodPressure.getText().trim().isEmpty()) {
                    String bp = bloodPressure.getText();
                    boolean isNumeric = bp.chars().allMatch( Character::isDigit );
                    if(!isNumeric){
                        alert.setContentText("Blood Pressure can only be a positive number");
                        alert.show();
                        break;
                    }
                }

                // not all the fields can be empty
                if(weight.getText().trim().isEmpty() && temperature.getText().trim().isEmpty() && bloodPressure.getText().trim().isEmpty() && note.getText().trim().isEmpty()){
                    alert.setContentText("A record can not have all the fields empty");
                    alert.show();
                } else {
                    // updates the table UI
                    String newWeight = weight.getText();
                    String newTemperature = temperature.getText();
                    String newBloodPressure = bloodPressure.getText();
                    String newNote = note.getText();
                    UserRecords.setWeight(newWeight);
                    UserRecords.setTemperature(newTemperature);
                    UserRecords.setBloodPressure(newBloodPressure);
                    UserRecords.setNote(newNote);
                    // updates the database
                    UpdateRecordModel.updateDataTable(currentUserRecordId, newWeight, newTemperature, newBloodPressure, newNote);
                    this.records.setItems(currentTableData);
                    this.records.refresh();
                }
                break;
            }
        }
    }

    //when a particular record is clicked from the table
    @FXML
    void rowClicked(MouseEvent event) {
        try{
            UserRecord clickedUserRecords = records.getSelectionModel().getSelectedItem();
            inputId.setText(String.valueOf(clickedUserRecords.getId()));
            weight.setText(String.valueOf(clickedUserRecords.getWeight()));
            temperature.setText(String.valueOf(clickedUserRecords.getTemperature()));
            bloodPressure.setText(String.valueOf(clickedUserRecords.getBloodPressure()));
            note.setText(String.valueOf(clickedUserRecords.getNote()));
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    //when the user navigates to this page, set up the table by retrieving the data for records from database
    private void setupTable(){
        ArrayList<UserRecord> allUserRecords = null;
        allUserRecords = UpdateRecordModel.extractRecords();
        for(UserRecord record : allUserRecords){
            records.getItems().add(record);
        }
    }
}