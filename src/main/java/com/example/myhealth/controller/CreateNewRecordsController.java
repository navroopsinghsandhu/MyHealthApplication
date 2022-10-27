package com.example.myhealth.controller;

import com.example.myhealth.model.CreateNewRecordsModel;
import com.example.myhealth.model.DBUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

//controller for create new records page - create-new-records.fxml
public class CreateNewRecordsController implements Initializable {
    @FXML
    Button button_go_back;

    @FXML
    Button button_create_record;

    @FXML
    private TextField tf_weight;

    @FXML
    private TextField tf_temperature;

    @FXML
    private TextField tf_blood_pressure;

    @FXML
    private TextArea ta_note;

    //retrieves the current user's details to send in the new scene to show on the window
    Preferences userPreferences = Preferences.userRoot();
    private String firstname = userPreferences.get("firstname", "firstname");
    private String lastname = userPreferences.get("lastname", "lastname");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        button_go_back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                DBUtils.changeScene(actionEvent, "handle-records.fxml", firstname + " " + lastname, null);
            }
        });

        button_create_record.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Some error");
                // checks for the value of weight entered
                // should be a positive number only
                if (!tf_weight.getText().trim().isEmpty()) {
                    String wt = tf_weight.getText();
                    boolean isNumeric = wt.chars().allMatch( Character::isDigit );
                    if(!isNumeric){
                        alert.setContentText("Weight can only be a positive number");
                        alert.show();
                        return;
                    }
                }

                // checks for the value of temperature entered
                // should be a positive number only
                if (!tf_temperature.getText().trim().isEmpty()) {
                    String tmp = tf_temperature.getText();
                    boolean isNumeric = tmp.chars().allMatch( Character::isDigit );
                    if(!isNumeric){
                        alert.setContentText("Temperature can only be a positive number");
                        alert.show();
                        return;
                    }
                }

                // checks for the value of blood pressure entered
                // should be a positive number only
                if (!tf_blood_pressure.getText().trim().isEmpty()) {
                    String bp = tf_blood_pressure.getText();
                    boolean isNumeric = bp.chars().allMatch( Character::isDigit );
                    if(!isNumeric){
                        alert.setContentText("Blood Pressure can only be a positive number");
                        alert.show();
                        return;
                    }
                }

                // check to make sure at least one field is filled while creating a record
                if(tf_weight.getText().trim().isEmpty() && tf_temperature.getText().trim().isEmpty() && tf_blood_pressure.getText().trim().isEmpty() && ta_note.getText().trim().isEmpty()){
                    alert.setContentText("Please fill at least one field");
                    alert.show();
                    return;
                }
                CreateNewRecordsModel.createNewRecord(actionEvent, tf_weight.getText(), tf_temperature.getText(), tf_blood_pressure.getText(), ta_note.getText());

            }
        });

    }
}
