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
    public static final String CLIENTE_FREQUENCIA = "frequencia";
    public static final String CLIENTE_OBS = "obs";
    public static final String CLIENTE_VENDEDOR = "vendedor";

    // ############ Colunas da tabela cliente ###########
    public static final String[] TBL_CLIENTES_COLUNAS = {
            BaseDB.CLIENTE_ID,
            BaseDB.CLIENTE_RAZAO,
            BaseDB.CLIENTE_FANTASIA,
            BaseDB.CLIENTE_CIDADE,
            BaseDB.CLIENTE_BAIRRO,
            BaseDB.CLIENTE_RUA,
            BaseDB.CLIENTE_NUMERO,
            BaseDB.CLIENTE_FREQUENCIA,
            BaseDB.CLIENTE_OBS,
            BaseDB.CLIENTE_VENDEDOR
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
                    CLIENTE_FREQUENCIA+" INTEGER, "+
                    CLIENTE_OBS+" TEXT, "+
                    CLIENTE_VENDEDOR+" INTEGER"+
                    ");";

    // ################ DDL - Exclusão da tabela cliente ############
    public static final String DROP_CLIENTE =
            "DROP TABLE IF EXISTS "+TBL_CLIENTE;


















    // ################ Tabela Vendedor #################
    public static final String TBL_VENDEDOR = "vendedor";
    public static final String VENDEDOR_ID = "id";
    public static final String VENDEDOR_NOME = "nome";

    // ############### Colunas da tabela vendedor ############
    public static final String[] TBL_VENDEDOR_COLUNAS = {
            VENDEDOR_ID,
            VENDEDOR_NOME
    };

    // ############### DDL - Criação da tabela vendedor ##########
    public static final String CREATE_VENDEDOR =
            "CREATE TABLE "+TBL_VENDEDOR+"("+
                    VENDEDOR_ID+" INTEGER PRIMARY KEY, "+
                    VENDEDOR_NOME+" TEXT NOT NULL"+
                    ");";

    // ################ DDL - Exclusão da tabela vendedor ##########
    public static final String DROP_VENDEDOR = "DROP TABLE IF EXISTS "+TBL_VENDEDOR;


















    // ############ BANCO, NOME, VERSAO #############

    private static final String BANCO_NOME = "w190225.sqlite";
    private static final int BANCO_VERSAO = 1;

    public BaseDB(Context context){
        super(context, BANCO_NOME, null, BANCO_VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_CLIENTE);
        sqLiteDatabase.execSQL(CREATE_VENDEDOR);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DROP_CLIENTE);
        sqLiteDatabase.execSQL(DROP_VENDEDOR);
        onCreate(sqLiteDatabase);
    }
}
