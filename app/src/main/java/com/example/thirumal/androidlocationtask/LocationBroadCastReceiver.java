package com.example.thirumal.androidlocationtask;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

public class LocationBroadCastReceiver extends BroadcastReceiver {
    Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            this.context = context;
            if (intent != null) {
                Log.i("Intent", intent.getAction());
                if (intent.getExtras().get("onLocationChanged") != null) {
                    String value = (String) intent.getExtras().get("onLocationChanged");
                    double longitude = (double) intent.getExtras().get("Longitude");
                    double latitude = (double) intent.getExtras().get("Latitude");
                    String showDetailedLocation = showDetailedLocation(latitude, longitude);
                    Observer.getObserver().notifyConnectionStatus("onLocationChanged", showDetailedLocation);
                } else if (intent.getExtras().get("onStatusChanged") != null) {
                    String value = (String) intent.getExtras().get("onStatusChanged");
                    Observer.getObserver().notifyConnectionStatus("onStatusChanged", value);
                } else if (intent.getExtras().get("onProviderEnabled") != null) {
                    String value = (String) intent.getExtras().get("onProviderEnabled");
                    Observer.getObserver().notifyConnectionStatus("onProviderEnabled", value);
                } else if (intent.getExtras().get("onProviderDisabled") != null) {
                    String value = (String) intent.getExtras().get("onProviderDisabled");
                    Observer.getObserver().notifyConnectionStatus("onProviderDisabled", value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String showDetailedLocation(double latitude, double longitude) {
        String con = null;
        try {
            Geocoder geo = new Geocoder(context, Locale.getDefault());
            List<Address> addresses = geo.getFromLocation(latitude, longitude, 1);
            if (addresses.isEmpty() && addresses.size() != 0) {
                Toast.makeText(context, "Waiting for loacation", Toast.LENGTH_SHORT).show();
            } else {
                if (addresses.size() > 0) {
                    con = addresses.get(0).getFeatureName() + "," + addresses.get(0).getAdminArea() + "," + addresses.get(0).getCountryCode() + "," + addresses.get(0).getCountryName() + "," + addresses.get(0).getLocality() + "," + addresses.get(0).getPostalCode() /*+ "," + addresses.get(0).getSubAdminArea()*/;
                    Log.i("Location", "red pin " + con);
                    if (con != null && !con.equalsIgnoreCase("")) {
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, " Please Reboot Your Device", Toast.LENGTH_SHORT).show();
        }
        return con;
    }
}
