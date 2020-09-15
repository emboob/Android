package com.project.daylight;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SignupActivity extends AppCompatActivity {

    TextView signUpEmail;
    TextView signUpPwd;
    Button signUpBtn;
    Button signUpCancel;

    String email;
    String password;

    FirebaseAuth mAuth;

    String TAG = "회원가입 : ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signUpBtn = findViewById(R.id.sign_up_btn);
        signUpCancel =  findViewById(R.id.sign_up_cancel);
        signUpEmail =  findViewById(R.id.sign_up_email);
        signUpPwd = findViewById(R.id.sign_up_pwd);

        mAuth = FirebaseAuth.getInstance();

        Log.d(TAG, "회원가입 :" + email);

        signUpBtn.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                email = signUpEmail.getText().toString();
                password = signUpPwd.getText().toString();
                if (email.equals("") || password.equals("") || email == "" || password == "") {
                    Toast.makeText(getApplicationContext(), "이메일 또는 비밀번호를 입력해주세요", Toast.LENGTH_LONG).show();
                    return;
                }
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(getApplicationContext(), "이메일 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                createUser(email, password);
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);

            }
        });

        signUpCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    public void createUser(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "회원 가입 성공!");
                    FirebaseUser user = mAuth.getCurrentUser();

                } else {
                    Log.w(TAG, "회원가입 실패", task.getException());
                    Toast.makeText(SignupActivity.this, "회원가입 실패", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
