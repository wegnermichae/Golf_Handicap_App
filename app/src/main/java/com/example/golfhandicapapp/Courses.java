package com.example.golfhandicapapp;

public class Courses {
    public int holeNumber;
    public int par;
    public int handicap;

    public Courses(int holeNumber, int par, int handicap) {

        this.holeNumber = holeNumber;
        this.par = par;
        this.handicap = handicap;
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

    @Override
    public String toString(){
        return "Hole #: " + holeNumber + " Par: " + par +  " Handicap: " + handicap;
    }

}
