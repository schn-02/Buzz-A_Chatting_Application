package com.example.buzz.Authentication;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.buzz.MainActivity;
import com.example.buzz.Model.users;
import com.example.buzz.R;
import com.facebook.CallbackManager;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class SignUp extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseDatabase database;
    ProgressDialog progressDialog;
    private CallbackManager callbackManager;
    FirebaseStorage storage;
    ActivityResultLauncher<String> launcher;
    String profilePicUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initialize Firebase Auth and Database
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        callbackManager = CallbackManager.Factory.create();

        Button btn = findViewById(R.id.signup);
        TextView btn2 = findViewById(R.id.al);
        EditText email = findViewById(R.id.email1);
        EditText name = findViewById(R.id.name1);
        EditText pass = findViewById(R.id.password1);
        ImageView img = findViewById(R.id.add);




   btn2.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent it = new Intent(SignUp.this,SignIn.class);
        startActivity(it);
    }
});
        launcher = registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
            if (uri != null) {
                img.setImageURI(uri);
                progressDialog.show();
                uploadProfilePicture(uri);
            } else {
                Toast.makeText(SignUp.this, "No image selected", Toast.LENGTH_SHORT).show();
            }
        });

        img.setOnClickListener(view -> launcher.launch("image/*"));

        progressDialog = new ProgressDialog(SignUp.this);
        progressDialog.setMessage("Please wait...");

        btn.setOnClickListener(view -> {
            String emailInput = email.getText().toString().trim();
            String passwordInput = pass.getText().toString().trim();
            String nameInput = name.getText().toString().trim();

            if (TextUtils.isEmpty(emailInput) || TextUtils.isEmpty(passwordInput) || TextUtils.isEmpty(nameInput) )
            {
                Toast.makeText(SignUp.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
                return;
            }
            if (img.getDrawable()==null)
            {
                Toast.makeText(SignUp.this, "Please select your profile pic also", Toast.LENGTH_SHORT).show();
                return;
            }

            if (nameInput.length() > 10) {
                Toast.makeText(SignUp.this, "Name cannot exceed 10 characters", Toast.LENGTH_SHORT).show();
                return;
            }

            progressDialog.setTitle("Creating Account");
            progressDialog.show();

            createUser(emailInput, passwordInput, nameInput);
        });
    }

    private void uploadProfilePicture(Uri uri) {
        StorageReference reference = storage.getReference().child("images").child(System.currentTimeMillis() + ".jpg");
        reference.putFile(uri).addOnSuccessListener(taskSnapshot -> {
            reference.getDownloadUrl().addOnSuccessListener(downloadUrl -> {
                profilePicUrl = downloadUrl.toString();
                progressDialog.dismiss();
                Toast.makeText(SignUp.this, "Image uploaded successfully", Toast.LENGTH_SHORT).show();
            });
        });
    }

    private void createUser(String email, String password, String name) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String id = task.getResult().getUser().getUid();
                        users user = new users(email, name, password, profilePicUrl);
                        database.getReference().child("user").child(id).setValue(user)
                                .addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()) {
                                        Toast.makeText(SignUp.this, "User Created Successfully", Toast.LENGTH_SHORT).show();
                                        progressDialog.show();

                                        startActivity(new Intent(SignUp.this, MainActivity.class));
                                        finish();
                                        progressDialog.dismiss();

                                    } else {
                                        Toast.makeText(SignUp.this, "Failed to save user data: " + task1.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                    progressDialog.dismiss();
                                });
                    } else {
                        Toast.makeText(SignUp.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });





    }

    public void showDialog()
    {
        TextView btn1, btn2, btn3;
        Dialog dialog = new Dialog(SignUp.this);
        dialog.setContentView(R.layout.activity_alert_dialog);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        btn2 = dialog.findViewById(R.id.textView3);
        btn3 = dialog.findViewById(R.id.textView2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                dialog.dismiss();
            }
        });

        dialog.show();

    }

}