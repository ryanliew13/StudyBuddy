package com.example.studyapp_ryan;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class TimerSettingsActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "StudyBuddyPrefs";
    private static final String PREF_POMODORO_LENGTH = "pomodoroLength";
    private static final String PREF_SHORT_BREAK_LENGTH = "shortBreakLength";
    private static final String PREF_LONG_BREAK_LENGTH = "longBreakLength";

    private static final int DEFAULT_POMODORO_LENGTH = 25;
    private static final int DEFAULT_SHORT_BREAK_LENGTH = 5;
    private static final int DEFAULT_LONG_BREAK_LENGTH = 15;

    private EditText pomodoroLengthEditText;
    private EditText shortBreakLengthEditText;
    private EditText longBreakLengthEditText;
    private Button saveSettingsButton;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_settings);

        prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        pomodoroLengthEditText = findViewById(R.id.pomodoroLength);
        shortBreakLengthEditText = findViewById(R.id.shortBreakLength);
        longBreakLengthEditText = findViewById(R.id.longBreakLength);
        saveSettingsButton = findViewById(R.id.saveSettingsButton);

        saveSettingsButton.setOnClickListener(v -> saveSettings());

        loadSettings();
    }

    private void loadSettings() {
        pomodoroLengthEditText.setText(String.valueOf(prefs.getInt(PREF_POMODORO_LENGTH, DEFAULT_POMODORO_LENGTH)));
        shortBreakLengthEditText.setText(String.valueOf(prefs.getInt(PREF_SHORT_BREAK_LENGTH, DEFAULT_SHORT_BREAK_LENGTH)));
        longBreakLengthEditText.setText(String.valueOf(prefs.getInt(PREF_LONG_BREAK_LENGTH, DEFAULT_LONG_BREAK_LENGTH)));
    }

    private void saveSettings() {
        try {
            int pomodoroLength = Integer.parseInt(pomodoroLengthEditText.getText().toString());
            int shortBreakLength = Integer.parseInt(shortBreakLengthEditText.getText().toString());
            int longBreakLength = Integer.parseInt(longBreakLengthEditText.getText().toString());

            if (pomodoroLength <= 0 || shortBreakLength <= 0 || longBreakLength <= 0) {
                throw new NumberFormatException("Duration must be positive");
            }

            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt(PREF_POMODORO_LENGTH, pomodoroLength);
            editor.putInt(PREF_SHORT_BREAK_LENGTH, shortBreakLength);
            editor.putInt(PREF_LONG_BREAK_LENGTH, longBreakLength);
            editor.apply();

            Toast.makeText(this, R.string.settings_saved, Toast.LENGTH_SHORT).show();
            finish();
        } catch (NumberFormatException e) {
            Toast.makeText(this, R.string.invalid_input, Toast.LENGTH_LONG).show();
        }
    }
}


