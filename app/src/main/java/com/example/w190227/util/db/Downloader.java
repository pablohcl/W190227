package com.example.w190227.util.db;

import android.app.Activity;
import android.content.Context;
import android.net.UrlQuerySanitizer;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.w190227.fragment.HomeFragment;
import com.example.w190227.objetos.Categoria;
import com.example.w190227.objetos.Meta;
import com.example.w190227.objetos.Vendedor;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Downloader {

    public Downloader() {
    }

    public ArrayList<String> baixarTxt(URL url) throws IOException {
        StringBuffer datax = new StringBuffer();

        InputStream is = url.openStream();
        //InputStreamReader isr = new InputStreamReader(is, "windows-1250");
        InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(isr);

        String readString = br.readLine();
        ArrayList<String> arrayList = new ArrayList<>();
        while(readString != null){
            datax.append(readString);
            arrayList.add(readString);
            readString = br.readLine();
        }

        isr.close();
        return arrayList;
    }

    public void salvarNoBanco(ArrayList<String> list, Context context, String fileName){
        if(fileName == "vendedores"){
            VendedorDB venDB = new VendedorDB(context);
            venDB.recriarTblVendedor();
            for(int i = 0; i < list.size(); i++){
                String linha = list.get(i);
                String[] dadosLinha = linha.split(";");

                Vendedor v = new Vendedor();
                v.setId(Integer.parseInt(dadosLinha[0].replaceAll("\\p{C}", "")));
                v.setNome(dadosLinha[1]);
                v.setSenha(dadosLinha[2]);
                v.setMeta(Integer.parseInt(dadosLinha[3].replaceAll("\\p{C}", "")));

                venDB.inserir(v);
            }
        } else if(fileName == "metas"){
            MetaDB meDB = new MetaDB(context);
            meDB.recriarTblMetas();
            for(int i = 0; i < list.size(); i++){
                String linha = list.get(i);
                String[] dadosLinha = linha.split(";");

                Meta m = new Meta();
                m.setId(Integer.parseInt(dadosLinha[0].replaceAll("\\p{C}", "")));
                m.setVendedor(Integer.parseInt(dadosLinha[1].replaceAll("\\p{C}", "")));
                m.setCategoria(Integer.parseInt(dadosLinha[2].replaceAll("\\p{C}", "")));
                m.setValor(Double.valueOf(dadosLinha[3]));

                meDB.inserir(m);
            }
        } else if(fileName == "grupo"){
            CategoriaDB catDB = new CategoriaDB(context);
            catDB.recriarTblCategorias();
            for(int i = 0; i < list.size(); i++){
                String linha = list.get(i);
                String[] dadosLinha = linha.split(";");

                Categoria cat = new Categoria();
                cat.setId(Integer.parseInt(dadosLinha[0].replaceAll("\\p{C}", "")));
                cat.setNome(dadosLinha[1]);

                catDB.inserir(cat);
            }
        }
    }
}
