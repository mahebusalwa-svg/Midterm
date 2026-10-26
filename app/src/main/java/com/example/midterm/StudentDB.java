package com.example.midterm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class StudentDB extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "StudentDB.db";
    private static final int DATABASE_VERSION = 1;

    public StudentDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE students (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "student_name TEXT, " +
                "student_id TEXT UNIQUE, " +
                "major TEXT)";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(
            SQLiteDatabase db,
            int oldVersion,
            int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS students");
        onCreate(db);
    }

    // =========================
    // ADD STUDENT
    // =========================
    public boolean addStudent(
            String name,
            String studentId,
            String major) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("student_name", name);
        values.put("student_id", studentId);
        values.put("major", major);

        long result = db.insert(
                "students",
                null,
                values
        );

        return result != -1;
    }

    // =========================
    // VIEW STUDENTS
    // =========================
    public String getStudents() {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM students",
                null
        );

        StringBuilder result = new StringBuilder();

        if (cursor.moveToFirst()) {

            do {

                int id = cursor.getInt(
                        cursor.getColumnIndexOrThrow("id")
                );

                String name = cursor.getString(
                        cursor.getColumnIndexOrThrow("student_name")
                );

                String studentId = cursor.getString(
                        cursor.getColumnIndexOrThrow("student_id")
                );

                String major = cursor.getString(
                        cursor.getColumnIndexOrThrow("major")
                );

                result.append("ID: ")
                        .append(id)
                        .append("\n");

                result.append("Student Name: ")
                        .append(name)
                        .append("\n");

                result.append("Student ID: ")
                        .append(studentId)
                        .append("\n");

                result.append("Major: ")
                        .append(major)
                        .append("\n");

                result.append("--------------------------\n");

            } while (cursor.moveToNext());

        } else {

            result.append("No student data");

        }

        cursor.close();

        return result.toString();
    }

    // =========================
    // EDIT STUDENT
    // =========================
    public boolean editStudent(
            String studentId,
            String name,
            String major) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("student_name", name);
        values.put("major", major);

        int result = db.update(
                "students",
                values,
                "student_id = ?",
                new String[]{studentId}
        );

        return result > 0;
    }

    // =========================
    // DELETE STUDENT
    // =========================
    public boolean deleteStudent(String studentId) {

        SQLiteDatabase db = this.getWritableDatabase();

        int result = db.delete(
                "students",
                "student_id = ?",
                new String[]{studentId}
        );

        return result > 0;
    }
}