package com.example.golfhandicapapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ScoreActivity extends AppCompatActivity {

    ArrayList<String> scores = new ArrayList<String>();

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
        TextView scoreEntry = findViewById(R.id.scoreEntry);
        TextView courseEntry = findViewById(R.id.courseEntry);
        TextView playerEntry = findViewById(R.id.playerEntry);
        EditText scoreInput = findViewById(R.id.scoreInput);
        EditText courseInput = findViewById(R.id.courseInput);
        EditText playerInput = findViewById(R.id.playerInput);
        Button ViewScores = findViewById(R.id.viewScores);

        ViewScores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.viewScores) {
                    DataBaseHelper dataBaseHelper = new DataBaseHelper(ScoreActivity.this);
                    List<Scores> everyone = dataBaseHelper.getAllScores();

                    ArrayAdapter<Scores> scoreArrayAdapter = new ArrayAdapter<Scores>(ScoreActivity.this, android.R.layout.simple_list_item_1, everyone);
                    ScoreList.setAdapter(scoreArrayAdapter);
                }
            }
        });


        AddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.addButton) {
                    Scores score;
                    try {
                        score = new Scores(Integer.parseInt(scoreInput.getText().toString()), courseInput.getText().toString(), playerInput.getText().toString());
                    } catch (Exception e) {
                        score = new Scores(0, "Null", "Null");
                    }
                    DataBaseHelper dataBaseHelper = new DataBaseHelper(ScoreActivity.this);
                    boolean success = dataBaseHelper.addOne(score);
                    if (success) {
                        Toast.makeText(ScoreActivity.this, "Score Added", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


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