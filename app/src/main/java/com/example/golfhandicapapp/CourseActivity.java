/**
 * Author: Michael Wegner
 * Class: CourseActivity
 * Purpose: This class will handle the Courses view of the application and its interactions
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

import java.util.List;

public class CourseActivity extends AppCompatActivity {

    ImageButton DashButton, ScoreButton, PlayerButton, CourseButton, BagButton, courseAdd;
    EditText nameEntry, holeEntry, parEntry, handicapEntry;
    ListView courseList;
    Button viewButton;

    private void setupButtonNav(){
        DashButton.setOnClickListener(v -> navigateToActivity(MainActivity.class));
        ScoreButton.setOnClickListener(v -> navigateToActivity(ScoreActivity.class));
        BagButton.setOnClickListener(v -> navigateToActivity(BagActivity.class));
        CourseButton.setOnClickListener(v -> navigateToActivity(CourseActivity.class));
        PlayerButton.setOnClickListener(v -> navigateToActivity(PlayerActivity.class));
    }

    private void navigateToActivity(Class<?> activityClass) {
        Intent intent = new Intent(CourseActivity.this, activityClass);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_course);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

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

        setupButtonNav();

        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.viewButton) {
                    String dbName = nameEntry.getText().toString();
                    if(!dbName.isEmpty()) {
                        DataBaseHelperCourses dataBaseHelperCourses = new DataBaseHelperCourses(CourseActivity.this, dbName);

                        List<Courses> everyone = dataBaseHelperCourses.getAllHoles();

                        ArrayAdapter<Courses> courseArrayAdapter = new ArrayAdapter<Courses>(CourseActivity.this, android.R.layout.simple_list_item_1, everyone);
                        courseList.setAdapter(courseArrayAdapter);
                    }else{
                        nameEntry.setError("Please enter a name");
                    }
                }
            }
        });

        courseAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.courseAdd) {
                    Courses courses;
                    try {
                        courses = new Courses(-1, Integer.parseInt(holeEntry.getText().toString()), Integer.parseInt(parEntry.getText().toString()), Integer.parseInt(handicapEntry.getText().toString()));
                    } catch (Exception e) {
                        courses = new Courses(-1, Integer.parseInt(holeEntry.getText().toString()), 0, 0);
                    }
                    String dbName = nameEntry.getText().toString();
                    if (!dbName.isEmpty()) {
                        DataBaseHelperCourses dataBaseHelperCourses = new DataBaseHelperCourses(CourseActivity.this, dbName);
                        boolean success = dataBaseHelperCourses.addOne(courses);
                        if (success) {
                            Toast.makeText(CourseActivity.this, "Hole Added", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        nameEntry.setError("Please enter a name");
                    }
                }
            }
        });

        courseList.setOnItemClickListener((parent, view, position, id) -> {
            Courses courses = (Courses) parent.getItemAtPosition(position);
            String dbName = nameEntry.getText().toString();
            if(!dbName.isEmpty()) {
                DataBaseHelperCourses dataBaseHelperCourses = new DataBaseHelperCourses(CourseActivity.this, dbName);
                dataBaseHelperCourses.deleteOne(courses);
                Toast.makeText(CourseActivity.this, "Score Deleted", Toast.LENGTH_SHORT).show();
            }else{
                nameEntry.setError("Please enter a name");
            }
        });
    }
}