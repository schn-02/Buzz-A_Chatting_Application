package com.example.buzz.Authentication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.example.buzz.R;

public class Mobile extends AppCompatActivity {

    private TextView enteredNumbers;
    private StringBuilder currentNumbers = new StringBuilder();
    private static final int CALL_PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile);

        enteredNumbers = findViewById(R.id.no);

        // Get references to buttons
        Button button0 = findViewById(R.id.btn0);
        Button button1 = findViewById(R.id.btn1);
        Button button2 = findViewById(R.id.btn2);
        Button button3 = findViewById(R.id.btn3);
        Button button4 = findViewById(R.id.btn4);
        Button button5 = findViewById(R.id.btn5);
        Button button6 = findViewById(R.id.btn6);
        Button button7 = findViewById(R.id.btn7);
        Button button8 = findViewById(R.id.btn8);
        Button button9 = findViewById(R.id.btn9);
        Button buttonHash = findViewById(R.id.btnhash);
        Button buttonStar = findViewById(R.id.btnstar);
        Button buttonCall = findViewById(R.id.call);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        Button buttonClear = findViewById(R.id.btnClear);
        // Add a call button

        // Set click listeners for buttons
        button1.setOnClickListener(view -> appendNumber("1"));
        button2.setOnClickListener(view -> appendNumber("2"));
        button3.setOnClickListener(view -> appendNumber("3"));
        button4.setOnClickListener(view -> appendNumber("4"));
        button5.setOnClickListener(view -> appendNumber("5"));
        button6.setOnClickListener(view -> appendNumber("6"));
        button7.setOnClickListener(view -> appendNumber("7"));
        button8.setOnClickListener(view -> appendNumber("8"));
        button9.setOnClickListener(view -> appendNumber("9"));
        button0.setOnClickListener(view -> appendNumber("0"));
        buttonHash.setOnClickListener(view -> appendNumber("#"));
        buttonStar.setOnClickListener(view -> appendNumber("*"));

        // Set click listener for call button
        buttonCall.setOnClickListener(view -> {
            if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // Request permission
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.CALL_PHONE},
                        CALL_PERMISSION_REQUEST_CODE);
            } else {
                makeCall();
            }
        });
        buttonClear.setOnClickListener(view -> clearInput());
    }



        private void clearInput()
    {
            currentNumbers.setLength(0); // Clear the StringBuilder
            enteredNumbers.setText(""); // Clear the TextView
        }


    private void appendNumber(String number) {
        currentNumbers.append(number);
        enteredNumbers.setText(currentNumbers.toString());
    }

    private void makeCall() {
        String numberToCall = currentNumbers.toString();
        if (!numberToCall.isEmpty()) {
            // Check if number length is valid for a call
            if (numberToCall.length() < 3) {
                enteredNumbers.setError("Please enter a valid phone number");
                return;
            }

            // Intent to make a phone call
            Intent phoneIntent = new Intent(Intent.ACTION_CALL);
            phoneIntent.setData(Uri.parse("tel:" + numberToCall));



            // Delay the call to show splash screen first
            new android.os.Handler().postDelayed(() -> startActivity(phoneIntent), 2000); // 2-second delay
        } else {
            enteredNumbers.setError("Please enter a number to call");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CALL_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makeCall();
            } else {
                enteredNumbers.setError("Permission to make calls denied");
            }
        }
    }
}
