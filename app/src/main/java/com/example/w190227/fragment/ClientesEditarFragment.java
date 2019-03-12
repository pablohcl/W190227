package com.example.w190227.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.w190227.R;
import com.example.w190227.objetos.Cliente;
import com.example.w190227.util.db.ClienteDB;

import java.util.Calendar;

public class ClientesEditarFragment extends BaseFragment {

    private TextInputLayout et_razao;
    private TextInputLayout et_fantasia;
    private TextInputLayout et_rua;
    private TextInputLayout et_numero;
    private TextInputLayout et_bairro;
    private TextInputLayout et_cidade;
    private TextInputLayout et_frequencia;
    private TextInputLayout et_obs;
    private TextView tvDia;
    private TextView tvMes;
    private TextView tvAno;
    private Button btnMudar;
    private ClienteDB cliDB;
    private Bundle arguments;

    public ClientesEditarFragment(){};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_novo_cliente, container, false);

        et_razao = (TextInputLayout) v.findViewById(R.id.et_razao);
        et_fantasia = (TextInputLayout) v.findViewById(R.id.et_fantasia);
        et_rua = (TextInputLayout) v.findViewById(R.id.et_rua);
        et_numero = (TextInputLayout) v.findViewById(R.id.et_numero);
        et_bairro = (TextInputLayout) v.findViewById(R.id.et_bairro);
        et_cidade = (TextInputLayout) v.findViewById(R.id.et_cidade);
        et_frequencia = (TextInputLayout) v.findViewById(R.id.et_frequencia);
        et_obs = (TextInputLayout) v.findViewById(R.id.et_obs);
        tvDia = v.findViewById(R.id.tv_dia_visita);
        tvMes = v.findViewById(R.id.tv_mes_visita);
        tvAno = v.findViewById(R.id.tv_ano_visita);
        btnMudar = v.findViewById(R.id.btn_mudar_data);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        cliDB = new ClienteDB(getActivity());

        getActivity().setTitle("Editar dados");
        setNavigationViewInvisible();
        setHasOptionsMenu(true);
        btnMudar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });
        arguments = getArguments();
        getClienteSelecionado(arguments.getInt("id"));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.actionbar_fragment_novo_cliente, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_cancelar_novo_cliente_fragment:
                cancelarCadastramento();
                return true;

            case R.id.action_salvar_novo_cliente_fragment:
                atualizarCadastro();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void cancelarCadastramento(){
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setMessage("Tem certeza que deseja cancelar? Todos as alterações digitadas serão perdidas.").setTitle("Atenção!").setNegativeButton("Não", null);
        alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                limpar();
                getActivity().getSupportFragmentManager().popBackStack();
            }
        }).show();
    }

    private void limpar(){
        et_razao.getEditText().setText(null);
        et_fantasia.getEditText().setText(null);
        et_cidade.getEditText().setText(null);
        et_bairro.getEditText().setText(null);
        et_rua.getEditText().setText(null);
        et_numero.getEditText().setText(null);
        et_frequencia.getEditText().setText(null);
        et_obs.getEditText().setText(null);
    }

    public void showDatePickerDialog(){
        DialogFragment newFragment = new NovoClienteFragment.DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }

    private void getClienteSelecionado(int id){
        int idCliente = id;
        Cliente c = cliDB.consultarSelecionado(id);

        et_razao.getEditText().setText(c.getRazao());
        et_fantasia.getEditText().setText(c.getFantasia());
        et_cidade.getEditText().setText(c.getCidade());
        et_bairro.getEditText().setText(c.getBairro());
        et_rua.getEditText().setText(c.getRua());
        et_numero.getEditText().setText(c.getNumero());
        tvDia.setText(c.getUltimaDataDia());
        tvMes.setText(c.getUltimaDataMes());
        tvAno.setText(c.getUltimaDataAno());
        et_frequencia.getEditText().setText(c.getFrequencia());
        et_obs.getEditText().setText(c.getObs());
    }

    public void atualizarCadastro(){
        if(et_razao.getEditText().getText().toString().isEmpty() || et_fantasia.getEditText().getText().toString().isEmpty() || et_cidade.getEditText().getText().toString().isEmpty() || et_bairro.getEditText().getText().toString().isEmpty() || et_rua.getEditText().getText().toString().isEmpty() || et_numero.getEditText().getText().toString().isEmpty() || et_frequencia.getEditText().getText().toString().isEmpty() || tvDia.getText().toString().isEmpty()){
            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
            alert.setMessage("Preencha todos os campos com *").setTitle("Atenção!").setNeutralButton("OK", null).show();
        } else {
            Calendar ultimaData = Calendar.getInstance();
            ultimaData.clear();
            ultimaData.set(Integer.valueOf(tvAno.getText().toString()), (Integer.valueOf(tvMes.getText().toString())-1), Integer.valueOf(tvDia.getText().toString()));
            Calendar proximaData = calcularNovaData(ultimaData, Integer.valueOf(et_frequencia.getEditText().getText().toString()));

            Cliente c = new Cliente();
            ClienteDB cliDB = new ClienteDB(getActivity());
            c.setId(arguments.getInt("id"));
            c.setRazao(et_razao.getEditText().getText().toString());
            c.setFantasia(et_fantasia.getEditText().getText().toString());
            c.setCidade(et_cidade.getEditText().getText().toString());
            c.setBairro(et_bairro.getEditText().getText().toString());
            c.setRua(et_rua.getEditText().getText().toString());
            c.setNumero(et_numero.getEditText().getText().toString());
            c.setUltimaDataDia(tvDia.getText().toString());
            c.setUltimaDataMes(tvMes.getText().toString());
            c.setUltimaDataAno(tvAno.getText().toString());
            c.setProximaDataDia(String.valueOf(proximaData.get(Calendar.DAY_OF_MONTH)));
            c.setProximaDataMes(String.valueOf((proximaData.get(Calendar.MONTH)+1)));
            c.setProximaDataAno(String.valueOf(proximaData.get(Calendar.YEAR)));
            c.setFrequencia(et_frequencia.getEditText().getText().toString());
            c.setObs(et_obs.getEditText().getText().toString());

            cliDB.atualizarCadastro(c);

            limpar();

            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
            alert.setMessage("Cliente alterado com sucesso!").setTitle("Feito").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    limpar();
                    getActivity().getSupportFragmentManager().popBackStack();
                }
            }).show();
        }
    }
}
