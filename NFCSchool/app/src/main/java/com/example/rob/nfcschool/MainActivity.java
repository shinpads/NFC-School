package com.example.rob.nfcschool;


import android.content.Context;

import android.app.ActionBar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;


import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import android.view.Gravity;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TableLayout tableLayout;
    static String nfcRead = "";
    private String message = "";
    public static MainActivity mainActivity;
    private MainActivity ma = this;
    TableRow tablerowtemplate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        tableLayout = (TableLayout) findViewById(R.id.readTable);
        tablerowtemplate = (TableRow) findViewById(R.id.tableRow);
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


    private String[] splitReadMessage(String message) {
        String splitMessage[] = new String[2];
        String part1 = "";
        String part2 = "";
        int i = 4;
        char x = '-';
        while (x != '>') {
            part1 += message.charAt(i);

            i++;
            x = message.charAt(i);

        }
        for (int b = i + 1; b < message.length(); b++) {
            part2 += message.charAt(b);
        }
        splitMessage[0] = part1;
        splitMessage[1] = part2;
        Log.i("split message", splitMessage[0]);
        return splitMessage;
    }

    private void writeToFile(String myData) {
        try {
            //String[] myDataList = myData.split(" ");
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("totalMessage.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(myData);
            //BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
            /*for (String word: myDataList) {
                writer.write(myDataList);
                writer.newLine();
            }*/

            outputStreamWriter.close();
            //writer.close();
        } catch (IOException e) {
            Log.v("MyActivity", e.toString());
        }
    }

    private String readFromFile() {
        String result = "";

        try {
            InputStream inputStream = openFileInput("totalMessage.txt");

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String tempString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ((tempString = bufferedReader.readLine()) != null) {
                    stringBuilder.append(tempString);

                    /*if((tempString = bufferedReader.readLine()) == " "){
                        //append stringBuilder to totalMessageList;
                        //stringBuilder = "";
                    }*/
                }
                inputStream.close();
                result = stringBuilder.toString();
            }
        } catch (FileNotFoundException e) {
            Log.v("MyActivity", "File not found" + e.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public void setMessage(String string) {
        message = string;
        addItem(message);
    }

    public void addItem(String message) {
        Log.i("NFC MESSAGE: ", message);
        String[] splitMsg = splitReadMessage(message);
        final TableRow row = new TableRow(this);
        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //remove if tapped:
                ((ViewManager)row.getParent()).removeView(row);
            }
        });
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
        TextView courseName = new TextView(this);
        TextView content = new TextView(this);
        TableRow.LayoutParams clp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        TableRow.LayoutParams clp2 = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        clp.gravity = Gravity.START;
        clp2.gravity = Gravity.START;
        clp.column = 2;
        content.setText(splitMsg[1]);
        content.setTextSize(12);
        content.setLayoutParams(clp);
        courseName.setText(splitMsg[0]);
        courseName.setTextSize(20);
        courseName.setTypeface(null, Typeface.BOLD);
        clp2.column = 1;
        courseName.setLayoutParams(clp2);
        row.addView(courseName);
        row.addView(content);
        TableLayout table = (TableLayout) findViewById(R.id.readTable);
        table.addView(row);

    }
}

