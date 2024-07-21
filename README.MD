# DES GUI IMPLEMENTATION
This project is a simple implementation of the DES algorithm using JavaFX for the GUI. The project is a simple implementation of the DES algorithm and is not intended for production use. The project is intended for educational purposes only, tailored for CE372 Cryptography Labwork

![DES GUI](screenshot.png)
![Encryption](screenshot1.png)
![Decryption](screenshot1.png)
## PROJECT STRUCTURE
```main
├── java
│   ├── com
│   │   └── dvc
│   │       └── des_gui
│   │           └── des
│   │               ├── DESApplication.java
│   │               ├── controllers
│   │               │   ├── DESController.java
│   │               │   ├── DecryptionController.java
│   │               │   └── EncryptionController.java
│   │               └── core
│   │                   ├── DES.java
│   │                   └── exception
│   │                       ├── DecryptionException.java
│   │                       └── EncryptionException.java
│   └── module-info.java
└── resources
    ├── MANIFEST.MF
    └── com
        └── dvc
            └── des_gui
                └── des
                    ├── decryption.fxml
                    ├── encryption.fxml
                    └── main.fxml

```

## HOW TO RUN THE PROJECT
1. Clone the repository
2. Open your terminal and navigate to the project directory
3. Run the following command to build the project
```shell    
    mvn clean package
```