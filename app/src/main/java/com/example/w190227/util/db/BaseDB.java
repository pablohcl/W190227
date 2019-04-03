package com.example.w190227.util.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseDB extends SQLiteOpenHelper {

    // ############ Tabela cliente #####################
    public static final String TBL_CLIENTE = "t_cliente";
    public static final String CLIENTE_ID = "id";
    public static final String CLIENTE_RAZAO = "razao";
    public static final String CLIENTE_FANTASIA = "fantasia";
    public static final String CLIENTE_CIDADE = "cidade";
    public static final String CLIENTE_BAIRRO = "bairro";
    public static final String CLIENTE_RUA = "rua";
    public static final String CLIENTE_NUMERO = "numero";
    public static final String CLIENTE_ULTIMA_DATA = "ultima_data";
    public static final String CLIENTE_PROXIMA_DATA = "proxima_data";
    public static final String CLIENTE_FREQUENCIA = "frequencia";
    public static final String CLIENTE_OBS = "obs";
    public static final String CLIENTE_VENDEDOR = "vendedor";
    public static final String CLIENTE_LATITUDE = "latitude";
    public static final String CLIENTE_LONGITUDE = "longitude";

    // ############ Colunas da tabela cliente ###########
    public static final String[] TBL_CLIENTES_COLUNAS = {
            BaseDB.CLIENTE_ID,
            BaseDB.CLIENTE_RAZAO,
            BaseDB.CLIENTE_FANTASIA,
            BaseDB.CLIENTE_CIDADE,
            BaseDB.CLIENTE_BAIRRO,
            BaseDB.CLIENTE_RUA,
            BaseDB.CLIENTE_NUMERO,
            BaseDB.CLIENTE_ULTIMA_DATA,
            BaseDB.CLIENTE_PROXIMA_DATA,
            BaseDB.CLIENTE_FREQUENCIA,
            BaseDB.CLIENTE_OBS,
            BaseDB.CLIENTE_VENDEDOR,
            BaseDB.CLIENTE_LATITUDE,
            BaseDB.CLIENTE_LONGITUDE
    };

    // ############## DDL - Criação da tabela cliente #############
    public static final String CREATE_CLIENTE =
            "CREATE TABLE "+TBL_CLIENTE+"("+
                    CLIENTE_ID+" INTEGER PRIMARY KEY, "+
                    CLIENTE_RAZAO+" TEXT NOT NULL, "+
                    CLIENTE_FANTASIA+" TEXT, "+
                    CLIENTE_CIDADE+" TEXT, "+
                    CLIENTE_BAIRRO+" TEXT, "+
                    CLIENTE_RUA+" TEXT, "+
                    CLIENTE_NUMERO+" TEXT, "+
                    CLIENTE_ULTIMA_DATA+" TEXT, "+
                    CLIENTE_PROXIMA_DATA+" TEXT, "+
                    CLIENTE_FREQUENCIA+" INTEGER, "+
                    CLIENTE_OBS+" TEXT, "+
                    CLIENTE_VENDEDOR+" INTEGER, "+
                    CLIENTE_LATITUDE+" REAL, "+
                    CLIENTE_LONGITUDE+" REAL"+
                    ");";

    // ################ DDL - Exclusão da tabela cliente ############
    public static final String DROP_CLIENTE =
            "DROP TABLE IF EXISTS "+TBL_CLIENTE;






















    // ############## TABELA VISITAS #############
    public static final String TBL_VISITAS = "t_visitas";
    public static final String VISITAS_ID = "id";
    public static final String VISITAS_CLIENTE = "cliente";
    public static final String VISITAS_PROXIMA_DATA = "proxima_data";
    public static final String VISITAS_OBS = "obs";
    public static final String VISITAS_POSITIVADO = "positivado";
    public static final String VISITAS_LATITUDE = "latitude";
    public static final String VISITAS_LONGITUDE = "longitude";

    // ############ Colunas da tabela visitas ###########
    public static final String[] TBL_VISITAS_COLUNAS = {
            BaseDB.VISITAS_ID,
            BaseDB.VISITAS_CLIENTE,
            BaseDB.VISITAS_PROXIMA_DATA,
            BaseDB.VISITAS_OBS,
            BaseDB.VISITAS_POSITIVADO,
            BaseDB.VISITAS_LATITUDE,
            BaseDB.VISITAS_LONGITUDE
    };

    // ############## DDL - Criação da tabela visitas #############
    public static final String CREATE_VISITAS =
            "CREATE TABLE "+TBL_VISITAS+"("+
                    VISITAS_ID+" INTEGER PRIMARY KEY, "+
                    VISITAS_CLIENTE+" INTEGER NOT NULL, "+
                    VISITAS_PROXIMA_DATA+" TEXT, "+
                    VISITAS_OBS+" TEXT, "+
                    VISITAS_POSITIVADO+" INTEGER, "+
                    VISITAS_LATITUDE+" REAL, "+
                    VISITAS_LONGITUDE+" REAL"+
                    ");";

    // ################ DDL - Exclusão da tabela cliente ############
    public static final String DROP_VISITAS =
            "DROP TABLE IF EXISTS "+TBL_VISITAS;




















    // ################ Tabela Vendedor #################
    public static final String TBL_VENDEDOR = "vendedor";
    public static final String VENDEDOR_ID = "id";
    public static final String VENDEDOR_NOME = "nome";
    public static final String VENDEDOR_SENHA = "senha";
    public static final String VENDEDOR_META = "meta";
    public static final String VENDEDOR_VLR_ATUAL = "vlr_atual";

    // ############### Colunas da tabela vendedor ############
    public static final String[] TBL_VENDEDOR_COLUNAS = {
            VENDEDOR_ID,
            VENDEDOR_NOME,
            VENDEDOR_SENHA,
            VENDEDOR_META,
            VENDEDOR_VLR_ATUAL
    };

    // ############### DDL - Criação da tabela vendedor ##########
    public static final String CREATE_VENDEDOR =
            "CREATE TABLE "+TBL_VENDEDOR+"("+
                    VENDEDOR_ID+" INTEGER PRIMARY KEY, "+
                    VENDEDOR_NOME+" TEXT NOT NULL, "+
                    VENDEDOR_SENHA+" TEXT, "+
                    VENDEDOR_META+" REAL, "+
                    VENDEDOR_VLR_ATUAL+" REAL"+
                    ");";

    // ################ DDL - Exclusão da tabela vendedor ##########
    public static final String DROP_VENDEDOR = "DROP TABLE IF EXISTS "+TBL_VENDEDOR;

























    // ############ BANCO, NOME, VERSAO #############

    private static final String BANCO_NOME = "w190227.sqlite";
    private static final int BANCO_VERSAO = 12;

    public BaseDB(Context context){
        super(context, BANCO_NOME, null, BANCO_VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_CLIENTE);
        sqLiteDatabase.execSQL(CREATE_VENDEDOR);
        sqLiteDatabase.execSQL(CREATE_VISITAS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DROP_CLIENTE);
        sqLiteDatabase.execSQL(DROP_VENDEDOR);
        sqLiteDatabase.execSQL(DROP_VISITAS);
        onCreate(sqLiteDatabase);
    }
}
