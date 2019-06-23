package com.example.user.provectus.manager;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class ConnectiviteManager {

    public boolean isNetworkConnection(Context context) {


       
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm != null ? cm.getActiveNetworkInfo() : null;
        if (networkInfo != null) {
            if (networkInfo.isConnectedOrConnecting()) {
                NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                return (mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnected());
            } else
                return false;
        }
        return false;
    }
}
