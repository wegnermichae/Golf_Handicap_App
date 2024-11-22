/**
 * CourseActivity handles the Courses view of the Golf Handicap App, allowing users to view, add, and delete golf holes
 * associated with a specific golf course. This activity interacts with the database to store and retrieve information about
 * golf courses and their corresponding holes. It also provides navigation to other parts of the app through buttons.
 * <p>
 * The user can add holes with specific details (hole number, par, and handicap), view all holes of a selected course,
 * and delete holes from the list. The activity uses a list view to display course data, and each action (view, add, delete)
 * is triggered by specific buttons.
 * </p>
 * <p>
 * Author: Michael Wegner
 * Class: CourseActivity
 * Purpose: To manage the display of golf courses, handle user input for course details, and provide CRUD operations
 *         for managing golf holes in a selected course.
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

import java.util.List;

public class CourseActivity extends AppCompatActivity {

    ImageButton DashButton, ScoreButton, PlayerButton, CourseButton, BagButton;
    EditText nameEntry, holeEntry, parEntry, handicapEntry, slopeRatingEntry, courseRatingEntry;
    ListView courseList;
    Button viewButton, courseAdd, holeAdd;

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
        Intent intent = new Intent(CourseActivity.this, activityClass);
        startActivity(intent);
    }

    /**
     * Initializes the CourseActivity, setting up the user interface elements, restoring system bar insets for padding,
     * and setting up button navigation. It also defines click listeners for the 'View' and 'Add Hole' buttons, handling
     * user input for adding and displaying golf holes in a course.
     *
     * @param savedInstanceState A bundle containing the activity's previously saved state (if any).
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_course);

        // Sets up padding for system bars like status and navigation bar.
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize UI components
        DashButton = findViewById(R.id.dashButton3);
        ScoreButton = findViewById(R.id.scoreButton3);
        PlayerButton = findViewById(R.id.playerButton3);
        CourseButton = findViewById(R.id.courseButton3);
        BagButton = findViewById(R.id.bagButton3);
        courseAdd = findViewById(R.id.courseAdd);
        nameEntry = findViewById(R.id.nameEntry);
        holeEntry = findViewById(R.id.holeEntry);
        parEntry = findViewById(R.id.parEntry);
        handicapEntry = findViewById(R.id.handicapEntry);
        courseList = findViewById(R.id.courseList);
        viewButton = findViewById(R.id.viewButton);
        slopeRatingEntry = findViewById(R.id.slopeRatingEntry);
        courseRatingEntry = findViewById(R.id.courseRatingEntry);
        holeAdd = findViewById(R.id.holeAdd);

        setupButtonNav();

        courseAdd.setOnClickListener(v -> {
            if (v.getId() == R.id.courseAdd) {
                String dbName = nameEntry.getText().toString();
                DataBaseHelperHoles dataBaseHelperHoles = new DataBaseHelperHoles(CourseActivity.this, dbName);
                Courses courses;
                try {
                    courses = new Courses(-1, Double.parseDouble(courseRatingEntry.getText().toString()), Integer.parseInt(slopeRatingEntry.getText().toString()), nameEntry.getText().toString(), dataBaseHelperHoles.getAllHoles());
                } catch (Exception e) {
                    courses = new Courses(-1, 0, 0, nameEntry.getText().toString(), dataBaseHelperHoles.getAllHoles());
                }
                if (!dbName.isEmpty()) {
                    DataBaseHelperCourses dataBaseHelperCourses = new DataBaseHelperCourses(CourseActivity.this);
                    boolean success = dataBaseHelperCourses.addOne(courses);
                    if (success) {
                        Toast.makeText(CourseActivity.this, "Course Added", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    nameEntry.setError("Please enter a name");
                }
            }
        });


        // View button listener to display all holes in the selected course
        viewButton.setOnClickListener(v -> {
            if (v.getId() == R.id.viewButton) {
                String dbName = nameEntry.getText().toString().trim(); // Trim to remove extra spaces
                if (!dbName.isEmpty()) {
                    DataBaseHelperCourses dataBaseHelperCourses = new DataBaseHelperCourses(CourseActivity.this);

                    // Check if the course exists
                    if (dataBaseHelperCourses.courseExists(dbName)) {
                        DataBaseHelperHoles dataBaseHelperHoles = new DataBaseHelperHoles(CourseActivity.this, dbName);

                        // Retrieve and display the list of holes for the course
                        List<Holes> everyone = dataBaseHelperHoles.getAllHoles();
                        ArrayAdapter<Holes> courseArrayAdapter = new ArrayAdapter<>(CourseActivity.this, android.R.layout.simple_list_item_1, everyone);
                        courseList.setAdapter(courseArrayAdapter);
                    } else {
                        nameEntry.setError("Enter a name that matches an existing course or tap 'Add' to create a new course.");
                    }
                } else {
                    nameEntry.setError("Please enter a course name.");
                }
            }
        });

        // Add hole button listener to add a new hole to the selected course
        holeAdd.setOnClickListener(v -> {
            if (v.getId() == R.id.holeAdd) {
                Holes holes;
                try {
                    holes = new Holes(-1, Integer.parseInt(holeEntry.getText().toString()), Integer.parseInt(parEntry.getText().toString()), Integer.parseInt(handicapEntry.getText().toString()));
                } catch (Exception e) {
                    holes = new Holes(-1, Integer.parseInt(holeEntry.getText().toString()), 0, 0);
                }
                String dbName = nameEntry.getText().toString();
                if (!dbName.isEmpty()) {
                    DataBaseHelperHoles dataBaseHelperHoles = new DataBaseHelperHoles(CourseActivity.this, dbName);
                    boolean success = dataBaseHelperHoles.addOne(holes);
                    if (success) {
                        Toast.makeText(CourseActivity.this, "Hole Added", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    nameEntry.setError("Please enter a name");
                }
            }
        });

        // List item click listener to delete a selected hole from the course
        courseList.setOnItemClickListener((parent, view, position, id) -> {
            Holes holes = (Holes) parent.getItemAtPosition(position);
            String dbName = nameEntry.getText().toString();
            if(!dbName.isEmpty()) {
                DataBaseHelperHoles dataBaseHelperHoles = new DataBaseHelperHoles(CourseActivity.this, dbName);
                dataBaseHelperHoles.deleteOne(holes);
                Toast.makeText(CourseActivity.this, "Score Deleted", Toast.LENGTH_SHORT).show();
            }else{
                nameEntry.setError("Please enter a name");
            }
        });
    }
}