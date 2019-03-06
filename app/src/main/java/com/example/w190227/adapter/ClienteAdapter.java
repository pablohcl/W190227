package com.example.w190227.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.w190227.R;
import com.example.w190227.objetos.Cliente;

import java.util.ArrayList;

public class ClienteAdapter extends RecyclerView.Adapter {

    private Context context;
    private ArrayList<Cliente> alClientes;

    public ClienteAdapter(Context context1, ArrayList<Cliente> clientes){
        this.context = context1;
        this.alClientes = clientes;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.linha_cliente, viewGroup, false);
        CustomViewHolder customViewHolder = new CustomViewHolder(view);
        return customViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        CustomViewHolder customViewHolder = (CustomViewHolder) viewHolder;
        final Cliente cliente = alClientes.get(i);

        customViewHolder.tvRazao.setText(String.valueOf(cliente.getRazao()));
        customViewHolder.tvFantasia.setText(String.valueOf(cliente.getFantasia()));
        customViewHolder.tvCidade.setText(String.valueOf(cliente.getCidade()));
        customViewHolder.tvBairro.setText(String.valueOf(cliente.getBairro()));
        customViewHolder.tvRua.setText(String.valueOf(cliente.getRua()));
        customViewHolder.tvNumero.setText(String.valueOf(cliente.getNumero()));
        customViewHolder.itemView.setTag(cliente.getId());
        customViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Clicou no "+cliente.getId(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return alClientes.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder{
        final TextView tvRazao;
        final TextView tvFantasia;
        final TextView tvCidade;
        final TextView tvBairro;
        final TextView tvRua;
        final TextView tvNumero;

        public CustomViewHolder(View v){
            super(v);

            tvRazao = (TextView)v.findViewById(R.id.tv_razao_linha_cliente);
            tvFantasia = (TextView) v.findViewById(R.id.tv_fantasia_linha_cliente);
            tvCidade = (TextView) v.findViewById(R.id.tv_cidade_linha_cliente);
            tvBairro = (TextView) v.findViewById(R.id.tv_bairro_linha_cliente);
            tvRua = (TextView) v.findViewById(R.id.tv_rua_linha_cliente);
            tvNumero = (TextView) v.findViewById(R.id.tv_numero_linha_cliente);
        }
    }
}
