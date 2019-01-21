package com.example.thirumal.androidlocationtask;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class SplashActivity extends Activity {
    private static final int REQUESTPERMISSIONCODE = 2;
    private String[] permissionsRequired = new String[]{ACCESS_FINE_LOCATION};
    private ImageView logo;
    private Context context;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    @Override
    protected void onCreate(Bundle SaveInstancestate) {
        super.onCreate(SaveInstancestate);
        setContentView(R.layout.splash_layout);
        context = this;
        logo = findViewById(R.id.logo);
        logo.setVisibility(View.VISIBLE);


        Thread timerThread = new Thread() {
            public void run() {
                try {
                    logo.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce));
                    sleep(2000);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (checkSelfPermission()) {
                        Intent intent = new Intent(context, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        getPermission();
                    }

                }
            }
        };
        timerThread.start();


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUESTPERMISSIONCODE:
                try {
                    if (grantResults.length > 0) {
                        boolean allgranted = false;
                        for (int i = 0; i < grantResults.length; i++) {
                            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                                allgranted = true;
                            } else {
                                allgranted = false;
                                break;
                            }
                        }
                        if (allgranted) {
                            proceedAfterPermission();
                        } else if (ActivityCompat.shouldShowRequestPermissionRationale(SplashActivity.this, permissionsRequired[0])) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
                            builder.setTitle("Need Location Permissions");
                            builder.setMessage("This app needs Location permissions.");
                            builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                    ActivityCompat.requestPermissions(SplashActivity.this, permissionsRequired, REQUESTPERMISSIONCODE);
                                }
                            });
                            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                    finish();
                                }
                            });
                            builder.show();
                        } else {
                            proceedAfterPermission();
                        }
                    }
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            case MY_PERMISSIONS_REQUEST_LOCATION:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        //Request location updates:
                        proceedAfterPermission();
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                break;
        }
    }

    private boolean checkSelfPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle(R.string.title_location_permission)
                        .setMessage(R.string.text_location_permission)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(SplashActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            }
            return false;
        } else {
            return true;
        }
    }

    private void getPermission() {
        if (!checkSelfPermission()) {
            ActivityCompat.requestPermissions(SplashActivity.this, new String[]
                    {ACCESS_FINE_LOCATION}, REQUESTPERMISSIONCODE);
        } else {
            //You already have the permission, just go ahead.
            proceedAfterPermission();
        }
    }


    private void proceedAfterPermission() {
        Thread timerThread = new Thread() {
            public void run() {
                try {
                    logo.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce));
                    sleep(2000);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (checkSelfPermission()) {
                        Intent intent = new Intent(context, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        getPermission();
                    }

                }
            }
        };
        timerThread.start();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
//        finish();
    }
}
