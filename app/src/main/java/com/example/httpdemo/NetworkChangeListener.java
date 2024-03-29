package com.example.httpdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.Gravity;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

public class NetworkChangeListener extends BroadcastReceiver {
    SharedPreferences sharedpreferences;


    @Override
    public void onReceive(Context context, Intent intent) {
        sharedpreferences = context.getApplicationContext().getSharedPreferences("Network", 0);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        if (!isConnectedToInternet(context)) {

            editor.putString("connect", null);
            editor.apply();

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("No Internet Connection")
                    .setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });

            AlertDialog dialog = builder.create();
            dialog.show();
            dialog.getWindow().setGravity(Gravity.CENTER);

        }else{
            editor.putString("connect", "1");
            editor.apply();
        }
    }
    public boolean isConnectedToInternet(Context context){

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);

        if( connectivityManager != null){
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();

            if( networkInfo != null){
                for ( int i = 0 ; i < networkInfo.length ; i++){
                    if( networkInfo[i].getState() == NetworkInfo.State.CONNECTED){
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
