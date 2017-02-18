package com.example.rob.nfcschool;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    String nfcRead = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO read NFC

            }
        });
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    private String[] splitReadMessage(String message){
        String splitMessage[] = message.split(">", 1);
        return splitMessage;
    }
    String splitMessage[] = splitReadMessage(nfcRead);
    String courseName = splitMessage[0];
    String homework = splitMessage[1];
}
