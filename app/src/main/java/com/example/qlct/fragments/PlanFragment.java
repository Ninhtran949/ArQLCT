package com.example.qlct.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.qlct.R;
import com.example.qlct.acitivity.AddPlanActivity;
import com.example.qlct.acitivity.PlanDetailAcitivity;
import com.example.qlct.adapter.OnClickListener;
import com.example.qlct.adapter.PlanAdapter;
import com.example.qlct.databinding.FragmentHomeBinding;
import com.example.qlct.databinding.FragmentPlanBinding;
import com.example.qlct.model.KeHoach;
import com.example.qlct.sqlite.KeHoachSql;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;


public class PlanFragment extends Fragment {
    FragmentPlanBinding binding;
    private PlanAdapter mPlanAdapter;
    private ArrayList<KeHoach> mPlans;
    private PieData pieData;
    private PieDataSet pieDataSet;
    FirebaseAuth auth;
    FirebaseUser user;
    KeHoachSql keHoachSql;
    private OnClickListener mOnClickListener = new OnClickListener() {
        @Override
        public void onClickListener(int position) {
            Intent intent = new Intent(getActivity(), PlanDetailAcitivity.class);
            intent.putExtra("item", position);
            startActivity(intent);
        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_plan, container, false);
        binding = FragmentPlanBinding.bind(view);
        initView();
        return view;
    }
    private void initView() {
        keHoachSql = new KeHoachSql(getActivity(), KeHoachSql.TableName, null, 1);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        getPieEntries();

        // setting text size
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Chưa hoàn thành"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Đã hoàn thành"));
        mPlans = new ArrayList<>();
        try {
            mPlans = keHoachSql.getListKeHoach(user.getEmail(), 0);
        } catch (ParseException e) {
            Log.d("error", e.getLocalizedMessage());
        }
        mPlanAdapter = new PlanAdapter(getContext(), mPlans, mOnClickListener);
        binding.rvKeHoach.setAdapter(mPlanAdapter);
        binding.rvKeHoach.setLayoutManager(new LinearLayoutManager(getContext()));
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int id = mPlans.get(viewHolder.getAdapterPosition()).getMaKeHoach();
                keHoachSql.deleteKeHoach(id);
                mPlanAdapter.notifyDataSetChanged();
            }
        });
        itemTouchHelper.attachToRecyclerView(binding.rvKeHoach);

        binding.btnAddPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddPlanActivity.class);
                startActivity(intent);
            }
        });


        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getText().toString().trim() == "Chưa hoàn thành") {
                    initData(0);
                }
                else if (tab.getText().toString().trim() == "Đã hoàn thành"){
                    initData(1);
                } else {
                    return;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        initData(0);
        getPieEntries();
    }

    @Override
    public void onStart() {
        super.onStart();
        initData(0);
        getPieEntries();
    }

    private void getPieEntries() {
        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        ArrayList<String> xEntrys = new ArrayList<>();
        float[] yData = new float[0];
        try {
            yData = new float[]{keHoachSql.getKeHoach(user.getEmail(), 0), keHoachSql.getKeHoach(user.getEmail(),1)};
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String[] xData = { "Chưa hoàn thành", "Hoàn thành"};

        for (int i = 0; i < yData.length;i++){
            yEntrys.add(new PieEntry(yData[i],i));
        }
        for (int i = 0; i < xData.length;i++){
            xEntrys.add(xData[i]);
        }
        pieDataSet=new PieDataSet(yEntrys,"Chưa hoàn thanh Đã hoàn thành");
        pieData = new PieData(pieDataSet);
        binding.planChart.setData(pieData);
        pieDataSet.setColors(ColorTemplate.PASTEL_COLORS);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(16f);
        binding.planChart.getDescription().setEnabled(false);
    }
    private void initData(int hoanthanh) {
        try {
            mPlans = keHoachSql.getListKeHoach(user.getEmail(), hoanthanh);
            mPlanAdapter = new PlanAdapter(getActivity(), mPlans, mOnClickListener);
            binding.rvKeHoach.setAdapter(mPlanAdapter);
        } catch (ParseException e) {
            Log.d("errorinit", e.getLocalizedMessage());
        }
    }
}