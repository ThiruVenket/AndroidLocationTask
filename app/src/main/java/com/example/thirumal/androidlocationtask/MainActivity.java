package com.example.thirumal.androidlocationtask;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements LocationInterface, View.OnClickListener {
    private TextView locationText;
    private Context context;
    private double latitude;
    private double longitude;
    private LocationManager locationManager;
    private Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        Observer.getObserver().setObserver(this);
    }

    private void initViews() {
        try {
            locationText = findViewById(R.id.location);
            context = this;
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String showDetailedLocation() {
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
                        locationText.setText(con);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), " Please Reboot Your Device", Toast.LENGTH_SHORT).show();
        }
        return con;
    }

    @Override
    public void getLocation(Location location) {
        try {
            Toast.makeText(context, showDetailedLocation(), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void notifyConnectionStatus(String status, String value) {
        if (status != null && !status.equalsIgnoreCase("")) {
            if (status.equalsIgnoreCase("onLocationChanged")) {
                if (value != null && !value.equalsIgnoreCase("")) {
                    locationText.setText(value);
                }
            } else if (status.equalsIgnoreCase("onStatusChanged")) {
                Toast.makeText(context, value, Toast.LENGTH_SHORT).show();
            } else if (status.equalsIgnoreCase("onProviderEnabled")) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                if (location != null) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                    showDetailedLocation();
                }
            } else if (status.equalsIgnoreCase("onProviderDisabled")) {
                showSettingsAlert();
            }
        }
    }

    @Override
    public void onClick(View v) {

    }

    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }
}
