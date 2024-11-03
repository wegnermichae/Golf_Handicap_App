package com.example.golfhandicapapp;

public class Scores {
    public int score;
    public String course;
    public String player;
    public Scores(int score, String course, String player){
        this.score = score;
        this.course = course;
        this.player = player;
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
    public void setScore(int score){
        this.score = score;
    }
    public void setCourse(String course){
        this.course = course;
        }
    public void setPlayer(String player){
        this.player = player;
    }
}
