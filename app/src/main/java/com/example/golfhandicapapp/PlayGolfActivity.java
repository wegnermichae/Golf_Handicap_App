/**
 * PlayGolfActivity handles the Play Golf view of the Golf Handicap App, where users can enter golfer details,
 * view hole information, and calculate extra strokes based on their handicaps.
 * This activity interacts with the database to retrieve and display golf course and hole data,
 * and also calculates and displays the strokes golfers receive for each hole.
 * <p>
 * The user can input golfer names and handicaps, submit the data, and the activity will display the relevant information
 * such as the calculated strokes each golfer gets on specific holes. It also updates the list view with messages about the golfers' strokes.
 * The activity provides navigation to other sections of the app through various buttons.
 * </p>
 * <p>
 * Author: Michael Wegner
 * Class: PlayGolfActivity
 * Purpose: To manage the Play Golf view, calculate golfer strokes based on their handicap,
 *         and provide navigation to other parts of the app.
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

    // Constants for formatting
    private static final String HANDICAP_FORMAT = "{0}";
    private static final String STROKES_FORMAT = "{0}";

    // Golfer data variables
    private int golfer1StrokesInt, golfer2StrokesInt, golfer3StrokesInt, golfer4StrokesInt;
    private String name1, name2, name3, name4;

    /**
     * Updates the strokes for a golfer based on the entered handicap.
     *
     * @param handicapField The EditText field for the golfer's handicap.
     * @param strokesField The TextView to display the calculated strokes.
     * @return The calculated number of strokes.
     */
    private int updateGolferStrokes(EditText handicapField, TextView strokesField) {
        try{
            int strokes = Integer.parseInt(handicapField.getText().toString());
            updateHandicapText(strokes, strokesField, PlayGolfActivity.STROKES_FORMAT);
            return strokes;
        } catch (Exception e){
            updateHandicapText(-1, strokesField, PlayGolfActivity.STROKES_FORMAT);
            return -1;
        }
    }

    /**
     * Updates the strokes for each golfer based on their handicaps and recalculates strokes
     * relative to the smallest number of strokes.
     *
     * @param sorted A sorted list of golf holes for the course.
     */
    public void updateExtraStrokes(List<Holes> sorted){

        // Gets golfers names
        name1 = golfer1Name.getText().toString();
        name2 = golfer2Name.getText().toString();
        name3 = golfer3Name.getText().toString();
        name4 = golfer4Name.getText().toString();

        //update strokes each golfer gets if handicap was entered
        golfer1StrokesInt = updateGolferStrokes(golfer1Handicap, golfer1Strokes);
        golfer2StrokesInt = updateGolferStrokes(golfer2Handicap, golfer2Strokes);
        golfer3StrokesInt = updateGolferStrokes(golfer3Handicap, golfer3Strokes);
        golfer4StrokesInt = updateGolferStrokes(golfer4Handicap, golfer4Strokes);

        DataBaseHelperPlayers dataBaseHelperPlayers = new DataBaseHelperPlayers(PlayGolfActivity.this);
        if(dataBaseHelperPlayers.getOneGolfer(name1) != -1){
            golfer1StrokesInt = dataBaseHelperPlayers.getOneGolfer(name1);
        }
        if(dataBaseHelperPlayers.getOneGolfer(name2) != -1){
            golfer2StrokesInt = dataBaseHelperPlayers.getOneGolfer(name2);
        }
        if(dataBaseHelperPlayers.getOneGolfer(name3) != -1){
            golfer3StrokesInt = dataBaseHelperPlayers.getOneGolfer(name3);
        }
        if(dataBaseHelperPlayers.getOneGolfer(name4) != -1){
            golfer4StrokesInt = dataBaseHelperPlayers.getOneGolfer(name4);
        }

        //update handicap text if handicap was entered
        updateHandicapText(golfer1StrokesInt, golfer1Handicap, HANDICAP_FORMAT);
        updateHandicapText(golfer2StrokesInt, golfer2Handicap, HANDICAP_FORMAT);
        updateHandicapText(golfer3StrokesInt, golfer3Handicap, HANDICAP_FORMAT);
        updateHandicapText(golfer4StrokesInt, golfer4Handicap, HANDICAP_FORMAT);

        int smallest = calculateSmallestStrokes();

        // Adjust strokes based on smallest number
        golfer1StrokesInt = golfer1StrokesInt - smallest;
        golfer2StrokesInt = golfer2StrokesInt - smallest;
        golfer3StrokesInt = golfer3StrokesInt - smallest;
        golfer4StrokesInt = golfer4StrokesInt - smallest;

        // Update strokes text if strokes were calculated
        updateHandicapText(golfer1StrokesInt, golfer1Strokes, "{0}");
        updateHandicapText(golfer2StrokesInt, golfer2Strokes, "{0}");
        updateHandicapText(golfer3StrokesInt, golfer3Strokes, "{0}");
        updateHandicapText(golfer4StrokesInt, golfer4Strokes, "{0}");

        // Generate and update messages
        List<String> messages = generateMessages(sorted);
        updateListView(messages);
    }

    /**
     * Generates a list of messages for each golfer indicating which holes they get a stroke on.
     *
     * @param sorted A sorted list of golf holes for the course.
     * @return A list of messages for each golfer.
     */
    private List<String> generateMessages(List<Holes> sorted) {
        List<String> messages = new ArrayList<>();
        int[] strokes = {golfer1StrokesInt, golfer2StrokesInt, golfer3StrokesInt, golfer4StrokesInt};
        String[] names = {name1, name2, name3, name4};

        for(int i = 0; i < 4; i++){
            if(strokes[i] > 0){
                messages.add(names[i] + " gets a stroke on hole(s): " + getHoles(sorted, strokes[i]));
            }
        }
        return messages;
    }

    /**
     * Returns a string representing the holes that a golfer gets strokes on.
     *
     * @param sorted A sorted list of golf holes.
     * @param strokes The number of strokes the golfer receives.
     * @return A comma-separated list of hole numbers.
     */
    @NonNull
    private String getHoles(List<Holes> sorted, int strokes) {
        StringBuilder holes = new StringBuilder();
        for (int i = 0; i < strokes; i++) {
            if (i > 0) {
                holes.append(", ");
            }
            holes.append(sorted.get(i).getHoleNumber());
        }
        return holes.toString();
    }

    /**
     * Updates the ListView to display the generated messages.
     *
     * @param messages The list of messages to display.
     */
    private void updateListView(List<String> messages) {
        outputMessages.clear();
        outputMessages.addAll(messages);
        outputAdapter.notifyDataSetChanged();
    }

    /**
     * Calculates the smallest number of strokes among the four golfers.
     *
     * @return The smallest number of strokes.
     */
    private int calculateSmallestStrokes() {
        int adjusted1 = golfer1StrokesInt == -1 ? Integer.MAX_VALUE : golfer1StrokesInt;
        int adjusted2 = golfer2StrokesInt == -1 ? Integer.MAX_VALUE : golfer2StrokesInt;
        int adjusted3 = golfer3StrokesInt == -1 ? Integer.MAX_VALUE : golfer3StrokesInt;
        int adjusted4 = golfer4StrokesInt == -1 ? Integer.MAX_VALUE : golfer4StrokesInt;
        return Math.min(adjusted1, Math.min(adjusted2, Math.min(adjusted3, adjusted4)));
    }

    /**
     * Updates the given TextView with the formatted handicap or strokes text.
     *
     * @param strokesInt The number of strokes or handicap value.
     * @param textView The TextView to update.
     * @param format The format string to apply to the value.
     */
    private void updateHandicapText(int strokesInt, TextView textView, String format) {
        if (strokesInt > -1) {
            textView.setText(MessageFormat.format(format, strokesInt));
        }
    }

    /**
     * Sets up navigation buttons to allow the user to navigate to different activities within the app.
     */
    private void setupButtonNav(){
        dashButton.setOnClickListener(v -> navigateToActivity(MainActivity.class));
        scoreButton.setOnClickListener(v -> navigateToActivity(ScoreActivity.class));
        bagButton.setOnClickListener(v -> navigateToActivity(BagActivity.class));
        courseButton.setOnClickListener(v -> navigateToActivity(CourseActivity.class));
        playerButton.setOnClickListener(v -> navigateToActivity(PlayerActivity.class));
    }

    /**
     * Navigates to the specified activity.
     *
     * @param activityClass The class of the activity to navigate to.
     */
    private void navigateToActivity(Class<?> activityClass) {
        Intent intent = new Intent(PlayGolfActivity.this, activityClass);
        startActivity(intent);
    }

    /**
     * Initializes the activity, sets up the UI elements, and handles button clicks.
     *
     * @param savedInstanceState The saved instance state of the activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_play_golf);

        // Apply system bar insets for proper padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize UI components
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

        setupButtonNav();

        // Set up the submit button click listener
        submitButton.setOnClickListener(v -> {
            if (v.getId() == R.id.submitButton) {

                String dbName = courseName.getText().toString();
                if(!dbName.isEmpty()) {
                    DataBaseHelperHoles dataBaseHelperHoles = new DataBaseHelperHoles(PlayGolfActivity.this, dbName);
                    if (DataBaseHelperHoles.doesDatabaseExist(PlayGolfActivity.this, dbName)) {
                        List<Holes> everyone = dataBaseHelperHoles.getAllHoles();
                        List<Holes> sorted = dataBaseHelperHoles.getHolesSortedByHandicap();
                        updateExtraStrokes(sorted);
                        ArrayAdapter<Holes> courseArrayAdapter = new ArrayAdapter<>(PlayGolfActivity.this, android.R.layout.simple_list_item_1, everyone);
                        holeView.setAdapter(courseArrayAdapter);
                    } else {
                        courseName.setError("Please enter a course that you have uploaded");
                    }
                }else{
                    courseName.setError("Please enter a course name");
                }
            }
        });
    }
}