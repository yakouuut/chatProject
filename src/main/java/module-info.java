module com.example.chatproject {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.chatproject to javafx.fxml;
    exports com.example.chatproject;
}