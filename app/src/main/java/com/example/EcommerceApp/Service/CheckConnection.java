package com.example.EcommerceApp.Service;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.widget.Toast;

public class CheckConnection {
    public static boolean haveNetworkConnection(Context context){
        boolean haveConnectedWifi = false;
        boolean haveConnectedMoblie = false;

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo){
            if(ni.getTypeName().equalsIgnoreCase("Wifi"))
                if(ni.isConnected())
                    haveConnectedWifi = true;
            if(ni.getTypeName().equalsIgnoreCase("Mobile"))
                if (ni.isConnected())
                    haveConnectedMoblie = true;
        }
        return haveConnectedWifi || haveConnectedMoblie;
    }
    public static void showToast_Short(Context context,String thongbao){
        Toast.makeText(context,thongbao,Toast.LENGTH_SHORT).show();
    }
}
