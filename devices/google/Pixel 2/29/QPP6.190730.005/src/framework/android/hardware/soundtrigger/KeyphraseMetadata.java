/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.soundtrigger;

import android.util.ArraySet;
import java.util.Locale;

public class KeyphraseMetadata {
    public final int id;
    public final String keyphrase;
    public final int recognitionModeFlags;
    public final ArraySet<Locale> supportedLocales;

    public KeyphraseMetadata(int n, String string2, ArraySet<Locale> arraySet, int n2) {
        this.id = n;
        this.keyphrase = string2;
        this.supportedLocales = arraySet;
        this.recognitionModeFlags = n2;
    }

    public boolean supportsLocale(Locale locale) {
        boolean bl = this.supportedLocales.isEmpty() || this.supportedLocales.contains(locale);
        return bl;
    }

    public boolean supportsPhrase(String string2) {
        boolean bl = this.keyphrase.isEmpty() || this.keyphrase.equalsIgnoreCase(string2);
        return bl;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("id=");
        stringBuilder.append(this.id);
        stringBuilder.append(", keyphrase=");
        stringBuilder.append(this.keyphrase);
        stringBuilder.append(", supported-locales=");
        stringBuilder.append(this.supportedLocales);
        stringBuilder.append(", recognition-modes=");
        stringBuilder.append(this.recognitionModeFlags);
        return stringBuilder.toString();
    }
}

