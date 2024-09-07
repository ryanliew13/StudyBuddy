package com.example.studyapp_ryan;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TimerActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "StudyBuddyPrefs";
    private static final String PREF_POMODORO_LENGTH = "pomodoroLength";
    private static final String PREF_SHORT_BREAK_LENGTH = "shortBreakLength";
    private static final String PREF_LONG_BREAK_LENGTH = "longBreakLength";

    private static final int DEFAULT_POMODORO_LENGTH = 25;
    private static final int DEFAULT_SHORT_BREAK_LENGTH = 5;
    private static final int DEFAULT_LONG_BREAK_LENGTH = 15;

    private CircleTimerView circleTimerView;
    private TextView stateTextView, roundTextView;
    private Button startButton, pauseButton, resetButton, settingsButton;
    private int pomodoroLength, shortBreakLength, longBreakLength;
    private boolean isRunning = false;
    private long startTime;
    private long pausedTime;
    private MediaPlayer mediaPlayer;
    private Vibrator vibrator;
    private Handler handler = new Handler();
    private SharedPreferences prefs;

    private enum TimerState {
        POMODORO, SHORT_BREAK, LONG_BREAK
    }

    private TimerState currentState = TimerState.POMODORO;
    private int pomodoroCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        // Prevent screen from turning off while timer is active
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        // Initialize UI elements
        circleTimerView = findViewById(R.id.circleTimerView);
        stateTextView = findViewById(R.id.stateTextView);
        roundTextView = findViewById(R.id.roundTextView);
        startButton = findViewById(R.id.startButton);
        pauseButton = findViewById(R.id.pauseButton);
        resetButton = findViewById(R.id.resetButton);
        settingsButton = findViewById(R.id.settingsButton);

        // Initialize media player and vibrator
        mediaPlayer = MediaPlayer.create(this, R.raw.bell_sound);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        // Load saved settings
        prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        loadSettings();

        // Update UI with initial timer and state
        updateTimerDisplay(getCurrentTimerLength() * 60);
        updateStateDisplay();

        // Set button click listeners
        startButton.setOnClickListener(v -> startTimer());
        pauseButton.setOnClickListener(v -> pauseTimer());
        resetButton.setOnClickListener(v -> resetTimer());
        settingsButton.setOnClickListener(v -> startActivity(new Intent(TimerActivity.this, TimerSettingsActivity.class)));
    }

    private void startTimer() {
        if (!isRunning) {
            isRunning = true;
            startTime = System.currentTimeMillis() - (pausedTime > 0 ? pausedTime : 0);
            handler.post(updateTimerRunnable);
        }
    }

    private void pauseTimer() {
        if (isRunning) {
            isRunning = false;
            pausedTime = System.currentTimeMillis() - startTime;
            handler.removeCallbacks(updateTimerRunnable);
        }
    }

    private void resetTimer() {
        isRunning = false;
        pausedTime = 0;
        handler.removeCallbacks(updateTimerRunnable);
        updateTimerDisplay(getCurrentTimerLength() * 60);
    }

    private Runnable updateTimerRunnable = new Runnable() {
        @Override
        public void run() {
            if (isRunning) {
                long elapsed = (System.currentTimeMillis() - startTime) / 1000;
                long remainingTime = getCurrentTimerLength() * 60 - elapsed;

                if (remainingTime <= 0) {
                    updateTimerDisplay(0);
                    isRunning = false;
                    playBellSound();
                    moveToNextState();
                } else {
                    updateTimerDisplay(remainingTime);
                    handler.postDelayed(this, 1000);
                }
            }
        }
    };

    private void moveToNextState() {
        switch (currentState) {
            case POMODORO:
                pomodoroCount++;
                if (pomodoroCount % 4 == 0) {
                    currentState = TimerState.LONG_BREAK;
                } else {
                    currentState = TimerState.SHORT_BREAK;
                }
                break;
            case SHORT_BREAK:
            case LONG_BREAK:
                currentState = TimerState.POMODORO;
                break;
        }
        updateStateDisplay();
        resetTimer();
    }

    private int getCurrentTimerLength() {
        switch (currentState) {
            case POMODORO:
                return pomodoroLength;
            case SHORT_BREAK:
                return shortBreakLength;
            case LONG_BREAK:
                return longBreakLength;
            default:
                return pomodoroLength;
        }
    }

    private void updateTimerDisplay(long seconds) {
        int minutes = (int) (seconds / 60);
        int secs = (int) (seconds % 60);
        String timeText = String.format("%02d:%02d", minutes, secs);

        if (circleTimerView != null) {
            circleTimerView.setTimeText(timeText);
            circleTimerView.setProgress((float) seconds / (getCurrentTimerLength() * 60) * 100);
        }
    }

    private void updateStateDisplay() {
        switch (currentState) {
            case POMODORO:
                stateTextView.setText("Pomodoro");
                break;
            case SHORT_BREAK:
                stateTextView.setText("Short Break");
                break;
            case LONG_BREAK:
                stateTextView.setText("Long Break");
                break;
        }

        int roundsLeft = (4 - pomodoroCount % 4) % 4;
        roundTextView.setText("Round: " + (pomodoroCount % 4 + 1) + " / 4\nSessions until long break: " + roundsLeft);
    }

    private void loadSettings() {
        pomodoroLength = prefs.getInt(PREF_POMODORO_LENGTH, DEFAULT_POMODORO_LENGTH);
        shortBreakLength = prefs.getInt(PREF_SHORT_BREAK_LENGTH, DEFAULT_SHORT_BREAK_LENGTH);
        longBreakLength = prefs.getInt(PREF_LONG_BREAK_LENGTH, DEFAULT_LONG_BREAK_LENGTH);
    }

    private void playBellSound() {
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }

        if (vibrator != null) {
            vibrator.vibrate(500);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        if (vibrator != null) {
            vibrator.cancel();
        }
        handler.removeCallbacks(updateTimerRunnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadSettings();
        if (!isRunning) {
            updateTimerDisplay(getCurrentTimerLength() * 60);
        }
        updateStateDisplay();
    }
}





