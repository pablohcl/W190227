package com.example.w190227.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.Calendar;

public class MyBaseAdapter extends RecyclerView.Adapter {

    public MyBaseAdapter() {
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

    }

    public String formatDate(String a){
        return a.substring(6)+"/"+a.substring(4, 6)+"/"+a.substring(0, 4);
    }

    public Calendar calcularNovaData(Calendar ultimaData, int numOfDays){
        Calendar ultimoDia = ultimaData;
        ultimoDia.add(Calendar.DAY_OF_MONTH, numOfDays);
        return ultimoDia;
    }
}
