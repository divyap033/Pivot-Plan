package com.example.planpivot;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
    }
    private void sendUserToNextActivity(){
        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }
}