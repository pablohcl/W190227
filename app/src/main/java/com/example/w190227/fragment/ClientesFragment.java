package com.example.w190227.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
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
import android.widget.Toast;

import com.example.w190227.R;
import com.example.w190227.adapter.ClienteAdapter;
import com.example.w190227.objetos.Cliente;
import com.example.w190227.util.db.ClienteDB;

import java.util.ArrayList;

public class ClientesFragment extends BaseFragment {

    private RecyclerView rvClientes;
    private ArrayList<Cliente> alClientes;
    private ClienteDB cliDB;
    private TextView tvTotalClientes;

    public ClientesFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_clientes, container, false);

        rvClientes = view.findViewById(R.id.rv_clientes);
        ArrayList<Cliente> alClientes = new ArrayList<>();
        cliDB = new ClienteDB(getActivity());
        tvTotalClientes = view.findViewById(R.id.tv_total_clientes_vlr);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle("Clientes");

        setHasOptionsMenu(true);
        mostrarTodos();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.clientes_menu_actionbar, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menu_clientes_adicionar:
                NovoClienteFragment novoClienteFragment = new NovoClienteFragment();
                replaceFragmentWithBackStack(novoClienteFragment);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        setNavigationViewVisible();
    }

    private void refreshList(){
        ClienteAdapter adapter = new ClienteAdapter(getActivity(), alClientes);
        rvClientes.setAdapter(adapter);
        rvClientes.setHasFixedSize(true);
        rvClientes.setLayoutManager(new LinearLayoutManager(getActivity()));
        tvTotalClientes.setText(String.valueOf(alClientes.size()));
    }

    private void mostrarTodos(){
        alClientes = cliDB.consultar();
        refreshList();
    }
}
