/*
 * Decompiled with CFR 0.145.
 */
package android.location;

import android.annotation.UnsupportedAppUsage;
import android.location.Country;
import android.location.CountryListener;
import android.location.ICountryDetector;
import android.location.ICountryListener;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;
import java.util.HashMap;

public class CountryDetector {
    private static final String TAG = "CountryDetector";
    private final HashMap<CountryListener, ListenerTransport> mListeners;
    private final ICountryDetector mService;

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public CountryDetector(ICountryDetector iCountryDetector) {
        this.mService = iCountryDetector;
        this.mListeners = new HashMap();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public void addCountryListener(CountryListener countryListener, Looper looper) {
        HashMap<CountryListener, ListenerTransport> hashMap = this.mListeners;
        synchronized (hashMap) {
            if (!this.mListeners.containsKey(countryListener)) {
                ListenerTransport listenerTransport = new ListenerTransport(countryListener, looper);
                try {
                    this.mService.addCountryListener(listenerTransport);
                    this.mListeners.put(countryListener, listenerTransport);
                }
                catch (RemoteException remoteException) {
                    Log.e(TAG, "addCountryListener: RemoteException", remoteException);
                }
            }
            return;
        }
    }

    @UnsupportedAppUsage
    public Country detectCountry() {
        try {
            Country country = this.mService.detectCountry();
            return country;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "detectCountry: RemoteException", remoteException);
            return null;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public void removeCountryListener(CountryListener countryListener) {
        HashMap<CountryListener, ListenerTransport> hashMap = this.mListeners;
        synchronized (hashMap) {
            ListenerTransport listenerTransport = this.mListeners.get(countryListener);
            if (listenerTransport != null) {
                try {
                    this.mListeners.remove(countryListener);
                    this.mService.removeCountryListener(listenerTransport);
                }
                catch (RemoteException remoteException) {
                    Log.e(TAG, "removeCountryListener: RemoteException", remoteException);
                }
            }
            return;
        }
    }

    private static final class ListenerTransport
    extends ICountryListener.Stub {
        private final Handler mHandler;
        private final CountryListener mListener;

        public ListenerTransport(CountryListener countryListener, Looper looper) {
            this.mListener = countryListener;
            this.mHandler = looper != null ? new Handler(looper) : new Handler();
        }

        @Override
        public void onCountryDetected(final Country country) {
            this.mHandler.post(new Runnable(){

                @Override
                public void run() {
                    ListenerTransport.this.mListener.onCountryDetected(country);
                }
            });
        }

    }

}

