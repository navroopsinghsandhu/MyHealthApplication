package com.example.myhealth.controller;

import com.example.myhealth.model.DBUtils;
import com.example.myhealth.model.SignUpModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {
    @FXML
    private Button button_signup;

    @FXML
    private Button button_log_in;

    @FXML
    private TextField tf_username;

    @FXML
    private TextField pf_password;

    @FXML
    private TextField tf_firstname;

    @FXML
    private TextField tf_lastname;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // signs you up when all the fields are filled
        button_signup.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(!tf_username.getText().trim().isEmpty() && !pf_password.getText().trim().isEmpty() && !tf_firstname.getText().trim().isEmpty() && !tf_firstname.getText().trim().isEmpty()){
                    SignUpModel.signUpUser(actionEvent, tf_username.getText(), pf_password.getText(), tf_firstname.getText(), tf_lastname.getText());
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please fill in all the information");
                    alert.show();
                }
            }
        });

        // if you have already signed up, this button lets you navigate to the "Log In" page
        button_log_in.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                DBUtils.changeScene(actionEvent, "log-in.fxml", "Log In!", null);
            }
        });
    }
}
