package com.example.w190227.fragment;

import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.example.w190227.R;

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
}
