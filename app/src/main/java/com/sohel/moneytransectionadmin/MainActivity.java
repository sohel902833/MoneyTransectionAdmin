package com.sohel.moneytransectionadmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    private Button appSetting;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        appSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,AppSettingActivity.class));
            }
        });






    }

    private void init() {
        toolbar=findViewById(R.id.appBariId);
        setSupportActionBar(toolbar);
        this.setTitle("Admin");
        appSetting=findViewById(R.id.appSettings);

    }
}