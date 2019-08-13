/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.AsyncResult
 *  android.os.Handler
 *  android.os.Message
 *  android.os.PowerManager
 *  android.os.SystemProperties
 *  android.os.storage.StorageManager
 *  android.telephony.PhoneCapability
 *  android.telephony.Rlog
 *  android.telephony.TelephonyManager
 *  android.util.Log
 */
package com.android.internal.telephony;

import android.content.Context;
import android.os.AsyncResult;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.os.SystemProperties;
import android.os.storage.StorageManager;
import android.telephony.PhoneCapability;
import android.telephony.Rlog;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.android.internal.telephony.CommandsInterface;
import com.android.internal.telephony.DefaultPhoneNotifier;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.PhoneFactory;
import com.android.internal.telephony.RadioConfig;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class PhoneConfigurationManager {
    public static final String DSDA = "dsda";
    public static final String DSDS = "dsds";
    private static final int EVENT_GET_MODEM_STATUS = 101;
    private static final int EVENT_GET_MODEM_STATUS_DONE = 102;
    private static final int EVENT_GET_PHONE_CAPABILITY_DONE = 103;
    private static final int EVENT_SWITCH_DSDS_CONFIG_DONE = 100;
    private static final String LOG_TAG = "PhoneCfgMgr";
    public static final String SSSS = "";
    public static final String TSTS = "tsts";
    private static PhoneConfigurationManager sInstance = null;
    private final Context mContext;
    private final MainThreadHandler mHandler;
    private final Map<Integer, Boolean> mPhoneStatusMap;
    private final Phone[] mPhones;
    private final RadioConfig mRadioConfig;
    private PhoneCapability mStaticCapability;

    private PhoneConfigurationManager(Context object) {
        this.mContext = object;
        new TelephonyManager((Context)object);
        this.mStaticCapability = this.getDefaultCapability();
        this.mRadioConfig = RadioConfig.getInstance(this.mContext);
        this.mHandler = new MainThreadHandler();
        this.mPhoneStatusMap = new HashMap<Integer, Boolean>();
        this.notifyCapabilityChanged();
        this.mPhones = PhoneFactory.getPhones();
        boolean bl = StorageManager.inCryptKeeperBounce();
        int n = 0;
        if (!bl) {
            for (Phone phone : this.mPhones) {
                phone.mCi.registerForAvailable(this.mHandler, 1, phone);
            }
        } else {
            Phone[] arrphone = this.mPhones;
            int n2 = arrphone.length;
            for (int i = n; i < n2; ++i) {
                Phone phone = arrphone[i];
                phone.mCi.registerForOn(this.mHandler, 5, phone);
            }
        }
    }

    private PhoneCapability getDefaultCapability() {
        if (this.getPhoneCount() > 1) {
            return PhoneCapability.DEFAULT_DSDS_CAPABILITY;
        }
        return PhoneCapability.DEFAULT_SSSS_CAPABILITY;
    }

    public static PhoneConfigurationManager getInstance() {
        if (sInstance == null) {
            Log.wtf((String)LOG_TAG, (String)"getInstance null");
        }
        return sInstance;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static PhoneConfigurationManager init(Context object) {
        synchronized (PhoneConfigurationManager.class) {
            if (sInstance == null) {
                PhoneConfigurationManager phoneConfigurationManager;
                sInstance = phoneConfigurationManager = new PhoneConfigurationManager((Context)object);
                return sInstance;
            } else {
                object = new StringBuilder();
                ((StringBuilder)object).append("init() called multiple times!  sInstance = ");
                ((StringBuilder)object).append(sInstance);
                Log.wtf((String)LOG_TAG, (String)((StringBuilder)object).toString());
            }
            return sInstance;
        }
    }

    private static void log(String string) {
        Rlog.d((String)LOG_TAG, (String)string);
    }

    private void notifyCapabilityChanged() {
        new DefaultPhoneNotifier().notifyPhoneCapabilityChanged(this.mStaticCapability);
    }

    private void setMultiSimProperties(int n) {
        String string = n != 2 ? (n != 3 ? SSSS : TSTS) : DSDS;
        SystemProperties.set((String)"persist.radio.multisim.config", (String)string);
        if (this.isRebootRequiredForModemConfigChange()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("setMultiSimProperties: Rebooting due to switching multi-sim config to ");
            stringBuilder.append(string);
            PhoneConfigurationManager.log(stringBuilder.toString());
            stringBuilder = (PowerManager)this.mContext.getSystemService("power");
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("Switching to ");
            stringBuilder2.append(string);
            stringBuilder.reboot(stringBuilder2.toString());
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("setMultiSimProperties: Rebooting is not required to switch multi-sim config to ");
            stringBuilder.append(string);
            PhoneConfigurationManager.log(stringBuilder.toString());
        }
    }

    private void updatePhoneStatus(Phone phone) {
        Message message = Message.obtain((Handler)this.mHandler, (int)102, (int)phone.getPhoneId(), (int)0);
        phone.mCi.getModemStatus(message);
    }

    public void addToPhoneStatusCache(int n, boolean bl) {
        this.mPhoneStatusMap.put(n, bl);
    }

    public void enablePhone(Phone phone, boolean bl, Message message) {
        if (phone == null) {
            PhoneConfigurationManager.log("enablePhone failed phone is null");
            return;
        }
        phone.mCi.enableModem(bl, message);
    }

    public PhoneCapability getCurrentPhoneCapability() {
        return this.getStaticPhoneCapability();
    }

    public int getNumberOfModemsWithSimultaneousDataConnections() {
        return this.mStaticCapability.maxActiveData;
    }

    public int getPhoneCount() {
        return new TelephonyManager(this.mContext).getPhoneCount();
    }

    public boolean getPhoneStatus(Phone phone) {
        if (phone == null) {
            PhoneConfigurationManager.log("getPhoneStatus failed phone is null");
            return false;
        }
        int n = phone.getPhoneId();
        try {
            boolean bl = this.getPhoneStatusFromCache(n);
            return bl;
        }
        catch (NoSuchElementException noSuchElementException) {
            this.updatePhoneStatus(phone);
            return true;
        }
    }

    public boolean getPhoneStatusFromCache(int n) throws NoSuchElementException {
        if (this.mPhoneStatusMap.containsKey(n)) {
            return this.mPhoneStatusMap.get(n);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("phoneId not found: ");
        stringBuilder.append(n);
        throw new NoSuchElementException(stringBuilder.toString());
    }

    public void getPhoneStatusFromModem(Phone phone, Message message) {
        if (phone == null) {
            PhoneConfigurationManager.log("getPhoneStatus failed phone is null");
        }
        phone.mCi.getModemStatus(message);
    }

    public PhoneCapability getStaticPhoneCapability() {
        synchronized (this) {
            PhoneCapability phoneCapability;
            if (this.getDefaultCapability().equals((Object)this.mStaticCapability)) {
                PhoneConfigurationManager.log("getStaticPhoneCapability: sending the request for getting PhoneCapability");
                phoneCapability = Message.obtain((Handler)this.mHandler, (int)103);
                this.mRadioConfig.getPhoneCapability((Message)phoneCapability);
            }
            phoneCapability = this.mStaticCapability;
            return phoneCapability;
        }
    }

    public boolean isRebootRequiredForModemConfigChange() {
        String string = SystemProperties.get((String)"persist.radio.reboot_on_modem_change");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("isRebootRequiredForModemConfigChange: isRebootRequired = ");
        stringBuilder.append(string);
        PhoneConfigurationManager.log(stringBuilder.toString());
        return string.equals("false") ^ true;
    }

    public void switchMultiSimConfig(int n) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("switchMultiSimConfig: with numOfSims = ");
        stringBuilder.append(n);
        PhoneConfigurationManager.log(stringBuilder.toString());
        if (this.getStaticPhoneCapability().logicalModemList.size() < n) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("switchMultiSimConfig: Phone is not capable of enabling ");
            stringBuilder.append(n);
            stringBuilder.append(" sims, exiting!");
            PhoneConfigurationManager.log(stringBuilder.toString());
            return;
        }
        if (this.getPhoneCount() != n) {
            PhoneConfigurationManager.log("switchMultiSimConfig: sending the request for switching");
            stringBuilder = Message.obtain((Handler)this.mHandler, (int)100, (int)n, (int)0);
            this.mRadioConfig.setModemsConfig(n, (Message)stringBuilder);
        } else {
            stringBuilder = new StringBuilder();
            stringBuilder.append("switchMultiSimConfig: No need to switch. getNumOfActiveSims is already ");
            stringBuilder.append(n);
            PhoneConfigurationManager.log(stringBuilder.toString());
        }
    }

    private final class MainThreadHandler
    extends Handler {
        private MainThreadHandler() {
        }

        public void handleMessage(Message object) {
            int n = object.what;
            if (n != 1 && n != 5) {
                if (n != 100) {
                    if (n != 102) {
                        if (n == 103) {
                            AsyncResult asyncResult = (AsyncResult)object.obj;
                            if (asyncResult != null && asyncResult.exception == null) {
                                PhoneConfigurationManager.this.mStaticCapability = (PhoneCapability)asyncResult.result;
                                PhoneConfigurationManager.this.notifyCapabilityChanged();
                            } else {
                                StringBuilder stringBuilder = new StringBuilder();
                                stringBuilder.append(object.what);
                                stringBuilder.append(" failure. Not getting phone capability.");
                                stringBuilder.append(asyncResult.exception);
                                PhoneConfigurationManager.log(stringBuilder.toString());
                            }
                        }
                    } else {
                        AsyncResult asyncResult = (AsyncResult)object.obj;
                        if (asyncResult != null && asyncResult.exception == null) {
                            n = object.arg1;
                            boolean bl = (Boolean)asyncResult.result;
                            PhoneConfigurationManager.this.addToPhoneStatusCache(n, bl);
                        } else {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append(object.what);
                            stringBuilder.append(" failure. Not updating modem status.");
                            stringBuilder.append(asyncResult.exception);
                            PhoneConfigurationManager.log(stringBuilder.toString());
                        }
                    }
                } else {
                    AsyncResult asyncResult = (AsyncResult)object.obj;
                    if (asyncResult != null && asyncResult.exception == null) {
                        n = object.arg1;
                        PhoneConfigurationManager.this.setMultiSimProperties(n);
                    } else {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(object.what);
                        stringBuilder.append(" failure. Not switching multi-sim config.");
                        stringBuilder.append(asyncResult.exception);
                        PhoneConfigurationManager.log(stringBuilder.toString());
                    }
                }
            } else {
                PhoneConfigurationManager.log("Received EVENT_RADIO_AVAILABLE/EVENT_RADIO_ON");
                Object object2 = (AsyncResult)object.obj;
                if (((AsyncResult)object2).userObj != null && ((AsyncResult)object2).userObj instanceof Phone) {
                    object = (Phone)((AsyncResult)object2).userObj;
                    PhoneConfigurationManager.this.updatePhoneStatus((Phone)object);
                } else {
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("Unable to add phoneStatus to cache. No phone object provided for event ");
                    ((StringBuilder)object2).append(object.what);
                    PhoneConfigurationManager.log(((StringBuilder)object2).toString());
                }
                PhoneConfigurationManager.this.getStaticPhoneCapability();
            }
        }
    }

}

