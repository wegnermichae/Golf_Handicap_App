package com.example.golfhandicapapp;

public class Clubs {

    private int id;
    private String name;
    private int distance;

    public Clubs(int id, String name, int distance){
        this.id = id;
        this.name = name;
        this.distance = distance;
    }

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public int getDistance(){
        return distance;
    }

    public String toString(){
        return "Name: " + name + ", Distance: " + distance + " yds";
    }

}
