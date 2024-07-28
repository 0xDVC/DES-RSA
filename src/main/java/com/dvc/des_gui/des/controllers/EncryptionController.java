package com.dvc.des_gui.des.controllers;

import com.dvc.des_gui.des.DESApplication;
import com.dvc.des_gui.des.core.DES;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Files;
import java.security.NoSuchAlgorithmException;

public class EncryptionController {
    @FXML
    private Button backBtn;

    @FXML
    private Button encryptBtn;

    @FXML
    private TextField encryptionKey;

    @FXML
    private Text fileName;

    @FXML
    private Text timeTaken;

    private File file;
    private final static int KEY_LENGTH = 16;
    private final DES des = new DES();

    public EncryptionController() throws NoSuchAlgorithmException {
    }

    private boolean isReadyForEncryption() {
        return file != null && encryptionKey.getText().length() == KEY_LENGTH;
    }

    @FXML
    void desScreen() throws IOException {
        DecryptionController.mainStage(backBtn);
    }

    @FXML
    void getFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            fileName.setText(file.getName());
        }
        this.file = file;

        if (isReadyForEncryption()) {
            encryptBtn.setDisable(false);
        }
    }

    @FXML
    void generateKey() {
        encryptionKey.setText(des.getKey());
    }

    @FXML
    void encryption() {
        if (!isReadyForEncryption()) {
            return;
        }

        try {
            byte[] fileData = readFromFile(file);

            des.setKey(encryptionKey.getText());
            long startTime = System.nanoTime();
            byte[] encryptedData = des.encrypt(new String(fileData));
            long endTime = System.nanoTime();
            long encryptionTime = (endTime - startTime) / 1_000;

            writeToFile(encryptedData);

            timeTaken.setText("Finished in " + encryptionTime + " µs");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    byte[] readFromFile(File file) throws IOException {
        return Files.readAllBytes(file.toPath());
    }

    void writeToFile(byte[] encryptedData) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Encrypted File");
        File file = fileChooser.showSaveDialog(new Stage());
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(encryptedData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
