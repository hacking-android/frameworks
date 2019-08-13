/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.number;

import android.icu.impl.StandardPlural;
import android.icu.impl.number.Modifier;
import android.icu.impl.number.ModifierStore;

public class AdoptingModifierStore
implements ModifierStore {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    boolean frozen;
    final Modifier[] mods;
    private final Modifier negative;
    private final Modifier positive;
    private final Modifier zero;

    public AdoptingModifierStore() {
        this.positive = null;
        this.zero = null;
        this.negative = null;
        this.mods = new Modifier[StandardPlural.COUNT * 3];
        this.frozen = false;
    }

    public AdoptingModifierStore(Modifier modifier, Modifier modifier2, Modifier modifier3) {
        this.positive = modifier;
        this.zero = modifier2;
        this.negative = modifier3;
        this.mods = null;
        this.frozen = true;
    }

    private static int getModIndex(int n, StandardPlural standardPlural) {
        return standardPlural.ordinal() * 3 + (n + 1);
    }

    public void freeze() {
        this.frozen = true;
    }

    @Override
    public Modifier getModifier(int n, StandardPlural standardPlural) {
        return this.mods[AdoptingModifierStore.getModIndex(n, standardPlural)];
    }

    public Modifier getModifierWithoutPlural(int n) {
        Modifier modifier = n == 0 ? this.zero : (n < 0 ? this.negative : this.positive);
        return modifier;
    }

    public void setModifier(int n, StandardPlural standardPlural, Modifier modifier) {
        this.mods[AdoptingModifierStore.getModIndex((int)n, (StandardPlural)standardPlural)] = modifier;
    }
}

