/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.location;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.location.GeofenceHardwareImpl;
import android.hardware.location.GeofenceHardwareRequestParcelable;
import android.hardware.location.IGeofenceHardware;
import android.hardware.location.IGeofenceHardwareCallback;
import android.hardware.location.IGeofenceHardwareMonitorCallback;
import android.location.IFusedGeofenceHardware;
import android.location.IGpsGeofenceHardware;
import android.os.Binder;
import android.os.IBinder;

public class GeofenceHardwareService
extends Service {
    private IBinder mBinder = new IGeofenceHardware.Stub(){

        @Override
        public boolean addCircularFence(int n, GeofenceHardwareRequestParcelable geofenceHardwareRequestParcelable, IGeofenceHardwareCallback iGeofenceHardwareCallback) {
            GeofenceHardwareService.this.mContext.enforceCallingPermission("android.permission.LOCATION_HARDWARE", "Location Hardware permission not granted to access hardware geofence");
            GeofenceHardwareService.this.checkPermission(Binder.getCallingPid(), Binder.getCallingUid(), n);
            return GeofenceHardwareService.this.mGeofenceHardwareImpl.addCircularFence(n, geofenceHardwareRequestParcelable, iGeofenceHardwareCallback);
        }

        @Override
        public int[] getMonitoringTypes() {
            GeofenceHardwareService.this.mContext.enforceCallingPermission("android.permission.LOCATION_HARDWARE", "Location Hardware permission not granted to access hardware geofence");
            return GeofenceHardwareService.this.mGeofenceHardwareImpl.getMonitoringTypes();
        }

        @Override
        public int getStatusOfMonitoringType(int n) {
            GeofenceHardwareService.this.mContext.enforceCallingPermission("android.permission.LOCATION_HARDWARE", "Location Hardware permission not granted to access hardware geofence");
            return GeofenceHardwareService.this.mGeofenceHardwareImpl.getStatusOfMonitoringType(n);
        }

        @Override
        public boolean pauseGeofence(int n, int n2) {
            GeofenceHardwareService.this.mContext.enforceCallingPermission("android.permission.LOCATION_HARDWARE", "Location Hardware permission not granted to access hardware geofence");
            GeofenceHardwareService.this.checkPermission(Binder.getCallingPid(), Binder.getCallingUid(), n2);
            return GeofenceHardwareService.this.mGeofenceHardwareImpl.pauseGeofence(n, n2);
        }

        @Override
        public boolean registerForMonitorStateChangeCallback(int n, IGeofenceHardwareMonitorCallback iGeofenceHardwareMonitorCallback) {
            GeofenceHardwareService.this.mContext.enforceCallingPermission("android.permission.LOCATION_HARDWARE", "Location Hardware permission not granted to access hardware geofence");
            GeofenceHardwareService.this.checkPermission(Binder.getCallingPid(), Binder.getCallingUid(), n);
            return GeofenceHardwareService.this.mGeofenceHardwareImpl.registerForMonitorStateChangeCallback(n, iGeofenceHardwareMonitorCallback);
        }

        @Override
        public boolean removeGeofence(int n, int n2) {
            GeofenceHardwareService.this.mContext.enforceCallingPermission("android.permission.LOCATION_HARDWARE", "Location Hardware permission not granted to access hardware geofence");
            GeofenceHardwareService.this.checkPermission(Binder.getCallingPid(), Binder.getCallingUid(), n2);
            return GeofenceHardwareService.this.mGeofenceHardwareImpl.removeGeofence(n, n2);
        }

        @Override
        public boolean resumeGeofence(int n, int n2, int n3) {
            GeofenceHardwareService.this.mContext.enforceCallingPermission("android.permission.LOCATION_HARDWARE", "Location Hardware permission not granted to access hardware geofence");
            GeofenceHardwareService.this.checkPermission(Binder.getCallingPid(), Binder.getCallingUid(), n2);
            return GeofenceHardwareService.this.mGeofenceHardwareImpl.resumeGeofence(n, n2, n3);
        }

        @Override
        public void setFusedGeofenceHardware(IFusedGeofenceHardware iFusedGeofenceHardware) {
            GeofenceHardwareService.this.mGeofenceHardwareImpl.setFusedGeofenceHardware(iFusedGeofenceHardware);
        }

        @Override
        public void setGpsGeofenceHardware(IGpsGeofenceHardware iGpsGeofenceHardware) {
            GeofenceHardwareService.this.mGeofenceHardwareImpl.setGpsHardwareGeofence(iGpsGeofenceHardware);
        }

        @Override
        public boolean unregisterForMonitorStateChangeCallback(int n, IGeofenceHardwareMonitorCallback iGeofenceHardwareMonitorCallback) {
            GeofenceHardwareService.this.mContext.enforceCallingPermission("android.permission.LOCATION_HARDWARE", "Location Hardware permission not granted to access hardware geofence");
            GeofenceHardwareService.this.checkPermission(Binder.getCallingPid(), Binder.getCallingUid(), n);
            return GeofenceHardwareService.this.mGeofenceHardwareImpl.unregisterForMonitorStateChangeCallback(n, iGeofenceHardwareMonitorCallback);
        }
    };
    private Context mContext;
    private GeofenceHardwareImpl mGeofenceHardwareImpl;

    private void checkPermission(int n, int n2, int n3) {
        if (this.mGeofenceHardwareImpl.getAllowedResolutionLevel(n, n2) >= this.mGeofenceHardwareImpl.getMonitoringResolutionLevel(n3)) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Insufficient permissions to access hardware geofence for type: ");
        stringBuilder.append(n3);
        throw new SecurityException(stringBuilder.toString());
    }

    @Override
    public IBinder onBind(Intent intent) {
        return this.mBinder;
    }

    @Override
    public void onCreate() {
        this.mContext = this;
        this.mGeofenceHardwareImpl = GeofenceHardwareImpl.getInstance(this.mContext);
    }

    @Override
    public void onDestroy() {
        this.mGeofenceHardwareImpl = null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return false;
    }

}

