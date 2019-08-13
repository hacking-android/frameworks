/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.telephony.PhoneStateListener;
import com.android.internal.util.FunctionalUtils;

public final class _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$XyayAGWQZC2dNjwr697SfSGBBOc
implements FunctionalUtils.ThrowingRunnable {
    private final /* synthetic */ PhoneStateListener.IPhoneStateListenerStub f$0;
    private final /* synthetic */ PhoneStateListener f$1;
    private final /* synthetic */ int f$2;

    public /* synthetic */ _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$XyayAGWQZC2dNjwr697SfSGBBOc(PhoneStateListener.IPhoneStateListenerStub iPhoneStateListenerStub, PhoneStateListener phoneStateListener, int n) {
        this.f$0 = iPhoneStateListenerStub;
        this.f$1 = phoneStateListener;
        this.f$2 = n;
    }

    @Override
    public final void runOrThrow() {
        this.f$0.lambda$onDataActivity$15$PhoneStateListener$IPhoneStateListenerStub(this.f$1, this.f$2);
    }
}

