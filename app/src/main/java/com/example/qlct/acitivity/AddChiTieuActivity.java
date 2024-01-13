package com.example.qlct.acitivity;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.qlct.R;
import com.example.qlct.databinding.ActivityAddChiTieuBinding;
import com.example.qlct.model.ChiTieu;
import com.example.qlct.model.ThuNhap;
import com.example.qlct.sqlite.ChiTieuSql;
import com.example.qlct.sqlite.ThuNhapSql;
import com.example.qlct.sqlite.ViTienSql;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class AddChiTieuActivity extends AppCompatActivity {
    ActivityAddChiTieuBinding binding;
    ChiTieuSql chiTieuSql;
    ThuNhapSql thuNhapSql;
    ViTienSql viTienSql;
    FirebaseAuth firebaseAuth;
    Calendar c;
    Date date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddChiTieuBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        initData();
        initView();
    }
    private void initView(){
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.tvChonNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c = Calendar.getInstance();
                DatePickerDialog bdDialog = new DatePickerDialog(AddChiTieuActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        date = new Date(year, month + 1, dayOfMonth); // Cộng thêm 1 vào tháng
                        binding.tvChonNgay.setText(String.valueOf(dayOfMonth) + "/" + String.valueOf(month + 1) + "/" + String.valueOf(year)); // Cộng thêm 1 vào tháng
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                bdDialog.show();
            }

        });
    }
    private void initData(){
        ArrayAdapter<CharSequence> cdAdapter = ArrayAdapter.createFromResource(this, R.array.listCheDo, android.R.layout.simple_spinner_dropdown_item);
        binding.spnCheDo.setAdapter(cdAdapter);
        ArrayAdapter<CharSequence> loaiCTAdapter = ArrayAdapter.createFromResource(this, R.array.listLoaiCT, android.R.layout.simple_spinner_dropdown_item);
        binding.spnLoaiChiTieu.setAdapter(loaiCTAdapter);
        chiTieuSql = new ChiTieuSql(this, ChiTieuSql.TableName, null, 1);
        thuNhapSql = new ThuNhapSql(this, ThuNhapSql.TableName, null, 1);
        viTienSql = new ViTienSql(this, ViTienSql.TableName, null, 1);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        binding.btnAddThuChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Validate()){
                    double tongTien = 0;
                    double soTien = Double.parseDouble(binding.edtHanMuc.getText().toString());
                    String ghiChu = binding.edtGhiChu.getText().toString();
                    if(binding.spnCheDo.getSelectedItem().toString().contentEquals( "Chi tiêu")){
                        String loaiCT = binding.spnLoaiChiTieu.getSelectedItem().toString();
                        chiTieuSql.addChiTieu(new ChiTieu(1, soTien,user.getEmail(),loaiCT, date, ghiChu));
                        try {
                            tongTien =  viTienSql.getViTien(user.getEmail()).get(0).getSoTien();
                            tongTien -= soTien;
                            viTienSql.updateTienTangGiam(tongTien, user.getEmail());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }



                    } else {
                        thuNhapSql.addThuNhap(new ThuNhap(1, soTien,user.getEmail(), date, ghiChu));
                        try {
                            tongTien =  viTienSql.getViTien(user.getEmail()).get(0).getSoTien();
                            tongTien += soTien;
                            viTienSql.updateTienTangGiam(tongTien, user.getEmail());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }
                    finish();
                } else{
                    Toast.makeText(AddChiTieuActivity.this, "Vui lòng đầy đủ thông tin.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private boolean Validate(){
        if(TextUtils.isEmpty(binding.edtHanMuc.getText())){
            return false;
        }
        if(TextUtils.isEmpty(binding.edtGhiChu.getText())){
            return false;
        }
        if(TextUtils.isEmpty(binding.tvChonNgay.getText())){
            return false;
        }
        return true;
    }
}