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

class LowercaseTransliterator
extends Transliterator {
    static final String _ID = "Any-Lower";
    private int caseLocale;
    private final UCaseProps csp;
    private ReplaceableContextIterator iter;
    private final ULocale locale;
    private StringBuilder result;
    SourceTargetUtility sourceTargetUtility = null;

    public LowercaseTransliterator(ULocale uLocale) {
        super(_ID, null);
        this.locale = uLocale;
        this.csp = UCaseProps.INSTANCE;
        this.iter = new ReplaceableContextIterator();
        this.result = new StringBuilder();
        this.caseLocale = UCaseProps.getCaseLocale(this.locale);
    }

    static void register() {
        Transliterator.registerFactory(_ID, new Transliterator.Factory(){

            @Override
            public Transliterator getInstance(String string) {
                return new LowercaseTransliterator(ULocale.US);
            }
        });
        Transliterator.registerSpecialInverse("Lower", "Upper", true);
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
                        return UCharacter.toLowerCase(LowercaseTransliterator.this.locale, string);
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
            UCaseProps uCaseProps = this.csp;
            if (uCaseProps == null) {
                return;
            }
            int n = var2_2.start;
            int n2 = var2_2.limit;
            if (n >= n2) {
                return;
            }
            this.iter.setText(replaceable);
            this.result.setLength(0);
            this.iter.setIndex(var2_2.start);
            this.iter.setLimit(var2_2.limit);
            this.iter.setContextLimits(var2_2.contextStart, var2_2.contextLimit);
            do {
                void var3_3;
                if ((n = this.iter.nextCaseMapCP()) < 0) {
                    var2_2.start = var2_2.limit;
                    return;
                }
                n = this.csp.toFullLower(n, this.iter, this.result, this.caseLocale);
                if (this.iter.didReachLimit() && var3_3 != false) {
                    var2_2.start = this.iter.getCaseMapCPStart();
                    return;
                }
                if (n < 0) continue;
                if (n <= 31) {
                    n = this.iter.replace(this.result.toString());
                    this.result.setLength(0);
                } else {
                    n = this.iter.replace(UTF16.valueOf(n));
                }
                if (n == 0) continue;
                var2_2.limit += n;
                var2_2.contextLimit += n;
            } while (true);
        }
    }

}

