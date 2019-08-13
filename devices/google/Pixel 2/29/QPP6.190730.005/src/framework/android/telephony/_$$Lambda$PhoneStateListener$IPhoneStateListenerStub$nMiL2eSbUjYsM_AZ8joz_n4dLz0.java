/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.telephony.PhoneStateListener;
import java.util.List;

public final class _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$nMiL2eSbUjYsM_AZ8joz_n4dLz0
implements Runnable {
    private final /* synthetic */ PhoneStateListener f$0;
    private final /* synthetic */ List f$1;

    public /* synthetic */ _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$nMiL2eSbUjYsM_AZ8joz_n4dLz0(PhoneStateListener phoneStateListener, List list) {
        this.f$0 = phoneStateListener;
        this.f$1 = list;
    }

    @Override
    public final void run() {
        PhoneStateListener.IPhoneStateListenerStub.lambda$onPhysicalChannelConfigurationChanged$42(this.f$0, this.f$1);
    }
}

