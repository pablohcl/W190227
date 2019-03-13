package com.example.w190227.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.w190227.R;
import com.example.w190227.objetos.Vendedor;
import com.example.w190227.util.db.VendedorDB;

public class HomeFragment extends BaseFragment {

    private ProgressBar progressBar;
    private VendedorDB venDB;
    private TextView tvVlrAtual;
    private TextView tvMeta;

    public HomeFragment(){
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_home, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle("Início");

        progressBar = getActivity().findViewById(R.id.progressBar);
        venDB = new VendedorDB(getActivity());
        tvMeta = getActivity().findViewById(R.id.tv_meta_home);
        tvVlrAtual = getActivity().findViewById(R.id.tv_vlr_atual_home);

        setMeta();
    }

    private void setMeta(){
        Vendedor v = new Vendedor();

        v.setId(1);
        v.setNome("Pablo");
        v.setMeta(10000.50);
        v.setVlrAtual(7500.05);

        tvVlrAtual.setText(String.valueOf(7500.05));
        tvMeta.setText(String.valueOf(10000));

        progressBar.setMax(10000);
        progressBar.setProgress(7500);
    }
}
