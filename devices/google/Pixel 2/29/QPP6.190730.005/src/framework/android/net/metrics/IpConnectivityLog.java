/*
 * Decompiled with CFR 0.145.
 */
package android.net.metrics;

import android.annotation.SystemApi;
import android.net.ConnectivityMetricsEvent;
import android.net.IIpConnectivityMetrics;
import android.net.Network;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.BitUtils;

@SystemApi
public class IpConnectivityLog {
    private static final boolean DBG = false;
    public static final String SERVICE_NAME = "connmetrics";
    private static final String TAG = IpConnectivityLog.class.getSimpleName();
    private IIpConnectivityMetrics mService;

    @SystemApi
    public IpConnectivityLog() {
    }

    @VisibleForTesting
    public IpConnectivityLog(IIpConnectivityMetrics iIpConnectivityMetrics) {
        this.mService = iIpConnectivityMetrics;
    }

    private boolean checkLoggerService() {
        if (this.mService != null) {
            return true;
        }
        IIpConnectivityMetrics iIpConnectivityMetrics = IIpConnectivityMetrics.Stub.asInterface(ServiceManager.getService(SERVICE_NAME));
        if (iIpConnectivityMetrics == null) {
            return false;
        }
        this.mService = iIpConnectivityMetrics;
        return true;
    }

    private static ConnectivityMetricsEvent makeEv(Event event) {
        ConnectivityMetricsEvent connectivityMetricsEvent = new ConnectivityMetricsEvent();
        connectivityMetricsEvent.data = event;
        return connectivityMetricsEvent;
    }

    public boolean log(int n, int[] arrn, Event parcelable) {
        parcelable = IpConnectivityLog.makeEv(parcelable);
        ((ConnectivityMetricsEvent)parcelable).netId = n;
        ((ConnectivityMetricsEvent)parcelable).transports = BitUtils.packBits(arrn);
        return this.log((ConnectivityMetricsEvent)parcelable);
    }

    public boolean log(long l, Event parcelable) {
        parcelable = IpConnectivityLog.makeEv(parcelable);
        ((ConnectivityMetricsEvent)parcelable).timestamp = l;
        return this.log((ConnectivityMetricsEvent)parcelable);
    }

    public boolean log(ConnectivityMetricsEvent connectivityMetricsEvent) {
        boolean bl = this.checkLoggerService();
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        if (connectivityMetricsEvent.timestamp == 0L) {
            connectivityMetricsEvent.timestamp = System.currentTimeMillis();
        }
        try {
            int n = this.mService.logEvent(connectivityMetricsEvent);
            if (n >= 0) {
                bl2 = true;
            }
            return bl2;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "Error logging event", remoteException);
            return false;
        }
    }

    public boolean log(Network network, int[] arrn, Event event) {
        return this.log(network.netId, arrn, event);
    }

    public boolean log(Event event) {
        return this.log(IpConnectivityLog.makeEv(event));
    }

    public boolean log(String string2, Event parcelable) {
        parcelable = IpConnectivityLog.makeEv(parcelable);
        ((ConnectivityMetricsEvent)parcelable).ifname = string2;
        return this.log((ConnectivityMetricsEvent)parcelable);
    }

    public static interface Event
    extends Parcelable {
    }

}

