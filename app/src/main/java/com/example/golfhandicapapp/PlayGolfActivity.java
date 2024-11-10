package com.example.golfhandicapapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PlayGolfActivity extends AppCompatActivity {

    Button submitButton;
    EditText courseName, golfer1Name, golfer2Name, golfer3Name, golfer4Name, golfer1Handicap,
            golfer2Handicap, golfer3Handicap, golfer4Handicap, golfer1Strokes, golfer2Strokes,
            golfer3Strokes, golfer4Strokes;
    ListView holeView;
    ImageButton dashButton, scoreButton, bagButton, courseButton, playerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_play_golf);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dashButton = findViewById(R.id.dashButton5);
        scoreButton = findViewById(R.id.scoreButton5);
        bagButton = findViewById(R.id.bagButton5);
        courseButton = findViewById(R.id.courseButton5);
        playerButton = findViewById(R.id.playerButton5);


        //the following listeners will allow for functionality of the specified button clicks
        dashButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.dashButton5) {
                    Intent intent = new Intent(PlayGolfActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });

        scoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.scoreButton5) {
                    Intent intent = new Intent(PlayGolfActivity.this, ScoreActivity.class);
                    startActivity(intent);
                }
            }
        });

        playerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.playerButton5) {
                    Intent intent = new Intent(PlayGolfActivity.this, PlayerActivity.class);
                    startActivity(intent);
                }
            }
        });

        courseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.courseButton5) {
                    Intent intent = new Intent(PlayGolfActivity.this, CourseActivity.class);
                    startActivity(intent);
                }
            }
        });

        bagButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.bagButton5) {
                    Intent intent = new Intent(PlayGolfActivity.this, BagActivity.class);
                    startActivity(intent);
                }
            }
        });




    }
}