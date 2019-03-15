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
    private ProgressBar progressBar2;
    private ProgressBar progressBar3;
    private ProgressBar progressBar4;
    private VendedorDB venDB;
    private TextView tvVlrAtual;
    private TextView tvMeta;
    private TextView tvVlr2;
    private TextView tvVlrplastico;
    private TextView tvVlrPapel;

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
        progressBar2 = getActivity().findViewById(R.id.progressBar3);
        progressBar3 = getActivity().findViewById(R.id.progressBar4);
        progressBar4 = getActivity().findViewById(R.id.progressBar5);
        venDB = new VendedorDB(getActivity());
        tvMeta = getActivity().findViewById(R.id.tv_meta_home);
        tvVlrAtual = getActivity().findViewById(R.id.tv_vlr_atual_home);
        tvVlr2 = getActivity().findViewById(R.id.tv_vlr_aluminio_home);
        tvVlrplastico = getActivity().findViewById(R.id.tv_vlr_plastico_home);
        tvVlrPapel = getActivity().findViewById(R.id.tv_vlr_papel_home);

        setMeta();
    }

    private void setMeta(){
        Vendedor v = new Vendedor();

        v.setId(1);
        v.setNome("Pablo");
        v.setMeta(10000.50);
        v.setVlrAtual(7500.05);

        tvVlrAtual.setText(String.valueOf(7500));
        tvMeta.setText(String.valueOf(10000));

        progressBar.setMax(10000);
        progressBar.setProgress(7500);

        tvVlr2.setText(String.valueOf(3400));
        progressBar2.setMax(10000);
        progressBar2.setProgress(3400);

        tvVlrplastico.setText(String.valueOf(3000));
        progressBar3.setMax(10000);
        progressBar3.setProgress(3000);

        tvVlrPapel.setText(String.valueOf(1100));
        progressBar4.setMax(10000);
        progressBar4.setProgress(1100);
    }
}
