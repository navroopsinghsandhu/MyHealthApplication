package com.example.myhealth.controller;

import com.example.myhealth.model.DBUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class LoggedInController implements Initializable {

    @FXML
    private Button button_logout;

    @FXML
    private Button button_personal;

    @FXML
    private Button button_records;

    @FXML
    Button button_about;

    @FXML
    private Label label_welcome;

    @FXML
    private Label label_welcome_record;

    @FXML
    private Label label_fullname;

    //retrieves the current user's details to send in the new scene to show on the window
    Preferences userPreferences = Preferences.userRoot();
    private String firstname = userPreferences.get("firstname", "firstname");
    private String lastname = userPreferences.get("lastname", "lastname");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        button_logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                DBUtils.changeScene(actionEvent, "log-in.fxml", "Log In!", null);
                Preferences userPreferences = Preferences.userRoot();
                userPreferences.put("username","");
                userPreferences.put("firstname","");
                userPreferences.put("lastname","");
            }
        });

        button_personal.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                DBUtils.changeScene(actionEvent, "update-personal-details.fxml", firstname + " " + lastname, null);
            }
        });

        button_records.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                DBUtils.changeScene(actionEvent, "handle-records.fxml", firstname + " " + lastname, null);
            }
        });

        button_about.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Preferences userPreferences = Preferences.userRoot();
                String firstname = userPreferences.get("firstname","firstname");
                String lastname = userPreferences.get("lastname","lastname");
                DBUtils.changeScene(actionEvent, "about.fxml", firstname + " " + lastname, null);
            }
        });
    }

    public void setUserInformation(){
        label_fullname.setText(firstname + " " + lastname);
    }
}
