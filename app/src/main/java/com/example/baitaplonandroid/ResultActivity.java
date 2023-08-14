package com.example.baitaplonandroid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.baitaplonandroid.model.Category;

public class ResultActivity extends AppCompatActivity {

    private TextView txtHighScore;
    private TextView txtTotalQuestion , txtCorrectQues , txtWrongQues;
    private int HighScore;


    private Button btStart;
    private Button btMainMenu;
    private MainActivity mainActivity;



    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        btMainMenu = findViewById(R.id.result_bt_mainmenu);
        btStart = findViewById(R.id.result_bt_playagain);
        txtHighScore = findViewById(R.id.result_text_High_Score);
        txtTotalQuestion = findViewById(R.id.result_total_Question);
        txtCorrectQues = findViewById(R.id.result_Correct_Ques);
        txtWrongQues = findViewById(R.id.result_Wrong_Ques);

        btMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this ,MainActivity.class);
                startActivity(intent);
            }
        });

        btStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        Intent intent = getIntent();

        HighScore = intent.getIntExtra("UserScore" ,0);
        int totalQuestion = intent.getIntExtra("TotalQuestion" ,0);
        int correctQues = intent.getIntExtra("CorrectQues" ,0);
        int wrongQues = intent.getIntExtra("WrongQues" ,0);


        txtHighScore.setText("Score:" + String.valueOf(HighScore));
        txtTotalQuestion.setText("Total:" +String.valueOf(totalQuestion));
        txtCorrectQues.setText("Correct:"+String.valueOf(correctQues));
        txtWrongQues.setText("Wrong:" + String.valueOf(wrongQues));




    }



}
