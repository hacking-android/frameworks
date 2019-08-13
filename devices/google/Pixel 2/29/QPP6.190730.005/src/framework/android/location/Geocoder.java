/*
 * Decompiled with CFR 0.145.
 */
package android.location;

import android.content.Context;
import android.location.Address;
import android.location.GeocoderParams;
import android.location.ILocationManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public final class Geocoder {
    private static final String TAG = "Geocoder";
    private GeocoderParams mParams;
    private ILocationManager mService;

    public Geocoder(Context context) {
        this(context, Locale.getDefault());
    }

    public Geocoder(Context context, Locale locale) {
        if (locale != null) {
            this.mParams = new GeocoderParams(context, locale);
            this.mService = ILocationManager.Stub.asInterface(ServiceManager.getService("location"));
            return;
        }
        throw new NullPointerException("locale == null");
    }

    public static boolean isPresent() {
        ILocationManager iLocationManager = ILocationManager.Stub.asInterface(ServiceManager.getService("location"));
        try {
            boolean bl = iLocationManager.geocoderIsPresent();
            return bl;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "isPresent: got RemoteException", remoteException);
            return false;
        }
    }

    public List<Address> getFromLocation(double d, double d2, int n) throws IOException {
        if (!(d < -90.0) && !(d > 90.0)) {
            if (!(d2 < -180.0) && !(d2 > 180.0)) {
                Serializable serializable;
                String string2;
                block5 : {
                    try {
                        serializable = new Serializable();
                        string2 = this.mService.getFromLocation(d, d2, n, this.mParams, (List<Address>)((Object)serializable));
                        if (string2 != null) break block5;
                        return serializable;
                    }
                    catch (RemoteException remoteException) {
                        Log.e(TAG, "getFromLocation: got RemoteException", remoteException);
                        return null;
                    }
                }
                serializable = new Serializable(string2);
                throw serializable;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("longitude == ");
            stringBuilder.append(d2);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("latitude == ");
        stringBuilder.append(d);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public List<Address> getFromLocationName(String string2, int n) throws IOException {
        if (string2 != null) {
            Serializable serializable;
            block4 : {
                try {
                    serializable = new Serializable();
                    string2 = this.mService.getFromLocationName(string2, 0.0, 0.0, 0.0, 0.0, n, this.mParams, (List<Address>)((Object)serializable));
                    if (string2 != null) break block4;
                    return serializable;
                }
                catch (RemoteException remoteException) {
                    Log.e(TAG, "getFromLocationName: got RemoteException", remoteException);
                    return null;
                }
            }
            serializable = new Serializable(string2);
            throw serializable;
        }
        throw new IllegalArgumentException("locationName == null");
    }

    public List<Address> getFromLocationName(String charSequence, int n, double d, double d2, double d3, double d4) throws IOException {
        if (charSequence != null) {
            if (!(d < -90.0) && !(d > 90.0)) {
                if (!(d2 < -180.0) && !(d2 > 180.0)) {
                    if (!(d3 < -90.0) && !(d3 > 90.0)) {
                        if (!(d4 < -180.0) && !(d4 > 180.0)) {
                            Serializable serializable;
                            block8 : {
                                try {
                                    serializable = new Serializable();
                                    charSequence = this.mService.getFromLocationName((String)charSequence, d, d2, d3, d4, n, this.mParams, (List<Address>)((Object)serializable));
                                    if (charSequence != null) break block8;
                                    return serializable;
                                }
                                catch (RemoteException remoteException) {
                                    Log.e(TAG, "getFromLocationName: got RemoteException", remoteException);
                                    return null;
                                }
                            }
                            serializable = new Serializable((String)charSequence);
                            throw serializable;
                        }
                        charSequence = new StringBuilder();
                        ((StringBuilder)charSequence).append("upperRightLongitude == ");
                        ((StringBuilder)charSequence).append(d4);
                        throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
                    }
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("upperRightLatitude == ");
                    ((StringBuilder)charSequence).append(d3);
                    throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
                }
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("lowerLeftLongitude == ");
                ((StringBuilder)charSequence).append(d2);
                throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("lowerLeftLatitude == ");
            ((StringBuilder)charSequence).append(d);
            throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
        }
        throw new IllegalArgumentException("locationName == null");
    }
}

