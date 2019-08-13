/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 *  android.os.AsyncResult
 *  android.os.Handler
 *  android.os.Message
 *  com.android.internal.telephony.IccCardConstants
 *  com.android.internal.telephony.IccCardConstants$State
 */
package com.android.internal.telephony;

import android.annotation.UnsupportedAppUsage;
import android.os.AsyncResult;
import android.os.Handler;
import android.os.Message;
import com.android.internal.telephony.IccCardConstants;
import com.android.internal.telephony.uicc.IccCardApplicationStatus;
import com.android.internal.telephony.uicc.IccRecords;

public class IccCard {
    private IccCardConstants.State mIccCardState = IccCardConstants.State.UNKNOWN;

    public IccCard() {
    }

    public IccCard(IccCardConstants.State state) {
        this.mIccCardState = state;
    }

    private void sendMessageWithCardAbsentException(Message message) {
        AsyncResult.forMessage((Message)message).exception = new RuntimeException("No valid IccCard");
        message.sendToTarget();
    }

    public void changeIccFdnPassword(String string, String string2, Message message) {
        this.sendMessageWithCardAbsentException(message);
    }

    public void changeIccLockPassword(String string, String string2, Message message) {
        this.sendMessageWithCardAbsentException(message);
    }

    public boolean getIccFdnAvailable() {
        return false;
    }

    public boolean getIccFdnEnabled() {
        return false;
    }

    public boolean getIccLockEnabled() {
        return false;
    }

    public boolean getIccPin2Blocked() {
        return false;
    }

    public boolean getIccPuk2Blocked() {
        return false;
    }

    public IccRecords getIccRecords() {
        return null;
    }

    public String getServiceProviderName() {
        return null;
    }

    @UnsupportedAppUsage
    public IccCardConstants.State getState() {
        return this.mIccCardState;
    }

    public boolean hasIccCard() {
        return false;
    }

    public boolean isApplicationOnIcc(IccCardApplicationStatus.AppType appType) {
        return false;
    }

    public boolean isEmptyProfile() {
        return false;
    }

    @UnsupportedAppUsage
    public void registerForNetworkLocked(Handler handler, int n, Object object) {
    }

    public void setIccFdnEnabled(boolean bl, String string, Message message) {
        this.sendMessageWithCardAbsentException(message);
    }

    public void setIccLockEnabled(boolean bl, String string, Message message) {
        this.sendMessageWithCardAbsentException(message);
    }

    @UnsupportedAppUsage
    public void supplyNetworkDepersonalization(String string, Message message) {
        this.sendMessageWithCardAbsentException(message);
    }

    @UnsupportedAppUsage
    public void supplyPin(String string, Message message) {
        this.sendMessageWithCardAbsentException(message);
    }

    public void supplyPin2(String string, Message message) {
        this.sendMessageWithCardAbsentException(message);
    }

    @UnsupportedAppUsage
    public void supplyPuk(String string, String string2, Message message) {
        this.sendMessageWithCardAbsentException(message);
    }

    public void supplyPuk2(String string, String string2, Message message) {
        this.sendMessageWithCardAbsentException(message);
    }

    public void unregisterForNetworkLocked(Handler handler) {
    }
}

