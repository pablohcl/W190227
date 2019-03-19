package com.example.w190227.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.w190227.R;
import com.example.w190227.fragment.AtendimentoIniciar;
import com.example.w190227.fragment.BaseFragment;
import com.example.w190227.fragment.ClientesDadosFragment;
import com.example.w190227.objetos.Cliente;

import java.util.ArrayList;
import java.util.Calendar;

public class VisitaAdapter extends MyBaseAdapter {

    private Context context;
    private ArrayList<Cliente> alVisitas;
    private Fragment f;

    public VisitaAdapter(Context context, ArrayList<Cliente> alVisitas, Fragment f) {
        this.context = context;
        this.alVisitas = alVisitas;
        this.f = f;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.linha_visita, viewGroup, false);
        VisitaViewHolder visitaViewHolder = new VisitaViewHolder(v);
        return visitaViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        VisitaViewHolder customViewHolder = (VisitaViewHolder) viewHolder;
        final Cliente cliente = alVisitas.get(i);

        customViewHolder.tvProximaData.setText(formatDate(cliente.getProximaData()));
        customViewHolder.tvCliente.setText(String.valueOf(cliente.getRazao()));
        customViewHolder.tvRua.setText(String.valueOf(cliente.getRua()));
        customViewHolder.tvNumero.setText(String.valueOf(cliente.getNumero()));
        customViewHolder.tvBairro.setText(String.valueOf(cliente.getBairro()));
        customViewHolder.tvCidade.setText(String.valueOf(cliente.getCidade()));
        customViewHolder.layout.setBackgroundColor(context.getResources().getColor(colorir(cliente.getProximaData())));
        customViewHolder.itemView.setTag(cliente.getId());
        customViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setItems(R.array.alert_options_linha_visita, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch(i){
                            case 0:
                                AtendimentoIniciar atendimentoIniciar = new AtendimentoIniciar();
                                FragmentTransaction ft = f.getFragmentManager().beginTransaction();
                                Bundle arguments = new Bundle();
                                arguments.putInt("id", cliente.getId());
                                atendimentoIniciar.setArguments(arguments);
                                ft.replace(R.id.frame_content, atendimentoIniciar, null);
                                ft.addToBackStack(null);
                                ft.commit();
                                break;

                            case 1:
                                ClientesDadosFragment clientesDadosFragment = new ClientesDadosFragment();
                                FragmentTransaction ft2 = f.getFragmentManager().beginTransaction();
                                Bundle arguments2 = new Bundle();
                                arguments2.putInt("id", cliente.getId());
                                clientesDadosFragment.setArguments(arguments2);
                                ft2.replace(R.id.frame_content, clientesDadosFragment, null);
                                ft2.addToBackStack(null);
                                ft2.commit();
                                break;

                            default:

                        }
                    }
                }).show();
            }
        });
    }

    public int colorir(String a){
        Calendar calendarioHoje = Calendar.getInstance();
        int dia = calendarioHoje.get(Calendar.DAY_OF_MONTH);
        int mes = (calendarioHoje.get(Calendar.MONTH)+1);
        int ano = calendarioHoje.get(Calendar.YEAR);
        String hoje = ano+""+filtroDoisDigitos(String.valueOf(mes))+""+filtroDoisDigitos(String.valueOf(dia));

        if(Integer.valueOf(hoje) > Integer.valueOf(a)){
            return R.color.md_red_200;
        } else if(hoje.equals(a)){
            return R.color.md_green_200;
        } else {
            return R.color.md_blue_200;
        }
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

    /*public String formatDate(String a){
        return a.substring(6)+"/"+a.substring(4, 6)+"/"+a.substring(0, 4);
    }*/

    @Override
    public int getItemCount() {
        return alVisitas.size();
    }

    public class VisitaViewHolder extends RecyclerView.ViewHolder{
        final TextView tvProximaData;
        final TextView tvCliente;
        final TextView tvRua;
        final TextView tvNumero;
        final TextView tvBairro;
        final TextView tvCidade;
        final ConstraintLayout layout;

        public VisitaViewHolder(View v){
            super(v);

            tvProximaData = (TextView)v.findViewById(R.id.tv_proxima_data_linha_visita);
            tvCliente = (TextView)v.findViewById(R.id.tv_cliente_linha_visita);
            tvRua = (TextView)v.findViewById(R.id.tv_rua_linha_visita);
            tvNumero = (TextView)v.findViewById(R.id.tv_numero_linha_visita);
            tvBairro = (TextView)v.findViewById(R.id.tv_bairro_linha_visita);
            tvCidade = (TextView)v.findViewById(R.id.tv_cidade_linha_visita);
            layout = (ConstraintLayout) v.findViewById(R.id.bottom_layout_linha_visita);
        }
    }
}
