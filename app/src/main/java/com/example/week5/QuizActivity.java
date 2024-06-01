package com.example.week5;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class QuizActivity extends AppCompatActivity {

    private TextView questionTextView;
    private LinearLayout answersLayout;
    private ProgressBar progressBar;
    private TextView progressTextView;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private String[] questions = {"Who is the GOAT?", "Whats the best COD?", "Whats the best Console?"};
    private String[][] answers = {{"Messi", "Ronaldo", "Mbappe", "Lebron James"}, {"MW2", "B03", "B02", "Warzone"}, {"Xbox", "Nintendo", "PlayStation", "Wii"}};
    private int[] correctAnswers = {0, 1, 2};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        questionTextView = findViewById(R.id.questionTextView);
        answersLayout = findViewById(R.id.answersLayout);
        progressBar = findViewById(R.id.progressBar);
        progressTextView = findViewById(R.id.progressTextView);

        loadQuestion();
    }

    private void loadQuestion() {
        questionTextView.setText(questions[currentQuestionIndex]);
        answersLayout.removeAllViews();
        for (int i = 0; i < answers[currentQuestionIndex].length; i++) {
            Button answerButton = new Button(this);
            answerButton.setText(answers[currentQuestionIndex][i]);
            answerButton.setId(i);
            answerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkAnswer(v);
                }
            });
            answersLayout.addView(answerButton);
        }
        updateProgressBar();
    }

    private void checkAnswer(View view) {
        int selectedAnswerIndex = view.getId();
        if (selectedAnswerIndex == correctAnswers[currentQuestionIndex]) {
            score++;
            view.setBackgroundColor(Color.GREEN);
        } else {
            view.setBackgroundColor(Color.RED);
        }


        for (int i = 0; i < answersLayout.getChildCount(); i++) {
            answersLayout.getChildAt(i).setEnabled(false);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (currentQuestionIndex < questions.length - 1) {
                    currentQuestionIndex++;
                    loadQuestion();
                } else {
                    Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
                    intent.putExtra("score", score);
                    startActivity(intent);
                    finish();
                }
            }
        }, 2000);
    }

    private void updateProgressBar() {
        int progress = (int) (((double) (currentQuestionIndex + 1) / questions.length) * 100);
        progressBar.setProgress(progress);
        progressTextView.setText(progress + "%");
    }
}
