package com.example.golfhandicapapp;

public class Clubs {

    private String name;
    private int distance;

    public Clubs(String name, int distance){
        this.name = name;
        this.distance = distance;
    }

    public String getName(){
        return name;
    }

    public int getDistance(){
        return distance;
    }

}
