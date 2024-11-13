package com.example.golfhandicapapp;

public class Courses {

    public int id;
    public int holeNumber;
    public int par;
    public int handicap;

    public Courses(int id, int holeNumber, int par, int handicap) {
        this.id = id;
        this.holeNumber = holeNumber;
        this.par = par;
        this.handicap = handicap;
    }

    public int getId(){
        return id;
    }
    public int getHoleNumber(){
        return holeNumber;
    }
    public int getPar(){
        return par;
    }

    public int getHoleHandicap(){
        return handicap;
    }

    public void setId(int id){
        this.id = id;
    }

    @Override
    public String toString(){
        return "Hole #: " + holeNumber + " Par: " + par +  " Handicap: " + handicap;
    }

}
