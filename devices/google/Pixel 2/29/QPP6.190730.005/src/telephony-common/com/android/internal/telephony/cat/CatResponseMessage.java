/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 */
package com.android.internal.telephony.cat;

import android.annotation.UnsupportedAppUsage;
import com.android.internal.telephony.cat.CatCmdMessage;
import com.android.internal.telephony.cat.CommandDetails;
import com.android.internal.telephony.cat.ResultCode;

public class CatResponseMessage {
    byte[] mAddedInfo = null;
    int mAdditionalInfo = 0;
    CommandDetails mCmdDet = null;
    int mEventValue = -1;
    boolean mIncludeAdditionalInfo = false;
    ResultCode mResCode = ResultCode.OK;
    boolean mUsersConfirm = false;
    String mUsersInput = null;
    int mUsersMenuSelection = 0;
    boolean mUsersYesNoSelection = false;

    public CatResponseMessage(CatCmdMessage catCmdMessage) {
        this.mCmdDet = catCmdMessage.mCmdDet;
    }

    CommandDetails getCmdDetails() {
        return this.mCmdDet;
    }

    public void setAdditionalInfo(int n) {
        this.mIncludeAdditionalInfo = true;
        this.mAdditionalInfo = n;
    }

    public void setConfirmation(boolean bl) {
        this.mUsersConfirm = bl;
    }

    @UnsupportedAppUsage
    public void setEventDownload(int n, byte[] arrby) {
        this.mEventValue = n;
        this.mAddedInfo = arrby;
    }

    public void setInput(String string) {
        this.mUsersInput = string;
    }

    public void setMenuSelection(int n) {
        this.mUsersMenuSelection = n;
    }

    public void setResultCode(ResultCode resultCode) {
        this.mResCode = resultCode;
    }

    public void setYesNo(boolean bl) {
        this.mUsersYesNoSelection = bl;
    }
}

