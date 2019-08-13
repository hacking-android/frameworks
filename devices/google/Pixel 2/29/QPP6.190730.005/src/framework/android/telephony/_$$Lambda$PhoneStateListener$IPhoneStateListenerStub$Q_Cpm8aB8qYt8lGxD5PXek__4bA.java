/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.telephony.CallAttributes;
import android.telephony.PhoneStateListener;
import com.android.internal.util.FunctionalUtils;

public final class _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$Q_Cpm8aB8qYt8lGxD5PXek__4bA
implements FunctionalUtils.ThrowingRunnable {
    private final /* synthetic */ PhoneStateListener.IPhoneStateListenerStub f$0;
    private final /* synthetic */ PhoneStateListener f$1;
    private final /* synthetic */ CallAttributes f$2;

    public /* synthetic */ _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$Q_Cpm8aB8qYt8lGxD5PXek__4bA(PhoneStateListener.IPhoneStateListenerStub iPhoneStateListenerStub, PhoneStateListener phoneStateListener, CallAttributes callAttributes) {
        this.f$0 = iPhoneStateListenerStub;
        this.f$1 = phoneStateListener;
        this.f$2 = callAttributes;
    }

    @Override
    public final void runOrThrow() {
        this.f$0.lambda$onCallAttributesChanged$51$PhoneStateListener$IPhoneStateListenerStub(this.f$1, this.f$2);
    }
}

