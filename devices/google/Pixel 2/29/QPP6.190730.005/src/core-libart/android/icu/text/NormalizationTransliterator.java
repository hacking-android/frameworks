/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.impl.Norm2AllModes;
import android.icu.impl.Normalizer2Impl;
import android.icu.text.Normalizer2;
import android.icu.text.Replaceable;
import android.icu.text.SourceTargetUtility;
import android.icu.text.Transform;
import android.icu.text.Transliterator;
import android.icu.text.UnicodeFilter;
import android.icu.text.UnicodeSet;
import java.util.HashMap;
import java.util.Map;

final class NormalizationTransliterator
extends Transliterator {
    static final Map<Normalizer2, SourceTargetUtility> SOURCE_CACHE = new HashMap<Normalizer2, SourceTargetUtility>();
    private final Normalizer2 norm2;

    private NormalizationTransliterator(String string, Normalizer2 normalizer2) {
        super(string, null);
        this.norm2 = normalizer2;
    }

    static void register() {
        Transliterator.registerFactory("Any-NFC", new Transliterator.Factory(){

            @Override
            public Transliterator getInstance(String string) {
                return new NormalizationTransliterator("NFC", Normalizer2.getNFCInstance());
            }
        });
        Transliterator.registerFactory("Any-NFD", new Transliterator.Factory(){

            @Override
            public Transliterator getInstance(String string) {
                return new NormalizationTransliterator("NFD", Normalizer2.getNFDInstance());
            }
        });
        Transliterator.registerFactory("Any-NFKC", new Transliterator.Factory(){

            @Override
            public Transliterator getInstance(String string) {
                return new NormalizationTransliterator("NFKC", Normalizer2.getNFKCInstance());
            }
        });
        Transliterator.registerFactory("Any-NFKD", new Transliterator.Factory(){

            @Override
            public Transliterator getInstance(String string) {
                return new NormalizationTransliterator("NFKD", Normalizer2.getNFKDInstance());
            }
        });
        Transliterator.registerFactory("Any-FCD", new Transliterator.Factory(){

            @Override
            public Transliterator getInstance(String string) {
                return new NormalizationTransliterator("FCD", Norm2AllModes.getFCDNormalizer2());
            }
        });
        Transliterator.registerFactory("Any-FCC", new Transliterator.Factory(){

            @Override
            public Transliterator getInstance(String string) {
                return new NormalizationTransliterator("FCC", Norm2AllModes.getNFCInstance().fcc);
            }
        });
        Transliterator.registerSpecialInverse("NFC", "NFD", true);
        Transliterator.registerSpecialInverse("NFKC", "NFKD", true);
        Transliterator.registerSpecialInverse("FCC", "NFD", false);
        Transliterator.registerSpecialInverse("FCD", "FCD", false);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void addSourceTargetSet(UnicodeSet unicodeSet, UnicodeSet unicodeSet2, UnicodeSet unicodeSet3) {
        SourceTargetUtility sourceTargetUtility;
        Map<Normalizer2, SourceTargetUtility> map = SOURCE_CACHE;
        synchronized (map) {
            Object object = SOURCE_CACHE.get(this.norm2);
            sourceTargetUtility = object;
            if (object == null) {
                object = new NormalizingTransform(this.norm2);
                sourceTargetUtility = new SourceTargetUtility((Transform<String, String>)object, this.norm2);
                SOURCE_CACHE.put(this.norm2, sourceTargetUtility);
            }
        }
        sourceTargetUtility.addSourceTargetSet(this, unicodeSet, unicodeSet2, unicodeSet3);
    }

    @Override
    protected void handleTransliterate(Replaceable replaceable, Transliterator.Position position, boolean bl) {
        int n;
        int n2;
        block6 : {
            int n3;
            int n4 = position.start;
            n2 = position.limit;
            if (n4 >= n2) {
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            StringBuilder stringBuilder2 = new StringBuilder();
            int n5 = replaceable.char32At(n4);
            do {
                int n6;
                block5 : {
                    Normalizer2 normalizer2;
                    int n7;
                    stringBuilder.setLength(0);
                    n3 = n4;
                    do {
                        stringBuilder.appendCodePoint(n5);
                        n6 = n3 + Character.charCount(n5);
                        if (n6 >= n2) break block5;
                        normalizer2 = this.norm2;
                        n = n7 = replaceable.char32At(n6);
                        n3 = n6;
                        n5 = n;
                    } while (!normalizer2.hasBoundaryBefore(n7));
                    n5 = n;
                }
                if (n6 == n2 && bl && !this.norm2.hasBoundaryAfter(n5)) {
                    n = n4;
                    break block6;
                }
                this.norm2.normalize((CharSequence)stringBuilder, stringBuilder2);
                n = n6;
                n3 = n2;
                if (!Normalizer2Impl.UTF16Plus.equal(stringBuilder, stringBuilder2)) {
                    replaceable.replace(n4, n6, stringBuilder2.toString());
                    n4 = stringBuilder2.length() - (n6 - n4);
                    n = n6 + n4;
                    n3 = n2 + n4;
                }
                n4 = n;
                n2 = n3;
            } while (n < n3);
            n2 = n3;
        }
        position.start = n;
        position.contextLimit += n2 - position.limit;
        position.limit = n2;
    }

    static class NormalizingTransform
    implements Transform<String, String> {
        final Normalizer2 norm2;

        public NormalizingTransform(Normalizer2 normalizer2) {
            this.norm2 = normalizer2;
        }

        @Override
        public String transform(String string) {
            return this.norm2.normalize(string);
        }
    }

}

