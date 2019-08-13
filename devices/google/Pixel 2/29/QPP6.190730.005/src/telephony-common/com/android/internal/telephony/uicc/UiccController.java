/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 *  android.app.BroadcastOptions
 *  android.content.Context
 *  android.content.Intent
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.content.pm.PackageManager
 *  android.content.res.Resources
 *  android.os.AsyncResult
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Message
 *  android.os.Registrant
 *  android.os.RegistrantList
 *  android.os.storage.StorageManager
 *  android.preference.PreferenceManager
 *  android.telephony.CarrierConfigManager
 *  android.telephony.Rlog
 *  android.telephony.TelephonyManager
 *  android.telephony.UiccCardInfo
 *  android.text.TextUtils
 *  android.util.LocalLog
 *  com.android.internal.annotations.VisibleForTesting
 *  com.android.internal.telephony.IccCardConstants
 *  com.android.internal.telephony.IccCardConstants$State
 *  com.android.internal.telephony.uicc.IccUtils
 */
package com.android.internal.telephony.uicc;

import android.annotation.UnsupportedAppUsage;
import android.app.BroadcastOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.AsyncResult;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Registrant;
import android.os.RegistrantList;
import android.os.storage.StorageManager;
import android.preference.PreferenceManager;
import android.telephony.CarrierConfigManager;
import android.telephony.Rlog;
import android.telephony.TelephonyManager;
import android.telephony.UiccCardInfo;
import android.text.TextUtils;
import android.util.LocalLog;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.telephony.CommandException;
import com.android.internal.telephony.CommandsInterface;
import com.android.internal.telephony.IccCardConstants;
import com.android.internal.telephony.PhoneFactory;
import com.android.internal.telephony.RadioConfig;
import com.android.internal.telephony.SubscriptionInfoUpdater;
import com.android.internal.telephony.uicc.IccCardStatus;
import com.android.internal.telephony.uicc.IccFileHandler;
import com.android.internal.telephony.uicc.IccRecords;
import com.android.internal.telephony.uicc.IccRefreshResponse;
import com.android.internal.telephony.uicc.IccSlotStatus;
import com.android.internal.telephony.uicc.IccUtils;
import com.android.internal.telephony.uicc.UiccCard;
import com.android.internal.telephony.uicc.UiccCardApplication;
import com.android.internal.telephony.uicc.UiccProfile;
import com.android.internal.telephony.uicc.UiccSlot;
import com.android.internal.telephony.uicc.UiccStateChangedLauncher;
import com.android.internal.telephony.uicc.euicc.EuiccCard;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

public class UiccController
extends Handler {
    public static final int APP_FAM_3GPP = 1;
    public static final int APP_FAM_3GPP2 = 2;
    public static final int APP_FAM_IMS = 3;
    private static final String CARD_STRINGS = "card_strings";
    private static final boolean DBG = true;
    private static final String DEFAULT_CARD = "default_card";
    private static final int EID_LENGTH = 32;
    private static final int EVENT_EID_READY = 9;
    private static final int EVENT_GET_ICC_STATUS_DONE = 3;
    private static final int EVENT_GET_SLOT_STATUS_DONE = 4;
    private static final int EVENT_ICC_STATUS_CHANGED = 1;
    private static final int EVENT_RADIO_AVAILABLE = 6;
    private static final int EVENT_RADIO_ON = 5;
    private static final int EVENT_RADIO_UNAVAILABLE = 7;
    private static final int EVENT_SIM_REFRESH = 8;
    private static final int EVENT_SLOT_STATUS_CHANGED = 2;
    public static final int INVALID_SLOT_ID = -1;
    private static final String LOG_TAG = "UiccController";
    private static final int TEMPORARILY_UNSUPPORTED_CARD_ID = -3;
    private static final boolean VDBG = false;
    @UnsupportedAppUsage
    private static UiccController mInstance;
    @UnsupportedAppUsage
    private static final Object mLock;
    private static ArrayList<IccSlotStatus> sLastSlotStatus;
    static LocalLog sLocalLog;
    private ArrayList<String> mCardStrings;
    @UnsupportedAppUsage
    private CommandsInterface[] mCis;
    @UnsupportedAppUsage
    @VisibleForTesting
    public Context mContext;
    private int mDefaultEuiccCardId;
    protected RegistrantList mIccChangedRegistrants = new RegistrantList();
    private boolean mIsSlotStatusSupported = true;
    private UiccStateChangedLauncher mLauncher;
    private int[] mPhoneIdToSlotId;
    private RadioConfig mRadioConfig;
    @VisibleForTesting
    public UiccSlot[] mUiccSlots;

    static {
        mLock = new Object();
        sLocalLog = new LocalLog(100);
    }

    private UiccController(Context context, CommandsInterface[] arrcommandsInterface) {
        this.log("Creating UiccController");
        this.mContext = context;
        this.mCis = arrcommandsInterface;
        CommandsInterface[] arrcommandsInterface2 = new StringBuilder();
        arrcommandsInterface2.append("config_num_physical_slots = ");
        arrcommandsInterface2.append(context.getResources().getInteger(17694871));
        arrcommandsInterface2 = arrcommandsInterface2.toString();
        this.log((String)arrcommandsInterface2);
        sLocalLog.log((String)arrcommandsInterface2);
        int n = context.getResources().getInteger(17694871);
        arrcommandsInterface2 = this.mCis;
        int n2 = n;
        if (n < arrcommandsInterface2.length) {
            n2 = arrcommandsInterface2.length;
        }
        this.mUiccSlots = new UiccSlot[n2];
        this.mPhoneIdToSlotId = new int[arrcommandsInterface.length];
        Arrays.fill(this.mPhoneIdToSlotId, -1);
        this.mRadioConfig = RadioConfig.getInstance(this.mContext);
        this.mRadioConfig.registerForSimSlotStatusChanged(this, 2, null);
        for (n2 = 0; n2 < (arrcommandsInterface = this.mCis).length; ++n2) {
            arrcommandsInterface[n2].registerForIccStatusChanged(this, 1, n2);
            if (!StorageManager.inCryptKeeperBounce()) {
                this.mCis[n2].registerForAvailable(this, 6, n2);
            } else {
                this.mCis[n2].registerForOn(this, 5, n2);
            }
            this.mCis[n2].registerForNotAvailable(this, 7, n2);
            this.mCis[n2].registerForIccRefresh(this, 8, n2);
        }
        this.mLauncher = new UiccStateChangedLauncher(context, this);
        this.mCardStrings = this.loadCardStrings();
        this.mDefaultEuiccCardId = -2;
    }

    private void addCardId(String string) {
        if (TextUtils.isEmpty((CharSequence)string)) {
            return;
        }
        String string2 = string;
        if (string.length() < 32) {
            string2 = IccUtils.stripTrailingFs((String)string);
        }
        if (!this.mCardStrings.contains(string2)) {
            this.mCardStrings.add(string2);
            this.saveCardStrings();
        }
    }

    private boolean eidIsNotSupported(IccCardStatus iccCardStatus) {
        boolean bl = iccCardStatus.physicalSlotIndex == -1;
        return bl;
    }

    private Integer getCiIndex(Message message) {
        Integer n;
        Integer n2 = n = new Integer(0);
        if (message != null) {
            if (message.obj != null && message.obj instanceof Integer) {
                n2 = (Integer)message.obj;
            } else {
                n2 = n;
                if (message.obj != null) {
                    n2 = n;
                    if (message.obj instanceof AsyncResult) {
                        message = (AsyncResult)message.obj;
                        n2 = n;
                        if (message.userObj != null) {
                            n2 = n;
                            if (message.userObj instanceof Integer) {
                                n2 = (Integer)message.userObj;
                            }
                        }
                    }
                }
            }
        }
        return n2;
    }

    static String getIccStateIntentString(IccCardConstants.State state) {
        switch (state) {
            default: {
                return "UNKNOWN";
            }
            case LOADED: {
                return "LOADED";
            }
            case CARD_RESTRICTED: {
                return "CARD_RESTRICTED";
            }
            case CARD_IO_ERROR: {
                return "CARD_IO_ERROR";
            }
            case PERM_DISABLED: {
                return "LOCKED";
            }
            case NOT_READY: {
                return "NOT_READY";
            }
            case READY: {
                return "READY";
            }
            case NETWORK_LOCKED: {
                return "LOCKED";
            }
            case PUK_REQUIRED: {
                return "LOCKED";
            }
            case PIN_REQUIRED: {
                return "LOCKED";
            }
            case ABSENT: 
        }
        return "ABSENT";
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public static UiccController getInstance() {
        Object object = mLock;
        synchronized (object) {
            if (mInstance != null) {
                return mInstance;
            }
            RuntimeException runtimeException = new RuntimeException("UiccController.getInstance can't be called before make()");
            throw runtimeException;
        }
    }

    public static boolean isCdmaSupported(Context context) {
        context = context.getPackageManager();
        return context.hasSystemFeature("android.hardware.telephony.cdma");
    }

    private boolean isValidPhoneIndex(int n) {
        boolean bl = n >= 0 && n < TelephonyManager.getDefault().getPhoneCount();
        return bl;
    }

    private boolean isValidSlotIndex(int n) {
        boolean bl = n >= 0 && n < this.mUiccSlots.length;
        return bl;
    }

    private ArrayList<String> loadCardStrings() {
        String string = PreferenceManager.getDefaultSharedPreferences((Context)this.mContext).getString(CARD_STRINGS, "");
        if (TextUtils.isEmpty((CharSequence)string)) {
            return new ArrayList<String>();
        }
        return new ArrayList<String>(Arrays.asList(string.split(",")));
    }

    @UnsupportedAppUsage
    private void log(String string) {
        Rlog.d((String)LOG_TAG, (String)string);
    }

    private void logPhoneIdToSlotIdMapping() {
        this.log("mPhoneIdToSlotId mapping:");
        for (int i = 0; i < this.mPhoneIdToSlotId.length; ++i) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("    phoneId ");
            stringBuilder.append(i);
            stringBuilder.append(" slotId ");
            stringBuilder.append(this.mPhoneIdToSlotId[i]);
            this.log(stringBuilder.toString());
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static UiccController make(Context object, CommandsInterface[] arrcommandsInterface) {
        Object object2 = mLock;
        synchronized (object2) {
            if (mInstance == null) {
                UiccController uiccController;
                mInstance = uiccController = new UiccController((Context)object, arrcommandsInterface);
                return mInstance;
            }
            object = new RuntimeException("UiccController.make() should only be called once");
            throw object;
        }
    }

    private void onEidReady(AsyncResult object, Integer serializable) {
        if (((AsyncResult)object).exception != null) {
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("onEidReady: exception: ");
            ((StringBuilder)serializable).append(((AsyncResult)object).exception);
            Rlog.e((String)LOG_TAG, (String)((StringBuilder)serializable).toString());
            return;
        }
        if (!this.isValidPhoneIndex((Integer)serializable)) {
            object = new StringBuilder();
            ((StringBuilder)object).append("onEidReady: invalid index: ");
            ((StringBuilder)object).append(serializable);
            Rlog.e((String)LOG_TAG, (String)((StringBuilder)object).toString());
            return;
        }
        int n = this.mPhoneIdToSlotId[(Integer)serializable];
        UiccCard uiccCard = this.mUiccSlots[n].getUiccCard();
        if (uiccCard == null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("onEidReady: UiccCard in slot ");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append(" is null");
            Rlog.e((String)LOG_TAG, (String)((StringBuilder)object).toString());
            return;
        }
        object = ((EuiccCard)uiccCard).getEid();
        this.addCardId((String)object);
        int n2 = this.mDefaultEuiccCardId;
        if (n2 == -2 || n2 == -3) {
            this.mDefaultEuiccCardId = this.convertToPublicCardId((String)object);
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("onEidReady: eid=");
            ((StringBuilder)serializable).append((String)object);
            ((StringBuilder)serializable).append(" slot=");
            ((StringBuilder)serializable).append(n);
            ((StringBuilder)serializable).append(" mDefaultEuiccCardId=");
            ((StringBuilder)serializable).append(this.mDefaultEuiccCardId);
            this.log(((StringBuilder)serializable).toString());
        }
        ((EuiccCard)uiccCard).unregisterForEidReady(this);
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void onGetIccCardStatusDone(AsyncResult object, Integer n) {
        synchronized (this) {
            int n2;
            void var2_2;
            if (((AsyncResult)object).exception != null) {
                Rlog.e((String)LOG_TAG, (String)"Error getting ICC status. RIL_REQUEST_GET_ICC_STATUS should never return an error", (Throwable)((AsyncResult)object).exception);
                return;
            }
            if (!this.isValidPhoneIndex(var2_2.intValue())) {
                object = new StringBuilder();
                ((StringBuilder)object).append("onGetIccCardStatusDone: invalid index : ");
                ((StringBuilder)object).append(var2_2);
                Rlog.e((String)LOG_TAG, (String)((StringBuilder)object).toString());
                return;
            }
            object = (IccCardStatus)((AsyncResult)object).result;
            Object object2 = sLocalLog;
            UiccSlot[] arruiccSlot = new StringBuilder();
            arruiccSlot.append("onGetIccCardStatusDone: phoneId ");
            arruiccSlot.append(var2_2);
            arruiccSlot.append(" IccCardStatus: ");
            arruiccSlot.append(object);
            object2.log(arruiccSlot.toString());
            int n3 = n2 = ((IccCardStatus)object).physicalSlotIndex;
            if (n2 == -1) {
                n3 = var2_2.intValue();
            }
            if (this.eidIsNotSupported((IccCardStatus)object)) {
                this.log("eid is not supported");
                this.mDefaultEuiccCardId = -1;
            }
            this.mPhoneIdToSlotId[var2_2.intValue()] = n3;
            if (this.mUiccSlots[n3] == null) {
                arruiccSlot = this.mUiccSlots;
                object2 = new UiccSlot(this.mContext, true);
                arruiccSlot[n3] = object2;
            }
            this.mUiccSlots[n3].update(this.mCis[var2_2.intValue()], (IccCardStatus)object, var2_2.intValue(), n3);
            object2 = this.mUiccSlots[n3].getUiccCard();
            if (object2 == null) {
                object = new StringBuilder();
                ((StringBuilder)object).append("mUiccSlots[");
                ((StringBuilder)object).append(n3);
                ((StringBuilder)object).append("] has no card. Notifying IccChangedRegistrants");
                this.log(((StringBuilder)object).toString());
                object2 = this.mIccChangedRegistrants;
                object = new AsyncResult(null, (Object)var2_2, null);
                object2.notifyRegistrants((AsyncResult)object);
                return;
            }
            boolean bl = this.mUiccSlots[n3].isEuicc();
            object = bl ? ((EuiccCard)object2).getEid() : ((UiccCard)object2).getIccId();
            if (bl && object == null && this.mDefaultEuiccCardId != -1) {
                ((EuiccCard)object2).registerForEidReady(this, 9, var2_2);
            }
            if (object != null) {
                this.addCardId((String)object);
            }
            this.log("Notifying IccChangedRegistrants");
            object = this.mIccChangedRegistrants;
            object2 = new AsyncResult(null, (Object)var2_2, null);
            object.notifyRegistrants((AsyncResult)object2);
            return;
        }
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void onGetSlotStatusDone(AsyncResult object) {
        synchronized (this) {
            boolean bl = this.mIsSlotStatusSupported;
            if (!bl) {
                return;
            }
            Serializable serializable = ((AsyncResult)object).exception;
            int n = 0;
            if (serializable != null) {
                if (serializable instanceof CommandException && ((CommandException)serializable).getCommandError() == CommandException.Error.REQUEST_NOT_SUPPORTED) {
                    this.log("onGetSlotStatusDone: request not supported; marking mIsSlotStatusSupported to false");
                    sLocalLog.log("onGetSlotStatusDone: request not supported; marking mIsSlotStatusSupported to false");
                    this.mIsSlotStatusSupported = false;
                } else {
                    serializable = new StringBuilder();
                    ((StringBuilder)serializable).append("Unexpected error getting slot status: ");
                    ((StringBuilder)serializable).append(((AsyncResult)object).exception);
                    object = ((StringBuilder)serializable).toString();
                    Rlog.e((String)LOG_TAG, (String)object);
                    sLocalLog.log((String)object);
                }
                return;
            }
            serializable = (ArrayList)((AsyncResult)object).result;
            if (!this.slotStatusChanged((ArrayList<IccSlotStatus>)serializable)) {
                this.log("onGetSlotStatusDone: No change in slot status");
                return;
            }
            sLastSlotStatus = serializable;
            int n2 = 0;
            int n3 = 0;
            int n4 = 0;
            int n5 = 0;
            int n6 = 0;
            do {
                UiccSlot uiccSlot;
                int n7 = ((ArrayList)serializable).size();
                bl = true;
                if (n6 >= n7) break;
                Object object2 = (IccSlotStatus)((ArrayList)serializable).get(n6);
                if (((IccSlotStatus)object2).slotState != IccSlotStatus.SlotState.SLOTSTATE_ACTIVE) {
                    bl = false;
                }
                n7 = n2;
                if (bl) {
                    n7 = n2 + 1;
                    if (!this.isValidPhoneIndex(((IccSlotStatus)object2).logicalSlotIndex)) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Skipping slot ");
                        ((StringBuilder)object).append(n6);
                        ((StringBuilder)object).append(" as phone ");
                        ((StringBuilder)object).append(((IccSlotStatus)object2).logicalSlotIndex);
                        ((StringBuilder)object).append(" is not available to communicate with this slot");
                        Rlog.e((String)LOG_TAG, (String)((StringBuilder)object).toString());
                    } else {
                        this.mPhoneIdToSlotId[object2.logicalSlotIndex] = n6;
                    }
                }
                if (this.mUiccSlots[n6] == null) {
                    object = this.mUiccSlots;
                    uiccSlot = new UiccSlot(this.mContext, bl);
                    object[n6] = uiccSlot;
                }
                boolean bl2 = this.isValidPhoneIndex(((IccSlotStatus)object2).logicalSlotIndex);
                object = null;
                if (!bl2) {
                    this.mUiccSlots[n6].update(null, (IccSlotStatus)object2, n6);
                } else {
                    uiccSlot = this.mUiccSlots[n6];
                    if (bl) {
                        object = this.mCis[((IccSlotStatus)object2).logicalSlotIndex];
                    }
                    uiccSlot.update((CommandsInterface)object, (IccSlotStatus)object2, n6);
                }
                int n8 = n3;
                int n9 = n4;
                if (this.mUiccSlots[n6].isEuicc()) {
                    n2 = 1;
                    if (bl) {
                        n4 = 1;
                    }
                    if (TextUtils.isEmpty((CharSequence)(object2 = ((IccSlotStatus)object2).eid))) {
                        n8 = n3;
                        n9 = n4;
                        n5 = n2;
                    } else {
                        this.addCardId((String)object2);
                        n8 = n3;
                        n9 = n4;
                        n5 = n2;
                        if (n3 == 0) {
                            n8 = 1;
                            this.mDefaultEuiccCardId = this.convertToPublicCardId((String)object2);
                            object = new StringBuilder();
                            ((StringBuilder)object).append("Using eid=");
                            ((StringBuilder)object).append((String)object2);
                            ((StringBuilder)object).append(" in slot=");
                            ((StringBuilder)object).append(n6);
                            ((StringBuilder)object).append(" to set mDefaultEuiccCardId=");
                            ((StringBuilder)object).append(this.mDefaultEuiccCardId);
                            this.log(((StringBuilder)object).toString());
                            n5 = n2;
                            n9 = n4;
                        }
                    }
                }
                ++n6;
                n2 = n7;
                n3 = n8;
                n4 = n9;
            } while (true);
            if (n5 != 0 && n4 == 0 && n3 == 0) {
                this.log("onGetSlotStatusDone: setting TEMPORARILY_UNSUPPORTED_CARD_ID");
                this.mDefaultEuiccCardId = -3;
            }
            if (n2 != this.mPhoneIdToSlotId.length) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Number of active slots ");
                ((StringBuilder)object).append(n2);
                ((StringBuilder)object).append(" does not match the number of Phones");
                ((StringBuilder)object).append(this.mPhoneIdToSlotId.length);
                Rlog.e((String)LOG_TAG, (String)((StringBuilder)object).toString());
            }
            serializable = new HashSet();
            object = this.mPhoneIdToSlotId;
            n6 = ((Object)object).length;
            n4 = n;
            do {
                if (n4 >= n6) {
                    object = BroadcastOptions.makeBasic();
                    object.setBackgroundActivityStartsAllowed(true);
                    serializable = new Intent("android.telephony.action.SIM_SLOT_STATUS_CHANGED");
                    serializable.addFlags(67108864);
                    serializable.addFlags(16777216);
                    this.mContext.sendBroadcast((Intent)serializable, "android.permission.READ_PRIVILEGED_PHONE_STATE", object.toBundle());
                    return;
                }
                n3 = object[n4];
                if (serializable.contains(n3)) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("slotId ");
                    ((StringBuilder)object).append(n3);
                    ((StringBuilder)object).append(" mapped to multiple phoneIds");
                    serializable = new RuntimeException(((StringBuilder)object).toString());
                    throw serializable;
                }
                serializable.add(n3);
                ++n4;
            } while (true);
        }
    }

    private void onSimRefresh(AsyncResult object, Integer serializable) {
        boolean bl;
        if (((AsyncResult)object).exception != null) {
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("onSimRefresh: Sim REFRESH with exception: ");
            ((StringBuilder)serializable).append(((AsyncResult)object).exception);
            Rlog.e((String)LOG_TAG, (String)((StringBuilder)serializable).toString());
            return;
        }
        if (!this.isValidPhoneIndex((Integer)serializable)) {
            object = new StringBuilder();
            ((StringBuilder)object).append("onSimRefresh: invalid index : ");
            ((StringBuilder)object).append(serializable);
            Rlog.e((String)LOG_TAG, (String)((StringBuilder)object).toString());
            return;
        }
        object = (IccRefreshResponse)((AsyncResult)object).result;
        Object object2 = new StringBuilder();
        ((StringBuilder)object2).append("onSimRefresh: ");
        ((StringBuilder)object2).append(object);
        this.log(((StringBuilder)object2).toString());
        object2 = sLocalLog;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("onSimRefresh: ");
        stringBuilder.append(object);
        object2.log(stringBuilder.toString());
        if (object == null) {
            Rlog.e((String)LOG_TAG, (String)"onSimRefresh: received without input");
            return;
        }
        object2 = this.getUiccCardForPhone((Integer)serializable);
        if (object2 == null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("onSimRefresh: refresh on null card : ");
            ((StringBuilder)object).append(serializable);
            Rlog.e((String)LOG_TAG, (String)((StringBuilder)object).toString());
            return;
        }
        int n = ((IccRefreshResponse)object).refreshResult;
        if (n != 1) {
            if (n != 2) {
                return;
            }
            bl = ((UiccCard)object2).resetAppWithAid(((IccRefreshResponse)object).aid, true);
        } else {
            bl = ((UiccCard)object2).resetAppWithAid(((IccRefreshResponse)object).aid, false);
        }
        if (bl && ((IccRefreshResponse)object).refreshResult == 2) {
            ((CarrierConfigManager)this.mContext.getSystemService("carrier_config")).updateConfigForPhoneId(((Integer)serializable).intValue(), "UNKNOWN");
            if (this.mContext.getResources().getBoolean(17891499)) {
                this.mCis[(Integer)serializable].setRadioPower(false, null);
            }
        }
        this.mCis[(Integer)serializable].getIccCardStatus(this.obtainMessage(3, (Object)serializable));
    }

    private void saveCardStrings() {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences((Context)this.mContext).edit();
        editor.putString(CARD_STRINGS, TextUtils.join((CharSequence)",", this.mCardStrings));
        editor.commit();
    }

    private boolean slotStatusChanged(ArrayList<IccSlotStatus> object) {
        Object object2 = sLastSlotStatus;
        if (object2 != null && ((ArrayList)object2).size() == ((ArrayList)object).size()) {
            object = ((ArrayList)object).iterator();
            while (object.hasNext()) {
                object2 = (IccSlotStatus)object.next();
                if (sLastSlotStatus.contains(object2)) continue;
                return true;
            }
            return false;
        }
        return true;
    }

    static void updateInternalIccState(Context context, IccCardConstants.State state, String string, int n) {
        UiccController.updateInternalIccState(context, state, string, n, false);
    }

    static void updateInternalIccState(Context object, IccCardConstants.State state, String string, int n, boolean bl) {
        ((TelephonyManager)object.getSystemService("phone")).setSimStateForPhone(n, state.toString());
        object = PhoneFactory.getSubscriptionInfoUpdater();
        if (object != null) {
            ((SubscriptionInfoUpdater)((Object)object)).updateInternalIccState(UiccController.getIccStateIntentString(state), string, n, bl);
        } else {
            Rlog.e((String)LOG_TAG, (String)"subInfoUpdate is null.");
        }
    }

    public void addCardLog(String string) {
        sLocalLog.log(string);
    }

    public int convertToPublicCardId(String string) {
        int n;
        if (this.mDefaultEuiccCardId == -1) {
            return -1;
        }
        if (TextUtils.isEmpty((CharSequence)string)) {
            return -2;
        }
        String string2 = string;
        if (string.length() < 32) {
            string2 = IccUtils.stripTrailingFs((String)string);
        }
        if ((n = this.mCardStrings.indexOf(string2)) == -1) {
            return -2;
        }
        return n;
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
        int n;
        Object object = new StringBuilder();
        ((StringBuilder)object).append("UiccController: ");
        ((StringBuilder)object).append((Object)this);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mContext=");
        ((StringBuilder)object).append((Object)this.mContext);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mInstance=");
        ((StringBuilder)object).append((Object)mInstance);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mIccChangedRegistrants: size=");
        ((StringBuilder)object).append(this.mIccChangedRegistrants.size());
        printWriter.println(((StringBuilder)object).toString());
        for (n = 0; n < this.mIccChangedRegistrants.size(); ++n) {
            object = new StringBuilder();
            ((StringBuilder)object).append("  mIccChangedRegistrants[");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append("]=");
            ((StringBuilder)object).append((Object)((Registrant)this.mIccChangedRegistrants.get(n)).getHandler());
            printWriter.println(((StringBuilder)object).toString());
        }
        printWriter.println();
        printWriter.flush();
        object = new StringBuilder();
        ((StringBuilder)object).append(" mIsCdmaSupported=");
        ((StringBuilder)object).append(UiccController.isCdmaSupported(this.mContext));
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mUiccSlots: size=");
        ((StringBuilder)object).append(this.mUiccSlots.length);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mCardStrings=");
        ((StringBuilder)object).append(this.mCardStrings);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mDefaultEuiccCardId=");
        ((StringBuilder)object).append(this.mDefaultEuiccCardId);
        printWriter.println(((StringBuilder)object).toString());
        for (n = 0; n < ((UiccSlot[])(object = this.mUiccSlots)).length; ++n) {
            if (object[n] == null) {
                object = new StringBuilder();
                ((StringBuilder)object).append("  mUiccSlots[");
                ((StringBuilder)object).append(n);
                ((StringBuilder)object).append("]=null");
                printWriter.println(((StringBuilder)object).toString());
                continue;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("  mUiccSlots[");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append("]=");
            ((StringBuilder)object).append((Object)this.mUiccSlots[n]);
            printWriter.println(((StringBuilder)object).toString());
            this.mUiccSlots[n].dump(fileDescriptor, printWriter, arrstring);
        }
        printWriter.println(" sLocalLog= ");
        sLocalLog.dump(fileDescriptor, printWriter, arrstring);
    }

    public ArrayList<UiccCardInfo> getAllUiccCardInfos() {
        Object object;
        ArrayList<UiccCardInfo> arrayList = new ArrayList<UiccCardInfo>();
        for (int i = 0; i < ((UiccSlot[])(object = this.mUiccSlots)).length; ++i) {
            int n;
            String string;
            if ((object = object[i]) == null) continue;
            boolean bl = ((UiccSlot)((Object)object)).isEuicc();
            Object object2 = ((UiccSlot)((Object)object)).getUiccCard();
            boolean bl2 = ((UiccSlot)((Object)object)).isRemovable();
            if (object2 != null) {
                object = ((UiccCard)object2).getIccId();
                if (bl) {
                    string = ((EuiccCard)object2).getEid();
                    n = this.convertToPublicCardId(string);
                    object2 = object;
                    object = string;
                } else {
                    n = this.convertToPublicCardId((String)object);
                    string = null;
                    object2 = object;
                    object = string;
                }
            } else {
                object = ((UiccSlot)((Object)object)).getIccId();
                if (!bl && !TextUtils.isEmpty((CharSequence)object)) {
                    n = this.convertToPublicCardId((String)object);
                    string = null;
                    object2 = object;
                    object = string;
                } else {
                    string = null;
                    n = -2;
                    object2 = object;
                    object = string;
                }
            }
            arrayList.add(new UiccCardInfo(bl, n, (String)object, IccUtils.stripTrailingFs((String)object2), i, bl2));
        }
        return arrayList;
    }

    public int getCardIdForDefaultEuicc() {
        int n = this.mDefaultEuiccCardId;
        if (n == -3) {
            return -1;
        }
        return n;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public IccFileHandler getIccFileHandler(int n, int n2) {
        Object object = mLock;
        synchronized (object) {
            UiccCardApplication uiccCardApplication = this.getUiccCardApplication(n, n2);
            if (uiccCardApplication == null) return null;
            return uiccCardApplication.getIccFileHandler();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public IccRecords getIccRecords(int n, int n2) {
        Object object = mLock;
        synchronized (object) {
            UiccCardApplication uiccCardApplication = this.getUiccCardApplication(n, n2);
            if (uiccCardApplication == null) return null;
            return uiccCardApplication.getIccRecords();
        }
    }

    public int getPhoneIdFromSlotId(int n) {
        int[] arrn;
        for (int i = 0; i < (arrn = this.mPhoneIdToSlotId).length; ++i) {
            if (arrn[i] != n) continue;
            return i;
        }
        return -1;
    }

    public int getSlotIdFromPhoneId(int n) {
        try {
            n = this.mPhoneIdToSlotId[n];
            return n;
        }
        catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            return -1;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public UiccCard getUiccCard(int n) {
        Object object = mLock;
        synchronized (object) {
            return this.getUiccCardForPhone(n);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public UiccCardApplication getUiccCardApplication(int n, int n2) {
        Object object = mLock;
        synchronized (object) {
            UiccCard uiccCard = this.getUiccCardForPhone(n);
            if (uiccCard == null) return null;
            return uiccCard.getApplication(n2);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public UiccCard getUiccCardForPhone(int n) {
        Object object = mLock;
        synchronized (object) {
            if (!this.isValidPhoneIndex(n)) return null;
            Object object2 = this.getUiccSlotForPhone(n);
            if (object2 == null) return null;
            return object2.getUiccCard();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public UiccCard getUiccCardForSlot(int n) {
        Object object = mLock;
        synchronized (object) {
            Object object2 = this.getUiccSlot(n);
            if (object2 == null) return null;
            return object2.getUiccCard();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public UiccProfile getUiccProfileForPhone(int n) {
        Object object = mLock;
        synchronized (object) {
            boolean bl = this.isValidPhoneIndex(n);
            UiccProfile uiccProfile = null;
            if (!bl) return null;
            UiccCard uiccCard = this.getUiccCardForPhone(n);
            if (uiccCard == null) return uiccProfile;
            return uiccCard.getUiccProfile();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public UiccSlot getUiccSlot(int n) {
        Object object = mLock;
        synchronized (object) {
            if (!this.isValidSlotIndex(n)) return null;
            return this.mUiccSlots[n];
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int getUiccSlotForCardId(String string) {
        Object object = mLock;
        synchronized (object) {
            int n;
            int n2 = 0;
            for (n = 0; n < this.mUiccSlots.length; ++n) {
                UiccCard uiccCard;
                if (this.mUiccSlots[n] == null || (uiccCard = this.mUiccSlots[n].getUiccCard()) == null || !string.equals(uiccCard.getCardId())) continue;
                return n;
            }
            n = n2;
            while (n < this.mUiccSlots.length) {
                if (this.mUiccSlots[n] != null && string.equals(this.mUiccSlots[n].getIccId())) {
                    return n;
                }
                ++n;
            }
            return -1;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public UiccSlot getUiccSlotForPhone(int n) {
        Object object = mLock;
        synchronized (object) {
            if (!this.isValidPhoneIndex(n)) return null;
            if (!this.isValidSlotIndex(n = this.getSlotIdFromPhoneId(n))) return null;
            return this.mUiccSlots[n];
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public UiccSlot[] getUiccSlots() {
        Object object = mLock;
        synchronized (object) {
            return this.mUiccSlots;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void handleMessage(Message object) {
        Object object2 = mLock;
        // MONITORENTER : object2
        Serializable serializable = this.getCiIndex((Message)object);
        if ((Integer)serializable >= 0 && (Integer)serializable < this.mCis.length) {
            LocalLog localLog = sLocalLog;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("handleMessage: Received ");
            stringBuilder.append(((Message)object).what);
            stringBuilder.append(" for phoneId ");
            stringBuilder.append(serializable);
            localLog.log(stringBuilder.toString());
            stringBuilder = (AsyncResult)((Message)object).obj;
            switch (((Message)object).what) {
                default: {
                    break;
                }
                case 9: {
                    this.log("Received EVENT_EID_READY");
                    this.onEidReady((AsyncResult)stringBuilder, (Integer)serializable);
                    return;
                }
                case 8: {
                    this.log("Received EVENT_SIM_REFRESH");
                    this.onSimRefresh((AsyncResult)stringBuilder, (Integer)serializable);
                    return;
                }
                case 7: {
                    this.log("EVENT_RADIO_UNAVAILABLE, dispose card");
                    object = this.getUiccSlotForPhone((Integer)serializable);
                    if (object != null) {
                        ((UiccSlot)((Object)object)).onRadioStateUnavailable();
                    }
                    object = this.mIccChangedRegistrants;
                    stringBuilder = new AsyncResult(null, (Object)serializable, null);
                    object.notifyRegistrants((AsyncResult)stringBuilder);
                    return;
                }
                case 5: 
                case 6: {
                    this.log("Received EVENT_RADIO_AVAILABLE/EVENT_RADIO_ON, calling getIccCardStatus");
                    this.mCis[(Integer)serializable].getIccCardStatus(this.obtainMessage(3, (Object)serializable));
                    if ((Integer)serializable != 0) return;
                    {
                        this.log("Received EVENT_RADIO_AVAILABLE/EVENT_RADIO_ON for phoneId 0, calling getIccSlotsStatus");
                        this.mRadioConfig.getSimSlotsStatus(this.obtainMessage(4, (Object)serializable));
                        return;
                    }
                }
                case 3: {
                    this.log("Received EVENT_GET_ICC_STATUS_DONE");
                    this.onGetIccCardStatusDone((AsyncResult)stringBuilder, (Integer)serializable);
                    return;
                }
                case 2: 
                case 4: {
                    this.log("Received EVENT_SLOT_STATUS_CHANGED or EVENT_GET_SLOT_STATUS_DONE");
                    this.onGetSlotStatusDone((AsyncResult)stringBuilder);
                    return;
                }
                case 1: {
                    this.log("Received EVENT_ICC_STATUS_CHANGED, calling getIccCardStatus");
                    this.mCis[(Integer)serializable].getIccCardStatus(this.obtainMessage(3, (Object)serializable));
                    return;
                }
            }
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append(" Unknown Event ");
            ((StringBuilder)serializable).append(((Message)object).what);
            Rlog.e((String)LOG_TAG, (String)((StringBuilder)serializable).toString());
            // MONITOREXIT : object2
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid phoneId : ");
        stringBuilder.append(serializable);
        stringBuilder.append(" received with event ");
        stringBuilder.append(((Message)object).what);
        Rlog.e((String)LOG_TAG, (String)stringBuilder.toString());
        // MONITOREXIT : object2
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public void registerForIccChanged(Handler handler, int n, Object object) {
        Object object2 = mLock;
        synchronized (object2) {
            Registrant registrant = new Registrant(handler, n, object);
            this.mIccChangedRegistrants.add(registrant);
            registrant.notifyRegistrant();
            return;
        }
    }

    public void switchSlots(int[] arrn, Message message) {
        this.mRadioConfig.setSimSlotsMapping(arrn, message);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void unregisterForIccChanged(Handler handler) {
        Object object = mLock;
        synchronized (object) {
            this.mIccChangedRegistrants.remove(handler);
            return;
        }
    }

}

