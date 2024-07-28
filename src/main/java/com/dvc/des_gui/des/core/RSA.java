package com.dvc.des_gui.des.core;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class RSA {
    private BigInteger p;
    private BigInteger q;
    private BigInteger n;
    private BigInteger e;
    private BigInteger d;

    public RSA(int bitLength) {
        generateKeys(bitLength);
    }

    private void generateKeys(int bitLength) {
        SecureRandom rand = new SecureRandom();
        p = BigInteger.probablePrime(bitLength / 2, rand);
        q = BigInteger.probablePrime(bitLength / 2, rand);
        n = p.multiply(q);
        BigInteger phi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
        e = BigInteger.probablePrime(bitLength / 2, rand);

        while (e.compareTo(BigInteger.ONE) <= 0 || e.compareTo(phi) >= 0 || phi.gcd(e).compareTo(BigInteger.ONE) != 0) {
            e = e.add(BigInteger.ONE);
        }

        d = e.modInverse(phi);
    }

    public String encrypt(String message) {
        StringBuilder ciphertextBuilder = new StringBuilder();
        for (int i = 0; i < message.length(); i++) {
            char c = message.charAt(i);
            BigInteger m = BigInteger.valueOf((int) c);
            BigInteger encrypted = m.modPow(e, n);
            ciphertextBuilder.append(encrypted.toString()).append(" ");
        }
        return ciphertextBuilder.toString().trim();
    }

    public String decrypt(String ciphertext) {
        StringBuilder plaintextBuilder = new StringBuilder();
        String[] encryptedChars = ciphertext.split(" ");
        for (String encryptedChar : encryptedChars) {
            BigInteger encrypted = new BigInteger(encryptedChar);
            BigInteger decrypted = encrypted.modPow(d, n);
            char plaintextChar = (char) decrypted.intValue();
            plaintextBuilder.append(plaintextChar);
        }
        return plaintextBuilder.toString();
    }

    public void printKeys() {
        System.out.println("Prime numbers p and q:");
        System.out.println("p: " + p);
        System.out.println("q: " + q);
        System.out.println("Public key (n, e):");
        System.out.println("n: " + n);
        System.out.println("e: " + e);
        System.out.println("Private key (d):");
        System.out.println("d: " + d);
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java RSA <bit-length>");
            return;
        }

        int bitLength;
        try {
            bitLength = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.out.println("Invalid bit length. Please enter a valid integer.");
            return;
        }

        RSA rsa = new RSA(bitLength);
        rsa.printKeys();

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter plaintext to encrypt:");
        String plaintext = scanner.nextLine();

        long startEncrypt = System.nanoTime();
        String ciphertext = rsa.encrypt(plaintext);
        long endEncrypt = System.nanoTime();
        long encryptionTime = (endEncrypt - startEncrypt) / 1_000;

        writeFile(ciphertext);

        System.out.println("Ciphertext written to ciphertext.txt");
        System.out.println("Encryption time: " + encryptionTime + " µs");

        System.out.println("Do you want to decrypt the ciphertext? (y/n)");
        String response = scanner.nextLine();
        if (response.equalsIgnoreCase("y")) {
            try {
                String fileCiphertext = readFile();

                long startDecrypt = System.nanoTime();
                String decryptedText = rsa.decrypt(fileCiphertext);
                long endDecrypt = System.nanoTime();
                long decryptionTime = (endDecrypt - startDecrypt) / 1_000;

                System.out.println("Decrypted text:");
                System.out.println(decryptedText);
                System.out.println("Decryption time: " + decryptionTime + " µs");
            } catch (IOException e) {
                System.out.println("Error reading from file: " + e.getMessage());
            }
        } else {
            System.out.println("Decryption skipped.");
        }
    }

    private static void writeFile(String content) {
        try (FileWriter fileWriter = new FileWriter("ciphertext.txt", false)) {
            fileWriter.write(content);
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    private static String readFile() throws IOException {
        return new String(Files.readAllBytes(Paths.get("ciphertext.txt")));
    }
}
