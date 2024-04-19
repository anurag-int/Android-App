package com.example.myapp;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private TextView selectedDateTextView;
    private EditText editTextPatientName, editTextSymptoms, editTextPatientContactNumber,
            editTextTextEmailAddress, editTextBloodPressure, editTextHeartRate, editTextTemperature,
            editTextPharmasy, editTextNameOfPhysican, editTextContactNoOfPhysican, editTextSignature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Enable edge-to-edge display
        EdgeToEdge.enable(this);

        // Initialize EditText fields
        editTextPatientName = findViewById(R.id.editTextPatientName);
        editTextSymptoms = findViewById(R.id.editTextSymptoms);
        editTextPatientContactNumber = findViewById(R.id.editTextPatientContactNumber);
        editTextTextEmailAddress = findViewById(R.id.editTextTextEmailAddress);
        editTextBloodPressure = findViewById(R.id.editTextBloodPressure);
        editTextHeartRate = findViewById(R.id.editTextHeartRate);
        editTextTemperature = findViewById(R.id.editTextTemperature);
        editTextPharmasy = findViewById(R.id.editTextPharmasy);
        editTextNameOfPhysican = findViewById(R.id.editTextNameOfPhysican);
        editTextContactNoOfPhysican = findViewById(R.id.editTextContactNoOfPhysican);
        editTextSignature = findViewById(R.id.editTextSignature);

        Spinner insuranceSpinner = findViewById(R.id.insurance);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> insuranceAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.insurance, // Reference the string array resource
                android.R.layout.simple_spinner_item // Use a default spinner layout
        );

        // Specify the layout to use when the list of choices appears
        insuranceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        insuranceSpinner.setAdapter(insuranceAdapter);

        // Find the Spinner by its ID
        Spinner spinner = findViewById(R.id.previousMedicalConditions);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.disease_array, // Reference the string array resource
                android.R.layout.simple_spinner_item // Use a default spinner layout
        );

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        final Calendar calendar = Calendar.getInstance();
        final Button showDateButton = findViewById(R.id.showDateButton);
        selectedDateTextView = findViewById(R.id.selectedDateTextView);

        final DatePicker datePicker = findViewById(R.id.datePicker);
        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
                new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        calendar.set(year, monthOfYear, dayOfMonth);
                    }
                });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(calendar);
            }
        };

        showDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(MainActivity.this, dateSetListener, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        // Apply system insets to the main layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Set OnClickListener for the submit button
        Button submitButton = findViewById(R.id.button2);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSubmit(v);
            }
        });
    }

    private void updateLabel(Calendar calendar) {
        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        selectedDateTextView.setText(sdf.format(calendar.getTime()));
    }

    private boolean isEmpty(EditText editText) {
        return editText.getText().toString().trim().isEmpty();
    }


    private boolean isValidPhoneNumber(String phoneNumber) {
        // Check if the phone number starts with "+1"
        return phoneNumber.startsWith("+1") && phoneNumber.length() == 12;
    }

    private boolean validateFields() {
        boolean isValid = true;

        // Validate each field individually
        if (isEmpty(editTextPatientName)) {
            editTextPatientName.setError("Please enter patient name");
            isValid = false;
        }

        if (isEmpty(editTextSymptoms)) {
            editTextSymptoms.setError("Please enter symptoms");
            isValid = false;
        }

        if (isEmpty(editTextPatientContactNumber)) {
            editTextPatientContactNumber.setError("Please enter contact number");
            isValid = false;
        }
        String phoneNumber = editTextPatientContactNumber.getText().toString().trim();
        if (!isValidPhoneNumber(phoneNumber)) {
            editTextPatientContactNumber.setError("Please enter a valid US phone number starting with +1");
            isValid = false;
        }

        if (isEmpty(editTextTextEmailAddress)) {
            editTextTextEmailAddress.setError("Please enter email address");
            isValid = false;
        }

        if (isEmpty(editTextBloodPressure)) {
            editTextBloodPressure.setError("Please enter blood pressure");
            isValid = false;
        }

        if (isEmpty(editTextHeartRate)) {
            editTextHeartRate.setError("Please enter heart rate");
            isValid = false;
        }

        if (isEmpty(editTextTemperature)) {
            editTextTemperature.setError("Please enter temperature");
            isValid = false;
        }

        if (isEmpty(editTextPharmasy)) {
            editTextPharmasy.setError("Please enter pharmacy");
            isValid = false;
        }

        if (isEmpty(editTextNameOfPhysican)) {
            editTextNameOfPhysican.setError("Please enter physician's name");
            isValid = false;
        }

        if (isEmpty(editTextContactNoOfPhysican)) {
            editTextContactNoOfPhysican.setError("Please enter physician's contact number");
            isValid = false;
        }

        if (isEmpty(editTextSignature)) {
            editTextSignature.setError("Please enter signature");
            isValid = false;
        }

        return isValid;
    }

    public void onSubmit(View view) {
        if (validateFields()) {
            // All fields are filled, proceed with submission
            Toast.makeText(this, "Form submitted successfully!", Toast.LENGTH_SHORT).show();
            // Clear all fields
            clearFields();
        } else {
            // Display error message if fields are not filled
            Toast.makeText(this, "Please fill all the required fields", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearFields() {
        editTextPatientName.setText("");
        editTextSymptoms.setText("");
        editTextPatientContactNumber.setText("");
        editTextTextEmailAddress.setText("");
        selectedDateTextView.setText("");
        editTextBloodPressure.setText("");
        editTextHeartRate.setText("");
        editTextTemperature.setText("");
        editTextPharmasy.setText("");
        editTextNameOfPhysican.setText("");
        editTextContactNoOfPhysican.setText("");
        editTextSignature.setText("");
        Spinner insuranceSpinner = findViewById(R.id.insurance);
        insuranceSpinner.setSelection(0); // Set the default selection to the first item

        Spinner spinner = findViewById(R.id.previousMedicalConditions);
        spinner.setSelection(0);
    }
}
