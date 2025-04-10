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

public class MainMenuController {

    private MainApplication mainApp;

    public MainApplication getMainApp() {
        return mainApp;
    }

    public void setMainApp(MainApplication mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private Label clubNameLabel1;

    @FXML
    private Label clubNameLabel2;

    @FXML
    private ImageView logoImageView;

    public void initialize(MainApplication mainApp, String clubName) {

        this.mainApp = mainApp;
        this.clubNameLabel1.setText(clubName);
        this.clubNameLabel2.setText(clubName);

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(mainApp.getClubLogoByteArray());
        Image image = new Image(byteArrayInputStream);
        logoImageView.setImage(image);

    }

    public void onSearchPlayerClick(ActionEvent actionEvent) throws IOException {
        mainApp.loadSearchPlayerMenu();
    }

    public void onSearchClubClick(ActionEvent actionEvent) throws IOException {
        mainApp.loadSearchClubMenu();
    }

    /*public void onAddPlayerClick(ActionEvent actionEvent) throws IOException {
        mainApp.loadAddPlayerMenu();
    }*/

    public void onBuyPlayerClick(ActionEvent actionEvent) throws IOException {
        mainApp.loadMarket();
    }

    public void onSellPlayerClick(ActionEvent actionEvent) throws IOException {
        mainApp.loadSellPlayerMenu();
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

    public void onChangePassClick(ActionEvent actionEvent) throws IOException {
        mainApp.loadChangePassMenu();
    }

}