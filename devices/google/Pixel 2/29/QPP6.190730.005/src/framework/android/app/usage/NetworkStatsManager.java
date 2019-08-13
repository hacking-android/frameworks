/*
 * Decompiled with CFR 0.145.
 */
package android.app.usage;

import android.annotation.UnsupportedAppUsage;
import android.app.usage.NetworkStats;
import android.content.Context;
import android.net.DataUsageRequest;
import android.net.INetworkStatsService;
import android.net.NetworkIdentity;
import android.net.NetworkTemplate;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.DataUnit;
import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.Preconditions;

public class NetworkStatsManager {
    public static final int CALLBACK_LIMIT_REACHED = 0;
    public static final int CALLBACK_RELEASED = 1;
    private static final boolean DBG = false;
    public static final int FLAG_AUGMENT_WITH_SUBSCRIPTION_PLAN = 4;
    public static final int FLAG_POLL_FORCE = 2;
    public static final int FLAG_POLL_ON_OPEN = 1;
    public static final long MIN_THRESHOLD_BYTES = DataUnit.MEBIBYTES.toBytes(2L);
    private static final String TAG = "NetworkStatsManager";
    private final Context mContext;
    private int mFlags;
    private final INetworkStatsService mService;

    @UnsupportedAppUsage
    public NetworkStatsManager(Context context) throws ServiceManager.ServiceNotFoundException {
        this(context, INetworkStatsService.Stub.asInterface(ServiceManager.getServiceOrThrow("netstats")));
    }

    @VisibleForTesting
    public NetworkStatsManager(Context context, INetworkStatsService iNetworkStatsService) {
        this.mContext = context;
        this.mService = iNetworkStatsService;
        this.setPollOnOpen(true);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static NetworkTemplate createTemplate(int n, String object) {
        if (n != 0) {
            if (n == 1) {
                return NetworkTemplate.buildTemplateWifiWildcard();
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Cannot create template for network type ");
            stringBuilder.append(n);
            stringBuilder.append(", subscriberId '");
            stringBuilder.append(NetworkIdentity.scrubSubscriberId((String)object));
            stringBuilder.append("'.");
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        if (object != null) return NetworkTemplate.buildTemplateMobileAll((String)object);
        return NetworkTemplate.buildTemplateMobileWildcard();
    }

    public NetworkStats queryDetails(int n, String object, long l, long l2) throws SecurityException, RemoteException {
        try {
            object = NetworkStatsManager.createTemplate(n, (String)object);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            return null;
        }
        object = new NetworkStats(this.mContext, (NetworkTemplate)object, this.mFlags, l, l2, this.mService);
        ((NetworkStats)object).startUserUidEnumeration();
        return object;
    }

    public NetworkStats queryDetailsForUid(int n, String string2, long l, long l2, int n2) throws SecurityException {
        return this.queryDetailsForUidTagState(n, string2, l, l2, n2, 0, -1);
    }

    public NetworkStats queryDetailsForUid(NetworkTemplate networkTemplate, long l, long l2, int n) throws SecurityException {
        return this.queryDetailsForUidTagState(networkTemplate, l, l2, n, 0, -1);
    }

    public NetworkStats queryDetailsForUidTag(int n, String string2, long l, long l2, int n2, int n3) throws SecurityException {
        return this.queryDetailsForUidTagState(n, string2, l, l2, n2, n3, -1);
    }

    public NetworkStats queryDetailsForUidTagState(int n, String string2, long l, long l2, int n2, int n3, int n4) throws SecurityException {
        return this.queryDetailsForUidTagState(NetworkStatsManager.createTemplate(n, string2), l, l2, n2, n3, n4);
    }

    public NetworkStats queryDetailsForUidTagState(NetworkTemplate object, long l, long l2, int n, int n2, int n3) throws SecurityException {
        try {
            NetworkStats networkStats = new NetworkStats(this.mContext, (NetworkTemplate)object, this.mFlags, l, l2, this.mService);
            networkStats.startHistoryEnumeration(n, n2, n3);
            return networkStats;
        }
        catch (RemoteException remoteException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Error while querying stats for uid=");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append(" tag=");
            ((StringBuilder)object).append(n2);
            ((StringBuilder)object).append(" state=");
            ((StringBuilder)object).append(n3);
            Log.e(TAG, ((StringBuilder)object).toString(), remoteException);
            return null;
        }
    }

    public NetworkStats querySummary(int n, String object, long l, long l2) throws SecurityException, RemoteException {
        try {
            object = NetworkStatsManager.createTemplate(n, (String)object);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            return null;
        }
        return this.querySummary((NetworkTemplate)object, l, l2);
    }

    public NetworkStats querySummary(NetworkTemplate object, long l, long l2) throws SecurityException, RemoteException {
        object = new NetworkStats(this.mContext, (NetworkTemplate)object, this.mFlags, l, l2, this.mService);
        ((NetworkStats)object).startSummaryEnumeration();
        return object;
    }

    public NetworkStats.Bucket querySummaryForDevice(int n, String object, long l, long l2) throws SecurityException, RemoteException {
        try {
            object = NetworkStatsManager.createTemplate(n, (String)object);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            return null;
        }
        return this.querySummaryForDevice((NetworkTemplate)object, l, l2);
    }

    public NetworkStats.Bucket querySummaryForDevice(NetworkTemplate object, long l, long l2) throws SecurityException, RemoteException {
        object = new NetworkStats(this.mContext, (NetworkTemplate)object, this.mFlags, l, l2, this.mService);
        NetworkStats.Bucket bucket = ((NetworkStats)object).getDeviceSummaryForNetwork();
        ((NetworkStats)object).close();
        return bucket;
    }

    public NetworkStats.Bucket querySummaryForUser(int n, String object, long l, long l2) throws SecurityException, RemoteException {
        try {
            object = NetworkStatsManager.createTemplate(n, (String)object);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            return null;
        }
        object = new NetworkStats(this.mContext, (NetworkTemplate)object, this.mFlags, l, l2, this.mService);
        ((NetworkStats)object).startSummaryEnumeration();
        ((NetworkStats)object).close();
        return ((NetworkStats)object).getSummaryAggregate();
    }

    public void registerUsageCallback(int n, String string2, long l, UsageCallback usageCallback) {
        this.registerUsageCallback(n, string2, l, usageCallback, null);
    }

    public void registerUsageCallback(int n, String string2, long l, UsageCallback usageCallback, Handler handler) {
        this.registerUsageCallback(NetworkStatsManager.createTemplate(n, string2), n, l, usageCallback, handler);
    }

    public void registerUsageCallback(NetworkTemplate object, int n, long l, UsageCallback usageCallback, Handler object2) {
        Preconditions.checkNotNull(usageCallback, "UsageCallback cannot be null");
        object2 = object2 == null ? Looper.myLooper() : ((Handler)object2).getLooper();
        DataUsageRequest dataUsageRequest = new DataUsageRequest(0, (NetworkTemplate)object, l);
        try {
            Object object3 = new CallbackHandler((Looper)object2, n, ((NetworkTemplate)object).getSubscriberId(), usageCallback);
            INetworkStatsService iNetworkStatsService = this.mService;
            object = this.mContext.getOpPackageName();
            object2 = new Messenger((Handler)object3);
            object3 = new Binder();
            usageCallback.request = iNetworkStatsService.registerUsageCallback((String)object, dataUsageRequest, (Messenger)object2, (IBinder)object3);
            if (usageCallback.request == null) {
                Log.e(TAG, "Request from callback is null; should not happen");
            }
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void setAugmentWithSubscriptionPlan(boolean bl) {
        this.mFlags = bl ? (this.mFlags |= 4) : (this.mFlags &= -5);
    }

    public void setPollForce(boolean bl) {
        this.mFlags = bl ? (this.mFlags |= 2) : (this.mFlags &= -3);
    }

    public void setPollOnOpen(boolean bl) {
        this.mFlags = bl ? (this.mFlags |= 1) : (this.mFlags &= -2);
    }

    public void unregisterUsageCallback(UsageCallback usageCallback) {
        if (usageCallback != null && usageCallback.request != null && UsageCallback.access$000((UsageCallback)usageCallback).requestId != 0) {
            try {
                this.mService.unregisterUsageRequest(usageCallback.request);
                return;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        throw new IllegalArgumentException("Invalid UsageCallback");
    }

    private static class CallbackHandler
    extends Handler {
        private UsageCallback mCallback;
        private final int mNetworkType;
        private final String mSubscriberId;

        CallbackHandler(Looper looper, int n, String string2, UsageCallback usageCallback) {
            super(looper);
            this.mNetworkType = n;
            this.mSubscriberId = string2;
            this.mCallback = usageCallback;
        }

        private static Object getObject(Message message, String string2) {
            return message.getData().getParcelable(string2);
        }

        @Override
        public void handleMessage(Message object) {
            DataUsageRequest dataUsageRequest = (DataUsageRequest)CallbackHandler.getObject((Message)object, "DataUsageRequest");
            int n = ((Message)object).what;
            if (n != 0) {
                if (n == 1) {
                    this.mCallback = null;
                }
            } else {
                object = this.mCallback;
                if (object != null) {
                    ((UsageCallback)object).onThresholdReached(this.mNetworkType, this.mSubscriberId);
                } else {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("limit reached with released callback for ");
                    ((StringBuilder)object).append(dataUsageRequest);
                    Log.e(NetworkStatsManager.TAG, ((StringBuilder)object).toString());
                }
            }
        }
    }

    public static abstract class UsageCallback {
        private DataUsageRequest request;

        public abstract void onThresholdReached(int var1, String var2);
    }

}

