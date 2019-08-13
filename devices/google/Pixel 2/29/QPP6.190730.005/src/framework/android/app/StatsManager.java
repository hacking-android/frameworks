/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.annotation.SystemApi;
import android.app.PendingIntent;
import android.content.Context;
import android.content.IIntentSender;
import android.os.IBinder;
import android.os.IStatsManager;
import android.os.IStatsPullerCallback;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.AndroidException;
import android.util.Slog;

@SystemApi
public final class StatsManager {
    public static final String ACTION_STATSD_STARTED = "android.app.action.STATSD_STARTED";
    private static final boolean DEBUG = false;
    public static final String EXTRA_STATS_ACTIVE_CONFIG_KEYS = "android.app.extra.STATS_ACTIVE_CONFIG_KEYS";
    public static final String EXTRA_STATS_BROADCAST_SUBSCRIBER_COOKIES = "android.app.extra.STATS_BROADCAST_SUBSCRIBER_COOKIES";
    public static final String EXTRA_STATS_CONFIG_KEY = "android.app.extra.STATS_CONFIG_KEY";
    public static final String EXTRA_STATS_CONFIG_UID = "android.app.extra.STATS_CONFIG_UID";
    public static final String EXTRA_STATS_DIMENSIONS_VALUE = "android.app.extra.STATS_DIMENSIONS_VALUE";
    public static final String EXTRA_STATS_SUBSCRIPTION_ID = "android.app.extra.STATS_SUBSCRIPTION_ID";
    public static final String EXTRA_STATS_SUBSCRIPTION_RULE_ID = "android.app.extra.STATS_SUBSCRIPTION_RULE_ID";
    private static final String TAG = "StatsManager";
    private final Context mContext;
    private IStatsManager mService;

    public StatsManager(Context context) {
        this.mContext = context;
    }

    private IStatsManager getIStatsManagerLocked() throws StatsUnavailableException {
        Object object = this.mService;
        if (object != null) {
            return object;
        }
        this.mService = IStatsManager.Stub.asInterface(ServiceManager.getService("stats"));
        object = this.mService;
        if (object != null) {
            try {
                object = object.asBinder();
                StatsdDeathRecipient statsdDeathRecipient = new StatsdDeathRecipient();
                object.linkToDeath(statsdDeathRecipient, 0);
                return this.mService;
            }
            catch (RemoteException remoteException) {
                throw new StatsUnavailableException("could not connect when linkToDeath", remoteException);
            }
        }
        throw new StatsUnavailableException("could not be found");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void addConfig(long l, byte[] object) throws StatsUnavailableException {
        synchronized (this) {
            try {
                try {
                    this.getIStatsManagerLocked().addConfiguration(l, (byte[])object, this.mContext.getOpPackageName());
                    return;
                }
                catch (SecurityException securityException) {
                    object = new StatsUnavailableException(securityException.getMessage(), securityException);
                    throw object;
                }
                catch (RemoteException remoteException) {
                    Slog.e(TAG, "Failed to connect to statsd when adding configuration");
                    object = new StatsUnavailableException("could not connect", remoteException);
                    throw object;
                }
            }
            catch (Throwable throwable2) {}
            throw throwable2;
        }
    }

    @Deprecated
    public boolean addConfiguration(long l, byte[] arrby) {
        try {
            this.addConfig(l, arrby);
            return true;
        }
        catch (StatsUnavailableException | IllegalArgumentException exception) {
            return false;
        }
    }

    @Deprecated
    public byte[] getData(long l) {
        try {
            byte[] arrby = this.getReports(l);
            return arrby;
        }
        catch (StatsUnavailableException statsUnavailableException) {
            return null;
        }
    }

    @Deprecated
    public byte[] getMetadata() {
        try {
            byte[] arrby = this.getStatsMetadata();
            return arrby;
        }
        catch (StatsUnavailableException statsUnavailableException) {
            return null;
        }
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public long[] getRegisteredExperimentIds() throws StatsUnavailableException {
        long[] arrl;
        block7 : {
            // MONITORENTER : this
            arrl = this.getIStatsManagerLocked();
            if (arrl != null) break block7;
            return new long[0];
        }
        arrl = arrl.getRegisteredExperimentIds();
        // MONITOREXIT : this
        return arrl;
        catch (RemoteException remoteException) {
            // MONITOREXIT : this
            return new long[0];
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public byte[] getReports(long l) throws StatsUnavailableException {
        synchronized (this) {
            try {
                try {
                    return this.getIStatsManagerLocked().getData(l, this.mContext.getOpPackageName());
                }
                catch (SecurityException securityException) {
                    StatsUnavailableException statsUnavailableException = new StatsUnavailableException(securityException.getMessage(), securityException);
                    throw statsUnavailableException;
                }
                catch (RemoteException remoteException) {
                    Slog.e(TAG, "Failed to connect to statsd when getting data");
                    StatsUnavailableException statsUnavailableException = new StatsUnavailableException("could not connect", remoteException);
                    throw statsUnavailableException;
                }
            }
            catch (Throwable throwable2) {}
            throw throwable2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public byte[] getStatsMetadata() throws StatsUnavailableException {
        synchronized (this) {
            try {
                try {
                    return this.getIStatsManagerLocked().getMetadata(this.mContext.getOpPackageName());
                }
                catch (SecurityException securityException) {
                    StatsUnavailableException statsUnavailableException = new StatsUnavailableException(securityException.getMessage(), securityException);
                    throw statsUnavailableException;
                }
                catch (RemoteException remoteException) {
                    Slog.e(TAG, "Failed to connect to statsd when getting metadata");
                    StatsUnavailableException statsUnavailableException = new StatsUnavailableException("could not connect", remoteException);
                    throw statsUnavailableException;
                }
            }
            catch (Throwable throwable2) {}
            throw throwable2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void removeConfig(long l) throws StatsUnavailableException {
        synchronized (this) {
            try {
                try {
                    this.getIStatsManagerLocked().removeConfiguration(l, this.mContext.getOpPackageName());
                    return;
                }
                catch (SecurityException securityException) {
                    StatsUnavailableException statsUnavailableException = new StatsUnavailableException(securityException.getMessage(), securityException);
                    throw statsUnavailableException;
                }
                catch (RemoteException remoteException) {
                    Slog.e(TAG, "Failed to connect to statsd when removing configuration");
                    StatsUnavailableException statsUnavailableException = new StatsUnavailableException("could not connect", remoteException);
                    throw statsUnavailableException;
                }
            }
            catch (Throwable throwable2) {}
            throw throwable2;
        }
    }

    @Deprecated
    public boolean removeConfiguration(long l) {
        try {
            this.removeConfig(l);
            return true;
        }
        catch (StatsUnavailableException statsUnavailableException) {
            return false;
        }
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public long[] setActiveConfigsChangedOperation(PendingIntent arrl) throws StatsUnavailableException {
        IStatsManager iStatsManager;
        block7 : {
            // MONITORENTER : this
            iStatsManager = this.getIStatsManagerLocked();
            if (arrl != null) break block7;
            iStatsManager.removeActiveConfigsChangedOperation(this.mContext.getOpPackageName());
            // MONITOREXIT : this
            return new long[0];
        }
        arrl = iStatsManager.setActiveConfigsChangedOperation(((PendingIntent)arrl).getTarget().asBinder(), this.mContext.getOpPackageName());
        // MONITOREXIT : this
        return arrl;
        catch (SecurityException securityException) {
            arrl = new StatsUnavailableException(securityException.getMessage(), securityException);
            throw arrl;
        }
        catch (RemoteException remoteException) {
            Slog.e(TAG, "Failed to connect to statsd when registering active configs listener.");
            arrl = new StatsUnavailableException("could not connect", remoteException);
            throw arrl;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setBroadcastSubscriber(PendingIntent object, long l, long l2) throws StatsUnavailableException {
        synchronized (this) {
            try {
                try {
                    IStatsManager iStatsManager = this.getIStatsManagerLocked();
                    if (object != null) {
                        iStatsManager.setBroadcastSubscriber(l, l2, ((PendingIntent)object).getTarget().asBinder(), this.mContext.getOpPackageName());
                    } else {
                        iStatsManager.unsetBroadcastSubscriber(l, l2, this.mContext.getOpPackageName());
                    }
                    return;
                }
                catch (SecurityException securityException) {
                    StatsUnavailableException statsUnavailableException = new StatsUnavailableException(securityException.getMessage(), securityException);
                    throw statsUnavailableException;
                }
                catch (RemoteException remoteException) {
                    Slog.e(TAG, "Failed to connect to statsd when adding broadcast subscriber", remoteException);
                    object = new StatsUnavailableException("could not connect", remoteException);
                    throw object;
                }
            }
            catch (Throwable throwable2) {}
            throw throwable2;
        }
    }

    @Deprecated
    public boolean setBroadcastSubscriber(long l, long l2, PendingIntent pendingIntent) {
        try {
            this.setBroadcastSubscriber(pendingIntent, l, l2);
            return true;
        }
        catch (StatsUnavailableException statsUnavailableException) {
            return false;
        }
    }

    @Deprecated
    public boolean setDataFetchOperation(long l, PendingIntent pendingIntent) {
        try {
            this.setFetchReportsOperation(pendingIntent, l);
            return true;
        }
        catch (StatsUnavailableException statsUnavailableException) {
            return false;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setFetchReportsOperation(PendingIntent object, long l) throws StatsUnavailableException {
        synchronized (this) {
            try {
                try {
                    IStatsManager iStatsManager = this.getIStatsManagerLocked();
                    if (object == null) {
                        iStatsManager.removeDataFetchOperation(l, this.mContext.getOpPackageName());
                    } else {
                        iStatsManager.setDataFetchOperation(l, ((PendingIntent)object).getTarget().asBinder(), this.mContext.getOpPackageName());
                    }
                    return;
                }
                catch (SecurityException securityException) {
                    StatsUnavailableException statsUnavailableException = new StatsUnavailableException(securityException.getMessage(), securityException);
                    throw statsUnavailableException;
                }
                catch (RemoteException remoteException) {
                    Slog.e(TAG, "Failed to connect to statsd when registering data listener.");
                    object = new StatsUnavailableException("could not connect", remoteException);
                    throw object;
                }
            }
            catch (Throwable throwable2) {}
            throw throwable2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setPullerCallback(int n, IStatsPullerCallback iStatsPullerCallback) throws StatsUnavailableException {
        synchronized (this) {
            try {
                try {
                    IStatsManager iStatsManager = this.getIStatsManagerLocked();
                    if (iStatsPullerCallback == null) {
                        iStatsManager.unregisterPullerCallback(n, this.mContext.getOpPackageName());
                    } else {
                        iStatsManager.registerPullerCallback(n, iStatsPullerCallback, this.mContext.getOpPackageName());
                    }
                    return;
                }
                catch (SecurityException securityException) {
                    StatsUnavailableException statsUnavailableException = new StatsUnavailableException(securityException.getMessage(), securityException);
                    throw statsUnavailableException;
                }
                catch (RemoteException remoteException) {
                    Slog.e(TAG, "Failed to connect to statsd when registering data listener.");
                    StatsUnavailableException statsUnavailableException = new StatsUnavailableException("could not connect", remoteException);
                    throw statsUnavailableException;
                }
            }
            catch (Throwable throwable2) {}
            throw throwable2;
        }
    }

    public static class StatsUnavailableException
    extends AndroidException {
        public StatsUnavailableException(String string2) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Failed to connect to statsd: ");
            stringBuilder.append(string2);
            super(stringBuilder.toString());
        }

        public StatsUnavailableException(String string2, Throwable throwable) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Failed to connect to statsd: ");
            stringBuilder.append(string2);
            super(stringBuilder.toString(), throwable);
        }
    }

    private class StatsdDeathRecipient
    implements IBinder.DeathRecipient {
        private StatsdDeathRecipient() {
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void binderDied() {
            synchronized (this) {
                StatsManager.this.mService = null;
                return;
            }
        }
    }

}

