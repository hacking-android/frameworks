/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.pm.ApplicationInfo
 *  android.hardware.radio.V1_0.RadioResponseInfo
 *  android.net.ConnectivityManager
 *  android.os.AsyncResult
 *  android.os.Handler
 *  android.os.IHwBinder
 *  android.os.IHwBinder$DeathRecipient
 *  android.os.Message
 *  android.os.Registrant
 *  android.os.RemoteException
 *  android.os.WorkSource
 *  android.telephony.Rlog
 *  android.util.SparseArray
 */
package com.android.internal.telephony;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.hardware.radio.V1_0.RadioResponseInfo;
import android.hardware.radio.config.V1_0.IRadioConfig;
import android.hardware.radio.config.V1_0.IRadioConfigIndication;
import android.hardware.radio.config.V1_0.IRadioConfigResponse;
import android.hardware.radio.config.V1_0.SimSlotStatus;
import android.hardware.radio.config.V1_1.ModemsConfig;
import android.net.ConnectivityManager;
import android.os.AsyncResult;
import android.os.Handler;
import android.os.IHwBinder;
import android.os.Message;
import android.os.Registrant;
import android.os.RemoteException;
import android.os.WorkSource;
import android.telephony.Rlog;
import android.util.SparseArray;
import com.android.internal.telephony.CommandException;
import com.android.internal.telephony.HalVersion;
import com.android.internal.telephony.RILRequest;
import com.android.internal.telephony.RadioConfigIndication;
import com.android.internal.telephony.RadioConfigResponse;
import com.android.internal.telephony.uicc.IccSlotStatus;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicLong;

public class RadioConfig
extends Handler {
    private static final boolean DBG = true;
    private static final int EVENT_SERVICE_DEAD = 1;
    private static final HalVersion RADIO_CONFIG_HAL_VERSION_1_0;
    private static final HalVersion RADIO_CONFIG_HAL_VERSION_1_1;
    private static final HalVersion RADIO_CONFIG_HAL_VERSION_UNKNOWN;
    private static final String TAG = "RadioConfig";
    private static final boolean VDBG = false;
    private static RadioConfig sRadioConfig;
    private final WorkSource mDefaultWorkSource;
    private final boolean mIsMobileNetworkSupported;
    private final RadioConfigIndication mRadioConfigIndication;
    private volatile IRadioConfig mRadioConfigProxy = null;
    private final AtomicLong mRadioConfigProxyCookie = new AtomicLong(0L);
    private final RadioConfigResponse mRadioConfigResponse;
    private HalVersion mRadioConfigVersion = RADIO_CONFIG_HAL_VERSION_UNKNOWN;
    private final SparseArray<RILRequest> mRequestList = new SparseArray();
    private final ServiceDeathRecipient mServiceDeathRecipient;
    protected Registrant mSimSlotStatusRegistrant;

    static {
        RADIO_CONFIG_HAL_VERSION_UNKNOWN = new HalVersion(-1, -1);
        RADIO_CONFIG_HAL_VERSION_1_0 = new HalVersion(1, 0);
        RADIO_CONFIG_HAL_VERSION_1_1 = new HalVersion(1, 1);
    }

    private RadioConfig(Context context) {
        this.mIsMobileNetworkSupported = ((ConnectivityManager)context.getSystemService("connectivity")).isNetworkSupported(0);
        this.mRadioConfigResponse = new RadioConfigResponse(this);
        this.mRadioConfigIndication = new RadioConfigIndication(this);
        this.mServiceDeathRecipient = new ServiceDeathRecipient();
        this.mDefaultWorkSource = new WorkSource(context.getApplicationInfo().uid, context.getPackageName());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void clearRequestList(int n, boolean bl) {
        SparseArray<RILRequest> sparseArray = this.mRequestList;
        synchronized (sparseArray) {
            StringBuilder stringBuilder;
            int n2 = this.mRequestList.size();
            if (bl) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("clearRequestList: mRequestList=");
                stringBuilder.append(n2);
                RadioConfig.logd(stringBuilder.toString());
            }
            int n3 = 0;
            do {
                if (n3 >= n2) {
                    this.mRequestList.clear();
                    return;
                }
                RILRequest rILRequest = (RILRequest)this.mRequestList.valueAt(n3);
                if (bl) {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(n3);
                    stringBuilder.append(": [");
                    stringBuilder.append(rILRequest.mSerial);
                    stringBuilder.append("] ");
                    stringBuilder.append(RadioConfig.requestToString(rILRequest.mRequest));
                    RadioConfig.logd(stringBuilder.toString());
                }
                rILRequest.onError(n, null);
                rILRequest.release();
                ++n3;
            } while (true);
        }
    }

    static ArrayList<IccSlotStatus> convertHalSlotStatus(ArrayList<SimSlotStatus> object) {
        ArrayList<IccSlotStatus> arrayList = new ArrayList<IccSlotStatus>(((ArrayList)object).size());
        Iterator<SimSlotStatus> iterator = ((ArrayList)object).iterator();
        while (iterator.hasNext()) {
            SimSlotStatus simSlotStatus = iterator.next();
            object = new IccSlotStatus();
            ((IccSlotStatus)object).setCardState(simSlotStatus.cardState);
            ((IccSlotStatus)object).setSlotState(simSlotStatus.slotState);
            ((IccSlotStatus)object).logicalSlotIndex = simSlotStatus.logicalSlotId;
            ((IccSlotStatus)object).atr = simSlotStatus.atr;
            ((IccSlotStatus)object).iccid = simSlotStatus.iccid;
            arrayList.add((IccSlotStatus)object);
        }
        return arrayList;
    }

    static ArrayList<IccSlotStatus> convertHalSlotStatus_1_2(ArrayList<android.hardware.radio.config.V1_2.SimSlotStatus> object) {
        ArrayList<IccSlotStatus> arrayList = new ArrayList<IccSlotStatus>(((ArrayList)object).size());
        Iterator<android.hardware.radio.config.V1_2.SimSlotStatus> iterator = ((ArrayList)object).iterator();
        while (iterator.hasNext()) {
            object = iterator.next();
            IccSlotStatus iccSlotStatus = new IccSlotStatus();
            iccSlotStatus.setCardState(object.base.cardState);
            iccSlotStatus.setSlotState(object.base.slotState);
            iccSlotStatus.logicalSlotIndex = object.base.logicalSlotId;
            iccSlotStatus.atr = object.base.atr;
            iccSlotStatus.iccid = object.base.iccid;
            iccSlotStatus.eid = ((android.hardware.radio.config.V1_2.SimSlotStatus)object).eid;
            arrayList.add(iccSlotStatus);
        }
        return arrayList;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private RILRequest findAndRemoveRequestFromList(int n) {
        SparseArray<RILRequest> sparseArray = this.mRequestList;
        synchronized (sparseArray) {
            RILRequest rILRequest = (RILRequest)this.mRequestList.get(n);
            if (rILRequest != null) {
                this.mRequestList.remove(n);
            }
            return rILRequest;
        }
    }

    public static RadioConfig getInstance(Context context) {
        if (sRadioConfig == null) {
            sRadioConfig = new RadioConfig(context);
        }
        return sRadioConfig;
    }

    private static void logd(String string) {
        Rlog.d((String)TAG, (String)string);
    }

    private static void loge(String string) {
        Rlog.e((String)TAG, (String)string);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private RILRequest obtainRequest(int n, Message sparseArray, WorkSource object) {
        object = RILRequest.obtain(n, (Message)sparseArray, object);
        sparseArray = this.mRequestList;
        synchronized (sparseArray) {
            this.mRequestList.append(object.mSerial, object);
            return object;
        }
    }

    private static ArrayList<Integer> primitiveArrayToArrayList(int[] arrn) {
        ArrayList<Integer> arrayList = new ArrayList<Integer>(arrn.length);
        int n = arrn.length;
        for (int i = 0; i < n; ++i) {
            arrayList.add(arrn[i]);
        }
        return arrayList;
    }

    static String requestToString(int n) {
        if (n != 200) {
            if (n != 201) {
                if (n != 204) {
                    if (n != 206) {
                        if (n != 207) {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("<unknown request ");
                            stringBuilder.append(n);
                            stringBuilder.append(">");
                            return stringBuilder.toString();
                        }
                        return "SWITCH_DUAL_SIM_CONFIG";
                    }
                    return "GET_PHONE_CAPABILITY";
                }
                return "SET_PREFERRED_DATA_MODEM";
            }
            return "SET_LOGICAL_TO_PHYSICAL_SLOT_MAPPING";
        }
        return "GET_SLOT_STATUS";
    }

    private void resetProxyAndRequestList(String string, Exception exception) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append(": ");
        stringBuilder.append(exception);
        RadioConfig.loge(stringBuilder.toString());
        this.mRadioConfigProxy = null;
        this.mRadioConfigProxyCookie.incrementAndGet();
        RILRequest.resetSerial();
        this.clearRequestList(1, false);
        this.getRadioConfigProxy(null);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void updateRadioConfigProxy() {
        try {
            try {
                this.mRadioConfigProxy = android.hardware.radio.config.V1_1.IRadioConfig.getService(true);
                this.mRadioConfigVersion = RADIO_CONFIG_HAL_VERSION_1_1;
            }
            catch (NoSuchElementException noSuchElementException) {
                // empty catch block
            }
            IRadioConfig iRadioConfig = this.mRadioConfigProxy;
            if (iRadioConfig == null) {
                try {
                    this.mRadioConfigProxy = IRadioConfig.getService(true);
                    this.mRadioConfigVersion = RADIO_CONFIG_HAL_VERSION_1_0;
                }
                catch (NoSuchElementException noSuchElementException) {
                    // empty catch block
                }
            }
            if (this.mRadioConfigProxy == null) {
                RadioConfig.loge("getRadioConfigProxy: mRadioConfigProxy == null");
                return;
            }
            this.mRadioConfigProxy.linkToDeath(this.mServiceDeathRecipient, this.mRadioConfigProxyCookie.incrementAndGet());
            this.mRadioConfigProxy.setResponseFunctions(this.mRadioConfigResponse, this.mRadioConfigIndication);
            return;
        }
        catch (RemoteException | RuntimeException throwable2) {}
        this.mRadioConfigProxy = null;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("getRadioConfigProxy: RadioConfigProxy setResponseFunctions: ");
        stringBuilder.append(throwable2);
        RadioConfig.loge(stringBuilder.toString());
    }

    public void getPhoneCapability(Message object) {
        if (this.getRadioConfigProxy(null) != null && !this.mRadioConfigVersion.less(RADIO_CONFIG_HAL_VERSION_1_1)) {
            RILRequest rILRequest = this.obtainRequest(206, (Message)object, this.mDefaultWorkSource);
            object = new StringBuilder();
            ((StringBuilder)object).append(rILRequest.serialString());
            ((StringBuilder)object).append("> ");
            ((StringBuilder)object).append(RadioConfig.requestToString(rILRequest.mRequest));
            RadioConfig.logd(((StringBuilder)object).toString());
            try {
                ((android.hardware.radio.config.V1_1.IRadioConfig)this.mRadioConfigProxy).getPhoneCapability(rILRequest.mSerial);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.resetProxyAndRequestList("getPhoneCapability", (Exception)throwable);
            }
            return;
        }
        if (object != null) {
            AsyncResult.forMessage((Message)object, null, (Throwable)CommandException.fromRilErrno(6));
            object.sendToTarget();
        }
    }

    public IRadioConfig getRadioConfigProxy(Message message) {
        if (!this.mIsMobileNetworkSupported) {
            if (message != null) {
                AsyncResult.forMessage((Message)message, null, (Throwable)CommandException.fromRilErrno(1));
                message.sendToTarget();
            }
            return null;
        }
        if (this.mRadioConfigProxy != null) {
            return this.mRadioConfigProxy;
        }
        this.updateRadioConfigProxy();
        if (this.mRadioConfigProxy == null && message != null) {
            AsyncResult.forMessage((Message)message, null, (Throwable)CommandException.fromRilErrno(1));
            message.sendToTarget();
        }
        return this.mRadioConfigProxy;
    }

    public void getSimSlotsStatus(Message object) {
        IRadioConfig iRadioConfig = this.getRadioConfigProxy((Message)object);
        if (iRadioConfig != null) {
            RILRequest rILRequest = this.obtainRequest(200, (Message)object, this.mDefaultWorkSource);
            object = new StringBuilder();
            ((StringBuilder)object).append(rILRequest.serialString());
            ((StringBuilder)object).append("> ");
            ((StringBuilder)object).append(RadioConfig.requestToString(rILRequest.mRequest));
            RadioConfig.logd(((StringBuilder)object).toString());
            try {
                iRadioConfig.getSimSlotsStatus(rILRequest.mSerial);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.resetProxyAndRequestList("getSimSlotsStatus", (Exception)throwable);
            }
        }
    }

    public void handleMessage(Message message) {
        if (message.what == 1) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("handleMessage: EVENT_SERVICE_DEAD cookie = ");
            stringBuilder.append(message.obj);
            stringBuilder.append(" mRadioConfigProxyCookie = ");
            stringBuilder.append(this.mRadioConfigProxyCookie.get());
            RadioConfig.logd(stringBuilder.toString());
            if (((Long)message.obj).longValue() == this.mRadioConfigProxyCookie.get()) {
                this.resetProxyAndRequestList("EVENT_SERVICE_DEAD", null);
            }
        }
    }

    public boolean isSetPreferredDataCommandSupported() {
        boolean bl = this.getRadioConfigProxy(null) != null && this.mRadioConfigVersion.greaterOrEqual(RADIO_CONFIG_HAL_VERSION_1_1);
        return bl;
    }

    public RILRequest processResponse(RadioResponseInfo object) {
        int n = ((RadioResponseInfo)object).serial;
        int n2 = ((RadioResponseInfo)object).error;
        int n3 = ((RadioResponseInfo)object).type;
        if (n3 != 0) {
            object = new StringBuilder();
            ((StringBuilder)object).append("processResponse: Unexpected response type ");
            ((StringBuilder)object).append(n3);
            RadioConfig.loge(((StringBuilder)object).toString());
        }
        if ((object = this.findAndRemoveRequestFromList(n)) == null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("processResponse: Unexpected response! serial: ");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append(" error: ");
            ((StringBuilder)object).append(n2);
            RadioConfig.loge(((StringBuilder)object).toString());
            return null;
        }
        return object;
    }

    public void registerForSimSlotStatusChanged(Handler handler, int n, Object object) {
        this.mSimSlotStatusRegistrant = new Registrant(handler, n, object);
    }

    public void setModemsConfig(int n, Message object) {
        IRadioConfig iRadioConfig = this.getRadioConfigProxy((Message)object);
        if (iRadioConfig != null && this.mRadioConfigVersion.greaterOrEqual(RADIO_CONFIG_HAL_VERSION_1_1)) {
            iRadioConfig = (android.hardware.radio.config.V1_1.IRadioConfig)iRadioConfig;
            object = this.obtainRequest(207, (Message)object, this.mDefaultWorkSource);
            Object object2 = new StringBuilder();
            ((StringBuilder)object2).append(((RILRequest)object).serialString());
            ((StringBuilder)object2).append("> ");
            ((StringBuilder)object2).append(RadioConfig.requestToString(((RILRequest)object).mRequest));
            ((StringBuilder)object2).append(", numOfLiveModems = ");
            ((StringBuilder)object2).append(n);
            RadioConfig.logd(((StringBuilder)object2).toString());
            try {
                object2 = new ModemsConfig();
                ((ModemsConfig)object2).numOfLiveModems = (byte)n;
                iRadioConfig.setModemsConfig(((RILRequest)object).mSerial, (ModemsConfig)object2);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.resetProxyAndRequestList("setModemsConfig", (Exception)throwable);
            }
        }
    }

    public void setPreferredDataModem(int n, Message object) {
        if (!this.isSetPreferredDataCommandSupported()) {
            if (object != null) {
                AsyncResult.forMessage((Message)object, null, (Throwable)CommandException.fromRilErrno(6));
                object.sendToTarget();
            }
            return;
        }
        RILRequest rILRequest = this.obtainRequest(204, (Message)object, this.mDefaultWorkSource);
        object = new StringBuilder();
        ((StringBuilder)object).append(rILRequest.serialString());
        ((StringBuilder)object).append("> ");
        ((StringBuilder)object).append(RadioConfig.requestToString(rILRequest.mRequest));
        RadioConfig.logd(((StringBuilder)object).toString());
        try {
            ((android.hardware.radio.config.V1_1.IRadioConfig)this.mRadioConfigProxy).setPreferredDataModem(rILRequest.mSerial, (byte)n);
        }
        catch (RemoteException | RuntimeException throwable) {
            this.resetProxyAndRequestList("setPreferredDataModem", (Exception)throwable);
        }
    }

    public void setSimSlotsMapping(int[] arrn, Message object) {
        IRadioConfig iRadioConfig = this.getRadioConfigProxy((Message)object);
        if (iRadioConfig != null) {
            RILRequest rILRequest = this.obtainRequest(201, (Message)object, this.mDefaultWorkSource);
            object = new StringBuilder();
            ((StringBuilder)object).append(rILRequest.serialString());
            ((StringBuilder)object).append("> ");
            ((StringBuilder)object).append(RadioConfig.requestToString(rILRequest.mRequest));
            ((StringBuilder)object).append(" ");
            ((StringBuilder)object).append(Arrays.toString(arrn));
            RadioConfig.logd(((StringBuilder)object).toString());
            try {
                iRadioConfig.setSimSlotsMapping(rILRequest.mSerial, RadioConfig.primitiveArrayToArrayList(arrn));
            }
            catch (RemoteException | RuntimeException throwable) {
                this.resetProxyAndRequestList("setSimSlotsMapping", (Exception)throwable);
            }
        }
    }

    public void unregisterForSimSlotStatusChanged(Handler handler) {
        Registrant registrant = this.mSimSlotStatusRegistrant;
        if (registrant != null && registrant.getHandler() == handler) {
            this.mSimSlotStatusRegistrant.clear();
            this.mSimSlotStatusRegistrant = null;
        }
    }

    final class ServiceDeathRecipient
    implements IHwBinder.DeathRecipient {
        ServiceDeathRecipient() {
        }

        public void serviceDied(long l) {
            RadioConfig.logd("serviceDied");
            RadioConfig radioConfig = RadioConfig.this;
            radioConfig.sendMessage(radioConfig.obtainMessage(1, (Object)l));
        }
    }

}

