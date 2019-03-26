package com.example.w190227.activity;

import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

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
        int statusCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(MainActivity.this);
        if( statusCode != ConnectionResult.SUCCESS)
        {
            Log.e("statuscode",statusCode+"");
            if(GooglePlayServicesUtil.isUserRecoverableError(statusCode))
            {
                Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(
                        statusCode,
                        MainActivity.this,
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
}
