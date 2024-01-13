package com.example.qlct.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.qlct.R;
import com.example.qlct.acitivity.ProfileDetailActivity;
import com.example.qlct.databinding.FragmentHomeBinding;
import com.example.qlct.databinding.FragmentSettingBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SettingFragment extends Fragment {
    FragmentSettingBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        binding = FragmentSettingBinding.bind(view);
        initView();
        showUserInformation();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        showUserInformation();
    }

    private void initView() {
        binding.btnHoTro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.llHotro.getVisibility() == View.VISIBLE) {
                    binding.llHotro.setVisibility(View.GONE);
                } else {
                    binding.llHotro.setVisibility(View.VISIBLE);
                }
            }
        });
        binding.btnGioiThieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.txtGioithieu.getVisibility() == View.VISIBLE) {
                    binding.txtGioithieu.setVisibility(View.GONE);
                } else {
                    binding.txtGioithieu.setVisibility(View.VISIBLE);
                }
            }
        });
        binding.btnQLTK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ProfileDetailActivity.class));
            }
        });
        binding.txtSDT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(Intent.ACTION_VIEW,
                        Uri.parse("tel:"+ binding.txtSDT.getText()));
                getActivity().startActivity(intent1);
            }
        });
    }
    private void showUserInformation() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            return;
        }
        String email = user.getEmail();
        Uri photoUrl = user.getPhotoUrl();
        binding.txtName.setText(email);
        if (photoUrl != null) {
            Glide.with(this).load(photoUrl).into(binding.imgAvt);
        }
    }
}