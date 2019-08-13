/*
 * Decompiled with CFR 0.145.
 */
package android.location;

import android.annotation.UnsupportedAppUsage;
import android.app.PendingIntent;
import android.location.Address;
import android.location.Criteria;
import android.location.GeocoderParams;
import android.location.Geofence;
import android.location.GnssMeasurementCorrections;
import android.location.IBatchedLocationCallback;
import android.location.IGnssMeasurementsListener;
import android.location.IGnssNavigationMessageListener;
import android.location.IGnssStatusListener;
import android.location.ILocationListener;
import android.location.Location;
import android.location.LocationRequest;
import android.location.LocationTime;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.android.internal.location.ProviderProperties;
import java.util.ArrayList;
import java.util.List;

public interface ILocationManager
extends IInterface {
    public boolean addGnssBatchingCallback(IBatchedLocationCallback var1, String var2) throws RemoteException;

    public boolean addGnssMeasurementsListener(IGnssMeasurementsListener var1, String var2) throws RemoteException;

    public boolean addGnssNavigationMessageListener(IGnssNavigationMessageListener var1, String var2) throws RemoteException;

    public void addTestProvider(String var1, ProviderProperties var2, String var3) throws RemoteException;

    public void flushGnssBatch(String var1) throws RemoteException;

    public boolean geocoderIsPresent() throws RemoteException;

    @UnsupportedAppUsage
    public List<String> getAllProviders() throws RemoteException;

    public String[] getBackgroundThrottlingWhitelist() throws RemoteException;

    public String getBestProvider(Criteria var1, boolean var2) throws RemoteException;

    public String getExtraLocationControllerPackage() throws RemoteException;

    public String getFromLocation(double var1, double var3, int var5, GeocoderParams var6, List<Address> var7) throws RemoteException;

    public String getFromLocationName(String var1, double var2, double var4, double var6, double var8, int var10, GeocoderParams var11, List<Address> var12) throws RemoteException;

    public int getGnssBatchSize(String var1) throws RemoteException;

    public long getGnssCapabilities(String var1) throws RemoteException;

    public String getGnssHardwareModelName() throws RemoteException;

    public LocationTime getGnssTimeMillis() throws RemoteException;

    public int getGnssYearOfHardware() throws RemoteException;

    public String[] getIgnoreSettingsWhitelist() throws RemoteException;

    public Location getLastLocation(LocationRequest var1, String var2) throws RemoteException;

    public ProviderProperties getProviderProperties(String var1) throws RemoteException;

    public List<String> getProviders(Criteria var1, boolean var2) throws RemoteException;

    public List<LocationRequest> getTestProviderCurrentRequests(String var1, String var2) throws RemoteException;

    public void injectGnssMeasurementCorrections(GnssMeasurementCorrections var1, String var2) throws RemoteException;

    public boolean injectLocation(Location var1) throws RemoteException;

    public boolean isExtraLocationControllerPackageEnabled() throws RemoteException;

    public boolean isLocationEnabledForUser(int var1) throws RemoteException;

    public boolean isProviderEnabledForUser(String var1, int var2) throws RemoteException;

    public boolean isProviderPackage(String var1) throws RemoteException;

    public void locationCallbackFinished(ILocationListener var1) throws RemoteException;

    public boolean registerGnssStatusCallback(IGnssStatusListener var1, String var2) throws RemoteException;

    public void removeGeofence(Geofence var1, PendingIntent var2, String var3) throws RemoteException;

    public void removeGnssBatchingCallback() throws RemoteException;

    public void removeGnssMeasurementsListener(IGnssMeasurementsListener var1) throws RemoteException;

    public void removeGnssNavigationMessageListener(IGnssNavigationMessageListener var1) throws RemoteException;

    public void removeTestProvider(String var1, String var2) throws RemoteException;

    public void removeUpdates(ILocationListener var1, PendingIntent var2, String var3) throws RemoteException;

    public void requestGeofence(LocationRequest var1, Geofence var2, PendingIntent var3, String var4) throws RemoteException;

    public void requestLocationUpdates(LocationRequest var1, ILocationListener var2, PendingIntent var3, String var4) throws RemoteException;

    public boolean sendExtraCommand(String var1, String var2, Bundle var3) throws RemoteException;

    public boolean sendNiResponse(int var1, int var2) throws RemoteException;

    public void setExtraLocationControllerPackage(String var1) throws RemoteException;

    public void setExtraLocationControllerPackageEnabled(boolean var1) throws RemoteException;

    public void setTestProviderEnabled(String var1, boolean var2, String var3) throws RemoteException;

    public void setTestProviderLocation(String var1, Location var2, String var3) throws RemoteException;

    public void setTestProviderStatus(String var1, int var2, Bundle var3, long var4, String var6) throws RemoteException;

    public boolean startGnssBatch(long var1, boolean var3, String var4) throws RemoteException;

    public boolean stopGnssBatch() throws RemoteException;

    public void unregisterGnssStatusCallback(IGnssStatusListener var1) throws RemoteException;

    public static class Default
    implements ILocationManager {
        @Override
        public boolean addGnssBatchingCallback(IBatchedLocationCallback iBatchedLocationCallback, String string2) throws RemoteException {
            return false;
        }

        @Override
        public boolean addGnssMeasurementsListener(IGnssMeasurementsListener iGnssMeasurementsListener, String string2) throws RemoteException {
            return false;
        }

        @Override
        public boolean addGnssNavigationMessageListener(IGnssNavigationMessageListener iGnssNavigationMessageListener, String string2) throws RemoteException {
            return false;
        }

        @Override
        public void addTestProvider(String string2, ProviderProperties providerProperties, String string3) throws RemoteException {
        }

        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void flushGnssBatch(String string2) throws RemoteException {
        }

        @Override
        public boolean geocoderIsPresent() throws RemoteException {
            return false;
        }

        @Override
        public List<String> getAllProviders() throws RemoteException {
            return null;
        }

        @Override
        public String[] getBackgroundThrottlingWhitelist() throws RemoteException {
            return null;
        }

        @Override
        public String getBestProvider(Criteria criteria, boolean bl) throws RemoteException {
            return null;
        }

        @Override
        public String getExtraLocationControllerPackage() throws RemoteException {
            return null;
        }

        @Override
        public String getFromLocation(double d, double d2, int n, GeocoderParams geocoderParams, List<Address> list) throws RemoteException {
            return null;
        }

        @Override
        public String getFromLocationName(String string2, double d, double d2, double d3, double d4, int n, GeocoderParams geocoderParams, List<Address> list) throws RemoteException {
            return null;
        }

        @Override
        public int getGnssBatchSize(String string2) throws RemoteException {
            return 0;
        }

        @Override
        public long getGnssCapabilities(String string2) throws RemoteException {
            return 0L;
        }

        @Override
        public String getGnssHardwareModelName() throws RemoteException {
            return null;
        }

        @Override
        public LocationTime getGnssTimeMillis() throws RemoteException {
            return null;
        }

        @Override
        public int getGnssYearOfHardware() throws RemoteException {
            return 0;
        }

        @Override
        public String[] getIgnoreSettingsWhitelist() throws RemoteException {
            return null;
        }

        @Override
        public Location getLastLocation(LocationRequest locationRequest, String string2) throws RemoteException {
            return null;
        }

        @Override
        public ProviderProperties getProviderProperties(String string2) throws RemoteException {
            return null;
        }

        @Override
        public List<String> getProviders(Criteria criteria, boolean bl) throws RemoteException {
            return null;
        }

        @Override
        public List<LocationRequest> getTestProviderCurrentRequests(String string2, String string3) throws RemoteException {
            return null;
        }

        @Override
        public void injectGnssMeasurementCorrections(GnssMeasurementCorrections gnssMeasurementCorrections, String string2) throws RemoteException {
        }

        @Override
        public boolean injectLocation(Location location) throws RemoteException {
            return false;
        }

        @Override
        public boolean isExtraLocationControllerPackageEnabled() throws RemoteException {
            return false;
        }

        @Override
        public boolean isLocationEnabledForUser(int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean isProviderEnabledForUser(String string2, int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean isProviderPackage(String string2) throws RemoteException {
            return false;
        }

        @Override
        public void locationCallbackFinished(ILocationListener iLocationListener) throws RemoteException {
        }

        @Override
        public boolean registerGnssStatusCallback(IGnssStatusListener iGnssStatusListener, String string2) throws RemoteException {
            return false;
        }

        @Override
        public void removeGeofence(Geofence geofence, PendingIntent pendingIntent, String string2) throws RemoteException {
        }

        @Override
        public void removeGnssBatchingCallback() throws RemoteException {
        }

        @Override
        public void removeGnssMeasurementsListener(IGnssMeasurementsListener iGnssMeasurementsListener) throws RemoteException {
        }

        @Override
        public void removeGnssNavigationMessageListener(IGnssNavigationMessageListener iGnssNavigationMessageListener) throws RemoteException {
        }

        @Override
        public void removeTestProvider(String string2, String string3) throws RemoteException {
        }

        @Override
        public void removeUpdates(ILocationListener iLocationListener, PendingIntent pendingIntent, String string2) throws RemoteException {
        }

        @Override
        public void requestGeofence(LocationRequest locationRequest, Geofence geofence, PendingIntent pendingIntent, String string2) throws RemoteException {
        }

        @Override
        public void requestLocationUpdates(LocationRequest locationRequest, ILocationListener iLocationListener, PendingIntent pendingIntent, String string2) throws RemoteException {
        }

        @Override
        public boolean sendExtraCommand(String string2, String string3, Bundle bundle) throws RemoteException {
            return false;
        }

        @Override
        public boolean sendNiResponse(int n, int n2) throws RemoteException {
            return false;
        }

        @Override
        public void setExtraLocationControllerPackage(String string2) throws RemoteException {
        }

        @Override
        public void setExtraLocationControllerPackageEnabled(boolean bl) throws RemoteException {
        }

        @Override
        public void setTestProviderEnabled(String string2, boolean bl, String string3) throws RemoteException {
        }

        @Override
        public void setTestProviderLocation(String string2, Location location, String string3) throws RemoteException {
        }

        @Override
        public void setTestProviderStatus(String string2, int n, Bundle bundle, long l, String string3) throws RemoteException {
        }

        @Override
        public boolean startGnssBatch(long l, boolean bl, String string2) throws RemoteException {
            return false;
        }

        @Override
        public boolean stopGnssBatch() throws RemoteException {
            return false;
        }

        @Override
        public void unregisterGnssStatusCallback(IGnssStatusListener iGnssStatusListener) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements ILocationManager {
        private static final String DESCRIPTOR = "android.location.ILocationManager";
        static final int TRANSACTION_addGnssBatchingCallback = 21;
        static final int TRANSACTION_addGnssMeasurementsListener = 12;
        static final int TRANSACTION_addGnssNavigationMessageListener = 16;
        static final int TRANSACTION_addTestProvider = 38;
        static final int TRANSACTION_flushGnssBatch = 24;
        static final int TRANSACTION_geocoderIsPresent = 8;
        static final int TRANSACTION_getAllProviders = 27;
        static final int TRANSACTION_getBackgroundThrottlingWhitelist = 47;
        static final int TRANSACTION_getBestProvider = 29;
        static final int TRANSACTION_getExtraLocationControllerPackage = 33;
        static final int TRANSACTION_getFromLocation = 9;
        static final int TRANSACTION_getFromLocationName = 10;
        static final int TRANSACTION_getGnssBatchSize = 20;
        static final int TRANSACTION_getGnssCapabilities = 14;
        static final int TRANSACTION_getGnssHardwareModelName = 19;
        static final int TRANSACTION_getGnssTimeMillis = 43;
        static final int TRANSACTION_getGnssYearOfHardware = 18;
        static final int TRANSACTION_getIgnoreSettingsWhitelist = 48;
        static final int TRANSACTION_getLastLocation = 5;
        static final int TRANSACTION_getProviderProperties = 30;
        static final int TRANSACTION_getProviders = 28;
        static final int TRANSACTION_getTestProviderCurrentRequests = 42;
        static final int TRANSACTION_injectGnssMeasurementCorrections = 13;
        static final int TRANSACTION_injectLocation = 26;
        static final int TRANSACTION_isExtraLocationControllerPackageEnabled = 35;
        static final int TRANSACTION_isLocationEnabledForUser = 37;
        static final int TRANSACTION_isProviderEnabledForUser = 36;
        static final int TRANSACTION_isProviderPackage = 31;
        static final int TRANSACTION_locationCallbackFinished = 46;
        static final int TRANSACTION_registerGnssStatusCallback = 6;
        static final int TRANSACTION_removeGeofence = 4;
        static final int TRANSACTION_removeGnssBatchingCallback = 22;
        static final int TRANSACTION_removeGnssMeasurementsListener = 15;
        static final int TRANSACTION_removeGnssNavigationMessageListener = 17;
        static final int TRANSACTION_removeTestProvider = 39;
        static final int TRANSACTION_removeUpdates = 2;
        static final int TRANSACTION_requestGeofence = 3;
        static final int TRANSACTION_requestLocationUpdates = 1;
        static final int TRANSACTION_sendExtraCommand = 45;
        static final int TRANSACTION_sendNiResponse = 11;
        static final int TRANSACTION_setExtraLocationControllerPackage = 32;
        static final int TRANSACTION_setExtraLocationControllerPackageEnabled = 34;
        static final int TRANSACTION_setTestProviderEnabled = 41;
        static final int TRANSACTION_setTestProviderLocation = 40;
        static final int TRANSACTION_setTestProviderStatus = 44;
        static final int TRANSACTION_startGnssBatch = 23;
        static final int TRANSACTION_stopGnssBatch = 25;
        static final int TRANSACTION_unregisterGnssStatusCallback = 7;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ILocationManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ILocationManager) {
                return (ILocationManager)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ILocationManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 48: {
                    return "getIgnoreSettingsWhitelist";
                }
                case 47: {
                    return "getBackgroundThrottlingWhitelist";
                }
                case 46: {
                    return "locationCallbackFinished";
                }
                case 45: {
                    return "sendExtraCommand";
                }
                case 44: {
                    return "setTestProviderStatus";
                }
                case 43: {
                    return "getGnssTimeMillis";
                }
                case 42: {
                    return "getTestProviderCurrentRequests";
                }
                case 41: {
                    return "setTestProviderEnabled";
                }
                case 40: {
                    return "setTestProviderLocation";
                }
                case 39: {
                    return "removeTestProvider";
                }
                case 38: {
                    return "addTestProvider";
                }
                case 37: {
                    return "isLocationEnabledForUser";
                }
                case 36: {
                    return "isProviderEnabledForUser";
                }
                case 35: {
                    return "isExtraLocationControllerPackageEnabled";
                }
                case 34: {
                    return "setExtraLocationControllerPackageEnabled";
                }
                case 33: {
                    return "getExtraLocationControllerPackage";
                }
                case 32: {
                    return "setExtraLocationControllerPackage";
                }
                case 31: {
                    return "isProviderPackage";
                }
                case 30: {
                    return "getProviderProperties";
                }
                case 29: {
                    return "getBestProvider";
                }
                case 28: {
                    return "getProviders";
                }
                case 27: {
                    return "getAllProviders";
                }
                case 26: {
                    return "injectLocation";
                }
                case 25: {
                    return "stopGnssBatch";
                }
                case 24: {
                    return "flushGnssBatch";
                }
                case 23: {
                    return "startGnssBatch";
                }
                case 22: {
                    return "removeGnssBatchingCallback";
                }
                case 21: {
                    return "addGnssBatchingCallback";
                }
                case 20: {
                    return "getGnssBatchSize";
                }
                case 19: {
                    return "getGnssHardwareModelName";
                }
                case 18: {
                    return "getGnssYearOfHardware";
                }
                case 17: {
                    return "removeGnssNavigationMessageListener";
                }
                case 16: {
                    return "addGnssNavigationMessageListener";
                }
                case 15: {
                    return "removeGnssMeasurementsListener";
                }
                case 14: {
                    return "getGnssCapabilities";
                }
                case 13: {
                    return "injectGnssMeasurementCorrections";
                }
                case 12: {
                    return "addGnssMeasurementsListener";
                }
                case 11: {
                    return "sendNiResponse";
                }
                case 10: {
                    return "getFromLocationName";
                }
                case 9: {
                    return "getFromLocation";
                }
                case 8: {
                    return "geocoderIsPresent";
                }
                case 7: {
                    return "unregisterGnssStatusCallback";
                }
                case 6: {
                    return "registerGnssStatusCallback";
                }
                case 5: {
                    return "getLastLocation";
                }
                case 4: {
                    return "removeGeofence";
                }
                case 3: {
                    return "requestGeofence";
                }
                case 2: {
                    return "removeUpdates";
                }
                case 1: 
            }
            return "requestLocationUpdates";
        }

        public static boolean setDefaultImpl(ILocationManager iLocationManager) {
            if (Proxy.sDefaultImpl == null && iLocationManager != null) {
                Proxy.sDefaultImpl = iLocationManager;
                return true;
            }
            return false;
        }

        @Override
        public IBinder asBinder() {
            return this;
        }

        @Override
        public String getTransactionName(int n) {
            return Stub.getDefaultTransactionName(n);
        }

        @Override
        public boolean onTransact(int n, Parcel object, Parcel parcel, int n2) throws RemoteException {
            if (n != 1598968902) {
                boolean bl = false;
                boolean bl2 = false;
                boolean bl3 = false;
                boolean bl4 = false;
                boolean bl5 = false;
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, parcel, n2);
                    }
                    case 48: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getIgnoreSettingsWhitelist();
                        parcel.writeNoException();
                        parcel.writeStringArray((String[])object);
                        return true;
                    }
                    case 47: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getBackgroundThrottlingWhitelist();
                        parcel.writeNoException();
                        parcel.writeStringArray((String[])object);
                        return true;
                    }
                    case 46: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.locationCallbackFinished(ILocationListener.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 45: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string2 = ((Parcel)object).readString();
                        String string3 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.sendExtraCommand(string2, string3, (Bundle)object) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        if (object != null) {
                            parcel.writeInt(1);
                            ((Bundle)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 44: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string4 = ((Parcel)object).readString();
                        n = ((Parcel)object).readInt();
                        Bundle bundle = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setTestProviderStatus(string4, n, bundle, ((Parcel)object).readLong(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 43: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getGnssTimeMillis();
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((LocationTime)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 42: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getTestProviderCurrentRequests(((Parcel)object).readString(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeTypedList(object);
                        return true;
                    }
                    case 41: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string5 = ((Parcel)object).readString();
                        if (((Parcel)object).readInt() != 0) {
                            bl5 = true;
                        }
                        this.setTestProviderEnabled(string5, bl5, ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 40: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string6 = ((Parcel)object).readString();
                        Location location = ((Parcel)object).readInt() != 0 ? Location.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setTestProviderLocation(string6, location, ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 39: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.removeTestProvider(((Parcel)object).readString(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 38: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string7 = ((Parcel)object).readString();
                        ProviderProperties providerProperties = ((Parcel)object).readInt() != 0 ? ProviderProperties.CREATOR.createFromParcel((Parcel)object) : null;
                        this.addTestProvider(string7, providerProperties, ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 37: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isLocationEnabledForUser(((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 36: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isProviderEnabledForUser(((Parcel)object).readString(), ((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 35: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isExtraLocationControllerPackageEnabled() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 34: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl5 = bl;
                        if (((Parcel)object).readInt() != 0) {
                            bl5 = true;
                        }
                        this.setExtraLocationControllerPackageEnabled(bl5);
                        parcel.writeNoException();
                        return true;
                    }
                    case 33: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getExtraLocationControllerPackage();
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 32: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setExtraLocationControllerPackage(((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 31: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isProviderPackage(((Parcel)object).readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 30: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getProviderProperties(((Parcel)object).readString());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((ProviderProperties)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 29: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        Criteria criteria = ((Parcel)object).readInt() != 0 ? Criteria.CREATOR.createFromParcel((Parcel)object) : null;
                        bl5 = bl2;
                        if (((Parcel)object).readInt() != 0) {
                            bl5 = true;
                        }
                        object = this.getBestProvider(criteria, bl5);
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 28: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        Criteria criteria = ((Parcel)object).readInt() != 0 ? Criteria.CREATOR.createFromParcel((Parcel)object) : null;
                        bl5 = bl3;
                        if (((Parcel)object).readInt() != 0) {
                            bl5 = true;
                        }
                        object = this.getProviders(criteria, bl5);
                        parcel.writeNoException();
                        parcel.writeStringList((List<String>)object);
                        return true;
                    }
                    case 27: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getAllProviders();
                        parcel.writeNoException();
                        parcel.writeStringList((List<String>)object);
                        return true;
                    }
                    case 26: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? Location.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.injectLocation((Location)object) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 25: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.stopGnssBatch() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 24: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.flushGnssBatch(((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 23: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        long l = ((Parcel)object).readLong();
                        bl5 = bl4;
                        if (((Parcel)object).readInt() != 0) {
                            bl5 = true;
                        }
                        n = this.startGnssBatch(l, bl5, ((Parcel)object).readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 22: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.removeGnssBatchingCallback();
                        parcel.writeNoException();
                        return true;
                    }
                    case 21: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.addGnssBatchingCallback(IBatchedLocationCallback.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 20: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getGnssBatchSize(((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 19: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getGnssHardwareModelName();
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 18: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getGnssYearOfHardware();
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 17: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.removeGnssNavigationMessageListener(IGnssNavigationMessageListener.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 16: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.addGnssNavigationMessageListener(IGnssNavigationMessageListener.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 15: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.removeGnssMeasurementsListener(IGnssMeasurementsListener.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 14: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        long l = this.getGnssCapabilities(((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeLong(l);
                        return true;
                    }
                    case 13: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        GnssMeasurementCorrections gnssMeasurementCorrections = ((Parcel)object).readInt() != 0 ? GnssMeasurementCorrections.CREATOR.createFromParcel((Parcel)object) : null;
                        this.injectGnssMeasurementCorrections(gnssMeasurementCorrections, ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 12: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.addGnssMeasurementsListener(IGnssMeasurementsListener.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 11: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.sendNiResponse(((Parcel)object).readInt(), ((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string8 = ((Parcel)object).readString();
                        double d = ((Parcel)object).readDouble();
                        double d2 = ((Parcel)object).readDouble();
                        double d3 = ((Parcel)object).readDouble();
                        double d4 = ((Parcel)object).readDouble();
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? GeocoderParams.CREATOR.createFromParcel((Parcel)object) : null;
                        ArrayList<Address> arrayList = new ArrayList<Address>();
                        object = this.getFromLocationName(string8, d, d2, d3, d4, n, (GeocoderParams)object, arrayList);
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        parcel.writeTypedList(arrayList);
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        double d = ((Parcel)object).readDouble();
                        double d5 = ((Parcel)object).readDouble();
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? GeocoderParams.CREATOR.createFromParcel((Parcel)object) : null;
                        ArrayList<Address> arrayList = new ArrayList<Address>();
                        object = this.getFromLocation(d, d5, n, (GeocoderParams)object, arrayList);
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        parcel.writeTypedList(arrayList);
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.geocoderIsPresent() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.unregisterGnssStatusCallback(IGnssStatusListener.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.registerGnssStatusCallback(IGnssStatusListener.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        LocationRequest locationRequest = ((Parcel)object).readInt() != 0 ? LocationRequest.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.getLastLocation(locationRequest, ((Parcel)object).readString());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((Location)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        Geofence geofence = ((Parcel)object).readInt() != 0 ? Geofence.CREATOR.createFromParcel((Parcel)object) : null;
                        PendingIntent pendingIntent = ((Parcel)object).readInt() != 0 ? PendingIntent.CREATOR.createFromParcel((Parcel)object) : null;
                        this.removeGeofence(geofence, pendingIntent, ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        LocationRequest locationRequest = ((Parcel)object).readInt() != 0 ? LocationRequest.CREATOR.createFromParcel((Parcel)object) : null;
                        Geofence geofence = ((Parcel)object).readInt() != 0 ? Geofence.CREATOR.createFromParcel((Parcel)object) : null;
                        PendingIntent pendingIntent = ((Parcel)object).readInt() != 0 ? PendingIntent.CREATOR.createFromParcel((Parcel)object) : null;
                        this.requestGeofence(locationRequest, geofence, pendingIntent, ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ILocationListener iLocationListener = ILocationListener.Stub.asInterface(((Parcel)object).readStrongBinder());
                        PendingIntent pendingIntent = ((Parcel)object).readInt() != 0 ? PendingIntent.CREATOR.createFromParcel((Parcel)object) : null;
                        this.removeUpdates(iLocationListener, pendingIntent, ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                LocationRequest locationRequest = ((Parcel)object).readInt() != 0 ? LocationRequest.CREATOR.createFromParcel((Parcel)object) : null;
                ILocationListener iLocationListener = ILocationListener.Stub.asInterface(((Parcel)object).readStrongBinder());
                PendingIntent pendingIntent = ((Parcel)object).readInt() != 0 ? PendingIntent.CREATOR.createFromParcel((Parcel)object) : null;
                this.requestLocationUpdates(locationRequest, iLocationListener, pendingIntent, ((Parcel)object).readString());
                parcel.writeNoException();
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements ILocationManager {
            public static ILocationManager sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public boolean addGnssBatchingCallback(IBatchedLocationCallback iBatchedLocationCallback, String string2) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block9 : {
                    IBinder iBinder;
                    block8 : {
                        block7 : {
                            parcel2 = Parcel.obtain();
                            parcel = Parcel.obtain();
                            try {
                                parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                                if (iBatchedLocationCallback == null) break block7;
                            }
                            catch (Throwable throwable) {
                                parcel.recycle();
                                parcel2.recycle();
                                throw throwable;
                            }
                            iBinder = iBatchedLocationCallback.asBinder();
                            break block8;
                        }
                        iBinder = null;
                    }
                    parcel2.writeStrongBinder(iBinder);
                    parcel2.writeString(string2);
                    iBinder = this.mRemote;
                    bl = false;
                    if (iBinder.transact(21, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block9;
                    bl = Stub.getDefaultImpl().addGnssBatchingCallback(iBatchedLocationCallback, string2);
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                int n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public boolean addGnssMeasurementsListener(IGnssMeasurementsListener iGnssMeasurementsListener, String string2) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block9 : {
                    IBinder iBinder;
                    block8 : {
                        block7 : {
                            parcel2 = Parcel.obtain();
                            parcel = Parcel.obtain();
                            try {
                                parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                                if (iGnssMeasurementsListener == null) break block7;
                            }
                            catch (Throwable throwable) {
                                parcel.recycle();
                                parcel2.recycle();
                                throw throwable;
                            }
                            iBinder = iGnssMeasurementsListener.asBinder();
                            break block8;
                        }
                        iBinder = null;
                    }
                    parcel2.writeStrongBinder(iBinder);
                    parcel2.writeString(string2);
                    iBinder = this.mRemote;
                    bl = false;
                    if (iBinder.transact(12, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block9;
                    bl = Stub.getDefaultImpl().addGnssMeasurementsListener(iGnssMeasurementsListener, string2);
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                int n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public boolean addGnssNavigationMessageListener(IGnssNavigationMessageListener iGnssNavigationMessageListener, String string2) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block9 : {
                    IBinder iBinder;
                    block8 : {
                        block7 : {
                            parcel2 = Parcel.obtain();
                            parcel = Parcel.obtain();
                            try {
                                parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                                if (iGnssNavigationMessageListener == null) break block7;
                            }
                            catch (Throwable throwable) {
                                parcel.recycle();
                                parcel2.recycle();
                                throw throwable;
                            }
                            iBinder = iGnssNavigationMessageListener.asBinder();
                            break block8;
                        }
                        iBinder = null;
                    }
                    parcel2.writeStrongBinder(iBinder);
                    parcel2.writeString(string2);
                    iBinder = this.mRemote;
                    bl = false;
                    if (iBinder.transact(16, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block9;
                    bl = Stub.getDefaultImpl().addGnssNavigationMessageListener(iGnssNavigationMessageListener, string2);
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                int n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public void addTestProvider(String string2, ProviderProperties providerProperties, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (providerProperties != null) {
                        parcel.writeInt(1);
                        providerProperties.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(38, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addTestProvider(string2, providerProperties, string3);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void flushGnssBatch(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(24, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().flushGnssBatch(string2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean geocoderIsPresent() throws RemoteException {
                boolean bl;
                Parcel parcel;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(8, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().geocoderIsPresent();
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                int n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public List<String> getAllProviders() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(27, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        List<String> list = Stub.getDefaultImpl().getAllProviders();
                        return list;
                    }
                    parcel2.readException();
                    ArrayList<String> arrayList = parcel2.createStringArrayList();
                    return arrayList;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String[] getBackgroundThrottlingWhitelist() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(47, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String[] arrstring = Stub.getDefaultImpl().getBackgroundThrottlingWhitelist();
                        return arrstring;
                    }
                    parcel2.readException();
                    String[] arrstring = parcel2.createStringArray();
                    return arrstring;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public String getBestProvider(Criteria object, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n = 1;
                    if (object != null) {
                        parcel.writeInt(1);
                        ((Criteria)object).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!bl) {
                        n = 0;
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(29, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        object = Stub.getDefaultImpl().getBestProvider((Criteria)object, bl);
                        return object;
                    }
                    parcel2.readException();
                    object = parcel2.readString();
                    return object;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String getExtraLocationControllerPackage() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(33, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String string2 = Stub.getDefaultImpl().getExtraLocationControllerPackage();
                        return string2;
                    }
                    parcel2.readException();
                    String string3 = parcel2.readString();
                    return string3;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Loose catch block
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public String getFromLocation(double d, double d2, int n, GeocoderParams object, List<Address> list) throws RemoteException {
                void var6_9;
                Parcel parcel2;
                Parcel parcel;
                block12 : {
                    String string2;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel2.writeDouble(d);
                    }
                    catch (Throwable throwable) {
                        break block12;
                    }
                    try {
                        parcel2.writeDouble(d2);
                        parcel2.writeInt(n);
                        if (object != null) {
                            parcel2.writeInt(1);
                            ((GeocoderParams)object).writeToParcel(parcel2, 0);
                        } else {
                            parcel2.writeInt(0);
                        }
                        if (!this.mRemote.transact(9, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                            object = Stub.getDefaultImpl().getFromLocation(d, d2, n, (GeocoderParams)object, list);
                            parcel.recycle();
                            parcel2.recycle();
                            return object;
                        }
                        parcel.readException();
                        string2 = parcel.readString();
                        object = Address.CREATOR;
                    }
                    catch (Throwable throwable) {}
                    try {
                        parcel.readTypedList(list, object);
                        parcel.recycle();
                        parcel2.recycle();
                        return string2;
                    }
                    catch (Throwable throwable) {}
                    break block12;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                throw var6_9;
            }

            /*
             * Unable to fully structure code
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public String getFromLocationName(String var1_1, double var2_9, double var4_10, double var6_11, double var8_12, int var10_13, GeocoderParams var11_14, List<Address> var12_15) throws RemoteException {
                block14 : {
                    block15 : {
                        block13 : {
                            var13_16 = Parcel.obtain();
                            var14_17 = Parcel.obtain();
                            var13_16.writeInterfaceToken("android.location.ILocationManager");
                            var13_16.writeString((String)var1_1);
                            var13_16.writeDouble(var2_9);
                            var13_16.writeDouble(var4_10);
                            var13_16.writeDouble(var6_11);
                            var13_16.writeDouble(var8_12);
                            var13_16.writeInt(var10_13);
                            if (var11_14 == null) break block13;
                            try {
                                var13_16.writeInt(1);
                                var11_14.writeToParcel(var13_16, 0);
                                ** GOTO lbl21
                            }
                            catch (Throwable var1_2) {
                                break block14;
                            }
                        }
                        var13_16.writeInt(0);
lbl21: // 2 sources:
                        var15_18 = this.mRemote.transact(10, var13_16, var14_17, 0);
                        if (var15_18) break block15;
                        try {
                            if (Stub.getDefaultImpl() == null) break block15;
                            var16_19 = Stub.getDefaultImpl();
                        }
                        catch (Throwable var1_4) {
                            break block14;
                        }
                        try {
                            var1_1 = var16_19.getFromLocationName((String)var1_1, var2_9, var4_10, var6_11, var8_12, var10_13, (GeocoderParams)var11_14, var12_15);
                            var14_17.recycle();
                            var13_16.recycle();
                            return var1_1;
                        }
                        catch (Throwable var1_3) {}
                        break block14;
                    }
                    try {
                        var14_17.readException();
                        var11_14 = var14_17.readString();
                        var16_20 = Address.CREATOR;
                        var1_1 = var14_17;
                    }
                    catch (Throwable var1_6) {}
                    try {
                        var1_1.readTypedList(var12_15, var16_20);
                        var1_1.recycle();
                        var13_16.recycle();
                        return var11_14;
                    }
                    catch (Throwable var1_5) {}
                    break block14;
                    catch (Throwable var1_7) {
                        // empty catch block
                    }
                }
                var14_17.recycle();
                var13_16.recycle();
                throw var1_8;
            }

            @Override
            public int getGnssBatchSize(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(20, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getGnssBatchSize(string2);
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public long getGnssCapabilities(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(14, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        long l = Stub.getDefaultImpl().getGnssCapabilities(string2);
                        return l;
                    }
                    parcel2.readException();
                    long l = parcel2.readLong();
                    return l;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String getGnssHardwareModelName() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(19, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String string2 = Stub.getDefaultImpl().getGnssHardwareModelName();
                        return string2;
                    }
                    parcel2.readException();
                    String string3 = parcel2.readString();
                    return string3;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public LocationTime getGnssTimeMillis() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(43, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        LocationTime locationTime = Stub.getDefaultImpl().getGnssTimeMillis();
                        parcel.recycle();
                        parcel2.recycle();
                        return locationTime;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                LocationTime locationTime = parcel.readInt() != 0 ? LocationTime.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return locationTime;
            }

            @Override
            public int getGnssYearOfHardware() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(18, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getGnssYearOfHardware();
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String[] getIgnoreSettingsWhitelist() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(48, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String[] arrstring = Stub.getDefaultImpl().getIgnoreSettingsWhitelist();
                        return arrstring;
                    }
                    parcel2.readException();
                    String[] arrstring = parcel2.createStringArray();
                    return arrstring;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            /*
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public Location getLastLocation(LocationRequest parcelable, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    void var2_7;
                    void var1_5;
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelable != null) {
                        parcel.writeInt(1);
                        ((LocationRequest)parcelable).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString((String)var2_7);
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Location location = Stub.getDefaultImpl().getLastLocation((LocationRequest)parcelable, (String)var2_7);
                        parcel2.recycle();
                        parcel.recycle();
                        return location;
                    }
                    parcel2.readException();
                    if (parcel2.readInt() != 0) {
                        Location location = Location.CREATOR.createFromParcel(parcel2);
                    } else {
                        Object var1_4 = null;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return var1_5;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            @Override
            public ProviderProperties getProviderProperties(String object) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString((String)object);
                        if (this.mRemote.transact(30, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        object = Stub.getDefaultImpl().getProviderProperties((String)object);
                        parcel2.recycle();
                        parcel.recycle();
                        return object;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.readException();
                object = parcel2.readInt() != 0 ? ProviderProperties.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return object;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public List<String> getProviders(Criteria list, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n = 1;
                    if (list != null) {
                        parcel.writeInt(1);
                        ((Criteria)((Object)list)).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!bl) {
                        n = 0;
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(28, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        list = Stub.getDefaultImpl().getProviders((Criteria)((Object)list), bl);
                        return list;
                    }
                    parcel2.readException();
                    list = parcel2.createStringArrayList();
                    return list;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public List<LocationRequest> getTestProviderCurrentRequests(String arrayList, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString((String)((Object)arrayList));
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(42, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        arrayList = Stub.getDefaultImpl().getTestProviderCurrentRequests((String)((Object)arrayList), string2);
                        return arrayList;
                    }
                    parcel2.readException();
                    arrayList = parcel2.createTypedArrayList(LocationRequest.CREATOR);
                    return arrayList;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void injectGnssMeasurementCorrections(GnssMeasurementCorrections gnssMeasurementCorrections, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (gnssMeasurementCorrections != null) {
                        parcel.writeInt(1);
                        gnssMeasurementCorrections.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(13, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().injectGnssMeasurementCorrections(gnssMeasurementCorrections, string2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public boolean injectLocation(Location location) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean bl = true;
                    if (location != null) {
                        parcel.writeInt(1);
                        location.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(26, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().injectLocation(location);
                        parcel2.recycle();
                        parcel.recycle();
                        return bl;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    if (n == 0) {
                        bl = false;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            @Override
            public boolean isExtraLocationControllerPackageEnabled() throws RemoteException {
                boolean bl;
                Parcel parcel;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(35, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isExtraLocationControllerPackageEnabled();
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                int n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public boolean isLocationEnabledForUser(int n) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(37, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isLocationEnabledForUser(n);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public boolean isProviderEnabledForUser(String string2, int n) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeString(string2);
                        parcel2.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(36, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isProviderEnabledForUser(string2, n);
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public boolean isProviderPackage(String string2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString(string2);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(31, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isProviderPackage(string2);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                int n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void locationCallbackFinished(ILocationListener iLocationListener) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iLocationListener != null ? iLocationListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(46, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().locationCallbackFinished(iLocationListener);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean registerGnssStatusCallback(IGnssStatusListener iGnssStatusListener, String string2) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block9 : {
                    IBinder iBinder;
                    block8 : {
                        block7 : {
                            parcel2 = Parcel.obtain();
                            parcel = Parcel.obtain();
                            try {
                                parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                                if (iGnssStatusListener == null) break block7;
                            }
                            catch (Throwable throwable) {
                                parcel.recycle();
                                parcel2.recycle();
                                throw throwable;
                            }
                            iBinder = iGnssStatusListener.asBinder();
                            break block8;
                        }
                        iBinder = null;
                    }
                    parcel2.writeStrongBinder(iBinder);
                    parcel2.writeString(string2);
                    iBinder = this.mRemote;
                    bl = false;
                    if (iBinder.transact(6, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block9;
                    bl = Stub.getDefaultImpl().registerGnssStatusCallback(iGnssStatusListener, string2);
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                int n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public void removeGeofence(Geofence geofence, PendingIntent pendingIntent, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (geofence != null) {
                        parcel.writeInt(1);
                        geofence.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (pendingIntent != null) {
                        parcel.writeInt(1);
                        pendingIntent.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeGeofence(geofence, pendingIntent, string2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void removeGnssBatchingCallback() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(22, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeGnssBatchingCallback();
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void removeGnssMeasurementsListener(IGnssMeasurementsListener iGnssMeasurementsListener) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iGnssMeasurementsListener != null ? iGnssMeasurementsListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(15, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeGnssMeasurementsListener(iGnssMeasurementsListener);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void removeGnssNavigationMessageListener(IGnssNavigationMessageListener iGnssNavigationMessageListener) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iGnssNavigationMessageListener != null ? iGnssNavigationMessageListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(17, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeGnssNavigationMessageListener(iGnssNavigationMessageListener);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void removeTestProvider(String string2, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(39, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeTestProvider(string2, string3);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void removeUpdates(ILocationListener iLocationListener, PendingIntent pendingIntent, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iLocationListener != null ? iLocationListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (pendingIntent != null) {
                        parcel.writeInt(1);
                        pendingIntent.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeUpdates(iLocationListener, pendingIntent, string2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void requestGeofence(LocationRequest locationRequest, Geofence geofence, PendingIntent pendingIntent, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (locationRequest != null) {
                        parcel.writeInt(1);
                        locationRequest.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (geofence != null) {
                        parcel.writeInt(1);
                        geofence.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (pendingIntent != null) {
                        parcel.writeInt(1);
                        pendingIntent.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().requestGeofence(locationRequest, geofence, pendingIntent, string2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void requestLocationUpdates(LocationRequest locationRequest, ILocationListener iLocationListener, PendingIntent pendingIntent, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (locationRequest != null) {
                        parcel.writeInt(1);
                        locationRequest.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    IBinder iBinder = iLocationListener != null ? iLocationListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (pendingIntent != null) {
                        parcel.writeInt(1);
                        pendingIntent.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().requestLocationUpdates(locationRequest, iLocationListener, pendingIntent, string2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public boolean sendExtraCommand(String string2, String string3, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    boolean bl = true;
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(45, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().sendExtraCommand(string2, string3, bundle);
                        return bl;
                    }
                    parcel2.readException();
                    if (parcel2.readInt() == 0) {
                        bl = false;
                    }
                    if (parcel2.readInt() == 0) return bl;
                    bundle.readFromParcel(parcel2);
                    return bl;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean sendNiResponse(int n, int n2) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeInt(n);
                        parcel2.writeInt(n2);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(11, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().sendNiResponse(n, n2);
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public void setExtraLocationControllerPackage(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(32, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setExtraLocationControllerPackage(string2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setExtraLocationControllerPackageEnabled(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(34, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setExtraLocationControllerPackageEnabled(bl);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setTestProviderEnabled(String string2, boolean bl, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeString(string2);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(41, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setTestProviderEnabled(string2, bl, string3);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setTestProviderLocation(String string2, Location location, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (location != null) {
                        parcel.writeInt(1);
                        location.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(40, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setTestProviderLocation(string2, location, string3);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Loose catch block
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public void setTestProviderStatus(String string2, int n, Bundle bundle, long l, String string3) throws RemoteException {
                Parcel parcel;
                void var1_7;
                Parcel parcel2;
                block14 : {
                    block13 : {
                        parcel2 = Parcel.obtain();
                        parcel = Parcel.obtain();
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        try {
                            parcel2.writeString(string2);
                        }
                        catch (Throwable throwable) {
                            break block14;
                        }
                        try {
                            parcel2.writeInt(n);
                            if (bundle != null) {
                                parcel2.writeInt(1);
                                bundle.writeToParcel(parcel2, 0);
                                break block13;
                            }
                            parcel2.writeInt(0);
                        }
                        catch (Throwable throwable) {}
                    }
                    try {
                        parcel2.writeLong(l);
                    }
                    catch (Throwable throwable) {
                        break block14;
                    }
                    try {
                        parcel2.writeString(string3);
                        if (!this.mRemote.transact(44, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().setTestProviderStatus(string2, n, bundle, l, string3);
                            parcel.recycle();
                            parcel2.recycle();
                            return;
                        }
                        parcel.readException();
                        parcel.recycle();
                        parcel2.recycle();
                        return;
                    }
                    catch (Throwable throwable) {}
                    break block14;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                throw var1_7;
            }

            @Override
            public boolean startGnssBatch(long l, boolean bl, String string2) throws RemoteException {
                int n;
                boolean bl2;
                Parcel parcel;
                Parcel parcel2;
                block4 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeLong(l);
                        bl2 = true;
                        n = bl ? 1 : 0;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (this.mRemote.transact(23, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block4;
                    bl = Stub.getDefaultImpl().startGnssBatch(l, bl, string2);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                n = parcel2.readInt();
                bl = n != 0 ? bl2 : false;
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public boolean stopGnssBatch() throws RemoteException {
                boolean bl;
                Parcel parcel;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(25, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().stopGnssBatch();
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                int n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void unregisterGnssStatusCallback(IGnssStatusListener iGnssStatusListener) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iGnssStatusListener != null ? iGnssStatusListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(7, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unregisterGnssStatusCallback(iGnssStatusListener);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }
        }

    }

}

