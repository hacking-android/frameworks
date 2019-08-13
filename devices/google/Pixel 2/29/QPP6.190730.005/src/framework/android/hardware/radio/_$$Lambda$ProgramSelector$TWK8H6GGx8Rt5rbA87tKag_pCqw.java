/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio;

import android.hardware.radio.ProgramSelector;
import java.util.function.Predicate;

public final class _$$Lambda$ProgramSelector$TWK8H6GGx8Rt5rbA87tKag_pCqw
implements Predicate {
    private final /* synthetic */ int f$0;

    public /* synthetic */ _$$Lambda$ProgramSelector$TWK8H6GGx8Rt5rbA87tKag_pCqw(int n) {
        this.f$0 = n;
    }

    public final boolean test(Object object) {
        return ProgramSelector.lambda$withSecondaryPreferred$1(this.f$0, (ProgramSelector.Identifier)object);
    }
}

