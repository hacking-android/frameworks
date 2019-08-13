/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.text.Replaceable;
import android.icu.text.Transliterator;
import android.icu.text.UnicodeFilter;
import android.icu.text.UnicodeSet;
import java.util.List;

class CompoundTransliterator
extends Transliterator {
    private int numAnonymousRBTs = 0;
    private Transliterator[] trans;

    CompoundTransliterator(String string, UnicodeFilter unicodeFilter, Transliterator[] arrtransliterator, int n) {
        super(string, unicodeFilter);
        this.trans = arrtransliterator;
        this.numAnonymousRBTs = n;
    }

    CompoundTransliterator(List<Transliterator> list) {
        this(list, 0);
    }

    CompoundTransliterator(List<Transliterator> list, int n) {
        super("", null);
        this.trans = null;
        this.init(list, 0, false);
        this.numAnonymousRBTs = n;
    }

    private static void _smartAppend(StringBuilder stringBuilder, char c) {
        if (stringBuilder.length() != 0 && stringBuilder.charAt(stringBuilder.length() - 1) != c) {
            stringBuilder.append(c);
        }
    }

    private void computeMaximumContextLength() {
        Transliterator[] arrtransliterator;
        int n = 0;
        for (int i = 0; i < (arrtransliterator = this.trans).length; ++i) {
            int n2 = arrtransliterator[i].getMaximumContextLength();
            int n3 = n;
            if (n2 > n) {
                n3 = n2;
            }
            n = n3;
        }
        this.setMaximumContextLength(n);
    }

    private void init(List<Transliterator> object, int n, boolean bl) {
        int n2 = object.size();
        this.trans = new Transliterator[n2];
        for (int i = 0; i < n2; ++i) {
            int n3 = n == 0 ? i : n2 - 1 - i;
            this.trans[i] = object.get(n3);
        }
        if (n == 1 && bl) {
            object = new StringBuilder();
            for (n = 0; n < n2; ++n) {
                if (n > 0) {
                    ((StringBuilder)object).append(';');
                }
                ((StringBuilder)object).append(this.trans[n].getID());
            }
            this.setID(((StringBuilder)object).toString());
        }
        this.computeMaximumContextLength();
    }

    @Override
    public void addSourceTargetSet(UnicodeSet unicodeSet, UnicodeSet unicodeSet2, UnicodeSet unicodeSet3) {
        UnicodeSet unicodeSet4 = new UnicodeSet(this.getFilterAsUnicodeSet(unicodeSet));
        unicodeSet = new UnicodeSet();
        for (int i = 0; i < this.trans.length; ++i) {
            unicodeSet.clear();
            this.trans[i].addSourceTargetSet(unicodeSet4, unicodeSet2, unicodeSet);
            unicodeSet3.addAll(unicodeSet);
            unicodeSet4.addAll(unicodeSet);
        }
    }

    public int getCount() {
        return this.trans.length;
    }

    public Transliterator getTransliterator(int n) {
        return this.trans[n];
    }

    @Override
    protected void handleTransliterate(Replaceable object, Transliterator.Position position, boolean bl) {
        if (this.trans.length < 1) {
            position.start = position.limit;
            return;
        }
        int n = position.limit;
        int n2 = position.start;
        int n3 = 0;
        for (int i = 0; i < this.trans.length; ++i) {
            position.start = n2;
            int n4 = position.limit;
            if (position.start == position.limit) break;
            this.trans[i].filteredTransliterate((Replaceable)object, position, bl);
            if (!bl && position.start != position.limit) {
                object = new StringBuilder();
                ((StringBuilder)object).append("ERROR: Incomplete non-incremental transliteration by ");
                ((StringBuilder)object).append(this.trans[i].getID());
                throw new RuntimeException(((StringBuilder)object).toString());
            }
            n3 += position.limit - n4;
            if (!bl) continue;
            position.limit = position.start;
        }
        position.limit = n + n3;
    }

    public Transliterator safeClone() {
        UnicodeFilter unicodeFilter;
        UnicodeFilter unicodeFilter2 = unicodeFilter = this.getFilter();
        if (unicodeFilter != null) {
            unicodeFilter2 = unicodeFilter;
            if (unicodeFilter instanceof UnicodeSet) {
                unicodeFilter2 = new UnicodeSet((UnicodeSet)unicodeFilter);
            }
        }
        return new CompoundTransliterator(this.getID(), unicodeFilter2, this.trans, this.numAnonymousRBTs);
    }

    @Override
    public String toRules(boolean bl) {
        Object object;
        StringBuilder stringBuilder = new StringBuilder();
        if (this.numAnonymousRBTs >= 1 && this.getFilter() != null) {
            stringBuilder.append("::");
            stringBuilder.append(this.getFilter().toPattern(bl));
            stringBuilder.append(';');
        }
        for (int i = 0; i < ((Transliterator[])(object = this.trans)).length; ++i) {
            if (object[i].getID().startsWith("%Pass")) {
                String string = this.trans[i].toRules(bl);
                object = string;
                if (this.numAnonymousRBTs > 1) {
                    object = string;
                    if (i > 0) {
                        object = string;
                        if (this.trans[i - 1].getID().startsWith("%Pass")) {
                            object = new StringBuilder();
                            ((StringBuilder)object).append("::Null;");
                            ((StringBuilder)object).append(string);
                            object = ((StringBuilder)object).toString();
                        }
                    }
                }
            } else {
                object = this.trans[i].getID().indexOf(59) >= 0 ? this.trans[i].toRules(bl) : this.trans[i].baseToRules(bl);
            }
            CompoundTransliterator._smartAppend(stringBuilder, '\n');
            stringBuilder.append((String)object);
            CompoundTransliterator._smartAppend(stringBuilder, ';');
        }
        return stringBuilder.toString();
    }
}

