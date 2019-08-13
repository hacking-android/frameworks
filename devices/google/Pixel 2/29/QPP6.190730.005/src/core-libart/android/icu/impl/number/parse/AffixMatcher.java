/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.number.parse;

import android.icu.impl.StandardPlural;
import android.icu.impl.StringSegment;
import android.icu.impl.number.AffixPatternProvider;
import android.icu.impl.number.AffixUtils;
import android.icu.impl.number.PatternStringUtils;
import android.icu.impl.number.parse.AffixPatternMatcher;
import android.icu.impl.number.parse.AffixTokenMatcherFactory;
import android.icu.impl.number.parse.IgnorablesMatcher;
import android.icu.impl.number.parse.NumberParseMatcher;
import android.icu.impl.number.parse.NumberParserImpl;
import android.icu.impl.number.parse.ParsedNumber;
import android.icu.number.NumberFormatter;
import android.icu.text.UnicodeSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;

public class AffixMatcher
implements NumberParseMatcher {
    public static final Comparator<AffixMatcher> COMPARATOR = new Comparator<AffixMatcher>(){

        @Override
        public int compare(AffixMatcher affixMatcher, AffixMatcher affixMatcher2) {
            int n = AffixMatcher.length(affixMatcher.prefix);
            int n2 = AffixMatcher.length(affixMatcher2.prefix);
            int n3 = -1;
            if (n != n2) {
                if (AffixMatcher.length(affixMatcher.prefix) <= AffixMatcher.length(affixMatcher2.prefix)) {
                    n3 = 1;
                }
                return n3;
            }
            if (AffixMatcher.length(affixMatcher.suffix) != AffixMatcher.length(affixMatcher2.suffix)) {
                if (AffixMatcher.length(affixMatcher.suffix) <= AffixMatcher.length(affixMatcher2.suffix)) {
                    n3 = 1;
                }
                return n3;
            }
            if (!affixMatcher.equals(affixMatcher2)) {
                if (affixMatcher.hashCode() <= affixMatcher2.hashCode()) {
                    n3 = 1;
                }
                return n3;
            }
            return 0;
        }
    };
    private final int flags;
    private final AffixPatternMatcher prefix;
    private final AffixPatternMatcher suffix;

    private AffixMatcher(AffixPatternMatcher affixPatternMatcher, AffixPatternMatcher affixPatternMatcher2, int n) {
        this.prefix = affixPatternMatcher;
        this.suffix = affixPatternMatcher2;
        this.flags = n;
    }

    public static void createMatchers(AffixPatternProvider affixPatternProvider, NumberParserImpl numberParserImpl, AffixTokenMatcherFactory affixTokenMatcherFactory, IgnorablesMatcher object, int n) {
        if (!AffixMatcher.isInteresting(affixPatternProvider, (IgnorablesMatcher)object, n)) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        object = new ArrayList(6);
        boolean bl = (n & 128) != 0;
        NumberFormatter.SignDisplay signDisplay = (n & 1024) != 0 ? NumberFormatter.SignDisplay.ALWAYS : NumberFormatter.SignDisplay.AUTO;
        Object object2 = null;
        Object object3 = null;
        for (int i = 1; i >= -1; --i) {
            NumberParseMatcher numberParseMatcher;
            PatternStringUtils.patternInfoToStringBuilder(affixPatternProvider, true, i, signDisplay, StandardPlural.OTHER, false, stringBuilder);
            AffixPatternMatcher affixPatternMatcher = AffixPatternMatcher.fromAffixPattern(stringBuilder.toString(), affixTokenMatcherFactory, n);
            Object object4 = StandardPlural.OTHER;
            PatternStringUtils.patternInfoToStringBuilder(affixPatternProvider, false, i, signDisplay, object4, false, stringBuilder);
            AffixPatternMatcher affixPatternMatcher2 = AffixPatternMatcher.fromAffixPattern(stringBuilder.toString(), affixTokenMatcherFactory, n);
            if (i == 1) {
                object2 = affixPatternMatcher2;
                object4 = affixPatternMatcher;
            } else {
                numberParseMatcher = object2;
                object2 = object3;
                object4 = numberParseMatcher;
                if (Objects.equals(affixPatternMatcher, numberParseMatcher)) {
                    object2 = object3;
                    object4 = numberParseMatcher;
                    if (Objects.equals(affixPatternMatcher2, object3)) {
                        object2 = numberParseMatcher;
                        continue;
                    }
                }
            }
            int n2 = i == -1 ? 1 : 0;
            numberParseMatcher = AffixMatcher.getInstance(affixPatternMatcher, affixPatternMatcher2, n2);
            object3 = object;
            ((ArrayList)object3).add(numberParseMatcher);
            if (bl && affixPatternMatcher != null && affixPatternMatcher2 != null) {
                if (i == 1 || !Objects.equals(affixPatternMatcher, object4)) {
                    ((ArrayList)object3).add(AffixMatcher.getInstance(affixPatternMatcher, null, n2));
                }
                if (i == 1 || !Objects.equals(affixPatternMatcher2, object2)) {
                    ((ArrayList)object3).add(AffixMatcher.getInstance(null, affixPatternMatcher2, n2));
                }
            }
            object3 = object2;
            object2 = object4;
        }
        Collections.sort(object, COMPARATOR);
        numberParserImpl.addMatchers((Collection<? extends NumberParseMatcher>)object);
    }

    private static final AffixMatcher getInstance(AffixPatternMatcher affixPatternMatcher, AffixPatternMatcher affixPatternMatcher2, int n) {
        return new AffixMatcher(affixPatternMatcher, affixPatternMatcher2, n);
    }

    private static boolean isInteresting(AffixPatternProvider affixPatternProvider, IgnorablesMatcher ignorablesMatcher, int n) {
        String string = affixPatternProvider.getString(256);
        String string2 = affixPatternProvider.getString(0);
        String string3 = null;
        String string4 = null;
        if (affixPatternProvider.hasNegativeSubpattern()) {
            string3 = affixPatternProvider.getString(768);
            string4 = affixPatternProvider.getString(512);
        }
        return (n & 256) != 0 || !AffixUtils.containsOnlySymbolsAndIgnorables(string, ignorablesMatcher.getSet()) || !AffixUtils.containsOnlySymbolsAndIgnorables(string2, ignorablesMatcher.getSet()) || !AffixUtils.containsOnlySymbolsAndIgnorables(string3, ignorablesMatcher.getSet()) || !AffixUtils.containsOnlySymbolsAndIgnorables(string4, ignorablesMatcher.getSet()) || AffixUtils.containsType(string2, -2) || AffixUtils.containsType(string2, -1) || AffixUtils.containsType(string4, -2) || AffixUtils.containsType(string4, -1);
    }

    private static int length(AffixPatternMatcher affixPatternMatcher) {
        int n = affixPatternMatcher == null ? 0 : affixPatternMatcher.getPattern().length();
        return n;
    }

    static boolean matched(AffixPatternMatcher affixPatternMatcher, String string) {
        boolean bl = affixPatternMatcher == null && string == null || affixPatternMatcher != null && affixPatternMatcher.getPattern().equals(string);
        return bl;
    }

    public boolean equals(Object object) {
        boolean bl;
        block1 : {
            boolean bl2 = object instanceof AffixMatcher;
            bl = false;
            if (!bl2) {
                return false;
            }
            object = (AffixMatcher)object;
            if (!Objects.equals(this.prefix, ((AffixMatcher)object).prefix) || !Objects.equals(this.suffix, ((AffixMatcher)object).suffix) || this.flags != ((AffixMatcher)object).flags) break block1;
            bl = true;
        }
        return bl;
    }

    public int hashCode() {
        return Objects.hashCode(this.prefix) ^ Objects.hashCode(this.suffix) ^ this.flags;
    }

    @Override
    public boolean match(StringSegment stringSegment, ParsedNumber parsedNumber) {
        if (!parsedNumber.seenNumber()) {
            if (parsedNumber.prefix == null && this.prefix != null) {
                int n = stringSegment.getOffset();
                boolean bl = this.prefix.match(stringSegment, parsedNumber);
                if (n != stringSegment.getOffset()) {
                    parsedNumber.prefix = this.prefix.getPattern();
                }
                return bl;
            }
            return false;
        }
        if (parsedNumber.suffix == null && this.suffix != null && AffixMatcher.matched(this.prefix, parsedNumber.prefix)) {
            int n = stringSegment.getOffset();
            boolean bl = this.suffix.match(stringSegment, parsedNumber);
            if (n != stringSegment.getOffset()) {
                parsedNumber.suffix = this.suffix.getPattern();
            }
            return bl;
        }
        return false;
    }

    @Override
    public void postProcess(ParsedNumber parsedNumber) {
        if (AffixMatcher.matched(this.prefix, parsedNumber.prefix) && AffixMatcher.matched(this.suffix, parsedNumber.suffix)) {
            if (parsedNumber.prefix == null) {
                parsedNumber.prefix = "";
            }
            if (parsedNumber.suffix == null) {
                parsedNumber.suffix = "";
            }
            parsedNumber.flags |= this.flags;
            AffixPatternMatcher affixPatternMatcher = this.prefix;
            if (affixPatternMatcher != null) {
                affixPatternMatcher.postProcess(parsedNumber);
            }
            if ((affixPatternMatcher = this.suffix) != null) {
                affixPatternMatcher.postProcess(parsedNumber);
            }
        }
    }

    @Override
    public boolean smokeTest(StringSegment stringSegment) {
        AffixPatternMatcher affixPatternMatcher = this.prefix;
        boolean bl = affixPatternMatcher != null && affixPatternMatcher.smokeTest(stringSegment) || (affixPatternMatcher = this.suffix) != null && affixPatternMatcher.smokeTest(stringSegment);
        return bl;
    }

    public String toString() {
        int n = this.flags;
        boolean bl = true;
        if ((n & 1) == 0) {
            bl = false;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<AffixMatcher");
        String string = bl ? ":negative " : " ";
        stringBuilder.append(string);
        stringBuilder.append(this.prefix);
        stringBuilder.append("#");
        stringBuilder.append(this.suffix);
        stringBuilder.append(">");
        return stringBuilder.toString();
    }

}

