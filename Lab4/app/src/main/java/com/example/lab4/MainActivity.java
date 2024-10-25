package com.example.lab4;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private TextView tv_meal;
    private Button btn_select;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_meal = findViewById(R.id.tv_meal);
        btn_select = findViewById(R.id.btn_choice);
        btn_select.setOnClickListener(view ->
            startActivityForResult(new Intent(MainActivity.this, Main2Activity.class), 1)
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data == null) return;

        if (requestCode == 1) {
            if (resultCode == 101) {
                Bundle b = data.getExtras();
                String str1 = b.getString("drink");
                String str2 = b.getString("sugar");
                String str3 = b.getString("ice");
                tv_meal.setText(String.format("飲料: %s\n\n甜度: %s\n\n冰塊: %s", str1, str2, str3));
            }
        }
    }
}