/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.database.CursorWindow;
import android.telephony.SmsManager;

public final class _$$Lambda$SmsManager$9$Ma_xGOTcrGGV8QvZI0NSA6WUbKA
implements Runnable {
    private final /* synthetic */ SmsManager.FinancialSmsCallback f$0;
    private final /* synthetic */ CursorWindow f$1;

    public /* synthetic */ _$$Lambda$SmsManager$9$Ma_xGOTcrGGV8QvZI0NSA6WUbKA(SmsManager.FinancialSmsCallback financialSmsCallback, CursorWindow cursorWindow) {
        this.f$0 = financialSmsCallback;
        this.f$1 = cursorWindow;
    }

    @Override
    public final void run() {
        SmsManager.9.lambda$onGetSmsMessagesForFinancialApp$0(this.f$0, this.f$1);
    }
}

