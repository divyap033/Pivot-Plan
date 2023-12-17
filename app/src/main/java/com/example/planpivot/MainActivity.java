package com.example.planpivot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    TextView title;
    Button reg_btn;

    EditText email_ip, pass_ip;

    Button login_btn;



    String emailPattern = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    ProgressDialog progressDialog;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //Format Title
        title = findViewById(R.id.title_txt);
        title.setTypeface(Typeface.DEFAULT_BOLD);
        title.setTextSize(20);
        title.setTextColor(Color.BLUE);
        title.setShadowLayer(10, 5, 5, Color.GRAY);
        title.setLineSpacing(10, 1);
        title.setLetterSpacing(0.1f);
        title.setGravity(10);

        email_ip = findViewById(R.id.email_ip);
        pass_ip = findViewById(R.id.pass_ip);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        login_btn = findViewById(R.id.login_btn);






        progressDialog = new ProgressDialog(this);

        reg_btn = findViewById(R.id.reg_btn);
        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            }

        });


        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performLogin();

            }
        });

    }

    private void performLogin() {
        String email = email_ip.getText().toString();
        String password = pass_ip.getText().toString();

        if (!email.matches(emailPattern)) {
            email_ip.setError("Enter Correct Email");
        } else if (password.isEmpty() || password.length() < 8) {
            pass_ip.setError("Enter Correct Password");
        } else {
            progressDialog.setMessage("Please Wait....");
            progressDialog.setTitle("Login");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        progressDialog.dismiss();
                        sendUserToNextActivity();
                        Toast.makeText(MainActivity.this,"Login Successful", Toast.LENGTH_SHORT).show();
                    }else
                    {
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this,"" +task.getException(), Toast.LENGTH_SHORT);
                    }
                }
            });
        }


    }

    private void sendUserToNextActivity() {
        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}