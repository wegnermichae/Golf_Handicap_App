/**
 * PlayerActivity handles the Players view of the Golf Handicap App, allowing users to view, add, and delete golfers.
 * This activity interacts with the database to store and retrieve golfer information, including their names and handicaps.
 * It also provides navigation to other parts of the app through buttons.
 * <p>
 * The user can add golfers with their names and handicaps, view all golfers in the system, and delete golfers from the list.
 * The activity uses a list view to display golfer data, and each action (view, add, delete) is triggered by specific buttons.
 * </p>
 * <p>
 * Author: Michael Wegner
 * Class: PlayerActivity
 * Purpose: To manage the display of golfers, handle user input for golfer details, and provide CRUD operations
 *         for managing golfers in the app.
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
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class PlayerActivity extends AppCompatActivity {

    ImageButton DashButton, ScoreButton, PlayerButton, CourseButton, BagButton;
    Button ViewButton, SubmitButton;
    EditText GolferNameEntry, GolferHandicapEntry;
    ListView PlayerView;

    /**
     * Sets up the button navigation by defining click listeners for each button that allow the user to navigate
     * to different activities in the app.
     */
    private void setupButtonNav(){
        DashButton.setOnClickListener(v -> navigateToActivity(MainActivity.class));
        ScoreButton.setOnClickListener(v -> navigateToActivity(ScoreActivity.class));
        BagButton.setOnClickListener(v -> navigateToActivity(BagActivity.class));
        CourseButton.setOnClickListener(v -> navigateToActivity(CourseActivity.class));
        PlayerButton.setOnClickListener(v -> navigateToActivity(PlayerActivity.class));
    }

    /**
     * Navigates to the specified activity when a button is pressed.
     *
     * @param activityClass The class of the activity to navigate to.
     */
    private void navigateToActivity(Class<?> activityClass) {
        Intent intent = new Intent(PlayerActivity.this, activityClass);
        startActivity(intent);
    }

    /**
     * Calculates handicap given the rounds a player has input into the app.
     *
     * @param player - The name of the player the function will calculate handicap for
     * @return - The calculated handicap for the players rounds and course information (double)
     */
    public double calculateHandicapPerRound(String player) {
        final int CONSTANT = 113;
        DataBaseHelperScores dbScores = new DataBaseHelperScores(PlayerActivity.this);
        DataBaseHelperCourses dbCourses = new DataBaseHelperCourses(PlayerActivity.this);

        // Retrieve all scores for the player
        List<Scores> playerScores = dbScores.getScoresByPlayer(player);

        // Calculate indexes
        double totalIndex = 0;
        for (Scores scoreEntry : playerScores) {
            String course = scoreEntry.getCourse();
            int score = scoreEntry.getScore();

            // Retrieve course details
            int slopeRating = dbCourses.getCourseSlope(course);
            double courseRating = dbCourses.getCourseRating(course);

            // Calculate adjusted score and index
            int par = new DataBaseHelperHoles(PlayerActivity.this, course).getAllPar();
            double adjustedScore = score - (courseRating - par);
            double index = ((adjustedScore - courseRating) / slopeRating) * CONSTANT;

            totalIndex += index;
        }

        // Return average index or 0 if no scores exist
        return playerScores.isEmpty() ? 0 : totalIndex / playerScores.size();
    }

    /**
     * Initializes the PlayerActivity, setting up the user interface elements, restoring system bar insets for padding,
     * and setting up button navigation. It also defines click listeners for the 'View' and 'Submit' buttons, handling
     * user input for adding and displaying golfers.
     *
     * @param savedInstanceState A bundle containing the activity's previously saved state (if any).
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_player);

        // Sets up padding for system bars like status and navigation bar.
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize UI components
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

        // View button listener to display all golfers
        ViewButton.setOnClickListener(v -> {
            if (v.getId() == R.id.view) {
                DataBaseHelperPlayers dataBaseHelperPlayers = new DataBaseHelperPlayers(PlayerActivity.this);
                PlayerView.setAdapter(new ArrayAdapter<>(PlayerActivity.this, android.R.layout.simple_list_item_1, dataBaseHelperPlayers.getAllGolfers()));
            }
        });

        // List item click listener to delete a selected golfer
        PlayerView.setOnItemClickListener((parent, view, position, id) -> {
            Golfers golfers = (Golfers) parent.getItemAtPosition(position);
            DataBaseHelperPlayers dataBaseHelperPlayers = new DataBaseHelperPlayers(PlayerActivity.this);
            dataBaseHelperPlayers.deleteOne(golfers);
            Toast.makeText(PlayerActivity.this, "Handicap Deleted", Toast.LENGTH_SHORT).show();
        });

        // Submit button listener to add a new golfer
        SubmitButton.setOnClickListener(v -> {
            if (v.getId() == R.id.submit) {
                Golfers golfers;
                try{
                    golfers = new Golfers(-1, GolferNameEntry.getText().toString(), calculateHandicapPerRound(GolferNameEntry.getText().toString()));
                }catch (Exception e){
                    golfers = new Golfers(-1, GolferNameEntry.getText().toString(), 0);
                }
                DataBaseHelperPlayers dataBaseHelperGolfers = new DataBaseHelperPlayers(PlayerActivity.this);
                boolean success = dataBaseHelperGolfers.addOne(golfers);
                if (success) {
                    Toast.makeText(PlayerActivity.this, "Golfer Added", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}