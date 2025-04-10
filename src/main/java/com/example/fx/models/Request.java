package com.example.fx.models;

import java.io.Serializable;
import java.util.List;

public class Request implements Serializable {

    /*
    type  0: invalid login
    type  1: login request
    type  2: club object
    type  3: sell request
    type  4: buy request
    type  5: add player
    type  6: remove player
    type  7: add to market
    type  8: remove from market
    type  9: player already sold
    type 10: log out
    type 11: exit
    type 12: already logged in
    type 13: change pass
    type 14: wrong current pass
    type 15: pass changed
     */

    private int type;
    private String username;
    private String password;
    private Club club;
    private Player player;
    private String senderClubName;
    private List<Player> marketPlayers;
    private String currentPass;
    private String newPass;

    public Request(){}

    public Request(int type){
        this.type = type;
    }

    public Request(int type, String senderClubName){
        this.type = type;
        this.senderClubName = senderClubName;
    }

    public Request(int type, Player player){
        this.type = type;
        this.player = player;
    }

    public Request(int type, String username, String password){
        this.type = type;
        this.username = username;
        this.password = password;
    }

    public Request(int type, Player player, String senderClubName){
        this.type = type;
        this.player = player;
        this.senderClubName = senderClubName;
    }

    public Request(int type, Club club, List<Player> marketPlayers){
        this.type = type;
        this.club = club;
        this.marketPlayers = marketPlayers;
    }

    public Request(int type, String senderClubName, String currentPass, String newPass){
        this.type = type;
        this.senderClubName = senderClubName;
        this.currentPass = currentPass;
        this.newPass = newPass;
    }

    public int getType() {
        return type;
    }

    public String getCurrentPass() {
        return currentPass;
    }

    public void setCurrentPass(String currentPass) {
        this.currentPass = currentPass;
    }

    public String getNewPass() {
        return newPass;
    }

    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }

    public List<Player> getMarketPlayers() {
        return marketPlayers;
    }

    public void setMarketPlayers(List<Player> marketPlayers) {
        this.marketPlayers = marketPlayers;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public String getSenderClubName() {
        return senderClubName;
    }

    public void setSenderClubName(String senderClubName) {
        this.senderClubName = senderClubName;
    }

}
