/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 *  android.content.Context
 *  android.content.res.Resources
 *  android.os.AsyncResult
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Message
 *  android.os.Parcelable
 *  android.os.ResultReceiver
 *  android.telephony.PhoneNumberUtils
 *  android.telephony.Rlog
 *  android.telephony.ServiceState
 *  android.telephony.ims.ImsCallForwardInfo
 *  android.telephony.ims.ImsSsData
 *  android.telephony.ims.ImsSsInfo
 *  android.telephony.ims.ImsSsInfo$Builder
 *  android.text.SpannableStringBuilder
 *  android.text.TextUtils
 *  com.android.ims.ImsException
 *  com.android.ims.ImsUtInterface
 */
package com.android.internal.telephony.imsphone;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncResult;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;
import android.os.ResultReceiver;
import android.telephony.PhoneNumberUtils;
import android.telephony.Rlog;
import android.telephony.ServiceState;
import android.telephony.ims.ImsCallForwardInfo;
import android.telephony.ims.ImsSsData;
import android.telephony.ims.ImsSsInfo;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import com.android.ims.ImsException;
import com.android.ims.ImsUtInterface;
import com.android.internal.telephony.CallForwardInfo;
import com.android.internal.telephony.CallStateException;
import com.android.internal.telephony.CommandException;
import com.android.internal.telephony.MmiCode;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.ServiceStateTracker;
import com.android.internal.telephony.imsphone.ImsPhone;
import com.android.internal.telephony.imsphone.ImsPhoneCallTracker;
import com.android.internal.telephony.uicc.IccRecords;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ImsPhoneMmiCode
extends Handler
implements MmiCode {
    private static final String ACTION_ACTIVATE = "*";
    private static final String ACTION_DEACTIVATE = "#";
    private static final String ACTION_ERASURE = "##";
    private static final String ACTION_INTERROGATE = "*#";
    private static final String ACTION_REGISTER = "**";
    private static final char END_OF_USSD_COMMAND = '#';
    private static final int EVENT_GET_CLIR_COMPLETE = 6;
    private static final int EVENT_QUERY_CF_COMPLETE = 1;
    private static final int EVENT_QUERY_COMPLETE = 3;
    private static final int EVENT_QUERY_ICB_COMPLETE = 10;
    private static final int EVENT_SET_CFF_COMPLETE = 4;
    private static final int EVENT_SET_COMPLETE = 0;
    private static final int EVENT_SUPP_SVC_QUERY_COMPLETE = 7;
    private static final int EVENT_USSD_CANCEL_COMPLETE = 5;
    private static final int EVENT_USSD_COMPLETE = 2;
    static final String IcbAnonymousMmi = "Anonymous Incoming Call Barring";
    static final String IcbDnMmi = "Specific Incoming Call Barring";
    static final String LOG_TAG = "ImsPhoneMmiCode";
    private static final int MATCH_GROUP_ACTION = 2;
    private static final int MATCH_GROUP_DIALING_NUMBER = 12;
    private static final int MATCH_GROUP_POUND_STRING = 1;
    private static final int MATCH_GROUP_PWD_CONFIRM = 11;
    private static final int MATCH_GROUP_SERVICE_CODE = 3;
    private static final int MATCH_GROUP_SIA = 5;
    private static final int MATCH_GROUP_SIB = 7;
    private static final int MATCH_GROUP_SIC = 9;
    private static final int MAX_LENGTH_SHORT_CODE = 2;
    private static final int NUM_PRESENTATION_ALLOWED = 0;
    private static final int NUM_PRESENTATION_RESTRICTED = 1;
    private static final String SC_BAIC = "35";
    private static final String SC_BAICa = "157";
    private static final String SC_BAICr = "351";
    private static final String SC_BAOC = "33";
    private static final String SC_BAOIC = "331";
    private static final String SC_BAOICxH = "332";
    private static final String SC_BA_ALL = "330";
    private static final String SC_BA_MO = "333";
    private static final String SC_BA_MT = "353";
    private static final String SC_BS_MT = "156";
    private static final String SC_CFB = "67";
    private static final String SC_CFNR = "62";
    private static final String SC_CFNRy = "61";
    private static final String SC_CFU = "21";
    private static final String SC_CFUT = "22";
    private static final String SC_CF_All = "002";
    private static final String SC_CF_All_Conditional = "004";
    private static final String SC_CLIP = "30";
    private static final String SC_CLIR = "31";
    private static final String SC_CNAP = "300";
    private static final String SC_COLP = "76";
    private static final String SC_COLR = "77";
    private static final String SC_PIN = "04";
    private static final String SC_PIN2 = "042";
    private static final String SC_PUK = "05";
    private static final String SC_PUK2 = "052";
    private static final String SC_PWD = "03";
    private static final String SC_WAIT = "43";
    public static final String UT_BUNDLE_KEY_CLIR = "queryClir";
    public static final String UT_BUNDLE_KEY_SSINFO = "imsSsInfo";
    private static Pattern sPatternSuppService = Pattern.compile("((\\*|#|\\*#|\\*\\*|##)(\\d{2,3})(\\*([^*#]*)(\\*([^*#]*)(\\*([^*#]*)(\\*([^*#]*))?)?)?)?#)(.*)");
    private static String[] sTwoDigitNumberPattern;
    private String mAction;
    private ResultReceiver mCallbackReceiver;
    @UnsupportedAppUsage
    private Context mContext;
    private String mDialingNumber;
    private IccRecords mIccRecords;
    private boolean mIsCallFwdReg;
    private boolean mIsPendingUSSD;
    private boolean mIsSsInfo = false;
    private boolean mIsUssdRequest;
    private CharSequence mMessage;
    @UnsupportedAppUsage
    private ImsPhone mPhone;
    private String mPoundString;
    private String mPwd;
    private String mSc;
    private String mSia;
    private String mSib;
    private String mSic;
    private MmiCode.State mState = MmiCode.State.PENDING;

    public ImsPhoneMmiCode(ImsPhone imsPhone) {
        super(imsPhone.getHandler().getLooper());
        this.mPhone = imsPhone;
        this.mContext = imsPhone.getContext();
        this.mIccRecords = this.mPhone.mDefaultPhone.getIccRecords();
    }

    private static String convertCdmaMmiCodesTo3gppMmiCodes(String charSequence) {
        Object object = sPatternCdmaMmiCodeWhileRoaming.matcher(charSequence);
        String string = charSequence;
        if (((Matcher)object).matches()) {
            String string2 = ImsPhoneMmiCode.makeEmptyNull(((Matcher)object).group(1));
            String string3 = ((Matcher)object).group(2);
            object = ImsPhoneMmiCode.makeEmptyNull(((Matcher)object).group(3));
            if (string2.equals(SC_CFB) && object != null) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("#31#");
                ((StringBuilder)charSequence).append(string3);
                ((StringBuilder)charSequence).append((String)object);
                string = ((StringBuilder)charSequence).toString();
            } else {
                string = charSequence;
                if (string2.equals("82")) {
                    string = charSequence;
                    if (object != null) {
                        charSequence = new StringBuilder();
                        ((StringBuilder)charSequence).append("*31#");
                        ((StringBuilder)charSequence).append(string3);
                        ((StringBuilder)charSequence).append((String)object);
                        string = ((StringBuilder)charSequence).toString();
                    }
                }
            }
        }
        return string;
    }

    private CharSequence createQueryCallWaitingResultMessage(int n) {
        StringBuilder stringBuilder = new StringBuilder(this.mContext.getText(17040999));
        for (int i = 1; i <= 128; i <<= 1) {
            if ((i & n) == 0) continue;
            stringBuilder.append("\n");
            stringBuilder.append(this.serviceClassToCFString(i & n));
        }
        return stringBuilder;
    }

    private String getActionStringFromReqType(int n) {
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            return null;
                        }
                        return ACTION_ERASURE;
                    }
                    return ACTION_REGISTER;
                }
                return ACTION_INTERROGATE;
            }
            return ACTION_DEACTIVATE;
        }
        return ACTION_ACTIVATE;
    }

    @UnsupportedAppUsage
    private CharSequence getErrorMessage(AsyncResult object) {
        if ((object = this.getMmiErrorMessage((AsyncResult)object)) == null) {
            object = this.mContext.getText(17040444);
        }
        return object;
    }

    private CharSequence getImsErrorMessage(AsyncResult asyncResult) {
        ImsException imsException = (ImsException)asyncResult.exception;
        CharSequence charSequence = this.getMmiErrorMessage(asyncResult);
        if (charSequence != null) {
            return charSequence;
        }
        if (imsException.getMessage() != null) {
            return imsException.getMessage();
        }
        return this.getErrorMessage(asyncResult);
    }

    private CharSequence getMmiErrorMessage(AsyncResult object) {
        if (((AsyncResult)object).exception instanceof ImsException) {
            int n = ((ImsException)((AsyncResult)object).exception).getCode();
            if (n != 241) {
                switch (n) {
                    default: {
                        return null;
                    }
                    case 825: {
                        return this.mContext.getText(17041096);
                    }
                    case 824: {
                        return this.mContext.getText(17041097);
                    }
                    case 823: {
                        return this.mContext.getText(17041098);
                    }
                    case 822: 
                }
                return this.mContext.getText(17041095);
            }
            return this.mContext.getText(17040446);
        }
        if (((AsyncResult)object).exception instanceof CommandException) {
            object = (CommandException)((AsyncResult)object).exception;
            if (((CommandException)object).getCommandError() == CommandException.Error.FDN_CHECK_FAILURE) {
                return this.mContext.getText(17040446);
            }
            if (((CommandException)object).getCommandError() == CommandException.Error.SS_MODIFIED_TO_DIAL) {
                return this.mContext.getText(17041095);
            }
            if (((CommandException)object).getCommandError() == CommandException.Error.SS_MODIFIED_TO_USSD) {
                return this.mContext.getText(17041098);
            }
            if (((CommandException)object).getCommandError() == CommandException.Error.SS_MODIFIED_TO_SS) {
                return this.mContext.getText(17041097);
            }
            if (((CommandException)object).getCommandError() == CommandException.Error.SS_MODIFIED_TO_DIAL_VIDEO) {
                return this.mContext.getText(17041096);
            }
        }
        return null;
    }

    @UnsupportedAppUsage
    private CharSequence getScString() {
        String string = this.mSc;
        if (string != null) {
            if (ImsPhoneMmiCode.isServiceCodeCallBarring(string)) {
                return this.mContext.getText(17039397);
            }
            if (ImsPhoneMmiCode.isServiceCodeCallForwarding(this.mSc)) {
                return this.mContext.getText(17039403);
            }
            if (this.mSc.equals(SC_PWD)) {
                return this.mContext.getText(17039420);
            }
            if (this.mSc.equals(SC_WAIT)) {
                return this.mContext.getText(17039411);
            }
            if (this.mSc.equals(SC_CLIP)) {
                return this.mContext.getText(17039404);
            }
            if (this.mSc.equals(SC_CLIR)) {
                return this.mContext.getText(17039405);
            }
            if (this.mSc.equals(SC_COLP)) {
                return this.mContext.getText(17039409);
            }
            if (this.mSc.equals(SC_COLR)) {
                return this.mContext.getText(17039410);
            }
            if (this.mSc.equals(SC_BS_MT)) {
                return IcbDnMmi;
            }
            if (this.mSc.equals(SC_BAICa)) {
                return IcbAnonymousMmi;
            }
        }
        return "";
    }

    private String getScStringFromScType(int n) {
        switch (n) {
            default: {
                return null;
            }
            case 22: {
                return SC_BAICa;
            }
            case 21: {
                return SC_BS_MT;
            }
            case 20: {
                return SC_BA_MT;
            }
            case 19: {
                return SC_BA_MO;
            }
            case 18: {
                return SC_BA_ALL;
            }
            case 17: {
                return SC_BAICr;
            }
            case 16: {
                return SC_BAIC;
            }
            case 15: {
                return SC_BAOICxH;
            }
            case 14: {
                return SC_BAOIC;
            }
            case 13: {
                return SC_BAOC;
            }
            case 12: {
                return SC_WAIT;
            }
            case 11: {
                return SC_CNAP;
            }
            case 10: {
                return SC_COLR;
            }
            case 9: {
                return SC_COLP;
            }
            case 8: {
                return SC_CLIR;
            }
            case 7: {
                return SC_CLIP;
            }
            case 5: {
                return SC_CF_All_Conditional;
            }
            case 4: {
                return SC_CF_All;
            }
            case 3: {
                return SC_CFNR;
            }
            case 2: {
                return SC_CFNRy;
            }
            case 1: {
                return SC_CFB;
            }
            case 0: 
        }
        return SC_CFU;
    }

    @UnsupportedAppUsage
    private static boolean isEmptyOrNull(CharSequence charSequence) {
        boolean bl = charSequence == null || charSequence.length() == 0;
        return bl;
    }

    static boolean isScMatchesSuppServType(String object) {
        boolean bl = false;
        object = sPatternSuppService.matcher((CharSequence)object);
        boolean bl2 = bl;
        if (((Matcher)object).matches()) {
            if (((String)(object = ImsPhoneMmiCode.makeEmptyNull(((Matcher)object).group(3)))).equals(SC_CFUT)) {
                bl2 = true;
            } else {
                bl2 = bl;
                if (((String)object).equals(SC_BS_MT)) {
                    bl2 = true;
                }
            }
        }
        return bl2;
    }

    private boolean isServiceClassVoiceVideoOrNone(int n) {
        boolean bl;
        boolean bl2 = bl = true;
        if (n != 0) {
            bl2 = bl;
            if (n != 1) {
                bl2 = n == 80 ? bl : false;
            }
        }
        return bl2;
    }

    static boolean isServiceCodeCallBarring(String string) {
        String[] arrstring = Resources.getSystem();
        if (string != null && (arrstring = arrstring.getStringArray(17235995)) != null) {
            int n = arrstring.length;
            for (int i = 0; i < n; ++i) {
                if (!string.equals(arrstring[i])) continue;
                return true;
            }
        }
        return false;
    }

    static boolean isServiceCodeCallForwarding(String string) {
        boolean bl = string != null && (string.equals(SC_CFU) || string.equals(SC_CFB) || string.equals(SC_CFNRy) || string.equals(SC_CFNR) || string.equals(SC_CF_All) || string.equals(SC_CF_All_Conditional));
        return bl;
    }

    private static boolean isShortCode(String string, ImsPhone imsPhone) {
        if (string == null) {
            return false;
        }
        if (string.length() == 0) {
            return false;
        }
        if (PhoneNumberUtils.isLocalEmergencyNumber((Context)imsPhone.getContext(), (String)string)) {
            return false;
        }
        return ImsPhoneMmiCode.isShortCodeUSSD(string, imsPhone);
    }

    private static boolean isShortCodeUSSD(String string, ImsPhone imsPhone) {
        if (string != null && string.length() <= 2) {
            if (imsPhone.isInCall()) {
                return true;
            }
            if (string.length() != 2 || string.charAt(0) != '1') {
                return true;
            }
        }
        return false;
    }

    /*
     * WARNING - void declaration
     */
    private static boolean isTwoDigitShortCode(Context object2, String string) {
        void var1_3;
        Rlog.d((String)LOG_TAG, (String)"isTwoDigitShortCode");
        if (var1_3 != null && var1_3.length() <= 2) {
            if (sTwoDigitNumberPattern == null) {
                sTwoDigitNumberPattern = object2.getResources().getStringArray(17236073);
            }
            for (String string2 : sTwoDigitNumberPattern) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Two Digit Number Pattern ");
                stringBuilder.append(string2);
                Rlog.d((String)LOG_TAG, (String)stringBuilder.toString());
                if (!var1_3.equals(string2)) continue;
                Rlog.d((String)LOG_TAG, (String)"Two Digit Number Pattern -true");
                return true;
            }
            Rlog.d((String)LOG_TAG, (String)"Two Digit Number Pattern -false");
            return false;
        }
        return false;
    }

    private CharSequence makeCFQueryResultMessage(CallForwardInfo callForwardInfo, int n) {
        int n2 = callForwardInfo.reason;
        boolean bl = false;
        n2 = n2 == 2 ? 1 : 0;
        CharSequence charSequence = callForwardInfo.status == 1 ? (n2 != 0 ? this.mContext.getText(17039648) : this.mContext.getText(17039647)) : (callForwardInfo.status == 0 && ImsPhoneMmiCode.isEmptyOrNull(callForwardInfo.number) ? this.mContext.getText(17039649) : (n2 != 0 ? this.mContext.getText(17039651) : this.mContext.getText(17039650)));
        CharSequence charSequence2 = this.serviceClassToCFString(callForwardInfo.serviceClass & n);
        String string = PhoneNumberUtils.stringFromStringAndTOA((String)callForwardInfo.number, (int)callForwardInfo.toa);
        String string2 = Integer.toString(callForwardInfo.timeSeconds);
        if (callForwardInfo.reason == 0 && (callForwardInfo.serviceClass & n) == 1) {
            if (callForwardInfo.status == 1) {
                bl = true;
            }
            if (this.mIccRecords != null) {
                this.mPhone.setVoiceCallForwardingFlag(1, bl, callForwardInfo.number);
            }
        }
        return TextUtils.replace((CharSequence)charSequence, (String[])new String[]{"{0}", "{1}", "{2}"}, (CharSequence[])new CharSequence[]{charSequence2, string, string2});
    }

    private static String makeEmptyNull(String string) {
        if (string != null && string.length() == 0) {
            return null;
        }
        return string;
    }

    @UnsupportedAppUsage
    static ImsPhoneMmiCode newFromDialString(String string, ImsPhone imsPhone) {
        return ImsPhoneMmiCode.newFromDialString(string, imsPhone, null);
    }

    static ImsPhoneMmiCode newFromDialString(String object, ImsPhone imsPhone, ResultReceiver object2) {
        ImsPhoneMmiCode imsPhoneMmiCode = null;
        String string = object;
        if (imsPhone.getDefaultPhone().getServiceState().getVoiceRoaming()) {
            string = object;
            if (imsPhone.getDefaultPhone().supportsConversionOfCdmaCallerIdMmiCodesWhileRoaming()) {
                string = ImsPhoneMmiCode.convertCdmaMmiCodesTo3gppMmiCodes((String)object);
            }
        }
        if (((Matcher)(object = sPatternSuppService.matcher(string))).matches()) {
            imsPhoneMmiCode = new ImsPhoneMmiCode(imsPhone);
            imsPhoneMmiCode.mPoundString = ImsPhoneMmiCode.makeEmptyNull(((Matcher)object).group(1));
            imsPhoneMmiCode.mAction = ImsPhoneMmiCode.makeEmptyNull(((Matcher)object).group(2));
            imsPhoneMmiCode.mSc = ImsPhoneMmiCode.makeEmptyNull(((Matcher)object).group(3));
            imsPhoneMmiCode.mSia = ImsPhoneMmiCode.makeEmptyNull(((Matcher)object).group(5));
            imsPhoneMmiCode.mSib = ImsPhoneMmiCode.makeEmptyNull(((Matcher)object).group(7));
            imsPhoneMmiCode.mSic = ImsPhoneMmiCode.makeEmptyNull(((Matcher)object).group(9));
            imsPhoneMmiCode.mPwd = ImsPhoneMmiCode.makeEmptyNull(((Matcher)object).group(11));
            imsPhoneMmiCode.mDialingNumber = ImsPhoneMmiCode.makeEmptyNull(((Matcher)object).group(12));
            imsPhoneMmiCode.mCallbackReceiver = object2;
            object2 = imsPhoneMmiCode.mDialingNumber;
            object = imsPhoneMmiCode;
            if (object2 != null) {
                object = imsPhoneMmiCode;
                if (((String)object2).endsWith(ACTION_DEACTIVATE)) {
                    object = imsPhoneMmiCode;
                    if (string.endsWith(ACTION_DEACTIVATE)) {
                        object = new ImsPhoneMmiCode(imsPhone);
                        ((ImsPhoneMmiCode)object).mPoundString = string;
                    }
                }
            }
        } else if (string.endsWith(ACTION_DEACTIVATE)) {
            object = new ImsPhoneMmiCode(imsPhone);
            ((ImsPhoneMmiCode)object).mPoundString = string;
        } else if (ImsPhoneMmiCode.isTwoDigitShortCode(imsPhone.getContext(), string)) {
            object = null;
        } else {
            object = imsPhoneMmiCode;
            if (ImsPhoneMmiCode.isShortCode(string, imsPhone)) {
                object = new ImsPhoneMmiCode(imsPhone);
                ((ImsPhoneMmiCode)object).mDialingNumber = string;
            }
        }
        return object;
    }

    static ImsPhoneMmiCode newFromUssdUserInput(String string, ImsPhone handler) {
        handler = new ImsPhoneMmiCode((ImsPhone)handler);
        ((ImsPhoneMmiCode)handler).mMessage = string;
        ((ImsPhoneMmiCode)handler).mState = MmiCode.State.PENDING;
        ((ImsPhoneMmiCode)handler).mIsPendingUSSD = true;
        return handler;
    }

    static ImsPhoneMmiCode newNetworkInitiatedUssd(String string, boolean bl, ImsPhone handler) {
        handler = new ImsPhoneMmiCode((ImsPhone)handler);
        ((ImsPhoneMmiCode)handler).mMessage = string;
        ((ImsPhoneMmiCode)handler).mIsUssdRequest = bl;
        if (bl) {
            ((ImsPhoneMmiCode)handler).mIsPendingUSSD = true;
            ((ImsPhoneMmiCode)handler).mState = MmiCode.State.PENDING;
        } else {
            ((ImsPhoneMmiCode)handler).mState = MmiCode.State.COMPLETE;
        }
        return handler;
    }

    private void onIcbQueryComplete(AsyncResult object) {
        Object object2 = new StringBuilder();
        ((StringBuilder)object2).append("onIcbQueryComplete mmi=");
        ((StringBuilder)object2).append(this);
        Rlog.d((String)LOG_TAG, (String)((StringBuilder)object2).toString());
        StringBuilder stringBuilder = new StringBuilder(this.getScString());
        stringBuilder.append("\n");
        if (((AsyncResult)object).exception != null) {
            this.mState = MmiCode.State.FAILED;
            if (((AsyncResult)object).exception instanceof ImsException) {
                stringBuilder.append(this.getImsErrorMessage((AsyncResult)object));
            } else {
                stringBuilder.append(this.getErrorMessage((AsyncResult)object));
            }
        } else {
            try {
                object = object2 = (List)((AsyncResult)object).result;
            }
            catch (ClassCastException classCastException) {
                object = Arrays.asList((ImsSsInfo[])((AsyncResult)object).result);
            }
            if (object != null && object.size() != 0) {
                int n = object.size();
                for (int i = 0; i < n; ++i) {
                    ImsSsInfo imsSsInfo = (ImsSsInfo)object.get(i);
                    if (imsSsInfo.getIncomingCommunicationBarringNumber() != null) {
                        object2 = new StringBuilder();
                        ((StringBuilder)object2).append("Num: ");
                        ((StringBuilder)object2).append(imsSsInfo.getIncomingCommunicationBarringNumber());
                        ((StringBuilder)object2).append(" status: ");
                        ((StringBuilder)object2).append(imsSsInfo.getStatus());
                        ((StringBuilder)object2).append("\n");
                        stringBuilder.append(((StringBuilder)object2).toString());
                        continue;
                    }
                    if (imsSsInfo.getStatus() == 1) {
                        stringBuilder.append(this.mContext.getText(17040998));
                        continue;
                    }
                    stringBuilder.append(this.mContext.getText(17040997));
                }
            } else {
                stringBuilder.append(this.mContext.getText(17040997));
            }
            this.mState = MmiCode.State.COMPLETE;
        }
        this.mMessage = stringBuilder;
        this.mPhone.onMMIDone(this);
    }

    private void onQueryCfComplete(AsyncResult object) {
        StringBuilder stringBuilder = new StringBuilder(this.getScString());
        stringBuilder.append("\n");
        if (((AsyncResult)object).exception != null) {
            this.mState = MmiCode.State.FAILED;
            if (((AsyncResult)object).exception instanceof ImsException) {
                stringBuilder.append(this.getImsErrorMessage((AsyncResult)object));
            } else {
                stringBuilder.append(this.getErrorMessage((AsyncResult)object));
            }
        } else {
            object = (CallForwardInfo[])((AsyncResult)object).result;
            if (object != null && ((CallForwardInfo[])object).length != 0) {
                SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
                for (int i = 1; i <= 128; i <<= 1) {
                    int n = ((CallForwardInfo[])object).length;
                    for (int j = 0; j < n; ++j) {
                        if ((((CallForwardInfo)object[j]).serviceClass & i) == 0) continue;
                        spannableStringBuilder.append(this.makeCFQueryResultMessage((CallForwardInfo)object[j], i));
                        spannableStringBuilder.append((CharSequence)"\n");
                    }
                }
                stringBuilder.append((CharSequence)spannableStringBuilder);
            } else {
                stringBuilder.append(this.mContext.getText(17040997));
                if (this.mIccRecords != null) {
                    this.mPhone.setVoiceCallForwardingFlag(1, false, null);
                }
            }
            this.mState = MmiCode.State.COMPLETE;
        }
        this.mMessage = stringBuilder;
        object = new StringBuilder();
        ((StringBuilder)object).append("onQueryCfComplete: mmi=");
        ((StringBuilder)object).append(this);
        Rlog.d((String)LOG_TAG, (String)((StringBuilder)object).toString());
        this.mPhone.onMMIDone(this);
    }

    private void onQueryClirComplete(AsyncResult object) {
        StringBuilder stringBuilder = new StringBuilder(this.getScString());
        stringBuilder.append("\n");
        this.mState = MmiCode.State.FAILED;
        if (((AsyncResult)object).exception != null) {
            if (((AsyncResult)object).exception instanceof ImsException) {
                stringBuilder.append(this.getImsErrorMessage((AsyncResult)object));
            }
        } else {
            int[] arrn = ((Bundle)((AsyncResult)object).result).getIntArray(UT_BUNDLE_KEY_CLIR);
            object = new StringBuilder();
            ((StringBuilder)object).append("onQueryClirComplete: CLIR param n=");
            ((StringBuilder)object).append(arrn[0]);
            ((StringBuilder)object).append(" m=");
            ((StringBuilder)object).append(arrn[1]);
            Rlog.d((String)LOG_TAG, (String)((StringBuilder)object).toString());
            int n = arrn[1];
            if (n != 0) {
                if (n != 1) {
                    if (n != 3) {
                        if (n != 4) {
                            stringBuilder.append(this.mContext.getText(17040444));
                            this.mState = MmiCode.State.FAILED;
                        } else {
                            n = arrn[0];
                            if (n != 0) {
                                if (n != 1) {
                                    if (n != 2) {
                                        stringBuilder.append(this.mContext.getText(17040444));
                                        this.mState = MmiCode.State.FAILED;
                                    } else {
                                        stringBuilder.append(this.mContext.getText(17039398));
                                        this.mState = MmiCode.State.COMPLETE;
                                    }
                                } else {
                                    stringBuilder.append(this.mContext.getText(17039399));
                                    this.mState = MmiCode.State.COMPLETE;
                                }
                            } else {
                                stringBuilder.append(this.mContext.getText(17039398));
                                this.mState = MmiCode.State.COMPLETE;
                            }
                        }
                    } else {
                        n = arrn[0];
                        if (n != 0) {
                            if (n != 1) {
                                if (n != 2) {
                                    stringBuilder.append(this.mContext.getText(17040444));
                                    this.mState = MmiCode.State.FAILED;
                                } else {
                                    stringBuilder.append(this.mContext.getText(17039400));
                                    this.mState = MmiCode.State.COMPLETE;
                                }
                            } else {
                                stringBuilder.append(this.mContext.getText(17039401));
                                this.mState = MmiCode.State.COMPLETE;
                            }
                        } else {
                            stringBuilder.append(this.mContext.getText(17039401));
                            this.mState = MmiCode.State.COMPLETE;
                        }
                    }
                } else {
                    stringBuilder.append(this.mContext.getText(17039402));
                    this.mState = MmiCode.State.COMPLETE;
                }
            } else {
                stringBuilder.append(this.mContext.getText(17041001));
                this.mState = MmiCode.State.COMPLETE;
            }
        }
        this.mMessage = stringBuilder;
        object = new StringBuilder();
        ((StringBuilder)object).append("onQueryClirComplete mmi=");
        ((StringBuilder)object).append(this);
        Rlog.d((String)LOG_TAG, (String)((StringBuilder)object).toString());
        this.mPhone.onMMIDone(this);
    }

    private void onQueryComplete(AsyncResult object) {
        StringBuilder stringBuilder = new StringBuilder(this.getScString());
        stringBuilder.append("\n");
        if (((AsyncResult)object).exception != null) {
            this.mState = MmiCode.State.FAILED;
            if (((AsyncResult)object).exception instanceof ImsException) {
                stringBuilder.append(this.getImsErrorMessage((AsyncResult)object));
            } else {
                stringBuilder.append(this.getErrorMessage((AsyncResult)object));
            }
        } else {
            object = (int[])((AsyncResult)object).result;
            if (((int[])object).length != 0) {
                if (object[0] == 0) {
                    stringBuilder.append(this.mContext.getText(17040997));
                } else if (this.mSc.equals(SC_WAIT)) {
                    stringBuilder.append(this.createQueryCallWaitingResultMessage(object[1]));
                } else if (object[0] == 1) {
                    stringBuilder.append(this.mContext.getText(17040998));
                } else {
                    stringBuilder.append(this.mContext.getText(17040444));
                }
            } else {
                stringBuilder.append(this.mContext.getText(17040444));
            }
            this.mState = MmiCode.State.COMPLETE;
        }
        this.mMessage = stringBuilder;
        object = new StringBuilder();
        ((StringBuilder)object).append("onQueryComplete mmi=");
        ((StringBuilder)object).append(this);
        Rlog.d((String)LOG_TAG, (String)((StringBuilder)object).toString());
        this.mPhone.onMMIDone(this);
    }

    private void onSetComplete(Message object, AsyncResult object2) {
        object = new StringBuilder(this.getScString());
        ((StringBuilder)object).append("\n");
        if (object2.exception != null) {
            this.mState = MmiCode.State.FAILED;
            if (object2.exception instanceof CommandException) {
                CommandException commandException = (CommandException)object2.exception;
                if (commandException.getCommandError() == CommandException.Error.PASSWORD_INCORRECT) {
                    ((StringBuilder)object).append(this.mContext.getText(17040537));
                } else if ((object2 = this.getMmiErrorMessage((AsyncResult)object2)) != null) {
                    ((StringBuilder)object).append((CharSequence)object2);
                } else if (commandException.getMessage() != null) {
                    ((StringBuilder)object).append(commandException.getMessage());
                } else {
                    ((StringBuilder)object).append(this.mContext.getText(17040444));
                }
            } else if (object2.exception instanceof ImsException) {
                ((StringBuilder)object).append(this.getImsErrorMessage((AsyncResult)object2));
            }
        } else if (this.isActivate()) {
            this.mState = MmiCode.State.COMPLETE;
            if (this.mIsCallFwdReg) {
                ((StringBuilder)object).append(this.mContext.getText(17041002));
            } else {
                ((StringBuilder)object).append(this.mContext.getText(17040998));
            }
            if (this.mSc.equals(SC_CLIR)) {
                this.mPhone.saveClirSetting(1);
            }
        } else if (this.isDeactivate()) {
            this.mState = MmiCode.State.COMPLETE;
            ((StringBuilder)object).append(this.mContext.getText(17040997));
            if (this.mSc.equals(SC_CLIR)) {
                this.mPhone.saveClirSetting(2);
            }
        } else if (this.isRegister()) {
            this.mState = MmiCode.State.COMPLETE;
            ((StringBuilder)object).append(this.mContext.getText(17041002));
        } else if (this.isErasure()) {
            this.mState = MmiCode.State.COMPLETE;
            ((StringBuilder)object).append(this.mContext.getText(17041000));
        } else {
            this.mState = MmiCode.State.FAILED;
            ((StringBuilder)object).append(this.mContext.getText(17040444));
        }
        this.mMessage = object;
        object = new StringBuilder();
        ((StringBuilder)object).append("onSetComplete: mmi=");
        ((StringBuilder)object).append(this);
        Rlog.d((String)LOG_TAG, (String)((StringBuilder)object).toString());
        this.mPhone.onMMIDone(this);
    }

    private void onSuppSvcQueryComplete(AsyncResult object) {
        StringBuilder stringBuilder = new StringBuilder(this.getScString());
        stringBuilder.append("\n");
        this.mState = MmiCode.State.FAILED;
        if (((AsyncResult)object).exception != null) {
            if (((AsyncResult)object).exception instanceof ImsException) {
                stringBuilder.append(this.getImsErrorMessage((AsyncResult)object));
            } else {
                stringBuilder.append(this.getErrorMessage((AsyncResult)object));
            }
        } else if (((AsyncResult)object).result instanceof Bundle) {
            Rlog.d((String)LOG_TAG, (String)"onSuppSvcQueryComplete: Received CLIP/COLP/COLR Response.");
            object = (ImsSsInfo)((Bundle)((AsyncResult)object).result).getParcelable(UT_BUNDLE_KEY_SSINFO);
            if (object != null) {
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("onSuppSvcQueryComplete: ImsSsInfo mStatus = ");
                stringBuilder2.append(object.getStatus());
                Rlog.d((String)LOG_TAG, (String)stringBuilder2.toString());
                if (object.getProvisionStatus() == 0) {
                    stringBuilder.append(this.mContext.getText(17041001));
                    this.mState = MmiCode.State.COMPLETE;
                } else if (object.getStatus() == 0) {
                    stringBuilder.append(this.mContext.getText(17040997));
                    this.mState = MmiCode.State.COMPLETE;
                } else if (object.getStatus() == 1) {
                    stringBuilder.append(this.mContext.getText(17040998));
                    this.mState = MmiCode.State.COMPLETE;
                } else {
                    stringBuilder.append(this.mContext.getText(17040444));
                }
            } else {
                stringBuilder.append(this.mContext.getText(17040444));
            }
        } else {
            Rlog.d((String)LOG_TAG, (String)"onSuppSvcQueryComplete: Received Call Barring Response.");
            if (((int[])((AsyncResult)object).result)[0] == 1) {
                stringBuilder.append(this.mContext.getText(17040998));
                this.mState = MmiCode.State.COMPLETE;
            } else {
                stringBuilder.append(this.mContext.getText(17040997));
                this.mState = MmiCode.State.COMPLETE;
            }
        }
        this.mMessage = stringBuilder;
        object = new StringBuilder();
        ((StringBuilder)object).append("onSuppSvcQueryComplete mmi=");
        ((StringBuilder)object).append(this);
        Rlog.d((String)LOG_TAG, (String)((StringBuilder)object).toString());
        this.mPhone.onMMIDone(this);
    }

    private void processIcbMmiCodeForUpdate() {
        String string = this.mSia;
        String[] arrstring = null;
        if (string != null) {
            arrstring = string.split("\\$");
        }
        int n = this.callBarAction(string);
        try {
            this.mPhone.mCT.getUtInterface().updateCallBarring(10, n, this.obtainMessage(0, (Object)this), arrstring);
        }
        catch (ImsException imsException) {
            Rlog.d((String)LOG_TAG, (String)"processIcbMmiCodeForUpdate:Could not get UT handle for updating ICB.");
        }
    }

    static String scToBarringFacility(String string) {
        if (string != null) {
            if (string.equals(SC_BAOC)) {
                return "AO";
            }
            if (string.equals(SC_BAOIC)) {
                return "OI";
            }
            if (string.equals(SC_BAOICxH)) {
                return "OX";
            }
            if (string.equals(SC_BAIC)) {
                return "AI";
            }
            if (string.equals(SC_BAICr)) {
                return "IR";
            }
            if (string.equals(SC_BA_ALL)) {
                return "AB";
            }
            if (string.equals(SC_BA_MO)) {
                return "AG";
            }
            if (string.equals(SC_BA_MT)) {
                return "AC";
            }
            throw new RuntimeException("invalid call barring sc");
        }
        throw new RuntimeException("invalid call barring sc");
    }

    private static int scToCallForwardReason(String string) {
        if (string != null) {
            if (string.equals(SC_CF_All)) {
                return 4;
            }
            if (string.equals(SC_CFU)) {
                return 0;
            }
            if (string.equals(SC_CFB)) {
                return 1;
            }
            if (string.equals(SC_CFNR)) {
                return 3;
            }
            if (string.equals(SC_CFNRy)) {
                return 2;
            }
            if (string.equals(SC_CF_All_Conditional)) {
                return 5;
            }
            throw new RuntimeException("invalid call forward sc");
        }
        throw new RuntimeException("invalid call forward sc");
    }

    @UnsupportedAppUsage
    private CharSequence serviceClassToCFString(int n) {
        if (n != 1) {
            if (n != 2) {
                if (n != 4) {
                    if (n != 8) {
                        if (n != 16) {
                            if (n != 32) {
                                if (n != 64) {
                                    if (n != 128) {
                                        return null;
                                    }
                                    return this.mContext.getText(17040993);
                                }
                                return this.mContext.getText(17040994);
                            }
                            return this.mContext.getText(17040990);
                        }
                        return this.mContext.getText(17040991);
                    }
                    return this.mContext.getText(17040995);
                }
                return this.mContext.getText(17040992);
            }
            return this.mContext.getText(17040989);
        }
        return this.mContext.getText(17040996);
    }

    private static int siToServiceClass(String string) {
        if (string != null && string.length() != 0) {
            int n = Integer.parseInt(string, 10);
            if (n != 16) {
                if (n != 99) {
                    switch (n) {
                        default: {
                            switch (n) {
                                default: {
                                    switch (n) {
                                        default: {
                                            StringBuilder stringBuilder = new StringBuilder();
                                            stringBuilder.append("unsupported MMI service code ");
                                            stringBuilder.append(string);
                                            throw new RuntimeException(stringBuilder.toString());
                                        }
                                        case 26: {
                                            return 17;
                                        }
                                        case 25: {
                                            return 32;
                                        }
                                        case 24: 
                                    }
                                    return 16;
                                }
                                case 22: {
                                    return 80;
                                }
                                case 21: {
                                    return 160;
                                }
                                case 20: {
                                    return 48;
                                }
                                case 19: 
                            }
                            return 5;
                        }
                        case 13: {
                            return 4;
                        }
                        case 12: {
                            return 12;
                        }
                        case 11: {
                            return 1;
                        }
                        case 10: 
                    }
                    return 13;
                }
                return 64;
            }
            return 8;
        }
        return 0;
    }

    private static int siToTime(String string) {
        if (string != null && string.length() != 0) {
            return Integer.parseInt(string, 10);
        }
        return 0;
    }

    public int callBarAction(String string) {
        if (this.isActivate()) {
            return 1;
        }
        if (this.isDeactivate()) {
            return 0;
        }
        if (this.isRegister()) {
            if (!ImsPhoneMmiCode.isEmptyOrNull(string)) {
                return 3;
            }
            throw new RuntimeException("invalid action");
        }
        if (this.isErasure()) {
            return 4;
        }
        throw new RuntimeException("invalid action");
    }

    @Override
    public void cancel() {
        if (this.mState != MmiCode.State.COMPLETE && this.mState != MmiCode.State.FAILED) {
            this.mState = MmiCode.State.CANCELLED;
            if (this.mIsPendingUSSD) {
                this.mPhone.cancelUSSD(this.obtainMessage(5, (Object)this));
            } else {
                this.mPhone.onMMIDone(this);
            }
            return;
        }
    }

    @UnsupportedAppUsage
    int getCLIRMode() {
        String string = this.mSc;
        if (string != null && string.equals(SC_CLIR)) {
            if (this.isActivate()) {
                return 2;
            }
            if (this.isDeactivate()) {
                return 1;
            }
        }
        return 0;
    }

    @Override
    public String getDialString() {
        return this.mPoundString;
    }

    @UnsupportedAppUsage
    String getDialingNumber() {
        return this.mDialingNumber;
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
        return this.mCallbackReceiver;
    }

    public void handleMessage(Message message) {
        block14 : {
            block13 : {
                int n = message.what;
                if (n == 10) break block13;
                switch (n) {
                    default: {
                        break;
                    }
                    case 7: {
                        this.onSuppSvcQueryComplete((AsyncResult)message.obj);
                        break;
                    }
                    case 6: {
                        this.onQueryClirComplete((AsyncResult)message.obj);
                        break;
                    }
                    case 5: {
                        this.mPhone.onMMIDone(this);
                        break;
                    }
                    case 4: {
                        AsyncResult asyncResult = (AsyncResult)message.obj;
                        if (asyncResult.exception == null && message.arg1 == 1) {
                            boolean bl = message.arg2 == 1;
                            if (this.mIccRecords != null) {
                                this.mPhone.setVoiceCallForwardingFlag(1, bl, this.mDialingNumber);
                            }
                        }
                        this.onSetComplete(message, asyncResult);
                        break;
                    }
                    case 3: {
                        this.onQueryComplete((AsyncResult)message.obj);
                        break;
                    }
                    case 2: {
                        message = (AsyncResult)message.obj;
                        if (message.exception != null) {
                            this.mState = MmiCode.State.FAILED;
                            this.mMessage = this.getErrorMessage((AsyncResult)message);
                            this.mPhone.onMMIDone(this);
                            break;
                        }
                        break block14;
                    }
                    case 1: {
                        this.onQueryCfComplete((AsyncResult)message.obj);
                        break;
                    }
                    case 0: {
                        this.onSetComplete(message, (AsyncResult)message.obj);
                        break;
                    }
                }
                break block14;
            }
            this.onIcbQueryComplete((AsyncResult)message.obj);
        }
    }

    @UnsupportedAppUsage
    boolean isActivate() {
        String string = this.mAction;
        boolean bl = string != null && string.equals(ACTION_ACTIVATE);
        return bl;
    }

    @Override
    public boolean isCancelable() {
        return this.mIsPendingUSSD;
    }

    @UnsupportedAppUsage
    boolean isDeactivate() {
        String string = this.mAction;
        boolean bl = string != null && string.equals(ACTION_DEACTIVATE);
        return bl;
    }

    @UnsupportedAppUsage
    boolean isErasure() {
        String string = this.mAction;
        boolean bl = string != null && string.equals(ACTION_ERASURE);
        return bl;
    }

    boolean isInterrogate() {
        String string = this.mAction;
        boolean bl = string != null && string.equals(ACTION_INTERROGATE);
        return bl;
    }

    boolean isMMI() {
        boolean bl = this.mPoundString != null;
        return bl;
    }

    public boolean isPendingUSSD() {
        return this.mIsPendingUSSD;
    }

    @Override
    public boolean isPinPukCommand() {
        String string = this.mSc;
        boolean bl = string != null && (string.equals(SC_PIN) || this.mSc.equals(SC_PIN2) || this.mSc.equals(SC_PUK) || this.mSc.equals(SC_PUK2));
        return bl;
    }

    @UnsupportedAppUsage
    boolean isRegister() {
        String string = this.mAction;
        boolean bl = string != null && string.equals(ACTION_REGISTER);
        return bl;
    }

    boolean isShortCode() {
        String string;
        boolean bl = this.mPoundString == null && (string = this.mDialingNumber) != null && string.length() <= 2;
        return bl;
    }

    public boolean isSsInfo() {
        return this.mIsSsInfo;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @UnsupportedAppUsage
    boolean isSupportedOverImsPhone() {
        CharSequence charSequence;
        if (this.isShortCode()) {
            return true;
        }
        if (!(ImsPhoneMmiCode.isServiceCodeCallForwarding(this.mSc) || ImsPhoneMmiCode.isServiceCodeCallBarring(this.mSc) || (charSequence = this.mSc) != null && ((String)charSequence).equals(SC_WAIT) || (charSequence = this.mSc) != null && ((String)charSequence).equals(SC_CLIR) || (charSequence = this.mSc) != null && ((String)charSequence).equals(SC_CLIP) || (charSequence = this.mSc) != null && ((String)charSequence).equals(SC_COLR) || (charSequence = this.mSc) != null && ((String)charSequence).equals(SC_COLP) || (charSequence = this.mSc) != null && ((String)charSequence).equals(SC_BS_MT) || (charSequence = this.mSc) != null && ((String)charSequence).equals(SC_BAICa))) {
            if (this.isPinPukCommand() || (charSequence = this.mSc) != null && (((String)charSequence).equals(SC_PWD) || this.mSc.equals(SC_CLIP) || this.mSc.equals(SC_CLIR))) return false;
            if (this.mPoundString == null) return false;
            return true;
        }
        try {
            int n = ImsPhoneMmiCode.siToServiceClass(this.mSib);
            return n == 0 || n == 1 || n == 80;
        }
        catch (RuntimeException runtimeException) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Invalid service class ");
            ((StringBuilder)charSequence).append(runtimeException);
            Rlog.d((String)LOG_TAG, (String)((StringBuilder)charSequence).toString());
        }
        return false;
    }

    @UnsupportedAppUsage
    boolean isTemporaryModeCLIR() {
        String string = this.mSc;
        boolean bl = string != null && string.equals(SC_CLIR) && this.mDialingNumber != null && (this.isActivate() || this.isDeactivate());
        return bl;
    }

    @Override
    public boolean isUssdRequest() {
        return this.mIsUssdRequest;
    }

    void onUssdFinished(String charSequence, boolean bl) {
        if (this.mState == MmiCode.State.PENDING) {
            if (TextUtils.isEmpty((CharSequence)charSequence)) {
                this.mMessage = this.mContext.getText(17040443);
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("onUssdFinished: no message; using: ");
                ((StringBuilder)charSequence).append((Object)this.mMessage);
                Rlog.v((String)LOG_TAG, (String)((StringBuilder)charSequence).toString());
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("onUssdFinished: message: ");
                stringBuilder.append((String)charSequence);
                Rlog.v((String)LOG_TAG, (String)stringBuilder.toString());
                this.mMessage = charSequence;
            }
            this.mIsUssdRequest = bl;
            if (!bl) {
                this.mState = MmiCode.State.COMPLETE;
            }
            this.mPhone.onMMIDone(this);
        }
    }

    void onUssdFinishedError() {
        if (this.mState == MmiCode.State.PENDING) {
            this.mState = MmiCode.State.FAILED;
            this.mMessage = this.mContext.getText(17040444);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onUssdFinishedError: mmi=");
            stringBuilder.append(this);
            Rlog.d((String)LOG_TAG, (String)stringBuilder.toString());
            this.mPhone.onMMIDone(this);
        }
    }

    void parseSsData(ImsSsData arrimsCallForwardInfo) {
        block17 : {
            Object object;
            boolean bl;
            Object object2;
            block15 : {
                block16 : {
                    object = arrimsCallForwardInfo.getResult() != 0 ? new ImsException(null, arrimsCallForwardInfo.getResult()) : null;
                    this.mSc = this.getScStringFromScType(arrimsCallForwardInfo.getServiceType());
                    this.mAction = this.getActionStringFromReqType(arrimsCallForwardInfo.getRequestType());
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("parseSsData msc = ");
                    ((StringBuilder)object2).append(this.mSc);
                    ((StringBuilder)object2).append(", action = ");
                    ((StringBuilder)object2).append(this.mAction);
                    ((StringBuilder)object2).append(", ex = ");
                    ((StringBuilder)object2).append(object);
                    Rlog.d((String)LOG_TAG, (String)((StringBuilder)object2).toString());
                    int n = arrimsCallForwardInfo.getRequestType();
                    bl = false;
                    if (n == 0 || n == 1) break block15;
                    if (n == 2) break block16;
                    if (n == 3 || n == 4) break block15;
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Invaid requestType in SSData : ");
                    ((StringBuilder)object).append(arrimsCallForwardInfo.getRequestType());
                    Rlog.e((String)LOG_TAG, (String)((StringBuilder)object).toString());
                    break block17;
                }
                if (arrimsCallForwardInfo.isTypeClir()) {
                    Rlog.d((String)LOG_TAG, (String)"CLIR INTERROGATION");
                    object2 = new Bundle();
                    object2.putIntArray(UT_BUNDLE_KEY_CLIR, arrimsCallForwardInfo.getSuppServiceInfoCompat());
                    this.onQueryClirComplete(new AsyncResult(null, object2, (Throwable)object));
                } else if (arrimsCallForwardInfo.isTypeCF()) {
                    Rlog.d((String)LOG_TAG, (String)"CALL FORWARD INTERROGATION");
                    object2 = arrimsCallForwardInfo.getCallForwardInfo();
                    arrimsCallForwardInfo = null;
                    if (object2 != null) {
                        arrimsCallForwardInfo = object2.toArray((T[])new ImsCallForwardInfo[object2.size()]);
                    }
                    this.onQueryCfComplete(new AsyncResult(null, (Object)this.mPhone.handleCfQueryResult(arrimsCallForwardInfo), (Throwable)object));
                } else if (arrimsCallForwardInfo.isTypeBarring()) {
                    this.onSuppSvcQueryComplete(new AsyncResult(null, (Object)arrimsCallForwardInfo.getSuppServiceInfoCompat(), (Throwable)object));
                } else if (!(arrimsCallForwardInfo.isTypeColr() || arrimsCallForwardInfo.isTypeClip() || arrimsCallForwardInfo.isTypeColp())) {
                    if (arrimsCallForwardInfo.isTypeIcb()) {
                        this.onIcbQueryComplete(new AsyncResult(null, (Object)arrimsCallForwardInfo.getSuppServiceInfo(), (Throwable)object));
                    } else {
                        this.onQueryComplete(new AsyncResult(null, (Object)arrimsCallForwardInfo.getSuppServiceInfoCompat(), (Throwable)object));
                    }
                } else {
                    arrimsCallForwardInfo = new ImsSsInfo.Builder(arrimsCallForwardInfo.getSuppServiceInfoCompat()[0]).build();
                    object2 = new Bundle();
                    object2.putParcelable(UT_BUNDLE_KEY_SSINFO, (Parcelable)arrimsCallForwardInfo);
                    this.onSuppSvcQueryComplete(new AsyncResult(null, object2, (Throwable)object));
                }
                break block17;
            }
            if (arrimsCallForwardInfo.getResult() == 0 && arrimsCallForwardInfo.isTypeUnConditional()) {
                if ((arrimsCallForwardInfo.getRequestType() == 0 || arrimsCallForwardInfo.getRequestType() == 3) && this.isServiceClassVoiceVideoOrNone(arrimsCallForwardInfo.getServiceClass())) {
                    bl = true;
                }
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("setCallForwardingFlag cffEnabled: ");
                ((StringBuilder)object2).append(bl);
                Rlog.d((String)LOG_TAG, (String)((StringBuilder)object2).toString());
                if (this.mIccRecords != null) {
                    Rlog.d((String)LOG_TAG, (String)"setVoiceCallForwardingFlag done from SS Info.");
                    this.mPhone.setVoiceCallForwardingFlag(1, bl, null);
                } else {
                    Rlog.e((String)LOG_TAG, (String)"setCallForwardingFlag aborted. sim records is null.");
                }
            }
            this.onSetComplete(null, new AsyncResult(null, (Object)arrimsCallForwardInfo.getCallForwardInfo(), (Throwable)object));
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    @Override
    public void processCode() throws CallStateException {
        try {
            boolean bl = this.isShortCode();
            if (bl) {
                Rlog.d((String)LOG_TAG, (String)"processCode: isShortCode");
                Serializable serializable = new StringBuilder();
                serializable.append("processCode: Sending short code '");
                serializable.append(this.mDialingNumber);
                serializable.append("' over CS pipe.");
                Rlog.d((String)LOG_TAG, (String)serializable.toString());
                serializable = new CallStateException("cs_fallback");
                throw serializable;
            }
            bl = ImsPhoneMmiCode.isServiceCodeCallForwarding(this.mSc);
            int n = 1;
            if (bl) {
                int n2;
                Rlog.d((String)LOG_TAG, (String)"processCode: is CF");
                Object object = this.mSia;
                int n3 = ImsPhoneMmiCode.scToCallForwardReason(this.mSc);
                int n4 = ImsPhoneMmiCode.siToServiceClass(this.mSib);
                int n5 = ImsPhoneMmiCode.siToTime(this.mSic);
                if (this.isInterrogate()) {
                    this.mPhone.getCallForwardingOption(n3, this.obtainMessage(1, (Object)this));
                    return;
                }
                if (this.isActivate()) {
                    if (ImsPhoneMmiCode.isEmptyOrNull((CharSequence)object)) {
                        n2 = 1;
                        this.mIsCallFwdReg = false;
                    } else {
                        n2 = 3;
                        this.mIsCallFwdReg = true;
                    }
                } else if (this.isDeactivate()) {
                    n2 = 0;
                } else if (this.isRegister()) {
                    n2 = 3;
                } else {
                    if (!this.isErasure()) {
                        object = new RuntimeException("invalid action");
                        throw object;
                    }
                    n2 = 4;
                }
                int n6 = n3 != 0 && n3 != 4 ? 0 : 1;
                if (n2 != 1 && n2 != 3) {
                    n = 0;
                }
                Rlog.d((String)LOG_TAG, (String)"processCode: is CF setCallForward");
                this.mPhone.setCallForwardingOption(n2, n3, (String)object, n4, n5, this.obtainMessage(4, n6, n, (Object)this));
                return;
            }
            bl = ImsPhoneMmiCode.isServiceCodeCallBarring(this.mSc);
            if (bl) {
                String string = this.mSia;
                Object object = ImsPhoneMmiCode.scToBarringFacility(this.mSc);
                int n7 = ImsPhoneMmiCode.siToServiceClass(this.mSib);
                if (this.isInterrogate()) {
                    this.mPhone.getCallBarring((String)object, this.obtainMessage(7, (Object)this), n7);
                    return;
                }
                if (!this.isActivate() && !this.isDeactivate()) {
                    object = new RuntimeException("Invalid or Unsupported MMI Code");
                    throw object;
                }
                this.mPhone.setCallBarring((String)object, this.isActivate(), string, this.obtainMessage(0, (Object)this), n7);
                return;
            }
            if (this.mSc != null && this.mSc.equals(SC_CLIR)) {
                bl = this.isActivate();
                if (bl) {
                    try {
                        this.mPhone.mCT.getUtInterface().updateCLIR(1, this.obtainMessage(0, (Object)this));
                        return;
                    }
                    catch (ImsException imsException) {
                        Rlog.d((String)LOG_TAG, (String)"processCode: Could not get UT handle for updateCLIR.");
                        return;
                    }
                }
                bl = this.isDeactivate();
                if (bl) {
                    try {
                        this.mPhone.mCT.getUtInterface().updateCLIR(2, this.obtainMessage(0, (Object)this));
                        return;
                    }
                    catch (ImsException imsException) {
                        Rlog.d((String)LOG_TAG, (String)"processCode: Could not get UT handle for updateCLIR.");
                        return;
                    }
                }
                bl = this.isInterrogate();
                if (!bl) {
                    RuntimeException runtimeException = new RuntimeException("Invalid or Unsupported MMI Code");
                    throw runtimeException;
                }
                try {
                    this.mPhone.mCT.getUtInterface().queryCLIR(this.obtainMessage(6, (Object)this));
                    return;
                }
                catch (ImsException imsException) {
                    Rlog.d((String)LOG_TAG, (String)"processCode: Could not get UT handle for queryCLIR.");
                    return;
                }
            }
            if (this.mSc != null && this.mSc.equals(SC_CLIP)) {
                bl = this.isInterrogate();
                if (bl) {
                    try {
                        this.mPhone.mCT.getUtInterface().queryCLIP(this.obtainMessage(7, (Object)this));
                        return;
                    }
                    catch (ImsException imsException) {
                        Rlog.d((String)LOG_TAG, (String)"processCode: Could not get UT handle for queryCLIP.");
                        return;
                    }
                }
                if (!this.isActivate() && !this.isDeactivate()) {
                    RuntimeException runtimeException = new RuntimeException("Invalid or Unsupported MMI Code");
                    throw runtimeException;
                }
                try {
                    this.mPhone.mCT.getUtInterface().updateCLIP(this.isActivate(), this.obtainMessage(0, (Object)this));
                    return;
                }
                catch (ImsException imsException) {
                    Rlog.d((String)LOG_TAG, (String)"processCode: Could not get UT handle for updateCLIP.");
                    return;
                }
            }
            if (this.mSc != null && this.mSc.equals(SC_COLP)) {
                bl = this.isInterrogate();
                if (bl) {
                    try {
                        this.mPhone.mCT.getUtInterface().queryCOLP(this.obtainMessage(7, (Object)this));
                        return;
                    }
                    catch (ImsException imsException) {
                        Rlog.d((String)LOG_TAG, (String)"processCode: Could not get UT handle for queryCOLP.");
                        return;
                    }
                }
                if (!this.isActivate() && !this.isDeactivate()) {
                    RuntimeException runtimeException = new RuntimeException("Invalid or Unsupported MMI Code");
                    throw runtimeException;
                }
                try {
                    this.mPhone.mCT.getUtInterface().updateCOLP(this.isActivate(), this.obtainMessage(0, (Object)this));
                    return;
                }
                catch (ImsException imsException) {
                    Rlog.d((String)LOG_TAG, (String)"processCode: Could not get UT handle for updateCOLP.");
                    return;
                }
            }
            if (this.mSc != null && this.mSc.equals(SC_COLR)) {
                bl = this.isActivate();
                if (bl) {
                    try {
                        this.mPhone.mCT.getUtInterface().updateCOLR(1, this.obtainMessage(0, (Object)this));
                        return;
                    }
                    catch (ImsException imsException) {
                        Rlog.d((String)LOG_TAG, (String)"processCode: Could not get UT handle for updateCOLR.");
                        return;
                    }
                }
                bl = this.isDeactivate();
                if (bl) {
                    try {
                        this.mPhone.mCT.getUtInterface().updateCOLR(0, this.obtainMessage(0, (Object)this));
                        return;
                    }
                    catch (ImsException imsException) {
                        Rlog.d((String)LOG_TAG, (String)"processCode: Could not get UT handle for updateCOLR.");
                        return;
                    }
                }
                bl = this.isInterrogate();
                if (!bl) {
                    RuntimeException runtimeException = new RuntimeException("Invalid or Unsupported MMI Code");
                    throw runtimeException;
                }
                try {
                    this.mPhone.mCT.getUtInterface().queryCOLR(this.obtainMessage(7, (Object)this));
                    return;
                }
                catch (ImsException imsException) {
                    Rlog.d((String)LOG_TAG, (String)"processCode: Could not get UT handle for queryCOLR.");
                    return;
                }
            }
            if (this.mSc != null && (bl = this.mSc.equals(SC_BS_MT))) {
                try {
                    if (this.isInterrogate()) {
                        this.mPhone.mCT.getUtInterface().queryCallBarring(10, this.obtainMessage(10, (Object)this));
                        return;
                    }
                    this.processIcbMmiCodeForUpdate();
                    return;
                }
                catch (ImsException imsException) {
                    Rlog.d((String)LOG_TAG, (String)"processCode: Could not get UT handle for ICB.");
                    return;
                }
            }
            if (this.mSc != null && (bl = this.mSc.equals(SC_BAICa))) {
                int n8 = 0;
                try {
                    if (this.isInterrogate()) {
                        this.mPhone.mCT.getUtInterface().queryCallBarring(6, this.obtainMessage(10, (Object)this));
                        return;
                    }
                    if (this.isActivate()) {
                        n8 = 1;
                    } else if (this.isDeactivate()) {
                        n8 = 0;
                    }
                    this.mPhone.mCT.getUtInterface().updateCallBarring(6, n8, this.obtainMessage(0, (Object)this), null);
                    return;
                }
                catch (ImsException imsException) {
                    Rlog.d((String)LOG_TAG, (String)"processCode: Could not get UT handle for ICBa.");
                    return;
                }
            }
            if (this.mSc != null && this.mSc.equals(SC_WAIT)) {
                int n9 = ImsPhoneMmiCode.siToServiceClass(this.mSib);
                if (!this.isActivate() && !this.isDeactivate()) {
                    if (this.isInterrogate()) {
                        this.mPhone.getCallWaiting(this.obtainMessage(3, (Object)this));
                        return;
                    }
                    RuntimeException runtimeException = new RuntimeException("Invalid or Unsupported MMI Code");
                    throw runtimeException;
                }
                this.mPhone.setCallWaiting(this.isActivate(), n9, this.obtainMessage(0, (Object)this));
                return;
            }
            if (this.mPoundString == null) {
                Rlog.d((String)LOG_TAG, (String)"processCode: invalid or unsupported MMI");
                RuntimeException runtimeException = new RuntimeException("Invalid or Unsupported MMI Code");
                throw runtimeException;
            }
            if (this.mPhone.getDefaultPhone().getServiceStateTracker().mSS.getState() != 0) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("processCode: CS is out of service, sending ussd string '");
                stringBuilder.append(Rlog.pii((String)LOG_TAG, (Object)this.mPoundString));
                stringBuilder.append("' over IMS pipe.");
                Rlog.i((String)LOG_TAG, (String)stringBuilder.toString());
                this.sendUssd(this.mPoundString);
                return;
            }
            Serializable serializable = new StringBuilder();
            serializable.append("processCode: Sending ussd string '");
            serializable.append(Rlog.pii((String)LOG_TAG, (Object)this.mPoundString));
            serializable.append("' over CS pipe.");
            Rlog.i((String)LOG_TAG, (String)serializable.toString());
            serializable = new CallStateException("cs_fallback");
            throw serializable;
        }
        catch (RuntimeException runtimeException) {
            this.mState = MmiCode.State.FAILED;
            this.mMessage = this.mContext.getText(17040444);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("processCode: RuntimeException = ");
            stringBuilder.append(runtimeException);
            Rlog.d((String)LOG_TAG, (String)stringBuilder.toString());
            this.mPhone.onMMIDone(this);
        }
    }

    public void processImsSsData(AsyncResult asyncResult) throws ImsException {
        try {
            this.parseSsData((ImsSsData)asyncResult.result);
            return;
        }
        catch (ClassCastException | NullPointerException runtimeException) {
            throw new ImsException("Exception in parsing SS Data", 0);
        }
    }

    void sendUssd(String string) {
        this.mIsPendingUSSD = true;
        this.mPhone.sendUSSD(string, this.obtainMessage(2, (Object)this));
    }

    public void setIsSsInfo(boolean bl) {
        this.mIsSsInfo = bl;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("ImsPhoneMmiCode {");
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("State=");
        stringBuilder2.append((Object)this.getState());
        stringBuilder.append(stringBuilder2.toString());
        if (this.mAction != null) {
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append(" action=");
            stringBuilder2.append(this.mAction);
            stringBuilder.append(stringBuilder2.toString());
        }
        if (this.mSc != null) {
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append(" sc=");
            stringBuilder2.append(this.mSc);
            stringBuilder.append(stringBuilder2.toString());
        }
        if (this.mSia != null) {
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append(" sia=");
            stringBuilder2.append(this.mSia);
            stringBuilder.append(stringBuilder2.toString());
        }
        if (this.mSib != null) {
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append(" sib=");
            stringBuilder2.append(this.mSib);
            stringBuilder.append(stringBuilder2.toString());
        }
        if (this.mSic != null) {
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append(" sic=");
            stringBuilder2.append(this.mSic);
            stringBuilder.append(stringBuilder2.toString());
        }
        if (this.mPoundString != null) {
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append(" poundString=");
            stringBuilder2.append(Rlog.pii((String)LOG_TAG, (Object)this.mPoundString));
            stringBuilder.append(stringBuilder2.toString());
        }
        if (this.mDialingNumber != null) {
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append(" dialingNumber=");
            stringBuilder2.append(Rlog.pii((String)LOG_TAG, (Object)this.mDialingNumber));
            stringBuilder.append(stringBuilder2.toString());
        }
        if (this.mPwd != null) {
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append(" pwd=");
            stringBuilder2.append(Rlog.pii((String)LOG_TAG, (Object)this.mPwd));
            stringBuilder.append(stringBuilder2.toString());
        }
        if (this.mCallbackReceiver != null) {
            stringBuilder.append(" hasReceiver");
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}

