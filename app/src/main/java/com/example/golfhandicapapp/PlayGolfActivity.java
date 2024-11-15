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
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class PlayGolfActivity extends AppCompatActivity {
    Button submitButton;
    EditText courseName, golfer1Name, golfer2Name, golfer3Name, golfer4Name, golfer1Handicap,
            golfer2Handicap, golfer3Handicap, golfer4Handicap, golfer1Strokes, golfer2Strokes,
            golfer3Strokes, golfer4Strokes;
    ListView holeView, outputListView;
    ImageButton dashButton, scoreButton, bagButton, courseButton, playerButton;
    ArrayAdapter<String> outputAdapter;
    List<String> outputMessages;

    public int golfer1StrokesInt, golfer2StrokesInt, golfer3StrokesInt, golfer4StrokesInt;
    public String name1, name2, name3, name4;

    public void updateExtraStrokes(List<Courses> sorted){

        try{
            golfer1StrokesInt = Integer.parseInt(golfer1Handicap.getText().toString());
        }catch(Exception e){
            golfer1StrokesInt = -1;
        }
        try{
            golfer2StrokesInt = Integer.parseInt(golfer2Handicap.getText().toString());
        }catch(Exception e){
            golfer2StrokesInt = -1;
        }
        try{
            golfer3StrokesInt = Integer.parseInt(golfer3Handicap.getText().toString());
        }catch(Exception e){
            golfer3StrokesInt = -1;
        }
        try{
            golfer4StrokesInt = Integer.parseInt(golfer4Handicap.getText().toString());
        }catch(Exception e){
            golfer4StrokesInt = -1;
        }

        //update handicap text if handicap was entered
        updateHandicapText(golfer1StrokesInt, golfer1Handicap, "Handicap: {0}");
        updateHandicapText(golfer2StrokesInt, golfer2Handicap, "Handicap: {0}");
        updateHandicapText(golfer3StrokesInt, golfer3Handicap, "Handicap: {0}");
        updateHandicapText(golfer4StrokesInt, golfer4Handicap, "Handicap: {0}");

        int smallest = calculateSmallestStrokes();

        golfer1StrokesInt = golfer1StrokesInt - smallest;
        golfer2StrokesInt = golfer2StrokesInt - smallest;
        golfer3StrokesInt = golfer3StrokesInt - smallest;
        golfer4StrokesInt = golfer4StrokesInt - smallest;

        //update strokes text if strokes were calculated
        updateHandicapText(golfer1StrokesInt, golfer1Strokes, "Strokes: {0}");
        updateHandicapText(golfer2StrokesInt, golfer2Strokes, "Strokes: {0}");
        updateHandicapText(golfer3StrokesInt, golfer3Strokes, "Strokes: {0}");
        updateHandicapText(golfer4StrokesInt, golfer4Strokes, "Strokes: {0}");

        //gets golfers names
        name1 = golfer1Name.getText().toString();
        name2 = golfer2Name.getText().toString();
        name3 = golfer3Name.getText().toString();
        name4 = golfer4Name.getText().toString();

        List<String> messages = generateMessages(sorted);
        updateListView(messages);
    }

    private List<String> generateMessages(List<Courses> sorted) {
        List<String> messages = new ArrayList<>();
        if (golfer1StrokesInt > 0) {
            messages.add(name1 + " gets a stroke on hole(s): " + getHoles(sorted, golfer1StrokesInt));
        }
        if (golfer2StrokesInt > 0) {
            messages.add(name2 + " gets a stroke on hole(s): " + getHoles(sorted, golfer2StrokesInt));
        }
        if (golfer3StrokesInt > 0) {
            messages.add(name3 + " gets a stroke on hole(s): " + getHoles(sorted, golfer3StrokesInt));
        }
        if (golfer4StrokesInt > 0) {
            messages.add(name4 + " gets a stroke on hole(s): " + getHoles(sorted, golfer4StrokesInt));
        }
        return messages;
    }

    @NonNull
    private String getHoles(List<Courses> sorted, int strokes) {
        StringBuilder holes = new StringBuilder();
        for (int i = 0; i < strokes; i++) {
            if (i > 0) {
                holes.append(", ");
            }
            holes.append(sorted.get(i).getHoleNumber());
        }
        return holes.toString();
    }

    private void updateListView(List<String> messages) {
        outputMessages.clear();
        outputMessages.addAll(messages);
        outputAdapter.notifyDataSetChanged();
    }

    private int calculateSmallestStrokes() {
        int adjusted1 = golfer1StrokesInt == -1 ? Integer.MAX_VALUE : golfer1StrokesInt;
        int adjusted2 = golfer2StrokesInt == -1 ? Integer.MAX_VALUE : golfer2StrokesInt;
        int adjusted3 = golfer3StrokesInt == -1 ? Integer.MAX_VALUE : golfer3StrokesInt;
        int adjusted4 = golfer4StrokesInt == -1 ? Integer.MAX_VALUE : golfer4StrokesInt;
        return Math.min(adjusted1, Math.min(adjusted2, Math.min(adjusted3, adjusted4)));
    }

    private void updateHandicapText(int strokesInt, TextView textView, String format) {
        if (strokesInt > -1) {
            textView.setText(MessageFormat.format(format, strokesInt));
        }
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

        outputListView = findViewById(R.id.outputListView);
        outputMessages = new ArrayList<>();
        outputAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, outputMessages);
        outputListView.setAdapter(outputAdapter);

        holeView = findViewById(R.id.holeView);


        submitButton.setOnClickListener(v -> {
            if (v.getId() == R.id.submitButton) {

                String dbName = courseName.getText().toString();
                if(!dbName.isEmpty()) {
                    DataBaseHelperCourses dataBaseHelperCourses = new DataBaseHelperCourses(PlayGolfActivity.this, dbName);
                    if (DataBaseHelperCourses.doesDatabaseExist(PlayGolfActivity.this, dbName)) {
                        List<Courses> everyone = dataBaseHelperCourses.getAllHoles();
                        List<Courses> sorted = dataBaseHelperCourses.getHolesSortedByHandicap();
                        updateExtraStrokes(sorted);
                        ArrayAdapter<Courses> courseArrayAdapter = new ArrayAdapter<>(PlayGolfActivity.this, android.R.layout.simple_list_item_1, everyone);
                        holeView.setAdapter(courseArrayAdapter);
                    } else {
                        courseName.setError("Please enter a course that you have uploaded");
                    }
                }else{
                    courseName.setError("Please enter a course name");
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