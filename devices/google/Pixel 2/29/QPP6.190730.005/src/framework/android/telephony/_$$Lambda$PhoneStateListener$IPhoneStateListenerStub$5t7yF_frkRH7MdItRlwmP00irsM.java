/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.telephony.CallAttributes;
import android.telephony.PhoneStateListener;

public final class _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$5t7yF_frkRH7MdItRlwmP00irsM
implements Runnable {
    private final /* synthetic */ PhoneStateListener f$0;
    private final /* synthetic */ CallAttributes f$1;

    public /* synthetic */ _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$5t7yF_frkRH7MdItRlwmP00irsM(PhoneStateListener phoneStateListener, CallAttributes callAttributes) {
        this.f$0 = phoneStateListener;
        this.f$1 = callAttributes;
    }

    @Override
    public final void run() {
        PhoneStateListener.IPhoneStateListenerStub.lambda$onCallAttributesChanged$50(this.f$0, this.f$1);
    }
}

