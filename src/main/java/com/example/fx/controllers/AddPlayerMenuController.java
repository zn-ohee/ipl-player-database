package com.example.fx.controllers;

import com.example.fx.client.MainApplication;
import com.example.fx.models.Player;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

public class AddPlayerMenuController {

    public TextField inputName;
    public TextField inputCountry;
    public TextField inputAge;
    public TextField inputHeight;
    public TextField inputClub;
    public TextField inputPosition;
    public TextField inputNumber;
    public TextField inputSalary;

    public Button addPlayerButton;
    private MainApplication mainApp;

    public MainApplication getMainApp() {
        return mainApp;
    }

    public void setMainApp(MainApplication mainApp) {
        this.mainApp = mainApp;
    }

    public void onBackButtonClick(ActionEvent actionEvent) throws IOException {
        mainApp.loadMainMenu();
    }

    public void onAddPlayerClick(ActionEvent actionEvent) {

        try{
            if(!mainApp.getDatabase().isNameAvailable(inputName.getText())) {
                Alert duplicateAlert = new Alert(Alert.AlertType.ERROR);
                duplicateAlert.setTitle("Error");
                duplicateAlert.setHeaderText("Duplicate Name for New Player");
                duplicateAlert.setContentText("Enter a valid name for new player");
                duplicateAlert.showAndWait();
            }

            else {

                String playerName = inputName.getText();
                if(playerName.isEmpty()) throw new Exception();

                String country = inputCountry.getText();
                if(country.isEmpty()) throw new Exception();

                if(inputAge.getText().isEmpty()) throw new Exception();
                int age = Integer.parseInt(inputAge.getText());

                if(inputHeight.getText().isEmpty()) throw new Exception();
                double height = Double.parseDouble(inputHeight.getText());

                String club = inputClub.getText();
                if(club.isEmpty()) throw new Exception();

                String position = inputPosition.getText();
                if(position.isEmpty()) throw new Exception();

                int number;
                String givenNumber = inputNumber.getText();
                if(givenNumber.isEmpty()) throw new Exception();

                if(givenNumber.equalsIgnoreCase("N/A")) {
                    number = -1;
                }
                else{
                    number = Integer.parseInt(givenNumber);
                }

                if(inputSalary.getText().isEmpty()) throw new Exception();
                int salary = Integer.parseInt(inputSalary.getText());

                Player newPlayer = new Player(playerName, country, age, height, club, position, number, salary);
                mainApp.getDatabase().addPlayer(newPlayer);

                Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
                infoAlert.setTitle("Information");
                infoAlert.setHeaderText("Player Added");
                infoAlert.setContentText("Player Added Successfully");
                infoAlert.showAndWait();

                inputAge.clear();
                inputCountry.clear();
                inputName.clear();
                inputHeight.clear();
                inputClub.clear();
                inputPosition.clear();
                inputNumber.clear();
                inputSalary.clear();

            }
        }
        catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid input");
            alert.setContentText("Provide valid input");
            alert.showAndWait();
        }

    }
}
