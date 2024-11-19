/**
 * Author: Michael Wegner
 * Class: BagActivity
 * Purpose: This class will handle the 'Bag' function of the application and its interactions
 */

package com.example.golfhandicapapp;

import android.content.Intent;
import android.os.Bundle;
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

    private void setupButtonNav(){
        DashButton.setOnClickListener(v -> navigateToActivity(MainActivity.class));
        ScoreButton.setOnClickListener(v -> navigateToActivity(ScoreActivity.class));
        BagButton.setOnClickListener(v -> navigateToActivity(BagActivity.class));
        CourseButton.setOnClickListener(v -> navigateToActivity(CourseActivity.class));
        PlayerButton.setOnClickListener(v -> navigateToActivity(PlayerActivity.class));
    }

    private void navigateToActivity(Class<?> activityClass) {
        Intent intent = new Intent(BagActivity.this, activityClass);
        startActivity(intent);
    }

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

        setupButtonNav();

        viewButton.setOnClickListener(v -> {
            if (v.getId() == R.id.viewBut) {
                DataBaseHelperClubs dataBaseHelperClubs = new DataBaseHelperClubs(BagActivity.this);
                clubView.setAdapter(new ArrayAdapter<>(BagActivity.this, android.R.layout.simple_list_item_1, dataBaseHelperClubs.getAllClubs()));
            }
        });

        clubView.setOnItemClickListener((parent, view, position, id) -> {
            Clubs clubs = (Clubs) parent.getItemAtPosition(position);
            DataBaseHelperClubs dataBaseHelperClubs = new DataBaseHelperClubs(BagActivity.this);
            dataBaseHelperClubs.deleteOne(clubs);
            Toast.makeText(BagActivity.this, "Score Deleted", Toast.LENGTH_SHORT).show();
        });

        submitButton.setOnClickListener(v -> {
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
        });
    }
}