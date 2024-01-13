package com.example.qlct.acitivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.qlct.R;
import com.example.qlct.databinding.ActivityForgotPasswordBinding;
import com.example.qlct.databinding.ActivityProfileDetailBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {
    ActivityForgotPasswordBinding binding;
    FirebaseAuth auth;
    public boolean isValidEmail(String password) {
        String passwordRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+.[a-zA-Z]{2,}$";
        return password.matches(passwordRegex);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        auth = FirebaseAuth.getInstance();
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.OkForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailStr = binding.textEmailForgot.getText().toString();
                if(!isValidEmail(emailStr)){
                    Toast.makeText(ForgotPasswordActivity.this, "email không phù hợp",
                            Toast.LENGTH_SHORT).show();
                }
                auth.sendPasswordResetEmail(emailStr).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(ForgotPasswordActivity.this, "Đã gửi qua email, hãy kiểm tra lại",
                                    Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            Toast.makeText(ForgotPasswordActivity.this, "Lỗi gửi mail", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}