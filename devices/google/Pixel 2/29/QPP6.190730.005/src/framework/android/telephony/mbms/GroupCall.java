/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.mbms;

import android.os.RemoteException;
import android.telephony.MbmsGroupCallSession;
import android.telephony.mbms.InternalGroupCallCallback;
import android.telephony.mbms.vendor.IMbmsGroupCallService;
import android.util.Log;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

public class GroupCall
implements AutoCloseable {
    private static final String LOG_TAG = "MbmsGroupCall";
    public static final int REASON_BY_USER_REQUEST = 1;
    public static final int REASON_FREQUENCY_CONFLICT = 3;
    public static final int REASON_LEFT_MBMS_BROADCAST_AREA = 6;
    public static final int REASON_NONE = 0;
    public static final int REASON_NOT_CONNECTED_TO_HOMECARRIER_LTE = 5;
    public static final int REASON_OUT_OF_MEMORY = 4;
    public static final int STATE_STALLED = 3;
    public static final int STATE_STARTED = 2;
    public static final int STATE_STOPPED = 1;
    private final InternalGroupCallCallback mCallback;
    private final MbmsGroupCallSession mParentSession;
    private IMbmsGroupCallService mService;
    private final int mSubscriptionId;
    private final long mTmgi;

    public GroupCall(int n, IMbmsGroupCallService iMbmsGroupCallService, MbmsGroupCallSession mbmsGroupCallSession, long l, InternalGroupCallCallback internalGroupCallCallback) {
        this.mSubscriptionId = n;
        this.mParentSession = mbmsGroupCallSession;
        this.mService = iMbmsGroupCallService;
        this.mTmgi = l;
        this.mCallback = internalGroupCallCallback;
    }

    private void sendErrorToApp(int n, String string2) {
        this.mCallback.onError(n, string2);
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
        if (var1_1 == null) throw new IllegalStateException("No group call service attached");
        var1_1.stopGroupCall(this.mSubscriptionId, this.mTmgi);
        this.mParentSession.onGroupCallStopped(this);
        return;
        {
            catch (RemoteException var1_3) {}
            {
                Log.w("MbmsGroupCall", "Remote process died");
                this.mService = null;
                this.sendErrorToApp(3, null);
            }
        }
        ** finally { 
lbl13: // 1 sources:
        this.mParentSession.onGroupCallStopped(this);
        throw var1_2;
    }

    public InternalGroupCallCallback getCallback() {
        return this.mCallback;
    }

    public long getTmgi() {
        return this.mTmgi;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public void updateGroupCall(List<Integer> var1_1, List<Integer> var2_4) {
        var3_5 = this.mService;
        if (var3_5 == null) throw new IllegalStateException("No group call service attached");
        var3_5.updateGroupCall(this.mSubscriptionId, this.mTmgi, var1_1, var2_4);
        this.mParentSession.onGroupCallStopped(this);
        return;
        {
            catch (RemoteException var1_3) {}
            {
                Log.w("MbmsGroupCall", "Remote process died");
                this.mService = null;
                this.sendErrorToApp(3, null);
            }
        }
        ** finally { 
lbl13: // 1 sources:
        this.mParentSession.onGroupCallStopped(this);
        throw var1_2;
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface GroupCallState {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface GroupCallStateChangeReason {
    }

}

