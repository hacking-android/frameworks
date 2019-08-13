/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio;

import android.hardware.radio.TunerCallbackAdapter;

public final class _$$Lambda$TunerCallbackAdapter$ZwPm3xxjeLvbP12KweyzqFJVnj4
implements Runnable {
    private final /* synthetic */ TunerCallbackAdapter f$0;
    private final /* synthetic */ boolean f$1;

    public /* synthetic */ _$$Lambda$TunerCallbackAdapter$ZwPm3xxjeLvbP12KweyzqFJVnj4(TunerCallbackAdapter tunerCallbackAdapter, boolean bl) {
        this.f$0 = tunerCallbackAdapter;
        this.f$1 = bl;
    }

    @Override
    public final void run() {
        this.f$0.lambda$onEmergencyAnnouncement$8$TunerCallbackAdapter(this.f$1);
    }
}

