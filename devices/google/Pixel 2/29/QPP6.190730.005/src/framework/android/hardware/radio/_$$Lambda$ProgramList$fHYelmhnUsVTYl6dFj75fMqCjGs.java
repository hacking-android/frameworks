/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio;

import android.hardware.radio.ProgramList;
import android.hardware.radio.ProgramSelector;
import java.util.function.Consumer;

public final class _$$Lambda$ProgramList$fHYelmhnUsVTYl6dFj75fMqCjGs
implements Consumer {
    private final /* synthetic */ ProgramSelector.Identifier f$0;

    public /* synthetic */ _$$Lambda$ProgramList$fHYelmhnUsVTYl6dFj75fMqCjGs(ProgramSelector.Identifier identifier) {
        this.f$0 = identifier;
    }

    public final void accept(Object object) {
        ProgramList.lambda$removeLocked$6(this.f$0, (ProgramList.ListCallback)object);
    }
}

