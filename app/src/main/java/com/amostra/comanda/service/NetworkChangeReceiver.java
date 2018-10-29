package com.amostra.comanda.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.amostra.comanda.App;
import com.amostra.comanda.ui.views.activities.MainActivity;

public class NetworkChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if(context instanceof MainActivity) {
            App.setStatusConnection(getStatusConnection(context));
            ((MainActivity) context).networkStatusChange();
        }
    }

    private boolean getStatusConnection(Context context) {
        final ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }
}
