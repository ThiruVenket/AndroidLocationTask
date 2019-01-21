package com.example.thirumal.androidlocationtask;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class LocationApplication extends Application implements LocationListener {
    public LocationManager locationManager;
    private static Context context;
    private static final long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 1; // in Meters
    private static final long MINIMUM_TIME_BETWEEN_UPDATES = 1000; // in Milliseconds
    LocationBroadCastReceiver locationBroadCastReceiver;

    @Override
    public void onCreate() {
        super.onCreate();
        LocationApplication.context = getApplicationContext();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Log.i("LocationApplication", "Oncreate() 1");
        if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MINIMUM_TIME_BETWEEN_UPDATES, MINIMUM_DISTANCE_CHANGE_FOR_UPDATES, this);
        Log.i("LocationApplication", "Oncreate() 2");
        try {
            locationBroadCastReceiver = new LocationBroadCastReceiver();
            IntentFilter intentFilter = new IntentFilter("com.example.thirumal.androidlocationtask");
            registerReceiver(locationBroadCastReceiver, intentFilter);


            /*Intent intent = new Intent();
            intent.setAction("com.example.thirumal.androidlocationtask");
            intent.putExtra("Key", 10);
            sendBroadcast(intent);*/
            Log.i("LocationApplication", "Oncreate() 3");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static Context getAppContext() {
        return LocationApplication.context;
    }

    @Override
    public void onLocationChanged(Location location) {
        try {
            Log.i("Location", "Longti" + location.getLongitude());
            Log.i("Location", "Latitu" + location.getLatitude());
            Intent intent = new Intent();
            intent.setAction("com.example.thirumal.androidlocationtask");
            intent.putExtra("onLocationChanged", "onLocationChanged");
            intent.putExtra("Longitude", location.getLongitude());
            intent.putExtra("Latitude", location.getLatitude());
            sendBroadcast(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        try {
            Intent intent = new Intent();
            intent.setAction("com.example.thirumal.androidlocationtask");
            intent.putExtra("onStatusChanged", "onStatusChanged");
            sendBroadcast(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onProviderEnabled(String provider) {
        try {
            Intent intent = new Intent();
            intent.setAction("com.example.thirumal.androidlocationtask");
            intent.putExtra("onProviderEnabled", "ON");
            sendBroadcast(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onProviderDisabled(String provider) {
        try {
            Intent intent = new Intent();
            intent.setAction("com.example.thirumal.androidlocationtask");
            intent.putExtra("onProviderDisabled", "OFF");
            sendBroadcast(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

    private String showDetailedLocation(double latitude, double longitude) {
        String con = null;
        try {
            Geocoder geo = new Geocoder(this.getApplicationContext(), Locale.getDefault());
            List<Address> addresses = geo.getFromLocation(latitude, longitude, 1);
            if (addresses.isEmpty() && addresses.size() != 0) {
                Toast.makeText(getApplicationContext(), "Waiting for loacation", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(getApplicationContext(), " Please Reboot Your Device", Toast.LENGTH_SHORT).show();
        }
        return con;
    }
}
