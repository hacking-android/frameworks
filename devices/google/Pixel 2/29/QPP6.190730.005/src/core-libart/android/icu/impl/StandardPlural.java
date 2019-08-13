/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class StandardPlural
extends Enum<StandardPlural> {
    private static final /* synthetic */ StandardPlural[] $VALUES;
    public static final int COUNT;
    public static final /* enum */ StandardPlural FEW;
    public static final /* enum */ StandardPlural MANY;
    public static final /* enum */ StandardPlural ONE;
    public static final /* enum */ StandardPlural OTHER;
    public static final int OTHER_INDEX;
    public static final /* enum */ StandardPlural TWO;
    public static final List<StandardPlural> VALUES;
    public static final /* enum */ StandardPlural ZERO;
    private final String keyword;

    static {
        ZERO = new StandardPlural("zero");
        ONE = new StandardPlural("one");
        TWO = new StandardPlural("two");
        FEW = new StandardPlural("few");
        MANY = new StandardPlural("many");
        OTHER = new StandardPlural("other");
        StandardPlural standardPlural = ZERO;
        StandardPlural standardPlural2 = ONE;
        StandardPlural standardPlural3 = TWO;
        StandardPlural standardPlural4 = FEW;
        StandardPlural standardPlural5 = MANY;
        StandardPlural standardPlural6 = OTHER;
        $VALUES = new StandardPlural[]{standardPlural, standardPlural2, standardPlural3, standardPlural4, standardPlural5, standardPlural6};
        OTHER_INDEX = standardPlural6.ordinal();
        VALUES = Collections.unmodifiableList(Arrays.asList(StandardPlural.values()));
        COUNT = VALUES.size();
    }

    private StandardPlural(String string2) {
        this.keyword = string2;
    }

    public static final StandardPlural fromString(CharSequence charSequence) {
        StandardPlural standardPlural = StandardPlural.orNullFromString(charSequence);
        if (standardPlural != null) {
            return standardPlural;
        }
        throw new IllegalArgumentException(charSequence.toString());
    }

    public static final int indexFromString(CharSequence charSequence) {
        StandardPlural standardPlural = StandardPlural.orNullFromString(charSequence);
        if (standardPlural != null) {
            return standardPlural.ordinal();
        }
        throw new IllegalArgumentException(charSequence.toString());
    }

    public static final int indexOrNegativeFromString(CharSequence object) {
        int n = (object = StandardPlural.orNullFromString((CharSequence)object)) != null ? ((Enum)object).ordinal() : -1;
        return n;
    }

    public static final int indexOrOtherIndexFromString(CharSequence object) {
        int n = (object = StandardPlural.orNullFromString((CharSequence)object)) != null ? ((Enum)object).ordinal() : OTHER.ordinal();
        return n;
    }

    public static final StandardPlural orNullFromString(CharSequence charSequence) {
        int n = charSequence.length();
        if (n != 3) {
            if (n != 4) {
                if (n == 5 && "other".contentEquals(charSequence)) {
                    return OTHER;
                }
            } else {
                if ("many".contentEquals(charSequence)) {
                    return MANY;
                }
                if ("zero".contentEquals(charSequence)) {
                    return ZERO;
                }
            }
        } else {
            if ("one".contentEquals(charSequence)) {
                return ONE;
            }
            if ("two".contentEquals(charSequence)) {
                return TWO;
            }
            if ("few".contentEquals(charSequence)) {
                return FEW;
            }
        }
        return null;
    }

    public static final StandardPlural orOtherFromString(CharSequence object) {
        if ((object = StandardPlural.orNullFromString(object)) == null) {
            object = OTHER;
        }
        return object;
    }

    public static StandardPlural valueOf(String string) {
        return Enum.valueOf(StandardPlural.class, string);
    }

    public static StandardPlural[] values() {
        return (StandardPlural[])$VALUES.clone();
    }

    public final String getKeyword() {
        return this.keyword;
    }
}

