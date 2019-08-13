/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.telephony.PhoneStateListener;
import java.util.Map;

public final class _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$jGj_qFMdpjbsKaUErqJEeOALEGo
implements Runnable {
    private final /* synthetic */ PhoneStateListener f$0;
    private final /* synthetic */ Map f$1;

    public /* synthetic */ _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$jGj_qFMdpjbsKaUErqJEeOALEGo(PhoneStateListener phoneStateListener, Map map) {
        this.f$0 = phoneStateListener;
        this.f$1 = map;
    }

    @Override
    public final void run() {
        PhoneStateListener.IPhoneStateListenerStub.lambda$onEmergencyNumberListChanged$44(this.f$0, this.f$1);
    }
}

