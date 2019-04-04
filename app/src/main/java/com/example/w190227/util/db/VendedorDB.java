package com.example.w190227.util.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.w190227.objetos.Vendedor;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class VendedorDB {

    private SQLiteDatabase database;
    private BaseDB dbHelper;

    public VendedorDB(Context context){
        dbHelper = new BaseDB(context);
    }

    public void abrirBanco(){
        database = dbHelper.getWritableDatabase();
    }

    public void fecharBanco(){
        database.close();
    }

    public void inserir(Vendedor c){
        ContentValues cv = new ContentValues();
        LoginUtil loginUtil = new LoginUtil();

        cv.put(BaseDB.VENDEDOR_ID, c.getId());
        cv.put(BaseDB.VENDEDOR_NOME, c.getNome());
        cv.put(BaseDB.VENDEDOR_SENHA, loginUtil.cript(c.getSenha()));
        cv.put(BaseDB.VENDEDOR_META, c.getMeta());
        cv.put(BaseDB.VENDEDOR_VLR_ATUAL, c.getVlrAtual());

        abrirBanco();
        database.insert(BaseDB.TBL_VENDEDOR, null, cv);
        fecharBanco();
    }

    public void recriarTblVendedor(){
        abrirBanco();
        database.execSQL(BaseDB.DROP_VENDEDOR);
        database.execSQL(BaseDB.CREATE_VENDEDOR);
        fecharBanco();
    }

    public ArrayList<Vendedor> consultar(){
        ArrayList<Vendedor>  al_vendedores = new ArrayList<>();
        abrirBanco();

        Cursor cursor = database.query(
                BaseDB.TBL_VENDEDOR,
                BaseDB.TBL_VENDEDOR_COLUNAS,
                null,
                null,
                null,
                null,
                BaseDB.VENDEDOR_NOME
        );

        if(cursor.getCount() != 0){
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                Vendedor c = new Vendedor();
                c.setId(cursor.getInt(0));
                c.setNome(cursor.getString(1));
                c.setSenha(cursor.getString(2));
                c.setMeta(cursor.getDouble(3));
                c.setVlrAtual(cursor.getDouble(4));
                cursor.moveToNext();
                al_vendedores.add(c);
            }
        }

        cursor.close();
        fecharBanco();
        return al_vendedores;
    }

    public Vendedor consultarSelecionado(int id){
        Vendedor c = new Vendedor();
        abrirBanco();

        Cursor cursor = database.query(
                BaseDB.TBL_VENDEDOR,
                BaseDB.TBL_VENDEDOR_COLUNAS,
                BaseDB.VENDEDOR_ID+" LIKE '%"+id+"%'",
                null,
                null,
                null,
                null
        );
        cursor.moveToFirst();
        if(cursor.getCount() == 1){

            c.setId(cursor.getInt(0));
            c.setNome(cursor.getString(1));
            c.setSenha(cursor.getString(2));
            c.setMeta(cursor.getDouble(3));
            c.setVlrAtual(cursor.getDouble(4));

        } else {
            Log.d("LOG", "Erro! Vendedor não encontrado.");
        }

        cursor.close();
        fecharBanco();
        return c;
    }

    public Vendedor consultarSelecionado(String nome, String senha){
        Vendedor c = new Vendedor();
        abrirBanco();

        Cursor cursor = database.query(
                BaseDB.TBL_VENDEDOR,
                BaseDB.TBL_VENDEDOR_COLUNAS,
                BaseDB.VENDEDOR_NOME+" LIKE '%"+nome+"%' AND "+BaseDB.VENDEDOR_SENHA+" LIKE '%"+senha+"%' ",
                null,
                null,
                null,
                null
        );
        cursor.moveToFirst();
        if(cursor.getCount() == 1){

            c.setId(cursor.getInt(0));
            c.setNome(cursor.getString(1));
            c.setSenha(cursor.getString(2));
            c.setMeta(cursor.getDouble(3));
            c.setVlrAtual(cursor.getDouble(4));

        } else {
            Log.d("LOG", "Erro! Vendedor não encontrado.");
        }

        cursor.close();
        fecharBanco();
        return c;
    }
}
