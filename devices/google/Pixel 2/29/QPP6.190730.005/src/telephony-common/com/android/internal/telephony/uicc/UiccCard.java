/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.Signature
 *  android.os.Handler
 *  android.os.Message
 *  android.telephony.Rlog
 *  android.text.TextUtils
 */
package com.android.internal.telephony.uicc;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Handler;
import android.os.Message;
import android.telephony.Rlog;
import android.text.TextUtils;
import com.android.internal.telephony.CommandsInterface;
import com.android.internal.telephony.TelephonyComponentFactory;
import com.android.internal.telephony.uicc.IccCardApplicationStatus;
import com.android.internal.telephony.uicc.IccCardStatus;
import com.android.internal.telephony.uicc.UiccCardApplication;
import com.android.internal.telephony.uicc.UiccProfile;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.List;

public class UiccCard {
    protected static final boolean DBG = true;
    public static final String EXTRA_ICC_CARD_ADDED = "com.android.internal.telephony.uicc.ICC_CARD_ADDED";
    protected static final String LOG_TAG = "UiccCard";
    protected String mCardId;
    @UnsupportedAppUsage
    private IccCardStatus.CardState mCardState;
    @UnsupportedAppUsage
    private CommandsInterface mCi;
    @UnsupportedAppUsage
    private Context mContext;
    private String mIccid;
    @UnsupportedAppUsage
    protected final Object mLock;
    @UnsupportedAppUsage
    private final int mPhoneId;
    private UiccProfile mUiccProfile;

    public UiccCard(Context context, CommandsInterface commandsInterface, IccCardStatus iccCardStatus, int n, Object object) {
        this.log("Creating");
        this.mCardState = iccCardStatus.mCardState;
        this.mPhoneId = n;
        this.mLock = object;
        this.update(context, commandsInterface, iccCardStatus);
    }

    @UnsupportedAppUsage
    private void log(String string) {
        Rlog.d((String)LOG_TAG, (String)string);
    }

    @UnsupportedAppUsage
    private void loge(String string) {
        Rlog.e((String)LOG_TAG, (String)string);
    }

    @Deprecated
    public boolean areCarrierPriviligeRulesLoaded() {
        UiccProfile uiccProfile = this.mUiccProfile;
        if (uiccProfile != null) {
            return uiccProfile.areCarrierPriviligeRulesLoaded();
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void dispose() {
        Object object = this.mLock;
        synchronized (object) {
            this.log("Disposing card");
            if (this.mUiccProfile != null) {
                this.mUiccProfile.dispose();
            }
            this.mUiccProfile = null;
            return;
        }
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
        printWriter.println("UiccCard:");
        Object object = new StringBuilder();
        ((StringBuilder)object).append(" mCi=");
        ((StringBuilder)object).append(this.mCi);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mCardState=");
        ((StringBuilder)object).append((Object)this.mCardState);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mCardId=");
        ((StringBuilder)object).append(this.mCardId);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mPhoneId=");
        ((StringBuilder)object).append(this.mPhoneId);
        printWriter.println(((StringBuilder)object).toString());
        printWriter.println();
        object = this.mUiccProfile;
        if (object != null) {
            ((UiccProfile)object).dump(fileDescriptor, printWriter, arrstring);
        }
    }

    protected void finalize() {
        this.log("UiccCard finalized");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Deprecated
    @UnsupportedAppUsage
    public UiccCardApplication getApplication(int n) {
        Object object = this.mLock;
        synchronized (object) {
            if (this.mUiccProfile == null) return null;
            return this.mUiccProfile.getApplication(n);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Deprecated
    @UnsupportedAppUsage
    public UiccCardApplication getApplicationByType(int n) {
        Object object = this.mLock;
        synchronized (object) {
            if (this.mUiccProfile == null) return null;
            return this.mUiccProfile.getApplicationByType(n);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Deprecated
    @UnsupportedAppUsage
    public UiccCardApplication getApplicationIndex(int n) {
        Object object = this.mLock;
        synchronized (object) {
            if (this.mUiccProfile == null) return null;
            return this.mUiccProfile.getApplicationIndex(n);
        }
    }

    public String getCardId() {
        if (!TextUtils.isEmpty((CharSequence)this.mCardId)) {
            return this.mCardId;
        }
        UiccProfile uiccProfile = this.mUiccProfile;
        if (uiccProfile != null) {
            return uiccProfile.getIccId();
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public IccCardStatus.CardState getCardState() {
        Object object = this.mLock;
        synchronized (object) {
            return this.mCardState;
        }
    }

    @Deprecated
    @UnsupportedAppUsage
    public List<String> getCarrierPackageNamesForIntent(PackageManager packageManager, Intent intent) {
        UiccProfile uiccProfile = this.mUiccProfile;
        if (uiccProfile != null) {
            return uiccProfile.getCarrierPackageNamesForIntent(packageManager, intent);
        }
        return null;
    }

    @Deprecated
    public int getCarrierPrivilegeStatus(PackageInfo packageInfo) {
        UiccProfile uiccProfile = this.mUiccProfile;
        if (uiccProfile != null) {
            return uiccProfile.getCarrierPrivilegeStatus(packageInfo);
        }
        return -1;
    }

    @Deprecated
    public int getCarrierPrivilegeStatus(PackageManager packageManager, String string) {
        UiccProfile uiccProfile = this.mUiccProfile;
        if (uiccProfile != null) {
            return uiccProfile.getCarrierPrivilegeStatus(packageManager, string);
        }
        return -1;
    }

    @Deprecated
    public int getCarrierPrivilegeStatus(Signature signature, String string) {
        UiccProfile uiccProfile = this.mUiccProfile;
        if (uiccProfile != null) {
            return uiccProfile.getCarrierPrivilegeStatus(signature, string);
        }
        return -1;
    }

    @Deprecated
    public int getCarrierPrivilegeStatusForCurrentTransaction(PackageManager packageManager) {
        UiccProfile uiccProfile = this.mUiccProfile;
        if (uiccProfile != null) {
            return uiccProfile.getCarrierPrivilegeStatusForCurrentTransaction(packageManager);
        }
        return -1;
    }

    @UnsupportedAppUsage
    public String getIccId() {
        Object object = this.mIccid;
        if (object != null) {
            return object;
        }
        object = this.mUiccProfile;
        if (object != null) {
            return ((UiccProfile)object).getIccId();
        }
        return null;
    }

    @Deprecated
    @UnsupportedAppUsage
    public int getNumApplications() {
        UiccProfile uiccProfile = this.mUiccProfile;
        if (uiccProfile != null) {
            return uiccProfile.getNumApplications();
        }
        return 0;
    }

    @Deprecated
    @UnsupportedAppUsage
    public String getOperatorBrandOverride() {
        UiccProfile uiccProfile = this.mUiccProfile;
        if (uiccProfile != null) {
            return uiccProfile.getOperatorBrandOverride();
        }
        return null;
    }

    public int getPhoneId() {
        return this.mPhoneId;
    }

    public UiccProfile getUiccProfile() {
        return this.mUiccProfile;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Deprecated
    public IccCardStatus.PinState getUniversalPinState() {
        Object object = this.mLock;
        synchronized (object) {
            if (this.mUiccProfile == null) return IccCardStatus.PinState.PINSTATE_UNKNOWN;
            return this.mUiccProfile.getUniversalPinState();
        }
    }

    @Deprecated
    public boolean hasCarrierPrivilegeRules() {
        UiccProfile uiccProfile = this.mUiccProfile;
        if (uiccProfile != null) {
            return uiccProfile.hasCarrierPrivilegeRules();
        }
        return false;
    }

    @Deprecated
    public void iccCloseLogicalChannel(int n, Message message) {
        UiccProfile uiccProfile = this.mUiccProfile;
        if (uiccProfile != null) {
            uiccProfile.iccCloseLogicalChannel(n, message);
        } else {
            this.loge("iccCloseLogicalChannel Failed!");
        }
    }

    @Deprecated
    public void iccExchangeSimIO(int n, int n2, int n3, int n4, int n5, String string, Message message) {
        UiccProfile uiccProfile = this.mUiccProfile;
        if (uiccProfile != null) {
            uiccProfile.iccExchangeSimIO(n, n2, n3, n4, n5, string, message);
        } else {
            this.loge("iccExchangeSimIO Failed!");
        }
    }

    @Deprecated
    public void iccOpenLogicalChannel(String string, int n, Message message) {
        UiccProfile uiccProfile = this.mUiccProfile;
        if (uiccProfile != null) {
            uiccProfile.iccOpenLogicalChannel(string, n, message);
        } else {
            this.loge("iccOpenLogicalChannel Failed!");
        }
    }

    @Deprecated
    public void iccTransmitApduBasicChannel(int n, int n2, int n3, int n4, int n5, String string, Message message) {
        UiccProfile uiccProfile = this.mUiccProfile;
        if (uiccProfile != null) {
            uiccProfile.iccTransmitApduBasicChannel(n, n2, n3, n4, n5, string, message);
        } else {
            this.loge("iccTransmitApduBasicChannel Failed!");
        }
    }

    @Deprecated
    public void iccTransmitApduLogicalChannel(int n, int n2, int n3, int n4, int n5, int n6, String string, Message message) {
        UiccProfile uiccProfile = this.mUiccProfile;
        if (uiccProfile != null) {
            uiccProfile.iccTransmitApduLogicalChannel(n, n2, n3, n4, n5, n6, string, message);
        } else {
            this.loge("iccTransmitApduLogicalChannel Failed!");
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Deprecated
    @UnsupportedAppUsage
    public boolean isApplicationOnIcc(IccCardApplicationStatus.AppType appType) {
        Object object = this.mLock;
        synchronized (object) {
            if (this.mUiccProfile == null) return false;
            return this.mUiccProfile.isApplicationOnIcc(appType);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Deprecated
    public void registerForCarrierPrivilegeRulesLoaded(Handler handler, int n, Object object) {
        Object object2 = this.mLock;
        synchronized (object2) {
            if (this.mUiccProfile != null) {
                this.mUiccProfile.registerForCarrierPrivilegeRulesLoaded(handler, n, object);
            } else {
                this.loge("registerForCarrierPrivilegeRulesLoaded Failed!");
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Deprecated
    public boolean resetAppWithAid(String string, boolean bl) {
        Object object = this.mLock;
        synchronized (object) {
            if (this.mUiccProfile == null) return false;
            return this.mUiccProfile.resetAppWithAid(string, bl);
        }
    }

    @Deprecated
    public void sendEnvelopeWithStatus(String string, Message message) {
        UiccProfile uiccProfile = this.mUiccProfile;
        if (uiccProfile != null) {
            uiccProfile.sendEnvelopeWithStatus(string, message);
        } else {
            this.loge("sendEnvelopeWithStatus Failed!");
        }
    }

    @Deprecated
    public boolean setOperatorBrandOverride(String string) {
        UiccProfile uiccProfile = this.mUiccProfile;
        if (uiccProfile != null) {
            return uiccProfile.setOperatorBrandOverride(string);
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Deprecated
    public void unregisterForCarrierPrivilegeRulesLoaded(Handler handler) {
        Object object = this.mLock;
        synchronized (object) {
            if (this.mUiccProfile != null) {
                this.mUiccProfile.unregisterForCarrierPrivilegeRulesLoaded(handler);
            } else {
                this.loge("unregisterForCarrierPrivilegeRulesLoaded Failed!");
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void update(Context object, CommandsInterface commandsInterface, IccCardStatus iccCardStatus) {
        Object object2 = this.mLock;
        synchronized (object2) {
            this.mCardState = iccCardStatus.mCardState;
            this.mContext = object;
            this.mCi = commandsInterface;
            this.mIccid = iccCardStatus.iccid;
            this.updateCardId();
            if (this.mCardState == IccCardStatus.CardState.CARDSTATE_ABSENT) {
                object = new RuntimeException("Card state is absent when updating!");
                throw object;
            }
            if (this.mUiccProfile == null) {
                this.mUiccProfile = TelephonyComponentFactory.getInstance().inject(UiccProfile.class.getName()).makeUiccProfile(this.mContext, this.mCi, iccCardStatus, this.mPhoneId, this, this.mLock);
            } else {
                this.mUiccProfile.update(this.mContext, this.mCi, iccCardStatus);
            }
            return;
        }
    }

    protected void updateCardId() {
        this.mCardId = this.mIccid;
    }
}

