package com.example.golfhandicapapp;

import java.util.List;

public class Courses {

    public int id;
    public double courseRating;
    public int slopeRating;
    public String name;
    public int par;
    private List<Holes> holes;

    public Courses(int id, double courseRating, int slopeRating, String name, List<Holes> holes) {
        this.id = id;
        this.courseRating = courseRating;
        this.slopeRating = slopeRating;
        this.name = name;
        this.holes = holes;
    }

    public int getId(){
        return id;
    }
    public double getCourseRating(){
        return courseRating;
    }
    public int getSlopeRating(){
        return slopeRating;
    }
    public String getName(){
        return name;
    }

    public void setPar(){
        for(int i = 0; i < holes.size(); i++){
            par += holes.get(i).getPar();
        }
    }

    public int getPar(){
        return par;
    }

    public List<Holes> getHoles(){
        return holes;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setHoles(List<Holes> holes){
        this.holes = holes;
    }

    @Override
    public String toString(){
        return name;
    }

}
