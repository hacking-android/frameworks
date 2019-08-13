/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 *  android.content.Context
 *  android.content.res.Resources
 *  android.os.AsyncResult
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Message
 *  android.os.ResultReceiver
 *  android.telephony.Rlog
 */
package com.android.internal.telephony.cdma;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncResult;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.ResultReceiver;
import android.telephony.Rlog;
import com.android.internal.telephony.CommandException;
import com.android.internal.telephony.GsmCdmaPhone;
import com.android.internal.telephony.MmiCode;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.uicc.IccCardApplicationStatus;
import com.android.internal.telephony.uicc.UiccCardApplication;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class CdmaMmiCode
extends Handler
implements MmiCode {
    static final String ACTION_REGISTER = "**";
    static final int EVENT_SET_COMPLETE = 1;
    static final String LOG_TAG = "CdmaMmiCode";
    static final int MATCH_GROUP_ACTION = 2;
    static final int MATCH_GROUP_DIALING_NUMBER = 12;
    static final int MATCH_GROUP_POUND_STRING = 1;
    static final int MATCH_GROUP_PWD_CONFIRM = 11;
    static final int MATCH_GROUP_SERVICE_CODE = 3;
    static final int MATCH_GROUP_SIA = 5;
    static final int MATCH_GROUP_SIB = 7;
    static final int MATCH_GROUP_SIC = 9;
    static final String SC_PIN = "04";
    static final String SC_PIN2 = "042";
    static final String SC_PUK = "05";
    static final String SC_PUK2 = "052";
    static Pattern sPatternSuppService = Pattern.compile("((\\*|#|\\*#|\\*\\*|##)(\\d{2,3})(\\*([^*#]*)(\\*([^*#]*)(\\*([^*#]*)(\\*([^*#]*))?)?)?)?#)(.*)");
    String mAction;
    Context mContext;
    String mDialingNumber;
    CharSequence mMessage;
    GsmCdmaPhone mPhone;
    String mPoundString;
    String mPwd;
    @UnsupportedAppUsage
    String mSc;
    String mSia;
    String mSib;
    String mSic;
    MmiCode.State mState = MmiCode.State.PENDING;
    UiccCardApplication mUiccApplication;

    CdmaMmiCode(GsmCdmaPhone gsmCdmaPhone, UiccCardApplication uiccCardApplication) {
        super(gsmCdmaPhone.getHandler().getLooper());
        this.mPhone = gsmCdmaPhone;
        this.mContext = gsmCdmaPhone.getContext();
        this.mUiccApplication = uiccCardApplication;
    }

    private CharSequence getScString() {
        if (this.mSc != null && this.isPinPukCommand()) {
            return this.mContext.getText(17039419);
        }
        return "";
    }

    private void handlePasswordError(int n) {
        this.mState = MmiCode.State.FAILED;
        StringBuilder stringBuilder = new StringBuilder(this.getScString());
        stringBuilder.append("\n");
        stringBuilder.append(this.mContext.getText(n));
        this.mMessage = stringBuilder;
        this.mPhone.onMMIDone(this);
    }

    @UnsupportedAppUsage
    private static String makeEmptyNull(String string) {
        if (string != null && string.length() == 0) {
            return null;
        }
        return string;
    }

    public static CdmaMmiCode newFromDialString(String object, GsmCdmaPhone gsmCdmaPhone, UiccCardApplication uiccCardApplication) {
        Object var3_3 = null;
        Matcher matcher = sPatternSuppService.matcher((CharSequence)object);
        object = var3_3;
        if (matcher.matches()) {
            object = new CdmaMmiCode(gsmCdmaPhone, uiccCardApplication);
            ((CdmaMmiCode)object).mPoundString = CdmaMmiCode.makeEmptyNull(matcher.group(1));
            ((CdmaMmiCode)object).mAction = CdmaMmiCode.makeEmptyNull(matcher.group(2));
            ((CdmaMmiCode)object).mSc = CdmaMmiCode.makeEmptyNull(matcher.group(3));
            ((CdmaMmiCode)object).mSia = CdmaMmiCode.makeEmptyNull(matcher.group(5));
            ((CdmaMmiCode)object).mSib = CdmaMmiCode.makeEmptyNull(matcher.group(7));
            ((CdmaMmiCode)object).mSic = CdmaMmiCode.makeEmptyNull(matcher.group(9));
            ((CdmaMmiCode)object).mPwd = CdmaMmiCode.makeEmptyNull(matcher.group(11));
            ((CdmaMmiCode)object).mDialingNumber = CdmaMmiCode.makeEmptyNull(matcher.group(12));
        }
        return object;
    }

    private void onSetComplete(Message object, AsyncResult object2) {
        StringBuilder stringBuilder = new StringBuilder(this.getScString());
        stringBuilder.append("\n");
        if (object2.exception != null) {
            this.mState = MmiCode.State.FAILED;
            if (object2.exception instanceof CommandException) {
                object2 = ((CommandException)object2.exception).getCommandError();
                if (object2 == CommandException.Error.PASSWORD_INCORRECT) {
                    if (this.isPinPukCommand()) {
                        if (!this.mSc.equals(SC_PUK) && !this.mSc.equals(SC_PUK2)) {
                            stringBuilder.append(this.mContext.getText(17039598));
                        } else {
                            stringBuilder.append(this.mContext.getText(17039599));
                        }
                        int n = ((Message)object).arg1;
                        if (n <= 0) {
                            Rlog.d((String)LOG_TAG, (String)"onSetComplete: PUK locked, cancel as lock screen will handle this");
                            this.mState = MmiCode.State.CANCELLED;
                        } else if (n > 0) {
                            object = new StringBuilder();
                            ((StringBuilder)object).append("onSetComplete: attemptsRemaining=");
                            ((StringBuilder)object).append(n);
                            Rlog.d((String)LOG_TAG, (String)((StringBuilder)object).toString());
                            stringBuilder.append(this.mContext.getResources().getQuantityString(18153494, n, new Object[]{n}));
                        }
                    } else {
                        stringBuilder.append(this.mContext.getText(17040537));
                    }
                } else if (object2 == CommandException.Error.SIM_PUK2) {
                    stringBuilder.append(this.mContext.getText(17039598));
                    stringBuilder.append("\n");
                    stringBuilder.append(this.mContext.getText(17040453));
                } else if (object2 == CommandException.Error.REQUEST_NOT_SUPPORTED) {
                    if (this.mSc.equals(SC_PIN)) {
                        stringBuilder.append(this.mContext.getText(17039897));
                    }
                } else {
                    stringBuilder.append(this.mContext.getText(17040444));
                }
            } else {
                stringBuilder.append(this.mContext.getText(17040444));
            }
        } else if (this.isRegister()) {
            this.mState = MmiCode.State.COMPLETE;
            stringBuilder.append(this.mContext.getText(17041002));
        } else {
            this.mState = MmiCode.State.FAILED;
            stringBuilder.append(this.mContext.getText(17040444));
        }
        this.mMessage = stringBuilder;
        this.mPhone.onMMIDone(this);
    }

    @Override
    public void cancel() {
        if (this.mState != MmiCode.State.COMPLETE && this.mState != MmiCode.State.FAILED) {
            this.mState = MmiCode.State.CANCELLED;
            this.mPhone.onMMIDone(this);
            return;
        }
    }

    @Override
    public String getDialString() {
        return null;
    }

    @Override
    public CharSequence getMessage() {
        return this.mMessage;
    }

    @Override
    public Phone getPhone() {
        return this.mPhone;
    }

    @Override
    public MmiCode.State getState() {
        return this.mState;
    }

    @Override
    public ResultReceiver getUssdCallbackReceiver() {
        return null;
    }

    public void handleMessage(Message message) {
        if (message.what == 1) {
            this.onSetComplete(message, (AsyncResult)message.obj);
        } else {
            Rlog.e((String)LOG_TAG, (String)"Unexpected reply");
        }
    }

    @Override
    public boolean isCancelable() {
        return false;
    }

    @Override
    public boolean isPinPukCommand() {
        String string = this.mSc;
        boolean bl = string != null && (string.equals(SC_PIN) || this.mSc.equals(SC_PIN2) || this.mSc.equals(SC_PUK) || this.mSc.equals(SC_PUK2));
        return bl;
    }

    boolean isRegister() {
        String string = this.mAction;
        boolean bl = string != null && string.equals(ACTION_REGISTER);
        return bl;
    }

    @Override
    public boolean isUssdRequest() {
        Rlog.w((String)LOG_TAG, (String)"isUssdRequest is not implemented in CdmaMmiCode");
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void processCode() {
        try {
            if (!this.isPinPukCommand()) return;
            Object object = this.mSia;
            Object object2 = this.mSib;
            int n = ((String)object2).length();
            if (!this.isRegister()) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Ivalid register/action=");
                ((StringBuilder)object).append(this.mAction);
                object2 = new RuntimeException(((StringBuilder)object).toString());
                throw object2;
            }
            if (!((String)object2).equals(this.mSic)) {
                this.handlePasswordError(17040434);
                return;
            }
            if (n >= 4 && n <= 8) {
                if (this.mSc.equals(SC_PIN) && this.mUiccApplication != null && this.mUiccApplication.getState() == IccCardApplicationStatus.AppState.APPSTATE_PUK) {
                    this.handlePasswordError(17040452);
                    return;
                }
                if (this.mUiccApplication == null) {
                    object = new RuntimeException("No application mUiccApplicaiton is null");
                    throw object;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("process mmi service code using UiccApp sc=");
                stringBuilder.append(this.mSc);
                Rlog.d((String)LOG_TAG, (String)stringBuilder.toString());
                if (this.mSc.equals(SC_PIN)) {
                    this.mUiccApplication.changeIccLockPassword((String)object, (String)object2, this.obtainMessage(1, (Object)this));
                    return;
                }
                if (this.mSc.equals(SC_PIN2)) {
                    this.mUiccApplication.changeIccFdnPassword((String)object, (String)object2, this.obtainMessage(1, (Object)this));
                    return;
                }
                if (this.mSc.equals(SC_PUK)) {
                    this.mUiccApplication.supplyPuk((String)object, (String)object2, this.obtainMessage(1, (Object)this));
                    return;
                }
                if (this.mSc.equals(SC_PUK2)) {
                    this.mUiccApplication.supplyPuk2((String)object, (String)object2, this.obtainMessage(1, (Object)this));
                    return;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("Unsupported service code=");
                ((StringBuilder)object).append(this.mSc);
                object2 = new RuntimeException(((StringBuilder)object).toString());
                throw object2;
            }
            this.handlePasswordError(17040140);
            return;
        }
        catch (RuntimeException runtimeException) {
            this.mState = MmiCode.State.FAILED;
            this.mMessage = this.mContext.getText(17040444);
            this.mPhone.onMMIDone(this);
        }
    }
}

