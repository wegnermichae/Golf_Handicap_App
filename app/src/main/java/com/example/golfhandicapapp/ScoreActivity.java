package com.example.golfhandicapapp;

import android.content.Intent;
import android.os.Bundle;
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

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ScoreActivity extends AppCompatActivity {


    ImageButton DashButton, ScoreButton, PlayerButton, CourseButton, BagButton, AddButton;
    EditText scoreInput, courseInput, playerInput;
    TextView scoreEntry, courseEntry, playerEntry;
    ListView ScoreList;
    Button ViewScores;

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

        DashButton = findViewById(R.id.dashButton2);
        ScoreButton = findViewById(R.id.scoreButton2);
        PlayerButton = findViewById(R.id.playerButton2);
        CourseButton = findViewById(R.id.courseButton2);
        BagButton = findViewById(R.id.bagButton2);
        AddButton = findViewById(R.id.addButton);
        ScoreList = findViewById(R.id.scoreList);
        scoreEntry = findViewById(R.id.scoreEntry);
        courseEntry = findViewById(R.id.courseEntry);
        playerEntry = findViewById(R.id.playerEntry);
        scoreInput = findViewById(R.id.scoreInput);
        courseInput = findViewById(R.id.courseInput);
        playerInput = findViewById(R.id.playerInput);
        ViewScores = findViewById(R.id.viewScores);

        //Handle viewing scores from score database
        ViewScores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.viewScores) {
                    DataBaseHelperScores dataBaseHelperScores = new DataBaseHelperScores(ScoreActivity.this);
                    List<Scores> everyone = dataBaseHelperScores.getAllScores();

                    Toast.makeText(ScoreActivity.this, everyone.get(0).getPlayer(), Toast.LENGTH_SHORT).show();


                    ArrayAdapter<Scores> scoreArrayAdapter = new ArrayAdapter<Scores>(ScoreActivity.this, android.R.layout.simple_list_item_1, everyone);
                    ScoreList.setAdapter(scoreArrayAdapter);
                }
            }
        });

        //Handle adding scores to score database
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
                    DataBaseHelperScores dataBaseHelperScores = new DataBaseHelperScores(ScoreActivity.this);
                    boolean success = dataBaseHelperScores.addOne(score);
                    if (success) {
                        Toast.makeText(ScoreActivity.this, "Score Added", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        //Handle deleting score from score database
        ScoreList.setOnItemClickListener((parent, view, position, id) -> {
            Scores scores = (Scores) parent.getItemAtPosition(position);
            //make popup asking delete, edit or cancel
            DataBaseHelperScores dataBaseHelperScores = new DataBaseHelperScores(ScoreActivity.this);
            dataBaseHelperScores.deleteOne(scores);
            Toast.makeText(ScoreActivity.this, "Score Deleted", Toast.LENGTH_SHORT).show();

        });


        //The following listeners will allow for functionality of the specified button clicks
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