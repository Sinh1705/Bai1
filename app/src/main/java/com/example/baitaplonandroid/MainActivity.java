package com.example.baitaplonandroid;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.baitaplonandroid.model.Category;


import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView textViewHighScore;
    private Spinner spinnerCategory;
    private Button buttonStartQuestion;

    private int highScore;
    private static final int REQUEST_CODE_QUESTION = 1;
    //private final String SHARED_PREF_NAME ="shared";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AnhXa();

        //Load chủ đề
        loadCategories();

        //Load điểm
        loadHighScore();

        //Click bắt đầu
        buttonStartQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startQuestion();
            }
        });

    }

    //Load điểm hiển thị
    private void loadHighScore() {
        SharedPreferences preferences = getSharedPreferences("share", MODE_PRIVATE);
        highScore = preferences.getInt("highScore", 0);
        textViewHighScore.setText("Điểm cao: " + highScore);
    }

    //Hàm bắt đầu câu hỏi qua activity question
    private void startQuestion() {

        //Lấy id, name chủ đề đã chọn
        Category category = (Category) spinnerCategory.getSelectedItem();
        int categoryID = category.getId();
        String categoryName = category.getName();

        //Chuyển qua activity question
        Intent intent = new Intent(MainActivity.this, QuestionActivity.class);

        //Gửi dữ liệu name, id
        intent.putExtra("idcategories", categoryID);
        intent.putExtra("categoriesname", categoryName);

        //Sử dụng startActivityForResult để có thể nhận lại dữ liệu kết quả trả về thông qua phương thức onActivityResult()
        startActivityForResult(intent, REQUEST_CODE_QUESTION);
    }

    //Phương thức ánh xạ id
    private void AnhXa(){
        textViewHighScore = findViewById(R.id.textview_high_score);
        buttonStartQuestion = findViewById(R.id.button_start_question);
        spinnerCategory = findViewById(R.id.spinner_category);
    }

    //Load chủ đề
    private void loadCategories(){
        Database database = new Database(this);

        //Lấy dữ liệu danh sách chủ đề
        List<Category> categories = database.getDataCategories();

        //Tạo adapter
        ArrayAdapter<Category> categoryArrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, categories);

        //Bố cục hiển thị chủ đề
        categoryArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Gán chủ đề lên Spinner hiển thị
        spinnerCategory.setAdapter(categoryArrayAdapter);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_QUESTION) {
            if (resultCode == RESULT_OK) {
                int score = data.getIntExtra("score", 0);

                if (score > highScore) {
                    updateHighScore(score);
                }
            }
        }
    }

    //Cập nhật điểm cao
    private void updateHighScore(int score) {

        //Gán điểm cao mới
        highScore = score;

        //Hiển thị
        textViewHighScore.setText("Điểm cao: " + highScore);

        //Lưu trữ điểm
        SharedPreferences preferences = getSharedPreferences("share", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        //Gán giá trị cho điểm cao mới vào khoá
        editor.putInt("highScore", highScore);

        //Hoàn tất
        editor.apply();
    }
}