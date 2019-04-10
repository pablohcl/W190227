package com.example.w190227.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.w190227.R;
import com.example.w190227.activity.MainActivity;
import com.example.w190227.adapter.MetaAdapter;
import com.example.w190227.objetos.Categoria;
import com.example.w190227.objetos.Meta;
import com.example.w190227.objetos.Vendedor;
import com.example.w190227.util.db.CategoriaDB;
import com.example.w190227.util.db.Downloader;
import com.example.w190227.util.db.MetaDB;
import com.example.w190227.util.db.VendedorDB;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class HomeFragment extends BaseFragment {

    private ProgressBar progressBar;
    private TextView tvVlrAtual;
    private TextView tvMeta;
    private RecyclerView rvHome;
    private ArrayList<Categoria> alCategorias;
    private CategoriaDB catDB;
    private VendedorDB venDB;
    private MetaDB meDB;

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
        setHasOptionsMenu(true);

        progressBar = getActivity().findViewById(R.id.progressBar);
        tvMeta = getActivity().findViewById(R.id.tv_meta_home);
        tvVlrAtual = getActivity().findViewById(R.id.tv_vlr_atual_home);
        rvHome = getActivity().findViewById(R.id.rv_home_fragment);
        alCategorias = new ArrayList<>();
        catDB = new CategoriaDB(getActivity());
        venDB = new VendedorDB(getActivity());
        meDB = new MetaDB(getActivity());

        solicitarPermissao();
    }

    private void refreshList(){
        MetaAdapter adapter = new MetaAdapter(getActivity(), alCategorias, this);
        rvHome.setAdapter(adapter);
        rvHome.setHasFixedSize(true);
        rvHome.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public void mostrarTodos(){
        alCategorias = catDB.consultar();
        Vendedor v = venDB.consultarSelecionado(MainActivity.VENDEDOR);
        Double meta = v.getMeta();
        tvMeta.setText(new DecimalFormat("#,##0.00").format(meta));
        progressBar.setMax(meta.intValue());
        Double vlrTotalAtual = meDB.consultarVlrTotalAtual(MainActivity.VENDEDOR);
        tvVlrAtual.setText(new DecimalFormat("#,##0.00").format(vlrTotalAtual));
        progressBar.setProgress(vlrTotalAtual.intValue());
        refreshList();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.actionbar_home_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_atualizar:
                mostrarTodos();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}


