package com.example.fx.controllers;

import com.example.fx.client.MainApplication;
import com.example.fx.models.Player;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.ByteArrayInputStream;

public class PlayerViewController {

    public Label nameLabel;
    public Label countryLabel;
    public Label ageLabel;
    public Label heightLabel;
    public Label clubLabel;
    public Label positionLabel;
    public Label numberLabel;
    public Label salaryLabel;

    private MainApplication mainApp;

    public MainApplication getMainApp() {
        return mainApp;
    }

    public void setMainApp(MainApplication mainApp) {
        this.mainApp = mainApp;
    }

    public void setLabels(Player player) {
        nameLabel.setText(nameLabel.getText() + player.getName());
        countryLabel.setText(countryLabel.getText() + player.getCountryName());
        ageLabel.setText(ageLabel.getText() + player.getAge() + " years");
        heightLabel.setText(heightLabel.getText() + player.getHeight() + " m");
        clubLabel.setText(clubLabel.getText() + player.getClubName());
        positionLabel.setText(positionLabel.getText() + player.getPosition());
        if(player.getNumber() >= 0){
            numberLabel.setText(numberLabel.getText() + player.getNumber());
        }
        else{
            numberLabel.setText(numberLabel.getText() + "N/A");
        }

        salaryLabel.setText(salaryLabel.getText() + player.getSalary() + " Rs");
    }

    @FXML
    private ImageView playerImageView;

    @FXML
    private ImageView logoImageView;

    @FXML
    private ImageView flagImageView;

    public void setPlayerImages(Player player) {

        ByteArrayInputStream byteArrayInputStream;
        Image image;

        byteArrayInputStream = new ByteArrayInputStream(player.getPlayerImageByteArray());
        image = new Image(byteArrayInputStream);
        playerImageView.setImage(image);

        byteArrayInputStream = new ByteArrayInputStream(player.getClubLogoByteArray());
        image = new Image(byteArrayInputStream);
        logoImageView.setImage(image);

        byteArrayInputStream = new ByteArrayInputStream(player.getCountryFlagByteArray());
        image = new Image(byteArrayInputStream);
        flagImageView.setImage(image);

    }


}
