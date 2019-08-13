/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.icu.text.Normalizer
 *  android.icu.text.Normalizer$Mode
 */
package java.text;

import android.icu.text.Normalizer;

public final class Normalizer {
    private Normalizer() {
    }

    public static boolean isNormalized(CharSequence charSequence, Form form) {
        return android.icu.text.Normalizer.isNormalized((String)charSequence.toString(), (Normalizer.Mode)form.icuMode, (int)0);
    }

    public static String normalize(CharSequence charSequence, Form form) {
        return android.icu.text.Normalizer.normalize((String)charSequence.toString(), (Normalizer.Mode)form.icuMode);
    }

    public static enum Form {
        NFD(android.icu.text.Normalizer.NFD),
        NFC(android.icu.text.Normalizer.NFC),
        NFKD(android.icu.text.Normalizer.NFKD),
        NFKC(android.icu.text.Normalizer.NFKC);
        
        private final Normalizer.Mode icuMode;

        private Form(Normalizer.Mode mode) {
            this.icuMode = mode;
        }
    }

}

