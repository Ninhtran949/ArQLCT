package com.example.qlct.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.qlct.R;
import com.example.qlct.adapter.ChiTieuAdapter;
import com.example.qlct.databinding.FragmentHomeBinding;
import com.example.qlct.databinding.FragmentSoChiTieuBinding;
import com.example.qlct.model.ChiTieu;
import com.example.qlct.model.ViTien;
import com.example.qlct.sqlite.ChiTieuSql;
import com.example.qlct.sqlite.ViTienSql;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private ArrayList<ChiTieu> mChiTieus;
    private ChiTieuAdapter ctAdapter;
    ViTienSql viTienSql;
    ChiTieuSql chiTieuSql;
    FirebaseAuth auth;
    FirebaseUser user;
    Calendar c;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        binding = FragmentHomeBinding.bind(view);
        initView();
        return view;
    }
    private void initView() {
        viTienSql = new ViTienSql(getActivity(), ViTienSql.TableName, null, 1);
        chiTieuSql = new ChiTieuSql(getActivity(), ChiTieuSql.TableName, null, 1);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        try {
            if(!viTienSql.hasViTien(user.getEmail())){
                viTienSql.addViTien(new ViTien(0, "Vi" + user.getEmail(), 0, user.getEmail()));
            }
        } catch (ParseException e) {
            Log.d("error", e.getLocalizedMessage());
        }
        mChiTieus = new ArrayList<>();
        ctAdapter = new ChiTieuAdapter(getActivity(), mChiTieus);
        binding.rvChiTieu.setAdapter(ctAdapter);
        binding.rvChiTieu.setLayoutManager(new LinearLayoutManager(getActivity()));

        initData();
    }
    private void initData() {
        try {
            binding.tvTTC.setText(String.valueOf(viTienSql.getViTien(user.getEmail()).get(0).getSoTien()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        initChiTieu();
        c = Calendar.getInstance();
        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        try {
            pieEntries.add(new PieEntry(1f, (float) chiTieuSql.getMoneyInMonthCT(user.getEmail(), c.get(Calendar.MONTH) -1,c.get(Calendar.YEAR))));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            pieEntries.add(new PieEntry(2f, (float) chiTieuSql.getMoneyInMonthCT(user.getEmail(), c.get(Calendar.MONTH),c.get(Calendar.YEAR))));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // Initalize bar data set
        PieDataSet pieDataSet = new PieDataSet(pieEntries,"Thu    Chi");
        // Set color
        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        // Hide draw value
        pieDataSet.setDrawValues(false);
        // Set bar data
        binding.chartTC.setData(new PieData(pieDataSet));
        // Set animation
//        binding.chartTC.animateY(2000);
        binding.chartTC.setDescription(null);
    }
    private void initChiTieu() {
        try {
            mChiTieus = chiTieuSql.getTop5(user.getEmail());
        } catch (ParseException e) {
            Log.d("error", e.getLocalizedMessage());
        }
        ctAdapter = new ChiTieuAdapter(getActivity(), mChiTieus);
        binding.rvChiTieu.setAdapter(ctAdapter);
        binding.rvChiTieu.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onStart() {
        super.onStart();
        initData();
    }
}