module com.example.hangmanproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires org.json;


    opens com.example.hangmanproject to javafx.fxml;
    exports com.example.hangmanproject;
}