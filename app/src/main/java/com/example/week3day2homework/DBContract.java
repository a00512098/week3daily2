package com.example.week3day2homework;

import java.util.Locale;

public class DBContract {
    public static final String DB_NAME = "student_db";
    public static final int DB_VERSION = 1;
    public static final String TABLE_NAME = "Students";
    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";
    public static final String COL_MAJOR = "major";
    public static final String COL_MINOR = "minor";
    public static final String COL_GRAD_YEAR = "grad_year";
    public static final String COL_GPA = "gpa";
    public static final String COL_COMPLETED_HOURS = "completed_hours";


    public static String createTableQuery() {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("CREATE TABLE ");
        queryBuilder.append(TABLE_NAME);
        queryBuilder.append(" ( ");
        //Must have unique primary key
        queryBuilder.append(COL_ID);
        queryBuilder.append(" ");
        queryBuilder.append(" INTEGER PRIMARY KEY AUTOINCREMENT, ");
        //Add rest of the columns
        queryBuilder.append(COL_NAME);
        queryBuilder.append(" TEXT, ");
        queryBuilder.append(COL_MAJOR);
        queryBuilder.append(" TEXT, ");
        queryBuilder.append(COL_MINOR);
        queryBuilder.append(" TEXT, ");
        queryBuilder.append(COL_GRAD_YEAR);
        queryBuilder.append(" TEXT, ");
        queryBuilder.append(COL_GPA);
        queryBuilder.append(" TEXT, ");
        queryBuilder.append(COL_COMPLETED_HOURS);
        queryBuilder.append(" TEXT )");

        return queryBuilder.toString();
    }

    public static String getAllStudentsQuery() {
        return "SELECT * FROM " + TABLE_NAME;
    }

    public static String getOneStudentById(int id) {
        return String.format("SELECT * FROM %s WHERE %s = \"%d\"", TABLE_NAME, COL_ID, id);
    }

    public static String getWhereClauseById() {
        return String.format(Locale.US, "%s = ", COL_ID);
    }
}
