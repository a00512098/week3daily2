package com.example.week3day2homework;

import android.support.annotation.NonNull;

public class Student {
    private int id;
    private String name;
    private String major;
    private String minor;
    private String year;
    private String gpa;
    private String hours;

    public Student(int id, String name, String major, String minor, String year, String gpa, String hours) {
        this.id = id;
        this.name = name;
        this.major = major;
        this.minor = minor;
        this.year = year;
        this.gpa = gpa;
        this.hours = hours;
    }

    public Student() {

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getMinor() {
        return minor;
    }

    public void setMinor(String minor) {
        this.minor = minor;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getGpa() {
        return gpa;
    }

    public void setGpa(String gpa) {
        this.gpa = gpa;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    @NonNull
    @Override
    public String toString() {
        return "Student {" + id + " || "
                + name + " || "
                + major + " || "
                + minor + " || "
                + year + " || "
                + gpa + " || "
                + hours + " }";
    }
}
