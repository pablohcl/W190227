package com.example.w190227.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.w190227.R;
import com.example.w190227.objetos.Cliente;
import com.example.w190227.objetos.Visita;
import com.example.w190227.util.db.ClienteDB;

import java.util.ArrayList;

public class AtendimentoAdapter extends MyBaseAdapter {

    private Context context;
    private ArrayList<Visita> alVisitas;
    private Fragment f;

    public AtendimentoAdapter(Context context, ArrayList<Visita> alVisitas, Fragment f) {
        this.context = context;
        this.alVisitas = alVisitas;
        this.f = f;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.linha_atendimento, viewGroup, false);
        AtendimentoViewHolder atendimentoViewHolder = new AtendimentoViewHolder(v);
        return atendimentoViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        AtendimentoViewHolder holder = (AtendimentoViewHolder) viewHolder;
        Visita v = alVisitas.get(i);
        ClienteDB cliDB = new ClienteDB(context);
        Cliente c = cliDB.consultarSelecionado(v.getCliente());

        holder.tvData.setText(formatDate(v.getDataDaVisita()));
        holder.tvCliente.setText(c.getRazao());
        holder.tvPositivado.setText(filtroBooleanToString(v.getPositivado()));
        holder.tvMotivo.setText(v.getObs());
        holder.itemView.setTag(v.getId());
    }

    @Override
    public int getItemCount() {
        return alVisitas.size();
    }

    private String filtroBooleanToString(int b){
        if(b == 1){
            return "Sim";
        } else {
            return "Não";
        }
    }

    public class AtendimentoViewHolder extends RecyclerView.ViewHolder{
        final TextView tvData;
        final TextView tvCliente;
        final TextView tvPositivado;
        final TextView tvMotivo;

        public AtendimentoViewHolder(View v){
            super(v);

            tvData = (TextView)v.findViewById(R.id.tv_data_linha_atendimento);
            tvCliente = (TextView)v.findViewById(R.id.tv_cliente_linha_atendimento);
            tvPositivado = (TextView)v.findViewById(R.id.tv_positivado_linha_atendimento);
            tvMotivo = (TextView)v.findViewById(R.id.tv_motivo_linha_atendimento);
        }
    }
}
