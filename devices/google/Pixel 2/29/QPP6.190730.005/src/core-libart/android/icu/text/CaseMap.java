/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.impl.CaseMapImpl;
import android.icu.impl.UCaseProps;
import android.icu.text.BreakIterator;
import android.icu.text.Edits;
import java.util.Locale;

public abstract class CaseMap {
    @Deprecated
    protected int internalOptions;

    private CaseMap(int n) {
        this.internalOptions = n;
    }

    public static Fold fold() {
        return Fold.DEFAULT;
    }

    private static int getCaseLocale(Locale locale) {
        Locale locale2 = locale;
        if (locale == null) {
            locale2 = Locale.getDefault();
        }
        return UCaseProps.getCaseLocale(locale2);
    }

    public static Lower toLower() {
        return Lower.DEFAULT;
    }

    public static Title toTitle() {
        return Title.DEFAULT;
    }

    public static Upper toUpper() {
        return Upper.DEFAULT;
    }

    public abstract CaseMap omitUnchangedText();

    public static final class Fold
    extends CaseMap {
        private static final Fold DEFAULT = new Fold(0);
        private static final Fold OMIT_UNCHANGED;
        private static final Fold TURKIC;
        private static final Fold TURKIC_OMIT_UNCHANGED;

        static {
            TURKIC = new Fold(1);
            OMIT_UNCHANGED = new Fold(16384);
            TURKIC_OMIT_UNCHANGED = new Fold(16385);
        }

        private Fold(int n) {
            super(n);
        }

        public <A extends Appendable> A apply(CharSequence charSequence, A a, Edits edits) {
            return CaseMapImpl.fold(this.internalOptions, charSequence, a, edits);
        }

        public String apply(CharSequence charSequence) {
            return CaseMapImpl.fold(this.internalOptions, charSequence);
        }

        @Override
        public Fold omitUnchangedText() {
            Fold fold = (this.internalOptions & 1) == 0 ? OMIT_UNCHANGED : TURKIC_OMIT_UNCHANGED;
            return fold;
        }

        public Fold turkic() {
            Fold fold = (this.internalOptions & 16384) == 0 ? TURKIC : TURKIC_OMIT_UNCHANGED;
            return fold;
        }
    }

    public static final class Lower
    extends CaseMap {
        private static final Lower DEFAULT = new Lower(0);
        private static final Lower OMIT_UNCHANGED = new Lower(16384);

        private Lower(int n) {
            super(n);
        }

        public <A extends Appendable> A apply(Locale locale, CharSequence charSequence, A a, Edits edits) {
            return CaseMapImpl.toLower(CaseMap.getCaseLocale(locale), this.internalOptions, charSequence, a, edits);
        }

        public String apply(Locale locale, CharSequence charSequence) {
            return CaseMapImpl.toLower(CaseMap.getCaseLocale(locale), this.internalOptions, charSequence);
        }

        @Override
        public Lower omitUnchangedText() {
            return OMIT_UNCHANGED;
        }
    }

    public static final class Title
    extends CaseMap {
        private static final Title DEFAULT = new Title(0);
        private static final Title OMIT_UNCHANGED = new Title(16384);

        private Title(int n) {
            super(n);
        }

        public Title adjustToCased() {
            return new Title(CaseMapImpl.addTitleAdjustmentOption(this.internalOptions, 1024));
        }

        public <A extends Appendable> A apply(Locale cloneable, BreakIterator breakIterator, CharSequence charSequence, A a, Edits edits) {
            Locale locale = cloneable;
            if (breakIterator == null) {
                locale = cloneable;
                if (cloneable == null) {
                    locale = Locale.getDefault();
                }
            }
            cloneable = CaseMapImpl.getTitleBreakIterator(locale, this.internalOptions, breakIterator);
            ((BreakIterator)cloneable).setText(charSequence);
            return CaseMapImpl.toTitle(CaseMap.getCaseLocale(locale), this.internalOptions, (BreakIterator)cloneable, charSequence, a, edits);
        }

        public String apply(Locale cloneable, BreakIterator breakIterator, CharSequence charSequence) {
            Locale locale = cloneable;
            if (breakIterator == null) {
                locale = cloneable;
                if (cloneable == null) {
                    locale = Locale.getDefault();
                }
            }
            cloneable = CaseMapImpl.getTitleBreakIterator(locale, this.internalOptions, breakIterator);
            ((BreakIterator)cloneable).setText(charSequence);
            return CaseMapImpl.toTitle(CaseMap.getCaseLocale(locale), this.internalOptions, (BreakIterator)cloneable, charSequence);
        }

        public Title noBreakAdjustment() {
            return new Title(CaseMapImpl.addTitleAdjustmentOption(this.internalOptions, 512));
        }

        public Title noLowercase() {
            return new Title(this.internalOptions | 256);
        }

        @Override
        public Title omitUnchangedText() {
            if (this.internalOptions != 0 && this.internalOptions != 16384) {
                return new Title(16384 | this.internalOptions);
            }
            return OMIT_UNCHANGED;
        }

        public Title sentences() {
            return new Title(CaseMapImpl.addTitleIteratorOption(this.internalOptions, 64));
        }

        public Title wholeString() {
            return new Title(CaseMapImpl.addTitleIteratorOption(this.internalOptions, 32));
        }
    }

    public static final class Upper
    extends CaseMap {
        private static final Upper DEFAULT = new Upper(0);
        private static final Upper OMIT_UNCHANGED = new Upper(16384);

        private Upper(int n) {
            super(n);
        }

        public <A extends Appendable> A apply(Locale locale, CharSequence charSequence, A a, Edits edits) {
            return CaseMapImpl.toUpper(CaseMap.getCaseLocale(locale), this.internalOptions, charSequence, a, edits);
        }

        public String apply(Locale locale, CharSequence charSequence) {
            return CaseMapImpl.toUpper(CaseMap.getCaseLocale(locale), this.internalOptions, charSequence);
        }

        @Override
        public Upper omitUnchangedText() {
            return OMIT_UNCHANGED;
        }
    }

}

