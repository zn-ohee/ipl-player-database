package com.example.fx.controllers;

import com.example.fx.client.MainApplication;
import com.example.fx.models.Player;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Optional;

public class MaxHeightPlayersMenuController{

    private MainApplication mainApp;

    public MainApplication getMainApp() {
        return mainApp;
    }

    public void setMainApp(MainApplication mainApp) {
        this.mainApp = mainApp;
    }

    public void onBackButtonClick(ActionEvent actionEvent) throws IOException {
        mainApp.loadSearchClubMenu();
    }

    @FXML
    private TableView<Player> playerTable;

    @FXML
    private TableColumn<Player, String> playerNames;

    @FXML
    private TableColumn<Player, String> playerCountries;

    @FXML
    private TableColumn<Player, String> playerAges;

    @FXML
    private TableColumn<Player, String> playerPositions;

    @FXML
    private TableColumn<Player, String> playerSalaries;

    @FXML
    private Button showDetailsButton;

    @FXML
    private ImageView logoImageView;

    @FXML
    private Label clubNameLabel;

    public void initialize(MainApplication mainApp) {

        playerNames.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        playerCountries.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCountryName()));
        playerAges.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getAge())));
        playerPositions.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPosition()));
        playerSalaries.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getSalary())));

        showDetailsButton.setDisable(true);

        playerTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            showDetailsButton.setDisable(newValue == null);
        });

        this.mainApp = mainApp;

        playerTable.setItems(FXCollections.observableArrayList(mainApp.getDatabase().MaxHeightPlayers(mainApp.getMyClubName())));

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(mainApp.getClubLogoByteArray());
        Image image = new Image(byteArrayInputStream);
        logoImageView.setImage(image);
        clubNameLabel.setText(mainApp.getMyClubName());

    }

    public void onShowDetailsClick(ActionEvent actionEvent) throws IOException {
        Player selectedPlayer = playerTable.getSelectionModel().getSelectedItem();
        mainApp.loadPlayerInfo(selectedPlayer);
    }

    public void onRefreshClick(ActionEvent actionEvent) throws IOException {
        mainApp.loadMaxHeightPlayersMenu();
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
