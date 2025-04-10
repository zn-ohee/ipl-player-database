package com.example.fx.client;

import com.example.fx.controllers.*;
import com.example.fx.database.Database;
import com.example.fx.models.Club;
import com.example.fx.models.Player;
import com.example.fx.models.Request;
import com.example.fx.utils.SocketWrapper;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainApplication extends Application{

    private int SCREEN_WIDTH = 1300;
    private int SCREEN_HEIGHT = 800;
    private Stage primaryStage;
    private Image logo;
    private Image avatar;

    private Database database;
    private List <Player> marketPlayers;
    private String myClubName;
    private byte[] clubLogoByteArray;

    private String hostIP = "127.0.0.1";
    private int port = 33333;
    private SocketWrapper serverSocketWrapper;
    private ClientReadThread clientReadThread;

    private MarketController marketController;
    private SellPlayerMenuController sellPlayerMenuController;
    private ChangePassMenuController changePassMenuController;

    @Override
    public void init() throws Exception {
        super.init();
        database = new Database();
        marketPlayers = new ArrayList<>();
        serverSocketWrapper = new SocketWrapper(hostIP, port);
        System.out.println("Connected to server");
        clientReadThread = new ClientReadThread(serverSocketWrapper, this);
    }

    @Override
    public void start(Stage stage) throws IOException {

        this.primaryStage = stage;
        this.primaryStage.setTitle("IPL PLAYER DATABASE");

        String imagePath = "/com/example/fx/images/logo.png";
        URL resource = getClass().getResource(imagePath);

        if(resource != null){
            logo = new Image(resource.toExternalForm());
            this.primaryStage.getIcons().add(logo);
        }

        imagePath = "/com/example/fx/images/avatar.png";
        resource = getClass().getResource(imagePath);

        if(resource != null){
            avatar = new Image(resource.toExternalForm());
        }

        this.loadLoginPage();

    }

    public Database getDatabase() {
        return database;
    }

    public String getMyClubName() {
        return myClubName;
    }

    public Image getLogo() {
        return logo;
    }

    public byte[] getClubLogoByteArray() {
        return clubLogoByteArray;
    }

    public void sendLoginRequest(String clubName, String password) throws IOException{
        Request loginRequest = new Request(1, clubName, password);
        serverSocketWrapper.write(loginRequest);
    }

    public void loadDatabase(Club club, List <Player> marketPlayers){
        myClubName = club.getName();
        this.marketPlayers = marketPlayers;
        for(Player player : club.getPlayers()){
            database.addPlayer(player);
        }
        this.clubLogoByteArray = club.getLogoByteArray();
    }

    public void loadLoginPage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/com/example/fx/fxmls/LoginPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), SCREEN_WIDTH, SCREEN_HEIGHT);
        LoginPageController controller = fxmlLoader.getController();
        controller.initialize(this, logo, avatar);
        this.primaryStage.setScene(scene);
        this.primaryStage.setResizable(false);
        this.primaryStage.show();
    }

    public void loadMainMenu() throws IOException {
        Platform.runLater(() -> {
            try{
                FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/com/example/fx/fxmls/MainMenuView.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), SCREEN_WIDTH, SCREEN_HEIGHT);
                MainMenuController controller = fxmlLoader.getController();
                controller.initialize(this, myClubName);
                this.primaryStage.setTitle(myClubName.toUpperCase());
                this.primaryStage.setScene(scene);
                this.primaryStage.show();
            }
            catch (IOException e){
                e.printStackTrace();
            }
        });
    }

    public void loadSearchPlayerMenu() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/com/example/fx/fxmls/SearchPlayerMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), SCREEN_WIDTH, SCREEN_HEIGHT);
        SearchPlayerMenuController controller = fxmlLoader.getController();
        controller.initialize(this);
        this.primaryStage.setScene(scene);
        this.primaryStage.show();
    }

    public void loadSearchClubMenu() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/com/example/fx/fxmls/SearchClubMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), SCREEN_WIDTH, SCREEN_HEIGHT);
        SearchClubMenuController controller = fxmlLoader.getController();
        controller.initialize(this);
        this.primaryStage.setScene(scene);
        this.primaryStage.show();
    }

    /*public void loadAddPlayerMenu() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("AddPlayerMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1300, 800);
        AddPlayerMenuController controller = fxmlLoader.getController();
        controller.setMainApp(this);
        this.primaryStage.setScene(scene);
        this.primaryStage.show();
    }*/

    public void loadSearchByNameMenu() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/com/example/fx/fxmls/SearchByNameMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), SCREEN_WIDTH, SCREEN_HEIGHT);
        SearchByNameMenuController controller = fxmlLoader.getController();
        controller.initialize(this);
        this.primaryStage.setScene(scene);
        this.primaryStage.show();
    }

    public void loadSearchByPositionMenu() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/com/example/fx/fxmls/SearchByPositionMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), SCREEN_WIDTH, SCREEN_HEIGHT);
        SearchByPositionMenuController controller = fxmlLoader.getController();
        controller.initialize(this);
        this.primaryStage.setScene(scene);
        this.primaryStage.show();
    }

    public void loadPlayerInfo(Player player) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/com/example/fx/fxmls/PlayerView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        PlayerViewController controller = fxmlLoader.getController();

        controller.setMainApp(this);
        controller.setLabels(player);

        controller.setPlayerImages(player);

        Stage playerViewStage = new Stage();
        playerViewStage.setTitle("Player Information");

        playerViewStage.getIcons().add(logo);

        playerViewStage.setScene(scene);
        playerViewStage.setResizable(false);

        playerViewStage.show();

    }

    public void loadSearchByCNCMenu() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/com/example/fx/fxmls/SearchByCNCMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), SCREEN_WIDTH, SCREEN_HEIGHT);
        SearchByCNCMenuController controller = fxmlLoader.getController();
        controller.initialize(this);
        this.primaryStage.setScene(scene);
        this.primaryStage.show();
    }

    public void loadSearchBySalaryMenu() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/com/example/fx/fxmls/SearchBySalaryMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), SCREEN_WIDTH, SCREEN_HEIGHT);
        SearchBySalaryMenuController controller = fxmlLoader.getController();
        controller.initialize(this);
        this.primaryStage.setScene(scene);
        this.primaryStage.show();
    }

    public void loadCountryWiseCountView() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/com/example/fx/fxmls/CountryWiseCountView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), SCREEN_WIDTH, SCREEN_HEIGHT);
        CountryWiseCountViewController controller = fxmlLoader.getController();
        controller.initialize(this);
        this.primaryStage.setScene(scene);
        this.primaryStage.show();
    }

    public void loadMaxSalaryPlayersMenu() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/com/example/fx/fxmls/MaxSalaryPlayersMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), SCREEN_WIDTH, SCREEN_HEIGHT);
        MaxSalaryPlayersMenuController controller = fxmlLoader.getController();
        controller.initialize(this);
        this.primaryStage.setScene(scene);
        this.primaryStage.show();
    }

    public void loadMaxAgePlayersMenu() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/com/example/fx/fxmls/MaxAgePlayersMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), SCREEN_WIDTH, SCREEN_HEIGHT);
        MaxAgePlayersMenuController controller = fxmlLoader.getController();
        controller.initialize(this);
        this.primaryStage.setScene(scene);
        this.primaryStage.show();
    }

    public void loadMaxHeightPlayersMenu() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/com/example/fx/fxmls/MaxHeightPlayersMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), SCREEN_WIDTH, SCREEN_HEIGHT);
        MaxHeightPlayersMenuController controller = fxmlLoader.getController();
        controller.initialize(this);
        this.primaryStage.setScene(scene);
        this.primaryStage.show();
    }

/*    public void loadTotalYearlySalaryMenu() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/com/example/fx/TotalYearlySalaryMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1300, 800);
        TotalYearlySalaryMenuController controller = fxmlLoader.getController();
        controller.initialize(this);
        this.primaryStage.setScene(scene);
        this.primaryStage.show();
    }*/

    public void showTotalYearlySalary() {

        String totalYearlySalary = "0";

        if(database.getClub(myClubName) != null) {
            totalYearlySalary = String.format("%.0f", 52.0 * database.getClub(myClubName).getTotalSalary());
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Total Yearly Salary");
        alert.setHeaderText("Total yearly salary of all players");
        alert.setContentText("Total yearly salary of all players is " + totalYearlySalary + " Rs");

        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        alertStage.getIcons().add(logo);

        alert.showAndWait();

    }

    public void loadChangePassMenu() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/com/example/fx/fxmls/ChangePassMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), SCREEN_WIDTH, SCREEN_HEIGHT);
        changePassMenuController = fxmlLoader.getController();
        changePassMenuController.initialize(this);
        this.primaryStage.setScene(scene);
        this.primaryStage.show();
    }

    public void sendChangePassRequest(String currentPass, String newPass) throws IOException {
        Request request = new Request(13, myClubName, currentPass, newPass);
        serverSocketWrapper.write(request);
    }

    public void clearChangePassInputs() {
        if(changePassMenuController != null) {
            changePassMenuController.clearInputs();
        }
    }

    public void loadMarket() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/com/example/fx/fxmls/Market.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), SCREEN_WIDTH, SCREEN_HEIGHT);
        marketController = fxmlLoader.getController();
        MarketController controller = fxmlLoader.getController();
        controller.initialize(this, marketPlayers);
        this.primaryStage.setScene(scene);
        this.primaryStage.show();
    }

    public void loadSellPlayerMenu() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/com/example/fx/fxmls/SellPlayerMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), SCREEN_WIDTH, SCREEN_HEIGHT);
        sellPlayerMenuController = fxmlLoader.getController();
        sellPlayerMenuController.initialize(this);
        this.primaryStage.setScene(scene);
        this.primaryStage.show();
    }

    public void sendSellRequest(Player player) throws IOException {
        Request request = new Request(3, player, myClubName);
        serverSocketWrapper.write(request);
    }

    public void addToMarket(Player player) throws IOException {
        marketPlayers.add(player);
        if(marketController != null) {
            Platform.runLater(() -> {
                marketController.updateTableData(marketPlayers);
            });
        }
    }

    public void sendBuyRequest(Player selectedPlayer) throws IOException {
        Request request = new Request(4, selectedPlayer, myClubName);
        serverSocketWrapper.write(request);
    }

    public void removeFromMarket(Player player) {
        Player requiredPlayer = null;
        for(Player marketPlayer : marketPlayers) {
            if(marketPlayer.getName().equalsIgnoreCase(player.getName())) {
                requiredPlayer = marketPlayer;
                break;
            }
        }
        if(requiredPlayer != null) {
            marketPlayers.remove(requiredPlayer);
        }
        if(marketController != null) {
            Platform.runLater(() -> {
                marketController.updateTableData(marketPlayers);
            });
        }
    }

    public void addPlayerToMyClub(Player player) {
        database.addPlayer(player);
        if(sellPlayerMenuController != null) {
            Platform.runLater(() -> {
               sellPlayerMenuController.updateTableData(database.getPlayerList());
            });
        }
    }

    public void removePlayerFromMyClub(Player requestPlayer) {
        Player requiredPlayer = null;
        for(Player player : database.getPlayerList()){
            if(player.getName().equalsIgnoreCase(requestPlayer.getName())){
                requiredPlayer = player;
                break;
            }
        }
        if(requiredPlayer != null) {
            database.removePlayer(requiredPlayer);
        }
        if(sellPlayerMenuController != null) {
            Platform.runLater(() -> {
                sellPlayerMenuController.updateTableData(database.getPlayerList());
            });
        }
    }

    public void closeProgram() throws IOException {
        if(myClubName != null) {
            serverSocketWrapper.write(new Request(10, myClubName));
        }
        else{
            serverSocketWrapper.write(new Request(11));
        }
        clientReadThread.stopThread();
        Platform.exit();
    }

    public static void main(String[] args) {
        launch(args);
    }

}