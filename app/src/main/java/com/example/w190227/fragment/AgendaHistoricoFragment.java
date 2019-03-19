package com.example.w190227.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.w190227.R;
import com.example.w190227.adapter.AtendimentoAdapter;
import com.example.w190227.adapter.VisitaAdapter;
import com.example.w190227.objetos.Visita;
import com.example.w190227.util.db.VisitaDB;

import java.util.ArrayList;

public class AgendaHistoricoFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private ArrayList<Visita> alVisitas;
    private VisitaDB viDB;

    public AgendaHistoricoFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_agenda_historico, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle("Histórico");
        setNavigationViewInvisible();

        recyclerView = getActivity().findViewById(R.id.rv_agenda_historico);
        alVisitas = new ArrayList<>();
        viDB = new VisitaDB(getActivity());

        mostrarTodos();
    }

    private void mostrarTodos(){
        alVisitas = viDB.consultar();
        refreshList();
    }

    private void refreshList(){
        AtendimentoAdapter adapter = new AtendimentoAdapter(getActivity(), alVisitas, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
}
