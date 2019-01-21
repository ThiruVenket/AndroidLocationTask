package com.example.thirumal.androidlocationtask;

public class Observer {
    private static Observer observer = null;
    private LocationInterface locationInterface;

    public static Observer getObserver() {
        if (observer == null) {
            observer = new Observer();
        }
        return observer;
    }

    public void setObserver(LocationInterface locationInterface) {
        this.locationInterface = locationInterface;
    }

    public void notifyConnectionStatus(String status, String value) {
        if (locationInterface != null) {
            locationInterface.notifyConnectionStatus(status, value);
        }
    }
}
