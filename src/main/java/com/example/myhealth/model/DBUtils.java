package com.example.myhealth.model;

import com.example.myhealth.controller.LoggedInController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

// to change the scene wherever requested - completed the purpose of navigating from one page to another within the app
public class DBUtils {
    public static void changeScene(ActionEvent event, String fxmlFile, String title, String username) {
        Parent root = null;

        if (username != null) {
            try {
                FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource("/com/example/myhealth/" + fxmlFile));
                root = loader.load();
                if(fxmlFile == "logged-in.fxml"){
                    LoggedInController loggedInController = loader.getController();
                    loggedInController.setUserInformation();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                root = FXMLLoader.load(DBUtils.class.getResource("/com/example/myhealth/" + fxmlFile));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(new Scene(root, 1000, 600));
        stage.show();
    }
}