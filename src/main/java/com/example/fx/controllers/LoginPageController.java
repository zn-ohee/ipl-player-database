package com.example.fx.controllers;

import com.example.fx.client.MainApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class LoginPageController {

    private MainApplication mainApp;

    @FXML
    private TextField inputClubName;

    @FXML
    private PasswordField inputPassword;

    @FXML
    private ImageView logoImageView;

    @FXML
    private ImageView avatarImageView;

    public MainApplication getMainApp() {
        return mainApp;
    }

    public void initialize(MainApplication mainApp, Image logo, Image avatar) {
        this.mainApp = mainApp;
        logoImageView.setImage(logo);
        avatarImageView.setImage(avatar);
    }

    public void setMainApp(MainApplication mainApp) {
        this.mainApp = mainApp;
    }

    public void onLoginButtonClick(ActionEvent actionEvent) throws IOException {
        if(inputClubName.getText().isEmpty() || inputPassword.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Insufficient Information");
            alert.setContentText("Provide sufficient information");
            Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
            alertStage.getIcons().add(mainApp.getLogo());
            alert.showAndWait();
        }
        else{
            mainApp.sendLoginRequest(inputClubName.getText(), inputPassword.getText());
        }
    }

    public void onExitButtonClick(ActionEvent actionEvent) throws Exception{
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText("Confirm exit");
        alert.setContentText("Are you sure you want to exit?");
        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        alertStage.getIcons().add(mainApp.getLogo());
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            mainApp.closeProgram();
        }
    }

}
