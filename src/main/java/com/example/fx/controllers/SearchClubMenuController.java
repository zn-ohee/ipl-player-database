package com.example.fx.controllers;

import com.example.fx.client.MainApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Optional;

public class SearchClubMenuController {

    private MainApplication mainApp;

    @FXML
    private ImageView logoImageView;

    @FXML
    private Label clubNameLabel;

    public MainApplication getMainApp() {
        return mainApp;
    }

    public void setMainApp(MainApplication mainApp) {
        this.mainApp = mainApp;
    }

    public void initialize(MainApplication mainApp) {
        this.mainApp = mainApp;
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(mainApp.getClubLogoByteArray());
        Image image = new Image(byteArrayInputStream);
        logoImageView.setImage(image);
        clubNameLabel.setText(mainApp.getMyClubName());
    }

    public void onBackButtonClick(ActionEvent actionEvent) throws IOException {
        mainApp.loadMainMenu();
    }

    public void onMaxSalaryClick(ActionEvent actionEvent) throws IOException {
        mainApp.loadMaxSalaryPlayersMenu();
    }

    public void onMaxAgeClick(ActionEvent actionEvent) throws IOException {
        mainApp.loadMaxAgePlayersMenu();
    }

    public void onMaxHeightClick(ActionEvent actionEvent) throws IOException {
        mainApp.loadMaxHeightPlayersMenu();
    }

    public void onTotalYearlySalaryClick(ActionEvent actionEvent) throws IOException {
        //mainApp.loadTotalYearlySalaryMenu();
        mainApp.showTotalYearlySalary();
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

}
