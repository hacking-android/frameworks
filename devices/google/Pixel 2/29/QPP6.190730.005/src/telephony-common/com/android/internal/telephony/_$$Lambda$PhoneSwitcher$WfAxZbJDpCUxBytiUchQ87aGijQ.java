/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony;

import com.android.internal.telephony.CellularNetworkValidator;
import com.android.internal.telephony.PhoneSwitcher;

public final class _$$Lambda$PhoneSwitcher$WfAxZbJDpCUxBytiUchQ87aGijQ
implements CellularNetworkValidator.ValidationCallback {
    private final /* synthetic */ PhoneSwitcher f$0;

    public /* synthetic */ _$$Lambda$PhoneSwitcher$WfAxZbJDpCUxBytiUchQ87aGijQ(PhoneSwitcher phoneSwitcher) {
        this.f$0 = phoneSwitcher;
    }

    @Override
    public final void onValidationResult(boolean bl, int n) {
        this.f$0.lambda$new$0$PhoneSwitcher(bl, n);
    }
}

