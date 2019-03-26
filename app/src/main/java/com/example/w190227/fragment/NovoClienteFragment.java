package com.example.w190227.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
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
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.Calendar;

public class NovoClienteFragment extends BaseFragment {

    private TextInputLayout et_razao;
    private TextInputLayout et_fantasia;
    private TextInputLayout et_rua;
    private TextInputLayout et_numero;
    private TextInputLayout et_bairro;
    private TextInputLayout et_cidade;
    private TextInputLayout et_frequencia;
    private TextInputLayout et_obs;
    private TextView tvUltimaData;
    private Button btnMudar;
    private TextView tvLatitude, tvLongitude;
    private SupportMapFragment mapFragment;
    private Button btnLocalizar;

    public NovoClienteFragment(){

    }

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
        tvUltimaData = v.findViewById(R.id.tv_ultima_data_cliente_novo);
        btnMudar = v.findViewById(R.id.btn_mudar_data);
        tvLatitude = v.findViewById(R.id.tv_latitude_cliente_novo);
        tvLongitude = v.findViewById(R.id.tv_longitude_cliente_novo);
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_frag);
        btnLocalizar = v.findViewById(R.id.btn_localizar);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle("Novo Cliente");
        solicitarPermissao();

        setNavigationViewInvisible();
        setHasOptionsMenu(true);
        setDataAtual(tvUltimaData);
        getUltimaPosicao(tvLatitude, tvLongitude);

        btnMudar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        btnLocalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setMap(mapFragment, Double.valueOf(tvLatitude.getText().toString()), Double.valueOf(tvLongitude.getText().toString()));
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
            case R.id.action_cancelar_novo_cliente_fragment:
                cancelarCadastramento();
                return true;

            case R.id.action_salvar_novo_cliente_fragment:
                salvarNoBanco();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void salvarNoBanco(){
        if(et_razao.getEditText().getText().toString().isEmpty() || et_fantasia.getEditText().getText().toString().isEmpty() || et_cidade.getEditText().getText().toString().isEmpty() || et_bairro.getEditText().getText().toString().isEmpty() || et_rua.getEditText().getText().toString().isEmpty() || et_numero.getEditText().getText().toString().isEmpty() || et_frequencia.getEditText().getText().toString().isEmpty() || tvUltimaData.getText().toString().isEmpty()){
            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
            alert.setMessage("Preencha todos os campos com *").setTitle("Atenção!").setNeutralButton("OK", null).show();
        } else {

            Calendar ultimaData = Calendar.getInstance();
            ultimaData.clear();
            ultimaData.set(Integer.valueOf(tvUltimaData.getText().toString().substring(6)), (Integer.valueOf(filtroDesfazerDoisDigitos(tvUltimaData.getText().toString().substring(3, 5)))-1), Integer.valueOf(filtroDesfazerDoisDigitos(tvUltimaData.getText().toString().substring(0, 2))));
            Calendar proximaData = calcularNovaData(ultimaData, Integer.valueOf(et_frequencia.getEditText().getText().toString()));

            final Cliente c = new Cliente();
            ClienteDB cliDB = new ClienteDB(getActivity());
            c.setId(cliDB.getCodigoNovo());
            c.setRazao(et_razao.getEditText().getText().toString());
            c.setFantasia(et_fantasia.getEditText().getText().toString());
            c.setCidade(et_cidade.getEditText().getText().toString());
            c.setBairro(et_bairro.getEditText().getText().toString());
            c.setRua(et_rua.getEditText().getText().toString());
            c.setNumero(et_numero.getEditText().getText().toString());
            //c.setUltimaData(tvUltimaData.getText().toString().substring(6)+""+tvUltimaData.getText().toString().substring(3, 5)+""+tvUltimaData.getText().toString().substring(0, 2));
            c.setUltimaData(unformatDate(tvUltimaData.getText().toString()));
            c.setProximaData(String.valueOf(proximaData.get(Calendar.YEAR))+""+filtroDoisDigitos(String.valueOf((proximaData.get(Calendar.MONTH)+1)))+""+filtroDoisDigitos(String.valueOf(proximaData.get(Calendar.DAY_OF_MONTH))));
            c.setFrequencia(et_frequencia.getEditText().getText().toString());
            c.setObs(et_obs.getEditText().getText().toString());
            c.setLatitude(Double.valueOf(tvLatitude.getText().toString()));
            c.setLongitude(Double.valueOf(tvLongitude.getText().toString()));

            cliDB.inserir(c);

            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
            alert.setMessage("Cliente cadastrado com sucesso!").setTitle("Feito").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    limpar();
                    getActivity().getSupportFragmentManager().popBackStack();
                }
            }).show();
        }
    }

    public void cancelarCadastramento(){
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setMessage("Tem certeza que deseja cancelar? Todos os dados digitados serão perdidos.").setTitle("Atenção!").setNegativeButton("Não", null);
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
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            TextView tvUltimaData = getActivity().findViewById(R.id.tv_ultima_data_cliente_novo);

            tvUltimaData.setText(filtroDoisDigitos(String.valueOf(day))+"/"+filtroDoisDigitos(String.valueOf(month+1))+"/"+String.valueOf(year));
        }

        public String filtroDoisDigitos(String a){
            String result;

            if(a.length() == 1){
                result = "0"+a;
            } else {
                return a;
            }

            return result;
        }
    }
}
