module com.ignaciomanuel {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.ignaciomanuel.mazmorra to javafx.fxml;
    exports com.ignaciomanuel.mazmorra;

    opens com.ignaciomanuel.mazmorra.logica to javafx.base;
    exports com.ignaciomanuel.mazmorra.logica;
}
