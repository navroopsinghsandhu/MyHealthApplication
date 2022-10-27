package com.example.myhealth.controller;

import com.example.myhealth.model.DBUtils;
import com.example.myhealth.model.UpdatePersonalDetailsModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

// controller to update personal details - update-personal-details.fxml
public class UpdatePersonalDetailsController implements Initializable {
    @FXML
    private Button button_update_personal;

    @FXML
    private Button button_go_back;

    @FXML
    private TextField tf_update_firstname;

    @FXML
    private TextField tf_update_lastname;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        button_update_personal.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                // no empty firstname or lastname
                if(!tf_update_firstname.getText().trim().isEmpty() && !tf_update_lastname.getText().trim().isEmpty()){
                    UpdatePersonalDetailsModel.UpdateUserDetails(actionEvent, tf_update_firstname.getText(), tf_update_lastname.getText());
                    Preferences userPreferences = Preferences.userRoot();
                    userPreferences.put("firstname",tf_update_firstname.getText());
                    userPreferences.put("lastname",tf_update_lastname.getText());
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please fill in all the information");
                    alert.show();
                }
            }
        });

        button_go_back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //retrieves the current user's details to send in the new scene to show on the window
                Preferences userPreferences = Preferences.userRoot();
                String username = userPreferences.get("username", "username");
                String firstname = userPreferences.get("firstname", "firstname");
                String lastname = userPreferences.get("lastname", "lastname");
                DBUtils.changeScene(actionEvent, "logged-in.fxml", firstname + " " + lastname, username);
            }
        });
    }
}
