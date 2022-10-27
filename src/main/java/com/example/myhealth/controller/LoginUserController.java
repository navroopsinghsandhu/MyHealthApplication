package com.example.myhealth.controller;

import com.example.myhealth.model.DBUtils;
import com.example.myhealth.model.LoginUserModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

//controller for log in page
public class LoginUserController implements Initializable {

    @FXML
    private
    Button button_log_in;

    @FXML
    Button button_signup;

    @FXML
    private TextField tf_username;

    @FXML
    private PasswordField pf_password;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //button logs you in when the credentials are correctly verified
        button_log_in.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                LoginUserModel.logInUser(actionEvent, tf_username.getText(), pf_password.getText());
            }
        });

        //button takes you to signu up page
        button_signup.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                DBUtils.changeScene(actionEvent, "sign-up.fxml", "Sign Up!", null);
            }
        });


    }
}