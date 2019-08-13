/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.os.StrictMode;
import android.view.IWindowManager;
import java.util.ArrayList;

public final class _$$Lambda$StrictMode$AndroidBlockGuardPolicy$9nBulCQKaMajrWr41SB7f7YRT1I
implements Runnable {
    private final /* synthetic */ StrictMode.AndroidBlockGuardPolicy f$0;
    private final /* synthetic */ IWindowManager f$1;
    private final /* synthetic */ ArrayList f$2;

    public /* synthetic */ _$$Lambda$StrictMode$AndroidBlockGuardPolicy$9nBulCQKaMajrWr41SB7f7YRT1I(StrictMode.AndroidBlockGuardPolicy androidBlockGuardPolicy, IWindowManager iWindowManager, ArrayList arrayList) {
        this.f$0 = androidBlockGuardPolicy;
        this.f$1 = iWindowManager;
        this.f$2 = arrayList;
    }

    @Override
    public final void run() {
        this.f$0.lambda$handleViolationWithTimingAttempt$0$StrictMode$AndroidBlockGuardPolicy(this.f$1, this.f$2);
    }
}

