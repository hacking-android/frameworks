/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.telephony.PhoneStateListener;
import com.android.internal.util.FunctionalUtils;

public final class _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$okPCYOx4UxYuvUHlM2iS425QGIg
implements FunctionalUtils.ThrowingRunnable {
    private final /* synthetic */ PhoneStateListener.IPhoneStateListenerStub f$0;
    private final /* synthetic */ PhoneStateListener f$1;
    private final /* synthetic */ boolean f$2;

    public /* synthetic */ _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$okPCYOx4UxYuvUHlM2iS425QGIg(PhoneStateListener.IPhoneStateListenerStub iPhoneStateListenerStub, PhoneStateListener phoneStateListener, boolean bl) {
        this.f$0 = iPhoneStateListenerStub;
        this.f$1 = phoneStateListener;
        this.f$2 = bl;
    }

    @Override
    public final void runOrThrow() {
        this.f$0.lambda$onMessageWaitingIndicatorChanged$5$PhoneStateListener$IPhoneStateListenerStub(this.f$1, this.f$2);
    }
}

