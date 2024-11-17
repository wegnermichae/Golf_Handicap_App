package com.example.golfhandicapapp;

public class Scores {
    private int id;
    private int score;
    private String course;
    private String player;
    public Scores(int id, int score, String course, String player){
        this.id = id;
        this.score = score;
        this.course = course;
        this.player = player;
    }

    public int getId(){
        return id;
    }
    public int getScore(){
        return score;
    }
    public String getCourse(){
        return course;
        }
    public String getPlayer(){
        return player;
    }
    public void setId(int id){
        this.id = id;
    }

    @Override
    public String toString(){
        return "Score: " + score + " Course: " + course + " Player: " + player;
    }


}
