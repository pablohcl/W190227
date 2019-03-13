package com.example.w190227.util.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.w190227.objetos.Vendedor;

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

        cv.put(BaseDB.VENDEDOR_ID, c.getId());
        cv.put(BaseDB.VENDEDOR_NOME, c.getNome());
        cv.put(BaseDB.VENDEDOR_META, c.getMeta());
        cv.put(BaseDB.VENDEDOR_VLR_ATUAL, c.getVlrAtual());

        abrirBanco();
        database.insert(BaseDB.TBL_VENDEDOR, null, cv);
        fecharBanco();
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
            c.setMeta(cursor.getDouble(2));
            c.setVlrAtual(cursor.getDouble(3));

        } else {
            Log.d("LOG", "Erro! Vendedor não encontrado.");
        }

        cursor.close();
        fecharBanco();
        return c;
    }
}
