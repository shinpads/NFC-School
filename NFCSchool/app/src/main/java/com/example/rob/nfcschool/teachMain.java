package com.example.rob.nfcschool;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class TeachMain extends AppCompatActivity {
    EditText course;
    EditText text;
    String courseTxt = "";
    String homework = "";
    public static String finalmessage = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teach_main);
        course = (EditText)findViewById(R.id.courseField);
        text = (EditText)findViewById(R.id.hwField);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButtonUp);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO WRITE TO NFC
                courseTxt = course.getText().toString();
                homework = text.getText().toString();
                finalmessage = "<" + courseTxt + ">" + homework;
                Intent i = new Intent(TeachMain.this,WriteTag.class);
                startActivity(i);

            }
        });
    }
}
