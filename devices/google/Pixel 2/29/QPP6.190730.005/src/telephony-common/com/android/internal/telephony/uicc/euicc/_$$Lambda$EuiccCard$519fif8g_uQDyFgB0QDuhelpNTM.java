/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony.uicc.euicc;

import com.android.internal.telephony.uicc.euicc.EuiccCard;
import com.android.internal.telephony.uicc.euicc.async.AsyncResultCallback;

public final class _$$Lambda$EuiccCard$519fif8g_uQDyFgB0QDuhelpNTM
implements EuiccCard.ApduExceptionHandler {
    private final /* synthetic */ AsyncResultCallback f$0;

    public /* synthetic */ _$$Lambda$EuiccCard$519fif8g_uQDyFgB0QDuhelpNTM(AsyncResultCallback asyncResultCallback) {
        this.f$0 = asyncResultCallback;
    }

    @Override
    public final void handleException(Throwable throwable) {
        EuiccCard.lambda$sendApdu$51(this.f$0, throwable);
    }
}

