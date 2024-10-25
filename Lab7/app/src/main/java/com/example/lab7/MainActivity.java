package com.example.lab7;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // 建立兩個數值，用於計算兔子與烏龜的進度
    private int progressRabbit = 0;
    private int progressTurtle = 0;

    private Button btn_start;
    private SeekBar sb_rabbit, sb_turtle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_start = findViewById(R.id.btn_start);
        sb_rabbit = findViewById(R.id.sb_rabbit);
        sb_turtle = findViewById(R.id.sb_turtle);

        btn_start.setOnClickListener(v -> {
            btn_start.setEnabled(false); // 比賽期間，不可重複操作
            progressRabbit = 0; // 初始化進度
            progressTurtle = 0;
            sb_rabbit.setProgress(0);
            sb_turtle.setProgress(0);
            // 執行方法
            runRabbit();
            runTurtle();
        });
    }

    private final Handler handler = new Handler(Looper.myLooper(), new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            // 判斷編號
            if (msg.what == 1)
                sb_rabbit.setProgress(progressRabbit);
            else if (msg.what == 2)
                sb_turtle.setProgress(progressTurtle);

            // 判斷誰快
            if (progressRabbit >= 100 && progressTurtle < 100) {
                Toast.makeText(MainActivity.this, "兔子勝", Toast.LENGTH_SHORT).show();
                btn_start.setEnabled(true);
            } else if (progressTurtle >= 100 && progressRabbit < 100) {
                Toast.makeText(MainActivity.this, "烏龜勝", Toast.LENGTH_SHORT).show();
                btn_start.setEnabled(true);
            }

            return false;
        }
    });

    private void runRabbit() {
        new Thread(() -> {
            // 兔子有三分之二的機率會偷懶
            Boolean[] sleepProbability = {true, true, false};

            while (progressRabbit <= 100 && progressTurtle < 100) {
                try {
                    Thread.sleep(100); // 延遲0.1秒
                    // 隨機:0 ~ 2。 0和1為兔子休息、 2為移動
                    if (sleepProbability[(int) (Math.random() * 3)])
                        Thread.sleep(300); // 兔子休息0.3秒
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                progressRabbit += 3; // 每次三步

                Message msg = new Message(); // 建立Message物件
                msg.what = 1; // 加入編號
                handler.sendMessage(msg);
            }
        }).start();
    }

    private void runTurtle() {
        new Thread(() -> {
            while (progressTurtle <= 100 && progressRabbit < 100) {
                try {
                    Thread.sleep(100); // 延遲0.1秒
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                progressTurtle += 1; // 每次一步

                Message msg = new Message(); // 建立Message物件
                msg.what = 2; // 加入編號
                handler.sendMessage(msg);
            }
        }).start();
    }
}