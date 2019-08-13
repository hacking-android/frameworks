/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony.test;

public interface SimulatedRadioControl {
    public void pauseResponses();

    public void progressConnectingCallState();

    public void progressConnectingToActive();

    public void resumeResponses();

    public void setAutoProgressConnectingCall(boolean var1);

    public void setNextCallFailCause(int var1);

    public void setNextDialFailImmediately(boolean var1);

    public void shutdown();

    public void triggerHangupAll();

    public void triggerHangupBackground();

    public void triggerHangupForeground();

    public void triggerIncomingSMS(String var1);

    public void triggerIncomingUssd(String var1, String var2);

    public void triggerRing(String var1);

    public void triggerSsn(int var1, int var2);
}

