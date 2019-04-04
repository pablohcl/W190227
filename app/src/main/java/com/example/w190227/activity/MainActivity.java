package com.example.w190227.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.w190227.R;
import com.example.w190227.fragment.AgendaFragment;
import com.example.w190227.fragment.ClientesFragment;
import com.example.w190227.fragment.HomeFragment;
import com.example.w190227.objetos.Vendedor;
import com.example.w190227.util.db.Downloader;
import com.example.w190227.util.db.LoginUtil;
import com.example.w190227.util.db.VendedorDB;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private static boolean LOGGED;
    private static boolean MASTER;
    private static int VENDEDOR;
    private static final int REQUEST_CODE_RECOVER_PLAY_SERVICES = 1;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        if(savedInstanceState == null){
            HomeFragment homeFragment = new HomeFragment();
            replaceFragment(homeFragment);
        }
        verificarGooglePlaySvc();

        if(!LOGGED){
            verificarLogin();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        switch(id){
            case R.id.nav_item_home:
                HomeFragment homeFragment = new HomeFragment();
                clearBackStack();
                replaceFragment(homeFragment);
                setNavigationViewVisible();
                break;

            case R.id.nav_item_agenda:
                AgendaFragment agendaFragment = new AgendaFragment();
                clearBackStack();
                replaceFragment(agendaFragment);
                setNavigationViewVisible();
                break;

            case R.id.nav_item_clientes:
                ClientesFragment clientesFragment = new ClientesFragment();
                clearBackStack();
                replaceFragment(clientesFragment);
                setNavigationViewVisible();
                break;

            default:
                break;
        }

        return true;
    }



    public void replaceFragment(Fragment f){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_content, f, null);
        fragmentTransaction.commit();
    }

    public void replaceFragmentWithBackStack(Fragment f){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_content, f, null);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void clearBackStack(){
        FragmentManager fm = getSupportFragmentManager();
        for(int i = 0; i< fm.getBackStackEntryCount(); ++i){
            fm.popBackStack();
        }
    }

    public void setNavigationViewVisible(){
        bottomNavigationView.setVisibility(View.VISIBLE);
    }

    private void verificarGooglePlaySvc(){
        int statusCode = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);
        if( statusCode != ConnectionResult.SUCCESS)
        {
            Log.e("statuscode",statusCode+"");
            if(GoogleApiAvailability.getInstance().isUserResolvableError(statusCode))
            {
                Dialog errorDialog = GoogleApiAvailability.getInstance().getErrorDialog(
                        MainActivity.this,
                        statusCode,
                        REQUEST_CODE_RECOVER_PLAY_SERVICES);

                // If Google Play services can provide an error dialog
                if (errorDialog != null) {
                    errorDialog.show();
                }
            }
            else
            {
                Toast.makeText(this, "Google Play Services não está instalado!",Toast.LENGTH_LONG).show();
            }
        }
    }

    public void verificarLogin(){
        VendedorDB venDB = new VendedorDB(getBaseContext());
        ArrayList<Vendedor> array = venDB.consultar();
        if(array.size() != 0){
            View v = getLayoutInflater().inflate(R.layout.alert_login, null);
            final TextInputLayout etUsuario = v.findViewById(R.id.et_usuario_alert_login);
            final TextInputLayout etSenha = v.findViewById(R.id.et_senha_alert_login);
            AlertDialog.Builder alertLogin = new AlertDialog.Builder(this);
            alertLogin.setView(v);
            alertLogin.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String usuario = String.valueOf(getResources().getString(R.string.usuario));
                    String senha = String.valueOf(getResources().getString(R.string.pass));
                    VendedorDB venDB = new VendedorDB(MainActivity.this);
                    LoginUtil loginUtil = new LoginUtil();
                    Vendedor v = venDB.consultarSelecionado(etUsuario.getEditText().getText().toString(), loginUtil.cript(etSenha.getEditText().getText().toString()));
                    if(etUsuario.getEditText().getText().toString().equals(usuario) && etSenha.getEditText().getText().toString().equals(senha)){
                        LOGGED = true;
                        MASTER = true;
                        VENDEDOR = 0;
                        Toast.makeText(MainActivity.this, "Bem vindo "+usuario, Toast.LENGTH_SHORT).show();
                    } else if(v.getNome() != null){
                        LOGGED = true;
                        VENDEDOR = v.getId();
                        Toast.makeText(MainActivity.this, "Bem vindo "+v.getNome(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Dados incorretos!", Toast.LENGTH_SHORT).show();
                        verificarLogin();
                    }
                }
            }).setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            }).setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    finish();
                }
            }).show();
        } else {
            try{
                DownloaderAsyncTask download = new DownloaderAsyncTask();
                URL url = new URL("http://www.wattdistribuidora.com.br/mobile/vendedores.txt");
                download.execute(url);
            }catch(Exception e){
                Log.d("LOG", "ERRO NA FORMAÇÃO DA URL: "+e);
            }
        }
    }

    public class DownloaderAsyncTask extends AsyncTask<URL, Void, ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(URL... urls) {
            ArrayList<String> result;
            try {
                Downloader download = new Downloader();
                URL url = urls[0];
                result = download.baixarTxt(url, "grupos");
            }catch(Exception e){
                Log.d("LOG", "ERRO: "+e);
                result = new ArrayList<>();
                result.add("ERRO no arraylist");
            }
            return result;
        }

        @Override
        protected void onPostExecute(ArrayList<String> s) {
            Log.d("LOG", "Baixou: "+s);
            Downloader download = new Downloader();
            download.salvarNoBanco(s, getBaseContext());
            verificarLogin();
        }
    }
}
