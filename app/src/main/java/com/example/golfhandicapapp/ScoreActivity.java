/**
 * ScoreActivity handles the Scores view of the Golf Handicap App, allowing users to add, view, and delete scores.
 * This activity interacts with the database to store and retrieve scores, including player names, courses, and scores.
 * It also provides navigation to other parts of the app through buttons.
 * <p>
 * The user can input score information (score, course, and player), view all scores in the system, and delete scores from the list.
 * The activity uses a ListView to display the scores, and each action (view, add, delete) is triggered by specific buttons.
 * </p>
 * <p>
 * Author: Michael Wegner
 * Class: ScoreActivity
 * Purpose: To manage the display of scores, handle user input for score details, and provide CRUD operations
 *         for managing scores in the app.
 * </p>
 */

package com.example.golfhandicapapp;

import android.content.Intent;
import android.os.Bundle;

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

public class ScoreActivity extends AppCompatActivity {

    ImageButton DashButton, ScoreButton, PlayerButton, CourseButton, BagButton, AddButton;
    EditText scoreInput, courseInput, playerInput;
    TextView scoreEntry, courseEntry, playerEntry;
    ListView ScoreList;
    Button ViewScores;
    ArrayAdapter<Scores> scoreArrayAdapter;

    /**
     * Sets up the button navigation to other activities in the app.
     * Each button navigates to a specific activity when clicked.
     */
    private void setupButtonNav(){
        DashButton.setOnClickListener(v -> navigateToActivity(MainActivity.class));
        ScoreButton.setOnClickListener(v -> navigateToActivity(ScoreActivity.class));
        BagButton.setOnClickListener(v -> navigateToActivity(BagActivity.class));
        CourseButton.setOnClickListener(v -> navigateToActivity(CourseActivity.class));
        PlayerButton.setOnClickListener(v -> navigateToActivity(PlayerActivity.class));
    }

    /**
     * Navigates to the specified activity.
     *
     * @param activityClass The class of the activity to navigate to.
     */
    private void navigateToActivity(Class<?> activityClass) {
        Intent intent = new Intent(ScoreActivity.this, activityClass);
        startActivity(intent);
    }

    /**
     * Initializes the activity, sets up the layout, views, and buttons,
     * and configures the functionality for viewing, adding, and deleting scores.
     * <p>
     * This method is called when the activity is created. It sets up the views for score input,
     * initializes buttons for navigation and actions, and configures the ListView for displaying scores.
     * </p>
     *
     * @param savedInstanceState A Bundle containing the activity's previously saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_score);

        // Apply system bar insets for proper padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize buttons, input fields, and ListView for displaying scores
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

        setupButtonNav();

        // Handle viewing scores from the score database
        ViewScores.setOnClickListener(v -> {
            if (v.getId() == R.id.viewScores) {
                DataBaseHelperScores dataBaseHelperScores = new DataBaseHelperScores(ScoreActivity.this);
                scoreArrayAdapter = new ArrayAdapter<>(ScoreActivity.this, android.R.layout.simple_list_item_1, dataBaseHelperScores.getAllScores());
                ScoreList.setAdapter(scoreArrayAdapter);
            }
        });

        // Handle adding scores to the score database
        AddButton.setOnClickListener(v -> {
            if (v.getId() == R.id.addButton) {
                Scores score;
                try {
                    score = new Scores(-1, Integer.parseInt(scoreInput.getText().toString()), courseInput.getText().toString(), playerInput.getText().toString());
                } catch (Exception e) {
                    score = new Scores(-1, 0, "Null", "Null");
                }
                DataBaseHelperScores dataBaseHelperScores = new DataBaseHelperScores(ScoreActivity.this);
                boolean success = dataBaseHelperScores.addOne(score);

                if (success) {
                    Toast.makeText(ScoreActivity.this, "Score Added", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Handle deleting a score from the score database
        ScoreList.setOnItemClickListener((parent, view, position, id) -> {
            Scores scores = (Scores) parent.getItemAtPosition(position);
            DataBaseHelperScores dataBaseHelperScores = new DataBaseHelperScores(ScoreActivity.this);
            dataBaseHelperScores.deleteOne(scores);
            Toast.makeText(ScoreActivity.this, "Score Deleted", Toast.LENGTH_SHORT).show();
        });
    }
}