/**
 * Author: Michael Wegner
 * Class: MainActivity
 * Purpose: This class will handle the main menu of the application and its interactions
 */


package com.example.golfhandicapapp;

import android.content.Intent;
import android.os.Bundle;
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

    private void setupButtonNav(){
        DashButton.setOnClickListener(v -> navigateToActivity(MainActivity.class));
        ScoreButton.setOnClickListener(v -> navigateToActivity(ScoreActivity.class));
        BagButton.setOnClickListener(v -> navigateToActivity(BagActivity.class));
        CourseButton.setOnClickListener(v -> navigateToActivity(CourseActivity.class));
        PlayerButton.setOnClickListener(v -> navigateToActivity(PlayerActivity.class));
    }

    private void navigateToActivity(Class<?> activityClass) {
        Intent intent = new Intent(MainActivity.this, activityClass);
        startActivity(intent);
    }



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
        headingText = findViewById(R.id.textHeading);
        playButton = findViewById(R.id.playButton);

        setupButtonNav();

        playButton.setOnClickListener(v -> {
            if (v.getId() == R.id.playButton){
                Intent intent = new Intent(MainActivity.this, PlayGolfActivity.class);
                startActivity(intent);
            }
        });
    }
}