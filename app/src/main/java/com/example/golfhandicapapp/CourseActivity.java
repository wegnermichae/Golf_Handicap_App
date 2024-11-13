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

        ImageButton DashButton = findViewById(R.id.dashButton3);
        ImageButton ScoreButton = findViewById(R.id.scoreButton3);
        ImageButton PlayerButton = findViewById(R.id.playerButton3);
        ImageButton CourseButton = findViewById(R.id.courseButton3);
        ImageButton BagButton = findViewById(R.id.bagButton3);
        ImageButton courseAdd = findViewById(R.id.courseAdd);
        EditText nameEntry = findViewById(R.id.nameEntry);
        EditText holeEntry = findViewById(R.id.holeEntry);
        EditText parEntry = findViewById(R.id.parEntry);
        EditText handicapEntry = findViewById(R.id.handicapEntry);
        ListView courseList = findViewById(R.id.courseList);
        Button viewButton = findViewById(R.id.viewButton);

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
                        courses = new Courses(Integer.parseInt(holeEntry.getText().toString()), Integer.parseInt(parEntry.getText().toString()), Integer.parseInt(handicapEntry.getText().toString()));
                    } catch (Exception e) {
                        courses = new Courses(Integer.parseInt(holeEntry.getText().toString()), 0, 0);
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



        //the following listeners will allow for functionality of the specified button clicks
        DashButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.dashButton3) {
                    Intent intent = new Intent(CourseActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });

        ScoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.scoreButton3) {
                    Intent intent = new Intent(CourseActivity.this, ScoreActivity.class);
                    startActivity(intent);
                }
            }
        });

        PlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.playerButton3) {
                    Intent intent = new Intent(CourseActivity.this, PlayerActivity.class);
                    startActivity(intent);
                }
            }
        });

        CourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.courseButton3) {
                    Intent intent = new Intent(CourseActivity.this, CourseActivity.class);
                    startActivity(intent);
                }
            }
        });

        BagButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.bagButton3) {
                    Intent intent = new Intent(CourseActivity.this, BagActivity.class);
                    startActivity(intent);
                }
            }
        });






    }
}