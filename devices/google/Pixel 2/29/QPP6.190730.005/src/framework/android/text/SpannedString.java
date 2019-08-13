/*
 * Decompiled with CFR 0.145.
 */
package android.text;

import android.annotation.UnsupportedAppUsage;
import android.text.GetChars;
import android.text.SpannableStringInternal;
import android.text.Spanned;

public final class SpannedString
extends SpannableStringInternal
implements CharSequence,
GetChars,
Spanned {
    public SpannedString(CharSequence charSequence) {
        this(charSequence, false);
    }

    private SpannedString(CharSequence charSequence, int n, int n2) {
        super(charSequence, n, n2, false);
    }

    public SpannedString(CharSequence charSequence, boolean bl) {
        super(charSequence, 0, charSequence.length(), bl);
    }

    public static SpannedString valueOf(CharSequence charSequence) {
        if (charSequence instanceof SpannedString) {
            return (SpannedString)charSequence;
        }
        return new SpannedString(charSequence);
    }

    @Override
    public CharSequence subSequence(int n, int n2) {
        return new SpannedString(this, n, n2);
    }
}

