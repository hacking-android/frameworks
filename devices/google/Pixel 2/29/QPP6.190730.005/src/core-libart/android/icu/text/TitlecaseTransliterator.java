/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.impl.UCaseProps;
import android.icu.lang.UCharacter;
import android.icu.text.Replaceable;
import android.icu.text.ReplaceableContextIterator;
import android.icu.text.SourceTargetUtility;
import android.icu.text.Transform;
import android.icu.text.Transliterator;
import android.icu.text.UTF16;
import android.icu.text.UnicodeFilter;
import android.icu.text.UnicodeSet;
import android.icu.util.ULocale;

class TitlecaseTransliterator
extends Transliterator {
    static final String _ID = "Any-Title";
    private int caseLocale;
    private final UCaseProps csp;
    private ReplaceableContextIterator iter;
    private final ULocale locale;
    private StringBuilder result;
    SourceTargetUtility sourceTargetUtility = null;

    public TitlecaseTransliterator(ULocale uLocale) {
        super(_ID, null);
        this.locale = uLocale;
        this.setMaximumContextLength(2);
        this.csp = UCaseProps.INSTANCE;
        this.iter = new ReplaceableContextIterator();
        this.result = new StringBuilder();
        this.caseLocale = UCaseProps.getCaseLocale(this.locale);
    }

    static void register() {
        Transliterator.registerFactory(_ID, new Transliterator.Factory(){

            @Override
            public Transliterator getInstance(String string) {
                return new TitlecaseTransliterator(ULocale.US);
            }
        });
        TitlecaseTransliterator.registerSpecialInverse("Title", "Lower", false);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void addSourceTargetSet(UnicodeSet unicodeSet, UnicodeSet unicodeSet2, UnicodeSet unicodeSet3) {
        synchronized (this) {
            if (this.sourceTargetUtility == null) {
                SourceTargetUtility sourceTargetUtility;
                Transform<String, String> transform = new Transform<String, String>(){

                    @Override
                    public String transform(String string) {
                        return UCharacter.toTitleCase(TitlecaseTransliterator.this.locale, string, null);
                    }
                };
                this.sourceTargetUtility = sourceTargetUtility = new SourceTargetUtility(transform);
            }
        }
        this.sourceTargetUtility.addSourceTargetSet(this, unicodeSet, unicodeSet2, unicodeSet3);
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected void handleTransliterate(Replaceable replaceable, Transliterator.Position position, boolean bl) {
        synchronized (this) {
            void var2_2;
            int n = var2_2.start;
            int n2 = var2_2.limit;
            if (n >= n2) {
                return;
            }
            int n3 = 1;
            n2 = var2_2.start - 1;
            do {
                n = n3;
                if (n2 < var2_2.contextStart) break;
                int n4 = replaceable.char32At(n2);
                n = this.csp.getTypeOrIgnorable(n4);
                if (n > 0) {
                    n = 0;
                    break;
                }
                if (n == 0) {
                    n = n3;
                    break;
                }
                n2 -= UTF16.getCharCount(n4);
            } while (true);
            this.iter.setText(replaceable);
            this.iter.setIndex(var2_2.start);
            this.iter.setLimit(var2_2.limit);
            this.iter.setContextLimits(var2_2.contextStart, var2_2.contextLimit);
            this.result.setLength(0);
            n2 = n;
            do {
                void var3_3;
                if ((n3 = this.iter.nextCaseMapCP()) < 0) {
                    var2_2.start = var2_2.limit;
                    return;
                }
                n = this.csp.getTypeOrIgnorable(n3);
                if (n < 0) continue;
                n2 = n2 != 0 ? this.csp.toFullTitle(n3, this.iter, this.result, this.caseLocale) : this.csp.toFullLower(n3, this.iter, this.result, this.caseLocale);
                n = n == 0 ? 1 : 0;
                if (this.iter.didReachLimit() && var3_3 != false) {
                    var2_2.start = this.iter.getCaseMapCPStart();
                    return;
                }
                if (n2 < 0) {
                    n2 = n;
                    continue;
                }
                if (n2 <= 31) {
                    n3 = this.iter.replace(this.result.toString());
                    this.result.setLength(0);
                } else {
                    n3 = this.iter.replace(UTF16.valueOf(n2));
                }
                n2 = n;
                if (n3 == 0) continue;
                var2_2.limit += n3;
                var2_2.contextLimit += n3;
                n2 = n;
            } while (true);
        }
    }

}

