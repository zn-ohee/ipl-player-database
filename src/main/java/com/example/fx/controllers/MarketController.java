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
import java.util.List;
import java.util.Optional;

public class MarketController {

    private MainApplication mainApp;
    private List<Player> marketPlayers;

    public List<Player> getMarketPlayers() {
        return marketPlayers;
    }

    public void updateTableData(List<Player> marketPlayers){
        this.marketPlayers = marketPlayers;
        marketPlayerTable.setItems(FXCollections.observableArrayList(marketPlayers));
    }

    public MainApplication getMainApp() {
        return mainApp;
    }

    public void setMainApp(MainApplication mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private TableView <Player> marketPlayerTable;

    @FXML
    private TableColumn <Player, String> playerNames;

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
    private Button buyPlayerButton;

    @FXML
    private ImageView logoImageView;

    @FXML
    private Label clubNameLabel;

    public void initialize(MainApplication mainApp, List<Player> marketPlayers) {

        playerNames.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        playerCountries.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCountryName()));
        playerAges.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getAge())));
        playerPositions.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPosition()));
        playerSalaries.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getSalary())));


        showDetailsButton.setDisable(true);
        buyPlayerButton.setDisable(true);

        marketPlayerTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            showDetailsButton.setDisable(newValue == null);
            buyPlayerButton.setDisable(newValue == null);
        });

        this.mainApp = mainApp;
        this.marketPlayers = marketPlayers;

        marketPlayerTable.setItems(FXCollections.observableArrayList(this.marketPlayers));

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(mainApp.getClubLogoByteArray());
        Image image = new Image(byteArrayInputStream);
        logoImageView.setImage(image);
        clubNameLabel.setText(mainApp.getMyClubName());

    }

    public void onBackButtonClick(ActionEvent actionEvent) throws IOException {
        mainApp.loadMainMenu();
    }

    public void onShowDetailsClick(ActionEvent actionEvent) throws IOException {
        Player selectedPlayer = marketPlayerTable.getSelectionModel().getSelectedItem();
        mainApp.loadPlayerInfo(selectedPlayer);
    }

    public void onBuyPlayerClick(ActionEvent actionEvent) throws IOException {

        Player selectedPlayer = marketPlayerTable.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Buy player");
        alert.setContentText("Are you sure you want to buy " + selectedPlayer.getName() + " ?");
        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        alertStage.getIcons().add(mainApp.getLogo());
        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK){
            mainApp.sendBuyRequest(selectedPlayer);
        }

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
