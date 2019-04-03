package com.example.w190227.fragment;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.example.w190227.R;
import com.example.w190227.adapter.MyBaseAdapter;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.Calendar;

public class BaseFragment extends Fragment {

    private static final int MY_PERMISSIONS_REQUEST_CODE = 0;

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

    public void setDataAtual(TextView tv){
        Calendar calendar = Calendar.getInstance();
        tv.setText(filtroDoisDigitos(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)))+"/"+filtroDoisDigitos(String.valueOf((calendar.get(Calendar.MONTH)+1)))+"/"+String.valueOf(calendar.get(Calendar.YEAR)));
    }

    public void solicitarPermissao(){
        String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
        int location = ContextCompat.checkSelfPermission(getActivity(), perms[0]);
        int internet = ContextCompat.checkSelfPermission(getActivity(), perms[1]);
        int writeStorage = ContextCompat.checkSelfPermission(getActivity(), perms[2]);
        int readStorage = ContextCompat.checkSelfPermission(getActivity(), perms[3]);

        if (location != PackageManager.PERMISSION_GRANTED || internet != PackageManager.PERMISSION_GRANTED || writeStorage != PackageManager.PERMISSION_GRANTED || readStorage != PackageManager.PERMISSION_GRANTED) {
            boolean rationaleLocation = shouldShowRequestPermissionRationale(perms[0]);
            boolean rationaleInternet = shouldShowRequestPermissionRationale(perms[1]);
            boolean rationaleWriteStorage = shouldShowRequestPermissionRationale(perms[2]);
            boolean rationaleReadStorage = shouldShowRequestPermissionRationale(perms[3]);

            // Should we show an explanation?
            if (rationaleLocation || rationaleInternet || rationaleWriteStorage || rationaleReadStorage) {
                requestPermissions(perms, MY_PERMISSIONS_REQUEST_CODE);
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                requestPermissions(perms, MY_PERMISSIONS_REQUEST_CODE);


                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case MY_PERMISSIONS_REQUEST_CODE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                } else {
                    getActivity().finish();
                }

                if(grantResults.length > 0 && grantResults[1] == PackageManager.PERMISSION_GRANTED){

                } else {
                    getActivity().finish();
                }

                if(grantResults.length > 0 && grantResults[2] == PackageManager.PERMISSION_GRANTED){

                } else {
                    getActivity().finish();
                }

                if(grantResults.length > 0 && grantResults[3] == PackageManager.PERMISSION_GRANTED){

                } else {
                    getActivity().finish();
                }

                break;
        }
    }

    public void getUltimaPosicao(final TextView lat, final TextView lon){
        LocationManager service = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if(service.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
            if(ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED){
                try {
                    fusedLocationProviderClient.getLastLocation().addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                lat.setText(String.valueOf(location.getLatitude()));
                                lon.setText(String.valueOf(location.getLongitude()));
                            } else {
                                lat.setText("1.1");
                                lon.setText("1.1");
                                Log.d("log", "fusedLocationProvider RETURN FAILED");
                            }
                        }
                    });
                } catch(SecurityException e){
                    Log.d("log", "ERRO, A PERMISSAO NÃO FOI CONCEDIDA"+e);
                    lat.setText("1.1");
                    lon.setText("1.1");
                }
            } else {
                solicitarPermissao();
                Log.d("log", "PERMISSÃO NÃO CONCEDIDA");
                lat.setText("1.1");
                lon.setText("1.1");
            }
        } else {
            AlertDialog.Builder alertLigarGPS = new AlertDialog.Builder(getActivity());
            alertLigarGPS.setTitle("Atenção!").setMessage("Para continuar é necessário ligar o GPS no modo ALTA PRECISÃO").setNeutralButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    getActivity().getSupportFragmentManager().popBackStack();
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                }
            }).setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    getActivity().getSupportFragmentManager().popBackStack();
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                }
            }).show();
        }
    }

    public void setMap(SupportMapFragment mapf, final double lat, final double lon){
        mapf.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(new LatLng(lat, lon));
                googleMap.clear();
                googleMap.addMarker(markerOptions);
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(markerOptions.getPosition(), 16));
            }
        });
    }
}
