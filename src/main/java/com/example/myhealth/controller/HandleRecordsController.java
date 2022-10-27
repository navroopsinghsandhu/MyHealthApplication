package com.example.myhealth.controller;

import com.example.myhealth.model.DBUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

//controller for handle records page - handle-record.fxml
public class HandleRecordsController implements Initializable {
    @FXML
    Button button_new_record;

    @FXML
    Button button_go_back;

    @FXML
    Button button_update_record;

    //retrieves the current user's details to send in the new scene to show on the window
    Preferences userPreferences = Preferences.userRoot();
    private String firstname = userPreferences.get("firstname", "firstname");
    private String lastname = userPreferences.get("lastname", "lastname");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        button_new_record.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                DBUtils.changeScene(actionEvent, "create-new-records.fxml", firstname + " " + lastname, null);
            }
        });

        button_update_record.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                DBUtils.changeScene(actionEvent, "update-records.fxml", firstname + " " + lastname, null);
            }
        });

        button_go_back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Preferences userPreferences = Preferences.userRoot();
                String username = userPreferences.get("username", "username");
                DBUtils.changeScene(actionEvent, "logged-in.fxml", firstname + " " + lastname, username);
            }
        });
    }
}
