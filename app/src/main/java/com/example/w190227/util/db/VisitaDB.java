package com.example.w190227.util.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.w190227.objetos.Visita;

import java.util.ArrayList;

public class VisitaDB {

    private SQLiteDatabase database;
    private BaseDB dbHelper;

    public VisitaDB(Context context){
        dbHelper = new BaseDB(context);
    }

    public void abrirBanco(){
        database = dbHelper.getWritableDatabase();
    }

    public void fecharBanco(){
        database.close();
    }

    public void inserir(Visita c){
        ContentValues cv = new ContentValues();

        cv.put(BaseDB.VISITAS_ID, c.getId());
        cv.put(BaseDB.VISITAS_CLIENTE, c.getCliente());
        cv.put(BaseDB.VISITAS_PROXIMA_DATA, c.getDataDaVisita());
        cv.put(BaseDB.VISITAS_OBS, c.getObs());
        cv.put(BaseDB.VISITAS_POSITIVADO, c.getPositivado());
        cv.put(BaseDB.VISITAS_LATITUDE, c.getLatitude());
        cv.put(BaseDB.VISITAS_LONGITUDE, c.getLongitude());

        abrirBanco();
        database.insert(BaseDB.TBL_VISITAS, null, cv);
        fecharBanco();
    }

    public ArrayList<Visita> consultar(){
        ArrayList<Visita>  al_visitas = new ArrayList<>();
        abrirBanco();

        Cursor cursor = database.query(
                BaseDB.TBL_VISITAS,
                BaseDB.TBL_VISITAS_COLUNAS,
                null,
                null,
                null,
                null,
                BaseDB.VISITAS_ID+" DESC"
        );

        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Visita c = new Visita();
            c.setId(cursor.getInt(0));
            c.setCliente(cursor.getInt(1));
            c.setDataDaVisita(cursor.getString(2));
            c.setObs(cursor.getString(3));
            c.setPositivado(cursor.getInt(4));
            c.setLatitude(cursor.getDouble(5));
            c.setLongitude(cursor.getDouble(6));
            cursor.moveToNext();
            al_visitas.add(c);
        }

        cursor.close();
        fecharBanco();
        return al_visitas;
    }

    public Visita consultarSelecionado(int id){
        Visita c = new Visita();
        abrirBanco();

        Cursor cursor = database.query(
                BaseDB.TBL_VISITAS,
                BaseDB.TBL_VISITAS_COLUNAS,
                BaseDB.VISITAS_ID+" LIKE '%"+id+"%'",
                null,
                null,
                null,
                null
        );
        cursor.moveToFirst();
        if(cursor.getCount() == 1){

            c.setId(cursor.getInt(0));
            c.setCliente(cursor.getInt(1));
            c.setDataDaVisita(cursor.getString(2));
            c.setObs(cursor.getString(3));
            c.setPositivado(cursor.getInt(4));
            c.setLatitude(cursor.getDouble(5));
            c.setLongitude(cursor.getDouble(6));

        } else {
            Log.d("LOG", "Erro! Cliente não encontrado.");
        }

        cursor.close();
        fecharBanco();
        return c;
    }

    public int getCodigoNovo(){
        int result;
        abrirBanco();
        Cursor cursor = database.query(
                BaseDB.TBL_VISITAS,
                BaseDB.TBL_VISITAS_COLUNAS,
                null,
                null,
                null,
                null,
                BaseDB.VISITAS_ID+" DESC"
        );
        cursor.moveToFirst();
        if(cursor.getCount()==0) {
            result = 1;
            cursor.close();
        }else{
            result = cursor.getInt(0)+1;
            cursor.close();
        }
        fecharBanco();
        return result;
    }
}
