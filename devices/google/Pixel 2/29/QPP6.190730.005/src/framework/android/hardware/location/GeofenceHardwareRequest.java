/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.location;

import android.annotation.SystemApi;

@SystemApi
public final class GeofenceHardwareRequest {
    static final int GEOFENCE_TYPE_CIRCLE = 0;
    private int mLastTransition = 4;
    private double mLatitude;
    private double mLongitude;
    private int mMonitorTransitions = 7;
    private int mNotificationResponsiveness = 5000;
    private double mRadius;
    private int mSourceTechnologies = 1;
    private int mType;
    private int mUnknownTimer = 30000;

    public static GeofenceHardwareRequest createCircularGeofence(double d, double d2, double d3) {
        GeofenceHardwareRequest geofenceHardwareRequest = new GeofenceHardwareRequest();
        geofenceHardwareRequest.setCircularGeofence(d, d2, d3);
        return geofenceHardwareRequest;
    }

    private void setCircularGeofence(double d, double d2, double d3) {
        this.mLatitude = d;
        this.mLongitude = d2;
        this.mRadius = d3;
        this.mType = 0;
    }

    public int getLastTransition() {
        return this.mLastTransition;
    }

    public double getLatitude() {
        return this.mLatitude;
    }

    public double getLongitude() {
        return this.mLongitude;
    }

    public int getMonitorTransitions() {
        return this.mMonitorTransitions;
    }

    public int getNotificationResponsiveness() {
        return this.mNotificationResponsiveness;
    }

    public double getRadius() {
        return this.mRadius;
    }

    public int getSourceTechnologies() {
        return this.mSourceTechnologies;
    }

    int getType() {
        return this.mType;
    }

    public int getUnknownTimer() {
        return this.mUnknownTimer;
    }

    public void setLastTransition(int n) {
        this.mLastTransition = n;
    }

    public void setMonitorTransitions(int n) {
        this.mMonitorTransitions = n;
    }

    public void setNotificationResponsiveness(int n) {
        this.mNotificationResponsiveness = n;
    }

    public void setSourceTechnologies(int n) {
        if ((n &= 31) != 0) {
            this.mSourceTechnologies = n;
            return;
        }
        throw new IllegalArgumentException("At least one valid source technology must be set.");
    }

    public void setUnknownTimer(int n) {
        this.mUnknownTimer = n;
    }
}

