package com.dvc.des_gui.des.controllers;

import com.dvc.des_gui.des.DESApplication;
import com.dvc.des_gui.des.core.DES;
import com.dvc.des_gui.des.core.exception.DecryptionException;
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


    @FXML
    void decryption() {
        try (BufferedReader reader = new BufferedReader(new FileReader(_file))) {
            StringBuilder fileText = new StringBuilder();
            String line;
            long startTime = System.nanoTime();

            while ((line = reader.readLine()) != null) {
                fileText.append(line);
            }

            String encryptedText = DES.decrypt(fileText.toString(), encryptionKey.getText());
            writeToFile(encryptedText);

            long endTime = System.nanoTime();
            long encryptionTime = (endTime - startTime) / 1_000_000;

            timeTaken.setText("Finished in " + encryptionTime + " ms");

        } catch (DecryptionException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
