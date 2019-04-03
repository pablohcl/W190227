package com.example.w190227.util.db;

import android.app.Activity;
import android.content.Context;
import android.net.UrlQuerySanitizer;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

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

    public ArrayList<String> baixarTxt(URL url, String fileName) throws IOException {
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

    public void salvarNoBanco(ArrayList<String> list, Context context){
        VendedorDB venDB = new VendedorDB(context);
        venDB.recriarTblVendedor();
        for(int i = 0; i < list.size(); i++){
            String linha = list.get(i);
            String[] dadosLinha = linha.split(";");
            Toast.makeText(context, "valor do primeiro campo: "+Integer.parseInt(dadosLinha[0].replaceAll("\\p{C}", "")), Toast.LENGTH_SHORT).show();

            Vendedor v = new Vendedor();
            v.setId(Integer.parseInt(dadosLinha[0].replaceAll("\\p{C}", "")));
            v.setNome(dadosLinha[1]);
            v.setSenha(dadosLinha[2]);
            v.setMeta(Integer.parseInt(dadosLinha[3].replaceAll("\\p{C}", "")));
            v.setVlrAtual(Integer.parseInt(dadosLinha[4].replaceAll("\\p{C}", "")));

            venDB.inserir(v);
        }
    }
}
