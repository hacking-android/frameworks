/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.util.ULocale;
import dalvik.annotation.compat.UnsupportedAppUsage;
import java.text.Format;

public abstract class UFormat
extends Format {
    private static final long serialVersionUID = -4964390515840164416L;
    private ULocale actualLocale;
    private ULocale validLocale;

    @UnsupportedAppUsage
    public final ULocale getLocale(ULocale.Type object) {
        object = object == ULocale.ACTUAL_LOCALE ? this.actualLocale : this.validLocale;
        return object;
    }

    final void setLocale(ULocale uLocale, ULocale uLocale2) {
        boolean bl = true;
        boolean bl2 = uLocale == null;
        if (uLocale2 != null) {
            bl = false;
        }
        if (bl2 == bl) {
            this.validLocale = uLocale;
            this.actualLocale = uLocale2;
            return;
        }
        throw new IllegalArgumentException();
    }
}

