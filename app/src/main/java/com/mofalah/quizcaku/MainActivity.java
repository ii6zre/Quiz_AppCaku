package com.mofalah.quizcaku;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView totalQuestionsTextView;
    TextView questionTextView;
    Button ansA, ansB, ansC, ansD;
    Button submitBtn;

    int score = 0;
    int totalQuestion = Sorulari.question.length;
    int currentQuestionIndex = 0;
    String selectedAnswer = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        totalQuestionsTextView = findViewById(R.id.total_question);
        questionTextView = findViewById(R.id.question);
        ansA = findViewById(R.id.ans_A);
        ansB = findViewById(R.id.ans_B);
        ansC = findViewById(R.id.ans_C);
        ansD = findViewById(R.id.ans_D);
        submitBtn = findViewById(R.id.submit_btn);

        ansA.setOnClickListener(this);
        ansB.setOnClickListener(this);
        ansC.setOnClickListener(this);
        ansD.setOnClickListener(this);
        submitBtn.setOnClickListener(this);

        totalQuestionsTextView.setText("Toplam Soru: " + totalQuestion);
        loadNewQuestion();


    }

    @Override
    public void onClick(View view) {
        ansA.setBackgroundColor(Color.WHITE);
        ansB.setBackgroundColor(Color.WHITE);
        ansC.setBackgroundColor(Color.WHITE);
        ansD.setBackgroundColor(Color.WHITE);
        Button clickedButton = (Button) view;
        if (clickedButton.getId() == R.id.submit_btn) {
            if (selectedAnswer.equals(Sorulari.correctAnswer[currentQuestionIndex])) {
                score++;
            }
            currentQuestionIndex++;
            loadNewQuestion();
        }
        else {
            selectedAnswer = clickedButton.getText().toString();
            clickedButton.setBackgroundColor(Color.GRAY);
        }
    }

    void loadNewQuestion() {
        if (currentQuestionIndex == totalQuestion) {
            finishQuiz();
            return;
        }


        questionTextView.setText(Sorulari.question[currentQuestionIndex]);
        ansA.setText(Sorulari.choices[currentQuestionIndex][0]);
        ansB.setText(Sorulari.choices[currentQuestionIndex][1]);
        ansC.setText(Sorulari.choices[currentQuestionIndex][2]);
        ansD.setText(Sorulari.choices[currentQuestionIndex][3]);
    }

    void finishQuiz() {
        String passStatus = "";
        if (score <= totalQuestion*1) {
            passStatus = "Geçti";
        }
        else {
            passStatus = "Kaldı";
        }

        new AlertDialog.Builder(this)
                .setTitle(passStatus)
                .setMessage("Soru Sayısı: " + totalQuestion + " \nDoğru Sayısı: " + score +
                        " \nyanlış Sayısı: " + (totalQuestion - score))
                .setPositiveButton("Yeniden Başlat", (dialogInterface, i) -> restartQuiz())
                .setCancelable(false)
                .show();
    }

    void restartQuiz() {
        score = 0;
        currentQuestionIndex = 0;
        loadNewQuestion();
    }
}