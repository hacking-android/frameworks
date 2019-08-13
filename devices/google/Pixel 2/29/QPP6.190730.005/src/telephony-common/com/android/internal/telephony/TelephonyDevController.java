/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.res.Resources
 *  android.os.AsyncResult
 *  android.os.Handler
 *  android.os.Message
 *  android.telephony.Rlog
 */
package com.android.internal.telephony;

import android.content.res.Resources;
import android.os.AsyncResult;
import android.os.Handler;
import android.os.Message;
import android.telephony.Rlog;
import com.android.internal.telephony.CommandsInterface;
import com.android.internal.telephony.HardwareConfig;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TelephonyDevController
extends Handler {
    private static final boolean DBG = true;
    private static final int EVENT_HARDWARE_CONFIG_CHANGED = 1;
    private static final String LOG_TAG = "TDC";
    private static final Object mLock = new Object();
    private static ArrayList<HardwareConfig> mModems = new ArrayList();
    private static ArrayList<HardwareConfig> mSims = new ArrayList();
    private static Message sRilHardwareConfig;
    private static TelephonyDevController sTelephonyDevController;

    private TelephonyDevController() {
        this.initFromResource();
        mModems.trimToSize();
        mSims.trimToSize();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static TelephonyDevController create() {
        Object object = mLock;
        synchronized (object) {
            if (sTelephonyDevController == null) {
                TelephonyDevController telephonyDevController = new TelephonyDevController();
                sTelephonyDevController = telephonyDevController;
                return sTelephonyDevController;
            }
            RuntimeException runtimeException = new RuntimeException("TelephonyDevController already created!?!");
            throw runtimeException;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static TelephonyDevController getInstance() {
        Object object = mLock;
        synchronized (object) {
            if (sTelephonyDevController != null) {
                return sTelephonyDevController;
            }
            RuntimeException runtimeException = new RuntimeException("TelephonyDevController not yet created!?!");
            throw runtimeException;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static int getModemCount() {
        Object object = mLock;
        synchronized (object) {
            int n = mModems.size();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("getModemCount: ");
            stringBuilder.append(n);
            TelephonyDevController.logd(stringBuilder.toString());
            return n;
        }
    }

    private static void handleGetHardwareConfigChanged(AsyncResult object) {
        if (object.exception == null && object.result != null) {
            List list = (List)object.result;
            for (int i = 0; i < list.size(); ++i) {
                object = (HardwareConfig)list.get(i);
                if (object == null) continue;
                if (object.type == 0) {
                    TelephonyDevController.updateOrInsert((HardwareConfig)object, mModems);
                    continue;
                }
                if (object.type != 1) continue;
                TelephonyDevController.updateOrInsert((HardwareConfig)object, mSims);
            }
        } else {
            TelephonyDevController.loge("handleGetHardwareConfigChanged - returned an error.");
        }
    }

    private void initFromResource() {
        String[] arrstring = Resources.getSystem().getStringArray(17236065);
        if (arrstring != null) {
            int n = arrstring.length;
            for (int i = 0; i < n; ++i) {
                HardwareConfig hardwareConfig = new HardwareConfig(arrstring[i]);
                if (hardwareConfig.type == 0) {
                    TelephonyDevController.updateOrInsert(hardwareConfig, mModems);
                    continue;
                }
                if (hardwareConfig.type != 1) continue;
                TelephonyDevController.updateOrInsert(hardwareConfig, mSims);
            }
        }
    }

    private static void logd(String string) {
        Rlog.d((String)LOG_TAG, (String)string);
    }

    private static void loge(String string) {
        Rlog.e((String)LOG_TAG, (String)string);
    }

    public static void registerRIL(CommandsInterface commandsInterface) {
        commandsInterface.getHardwareConfig(sRilHardwareConfig);
        Message message = sRilHardwareConfig;
        if (message != null) {
            message = (AsyncResult)message.obj;
            if (message.exception == null) {
                TelephonyDevController.handleGetHardwareConfigChanged((AsyncResult)message);
            }
        }
        commandsInterface.registerForHardwareConfigChanged(sTelephonyDevController, 1, null);
    }

    public static void unregisterRIL(CommandsInterface commandsInterface) {
        commandsInterface.unregisterForHardwareConfigChanged(sTelephonyDevController);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static void updateOrInsert(HardwareConfig hardwareConfig, ArrayList<HardwareConfig> arrayList) {
        Object object = mLock;
        synchronized (object) {
            StringBuilder stringBuilder;
            int n = arrayList.size();
            for (int i = 0; i < n; ++i) {
                HardwareConfig hardwareConfig2 = arrayList.get(i);
                if (hardwareConfig2.uuid.compareTo(hardwareConfig.uuid) != 0) continue;
                stringBuilder = new StringBuilder();
                stringBuilder.append("updateOrInsert: removing: ");
                stringBuilder.append(hardwareConfig2);
                TelephonyDevController.logd(stringBuilder.toString());
                arrayList.remove(i);
                break;
            }
            stringBuilder = new StringBuilder();
            stringBuilder.append("updateOrInsert: inserting: ");
            stringBuilder.append(hardwareConfig);
            TelephonyDevController.logd(stringBuilder.toString());
            arrayList.add(hardwareConfig);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public ArrayList<HardwareConfig> getAllModems() {
        Object object = mLock;
        synchronized (object) {
            ArrayList<HardwareConfig> arrayList = new ArrayList<HardwareConfig>();
            if (mModems.isEmpty()) {
                TelephonyDevController.logd("getAllModems: empty list.");
            } else {
                Iterator<HardwareConfig> iterator = mModems.iterator();
                while (iterator.hasNext()) {
                    arrayList.add(iterator.next());
                }
            }
            return arrayList;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public ArrayList<HardwareConfig> getAllSims() {
        Object object = mLock;
        synchronized (object) {
            ArrayList<HardwareConfig> arrayList = new ArrayList<HardwareConfig>();
            if (mSims.isEmpty()) {
                TelephonyDevController.logd("getAllSims: empty list.");
            } else {
                Iterator<HardwareConfig> iterator = mSims.iterator();
                while (iterator.hasNext()) {
                    arrayList.add(iterator.next());
                }
            }
            return arrayList;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public ArrayList<HardwareConfig> getAllSimsForModem(int n) {
        Object object = mLock;
        synchronized (object) {
            if (mSims.isEmpty()) {
                TelephonyDevController.loge("getAllSimsForModem: no registered sim device?!?");
                return null;
            }
            if (n > TelephonyDevController.getModemCount()) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("getAllSimsForModem: out-of-bounds access for modem device ");
                stringBuilder.append(n);
                stringBuilder.append(" max: ");
                stringBuilder.append(TelephonyDevController.getModemCount());
                TelephonyDevController.loge(stringBuilder.toString());
                return null;
            }
            Object object2 = new StringBuilder();
            ((StringBuilder)object2).append("getAllSimsForModem ");
            ((StringBuilder)object2).append(n);
            TelephonyDevController.logd(((StringBuilder)object2).toString());
            ArrayList<HardwareConfig> arrayList = new ArrayList<HardwareConfig>();
            object2 = this.getModem(n);
            Iterator<HardwareConfig> iterator = mSims.iterator();
            while (iterator.hasNext()) {
                HardwareConfig hardwareConfig = iterator.next();
                if (!hardwareConfig.modemUuid.equals(((HardwareConfig)object2).uuid)) continue;
                arrayList.add(hardwareConfig);
            }
            return arrayList;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public HardwareConfig getModem(int n) {
        Object object = mLock;
        synchronized (object) {
            if (mModems.isEmpty()) {
                TelephonyDevController.loge("getModem: no registered modem device?!?");
                return null;
            }
            if (n > TelephonyDevController.getModemCount()) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("getModem: out-of-bounds access for modem device ");
                stringBuilder.append(n);
                stringBuilder.append(" max: ");
                stringBuilder.append(TelephonyDevController.getModemCount());
                TelephonyDevController.loge(stringBuilder.toString());
                return null;
            }
            Object object2 = new StringBuilder();
            ((StringBuilder)object2).append("getModem: ");
            ((StringBuilder)object2).append(n);
            TelephonyDevController.logd(((StringBuilder)object2).toString());
            return mModems.get(n);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public HardwareConfig getModemForSim(int n) {
        Object object = mLock;
        synchronized (object) {
            if (!mModems.isEmpty() && !mSims.isEmpty()) {
                HardwareConfig hardwareConfig;
                if (n > this.getSimCount()) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("getModemForSim: out-of-bounds access for sim device ");
                    stringBuilder.append(n);
                    stringBuilder.append(" max: ");
                    stringBuilder.append(this.getSimCount());
                    TelephonyDevController.loge(stringBuilder.toString());
                    return null;
                }
                Object object2 = new StringBuilder();
                ((StringBuilder)object2).append("getModemForSim ");
                ((StringBuilder)object2).append(n);
                TelephonyDevController.logd(((StringBuilder)object2).toString());
                object2 = this.getSim(n);
                Iterator<HardwareConfig> iterator = mModems.iterator();
                do {
                    if (!iterator.hasNext()) {
                        return null;
                    }
                    hardwareConfig = iterator.next();
                } while (!hardwareConfig.uuid.equals(((HardwareConfig)object2).modemUuid));
                return hardwareConfig;
            }
            TelephonyDevController.loge("getModemForSim: no registered modem/sim device?!?");
            return null;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public HardwareConfig getSim(int n) {
        Object object = mLock;
        synchronized (object) {
            if (mSims.isEmpty()) {
                TelephonyDevController.loge("getSim: no registered sim device?!?");
                return null;
            }
            if (n > this.getSimCount()) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("getSim: out-of-bounds access for sim device ");
                stringBuilder.append(n);
                stringBuilder.append(" max: ");
                stringBuilder.append(this.getSimCount());
                TelephonyDevController.loge(stringBuilder.toString());
                return null;
            }
            Object object2 = new StringBuilder();
            ((StringBuilder)object2).append("getSim: ");
            ((StringBuilder)object2).append(n);
            TelephonyDevController.logd(((StringBuilder)object2).toString());
            return mSims.get(n);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int getSimCount() {
        Object object = mLock;
        synchronized (object) {
            int n = mSims.size();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("getSimCount: ");
            stringBuilder.append(n);
            TelephonyDevController.logd(stringBuilder.toString());
            return n;
        }
    }

    public void handleMessage(Message message) {
        if (message.what != 1) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("handleMessage: Unknown Event ");
            stringBuilder.append(message.what);
            TelephonyDevController.loge(stringBuilder.toString());
        } else {
            TelephonyDevController.logd("handleMessage: received EVENT_HARDWARE_CONFIG_CHANGED");
            TelephonyDevController.handleGetHardwareConfigChanged((AsyncResult)message.obj);
        }
    }
}

