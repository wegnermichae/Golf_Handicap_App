/**
 * MainActivity is the primary screen for the Golf Handicap App that handles the main menu of the application
 * and its interactions. It provides the user interface to navigate to other parts of the app, such as the
 * score tracking, player management, and course selection, as well as the ability to update and store a user's
 * golf handicap.
 * <p>
 * This activity also allows the user to input their name, retrieves the associated golf handicap from the database,
 * and saves user data persistently across app sessions using SharedPreferences.
 * </p>
 * <p>
 * Author: Michael Wegner
 * Class: MainActivity
 * Purpose: To manage the main menu interface, user input, and interactions with other activities in the app.
 * </p>
 */


package com.example.golfhandicapapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    ImageButton DashButton, ScoreButton, PlayerButton, CourseButton, BagButton;
    TextView headingText;
    EditText mainUserName, trackedHandicap;
    Button playButton, updateUser;

    /**
     * Sets up button navigation by defining click listeners for each button to navigate to the respective activity.
     * Each button is linked to its corresponding activity, allowing the user to switch between different parts of the app.
     */
    private void setupButtonNav(){
        DashButton.setOnClickListener(v -> navigateToActivity(MainActivity.class));
        ScoreButton.setOnClickListener(v -> navigateToActivity(ScoreActivity.class));
        BagButton.setOnClickListener(v -> navigateToActivity(BagActivity.class));
        CourseButton.setOnClickListener(v -> navigateToActivity(CourseActivity.class));
        PlayerButton.setOnClickListener(v -> navigateToActivity(PlayerActivity.class));
    }

    /**
     * Navigates to a specified activity.
     *
     * @param activityClass The class of the activity to navigate to.
     */
    private void navigateToActivity(Class<?> activityClass) {
        Intent intent = new Intent(MainActivity.this, activityClass);
        startActivity(intent);
    }

    /**
     * Initializes the main activity, setting up the UI elements, restoring previously saved data (if any),
     * and setting up button navigation. It also defines logic for updating user data such as the player's name and handicap.
     *
     * @param savedInstanceState A bundle containing the activity's previously saved state (if any).
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Sets up padding for system bars like status and navigation bar.
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize UI components
        DashButton = findViewById(R.id.DashButton);
        ScoreButton = findViewById(R.id.ScoreButton);
        PlayerButton = findViewById(R.id.PlayerButton);
        CourseButton = findViewById(R.id.CourseButton);
        BagButton = findViewById(R.id.BagButton);
        headingText = findViewById(R.id.textHeading);
        playButton = findViewById(R.id.playButton);
        updateUser = findViewById(R.id.updateUser);
        mainUserName = findViewById(R.id.mainUserName);
        trackedHandicap = findViewById(R.id.trackedHandicap);

        restoreData();
        setupButtonNav();

        // Set up click listener for the play button to navigate to the PlayGolfActivity.
        playButton.setOnClickListener(v -> {
            if (v.getId() == R.id.playButton){
                Intent intent = new Intent(MainActivity.this, PlayGolfActivity.class);
                startActivity(intent);
            }
        });

        // Set up click listener for the updateUser button to update and store user's golf handicap.
        updateUser.setOnClickListener(v -> {
            if (v.getId() == R.id.updateUser) {
                DataBaseHelperPlayers db = new DataBaseHelperPlayers(MainActivity.this);
                String name = mainUserName.getText().toString();
                int handicap = db.getOneGolfer(name);
                if (handicap == -1) {
                    mainUserName.setError("Player not found, Please add them in the Player Menu");
                    trackedHandicap.setText("");
                }
                else {
                    trackedHandicap.setText(String.valueOf(handicap));
                    saveData();
                }
            }
        });
    }

    /**
     * Saves the user's name and handicap to SharedPreferences to persist data across app sessions.
     */
    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("UserName", mainUserName.getText().toString());
        editor.putString("Handicap", trackedHandicap.getText().toString());

        editor.apply();  // Apply changes asynchronously
    }

    /**
     * Restores the user's previously saved name and handicap from SharedPreferences (if available).
     * This allows the app to display the user's information after a shutdown and restart.
     */
    private void restoreData() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        String savedUserName = sharedPreferences.getString("UserName", "");
        String savedHandicap = sharedPreferences.getString("Handicap", "");

        mainUserName.setText(savedUserName);
        trackedHandicap.setText(savedHandicap);
    }


}