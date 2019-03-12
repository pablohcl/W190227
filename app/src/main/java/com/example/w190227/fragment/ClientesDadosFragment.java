package com.example.w190227.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.w190227.R;

public class ClientesDadosFragment extends BaseFragment {

    private TextView tvRazao;
    private TextView tvFantasia;
    private TextView tvRua;
    private TextView tvNumero;
    private TextView tvBairro;
    private TextView tvCidade;
    private TextView tvDiaUltima;
    private TextView tvMesUltima;
    private TextView tvAnoUltima;
    private TextView tvDiaProxima;
    private TextView tvMesProxima;
    private TextView tvAnoProxima;
    private TextView tvFrequencia;
    private TextInputLayout tvObs;

    private Bundle arguments;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_clientes_dados, container, false);

        tvRazao = v.findViewById(R.id.tv_razao_clientes_dados);
        tvFantasia = v.findViewById(R.id.tv_fantasia_clientes_dados);
        tvRua = v.findViewById(R.id.tv_rua_clientes_dados);
        tvNumero = v.findViewById(R.id.tv_numero_clientes_dados);
        tvBairro = v.findViewById(R.id.tv_bairro_clientes_dados);
        tvCidade = v.findViewById(R.id.tv_cidade_clientes_dados);
        tvDiaUltima = v.findViewById(R.id.tv_dia_ultima_clientes_dados);
        tvMesUltima = v.findViewById(R.id.tv_mes_ultima_clientes_dados);
        tvAnoUltima = v.findViewById(R.id.tv_ano_ultima_clientes_dados);
        tvDiaProxima = v.findViewById(R.id.tv_dia_proxima_clientes_dados);
        tvMesProxima = v.findViewById(R.id.tv_mes_proxima_clientes_dados);
        tvAnoProxima = v.findViewById(R.id.tv_ano_proxima_clientes_dados);
        tvFrequencia = v.findViewById(R.id.tv_frequencia_clientes_dados);
        tvObs = v.findViewById(R.id.tv_obs_clientes_dados);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle("Dados do Cliente");

        setNavigationViewInvisible();
        setHasOptionsMenu(true);
        arguments = getArguments();
        Toast.makeText(getActivity(), "id do cliente: "+arguments.getInt("id"), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.clientes_dados_actionbar, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_edit_cliente:
                // Abrir fragment para editar o cliente
                Toast.makeText(getActivity(), "Clicou em editar", Toast.LENGTH_SHORT).show();
                return true;

                default:
                    return super.onOptionsItemSelected(item);
        }
    }

    private void getClienteSelecionado(int id){
        // IMPLEMENTAR
    }
}
