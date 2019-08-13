/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 *  android.os.ResultReceiver
 */
package com.android.internal.telephony;

import android.annotation.UnsupportedAppUsage;
import android.os.ResultReceiver;
import com.android.internal.telephony.CallStateException;
import com.android.internal.telephony.Phone;
import java.util.regex.Pattern;

public interface MmiCode {
    public static final int MATCH_GROUP_CDMA_MMI_CODE_NUMBER = 3;
    public static final int MATCH_GROUP_CDMA_MMI_CODE_NUMBER_PREFIX = 2;
    public static final int MATCH_GROUP_CDMA_MMI_CODE_SERVICE_CODE = 1;
    public static final Pattern sPatternCdmaMmiCodeWhileRoaming = Pattern.compile("\\*(\\d{2})(\\+{0,1})(\\d{0,})");

    public void cancel();

    public String getDialString();

    public CharSequence getMessage();

    @UnsupportedAppUsage
    public Phone getPhone();

    public State getState();

    public ResultReceiver getUssdCallbackReceiver();

    public boolean isCancelable();

    public boolean isPinPukCommand();

    public boolean isUssdRequest();

    public void processCode() throws CallStateException;

    public static enum State {
        PENDING,
        CANCELLED,
        COMPLETE,
        FAILED;
        
    }

}

