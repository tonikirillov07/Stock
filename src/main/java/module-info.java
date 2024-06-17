module com.ds.stock {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.ds.stock to javafx.fxml;
    exports com.ds.stock;
}