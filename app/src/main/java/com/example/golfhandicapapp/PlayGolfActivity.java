package com.example.golfhandicapapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PlayGolfActivity extends AppCompatActivity {

    Button submitButton;

    EditText courseName, golfer1Name, golfer2Name, golfer3Name, golfer4Name, golfer1Handicap,
            golfer2Handicap, golfer3Handicap, golfer4Handicap, golfer1Strokes, golfer2Strokes,
            golfer3Strokes, golfer4Strokes, output;


    ListView holeView;
    ImageButton dashButton, scoreButton, bagButton, courseButton, playerButton;

    public int golfer1StrokesInt, golfer2StrokesInt, golfer3StrokesInt, golfer4StrokesInt;

    public int strokes1, strokes2, strokes3, strokes4;
    public String name1, name2, name3, name4;

    public String holes1, holes2, holes3, holes4;

    public void updateExtraStrokes(List<Courses> sorted){
        golfer1StrokesInt = Integer.parseInt(golfer1Handicap.getText().toString());
        golfer2StrokesInt = Integer.parseInt(golfer2Handicap.getText().toString());
        golfer3StrokesInt = Integer.parseInt(golfer3Handicap.getText().toString());
        golfer4StrokesInt = Integer.parseInt(golfer4Handicap.getText().toString());

        golfer1Handicap.setText(MessageFormat.format("Handicap: {0}", golfer1StrokesInt));
        golfer2Handicap.setText(MessageFormat.format("Handicap: {0}", golfer2StrokesInt));
        golfer3Handicap.setText(MessageFormat.format("Handicap: {0}", golfer3StrokesInt));
        golfer4Handicap.setText(MessageFormat.format("Handicap: {0}", golfer4StrokesInt));

        int smallest = Math.min(golfer1StrokesInt, Math.min(golfer2StrokesInt, Math.min(golfer3StrokesInt, golfer4StrokesInt)));

        golfer1StrokesInt = golfer1StrokesInt - smallest;
        golfer2StrokesInt = golfer2StrokesInt - smallest;
        golfer3StrokesInt = golfer3StrokesInt - smallest;
        golfer4StrokesInt = golfer4StrokesInt - smallest;

        golfer1Strokes.setText(MessageFormat.format("Strokes: {0}", golfer1StrokesInt));
        golfer2Strokes.setText(MessageFormat.format("Strokes: {0}", golfer2StrokesInt));
        golfer3Strokes.setText(MessageFormat.format("Strokes: {0}", golfer3StrokesInt));
        golfer4Strokes.setText(MessageFormat.format("Strokes: {0}", golfer4StrokesInt));

        name1 = golfer1Name.getText().toString();
        name2 = golfer2Name.getText().toString();
        name3 = golfer3Name.getText().toString();
        name4 = golfer4Name.getText().toString();

        StringBuilder holes1 = new StringBuilder();

        for (int i = 0; i < golfer1StrokesInt; i++) {
            if (i > 0) {
                holes1.append(", ");
            }
            holes1.append(sorted.get(i).getHoleNumber());
        }

        output.setText(MessageFormat.format("{0} gets a stroke on hole(s): {1}", name1 , holes1));

    }

    //ensure all of this can handle less than 4 golfers
    public void updateOutput(List<Courses> sorted) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_play_golf);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dashButton = findViewById(R.id.dashButton5);
        scoreButton = findViewById(R.id.scoreButton5);
        bagButton = findViewById(R.id.bagButton5);
        courseButton = findViewById(R.id.courseButton5);
        playerButton = findViewById(R.id.playerButton5);
        submitButton = findViewById(R.id.submitButton);

        courseName = findViewById(R.id.courseName);
        golfer1Name = findViewById(R.id.golfer1Name);
        golfer2Name = findViewById(R.id.golfer2Name);
        golfer3Name = findViewById(R.id.golfer3Name);
        golfer4Name = findViewById(R.id.golfer4Name);
        golfer1Handicap = findViewById(R.id.golfer1Handicap);
        golfer2Handicap = findViewById(R.id.golfer2Handicap);
        golfer3Handicap = findViewById(R.id.golfer3Handicap);
        golfer4Handicap = findViewById(R.id.golfer4Handicap);
        golfer1Strokes = findViewById(R.id.extraStrokes1);
        golfer2Strokes = findViewById(R.id.extraStrokes2);
        golfer3Strokes = findViewById(R.id.extraStrokes3);
        golfer4Strokes = findViewById(R.id.extraStrokes4);
        output = findViewById(R.id.output);

        holeView = findViewById(R.id.holeView);





        submitButton.setOnClickListener(v -> {
            if (v.getId() == R.id.submitButton) {

                String dbName = courseName.getText().toString();
                if(!dbName.isEmpty()) {
                    DataBaseHelperCourses dataBaseHelperCourses = new DataBaseHelperCourses(PlayGolfActivity.this, dbName);

                    List<Courses> everyone = dataBaseHelperCourses.getAllHoles();
                    List<Courses> sorted = dataBaseHelperCourses.getHolesSortedByHandicap();
                    //output.setText(String.valueOf(sorted.get(0).getHoleNumber()));//this works
                    updateExtraStrokes(sorted);
                    ArrayAdapter<Courses> courseArrayAdapter = new ArrayAdapter<>(PlayGolfActivity.this, android.R.layout.simple_list_item_1, everyone);
                    holeView.setAdapter(courseArrayAdapter);
                }else{
                    courseName.setError("Please enter a name");
                }
            }
        });


        //the following listeners will allow for functionality of the specified button clicks
        dashButton.setOnClickListener(v -> {
            if (v.getId() == R.id.dashButton5) {
                Intent intent = new Intent(PlayGolfActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        scoreButton.setOnClickListener(v -> {
            if (v.getId() == R.id.scoreButton5) {
                Intent intent = new Intent(PlayGolfActivity.this, ScoreActivity.class);
                startActivity(intent);
            }
        });

        playerButton.setOnClickListener(v -> {
            if (v.getId() == R.id.playerButton5) {
                Intent intent = new Intent(PlayGolfActivity.this, PlayerActivity.class);
                startActivity(intent);
            }
        });

        courseButton.setOnClickListener(v -> {
            if (v.getId() == R.id.courseButton5) {
                Intent intent = new Intent(PlayGolfActivity.this, CourseActivity.class);
                startActivity(intent);
            }
        });

        bagButton.setOnClickListener(v -> {
            if (v.getId() == R.id.bagButton5) {
                Intent intent = new Intent(PlayGolfActivity.this, BagActivity.class);
                startActivity(intent);
            }
        });




    }
}