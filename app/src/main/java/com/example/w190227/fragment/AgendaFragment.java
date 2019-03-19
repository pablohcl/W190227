package com.example.w190227.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.w190227.R;
import com.example.w190227.adapter.VisitaAdapter;
import com.example.w190227.objetos.Cliente;
import com.example.w190227.util.db.ClienteDB;
import com.example.w190227.util.db.VisitaDB;

import java.util.ArrayList;
import java.util.Calendar;

public class AgendaFragment extends BaseFragment {

    private Calendar calendar;
    private TextView tvDataAtual;
    private RecyclerView rvAgenda;
    private ArrayList<Cliente> alClientes;
    private ClienteDB cliDB;

    public AgendaFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_agenda, container, false);

        tvDataAtual = view.findViewById(R.id.tv_data_atual_agenda);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle("Agenda");
        setHasOptionsMenu(true);

        rvAgenda = getActivity().findViewById(R.id.rv_agenda);
        alClientes = new ArrayList<>();
        cliDB = new ClienteDB(getActivity());

        tvDataAtual.setText(setDataAtual());
        mostrarTodos();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.actionbar_agenda, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_historico:
                AgendaHistoricoFragment agendaHistoricoFragment = new AgendaHistoricoFragment();
                replaceFragmentWithBackStack(agendaHistoricoFragment);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        tvDataAtual.setText(setDataAtual());
        setNavigationViewVisible();
        clearBackStack();
    }

    private void mostrarTodos(){
        alClientes = cliDB.consultarOrderByProximaDataDesc();
        refreshList();
    }

    private void refreshList(){
        VisitaAdapter adapter = new VisitaAdapter(getActivity(), alClientes, this);
        rvAgenda.setAdapter(adapter);
        rvAgenda.setHasFixedSize(true);
        rvAgenda.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private String setDataAtual(){
        calendar = Calendar.getInstance();
        String mes;
        switch(calendar.get(Calendar.MONTH)){
            case 0:
                mes = "Janeiro";
                break;

            case 1:
                mes = "Fevereiro";
                break;

            case 2:
                mes = "Março";
                break;

            case 3:
                mes = "Abril";
                break;

            case 4:
                mes = "Maio";
                break;

            case 5:
                mes = "Junho";
                break;

            case 6:
                mes = "Julho";
                break;

            case 7:
                mes = "Agosto";
                break;

            case 8:
                mes = "Setembro";
                break;

            case 9:
                mes = "Outubro";
                break;

            case 10:
                mes = "Novembro";
                break;

            case 11:
                mes = "Dezembro";
                break;

            default:
                return "ERRO";
        }

        return calendar.get(Calendar.DAY_OF_MONTH)+" de "+mes+" de "+calendar.get(Calendar.YEAR);
    }
}
