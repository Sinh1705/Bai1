package com.example.baitaplonandroid;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.baitaplonandroid.model.Question;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class QuestionActivity extends AppCompatActivity {

    private TextView textViewQuestion;
    private TextView textViewScore;
    private TextView textViewQuestionCount;
    private TextView textViewCategory;
    private TextView textViewCountDown;

    private RadioGroup rbGroup;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private RadioButton rb4;
    private Button buttonConfirmNext;
    private Button buttonXoa;
    private EditText editTextName;

    private CountDownTimer countDownTimer;
    private ArrayList<Question> questionList;
    private long timeLeftInMillis;
    private int questionCounter;
    private int questionSize;

    private int Score;
    private boolean answered;
    private Question currentQuestion;

    private int count = 0;
    private int wrong=0;
    private int correct=0;
    private final String SHARED_PREF_NAME ="shared";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        anhxa();

        //Nhận dữ liệu chủ đề
        Intent intent = getIntent();
        int categoryID = intent.getIntExtra("idcategories", 0);
        String categoryName = intent.getStringExtra("categoriesname");

        //Hiển thị chủ đề
        textViewCategory.setText("Chủ đề: " + categoryName);

        Database database = new Database(this);

        //Danh sách list chứa câu hỏi
        questionList = database.getQuestions(categoryID);

        //Lấy kích cỡ danh sách = tổng số câu hỏi
        questionSize = questionList.size();

        //Đảo vị trí các phần tử câu hỏi
        Collections.shuffle(questionList);

        //Show câu hỏi và đáp án
        showNextQuestion();

        //Button xác nhận, câu tiếp, hoàn thành
        buttonConfirmNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Nếu chưa trả lời câu hỏi
                if (!answered) {

                    //Nếu chọn 1 trong 4 đáp án
                    if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()) {

                        //Kiểm tra đáp án
                        checkAnswer();
                    }
                    else {
                        Toast.makeText(QuestionActivity.this, "Hãy chọn đáp án", Toast.LENGTH_SHORT).show();
                    }
                }

                //Nếu đã trả lời, thực hiện chuyển câu hỏi
                else {
                    showNextQuestion();
                }
            }
        });

        //buttonXoa.setOnClickListener(new View.OnClickListener() {
            //@Override
            //public void onClick(View v) {
             //   database.deleteQuestionsTable();
            //}
        //});


    }

    //Hiển thị câu hỏi
    private void showNextQuestion() {

        //Set lại màu đen cho đáp án
        rb1.setTextColor(Color.BLACK);
        rb2.setTextColor(Color.BLACK);
        rb3.setTextColor(Color.BLACK);
        rb4.setTextColor(Color.BLACK);

        //Xoá chọn
        rbGroup.clearCheck();

        //Nếu còn câu hỏi
        if (questionCounter < questionSize) {

            //Lấy dữ liệu ở vị trí counter
            currentQuestion = questionList.get(questionCounter);

            //Hiển thị câu hỏi
            textViewQuestion.setText(currentQuestion.getQuestion());
            rb1.setText(currentQuestion.getOption1());
            rb2.setText(currentQuestion.getOption2());
            rb3.setText(currentQuestion.getOption3());
            rb4.setText(currentQuestion.getOption4());

            //Tăng sau mỗi câu hỏi
            questionCounter++;

            //Set vị trí câu hỏi hiện tại
            textViewQuestionCount.setText("Câu hỏi: " + questionCounter + " / " + questionSize);

            //Giá trị false, chưa trả lời, đang show
            answered = false;

            //Gán tên cho button
            buttonConfirmNext.setText("Xác nhận");

            //Thời gian chạy 30s
            timeLeftInMillis = 30000;

            //Đếm ngược thời gian trả lời
            startCountDown();
        }
        else {
            //showNameInputDialog();
            //Result();
            finishQuestion();
        }
    }

    //Phương thức thời gian đếm ngược
    private void startCountDown() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long l) {
                timeLeftInMillis = l;

                //update time
                updateCountDownText();

            }

            @Override
            public void onFinish() {

                //Hết giờ
                timeLeftInMillis = 0;
                updateCountDownText();

                //Phương thức kiểm tra đáp án
                checkAnswer();
            }
        }.start();
    }

    //Kiểm tra đáp án
    private void checkAnswer() {

        //true đã trả lời
        answered = true;

        //Trả về radiobutton trong rbGroup đã được check
        RadioButton rbSelected = findViewById(rbGroup.getCheckedRadioButtonId());

        //Vị trí của câu đã chọn
        int answer = rbGroup.indexOfChild(rbSelected) + 1;

        //Nếu trả lời đúng đáp án
        if (answer == currentQuestion.getAnswer()) {

            //Tổng 10 điểm
            Score = Score + 10;
            correct= correct+1;

            //Hiển thị
            textViewScore.setText("Điểm: " + Score);
        }else {
            wrong=wrong+1;
        }

        //Phương thức hiển thị đáp án
        showSolution();
    }

    //Đáp án
    private void showSolution() {

        //Set màu cho radiobutton đáp án
        rb1.setTextColor(Color.RED);
        rb2.setTextColor(Color.RED);
        rb3.setTextColor(Color.RED);
        rb4.setTextColor(Color.RED);

        //Kiểm tra đáp án set màu và hiển thị đáp án lên màn hình
        switch (currentQuestion.getAnswer()) {
            case 1:
                rb1.setTextColor(Color.GREEN);
                textViewQuestion.setText("Đáp án là A");
                break;
            case 2:
                rb2.setTextColor(Color.GREEN);
                textViewQuestion.setText("Đáp án là B");
                break;
            case 3:
                rb3.setTextColor(Color.GREEN);
                textViewQuestion.setText("Đáp án là C");
                break;
            case 4:
                rb4.setTextColor(Color.GREEN);
                textViewQuestion.setText("Đáp án là D");
                break;
        }

        //Nếu còn câu trả lời thì button sẽ setText là câu tiếp
        if (questionCounter < questionSize) {
            buttonConfirmNext.setText("Câu tiếp");
        }

        //setText hoàn thành
        else {
            buttonConfirmNext.setText("Hoàn thành");
        }

        //Dừng thời gian lại
        countDownTimer.cancel();
    }

    //Update thời gian
    private void updateCountDownText() {

        //Tính phút
        int minutes = (int) ((timeLeftInMillis/1000)/60);

        //Tính giây
        int seconds = (int) ((timeLeftInMillis/1000)%60);

        //Định dạng kiểu time
        String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        //Hiển thị lên màn hình
        textViewCountDown.setText(timeFormatted);

        //Nếu thời gian dưới 10s thì sẽ chuyển màu đỏ
        if (timeLeftInMillis < 10000) {
            textViewCountDown.setTextColor(Color.RED);
        }

        //Không thì vẫn màu đen
        else {
            textViewCountDown.setTextColor(Color.BLACK);
        }
    }

    //Thoát qua giao diện chính
    private void finishQuestion(){

        //Chứa dữ liệu gửi qua activity main
        Intent resultIntent = new Intent();
        resultIntent.putExtra("score", Score);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    //Back
    @Override
    public void onBackPressed() {
        count++;
        if (count >= 1) {
            finishQuestion();
        }
        count = 0;
    }

    //Ánh xạ id
    private void anhxa(){
        textViewQuestion = findViewById(R.id.text_view_question);
        textViewScore = findViewById(R.id.text_view_score);
        textViewQuestionCount = findViewById(R.id.text_view_question_count);
        textViewCategory = findViewById(R.id.text_view_category);

        textViewCountDown = findViewById(R.id.text_view_countdown);
        rbGroup = findViewById(R.id.radio_group);
        rb1 = findViewById(R.id.radio_button1);
        rb2 = findViewById(R.id.radio_button2);
        rb3 = findViewById(R.id.radio_button3);
        rb4 = findViewById(R.id.radio_button4);

        buttonConfirmNext = findViewById(R.id.button_confirm_next);

    }

    //sau khi kết thúc hiện số câu hỏi đáp án đúng sai
    private void Result(){
        Intent intent = new Intent(QuestionActivity.this , ResultActivity.class);
        intent.putExtra("UserScore",Score);
        intent.putExtra("TotalQuestion",questionSize);
        intent.putExtra("CorrectQues",correct);
        intent.putExtra("WrongQues",wrong);
        startActivity(intent);
    }

    //hiện dialog lưu tên khi kết thúc
    private void showNameInputDialog(){
        //Inflate layout của dialog
        View view = LayoutInflater.from(this).inflate(R.layout.custom_dialog,null);

        // Tạo dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        builder.setTitle("Nhập tên của bạn");

        // Thiết lập nút "OK" cho dialog
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Lấy tên người chơi từ EditText trong dialog
                 editTextName = view.findViewById(R.id.edit_text_name);
                String playerName = editTextName.getText().toString();

                // Lưu tên người chơi vào SharedPreferences hoặc database của ứng dụng
                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME , Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Tên",playerName);
                editor.commit();
                Intent intent = new Intent(QuestionActivity.this , ResultActivity.class);
                intent.putExtra("UserScore",Score);
                intent.putExtra("TotalQuestion",questionSize);
                intent.putExtra("CorrectQues",correct);
                intent.putExtra("WrongQues",wrong);
                startActivity(intent);
            }
        });

        // Thiết lập nút "Cancel" cho dialog
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        // Hiển thị dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }


}