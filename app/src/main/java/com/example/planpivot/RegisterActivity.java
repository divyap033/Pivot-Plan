package com.example.planpivot;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
    TextView alreadyacc_txt;
    EditText username_ip,email_ip,pass_ip,conpass_ip;
    Button register_btn;

    String emailPattern ="^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    ProgressDialog progressDialog;

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        alreadyacc_txt = findViewById(R.id.alreadyacc_txt);
        username_ip=findViewById(R.id.username_ip);
        email_ip=findViewById(R.id.email_ip);
        pass_ip=findViewById(R.id.pass_ip);
        conpass_ip=findViewById(R.id.conpass_ip);
        register_btn=findViewById(R.id.register_btn);
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();

        progressDialog = new ProgressDialog(this);
        alreadyacc_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,MainActivity.class));
            }
        });
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PerfoAuth();
            }
        });


    }
    private void PerfoAuth(){
        String email=email_ip.getText().toString();
        String password =pass_ip.getText().toString();
        String confirmPassword = conpass_ip.getText().toString();

        if (!email.matches(emailPattern)){
            email_ip.setError("Enter Correct Email");
        }else if (password.isEmpty() || password.length()<8){
            pass_ip.setError("Enter Correct Password");
        }else if (!password.equals(confirmPassword)){
            conpass_ip.setError("Password did not match");
        }else{
            progressDialog.setMessage("Please Wait....");
            progressDialog.setTitle("Registration");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth .createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        progressDialog.dismiss();
                        sendUserToNextActivity();
                        Toast.makeText(RegisterActivity.this,"Registration Successful", Toast.LENGTH_SHORT);
                    }else
                    {
                        progressDialog.dismiss();
                        Toast.makeText(RegisterActivity.this,"" +task.getException(), Toast.LENGTH_SHORT);
                    }
                }
            });

        }


    }
    private void sendUserToNextActivity() {
        Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
