package com.example.midterm;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    StudentDB studentDB;

    EditText etStudentName;
    EditText etStudentId;

    Spinner spinnerMajor;

    Button btnAdd;
    Button btnView;
    Button btnEdit;
    Button btnDelete;

    TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // DATABASE
        studentDB = new StudentDB(this);

        // INPUT
        etStudentName = findViewById(R.id.etStudentName);
        etStudentId = findViewById(R.id.etStudentId);

        // SPINNER
        spinnerMajor = findViewById(R.id.spinnerMajor);

        // BUTTONS
        btnAdd = findViewById(R.id.btnAdd);
        btnView = findViewById(R.id.btnView);
        btnEdit = findViewById(R.id.btnEdit);
        btnDelete = findViewById(R.id.btnDelete);

        // RESULT
        tvResult = findViewById(R.id.tvResult);

        setupSpinner();

        // ADD
        btnAdd.setOnClickListener(v -> addStudent());

        // VIEW
        btnView.setOnClickListener(v -> viewStudents());

        // EDIT
        btnEdit.setOnClickListener(v -> editStudent());

        // DELETE
        btnDelete.setOnClickListener(v -> deleteStudent());
    }

    // =========================
    // SPINNER
    // =========================
    private void setupSpinner() {

        String[] majors = {
                "Informatics",
                "Information Systems"
        };

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_item,
                        majors
                );

        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        spinnerMajor.setAdapter(adapter);
    }

    // =========================
    // ADD
    // =========================
    private void addStudent() {

        String name =
                etStudentName.getText()
                        .toString()
                        .trim();

        String studentId =
                etStudentId.getText()
                        .toString()
                        .trim();

        String major =
                spinnerMajor.getSelectedItem()
                        .toString();

        // CHECK NAME
        if (name.isEmpty()) {

            etStudentName.setError(
                    "Enter Student Name"
            );

            return;
        }

        // CHECK STUDENT ID
        if (studentId.isEmpty()) {

            etStudentId.setError(
                    "Enter Student ID"
            );

            return;
        }

        boolean success =
                studentDB.addStudent(
                        name,
                        studentId,
                        major
                );

        if (success) {

            Toast.makeText(
                    this,
                    "Student added successfully",
                    Toast.LENGTH_SHORT
            ).show();

            clearFields();

            viewStudents();

        } else {

            Toast.makeText(
                    this,
                    "Student ID already exists",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    // =========================
    // VIEW
    // =========================
    private void viewStudents() {

        String data =
                studentDB.getStudents();

        tvResult.setText(data);
    }

    // =========================
    // EDIT
    // =========================
    private void editStudent() {

        String name =
                etStudentName.getText()
                        .toString()
                        .trim();

        String studentId =
                etStudentId.getText()
                        .toString()
                        .trim();

        String major =
                spinnerMajor.getSelectedItem()
                        .toString();

        // CHECK STUDENT ID
        if (studentId.isEmpty()) {

            etStudentId.setError(
                    "Enter Student ID"
            );

            return;
        }

        // CHECK NAME
        if (name.isEmpty()) {

            etStudentName.setError(
                    "Enter Student Name"
            );

            return;
        }

        boolean success =
                studentDB.editStudent(
                        studentId,
                        name,
                        major
                );

        if (success) {

            Toast.makeText(
                    this,
                    "Student updated successfully",
                    Toast.LENGTH_SHORT
            ).show();

            clearFields();

            viewStudents();

        } else {

            Toast.makeText(
                    this,
                    "Student ID not found",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    // =========================
    // DELETE
    // =========================
    private void deleteStudent() {

        String studentId =
                etStudentId.getText()
                        .toString()
                        .trim();

        // CHECK STUDENT ID
        if (studentId.isEmpty()) {

            etStudentId.setError(
                    "Enter Student ID"
            );

            return;
        }

        boolean success =
                studentDB.deleteStudent(
                        studentId
                );

        if (success) {

            Toast.makeText(
                    this,
                    "Student deleted successfully",
                    Toast.LENGTH_SHORT
            ).show();

            clearFields();

            viewStudents();

        } else {

            Toast.makeText(
                    this,
                    "Student ID not found",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    // =========================
    // CLEAR INPUT
    // =========================
    private void clearFields() {

        etStudentName.setText("");
        etStudentId.setText("");

        spinnerMajor.setSelection(0);
    }
}