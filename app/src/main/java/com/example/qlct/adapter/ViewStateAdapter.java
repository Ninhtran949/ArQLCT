package com.example.qlct.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewStateAdapter extends FragmentStateAdapter {

    private final List<Fragment> list = new ArrayList<>();
    public void add(Fragment fragment){
        list.add(fragment);
        notifyDataSetChanged();
    }
    public ViewStateAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
        @Override
        public Fragment createFragment(int position) {
        return list.get(position);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
        public void setList(List<Fragment> lst){
            list.clear();
            list.addAll(lst);
            notifyDataSetChanged();
        }

    }