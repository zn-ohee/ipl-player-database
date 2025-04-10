package com.example.fx.models;

import java.io.Serializable;

public class Player implements Serializable {

    private String name;
    private String countryName;
    private int age;
    private double height;
    private String clubName;
    private String position;
    private int number;
    private int salary;

    private byte[] playerImageByteArray;
    private byte[] clubLogoByteArray;
    private byte[] countryFlagByteArray;

    public Player(){}

    public Player(String name, String countryName, int age, double height, String clubName, String position,
                  int number, int salary){
        this.name = name;
        this.countryName = countryName;
        this.age = age;
        this.height = height;
        this.clubName = clubName;
        this.position = position;
        this.number = number;
        this.salary = salary;
    }

    public byte[] getPlayerImageByteArray() {
        return playerImageByteArray;
    }

    public void setPlayerImageByteArray(byte[] playerImageByteArray) {
        this.playerImageByteArray = playerImageByteArray;
    }

    public byte[] getClubLogoByteArray() {
        return clubLogoByteArray;
    }

    public void setClubLogoByteArray(byte[] clubLogoByteArray) {
        this.clubLogoByteArray = clubLogoByteArray;
    }

    public byte[] getCountryFlagByteArray() {
        return countryFlagByteArray;
    }

    public void setCountryFlagByteArray(byte[] countryFlagByteArray) {
        this.countryFlagByteArray = countryFlagByteArray;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        String numberForDisplay;
        if(this.number == -1) numberForDisplay = "N/A";
        else numberForDisplay = Integer.toString(this.number);
        return "PLAYER INFORMATION" + System.lineSeparator() + "Name: " + this.name + 
            System.lineSeparator() + "Country: " + this.countryName + System.lineSeparator() + "Age: " +
            this.age + " years" + System.lineSeparator() + "Height: " + this.height + " meters" + 
            System.lineSeparator() + "Club: " + this.clubName + System.lineSeparator() + "Position: " +
            this.position + System.lineSeparator() + "Jersey Number: " + numberForDisplay + 
            System.lineSeparator() + "Weekly Salary: " + this.salary + " Rs" + System.lineSeparator();
    }

    public String dataString(){
        String numberForFile;
        if(this.number == -1) numberForFile = ""; 
        else numberForFile = Integer.toString(this.number);
        return this.name + "," + this.countryName + "," + this.age + "," + this.height + "," +
            this.clubName + "," + this.position + "," + numberForFile + "," + this.salary;
    }

}