package com.example.rob.nfcschool;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TableLayout tableLayout;
    static String nfcRead = "";
    private String message ="";
    public static MainActivity mainActivity;
    private MainActivity ma = this;
    TableRow tablerowtemplate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        tableLayout = (TableLayout) findViewById(R.id.readTable);
        tablerowtemplate = (TableRow)findViewById(R.id.tableRow);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO read NFC tag
                    mainActivity = ma;
                    Intent intent = new Intent(MainActivity.this, ReadTag.class);

                    startActivity(intent);
                }
            });
        }


    }
    private String[] splitReadMessage(String message){
        String splitMessage[] = new String[2];
        String part1 = "";
        String part2 = "";
        int i = 4;
        char x = '-';
        while(x!='>'){
            part1 += message.charAt(i);

            i ++;
            x = message.charAt(i);

        }
        for(int b = i+1; b<message.length();b++){
            part2 += message.charAt(b);
        }
        splitMessage[0] = part1;
        splitMessage[1] = part2;
        Log.i("split message",splitMessage[0]);
        return splitMessage;
    }
    public void setMessage(String string){
        message = string;
        addItem(message);
    }
    public void addItem(String message){
        Log.i("NFC MESSAGE: ", message);
        String[] splitMsg = splitReadMessage(message);
        TableRow row = new TableRow(this);
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
        TextView courseName = new TextView(this);
        TextView content = new TextView(this);
        TableRow.LayoutParams clp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        clp.gravity = Gravity.START;
        clp.column =1;
        content.setText(splitMsg[1]);
        content.setTextSize(18);
        content.setTypeface(null,Typeface.BOLD);
        content.setLayoutParams(clp);
        courseName.setText(splitMsg[0]);
        courseName.setTextSize(20);
        courseName.setTypeface(null,Typeface.BOLD);
        clp.column=2;
        courseName.setLayoutParams(clp);
        row.addView(courseName);
        row.addView(content);
        TableLayout table = (TableLayout) findViewById(R.id.readTable);
        table.addView(row);






    }
}

