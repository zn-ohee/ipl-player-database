package com.example.fx.models;

import java.util.List;
import java.util.ArrayList;

public class Country{
    
    private String name;
    private List <Player> players;
    private int maxSalary;
    private int maxAge;
    private double maxHeight;
    private long totalWeeklySalary;

    private byte[] flagByteArray;

    public Country(){
        this.players = new ArrayList<>();
    }
    
    public Country(String name) {
        this.name = name;
        this.players = new ArrayList<>();
    }

    public byte[] getFlagByteArray() {
        return flagByteArray;
    }

    public void setFlagByteArray(byte[] flagByteArray) {
        this.flagByteArray = flagByteArray;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public int getMaxSalary(){
        return maxSalary;
    }

    public void addPlayer(Player player){
        players.add(player);
        if(player.getSalary() > maxSalary){
            maxSalary = player.getSalary();
        }
        if(player.getAge() > maxAge){
            maxAge = player.getAge();
        }
        if(player.getHeight() > maxHeight){
            maxHeight = player.getHeight();
        }
        totalWeeklySalary += player.getSalary();
    }

    public void removePlayer(Player player) {
        players.remove(player);
        if(player.getSalary() == maxSalary){
            maxSalary = -1;
            for(Player myPlayer : players){
                if(myPlayer.getSalary() > maxSalary){
                    maxSalary = myPlayer.getSalary();
                }
            }
        }
        if(player.getAge() == maxAge){
            maxAge = -1;
            for(Player myPlayer : players){
                if(myPlayer.getAge() > maxAge){
                    maxAge = myPlayer.getAge();
                }
            }
        }
        if(player.getHeight() == maxHeight){
            maxHeight = -1;
            for(Player myPlayer : players){
                if(myPlayer.getHeight() > maxHeight){
                    maxHeight = myPlayer.getHeight();
                }
            }
        }
        totalWeeklySalary -= player.getSalary();
    }

    public int getPlayerCount(){
        return players.size();
    }

    public List<Player> getMaxSalaryPlayers(){
        List <Player> requiredPlayers = new ArrayList<>();
        for(Player player : players){
            if(maxSalary == player.getSalary()){
                requiredPlayers.add(player);
            }
        }
        return requiredPlayers;
    }

    public List<Player> getMaxAgePlayers(){
        List <Player> requiredPlayers = new ArrayList<>();
        for(Player player : players){
            if(maxAge == player.getAge()){
                requiredPlayers.add(player);
            }
        }
        return requiredPlayers;
    }

    public List<Player> getMaxHeightPlayers(){
        List <Player> requiredPlayers = new ArrayList<>();
        for(Player player : players){
            if(maxHeight == player.getHeight()){
                requiredPlayers.add(player);
            }
        }
        return requiredPlayers;
    }

    public long getTotalSalary(){
        return totalWeeklySalary;
    }

    public List<Player> getClubPlayers(String clubName){
        List <Player> requiredPlayers = new ArrayList<>();
        for(Player player : players){
            if(clubName.equalsIgnoreCase(player.getClubName())){
                requiredPlayers.add(player);
            }
        }
        return requiredPlayers;
    }

}