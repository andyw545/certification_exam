module com.andy.examapp {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.andy.examapp to javafx.fxml;
    exports com.andy.examapp;
}
