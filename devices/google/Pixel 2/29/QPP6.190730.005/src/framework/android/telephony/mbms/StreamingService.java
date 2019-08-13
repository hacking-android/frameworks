/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.mbms;

import android.net.Uri;
import android.os.RemoteException;
import android.telephony.MbmsStreamingSession;
import android.telephony.mbms.InternalStreamingServiceCallback;
import android.telephony.mbms.StreamingServiceInfo;
import android.telephony.mbms.vendor.IMbmsStreamingService;
import android.util.Log;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class StreamingService
implements AutoCloseable {
    public static final int BROADCAST_METHOD = 1;
    private static final String LOG_TAG = "MbmsStreamingService";
    public static final int REASON_BY_USER_REQUEST = 1;
    public static final int REASON_END_OF_SESSION = 2;
    public static final int REASON_FREQUENCY_CONFLICT = 3;
    public static final int REASON_LEFT_MBMS_BROADCAST_AREA = 6;
    public static final int REASON_NONE = 0;
    public static final int REASON_NOT_CONNECTED_TO_HOMECARRIER_LTE = 5;
    public static final int REASON_OUT_OF_MEMORY = 4;
    public static final int STATE_STALLED = 3;
    public static final int STATE_STARTED = 2;
    public static final int STATE_STOPPED = 1;
    public static final int UNICAST_METHOD = 2;
    private final InternalStreamingServiceCallback mCallback;
    private final MbmsStreamingSession mParentSession;
    private IMbmsStreamingService mService;
    private final StreamingServiceInfo mServiceInfo;
    private final int mSubscriptionId;

    public StreamingService(int n, IMbmsStreamingService iMbmsStreamingService, MbmsStreamingSession mbmsStreamingSession, StreamingServiceInfo streamingServiceInfo, InternalStreamingServiceCallback internalStreamingServiceCallback) {
        this.mSubscriptionId = n;
        this.mParentSession = mbmsStreamingSession;
        this.mService = iMbmsStreamingService;
        this.mServiceInfo = streamingServiceInfo;
        this.mCallback = internalStreamingServiceCallback;
    }

    private void sendErrorToApp(int n, String string2) {
        try {
            this.mCallback.onError(n, string2);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public void close() {
        var1_1 = this.mService;
        if (var1_1 == null) throw new IllegalStateException("No streaming service attached");
        var1_1.stopStreaming(this.mSubscriptionId, this.mServiceInfo.getServiceId());
        this.mParentSession.onStreamingServiceStopped(this);
        return;
        {
            catch (RemoteException var1_3) {}
            {
                Log.w("MbmsStreamingService", "Remote process died");
                this.mService = null;
                this.sendErrorToApp(3, null);
            }
        }
        ** finally { 
lbl13: // 1 sources:
        this.mParentSession.onStreamingServiceStopped(this);
        throw var1_2;
    }

    public InternalStreamingServiceCallback getCallback() {
        return this.mCallback;
    }

    public StreamingServiceInfo getInfo() {
        return this.mServiceInfo;
    }

    public Uri getPlaybackUri() {
        Object object = this.mService;
        if (object != null) {
            try {
                object = object.getPlaybackUri(this.mSubscriptionId, this.mServiceInfo.getServiceId());
                return object;
            }
            catch (RemoteException remoteException) {
                Log.w(LOG_TAG, "Remote process died");
                this.mService = null;
                this.mParentSession.onStreamingServiceStopped(this);
                this.sendErrorToApp(3, null);
                return null;
            }
        }
        throw new IllegalStateException("No streaming service attached");
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface StreamingState {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface StreamingStateChangeReason {
    }

}

