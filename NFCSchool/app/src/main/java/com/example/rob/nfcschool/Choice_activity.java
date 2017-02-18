package com.example.rob.nfcschool;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Choice_activity extends AppCompatActivity {
    Button teacher;
    Button student;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.role_choice);
        teacher = (Button)findViewById(R.id.teacherBtn);
        student = (Button)findViewById(R.id.studentBtn);
        teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //GO TO TEACHER PAGE
                Intent intent = new Intent(Choice_activity.this,TeachMain.class);
               startActivity(intent);
                finish();
            }
        });
        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //GO TO STUDENT PAGE
                Intent intent = new Intent(Choice_activity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
