package com.example.qlct.acitivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;


import android.view.View;
import android.widget.Toast;

import com.example.qlct.MainActivity;

import com.example.qlct.databinding.ActivityLogInBinding;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogInActivity extends AppCompatActivity {
    FirebaseAuth auth;
    ActivityLogInBinding binding;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLogInBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        initView();
    }
    public boolean isValidPassword(String password) {
        String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        return password.matches(passwordRegex);
    }
    public boolean isValidEmail(String password) {
        String passwordRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+.[a-zA-Z]{2,}$";
        return password.matches(passwordRegex);
    }
    private void initView() {
        progressDialog=new ProgressDialog(this);
        auth = FirebaseAuth.getInstance();
        binding.btnDangNhapGmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogIn();
            }
        });

        binding.txtDangKi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogInActivity.this, SignUpActivity.class));
                finish();
            }
        });
        binding.txtQuenMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogInActivity.this, ForgotPasswordActivity.class));
            }
        });
    }
    private void LogIn() {
        String email = binding.edtEmail.getText().toString();
        String pass = binding.edtPassword.getText().toString();
        if(!isValidEmail(email)){
            Toast.makeText(this, "Email không hợp lệ", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!isValidPassword(pass)){
            Toast.makeText(this, "Mật khẩu có chữ hoa ,chữ thường,kí tự đặc biệt ,8 kí tự", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.show();
        auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if(task.isSuccessful()){
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(LogInActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else Toast.makeText(getApplicationContext(), "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }
}