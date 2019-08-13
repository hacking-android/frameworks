/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 *  android.content.Context
 *  android.os.AsyncResult
 *  android.os.Handler
 *  android.os.Message
 *  android.os.Registrant
 *  android.os.RegistrantList
 *  android.telephony.Rlog
 */
package com.android.internal.telephony.uicc;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.os.AsyncResult;
import android.os.Handler;
import android.os.Message;
import android.os.Registrant;
import android.os.RegistrantList;
import android.telephony.Rlog;
import com.android.internal.telephony.CommandsInterface;
import com.android.internal.telephony.uicc.CsimFileHandler;
import com.android.internal.telephony.uicc.IccCardApplicationStatus;
import com.android.internal.telephony.uicc.IccCardStatus;
import com.android.internal.telephony.uicc.IccFileHandler;
import com.android.internal.telephony.uicc.IccRecords;
import com.android.internal.telephony.uicc.IsimFileHandler;
import com.android.internal.telephony.uicc.IsimUiccRecords;
import com.android.internal.telephony.uicc.RuimFileHandler;
import com.android.internal.telephony.uicc.RuimRecords;
import com.android.internal.telephony.uicc.SIMFileHandler;
import com.android.internal.telephony.uicc.SIMRecords;
import com.android.internal.telephony.uicc.UiccProfile;
import com.android.internal.telephony.uicc.UsimFileHandler;
import java.io.FileDescriptor;
import java.io.PrintWriter;

public class UiccCardApplication {
    public static final int AUTH_CONTEXT_EAP_AKA = 129;
    public static final int AUTH_CONTEXT_EAP_SIM = 128;
    public static final int AUTH_CONTEXT_UNDEFINED = -1;
    private static final boolean DBG = true;
    private static final int EVENT_CHANGE_FACILITY_FDN_DONE = 5;
    private static final int EVENT_CHANGE_FACILITY_LOCK_DONE = 7;
    private static final int EVENT_CHANGE_PIN1_DONE = 2;
    private static final int EVENT_CHANGE_PIN2_DONE = 3;
    private static final int EVENT_PIN1_PUK1_DONE = 1;
    private static final int EVENT_PIN2_PUK2_DONE = 8;
    private static final int EVENT_QUERY_FACILITY_FDN_DONE = 4;
    private static final int EVENT_QUERY_FACILITY_LOCK_DONE = 6;
    private static final int EVENT_RADIO_UNAVAILABLE = 9;
    private static final String LOG_TAG = "UiccCardApplication";
    @UnsupportedAppUsage
    private String mAid;
    private String mAppLabel;
    @UnsupportedAppUsage
    private IccCardApplicationStatus.AppState mAppState;
    @UnsupportedAppUsage
    private IccCardApplicationStatus.AppType mAppType;
    private int mAuthContext;
    @UnsupportedAppUsage
    private CommandsInterface mCi;
    private Context mContext;
    private boolean mDesiredFdnEnabled;
    private boolean mDesiredPinLocked;
    @UnsupportedAppUsage
    private boolean mDestroyed;
    private Handler mHandler;
    private boolean mIccFdnAvailable;
    private boolean mIccFdnEnabled;
    private IccFileHandler mIccFh;
    private boolean mIccLockEnabled;
    private IccRecords mIccRecords;
    private boolean mIgnoreApp;
    @UnsupportedAppUsage
    private final Object mLock = new Object();
    private RegistrantList mNetworkLockedRegistrants;
    @UnsupportedAppUsage
    private IccCardApplicationStatus.PersoSubState mPersoSubState;
    private boolean mPin1Replaced;
    @UnsupportedAppUsage
    private IccCardStatus.PinState mPin1State;
    private IccCardStatus.PinState mPin2State;
    private RegistrantList mPinLockedRegistrants;
    private RegistrantList mReadyRegistrants;
    private UiccProfile mUiccProfile;

    public UiccCardApplication(UiccProfile uiccProfile, IccCardApplicationStatus iccCardApplicationStatus, Context context, CommandsInterface commandsInterface) {
        boolean bl = true;
        this.mIccFdnAvailable = true;
        this.mReadyRegistrants = new RegistrantList();
        this.mPinLockedRegistrants = new RegistrantList();
        this.mNetworkLockedRegistrants = new RegistrantList();
        this.mHandler = new Handler(){

            public void handleMessage(Message message) {
                if (UiccCardApplication.this.mDestroyed) {
                    UiccCardApplication uiccCardApplication = UiccCardApplication.this;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Received message ");
                    stringBuilder.append((Object)message);
                    stringBuilder.append("[");
                    stringBuilder.append(message.what);
                    stringBuilder.append("] while being destroyed. Ignoring.");
                    uiccCardApplication.loge(stringBuilder.toString());
                    return;
                }
                switch (message.what) {
                    default: {
                        UiccCardApplication uiccCardApplication = UiccCardApplication.this;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Unknown Event ");
                        stringBuilder.append(message.what);
                        uiccCardApplication.loge(stringBuilder.toString());
                        break;
                    }
                    case 9: {
                        UiccCardApplication.this.log("handleMessage (EVENT_RADIO_UNAVAILABLE)");
                        UiccCardApplication.this.mAppState = IccCardApplicationStatus.AppState.APPSTATE_UNKNOWN;
                        break;
                    }
                    case 7: {
                        message = (AsyncResult)message.obj;
                        UiccCardApplication.this.onChangeFacilityLock((AsyncResult)message);
                        break;
                    }
                    case 6: {
                        message = (AsyncResult)message.obj;
                        UiccCardApplication.this.onQueryFacilityLock((AsyncResult)message);
                        break;
                    }
                    case 5: {
                        message = (AsyncResult)message.obj;
                        UiccCardApplication.this.onChangeFdnDone((AsyncResult)message);
                        break;
                    }
                    case 4: {
                        message = (AsyncResult)message.obj;
                        UiccCardApplication.this.onQueryFdnEnabled((AsyncResult)message);
                        break;
                    }
                    case 1: 
                    case 2: 
                    case 3: 
                    case 8: {
                        message = (AsyncResult)message.obj;
                        int n = UiccCardApplication.this.parsePinPukErrorResult((AsyncResult)message);
                        Message message2 = (Message)message.userObj;
                        AsyncResult.forMessage((Message)message2).exception = message.exception;
                        message2.arg1 = n;
                        message2.sendToTarget();
                    }
                }
            }
        };
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Creating UiccApp: ");
        stringBuilder.append(iccCardApplicationStatus);
        this.log(stringBuilder.toString());
        this.mUiccProfile = uiccProfile;
        this.mAppState = iccCardApplicationStatus.app_state;
        this.mAppType = iccCardApplicationStatus.app_type;
        this.mAuthContext = UiccCardApplication.getAuthContext(this.mAppType);
        this.mPersoSubState = iccCardApplicationStatus.perso_substate;
        this.mAid = iccCardApplicationStatus.aid;
        this.mAppLabel = iccCardApplicationStatus.app_label;
        if (iccCardApplicationStatus.pin1_replaced == 0) {
            bl = false;
        }
        this.mPin1Replaced = bl;
        this.mPin1State = iccCardApplicationStatus.pin1;
        this.mPin2State = iccCardApplicationStatus.pin2;
        this.mIgnoreApp = false;
        this.mContext = context;
        this.mCi = commandsInterface;
        this.mIccFh = this.createIccFileHandler(iccCardApplicationStatus.app_type);
        this.mIccRecords = this.createIccRecords(iccCardApplicationStatus.app_type, this.mContext, this.mCi);
        if (this.mAppState == IccCardApplicationStatus.AppState.APPSTATE_READY) {
            this.queryFdn();
            this.queryPin1State();
        }
        this.mCi.registerForNotAvailable(this.mHandler, 9, null);
    }

    private IccFileHandler createIccFileHandler(IccCardApplicationStatus.AppType appType) {
        int n = 2.$SwitchMap$com$android$internal$telephony$uicc$IccCardApplicationStatus$AppType[appType.ordinal()];
        if (n != 1) {
            if (n != 2) {
                if (n != 3) {
                    if (n != 4) {
                        if (n != 5) {
                            return null;
                        }
                        return new IsimFileHandler(this, this.mAid, this.mCi);
                    }
                    return new CsimFileHandler(this, this.mAid, this.mCi);
                }
                return new UsimFileHandler(this, this.mAid, this.mCi);
            }
            return new RuimFileHandler(this, this.mAid, this.mCi);
        }
        return new SIMFileHandler(this, this.mAid, this.mCi);
    }

    private IccRecords createIccRecords(IccCardApplicationStatus.AppType appType, Context context, CommandsInterface commandsInterface) {
        if (appType != IccCardApplicationStatus.AppType.APPTYPE_USIM && appType != IccCardApplicationStatus.AppType.APPTYPE_SIM) {
            if (appType != IccCardApplicationStatus.AppType.APPTYPE_RUIM && appType != IccCardApplicationStatus.AppType.APPTYPE_CSIM) {
                if (appType == IccCardApplicationStatus.AppType.APPTYPE_ISIM) {
                    return new IsimUiccRecords(this, context, commandsInterface);
                }
                return null;
            }
            return new RuimRecords(this, context, commandsInterface);
        }
        return new SIMRecords(this, context, commandsInterface);
    }

    private static int getAuthContext(IccCardApplicationStatus.AppType appType) {
        int n = 2.$SwitchMap$com$android$internal$telephony$uicc$IccCardApplicationStatus$AppType[appType.ordinal()];
        n = n != 1 ? (n != 3 ? -1 : 129) : 128;
        return n;
    }

    @UnsupportedAppUsage
    private void log(String string) {
        Rlog.d((String)LOG_TAG, (String)string);
    }

    @UnsupportedAppUsage
    private void loge(String string) {
        Rlog.e((String)LOG_TAG, (String)string);
    }

    private void notifyNetworkLockedRegistrantsIfNeeded(Registrant registrant) {
        if (this.mDestroyed) {
            return;
        }
        if (this.mAppState == IccCardApplicationStatus.AppState.APPSTATE_SUBSCRIPTION_PERSO && this.mPersoSubState == IccCardApplicationStatus.PersoSubState.PERSOSUBSTATE_SIM_NETWORK) {
            if (registrant == null) {
                this.log("Notifying registrants: NETWORK_LOCKED");
                this.mNetworkLockedRegistrants.notifyRegistrants();
            } else {
                this.log("Notifying 1 registrant: NETWORK_LOCED");
                registrant.notifyRegistrant(new AsyncResult(null, null, null));
            }
        }
    }

    private void notifyPinLockedRegistrantsIfNeeded(Registrant registrant) {
        block7 : {
            block6 : {
                if (this.mDestroyed) {
                    return;
                }
                if (this.mAppState != IccCardApplicationStatus.AppState.APPSTATE_PIN && this.mAppState != IccCardApplicationStatus.AppState.APPSTATE_PUK) break block6;
                if (this.mPin1State == IccCardStatus.PinState.PINSTATE_ENABLED_VERIFIED || this.mPin1State == IccCardStatus.PinState.PINSTATE_DISABLED) break block7;
                if (registrant == null) {
                    this.log("Notifying registrants: LOCKED");
                    this.mPinLockedRegistrants.notifyRegistrants();
                } else {
                    this.log("Notifying 1 registrant: LOCKED");
                    registrant.notifyRegistrant(new AsyncResult(null, null, null));
                }
            }
            return;
        }
        this.loge("Sanity check failed! APPSTATE is locked while PIN1 is not!!!");
    }

    private void notifyReadyRegistrantsIfNeeded(Registrant registrant) {
        if (this.mDestroyed) {
            return;
        }
        if (this.mAppState == IccCardApplicationStatus.AppState.APPSTATE_READY) {
            if (this.mPin1State != IccCardStatus.PinState.PINSTATE_ENABLED_NOT_VERIFIED && this.mPin1State != IccCardStatus.PinState.PINSTATE_ENABLED_BLOCKED && this.mPin1State != IccCardStatus.PinState.PINSTATE_ENABLED_PERM_BLOCKED) {
                if (registrant == null) {
                    this.log("Notifying registrants: READY");
                    this.mReadyRegistrants.notifyRegistrants();
                } else {
                    this.log("Notifying 1 registrant: READY");
                    registrant.notifyRegistrant(new AsyncResult(null, null, null));
                }
            } else {
                this.loge("Sanity check failed! APPSTATE is ready while PIN1 is not verified!!!");
                return;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void onChangeFacilityLock(AsyncResult asyncResult) {
        Object object = this.mLock;
        synchronized (object) {
            StringBuilder stringBuilder;
            int n = -1;
            if (asyncResult.exception == null) {
                this.mIccLockEnabled = this.mDesiredPinLocked;
                stringBuilder = new StringBuilder();
                stringBuilder.append("EVENT_CHANGE_FACILITY_LOCK_DONE: mIccLockEnabled= ");
                stringBuilder.append(this.mIccLockEnabled);
                this.log(stringBuilder.toString());
            } else {
                n = this.parsePinPukErrorResult(asyncResult);
                stringBuilder = new StringBuilder();
                stringBuilder.append("Error change facility lock with exception ");
                stringBuilder.append(asyncResult.exception);
                this.loge(stringBuilder.toString());
            }
            stringBuilder = (Message)asyncResult.userObj;
            AsyncResult.forMessage((Message)stringBuilder).exception = asyncResult.exception;
            ((Message)stringBuilder).arg1 = n;
            stringBuilder.sendToTarget();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void onChangeFdnDone(AsyncResult asyncResult) {
        Object object = this.mLock;
        synchronized (object) {
            StringBuilder stringBuilder;
            int n = -1;
            if (asyncResult.exception == null) {
                this.mIccFdnEnabled = this.mDesiredFdnEnabled;
                stringBuilder = new StringBuilder();
                stringBuilder.append("EVENT_CHANGE_FACILITY_FDN_DONE: mIccFdnEnabled=");
                stringBuilder.append(this.mIccFdnEnabled);
                this.log(stringBuilder.toString());
            } else {
                n = this.parsePinPukErrorResult(asyncResult);
                stringBuilder = new StringBuilder();
                stringBuilder.append("Error change facility fdn with exception ");
                stringBuilder.append(asyncResult.exception);
                this.loge(stringBuilder.toString());
            }
            stringBuilder = (Message)asyncResult.userObj;
            ((Message)stringBuilder).arg1 = n;
            AsyncResult.forMessage((Message)stringBuilder).exception = asyncResult.exception;
            stringBuilder.sendToTarget();
            return;
        }
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void onQueryFacilityLock(AsyncResult object) {
        Object object2 = this.mLock;
        synchronized (object2) {
            if (((AsyncResult)object).exception != null) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Error in querying facility lock:");
                stringBuilder.append(((AsyncResult)object).exception);
                this.log(stringBuilder.toString());
                return;
            }
            object = (int[])((AsyncResult)object).result;
            if (((int[])object).length != 0) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Query facility lock : ");
                boolean bl = false;
                stringBuilder.append(object[0]);
                this.log(stringBuilder.toString());
                if (object[0] != 0) {
                    bl = true;
                }
                this.mIccLockEnabled = bl;
                int n = 2.$SwitchMap$com$android$internal$telephony$uicc$IccCardStatus$PinState[this.mPin1State.ordinal()];
                if (n != 1) {
                    if (!(n != 2 && n != 3 && n != 4 && n != 5 || this.mIccLockEnabled)) {
                        this.loge("QUERY_FACILITY_LOCK:disabled GET_SIM_STATUS.Pin1:enabled. Fixme");
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Ignoring: pin1state=");
                    ((StringBuilder)object).append((Object)this.mPin1State);
                    this.log(((StringBuilder)object).toString());
                } else if (this.mIccLockEnabled) {
                    this.loge("QUERY_FACILITY_LOCK:enabled GET_SIM_STATUS.Pin1:disabled. Fixme");
                }
            } else {
                this.loge("Bogus facility lock response");
            }
            return;
        }
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void onQueryFdnEnabled(AsyncResult object) {
        Object object2 = this.mLock;
        synchronized (object2) {
            if (((AsyncResult)object).exception != null) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Error in querying facility lock:");
                stringBuilder.append(((AsyncResult)object).exception);
                this.log(stringBuilder.toString());
                return;
            }
            object = (int[])((AsyncResult)object).result;
            if (((int[])object).length != 0) {
                boolean bl = false;
                if (object[0] == 2) {
                    this.mIccFdnEnabled = false;
                    this.mIccFdnAvailable = false;
                } else {
                    if (object[0] == 1) {
                        bl = true;
                    }
                    this.mIccFdnEnabled = bl;
                    this.mIccFdnAvailable = true;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("Query facility FDN : FDN service available: ");
                ((StringBuilder)object).append(this.mIccFdnAvailable);
                ((StringBuilder)object).append(" enabled: ");
                ((StringBuilder)object).append(this.mIccFdnEnabled);
                this.log(((StringBuilder)object).toString());
            } else {
                this.loge("Bogus facility lock response");
            }
            return;
        }
    }

    private int parsePinPukErrorResult(AsyncResult object) {
        object = (int[])((AsyncResult)object).result;
        if (object == null) {
            return -1;
        }
        int n = ((int[])object).length;
        int n2 = -1;
        if (n > 0) {
            n2 = object[0];
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("parsePinPukErrorResult: attemptsRemaining=");
        ((StringBuilder)object).append(n2);
        this.log(((StringBuilder)object).toString());
        return n2;
    }

    private void queryPin1State() {
        this.mCi.queryFacilityLockForApp("SC", "", 7, this.mAid, this.mHandler.obtainMessage(6));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void changeIccFdnPassword(String string, String string2, Message message) {
        Object object = this.mLock;
        synchronized (object) {
            this.log("changeIccFdnPassword");
            this.mCi.changeIccPin2ForApp(string, string2, this.mAid, this.mHandler.obtainMessage(3, (Object)message));
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void changeIccLockPassword(String string, String string2, Message message) {
        Object object = this.mLock;
        synchronized (object) {
            this.log("changeIccLockPassword");
            this.mCi.changeIccPinForApp(string, string2, this.mAid, this.mHandler.obtainMessage(2, (Object)message));
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    void dispose() {
        Object object = this.mLock;
        synchronized (object) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append((Object)this.mAppType);
            stringBuilder.append(" being Disposed");
            this.log(stringBuilder.toString());
            this.mDestroyed = true;
            if (this.mIccRecords != null) {
                this.mIccRecords.dispose();
            }
            if (this.mIccFh != null) {
                this.mIccFh.dispose();
            }
            this.mIccRecords = null;
            this.mIccFh = null;
            this.mCi.unregisterForNotAvailable(this.mHandler);
            return;
        }
    }

    public void dump(FileDescriptor object, PrintWriter printWriter, String[] arrstring) {
        int n;
        object = new StringBuilder();
        ((StringBuilder)object).append("UiccCardApplication: ");
        ((StringBuilder)object).append(this);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mUiccProfile=");
        ((StringBuilder)object).append(this.mUiccProfile);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mAppState=");
        ((StringBuilder)object).append((Object)this.mAppState);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mAppType=");
        ((StringBuilder)object).append((Object)this.mAppType);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mPersoSubState=");
        ((StringBuilder)object).append((Object)this.mPersoSubState);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mAid=");
        ((StringBuilder)object).append(this.mAid);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mAppLabel=");
        ((StringBuilder)object).append(this.mAppLabel);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mPin1Replaced=");
        ((StringBuilder)object).append(this.mPin1Replaced);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mPin1State=");
        ((StringBuilder)object).append((Object)this.mPin1State);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mPin2State=");
        ((StringBuilder)object).append((Object)this.mPin2State);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mIccFdnEnabled=");
        ((StringBuilder)object).append(this.mIccFdnEnabled);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mDesiredFdnEnabled=");
        ((StringBuilder)object).append(this.mDesiredFdnEnabled);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mIccLockEnabled=");
        ((StringBuilder)object).append(this.mIccLockEnabled);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mDesiredPinLocked=");
        ((StringBuilder)object).append(this.mDesiredPinLocked);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mCi=");
        ((StringBuilder)object).append(this.mCi);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mIccRecords=");
        ((StringBuilder)object).append(this.mIccRecords);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mIccFh=");
        ((StringBuilder)object).append(this.mIccFh);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mDestroyed=");
        ((StringBuilder)object).append(this.mDestroyed);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mReadyRegistrants: size=");
        ((StringBuilder)object).append(this.mReadyRegistrants.size());
        printWriter.println(((StringBuilder)object).toString());
        for (n = 0; n < this.mReadyRegistrants.size(); ++n) {
            object = new StringBuilder();
            ((StringBuilder)object).append("  mReadyRegistrants[");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append("]=");
            ((StringBuilder)object).append((Object)((Registrant)this.mReadyRegistrants.get(n)).getHandler());
            printWriter.println(((StringBuilder)object).toString());
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(" mPinLockedRegistrants: size=");
        ((StringBuilder)object).append(this.mPinLockedRegistrants.size());
        printWriter.println(((StringBuilder)object).toString());
        for (n = 0; n < this.mPinLockedRegistrants.size(); ++n) {
            object = new StringBuilder();
            ((StringBuilder)object).append("  mPinLockedRegistrants[");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append("]=");
            ((StringBuilder)object).append((Object)((Registrant)this.mPinLockedRegistrants.get(n)).getHandler());
            printWriter.println(((StringBuilder)object).toString());
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(" mNetworkLockedRegistrants: size=");
        ((StringBuilder)object).append(this.mNetworkLockedRegistrants.size());
        printWriter.println(((StringBuilder)object).toString());
        for (n = 0; n < this.mNetworkLockedRegistrants.size(); ++n) {
            object = new StringBuilder();
            ((StringBuilder)object).append("  mNetworkLockedRegistrants[");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append("]=");
            ((StringBuilder)object).append((Object)((Registrant)this.mNetworkLockedRegistrants.get(n)).getHandler());
            printWriter.println(((StringBuilder)object).toString());
        }
        printWriter.flush();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public String getAid() {
        Object object = this.mLock;
        synchronized (object) {
            return this.mAid;
        }
    }

    public String getAppLabel() {
        return this.mAppLabel;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public int getAuthContext() {
        Object object = this.mLock;
        synchronized (object) {
            return this.mAuthContext;
        }
    }

    public boolean getIccFdnAvailable() {
        return this.mIccFdnAvailable;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean getIccFdnEnabled() {
        Object object = this.mLock;
        synchronized (object) {
            return this.mIccFdnEnabled;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public IccFileHandler getIccFileHandler() {
        Object object = this.mLock;
        synchronized (object) {
            return this.mIccFh;
        }
    }

    public boolean getIccLockEnabled() {
        return this.mIccLockEnabled;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean getIccPin2Blocked() {
        Object object = this.mLock;
        synchronized (object) {
            if (this.mPin2State != IccCardStatus.PinState.PINSTATE_ENABLED_BLOCKED) return false;
            return true;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean getIccPuk2Blocked() {
        Object object = this.mLock;
        synchronized (object) {
            if (this.mPin2State != IccCardStatus.PinState.PINSTATE_ENABLED_PERM_BLOCKED) return false;
            return true;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public IccRecords getIccRecords() {
        Object object = this.mLock;
        synchronized (object) {
            return this.mIccRecords;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public IccCardApplicationStatus.PersoSubState getPersoSubState() {
        Object object = this.mLock;
        synchronized (object) {
            return this.mPersoSubState;
        }
    }

    @UnsupportedAppUsage
    public int getPhoneId() {
        return this.mUiccProfile.getPhoneId();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public IccCardStatus.PinState getPin1State() {
        Object object = this.mLock;
        synchronized (object) {
            if (!this.mPin1Replaced) return this.mPin1State;
            return this.mUiccProfile.getUniversalPinState();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public IccCardApplicationStatus.AppState getState() {
        Object object = this.mLock;
        synchronized (object) {
            return this.mAppState;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public IccCardApplicationStatus.AppType getType() {
        Object object = this.mLock;
        synchronized (object) {
            return this.mAppType;
        }
    }

    protected UiccProfile getUiccProfile() {
        return this.mUiccProfile;
    }

    public boolean isAppIgnored() {
        return this.mIgnoreApp;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean isReady() {
        Object object = this.mLock;
        synchronized (object) {
            if (this.mAppState != IccCardApplicationStatus.AppState.APPSTATE_READY) {
                return false;
            }
            if (this.mPin1State != IccCardStatus.PinState.PINSTATE_ENABLED_NOT_VERIFIED && this.mPin1State != IccCardStatus.PinState.PINSTATE_ENABLED_BLOCKED && this.mPin1State != IccCardStatus.PinState.PINSTATE_ENABLED_PERM_BLOCKED) {
                return true;
            }
            this.loge("Sanity check failed! APPSTATE is ready while PIN1 is not verified!!!");
            return false;
        }
    }

    public void queryFdn() {
        this.mCi.queryFacilityLockForApp("FD", "", 7, this.mAid, this.mHandler.obtainMessage(4));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void registerForLocked(Handler handler, int n, Object object) {
        Object object2 = this.mLock;
        synchronized (object2) {
            Registrant registrant = new Registrant(handler, n, object);
            this.mPinLockedRegistrants.add(registrant);
            this.notifyPinLockedRegistrantsIfNeeded(registrant);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void registerForNetworkLocked(Handler handler, int n, Object object) {
        Object object2 = this.mLock;
        synchronized (object2) {
            Registrant registrant = new Registrant(handler, n, object);
            this.mNetworkLockedRegistrants.add(registrant);
            this.notifyNetworkLockedRegistrantsIfNeeded(registrant);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public void registerForReady(Handler handler, int n, Object object) {
        Object object2 = this.mLock;
        synchronized (object2) {
            Registrant registrant = new Registrant(handler, n, object);
            this.mReadyRegistrants.add(registrant);
            this.notifyReadyRegistrantsIfNeeded(registrant);
            return;
        }
    }

    public void setAppIgnoreState(boolean bl) {
        this.mIgnoreApp = bl;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setIccFdnEnabled(boolean bl, String string, Message message) {
        Object object = this.mLock;
        synchronized (object) {
            this.mDesiredFdnEnabled = bl;
            this.mCi.setFacilityLockForApp("FD", bl, string, 15, this.mAid, this.mHandler.obtainMessage(5, (Object)message));
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setIccLockEnabled(boolean bl, String string, Message message) {
        Object object = this.mLock;
        synchronized (object) {
            this.mDesiredPinLocked = bl;
            this.mCi.setFacilityLockForApp("SC", bl, string, 7, this.mAid, this.mHandler.obtainMessage(7, (Object)message));
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void supplyNetworkDepersonalization(String string, Message message) {
        Object object = this.mLock;
        synchronized (object) {
            this.log("supplyNetworkDepersonalization");
            this.mCi.supplyNetworkDepersonalization(string, message);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void supplyPin(String string, Message message) {
        Object object = this.mLock;
        synchronized (object) {
            this.mCi.supplyIccPinForApp(string, this.mAid, this.mHandler.obtainMessage(1, (Object)message));
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void supplyPin2(String string, Message message) {
        Object object = this.mLock;
        synchronized (object) {
            this.mCi.supplyIccPin2ForApp(string, this.mAid, this.mHandler.obtainMessage(8, (Object)message));
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void supplyPuk(String string, String string2, Message message) {
        Object object = this.mLock;
        synchronized (object) {
            this.mCi.supplyIccPukForApp(string, string2, this.mAid, this.mHandler.obtainMessage(1, (Object)message));
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void supplyPuk2(String string, String string2, Message message) {
        Object object = this.mLock;
        synchronized (object) {
            this.mCi.supplyIccPuk2ForApp(string, string2, this.mAid, this.mHandler.obtainMessage(8, (Object)message));
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void unregisterForLocked(Handler handler) {
        Object object = this.mLock;
        synchronized (object) {
            this.mPinLockedRegistrants.remove(handler);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void unregisterForNetworkLocked(Handler handler) {
        Object object = this.mLock;
        synchronized (object) {
            this.mNetworkLockedRegistrants.remove(handler);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public void unregisterForReady(Handler handler) {
        Object object = this.mLock;
        synchronized (object) {
            this.mReadyRegistrants.remove(handler);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public void update(IccCardApplicationStatus object, Context context, CommandsInterface commandsInterface) {
        Object object2 = this.mLock;
        synchronized (object2) {
            if (this.mDestroyed) {
                this.loge("Application updated after destroyed! Fix me!");
                return;
            }
            Object object3 = new StringBuilder();
            object3.append((Object)this.mAppType);
            object3.append(" update. New ");
            object3.append(object);
            this.log(object3.toString());
            this.mContext = context;
            this.mCi = commandsInterface;
            IccCardApplicationStatus.AppType appType = this.mAppType;
            object3 = this.mAppState;
            IccCardApplicationStatus.PersoSubState persoSubState = this.mPersoSubState;
            this.mAppType = ((IccCardApplicationStatus)object).app_type;
            this.mAuthContext = UiccCardApplication.getAuthContext(this.mAppType);
            this.mAppState = ((IccCardApplicationStatus)object).app_state;
            this.mPersoSubState = ((IccCardApplicationStatus)object).perso_substate;
            this.mAid = ((IccCardApplicationStatus)object).aid;
            this.mAppLabel = ((IccCardApplicationStatus)object).app_label;
            boolean bl = ((IccCardApplicationStatus)object).pin1_replaced != 0;
            this.mPin1Replaced = bl;
            this.mPin1State = ((IccCardApplicationStatus)object).pin1;
            this.mPin2State = ((IccCardApplicationStatus)object).pin2;
            if (this.mAppType != appType) {
                if (this.mIccFh != null) {
                    this.mIccFh.dispose();
                }
                if (this.mIccRecords != null) {
                    this.mIccRecords.dispose();
                }
                this.mIccFh = this.createIccFileHandler(((IccCardApplicationStatus)object).app_type);
                this.mIccRecords = this.createIccRecords(((IccCardApplicationStatus)object).app_type, context, commandsInterface);
            }
            if (this.mPersoSubState != persoSubState && this.mPersoSubState == IccCardApplicationStatus.PersoSubState.PERSOSUBSTATE_SIM_NETWORK) {
                this.notifyNetworkLockedRegistrantsIfNeeded(null);
            }
            if (this.mAppState != object3) {
                object = new StringBuilder();
                ((StringBuilder)object).append((Object)appType);
                ((StringBuilder)object).append(" changed state: ");
                ((StringBuilder)object).append(object3);
                ((StringBuilder)object).append(" -> ");
                ((StringBuilder)object).append((Object)this.mAppState);
                this.log(((StringBuilder)object).toString());
                if (this.mAppState == IccCardApplicationStatus.AppState.APPSTATE_READY) {
                    this.queryFdn();
                    this.queryPin1State();
                }
                this.notifyPinLockedRegistrantsIfNeeded(null);
                this.notifyReadyRegistrantsIfNeeded(null);
            }
            return;
        }
    }

}

