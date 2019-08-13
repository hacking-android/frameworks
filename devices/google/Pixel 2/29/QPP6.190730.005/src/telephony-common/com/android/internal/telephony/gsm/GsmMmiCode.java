/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.os.AsyncResult
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Message
 *  android.os.PersistableBundle
 *  android.os.ResultReceiver
 *  android.telephony.CarrierConfigManager
 *  android.telephony.PhoneNumberUtils
 *  android.telephony.Rlog
 *  android.telephony.ServiceState
 *  android.text.BidiFormatter
 *  android.text.SpannableStringBuilder
 *  android.text.TextDirectionHeuristic
 *  android.text.TextDirectionHeuristics
 *  android.text.TextUtils
 *  com.android.internal.util.ArrayUtils
 */
package com.android.internal.telephony.gsm;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncResult;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.PersistableBundle;
import android.os.ResultReceiver;
import android.telephony.CarrierConfigManager;
import android.telephony.PhoneNumberUtils;
import android.telephony.Rlog;
import android.telephony.ServiceState;
import android.text.BidiFormatter;
import android.text.SpannableStringBuilder;
import android.text.TextDirectionHeuristic;
import android.text.TextDirectionHeuristics;
import android.text.TextUtils;
import com.android.internal.telephony.CallForwardInfo;
import com.android.internal.telephony.CallStateException;
import com.android.internal.telephony.CommandException;
import com.android.internal.telephony.CommandsInterface;
import com.android.internal.telephony.GsmCdmaPhone;
import com.android.internal.telephony.MmiCode;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.gsm.SsData;
import com.android.internal.telephony.uicc.IccCardApplicationStatus;
import com.android.internal.telephony.uicc.IccRecords;
import com.android.internal.telephony.uicc.UiccCardApplication;
import com.android.internal.util.ArrayUtils;
import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class GsmMmiCode
extends Handler
implements MmiCode {
    static final String ACTION_ACTIVATE = "*";
    static final String ACTION_DEACTIVATE = "#";
    static final String ACTION_ERASURE = "##";
    static final String ACTION_INTERROGATE = "*#";
    static final String ACTION_REGISTER = "**";
    static final char END_OF_USSD_COMMAND = '#';
    static final int EVENT_GET_CLIR_COMPLETE = 2;
    static final int EVENT_QUERY_CF_COMPLETE = 3;
    static final int EVENT_QUERY_COMPLETE = 5;
    static final int EVENT_SET_CFF_COMPLETE = 6;
    static final int EVENT_SET_COMPLETE = 1;
    static final int EVENT_USSD_CANCEL_COMPLETE = 7;
    static final int EVENT_USSD_COMPLETE = 4;
    static final String LOG_TAG = "GsmMmiCode";
    static final int MATCH_GROUP_ACTION = 2;
    static final int MATCH_GROUP_DIALING_NUMBER = 12;
    static final int MATCH_GROUP_POUND_STRING = 1;
    static final int MATCH_GROUP_PWD_CONFIRM = 11;
    static final int MATCH_GROUP_SERVICE_CODE = 3;
    static final int MATCH_GROUP_SIA = 5;
    static final int MATCH_GROUP_SIB = 7;
    static final int MATCH_GROUP_SIC = 9;
    static final int MAX_LENGTH_SHORT_CODE = 2;
    static final String SC_BAIC = "35";
    static final String SC_BAICr = "351";
    static final String SC_BAOC = "33";
    static final String SC_BAOIC = "331";
    static final String SC_BAOICxH = "332";
    static final String SC_BA_ALL = "330";
    static final String SC_BA_MO = "333";
    static final String SC_BA_MT = "353";
    static final String SC_CFB = "67";
    static final String SC_CFNR = "62";
    static final String SC_CFNRy = "61";
    static final String SC_CFU = "21";
    static final String SC_CF_All = "002";
    static final String SC_CF_All_Conditional = "004";
    static final String SC_CLIP = "30";
    static final String SC_CLIR = "31";
    static final String SC_PIN = "04";
    static final String SC_PIN2 = "042";
    static final String SC_PUK = "05";
    static final String SC_PUK2 = "052";
    static final String SC_PWD = "03";
    static final String SC_WAIT = "43";
    static Pattern sPatternSuppService = Pattern.compile("((\\*|#|\\*#|\\*\\*|##)(\\d{2,3})(\\*([^*#]*)(\\*([^*#]*)(\\*([^*#]*)(\\*([^*#]*))?)?)?)?#)(.*)");
    private static String[] sTwoDigitNumberPattern;
    String mAction;
    private ResultReceiver mCallbackReceiver;
    Context mContext;
    public String mDialingNumber;
    IccRecords mIccRecords;
    private boolean mIsCallFwdReg;
    private boolean mIsPendingUSSD;
    private boolean mIsSsInfo = false;
    private boolean mIsUssdRequest;
    CharSequence mMessage;
    GsmCdmaPhone mPhone;
    String mPoundString;
    String mPwd;
    String mSc;
    String mSia;
    String mSib;
    String mSic;
    MmiCode.State mState = MmiCode.State.PENDING;
    UiccCardApplication mUiccApplication;

    public GsmMmiCode(GsmCdmaPhone gsmCdmaPhone, UiccCardApplication uiccCardApplication) {
        super(gsmCdmaPhone.getHandler().getLooper());
        this.mPhone = gsmCdmaPhone;
        this.mContext = gsmCdmaPhone.getContext();
        this.mUiccApplication = uiccCardApplication;
        if (uiccCardApplication != null) {
            this.mIccRecords = uiccCardApplication.getIccRecords();
        }
    }

    private static String convertCdmaMmiCodesTo3gppMmiCodes(String charSequence) {
        Object object = sPatternCdmaMmiCodeWhileRoaming.matcher(charSequence);
        String string = charSequence;
        if (((Matcher)object).matches()) {
            String string2 = GsmMmiCode.makeEmptyNull(((Matcher)object).group(1));
            String string3 = ((Matcher)object).group(2);
            object = GsmMmiCode.makeEmptyNull(((Matcher)object).group(3));
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

    private CharSequence createQueryCallBarringResultMessage(int n) {
        StringBuilder stringBuilder = new StringBuilder(this.mContext.getText(17040999));
        for (int i = 1; i <= 128; i <<= 1) {
            if ((i & n) == 0) continue;
            stringBuilder.append("\n");
            stringBuilder.append(this.serviceClassToCFString(i & n));
        }
        return stringBuilder;
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

    private String formatLtr(String string) {
        BidiFormatter bidiFormatter = BidiFormatter.getInstance();
        if (string != null) {
            string = bidiFormatter.unicodeWrap(string, TextDirectionHeuristics.LTR, true);
        }
        return string;
    }

    private String getActionStringFromReqType(SsData.RequestType requestType) {
        int n = 1.$SwitchMap$com$android$internal$telephony$gsm$SsData$RequestType[requestType.ordinal()];
        if (n != 1) {
            if (n != 2) {
                if (n != 3) {
                    if (n != 4) {
                        if (n != 5) {
                            return "";
                        }
                        return ACTION_INTERROGATE;
                    }
                    return ACTION_ERASURE;
                }
                return ACTION_REGISTER;
            }
            return ACTION_DEACTIVATE;
        }
        return ACTION_ACTIVATE;
    }

    private CharSequence getErrorMessage(AsyncResult object) {
        if (object.exception instanceof CommandException) {
            object = ((CommandException)object.exception).getCommandError();
            if (object == CommandException.Error.FDN_CHECK_FAILURE) {
                Rlog.i((String)LOG_TAG, (String)"FDN_CHECK_FAILURE");
                return this.mContext.getText(17040446);
            }
            if (object == CommandException.Error.USSD_MODIFIED_TO_DIAL) {
                Rlog.i((String)LOG_TAG, (String)"USSD_MODIFIED_TO_DIAL");
                return this.mContext.getText(17041099);
            }
            if (object == CommandException.Error.USSD_MODIFIED_TO_SS) {
                Rlog.i((String)LOG_TAG, (String)"USSD_MODIFIED_TO_SS");
                return this.mContext.getText(17041101);
            }
            if (object == CommandException.Error.USSD_MODIFIED_TO_USSD) {
                Rlog.i((String)LOG_TAG, (String)"USSD_MODIFIED_TO_USSD");
                return this.mContext.getText(17041102);
            }
            if (object == CommandException.Error.SS_MODIFIED_TO_DIAL) {
                Rlog.i((String)LOG_TAG, (String)"SS_MODIFIED_TO_DIAL");
                return this.mContext.getText(17041095);
            }
            if (object == CommandException.Error.SS_MODIFIED_TO_USSD) {
                Rlog.i((String)LOG_TAG, (String)"SS_MODIFIED_TO_USSD");
                return this.mContext.getText(17041098);
            }
            if (object == CommandException.Error.SS_MODIFIED_TO_SS) {
                Rlog.i((String)LOG_TAG, (String)"SS_MODIFIED_TO_SS");
                return this.mContext.getText(17041097);
            }
            if (object == CommandException.Error.OEM_ERROR_1) {
                Rlog.i((String)LOG_TAG, (String)"OEM_ERROR_1 USSD_MODIFIED_TO_DIAL_VIDEO");
                return this.mContext.getText(17041100);
            }
        }
        return this.mContext.getText(17040444);
    }

    private CharSequence getScString() {
        String string = this.mSc;
        if (string != null) {
            if (GsmMmiCode.isServiceCodeCallBarring(string)) {
                return this.mContext.getText(17039397);
            }
            if (GsmMmiCode.isServiceCodeCallForwarding(this.mSc)) {
                return this.mContext.getText(17039403);
            }
            if (this.mSc.equals(SC_CLIP)) {
                return this.mContext.getText(17039404);
            }
            if (this.mSc.equals(SC_CLIR)) {
                return this.mContext.getText(17039405);
            }
            if (this.mSc.equals(SC_PWD)) {
                return this.mContext.getText(17039420);
            }
            if (this.mSc.equals(SC_WAIT)) {
                return this.mContext.getText(17039411);
            }
            if (this.isPinPukCommand()) {
                return this.mContext.getText(17039419);
            }
        }
        return "";
    }

    private String getScStringFromScType(SsData.ServiceType serviceType) {
        switch (serviceType) {
            default: {
                return "";
            }
            case SS_INCOMING_BARRING: {
                return SC_BA_MT;
            }
            case SS_OUTGOING_BARRING: {
                return SC_BA_MO;
            }
            case SS_ALL_BARRING: {
                return SC_BA_ALL;
            }
            case SS_BAIC_ROAMING: {
                return SC_BAICr;
            }
            case SS_BAIC: {
                return SC_BAIC;
            }
            case SS_BAOIC_EXC_HOME: {
                return SC_BAOICxH;
            }
            case SS_BAOIC: {
                return SC_BAOIC;
            }
            case SS_BAOC: {
                return SC_BAOC;
            }
            case SS_WAIT: {
                return SC_WAIT;
            }
            case SS_CLIR: {
                return SC_CLIR;
            }
            case SS_CLIP: {
                return SC_CLIP;
            }
            case SS_CF_ALL_CONDITIONAL: {
                return SC_CF_All_Conditional;
            }
            case SS_CF_ALL: {
                return SC_CF_All;
            }
            case SS_CF_NOT_REACHABLE: {
                return SC_CFNR;
            }
            case SS_CF_NO_REPLY: {
                return SC_CFNRy;
            }
            case SS_CF_BUSY: {
                return SC_CFB;
            }
            case SS_CFU: 
        }
        return SC_CFU;
    }

    private void handlePasswordError(int n) {
        this.mState = MmiCode.State.FAILED;
        StringBuilder stringBuilder = new StringBuilder(this.getScString());
        stringBuilder.append("\n");
        stringBuilder.append(this.mContext.getText(n));
        this.mMessage = stringBuilder;
        this.mPhone.onMMIDone(this);
    }

    private static boolean isEmptyOrNull(CharSequence charSequence) {
        boolean bl = charSequence == null || charSequence.length() == 0;
        return bl;
    }

    private boolean isFacToDial() {
        Object[] arrobject = ((CarrierConfigManager)this.mPhone.getContext().getSystemService("carrier_config")).getConfigForSubId(this.mPhone.getSubId());
        if (arrobject != null && !ArrayUtils.isEmpty((Object[])(arrobject = arrobject.getStringArray("feature_access_codes_string_array")))) {
            int n = arrobject.length;
            for (int i = 0; i < n; ++i) {
                if (!((String)arrobject[i]).equals(this.mSc)) continue;
                return true;
            }
        }
        return false;
    }

    private boolean isServiceClassVoiceorNone(int n) {
        boolean bl = (n & 1) != 0 || n == 0;
        return bl;
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

    private static boolean isShortCode(String string, GsmCdmaPhone gsmCdmaPhone) {
        if (string == null) {
            return false;
        }
        if (string.length() == 0) {
            return false;
        }
        if (PhoneNumberUtils.isLocalEmergencyNumber((Context)gsmCdmaPhone.getContext(), (String)string)) {
            return false;
        }
        return GsmMmiCode.isShortCodeUSSD(string, gsmCdmaPhone);
    }

    private static boolean isShortCodeUSSD(String string, GsmCdmaPhone gsmCdmaPhone) {
        if (string != null && string.length() <= 2) {
            if (gsmCdmaPhone.isInCall()) {
                return true;
            }
            if (string.length() != 2 || string.charAt(0) != '1') {
                return true;
            }
        }
        return false;
    }

    private static boolean isTwoDigitShortCode(Context object, String string) {
        Rlog.d((String)LOG_TAG, (String)"isTwoDigitShortCode");
        if (string != null && string.length() <= 2) {
            if (sTwoDigitNumberPattern == null) {
                sTwoDigitNumberPattern = object.getResources().getStringArray(17236073);
            }
            for (String string2 : sTwoDigitNumberPattern) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Two Digit Number Pattern ");
                ((StringBuilder)object).append(string2);
                Rlog.d((String)LOG_TAG, (String)((StringBuilder)object).toString());
                if (!string.equals(string2)) continue;
                Rlog.d((String)LOG_TAG, (String)"Two Digit Number Pattern -true");
                return true;
            }
            Rlog.d((String)LOG_TAG, (String)"Two Digit Number Pattern -false");
            return false;
        }
        return false;
    }

    public static boolean isVoiceUnconditionalForwarding(int n, int n2) {
        boolean bl = !(n != 0 && n != 4 || (n2 & 1) == 0 && n2 != 0);
        return bl;
    }

    private CharSequence makeCFQueryResultMessage(CallForwardInfo callForwardInfo, int n) {
        int n2 = callForwardInfo.reason;
        boolean bl = false;
        n2 = n2 == 2 ? 1 : 0;
        CharSequence charSequence = callForwardInfo.status == 1 ? (n2 != 0 ? this.mContext.getText(17039648) : this.mContext.getText(17039647)) : (callForwardInfo.status == 0 && GsmMmiCode.isEmptyOrNull(callForwardInfo.number) ? this.mContext.getText(17039649) : (n2 != 0 ? this.mContext.getText(17039651) : this.mContext.getText(17039650)));
        CharSequence charSequence2 = this.serviceClassToCFString(callForwardInfo.serviceClass & n);
        String string = this.formatLtr(PhoneNumberUtils.stringFromStringAndTOA((String)callForwardInfo.number, (int)callForwardInfo.toa));
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

    public static GsmMmiCode newFromDialString(String string, GsmCdmaPhone gsmCdmaPhone, UiccCardApplication uiccCardApplication) {
        return GsmMmiCode.newFromDialString(string, gsmCdmaPhone, uiccCardApplication, null);
    }

    public static GsmMmiCode newFromDialString(String object, GsmCdmaPhone gsmCdmaPhone, UiccCardApplication uiccCardApplication, ResultReceiver resultReceiver) {
        GsmMmiCode gsmMmiCode = null;
        String string = object;
        if (gsmCdmaPhone.getServiceState().getVoiceRoaming()) {
            string = object;
            if (gsmCdmaPhone.supportsConversionOfCdmaCallerIdMmiCodesWhileRoaming()) {
                string = GsmMmiCode.convertCdmaMmiCodesTo3gppMmiCodes((String)object);
            }
        }
        if (((Matcher)(object = sPatternSuppService.matcher(string))).matches()) {
            gsmMmiCode = new GsmMmiCode(gsmCdmaPhone, uiccCardApplication);
            gsmMmiCode.mPoundString = GsmMmiCode.makeEmptyNull(((Matcher)object).group(1));
            gsmMmiCode.mAction = GsmMmiCode.makeEmptyNull(((Matcher)object).group(2));
            gsmMmiCode.mSc = GsmMmiCode.makeEmptyNull(((Matcher)object).group(3));
            gsmMmiCode.mSia = GsmMmiCode.makeEmptyNull(((Matcher)object).group(5));
            gsmMmiCode.mSib = GsmMmiCode.makeEmptyNull(((Matcher)object).group(7));
            gsmMmiCode.mSic = GsmMmiCode.makeEmptyNull(((Matcher)object).group(9));
            gsmMmiCode.mPwd = GsmMmiCode.makeEmptyNull(((Matcher)object).group(11));
            gsmMmiCode.mDialingNumber = GsmMmiCode.makeEmptyNull(((Matcher)object).group(12));
            object = gsmMmiCode.mDialingNumber;
            if (object != null && ((String)object).endsWith(ACTION_DEACTIVATE) && string.endsWith(ACTION_DEACTIVATE)) {
                object = new GsmMmiCode(gsmCdmaPhone, uiccCardApplication);
                ((GsmMmiCode)object).mPoundString = string;
            } else {
                object = gsmMmiCode;
                if (gsmMmiCode.isFacToDial()) {
                    object = null;
                }
            }
        } else if (string.endsWith(ACTION_DEACTIVATE)) {
            object = new GsmMmiCode(gsmCdmaPhone, uiccCardApplication);
            ((GsmMmiCode)object).mPoundString = string;
        } else if (GsmMmiCode.isTwoDigitShortCode(gsmCdmaPhone.getContext(), string)) {
            object = null;
        } else {
            object = gsmMmiCode;
            if (GsmMmiCode.isShortCode(string, gsmCdmaPhone)) {
                object = new GsmMmiCode(gsmCdmaPhone, uiccCardApplication);
                ((GsmMmiCode)object).mDialingNumber = string;
            }
        }
        if (object != null) {
            ((GsmMmiCode)object).mCallbackReceiver = resultReceiver;
        }
        return object;
    }

    public static GsmMmiCode newFromUssdUserInput(String string, GsmCdmaPhone handler, UiccCardApplication uiccCardApplication) {
        handler = new GsmMmiCode((GsmCdmaPhone)handler, uiccCardApplication);
        ((GsmMmiCode)handler).mMessage = string;
        ((GsmMmiCode)handler).mState = MmiCode.State.PENDING;
        ((GsmMmiCode)handler).mIsPendingUSSD = true;
        return handler;
    }

    public static GsmMmiCode newNetworkInitiatedUssd(String string, boolean bl, GsmCdmaPhone handler, UiccCardApplication uiccCardApplication) {
        handler = new GsmMmiCode((GsmCdmaPhone)handler, uiccCardApplication);
        ((GsmMmiCode)handler).mMessage = string;
        ((GsmMmiCode)handler).mIsUssdRequest = bl;
        if (bl) {
            ((GsmMmiCode)handler).mIsPendingUSSD = true;
            ((GsmMmiCode)handler).mState = MmiCode.State.PENDING;
        } else {
            ((GsmMmiCode)handler).mState = MmiCode.State.COMPLETE;
        }
        return handler;
    }

    private void onGetClirComplete(AsyncResult object) {
        StringBuilder stringBuilder = new StringBuilder(this.getScString());
        stringBuilder.append("\n");
        if (((AsyncResult)object).exception != null) {
            this.mState = MmiCode.State.FAILED;
            stringBuilder.append(this.getErrorMessage((AsyncResult)object));
        } else {
            object = (int[])((AsyncResult)object).result;
            Object object2 = object[1];
            if (object2 != 0) {
                if (object2 != 1) {
                    if (object2 != 2) {
                        if (object2 != 3) {
                            if (object2 == 4) {
                                object2 = object[0];
                                if (object2 != 1) {
                                    if (object2 != 2) {
                                        stringBuilder.append(this.mContext.getText(17039398));
                                    } else {
                                        stringBuilder.append(this.mContext.getText(17039398));
                                    }
                                } else {
                                    stringBuilder.append(this.mContext.getText(17039399));
                                }
                                this.mState = MmiCode.State.COMPLETE;
                            }
                        } else {
                            object2 = object[0];
                            if (object2 != 1) {
                                if (object2 != 2) {
                                    stringBuilder.append(this.mContext.getText(17039401));
                                } else {
                                    stringBuilder.append(this.mContext.getText(17039400));
                                }
                            } else {
                                stringBuilder.append(this.mContext.getText(17039401));
                            }
                            this.mState = MmiCode.State.COMPLETE;
                        }
                    } else {
                        stringBuilder.append(this.mContext.getText(17040444));
                        this.mState = MmiCode.State.FAILED;
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
        ((StringBuilder)object).append("onGetClirComplete: mmi=");
        ((StringBuilder)object).append(this);
        Rlog.d((String)LOG_TAG, (String)((StringBuilder)object).toString());
        this.mPhone.onMMIDone(this);
    }

    private void onQueryCfComplete(AsyncResult object) {
        StringBuilder stringBuilder = new StringBuilder(this.getScString());
        stringBuilder.append("\n");
        if (((AsyncResult)object).exception != null) {
            this.mState = MmiCode.State.FAILED;
            stringBuilder.append(this.getErrorMessage((AsyncResult)object));
        } else {
            object = (CallForwardInfo[])((AsyncResult)object).result;
            if (((CallForwardInfo[])object).length == 0) {
                stringBuilder.append(this.mContext.getText(17040997));
                if (this.mIccRecords != null) {
                    this.mPhone.setVoiceCallForwardingFlag(1, false, null);
                }
            } else {
                SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
                for (int i = 1; i <= 128; i <<= 1) {
                    int n = ((Object)object).length;
                    for (int j = 0; j < n; ++j) {
                        if ((((CallForwardInfo)object[j]).serviceClass & i) == 0) continue;
                        spannableStringBuilder.append(this.makeCFQueryResultMessage((CallForwardInfo)object[j], i));
                        spannableStringBuilder.append((CharSequence)"\n");
                    }
                }
                stringBuilder.append((CharSequence)spannableStringBuilder);
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

    private void onQueryComplete(AsyncResult object) {
        StringBuilder stringBuilder = new StringBuilder(this.getScString());
        stringBuilder.append("\n");
        if (((AsyncResult)object).exception != null) {
            this.mState = MmiCode.State.FAILED;
            stringBuilder.append(this.getErrorMessage((AsyncResult)object));
        } else {
            object = (int[])((AsyncResult)object).result;
            if (((int[])object).length != 0) {
                if (object[0] == 0) {
                    stringBuilder.append(this.mContext.getText(17040997));
                } else if (this.mSc.equals(SC_WAIT)) {
                    stringBuilder.append(this.createQueryCallWaitingResultMessage((int)object[1]));
                } else if (GsmMmiCode.isServiceCodeCallBarring(this.mSc)) {
                    stringBuilder.append(this.createQueryCallBarringResultMessage((int)object[0]));
                } else if (object[0] == true) {
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
        ((StringBuilder)object).append("onQueryComplete: mmi=");
        ((StringBuilder)object).append(this);
        Rlog.d((String)LOG_TAG, (String)((StringBuilder)object).toString());
        this.mPhone.onMMIDone(this);
    }

    private void onSetComplete(Message object, AsyncResult asyncResult) {
        StringBuilder stringBuilder = new StringBuilder(this.getScString());
        stringBuilder.append("\n");
        if (asyncResult.exception != null) {
            this.mState = MmiCode.State.FAILED;
            if (asyncResult.exception instanceof CommandException) {
                CommandException.Error error = ((CommandException)asyncResult.exception).getCommandError();
                if (error == CommandException.Error.PASSWORD_INCORRECT) {
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
                } else if (error == CommandException.Error.SIM_PUK2) {
                    stringBuilder.append(this.mContext.getText(17039598));
                    stringBuilder.append("\n");
                    stringBuilder.append(this.mContext.getText(17040453));
                } else if (error == CommandException.Error.REQUEST_NOT_SUPPORTED) {
                    if (this.mSc.equals(SC_PIN)) {
                        stringBuilder.append(this.mContext.getText(17039897));
                    }
                } else if (error == CommandException.Error.FDN_CHECK_FAILURE) {
                    Rlog.i((String)LOG_TAG, (String)"FDN_CHECK_FAILURE");
                    stringBuilder.append(this.mContext.getText(17040446));
                } else if (error == CommandException.Error.MODEM_ERR) {
                    if (GsmMmiCode.isServiceCodeCallForwarding(this.mSc) && this.mPhone.getServiceState().getVoiceRoaming() && !this.mPhone.supports3gppCallForwardingWhileRoaming()) {
                        stringBuilder.append(this.mContext.getText(17040445));
                    } else {
                        stringBuilder.append(this.getErrorMessage(asyncResult));
                    }
                } else {
                    stringBuilder.append(this.getErrorMessage(asyncResult));
                }
            } else {
                stringBuilder.append(this.mContext.getText(17040444));
            }
        } else if (this.isActivate()) {
            this.mState = MmiCode.State.COMPLETE;
            if (this.mIsCallFwdReg) {
                stringBuilder.append(this.mContext.getText(17041002));
            } else {
                stringBuilder.append(this.mContext.getText(17040998));
            }
            if (this.mSc.equals(SC_CLIR)) {
                this.mPhone.saveClirSetting(1);
            }
        } else if (this.isDeactivate()) {
            this.mState = MmiCode.State.COMPLETE;
            stringBuilder.append(this.mContext.getText(17040997));
            if (this.mSc.equals(SC_CLIR)) {
                this.mPhone.saveClirSetting(2);
            }
        } else if (this.isRegister()) {
            this.mState = MmiCode.State.COMPLETE;
            stringBuilder.append(this.mContext.getText(17041002));
        } else if (this.isErasure()) {
            this.mState = MmiCode.State.COMPLETE;
            stringBuilder.append(this.mContext.getText(17041000));
        } else {
            this.mState = MmiCode.State.FAILED;
            stringBuilder.append(this.mContext.getText(17040444));
        }
        this.mMessage = stringBuilder;
        object = new StringBuilder();
        ((StringBuilder)object).append("onSetComplete mmi=");
        ((StringBuilder)object).append(this);
        Rlog.d((String)LOG_TAG, (String)((StringBuilder)object).toString());
        this.mPhone.onMMIDone(this);
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

    @Override
    public void cancel() {
        if (this.mState != MmiCode.State.COMPLETE && this.mState != MmiCode.State.FAILED) {
            this.mState = MmiCode.State.CANCELLED;
            if (this.mIsPendingUSSD) {
                this.mPhone.mCi.cancelPendingUssd(this.obtainMessage(7, (Object)this));
            } else {
                this.mPhone.onMMIDone(this);
            }
            return;
        }
    }

    public int getCLIRMode() {
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
        switch (message.what) {
            default: {
                break;
            }
            case 7: {
                this.mPhone.onMMIDone(this);
                break;
            }
            case 6: {
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
            case 5: {
                this.onQueryComplete((AsyncResult)message.obj);
                break;
            }
            case 4: {
                message = (AsyncResult)message.obj;
                if (message.exception == null) break;
                this.mState = MmiCode.State.FAILED;
                this.mMessage = this.getErrorMessage((AsyncResult)message);
                this.mPhone.onMMIDone(this);
                break;
            }
            case 3: {
                this.onQueryCfComplete((AsyncResult)message.obj);
                break;
            }
            case 2: {
                this.onGetClirComplete((AsyncResult)message.obj);
                break;
            }
            case 1: {
                this.onSetComplete(message, (AsyncResult)message.obj);
            }
        }
    }

    boolean isActivate() {
        String string = this.mAction;
        boolean bl = string != null && string.equals(ACTION_ACTIVATE);
        return bl;
    }

    @Override
    public boolean isCancelable() {
        return this.mIsPendingUSSD;
    }

    boolean isDeactivate() {
        String string = this.mAction;
        boolean bl = string != null && string.equals(ACTION_DEACTIVATE);
        return bl;
    }

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

    public boolean isTemporaryModeCLIR() {
        String string = this.mSc;
        boolean bl = string != null && string.equals(SC_CLIR) && this.mDialingNumber != null && (this.isActivate() || this.isDeactivate());
        return bl;
    }

    @Override
    public boolean isUssdRequest() {
        return this.mIsUssdRequest;
    }

    public void onUssdFinished(String string, boolean bl) {
        if (this.mState == MmiCode.State.PENDING) {
            if (TextUtils.isEmpty((CharSequence)string)) {
                Rlog.d((String)LOG_TAG, (String)"onUssdFinished: no network provided message; using default.");
                this.mMessage = this.mContext.getText(17040443);
            } else {
                this.mMessage = string;
            }
            this.mIsUssdRequest = bl;
            if (!bl) {
                this.mState = MmiCode.State.COMPLETE;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onUssdFinished: ussdMessage=");
            stringBuilder.append(string);
            Rlog.d((String)LOG_TAG, (String)stringBuilder.toString());
            this.mPhone.onMMIDone(this);
        }
    }

    public void onUssdFinishedError() {
        if (this.mState == MmiCode.State.PENDING) {
            this.mState = MmiCode.State.FAILED;
            this.mMessage = this.mContext.getText(17040444);
            Rlog.d((String)LOG_TAG, (String)"onUssdFinishedError");
            this.mPhone.onMMIDone(this);
        }
    }

    public void onUssdRelease() {
        if (this.mState == MmiCode.State.PENDING) {
            this.mState = MmiCode.State.COMPLETE;
            this.mMessage = null;
            Rlog.d((String)LOG_TAG, (String)"onUssdRelease");
            this.mPhone.onMMIDone(this);
        }
    }

    void parseSsData(SsData ssData) {
        Serializable serializable = CommandException.fromRilErrno(ssData.result);
        this.mSc = this.getScStringFromScType(ssData.serviceType);
        this.mAction = this.getActionStringFromReqType(ssData.requestType);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("parseSsData msc = ");
        stringBuilder.append(this.mSc);
        stringBuilder.append(", action = ");
        stringBuilder.append(this.mAction);
        stringBuilder.append(", ex = ");
        stringBuilder.append(serializable);
        Rlog.d((String)LOG_TAG, (String)stringBuilder.toString());
        int n = 1.$SwitchMap$com$android$internal$telephony$gsm$SsData$RequestType[ssData.requestType.ordinal()];
        if (n != 1 && n != 2 && n != 3 && n != 4) {
            if (n != 5) {
                serializable = new StringBuilder();
                ((StringBuilder)serializable).append("Invaid requestType in SSData : ");
                ((StringBuilder)serializable).append((Object)ssData.requestType);
                Rlog.e((String)LOG_TAG, (String)((StringBuilder)serializable).toString());
            } else if (ssData.serviceType.isTypeClir()) {
                Rlog.d((String)LOG_TAG, (String)"CLIR INTERROGATION");
                this.onGetClirComplete(new AsyncResult(null, (Object)ssData.ssInfo, (Throwable)serializable));
            } else if (ssData.serviceType.isTypeCF()) {
                Rlog.d((String)LOG_TAG, (String)"CALL FORWARD INTERROGATION");
                this.onQueryCfComplete(new AsyncResult(null, (Object)ssData.cfInfo, (Throwable)serializable));
            } else {
                this.onQueryComplete(new AsyncResult(null, (Object)ssData.ssInfo, (Throwable)serializable));
            }
        } else {
            if (ssData.result == 0 && ssData.serviceType.isTypeUnConditional()) {
                boolean bl = (ssData.requestType == SsData.RequestType.SS_ACTIVATION || ssData.requestType == SsData.RequestType.SS_REGISTRATION) && this.isServiceClassVoiceorNone(ssData.serviceClass);
                stringBuilder = new StringBuilder();
                stringBuilder.append("setVoiceCallForwardingFlag cffEnabled: ");
                stringBuilder.append(bl);
                Rlog.d((String)LOG_TAG, (String)stringBuilder.toString());
                if (this.mIccRecords != null) {
                    this.mPhone.setVoiceCallForwardingFlag(1, bl, null);
                    Rlog.d((String)LOG_TAG, (String)"setVoiceCallForwardingFlag done from SS Info.");
                } else {
                    Rlog.e((String)LOG_TAG, (String)"setVoiceCallForwardingFlag aborted. sim records is null.");
                }
            }
            this.onSetComplete(null, new AsyncResult(null, (Object)ssData.cfInfo, (Throwable)serializable));
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void processCode() throws CallStateException {
        try {
            if (this.isShortCode()) {
                Rlog.d((String)LOG_TAG, (String)"processCode: isShortCode");
                this.sendUssd(this.mDialingNumber);
                return;
            }
            Object object = this.mDialingNumber;
            if (object != null) {
                object = new RuntimeException("Invalid or Unsupported MMI Code");
                throw object;
            }
            if (this.mSc != null && this.mSc.equals(SC_CLIP)) {
                Rlog.d((String)LOG_TAG, (String)"processCode: is CLIP");
                if (this.isInterrogate()) {
                    this.mPhone.mCi.queryCLIP(this.obtainMessage(5, (Object)this));
                    return;
                }
                object = new RuntimeException("Invalid or Unsupported MMI Code");
                throw object;
            }
            object = this.mSc;
            int n = 1;
            if (object != null && this.mSc.equals(SC_CLIR)) {
                Rlog.d((String)LOG_TAG, (String)"processCode: is CLIR");
                if (this.isActivate()) {
                    this.mPhone.mCi.setCLIR(1, this.obtainMessage(1, (Object)this));
                    return;
                }
                if (this.isDeactivate()) {
                    this.mPhone.mCi.setCLIR(2, this.obtainMessage(1, (Object)this));
                    return;
                }
                if (this.isInterrogate()) {
                    this.mPhone.mCi.getCLIR(this.obtainMessage(2, (Object)this));
                    return;
                }
                object = new RuntimeException("Invalid or Unsupported MMI Code");
                throw object;
            }
            if (GsmMmiCode.isServiceCodeCallForwarding(this.mSc)) {
                int n2;
                Rlog.d((String)LOG_TAG, (String)"processCode: is CF");
                object = this.mSia;
                int n3 = GsmMmiCode.siToServiceClass(this.mSib);
                int n4 = GsmMmiCode.scToCallForwardReason(this.mSc);
                int n5 = GsmMmiCode.siToTime(this.mSic);
                if (this.isInterrogate()) {
                    this.mPhone.mCi.queryCallForwardStatus(n4, n3, (String)object, this.obtainMessage(3, (Object)this));
                    return;
                }
                if (this.isActivate()) {
                    if (GsmMmiCode.isEmptyOrNull((CharSequence)object)) {
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
                int n6 = n2 != 1 && n2 != 3 ? 0 : 1;
                Rlog.d((String)LOG_TAG, (String)"processCode: is CF setCallForward");
                CommandsInterface commandsInterface = this.mPhone.mCi;
                if (!GsmMmiCode.isVoiceUnconditionalForwarding(n4, n3)) {
                    n = 0;
                }
                commandsInterface.setCallForward(n2, n4, n3, (String)object, n5, this.obtainMessage(6, n, n6, (Object)this));
                return;
            }
            if (GsmMmiCode.isServiceCodeCallBarring(this.mSc)) {
                String string = this.mSia;
                int n7 = GsmMmiCode.siToServiceClass(this.mSib);
                object = GsmMmiCode.scToBarringFacility(this.mSc);
                if (this.isInterrogate()) {
                    this.mPhone.mCi.queryFacilityLock((String)object, string, n7, this.obtainMessage(5, (Object)this));
                    return;
                }
                if (!this.isActivate() && !this.isDeactivate()) {
                    object = new RuntimeException("Invalid or Unsupported MMI Code");
                    throw object;
                }
                this.mPhone.mCi.setFacilityLock((String)object, this.isActivate(), string, n7, this.obtainMessage(1, (Object)this));
                return;
            }
            if (this.mSc != null && this.mSc.equals(SC_PWD)) {
                String string = this.mSib;
                String string2 = this.mSic;
                if (!this.isActivate() && !this.isRegister()) {
                    object = new RuntimeException("Invalid or Unsupported MMI Code");
                    throw object;
                }
                this.mAction = ACTION_REGISTER;
                object = this.mSia == null ? "AB" : GsmMmiCode.scToBarringFacility(this.mSia);
                if (string2.equals(this.mPwd)) {
                    this.mPhone.mCi.changeBarringPassword((String)object, string, string2, this.obtainMessage(1, (Object)this));
                    return;
                }
                this.handlePasswordError(17040537);
                return;
            }
            if (this.mSc != null && this.mSc.equals(SC_WAIT)) {
                int n8 = GsmMmiCode.siToServiceClass(this.mSia);
                if (!this.isActivate() && !this.isDeactivate()) {
                    if (this.isInterrogate()) {
                        this.mPhone.mCi.queryCallWaiting(n8, this.obtainMessage(5, (Object)this));
                        return;
                    }
                    object = new RuntimeException("Invalid or Unsupported MMI Code");
                    throw object;
                }
                this.mPhone.mCi.setCallWaiting(this.isActivate(), n8, this.obtainMessage(1, (Object)this));
                return;
            }
            if (this.isPinPukCommand()) {
                object = this.mSia;
                CharSequence charSequence = this.mSib;
                int n9 = ((String)charSequence).length();
                if (!this.isRegister()) {
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("Ivalid register/action=");
                    ((StringBuilder)charSequence).append(this.mAction);
                    object = new RuntimeException(((StringBuilder)charSequence).toString());
                    throw object;
                }
                if (!((String)charSequence).equals(this.mSic)) {
                    this.handlePasswordError(17040434);
                    return;
                }
                if (n9 >= 4 && n9 <= 8) {
                    if (this.mSc.equals(SC_PIN) && this.mUiccApplication != null && this.mUiccApplication.getState() == IccCardApplicationStatus.AppState.APPSTATE_PUK) {
                        this.handlePasswordError(17040452);
                        return;
                    }
                    if (this.mUiccApplication == null) {
                        object = new RuntimeException("No application mUiccApplicaiton is null");
                        throw object;
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("processCode: process mmi service code using UiccApp sc=");
                    stringBuilder.append(this.mSc);
                    Rlog.d((String)LOG_TAG, (String)stringBuilder.toString());
                    if (this.mSc.equals(SC_PIN)) {
                        this.mUiccApplication.changeIccLockPassword((String)object, (String)charSequence, this.obtainMessage(1, (Object)this));
                        return;
                    }
                    if (this.mSc.equals(SC_PIN2)) {
                        this.mUiccApplication.changeIccFdnPassword((String)object, (String)charSequence, this.obtainMessage(1, (Object)this));
                        return;
                    }
                    if (this.mSc.equals(SC_PUK)) {
                        this.mUiccApplication.supplyPuk((String)object, (String)charSequence, this.obtainMessage(1, (Object)this));
                        return;
                    }
                    if (this.mSc.equals(SC_PUK2)) {
                        this.mUiccApplication.supplyPuk2((String)object, (String)charSequence, this.obtainMessage(1, (Object)this));
                        return;
                    }
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("uicc unsupported service code=");
                    ((StringBuilder)charSequence).append(this.mSc);
                    object = new RuntimeException(((StringBuilder)charSequence).toString());
                    throw object;
                }
                this.handlePasswordError(17040140);
                return;
            }
            if (this.mPoundString != null) {
                this.sendUssd(this.mPoundString);
                return;
            }
            Rlog.d((String)LOG_TAG, (String)"processCode: Invalid or Unsupported MMI Code");
            object = new RuntimeException("Invalid or Unsupported MMI Code");
            throw object;
        }
        catch (RuntimeException runtimeException) {
            this.mState = MmiCode.State.FAILED;
            this.mMessage = this.mContext.getText(17040444);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("processCode: RuntimeException=");
            stringBuilder.append(runtimeException);
            Rlog.d((String)LOG_TAG, (String)stringBuilder.toString());
            this.mPhone.onMMIDone(this);
        }
    }

    public void processSsData(AsyncResult object) {
        Rlog.d((String)LOG_TAG, (String)"In processSsData");
        this.mIsSsInfo = true;
        try {
            this.parseSsData((SsData)((AsyncResult)object).result);
        }
        catch (NullPointerException nullPointerException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Null Pointer Exception in parsing SS Data : ");
            ((StringBuilder)object).append(nullPointerException);
            Rlog.e((String)LOG_TAG, (String)((StringBuilder)object).toString());
        }
        catch (ClassCastException classCastException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Class Cast Exception in parsing SS Data : ");
            stringBuilder.append(classCastException);
            Rlog.e((String)LOG_TAG, (String)stringBuilder.toString());
        }
    }

    public void sendUssd(String string) {
        this.mIsPendingUSSD = true;
        this.mPhone.mCi.sendUSSD(string, this.obtainMessage(4, (Object)this));
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("GsmMmiCode {");
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
            stringBuilder2.append(Rlog.pii((String)LOG_TAG, (Object)this.mSia));
            stringBuilder.append(stringBuilder2.toString());
        }
        if (this.mSib != null) {
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append(" sib=");
            stringBuilder2.append(Rlog.pii((String)LOG_TAG, (Object)this.mSib));
            stringBuilder.append(stringBuilder2.toString());
        }
        if (this.mSic != null) {
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append(" sic=");
            stringBuilder2.append(Rlog.pii((String)LOG_TAG, (Object)this.mSic));
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

