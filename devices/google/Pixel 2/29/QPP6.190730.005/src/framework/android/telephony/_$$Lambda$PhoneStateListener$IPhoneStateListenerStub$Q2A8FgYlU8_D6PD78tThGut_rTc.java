/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.telephony.PhoneStateListener;
import java.util.List;

public final class _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$Q2A8FgYlU8_D6PD78tThGut_rTc
implements Runnable {
    private final /* synthetic */ PhoneStateListener f$0;
    private final /* synthetic */ List f$1;

    public /* synthetic */ _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$Q2A8FgYlU8_D6PD78tThGut_rTc(PhoneStateListener phoneStateListener, List list) {
        this.f$0 = phoneStateListener;
        this.f$1 = list;
    }

    @Override
    public final void run() {
        PhoneStateListener.IPhoneStateListenerStub.lambda$onCellInfoChanged$20(this.f$0, this.f$1);
    }
}

