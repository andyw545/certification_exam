module com.andy.examapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires com.google.gson;

    opens com.andy.examapp to javafx.fxml;
    opens com.andy.examapp.models to com.google.gson, javafx.base;
    exports com.andy.examapp;
}
