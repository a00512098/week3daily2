package com.example.week3day2homework;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText name, major, minor, year, gpa, hours;
    Button createBtn, modifyBtn;
    DBHelper database;
    SharedPreferences sharedPreferences;
    final static String PREFERENCES = "PREFERENCES";
    final static String STUDENT_ID = "STUDENT_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        database = new DBHelper(this);
        sharedPreferences = getSharedPreferences(PREFERENCES, MODE_PRIVATE);
        Log.d("Log.d", "Preferences: " + sharedPreferences.getString(STUDENT_ID, "NO VALUE"));
    }

    private void initViews() {
        createBtn = findViewById(R.id.queryUser);
        createBtn.setOnClickListener(this);
        modifyBtn = findViewById(R.id.updateUser);
        modifyBtn.setOnClickListener(this);

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
                if (!isAnyEntryEmpty()) {
                    saveStudentToDB();
                }
                break;
            case R.id.updateUser:
                Intent intent = new Intent(getApplicationContext(), ModifyUserActivity.class);
                startActivity(intent);
                break;
        }
    }

    private boolean isAnyEntryEmpty() {
        return name.getText().toString().isEmpty() ||
                major.getText().toString().isEmpty() ||
                minor.getText().toString().isEmpty() ||
                year.getText().toString().isEmpty() ||
                gpa.getText().toString().isEmpty() ||
                hours.getText().toString().isEmpty();
    }

    public void saveStudentToDB() {
        Student student = new Student();
        student.setName(name.getText().toString());
        student.setMajor(major.getText().toString());
        student.setMinor(minor.getText().toString());
        student.setYear(year.getText().toString());
        student.setGpa(gpa.getText().toString());
        student.setHours(hours.getText().toString());

        database.insertStudentIntoDB(student);
        ArrayList<Student> students = database.getAllStudentsFromDB();
        for (Student student1 : students) {
            Log.d("Log.d", student1.toString());
        }

        //  Save the last student queried or inserted id in sharedPrefs
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(STUDENT_ID, "" + students.get(students.size() - 1).getId());
        editor.apply();
    }
}
