
package com.example.networkconnection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "NT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //checkNetwork();
        //Toast.makeText(this, "Network connected: " + isOnline(), Toast.LENGTH_LONG).show();
        testing();
    }

    /**
     * Prueba la conectividad de red para Wi-Fi y redes móviles. Determina si estas interfaces
     * de red están disponibles (es decir, si es posible establecer conectividad de red)
     * y si el dispositivo está conectado (es decir, si existe una conectividad de red
     * y si es posible establecer sockets y transmitir datos)
     */
    private void checkNetwork(){
        ConnectivityManager connMgr =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean isWifiConn = false;
        boolean isMobileConn = false;
        for (Network network : connMgr.getAllNetworks()) {
            NetworkInfo networkInfo = connMgr.getNetworkInfo(network);
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                isWifiConn |= networkInfo.isConnected();
            }
            if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                isMobileConn |= networkInfo.isConnected();
            }
        }
        Toast.makeText(this, "Wifi connected: " + isWifiConn, Toast.LENGTH_LONG).show();
        Toast.makeText(this, "Mobile connected: " + isMobileConn, Toast.LENGTH_LONG).show();
    }

    /**
     * Muestra una instancia de NetworkInfo, que representa la primera interfaz de red conectada que
     * puede encontrar, o null si ninguna de las interfaces está conectada (lo que significa que una
     * conexión a Internet no está disponible)
     * @return
     */
    public boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        Toast.makeText(this, "Conectividad: " + (networkInfo != null && networkInfo.isConnected()), Toast.LENGTH_LONG).show();
        return (networkInfo != null && networkInfo.isConnected());
    }

    public void testing(){
        ConnectivityManager connectivityManager = getSystemService(ConnectivityManager.class);

        connectivityManager.registerDefaultNetworkCallback(new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(Network network) {
                Log.e(TAG, "The default network is now: " + network);
                isOnline();
            }

            @Override
            public void onLost(Network network) {
                Log.e(TAG, "The application no longer has a default network. The last default network was " + network);
            }

            @Override
            public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
                Log.e(TAG, "The default network changed capabilities: " + networkCapabilities);
            }

            @Override
            public void onLinkPropertiesChanged(Network network, LinkProperties linkProperties) {
                Log.e(TAG, "The default network changed link properties: " + linkProperties);
            }
        });
    }


}