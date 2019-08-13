/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 *  android.content.Context
 *  android.content.Intent
 *  android.os.AsyncResult
 *  android.os.Handler
 *  android.os.Message
 *  android.os.PowerManager
 *  android.os.PowerManager$WakeLock
 *  android.telephony.RadioAccessFamily
 *  android.telephony.Rlog
 *  android.telephony.TelephonyManager
 *  android.util.Log
 */
package com.android.internal.telephony;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncResult;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.telephony.RadioAccessFamily;
import android.telephony.Rlog;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.android.internal.telephony.CommandsInterface;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.PhoneSubInfoController;
import com.android.internal.telephony.PhoneSwitcher;
import com.android.internal.telephony.RadioCapability;
import com.android.internal.telephony.SmsController;
import com.android.internal.telephony.SubscriptionController;
import com.android.internal.telephony.UiccPhoneBookController;
import com.android.internal.telephony.ims.RcsMessageStoreController;
import com.android.internal.telephony.uicc.UiccController;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class ProxyController {
    private static final int EVENT_APPLY_RC_RESPONSE = 3;
    private static final int EVENT_FINISH_RC_RESPONSE = 4;
    private static final int EVENT_NOTIFICATION_RC_CHANGED = 1;
    private static final int EVENT_START_RC_RESPONSE = 2;
    private static final int EVENT_TIMEOUT = 5;
    static final String LOG_TAG = "ProxyController";
    private static final int SET_RC_STATUS_APPLYING = 3;
    private static final int SET_RC_STATUS_FAIL = 5;
    private static final int SET_RC_STATUS_IDLE = 0;
    private static final int SET_RC_STATUS_STARTED = 2;
    private static final int SET_RC_STATUS_STARTING = 1;
    private static final int SET_RC_STATUS_SUCCESS = 4;
    private static final int SET_RC_TIMEOUT_WAITING_MSEC = 45000;
    @UnsupportedAppUsage
    private static ProxyController sProxyController;
    private CommandsInterface[] mCi;
    private Context mContext;
    private String[] mCurrentLogicalModemIds;
    private Handler mHandler = new Handler(){

        public void handleMessage(Message message) {
            ProxyController proxyController = ProxyController.this;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("handleMessage msg.what=");
            stringBuilder.append(message.what);
            proxyController.logd(stringBuilder.toString());
            int n = message.what;
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            if (n == 5) {
                                ProxyController.this.onTimeoutRadioCapability(message);
                            }
                        } else {
                            ProxyController.this.onFinishRadioCapabilityResponse(message);
                        }
                    } else {
                        ProxyController.this.onApplyRadioCapabilityResponse(message);
                    }
                } else {
                    ProxyController.this.onStartRadioCapabilityResponse(message);
                }
            } else {
                ProxyController.this.onNotificationRadioCapabilityChanged(message);
            }
        }
    };
    private String[] mNewLogicalModemIds;
    private int[] mNewRadioAccessFamily;
    @UnsupportedAppUsage
    private int[] mOldRadioAccessFamily;
    private PhoneSubInfoController mPhoneSubInfoController;
    private PhoneSwitcher mPhoneSwitcher;
    private Phone[] mPhones;
    private int mRadioAccessFamilyStatusCounter;
    @UnsupportedAppUsage
    private int mRadioCapabilitySessionId;
    @UnsupportedAppUsage
    private int[] mSetRadioAccessFamilyStatus;
    private SmsController mSmsController;
    private boolean mTransactionFailed = false;
    private UiccController mUiccController;
    private UiccPhoneBookController mUiccPhoneBookController;
    @UnsupportedAppUsage
    private AtomicInteger mUniqueIdGenerator = new AtomicInteger(new Random().nextInt());
    PowerManager.WakeLock mWakeLock;

    private ProxyController(Context arrphone, Phone[] arrphone2, UiccController uiccController, CommandsInterface[] arrcommandsInterface, PhoneSwitcher phoneSwitcher) {
        this.logd("Constructor - Enter");
        this.mContext = arrphone;
        this.mPhones = arrphone2;
        this.mUiccController = uiccController;
        this.mCi = arrcommandsInterface;
        this.mPhoneSwitcher = phoneSwitcher;
        RcsMessageStoreController.init((Context)arrphone);
        this.mUiccPhoneBookController = new UiccPhoneBookController(this.mPhones);
        this.mPhoneSubInfoController = new PhoneSubInfoController(this.mContext, this.mPhones);
        this.mSmsController = new SmsController(this.mContext);
        arrphone2 = this.mPhones;
        this.mSetRadioAccessFamilyStatus = new int[arrphone2.length];
        this.mNewRadioAccessFamily = new int[arrphone2.length];
        this.mOldRadioAccessFamily = new int[arrphone2.length];
        this.mCurrentLogicalModemIds = new String[arrphone2.length];
        this.mNewLogicalModemIds = new String[arrphone2.length];
        this.mWakeLock = ((PowerManager)arrphone.getSystemService("power")).newWakeLock(1, LOG_TAG);
        this.mWakeLock.setReferenceCounted(false);
        this.clearTransaction();
        for (int i = 0; i < (arrphone = this.mPhones).length; ++i) {
            arrphone[i].registerForRadioCapabilityChanged(this.mHandler, 1, null);
        }
        this.logd("Constructor - Exit");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void clearTransaction() {
        this.logd("clearTransaction");
        int[] arrn = this.mSetRadioAccessFamilyStatus;
        synchronized (arrn) {
            for (int i = 0; i < this.mPhones.length; ++i) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("clearTransaction: phoneId=");
                stringBuilder.append(i);
                stringBuilder.append(" status=IDLE");
                this.logd(stringBuilder.toString());
                this.mSetRadioAccessFamilyStatus[i] = 0;
                this.mOldRadioAccessFamily[i] = 0;
                this.mNewRadioAccessFamily[i] = 0;
                this.mTransactionFailed = false;
            }
            if (this.mWakeLock.isHeld()) {
                this.mWakeLock.release();
            }
            return;
        }
    }

    @UnsupportedAppUsage
    private void completeRadioCapabilityTransaction() {
        Object object = new StringBuilder();
        ((StringBuilder)object).append("onFinishRadioCapabilityResponse: success=");
        ((StringBuilder)object).append(this.mTransactionFailed ^ true);
        this.logd(((StringBuilder)object).toString());
        if (!this.mTransactionFailed) {
            ArrayList<RadioAccessFamily> arrayList = new ArrayList<RadioAccessFamily>();
            for (int i = 0; i < ((Phone[])(object = this.mPhones)).length; ++i) {
                int n = object[i].getRadioAccessFamily();
                object = new StringBuilder();
                ((StringBuilder)object).append("radioAccessFamily[");
                ((StringBuilder)object).append(i);
                ((StringBuilder)object).append("]=");
                ((StringBuilder)object).append(n);
                this.logd(((StringBuilder)object).toString());
                arrayList.add(new RadioAccessFamily(i, n));
            }
            object = new Intent("android.intent.action.ACTION_SET_RADIO_CAPABILITY_DONE");
            object.putParcelableArrayListExtra("rafs", arrayList);
            this.mRadioCapabilitySessionId = this.mUniqueIdGenerator.getAndIncrement();
            this.clearTransaction();
        } else {
            object = new Intent("android.intent.action.ACTION_SET_RADIO_CAPABILITY_FAILED");
            this.mTransactionFailed = false;
            RadioAccessFamily[] arrradioAccessFamily = new RadioAccessFamily[this.mPhones.length];
            for (int i = 0; i < this.mPhones.length; ++i) {
                arrradioAccessFamily[i] = new RadioAccessFamily(i, this.mOldRadioAccessFamily[i]);
            }
            this.doSetRadioCapabilities(arrradioAccessFamily);
        }
        this.mContext.sendBroadcast((Intent)object, "android.permission.READ_PHONE_STATE");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean doSetRadioCapabilities(RadioAccessFamily[] arrradioAccessFamily) {
        this.mRadioCapabilitySessionId = this.mUniqueIdGenerator.getAndIncrement();
        int[] arrn = this.mHandler;
        int n = this.mRadioCapabilitySessionId;
        int n2 = 0;
        arrn = arrn.obtainMessage(5, n, 0);
        this.mHandler.sendMessageDelayed((Message)arrn, 45000L);
        arrn = this.mSetRadioAccessFamilyStatus;
        synchronized (arrn) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("setRadioCapability: new request session id=");
            stringBuilder.append(this.mRadioCapabilitySessionId);
            this.logd(stringBuilder.toString());
            this.resetRadioAccessFamilyStatusCounter();
            while (n2 < arrradioAccessFamily.length) {
                int n3;
                n = arrradioAccessFamily[n2].getPhoneId();
                stringBuilder = new StringBuilder();
                stringBuilder.append("setRadioCapability: phoneId=");
                stringBuilder.append(n);
                stringBuilder.append(" status=STARTING");
                this.logd(stringBuilder.toString());
                this.mSetRadioAccessFamilyStatus[n] = 1;
                this.mOldRadioAccessFamily[n] = this.mPhones[n].getRadioAccessFamily();
                this.mNewRadioAccessFamily[n] = n3 = arrradioAccessFamily[n2].getRadioAccessFamily();
                this.mCurrentLogicalModemIds[n] = this.mPhones[n].getModemUuId();
                this.mNewLogicalModemIds[n] = this.getLogicalModemIdFromRaf(n3);
                stringBuilder = new StringBuilder();
                stringBuilder.append("setRadioCapability: mOldRadioAccessFamily[");
                stringBuilder.append(n);
                stringBuilder.append("]=");
                stringBuilder.append(this.mOldRadioAccessFamily[n]);
                this.logd(stringBuilder.toString());
                stringBuilder = new StringBuilder();
                stringBuilder.append("setRadioCapability: mNewRadioAccessFamily[");
                stringBuilder.append(n);
                stringBuilder.append("]=");
                stringBuilder.append(this.mNewRadioAccessFamily[n]);
                this.logd(stringBuilder.toString());
                this.sendRadioCapabilityRequest(n, this.mRadioCapabilitySessionId, 1, this.mOldRadioAccessFamily[n], this.mCurrentLogicalModemIds[n], 0, 2);
                ++n2;
            }
            return true;
        }
    }

    @UnsupportedAppUsage
    public static ProxyController getInstance() {
        return sProxyController;
    }

    public static ProxyController getInstance(Context context, Phone[] arrphone, UiccController uiccController, CommandsInterface[] arrcommandsInterface, PhoneSwitcher phoneSwitcher) {
        if (sProxyController == null) {
            sProxyController = new ProxyController(context, arrphone, uiccController, arrcommandsInterface, phoneSwitcher);
        }
        return sProxyController;
    }

    private String getLogicalModemIdFromRaf(int n) {
        String string;
        String string2 = null;
        int n2 = 0;
        do {
            Phone[] arrphone = this.mPhones;
            string = string2;
            if (n2 >= arrphone.length) break;
            if (arrphone[n2].getRadioAccessFamily() == n) {
                string = this.mPhones[n2].getModemUuId();
                break;
            }
            ++n2;
        } while (true);
        return string;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void issueFinish(int n) {
        int[] arrn = this.mSetRadioAccessFamilyStatus;
        synchronized (arrn) {
            int n2 = 0;
            while (n2 < this.mPhones.length) {
                CharSequence charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("issueFinish: phoneId=");
                ((StringBuilder)charSequence).append(n2);
                ((StringBuilder)charSequence).append(" sessionId=");
                ((StringBuilder)charSequence).append(n);
                ((StringBuilder)charSequence).append(" mTransactionFailed=");
                ((StringBuilder)charSequence).append(this.mTransactionFailed);
                this.logd(((StringBuilder)charSequence).toString());
                ++this.mRadioAccessFamilyStatusCounter;
                int n3 = this.mTransactionFailed ? this.mOldRadioAccessFamily[n2] : this.mNewRadioAccessFamily[n2];
                charSequence = this.mTransactionFailed ? this.mCurrentLogicalModemIds[n2] : this.mNewLogicalModemIds[n2];
                int n4 = this.mTransactionFailed ? 2 : 1;
                this.sendRadioCapabilityRequest(n2, n, 4, n3, (String)charSequence, n4, 4);
                if (this.mTransactionFailed) {
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("issueFinish: phoneId: ");
                    ((StringBuilder)charSequence).append(n2);
                    ((StringBuilder)charSequence).append(" status: FAIL");
                    this.logd(((StringBuilder)charSequence).toString());
                    this.mSetRadioAccessFamilyStatus[n2] = 5;
                }
                ++n2;
            }
            return;
        }
    }

    @UnsupportedAppUsage
    private void logd(String string) {
        Rlog.d((String)LOG_TAG, (String)string);
    }

    private void loge(String string) {
        Rlog.e((String)LOG_TAG, (String)string);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void onApplyRadioCapabilityResponse(Message object) {
        Object object2 = (RadioCapability)((AsyncResult)object.obj).result;
        if (object2 != null && ((RadioCapability)object2).getSession() == this.mRadioCapabilitySessionId) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onApplyRadioCapabilityResponse: rc=");
            stringBuilder.append(object2);
            this.logd(stringBuilder.toString());
            if (((AsyncResult)object.obj).exception != null) {
                object = this.mSetRadioAccessFamilyStatus;
                synchronized (object) {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("onApplyRadioCapabilityResponse: Error response session=");
                    stringBuilder.append(((RadioCapability)object2).getSession());
                    this.logd(stringBuilder.toString());
                    int n = ((RadioCapability)object2).getPhoneId();
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("onApplyRadioCapabilityResponse: phoneId=");
                    ((StringBuilder)object2).append(n);
                    ((StringBuilder)object2).append(" status=FAIL");
                    this.logd(((StringBuilder)object2).toString());
                    this.mSetRadioAccessFamilyStatus[n] = 5;
                    this.mTransactionFailed = true;
                    return;
                }
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("onApplyRadioCapabilityResponse: Valid start expecting notification rc=");
            ((StringBuilder)object).append(object2);
            this.logd(((StringBuilder)object).toString());
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("onApplyRadioCapabilityResponse: Ignore session=");
        ((StringBuilder)object).append(this.mRadioCapabilitySessionId);
        ((StringBuilder)object).append(" rc=");
        ((StringBuilder)object).append(object2);
        this.logd(((StringBuilder)object).toString());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void onNotificationRadioCapabilityChanged(Message object) {
        RadioCapability radioCapability = (RadioCapability)((AsyncResult)object.obj).result;
        if (radioCapability != null && radioCapability.getSession() == this.mRadioCapabilitySessionId) {
            int[] arrn = this.mSetRadioAccessFamilyStatus;
            synchronized (arrn) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("onNotificationRadioCapabilityChanged: rc=");
                stringBuilder.append(radioCapability);
                this.logd(stringBuilder.toString());
                if (radioCapability.getSession() != this.mRadioCapabilitySessionId) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("onNotificationRadioCapabilityChanged: Ignore session=");
                    ((StringBuilder)object).append(this.mRadioCapabilitySessionId);
                    ((StringBuilder)object).append(" rc=");
                    ((StringBuilder)object).append(radioCapability);
                    this.logd(((StringBuilder)object).toString());
                    return;
                }
                int n = radioCapability.getPhoneId();
                if (((AsyncResult)object.obj).exception == null && radioCapability.getStatus() != 2) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("onNotificationRadioCapabilityChanged: phoneId=");
                    ((StringBuilder)object).append(n);
                    ((StringBuilder)object).append(" status=SUCCESS");
                    this.logd(((StringBuilder)object).toString());
                    this.mSetRadioAccessFamilyStatus[n] = 4;
                    this.mPhoneSwitcher.onRadioCapChanged(n);
                    this.mPhones[n].radioCapabilityUpdated(radioCapability);
                } else {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("onNotificationRadioCapabilityChanged: phoneId=");
                    ((StringBuilder)object).append(n);
                    ((StringBuilder)object).append(" status=FAIL");
                    this.logd(((StringBuilder)object).toString());
                    this.mSetRadioAccessFamilyStatus[n] = 5;
                    this.mTransactionFailed = true;
                }
                --this.mRadioAccessFamilyStatusCounter;
                if (this.mRadioAccessFamilyStatusCounter == 0) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("onNotificationRadioCapabilityChanged: APPLY URC success=");
                    ((StringBuilder)object).append(this.mTransactionFailed);
                    this.logd(((StringBuilder)object).toString());
                    this.issueFinish(this.mRadioCapabilitySessionId);
                }
                return;
            }
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("onNotificationRadioCapabilityChanged: Ignore session=");
        ((StringBuilder)object).append(this.mRadioCapabilitySessionId);
        ((StringBuilder)object).append(" rc=");
        ((StringBuilder)object).append(radioCapability);
        this.logd(((StringBuilder)object).toString());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void onStartRadioCapabilityResponse(Message object) {
        int[] arrn = this.mSetRadioAccessFamilyStatus;
        synchronized (arrn) {
            Object object2 = (AsyncResult)((Message)object).obj;
            int n = TelephonyManager.getDefault().getPhoneCount();
            boolean bl = true;
            if (n == 1 && ((AsyncResult)object2).exception != null) {
                object = new StringBuilder();
                ((StringBuilder)object).append("onStartRadioCapabilityResponse got exception=");
                ((StringBuilder)object).append(((AsyncResult)object2).exception);
                this.logd(((StringBuilder)object).toString());
                this.mRadioCapabilitySessionId = this.mUniqueIdGenerator.getAndIncrement();
                object = new Intent("android.intent.action.ACTION_SET_RADIO_CAPABILITY_FAILED");
                this.mContext.sendBroadcast((Intent)object);
                this.clearTransaction();
                return;
            }
            object2 = (RadioCapability)((AsyncResult)object.obj).result;
            if (object2 != null && ((RadioCapability)object2).getSession() == this.mRadioCapabilitySessionId) {
                --this.mRadioAccessFamilyStatusCounter;
                n = ((RadioCapability)object2).getPhoneId();
                if (((AsyncResult)object.obj).exception != null) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("onStartRadioCapabilityResponse: Error response session=");
                    ((StringBuilder)object).append(((RadioCapability)object2).getSession());
                    this.logd(((StringBuilder)object).toString());
                    object = new StringBuilder();
                    ((StringBuilder)object).append("onStartRadioCapabilityResponse: phoneId=");
                    ((StringBuilder)object).append(n);
                    ((StringBuilder)object).append(" status=FAIL");
                    this.logd(((StringBuilder)object).toString());
                    this.mSetRadioAccessFamilyStatus[n] = 5;
                    this.mTransactionFailed = true;
                } else {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("onStartRadioCapabilityResponse: phoneId=");
                    ((StringBuilder)object).append(n);
                    ((StringBuilder)object).append(" status=STARTED");
                    this.logd(((StringBuilder)object).toString());
                    this.mSetRadioAccessFamilyStatus[n] = 2;
                }
                if (this.mRadioAccessFamilyStatusCounter == 0) {
                    object = new HashSet(this.mNewLogicalModemIds.length);
                    object2 = this.mNewLogicalModemIds;
                    int n2 = ((Object)object2).length;
                    for (n = 0; n < n2; ++n) {
                        if (((HashSet)object).add(object2[n])) continue;
                        this.mTransactionFailed = true;
                        Log.wtf((String)LOG_TAG, (String)"ERROR: sending down the same id for different phones");
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append("onStartRadioCapabilityResponse: success=");
                    if (this.mTransactionFailed) {
                        bl = false;
                    }
                    ((StringBuilder)object).append(bl);
                    this.logd(((StringBuilder)object).toString());
                    if (this.mTransactionFailed) {
                        this.issueFinish(this.mRadioCapabilitySessionId);
                    } else {
                        this.resetRadioAccessFamilyStatusCounter();
                        for (n = 0; n < this.mPhones.length; ++n) {
                            this.sendRadioCapabilityRequest(n, this.mRadioCapabilitySessionId, 2, this.mNewRadioAccessFamily[n], this.mNewLogicalModemIds[n], 0, 3);
                            object = new StringBuilder();
                            ((StringBuilder)object).append("onStartRadioCapabilityResponse: phoneId=");
                            ((StringBuilder)object).append(n);
                            ((StringBuilder)object).append(" status=APPLYING");
                            this.logd(((StringBuilder)object).toString());
                            this.mSetRadioAccessFamilyStatus[n] = 3;
                        }
                    }
                }
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("onStartRadioCapabilityResponse: Ignore session=");
            ((StringBuilder)object).append(this.mRadioCapabilitySessionId);
            ((StringBuilder)object).append(" rc=");
            ((StringBuilder)object).append(object2);
            this.logd(((StringBuilder)object).toString());
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void onTimeoutRadioCapability(Message arrn) {
        if (arrn.arg1 != this.mRadioCapabilitySessionId) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("RadioCapability timeout: Ignore msg.arg1=");
            stringBuilder.append(arrn.arg1);
            stringBuilder.append("!= mRadioCapabilitySessionId=");
            stringBuilder.append(this.mRadioCapabilitySessionId);
            this.logd(stringBuilder.toString());
            return;
        }
        arrn = this.mSetRadioAccessFamilyStatus;
        synchronized (arrn) {
            int n = 0;
            do {
                if (n >= this.mPhones.length) {
                    this.mRadioCapabilitySessionId = this.mUniqueIdGenerator.getAndIncrement();
                    this.mRadioAccessFamilyStatusCounter = 0;
                    this.mTransactionFailed = true;
                    this.issueFinish(this.mRadioCapabilitySessionId);
                    return;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("RadioCapability timeout: mSetRadioAccessFamilyStatus[");
                stringBuilder.append(n);
                stringBuilder.append("]=");
                stringBuilder.append(this.mSetRadioAccessFamilyStatus[n]);
                this.logd(stringBuilder.toString());
                ++n;
            } while (true);
        }
    }

    private void resetRadioAccessFamilyStatusCounter() {
        this.mRadioAccessFamilyStatusCounter = this.mPhones.length;
    }

    @UnsupportedAppUsage
    private void sendRadioCapabilityRequest(int n, int n2, int n3, int n4, String object, int n5, int n6) {
        object = new RadioCapability(n, n2, n3, n4, (String)object, n5);
        this.mPhones[n].setRadioCapability((RadioCapability)object, this.mHandler.obtainMessage(n6));
    }

    public boolean areAllDataDisconnected(int n) {
        n = SubscriptionController.getInstance().getPhoneId(n);
        if (n >= 0 && n < TelephonyManager.getDefault().getPhoneCount()) {
            return this.mPhones[n].areAllDataDisconnected();
        }
        return true;
    }

    public int getMaxRafSupported() {
        Phone[] arrphone;
        int[] arrn = new int[this.mPhones.length];
        int n = 0;
        int n2 = 0;
        for (int i = 0; i < (arrphone = this.mPhones).length; ++i) {
            arrn[i] = Integer.bitCount(arrphone[i].getRadioAccessFamily());
            int n3 = n;
            if (n < arrn[i]) {
                n3 = arrn[i];
                n2 = this.mPhones[i].getRadioAccessFamily();
            }
            n = n3;
        }
        return n2;
    }

    public int getMinRafSupported() {
        Phone[] arrphone;
        int[] arrn = new int[this.mPhones.length];
        int n = 0;
        int n2 = 0;
        for (int i = 0; i < (arrphone = this.mPhones).length; ++i) {
            int n3;
            block4 : {
                block3 : {
                    arrn[i] = Integer.bitCount(arrphone[i].getRadioAccessFamily());
                    if (n == 0) break block3;
                    n3 = n;
                    if (n <= arrn[i]) break block4;
                }
                n3 = arrn[i];
                n2 = this.mPhones[i].getRadioAccessFamily();
            }
            n = n3;
        }
        return n2;
    }

    public int getRadioAccessFamily(int n) {
        Phone[] arrphone = this.mPhones;
        if (n >= arrphone.length) {
            return 0;
        }
        return arrphone[n].getRadioAccessFamily();
    }

    public SmsController getSmsController() {
        return this.mSmsController;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void onFinishRadioCapabilityResponse(Message object) {
        Object object2 = (RadioCapability)((AsyncResult)object.obj).result;
        if (object2 != null && ((RadioCapability)object2).getSession() == this.mRadioCapabilitySessionId) {
            object = this.mSetRadioAccessFamilyStatus;
            synchronized (object) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append(" onFinishRadioCapabilityResponse mRadioAccessFamilyStatusCounter=");
                ((StringBuilder)object2).append(this.mRadioAccessFamilyStatusCounter);
                this.logd(((StringBuilder)object2).toString());
                --this.mRadioAccessFamilyStatusCounter;
                if (this.mRadioAccessFamilyStatusCounter == 0) {
                    this.completeRadioCapabilityTransaction();
                }
                return;
            }
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("onFinishRadioCapabilityResponse: Ignore session=");
        ((StringBuilder)object).append(this.mRadioCapabilitySessionId);
        ((StringBuilder)object).append(" rc=");
        ((StringBuilder)object).append(object2);
        this.logd(((StringBuilder)object).toString());
    }

    public void registerForAllDataDisconnected(int n, Handler handler, int n2) {
        n = SubscriptionController.getInstance().getPhoneId(n);
        if (n >= 0 && n < TelephonyManager.getDefault().getPhoneCount()) {
            this.mPhones[n].registerForAllDataDisconnected(handler, n2);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean setRadioCapability(RadioAccessFamily[] object) {
        int n;
        if (((RadioAccessFamily[])object).length != this.mPhones.length) {
            throw new RuntimeException("Length of input rafs must equal to total phone count");
        }
        Object[] arrobject = this.mSetRadioAccessFamilyStatus;
        synchronized (arrobject) {
            for (n = 0; n < this.mPhones.length; ++n) {
                if (this.mSetRadioAccessFamilyStatus[n] == 0) continue;
                object = new StringBuilder();
                ((StringBuilder)object).append("setRadioCapability: Phone[");
                ((StringBuilder)object).append(n);
                ((StringBuilder)object).append("] is not idle. Rejecting request.");
                this.loge(((StringBuilder)object).toString());
                return false;
            }
        }
        boolean bl = true;
        for (n = 0; n < (arrobject = this.mPhones).length; ++n) {
            if (arrobject[n].getRadioAccessFamily() == object[n].getRadioAccessFamily()) continue;
            bl = false;
        }
        if (bl) {
            this.logd("setRadioCapability: Already in requested configuration, nothing to do.");
            return true;
        }
        this.clearTransaction();
        this.mWakeLock.acquire();
        return this.doSetRadioCapabilities((RadioAccessFamily[])object);
    }

    public void unregisterForAllDataDisconnected(int n, Handler handler) {
        n = SubscriptionController.getInstance().getPhoneId(n);
        if (n >= 0 && n < TelephonyManager.getDefault().getPhoneCount()) {
            this.mPhones[n].unregisterForAllDataDisconnected(handler);
        }
    }

}

