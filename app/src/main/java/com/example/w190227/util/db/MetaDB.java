package com.example.w190227.util.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.GridView;

import com.example.w190227.objetos.Meta;

import java.util.ArrayList;

public class MetaDB {

    private SQLiteDatabase database;
    private BaseDB dbHelper;

    public MetaDB(Context context){
        dbHelper = new BaseDB(context);
    }

    public void abrirBanco(){
        database = dbHelper.getWritableDatabase();
    }

    public void fecharBanco(){
        database.close();
    }

    public void recriarTblMetas(){
        abrirBanco();
        database.execSQL(BaseDB.DROP_METAS);
        database.execSQL(BaseDB.CREATE_METAS);
        fecharBanco();
    }

    public void inserir(Meta c){
        ContentValues cv = new ContentValues();

        cv.put(BaseDB.METAS_ID, c.getId());
        cv.put(BaseDB.METAS_VENDEDOR, c.getVendedor());
        cv.put(BaseDB.METAS_CATEGORIA, c.getCategoria());
        cv.put(BaseDB.METAS_VALOR, c.getValor());

        abrirBanco();
        database.insert(BaseDB.TBL_METAS, null, cv);
        fecharBanco();
    }

    public Meta consultarSelecionado(int id, int cat){
        abrirBanco();

        Cursor cursor = database.query(
                BaseDB.TBL_METAS,
                BaseDB.TBL_METAS_COLUNAS,
                BaseDB.METAS_VENDEDOR+" LIKE '%"+id+"%' AND "+BaseDB.METAS_CATEGORIA+" LIKE '%"+cat+"%'",
                null,
                null,
                null,
                BaseDB.METAS_CATEGORIA
        );
        cursor.moveToFirst();
        Meta m = new Meta();
        if(cursor.getCount() == 1){
            m.setId(cursor.getInt(0));
            m.setVendedor(cursor.getInt(1));
            m.setCategoria(cursor.getInt(2));
            m.setValor(cursor.getDouble(3));
        } else {
            Log.d("LOG", "ERRO, BUSCA DE META RETORNOU MAIS DE UM REGISTRO"+String.valueOf(cursor.getCount()));
            m.setId(0);
        }

        cursor.close();
        fecharBanco();
        return m;
    }

    public Double consultarVlrTotalAtual(int idVendedor){
        abrirBanco();

        Double result;

        Cursor cursor = database.rawQuery("SELECT SUM("+BaseDB.METAS_VALOR+") FROM "+BaseDB.TBL_METAS+" WHERE "+BaseDB.METAS_VENDEDOR+" LIKE '%"+idVendedor+"%'", null);
        if(cursor.getCount() == 1){
            cursor.moveToFirst();
            result = cursor.getDouble(0);
        } else {
            Log.d("LOG", "CONSULTA DE VALOR TOTAL DE META RETORNOU MAIS DE UM REGISTRO. NUMERO DE REGISTROS: "+String.valueOf(cursor.getCount()));
            result = 1.1;
        }

        cursor.close();
        fecharBanco();

        return result;
    }
}
