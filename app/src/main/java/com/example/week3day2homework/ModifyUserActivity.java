package com.example.week3day2homework;

import android.content.SharedPreferences;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static com.example.week3day2homework.MainActivity.PREFERENCES;
import static com.example.week3day2homework.MainActivity.STUDENT_ID;

public class ModifyUserActivity extends AppCompatActivity implements View.OnClickListener{

    ConstraintLayout otherValues;
    EditText userId, name, major, minor, year, gpa, hours;
    Button queryBtn, updateBtn, deleteBtn;
    DBHelper database;
    SharedPreferences sharedPreferences;
    final static String EXTERNAL_FILE = "externalfile.txt";
    static final int READ_BLOCK_SIZE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_user);

        initViews();
        database = new DBHelper(this);

        // For debugging purpose
        printAllUsers();

        sharedPreferences = getSharedPreferences(PREFERENCES, MODE_PRIVATE);
        Log.d("Log.d", "Preferences: " + sharedPreferences.getString(STUDENT_ID, "NO VALUE"));

        readFromFile();
    }

    private void printAllUsers() {
        ArrayList<Student> students = database.getAllStudentsFromDB();
        for (Student student1 : students) {
            Log.d("Log.d", student1.toString());
        }
    }

    private void initViews() {
        otherValues = findViewById(R.id.otherData);

        queryBtn = findViewById(R.id.queryUser);
        queryBtn.setOnClickListener(this);
        updateBtn = findViewById(R.id.updateUser);
        updateBtn.setOnClickListener(this);
        deleteBtn = findViewById(R.id.deleteUser);
        deleteBtn.setOnClickListener(this);

        userId = findViewById(R.id.userId);
        name = findViewById(R.id.name);
        major = findViewById(R.id.major);
        minor = findViewById(R.id.minor);
        year = findViewById(R.id.year);
        gpa = findViewById(R.id.gpa);
        hours = findViewById(R.id.hours);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.queryUser:
                if (!isIdEmpty()) {
                    queryStudent();
                }
                break;
            case R.id.updateUser:
                if (!isIdEmpty()) {
                    updateStudent();
                }
                break;
            case R.id.deleteUser:
                if (!isIdEmpty()) {
                    deleteStudent();
                }
                break;
        }
    }

    private void queryStudent() {
        int id = Integer.parseInt(userId.getText().toString());
        Student student = database.getStudentById(id);
        if (student.getName() == null) {
            Toast.makeText(this, String.format("User with ID = %s not found", id), Toast.LENGTH_SHORT).show();
            return;
        }
        name.setText(student.getName());
        major.setText(student.getMajor());
        minor.setText(student.getMinor());
        year.setText(student.getYear());
        gpa.setText(student.getGpa());
        hours.setText(student.getHours());
        otherValues.setVisibility(View.VISIBLE);

        //  Save the last student queried or inserted id in sharedPrefs
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(STUDENT_ID, "" + student.getId());
        editor.apply();
        writeToFile(student.getId());
    }

    private void updateStudent() {
        int id = Integer.parseInt(userId.getText().toString());
        Student student = database.getStudentById(id);
        if (student.getName() != null || student.getId() != 0) {
            if (otherValues.getVisibility() == View.GONE) {
                Toast.makeText(this, "First edit the user", Toast.LENGTH_SHORT).show();
                queryStudent();
                return;
            }
            student = new Student(
                    Integer.parseInt(userId.getText().toString()),
                    name.getText().toString(),
                    major.getText().toString(),
                    minor.getText().toString(),
                    year.getText().toString(),
                    gpa.getText().toString(),
                    hours.getText().toString());
            database.updateStudentInDB(student);
            Toast.makeText(this, "Student updated", Toast.LENGTH_SHORT).show();
            otherValues.setVisibility(View.GONE);

        } else {
            Toast.makeText(this, "That user doesn't exists", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteStudent() {
        int id = Integer.parseInt(userId.getText().toString());
        Student student = database.getStudentById(id);
        //user exists
        if (student.getName() != null || student.getId() != 0) {
            database.deleteFromDBById(id);
            printAllUsers();
            otherValues.setVisibility(View.GONE);
            Toast.makeText(this, "User deleted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "That user doesn't exists", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isIdEmpty() {
        return userId.getText().toString().isEmpty();
    }

    //Write to INTERNAL storage
    public void writeToFile(int id) {
        try {

            //Open up file to edit
            FileOutputStream fileOutputStream= openFileOutput(EXTERNAL_FILE, MODE_PRIVATE);
            //Add to the file the text we want to save
            fileOutputStream.write(("ID: " + id).getBytes());
            //close the file
            Log.d("TAG", "writeToFile: " +fileOutputStream);
            fileOutputStream.close();
            Log.d("TAG", "writeToFile: TEXT WRITTEN TO FILE");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Read from INTERNAL storage
    public void readFromFile() {
        //Open file to read
        try {

            //Open file to read
            FileInputStream fileInputStream = openFileInput(EXTERNAL_FILE);
            //Assign reader to file stream
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);

            char[] readBuffer = new char[READ_BLOCK_SIZE]; //blockes to read at a time
            String stringReadFromFile = ""; //hold all the text read from file
            int currentRead; //current value being read

            // while the current item being read is greater that a valueof 0, read the next value
            while((currentRead = inputStreamReader.read(readBuffer)) > 0) {
                //Convert the read value to a char(String)
                String readstring=String.copyValueOf(readBuffer,0,currentRead);
                //add char to read string
                stringReadFromFile+= readstring;
                Log.d("TAG", "readFromFile: " + stringReadFromFile);
            }
            fileInputStream.close();

            //Display results in logcat
            Log.d("TAG", "readFromFile: " + stringReadFromFile);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
