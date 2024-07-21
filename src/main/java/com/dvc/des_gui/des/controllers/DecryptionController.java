package com.dvc.des_gui.des.controllers;

import com.dvc.des_gui.des.DESApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class DecryptionController {
    @FXML
    private Button browse;

    @FXML
    private Button backBtn;

    @FXML
    private Button decryptBtn;

    @FXML
    private TextField encryptionKey;

    @FXML
    private Text fileName;

    @FXML
    private Button provideBtn;

    @FXML
    private Text timeTaken;

    @FXML
    void desScreen() throws IOException {
        mainStage(backBtn);
    }

    static void mainStage(Button backBtn) throws IOException {
        Stage stage = (Stage) backBtn.getScene().getWindow();
        stage.hide();
        Stage newStage = new Stage();
        newStage.setTitle("DES");
        FXMLLoader fxmlLoader = new FXMLLoader(DESApplication.class.getResource("main.fxml"));
        newStage.setScene(new Scene(fxmlLoader.load()));
        newStage.show();
    }

    @FXML
    void decryption() {

    }
}
