/*
 * Decompiled with CFR 0.145.
 */
package android.icu.number;

import android.icu.impl.number.DecimalQuantity;
import android.icu.impl.number.MicroProps;
import android.icu.impl.number.MicroPropsGenerator;
import android.icu.impl.number.Modifier;
import android.icu.impl.number.MultiplierProducer;
import android.icu.impl.number.NumberStringBuilder;
import android.icu.number.Notation;
import android.icu.number.NumberFormatter;
import android.icu.number.Precision;
import android.icu.text.DecimalFormatSymbols;
import android.icu.text.NumberFormat;

public class ScientificNotation
extends Notation
implements Cloneable {
    int engineeringInterval;
    NumberFormatter.SignDisplay exponentSignDisplay;
    int minExponentDigits;
    boolean requireMinInt;

    ScientificNotation(int n, boolean bl, int n2, NumberFormatter.SignDisplay signDisplay) {
        this.engineeringInterval = n;
        this.requireMinInt = bl;
        this.minExponentDigits = n2;
        this.exponentSignDisplay = signDisplay;
    }

    public Object clone() {
        try {
            Object object = Object.super.clone();
            return object;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new AssertionError(cloneNotSupportedException);
        }
    }

    public ScientificNotation withExponentSignDisplay(NumberFormatter.SignDisplay signDisplay) {
        ScientificNotation scientificNotation = (ScientificNotation)this.clone();
        scientificNotation.exponentSignDisplay = signDisplay;
        return scientificNotation;
    }

    MicroPropsGenerator withLocaleData(DecimalFormatSymbols decimalFormatSymbols, boolean bl, MicroPropsGenerator microPropsGenerator) {
        return new ScientificHandler(this, decimalFormatSymbols, bl, microPropsGenerator);
    }

    public ScientificNotation withMinExponentDigits(int n) {
        if (n >= 1 && n <= 999) {
            ScientificNotation scientificNotation = (ScientificNotation)this.clone();
            scientificNotation.minExponentDigits = n;
            return scientificNotation;
        }
        throw new IllegalArgumentException("Integer digits must be between 1 and 999 (inclusive)");
    }

    private static class ScientificHandler
    implements MicroPropsGenerator,
    MultiplierProducer,
    Modifier {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        int exponent;
        final ScientificNotation notation;
        final MicroPropsGenerator parent;
        final ScientificModifier[] precomputedMods;
        final DecimalFormatSymbols symbols;

        private ScientificHandler(ScientificNotation scientificNotation, DecimalFormatSymbols decimalFormatSymbols, boolean bl, MicroPropsGenerator microPropsGenerator) {
            this.notation = scientificNotation;
            this.symbols = decimalFormatSymbols;
            this.parent = microPropsGenerator;
            if (bl) {
                this.precomputedMods = new ScientificModifier[25];
                for (int i = -12; i <= 12; ++i) {
                    this.precomputedMods[i + 12] = new ScientificModifier(i, this);
                }
            } else {
                this.precomputedMods = null;
            }
        }

        private int doApply(int n, NumberStringBuilder numberStringBuilder, int n2) {
            int n3;
            int n4 = n2 + numberStringBuilder.insert(n2, this.symbols.getExponentSeparator(), NumberFormat.Field.EXPONENT_SYMBOL);
            if (n < 0 && this.notation.exponentSignDisplay != NumberFormatter.SignDisplay.NEVER) {
                n3 = n4 + numberStringBuilder.insert(n4, this.symbols.getMinusSignString(), NumberFormat.Field.EXPONENT_SIGN);
            } else {
                n3 = n4;
                if (n >= 0) {
                    n3 = n4;
                    if (this.notation.exponentSignDisplay == NumberFormatter.SignDisplay.ALWAYS) {
                        n3 = n4 + numberStringBuilder.insert(n4, this.symbols.getPlusSignString(), NumberFormat.Field.EXPONENT_SIGN);
                    }
                }
            }
            n = Math.abs(n);
            n4 = 0;
            while (n4 < this.notation.minExponentDigits || n > 0) {
                n3 += numberStringBuilder.insert(n3 - n4, this.symbols.getDigitStringsLocal()[n % 10], NumberFormat.Field.EXPONENT);
                ++n4;
                n /= 10;
            }
            return n3 - n2;
        }

        @Override
        public int apply(NumberStringBuilder numberStringBuilder, int n, int n2) {
            return this.doApply(this.exponent, numberStringBuilder, n2);
        }

        @Override
        public boolean containsField(NumberFormat.Field field) {
            return false;
        }

        @Override
        public int getCodePointCount() {
            return 999;
        }

        @Override
        public int getMultiplier(int n) {
            int n2 = this.notation.engineeringInterval;
            if (!this.notation.requireMinInt) {
                n2 = n2 <= 1 ? 1 : (n % n2 + n2) % n2 + 1;
            }
            return n2 - n - 1;
        }

        @Override
        public Modifier.Parameters getParameters() {
            return null;
        }

        @Override
        public int getPrefixLength() {
            return 0;
        }

        @Override
        public boolean isStrong() {
            return true;
        }

        @Override
        public MicroProps processQuantity(DecimalQuantity arrscientificModifier) {
            int n;
            MicroProps microProps = this.parent.processQuantity((DecimalQuantity)arrscientificModifier);
            if (arrscientificModifier.isZero()) {
                if (this.notation.requireMinInt && microProps.rounder instanceof Precision.SignificantRounderImpl) {
                    ((Precision.SignificantRounderImpl)microProps.rounder).apply((DecimalQuantity)arrscientificModifier, this.notation.engineeringInterval);
                    n = 0;
                } else {
                    microProps.rounder.apply((DecimalQuantity)arrscientificModifier);
                    n = 0;
                }
            } else {
                n = -microProps.rounder.chooseMultiplierAndApply((DecimalQuantity)arrscientificModifier, this);
            }
            arrscientificModifier = this.precomputedMods;
            if (arrscientificModifier != null && n >= -12 && n <= 12) {
                microProps.modInner = arrscientificModifier[n + 12];
            } else if (this.precomputedMods != null) {
                microProps.modInner = new ScientificModifier(n, this);
            } else {
                this.exponent = n;
                microProps.modInner = this;
            }
            microProps.rounder = Precision.constructPassThrough();
            return microProps;
        }

        @Override
        public boolean semanticallyEquivalent(Modifier modifier) {
            return false;
        }
    }

    private static class ScientificModifier
    implements Modifier {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        final int exponent;
        final ScientificHandler handler;

        ScientificModifier(int n, ScientificHandler scientificHandler) {
            this.exponent = n;
            this.handler = scientificHandler;
        }

        @Override
        public int apply(NumberStringBuilder numberStringBuilder, int n, int n2) {
            return this.handler.doApply(this.exponent, numberStringBuilder, n2);
        }

        @Override
        public boolean containsField(NumberFormat.Field field) {
            return false;
        }

        @Override
        public int getCodePointCount() {
            return 999;
        }

        @Override
        public Modifier.Parameters getParameters() {
            return null;
        }

        @Override
        public int getPrefixLength() {
            return 0;
        }

        @Override
        public boolean isStrong() {
            return true;
        }

        @Override
        public boolean semanticallyEquivalent(Modifier modifier) {
            boolean bl = modifier instanceof ScientificModifier;
            boolean bl2 = false;
            if (!bl) {
                return false;
            }
            modifier = (ScientificModifier)modifier;
            if (this.exponent == ((ScientificModifier)modifier).exponent) {
                bl2 = true;
            }
            return bl2;
        }
    }

}

