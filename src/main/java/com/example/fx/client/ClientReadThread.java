package com.example.fx.client;

import com.example.fx.models.Request;
import com.example.fx.utils.SocketWrapper;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class ClientReadThread implements Runnable {

    private SocketWrapper serverSocketWrapper;
    private MainApplication mainApp;
    volatile private boolean isThreadRunning = true;

    public ClientReadThread(SocketWrapper serverSocketWrapper, MainApplication mainApp) {
        this.serverSocketWrapper = serverSocketWrapper;
        this.mainApp = mainApp;
        new Thread(this).start();
    }

    @Override
    public void run() {
        try{
            while(isThreadRunning){
                Request request = (Request) serverSocketWrapper.read();
                if(request.getType() == 0){
                    Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Wrong credentials");
                        alert.setContentText("Provide correct credentials");
                        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
                        alertStage.getIcons().add(mainApp.getLogo());
                        alert.showAndWait();
                    });
                }
                else if(request.getType() == 2){
                    mainApp.loadDatabase(request.getClub(), request.getMarketPlayers());
                    mainApp.loadMainMenu();
                }
                else if(request.getType() == 5){
                    mainApp.addPlayerToMyClub(request.getPlayer());
                    Platform.runLater(() -> {
                        Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
                        infoAlert.setTitle("Information");
                        infoAlert.setHeaderText("Player bought");
                        infoAlert.setContentText(request.getPlayer().getName() + " has been added to your player list");
                        Stage alertStage = (Stage) infoAlert.getDialogPane().getScene().getWindow();
                        alertStage.getIcons().add(mainApp.getLogo());
                        infoAlert.showAndWait();
                    });
                }
                else if(request.getType() == 6){
                    mainApp.removePlayerFromMyClub(request.getPlayer());
                }
                else if(request.getType() == 7){
                    mainApp.addToMarket(request.getPlayer());
                }
                else if(request.getType() == 8){
                    mainApp.removeFromMarket(request.getPlayer());
                }
                else if(request.getType() == 9){
                    Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Player already sold");
                        alert.setContentText("This player has already been sold");
                        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
                        alertStage.getIcons().add(mainApp.getLogo());
                        alert.showAndWait();
                    });
                }
                else if(request.getType() == 12){
                    Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("You are already logged in");
                        alert.setContentText("Log out from the current session to log in here");
                        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
                        alertStage.getIcons().add(mainApp.getLogo());
                        alert.showAndWait();
                    });
                }
                else if(request.getType() == 14){
                    Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Wrong current password");
                        alert.setContentText("Provided current password is incorrect");
                        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
                        alertStage.getIcons().add(mainApp.getLogo());
                        alert.showAndWait();
                    });
                }
                else if(request.getType() == 15){
                    Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information");
                        alert.setHeaderText("Password changed successfully");
                        alert.setContentText("Your password has been changed successfully");
                        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
                        alertStage.getIcons().add(mainApp.getLogo());
                        alert.showAndWait();
                    });
                    mainApp.clearChangePassInputs();
                }
            }
        }
        catch (Exception e){
            if(isThreadRunning){
                e.printStackTrace();
            }
            else{
                System.out.println("Connection closed");
            }
        }
        finally {
            try {
                serverSocketWrapper.closeConnection();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void stopThread(){
        isThreadRunning = false;
        try{
            serverSocketWrapper.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
