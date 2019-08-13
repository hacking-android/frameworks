/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.lang.CharSequences;
import android.icu.text.Normalizer2;
import android.icu.text.Transform;
import android.icu.text.Transliterator;
import android.icu.text.UTF16;
import android.icu.text.UnicodeSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

class SourceTargetUtility {
    static Normalizer2 NFC;
    static final UnicodeSet NON_STARTERS;
    final UnicodeSet sourceCache;
    final Set<String> sourceStrings;
    final Transform<String, String> transform;

    static {
        NON_STARTERS = new UnicodeSet("[:^ccc=0:]").freeze();
        NFC = Normalizer2.getNFCInstance();
    }

    public SourceTargetUtility(Transform<String, String> transform) {
        this(transform, null);
    }

    public SourceTargetUtility(Transform<String, String> transform, Normalizer2 normalizer2) {
        this.transform = transform;
        this.sourceCache = normalizer2 != null ? new UnicodeSet("[:^ccc=0:]") : new UnicodeSet();
        this.sourceStrings = new HashSet<String>();
        for (int i = 0; i <= 1114111; ++i) {
            String string = transform.transform(UTF16.valueOf(i));
            boolean bl = false;
            if (!CharSequences.equals(i, string)) {
                this.sourceCache.add(i);
                bl = true;
            }
            if (normalizer2 == null || (string = NFC.getDecomposition(i)) == null) continue;
            if (!string.equals(transform.transform(string))) {
                this.sourceStrings.add(string);
            }
            if (bl || normalizer2.isInert(i)) continue;
            this.sourceCache.add(i);
        }
        this.sourceCache.freeze();
    }

    public void addSourceTargetSet(Transliterator object, UnicodeSet iterator, UnicodeSet unicodeSet, UnicodeSet unicodeSet2) {
        object = ((Transliterator)object).getFilterAsUnicodeSet((UnicodeSet)((Object)iterator));
        iterator = new UnicodeSet(this.sourceCache).retainAll((UnicodeSet)object);
        unicodeSet.addAll((UnicodeSet)((Object)iterator));
        Object object2 = ((UnicodeSet)((Object)iterator)).iterator();
        while (object2.hasNext()) {
            iterator = object2.next();
            unicodeSet2.addAll(this.transform.transform((String)((Object)iterator)));
        }
        for (String string : this.sourceStrings) {
            if (!((UnicodeSet)object).containsAll(string) || string.equals(object2 = this.transform.transform(string))) continue;
            unicodeSet2.addAll((CharSequence)object2);
            unicodeSet.addAll(string);
        }
    }
}

