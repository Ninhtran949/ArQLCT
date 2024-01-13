package com.example.qlct.acitivity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qlct.R;
import com.example.qlct.databinding.ActivityAddChiTieuBinding;
import com.example.qlct.databinding.ActivityAddPlanBinding;
import com.example.qlct.model.KeHoach;
import com.example.qlct.sqlite.KeHoachSql;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;
import java.util.Date;

public class AddPlanActivity extends AppCompatActivity {
    ActivityAddPlanBinding binding;
    private Calendar c;
    Date dateBatDau;
    Date dateKetThuc;
    KeHoachSql keHoachSql;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddPlanBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        initView();
    }
    private void initView() {
        keHoachSql = new KeHoachSql(this, KeHoachSql.TableName, null, 1);
        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        binding.btnCloseAddPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.txtChonNgayBatDau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c = Calendar.getInstance();
                DatePickerDialog bdDialog = new DatePickerDialog(AddPlanActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        dateBatDau = new Date(year, month + 1, dayOfMonth); // Cộng thêm 1 vào tháng
                        binding.txtChonNgayBatDau.setText(String.valueOf(dayOfMonth) + "/" + String.valueOf(month + 1) + "/" + String.valueOf(year)); // Cộng thêm 1 vào tháng
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                bdDialog.show();
            }
        });


        binding.txtChonNgayKetThuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c = Calendar.getInstance();
                DatePickerDialog ktDialog = new DatePickerDialog(AddPlanActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        dateKetThuc = new Date(year, month + 1, dayOfMonth); // Cộng thêm 1 vào tháng
                        binding.txtChonNgayKetThuc.setText(String.valueOf(dayOfMonth) + "/" + String.valueOf(month + 1) + "/" + String.valueOf(year)); // Cộng thêm 1 vào tháng
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                ktDialog.show();
            }
        });

        binding.btnAddPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValidated()) {
                    String tenKH = binding.edtTenKeHoach.getText().toString().trim();
                    Double hanMuc = Double.parseDouble(binding.edtHanMuc.getText().toString().trim());
                    String GhiChu = binding.edtGhiChu.getText().toString().trim();
                    keHoachSql.addKeHoach(new KeHoach(0, tenKH, dateBatDau, dateKetThuc, hanMuc, GhiChu, user.getEmail(), 0));
                    Toast.makeText(AddPlanActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(AddPlanActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private boolean isValidated() {
        if(TextUtils.isEmpty(binding.edtTenKeHoach.getText().toString().trim())) {
            Toast.makeText(this, "Nhập tên kế hoạch", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(TextUtils.isEmpty(binding.edtGhiChu.getText().toString().trim())) {
            Toast.makeText(this, "Nhập ghi chú", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(TextUtils.isEmpty(binding.edtHanMuc.getText().toString().trim())) {
            Toast.makeText(this, "Nhập hạn mức", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(TextUtils.isEmpty(binding.txtChonNgayBatDau.getText().toString().trim())) {
            Toast.makeText(this, "Chọn ngày", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(TextUtils.isEmpty(binding.txtChonNgayKetThuc.getText().toString().trim())) {
            Toast.makeText(this, "Chọn ngày", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}