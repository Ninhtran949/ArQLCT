package com.example.qlct.acitivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.qlct.R;
import com.example.qlct.databinding.ActivityAddChiTieuBinding;
import com.example.qlct.databinding.ActivityPlanDetailAcitivityBinding;
import com.example.qlct.model.KeHoach;
import com.example.qlct.sqlite.KeHoachSql;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.ktx.Firebase;

import java.text.ParseException;
import java.util.Calendar;

public class PlanDetailAcitivity extends AppCompatActivity {
    ActivityPlanDetailAcitivityBinding binding;
    int makh = 0;
    private KeHoach mPlan;
    private Calendar c;
    KeHoachSql keHoachSql;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlanDetailAcitivityBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        keHoachSql = new KeHoachSql(this, KeHoachSql.TableName, null, 1);
        try {
            makh =  getIntent().getIntExtra("item", 0);
            mPlan = keHoachSql.getKeHoachHT(makh).get(0);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        initView();
    }

    private void initView() {
        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        binding.edtTenKeHoach.setText(mPlan.getTenKeHoach());
        binding.edtHanMuc.setText(String.valueOf(mPlan.getHanMuc()));
        binding.edtGhiChu.setText(mPlan.getGhiChu());
        binding.txtNgayBatDau.setText(mPlan.getThoiGianBatDau().toString());
        binding.txtNgayKetThuc.setText(mPlan.getThoiGianKetThuc().toString());
        if (mPlan.getHoanThanh() == 0) {
            binding.btnHoanThanh.setText("Chưa hoàn thành");
        } else {
            binding.btnHoanThanh.setText("Đã hoàn thành");
        }
        setUneditableItem();
        binding.btnCloseDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEditableItem();
                binding.edtTenKeHoach.setFocusable(true);
                binding.llButton.setVisibility(View.VISIBLE);
            }
        });

        binding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUneditableItem();
                binding.llButton.setVisibility(View.GONE);
            }
        });

        binding.btnHoanThanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPlan.getHoanThanh() == 1) {
                    binding.btnHoanThanh.setText("Chưa hoàn thành");
                    mPlan.setHoanThanh(0);
                    keHoachSql.updateKeHoachID(mPlan.getMaKeHoach(), 0);
                } else {
                    binding.btnHoanThanh.setText("Hoàn thành");
                    mPlan.setHoanThanh(1);
                    keHoachSql.updateKeHoachID(mPlan.getMaKeHoach(), 1);
                    try {
                        Log.d("error", String.valueOf(keHoachSql.getKeHoachHT(mPlan.getMaKeHoach()).get(0).getHoanThanh()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void setEditableItem() {
        binding.edtTenKeHoach.setEnabled(true);
        binding.edtHanMuc.setEnabled(true);
        binding.edtGhiChu.setEnabled(true);

        binding.txtNgayBatDau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c = Calendar.getInstance();
                DatePickerDialog bdDialog = new DatePickerDialog(PlanDetailAcitivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        binding.txtNgayBatDau.setText(String.valueOf(dayOfMonth) + "/" + String.valueOf(month) + "/" + String.valueOf(year));
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                bdDialog.show();
            }
        });
        binding.txtNgayKetThuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c = Calendar.getInstance();
                DatePickerDialog ktDialog = new DatePickerDialog(PlanDetailAcitivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        binding.txtNgayKetThuc.setText(String.valueOf(dayOfMonth) + "/" + String.valueOf(month) + "/" + String.valueOf(year));
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                ktDialog.show();
            }
        });
    }

    private void setUneditableItem() {
        binding.edtTenKeHoach.setEnabled(false);
        binding.edtHanMuc.setEnabled(false);
        binding.edtGhiChu.setEnabled(false);
        binding.txtNgayBatDau.setClickable(false);
        binding.txtNgayKetThuc.setClickable(false);
    }
}