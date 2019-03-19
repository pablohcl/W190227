package com.example.w190227.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.w190227.R;
import com.example.w190227.objetos.Cliente;
import com.example.w190227.objetos.Visita;
import com.example.w190227.util.db.ClienteDB;
import com.example.w190227.util.db.VisitaDB;

import java.util.Calendar;

public class AtendimentoIniciar extends BaseFragment {

    private TextInputLayout tvMotivo;
    private TextView tvData;
    private TextView tvCliente;
    private CheckBox cbPositivado;
    private Bundle arguments;
    private ClienteDB cliDB;
    private VisitaDB viDB;

    public AtendimentoIniciar() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_atendimento_iniciar, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle("Iniciar atendimento");
        setNavigationViewInvisible();
        setHasOptionsMenu(true);

        tvMotivo = getActivity().findViewById(R.id.tv_motivo_atendimento_iniciar);
        tvData = getActivity().findViewById(R.id.tv_data_frag_atendimento_iniciar);
        tvCliente = getActivity().findViewById(R.id.tv_cliente_frag_atendimento_iniciar);
        cbPositivado = getActivity().findViewById(R.id.checkBox_positivado);
        arguments = getArguments();
        cliDB = new ClienteDB(getActivity());
        viDB = new VisitaDB(getActivity());

        setDataAtual(tvData);
        setSelecionado(arguments.getInt("id"));

        cbPositivado.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                disableMotivo(b);
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.actionbar_fragment_novo_cliente, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_salvar_novo_cliente_fragment:
                salvar();
                return true;

            case R.id.action_cancelar_novo_cliente_fragment:
                cancelar();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setSelecionado(int a){
        Cliente c = cliDB.consultarSelecionado(a);
        tvCliente.setText(c.getRazao());
    }

    private void disableMotivo(boolean b){
        if(b){
            tvMotivo.getEditText().setEnabled(false);
        } else {
            tvMotivo.getEditText().setEnabled(true);
        }
    }

    private void salvar(){
        if(!cbPositivado.isChecked() && tvMotivo.getEditText().getText().toString().isEmpty()){
            AlertDialog.Builder alertPreencherCampos = new AlertDialog.Builder(getActivity());
            alertPreencherCampos.setTitle("Atenção!").setMessage("Preencha o campo MOTIVO.").setNeutralButton("OK", null).show();
        } else {

            Visita v = new Visita();

            v.setId(viDB.getCodigoNovo());
            v.setCliente(arguments.getInt("id"));
            v.setDataDaVisita(unformatDate(tvData.getText().toString()));
            v.setObs(tvMotivo.getEditText().getText().toString());
            v.setPositivado(filtroBoolean(cbPositivado.isChecked()));

            viDB.inserir(v);

            Cliente c = cliDB.consultarSelecionado(arguments.getInt("id"));
            c.setUltimaData(unformatDate(tvData.getText().toString()));
            Calendar ultimaData = Calendar.getInstance();
            ultimaData.clear();
            ultimaData.set(Integer.valueOf(tvData.getText().toString().substring(6)), (Integer.valueOf(filtroDesfazerDoisDigitos(tvData.getText().toString().substring(3, 5))) - 1), Integer.valueOf(filtroDesfazerDoisDigitos(tvData.getText().toString().substring(0, 2))));
            Calendar proximaData = calcularNovaData(ultimaData, Integer.valueOf(c.getFrequencia()));
            c.setProximaData(String.valueOf(proximaData.get(Calendar.YEAR)) + "" + filtroDoisDigitos(String.valueOf((proximaData.get(Calendar.MONTH) + 1))) + "" + filtroDoisDigitos(String.valueOf(proximaData.get(Calendar.DAY_OF_MONTH))));
            cliDB.atualizarCadastro(c);

            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
            alert.setTitle("Feito!").setMessage("Visita cadastrada.").setNeutralButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    limpar();
                    getActivity().getSupportFragmentManager().popBackStack();
                }
            }).show();
        }
    }

    private void cancelar(){
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("Atenção!").setMessage("Deseja mesmo cancelar? Os dados digitados serão perdidos.").setNegativeButton("Não", null);
        alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                limpar();
                getActivity().getSupportFragmentManager().popBackStack();
            }
        }).show();
    }

    private int filtroBoolean(boolean b){
        if(b){
            return 1;
        } else {
            return 0;
        }
    }

    private void limpar(){
        tvData.setText(null);
        tvCliente.setText(null);
        cbPositivado.setChecked(false);
        tvMotivo.getEditText().setText(null);
    }
}
