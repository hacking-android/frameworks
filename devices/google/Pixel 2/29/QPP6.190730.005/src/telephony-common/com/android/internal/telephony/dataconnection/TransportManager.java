/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.os.AsyncResult
 *  android.os.Handler
 *  android.os.Message
 *  android.os.RegistrantList
 *  android.os.SystemProperties
 *  android.telephony.AccessNetworkConstants
 *  android.telephony.Rlog
 *  android.telephony.data.ApnSetting
 *  android.util.LocalLog
 *  android.util.SparseArray
 *  android.util.SparseIntArray
 *  com.android.internal.annotations.VisibleForTesting
 *  com.android.internal.telephony.dataconnection.-$
 *  com.android.internal.telephony.dataconnection.-$$Lambda
 *  com.android.internal.telephony.dataconnection.-$$Lambda$TransportManager
 *  com.android.internal.telephony.dataconnection.-$$Lambda$TransportManager$vVwfnOC5CydwmAdimpil6w6F3zk
 *  com.android.internal.util.ArrayUtils
 *  com.android.internal.util.IndentingPrintWriter
 */
package com.android.internal.telephony.dataconnection;

import android.os.AsyncResult;
import android.os.Handler;
import android.os.Message;
import android.os.RegistrantList;
import android.os.SystemProperties;
import android.telephony.AccessNetworkConstants;
import android.telephony.Rlog;
import android.telephony.data.ApnSetting;
import android.util.LocalLog;
import android.util.SparseArray;
import android.util.SparseIntArray;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.telephony.HalVersion;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.RIL;
import com.android.internal.telephony.dataconnection.-$;
import com.android.internal.telephony.dataconnection.AccessNetworksManager;
import com.android.internal.telephony.dataconnection._$$Lambda$TransportManager$Dk_40vYVb_woK0GbaXsHT3AecbY;
import com.android.internal.telephony.dataconnection._$$Lambda$TransportManager$vVwfnOC5CydwmAdimpil6w6F3zk;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.IndentingPrintWriter;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.IntFunction;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TransportManager
extends Handler {
    private static final Map<Integer, Integer> ACCESS_NETWORK_TRANSPORT_TYPE_MAP;
    private static final int EVENT_QUALIFIED_NETWORKS_CHANGED = 1;
    private static final int EVENT_UPDATE_AVAILABLE_NETWORKS = 2;
    public static final String IWLAN_OPERATION_MODE_AP_ASSISTED = "AP-assisted";
    public static final String IWLAN_OPERATION_MODE_DEFAULT = "default";
    public static final String IWLAN_OPERATION_MODE_LEGACY = "legacy";
    public static final String SYSTEM_PROPERTIES_IWLAN_OPERATION_MODE = "ro.telephony.iwlan_operation_mode";
    private static final String TAG;
    private AccessNetworksManager mAccessNetworksManager;
    private final LinkedList<List<AccessNetworksManager.QualifiedNetworks>> mAvailableNetworksList;
    private final int[] mAvailableTransports;
    private final SparseArray<int[]> mCurrentAvailableNetworks;
    private final Map<Integer, Integer> mCurrentTransports;
    private final RegistrantList mHandoverNeededEventRegistrants;
    private final LocalLog mLocalLog = new LocalLog(100);
    private final SparseIntArray mPendingHandoverApns;
    private final Phone mPhone;

    static {
        TAG = TransportManager.class.getSimpleName();
        ACCESS_NETWORK_TRANSPORT_TYPE_MAP = new HashMap<Integer, Integer>();
        Object object = ACCESS_NETWORK_TRANSPORT_TYPE_MAP;
        Object object2 = 1;
        object.put((Integer)object2, (Integer)object2);
        Map<Integer, Integer> map = ACCESS_NETWORK_TRANSPORT_TYPE_MAP;
        object = 2;
        map.put((Integer)object, (Integer)object2);
        map = ACCESS_NETWORK_TRANSPORT_TYPE_MAP;
        map.put(3, (Integer)object2);
        map = ACCESS_NETWORK_TRANSPORT_TYPE_MAP;
        map.put(4, (Integer)object2);
        object2 = ACCESS_NETWORK_TRANSPORT_TYPE_MAP;
        object2.put(5, object);
    }

    public TransportManager(Phone phone) {
        this.mPhone = phone;
        this.mCurrentAvailableNetworks = new SparseArray();
        this.mCurrentTransports = new ConcurrentHashMap<Integer, Integer>();
        this.mPendingHandoverApns = new SparseIntArray();
        this.mHandoverNeededEventRegistrants = new RegistrantList();
        this.mAvailableNetworksList = new LinkedList();
        if (this.isInLegacyMode()) {
            this.log("operates in legacy mode.");
            this.mAvailableTransports = new int[]{1};
        } else {
            this.log("operates in AP-assisted mode.");
            this.mAccessNetworksManager = new AccessNetworksManager(phone);
            this.mAccessNetworksManager.registerForQualifiedNetworksChanged(this, 1);
            this.mAvailableTransports = new int[]{1, 2};
        }
    }

    private static boolean areNetworksValid(AccessNetworksManager.QualifiedNetworks arrn) {
        if (arrn.qualifiedNetworks == null) {
            return false;
        }
        for (int n : arrn.qualifiedNetworks) {
            if (ACCESS_NETWORK_TRANSPORT_TYPE_MAP.containsKey(n)) continue;
            return false;
        }
        return true;
    }

    private boolean isHandoverNeeded(AccessNetworksManager.QualifiedNetworks qualifiedNetworks) {
        int n = qualifiedNetworks.apnType;
        int[] arrn = qualifiedNetworks.qualifiedNetworks;
        int[] arrn2 = (int[])this.mCurrentAvailableNetworks.get(n);
        if (ArrayUtils.isEmpty((int[])arrn2) && ACCESS_NETWORK_TRANSPORT_TYPE_MAP.get(arrn[0]) == 2) {
            return true;
        }
        if (!ArrayUtils.isEmpty((int[])arrn) && !ArrayUtils.isEmpty((int[])arrn2)) {
            if (this.mPendingHandoverApns.get(qualifiedNetworks.apnType) == ACCESS_NETWORK_TRANSPORT_TYPE_MAP.get(arrn[0]).intValue()) {
                this.log("Handover not needed. There is already an ongoing handover.");
                return false;
            }
            return ACCESS_NETWORK_TRANSPORT_TYPE_MAP.get(arrn[0]).equals(this.getCurrentTransport(qualifiedNetworks.apnType)) ^ true;
        }
        return false;
    }

    private boolean isHandoverPending() {
        boolean bl = this.mPendingHandoverApns.size() > 0;
        return bl;
    }

    static /* synthetic */ String lambda$dump$1(int n) {
        return AccessNetworkConstants.transportTypeToString((int)n);
    }

    private void log(String string) {
        Rlog.d((String)TAG, (String)string);
    }

    private void loge(String string) {
        Rlog.e((String)TAG, (String)string);
    }

    private void logl(String string) {
        this.log(string);
        this.mLocalLog.log(string);
    }

    private void setCurrentTransport(int n, int n2) {
        synchronized (this) {
            this.mCurrentTransports.put(n, n2);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("setCurrentTransport: apnType=");
            stringBuilder.append(ApnSetting.getApnTypeString((int)n));
            stringBuilder.append(", transport=");
            stringBuilder.append(AccessNetworkConstants.transportTypeToString((int)n2));
            this.logl(stringBuilder.toString());
            return;
        }
    }

    private void updateAvailableNetworks() {
        if (this.isHandoverPending()) {
            this.log("There's ongoing handover. Will update networks once handover completed.");
            return;
        }
        if (this.mAvailableNetworksList.size() == 0) {
            this.log("Nothing in the available network list queue.");
            return;
        }
        Object object = this.mAvailableNetworksList.remove();
        Object object2 = new StringBuilder();
        ((StringBuilder)object2).append("updateAvailableNetworks: ");
        ((StringBuilder)object2).append(object);
        this.logl(((StringBuilder)object2).toString());
        object2 = object.iterator();
        while (object2.hasNext()) {
            StringBuilder stringBuilder;
            object = (AccessNetworksManager.QualifiedNetworks)object2.next();
            if (TransportManager.areNetworksValid((AccessNetworksManager.QualifiedNetworks)object)) {
                if (this.isHandoverNeeded((AccessNetworksManager.QualifiedNetworks)object)) {
                    int n = ACCESS_NETWORK_TRANSPORT_TYPE_MAP.get(((AccessNetworksManager.QualifiedNetworks)object).qualifiedNetworks[0]);
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("Handover needed for APN type: ");
                    stringBuilder.append(ApnSetting.getApnTypeString((int)((AccessNetworksManager.QualifiedNetworks)object).apnType));
                    stringBuilder.append(", target transport: ");
                    stringBuilder.append(AccessNetworkConstants.transportTypeToString((int)n));
                    this.logl(stringBuilder.toString());
                    this.mPendingHandoverApns.put(((AccessNetworksManager.QualifiedNetworks)object).apnType, n);
                    this.mHandoverNeededEventRegistrants.notifyResult((Object)new HandoverParams(((AccessNetworksManager.QualifiedNetworks)object).apnType, n, new _$$Lambda$TransportManager$Dk_40vYVb_woK0GbaXsHT3AecbY(this, (AccessNetworksManager.QualifiedNetworks)object, n)));
                }
                this.mCurrentAvailableNetworks.put(((AccessNetworksManager.QualifiedNetworks)object).apnType, (Object)((AccessNetworksManager.QualifiedNetworks)object).qualifiedNetworks);
                continue;
            }
            stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid networks received: ");
            stringBuilder.append(object);
            this.loge(stringBuilder.toString());
        }
        if (this.mAvailableNetworksList.size() > 0) {
            this.sendEmptyMessage(2);
        }
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
        printWriter = new IndentingPrintWriter((Writer)printWriter, "  ");
        printWriter.println("TransportManager:");
        printWriter.increaseIndent();
        Object object = new StringBuilder();
        ((StringBuilder)object).append("mAvailableTransports=[");
        ((StringBuilder)object).append(Arrays.stream(this.mAvailableTransports).mapToObj(_$$Lambda$TransportManager$vVwfnOC5CydwmAdimpil6w6F3zk.INSTANCE).collect(Collectors.joining(",")));
        ((StringBuilder)object).append("]");
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append("mCurrentAvailableNetworks=");
        ((StringBuilder)object).append(this.mCurrentAvailableNetworks);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append("mAvailableNetworksList=");
        ((StringBuilder)object).append(this.mAvailableNetworksList);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append("mPendingHandoverApns=");
        ((StringBuilder)object).append((Object)this.mPendingHandoverApns);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append("mCurrentTransports=");
        ((StringBuilder)object).append(this.mCurrentTransports);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append("isInLegacy=");
        ((StringBuilder)object).append(this.isInLegacyMode());
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append("IWLAN operation mode=");
        ((StringBuilder)object).append(SystemProperties.get((String)SYSTEM_PROPERTIES_IWLAN_OPERATION_MODE));
        printWriter.println(((StringBuilder)object).toString());
        object = this.mAccessNetworksManager;
        if (object != null) {
            ((AccessNetworksManager)((Object)object)).dump(fileDescriptor, (IndentingPrintWriter)printWriter, arrstring);
        }
        printWriter.println("Local logs=");
        printWriter.increaseIndent();
        this.mLocalLog.dump(fileDescriptor, printWriter, arrstring);
        printWriter.decreaseIndent();
        printWriter.decreaseIndent();
        printWriter.flush();
    }

    public int[] getAvailableTransports() {
        synchronized (this) {
            int[] arrn = this.mAvailableTransports;
            return arrn;
        }
    }

    public int getCurrentTransport(int n) {
        boolean bl = this.isInLegacyMode();
        int n2 = 1;
        if (bl) {
            return 1;
        }
        n = this.mCurrentTransports.get(n) == null ? n2 : this.mCurrentTransports.get(n);
        return n;
    }

    public void handleMessage(Message object) {
        int n = object.what;
        if (n != 1) {
            if (n != 2) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unexpected event ");
                stringBuilder.append(object.what);
                this.loge(stringBuilder.toString());
            } else {
                this.updateAvailableNetworks();
            }
        } else {
            object = (List)((AsyncResult)object.obj).result;
            this.mAvailableNetworksList.add((List<AccessNetworksManager.QualifiedNetworks>)object);
            this.sendEmptyMessage(2);
        }
    }

    public boolean isAnyApnPreferredOnIwlan() {
        for (int i = 0; i < this.mCurrentAvailableNetworks.size(); ++i) {
            int[] arrn = (int[])this.mCurrentAvailableNetworks.valueAt(i);
            if (arrn.length <= 0 || arrn[0] != 5) continue;
            return true;
        }
        return false;
    }

    public boolean isInLegacyMode() {
        boolean bl = SystemProperties.get((String)SYSTEM_PROPERTIES_IWLAN_OPERATION_MODE).equals(IWLAN_OPERATION_MODE_LEGACY) || this.mPhone.getHalVersion().less(RIL.RADIO_HAL_VERSION_1_4);
        return bl;
    }

    public /* synthetic */ void lambda$updateAvailableNetworks$0$TransportManager(AccessNetworksManager.QualifiedNetworks qualifiedNetworks, int n, boolean bl) {
        if (bl) {
            this.logl("Handover succeeded.");
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("APN type ");
            stringBuilder.append(ApnSetting.getApnTypeString((int)qualifiedNetworks.apnType));
            stringBuilder.append(" handover to ");
            stringBuilder.append(AccessNetworkConstants.transportTypeToString((int)n));
            stringBuilder.append(" failed.");
            this.logl(stringBuilder.toString());
        }
        this.setCurrentTransport(qualifiedNetworks.apnType, n);
        this.mPendingHandoverApns.delete(qualifiedNetworks.apnType);
        if (this.mAvailableNetworksList.size() > 0) {
            this.sendEmptyMessage(2);
        }
    }

    public void registerForHandoverNeededEvent(Handler handler, int n) {
        if (handler != null) {
            this.mHandoverNeededEventRegistrants.addUnique(handler, n, null);
        }
    }

    public void unregisterForHandoverNeededEvent(Handler handler) {
        this.mHandoverNeededEventRegistrants.remove(handler);
    }

    @VisibleForTesting
    public static final class HandoverParams {
        public final int apnType;
        public final HandoverCallback callback;
        public final int targetTransport;

        HandoverParams(int n, int n2, HandoverCallback handoverCallback) {
            this.apnType = n;
            this.targetTransport = n2;
            this.callback = handoverCallback;
        }

        public static interface HandoverCallback {
            public void onCompleted(boolean var1);
        }

    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface IwlanOperationMode {
    }

}

