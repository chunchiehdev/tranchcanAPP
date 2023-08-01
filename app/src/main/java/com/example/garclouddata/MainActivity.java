package com.example.garclouddata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button btnHistory,btnStaData,btnCon,btnAbout,btnManage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnStaData = (Button)findViewById(R.id.btnStaData);
        btnHistory = (Button)findViewById(R.id.btnHistory);
        btnCon = (Button)findViewById(R.id.btnCon);
        btnAbout = (Button)findViewById(R.id.btnAbout);
        btnManage = (Button)findViewById(R.id.btnManage);

        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent();
                intent.setClass(MainActivity.this,MainHistory.class);
                startActivity(intent);
            }
        });

        btnStaData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent();
                intent.setClass(MainActivity.this,MainChart.class);
                startActivity(intent);
            }
        });
        btnCon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,MainConnection.class);
                startActivity(intent);
            }
        });
        btnManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,MainManage.class);
                startActivity(intent);
            }
        });
        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,MainAbout.class);
                startActivity(intent);
            }
        });
    }
}
