package com.example.fx.controllers;

import com.example.fx.models.Club;
import com.example.fx.client.MainApplication;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.IOException;

public class TotalYearlySalaryMenuController{

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
    private TableView <Club> clubTable;

    @FXML
    private TableColumn <Club, String> clubNames;

    @FXML
    private TableColumn <Club, String> clubSalaries;

    public void initialize(MainApplication mainApp) {

        clubNames.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        clubSalaries.setCellValueFactory(cellData -> new SimpleStringProperty(String.format("%.0f", 52.0 * cellData.getValue().getTotalSalary())));

        this.mainApp = mainApp;

        clubTable.setItems(FXCollections.observableArrayList(mainApp.getDatabase().getClubList()));

    }

    @FXML
    private TextField inputClubName;

    public void onSearchButtonClick(ActionEvent actionEvent) {
        String clubName = inputClubName.getText();
        if(clubName.isEmpty()){
            clubTable.setItems(FXCollections.observableArrayList(mainApp.getDatabase().getClubList()));
        }
        else{
            Club club = mainApp.getDatabase().getClub(clubName);
            if(club != null) clubTable.setItems(FXCollections.observableArrayList(club));
            else clubTable.setItems(FXCollections.emptyObservableList());
        }
    }

}
