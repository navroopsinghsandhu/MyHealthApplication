module com.example.myhealth {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires java.prefs;

    opens com.example.myhealth to javafx.fxml;
    exports com.example.myhealth;
    exports com.example.myhealth.controller;
    opens com.example.myhealth.controller to javafx.fxml;
    exports com.example.myhealth.model;
    opens com.example.myhealth.model to javafx.fxml;
}