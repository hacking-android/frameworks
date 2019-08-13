/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio;

import android.hardware.radio.ProgramList;
import android.hardware.radio.ProgramSelector;

public final class _$$Lambda$ProgramList$1$a_xWqo5pESOZhcJIWvpiCd2AXmY
implements Runnable {
    private final /* synthetic */ ProgramList.ListCallback f$0;
    private final /* synthetic */ ProgramSelector.Identifier f$1;

    public /* synthetic */ _$$Lambda$ProgramList$1$a_xWqo5pESOZhcJIWvpiCd2AXmY(ProgramList.ListCallback listCallback, ProgramSelector.Identifier identifier) {
        this.f$0 = listCallback;
        this.f$1 = identifier;
    }

    @Override
    public final void run() {
        ProgramList.1.lambda$onItemRemoved$1(this.f$0, this.f$1);
    }
}

