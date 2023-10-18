module com.example.projetpuissance4 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.projetpuissance4 to javafx.fxml;
    exports com.projetpuissance4;
}