package com.example.studyapp_ryan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.cardview.widget.CardView;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;

public class MenuActivity extends AppCompatActivity {
    CardView goalCard;
    CardView dictionaryCard;
    CardView quizCard;
    CardView timerCard;
    CardView focusCard;
    ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        goalCard = findViewById(R.id.goalCard);
        goalCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        dictionaryCard = findViewById(R.id.dictionaryCard);
        dictionaryCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, DictionaryActivity.class);
                startActivity(intent);
            }
        });
        quizCard = findViewById(R.id.quizCard);
        quizCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, MainQuizActivity.class);
                startActivity(intent);
            }
        });
        timerCard = findViewById(R.id.timerCard);
        timerCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, TimerActivity.class);
                startActivity(intent);
            }
        });
        focusCard = findViewById(R.id.focusCard);
        focusCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, FocusActivity.class);
                startActivity(intent);
            }
        });

       showMenu();
    }

    void showMenu() {

        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Sign out from Firebase
                FirebaseAuth.getInstance().signOut();
                // Start the LoginActivity
                startActivity(new Intent(MenuActivity.this, LoginActivity.class));
                // Finish the current MenuActivity
                finish();
            }
        });
    }
}