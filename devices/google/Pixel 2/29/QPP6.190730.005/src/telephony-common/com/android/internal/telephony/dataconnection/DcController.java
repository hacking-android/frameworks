/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.net.INetworkPolicyListener
 *  android.net.LinkAddress
 *  android.net.LinkProperties
 *  android.net.LinkProperties$CompareResult
 *  android.net.NetworkPolicyManager
 *  android.net.NetworkPolicyManager$Listener
 *  android.net.NetworkUtils
 *  android.os.AsyncResult
 *  android.os.Build
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Message
 *  android.telephony.DataFailCause
 *  android.telephony.PhoneStateListener
 *  android.telephony.Rlog
 *  android.telephony.TelephonyManager
 *  android.telephony.data.DataCallResponse
 *  com.android.internal.telephony.DctConstants
 *  com.android.internal.telephony.DctConstants$Activity
 *  com.android.internal.util.State
 *  com.android.internal.util.StateMachine
 */
package com.android.internal.telephony.dataconnection;

import android.content.Context;
import android.net.INetworkPolicyListener;
import android.net.LinkAddress;
import android.net.LinkProperties;
import android.net.NetworkPolicyManager;
import android.net.NetworkUtils;
import android.os.AsyncResult;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.telephony.DataFailCause;
import android.telephony.PhoneStateListener;
import android.telephony.Rlog;
import android.telephony.TelephonyManager;
import android.telephony.data.DataCallResponse;
import com.android.internal.telephony.CommandsInterface;
import com.android.internal.telephony.DctConstants;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.dataconnection.ApnContext;
import com.android.internal.telephony.dataconnection.DataConnection;
import com.android.internal.telephony.dataconnection.DataServiceManager;
import com.android.internal.telephony.dataconnection.DcTesterDeactivateAll;
import com.android.internal.telephony.dataconnection.DcTracker;
import com.android.internal.util.State;
import com.android.internal.util.StateMachine;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class DcController
extends StateMachine {
    private static final boolean DBG = true;
    private static final boolean VDBG = false;
    private final DataServiceManager mDataServiceManager;
    private final HashMap<Integer, DataConnection> mDcListActiveByCid = new HashMap();
    final ArrayList<DataConnection> mDcListAll = new ArrayList();
    private final DcTesterDeactivateAll mDcTesterDeactivateAll;
    private DccDefaultState mDccDefaultState;
    private final DcTracker mDct;
    private volatile boolean mExecutingCarrierChange;
    private final INetworkPolicyListener mListener;
    final NetworkPolicyManager mNetworkPolicyManager;
    private final Phone mPhone;
    private PhoneStateListener mPhoneStateListener;
    final TelephonyManager mTelephonyManager;

    private DcController(String object, Phone phone, DcTracker dcTracker, DataServiceManager dataServiceManager, Handler handler) {
        super((String)object, handler);
        object = null;
        this.mDccDefaultState = new DccDefaultState();
        this.mListener = new NetworkPolicyManager.Listener(){

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            public void onSubscriptionOverride(int n, int n2, int n3) {
                HashMap hashMap;
                if (DcController.this.mPhone == null) return;
                if (DcController.this.mPhone.getSubId() != n) {
                    return;
                }
                Object object = DcController.this.mDcListAll;
                synchronized (object) {
                    hashMap = new HashMap(DcController.this.mDcListActiveByCid);
                }
                object = hashMap.values().iterator();
                while (object.hasNext()) {
                    ((DataConnection)((Object)object.next())).onSubscriptionOverride(n2, n3);
                }
            }
        };
        this.setLogRecSize(300);
        this.log("E ctor");
        this.mPhone = phone;
        this.mDct = dcTracker;
        this.mDataServiceManager = dataServiceManager;
        this.addState((State)this.mDccDefaultState);
        this.setInitialState((State)this.mDccDefaultState);
        this.log("X ctor");
        this.mPhoneStateListener = new PhoneStateListener(handler.getLooper()){

            public void onCarrierNetworkChange(boolean bl) {
                DcController.this.mExecutingCarrierChange = bl;
            }
        };
        this.mTelephonyManager = (TelephonyManager)phone.getContext().getSystemService("phone");
        this.mNetworkPolicyManager = (NetworkPolicyManager)phone.getContext().getSystemService("netpolicy");
        if (Build.IS_DEBUGGABLE) {
            object = new DcTesterDeactivateAll(this.mPhone, this, this.getHandler());
        }
        this.mDcTesterDeactivateAll = object;
        object = this.mTelephonyManager;
        if (object != null) {
            object.listen(this.mPhoneStateListener, 65536);
        }
    }

    private void lr(String string) {
        this.logAndAddLogRec(string);
    }

    public static DcController makeDcc(Phone phone, DcTracker dcTracker, DataServiceManager dataServiceManager, Handler handler, String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Dcc");
        stringBuilder.append(string);
        return new DcController(stringBuilder.toString(), phone, dcTracker, dataServiceManager, handler);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void addActiveDcByCid(DataConnection dataConnection) {
        Serializable serializable;
        if (dataConnection.mCid < 0) {
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("addActiveDcByCid dc.mCid < 0 dc=");
            ((StringBuilder)serializable).append((Object)dataConnection);
            this.log(((StringBuilder)serializable).toString());
        }
        serializable = this.mDcListAll;
        synchronized (serializable) {
            this.mDcListActiveByCid.put(dataConnection.mCid, dataConnection);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void addDc(DataConnection dataConnection) {
        ArrayList<DataConnection> arrayList = this.mDcListAll;
        synchronized (arrayList) {
            this.mDcListAll.add(dataConnection);
            return;
        }
    }

    void dispose() {
        this.log("dispose: call quiteNow()");
        TelephonyManager telephonyManager = this.mTelephonyManager;
        if (telephonyManager != null) {
            telephonyManager.listen(this.mPhoneStateListener, 0);
        }
        this.quitNow();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void dump(FileDescriptor arrayList, PrintWriter printWriter, String[] object) {
        super.dump((FileDescriptor)((Object)arrayList), printWriter, (String[])object);
        arrayList = new StringBuilder();
        ((StringBuilder)((Object)arrayList)).append(" mPhone=");
        ((StringBuilder)((Object)arrayList)).append(this.mPhone);
        printWriter.println(((StringBuilder)((Object)arrayList)).toString());
        arrayList = this.mDcListAll;
        synchronized (arrayList) {
            object = new StringBuilder();
            ((StringBuilder)object).append(" mDcListAll=");
            ((StringBuilder)object).append(this.mDcListAll);
            printWriter.println(((StringBuilder)object).toString());
            object = new StringBuilder();
            ((StringBuilder)object).append(" mDcListActiveByCid=");
            ((StringBuilder)object).append(this.mDcListActiveByCid);
            printWriter.println(((StringBuilder)object).toString());
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public DataConnection getActiveDcByCid(int n) {
        ArrayList<DataConnection> arrayList = this.mDcListAll;
        synchronized (arrayList) {
            return this.mDcListActiveByCid.get(n);
        }
    }

    protected String getWhatToString(int n) {
        return DataConnection.cmdToString(n);
    }

    boolean isExecutingCarrierChange() {
        return this.mExecutingCarrierChange;
    }

    protected void log(String string) {
        Rlog.d((String)this.getName(), (String)string);
    }

    protected void loge(String string) {
        Rlog.e((String)this.getName(), (String)string);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void removeActiveDcByCid(DataConnection dataConnection) {
        ArrayList<DataConnection> arrayList = this.mDcListAll;
        synchronized (arrayList) {
            if (this.mDcListActiveByCid.remove(dataConnection.mCid) == null) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("removeActiveDcByCid removedDc=null dc=");
                stringBuilder.append((Object)dataConnection);
                this.log(stringBuilder.toString());
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void removeDc(DataConnection dataConnection) {
        ArrayList<DataConnection> arrayList = this.mDcListAll;
        synchronized (arrayList) {
            this.mDcListActiveByCid.remove(dataConnection.mCid);
            this.mDcListAll.remove((Object)dataConnection);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public String toString() {
        ArrayList<DataConnection> arrayList = this.mDcListAll;
        synchronized (arrayList) {
            CharSequence charSequence = new StringBuilder();
            charSequence.append("mDcListAll=");
            charSequence.append(this.mDcListAll);
            charSequence.append(" mDcListActiveByCid=");
            charSequence.append(this.mDcListActiveByCid);
            return charSequence.toString();
        }
    }

    private class DccDefaultState
    extends State {
        private DccDefaultState() {
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void onDataStateChanged(ArrayList<DataCallResponse> object) {
            Object object2;
            Object object3;
            Object object4;
            Object object5 = DcController.this.mDcListAll;
            synchronized (object5) {
                object2 = new ArrayList(DcController.this.mDcListAll);
                object3 = new Object(DcController.this.mDcListActiveByCid);
            }
            Object object6 = DcController.this;
            object5 = new StringBuilder();
            ((StringBuilder)object5).append("onDataStateChanged: dcsList=");
            ((StringBuilder)object5).append(object);
            ((StringBuilder)object5).append(" dcListActiveByCid=");
            ((StringBuilder)object5).append(object3);
            ((DcController)((Object)object6)).lr(((StringBuilder)object5).toString());
            object5 = new HashMap();
            Object object7 = ((ArrayList)object).iterator();
            while (object7.hasNext()) {
                object6 = object7.next();
                ((HashMap)object5).put(object6.getId(), object6);
            }
            object6 = new ArrayList();
            for (DataConnection dataConnection : object3.values()) {
                if (((HashMap)object5).get(dataConnection.mCid) != null) continue;
                object7 = DcController.this;
                object4 = new StringBuilder();
                ((StringBuilder)object4).append("onDataStateChanged: add to retry dc=");
                ((StringBuilder)object4).append((Object)dataConnection);
                ((DcController)((Object)object7)).log(((StringBuilder)object4).toString());
                ((ArrayList)object6).add(dataConnection);
            }
            object4 = DcController.this;
            object7 = new StringBuilder();
            ((StringBuilder)object7).append("onDataStateChanged: dcsToRetry=");
            ((StringBuilder)object7).append(object6);
            ((DcController)((Object)object4)).log(((StringBuilder)object7).toString());
            object7 = new ArrayList();
            boolean bl = false;
            boolean bl2 = false;
            object4 = ((ArrayList)object).iterator();
            object = object3;
            object3 = object5;
            while (object4.hasNext()) {
                DataConnection dataConnection;
                dataConnection = (DataCallResponse)object4.next();
                object5 = (DataConnection)((Object)((HashMap)object).get(dataConnection.getId()));
                if (object5 == null) {
                    DcController.this.loge("onDataStateChanged: no associated DC yet, ignore");
                    continue;
                }
                Object object8 = ((DataConnection)((Object)object5)).getApnContexts();
                if (object8.size() == 0) {
                    DcController.this.loge("onDataStateChanged: no connected apns, ignore");
                } else {
                    int n;
                    Object object9 = DcController.this;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("onDataStateChanged: Found ConnId=");
                    stringBuilder.append(dataConnection.getId());
                    stringBuilder.append(" newState=");
                    stringBuilder.append(dataConnection.toString());
                    ((DcController)((Object)object9)).log(stringBuilder.toString());
                    if (dataConnection.getLinkStatus() == 0) {
                        if (DcController.access$800((DcController)DcController.this).isCleanupRequired.get()) {
                            ((ArrayList)object7).addAll(object8);
                            DcController.access$800((DcController)DcController.this).isCleanupRequired.set(false);
                        } else {
                            n = DataFailCause.getFailCause((int)dataConnection.getCause());
                            if (DataFailCause.isRadioRestartFailure((Context)DcController.this.mPhone.getContext(), (int)n, (int)DcController.this.mPhone.getSubId())) {
                                object8 = DcController.this;
                                object5 = new StringBuilder();
                                ((StringBuilder)object5).append("onDataStateChanged: X restart radio, failCause=");
                                ((StringBuilder)object5).append(n);
                                ((DcController)((Object)object8)).log(((StringBuilder)object5).toString());
                                DcController.this.mDct.sendRestartRadio();
                            } else if (DcController.this.mDct.isPermanentFailure(n)) {
                                object9 = DcController.this;
                                object5 = new StringBuilder();
                                ((StringBuilder)object5).append("onDataStateChanged: inactive, add to cleanup list. failCause=");
                                ((StringBuilder)object5).append(n);
                                ((DcController)((Object)object9)).log(((StringBuilder)object5).toString());
                                ((ArrayList)object7).addAll(object8);
                            } else {
                                object8 = DcController.this;
                                object9 = new StringBuilder();
                                ((StringBuilder)object9).append("onDataStateChanged: inactive, add to retry list. failCause=");
                                ((StringBuilder)object9).append(n);
                                ((DcController)((Object)object8)).log(((StringBuilder)object9).toString());
                                ((ArrayList)object6).add(object5);
                            }
                        }
                    } else {
                        object9 = ((DataConnection)((Object)object5)).updateLinkProperty((DataCallResponse)dataConnection);
                        if (((DataConnection.UpdateLinkPropertyResult)object9).oldLp.equals((Object)((DataConnection.UpdateLinkPropertyResult)object9).newLp)) {
                            DcController.this.log("onDataStateChanged: no change");
                        } else if (((DataConnection.UpdateLinkPropertyResult)object9).oldLp.isIdenticalInterfaceName(((DataConnection.UpdateLinkPropertyResult)object9).newLp)) {
                            if (((DataConnection.UpdateLinkPropertyResult)object9).oldLp.isIdenticalDnses(((DataConnection.UpdateLinkPropertyResult)object9).newLp) && ((DataConnection.UpdateLinkPropertyResult)object9).oldLp.isIdenticalRoutes(((DataConnection.UpdateLinkPropertyResult)object9).newLp) && ((DataConnection.UpdateLinkPropertyResult)object9).oldLp.isIdenticalHttpProxy(((DataConnection.UpdateLinkPropertyResult)object9).newLp) && ((DataConnection.UpdateLinkPropertyResult)object9).oldLp.isIdenticalAddresses(((DataConnection.UpdateLinkPropertyResult)object9).newLp)) {
                                DcController.this.log("onDataStateChanged: no changes");
                            } else {
                                stringBuilder = ((DataConnection.UpdateLinkPropertyResult)object9).oldLp.compareAddresses(((DataConnection.UpdateLinkPropertyResult)object9).newLp);
                                object5 = DcController.this;
                                StringBuilder stringBuilder2 = new StringBuilder();
                                stringBuilder2.append("onDataStateChanged: oldLp=");
                                stringBuilder2.append((Object)((DataConnection.UpdateLinkPropertyResult)object9).oldLp);
                                stringBuilder2.append(" newLp=");
                                stringBuilder2.append((Object)((DataConnection.UpdateLinkPropertyResult)object9).newLp);
                                stringBuilder2.append(" car=");
                                stringBuilder2.append((Object)stringBuilder);
                                ((DcController)((Object)object5)).log(stringBuilder2.toString());
                                n = 0;
                                for (LinkAddress linkAddress : ((LinkProperties.CompareResult)stringBuilder).added) {
                                    int n2;
                                    block35 : {
                                        LinkAddress linkAddress2;
                                        object5 = ((LinkProperties.CompareResult)stringBuilder).removed.iterator();
                                        do {
                                            n2 = n;
                                            if (!object5.hasNext()) break block35;
                                        } while (!NetworkUtils.addressTypeMatches((InetAddress)(linkAddress2 = (LinkAddress)object5.next()).getAddress(), (InetAddress)linkAddress.getAddress()));
                                        n2 = 1;
                                    }
                                    n = n2;
                                }
                                if (n != 0) {
                                    object5 = DcController.this;
                                    stringBuilder = new StringBuilder();
                                    stringBuilder.append("onDataStateChanged: addr change, cleanup apns=");
                                    stringBuilder.append(object8);
                                    stringBuilder.append(" oldLp=");
                                    stringBuilder.append((Object)((DataConnection.UpdateLinkPropertyResult)object9).oldLp);
                                    stringBuilder.append(" newLp=");
                                    stringBuilder.append((Object)((DataConnection.UpdateLinkPropertyResult)object9).newLp);
                                    ((DcController)((Object)object5)).log(stringBuilder.toString());
                                    ((ArrayList)object7).addAll(object8);
                                } else {
                                    DcController.this.log("onDataStateChanged: simple change");
                                    object5 = object8.iterator();
                                    while (object5.hasNext()) {
                                        object8 = (ApnContext)object5.next();
                                        DcController.this.mPhone.notifyDataConnection(((ApnContext)object8).getApnType());
                                    }
                                }
                            }
                        } else {
                            ((ArrayList)object7).addAll(object8);
                            object9 = DcController.this;
                            object5 = new StringBuilder();
                            ((StringBuilder)object5).append("onDataStateChanged: interface change, cleanup apns=");
                            ((StringBuilder)object5).append(object8);
                            ((DcController)((Object)object9)).log(((StringBuilder)object5).toString());
                        }
                    }
                }
                if (dataConnection.getLinkStatus() == 2) {
                    bl2 = true;
                }
                if (dataConnection.getLinkStatus() != 1) continue;
                bl = true;
            }
            if (bl && !bl2) {
                DcController.this.log("onDataStateChanged: Data Activity updated to DORMANT. stopNetStatePoll");
                DcController.this.mDct.sendStopNetStatPoll(DctConstants.Activity.DORMANT);
            } else {
                object3 = DcController.this;
                object = new StringBuilder();
                ((StringBuilder)object).append("onDataStateChanged: Data Activity updated to NONE. isAnyDataCallActive = ");
                ((StringBuilder)object).append(bl2);
                ((StringBuilder)object).append(" isAnyDataCallDormant = ");
                ((StringBuilder)object).append(bl);
                ((DcController)((Object)object3)).log(((StringBuilder)object).toString());
                if (bl2) {
                    DcController.this.mDct.sendStartNetStatPoll(DctConstants.Activity.NONE);
                }
            }
            object3 = DcController.this;
            object = new StringBuilder();
            ((StringBuilder)object).append("onDataStateChanged: dcsToRetry=");
            ((StringBuilder)object).append(object6);
            ((StringBuilder)object).append(" apnsToCleanup=");
            ((StringBuilder)object).append(object7);
            ((DcController)((Object)object3)).lr(((StringBuilder)object).toString());
            object = ((ArrayList)object7).iterator();
            while (object.hasNext()) {
                object3 = (ApnContext)object.next();
                DcController.this.mDct.cleanUpConnection((ApnContext)object3);
            }
            object2 = ((ArrayList)object6).iterator();
            while (object2.hasNext()) {
                object3 = (DataConnection)((Object)object2.next());
                object = DcController.this;
                object5 = new StringBuilder();
                ((StringBuilder)object5).append("onDataStateChanged: send EVENT_LOST_CONNECTION dc.mTag=");
                ((StringBuilder)object5).append(((DataConnection)object3).mTag);
                ((DcController)((Object)object)).log(((StringBuilder)object5).toString());
                object3.sendMessage(262153, ((DataConnection)object3).mTag);
            }
            return;
        }

        public void enter() {
            if (DcController.this.mPhone != null && DcController.this.mDataServiceManager.getTransportType() == 1) {
                DcController.access$200((DcController)DcController.this).mCi.registerForRilConnected(DcController.this.getHandler(), 262149, null);
            }
            DcController.this.mDataServiceManager.registerForDataCallListChanged(DcController.this.getHandler(), 262151);
            if (DcController.this.mNetworkPolicyManager != null) {
                DcController.this.mNetworkPolicyManager.registerListener(DcController.this.mListener);
            }
        }

        public void exit() {
            Phone phone = DcController.this.mPhone;
            boolean bl = false;
            boolean bl2 = phone != null;
            if (DcController.this.mDataServiceManager.getTransportType() == 1) {
                bl = true;
            }
            if (bl2 & bl) {
                DcController.access$200((DcController)DcController.this).mCi.unregisterForRilConnected(DcController.this.getHandler());
            }
            DcController.this.mDataServiceManager.unregisterForDataCallListChanged(DcController.this.getHandler());
            if (DcController.this.mDcTesterDeactivateAll != null) {
                DcController.this.mDcTesterDeactivateAll.dispose();
            }
            if (DcController.this.mNetworkPolicyManager != null) {
                DcController.this.mNetworkPolicyManager.unregisterListener(DcController.this.mListener);
            }
        }

        public boolean processMessage(Message message) {
            int n = message.what;
            if (n != 262149) {
                if (n == 262151) {
                    message = (AsyncResult)message.obj;
                    if (message.exception == null) {
                        this.onDataStateChanged((ArrayList)message.result);
                    } else {
                        DcController.this.log("DccDefaultState: EVENT_DATA_STATE_CHANGED: exception; likely radio not available, ignore");
                    }
                }
            } else {
                message = (AsyncResult)message.obj;
                if (message.exception == null) {
                    DcController dcController = DcController.this;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("DccDefaultState: msg.what=EVENT_RIL_CONNECTED mRilVersion=");
                    stringBuilder.append(message.result);
                    dcController.log(stringBuilder.toString());
                } else {
                    DcController.this.log("DccDefaultState: Unexpected exception on EVENT_RIL_CONNECTED");
                }
            }
            return true;
        }
    }

}

