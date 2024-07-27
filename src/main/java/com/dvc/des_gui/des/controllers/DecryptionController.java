package com.dvc.des_gui.des.controllers;

import com.dvc.des_gui.des.DESApplication;
import com.dvc.des_gui.des.core.DES;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.crypto.BadPaddingException;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.nio.file.Files;

public class DecryptionController {
    @FXML
    private Button backBtn;

    @FXML
    private Button decryptBtn;

    @FXML
    private TextField encryptionKey;

    @FXML
    private Text fileName;

    @FXML
    private Text timeTaken;

    private File file;
    private final static int KEY_LENGTH = 16;

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

    private boolean isReadyForDecryption() {
        return file != null && encryptionKey.getText().length() == KEY_LENGTH;
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

        if(isReadyForDecryption()) {
            decryptBtn.setDisable(false);
        }
    }

    void writeToFile(byte[] data) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Decrypted File");
        File file = fileChooser.showSaveDialog(new Stage());
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    byte[] readFromFile(File file) throws IOException {
        return Files.readAllBytes(file.toPath());
    }

    @FXML
    void decryption() {
        try {
            byte[] fileData = readFromFile(file);

            DES des = new DES();
            des.setKey(encryptionKey.getText());
            long startTime = System.nanoTime();
            try {
                byte[] decryptedData = des.decrypt(fileData);
                long endTime = System.nanoTime();
                long decryptionTime = (endTime - startTime) / 1_000_000;

                writeToFile(decryptedData);

                timeTaken.setText("Finished in " + decryptionTime + " ms");
            } catch (BadPaddingException e) {
                setTemporaryRedBorder(encryptionKey);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void setTemporaryRedBorder(TextField textField) {
        Border originalBorder = textField.getBorder();

        Border redBorder = new Border(new BorderStroke(
                Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));

        textField.setBorder(redBorder);

        Timeline timeline = new Timeline(new KeyFrame(
                Duration.seconds(2),
                event -> textField.setBorder(originalBorder)
        ));
        timeline.setCycleCount(1);
        timeline.play();
    }
}
