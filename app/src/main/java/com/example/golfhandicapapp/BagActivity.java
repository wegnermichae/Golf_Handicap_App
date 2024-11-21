/**
 * BagActivity handles the 'Bag' function of the Golf Handicap App, allowing users to manage their golf clubs.
 * This activity enables users to add new clubs to their golf bag, view existing clubs, and delete clubs.
 * It also includes navigation to other sections of the app through various buttons.
 * <p>
 * The user can input club names and their corresponding distances, view a list of clubs in their bag, and remove clubs.
 * The activity utilizes a ListView to display the clubs in the user's bag and provides buttons for submitting and viewing clubs.
 * </p>
 * <p>
 * Author: Michael Wegner
 * Class: BagActivity
 * Purpose: To manage the 'Bag' function of the app, including adding, viewing, and deleting golf clubs.
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

public class BagActivity extends AppCompatActivity {

    EditText clubEntry, distanceEntry;
    ImageButton DashButton, ScoreButton, PlayerButton, CourseButton, BagButton;
    Button submitButton, viewButton;
    ListView clubView;

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
        Intent intent = new Intent(BagActivity.this, activityClass);
        startActivity(intent);
    }

    /**
     * Initializes the activity, sets up the layout, views, and buttons,
     * and configures the functionality for adding, viewing, and deleting clubs.
     * <p>
     * This method is called when the activity is created. It initializes the views for club entry,
     * sets up button actions for submitting and viewing clubs, and configures a ListView to display the clubs.
     * </p>
     *
     * @param savedInstanceState A Bundle containing the activity's previously saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bag);

        // Apply system bar insets for proper padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize buttons, input fields, and ListView for displaying clubs
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

        // Handle viewing clubs from the club database
        viewButton.setOnClickListener(v -> {
            if (v.getId() == R.id.viewBut) {
                DataBaseHelperClubs dataBaseHelperClubs = new DataBaseHelperClubs(BagActivity.this);
                clubView.setAdapter(new ArrayAdapter<>(BagActivity.this, android.R.layout.simple_list_item_1, dataBaseHelperClubs.getAllClubs()));
            }
        });

        // Handle deleting a club from the club database
        clubView.setOnItemClickListener((parent, view, position, id) -> {
            Clubs clubs = (Clubs) parent.getItemAtPosition(position);
            DataBaseHelperClubs dataBaseHelperClubs = new DataBaseHelperClubs(BagActivity.this);
            dataBaseHelperClubs.deleteOne(clubs);
            Toast.makeText(BagActivity.this, "Score Deleted", Toast.LENGTH_SHORT).show();
        });

        // Handle submitting a new club to the club database
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