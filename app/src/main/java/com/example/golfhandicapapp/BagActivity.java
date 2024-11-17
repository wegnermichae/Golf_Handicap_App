package com.example.golfhandicapapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class BagActivity extends AppCompatActivity {

    EditText clubEntry, distanceEntry;
    ImageButton DashButton, ScoreButton, PlayerButton, CourseButton, BagButton;
    Button submitButton, viewButton;
    ListView clubView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bag);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        DashButton = findViewById(R.id.dashButton4);
        ScoreButton = findViewById(R.id.scoreButton4);
        PlayerButton = findViewById(R.id.playerButton4);
        CourseButton = findViewById(R.id.courseButton4);
        BagButton = findViewById(R.id.bagButton4);
        viewButton = findViewById(R.id.viewBut);
        submitButton = findViewById(R.id.submitBut);
        clubEntry = findViewById(R.id.clubEntry);
        distanceEntry = findViewById(R.id.distanceEntry);
        clubView = findViewById(R.id.clubView);


        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.viewBut) {
                    DataBaseHelperClubs dataBaseHelperClubs = new DataBaseHelperClubs(BagActivity.this);
                    clubView.setAdapter(new ArrayAdapter<Clubs>(BagActivity.this, android.R.layout.simple_list_item_1, dataBaseHelperClubs.getAllClubs()));
                }
            }
        });

        clubView.setOnItemClickListener((parent, view, position, id) -> {
            Clubs clubs = (Clubs) parent.getItemAtPosition(position);
            DataBaseHelperClubs dataBaseHelperClubs = new DataBaseHelperClubs(BagActivity.this);
            dataBaseHelperClubs.deleteOne(clubs);
            Toast.makeText(BagActivity.this, "Score Deleted", Toast.LENGTH_SHORT).show();
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.submitBut) {
                    Clubs clubs;
                    try{
                        clubs = new Clubs(-1, clubEntry.getText().toString(), Integer.parseInt(distanceEntry.getText().toString()));
                    }catch (Exception e){
                        clubs = new Clubs(-1, clubEntry.getText().toString(), 0);
                    }
                    DataBaseHelperClubs dataBaseHelperClubs = new DataBaseHelperClubs(BagActivity.this);
                    boolean success = dataBaseHelperClubs.addOne(clubs);
                    if (success) {
                        Toast.makeText(BagActivity.this, "Golfer Added", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });





        //the following listeners will allow for functionality of the specified button clicks
        DashButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.dashButton4) {
                    Intent intent = new Intent(BagActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });

        ScoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.scoreButton4) {
                    Intent intent = new Intent(BagActivity.this, ScoreActivity.class);
                    startActivity(intent);
                }
            }
        });

        PlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.playerButton4) {
                    Intent intent = new Intent(BagActivity.this, PlayerActivity.class);
                    startActivity(intent);
                }
            }
        });

        CourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.courseButton4) {
                    Intent intent = new Intent(BagActivity.this, CourseActivity.class);
                    startActivity(intent);
                }
            }
        });

        BagButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.bagButton4) {
                    Intent intent = new Intent(BagActivity.this, BagActivity.class);
                    startActivity(intent);
                }
            }
        });


    }
}