/*
 * Decompiled with CFR 0.145.
 */
package android.text;

import android.annotation.UnsupportedAppUsage;
import android.text.GetChars;
import android.text.Spannable;
import android.text.SpannableStringInternal;

public class SpannableString
extends SpannableStringInternal
implements CharSequence,
GetChars,
Spannable {
    public SpannableString(CharSequence charSequence) {
        this(charSequence, false);
    }

    private SpannableString(CharSequence charSequence, int n, int n2) {
        super(charSequence, n, n2, false);
    }

    public SpannableString(CharSequence charSequence, boolean bl) {
        super(charSequence, 0, charSequence.length(), bl);
    }

    public static SpannableString valueOf(CharSequence charSequence) {
        if (charSequence instanceof SpannableString) {
            return (SpannableString)charSequence;
        }
        return new SpannableString(charSequence);
    }

    @Override
    public void removeSpan(Object object) {
        super.removeSpan(object);
    }

    @Override
    public void setSpan(Object object, int n, int n2, int n3) {
        super.setSpan(object, n, n2, n3);
    }

    @Override
    public final CharSequence subSequence(int n, int n2) {
        return new SpannableString(this, n, n2);
    }
}

