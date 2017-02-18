package com.example.rob.nfcschool;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    static String nfcRead = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO read NFC tag
                    Intent intent = new Intent(MainActivity.this, ReadTag.class);
                    startActivity(intent);
                }
            });
        }

    }
    private String[] splitReadMessage(String message){
        String splitMessage[] = message.split(">", 1);
        return splitMessage;
    }
    public static void addItem(String message){
        Log.i("NFC MESSAGE: ", message);
    }
}

