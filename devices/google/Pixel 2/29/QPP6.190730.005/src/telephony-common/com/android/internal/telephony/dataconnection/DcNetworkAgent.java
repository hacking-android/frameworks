/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.net.LinkProperties
 *  android.net.NattKeepalivePacketData
 *  android.net.NetworkAgent
 *  android.net.NetworkCapabilities
 *  android.net.NetworkInfo
 *  android.net.NetworkMisc
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Message
 *  android.telephony.AccessNetworkConstants
 *  android.telephony.Rlog
 *  android.telephony.ServiceState
 *  android.util.LocalLog
 *  android.util.SparseArray
 *  com.android.internal.util.IndentingPrintWriter
 */
package com.android.internal.telephony.dataconnection;

import android.content.Context;
import android.net.LinkProperties;
import android.net.NattKeepalivePacketData;
import android.net.NetworkAgent;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkMisc;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.telephony.AccessNetworkConstants;
import android.telephony.Rlog;
import android.telephony.ServiceState;
import android.util.LocalLog;
import android.util.SparseArray;
import com.android.internal.telephony.CommandsInterface;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.dataconnection.DataConnection;
import com.android.internal.telephony.dataconnection.DcTracker;
import com.android.internal.telephony.dataconnection.KeepaliveStatus;
import com.android.internal.util.IndentingPrintWriter;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.concurrent.atomic.AtomicInteger;

public class DcNetworkAgent
extends NetworkAgent {
    private static AtomicInteger sSerialNumber = new AtomicInteger(0);
    public final DcKeepaliveTracker keepaliveTracker = new DcKeepaliveTracker();
    private DataConnection mDataConnection;
    private final LocalLog mNetCapsLocalLog = new LocalLog(50);
    private NetworkCapabilities mNetworkCapabilities;
    private Phone mPhone;
    private String mTag;
    private int mTransportType;

    private DcNetworkAgent(DataConnection dataConnection, String string, Phone object, NetworkInfo networkInfo, int n, NetworkMisc networkMisc, int n2, int n3) {
        super(dataConnection.getHandler().getLooper(), ((Phone)object).getContext(), string, networkInfo, dataConnection.getNetworkCapabilities(), dataConnection.getLinkProperties(), n, networkMisc, n2);
        this.mTag = string;
        this.mPhone = object;
        this.mNetworkCapabilities = dataConnection.getNetworkCapabilities();
        this.mTransportType = n3;
        this.mDataConnection = dataConnection;
        object = new StringBuilder();
        ((StringBuilder)object).append(string);
        ((StringBuilder)object).append(" created for data connection ");
        ((StringBuilder)object).append(dataConnection.getName());
        this.logd(((StringBuilder)object).toString());
    }

    public static DcNetworkAgent createDcNetworkAgent(DataConnection dataConnection, Phone phone, NetworkInfo networkInfo, int n, NetworkMisc networkMisc, int n2, int n3) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DcNetworkAgent-");
        stringBuilder.append(sSerialNumber.incrementAndGet());
        return new DcNetworkAgent(dataConnection, stringBuilder.toString(), phone, networkInfo, n, networkMisc, n2, n3);
    }

    private void logd(String string) {
        Rlog.d((String)this.mTag, (String)string);
    }

    private void loge(String string) {
        Rlog.e((String)this.mTag, (String)string);
    }

    public void acquireOwnership(DataConnection dataConnection, int n) {
        synchronized (this) {
            this.mDataConnection = dataConnection;
            this.mTransportType = n;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(dataConnection.getName());
            stringBuilder.append(" acquired the ownership of this agent.");
            this.logd(stringBuilder.toString());
            return;
        }
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
        printWriter = new IndentingPrintWriter((Writer)printWriter, "  ");
        printWriter.println(this.toString());
        printWriter.increaseIndent();
        printWriter.println("Net caps logs:");
        this.mNetCapsLocalLog.dump(fileDescriptor, printWriter, arrstring);
        printWriter.decreaseIndent();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void networkStatus(int n, String string) {
        synchronized (this) {
            if (this.mDataConnection == null) {
                this.loge("networkStatus called on no-owner DcNetworkAgent!");
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("validation status: ");
            stringBuilder.append(n);
            stringBuilder.append(" with redirection URL: ");
            stringBuilder.append(string);
            this.logd(stringBuilder.toString());
            DcTracker dcTracker = this.mPhone.getDcTracker(this.mTransportType);
            if (dcTracker != null) {
                dcTracker.obtainMessage(270380, n, 0, (Object)string).sendToTarget();
            }
            return;
        }
    }

    protected void pollLceData() {
        synchronized (this) {
            if (this.mDataConnection == null) {
                this.loge("pollLceData called on no-owner DcNetworkAgent!");
                return;
            }
            if (this.mPhone.getLceStatus() == 1 && this.mTransportType == 1) {
                this.mPhone.mCi.pullLceData(this.mDataConnection.obtainMessage(262158));
            }
            return;
        }
    }

    public void releaseOwnership(DataConnection object) {
        synchronized (this) {
            if (this.mDataConnection == null) {
                this.loge("releaseOwnership called on no-owner DcNetworkAgent!");
                return;
            }
            if (this.mDataConnection != object) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("releaseOwnership: This agent belongs to ");
                stringBuilder.append(this.mDataConnection.getName());
                stringBuilder.append(", ignored the request from ");
                stringBuilder.append(object.getName());
                this.log(stringBuilder.toString());
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Data connection ");
            ((StringBuilder)object).append(this.mDataConnection.getName());
            ((StringBuilder)object).append(" released the ownership.");
            this.logd(((StringBuilder)object).toString());
            this.mDataConnection = null;
            return;
        }
    }

    public void sendLinkProperties(LinkProperties object, DataConnection dataConnection) {
        synchronized (this) {
            if (this.mDataConnection == null) {
                this.loge("sendLinkProperties called on no-owner DcNetworkAgent!");
                return;
            }
            if (this.mDataConnection != dataConnection) {
                object = new StringBuilder();
                ((StringBuilder)object).append("sendLinkProperties: This agent belongs to ");
                ((StringBuilder)object).append(this.mDataConnection.getName());
                ((StringBuilder)object).append(", ignored the request from ");
                ((StringBuilder)object).append(dataConnection.getName());
                this.loge(((StringBuilder)object).toString());
                return;
            }
            this.sendLinkProperties((LinkProperties)object);
            return;
        }
    }

    public void sendNetworkCapabilities(NetworkCapabilities object, DataConnection object2) {
        synchronized (this) {
            if (this.mDataConnection == null) {
                this.loge("sendNetworkCapabilities called on no-owner DcNetworkAgent!");
                return;
            }
            if (this.mDataConnection != object2) {
                object = new StringBuilder();
                ((StringBuilder)object).append("sendNetworkCapabilities: This agent belongs to ");
                ((StringBuilder)object).append(this.mDataConnection.getName());
                ((StringBuilder)object).append(", ignored the request from ");
                ((StringBuilder)object).append(object2.getName());
                this.loge(((StringBuilder)object).toString());
                return;
            }
            if (!object.equals((Object)this.mNetworkCapabilities)) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Changed from ");
                ((StringBuilder)object2).append((Object)this.mNetworkCapabilities);
                ((StringBuilder)object2).append(" to ");
                ((StringBuilder)object2).append(object);
                ((StringBuilder)object2).append(", Data RAT=");
                ((StringBuilder)object2).append(this.mPhone.getServiceState().getRilDataRadioTechnology());
                ((StringBuilder)object2).append(", dc=");
                ((StringBuilder)object2).append(this.mDataConnection.getName());
                object2 = ((StringBuilder)object2).toString();
                this.logd((String)object2);
                this.mNetCapsLocalLog.log((String)object2);
                this.mNetworkCapabilities = object;
            }
            this.sendNetworkCapabilities((NetworkCapabilities)object);
            return;
        }
    }

    public void sendNetworkInfo(NetworkInfo object, DataConnection dataConnection) {
        synchronized (this) {
            if (this.mDataConnection == null) {
                this.loge("sendNetworkInfo called on no-owner DcNetworkAgent!");
                return;
            }
            if (this.mDataConnection != dataConnection) {
                object = new StringBuilder();
                ((StringBuilder)object).append("sendNetworkInfo: This agent belongs to ");
                ((StringBuilder)object).append(this.mDataConnection.getName());
                ((StringBuilder)object).append(", ignored the request from ");
                ((StringBuilder)object).append(dataConnection.getName());
                this.loge(((StringBuilder)object).toString());
                return;
            }
            this.sendNetworkInfo((NetworkInfo)object);
            return;
        }
    }

    public void sendNetworkScore(int n, DataConnection dataConnection) {
        synchronized (this) {
            if (this.mDataConnection == null) {
                this.loge("sendNetworkScore called on no-owner DcNetworkAgent!");
                return;
            }
            if (this.mDataConnection != dataConnection) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("sendNetworkScore: This agent belongs to ");
                stringBuilder.append(this.mDataConnection.getName());
                stringBuilder.append(", ignored the request from ");
                stringBuilder.append(dataConnection.getName());
                this.loge(stringBuilder.toString());
                return;
            }
            this.sendNetworkScore(n);
            return;
        }
    }

    protected void startSocketKeepalive(Message message) {
        synchronized (this) {
            if (this.mDataConnection == null) {
                this.loge("startSocketKeepalive called on no-owner DcNetworkAgent!");
                return;
            }
            if (message.obj instanceof NattKeepalivePacketData) {
                this.mDataConnection.obtainMessage(262165, message.arg1, message.arg2, message.obj).sendToTarget();
            } else {
                this.onSocketKeepaliveEvent(message.arg1, -30);
            }
            return;
        }
    }

    protected void stopSocketKeepalive(Message message) {
        synchronized (this) {
            if (this.mDataConnection == null) {
                this.loge("stopSocketKeepalive called on no-owner DcNetworkAgent!");
                return;
            }
            this.mDataConnection.obtainMessage(262166, message.arg1, message.arg2, message.obj).sendToTarget();
            return;
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DcNetworkAgent: mDataConnection=");
        Object object = this.mDataConnection;
        object = object != null ? object.getName() : null;
        stringBuilder.append((String)object);
        stringBuilder.append(" mTransportType=");
        stringBuilder.append(AccessNetworkConstants.transportTypeToString((int)this.mTransportType));
        stringBuilder.append(" mNetworkCapabilities=");
        stringBuilder.append((Object)this.mNetworkCapabilities);
        return stringBuilder.toString();
    }

    protected void unwanted() {
        synchronized (this) {
            if (this.mDataConnection == null) {
                this.loge("Unwanted found called on no-owner DcNetworkAgent!");
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("unwanted called. Now tear down the data connection ");
            stringBuilder.append(this.mDataConnection.getName());
            this.logd(stringBuilder.toString());
            this.mDataConnection.tearDownAll("releasedByConnectivityService", 2, null);
            return;
        }
    }

    class DcKeepaliveTracker {
        private final SparseArray<KeepaliveRecord> mKeepalives = new SparseArray();

        DcKeepaliveTracker() {
        }

        int getHandleForSlot(int n) {
            for (int i = 0; i < this.mKeepalives.size(); ++i) {
                if (((KeepaliveRecord)this.mKeepalives.valueAt((int)i)).slotId != n) continue;
                return this.mKeepalives.keyAt(i);
            }
            return -1;
        }

        void handleKeepaliveStarted(int n, KeepaliveStatus keepaliveStatus) {
            block3 : {
                block2 : {
                    block0 : {
                        block1 : {
                            int n2 = keepaliveStatus.statusCode;
                            if (n2 == 0) break block0;
                            if (n2 == 1) break block1;
                            if (n2 == 2) break block2;
                            DcNetworkAgent dcNetworkAgent = DcNetworkAgent.this;
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("Invalid KeepaliveStatus Code: ");
                            stringBuilder.append(keepaliveStatus.statusCode);
                            dcNetworkAgent.logd(stringBuilder.toString());
                            break block3;
                        }
                        DcNetworkAgent.this.onSocketKeepaliveEvent(n, this.keepaliveStatusErrorToPacketKeepaliveError(keepaliveStatus.errorCode));
                        break block3;
                    }
                    DcNetworkAgent.this.onSocketKeepaliveEvent(n, 0);
                }
                DcNetworkAgent dcNetworkAgent = DcNetworkAgent.this;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Adding keepalive handle=");
                stringBuilder.append(keepaliveStatus.sessionHandle);
                stringBuilder.append(" slot = ");
                stringBuilder.append(n);
                dcNetworkAgent.logd(stringBuilder.toString());
                this.mKeepalives.put(keepaliveStatus.sessionHandle, (Object)new KeepaliveRecord(n, keepaliveStatus.statusCode));
            }
        }

        void handleKeepaliveStatus(KeepaliveStatus object) {
            block12 : {
                block13 : {
                    Object object2;
                    block14 : {
                        int n;
                        block11 : {
                            object2 = (KeepaliveRecord)this.mKeepalives.get(object.sessionHandle);
                            if (object2 == null) {
                                DcNetworkAgent dcNetworkAgent = DcNetworkAgent.this;
                                object2 = new StringBuilder();
                                ((StringBuilder)object2).append("Discarding keepalive event for different data connection:");
                                ((StringBuilder)object2).append(object);
                                dcNetworkAgent.loge(((StringBuilder)object2).toString());
                                return;
                            }
                            n = ((KeepaliveRecord)object2).currentStatus;
                            if (n == 0) break block11;
                            if (n != 1) {
                                if (n != 2) {
                                    object = DcNetworkAgent.this;
                                    StringBuilder stringBuilder = new StringBuilder();
                                    stringBuilder.append("Invalid Keepalive Status received, ");
                                    stringBuilder.append(((KeepaliveRecord)object2).currentStatus);
                                    ((DcNetworkAgent)((Object)object)).loge(stringBuilder.toString());
                                } else {
                                    n = object.statusCode;
                                    if (n != 0) {
                                        if (n != 1) {
                                            if (n != 2) {
                                                DcNetworkAgent dcNetworkAgent = DcNetworkAgent.this;
                                                object2 = new StringBuilder();
                                                ((StringBuilder)object2).append("Invalid Keepalive Status received, ");
                                                ((StringBuilder)object2).append(object.statusCode);
                                                dcNetworkAgent.loge(((StringBuilder)object2).toString());
                                            } else {
                                                DcNetworkAgent.this.loge("Invalid unsolicied Keepalive Pending Status!");
                                            }
                                        } else {
                                            DcNetworkAgent.this.onSocketKeepaliveEvent(((KeepaliveRecord)object2).slotId, this.keepaliveStatusErrorToPacketKeepaliveError(object.errorCode));
                                            ((KeepaliveRecord)object2).currentStatus = 1;
                                            this.mKeepalives.remove(object.sessionHandle);
                                        }
                                    } else {
                                        DcNetworkAgent.this.logd("Pending Keepalive received active status!");
                                        ((KeepaliveRecord)object2).currentStatus = 0;
                                        DcNetworkAgent.this.onSocketKeepaliveEvent(((KeepaliveRecord)object2).slotId, 0);
                                    }
                                }
                            } else {
                                DcNetworkAgent.this.logd("Inactive Keepalive received status!");
                                DcNetworkAgent.this.onSocketKeepaliveEvent(((KeepaliveRecord)object2).slotId, -31);
                            }
                            break block12;
                        }
                        n = object.statusCode;
                        if (n == 0) break block13;
                        if (n == 1) break block14;
                        if (n == 2) break block13;
                        DcNetworkAgent dcNetworkAgent = DcNetworkAgent.this;
                        object2 = new StringBuilder();
                        ((StringBuilder)object2).append("Invalid Keepalive Status received, ");
                        ((StringBuilder)object2).append(object.statusCode);
                        dcNetworkAgent.loge(((StringBuilder)object2).toString());
                        break block12;
                    }
                    DcNetworkAgent.this.logd("Keepalive received stopped status!");
                    DcNetworkAgent.this.onSocketKeepaliveEvent(((KeepaliveRecord)object2).slotId, 0);
                    ((KeepaliveRecord)object2).currentStatus = 1;
                    this.mKeepalives.remove(object.sessionHandle);
                    break block12;
                }
                DcNetworkAgent.this.loge("Active Keepalive received invalid status!");
            }
        }

        int keepaliveStatusErrorToPacketKeepaliveError(int n) {
            if (n != 0) {
                if (n != 1) {
                    if (n != 2) {
                        return -31;
                    }
                    return -32;
                }
                return -30;
            }
            return 0;
        }

        private class KeepaliveRecord {
            public int currentStatus;
            public int slotId;

            KeepaliveRecord(int n, int n2) {
                this.slotId = n;
                this.currentStatus = n2;
            }
        }

    }

}

