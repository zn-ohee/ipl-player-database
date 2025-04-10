package com.example.fx.database;

import com.example.fx.models.Club;
import com.example.fx.models.Country;
import com.example.fx.models.Player;

import java.util.ArrayList;
import java.util.List;

public class Database {
    
    private List <Player> playerList;
    private List <Club> clubList;
    private List <Country> countryList;

    public Database(){
        this.playerList = new ArrayList<>();
        this.clubList = new ArrayList<>();
        this.countryList = new ArrayList<>();
    }

    public List<Player> getPlayerList(){
        return playerList;
    }

    public List<Club> getClubList() {
        return clubList;
    }

    public List<Country> getCountryList() {
        return countryList;
    }

    public Club getClub(String clubName){
        for(Club club : clubList){
            if(club.getName().equalsIgnoreCase(clubName)){
                return club;
            }
        }
        return null;
    }

    public Country getCountry(String countryName){
        for(Country country : countryList){
            if(country.getName().equalsIgnoreCase(countryName)){
                return country;
            }
        }
        return null;
    }

    private void addPlayerToClub(Player player){
        for(Club club : clubList){
            if(player.getClubName().equalsIgnoreCase(club.getName())){
                club.addPlayer(player);
                return;
            }
        }
        Club newClub = new Club(player.getClubName());
        newClub.addPlayer(player);
        clubList.add(newClub);
    }

    private void addPlayerToCountry(Player player){
        for(Country country : countryList){
            if(player.getCountryName().equalsIgnoreCase(country.getName())){
                country.addPlayer(player);
                return;
            }
        }
        Country newCountry = new Country(player.getCountryName());
        newCountry.addPlayer(player);
        countryList.add(newCountry);
    }

    public void addPlayer(Player newPlayer){
        playerList.add(newPlayer);
        addPlayerToClub(newPlayer);
        addPlayerToCountry(newPlayer);
    }

    public boolean isNameAvailable(String playerName){
        for(Player player : playerList){
            if(player.getName().equalsIgnoreCase(playerName)){
                return false;
            }
        }
        return true;
    }

    public Player searchByName(String playerName){
        for(Player player : playerList){
            if(playerName.equalsIgnoreCase(player.getName())){
                return player;
            }
        }
        return null;
    }

    public List<Player> searchByCountryClub(String countryName, String clubName){
        List <Player> requiredPlayers = new ArrayList<>();
        for(Country country : countryList){
            if(countryName.equalsIgnoreCase(country.getName())){
                if(clubName.equalsIgnoreCase("ANY")){
                    requiredPlayers = country.getPlayers();
                }
                else{
                    requiredPlayers = country.getClubPlayers(clubName);
                }
                break;
            }
        }
        return requiredPlayers;
    }

    public List<Player> searchByPosition(String position){
        List <Player> requiredPlayers = new ArrayList<>();
        for(Player player : playerList){
            if(position.equalsIgnoreCase(player.getPosition())){
                requiredPlayers.add(player);
            }
        }
        return requiredPlayers;
    }

    public List<Player> searchBySalaryRange(int start, int end){
        List <Player> requiredPlayers = new ArrayList<>();
        for(Player player : playerList){
            int playerSalary = player.getSalary();
            if(playerSalary >= start && playerSalary <= end){
                requiredPlayers.add(player);
            }
        }
        return requiredPlayers;
    }

    public List<Country> CountryWiseCount(){
        return countryList;
    }

    public List<Player> MaxSalaryPlayers(String clubName){
        List <Player> requiredPlayers = new ArrayList<>();
        for(Club club : clubList){
            if(clubName.equalsIgnoreCase(club.getName())){
                requiredPlayers = club.getMaxSalaryPlayers();
                break;
            }
        }
        return requiredPlayers;
    }

    public List<Player> MaxAgePlayers(String clubName){
        List <Player> requiredPlayers = new ArrayList<>();
        for(Club club : clubList){
            if(clubName.equalsIgnoreCase(club.getName())){
                requiredPlayers = club.getMaxAgePlayers();
                break;
            }
        }
        return requiredPlayers;
    }

    public List<Player> MaxHeightPlayers(String clubName){
        List <Player> requiredPlayers = new ArrayList<>();
        for(Club club : clubList){
            if(clubName.equalsIgnoreCase(club.getName())){
                requiredPlayers = club.getMaxHeightPlayers();
                break;
            }
        }
        return requiredPlayers;
    }

    /*public long clubYearlySalary(String clubName){
        long totalYearlySalary = -1;
        for(Club club : clubList){
            if(clubName.equalsIgnoreCase(club.getName())){
                totalYearlySalary = 52 * club.getTotalSalary();
                break;
            }
        }
        return totalYearlySalary;
    }*/

    private void removePlayerFromClub(Player player){
        Club requiredClub = getClub(player.getClubName());
        requiredClub.removePlayer(player);
    }

    private void removePlayerFromCountry(Player player) {
        Country requiredCountry = getCountry(player.getCountryName());
        requiredCountry.removePlayer(player);
        if(requiredCountry.getPlayers().isEmpty()){
            countryList.remove(requiredCountry);
        }
    }

    public void removePlayer(Player player){
        playerList.remove(player);
        removePlayerFromClub(player);
        removePlayerFromCountry(player);
    }

}