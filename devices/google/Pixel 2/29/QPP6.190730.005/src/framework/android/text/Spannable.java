/*
 * Decompiled with CFR 0.145.
 */
package android.text;

import android.text.SpannableString;
import android.text.Spanned;

public interface Spannable
extends Spanned {
    public void removeSpan(Object var1);

    default public void removeSpan(Object object, int n) {
        this.removeSpan(object);
    }

    public void setSpan(Object var1, int var2, int var3, int var4);

    public static class Factory {
        private static Factory sInstance = new Factory();

        public static Factory getInstance() {
            return sInstance;
        }

        public Spannable newSpannable(CharSequence charSequence) {
            return new SpannableString(charSequence);
        }
    }

}

