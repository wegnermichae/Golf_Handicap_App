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

    public double calculateHandicapPerRound(String player){
        List<Scores> playerScores;
        List<Double> indexies = new ArrayList<>();
        int score;
        int slopeRating;
        double courseRating;
        int par;
        String course;
        double adjustedScore;
        double index;
        int CONSTANT = 113;
        DataBaseHelperScores dataBaseHelperScores = new DataBaseHelperScores(PlayerActivity.this);
        DataBaseHelperCourses dataBaseHelperCourses = new DataBaseHelperCourses(PlayerActivity.this);
        playerScores = dataBaseHelperScores.getScoresByPlayer(player);
        for(int i = 0; i < playerScores.size(); i++){
            course = playerScores.get(i).getCourse();
            DataBaseHelperHoles dataBaseHelperHoles = new DataBaseHelperHoles(PlayerActivity.this, course);
            score = playerScores.get(i).getScore();
            slopeRating = dataBaseHelperCourses.getCourseSlope(course);
            courseRating = dataBaseHelperCourses.getCourseRating(course);
            par = dataBaseHelperHoles.getAllPar();
            adjustedScore = score - (courseRating - par);
            index = ((adjustedScore - courseRating)/slopeRating)*CONSTANT;
            indexies.add(index);
        }
        double finalIndex;
        double sum = 0;
        for(int i = 0; i < indexies.size(); i++){
            sum += indexies.get(i);
        }
        finalIndex = sum/indexies.size();
        return finalIndex;
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