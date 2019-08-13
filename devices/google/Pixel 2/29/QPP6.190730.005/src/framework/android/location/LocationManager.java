/*
 * Decompiled with CFR 0.145.
 */
package android.location;

import android.annotation.SuppressLint;
import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.location.BatchedLocationCallback;
import android.location.BatchedLocationCallbackTransport;
import android.location.Criteria;
import android.location.Geofence;
import android.location.GnssCapabilities;
import android.location.GnssMeasurementCallbackTransport;
import android.location.GnssMeasurementCorrections;
import android.location.GnssMeasurementsEvent;
import android.location.GnssNavigationMessage;
import android.location.GnssNavigationMessageCallbackTransport;
import android.location.GnssStatus;
import android.location.GpsMeasurementsEvent;
import android.location.GpsNavigationMessageEvent;
import android.location.GpsStatus;
import android.location.IGnssStatusListener;
import android.location.ILocationListener;
import android.location.ILocationManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationProvider;
import android.location.LocationRequest;
import android.location.OnNmeaMessageListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.os.RemoteException;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.ArrayMap;
import android.util.Log;
import com.android.internal.location.ProviderProperties;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LocationManager {
    public static final String EXTRA_PROVIDER_NAME = "android.location.extra.PROVIDER_NAME";
    public static final String FUSED_PROVIDER = "fused";
    public static final String GPS_PROVIDER = "gps";
    public static final String HIGH_POWER_REQUEST_CHANGE_ACTION = "android.location.HIGH_POWER_REQUEST_CHANGE";
    public static final String KEY_LOCATION_CHANGED = "location";
    public static final String KEY_PROVIDER_ENABLED = "providerEnabled";
    public static final String KEY_PROXIMITY_ENTERING = "entering";
    @Deprecated
    public static final String KEY_STATUS_CHANGED = "status";
    public static final String METADATA_SETTINGS_FOOTER_STRING = "com.android.settings.location.FOOTER_STRING";
    public static final String MODE_CHANGED_ACTION = "android.location.MODE_CHANGED";
    @Deprecated
    public static final String MODE_CHANGING_ACTION = "com.android.settings.location.MODE_CHANGING";
    public static final String NETWORK_PROVIDER = "network";
    public static final String PASSIVE_PROVIDER = "passive";
    public static final String PROVIDERS_CHANGED_ACTION = "android.location.PROVIDERS_CHANGED";
    public static final String SETTINGS_FOOTER_DISPLAYED_ACTION = "com.android.settings.location.DISPLAYED_FOOTER";
    public static final String SETTINGS_FOOTER_REMOVED_ACTION = "com.android.settings.location.REMOVED_FOOTER";
    private static final String TAG = "LocationManager";
    private final BatchedLocationCallbackTransport mBatchedLocationCallbackTransport;
    private final Context mContext;
    private final GnssMeasurementCallbackTransport mGnssMeasurementCallbackTransport;
    private final GnssNavigationMessageCallbackTransport mGnssNavigationMessageCallbackTransport;
    private final ArrayMap<OnNmeaMessageListener, GnssStatusListenerTransport> mGnssNmeaListeners = new ArrayMap();
    private volatile GnssStatus mGnssStatus;
    private final ArrayMap<GnssStatus.Callback, GnssStatusListenerTransport> mGnssStatusListeners = new ArrayMap();
    private final ArrayMap<GpsStatus.Listener, GnssStatusListenerTransport> mGpsStatusListeners = new ArrayMap();
    private final ArrayMap<LocationListener, ListenerTransport> mListeners = new ArrayMap();
    @UnsupportedAppUsage
    private final ILocationManager mService;
    private int mTimeToFirstFix;

    public LocationManager(Context context, ILocationManager iLocationManager) {
        this.mService = iLocationManager;
        this.mContext = context;
        this.mGnssMeasurementCallbackTransport = new GnssMeasurementCallbackTransport(this.mContext, this.mService);
        this.mGnssNavigationMessageCallbackTransport = new GnssNavigationMessageCallbackTransport(this.mContext, this.mService);
        this.mBatchedLocationCallbackTransport = new BatchedLocationCallbackTransport(this.mContext, this.mService);
    }

    private static void checkCriteria(Criteria criteria) {
        if (criteria != null) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("invalid criteria: ");
        stringBuilder.append(criteria);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    private static void checkGeofence(Geofence geofence) {
        if (geofence != null) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("invalid geofence: ");
        stringBuilder.append(geofence);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    private static void checkListener(LocationListener locationListener) {
        if (locationListener != null) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("invalid listener: ");
        stringBuilder.append(locationListener);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    private void checkPendingIntent(PendingIntent object) {
        if (object != null) {
            if (!((PendingIntent)object).isTargetedToPackage()) {
                object = new IllegalArgumentException("pending intent must be targeted to package");
                if (this.mContext.getApplicationInfo().targetSdkVersion <= 16) {
                    Log.w(TAG, (Throwable)object);
                } else {
                    throw object;
                }
            }
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("invalid pending intent: ");
        stringBuilder.append(object);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    private static void checkProvider(String string2) {
        if (string2 != null) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("invalid provider: ");
        stringBuilder.append(string2);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    private LocationProvider createProvider(String string2, ProviderProperties providerProperties) {
        return new LocationProvider(string2, providerProperties);
    }

    @UnsupportedAppUsage
    private void requestLocationUpdates(LocationRequest locationRequest, LocationListener object, Looper looper, PendingIntent pendingIntent) {
        String string2 = this.mContext.getPackageName();
        object = this.wrapListener((LocationListener)object, looper);
        try {
            this.mService.requestLocationUpdates(locationRequest, (ILocationListener)object, pendingIntent, string2);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private ListenerTransport wrapListener(LocationListener locationListener, Looper looper) {
        if (locationListener == null) {
            return null;
        }
        ArrayMap<LocationListener, ListenerTransport> arrayMap = this.mListeners;
        synchronized (arrayMap) {
            ListenerTransport listenerTransport;
            ListenerTransport listenerTransport2 = listenerTransport = this.mListeners.get(locationListener);
            if (listenerTransport == null) {
                listenerTransport2 = new ListenerTransport(locationListener, looper);
            }
            this.mListeners.put(locationListener, listenerTransport2);
            return listenerTransport2;
        }
    }

    public void addGeofence(LocationRequest locationRequest, Geofence geofence, PendingIntent pendingIntent) {
        this.checkPendingIntent(pendingIntent);
        LocationManager.checkGeofence(geofence);
        try {
            this.mService.requestGeofence(locationRequest, geofence, pendingIntent, this.mContext.getPackageName());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    @Deprecated
    @SuppressLint(value={"Doclava125"})
    public boolean addGpsMeasurementListener(GpsMeasurementsEvent.Listener listener) {
        return false;
    }

    @SystemApi
    @Deprecated
    @SuppressLint(value={"Doclava125"})
    public boolean addGpsNavigationMessageListener(GpsNavigationMessageEvent.Listener listener) {
        return false;
    }

    @Deprecated
    public boolean addGpsStatusListener(GpsStatus.Listener listener) {
        boolean bl;
        block4 : {
            GnssStatusListenerTransport gnssStatusListenerTransport;
            if (this.mGpsStatusListeners.get(listener) != null) {
                return true;
            }
            try {
                gnssStatusListenerTransport = new GnssStatusListenerTransport(listener, null);
                bl = this.mService.registerGnssStatusCallback(gnssStatusListenerTransport, this.mContext.getPackageName());
                if (!bl) break block4;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
            this.mGpsStatusListeners.put(listener, gnssStatusListenerTransport);
        }
        return bl;
    }

    @Deprecated
    public boolean addNmeaListener(GpsStatus.NmeaListener nmeaListener) {
        return false;
    }

    public boolean addNmeaListener(OnNmeaMessageListener onNmeaMessageListener) {
        return this.addNmeaListener(onNmeaMessageListener, null);
    }

    public boolean addNmeaListener(OnNmeaMessageListener onNmeaMessageListener, Handler handler) {
        boolean bl;
        block4 : {
            GnssStatusListenerTransport gnssStatusListenerTransport;
            if (this.mGnssNmeaListeners.get(onNmeaMessageListener) != null) {
                return true;
            }
            try {
                gnssStatusListenerTransport = new GnssStatusListenerTransport(onNmeaMessageListener, handler);
                bl = this.mService.registerGnssStatusCallback(gnssStatusListenerTransport, this.mContext.getPackageName());
                if (!bl) break block4;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
            this.mGnssNmeaListeners.put(onNmeaMessageListener, gnssStatusListenerTransport);
        }
        return bl;
    }

    public void addProximityAlert(double d, double d2, float f, long l, PendingIntent pendingIntent) {
        this.checkPendingIntent(pendingIntent);
        long l2 = l;
        if (l < 0L) {
            l2 = Long.MAX_VALUE;
        }
        Geofence geofence = Geofence.createCircle(d, d2, f);
        LocationRequest locationRequest = new LocationRequest().setExpireIn(l2);
        try {
            this.mService.requestGeofence(locationRequest, geofence, pendingIntent, this.mContext.getPackageName());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void addTestProvider(String string2, boolean bl, boolean bl2, boolean bl3, boolean bl4, boolean bl5, boolean bl6, boolean bl7, int n, int n2) {
        Object object = new ProviderProperties(bl, bl2, bl3, bl4, bl5, bl6, bl7, n, n2);
        if (!string2.matches("[^a-zA-Z0-9]")) {
            try {
                this.mService.addTestProvider(string2, (ProviderProperties)object, this.mContext.getOpPackageName());
                return;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("provider name contains illegal character: ");
        ((StringBuilder)object).append(string2);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    @Deprecated
    public void clearTestProviderEnabled(String string2) {
        this.setTestProviderEnabled(string2, false);
    }

    @Deprecated
    public void clearTestProviderLocation(String string2) {
    }

    @Deprecated
    public void clearTestProviderStatus(String string2) {
        this.setTestProviderStatus(string2, 2, null, 0L);
    }

    @SystemApi
    public void flushGnssBatch() {
        try {
            this.mService.flushGnssBatch(this.mContext.getPackageName());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public List<String> getAllProviders() {
        try {
            List<String> list = this.mService.getAllProviders();
            return list;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public String[] getBackgroundThrottlingWhitelist() {
        try {
            String[] arrstring = this.mService.getBackgroundThrottlingWhitelist();
            return arrstring;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public String getBestProvider(Criteria object, boolean bl) {
        LocationManager.checkCriteria((Criteria)object);
        try {
            object = this.mService.getBestProvider((Criteria)object, bl);
            return object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public String getExtraLocationControllerPackage() {
        try {
            String string2 = this.mService.getExtraLocationControllerPackage();
            return string2;
        }
        catch (RemoteException remoteException) {
            remoteException.rethrowFromSystemServer();
            return null;
        }
    }

    @SystemApi
    public int getGnssBatchSize() {
        try {
            int n = this.mService.getGnssBatchSize(this.mContext.getPackageName());
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public GnssCapabilities getGnssCapabilities() {
        long l;
        block3 : {
            try {
                long l2;
                l = l2 = this.mGnssMeasurementCallbackTransport.getGnssCapabilities();
                if (l2 != -1L) break block3;
                l = 0L;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        GnssCapabilities gnssCapabilities = GnssCapabilities.of(l);
        return gnssCapabilities;
    }

    public String getGnssHardwareModelName() {
        try {
            String string2 = this.mService.getGnssHardwareModelName();
            return string2;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public int getGnssYearOfHardware() {
        try {
            int n = this.mService.getGnssYearOfHardware();
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public GpsStatus getGpsStatus(GpsStatus gpsStatus) {
        GpsStatus gpsStatus2 = gpsStatus;
        if (gpsStatus == null) {
            gpsStatus2 = new GpsStatus();
        }
        if (this.mGnssStatus != null) {
            gpsStatus2.setStatus(this.mGnssStatus, this.mTimeToFirstFix);
        }
        return gpsStatus2;
    }

    public String[] getIgnoreSettingsWhitelist() {
        try {
            String[] arrstring = this.mService.getIgnoreSettingsWhitelist();
            return arrstring;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public Location getLastKnownLocation(String object) {
        LocationManager.checkProvider((String)object);
        String string2 = this.mContext.getPackageName();
        object = LocationRequest.createFromDeprecatedProvider((String)object, 0L, 0.0f, true);
        try {
            object = this.mService.getLastLocation((LocationRequest)object, string2);
            return object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public Location getLastLocation() {
        Object object = this.mContext.getPackageName();
        try {
            object = this.mService.getLastLocation(null, (String)object);
            return object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public LocationProvider getProvider(String object) {
        ProviderProperties providerProperties;
        block3 : {
            LocationManager.checkProvider((String)object);
            try {
                providerProperties = this.mService.getProviderProperties((String)object);
                if (providerProperties != null) break block3;
                return null;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        object = this.createProvider((String)object, providerProperties);
        return object;
    }

    public List<String> getProviders(Criteria object, boolean bl) {
        LocationManager.checkCriteria((Criteria)object);
        try {
            object = this.mService.getProviders((Criteria)object, bl);
            return object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public List<String> getProviders(boolean bl) {
        try {
            List<String> list = this.mService.getProviders(null, bl);
            return list;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public List<LocationRequest> getTestProviderCurrentRequests(String object) {
        LocationManager.checkProvider((String)object);
        try {
            object = this.mService.getTestProviderCurrentRequests((String)object, this.mContext.getOpPackageName());
            return object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void injectGnssMeasurementCorrections(GnssMeasurementCorrections gnssMeasurementCorrections) {
        try {
            this.mGnssMeasurementCallbackTransport.injectGnssMeasurementCorrections(gnssMeasurementCorrections);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean injectLocation(Location location) {
        try {
            boolean bl = this.mService.injectLocation(location);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean isExtraLocationControllerPackageEnabled() {
        try {
            boolean bl = this.mService.isExtraLocationControllerPackageEnabled();
            return bl;
        }
        catch (RemoteException remoteException) {
            remoteException.rethrowFromSystemServer();
            return false;
        }
    }

    public boolean isLocationEnabled() {
        return this.isLocationEnabledForUser(Process.myUserHandle());
    }

    @SystemApi
    public boolean isLocationEnabledForUser(UserHandle userHandle) {
        try {
            boolean bl = this.mService.isLocationEnabledForUser(userHandle.getIdentifier());
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean isProviderEnabled(String string2) {
        return this.isProviderEnabledForUser(string2, Process.myUserHandle());
    }

    @SystemApi
    public boolean isProviderEnabledForUser(String string2, UserHandle userHandle) {
        LocationManager.checkProvider(string2);
        try {
            boolean bl = this.mService.isProviderEnabledForUser(string2, userHandle.getIdentifier());
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean isProviderPackage(String string2) {
        try {
            boolean bl = this.mService.isProviderPackage(string2);
            return bl;
        }
        catch (RemoteException remoteException) {
            remoteException.rethrowFromSystemServer();
            return false;
        }
    }

    @SystemApi
    public boolean registerGnssBatchedLocationCallback(long l, boolean bl, BatchedLocationCallback batchedLocationCallback, Handler handler) {
        this.mBatchedLocationCallbackTransport.add(batchedLocationCallback, handler);
        try {
            bl = this.mService.startGnssBatch(l, bl, this.mContext.getPackageName());
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean registerGnssMeasurementsCallback(GnssMeasurementsEvent.Callback callback) {
        return this.registerGnssMeasurementsCallback(callback, null);
    }

    public boolean registerGnssMeasurementsCallback(GnssMeasurementsEvent.Callback callback, Handler handler) {
        return this.mGnssMeasurementCallbackTransport.add(callback, handler);
    }

    public boolean registerGnssNavigationMessageCallback(GnssNavigationMessage.Callback callback) {
        return this.registerGnssNavigationMessageCallback(callback, null);
    }

    public boolean registerGnssNavigationMessageCallback(GnssNavigationMessage.Callback callback, Handler handler) {
        return this.mGnssNavigationMessageCallbackTransport.add(callback, handler);
    }

    public boolean registerGnssStatusCallback(GnssStatus.Callback callback) {
        return this.registerGnssStatusCallback(callback, null);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean registerGnssStatusCallback(GnssStatus.Callback callback, Handler handler) {
        ArrayMap<GnssStatus.Callback, GnssStatusListenerTransport> arrayMap = this.mGnssStatusListeners;
        synchronized (arrayMap) {
            if (this.mGnssStatusListeners.get(callback) != null) {
                return true;
            }
            try {
                GnssStatusListenerTransport gnssStatusListenerTransport = new GnssStatusListenerTransport(callback, handler);
                boolean bl = this.mService.registerGnssStatusCallback(gnssStatusListenerTransport, this.mContext.getPackageName());
                if (bl) {
                    this.mGnssStatusListeners.put(callback, gnssStatusListenerTransport);
                }
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    public void removeAllGeofences(PendingIntent pendingIntent) {
        this.checkPendingIntent(pendingIntent);
        String string2 = this.mContext.getPackageName();
        try {
            this.mService.removeGeofence(null, pendingIntent, string2);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void removeGeofence(Geofence geofence, PendingIntent pendingIntent) {
        this.checkPendingIntent(pendingIntent);
        LocationManager.checkGeofence(geofence);
        String string2 = this.mContext.getPackageName();
        try {
            this.mService.removeGeofence(geofence, pendingIntent, string2);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    @Deprecated
    @SuppressLint(value={"Doclava125"})
    public void removeGpsMeasurementListener(GpsMeasurementsEvent.Listener listener) {
    }

    @SystemApi
    @Deprecated
    @SuppressLint(value={"Doclava125"})
    public void removeGpsNavigationMessageListener(GpsNavigationMessageEvent.Listener listener) {
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Deprecated
    public void removeGpsStatusListener(GpsStatus.Listener object) {
        try {
            object = this.mGpsStatusListeners.remove(object);
            if (object == null) return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
        this.mService.unregisterGnssStatusCallback((IGnssStatusListener)object);
    }

    @Deprecated
    public void removeNmeaListener(GpsStatus.NmeaListener nmeaListener) {
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void removeNmeaListener(OnNmeaMessageListener object) {
        try {
            object = this.mGnssNmeaListeners.remove(object);
            if (object == null) return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
        this.mService.unregisterGnssStatusCallback((IGnssStatusListener)object);
    }

    public void removeProximityAlert(PendingIntent pendingIntent) {
        this.checkPendingIntent(pendingIntent);
        String string2 = this.mContext.getPackageName();
        try {
            this.mService.removeGeofence(null, pendingIntent, string2);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void removeTestProvider(String string2) {
        try {
            this.mService.removeTestProvider(string2, this.mContext.getOpPackageName());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void removeUpdates(PendingIntent pendingIntent) {
        this.checkPendingIntent(pendingIntent);
        String string2 = this.mContext.getPackageName();
        try {
            this.mService.removeUpdates(null, pendingIntent, string2);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void removeUpdates(LocationListener object) {
        LocationManager.checkListener((LocationListener)object);
        String string2 = this.mContext.getPackageName();
        ArrayMap<LocationListener, ListenerTransport> arrayMap = this.mListeners;
        // MONITORENTER : arrayMap
        object = this.mListeners.remove(object);
        // MONITOREXIT : arrayMap
        if (object == null) {
            return;
        }
        try {
            this.mService.removeUpdates((ILocationListener)object, null, string2);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void requestLocationUpdates(long l, float f, Criteria criteria, PendingIntent pendingIntent) {
        LocationManager.checkCriteria(criteria);
        this.checkPendingIntent(pendingIntent);
        this.requestLocationUpdates(LocationRequest.createFromDeprecatedCriteria(criteria, l, f, false), null, null, pendingIntent);
    }

    public void requestLocationUpdates(long l, float f, Criteria criteria, LocationListener locationListener, Looper looper) {
        LocationManager.checkCriteria(criteria);
        LocationManager.checkListener(locationListener);
        this.requestLocationUpdates(LocationRequest.createFromDeprecatedCriteria(criteria, l, f, false), locationListener, looper, null);
    }

    @SystemApi
    public void requestLocationUpdates(LocationRequest locationRequest, PendingIntent pendingIntent) {
        this.checkPendingIntent(pendingIntent);
        this.requestLocationUpdates(locationRequest, null, null, pendingIntent);
    }

    @SystemApi
    public void requestLocationUpdates(LocationRequest locationRequest, LocationListener locationListener, Looper looper) {
        LocationManager.checkListener(locationListener);
        this.requestLocationUpdates(locationRequest, locationListener, looper, null);
    }

    public void requestLocationUpdates(String string2, long l, float f, PendingIntent pendingIntent) {
        LocationManager.checkProvider(string2);
        this.checkPendingIntent(pendingIntent);
        this.requestLocationUpdates(LocationRequest.createFromDeprecatedProvider(string2, l, f, false), null, null, pendingIntent);
    }

    public void requestLocationUpdates(String string2, long l, float f, LocationListener locationListener) {
        LocationManager.checkProvider(string2);
        LocationManager.checkListener(locationListener);
        this.requestLocationUpdates(LocationRequest.createFromDeprecatedProvider(string2, l, f, false), locationListener, null, null);
    }

    public void requestLocationUpdates(String string2, long l, float f, LocationListener locationListener, Looper looper) {
        LocationManager.checkProvider(string2);
        LocationManager.checkListener(locationListener);
        this.requestLocationUpdates(LocationRequest.createFromDeprecatedProvider(string2, l, f, false), locationListener, looper, null);
    }

    public void requestSingleUpdate(Criteria criteria, PendingIntent pendingIntent) {
        LocationManager.checkCriteria(criteria);
        this.checkPendingIntent(pendingIntent);
        this.requestLocationUpdates(LocationRequest.createFromDeprecatedCriteria(criteria, 0L, 0.0f, true), null, null, pendingIntent);
    }

    public void requestSingleUpdate(Criteria criteria, LocationListener locationListener, Looper looper) {
        LocationManager.checkCriteria(criteria);
        LocationManager.checkListener(locationListener);
        this.requestLocationUpdates(LocationRequest.createFromDeprecatedCriteria(criteria, 0L, 0.0f, true), locationListener, looper, null);
    }

    public void requestSingleUpdate(String string2, PendingIntent pendingIntent) {
        LocationManager.checkProvider(string2);
        this.checkPendingIntent(pendingIntent);
        this.requestLocationUpdates(LocationRequest.createFromDeprecatedProvider(string2, 0L, 0.0f, true), null, null, pendingIntent);
    }

    public void requestSingleUpdate(String string2, LocationListener locationListener, Looper looper) {
        LocationManager.checkProvider(string2);
        LocationManager.checkListener(locationListener);
        this.requestLocationUpdates(LocationRequest.createFromDeprecatedProvider(string2, 0L, 0.0f, true), locationListener, looper, null);
    }

    public boolean sendExtraCommand(String string2, String string3, Bundle bundle) {
        try {
            boolean bl = this.mService.sendExtraCommand(string2, string3, bundle);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public boolean sendNiResponse(int n, int n2) {
        try {
            boolean bl = this.mService.sendNiResponse(n, n2);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void setExtraLocationControllerPackage(String string2) {
        try {
            this.mService.setExtraLocationControllerPackage(string2);
        }
        catch (RemoteException remoteException) {
            remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void setExtraLocationControllerPackageEnabled(boolean bl) {
        try {
            this.mService.setExtraLocationControllerPackageEnabled(bl);
        }
        catch (RemoteException remoteException) {
            remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    @Deprecated
    public void setLocationControllerExtraPackage(String string2) {
        try {
            this.mService.setExtraLocationControllerPackage(string2);
        }
        catch (RemoteException remoteException) {
            remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    @Deprecated
    public void setLocationControllerExtraPackageEnabled(boolean bl) {
        try {
            this.mService.setExtraLocationControllerPackageEnabled(bl);
        }
        catch (RemoteException remoteException) {
            remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void setLocationEnabledForUser(boolean bl, UserHandle userHandle) {
        ContentResolver contentResolver = this.mContext.getContentResolver();
        int n = bl ? 3 : 0;
        Settings.Secure.putIntForUser(contentResolver, "location_mode", n, userHandle.getIdentifier());
    }

    @SystemApi
    @Deprecated
    public boolean setProviderEnabledForUser(String string2, boolean bl, UserHandle userHandle) {
        LocationManager.checkProvider(string2);
        ContentResolver contentResolver = this.mContext.getContentResolver();
        StringBuilder stringBuilder = new StringBuilder();
        String string3 = bl ? "+" : "-";
        stringBuilder.append(string3);
        stringBuilder.append(string2);
        return Settings.Secure.putStringForUser(contentResolver, "location_providers_allowed", stringBuilder.toString(), userHandle.getIdentifier());
    }

    public void setTestProviderEnabled(String string2, boolean bl) {
        try {
            this.mService.setTestProviderEnabled(string2, bl, this.mContext.getOpPackageName());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void setTestProviderLocation(String string2, Location location) {
        if (!location.isComplete()) {
            Serializable serializable = new StringBuilder();
            serializable.append("Incomplete location object, missing timestamp or accuracy? ");
            serializable.append(location);
            serializable = new IllegalArgumentException(serializable.toString());
            if (this.mContext.getApplicationInfo().targetSdkVersion <= 16) {
                Log.w(TAG, (Throwable)serializable);
                location.makeComplete();
            } else {
                throw serializable;
            }
        }
        try {
            this.mService.setTestProviderLocation(string2, location, this.mContext.getOpPackageName());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public void setTestProviderStatus(String string2, int n, Bundle bundle, long l) {
        try {
            this.mService.setTestProviderStatus(string2, n, bundle, l, this.mContext.getOpPackageName());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean unregisterGnssBatchedLocationCallback(BatchedLocationCallback batchedLocationCallback) {
        this.mBatchedLocationCallbackTransport.remove(batchedLocationCallback);
        try {
            boolean bl = this.mService.stopGnssBatch();
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void unregisterGnssMeasurementsCallback(GnssMeasurementsEvent.Callback callback) {
        this.mGnssMeasurementCallbackTransport.remove(callback);
    }

    public void unregisterGnssNavigationMessageCallback(GnssNavigationMessage.Callback callback) {
        this.mGnssNavigationMessageCallbackTransport.remove(callback);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void unregisterGnssStatusCallback(GnssStatus.Callback object) {
        ArrayMap<GnssStatus.Callback, GnssStatusListenerTransport> arrayMap = this.mGnssStatusListeners;
        synchronized (arrayMap) {
            try {
                try {
                    object = this.mGnssStatusListeners.remove(object);
                    if (object != null) {
                        this.mService.unregisterGnssStatusCallback((IGnssStatusListener)object);
                    }
                    return;
                }
                catch (RemoteException remoteException) {
                    throw remoteException.rethrowFromSystemServer();
                }
            }
            catch (Throwable throwable2) {}
            throw throwable2;
        }
    }

    private class GnssStatusListenerTransport
    extends IGnssStatusListener.Stub {
        private static final int GNSS_EVENT_FIRST_FIX = 4;
        private static final int GNSS_EVENT_SATELLITE_STATUS = 5;
        private static final int GNSS_EVENT_STARTED = 2;
        private static final int GNSS_EVENT_STOPPED = 3;
        private static final int NMEA_RECEIVED = 1;
        private final GnssStatus.Callback mGnssCallback;
        private final Handler mGnssHandler;
        private final OnNmeaMessageListener mGnssNmeaListener;
        private final ArrayList<Nmea> mNmeaBuffer;

        GnssStatusListenerTransport(GnssStatus.Callback callback, Handler handler) {
            this.mGnssCallback = callback;
            this.mGnssHandler = new GnssHandler(handler);
            this.mGnssNmeaListener = null;
            this.mNmeaBuffer = null;
        }

        GnssStatusListenerTransport(GpsStatus.Listener listener, Handler handler) {
            this.mGnssHandler = new GnssHandler(handler);
            this.mNmeaBuffer = null;
            LocationManager.this = listener != null ? new GnssStatus.Callback((LocationManager)LocationManager.this, listener){
                final /* synthetic */ GpsStatus.Listener val$listener;
                final /* synthetic */ LocationManager val$this$0;
                {
                    this.val$this$0 = locationManager;
                    this.val$listener = listener;
                }

                @Override
                public void onFirstFix(int n) {
                    this.val$listener.onGpsStatusChanged(3);
                }

                @Override
                public void onSatelliteStatusChanged(GnssStatus gnssStatus) {
                    this.val$listener.onGpsStatusChanged(4);
                }

                @Override
                public void onStarted() {
                    this.val$listener.onGpsStatusChanged(1);
                }

                @Override
                public void onStopped() {
                    this.val$listener.onGpsStatusChanged(2);
                }
            } : null;
            this.mGnssCallback = LocationManager.this;
            this.mGnssNmeaListener = null;
        }

        GnssStatusListenerTransport(OnNmeaMessageListener onNmeaMessageListener, Handler handler) {
            this.mGnssCallback = null;
            this.mGnssHandler = new GnssHandler(handler);
            this.mGnssNmeaListener = onNmeaMessageListener;
            this.mNmeaBuffer = new ArrayList();
        }

        @Override
        public void onFirstFix(int n) {
            if (this.mGnssCallback != null) {
                LocationManager.this.mTimeToFirstFix = n;
                this.mGnssHandler.obtainMessage(4).sendToTarget();
            }
        }

        @Override
        public void onGnssStarted() {
            if (this.mGnssCallback != null) {
                this.mGnssHandler.obtainMessage(2).sendToTarget();
            }
        }

        @Override
        public void onGnssStopped() {
            if (this.mGnssCallback != null) {
                this.mGnssHandler.obtainMessage(3).sendToTarget();
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void onNmeaReceived(long l, String string2) {
            if (this.mGnssNmeaListener == null) return;
            ArrayList<Nmea> arrayList = this.mNmeaBuffer;
            synchronized (arrayList) {
                ArrayList<Nmea> arrayList2 = this.mNmeaBuffer;
                Nmea nmea = new Nmea(l, string2);
                arrayList2.add(nmea);
            }
            this.mGnssHandler.removeMessages(1);
            this.mGnssHandler.obtainMessage(1).sendToTarget();
        }

        @Override
        public void onSvStatusChanged(int n, int[] arrn, float[] arrf, float[] arrf2, float[] arrf3, float[] arrf4) {
            if (this.mGnssCallback != null) {
                LocationManager.this.mGnssStatus = new GnssStatus(n, arrn, arrf, arrf2, arrf3, arrf4);
                this.mGnssHandler.removeMessages(5);
                this.mGnssHandler.obtainMessage(5).sendToTarget();
            }
        }

        private class GnssHandler
        extends Handler {
            GnssHandler(Handler handler) {
                GnssStatusListenerTransport.this = handler != null ? handler.getLooper() : Looper.myLooper();
                super((Looper)GnssStatusListenerTransport.this);
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void handleMessage(Message object) {
                int n = ((Message)object).what;
                if (n != 1) {
                    if (n == 2) {
                        GnssStatusListenerTransport.this.mGnssCallback.onStarted();
                        return;
                    }
                    if (n == 3) {
                        GnssStatusListenerTransport.this.mGnssCallback.onStopped();
                        return;
                    }
                    if (n == 4) {
                        GnssStatusListenerTransport.this.mGnssCallback.onFirstFix(LocationManager.this.mTimeToFirstFix);
                        return;
                    }
                    if (n != 5) {
                        return;
                    }
                    GnssStatusListenerTransport.this.mGnssCallback.onSatelliteStatusChanged(LocationManager.this.mGnssStatus);
                    return;
                }
                object = GnssStatusListenerTransport.this.mNmeaBuffer;
                synchronized (object) {
                    Iterator iterator = GnssStatusListenerTransport.this.mNmeaBuffer.iterator();
                    do {
                        if (!iterator.hasNext()) {
                            GnssStatusListenerTransport.this.mNmeaBuffer.clear();
                            return;
                        }
                        Nmea nmea = (Nmea)iterator.next();
                        GnssStatusListenerTransport.this.mGnssNmeaListener.onNmeaMessage(nmea.mNmea, nmea.mTimestamp);
                    } while (true);
                }
            }
        }

        private class Nmea {
            String mNmea;
            long mTimestamp;

            Nmea(long l, String string2) {
                this.mTimestamp = l;
                this.mNmea = string2;
            }
        }

    }

    private class ListenerTransport
    extends ILocationListener.Stub {
        private static final int TYPE_LOCATION_CHANGED = 1;
        private static final int TYPE_PROVIDER_DISABLED = 4;
        private static final int TYPE_PROVIDER_ENABLED = 3;
        private static final int TYPE_STATUS_CHANGED = 2;
        private LocationListener mListener;
        private final Handler mListenerHandler;

        ListenerTransport(LocationListener locationListener, Looper looper) {
            this.mListener = locationListener;
            this.mListenerHandler = looper == null ? new Handler(){

                @Override
                public void handleMessage(Message message) {
                    ListenerTransport.this._handleMessage(message);
                }
            } : new Handler(looper){

                @Override
                public void handleMessage(Message message) {
                    ListenerTransport.this._handleMessage(message);
                }
            };
        }

        private void _handleMessage(Message object) {
            int n = ((Message)object).what;
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n == 4) {
                            this.mListener.onProviderDisabled((String)((Message)object).obj);
                        }
                    } else {
                        this.mListener.onProviderEnabled((String)((Message)object).obj);
                    }
                } else {
                    Bundle bundle = (Bundle)((Message)object).obj;
                    object = bundle.getString("provider");
                    n = bundle.getInt(LocationManager.KEY_STATUS_CHANGED);
                    bundle = bundle.getBundle("extras");
                    this.mListener.onStatusChanged((String)object, n, bundle);
                }
            } else {
                object = new Location((Location)((Message)object).obj);
                this.mListener.onLocationChanged((Location)object);
            }
            this.locationCallbackFinished();
        }

        private void locationCallbackFinished() {
            try {
                LocationManager.this.mService.locationCallbackFinished(this);
                return;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }

        private void sendCallbackMessage(Message message) {
            if (!this.mListenerHandler.sendMessage(message)) {
                this.locationCallbackFinished();
            }
        }

        @Override
        public void onLocationChanged(Location location) {
            Message message = Message.obtain();
            message.what = 1;
            message.obj = location;
            this.sendCallbackMessage(message);
        }

        @Override
        public void onProviderDisabled(String string2) {
            Message message = Message.obtain();
            message.what = 4;
            message.obj = string2;
            this.sendCallbackMessage(message);
        }

        @Override
        public void onProviderEnabled(String string2) {
            Message message = Message.obtain();
            message.what = 3;
            message.obj = string2;
            this.sendCallbackMessage(message);
        }

        @Override
        public void onStatusChanged(String string2, int n, Bundle bundle) {
            Message message = Message.obtain();
            message.what = 2;
            Bundle bundle2 = new Bundle();
            bundle2.putString("provider", string2);
            bundle2.putInt(LocationManager.KEY_STATUS_CHANGED, n);
            if (bundle != null) {
                bundle2.putBundle("extras", bundle);
            }
            message.obj = bundle2;
            this.sendCallbackMessage(message);
        }

    }

}

