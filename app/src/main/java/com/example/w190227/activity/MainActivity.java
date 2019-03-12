package com.example.w190227.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.w190227.R;
import com.example.w190227.fragment.AgendaFragment;
import com.example.w190227.fragment.ClientesFragment;
import com.example.w190227.fragment.HomeFragment;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

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
}
