package com.project.daylight;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {


//    @BindView(R.id.login_email)
//    TextView login_email;
//
//    @BindView(R.id.login_pwd)
//    TextView login_pwd;
//
//    @BindView(R.id.login_btn)
//    Button login_btn;

    ImageView imageView;
    Button loginBtn;
    Button loginJoin;
    TextView loginEmail;
    TextView login_pwd;

    private FirebaseAuth mAuth;

    public boolean isValidUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        imageView = findViewById(R.id.image_logo);
        //imageView.setImageResource(R.drawable.logo);

//        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();

        loginBtn = (Button) findViewById(R.id.login_btn);
        loginJoin = (Button) findViewById(R.id.login_join);
        loginEmail = (TextView) findViewById(R.id.login_email);
        login_pwd = (TextView) findViewById(R.id.login_pwd);

        loginJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = loginEmail.getText().toString();
                String password = login_pwd.getText().toString();

                if (email.equals("") || password.equals("")) {
                    Toast.makeText(LoginActivity.this, "이메일 또는 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                checkValidUser(email, password);

                if (!isValidUser)
                    return;
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);


            }
        });

    }

    public void checkValidUser(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "로그인 성공!", Toast.LENGTH_SHORT).show();
                            isValidUser = true;
                        }else{
                            Toast.makeText(LoginActivity.this, "유효하지 않은 계정입니다.", Toast.LENGTH_SHORT).show();
                            isValidUser = false;
                        }
                    }
                });

    }
}

