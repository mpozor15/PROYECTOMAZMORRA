module com.ignaciomanuel {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.ignaciomanuel to javafx.fxml;
    exports com.ignaciomanuel;
}
