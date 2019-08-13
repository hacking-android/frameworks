/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.telephony.PhoneStateListener;
import com.android.internal.util.FunctionalUtils;
import java.util.Map;

public final class _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$d9DVwzLraeX80tegF_wEzf_k2FI
implements FunctionalUtils.ThrowingRunnable {
    private final /* synthetic */ PhoneStateListener.IPhoneStateListenerStub f$0;
    private final /* synthetic */ PhoneStateListener f$1;
    private final /* synthetic */ Map f$2;

    public /* synthetic */ _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$d9DVwzLraeX80tegF_wEzf_k2FI(PhoneStateListener.IPhoneStateListenerStub iPhoneStateListenerStub, PhoneStateListener phoneStateListener, Map map) {
        this.f$0 = iPhoneStateListenerStub;
        this.f$1 = phoneStateListener;
        this.f$2 = map;
    }

    @Override
    public final void runOrThrow() {
        this.f$0.lambda$onEmergencyNumberListChanged$45$PhoneStateListener$IPhoneStateListenerStub(this.f$1, this.f$2);
    }
}

