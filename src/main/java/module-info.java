module com.dvc.des_gui.des {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.dvc.des_gui.des to javafx.fxml;
    exports com.dvc.des_gui.des;
    exports com.dvc.des_gui.des.controllers;
    opens com.dvc.des_gui.des.controllers to javafx.fxml;
}