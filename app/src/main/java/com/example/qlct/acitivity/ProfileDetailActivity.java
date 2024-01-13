package com.example.qlct.acitivity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.qlct.MainActivity;
import com.example.qlct.R;
import com.example.qlct.databinding.ActivityAddChiTieuBinding;
import com.example.qlct.databinding.ActivityProfileDetailBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.io.IOException;

public class ProfileDetailActivity extends AppCompatActivity {
    ActivityProfileDetailBinding binding;
    FirebaseAuth auth;
    private Uri uri;
    private static final int MY_REQUEST_CODE = 10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        initView();
        setEnabled(false);
        initListener();
        showUserInformation();
    }
    final ActivityResultLauncher<Intent> mActivityResultLaucher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if ((result.getResultCode()==RESULT_OK))
            {
                Intent intent = result.getData();
                if(intent == null)
                {
                    return;
                }
                uri = intent.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                    setBitmapImageView(bitmap);
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }

    });
    private void initView(){
        auth = FirebaseAuth.getInstance();
        binding.btnDangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                finish();
                startActivity(new Intent(ProfileDetailActivity.this, MainActivity.class));
            }
        });
        binding.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEnabled(true);
                binding.edtName.setFocusable(true);
                binding.edtName.setFocusableInTouchMode(true);
                binding.llSave.setVisibility(View.VISIBLE);
            }
        });
        binding.btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickUpDate();
                binding.llSave.setVisibility(View.GONE);
                setEnabled(false);
            }
        });
        binding.btnCloseQLTK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void setEnabled(boolean yes) {
        binding.edtEmail.setEnabled(yes);
        binding.edtName.setEnabled(yes);
        binding.imgAvt.setEnabled(yes);
    }
    private void initListener(){
        binding.imgAvt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickRequestPermission();
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == MY_REQUEST_CODE)
        {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                openGallery();
            }
        }
    }
    public void openGallery(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivityResultLaucher.launch(Intent.createChooser(intent,"Select Picture"));
    }
    private void onClickRequestPermission()
    {

        if(this == null)
        {
            return;
        }
        if(Build.VERSION.SDK_INT <Build.VERSION_CODES.M){
            this.openGallery();
            return;
        }
        if (this.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
        {
            this.openGallery();
        } else {
            String [] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
            this.requestPermissions(permissions, MY_REQUEST_CODE);
        }
    }
    private void showUserInformation()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null)
        {
            return;
        }
        String name = user.getDisplayName();
        String email = user.getEmail();
        Uri photoUrl = user.getPhotoUrl();

        if (name == null)
        {
            binding.edtName.setVisibility(View.GONE);
            binding.txtName.setVisibility(View.GONE);
        } else {
            binding.edtName.setVisibility(View.VISIBLE);
            binding.txtName.setVisibility(View.VISIBLE);
            binding.edtName.setText(name);
        }
        binding.edtEmail.setText(email);
        binding.edtEmail.setFocusableInTouchMode(false);
        binding.edtEmail.setFocusable(false);
        binding.edtName.setFocusableInTouchMode(false);
        binding.edtName.setFocusable(false);
        if(photoUrl != null)
        {
            Glide.with(this).load(photoUrl).into(binding.imgAvt);
        }


    }
    public void setBitmapImageView(Bitmap bitmapImageView)
    {
        binding.imgAvt.setImageBitmap(bitmapImageView);
    }
    private void onClickUpDate(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user==null)
        {
            return;
        }
        String strfullName = binding.edtName.getText().toString().trim();
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(strfullName)
                .setPhotoUri(uri)
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),"Update success",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Lỗi",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}