module com.ds.stock {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jetbrains.annotations;
    requires java.sql;


    opens com.ds.stock to javafx.fxml;
    exports com.ds.stock;
    exports com.ds.stock.controllers;
    opens com.ds.stock.controllers to javafx.fxml;
}