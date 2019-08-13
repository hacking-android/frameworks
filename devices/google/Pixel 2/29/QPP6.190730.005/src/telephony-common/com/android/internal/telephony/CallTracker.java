/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 *  android.content.Context
 *  android.os.AsyncResult
 *  android.os.Handler
 *  android.os.Message
 *  android.os.PersistableBundle
 *  android.os.SystemProperties
 *  android.telephony.CarrierConfigManager
 *  android.telephony.PhoneNumberUtils
 *  android.text.TextUtils
 *  com.android.internal.telephony.PhoneConstants
 *  com.android.internal.telephony.PhoneConstants$State
 */
package com.android.internal.telephony;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.os.AsyncResult;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.os.SystemProperties;
import android.telephony.CarrierConfigManager;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import com.android.internal.telephony.Call;
import com.android.internal.telephony.CommandException;
import com.android.internal.telephony.CommandsInterface;
import com.android.internal.telephony.Connection;
import com.android.internal.telephony.DriverCall;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.PhoneConstants;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;

public abstract class CallTracker
extends Handler {
    private static final boolean DBG_POLL = false;
    protected static final int EVENT_CALL_STATE_CHANGE = 2;
    protected static final int EVENT_CALL_WAITING_INFO_CDMA = 15;
    protected static final int EVENT_CONFERENCE_RESULT = 11;
    protected static final int EVENT_ECT_RESULT = 13;
    protected static final int EVENT_EXIT_ECM_RESPONSE_CDMA = 14;
    protected static final int EVENT_GET_LAST_CALL_FAIL_CAUSE = 5;
    protected static final int EVENT_OPERATION_COMPLETE = 4;
    protected static final int EVENT_POLL_CALLS_RESULT = 1;
    protected static final int EVENT_RADIO_AVAILABLE = 9;
    protected static final int EVENT_RADIO_NOT_AVAILABLE = 10;
    protected static final int EVENT_REPOLL_AFTER_DELAY = 3;
    protected static final int EVENT_SEPARATE_RESULT = 12;
    protected static final int EVENT_SWITCH_RESULT = 8;
    protected static final int EVENT_THREE_WAY_DIAL_BLANK_FLASH = 20;
    protected static final int EVENT_THREE_WAY_DIAL_L2_RESULT_CDMA = 16;
    static final int POLL_DELAY_MSEC = 250;
    private final int VALID_COMPARE_LENGTH;
    @UnsupportedAppUsage
    public CommandsInterface mCi;
    protected ArrayList<Connection> mHandoverConnections = new ArrayList();
    protected Message mLastRelevantPoll;
    @UnsupportedAppUsage
    protected boolean mNeedsPoll;
    @UnsupportedAppUsage
    protected boolean mNumberConverted = false;
    @UnsupportedAppUsage
    protected int mPendingOperations;

    public CallTracker() {
        this.VALID_COMPARE_LENGTH = 3;
    }

    private boolean checkNoOperationsPending() {
        boolean bl = this.mPendingOperations == 0;
        return bl;
    }

    private boolean compareGid1(Phone object, String charSequence) {
        object = object.getGroupIdLevel1();
        int n = ((String)charSequence).length();
        boolean bl = true;
        if (((String)charSequence).equals("")) {
            object = new StringBuilder();
            ((StringBuilder)object).append("compareGid1 serviceGid is empty, return ");
            ((StringBuilder)object).append(true);
            this.log(((StringBuilder)object).toString());
            return true;
        }
        if (object == null || ((String)object).length() < n || !((String)object).substring(0, n).equalsIgnoreCase((String)charSequence)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(" gid1 ");
            stringBuilder.append((String)object);
            stringBuilder.append(" serviceGid1 ");
            stringBuilder.append((String)charSequence);
            this.log(stringBuilder.toString());
            bl = false;
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("compareGid1 is ");
        object = bl ? "Same" : "Different";
        ((StringBuilder)charSequence).append((String)object);
        this.log(((StringBuilder)charSequence).toString());
        return bl;
    }

    protected String checkForTestEmergencyNumber(String string) {
        String[] arrstring = SystemProperties.get((String)"ril.test.emergencynumber");
        Object object = string;
        if (!TextUtils.isEmpty((CharSequence)arrstring)) {
            arrstring = arrstring.split(":");
            object = new StringBuilder();
            ((StringBuilder)object).append("checkForTestEmergencyNumber: values.length=");
            ((StringBuilder)object).append(arrstring.length);
            this.log(((StringBuilder)object).toString());
            object = string;
            if (arrstring.length == 2) {
                object = string;
                if (arrstring[0].equals(PhoneNumberUtils.stripSeparators((String)string))) {
                    object = this.mCi;
                    if (object != null) {
                        object.testingEmergencyCall();
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append("checkForTestEmergencyNumber: remap ");
                    ((StringBuilder)object).append(string);
                    ((StringBuilder)object).append(" to ");
                    ((StringBuilder)object).append(arrstring[1]);
                    this.log(((StringBuilder)object).toString());
                    object = arrstring[1];
                }
            }
        }
        return object;
    }

    public void cleanupCalls() {
    }

    protected String convertNumberIfNecessary(Phone object, String string) {
        if (string == null) {
            return string;
        }
        Object object2 = null;
        Object object3 = ((CarrierConfigManager)((Phone)object).getContext().getSystemService("carrier_config")).getConfigForSubId(((Phone)object).getSubId());
        if (object3 != null) {
            object2 = object3.getStringArray("dial_string_replace_string_array");
        }
        if (object2 == null) {
            this.log("convertNumberIfNecessary convertMaps is null");
            return string;
        }
        object3 = new StringBuilder();
        ((StringBuilder)object3).append("convertNumberIfNecessary Roaming convertMaps.length ");
        ((StringBuilder)object3).append(((String[])object2).length);
        ((StringBuilder)object3).append(" dialNumber.length() ");
        ((StringBuilder)object3).append(string.length());
        this.log(((StringBuilder)object3).toString());
        if (((String[])object2).length >= 1 && string.length() >= 3) {
            String string2 = "";
            int n = ((String[])object2).length;
            int n2 = 0;
            do {
                object3 = string2;
                if (n2 >= n) break;
                object3 = object2[n2];
                Object object4 = new StringBuilder();
                ((StringBuilder)object4).append("convertNumberIfNecessary: ");
                ((StringBuilder)object4).append((String)object3);
                this.log(((StringBuilder)object4).toString());
                object3 = ((String)object3).split(":");
                if (object3 != null && ((Object)object3).length > 1) {
                    object4 = object3[0];
                    object3 = object3[1];
                    if (!TextUtils.isEmpty((CharSequence)object4) && string.equals(object4)) {
                        if (TextUtils.isEmpty((CharSequence)object3) || !((String)object3).endsWith("MDN")) break;
                        object2 = object.getLine1Number();
                        object = string2;
                        if (!TextUtils.isEmpty((CharSequence)object2)) {
                            if (((String)object2).startsWith("+")) {
                                object = object2;
                            } else {
                                object = new StringBuilder();
                                ((StringBuilder)object).append(((String)object3).substring(0, ((String)object3).length() - 3));
                                ((StringBuilder)object).append((String)object2);
                                object = ((StringBuilder)object).toString();
                            }
                        }
                        object3 = object;
                        break;
                    }
                }
                ++n2;
            } while (true);
            if (!TextUtils.isEmpty((CharSequence)object3)) {
                this.log("convertNumberIfNecessary: convert service number");
                this.mNumberConverted = true;
                return object3;
            }
            return string;
        }
        return string;
    }

    public void dump(FileDescriptor object, PrintWriter printWriter, String[] arrstring) {
        printWriter.println("CallTracker:");
        object = new StringBuilder();
        ((StringBuilder)object).append(" mPendingOperations=");
        ((StringBuilder)object).append(this.mPendingOperations);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mNeedsPoll=");
        ((StringBuilder)object).append(this.mNeedsPoll);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mLastRelevantPoll=");
        ((StringBuilder)object).append((Object)this.mLastRelevantPoll);
        printWriter.println(((StringBuilder)object).toString());
    }

    protected Connection getHoConnection(DriverCall object) {
        StringBuilder stringBuilder;
        for (Connection connection : this.mHandoverConnections) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("getHoConnection - compare number: hoConn= ");
            stringBuilder.append(connection.toString());
            this.log(stringBuilder.toString());
            if (connection.getAddress() == null || !connection.getAddress().contains(((DriverCall)object).number)) continue;
            object = new StringBuilder();
            ((StringBuilder)object).append("getHoConnection: Handover connection match found = ");
            ((StringBuilder)object).append(connection.toString());
            this.log(((StringBuilder)object).toString());
            return connection;
        }
        for (Connection connection : this.mHandoverConnections) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("getHoConnection: compare state hoConn= ");
            stringBuilder.append(connection.toString());
            this.log(stringBuilder.toString());
            if (connection.getStateBeforeHandover() != Call.stateFromDCState(((DriverCall)object).state)) continue;
            object = new StringBuilder();
            ((StringBuilder)object).append("getHoConnection: Handover connection match found = ");
            ((StringBuilder)object).append(connection.toString());
            this.log(((StringBuilder)object).toString());
            return connection;
        }
        return null;
    }

    protected abstract Phone getPhone();

    @UnsupportedAppUsage
    public abstract PhoneConstants.State getState();

    public abstract void handleMessage(Message var1);

    protected abstract void handlePollCalls(AsyncResult var1);

    protected void handleRadioAvailable() {
        this.pollCallsWhenSafe();
    }

    protected boolean isCommandExceptionRadioNotAvailable(Throwable throwable) {
        boolean bl = throwable != null && throwable instanceof CommandException && ((CommandException)throwable).getCommandError() == CommandException.Error.RADIO_NOT_AVAILABLE;
        return bl;
    }

    @UnsupportedAppUsage
    protected abstract void log(String var1);

    protected void notifySrvccState(Call.SrvccState object, ArrayList<Connection> arrayList) {
        if (object == Call.SrvccState.STARTED && arrayList != null) {
            this.mHandoverConnections.addAll(arrayList);
        } else if (object != Call.SrvccState.COMPLETED) {
            this.mHandoverConnections.clear();
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("notifySrvccState: mHandoverConnections= ");
        ((StringBuilder)object).append(this.mHandoverConnections.toString());
        this.log(((StringBuilder)object).toString());
    }

    protected Message obtainNoPollCompleteMessage(int n) {
        ++this.mPendingOperations;
        this.mLastRelevantPoll = null;
        return this.obtainMessage(n);
    }

    protected void pollCallsAfterDelay() {
        Message message = this.obtainMessage();
        message.what = 3;
        this.sendMessageDelayed(message, 250L);
    }

    protected void pollCallsWhenSafe() {
        this.mNeedsPoll = true;
        if (this.checkNoOperationsPending()) {
            this.mLastRelevantPoll = this.obtainMessage(1);
            this.mCi.getCurrentCalls(this.mLastRelevantPoll);
        }
    }

    @UnsupportedAppUsage
    public abstract void registerForVoiceCallEnded(Handler var1, int var2, Object var3);

    public abstract void registerForVoiceCallStarted(Handler var1, int var2, Object var3);

    public abstract void unregisterForVoiceCallEnded(Handler var1);

    public abstract void unregisterForVoiceCallStarted(Handler var1);
}

