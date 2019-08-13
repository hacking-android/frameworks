/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.number;

import android.icu.impl.number.DecimalFormatProperties;
import android.icu.number.Scale;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class RoundingUtils {
    public static final MathContext DEFAULT_MATH_CONTEXT_34_DIGITS;
    public static final MathContext DEFAULT_MATH_CONTEXT_UNLIMITED;
    public static final RoundingMode DEFAULT_ROUNDING_MODE;
    private static final MathContext[] MATH_CONTEXT_BY_ROUNDING_MODE_34_DIGITS;
    private static final MathContext[] MATH_CONTEXT_BY_ROUNDING_MODE_UNLIMITED;
    public static final int MAX_INT_FRAC_SIG = 999;
    public static final int SECTION_LOWER = 1;
    public static final int SECTION_MIDPOINT = 2;
    public static final int SECTION_UPPER = 3;

    static {
        DEFAULT_ROUNDING_MODE = RoundingMode.HALF_EVEN;
        MATH_CONTEXT_BY_ROUNDING_MODE_UNLIMITED = new MathContext[RoundingMode.values().length];
        MATH_CONTEXT_BY_ROUNDING_MODE_34_DIGITS = new MathContext[RoundingMode.values().length];
        for (int i = 0; i < MATH_CONTEXT_BY_ROUNDING_MODE_34_DIGITS.length; ++i) {
            RoundingUtils.MATH_CONTEXT_BY_ROUNDING_MODE_UNLIMITED[i] = new MathContext(0, RoundingMode.valueOf(i));
            RoundingUtils.MATH_CONTEXT_BY_ROUNDING_MODE_34_DIGITS[i] = new MathContext(34);
        }
        DEFAULT_MATH_CONTEXT_UNLIMITED = MATH_CONTEXT_BY_ROUNDING_MODE_UNLIMITED[DEFAULT_ROUNDING_MODE.ordinal()];
        DEFAULT_MATH_CONTEXT_34_DIGITS = MATH_CONTEXT_BY_ROUNDING_MODE_34_DIGITS[DEFAULT_ROUNDING_MODE.ordinal()];
    }

    public static MathContext getMathContextOr34Digits(DecimalFormatProperties object) {
        MathContext mathContext;
        Object object2 = mathContext = ((DecimalFormatProperties)object).getMathContext();
        if (mathContext == null) {
            object2 = ((DecimalFormatProperties)object).getRoundingMode();
            object = object2;
            if (object2 == null) {
                object = RoundingMode.HALF_EVEN;
            }
            object2 = MATH_CONTEXT_BY_ROUNDING_MODE_34_DIGITS[((Enum)object).ordinal()];
        }
        return object2;
    }

    public static MathContext getMathContextOrUnlimited(DecimalFormatProperties object) {
        MathContext mathContext;
        Object object2 = mathContext = ((DecimalFormatProperties)object).getMathContext();
        if (mathContext == null) {
            object2 = ((DecimalFormatProperties)object).getRoundingMode();
            object = object2;
            if (object2 == null) {
                object = RoundingMode.HALF_EVEN;
            }
            object2 = MATH_CONTEXT_BY_ROUNDING_MODE_UNLIMITED[((Enum)object).ordinal()];
        }
        return object2;
    }

    public static boolean getRoundingDirection(boolean bl, boolean bl2, int n, int n2, Object object) {
        switch (n2) {
            default: {
                break;
            }
            case 6: {
                if (n != 1) {
                    if (n != 2) {
                        if (n != 3) break;
                        return false;
                    }
                    return bl;
                }
                return true;
            }
            case 5: {
                if (n != 1) {
                    if (n != 2) {
                        if (n != 3) break;
                        return false;
                    }
                    return true;
                }
                return true;
            }
            case 4: {
                if (n != 1) {
                    if (n != 2) {
                        if (n != 3) break;
                        return false;
                    }
                    return false;
                }
                return true;
            }
            case 3: {
                return bl2 ^ true;
            }
            case 2: {
                return bl2;
            }
            case 1: {
                return true;
            }
            case 0: {
                return false;
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Rounding is required on ");
        stringBuilder.append(object.toString());
        throw new ArithmeticException(stringBuilder.toString());
    }

    public static MathContext mathContextUnlimited(RoundingMode roundingMode) {
        return MATH_CONTEXT_BY_ROUNDING_MODE_UNLIMITED[roundingMode.ordinal()];
    }

    public static boolean roundsAtMidpoint(int n) {
        return n != 0 && n != 1 && n != 2 && n != 3;
    }

    public static Scale scaleFromProperties(DecimalFormatProperties decimalFormatProperties) {
        MathContext mathContext = RoundingUtils.getMathContextOr34Digits(decimalFormatProperties);
        if (decimalFormatProperties.getMagnitudeMultiplier() != 0) {
            return Scale.powerOfTen(decimalFormatProperties.getMagnitudeMultiplier()).withMathContext(mathContext);
        }
        if (decimalFormatProperties.getMultiplier() != null) {
            return Scale.byBigDecimal(decimalFormatProperties.getMultiplier()).withMathContext(mathContext);
        }
        return null;
    }
}

