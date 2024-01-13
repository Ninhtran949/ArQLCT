package com.example.qlct.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qlct.R;
import com.example.qlct.model.ChiTieu;

import java.util.ArrayList;

public class ChiTieuAdapter extends RecyclerView.Adapter<ChiTieuAdapter.ChiTieuViewHolder>{
    private Context context;
    private ArrayList<ChiTieu> mChiTieus;
    private LayoutInflater layoutInflater;

    public ChiTieuAdapter(Context context, ArrayList<ChiTieu> mChiTieus) {
        this.context = context;
        this.mChiTieus = mChiTieus;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ChiTieuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.chitieu_item, parent, false);
        return new ChiTieuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChiTieuViewHolder holder, int position) {
        ChiTieu chitieu = mChiTieus.get(position);
        if(chitieu.getLoaiCT().contentEquals("Ăn uống")) {
            holder.imgItem.setImageResource(R.drawable.ic_food);
        }else if(chitieu.getLoaiCT().contentEquals("Du lịch")) {
            holder.imgItem.setImageResource(R.drawable.ic_travel);
        }
        else if(chitieu.getLoaiCT().contentEquals("Tiền nhà")){
            holder.imgItem.setImageResource(R.drawable.ic_house);
        }
        else {
            holder.imgItem.setImageResource(R.drawable.ic_bike);
        }
        holder.txtCTName.setText(String.valueOf(chitieu.getLoaiCT()));
        holder.txtTienCT.setText(String.valueOf(chitieu.getSoTien()));
    }

    @Override
    public int getItemCount() {
        if(mChiTieus.size() != 0) return mChiTieus.size();
        return 0;
    }

    public class ChiTieuViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgItem;
        private TextView txtCTName, txtTienCT;
        public ChiTieuViewHolder(@NonNull View itemView) {
            super(itemView);
            imgItem = itemView.findViewById(R.id.imgLoaiCT);
            txtCTName = itemView.findViewById(R.id.txtChiTieuName);
            txtTienCT = itemView.findViewById(R.id.txtChiTieuMoney);
        }
    }
}
