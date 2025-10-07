module com.example.arkanoid {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

//    requires com.dlsc.formsfx;
//    requires javafx.web;

    opens com.example.arkanoid to javafx.fxml;
    exports com.example.arkanoid;
}