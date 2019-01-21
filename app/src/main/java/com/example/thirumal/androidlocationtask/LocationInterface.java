package com.example.thirumal.androidlocationtask;

import android.location.Location;
import android.location.LocationManager;

public interface LocationInterface {
    void getLocation(Location location);

    void notifyConnectionStatus(String status, String value);
}
