package com.dvc.des_gui.des.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.security.SecureRandom;
import java.util.HexFormat;

public class DESController {

    @FXML
    private Button decryptionBtn;

    @FXML
    private Button encryptionBtn;

    @FXML
    void decryptionView() {

    }

    @FXML
    void encryptionView() {

    }


    public static String generateRandomKey() {
        SecureRandom random = new SecureRandom();
        byte[] keyBytes = new byte[20 / 2];
        random.nextBytes(keyBytes);
        return HexFormat.of().formatHex(keyBytes);
    }
}
