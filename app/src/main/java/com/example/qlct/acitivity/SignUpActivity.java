package com.example.qlct.acitivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.qlct.MainActivity;
import com.example.qlct.R;
import com.example.qlct.databinding.ActivityLogInBinding;
import com.example.qlct.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.ktx.Firebase;

public class SignUpActivity extends AppCompatActivity {
    ActivitySignUpBinding binding;
    FirebaseAuth auth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
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
    private void initView(){
        progressDialog= new ProgressDialog(this);
        auth = FirebaseAuth.getInstance();
        binding.btnDangKi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUp();
            }
        });
        binding.txtDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, LogInActivity.class));
            }
        });
    }

    private void SignUp() {
        String user = binding.edtEmail.getText().toString();
        String pass = binding.edtPassword.getText().toString();
        String pass2 = binding.edtRePassword.getText().toString();
        if(!isValidEmail(user)){
            Toast.makeText(this, "Email không hợp lệ", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!isValidPassword(pass)){
            Toast.makeText(this, "Mật khẩu có chữ hoa ,chữ thường,kí tự đặc biệt ,8 kí tự", Toast.LENGTH_SHORT).show();
            return;
        }

        if(pass.equals(pass2)==false){
            Toast.makeText(this, "mật khẩu nhập không khớp", Toast.LENGTH_SHORT).show();
            return ;
        }
        progressDialog.show();
        auth.createUserWithEmailAndPassword(user,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if(task.isSuccessful()){
                    Intent intent =new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(SignUpActivity.this, "dang ki that bai", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}