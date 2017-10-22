package com.example.thagadur.android_session_assignment1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{

    TextView inputData;
    EditText text;
    Button okay, delete;
    static String FILENAME = "testfile.txt";
    File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Call initializeID() to initialise all the objects with respective ID with layout elements
        initialiseID();

        //Created a File In the Storage using getExternalStorageDirectory()
        file = new File(Environment.getExternalStorageDirectory(), FILENAME);
        //Check If file is created or not
        try {
            if (file.createNewFile()) {
                Toast.makeText(getApplicationContext(), "Test File Created Successfully",
                        Toast.LENGTH_LONG).show();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //get the data from the textView and Store in the file , and clear the text after press ok to get next value
        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getText=  text.getText().toString();
                text.setText("");
                //call Asyc Task of to store data in to the file
                ReadDataFromFile readDataFromFile= new ReadDataFromFile(file);
                readDataFromFile.execute(getText);

            }
        });

        //OnClick of delete button delete the File created
        delete.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                file.delete();

            }
        });

    }

    public void initialiseID() {
        inputData = (TextView) findViewById(R.id.display_text);
        text = (EditText) findViewById(R.id.inputText);
        okay = (Button) findViewById(R.id.okay_file);
        delete = (Button) findViewById(R.id.delete_file);
    }

    /**
     * class ReadDataFromFile extending AsyncTask
     * input- String the contents which has to be stored in the file
     * progress- updating the contents to the file
     * output- ui -reading the file
     */
    private class ReadDataFromFile extends AsyncTask<String, Integer, String> {

        // static String FILENAME = "test.txt";
        File file;

        public ReadDataFromFile(File file) {
            super();
            this.file= file;
            // TODO Auto-generated constructor stub
        }

        //updated the data received from the textView to the file by using necessary file operations
        @Override
        protected String doInBackground(String... str) {
            String enter = "\n";
            FileWriter writer = null;
            try {
                writer = new FileWriter(file, true);
                writer.append(str[0].toString());
                writer.append(enter);
                writer.flush();

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                try {
                    writer.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            return null;

        }


        //read data from file
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            String name = "";
            StringBuilder sb = new StringBuilder();
            FileReader fr = null;

            try {
                fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);
                while ((name = br.readLine()) != null) {
                    sb.append(name);
                    sb.append("\n");

                }
                br.close();
                fr.close();
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            inputData.setText(sb.toString());
        }

    }
}