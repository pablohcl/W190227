package com.example.w190227.fragment;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.w190227.R;
import com.example.w190227.objetos.Cliente;
import com.example.w190227.objetos.Visita;
import com.example.w190227.util.db.ClienteDB;
import com.example.w190227.util.db.VisitaDB;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.Calendar;

public class AtendimentoIniciar extends BaseFragment {

    private static final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 0;
    private TextInputLayout tvMotivo;
    private TextView tvData;
    private TextView tvCliente;
    private CheckBox cbPositivado;
    private Bundle arguments;
    private ClienteDB cliDB;
    private VisitaDB viDB;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private boolean permissaoFineLocation;

    private double latitude, longitude;

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
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());

        setDataAtual(tvData);
        setSelecionado(arguments.getInt("id"));
        solicitarPermissao();

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

            if(permissaoFineLocation){
                try {
                    fusedLocationProviderClient.getLastLocation().addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            } else {
                                latitude = 0;
                                longitude = 0;
                                Log.d("log", "fusedLocationProvider RETURN FAILED");
                            }
                        }
                    });
                } catch(SecurityException e){
                    Log.d("log", "ERRO, A PERMISSAO NÃO FOI CONCEDIDA"+e);
                }
            } else {
                latitude = 0;
                longitude = 0;
                Log.d("log", "PERMISSÃO NÃO CONCEDIDA");
            }


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

    private void solicitarPermissao(){
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_FINE_LOCATION);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case MY_PERMISSIONS_REQUEST_FINE_LOCATION:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    permissaoFineLocation = true;
                    break;
                } else {
                    permissaoFineLocation = false;
                    break;
                }
        }
    }
}
