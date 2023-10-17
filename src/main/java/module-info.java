module com.example.projetpuissance4 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.projetpuissance4 to javafx.fxml;
    exports com.example.projetpuissance4;
}