/*
 * Decompiled with CFR 0.145.
 */
package com.android.ims;

import android.os.Handler;
import android.os.Message;

public interface ImsUtInterface {
    public static final int ACTION_ACTIVATION = 1;
    public static final int ACTION_DEACTIVATION = 0;
    public static final int ACTION_ERASURE = 4;
    public static final int ACTION_INTERROGATION = 5;
    public static final int ACTION_REGISTRATION = 3;
    public static final int CB_BAIC = 1;
    public static final int CB_BAOC = 2;
    public static final int CB_BA_ALL = 7;
    public static final int CB_BA_MO = 8;
    public static final int CB_BA_MT = 9;
    public static final int CB_BIC_ACR = 6;
    public static final int CB_BIC_WR = 5;
    public static final int CB_BOIC = 3;
    public static final int CB_BOIC_EXHC = 4;
    public static final int CB_BS_MT = 10;
    public static final int CDIV_CF_ALL = 4;
    public static final int CDIV_CF_ALL_CONDITIONAL = 5;
    public static final int CDIV_CF_BUSY = 1;
    public static final int CDIV_CF_NOT_LOGGED_IN = 6;
    public static final int CDIV_CF_NOT_REACHABLE = 3;
    public static final int CDIV_CF_NO_REPLY = 2;
    public static final int CDIV_CF_UNCONDITIONAL = 0;
    public static final int INVALID = -1;
    public static final int OIR_DEFAULT = 0;
    public static final int OIR_PRESENTATION_NOT_RESTRICTED = 2;
    public static final int OIR_PRESENTATION_RESTRICTED = 1;

    public void queryCLIP(Message var1);

    public void queryCLIR(Message var1);

    public void queryCOLP(Message var1);

    public void queryCOLR(Message var1);

    public void queryCallBarring(int var1, Message var2);

    public void queryCallBarring(int var1, Message var2, int var3);

    public void queryCallForward(int var1, String var2, Message var3);

    public void queryCallWaiting(Message var1);

    public void registerForSuppServiceIndication(Handler var1, int var2, Object var3);

    public void unregisterForSuppServiceIndication(Handler var1);

    public void updateCLIP(boolean var1, Message var2);

    public void updateCLIR(int var1, Message var2);

    public void updateCOLP(boolean var1, Message var2);

    public void updateCOLR(int var1, Message var2);

    public void updateCallBarring(int var1, int var2, Message var3, String[] var4);

    public void updateCallBarring(int var1, int var2, Message var3, String[] var4, int var5);

    public void updateCallForward(int var1, int var2, String var3, int var4, int var5, Message var6);

    public void updateCallWaiting(boolean var1, int var2, Message var3);
}

