package com.dvc.des_gui.des.controllers;

import com.dvc.des_gui.des.DESApplication;
import com.dvc.des_gui.des.core.DES;
import com.dvc.des_gui.des.core.exception.EncryptionException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.security.SecureRandom;
import java.util.HexFormat;

public class EncryptionController {
    @FXML
    private Button browse;

    @FXML
    private Button backBtn;

    @FXML
    private Button encryptBtn;

    @FXML
    private TextField encryptionKey;

    @FXML
    private Text fileName;

    @FXML
    private Button generateBtn;

    @FXML
    private Text timeTaken;

    @FXML
    void desScreen() throws IOException {
        DecryptionController.mainStage(backBtn);
    }

    @FXML
    void generateKey() {
        encryptionKey.setText(generateRandomKey());
    }

    private File _file;

    @FXML
    void getFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            fileName.setText(file.getName());
        }
        this._file = file;
    }

    @FXML
    void encryption() {
        try (BufferedReader reader = new BufferedReader(new FileReader(_file))) {
            StringBuilder fileText = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                fileText.append(line);
            }
            String encryptedText = DES.encrypt(fileText.toString(), encryptionKey.getText());
            writeToFile(encryptedText);

        } catch (EncryptionException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    void writeToFile(String encryptedText) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Encrypted File");
        File file = fileChooser.showSaveDialog(new Stage());
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(encryptedText);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String generateRandomKey() {
        SecureRandom random = new SecureRandom();
        byte[] keyBytes = new byte[20 / 2];
        random.nextBytes(keyBytes);
        return HexFormat.of().formatHex(keyBytes);
    }
}
