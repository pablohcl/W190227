package com.example.w190227.util.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.w190227.objetos.Cliente;

import java.util.ArrayList;

public class ClienteDB {

    private SQLiteDatabase database;
    private BaseDB dbHelper;

    public ClienteDB(Context context){
        dbHelper = new BaseDB(context);
    }

    public void abrirBanco(){
        database = dbHelper.getWritableDatabase();
    }

    public void fecharBanco(){
        database.close();
    }

    public void inserir(Cliente c){
        ContentValues cv = new ContentValues();

        cv.put(BaseDB.CLIENTE_ID, c.getId());
        cv.put(BaseDB.CLIENTE_RAZAO, c.getRazao());
        cv.put(BaseDB.CLIENTE_FANTASIA, c.getFantasia());
        cv.put(BaseDB.CLIENTE_CIDADE, c.getCidade());
        cv.put(BaseDB.CLIENTE_BAIRRO, c.getBairro());
        cv.put(BaseDB.CLIENTE_RUA, c.getRua());
        cv.put(BaseDB.CLIENTE_NUMERO, c.getNumero());
        cv.put(BaseDB.CLIENTE_ULTIMA_DATA_DIA, c.getUltimaDataDia());
        cv.put(BaseDB.CLIENTE_ULTIMA_DATA_MES, c.getUltimaDataMes());
        cv.put(BaseDB.CLIENTE_ULTIMA_DATA_ANO, c.getUltimaDataAno());
        cv.put(BaseDB.CLIENTE_PROXIMA_DATA_DIA, c.getProximaDataDia());
        cv.put(BaseDB.CLIENTE_PROXIMA_DATA_MES, c.getProximaDataMes());
        cv.put(BaseDB.CLIENTE_PROXIMA_DATA_ANO, c.getProximaDataAno());
        cv.put(BaseDB.CLIENTE_FREQUENCIA, c.getFrequencia());
        cv.put(BaseDB.CLIENTE_OBS, c.getObs());
        cv.put(BaseDB.CLIENTE_VENDEDOR, c.getVendedor());

        abrirBanco();
        database.insert(BaseDB.TBL_CLIENTE, null, cv);
        fecharBanco();
    }

    public ArrayList<Cliente> consultar(){
        ArrayList<Cliente>  al_cliente = new ArrayList<>();
        abrirBanco();

        Cursor cursor = database.query(
                BaseDB.TBL_CLIENTE,
                BaseDB.TBL_CLIENTES_COLUNAS,
                null,
                null,
                null,
                null,
                BaseDB.CLIENTE_FANTASIA
        );

        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Cliente c = new Cliente();
            c.setId(cursor.getInt(0));
            c.setRazao(cursor.getString(1));
            c.setFantasia(cursor.getString(2));
            c.setCidade(cursor.getString(3));
            c.setBairro(cursor.getString(4));
            c.setRua(cursor.getString(5));
            c.setNumero(cursor.getString(6));
            c.setUltimaDataDia(cursor.getString(7));
            c.setUltimaDataMes(cursor.getString(8));
            c.setUltimaDataAno(cursor.getString(9));
            c.setProximaDataDia(cursor.getString(10));
            c.setProximaDataMes(cursor.getString(11));
            c.setProximaDataAno(cursor.getString(12));
            c.setFrequencia(cursor.getString(13));
            c.setObs(cursor.getString(14));
            c.setVendedor(cursor.getString(15));
            cursor.moveToNext();
            al_cliente.add(c);
        }

        cursor.close();
        fecharBanco();
        return al_cliente;
    }

    public int getCodigoNovo(){
        int result;
        abrirBanco();
        Cursor cursor = database.query(
                BaseDB.TBL_CLIENTE,
                BaseDB.TBL_CLIENTES_COLUNAS,
                null,
                null,
                null,
                null,
                BaseDB.CLIENTE_ID+" DESC"
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
