package com.baycosinus.turretcontroller;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_settings);
        final EditText hostText = findViewById(R.id.hostText);
        final EditText portText = findViewById(R.id.portText);
        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!hostText.getText().toString().equals("") || !portText.getText().toString().equals(""))
                {
                    MainActivity.HOST = hostText.getText().toString();
                    MainActivity.PORT = Integer.valueOf(portText.getText().toString());
                }
                Intent intent = new Intent(Settings.this, MainActivity.class);
                startActivity(intent);

            }
        });
    }
}
