package com.example.w190227.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.w190227.R;
import com.example.w190227.activity.MainActivity;
import com.example.w190227.objetos.Categoria;
import com.example.w190227.objetos.Meta;
import com.example.w190227.objetos.Vendedor;
import com.example.w190227.util.db.MetaDB;
import com.example.w190227.util.db.VendedorDB;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class MetaAdapter extends MyBaseAdapter {

    private Context context;
    private ArrayList<Categoria> alCategorias;
    private Fragment f;

    public MetaAdapter(Context context, ArrayList<Categoria> alCat, Fragment f) {
        this.context = context;
        this.alCategorias = alCat;
        this.f = f;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.linha_meta, viewGroup, false);
        MetaViewHolder metaViewHolder = new MetaViewHolder(v);
        return metaViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        MetaViewHolder holder = (MetaViewHolder) viewHolder;
        Categoria cat = alCategorias.get(i);
        VendedorDB venDB = new VendedorDB(context);
        MetaDB meDB = new MetaDB(context);
        Vendedor v = venDB.consultarSelecionado(MainActivity.VENDEDOR);
        Meta m = meDB.consultarSelecionado(MainActivity.VENDEDOR, cat.getId()); // ERRO, RETORNA MAIS DE UM REGISTRO

        holder.tvCategoria.setText(cat.getNome());
        Double meta = v.getMeta();
        Double valor = m.getValor();
        holder.tvMeta.setText(new DecimalFormat("#,##0.00").format(valor));
        holder.progressBar.setMax(meta.intValue());
        holder.progressBar.setProgress(valor.intValue());
    }

    @Override
    public int getItemCount() {
        return alCategorias.size();
    }

    public class MetaViewHolder extends RecyclerView.ViewHolder{
        final TextView tvCategoria;
        final TextView tvMeta;
        final ProgressBar progressBar;

        public MetaViewHolder(View v){
            super(v);

            tvCategoria = (TextView)v.findViewById(R.id.tv_linha_meta_categoria);
            tvMeta = (TextView)v.findViewById(R.id.tv_linha_meta_meta);
            progressBar = v.findViewById(R.id.progressbar_linha_meta);
        }
    }
}
