package com.example.w190227.fragment;

import android.content.Context;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.w190227.R;

import java.util.Calendar;

public class BaseFragment extends Fragment {

    protected void replaceFragment(Fragment f){
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frame_content, f, null);
        ft.commit();
    }

    protected void replaceFragmentWithBackStack(Fragment f){
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frame_content, f, null);
        ft.addToBackStack(null);
        ft.commit();
    }

    public void setNavigationViewInvisible(){
        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setVisibility(View.INVISIBLE);
    }

    public void setNavigationViewVisible(){
        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setVisibility(View.VISIBLE);
    }

    public Calendar calcularNovaData(Calendar ultimaData, int numOfDays){
        Calendar ultimoDia = ultimaData;
        ultimoDia.add(Calendar.DAY_OF_MONTH, numOfDays);
        return ultimoDia;
    }

    public void clearBackStack(){
        FragmentManager fm = getFragmentManager();
        for(int i = 0; i< fm.getBackStackEntryCount(); ++i){
            fm.popBackStack();
        }
    }

    public String filtroDoisDigitos(String a){
        String result;

        if(a.length() == 1){
            result = "0"+a;
        } else {
            return a;
        }

        return result;
    }

    public String filtroDesfazerDoisDigitos(String a){
        String result;

        if(a.length() == 2 && a.charAt(0) == 0){
            result = a.substring(1);
        } else {
            return a;
        }

        return result;
    }

    public String formatDate(String a){
        return a.substring(6)+"/"+a.substring(4, 6)+"/"+a.substring(0, 4);
    }

    public String unformatDate(String a){
        return a.substring(6)+""+a.substring(3, 5)+""+a.substring(0, 2);
    }
}
