/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.database.CursorWindow;
import android.telephony.SmsManager;
import com.android.internal.util.FunctionalUtils;
import java.util.concurrent.Executor;

public final class _$$Lambda$SmsManager$9$rvckWwRKQKxMC1PhWEkHayc_gf8
implements FunctionalUtils.ThrowingRunnable {
    private final /* synthetic */ Executor f$0;
    private final /* synthetic */ SmsManager.FinancialSmsCallback f$1;
    private final /* synthetic */ CursorWindow f$2;

    public /* synthetic */ _$$Lambda$SmsManager$9$rvckWwRKQKxMC1PhWEkHayc_gf8(Executor executor, SmsManager.FinancialSmsCallback financialSmsCallback, CursorWindow cursorWindow) {
        this.f$0 = executor;
        this.f$1 = financialSmsCallback;
        this.f$2 = cursorWindow;
    }

    @Override
    public final void runOrThrow() {
        SmsManager.9.lambda$onGetSmsMessagesForFinancialApp$1(this.f$0, this.f$1, this.f$2);
    }
}

