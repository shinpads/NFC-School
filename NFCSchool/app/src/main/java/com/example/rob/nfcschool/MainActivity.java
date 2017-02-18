package com.example.rob.nfcschool;


import android.content.Context;

import android.app.ActionBar;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;


import android.view.Gravity;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.BufferedReader;
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

public class MainActivity extends AppCompatActivity {

    private GoogleApiClient client;
    TableLayout tableLayout;
    static String nfcRead = "";
    private String message ="";
    public static MainActivity mainActivity;
    private MainActivity ma = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        tableLayout = (TableLayout) findViewById(R.id.readTable);
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

    ArrayList<String> messageList = new ArrayList<String>();

    private String[] splitReadMessage(String message) {
        String splitMessage[] = message.split(">", 1);
        messageList.add(splitMessage[1]);
        return splitMessage;
    }

    private void writeToFile(String myData) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("totalMessage.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(myData);
            outputStreamWriter.close();
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

    public void setMessage(String string){
        message = string;
    }
    public void addItem(String message){
        Log.i("NFC MESSAGE: ", message);
        String[] splitMsg = splitReadMessage(message);
        TableRow row = new TableRow(this);
    }
}

