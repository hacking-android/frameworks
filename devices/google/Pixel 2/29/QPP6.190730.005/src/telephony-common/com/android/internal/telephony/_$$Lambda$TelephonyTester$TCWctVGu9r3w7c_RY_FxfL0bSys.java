/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony;

import com.android.internal.telephony.Connection;
import com.android.internal.telephony.TelephonyTester;
import java.util.function.Consumer;

public final class _$$Lambda$TelephonyTester$TCWctVGu9r3w7c_RY_FxfL0bSys
implements Consumer {
    private final /* synthetic */ String f$0;

    public /* synthetic */ _$$Lambda$TelephonyTester$TCWctVGu9r3w7c_RY_FxfL0bSys(String string) {
        this.f$0 = string;
    }

    public final void accept(Object object) {
        TelephonyTester.lambda$testChangeNumber$0(this.f$0, (Connection)object);
    }
}

