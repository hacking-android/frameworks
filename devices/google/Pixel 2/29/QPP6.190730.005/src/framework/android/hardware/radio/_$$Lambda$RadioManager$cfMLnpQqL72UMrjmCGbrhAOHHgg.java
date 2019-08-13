/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.hardware.radio.-$
 *  android.hardware.radio.-$$Lambda
 *  android.hardware.radio.-$$Lambda$RadioManager
 *  android.hardware.radio.-$$Lambda$RadioManager$cfMLnpQqL72UMrjmCGbrhAOHHgg
 */
package android.hardware.radio;

import android.hardware.radio.-$;
import android.hardware.radio.RadioManager;
import java.util.concurrent.Executor;

public final class _$$Lambda$RadioManager$cfMLnpQqL72UMrjmCGbrhAOHHgg
implements Executor {
    public static final /* synthetic */ -$.Lambda.RadioManager.cfMLnpQqL72UMrjmCGbrhAOHHgg INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$RadioManager$cfMLnpQqL72UMrjmCGbrhAOHHgg();
    }

    private /* synthetic */ _$$Lambda$RadioManager$cfMLnpQqL72UMrjmCGbrhAOHHgg() {
    }

    @Override
    public final void execute(Runnable runnable) {
        RadioManager.lambda$addAnnouncementListener$0(runnable);
    }
}

