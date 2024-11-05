package com.example.golfhandicapapp;

public class Courses {
    public String name;
    public int par;
    public int slopeRating;
    public double handicap;

    public Courses(String name, int par, int slopeRating, double handicap) {
        this.name = name;
        this.par = par;
        this.slopeRating = slopeRating;
        this.handicap = handicap;

    }

    public String getName(){
        return name;
    }
    public int getPar(){
        return par;
    }

    public int getSlopeRating(){
        return slopeRating;
    }

    public double getHandicap(){
        return handicap;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setPar(int par){
        this.par = par;
    }

    public void setSlopeRating(int slopeRating){
        this.slopeRating = slopeRating;
    }

    public void setHandicap(float handicap){
        this.handicap = handicap;
    }

    @Override
    public String toString(){
        return "Name: " + name + " Par: " + par + " Slope Rating: " + slopeRating + " Handicap: " + handicap;
    }

}
