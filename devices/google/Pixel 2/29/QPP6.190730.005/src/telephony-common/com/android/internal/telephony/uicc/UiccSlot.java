/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.app.AlertDialog
 *  android.app.AlertDialog$Builder
 *  android.content.ActivityNotFoundException
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 *  android.content.Intent
 *  android.content.res.Resources
 *  android.os.Handler
 *  android.os.Message
 *  android.os.PowerManager
 *  android.telephony.Rlog
 *  android.text.TextUtils
 *  android.view.Window
 *  com.android.internal.telephony.IccCardConstants
 *  com.android.internal.telephony.IccCardConstants$State
 */
package com.android.internal.telephony.uicc;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.telephony.Rlog;
import android.text.TextUtils;
import android.view.Window;
import com.android.internal.telephony.CommandsInterface;
import com.android.internal.telephony.IccCardConstants;
import com.android.internal.telephony.uicc.AnswerToReset;
import com.android.internal.telephony.uicc.IccCardStatus;
import com.android.internal.telephony.uicc.IccSlotStatus;
import com.android.internal.telephony.uicc.UiccCard;
import com.android.internal.telephony.uicc.UiccController;
import com.android.internal.telephony.uicc.euicc.EuiccCard;
import java.io.FileDescriptor;
import java.io.PrintWriter;

public class UiccSlot
extends Handler {
    private static final boolean DBG = true;
    private static final int EVENT_CARD_ADDED = 14;
    private static final int EVENT_CARD_REMOVED = 13;
    public static final String EXTRA_ICC_CARD_ADDED = "com.android.internal.telephony.uicc.ICC_CARD_ADDED";
    public static final int INVALID_PHONE_ID = -1;
    private static final String TAG = "UiccSlot";
    private boolean mActive;
    private AnswerToReset mAtr;
    private IccCardStatus.CardState mCardState;
    private CommandsInterface mCi;
    private Context mContext;
    private String mIccId;
    private boolean mIsEuicc;
    private boolean mIsRemovable;
    private int mLastRadioState = 2;
    private final Object mLock = new Object();
    private int mPhoneId = -1;
    private boolean mStateIsUnknown = true;
    private UiccCard mUiccCard;

    public UiccSlot(Context context, boolean bl) {
        this.log("Creating");
        this.mContext = context;
        this.mActive = bl;
        this.mCardState = null;
    }

    private boolean absentStateUpdateNeeded(IccCardStatus.CardState cardState) {
        boolean bl = (cardState != IccCardStatus.CardState.CARDSTATE_ABSENT || this.mUiccCard != null) && this.mCardState == IccCardStatus.CardState.CARDSTATE_ABSENT;
        return bl;
    }

    private void checkIsEuiccSupported() {
        AnswerToReset answerToReset = this.mAtr;
        this.mIsEuicc = answerToReset != null && answerToReset.isEuiccSupported();
    }

    private boolean isSlotRemovable(int n) {
        int[] arrn = this.mContext.getResources().getIntArray(17236094);
        if (arrn == null) {
            return true;
        }
        int n2 = arrn.length;
        for (int i = 0; i < n2; ++i) {
            if (arrn[i] != n) continue;
            return false;
        }
        return true;
    }

    private void log(String string) {
        Rlog.d((String)TAG, (String)string);
    }

    private void loge(String string) {
        Rlog.e((String)TAG, (String)string);
    }

    private void nullifyUiccCard(boolean bl) {
        UiccCard uiccCard = this.mUiccCard;
        if (uiccCard != null) {
            uiccCard.dispose();
        }
        this.mStateIsUnknown = bl;
        this.mUiccCard = null;
    }

    private void onIccSwap(boolean bl) {
        if (this.mContext.getResources().getBoolean(17891466)) {
            this.log("onIccSwap: isHotSwapSupported is true, don't prompt for rebooting");
            return;
        }
        this.log("onIccSwap: isHotSwapSupported is false, prompt for rebooting");
        this.promptForRestart(bl);
    }

    private void parseAtr(String string) {
        this.mAtr = AnswerToReset.parseAtr(string);
        this.checkIsEuiccSupported();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void promptForRestart(boolean bl) {
        Object object = this.mLock;
        synchronized (object) {
            Object object2;
            String string = this.mContext.getResources().getString(17039740);
            if (string != null) {
                object2 = new Intent();
                object2 = object2.setComponent(ComponentName.unflattenFromString((String)string)).addFlags(268435456).putExtra(EXTRA_ICC_CARD_ADDED, bl);
                try {
                    this.mContext.startActivity((Intent)object2);
                    return;
                }
                catch (ActivityNotFoundException activityNotFoundException) {
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("Unable to find ICC hotswap prompt for restart activity: ");
                    ((StringBuilder)object2).append((Object)activityNotFoundException);
                    this.loge(((StringBuilder)object2).toString());
                }
            }
            DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener(){

                /*
                 * Enabled aggressive block sorting
                 * Enabled unnecessary exception pruning
                 * Enabled aggressive exception aggregation
                 */
                public void onClick(DialogInterface object, int n) {
                    object = UiccSlot.this.mLock;
                    synchronized (object) {
                        if (n == -1) {
                            UiccSlot.this.log("Reboot due to SIM swap");
                            ((PowerManager)UiccSlot.this.mContext.getSystemService("power")).reboot("SIM is added.");
                        }
                        return;
                    }
                }
            };
            Object object3 = Resources.getSystem();
            object2 = bl ? object3.getString(17041028) : object3.getString(17041031);
            string = bl ? object3.getString(17041027) : object3.getString(17041030);
            object3 = object3.getString(17041032);
            AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext);
            object2 = builder.setTitle((CharSequence)object2).setMessage((CharSequence)string).setPositiveButton((CharSequence)object3, onClickListener).create();
            object2.getWindow().setType(2003);
            object2.show();
            return;
        }
    }

    private void updateCardStateAbsent() {
        CommandsInterface commandsInterface = this.mCi;
        int n = commandsInterface == null ? 2 : commandsInterface.getRadioState();
        if (n == 1 && this.mLastRadioState == 1) {
            this.log("update: notify card removed");
            this.sendMessage(this.obtainMessage(13, null));
        }
        UiccController.updateInternalIccState(this.mContext, IccCardConstants.State.ABSENT, null, this.mPhoneId);
        this.nullifyUiccCard(false);
        this.mLastRadioState = n;
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
        printWriter.println("UiccSlot:");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" mCi=");
        stringBuilder.append(this.mCi);
        printWriter.println(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append(" mActive=");
        stringBuilder.append(this.mActive);
        printWriter.println(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append(" mIsEuicc=");
        stringBuilder.append(this.mIsEuicc);
        printWriter.println(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append(" mLastRadioState=");
        stringBuilder.append(this.mLastRadioState);
        printWriter.println(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append(" mIccId=");
        stringBuilder.append(this.mIccId);
        printWriter.println(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append(" mCardState=");
        stringBuilder.append((Object)this.mCardState);
        printWriter.println(stringBuilder.toString());
        if (this.mUiccCard != null) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(" mUiccCard=");
            stringBuilder.append(this.mUiccCard);
            printWriter.println(stringBuilder.toString());
            this.mUiccCard.dump(fileDescriptor, printWriter, arrstring);
        } else {
            printWriter.println(" mUiccCard=null");
        }
        printWriter.println();
        printWriter.flush();
    }

    protected void finalize() {
        this.log("UiccSlot finalized");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public IccCardStatus.CardState getCardState() {
        Object object = this.mLock;
        synchronized (object) {
            if (this.mCardState != null) return this.mCardState;
            return IccCardStatus.CardState.CARDSTATE_ABSENT;
        }
    }

    public String getIccId() {
        Object object = this.mIccId;
        if (object != null) {
            return object;
        }
        object = this.mUiccCard;
        if (object != null) {
            return ((UiccCard)object).getIccId();
        }
        return null;
    }

    public int getPhoneId() {
        return this.mPhoneId;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public UiccCard getUiccCard() {
        Object object = this.mLock;
        synchronized (object) {
            return this.mUiccCard;
        }
    }

    public void handleMessage(Message message) {
        int n = message.what;
        if (n != 13) {
            if (n != 14) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unknown Event ");
                stringBuilder.append(message.what);
                this.loge(stringBuilder.toString());
            } else {
                this.onIccSwap(true);
            }
        } else {
            this.onIccSwap(false);
        }
    }

    public boolean isActive() {
        return this.mActive;
    }

    public boolean isEuicc() {
        return this.mIsEuicc;
    }

    public boolean isExtendedApduSupported() {
        AnswerToReset answerToReset = this.mAtr;
        boolean bl = answerToReset != null && answerToReset.isExtendedApduSupported();
        return bl;
    }

    public boolean isRemovable() {
        return this.mIsRemovable;
    }

    public boolean isStateUnknown() {
        IccCardStatus.CardState cardState = this.mCardState;
        if (cardState != null && cardState != IccCardStatus.CardState.CARDSTATE_ABSENT) {
            boolean bl = this.mUiccCard == null;
            return bl;
        }
        return this.mStateIsUnknown;
    }

    public void onRadioStateUnavailable() {
        this.nullifyUiccCard(true);
        if (this.mPhoneId != -1) {
            UiccController.updateInternalIccState(this.mContext, IccCardConstants.State.UNKNOWN, null, this.mPhoneId);
        }
        this.mCardState = null;
        this.mLastRadioState = 2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void update(CommandsInterface object, IccCardStatus iccCardStatus, int n, int n2) {
        Object object2 = new StringBuilder();
        ((StringBuilder)object2).append("cardStatus update: ");
        ((StringBuilder)object2).append(iccCardStatus.toString());
        this.log(((StringBuilder)object2).toString());
        object2 = this.mLock;
        synchronized (object2) {
            IccCardStatus.CardState cardState = this.mCardState;
            this.mCardState = iccCardStatus.mCardState;
            this.mIccId = iccCardStatus.iccid;
            this.mPhoneId = n;
            this.parseAtr(iccCardStatus.atr);
            this.mCi = object;
            this.mIsRemovable = this.isSlotRemovable(n2);
            n2 = this.mCi.getRadioState();
            object = new StringBuilder();
            ((StringBuilder)object).append("update: radioState=");
            ((StringBuilder)object).append(n2);
            ((StringBuilder)object).append(" mLastRadioState=");
            ((StringBuilder)object).append(this.mLastRadioState);
            this.log(((StringBuilder)object).toString());
            if (this.absentStateUpdateNeeded(cardState)) {
                this.updateCardStateAbsent();
            } else if ((cardState == null || cardState == IccCardStatus.CardState.CARDSTATE_ABSENT || this.mUiccCard == null) && this.mCardState != IccCardStatus.CardState.CARDSTATE_ABSENT) {
                if (n2 == 1 && this.mLastRadioState == 1) {
                    this.log("update: notify card added");
                    this.sendMessage(this.obtainMessage(14, null));
                }
                if (this.mUiccCard != null) {
                    this.loge("update: mUiccCard != null when card was present; disposing it now");
                    this.mUiccCard.dispose();
                }
                if (!this.mIsEuicc) {
                    this.mUiccCard = object = new UiccCard(this.mContext, this.mCi, iccCardStatus, this.mPhoneId, this.mLock);
                } else {
                    if (TextUtils.isEmpty((CharSequence)iccCardStatus.eid)) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("update: eid is missing. ics.eid=");
                        ((StringBuilder)object).append(iccCardStatus.eid);
                        this.loge(((StringBuilder)object).toString());
                    }
                    this.mUiccCard = object = new EuiccCard(this.mContext, this.mCi, iccCardStatus, n, this.mLock);
                }
            } else if (this.mUiccCard != null) {
                this.mUiccCard.update(this.mContext, this.mCi, iccCardStatus);
            }
            this.mLastRadioState = n2;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void update(CommandsInterface commandsInterface, IccSlotStatus iccSlotStatus, int n) {
        Object object = new StringBuilder();
        ((StringBuilder)object).append("slotStatus update: ");
        ((StringBuilder)object).append(iccSlotStatus.toString());
        this.log(((StringBuilder)object).toString());
        object = this.mLock;
        synchronized (object) {
            IccCardStatus.CardState cardState = this.mCardState;
            this.mCi = commandsInterface;
            this.parseAtr(iccSlotStatus.atr);
            this.mCardState = iccSlotStatus.cardState;
            this.mIccId = iccSlotStatus.iccid;
            this.mIsRemovable = this.isSlotRemovable(n);
            if (iccSlotStatus.slotState == IccSlotStatus.SlotState.SLOTSTATE_INACTIVE) {
                if (this.mActive) {
                    this.mActive = false;
                    this.mLastRadioState = 2;
                    UiccController.updateInternalIccState(this.mContext, IccCardConstants.State.ABSENT, null, this.mPhoneId, true);
                    this.mPhoneId = -1;
                    this.nullifyUiccCard(true);
                }
            } else {
                this.mActive = true;
                this.mPhoneId = iccSlotStatus.logicalSlotIndex;
                if (this.absentStateUpdateNeeded(cardState)) {
                    this.updateCardStateAbsent();
                }
            }
            return;
        }
    }

}

