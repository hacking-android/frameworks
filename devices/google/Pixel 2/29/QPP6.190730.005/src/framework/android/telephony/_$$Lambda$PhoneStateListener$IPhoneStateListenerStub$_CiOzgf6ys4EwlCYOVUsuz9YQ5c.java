/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.telephony.PhoneCapability;
import android.telephony.PhoneStateListener;
import com.android.internal.util.FunctionalUtils;

public final class _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$_CiOzgf6ys4EwlCYOVUsuz9YQ5c
implements FunctionalUtils.ThrowingRunnable {
    private final /* synthetic */ PhoneStateListener.IPhoneStateListenerStub f$0;
    private final /* synthetic */ PhoneStateListener f$1;
    private final /* synthetic */ PhoneCapability f$2;

    public /* synthetic */ _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$_CiOzgf6ys4EwlCYOVUsuz9YQ5c(PhoneStateListener.IPhoneStateListenerStub iPhoneStateListenerStub, PhoneStateListener phoneStateListener, PhoneCapability phoneCapability) {
        this.f$0 = iPhoneStateListenerStub;
        this.f$1 = phoneStateListener;
        this.f$2 = phoneCapability;
    }

    @Override
    public final void runOrThrow() {
        this.f$0.lambda$onPhoneCapabilityChanged$47$PhoneStateListener$IPhoneStateListenerStub(this.f$1, this.f$2);
    }
}

