/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.net.NetworkCapabilities
 *  android.net.NetworkFactory
 *  android.net.NetworkRequest
 *  android.net.NetworkSpecifier
 *  android.net.StringNetworkSpecifier
 *  android.os.AsyncResult
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Message
 *  android.os.Parcelable
 *  android.telephony.AccessNetworkConstants
 *  android.telephony.Rlog
 *  android.telephony.data.ApnSetting
 *  android.util.LocalLog
 *  com.android.internal.util.IndentingPrintWriter
 */
package com.android.internal.telephony.dataconnection;

import android.content.Context;
import android.net.NetworkCapabilities;
import android.net.NetworkFactory;
import android.net.NetworkRequest;
import android.net.NetworkSpecifier;
import android.net.StringNetworkSpecifier;
import android.os.AsyncResult;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;
import android.telephony.AccessNetworkConstants;
import android.telephony.Rlog;
import android.telephony.data.ApnSetting;
import android.util.LocalLog;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.PhoneSwitcher;
import com.android.internal.telephony.SubscriptionController;
import com.android.internal.telephony.SubscriptionMonitor;
import com.android.internal.telephony.dataconnection.ApnContext;
import com.android.internal.telephony.dataconnection.DataConnection;
import com.android.internal.telephony.dataconnection.DcTracker;
import com.android.internal.telephony.dataconnection.TransportManager;
import com.android.internal.util.IndentingPrintWriter;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TelephonyNetworkFactory
extends NetworkFactory {
    private static final int ACTION_NO_OP = 0;
    private static final int ACTION_RELEASE = 2;
    private static final int ACTION_REQUEST = 1;
    protected static final boolean DBG = true;
    private static final int EVENT_ACTIVE_PHONE_SWITCH = 1;
    private static final int EVENT_DATA_HANDOVER_COMPLETED = 6;
    private static final int EVENT_DATA_HANDOVER_NEEDED = 5;
    private static final int EVENT_NETWORK_RELEASE = 4;
    private static final int EVENT_NETWORK_REQUEST = 3;
    private static final int EVENT_SUBSCRIPTION_CHANGED = 2;
    private static final int REQUEST_LOG_SIZE = 40;
    private static final int TELEPHONY_NETWORK_SCORE = 50;
    public final String LOG_TAG;
    private final Handler mInternalHandler;
    private final LocalLog mLocalLog;
    private final Map<NetworkRequest, Integer> mNetworkRequests;
    private final Map<Message, TransportManager.HandoverParams> mPendingHandovers;
    private final Phone mPhone;
    private final PhoneSwitcher mPhoneSwitcher;
    private final SubscriptionController mSubscriptionController;
    private int mSubscriptionId;
    private final SubscriptionMonitor mSubscriptionMonitor;
    private final TransportManager mTransportManager;

    public TelephonyNetworkFactory(SubscriptionMonitor object, Looper looper, Phone phone) {
        Context context = phone.getContext();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("TelephonyNetworkFactory[");
        stringBuilder.append(phone.getPhoneId());
        stringBuilder.append("]");
        super(looper, context, stringBuilder.toString(), null);
        this.mLocalLog = new LocalLog(40);
        this.mNetworkRequests = new HashMap<NetworkRequest, Integer>();
        this.mPendingHandovers = new HashMap<Message, TransportManager.HandoverParams>();
        this.mPhone = phone;
        this.mTransportManager = this.mPhone.getTransportManager();
        this.mInternalHandler = new InternalHandler(looper);
        this.mSubscriptionController = SubscriptionController.getInstance();
        this.setCapabilityFilter(this.makeNetworkFilter(this.mSubscriptionController, this.mPhone.getPhoneId()));
        this.setScoreFilter(50);
        this.mPhoneSwitcher = PhoneSwitcher.getInstance();
        this.mSubscriptionMonitor = object;
        object = new StringBuilder();
        ((StringBuilder)object).append("TelephonyNetworkFactory[");
        ((StringBuilder)object).append(this.mPhone.getPhoneId());
        ((StringBuilder)object).append("]");
        this.LOG_TAG = ((StringBuilder)object).toString();
        this.mPhoneSwitcher.registerForActivePhoneSwitch(this.mInternalHandler, 1, null);
        this.mTransportManager.registerForHandoverNeededEvent(this.mInternalHandler, 5);
        this.mSubscriptionId = -1;
        this.mSubscriptionMonitor.registerForSubscriptionChanged(this.mPhone.getPhoneId(), this.mInternalHandler, 2, null);
        this.register();
    }

    private static int getAction(boolean bl, boolean bl2) {
        if (!bl && bl2) {
            return 1;
        }
        if (bl && !bl2) {
            return 2;
        }
        return 0;
    }

    private int getTransportTypeFromNetworkRequest(NetworkRequest networkRequest) {
        int n = ApnContext.getApnTypeFromNetworkRequest(networkRequest);
        return this.mTransportManager.getCurrentTransport(n);
    }

    private NetworkCapabilities makeNetworkFilter(int n) {
        NetworkCapabilities networkCapabilities = new NetworkCapabilities();
        networkCapabilities.addTransportType(0);
        networkCapabilities.addCapability(0);
        networkCapabilities.addCapability(1);
        networkCapabilities.addCapability(2);
        networkCapabilities.addCapability(3);
        networkCapabilities.addCapability(4);
        networkCapabilities.addCapability(5);
        networkCapabilities.addCapability(7);
        networkCapabilities.addCapability(8);
        networkCapabilities.addCapability(9);
        networkCapabilities.addCapability(10);
        networkCapabilities.addCapability(13);
        networkCapabilities.addCapability(12);
        networkCapabilities.setNetworkSpecifier((NetworkSpecifier)new StringNetworkSpecifier(String.valueOf(n)));
        return networkCapabilities;
    }

    private NetworkCapabilities makeNetworkFilter(SubscriptionController subscriptionController, int n) {
        return this.makeNetworkFilter(subscriptionController.getSubIdUsingPhoneId(n));
    }

    /*
     * WARNING - void declaration
     */
    private void onActivePhoneSwitch() {
        for (Map.Entry<NetworkRequest, Integer> entry : this.mNetworkRequests.entrySet()) {
            boolean bl;
            void var2_6;
            NetworkRequest networkRequest = entry.getKey();
            int n = entry.getValue();
            int n2 = -1;
            boolean bl2 = n != -1;
            int n3 = TelephonyNetworkFactory.getAction(bl2, bl = this.mPhoneSwitcher.shouldApplyNetworkRequest(networkRequest, this.mPhone.getPhoneId()));
            if (n3 == 0) continue;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onActivePhoneSwitch: ");
            if (n3 == 1) {
                String string = "Requesting";
            } else {
                String string = "Releasing";
            }
            stringBuilder.append((String)var2_6);
            stringBuilder.append(" network request ");
            stringBuilder.append((Object)networkRequest);
            this.logl(stringBuilder.toString());
            n = this.getTransportTypeFromNetworkRequest(networkRequest);
            if (n3 == 1) {
                this.requestNetworkInternal(networkRequest, 1, this.getTransportTypeFromNetworkRequest(networkRequest), null);
            } else if (n3 == 2) {
                this.releaseNetworkInternal(networkRequest, 2, this.getTransportTypeFromNetworkRequest(networkRequest));
            }
            Map<NetworkRequest, Integer> map = this.mNetworkRequests;
            if (bl) {
                n2 = n;
            }
            map.put(networkRequest, n2);
        }
    }

    private void onDataHandoverNeeded(int n, int n2, TransportManager.HandoverParams object) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("onDataHandoverNeeded: apnType=");
        stringBuilder.append(ApnSetting.getApnTypeString((int)n));
        stringBuilder.append(", target transport=");
        stringBuilder.append(AccessNetworkConstants.transportTypeToString((int)n2));
        this.log(stringBuilder.toString());
        if (this.mTransportManager.getCurrentTransport(n) == n2) {
            object = new StringBuilder();
            ((StringBuilder)object).append("APN type ");
            ((StringBuilder)object).append(ApnSetting.getApnTypeString((int)n));
            ((StringBuilder)object).append(" is already on ");
            ((StringBuilder)object).append(AccessNetworkConstants.transportTypeToString((int)n2));
            this.log(((StringBuilder)object).toString());
            return;
        }
        boolean bl = false;
        for (Map.Entry entry : this.mNetworkRequests.entrySet()) {
            Object object2 = (NetworkRequest)entry.getKey();
            int n3 = (Integer)entry.getValue();
            boolean bl2 = n3 != -1;
            boolean bl3 = bl;
            if (ApnContext.getApnTypeFromNetworkRequest((NetworkRequest)object2) == n) {
                bl3 = bl;
                if (bl2) {
                    bl3 = bl;
                    if (n3 != n2) {
                        DcTracker dcTracker = this.mPhone.getDcTracker(n3);
                        if (dcTracker != null) {
                            DataConnection dataConnection = dcTracker.getDataConnectionByApnType(ApnSetting.getApnTypeString((int)n));
                            if (dataConnection != null && (dataConnection.isActive() || dataConnection.isActivating())) {
                                Message message = this.mInternalHandler.obtainMessage(6);
                                message.getData().putParcelable("extra_network_request", (Parcelable)object2);
                                this.mPendingHandovers.put(message, (TransportManager.HandoverParams)object);
                                this.requestNetworkInternal((NetworkRequest)object2, 2, n2, message);
                                bl = true;
                            } else {
                                StringBuilder stringBuilder2 = new StringBuilder();
                                stringBuilder2.append("The network request is on transport ");
                                stringBuilder2.append(AccessNetworkConstants.transportTypeToString((int)n3));
                                stringBuilder2.append(", but no live data connection. Just move the request to transport ");
                                stringBuilder2.append(AccessNetworkConstants.transportTypeToString((int)n2));
                                stringBuilder2.append(", dc=");
                                stringBuilder2.append((Object)dataConnection);
                                this.log(stringBuilder2.toString());
                                this.releaseNetworkInternal((NetworkRequest)object2, 1, n3);
                                this.requestNetworkInternal((NetworkRequest)object2, 1, n2, null);
                            }
                            bl3 = bl;
                        } else {
                            object2 = new StringBuilder();
                            ((StringBuilder)object2).append("DcTracker on ");
                            ((StringBuilder)object2).append(AccessNetworkConstants.transportTypeToString((int)n3));
                            ((StringBuilder)object2).append(" is not available.");
                            this.log(((StringBuilder)object2).toString());
                            bl3 = bl;
                        }
                    }
                }
            }
            bl = bl3;
        }
        if (!bl) {
            this.log("No handover request pending. Handover process is now completed");
            ((TransportManager.HandoverParams)object).callback.onCompleted(true);
        }
    }

    private void onDataHandoverSetupCompleted(NetworkRequest networkRequest, boolean bl, int n, TransportManager.HandoverParams handoverParams) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("onDataHandoverSetupCompleted: ");
        stringBuilder.append((Object)networkRequest);
        stringBuilder.append(", success=");
        stringBuilder.append(bl);
        stringBuilder.append(", targetTransport=");
        stringBuilder.append(AccessNetworkConstants.transportTypeToString((int)n));
        this.log(stringBuilder.toString());
        int n2 = 1;
        int n3 = n == 1 ? 2 : 1;
        if (bl) {
            n2 = 3;
        }
        this.releaseNetworkInternal(networkRequest, n2, n3);
        this.mNetworkRequests.put(networkRequest, n);
        handoverParams.callback.onCompleted(bl);
    }

    private void onNeedNetworkFor(Message message) {
        message = (NetworkRequest)message.obj;
        boolean bl = this.mPhoneSwitcher.shouldApplyNetworkRequest((NetworkRequest)message, this.mPhone.getPhoneId());
        Object object = this.mNetworkRequests;
        int n = bl ? this.getTransportTypeFromNetworkRequest((NetworkRequest)message) : -1;
        object.put((NetworkRequest)message, n);
        object = new StringBuilder();
        ((StringBuilder)object).append("onNeedNetworkFor ");
        ((StringBuilder)object).append((Object)message);
        ((StringBuilder)object).append(" shouldApply ");
        ((StringBuilder)object).append(bl);
        this.logl(((StringBuilder)object).toString());
        if (bl) {
            this.requestNetworkInternal((NetworkRequest)message, 1, this.getTransportTypeFromNetworkRequest((NetworkRequest)message), null);
        }
    }

    private void onReleaseNetworkFor(Message object) {
        NetworkRequest networkRequest = (NetworkRequest)((Message)object).obj;
        boolean bl = this.mNetworkRequests.get((Object)networkRequest) != -1;
        this.mNetworkRequests.remove((Object)networkRequest);
        object = new StringBuilder();
        ((StringBuilder)object).append("onReleaseNetworkFor ");
        ((StringBuilder)object).append((Object)networkRequest);
        ((StringBuilder)object).append(" applied ");
        ((StringBuilder)object).append(bl);
        this.logl(((StringBuilder)object).toString());
        if (bl) {
            this.releaseNetworkInternal(networkRequest, 1, this.getTransportTypeFromNetworkRequest(networkRequest));
        }
    }

    private void onSubIdChange() {
        int n = this.mSubscriptionController.getSubIdUsingPhoneId(this.mPhone.getPhoneId());
        if (this.mSubscriptionId != n) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onSubIdChange ");
            stringBuilder.append(this.mSubscriptionId);
            stringBuilder.append("->");
            stringBuilder.append(n);
            this.log(stringBuilder.toString());
            this.mSubscriptionId = n;
            this.setCapabilityFilter(this.makeNetworkFilter(this.mSubscriptionId));
        }
    }

    private void releaseNetworkInternal(NetworkRequest networkRequest, int n, int n2) {
        if (this.mPhone.getDcTracker(n2) != null) {
            this.mPhone.getDcTracker(n2).releaseNetwork(networkRequest, n);
        }
    }

    private void requestNetworkInternal(NetworkRequest networkRequest, int n, int n2, Message message) {
        if (this.mPhone.getDcTracker(n2) != null) {
            this.mPhone.getDcTracker(n2).requestNetwork(networkRequest, n, message);
        }
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter object, String[] arrstring) {
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter((Writer)object, "  ");
        indentingPrintWriter.println("Network Requests:");
        indentingPrintWriter.increaseIndent();
        for (Map.Entry<NetworkRequest, Integer> entry : this.mNetworkRequests.entrySet()) {
            object = entry.getKey();
            int n = entry.getValue();
            StringBuilder object2 = new StringBuilder();
            object2.append(object);
            if (n != -1) {
                object = new StringBuilder();
                ((StringBuilder)object).append(" applied on ");
                ((StringBuilder)object).append(n);
                object = ((StringBuilder)object).toString();
            } else {
                object = " not applied";
            }
            object2.append((String)object);
            indentingPrintWriter.println(object2.toString());
        }
        this.mLocalLog.dump(fileDescriptor, (PrintWriter)indentingPrintWriter, arrstring);
        indentingPrintWriter.decreaseIndent();
    }

    protected void log(String string) {
        Rlog.d((String)this.LOG_TAG, (String)string);
    }

    protected void logl(String string) {
        this.log(string);
        this.mLocalLog.log(string);
    }

    public void needNetworkFor(NetworkRequest networkRequest, int n) {
        Message message = this.mInternalHandler.obtainMessage(3);
        message.obj = networkRequest;
        message.sendToTarget();
    }

    public void releaseNetworkFor(NetworkRequest networkRequest) {
        Message message = this.mInternalHandler.obtainMessage(4);
        message.obj = networkRequest;
        message.sendToTarget();
    }

    private class InternalHandler
    extends Handler {
        public InternalHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message object) {
            switch (object.what) {
                default: {
                    break;
                }
                case 6: {
                    Bundle bundle = object.getData();
                    if (bundle.getInt("extra_request_type") != 2) break;
                    NetworkRequest networkRequest = (NetworkRequest)bundle.getParcelable("extra_network_request");
                    boolean bl = bundle.getBoolean("extra_success");
                    int n = bundle.getInt("extra_transport_type");
                    object = (TransportManager.HandoverParams)TelephonyNetworkFactory.this.mPendingHandovers.remove(object);
                    if (object != null) {
                        TelephonyNetworkFactory.this.onDataHandoverSetupCompleted(networkRequest, bl, n, (TransportManager.HandoverParams)object);
                        break;
                    }
                    TelephonyNetworkFactory.this.logl("Handover completed but cannot find handover entry!");
                    break;
                }
                case 5: {
                    object = (TransportManager.HandoverParams)((AsyncResult)object.obj).result;
                    TelephonyNetworkFactory.this.onDataHandoverNeeded(object.apnType, object.targetTransport, (TransportManager.HandoverParams)object);
                    break;
                }
                case 4: {
                    TelephonyNetworkFactory.this.onReleaseNetworkFor(object);
                    break;
                }
                case 3: {
                    TelephonyNetworkFactory.this.onNeedNetworkFor(object);
                    break;
                }
                case 2: {
                    TelephonyNetworkFactory.this.onSubIdChange();
                    break;
                }
                case 1: {
                    TelephonyNetworkFactory.this.onActivePhoneSwitch();
                }
            }
        }
    }

}

