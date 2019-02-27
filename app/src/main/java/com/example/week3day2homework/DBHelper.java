package com.example.week3day2homework;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;

import static com.example.week3day2homework.DBContract.*;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DBContract.createTableQuery());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    public long insertStudentIntoDB(@NonNull Student student) {
        SQLiteDatabase writableDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_NAME, student.getName());
        contentValues.put(COL_MAJOR, student.getMajor());
        contentValues.put(COL_MINOR, student.getMinor());
        contentValues.put(COL_GRAD_YEAR, student.getYear());
        contentValues.put(COL_GPA, student.getGpa());
        contentValues.put(COL_COMPLETED_HOURS, student.getHours());

        return writableDB.insert(TABLE_NAME, null, contentValues);
    }

    public ArrayList<Student> getAllStudentsFromDB() {
        ArrayList<Student> studentsList = new ArrayList<>();
        SQLiteDatabase readableDB = this.getReadableDatabase();

        Cursor cursor = readableDB.rawQuery(DBContract.getAllStudentsQuery(), null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(COL_ID));
                String name = cursor.getString(cursor.getColumnIndex(COL_NAME));
                String major = cursor.getString(cursor.getColumnIndex(COL_MAJOR));
                String minor = cursor.getString(cursor.getColumnIndex(COL_MINOR));
                String year = cursor.getString(cursor.getColumnIndex(COL_GRAD_YEAR));
                String gpa = cursor.getString(cursor.getColumnIndex(COL_GPA));
                String hours = cursor.getString(cursor.getColumnIndex(COL_COMPLETED_HOURS));

                studentsList.add(new Student(id, name, major, minor, year, gpa, hours));
            } while (cursor.moveToNext());
        }

        cursor.close();

        return studentsList;
    }

    public Student getStudentById(int id) {
        SQLiteDatabase readableDB = getReadableDatabase();
        Student student = new Student();
        Cursor cursor = readableDB.rawQuery(getOneStudentById(id), null);
        if (cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndex(COL_NAME));
            String major = cursor.getString(cursor.getColumnIndex(COL_MAJOR));
            String minor = cursor.getString(cursor.getColumnIndex(COL_MINOR));
            String year = cursor.getString(cursor.getColumnIndex(COL_GRAD_YEAR));
            String gpa = cursor.getString(cursor.getColumnIndex(COL_GPA));
            String hours = cursor.getString(cursor.getColumnIndex(COL_COMPLETED_HOURS));

            student = new Student(id, name, major, minor, year, gpa, hours);
        }
        cursor.close();
        return student;
    }

    public long updateStudentInDB(@NonNull Student student) {
        SQLiteDatabase writableDB = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_ID, student.getId());
        contentValues.put(COL_NAME, student.getName());
        contentValues.put(COL_MAJOR, student.getMajor());
        contentValues.put(COL_MINOR, student.getMinor());
        contentValues.put(COL_GRAD_YEAR, student.getYear());
        contentValues.put(COL_GPA, student.getGpa());
        contentValues.put(COL_COMPLETED_HOURS, student.getHours());

        return writableDB.update(TABLE_NAME,
                contentValues,
                getWhereClauseById() + student.getId(),
                null);
    }

    public long deleteFromDBById(int id) {
        SQLiteDatabase writableDB = getWritableDatabase();
        return writableDB.delete(TABLE_NAME, getWhereClauseById() + id, null);
    }
}
