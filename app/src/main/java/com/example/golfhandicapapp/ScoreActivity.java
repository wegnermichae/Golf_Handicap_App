package com.example.golfhandicapapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ScoreActivity extends AppCompatActivity {

    ArrayList<Integer> scores = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_score);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton DashButton = findViewById(R.id.dashButton2);
        ImageButton ScoreButton = findViewById(R.id.scoreButton2);
        ImageButton PlayerButton = findViewById(R.id.playerButton2);
        ImageButton CourseButton = findViewById(R.id.courseButton2);
        ImageButton BagButton = findViewById(R.id.bagButton2);
        ImageButton AddButton = findViewById(R.id.addButton);
        ListView ScoreList = findViewById(R.id.scoreList);




        //the following listeners will allow for functionality of the specified button clicks
        DashButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.dashButton2) {
                    Intent intent = new Intent(ScoreActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });

        ScoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.scoreButton2) {
                    Intent intent = new Intent(ScoreActivity.this, ScoreActivity.class);
                    startActivity(intent);
                }
            }
        });

        PlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.playerButton2) {
                    Intent intent = new Intent(ScoreActivity.this, PlayerActivity.class);
                    startActivity(intent);
                }
            }
        });

        CourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.courseButton2) {
                    Intent intent = new Intent(ScoreActivity.this, CourseActivity.class);
                    startActivity(intent);
                }
            }
        });

        BagButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.bagButton2) {
                    Intent intent = new Intent(ScoreActivity.this, BagActivity.class);
                    startActivity(intent);
                }
            }
        });


    }
}