/*
 * Decompiled with CFR 0.145.
 */
package android.text;

import android.text.GetChars;
import android.text.InputFilter;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import java.io.IOException;

public interface Editable
extends CharSequence,
GetChars,
Spannable,
Appendable {
    @Override
    public Editable append(char var1);

    @Override
    public Editable append(CharSequence var1);

    @Override
    public Editable append(CharSequence var1, int var2, int var3);

    public void clear();

    public void clearSpans();

    public Editable delete(int var1, int var2);

    public InputFilter[] getFilters();

    public Editable insert(int var1, CharSequence var2);

    public Editable insert(int var1, CharSequence var2, int var3, int var4);

    public Editable replace(int var1, int var2, CharSequence var3);

    public Editable replace(int var1, int var2, CharSequence var3, int var4, int var5);

    public void setFilters(InputFilter[] var1);

    public static class Factory {
        private static Factory sInstance = new Factory();

        public static Factory getInstance() {
            return sInstance;
        }

        public Editable newEditable(CharSequence charSequence) {
            return new SpannableStringBuilder(charSequence);
        }
    }

}

