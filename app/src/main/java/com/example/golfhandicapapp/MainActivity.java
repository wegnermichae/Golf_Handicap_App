/**
 * Author: Michael Wegner
 * Class: MainActivity
 * Purpose: This class will handle the main menu of the application and its interactions
 */


package com.example.golfhandicapapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    ImageButton DashButton, ScoreButton, PlayerButton, CourseButton, BagButton;
    SeekBar scoreSeekBar, handicapSeekBar;
    TextView headingText, previousScore, currentHandicap;
    Button playButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        DashButton = findViewById(R.id.DashButton);
        ScoreButton = findViewById(R.id.ScoreButton);
        PlayerButton = findViewById(R.id.PlayerButton);
        CourseButton = findViewById(R.id.CourseButton);
        BagButton = findViewById(R.id.BagButton);
        scoreSeekBar = findViewById(R.id.scoreSeekBar);
        handicapSeekBar = findViewById(R.id.handicapSeekBar);
        headingText = findViewById(R.id.textHeading);
        previousScore = findViewById(R.id.previousScore);
        currentHandicap = findViewById(R.id.currentHandicap);
        playButton = findViewById(R.id.playButton);


        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.playButton){
                    Intent intent = new Intent(MainActivity.this, PlayGolfActivity.class);
                    startActivity(intent);
                }
            }
        });


        //the following listeners will allow for functionality of the specified button clicks
        DashButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.DashButton) {
                    Intent intent = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });

        ScoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.ScoreButton) {
                    Intent intent = new Intent(MainActivity.this, ScoreActivity.class);
                    startActivity(intent);
                }
            }
        });

        PlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.PlayerButton) {
                    Intent intent = new Intent(MainActivity.this, PlayerActivity.class);
                    startActivity(intent);
                }
            }
        });

        CourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.CourseButton) {
                    Intent intent = new Intent(MainActivity.this, CourseActivity.class);
                    startActivity(intent);
                }
            }
        });

        BagButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.BagButton) {
                    Intent intent = new Intent(MainActivity.this, BagActivity.class);
                    startActivity(intent);
                }
            }
        });


    }




}