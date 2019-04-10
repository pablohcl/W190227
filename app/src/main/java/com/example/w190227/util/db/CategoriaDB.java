package com.example.w190227.util.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.w190227.objetos.Categoria;

import java.util.ArrayList;

public class CategoriaDB {

    private SQLiteDatabase database;
    private BaseDB dbHelper;

    public CategoriaDB(Context context){
        dbHelper = new BaseDB(context);
    }

    public void abrirBanco(){
        database = dbHelper.getWritableDatabase();
    }

    public void fecharBanco(){
        database.close();
    }

    public void recriarTblCategorias(){
        abrirBanco();
        database.execSQL(BaseDB.DROP_CATEGORIAS);
        database.execSQL(BaseDB.CREATE_CATEGORIAS);
        fecharBanco();
    }

    public void inserir(Categoria c){
        ContentValues cv = new ContentValues();

        cv.put(BaseDB.CATEGORIAS_ID, c.getId());
        cv.put(BaseDB.CATEGORIAS_NOME, c.getNome());

        abrirBanco();
        database.insert(BaseDB.TBL_CATEGORIAS, null, cv);
        fecharBanco();
    }

    public ArrayList<Categoria> consultar(){
        ArrayList<Categoria>  alCategorias = new ArrayList<>();
        abrirBanco();

        Cursor cursor = database.query(
                BaseDB.TBL_CATEGORIAS,
                BaseDB.TBL_CATEGORIAS_COLUNAS,
                null,
                null,
                null,
                null,
                BaseDB.CATEGORIAS_NOME
        );

        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Categoria c = new Categoria();
            c.setId(cursor.getInt(0));
            c.setNome(cursor.getString(1));
            cursor.moveToNext();
            alCategorias.add(c);
        }

        cursor.close();
        fecharBanco();
        return alCategorias;
    }
}
