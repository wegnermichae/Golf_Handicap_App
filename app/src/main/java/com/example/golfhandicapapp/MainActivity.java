package com.example.golfhandicapapp;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        final ImageButton DashButton = findViewById(R.id.DashButton);
        final ImageButton ScoreButton = findViewById(R.id.ScoreButton);
        final ImageButton PlayerButton = findViewById(R.id.PlayerButton);
        final ImageButton CourseButton = findViewById(R.id.CourseButton);
        final ImageButton BagButton = findViewById(R.id.BagButton);
        SeekBar scoreSeekBar = findViewById(R.id.scoreSeekBar);
        SeekBar handicapSeekBar = findViewById(R.id.handicapSeekBar);
        TextView headingText = findViewById(R.id.headingText);
        TextView previousScore = findViewById(R.id.previousScore);
        TextView currentHandicap = findViewById(R.id.currentHandicap);

    }
}