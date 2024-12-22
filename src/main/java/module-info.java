module hu.petrik.stopwatch {
    requires javafx.controls;
    requires javafx.fxml;


    opens hu.petrik.stopwatch to javafx.fxml;
    exports hu.petrik.stopwatch;
}