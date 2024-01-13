package com.example.qlct;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.qlct.acitivity.AddChiTieuActivity;
import com.example.qlct.acitivity.LogInActivity;
import com.example.qlct.fragments.HomeFragment;
import com.example.qlct.fragments.PlanFragment;
import com.example.qlct.fragments.SettingFragment;
import com.example.qlct.fragments.SoChiTieuFragment;
import com.example.qlct.fragments.SoChiTieuViewFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {
    private BottomNavigationView mBottomNavigationView;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        if(user == null) {
            startActivity(new Intent(MainActivity.this, LogInActivity.class));
            finish();
        }
        mBottomNavigationView = findViewById(R.id.bottomNagivationView);
        replaceFragment(new HomeFragment());

        mBottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_home:
                        replaceFragment(new HomeFragment());
                        break;
                    case R.id.menu_transaction:
                        replaceFragment(new SoChiTieuFragment());
                        break;
                    case R.id.menu_add:
                        startActivity(new Intent(MainActivity.this, AddChiTieuActivity.class));
                        break;
                    case R.id.menu_plan:
                        replaceFragment(new PlanFragment());
                        break;
                    case R.id.menu_settings:
                        replaceFragment(new SettingFragment());
                        break;
                }
                return true;
            }
        });

    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}
