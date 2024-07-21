package com.dvc.des_gui.des.controllers;

import com.dvc.des_gui.des.DESApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class DESController {

    @FXML
    private Button decryptionBtn;

    @FXML
    private Button encryptionBtn;

    @FXML
    void decryptionView() throws IOException {
        Stage stage = (Stage) decryptionBtn.getScene().getWindow();
        stage.hide();
        Stage newStage = new Stage();
        newStage.setTitle("DES Decryption");
        FXMLLoader fxmlLoader = new FXMLLoader(DESApplication.class.getResource("decryption.fxml"));
        newStage.setScene(new Scene(fxmlLoader.load()));
        newStage.show();
    }

    @FXML
    void encryptionView() throws IOException {
        Stage stage = (Stage) encryptionBtn.getScene().getWindow();
        stage.hide();
        Stage newStage = new Stage();
        newStage.setTitle("DES Encryption");
        FXMLLoader fxmlLoader = new FXMLLoader(DESApplication.class.getResource("encryption.fxml"));
        newStage.setScene(new Scene(fxmlLoader.load()));
        newStage.show();
    }

}
