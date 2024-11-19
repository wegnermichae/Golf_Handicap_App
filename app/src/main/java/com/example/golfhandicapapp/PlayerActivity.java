/**
 * Author: Michael Wegner
 * Class: PlayerActivity
 * Purpose: This class will handle the Players view of the application and its interactions
 */

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

public class PlayerActivity extends AppCompatActivity {

    ImageButton DashButton, ScoreButton, PlayerButton, CourseButton, BagButton;
    Button ViewButton, SubmitButton;
    EditText GolferNameEntry, GolferHandicapEntry;
    ListView PlayerView;

    private void setupButtonNav(){
        DashButton.setOnClickListener(v -> navigateToActivity(MainActivity.class));
        ScoreButton.setOnClickListener(v -> navigateToActivity(ScoreActivity.class));
        BagButton.setOnClickListener(v -> navigateToActivity(BagActivity.class));
        CourseButton.setOnClickListener(v -> navigateToActivity(CourseActivity.class));
        PlayerButton.setOnClickListener(v -> navigateToActivity(PlayerActivity.class));
    }

    private void navigateToActivity(Class<?> activityClass) {
        Intent intent = new Intent(PlayerActivity.this, activityClass);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_player);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        DashButton = findViewById(R.id.dashButton);
        ScoreButton = findViewById(R.id.scoreButton);
        PlayerButton = findViewById(R.id.playerButton);
        CourseButton = findViewById(R.id.courseButton);
        BagButton = findViewById(R.id.bagButton);
        ViewButton = findViewById(R.id.view);
        SubmitButton = findViewById(R.id.submit);
        GolferNameEntry = findViewById(R.id.golferNameEntry);
        GolferHandicapEntry = findViewById(R.id.golferHandicapEntry);
        PlayerView = findViewById(R.id.playerView);

        setupButtonNav();

        ViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.view) {
                    DataBaseHelperPlayers dataBaseHelperPlayers = new DataBaseHelperPlayers(PlayerActivity.this);
                    PlayerView.setAdapter(new ArrayAdapter<Golfers>(PlayerActivity.this, android.R.layout.simple_list_item_1, dataBaseHelperPlayers.getAllGolfers()));
                }
            }
        });

        PlayerView.setOnItemClickListener((parent, view, position, id) -> {
            Golfers golfers = (Golfers) parent.getItemAtPosition(position);
            DataBaseHelperPlayers dataBaseHelperPlayers = new DataBaseHelperPlayers(PlayerActivity.this);
            dataBaseHelperPlayers.deleteOne(golfers);
            Toast.makeText(PlayerActivity.this, "Score Deleted", Toast.LENGTH_SHORT).show();
        });

        SubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.submit) {
                    Golfers golfers;
                    try{
                        golfers = new Golfers(-1, GolferNameEntry.getText().toString(), Integer.parseInt(GolferHandicapEntry.getText().toString()));
                    }catch (Exception e){
                        golfers = new Golfers(-1, GolferNameEntry.getText().toString(), 0);
                    }
                    DataBaseHelperPlayers dataBaseHelperGolfers = new DataBaseHelperPlayers(PlayerActivity.this);
                    boolean success = dataBaseHelperGolfers.addOne(golfers);
                    if (success) {
                        Toast.makeText(PlayerActivity.this, "Golfer Added", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}