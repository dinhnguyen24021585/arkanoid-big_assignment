module com.example.arkanoid {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires jdk.jfr;
    requires javafx.graphics;
    requires javafx.media;


//    requires com.dlsc.formsfx;
//    requires javafx.web;

    opens com.example.arkanoid.Controllers to javafx.fxml;
    exports com.example.arkanoid;
}