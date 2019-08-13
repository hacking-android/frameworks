/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony.imsphone;

import com.android.internal.telephony.imsphone.ImsPhoneConnection;
import com.android.internal.telephony.imsphone.ImsRttTextHandler;

public final class _$$Lambda$ImsPhoneConnection$gXYXXIQcibrbO2gQqP7d18avaBI
implements ImsRttTextHandler.NetworkWriter {
    private final /* synthetic */ ImsPhoneConnection f$0;

    public /* synthetic */ _$$Lambda$ImsPhoneConnection$gXYXXIQcibrbO2gQqP7d18avaBI(ImsPhoneConnection imsPhoneConnection) {
        this.f$0 = imsPhoneConnection;
    }

    @Override
    public final void write(String string) {
        this.f$0.lambda$createRttTextHandler$0$ImsPhoneConnection(string);
    }
}

