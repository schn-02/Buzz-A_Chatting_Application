package com.example.buzz.Authentication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.buzz.MainActivity;
import com.example.buzz.Model.users;
import com.example.buzz.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

public class SignIn extends AppCompatActivity {
    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;
    private GoogleSignInClient googleSignInClient;
    FirebaseAuth auth;
    users users;
   FirebaseDatabase database;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_in);

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(SignIn.this);

        progressDialog.setMessage("Wait a seconds");


        Button google = findViewById(R.id.g2);
         TextView Email = findViewById(R.id.email2);
        TextView pass = findViewById(R.id.password2);
        TextView textView = findViewById(R.id.da);
        Button btn1 = findViewById(R.id.signin);
        FirebaseUser firebaseUser = auth.getCurrentUser();

        if (firebaseUser!= null) {
            Intent mainIntent = new Intent(SignIn.this, MainActivity.class);
            startActivity(mainIntent);
            finish();
        }



        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = Email.getText().toString().trim();
                String password = pass.getText().toString().trim();

                if (TextUtils.isEmpty(email) && TextUtils.isEmpty(password) )
                {
                    Toast tt = Toast.makeText(SignIn.this, "Please enter email and password", Toast.LENGTH_SHORT);
                    tt.show();
                    new Handler().postDelayed(tt::cancel, 2000);
                    return;
                }
                progressDialog.show();
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressDialog.dismiss();
                                if (task.isSuccessful()) {
                                    Toast.makeText(SignIn.this, "Signed in successfully", Toast.LENGTH_SHORT).show();
                                    Intent it =  new Intent(SignIn.this, MainActivity.class);
                                    startActivity(it);
                                    finish();
                                } else {
                                    Toast.makeText(SignIn.this, "user Does not Exits: " , Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
        textView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent it = new Intent(SignIn.this, SignUp.class);
                startActivity(it);
            }
        });
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id)) // Client ID
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(SignIn.this, gso);

        // Google Sign-In button
        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setIcon(R.drawable.g);
                progressDialog.setTitle("Connecting Your Google Account");
                progressDialog.show();
                googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        signInWithGoogle();
                    }
                });

            }
        });

    }
    private void signInWithGoogle() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result from Google Sign-In
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign-In successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                Log.w("TAG", "Google sign-in failed", e);
                Toast.makeText(this, "Google Sign-In failed", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);

        // Check if user is already registered in Firebase
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = auth.getCurrentUser();

                            if (user != null) {
                                // Check if this is a new user or an existing user
                                boolean isNewUser = task.getResult().getAdditionalUserInfo().isNewUser();

                                if (!isNewUser) {
                                    // User is already registered, proceed to MainActivity
                                    Toast.makeText(SignIn.this, "Google Sign-In successful", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                    Intent it = new Intent(SignIn.this, MainActivity.class);
                                    startActivity(it);
                                } else {
                                    // User is not registered, sign-in should not happen

                                    Toast tt = Toast.makeText(SignIn.this, "This account is not registered. Please " +
                                            "sign up " +
                                            "first.", Toast.LENGTH_SHORT);
                                    tt.show();
                                    new Handler().postDelayed( tt::cancel ,2000);
                                    auth.signOut();  // Optional: Sign out immediately if you want to restrict new users
                                    progressDialog.dismiss();
                                }
                            }
                        } else {
                            // If sign-in fails
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                            Toast.makeText(SignIn.this, "Authentication Failed.", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                });
    }

    }