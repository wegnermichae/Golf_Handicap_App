package com.example.golfhandicapapp;

public class Golfers {
    private int id;
    private String name;
    private double handicap;
    public Golfers(int id, String name, double handicap){
        this.id = id;
        this.name = name;
        this.handicap = handicap;
    }

    public int getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public double getHandicap(){
        return handicap;
    }

    public void setId(int id){
        this.id = id;
    }
    public void setName(String name){
        this.name = name;
        }
    public void setHandicap(int handicap){
        this.handicap = handicap;
    }

    @Override
    public String toString(){
        return "Name: " + name + ", Handicap: " + handicap;
    }



}
