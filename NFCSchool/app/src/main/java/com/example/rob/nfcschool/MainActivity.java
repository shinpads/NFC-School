package com.example.rob.nfcschool;


import android.content.Context;

import android.app.ActionBar;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;


import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import android.view.Gravity;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

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
    ArrayList<String> homework = new ArrayList<String>();
    ArrayList<TableRow> homeworkRow = new ArrayList<TableRow>();
    ArrayList<TextView> spaces = new ArrayList<TextView>();
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
        //TODO LOAD INFO FROM TEXT FILE
        ArrayList<String> homeworkList = new ArrayList<String>();
        homeworkList = readFromFile();
        for(String x: homeworkList){
            setMessage(x);
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



    private void writeToFile(ArrayList<String> myData) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("totalMessage.txt", Context.MODE_PRIVATE));
            BufferedWriter writer = new BufferedWriter(outputStreamWriter);//new OutputStreamWriter(System.out)
            for(String i : myData){
                writer.write(i);
                writer.newLine();
            }
            writer.close();
            } catch (Exception e) {
                Log.e("text writing",e.getMessage());
            }
    }

    private ArrayList<String> readFromFile() {
        ArrayList<String> result = new ArrayList<>();

        try {
            InputStream inputStream = openFileInput("totalMessage.txt");

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String tempString = "";
                //StringBuilder stringBuilder = new StringBuilder();

                while ((tempString = bufferedReader.readLine()) != null) {
                    //stringBuilder.append(tempString);
                    result.add(tempString);
                    tempString = "";

                    /*if((tempString = bufferedReader.readLine()) == " "){
                        //append stringBuilder to totalMessageList;
                        //stringBuilder = "";
                    }*/
                }
                inputStream.close();
                //result = stringBuilder.toString();
            }
        } catch (FileNotFoundException e) {
            Log.v("MyActivity", "File not found" + e.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public void setMessage(String string) {
        homework.add(string);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(ma);

                builder.setTitle("Confirm");
                builder.setMessage("Are you sure you want to delete?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        int pos = homeworkRow.indexOf(row);
                        homework.remove(pos);
                        TextView space2Delete = spaces.get(pos);
                        ((ViewManager)space2Delete.getParent()).removeView(space2Delete);
                        spaces.remove(pos);
                        for(String x : homework){
                            Log.i("homework: ", x);
                        }
                        ((ViewManager)row.getParent()).removeView(row);
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();

            }
        });
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0,20,0,20);
        lp.height = TableRow.LayoutParams.WRAP_CONTENT;
        lp.width = TableRow.LayoutParams.MATCH_PARENT;
        lp.bottomMargin = 20;
        TextView courseName = new TextView(this);
        TextView content = new TextView(this);
        TableRow.LayoutParams clp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        TableRow.LayoutParams clp2 = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        clp.gravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL;
        clp2.gravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL;
        clp.column = 2;
        content.setText(splitMsg[1]);
        content.setTextSize(12);
        content.setLayoutParams(clp);
        content.setPadding(0,10,0,10);
        courseName.setText(splitMsg[0]);
        courseName.setTextSize(20);
        courseName.setTypeface(null, Typeface.BOLD);
        clp2.column = 1;
        courseName.setLayoutParams(clp2);
        courseName.setPadding(0,10,0,10);
        row.setBackgroundColor(Color.parseColor("#D79B9B9B"));
        homeworkRow.add(row);
        row.addView(courseName);
        row.addView(content);
        row.setPadding(40,20,40,20);
        TableLayout table = (TableLayout) findViewById(R.id.readTable);
        TextView space = new TextView(this);
        spaces.add(space);
        table.addView(space);
        table.addView(row,lp);

    }
    @Override
   public void onPause(){
       Log.i("SAVE TO TEXT", getFilesDir().getAbsolutePath());
        writeToFile(homework);
        super.onPause();

    }
}

