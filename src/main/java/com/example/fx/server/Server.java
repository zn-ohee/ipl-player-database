package com.example.fx.server;

import com.example.fx.models.Country;
import com.example.fx.utils.SocketWrapper;
import com.example.fx.database.Database;
import com.example.fx.models.Club;
import com.example.fx.models.Player;
import com.example.fx.models.Request;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Server {

    private final String PLAYER_DATA_FILE = "src/main/resources/com/example/fx/data/players.txt";
    private final String PASSWORD_DATA_FILE = "src/main/resources/com/example/fx/data/passwords.txt";
    private final String MARKET_DATA_FILE = "src/main/resources/com/example/fx/data/market.txt";

    private int port = 33333;
    private ServerSocket serverSocket;
    volatile private boolean mainThreadActive = true;

    private Database database;
    private HashMap <String, String> passwordMap;
    private HashMap <String, SocketWrapper> clientMap;
    private List <Player> marketPlayers;
    private HashMap <String, byte[]> playerImageMap;
    private HashMap <String, byte[]> clubLogoMap;
    private HashMap <String, byte[]> flagMap;

    public Server() {

        database = new Database();
        passwordMap = new HashMap<>();
        clientMap = new HashMap<>();
        marketPlayers = new ArrayList<>();
        playerImageMap = new HashMap<>();
        clubLogoMap = new HashMap<>();
        flagMap = new HashMap<>();

        try{

            BufferedReader br1 = new BufferedReader(new FileReader(PLAYER_DATA_FILE));

            while (true) {

                String line = br1.readLine();

                if (line == null) break;

                String[] playerInfo = line.split(",");

                String name = playerInfo[0];
                String country = playerInfo[1];
                int age = Integer.parseInt(playerInfo[2]);
                double height = Double.parseDouble(playerInfo[3]);
                String club = playerInfo[4];
                String position = playerInfo[5];
                int number = 0;
                if(playerInfo[6].isEmpty()) number = -1;
                else number = Integer.parseInt(playerInfo[6]);
                int salary = Integer.parseInt(playerInfo[7]);

                Player player = new Player(name, country, age, height, club, position, number, salary);
                database.addPlayer(player);

            }

            br1.close();
            System.out.println("Player data loaded");

        }
        catch (Exception e){
            e.printStackTrace();
        }

        try{

            BufferedReader br2 = new BufferedReader(new FileReader(PASSWORD_DATA_FILE));

            while(true){

                String line = br2.readLine();

                if (line == null) break;

                String[] clubLoginInfo = line.split(",");

                String clubName = clubLoginInfo[0];
                String password = clubLoginInfo[1];

                passwordMap.put(clubName, password);

            }

            br2.close();
            System.out.println("Password data loaded");

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        try{

            BufferedReader br3 = new BufferedReader(new FileReader(MARKET_DATA_FILE));

            while (true){

                String line = br3.readLine();

                if (line == null) break;

                String[] playerInfo = line.split(",");

                String name = playerInfo[0];
                String country = playerInfo[1];
                int age = Integer.parseInt(playerInfo[2]);
                double height = Double.parseDouble(playerInfo[3]);
                String club = playerInfo[4];
                String position = playerInfo[5];
                int number = 0;
                if(playerInfo[6].isEmpty()) number = -1;
                else number = Integer.parseInt(playerInfo[6]);
                int salary = Integer.parseInt(playerInfo[7]);

                Player player = new Player(name, country, age, height, club, position, number, salary);
                marketPlayers.add(player);

            }

            br3.close();
            System.out.println("Market data loaded");

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        try {
            for (Player player : database.getPlayerList()) {
                String playerImagePath = "src/main/resources/com/example/fx/images/playerImages/" + player.getName() + ".png";
                File playerImageFile = new File(playerImagePath);
                byte[] imageByteArray;
                if (playerImageFile.exists() && !playerImageFile.isDirectory()) {
                    imageByteArray = Files.readAllBytes(playerImageFile.toPath());
                }
                else{
                    String defaultImagePath = "src/main/resources/com/example/fx/images/avatar.png";
                    File defaultImageFile = new File(defaultImagePath);
                    imageByteArray = Files.readAllBytes(defaultImageFile.toPath());
                }
                player.setPlayerImageByteArray(imageByteArray);
                playerImageMap.put(player.getName(), imageByteArray);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        try {
            for (Player player : database.getPlayerList()) {
                String clubLogoImagePath = "src/main/resources/com/example/fx/images/clubLogos/" + player.getClubName() + ".png";
                File logoImageFile = new File(clubLogoImagePath);
                byte[] imageByteArray;
                if (logoImageFile.exists() && !logoImageFile.isDirectory()) {
                    imageByteArray = Files.readAllBytes(logoImageFile.toPath());
                }
                else{
                    String defaultImagePath = "src/main/resources/com/example/fx/images/clubLogos/genericLogo.png";
                    File defaultImageFile = new File(defaultImagePath);
                    imageByteArray = Files.readAllBytes(defaultImageFile.toPath());
                }
                player.setClubLogoByteArray(imageByteArray);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        try {
            for (Player player : database.getPlayerList()) {
                String flagImagePath = "src/main/resources/com/example/fx/images/countryFlags/" + player.getCountryName() + ".png";
                File flagImageFile = new File(flagImagePath);
                byte[] imageByteArray;
                if (flagImageFile.exists() && !flagImageFile.isDirectory()) {
                    imageByteArray = Files.readAllBytes(flagImageFile.toPath());
                }
                else{
                    String defaultImagePath = "src/main/resources/com/example/fx/images/countryFlags/genericCountry.png";
                    File defaultImageFile = new File(defaultImagePath);
                    imageByteArray = Files.readAllBytes(defaultImageFile.toPath());
                }
                player.setCountryFlagByteArray(imageByteArray);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        try {
            for (Club club : database.getClubList()) {
                String logoImagePath = "src/main/resources/com/example/fx/images/clubLogos/" + club.getName() + ".png";
                File logoImageFile = new File(logoImagePath);
                byte[] imageByteArray;
                if (logoImageFile.exists() && !logoImageFile.isDirectory()) {
                    imageByteArray = Files.readAllBytes(logoImageFile.toPath());
                }
                else{
                    String defaultImagePath = "src/main/resources/com/example/fx/images/clubLogos/genericLogo.png";
                    File defaultImageFile = new File(defaultImagePath);
                    imageByteArray = Files.readAllBytes(defaultImageFile.toPath());
                }
                club.setLogoByteArray(imageByteArray);
                clubLogoMap.put(club.getName(), imageByteArray);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        try {
            for (Country country : database.getCountryList()) {
                String flagImagePath = "src/main/resources/com/example/fx/images/countryFlags/" + country.getName() + ".png";
                File flagImageFile = new File(flagImagePath);
                byte[] imageByteArray;
                if (flagImageFile.exists() && !flagImageFile.isDirectory()) {
                    imageByteArray = Files.readAllBytes(flagImageFile.toPath());
                }
                else{
                    String defaultImagePath = "src/main/resources/com/example/fx/images/countryFlags/genericCountry.png";
                    File defaultImageFile = new File(defaultImagePath);
                    imageByteArray = Files.readAllBytes(defaultImageFile.toPath());
                }
                country.setFlagByteArray(imageByteArray);
                flagMap.put(country.getName(), imageByteArray);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    synchronized public void handleLoginRequest(Request loginRequest, SocketWrapper clientSocketWrapper) throws IOException {
        for(String key : passwordMap.keySet()){
            if(key.trim().equalsIgnoreCase(loginRequest.getUsername().trim()) && passwordMap.get(key).equals(loginRequest.getPassword())){
                for(String key2 : clientMap.keySet()){
                    if(key2.trim().equalsIgnoreCase(key.trim())){
                        clientSocketWrapper.write(new Request(12));
                        return;
                    }
                }
                for(Club club : database.getClubList()){
                    if(key.equalsIgnoreCase(club.getName())){
                        List <Player> requiredMarket = new ArrayList<>();
                        for(Player marketPlayer : marketPlayers){
                            if(!key.equalsIgnoreCase(marketPlayer.getClubName())){
                                requiredMarket.add(marketPlayer);
                            }
                        }
                        clientSocketWrapper.write(new Request(2, club, requiredMarket));
                        clientMap.put(club.getName(), clientSocketWrapper);
                        return;
                    }
                }
            }
        }
        clientSocketWrapper.write(new Request(0));
    }

    synchronized public void handleSellRequest(Request request) throws IOException {
        for(Player marketPlayer : marketPlayers){
            if(marketPlayer.getName().equalsIgnoreCase(request.getPlayer().getName())){
                return;
            }
        }
        marketPlayers.add(request.getPlayer());
        for(String clientName : clientMap.keySet()){
            if(!clientName.equalsIgnoreCase(request.getSenderClubName())){
                clientMap.get(clientName).write(new Request(7, request.getPlayer()));
            }
        }
    }

    synchronized public void handleBuyRequest(Request request) throws IOException {
        String prevClubName = request.getPlayer().getClubName();
        String newClubName = request.getSenderClubName();
        Player requiredPlayer = null;
        for(Player marketPlayer : marketPlayers){
            if(marketPlayer.getName().equalsIgnoreCase(request.getPlayer().getName())){
                requiredPlayer = marketPlayer;
                break;
            }
        }
        if(requiredPlayer == null){
            clientMap.get(newClubName).write(new Request(9));
        }
        else{
            marketPlayers.remove(requiredPlayer);
            for(String clientName : clientMap.keySet()){
                if(!clientName.equalsIgnoreCase(prevClubName)){
                    clientMap.get(clientName).write(new Request(8, requiredPlayer));
                }
            }
            if(clientMap.containsKey(prevClubName)){
                clientMap.get(prevClubName).write(new Request(6, requiredPlayer));
            }
            for(Player player : database.getPlayerList()){
                if(player.getName().equalsIgnoreCase(requiredPlayer.getName())){
                    requiredPlayer = player;
                    break;
                }
            }
            database.removePlayer(requiredPlayer);
            requiredPlayer.setClubName(newClubName);
            requiredPlayer.setClubLogoByteArray(clubLogoMap.get(newClubName));
            database.addPlayer(requiredPlayer);
            clientMap.get(newClubName).write(new Request(5, requiredPlayer));
        }
    }

    synchronized public void detachClient(String senderClubName) {
        clientMap.remove(senderClubName);
    }

    synchronized public void handleChangePassRequest(Request request, SocketWrapper clientSocketWrapper) throws IOException {
        String senderClubName = request.getSenderClubName();
        for(String key : passwordMap.keySet()){
            if(key.trim().equalsIgnoreCase(senderClubName.trim())){
                if(!passwordMap.get(key).equals(request.getCurrentPass())){
                    clientSocketWrapper.write(new Request(14));
                }
                else{
                    passwordMap.put(key, request.getNewPass());
                    clientSocketWrapper.write(new Request(15));
                }
                break;
            }
        }
    }

    public void serve(){
        try{
            serverSocket = new ServerSocket(port);
            System.out.println("Server is running");
            while(mainThreadActive){
                try{
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("New client connected");
                    SocketWrapper clientSocketWrapper = new SocketWrapper(clientSocket);
                    new ServerReadThread(clientSocketWrapper, this);
                }
                catch (Exception e){
                    if(mainThreadActive){
                        e.printStackTrace();
                    }
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            stopServer();
        }

    }

    public void stopServer(){
        mainThreadActive = false;
        try {
            serverSocket.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void closeServer(){

        try{
            BufferedWriter bw1 = new BufferedWriter(new FileWriter(PLAYER_DATA_FILE));
            for(Player player : database.getPlayerList()){
                bw1.write(player.dataString());
                bw1.write(System.lineSeparator());
            }
            bw1.close();
            System.out.println("Player data saved");
        }
        catch (Exception e){
            e.printStackTrace();
        }

        try{
            BufferedWriter bw2 = new BufferedWriter(new FileWriter(PASSWORD_DATA_FILE));
            for(String key : passwordMap.keySet()){
                bw2.write(key + "," + passwordMap.get(key));
                bw2.write(System.lineSeparator());
            }
            bw2.close();
            System.out.println("Password data saved");
        }
        catch (Exception e){
            e.printStackTrace();
        }

        try{
            BufferedWriter bw3 = new BufferedWriter(new FileWriter(MARKET_DATA_FILE));
            for(Player player : marketPlayers){
                bw3.write(player.dataString());
                bw3.write(System.lineSeparator());
            }
            bw3.close();
            System.out.println("Market data saved");
        }
        catch (Exception e){
            e.printStackTrace();
        }

        stopServer();

    }

    public static void main(String[] args){
        Server server = new Server();
        new ServerManageThread(server, Thread.currentThread());
        server.serve();
        System.out.println("Server stopped");
    }

}
