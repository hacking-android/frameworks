/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.content.Context
 *  android.content.res.Resources
 *  android.net.NetworkCapabilities
 *  android.net.NetworkConfig
 *  android.net.NetworkRequest
 *  android.os.Message
 *  android.telephony.Rlog
 *  android.telephony.data.ApnSetting
 *  android.text.TextUtils
 *  android.util.LocalLog
 *  android.util.SparseIntArray
 *  com.android.internal.telephony.DctConstants
 *  com.android.internal.telephony.DctConstants$State
 *  com.android.internal.util.IndentingPrintWriter
 */
package com.android.internal.telephony.dataconnection;

import android.app.PendingIntent;
import android.content.Context;
import android.content.res.Resources;
import android.net.NetworkCapabilities;
import android.net.NetworkConfig;
import android.net.NetworkRequest;
import android.os.Message;
import android.telephony.Rlog;
import android.telephony.data.ApnSetting;
import android.text.TextUtils;
import android.util.LocalLog;
import android.util.SparseIntArray;
import com.android.internal.telephony.DctConstants;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.RetryManager;
import com.android.internal.telephony.dataconnection.DataConnection;
import com.android.internal.telephony.dataconnection.DcTracker;
import com.android.internal.util.IndentingPrintWriter;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class ApnContext {
    protected static final boolean DBG = false;
    private static final String SLOG_TAG = "ApnContext";
    public final String LOG_TAG;
    private ApnSetting mApnSetting;
    private final String mApnType;
    private boolean mConcurrentVoiceAndDataAllowed;
    private final AtomicInteger mConnectionGeneration = new AtomicInteger(0);
    private DataConnection mDataConnection;
    AtomicBoolean mDataEnabled;
    private final DcTracker mDcTracker;
    AtomicBoolean mDependencyMet;
    private final LocalLog mLocalLog = new LocalLog(150);
    private final ArrayList<NetworkRequest> mNetworkRequests = new ArrayList();
    private final Phone mPhone;
    String mReason;
    PendingIntent mReconnectAlarmIntent;
    private int mRefCount = 0;
    private final Object mRefCountLock = new Object();
    private final SparseIntArray mRetriesLeftPerErrorCode = new SparseIntArray();
    private final RetryManager mRetryManager;
    private DctConstants.State mState;
    private final LocalLog mStateLocalLog = new LocalLog(50);
    public final int priority;

    public ApnContext(Phone phone, String string, String string2, NetworkConfig networkConfig, DcTracker dcTracker) {
        this.mPhone = phone;
        this.mApnType = string;
        this.mState = DctConstants.State.IDLE;
        this.setReason("dataEnabled");
        this.mDataEnabled = new AtomicBoolean(false);
        this.mDependencyMet = new AtomicBoolean(networkConfig.dependencyMet);
        this.priority = networkConfig.priority;
        this.LOG_TAG = string2;
        this.mDcTracker = dcTracker;
        this.mRetryManager = new RetryManager(phone, string);
    }

    static int getApnTypeFromNetworkRequest(NetworkRequest networkRequest) {
        Object object = networkRequest.networkCapabilities;
        if (object.getTransportTypes().length > 0 && !object.hasTransport(0)) {
            return 0;
        }
        int n = 0;
        int n2 = 0;
        int n3 = 0;
        if (object.hasCapability(12)) {
            n = 17;
        }
        int n4 = n;
        if (object.hasCapability(0)) {
            n2 = n3;
            if (n != 0) {
                n2 = 1;
            }
            n4 = 2;
        }
        int n5 = n4;
        n = n2;
        if (object.hasCapability(1)) {
            n = n2;
            if (n4 != 0) {
                n = 1;
            }
            n5 = 4;
        }
        n3 = n5;
        n2 = n;
        if (object.hasCapability(2)) {
            if (n5 != 0) {
                n = 1;
            }
            n3 = 8;
            n2 = n;
        }
        n4 = n3;
        n = n2;
        if (object.hasCapability(3)) {
            n = n2;
            if (n3 != 0) {
                n = 1;
            }
            n4 = 32;
        }
        n5 = n4;
        n2 = n;
        if (object.hasCapability(4)) {
            if (n4 != 0) {
                n = 1;
            }
            n5 = 64;
            n2 = n;
        }
        n3 = n5;
        n4 = n2;
        if (object.hasCapability(5)) {
            if (n5 != 0) {
                n2 = 1;
            }
            n3 = 128;
            n4 = n2;
        }
        n5 = n3;
        n = n4;
        if (object.hasCapability(7)) {
            n = n4;
            if (n3 != 0) {
                n = 1;
            }
            n5 = 256;
        }
        n4 = n5;
        n2 = n;
        if (object.hasCapability(10)) {
            if (n5 != 0) {
                n = 1;
            }
            n4 = 512;
            n2 = n;
        }
        n3 = n4;
        n = n2;
        if (object.hasCapability(23)) {
            if (n4 != 0) {
                n2 = 1;
            }
            n3 = 1024;
            n = n2;
        }
        if (n != 0) {
            Rlog.d((String)SLOG_TAG, (String)"Multiple apn types specified in request - result is unspecified!");
        }
        if (n3 == 0) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Unsupported NetworkRequest in Telephony: nr=");
            ((StringBuilder)object).append((Object)networkRequest);
            Rlog.d((String)SLOG_TAG, (String)((StringBuilder)object).toString());
        }
        return n3;
    }

    public static int getApnTypeFromNetworkType(int n) {
        if (n != 0) {
            if (n != 2) {
                if (n != 3) {
                    if (n != 4) {
                        if (n != 14) {
                            if (n != 15) {
                                switch (n) {
                                    default: {
                                        return 0;
                                    }
                                    case 12: {
                                        return 128;
                                    }
                                    case 11: {
                                        return 64;
                                    }
                                    case 10: 
                                }
                                return 32;
                            }
                            return 512;
                        }
                        return 256;
                    }
                    return 8;
                }
                return 4;
            }
            return 2;
        }
        return 17;
    }

    private boolean isFastRetryReason() {
        boolean bl = "nwTypeChanged".equals(this.mReason) || "apnChanged".equals(this.mReason);
        return bl;
    }

    private void log(String string) {
    }

    private void logl(String string) {
        this.log(string);
        this.mLocalLog.log(string);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void dump(FileDescriptor fileDescriptor, PrintWriter object, String[] arrstring) {
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter((Writer)object, "  ");
        object = this.mRefCountLock;
        synchronized (object) {
            indentingPrintWriter.println(this.toString());
            if (this.mNetworkRequests.size() > 0) {
                indentingPrintWriter.println("NetworkRequests:");
                indentingPrintWriter.increaseIndent();
                Iterator<NetworkRequest> iterator = this.mNetworkRequests.iterator();
                while (iterator.hasNext()) {
                    indentingPrintWriter.println((Object)iterator.next());
                }
                indentingPrintWriter.decreaseIndent();
            }
            indentingPrintWriter.increaseIndent();
            indentingPrintWriter.println("-----");
            indentingPrintWriter.println("Local log:");
            this.mLocalLog.dump(fileDescriptor, (PrintWriter)indentingPrintWriter, arrstring);
            indentingPrintWriter.println("-----");
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.println("Historical APN state:");
            indentingPrintWriter.increaseIndent();
            this.mStateLocalLog.dump(fileDescriptor, (PrintWriter)indentingPrintWriter, arrstring);
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.println((Object)this.mRetryManager);
            indentingPrintWriter.println("--------------------------");
            return;
        }
    }

    public ApnSetting getApnSetting() {
        synchronized (this) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("getApnSetting: apnSetting=");
            stringBuilder.append((Object)this.mApnSetting);
            this.log(stringBuilder.toString());
            stringBuilder = this.mApnSetting;
            return stringBuilder;
        }
    }

    public String getApnType() {
        return this.mApnType;
    }

    public int getApnTypeBitmask() {
        return ApnSetting.getApnTypesBitmaskFromString((String)this.mApnType);
    }

    public int getConnectionGeneration() {
        return this.mConnectionGeneration.get();
    }

    public DataConnection getDataConnection() {
        synchronized (this) {
            DataConnection dataConnection = this.mDataConnection;
            return dataConnection;
        }
    }

    public long getDelayForNextApn(boolean bl) {
        RetryManager retryManager = this.mRetryManager;
        bl = bl || this.isFastRetryReason();
        return retryManager.getDelayForNextApn(bl);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public List<NetworkRequest> getNetworkRequests() {
        Object object = this.mRefCountLock;
        synchronized (object) {
            return new ArrayList<NetworkRequest>(this.mNetworkRequests);
        }
    }

    public ApnSetting getNextApnSetting() {
        return this.mRetryManager.getNextApnSetting();
    }

    public String getReason() {
        synchronized (this) {
            String string = this.mReason;
            return string;
        }
    }

    public PendingIntent getReconnectIntent() {
        synchronized (this) {
            PendingIntent pendingIntent = this.mReconnectAlarmIntent;
            return pendingIntent;
        }
    }

    long getRetryAfterDisconnectDelay() {
        return this.mRetryManager.getRetryAfterDisconnectDelay();
    }

    public DctConstants.State getState() {
        synchronized (this) {
            DctConstants.State state = this.mState;
            return state;
        }
    }

    public ArrayList<ApnSetting> getWaitingApns() {
        return this.mRetryManager.getWaitingApns();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean hasRestrictedRequests(boolean bl) {
        Object object = this.mRefCountLock;
        synchronized (object) {
            NetworkRequest networkRequest;
            Iterator<NetworkRequest> iterator = this.mNetworkRequests.iterator();
            do {
                if (!iterator.hasNext()) {
                    return false;
                }
                networkRequest = iterator.next();
            } while (bl && networkRequest.networkCapabilities.hasCapability(2) || networkRequest.networkCapabilities.hasCapability(13));
            return true;
        }
    }

    public int incAndGetConnectionGeneration() {
        return this.mConnectionGeneration.incrementAndGet();
    }

    public boolean isConcurrentVoiceAndDataAllowed() {
        synchronized (this) {
            boolean bl = this.mConcurrentVoiceAndDataAllowed;
            return bl;
        }
    }

    public boolean isConnectable() {
        boolean bl = this.isReady() && (this.mState == DctConstants.State.IDLE || this.mState == DctConstants.State.RETRYING || this.mState == DctConstants.State.FAILED);
        return bl;
    }

    public boolean isConnectedOrConnecting() {
        boolean bl = this.isReady() && (this.mState == DctConstants.State.CONNECTED || this.mState == DctConstants.State.CONNECTING || this.mState == DctConstants.State.RETRYING);
        return bl;
    }

    public boolean isDependencyMet() {
        return this.mDependencyMet.get();
    }

    public boolean isDisconnected() {
        DctConstants.State state = this.getState();
        boolean bl = state == DctConstants.State.IDLE || state == DctConstants.State.FAILED;
        return bl;
    }

    public boolean isEnabled() {
        return this.mDataEnabled.get();
    }

    public boolean isProvisioningApn() {
        ApnSetting apnSetting;
        String string = this.mPhone.getContext().getResources().getString(17040447);
        if (!TextUtils.isEmpty((CharSequence)string) && (apnSetting = this.mApnSetting) != null && apnSetting.getApnName() != null) {
            return this.mApnSetting.getApnName().equals(string);
        }
        return false;
    }

    public boolean isReady() {
        boolean bl = this.mDataEnabled.get() && this.mDependencyMet.get();
        return bl;
    }

    public void markApnPermanentFailed(ApnSetting apnSetting) {
        this.mRetryManager.markApnPermanentFailed(apnSetting);
    }

    public void releaseDataConnection(String string) {
        synchronized (this) {
            if (this.mDataConnection != null) {
                this.mDataConnection.tearDown(this, string, null);
                this.mDataConnection = null;
            }
            this.setState(DctConstants.State.IDLE);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void releaseNetwork(NetworkRequest object, int n) {
        Object object2 = this.mRefCountLock;
        synchronized (object2) {
            if (!this.mNetworkRequests.contains(object)) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("releaseNetwork can't find this request (");
                stringBuilder.append(object);
                stringBuilder.append(")");
                this.logl(stringBuilder.toString());
            } else {
                this.mNetworkRequests.remove(object);
                if (this.mDataConnection != null) {
                    this.mDataConnection.reevaluateDataConnectionProperties();
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("releaseNetwork left with ");
                ((StringBuilder)object).append(this.mNetworkRequests.size());
                ((StringBuilder)object).append(" requests.");
                this.logl(((StringBuilder)object).toString());
                if (this.mNetworkRequests.size() == 0 || n == 2 || n == 3) {
                    this.mDcTracker.disableApn(ApnSetting.getApnTypesBitmaskFromString((String)this.mApnType), n);
                }
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void requestLog(String string) {
        LocalLog localLog = this.mLocalLog;
        synchronized (localLog) {
            this.mLocalLog.log(string);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void requestNetwork(NetworkRequest networkRequest, int n, Message message) {
        Object object = this.mRefCountLock;
        synchronized (object) {
            this.mNetworkRequests.add(networkRequest);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("requestNetwork for ");
            stringBuilder.append((Object)networkRequest);
            stringBuilder.append(", type=");
            stringBuilder.append(DcTracker.requestTypeToString(n));
            this.logl(stringBuilder.toString());
            this.mDcTracker.enableApn(ApnSetting.getApnTypesBitmaskFromString((String)this.mApnType), n, message);
            if (this.mDataConnection != null) {
                this.mDataConnection.reevaluateDataConnectionProperties();
            }
            return;
        }
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void resetErrorCodeRetries() {
        this.logl("ApnContext.resetErrorCodeRetries");
        arrstring = this.mPhone.getContext().getResources().getStringArray(17235999);
        var2_2 = this.mRetriesLeftPerErrorCode;
        // MONITORENTER : var2_2
        this.mRetriesLeftPerErrorCode.clear();
        var3_3 = arrstring.length;
        var4_4 = 0;
        do {
            block8 : {
                block7 : {
                    if (var4_4 >= var3_3) {
                        // MONITOREXIT : var2_2
                        return;
                    }
                    var5_5 = arrstring[var4_4];
                    var6_7 = var5_5.split(",");
                    if (var6_7 == null || (var7_8 = ((String[])var6_7).length) != 2) break block7;
                    try {
                        var8_9 = Integer.parseInt((String)var6_7[0]);
                        var7_8 = Integer.parseInt((String)var6_7[1]);
                        if (var7_8 <= 0 || var8_9 <= 0) ** GOTO lbl36
                    }
                    catch (NumberFormatException string) {
                        var6_7 = new StringBuilder();
                        var6_7.append("Exception parsing config_retries_per_error_code: ");
                        var6_7.append(string);
                        this.log(var6_7.toString());
                    }
                    this.mRetriesLeftPerErrorCode.put(var8_9, var7_8);
                    break block8;
                    break block8;
                }
                var6_7 = new StringBuilder();
                var6_7.append("Exception parsing config_retries_per_error_code: ");
                var6_7.append(var5_5);
                this.log(var6_7.toString());
            }
            ++var4_4;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean restartOnError(int n) {
        int n2;
        boolean bl = false;
        Object object = this.mRetriesLeftPerErrorCode;
        synchronized (object) {
            n2 = this.mRetriesLeftPerErrorCode.get(n);
            if (n2 != 0) {
                if (n2 != 1) {
                    this.mRetriesLeftPerErrorCode.put(n, n2 - 1);
                    bl = false;
                } else {
                    this.resetErrorCodeRetries();
                    bl = true;
                }
            }
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("ApnContext.restartOnError(");
        ((StringBuilder)object).append(n);
        ((StringBuilder)object).append(") found ");
        ((StringBuilder)object).append(n2);
        ((StringBuilder)object).append(" and returned ");
        ((StringBuilder)object).append(bl);
        this.logl(((StringBuilder)object).toString());
        return bl;
    }

    public void setApnSetting(ApnSetting apnSetting) {
        synchronized (this) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("setApnSetting: apnSetting=");
            stringBuilder.append((Object)apnSetting);
            this.log(stringBuilder.toString());
            this.mApnSetting = apnSetting;
            return;
        }
    }

    public void setConcurrentVoiceAndDataAllowed(boolean bl) {
        synchronized (this) {
            this.mConcurrentVoiceAndDataAllowed = bl;
            return;
        }
    }

    public void setDataConnection(DataConnection dataConnection) {
        synchronized (this) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("setDataConnectionAc: old=");
            stringBuilder.append((Object)this.mDataConnection);
            stringBuilder.append(",new=");
            stringBuilder.append((Object)dataConnection);
            stringBuilder.append(" this=");
            stringBuilder.append(this);
            this.log(stringBuilder.toString());
            this.mDataConnection = dataConnection;
            return;
        }
    }

    public void setEnabled(boolean bl) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("set enabled as ");
        stringBuilder.append(bl);
        stringBuilder.append(", current state is ");
        stringBuilder.append(this.mDataEnabled.get());
        this.log(stringBuilder.toString());
        this.mDataEnabled.set(bl);
    }

    public void setModemSuggestedDelay(long l) {
        this.mRetryManager.setModemSuggestedDelay(l);
    }

    public void setReason(String string) {
        synchronized (this) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("set reason as ");
            stringBuilder.append(string);
            stringBuilder.append(",current state ");
            stringBuilder.append((Object)this.mState);
            this.log(stringBuilder.toString());
            this.mReason = string;
            return;
        }
    }

    public void setReconnectIntent(PendingIntent pendingIntent) {
        synchronized (this) {
            this.mReconnectAlarmIntent = pendingIntent;
            return;
        }
    }

    public void setState(DctConstants.State state) {
        synchronized (this) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("setState: ");
            stringBuilder.append((Object)state);
            stringBuilder.append(", previous state:");
            stringBuilder.append((Object)this.mState);
            this.log(stringBuilder.toString());
            if (this.mState != state) {
                LocalLog localLog = this.mStateLocalLog;
                stringBuilder = new StringBuilder();
                stringBuilder.append("State changed from ");
                stringBuilder.append((Object)this.mState);
                stringBuilder.append(" to ");
                stringBuilder.append((Object)state);
                localLog.log(stringBuilder.toString());
                this.mState = state;
            }
            if (this.mState == DctConstants.State.FAILED && this.mRetryManager.getWaitingApns() != null) {
                this.mRetryManager.getWaitingApns().clear();
            }
            return;
        }
    }

    public void setWaitingApns(ArrayList<ApnSetting> arrayList) {
        synchronized (this) {
            this.mRetryManager.setWaitingApns(arrayList);
            return;
        }
    }

    public String toString() {
        synchronized (this) {
            CharSequence charSequence = new StringBuilder();
            charSequence.append("{mApnType=");
            charSequence.append(this.mApnType);
            charSequence.append(" mState=");
            charSequence.append((Object)this.getState());
            charSequence.append(" mWaitingApns={");
            charSequence.append(this.mRetryManager.getWaitingApns());
            charSequence.append("} mApnSetting={");
            charSequence.append((Object)this.mApnSetting);
            charSequence.append("} mReason=");
            charSequence.append(this.mReason);
            charSequence.append(" mDataEnabled=");
            charSequence.append(this.mDataEnabled);
            charSequence.append(" mDependencyMet=");
            charSequence.append(this.mDependencyMet);
            charSequence.append("}");
            charSequence = charSequence.toString();
            return charSequence;
        }
    }
}

