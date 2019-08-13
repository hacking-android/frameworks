/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.number;

import android.icu.impl.number.AffixPatternProvider;
import android.icu.impl.number.AffixUtils;
import android.icu.impl.number.DecimalFormatProperties;
import android.icu.impl.number.DecimalQuantity_DualStorageBCD;
import android.icu.impl.number.Padder;
import java.math.BigDecimal;

public class PatternStringParser {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int IGNORE_ROUNDING_ALWAYS = 2;
    public static final int IGNORE_ROUNDING_IF_CURRENCY = 1;
    public static final int IGNORE_ROUNDING_NEVER = 0;

    /*
     * Exception decompiling
     */
    private static long consumeAffix(ParserState var0, ParsedSubpatternInfo var1_1) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Extractable last case doesn't follow previous
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:478)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitches(SwitchReplacer.java:61)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:376)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    private static void consumeExponent(ParserState parserState, ParsedSubpatternInfo parsedSubpatternInfo) {
        if (parserState.peek() != 69) {
            return;
        }
        if ((parsedSubpatternInfo.groupingSizes & 0xFFFF0000L) == 0xFFFF0000L) {
            parserState.next();
            ++parsedSubpatternInfo.widthExceptAffixes;
            if (parserState.peek() == 43) {
                parserState.next();
                parsedSubpatternInfo.exponentHasPlusSign = true;
                ++parsedSubpatternInfo.widthExceptAffixes;
            }
            while (parserState.peek() == 48) {
                parserState.next();
                ++parsedSubpatternInfo.exponentZeros;
                ++parsedSubpatternInfo.widthExceptAffixes;
            }
            return;
        }
        throw parserState.toParseException("Cannot have grouping separator in scientific notation");
    }

    private static void consumeFormat(ParserState parserState, ParsedSubpatternInfo parsedSubpatternInfo) {
        PatternStringParser.consumeIntegerFormat(parserState, parsedSubpatternInfo);
        if (parserState.peek() == 46) {
            parserState.next();
            parsedSubpatternInfo.hasDecimal = true;
            ++parsedSubpatternInfo.widthExceptAffixes;
            PatternStringParser.consumeFractionFormat(parserState, parsedSubpatternInfo);
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static void consumeFractionFormat(ParserState parserState, ParsedSubpatternInfo parsedSubpatternInfo) {
        int n = 0;
        do {
            int n2;
            if ((n2 = parserState.peek()) != 35) {
                switch (n2) {
                    default: {
                        return;
                    }
                    case 48: 
                    case 49: 
                    case 50: 
                    case 51: 
                    case 52: 
                    case 53: 
                    case 54: 
                    case 55: 
                    case 56: 
                    case 57: 
                }
                if (parsedSubpatternInfo.fractionHashSigns > 0) throw parserState.toParseException("0 cannot follow # after decimal point");
                ++parsedSubpatternInfo.widthExceptAffixes;
                ++parsedSubpatternInfo.fractionNumerals;
                ++parsedSubpatternInfo.fractionTotal;
                if (parserState.peek() == 48) {
                    ++n;
                } else {
                    if (parsedSubpatternInfo.rounding == null) {
                        parsedSubpatternInfo.rounding = new DecimalQuantity_DualStorageBCD();
                    }
                    parsedSubpatternInfo.rounding.appendDigit((byte)(parserState.peek() - 48), n, false);
                    n = 0;
                }
            } else {
                ++parsedSubpatternInfo.widthExceptAffixes;
                ++parsedSubpatternInfo.fractionHashSigns;
                ++parsedSubpatternInfo.fractionTotal;
                ++n;
            }
            parserState.next();
        } while (true);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static void consumeIntegerFormat(ParserState parserState, ParsedSubpatternInfo parsedSubpatternInfo) {
        do {
            int n;
            if ((n = parserState.peek()) != 35) {
                if (n != 44) {
                    if (n != 64) {
                        switch (n) {
                            default: {
                                short s = (short)(parsedSubpatternInfo.groupingSizes & 65535L);
                                n = (short)(parsedSubpatternInfo.groupingSizes >>> 16 & 65535L);
                                short s2 = (short)(65535L & parsedSubpatternInfo.groupingSizes >>> 32);
                                if (s == 0 && n != -1) {
                                    throw parserState.toParseException("Trailing grouping separator is invalid");
                                }
                                if (n != 0 || s2 == -1) return;
                                throw parserState.toParseException("Grouping width of zero is invalid");
                            }
                            case 48: 
                            case 49: 
                            case 50: 
                            case 51: 
                            case 52: 
                            case 53: 
                            case 54: 
                            case 55: 
                            case 56: 
                            case 57: 
                        }
                        if (parsedSubpatternInfo.integerAtSigns > 0) throw parserState.toParseException("Cannot mix @ and 0");
                        ++parsedSubpatternInfo.widthExceptAffixes;
                        ++parsedSubpatternInfo.groupingSizes;
                        ++parsedSubpatternInfo.integerNumerals;
                        ++parsedSubpatternInfo.integerTotal;
                        if (parserState.peek() != 48 && parsedSubpatternInfo.rounding == null) {
                            parsedSubpatternInfo.rounding = new DecimalQuantity_DualStorageBCD();
                        }
                        if (parsedSubpatternInfo.rounding != null) {
                            parsedSubpatternInfo.rounding.appendDigit((byte)(parserState.peek() - 48), 0, true);
                        }
                    } else {
                        if (parsedSubpatternInfo.integerNumerals > 0) throw parserState.toParseException("Cannot mix 0 and @");
                        if (parsedSubpatternInfo.integerTrailingHashSigns > 0) throw parserState.toParseException("Cannot nest # inside of a run of @");
                        ++parsedSubpatternInfo.widthExceptAffixes;
                        ++parsedSubpatternInfo.groupingSizes;
                        ++parsedSubpatternInfo.integerAtSigns;
                        ++parsedSubpatternInfo.integerTotal;
                    }
                } else {
                    ++parsedSubpatternInfo.widthExceptAffixes;
                    parsedSubpatternInfo.groupingSizes <<= 16;
                }
            } else {
                if (parsedSubpatternInfo.integerNumerals > 0) throw parserState.toParseException("# cannot follow 0 before decimal point");
                ++parsedSubpatternInfo.widthExceptAffixes;
                ++parsedSubpatternInfo.groupingSizes;
                if (parsedSubpatternInfo.integerAtSigns > 0) {
                    ++parsedSubpatternInfo.integerTrailingHashSigns;
                } else {
                    ++parsedSubpatternInfo.integerLeadingHashSigns;
                }
                ++parsedSubpatternInfo.integerTotal;
            }
            parserState.next();
        } while (true);
    }

    private static void consumeLiteral(ParserState parserState) {
        if (parserState.peek() != -1) {
            if (parserState.peek() == 39) {
                parserState.next();
                while (parserState.peek() != 39) {
                    if (parserState.peek() != -1) {
                        parserState.next();
                        continue;
                    }
                    throw parserState.toParseException("Expected quoted literal but found EOL");
                }
                parserState.next();
            } else {
                parserState.next();
            }
            return;
        }
        throw parserState.toParseException("Expected unquoted literal but found EOL");
    }

    private static void consumePadding(ParserState parserState, ParsedSubpatternInfo parsedSubpatternInfo, Padder.PadPosition padPosition) {
        if (parserState.peek() != 42) {
            return;
        }
        if (parsedSubpatternInfo.paddingLocation == null) {
            parsedSubpatternInfo.paddingLocation = padPosition;
            parserState.next();
            parsedSubpatternInfo.paddingEndpoints |= (long)parserState.offset;
            PatternStringParser.consumeLiteral(parserState);
            parsedSubpatternInfo.paddingEndpoints |= (long)parserState.offset << 32;
            return;
        }
        throw parserState.toParseException("Cannot have multiple pad specifiers");
    }

    private static void consumePattern(ParserState parserState, ParsedPatternInfo parsedPatternInfo) {
        parsedPatternInfo.positive = new ParsedSubpatternInfo();
        PatternStringParser.consumeSubpattern(parserState, parsedPatternInfo.positive);
        if (parserState.peek() == 59) {
            parserState.next();
            if (parserState.peek() != -1) {
                parsedPatternInfo.negative = new ParsedSubpatternInfo();
                PatternStringParser.consumeSubpattern(parserState, parsedPatternInfo.negative);
            }
        }
        if (parserState.peek() == -1) {
            return;
        }
        throw parserState.toParseException("Found unquoted special character");
    }

    private static void consumeSubpattern(ParserState parserState, ParsedSubpatternInfo parsedSubpatternInfo) {
        PatternStringParser.consumePadding(parserState, parsedSubpatternInfo, Padder.PadPosition.BEFORE_PREFIX);
        parsedSubpatternInfo.prefixEndpoints = PatternStringParser.consumeAffix(parserState, parsedSubpatternInfo);
        PatternStringParser.consumePadding(parserState, parsedSubpatternInfo, Padder.PadPosition.AFTER_PREFIX);
        PatternStringParser.consumeFormat(parserState, parsedSubpatternInfo);
        PatternStringParser.consumeExponent(parserState, parsedSubpatternInfo);
        PatternStringParser.consumePadding(parserState, parsedSubpatternInfo, Padder.PadPosition.BEFORE_SUFFIX);
        parsedSubpatternInfo.suffixEndpoints = PatternStringParser.consumeAffix(parserState, parsedSubpatternInfo);
        PatternStringParser.consumePadding(parserState, parsedSubpatternInfo, Padder.PadPosition.AFTER_SUFFIX);
    }

    public static void parseToExistingProperties(String string, DecimalFormatProperties decimalFormatProperties) {
        PatternStringParser.parseToExistingProperties(string, decimalFormatProperties, 0);
    }

    public static void parseToExistingProperties(String string, DecimalFormatProperties decimalFormatProperties, int n) {
        PatternStringParser.parseToExistingPropertiesImpl(string, decimalFormatProperties, n);
    }

    private static void parseToExistingPropertiesImpl(String string, DecimalFormatProperties decimalFormatProperties, int n) {
        if (string != null && string.length() != 0) {
            PatternStringParser.patternInfoToProperties(decimalFormatProperties, PatternStringParser.parseToPatternInfo(string), n);
            return;
        }
        decimalFormatProperties.clear();
    }

    public static ParsedPatternInfo parseToPatternInfo(String object) {
        ParserState parserState = new ParserState((String)object);
        object = new ParsedPatternInfo((String)object);
        PatternStringParser.consumePattern(parserState, (ParsedPatternInfo)object);
        return object;
    }

    public static DecimalFormatProperties parseToProperties(String string) {
        return PatternStringParser.parseToProperties(string, 0);
    }

    public static DecimalFormatProperties parseToProperties(String string, int n) {
        DecimalFormatProperties decimalFormatProperties = new DecimalFormatProperties();
        PatternStringParser.parseToExistingPropertiesImpl(string, decimalFormatProperties, n);
        return decimalFormatProperties;
    }

    private static void patternInfoToProperties(DecimalFormatProperties decimalFormatProperties, ParsedPatternInfo parsedPatternInfo, int n) {
        ParsedSubpatternInfo parsedSubpatternInfo = parsedPatternInfo.positive;
        boolean bl = n == 0 ? false : (n == 1 ? parsedSubpatternInfo.hasCurrencySign : true);
        n = (short)(parsedSubpatternInfo.groupingSizes & 65535L);
        int n2 = (int)(parsedSubpatternInfo.groupingSizes >>> 16 & 65535L);
        short s = (short)(65535L & parsedSubpatternInfo.groupingSizes >>> 32);
        if (n2 != -1) {
            decimalFormatProperties.setGroupingSize(n);
            decimalFormatProperties.setGroupingUsed(true);
        } else {
            decimalFormatProperties.setGroupingSize(-1);
            decimalFormatProperties.setGroupingUsed(false);
        }
        if (s != -1) {
            decimalFormatProperties.setSecondaryGroupingSize(n2);
        } else {
            decimalFormatProperties.setSecondaryGroupingSize(-1);
        }
        if (parsedSubpatternInfo.integerTotal == 0 && parsedSubpatternInfo.fractionTotal > 0) {
            n = 0;
            n2 = Math.max(1, parsedSubpatternInfo.fractionNumerals);
        } else if (parsedSubpatternInfo.integerNumerals == 0 && parsedSubpatternInfo.fractionNumerals == 0) {
            n = 1;
            n2 = 0;
        } else {
            n = parsedSubpatternInfo.integerNumerals;
            n2 = parsedSubpatternInfo.fractionNumerals;
        }
        if (parsedSubpatternInfo.integerAtSigns > 0) {
            decimalFormatProperties.setMinimumFractionDigits(-1);
            decimalFormatProperties.setMaximumFractionDigits(-1);
            decimalFormatProperties.setRoundingIncrement(null);
            decimalFormatProperties.setMinimumSignificantDigits(parsedSubpatternInfo.integerAtSigns);
            decimalFormatProperties.setMaximumSignificantDigits(parsedSubpatternInfo.integerAtSigns + parsedSubpatternInfo.integerTrailingHashSigns);
        } else if (parsedSubpatternInfo.rounding != null) {
            if (!bl) {
                decimalFormatProperties.setMinimumFractionDigits(n2);
                decimalFormatProperties.setMaximumFractionDigits(parsedSubpatternInfo.fractionTotal);
                decimalFormatProperties.setRoundingIncrement(parsedSubpatternInfo.rounding.toBigDecimal().setScale(parsedSubpatternInfo.fractionNumerals));
            } else {
                decimalFormatProperties.setMinimumFractionDigits(-1);
                decimalFormatProperties.setMaximumFractionDigits(-1);
                decimalFormatProperties.setRoundingIncrement(null);
            }
            decimalFormatProperties.setMinimumSignificantDigits(-1);
            decimalFormatProperties.setMaximumSignificantDigits(-1);
        } else {
            if (!bl) {
                decimalFormatProperties.setMinimumFractionDigits(n2);
                decimalFormatProperties.setMaximumFractionDigits(parsedSubpatternInfo.fractionTotal);
                decimalFormatProperties.setRoundingIncrement(null);
            } else {
                decimalFormatProperties.setMinimumFractionDigits(-1);
                decimalFormatProperties.setMaximumFractionDigits(-1);
                decimalFormatProperties.setRoundingIncrement(null);
            }
            decimalFormatProperties.setMinimumSignificantDigits(-1);
            decimalFormatProperties.setMaximumSignificantDigits(-1);
        }
        if (parsedSubpatternInfo.hasDecimal && parsedSubpatternInfo.fractionTotal == 0) {
            decimalFormatProperties.setDecimalSeparatorAlwaysShown(true);
        } else {
            decimalFormatProperties.setDecimalSeparatorAlwaysShown(false);
        }
        if (parsedSubpatternInfo.exponentZeros > 0) {
            decimalFormatProperties.setExponentSignAlwaysShown(parsedSubpatternInfo.exponentHasPlusSign);
            decimalFormatProperties.setMinimumExponentDigits(parsedSubpatternInfo.exponentZeros);
            if (parsedSubpatternInfo.integerAtSigns == 0) {
                decimalFormatProperties.setMinimumIntegerDigits(parsedSubpatternInfo.integerNumerals);
                decimalFormatProperties.setMaximumIntegerDigits(parsedSubpatternInfo.integerTotal);
            } else {
                decimalFormatProperties.setMinimumIntegerDigits(1);
                decimalFormatProperties.setMaximumIntegerDigits(-1);
            }
        } else {
            decimalFormatProperties.setExponentSignAlwaysShown(false);
            decimalFormatProperties.setMinimumExponentDigits(-1);
            decimalFormatProperties.setMinimumIntegerDigits(n);
            decimalFormatProperties.setMaximumIntegerDigits(-1);
        }
        String string = parsedPatternInfo.getString(256);
        String string2 = parsedPatternInfo.getString(0);
        if (parsedSubpatternInfo.paddingLocation != null) {
            decimalFormatProperties.setFormatWidth(parsedSubpatternInfo.widthExceptAffixes + AffixUtils.estimateLength(string) + AffixUtils.estimateLength(string2));
            String string3 = parsedPatternInfo.getString(1024);
            if (string3.length() == 1) {
                decimalFormatProperties.setPadString(string3);
            } else if (string3.length() == 2) {
                if (string3.charAt(0) == '\'') {
                    decimalFormatProperties.setPadString("'");
                } else {
                    decimalFormatProperties.setPadString(string3);
                }
            } else {
                decimalFormatProperties.setPadString(string3.substring(1, string3.length() - 1));
            }
            decimalFormatProperties.setPadPosition(parsedSubpatternInfo.paddingLocation);
        } else {
            decimalFormatProperties.setFormatWidth(-1);
            decimalFormatProperties.setPadString(null);
            decimalFormatProperties.setPadPosition(null);
        }
        decimalFormatProperties.setPositivePrefixPattern(string);
        decimalFormatProperties.setPositiveSuffixPattern(string2);
        if (parsedPatternInfo.negative != null) {
            decimalFormatProperties.setNegativePrefixPattern(parsedPatternInfo.getString(768));
            decimalFormatProperties.setNegativeSuffixPattern(parsedPatternInfo.getString(512));
        } else {
            decimalFormatProperties.setNegativePrefixPattern(null);
            decimalFormatProperties.setNegativeSuffixPattern(null);
        }
        if (parsedSubpatternInfo.hasPercentSign) {
            decimalFormatProperties.setMagnitudeMultiplier(2);
        } else if (parsedSubpatternInfo.hasPerMilleSign) {
            decimalFormatProperties.setMagnitudeMultiplier(3);
        } else {
            decimalFormatProperties.setMagnitudeMultiplier(0);
        }
    }

    public static class ParsedPatternInfo
    implements AffixPatternProvider {
        public ParsedSubpatternInfo negative;
        public String pattern;
        public ParsedSubpatternInfo positive;

        private ParsedPatternInfo(String string) {
            this.pattern = string;
        }

        private long getEndpoints(int n) {
            int n2 = 1;
            boolean bl = (n & 256) != 0;
            boolean bl2 = (n & 512) != 0;
            n = (n & 1024) != 0 ? n2 : 0;
            if (bl2 && n != 0) {
                return this.negative.paddingEndpoints;
            }
            if (n != 0) {
                return this.positive.paddingEndpoints;
            }
            if (bl && bl2) {
                return this.negative.prefixEndpoints;
            }
            if (bl) {
                return this.positive.prefixEndpoints;
            }
            if (bl2) {
                return this.negative.suffixEndpoints;
            }
            return this.positive.suffixEndpoints;
        }

        public static int getLengthFromEndpoints(long l) {
            int n = (int)(-1L & l);
            return (int)(l >>> 32) - n;
        }

        @Override
        public char charAt(int n, int n2) {
            long l = this.getEndpoints(n);
            int n3 = (int)(-1L & l);
            n = (int)(l >>> 32);
            if (n2 >= 0 && n2 < n - n3) {
                return this.pattern.charAt(n3 + n2);
            }
            throw new IndexOutOfBoundsException();
        }

        @Override
        public boolean containsSymbolType(int n) {
            return AffixUtils.containsType(this.pattern, n);
        }

        @Override
        public String getString(int n) {
            long l = this.getEndpoints(n);
            int n2 = (int)(-1L & l);
            n = (int)(l >>> 32);
            if (n2 == n) {
                return "";
            }
            return this.pattern.substring(n2, n);
        }

        @Override
        public boolean hasBody() {
            boolean bl = this.positive.integerTotal > 0;
            return bl;
        }

        @Override
        public boolean hasCurrencySign() {
            ParsedSubpatternInfo parsedSubpatternInfo;
            boolean bl = this.positive.hasCurrencySign || (parsedSubpatternInfo = this.negative) != null && parsedSubpatternInfo.hasCurrencySign;
            return bl;
        }

        @Override
        public boolean hasNegativeSubpattern() {
            boolean bl = this.negative != null;
            return bl;
        }

        @Override
        public int length(int n) {
            return ParsedPatternInfo.getLengthFromEndpoints(this.getEndpoints(n));
        }

        @Override
        public boolean negativeHasMinusSign() {
            return this.negative.hasMinusSign;
        }

        @Override
        public boolean positiveHasPlusSign() {
            return this.positive.hasPlusSign;
        }
    }

    public static class ParsedSubpatternInfo {
        public boolean exponentHasPlusSign = false;
        public int exponentZeros = 0;
        public int fractionHashSigns = 0;
        public int fractionNumerals = 0;
        public int fractionTotal = 0;
        public long groupingSizes = 0xFFFFFFFF0000L;
        public boolean hasCurrencySign = false;
        public boolean hasDecimal = false;
        public boolean hasMinusSign = false;
        public boolean hasPerMilleSign = false;
        public boolean hasPercentSign = false;
        public boolean hasPlusSign = false;
        public int integerAtSigns = 0;
        public int integerLeadingHashSigns = 0;
        public int integerNumerals = 0;
        public int integerTotal = 0;
        public int integerTrailingHashSigns = 0;
        public long paddingEndpoints = 0L;
        public Padder.PadPosition paddingLocation = null;
        public long prefixEndpoints = 0L;
        public DecimalQuantity_DualStorageBCD rounding = null;
        public long suffixEndpoints = 0L;
        public int widthExceptAffixes = 0;
    }

    private static class ParserState {
        int offset;
        final String pattern;

        ParserState(String string) {
            this.pattern = string;
            this.offset = 0;
        }

        int next() {
            int n = this.peek();
            this.offset += Character.charCount(n);
            return n;
        }

        int peek() {
            if (this.offset == this.pattern.length()) {
                return -1;
            }
            return this.pattern.codePointAt(this.offset);
        }

        IllegalArgumentException toParseException(String string) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Malformed pattern for ICU DecimalFormat: \"");
            stringBuilder.append(this.pattern);
            stringBuilder.append("\": ");
            stringBuilder.append(string);
            stringBuilder.append(" at position ");
            stringBuilder.append(this.offset);
            return new IllegalArgumentException(stringBuilder.toString());
        }
    }

}

