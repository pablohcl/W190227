package com.example.w190227.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.w190227.R;
import com.example.w190227.fragment.BaseFragment;
import com.example.w190227.objetos.Cliente;

import java.util.ArrayList;

public class VisitaAdapter extends RecyclerView.Adapter {

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
        customViewHolder.itemView.setTag(cliente.getId());
        customViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*ClientesDadosFragment clientesDadosFragment = new ClientesDadosFragment();
                FragmentTransaction ft = frag.getFragmentManager().beginTransaction();
                Bundle arguments = new Bundle();
                arguments.putInt("id", cliente.getId());
                clientesDadosFragment.setArguments(arguments);
                ft.replace(R.id.frame_content, clientesDadosFragment, null);
                ft.addToBackStack(null);
                ft.commit();*/
                Toast.makeText(context, "Clicou em "+cliente.getId(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public String formatDate(String a){
        return a.substring(6)+"/"+a.substring(4, 6)+"/"+a.substring(0, 4);
    }

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

        public VisitaViewHolder(View v){
            super(v);

            tvProximaData = (TextView)v.findViewById(R.id.tv_proxima_data_linha_visita);
            tvCliente = (TextView)v.findViewById(R.id.tv_cliente_linha_visita);
            tvRua = (TextView)v.findViewById(R.id.tv_rua_linha_visita);
            tvNumero = (TextView)v.findViewById(R.id.tv_numero_linha_visita);
            tvBairro = (TextView)v.findViewById(R.id.tv_bairro_linha_visita);
            tvCidade = (TextView)v.findViewById(R.id.tv_cidade_linha_visita);
        }
    }
}
