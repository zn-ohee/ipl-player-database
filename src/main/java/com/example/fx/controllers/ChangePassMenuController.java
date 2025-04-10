package com.example.fx.controllers;

import com.example.fx.client.MainApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Optional;

public class ChangePassMenuController {

    private MainApplication mainApp;

    @FXML
    private Label clubNameLabel2;

    @FXML
    private ImageView logoImageView;

    public MainApplication getMainApp() {
        return mainApp;
    }

    public void setMainApp(MainApplication mainApp) {
        this.mainApp = mainApp;
    }

    public void initialize(MainApplication mainApp) {

        this.mainApp = mainApp;
        this.clubNameLabel2.setText(mainApp.getMyClubName());

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(mainApp.getClubLogoByteArray());
        Image image = new Image(byteArrayInputStream);
        logoImageView.setImage(image);

    }

    public void onBackButtonClick(ActionEvent actionEvent) throws IOException {
        mainApp.loadMainMenu();
    }

    public void onLogoutButtonClick(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Log out");
        alert.setHeaderText("Confirm log out");
        alert.setContentText("Are you sure you want to log out?");
        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        alertStage.getIcons().add(mainApp.getLogo());
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            mainApp.closeProgram();
        }
    }

    @FXML
    private PasswordField currentPassInput;

    @FXML
    private PasswordField newPassInput;

    @FXML
    private PasswordField confirmPassInput;

    public void onChangePassClick(ActionEvent actionEvent) throws IOException {

        String currentPass = currentPassInput.getText();
        String newPass = newPassInput.getText();
        String confirmPass = confirmPassInput.getText();

        if(currentPass.isEmpty() || newPass.isEmpty() || confirmPass.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Insufficient Information");
            alert.setContentText("Provide sufficient information");
            Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
            alertStage.getIcons().add(mainApp.getLogo());
            alert.showAndWait();
        }
        else if(!newPass.equals(confirmPass)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Try again");
            alert.setContentText("New passwords do not match");
            Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
            alertStage.getIcons().add(mainApp.getLogo());
            alert.showAndWait();
        }
        else{
            mainApp.sendChangePassRequest(currentPass, newPass);
        }

    }

    public void clearInputs() {
        currentPassInput.clear();
        newPassInput.clear();
        confirmPassInput.clear();
    }

}
