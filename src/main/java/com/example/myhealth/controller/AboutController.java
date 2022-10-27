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

//controller for About page - about.fxml
public class AboutController implements Initializable {
    @FXML
    Button button_back;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        button_back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //retrieves the current user's details and send in the new scene to show on the window
                Preferences userPreferences = Preferences.userRoot();
                String username = userPreferences.get("username","username");
                String firstname = userPreferences.get("firstname","firstname");
                String lastname = userPreferences.get("lastname","lastname");
                DBUtils.changeScene(actionEvent, "logged-in.fxml", firstname + " " + lastname, username);
            }
        });
    }
}
