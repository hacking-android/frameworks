/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.telephony.SmsMessage
 */
package com.android.internal.telephony;

import android.telephony.SmsMessage;
import com.android.internal.telephony.ImsSmsDispatcher;
import com.android.internal.telephony.SmsDispatchersController;

public final class _$$Lambda$ImsSmsDispatcher$3$q7JFSZBuWsj_jBm5R51WxdJYNxc
implements SmsDispatchersController.SmsInjectionCallback {
    private final /* synthetic */ ImsSmsDispatcher.3 f$0;
    private final /* synthetic */ SmsMessage f$1;
    private final /* synthetic */ int f$2;

    public /* synthetic */ _$$Lambda$ImsSmsDispatcher$3$q7JFSZBuWsj_jBm5R51WxdJYNxc(ImsSmsDispatcher.3 var1_1, SmsMessage smsMessage, int n) {
        this.f$0 = var1_1;
        this.f$1 = smsMessage;
        this.f$2 = n;
    }

    @Override
    public final void onSmsInjectedResult(int n) {
        this.f$0.lambda$onSmsReceived$0$ImsSmsDispatcher$3(this.f$1, this.f$2, n);
    }
}

