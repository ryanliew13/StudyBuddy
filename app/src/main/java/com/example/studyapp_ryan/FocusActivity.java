package com.example.studyapp_ryan;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class FocusActivity extends AppCompatActivity {

    private AudioManager audioManager;
    private MediaPlayer ambientSoundPlayer;
    private Button startFocusButton;
    private Button stopFocusButton;
    private Button timePickerButton;
    private TextView focusTimerTextView;

    private int focusLength = 10 * 60; // Default to 10 minutes
    private int focusElapsedTime = 0;
    private final Handler focusHandler = new Handler();
    private Runnable focusRunnable;
    private ImageView gifImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_focus);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        ambientSoundPlayer = MediaPlayer.create(this, R.raw.focus);
        ambientSoundPlayer.setLooping(true);

        startFocusButton = findViewById(R.id.startFocusButton);
        stopFocusButton = findViewById(R.id.stopFocusButton);
        timePickerButton = findViewById(R.id.timePickerButton);
        focusTimerTextView = findViewById(R.id.focusTimerTextView);
        gifImageView = findViewById(R.id.gifImageView);

        Glide.with(this)
                .asGif()
                .load(R.raw.lofi) // Replace with your GIF file name in res/drawable folder
                .into(gifImageView);
        // Timer to update every second
        focusRunnable = new Runnable() {
            @Override
            public void run() {
                focusElapsedTime++;
                updateFocusTimer();
                if (focusElapsedTime < focusLength) {
                    focusHandler.postDelayed(this, 1000);
                } else {
                    endFocusEnvironment();
                }
            }
        };

        startFocusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFocusEnvironment();
            }
        });

        stopFocusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopFocusEnvironment();
            }
        });

        timePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog();
            }
        });
    }

    private void showTimePickerDialog() {
        // Create an AlertDialog to allow the user to input focus time in minutes
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Focus Duration (in minutes)");

        // Create an EditText for the input
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER); // Input only numbers
        builder.setView(input);

        // Set up the dialog buttons
        builder.setPositiveButton("OK", (dialog, which) -> {
            String inputText = input.getText().toString();
            if (!inputText.isEmpty()) {
                int minutes = Integer.parseInt(inputText);
                focusLength = minutes * 60; // Convert minutes to seconds
                focusElapsedTime = 0; // Reset elapsed time
                updateFocusTimer(); // Update timer display
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    private void startFocusEnvironment() {
        // Silence notifications
        audioManager.setStreamMute(AudioManager.STREAM_NOTIFICATION, true);

        // Play ambient sound
        ambientSoundPlayer.start();

        // Start timer
        focusElapsedTime = 0; // Reset elapsed time
        focusHandler.post(focusRunnable);

        startFocusButton.setEnabled(false);
        stopFocusButton.setEnabled(true);
    }

    private void stopFocusEnvironment() {
        focusHandler.removeCallbacks(focusRunnable);

        if (ambientSoundPlayer.isPlaying()) {
            ambientSoundPlayer.stop();
            try {
                ambientSoundPlayer.prepare();
                ambientSoundPlayer.seekTo(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        audioManager.setStreamMute(AudioManager.STREAM_NOTIFICATION, false);
        focusTimerTextView.setText("Focus Stopped");
        focusElapsedTime = 0;
        startFocusButton.setEnabled(true);
        stopFocusButton.setEnabled(false);
    }

    private void updateFocusTimer() {
        int remainingTime = focusLength - focusElapsedTime;
        int hours = remainingTime / 3600; // Calculate hours
        int minutes = (remainingTime % 3600) / 60; // Calculate remaining minutes
        int seconds = remainingTime % 60; // Calculate remaining seconds

        // Display the timer in "HH:MM:SS" format
        focusTimerTextView.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
    }

    private void endFocusEnvironment() {
        if (ambientSoundPlayer.isPlaying()) {
            ambientSoundPlayer.stop();
        }

        audioManager.setStreamMute(AudioManager.STREAM_NOTIFICATION, false);
        focusTimerTextView.setText("Focus Complete!");
        focusElapsedTime = 0;
        startFocusButton.setEnabled(true);
        stopFocusButton.setEnabled(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        focusHandler.removeCallbacks(focusRunnable);
        ambientSoundPlayer.release();
    }

}
