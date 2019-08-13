/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.text.BreakIterator;
import android.icu.text.CollationElementIterator;
import android.icu.text.Collator;
import android.icu.text.Normalizer;
import android.icu.text.Normalizer2;
import android.icu.text.RuleBasedCollator;
import android.icu.text.SearchIterator;
import android.icu.util.ICUException;
import android.icu.util.ULocale;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.Locale;

public final class StringSearch
extends SearchIterator {
    private static int CE_LEVEL2_BASE = 0;
    private static int CE_LEVEL3_BASE = 0;
    private static final int CE_MATCH = -1;
    private static final int CE_NO_MATCH = 0;
    private static final int CE_SKIP_PATN = 2;
    private static final int CE_SKIP_TARG = 1;
    private static final int INITIAL_ARRAY_SIZE_ = 256;
    private static final int PRIMARYORDERMASK = -65536;
    private static final int SECONDARYORDERMASK = 65280;
    private static final int TERTIARYORDERMASK = 255;
    int ceMask_;
    private RuleBasedCollator collator_;
    private Normalizer2 nfd_;
    private Pattern pattern_;
    private int strength_;
    private CollationElementIterator textIter_;
    private CollationPCE textProcessedIter_;
    private boolean toShift_;
    private CollationElementIterator utilIter_;
    int variableTop_;

    static {
        CE_LEVEL2_BASE = 5;
        CE_LEVEL3_BASE = 327680;
    }

    public StringSearch(String string, String string2) {
        this(string, new StringCharacterIterator(string2), (RuleBasedCollator)Collator.getInstance(), null);
    }

    public StringSearch(String string, CharacterIterator characterIterator, RuleBasedCollator ruleBasedCollator) {
        this(string, characterIterator, ruleBasedCollator, null);
    }

    public StringSearch(String object, CharacterIterator characterIterator, RuleBasedCollator object2, BreakIterator breakIterator) {
        super(characterIterator, breakIterator);
        if (!((RuleBasedCollator)object2).getNumericCollation()) {
            this.collator_ = object2;
            this.strength_ = ((RuleBasedCollator)object2).getStrength();
            this.ceMask_ = StringSearch.getMask(this.strength_);
            this.toShift_ = ((RuleBasedCollator)object2).isAlternateHandlingShifted();
            this.variableTop_ = ((RuleBasedCollator)object2).getVariableTop();
            this.nfd_ = Normalizer2.getNFDInstance();
            this.pattern_ = new Pattern((String)object);
            this.search_.setMatchedLength(0);
            this.search_.matchedIndex_ = -1;
            this.utilIter_ = null;
            this.textIter_ = new CollationElementIterator(characterIterator, (RuleBasedCollator)object2);
            this.textProcessedIter_ = null;
            object = ((RuleBasedCollator)object2).getLocale(ULocale.VALID_LOCALE);
            object2 = this.search_;
            if (object == null) {
                object = ULocale.ROOT;
            }
            ((SearchIterator.Search)object2).internalBreakIter_ = BreakIterator.getCharacterInstance((ULocale)object);
            this.search_.internalBreakIter_.setText((CharacterIterator)characterIterator.clone());
            this.initialize();
            return;
        }
        throw new UnsupportedOperationException("Numeric collation is not supported by StringSearch");
    }

    public StringSearch(String string, CharacterIterator characterIterator, ULocale uLocale) {
        this(string, characterIterator, (RuleBasedCollator)Collator.getInstance(uLocale), null);
    }

    public StringSearch(String string, CharacterIterator characterIterator, Locale locale) {
        this(string, characterIterator, ULocale.forLocale(locale));
    }

    static /* synthetic */ Pattern access$500(StringSearch stringSearch) {
        return stringSearch.pattern_;
    }

    private static int[] addToIntArray(int[] arrn, int n, int n2, int n3) {
        int n4 = arrn.length;
        int[] arrn2 = arrn;
        if (n + 1 == n4) {
            arrn2 = new int[n4 + n3];
            System.arraycopy(arrn, 0, arrn2, 0, n);
        }
        arrn2[n] = n2;
        return arrn2;
    }

    private static long[] addToLongArray(long[] arrl, int n, int n2, long l, int n3) {
        long[] arrl2 = arrl;
        if (n + 1 == n2) {
            arrl2 = new long[n2 + n3];
            System.arraycopy(arrl, 0, arrl2, 0, n);
        }
        arrl2[n] = l;
        return arrl2;
    }

    private boolean checkIdentical(int n, int n2) {
        String string;
        String string2;
        if (this.strength_ != 15) {
            return true;
        }
        String string3 = string = StringSearch.getString(this.targetText, n, n2 - n);
        if (Normalizer.quickCheck(string, Normalizer.NFD, 0) == Normalizer.NO) {
            string3 = Normalizer.decompose(string, false);
        }
        string = string2 = this.pattern_.text_;
        if (Normalizer.quickCheck(string2, Normalizer.NFD, 0) == Normalizer.NO) {
            string = Normalizer.decompose(string2, false);
        }
        return string3.equals(string);
    }

    private static int codePointAt(CharacterIterator characterIterator, int n) {
        char c;
        int n2 = characterIterator.getIndex();
        char c2 = c = characterIterator.setIndex(n);
        n = c2;
        if (Character.isHighSurrogate(c)) {
            char c3 = characterIterator.next();
            n = c2;
            if (Character.isLowSurrogate(c3)) {
                n = Character.toCodePoint(c, c3);
            }
        }
        characterIterator.setIndex(n2);
        return n;
    }

    private static int codePointBefore(CharacterIterator characterIterator, int n) {
        char c;
        int n2 = characterIterator.getIndex();
        characterIterator.setIndex(n);
        char c2 = c = characterIterator.previous();
        n = c2;
        if (Character.isLowSurrogate(c)) {
            char c3 = characterIterator.previous();
            n = c2;
            if (Character.isHighSurrogate(c3)) {
                n = Character.toCodePoint(c3, c);
            }
        }
        characterIterator.setIndex(n2);
        return n;
    }

    private static int compareCE64s(long l, long l2, SearchIterator.ElementComparisonType elementComparisonType) {
        int n = -1;
        if (l == l2) {
            return -1;
        }
        if (elementComparisonType == SearchIterator.ElementComparisonType.STANDARD_ELEMENT_COMPARISON) {
            return 0;
        }
        long l3 = l >>> 32;
        int n2 = (int)(l3 & 0xFFFF0000L);
        long l4 = l2 >>> 32;
        int n3 = (int)(l4 & 0xFFFF0000L);
        if (n2 != n3) {
            if (n2 == 0) {
                return 1;
            }
            if (n3 == 0 && elementComparisonType == SearchIterator.ElementComparisonType.ANY_BASE_WEIGHT_IS_WILDCARD) {
                return 2;
            }
            return 0;
        }
        n3 = (int)(l3 & 65535L);
        n2 = (int)(l4 & 65535L);
        if (n3 != n2) {
            if (n3 == 0) {
                return 1;
            }
            if (n2 == 0 && elementComparisonType == SearchIterator.ElementComparisonType.ANY_BASE_WEIGHT_IS_WILDCARD) {
                return 2;
            }
            if (n2 != CE_LEVEL2_BASE && (elementComparisonType != SearchIterator.ElementComparisonType.ANY_BASE_WEIGHT_IS_WILDCARD || n3 != CE_LEVEL2_BASE)) {
                n = 0;
            }
            return n;
        }
        n2 = (int)(l & 0xFFFF0000L);
        n3 = (int)(l2 & 0xFFFF0000L);
        if (n2 != n3) {
            if (n3 != CE_LEVEL3_BASE && (elementComparisonType != SearchIterator.ElementComparisonType.ANY_BASE_WEIGHT_IS_WILDCARD || n2 != CE_LEVEL3_BASE)) {
                n = 0;
            }
            return n;
        }
        return -1;
    }

    private int getCE(int n) {
        int n2 = n & this.ceMask_;
        if (this.toShift_) {
            n = n2;
            if (this.variableTop_ > n2) {
                n = this.strength_ >= 3 ? n2 & -65536 : 0;
            }
        } else {
            n = n2;
            if (this.strength_ >= 3) {
                n = n2;
                if (n2 == 0) {
                    n = 65535;
                }
            }
        }
        return n;
    }

    private static int getMask(int n) {
        if (n != 0) {
            if (n != 1) {
                return -1;
            }
            return -256;
        }
        return -65536;
    }

    private static final String getString(CharacterIterator characterIterator, int n, int n2) {
        StringBuilder stringBuilder = new StringBuilder(n2);
        int n3 = characterIterator.getIndex();
        characterIterator.setIndex(n);
        for (n = 0; n < n2; ++n) {
            stringBuilder.append(characterIterator.current());
            characterIterator.next();
        }
        characterIterator.setIndex(n3);
        return stringBuilder.toString();
    }

    private boolean handleNextCanonical() {
        return this.handleNextCommonImpl();
    }

    private boolean handleNextCommonImpl() {
        Match match;
        int n = this.textIter_.getOffset();
        if (this.search(n, match = new Match())) {
            this.search_.matchedIndex_ = match.start_;
            this.search_.setMatchedLength(match.limit_ - match.start_);
            return true;
        }
        this.setMatchNotFound();
        return false;
    }

    private boolean handleNextExact() {
        return this.handleNextCommonImpl();
    }

    private boolean handlePreviousCanonical() {
        return this.handlePreviousCommonImpl();
    }

    private boolean handlePreviousCommonImpl() {
        int n;
        if (this.search_.isOverlap_) {
            if (this.search_.matchedIndex_ != -1) {
                n = this.search_.matchedIndex_ + this.search_.matchedLength() - 1;
            } else {
                this.initializePatternPCETable();
                if (!this.initTextProcessedIter()) {
                    this.setMatchNotFound();
                    return false;
                }
                for (n = 0; n < this.pattern_.PCELength_ - 1 && this.textProcessedIter_.nextProcessed(null) != -1L; ++n) {
                }
                n = this.textIter_.getOffset();
            }
        } else {
            n = this.textIter_.getOffset();
        }
        Match match = new Match();
        if (this.searchBackwards(n, match)) {
            this.search_.matchedIndex_ = match.start_;
            this.search_.setMatchedLength(match.limit_ - match.start_);
            return true;
        }
        this.setMatchNotFound();
        return false;
    }

    private boolean handlePreviousExact() {
        return this.handlePreviousCommonImpl();
    }

    private boolean initTextProcessedIter() {
        CollationPCE collationPCE = this.textProcessedIter_;
        if (collationPCE == null) {
            this.textProcessedIter_ = new CollationPCE(this.textIter_);
        } else {
            collationPCE.init(this.textIter_);
        }
        return true;
    }

    private void initialize() {
        this.initializePattern();
    }

    private int initializePattern() {
        this.pattern_.PCE_ = null;
        return this.initializePatternCETable();
    }

    private int initializePatternCETable() {
        int n;
        int[] arrn = new int[256];
        int n2 = this.pattern_.text_.length();
        Object object = this.utilIter_;
        if (object == null) {
            object = new CollationElementIterator(this.pattern_.text_, this.collator_);
            this.utilIter_ = object;
        } else {
            ((CollationElementIterator)object).setText(this.pattern_.text_);
        }
        int n3 = 0;
        int n4 = 0;
        while ((n = ((CollationElementIterator)object).next()) != -1) {
            int n5 = this.getCE(n);
            int[] arrn2 = arrn;
            int n6 = n3;
            if (n5 != 0) {
                arrn2 = StringSearch.addToIntArray(arrn, n3, n5, n2 - ((CollationElementIterator)object).getOffset() + 1);
                n6 = n3 + 1;
            }
            n4 += ((CollationElementIterator)object).getMaxExpansion(n) - 1;
            arrn = arrn2;
            n3 = n6;
        }
        arrn[n3] = 0;
        object = this.pattern_;
        ((Pattern)object).CE_ = arrn;
        ((Pattern)object).CELength_ = n3;
        return n4;
    }

    private int initializePatternPCETable() {
        long l;
        long[] arrl = new long[256];
        int n = arrl.length;
        int n2 = this.pattern_.text_.length();
        Object object = this.utilIter_;
        if (object == null) {
            object = new CollationElementIterator(this.pattern_.text_, this.collator_);
            this.utilIter_ = object;
        } else {
            ((CollationElementIterator)object).setText(this.pattern_.text_);
        }
        CollationPCE collationPCE = new CollationPCE((CollationElementIterator)object);
        int n3 = 0;
        while ((l = collationPCE.nextProcessed(null)) != -1L) {
            arrl = StringSearch.addToLongArray(arrl, n3, n, l, n2 - ((CollationElementIterator)object).getOffset() + 1);
            ++n3;
        }
        arrl[n3] = 0L;
        object = this.pattern_;
        ((Pattern)object).PCE_ = arrl;
        ((Pattern)object).PCELength_ = n3;
        return 0;
    }

    private boolean isBreakBoundary(int n) {
        BreakIterator breakIterator;
        BreakIterator breakIterator2 = breakIterator = this.search_.breakIter();
        if (breakIterator == null) {
            breakIterator2 = this.search_.internalBreakIter_;
        }
        boolean bl = breakIterator2 != null && breakIterator2.isBoundary(n);
        return bl;
    }

    private static final boolean isOutOfBounds(int n, int n2, int n3) {
        boolean bl = n3 < n || n3 > n2;
        return bl;
    }

    private int nextBoundaryAfter(int n) {
        BreakIterator breakIterator;
        BreakIterator breakIterator2 = breakIterator = this.search_.breakIter();
        if (breakIterator == null) {
            breakIterator2 = this.search_.internalBreakIter_;
        }
        if (breakIterator2 != null) {
            return breakIterator2.following(n);
        }
        return n;
    }

    private boolean search(int n, Match object) {
        block32 : {
            if (this.pattern_.CELength_ == 0 || n < this.search_.beginIndex() || n > this.search_.endIndex()) break block32;
            if (this.pattern_.PCE_ == null) {
                this.initializePatternPCETable();
            }
            this.textIter_.setOffset(n);
            CEBuffer cEBuffer = new CEBuffer(this);
            CEI cEI = null;
            int n2 = -1;
            n = -1;
            int n3 = 0;
            do {
                block33 : {
                    int n4;
                    block38 : {
                        boolean bl;
                        block34 : {
                            int n5;
                            block37 : {
                                block35 : {
                                    int n6;
                                    CEI cEI2;
                                    block36 : {
                                        CEI cEI3;
                                        CEI cEI4;
                                        block30 : {
                                            block31 : {
                                                bl = true;
                                                n6 = 0;
                                                long l = 0L;
                                                cEI3 = cEBuffer.get(n3);
                                                if (cEI3 == null) break;
                                                n4 = 0;
                                                while (n4 < this.pattern_.PCELength_) {
                                                    l = this.pattern_.PCE_[n4];
                                                    cEI = cEBuffer.get(n3 + n4 + n6);
                                                    int n7 = StringSearch.compareCE64s(cEI.ce_, l, this.search_.elementComparisonType_);
                                                    if (n7 == 0) {
                                                        bl = false;
                                                        break;
                                                    }
                                                    n5 = n6;
                                                    int n8 = n4;
                                                    if (n7 > 0) {
                                                        if (n7 == 1) {
                                                            n8 = n4 - 1;
                                                            n5 = n6 + 1;
                                                        } else {
                                                            n5 = n6 - 1;
                                                            n8 = n4;
                                                        }
                                                    }
                                                    n4 = n8 + 1;
                                                    n6 = n5;
                                                }
                                                n5 = n6 + this.pattern_.PCELength_;
                                                if (!bl && (cEI == null || cEI.ce_ != -1L)) break block33;
                                                if (!bl) break block34;
                                                cEI2 = cEBuffer.get(n3 + n5 - 1);
                                                n4 = cEI3.lowIndex_;
                                                n6 = cEI2.lowIndex_;
                                                if (this.search_.elementComparisonType_ == SearchIterator.ElementComparisonType.STANDARD_ELEMENT_COMPARISON) {
                                                    cEI4 = cEBuffer.get(n3 + n5);
                                                    n = cEI4.lowIndex_;
                                                    if (cEI4.lowIndex_ == cEI4.highIndex_ && cEI4.ce_ != -1L) {
                                                        bl = false;
                                                    }
                                                } else {
                                                    n2 = n5;
                                                    do {
                                                        cEI4 = cEBuffer.get(n3 + n2);
                                                        n = cEI4.lowIndex_;
                                                        if (cEI4.ce_ == -1L) break block30;
                                                        if ((cEI4.ce_ >>> 32 & 0xFFFF0000L) != 0L) break block31;
                                                        long l2 = cEI4.ce_;
                                                        n5 = StringSearch.compareCE64s(l2, l, this.search_.elementComparisonType_);
                                                        if (n5 == 0 || n5 == 2) break;
                                                        ++n2;
                                                    } while (true);
                                                    bl = false;
                                                }
                                                break block30;
                                            }
                                            CEBuffer cEBuffer2 = cEBuffer;
                                            if (cEI4.lowIndex_ == cEI4.highIndex_) {
                                                bl = false;
                                                cEBuffer = cEBuffer2;
                                            }
                                        }
                                        if (!this.isBreakBoundary(n4)) {
                                            bl = false;
                                        }
                                        if (n4 == cEI3.highIndex_) {
                                            bl = false;
                                        }
                                        n5 = this.breakIterator == null && (cEI4.ce_ >>> 32 & 0xFFFF0000L) != 0L && n >= cEI2.highIndex_ && cEI4.highIndex_ > n && (this.nfd_.hasBoundaryBefore(StringSearch.codePointAt(this.targetText, n)) || this.nfd_.hasBoundaryAfter(StringSearch.codePointBefore(this.targetText, n))) ? 1 : 0;
                                        if (n6 >= n) break block35;
                                        if (n6 != cEI2.highIndex_ || !this.isBreakBoundary(n6)) break block36;
                                        n2 = n6;
                                        break block37;
                                    }
                                    n2 = this.nextBoundaryAfter(n6);
                                    if (n2 >= cEI2.highIndex_ && (n5 == 0 || n2 < n)) break block37;
                                }
                                n2 = n;
                            }
                            if (n5 == 0) {
                                if (n2 > n) {
                                    bl = false;
                                }
                                if (!this.isBreakBoundary(n2)) {
                                    bl = false;
                                }
                            }
                            if (!this.checkIdentical(n4, n2)) {
                                bl = false;
                            }
                            if (!bl) break block38;
                            n = n2;
                            n2 = n4;
                        }
                        if (!bl) {
                            n2 = -1;
                            n = -1;
                        }
                        if (object != null) {
                            ((Match)object).start_ = n2;
                            ((Match)object).limit_ = n;
                        }
                        return bl;
                    }
                    n = n2;
                    n2 = n4;
                }
                ++n3;
            } while (true);
            object = new StringBuilder();
            ((StringBuilder)object).append("CEBuffer.get(");
            ((StringBuilder)object).append(n3);
            ((StringBuilder)object).append(") returned null.");
            throw new ICUException(((StringBuilder)object).toString());
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("search(");
        ((StringBuilder)object).append(n);
        ((StringBuilder)object).append(", m) - expected position to be between ");
        ((StringBuilder)object).append(this.search_.beginIndex());
        ((StringBuilder)object).append(" and ");
        ((StringBuilder)object).append(this.search_.endIndex());
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    private boolean searchBackwards(int n, Match object) {
        block27 : {
            if (this.pattern_.CELength_ == 0 || n < this.search_.beginIndex() || n > this.search_.endIndex()) break block27;
            if (this.pattern_.PCE_ == null) {
                this.initializePatternPCETable();
            }
            CEBuffer cEBuffer = new CEBuffer(this);
            int n2 = 0;
            if (n < this.search_.endIndex()) {
                n2 = this.search_.internalBreakIter_.following(n);
                this.textIter_.setOffset(n2);
                n2 = 0;
                while (cEBuffer.getPrevious((int)n2).lowIndex_ >= n) {
                    ++n2;
                }
            } else {
                this.textIter_.setOffset(n);
            }
            CEI cEI = null;
            int n3 = n2;
            int n4 = -1;
            int n5 = -1;
            int n6 = n2;
            do {
                block29 : {
                    block32 : {
                        boolean bl;
                        block31 : {
                            CEI cEI2;
                            int n7;
                            int n8;
                            block30 : {
                                block28 : {
                                    bl = true;
                                    cEI2 = cEBuffer.getPrevious(n6);
                                    if (cEI2 == null) break;
                                    n7 = 0;
                                    n8 = this.pattern_.PCELength_ - 1;
                                    n2 = n4;
                                    while (n8 >= 0) {
                                        long l = this.pattern_.PCE_[n8];
                                        cEI = cEBuffer.getPrevious(this.pattern_.PCELength_ + n6 - 1 - n8 + n7);
                                        int n9 = StringSearch.compareCE64s(cEI.ce_, l, this.search_.elementComparisonType_);
                                        if (n9 == 0) {
                                            bl = false;
                                            break;
                                        }
                                        n4 = n7;
                                        int n10 = n8;
                                        if (n9 > 0) {
                                            if (n9 == 1) {
                                                n10 = n8 + 1;
                                                n4 = n7 + 1;
                                            } else {
                                                n4 = n7 - 1;
                                                n10 = n8;
                                            }
                                        }
                                        n8 = n10 - 1;
                                        n7 = n4;
                                    }
                                    if (bl || cEI != null && cEI.ce_ == -1L) break block28;
                                    n4 = n2;
                                    break block29;
                                }
                                if (bl) break block30;
                                n = n2;
                                n2 = n5;
                                break block31;
                            }
                            n2 = this.pattern_.PCELength_;
                            n4 = 1;
                            CEI cEI3 = cEBuffer.getPrevious(n2 + n6 - 1 + n7);
                            n5 = cEI3.lowIndex_;
                            if (!this.isBreakBoundary(n5)) {
                                bl = false;
                            }
                            if (n5 == cEI3.highIndex_) {
                                bl = false;
                            }
                            n2 = cEI2.lowIndex_;
                            if (n6 > 0) {
                                cEI3 = cEBuffer.getPrevious(n6 - 1);
                                if (cEI3.lowIndex_ == cEI3.highIndex_ && cEI3.ce_ != -1L) {
                                    bl = false;
                                }
                                n8 = cEI3.lowIndex_;
                                if (this.breakIterator != null || (cEI3.ce_ >>> 32 & 0xFFFF0000L) == 0L || n8 < cEI2.highIndex_ || cEI3.highIndex_ <= n8 || !this.nfd_.hasBoundaryBefore(StringSearch.codePointAt(this.targetText, n8)) && !this.nfd_.hasBoundaryAfter(StringSearch.codePointBefore(this.targetText, n8))) {
                                    n4 = 0;
                                }
                                if (n2 >= n8 || (n2 = this.nextBoundaryAfter(n2)) < cEI2.highIndex_ || n4 != 0 && n2 >= n8) {
                                    n2 = n8;
                                }
                                boolean bl2 = bl;
                                if (n4 == 0) {
                                    if (n2 > n8) {
                                        bl = false;
                                    }
                                    bl2 = bl;
                                    if (!this.isBreakBoundary(n2)) {
                                        bl2 = false;
                                    }
                                }
                                bl = bl2;
                            } else if ((n2 = this.nextBoundaryAfter(n2)) <= 0 || n <= n2) {
                                n2 = n;
                            }
                            if (!this.checkIdentical(n5, n2)) {
                                bl = false;
                            }
                            if (!bl) break block32;
                            n = n5;
                        }
                        if (!bl) {
                            n2 = -1;
                            n = -1;
                        }
                        if (object != null) {
                            ((Match)object).start_ = n;
                            ((Match)object).limit_ = n2;
                        }
                        return bl;
                    }
                    n4 = n5;
                    n5 = n2;
                }
                ++n6;
            } while (true);
            object = new StringBuilder();
            ((StringBuilder)object).append("CEBuffer.getPrevious(");
            ((StringBuilder)object).append(n6);
            ((StringBuilder)object).append(") returned null.");
            throw new ICUException(((StringBuilder)object).toString());
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("searchBackwards(");
        ((StringBuilder)object).append(n);
        ((StringBuilder)object).append(", m) - expected position to be between ");
        ((StringBuilder)object).append(this.search_.beginIndex());
        ((StringBuilder)object).append(" and ");
        ((StringBuilder)object).append(this.search_.endIndex());
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public RuleBasedCollator getCollator() {
        return this.collator_;
    }

    @Override
    public int getIndex() {
        int n = this.textIter_.getOffset();
        if (StringSearch.isOutOfBounds(this.search_.beginIndex(), this.search_.endIndex(), n)) {
            return -1;
        }
        return n;
    }

    public String getPattern() {
        return this.pattern_.text_;
    }

    @Override
    protected int handleNext(int n) {
        if (this.pattern_.CELength_ == 0) {
            SearchIterator.Search search = this.search_;
            n = this.search_.matchedIndex_ == -1 ? this.getIndex() : this.search_.matchedIndex_ + 1;
            search.matchedIndex_ = n;
            this.search_.setMatchedLength(0);
            this.textIter_.setOffset(this.search_.matchedIndex_);
            if (this.search_.matchedIndex_ == this.search_.endIndex()) {
                this.search_.matchedIndex_ = -1;
            }
            return -1;
        }
        if (this.search_.matchedLength() <= 0) {
            this.search_.matchedIndex_ = n - 1;
        }
        this.textIter_.setOffset(n);
        if (this.search_.isCanonicalMatch_) {
            this.handleNextCanonical();
        } else {
            this.handleNextExact();
        }
        if (this.search_.matchedIndex_ == -1) {
            this.textIter_.setOffset(this.search_.endIndex());
        } else {
            this.textIter_.setOffset(this.search_.matchedIndex_);
        }
        return this.search_.matchedIndex_;
    }

    @Override
    protected int handlePrevious(int n) {
        if (this.pattern_.CELength_ == 0) {
            SearchIterator.Search search = this.search_;
            n = this.search_.matchedIndex_ == -1 ? this.getIndex() : this.search_.matchedIndex_;
            search.matchedIndex_ = n;
            if (this.search_.matchedIndex_ == this.search_.beginIndex()) {
                this.setMatchNotFound();
            } else {
                search = this.search_;
                --search.matchedIndex_;
                this.textIter_.setOffset(this.search_.matchedIndex_);
                this.search_.setMatchedLength(0);
            }
        } else {
            this.textIter_.setOffset(n);
            if (this.search_.isCanonicalMatch_) {
                this.handlePreviousCanonical();
            } else {
                this.handlePreviousExact();
            }
        }
        return this.search_.matchedIndex_;
    }

    public boolean isCanonical() {
        return this.search_.isCanonicalMatch_;
    }

    @Override
    public void reset() {
        boolean bl;
        int n;
        int n2;
        block8 : {
            block7 : {
                n = 1;
                int n3 = this.collator_.getStrength();
                if (this.strength_ < 3 && n3 >= 3) break block7;
                n2 = n;
                if (this.strength_ < 3) break block8;
                n2 = n;
                if (n3 >= 3) break block8;
            }
            n2 = 0;
        }
        this.strength_ = this.collator_.getStrength();
        n = StringSearch.getMask(this.strength_);
        if (this.ceMask_ != n) {
            this.ceMask_ = n;
            n2 = 0;
        }
        if (this.toShift_ != (bl = this.collator_.isAlternateHandlingShifted())) {
            this.toShift_ = bl;
            n2 = 0;
        }
        if (this.variableTop_ != (n = this.collator_.getVariableTop())) {
            this.variableTop_ = n;
            n2 = 0;
        }
        if (n2 == 0) {
            this.initialize();
        }
        this.textIter_.setText(this.search_.text());
        this.search_.setMatchedLength(0);
        this.search_.matchedIndex_ = -1;
        this.search_.isOverlap_ = false;
        this.search_.isCanonicalMatch_ = false;
        this.search_.elementComparisonType_ = SearchIterator.ElementComparisonType.STANDARD_ELEMENT_COMPARISON;
        this.search_.isForwardSearching_ = true;
        this.search_.reset_ = true;
    }

    public void setCanonical(boolean bl) {
        this.search_.isCanonicalMatch_ = bl;
    }

    public void setCollator(RuleBasedCollator ruleBasedCollator) {
        if (ruleBasedCollator != null) {
            this.collator_ = ruleBasedCollator;
            this.ceMask_ = StringSearch.getMask(this.collator_.getStrength());
            ULocale uLocale = ruleBasedCollator.getLocale(ULocale.VALID_LOCALE);
            SearchIterator.Search search = this.search_;
            if (uLocale == null) {
                uLocale = ULocale.ROOT;
            }
            search.internalBreakIter_ = BreakIterator.getCharacterInstance(uLocale);
            this.search_.internalBreakIter_.setText((CharacterIterator)this.search_.text().clone());
            this.toShift_ = ruleBasedCollator.isAlternateHandlingShifted();
            this.variableTop_ = ruleBasedCollator.getVariableTop();
            this.textIter_ = new CollationElementIterator(this.pattern_.text_, ruleBasedCollator);
            this.utilIter_ = new CollationElementIterator(this.pattern_.text_, ruleBasedCollator);
            this.initialize();
            return;
        }
        throw new IllegalArgumentException("Collator can not be null");
    }

    @Override
    public void setIndex(int n) {
        super.setIndex(n);
        this.textIter_.setOffset(n);
    }

    @Deprecated
    @Override
    protected void setMatchNotFound() {
        super.setMatchNotFound();
        if (this.search_.isForwardSearching_) {
            this.textIter_.setOffset(this.search_.text().getEndIndex());
        } else {
            this.textIter_.setOffset(0);
        }
    }

    public void setPattern(String string) {
        if (string != null && string.length() > 0) {
            this.pattern_.text_ = string;
            this.initialize();
            return;
        }
        throw new IllegalArgumentException("Pattern to search for can not be null or of length 0");
    }

    @Override
    public void setTarget(CharacterIterator characterIterator) {
        super.setTarget(characterIterator);
        this.textIter_.setText(characterIterator);
    }

    private static class CEBuffer {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        static final int CEBUFFER_EXTRA = 32;
        static final int MAX_TARGET_IGNORABLES_PER_PAT_JAMO_L = 8;
        static final int MAX_TARGET_IGNORABLES_PER_PAT_OTHER = 3;
        int bufSize_;
        CEI[] buf_;
        int firstIx_;
        int limitIx_;
        StringSearch strSearch_;

        CEBuffer(StringSearch stringSearch) {
            String string;
            this.strSearch_ = stringSearch;
            this.bufSize_ = StringSearch.access$500((StringSearch)stringSearch).PCELength_ + 32;
            if (stringSearch.search_.elementComparisonType_ != SearchIterator.ElementComparisonType.STANDARD_ELEMENT_COMPARISON && (string = StringSearch.access$500((StringSearch)stringSearch).text_) != null) {
                for (int i = 0; i < string.length(); ++i) {
                    if (CEBuffer.MIGHT_BE_JAMO_L(string.charAt(i))) {
                        this.bufSize_ += 8;
                        continue;
                    }
                    this.bufSize_ += 3;
                }
            }
            this.firstIx_ = 0;
            this.limitIx_ = 0;
            if (!stringSearch.initTextProcessedIter()) {
                return;
            }
            this.buf_ = new CEI[this.bufSize_];
        }

        static boolean MIGHT_BE_JAMO_L(char c) {
            boolean bl = c >= '\u1100' && c <= '\u115e' || c >= '\u3131' && c <= '\u314e' || c >= '\u3165' && c <= '\u3186';
            return bl;
        }

        CEI get(int n) {
            int n2 = n % this.bufSize_;
            if (n >= this.firstIx_ && n < this.limitIx_) {
                return this.buf_[n2];
            }
            int n3 = this.limitIx_;
            if (n != n3) {
                return null;
            }
            n = this.limitIx_ = n3 + 1;
            n3 = this.firstIx_;
            if (n - n3 >= this.bufSize_) {
                this.firstIx_ = n3 + 1;
            }
            CollationPCE.Range range = new CollationPCE.Range();
            CEI[] arrcEI = this.buf_;
            if (arrcEI[n2] == null) {
                arrcEI[n2] = new CEI();
            }
            this.buf_[n2].ce_ = this.strSearch_.textProcessedIter_.nextProcessed(range);
            this.buf_[n2].lowIndex_ = range.ixLow_;
            this.buf_[n2].highIndex_ = range.ixHigh_;
            return this.buf_[n2];
        }

        CEI getPrevious(int n) {
            int n2 = n % this.bufSize_;
            if (n >= this.firstIx_ && n < this.limitIx_) {
                return this.buf_[n2];
            }
            int n3 = this.limitIx_;
            if (n != n3) {
                return null;
            }
            this.limitIx_ = n3 + 1;
            n = this.firstIx_;
            if ((n3 = this.limitIx_) - n >= this.bufSize_) {
                this.firstIx_ = n + 1;
            }
            CollationPCE.Range range = new CollationPCE.Range();
            CEI[] arrcEI = this.buf_;
            if (arrcEI[n2] == null) {
                arrcEI[n2] = new CEI();
            }
            this.buf_[n2].ce_ = this.strSearch_.textProcessedIter_.previousProcessed(range);
            this.buf_[n2].lowIndex_ = range.ixLow_;
            this.buf_[n2].highIndex_ = range.ixHigh_;
            return this.buf_[n2];
        }
    }

    private static class CEI {
        long ce_;
        int highIndex_;
        int lowIndex_;

        private CEI() {
        }
    }

    private static class CollationPCE {
        private static final int BUFFER_GROW = 8;
        private static final int CONTINUATION_MARKER = 192;
        private static final int DEFAULT_BUFFER_SIZE = 16;
        private static final int PRIMARYORDERMASK = -65536;
        public static final long PROCESSED_NULLORDER = -1L;
        private CollationElementIterator cei_;
        private boolean isShifted_;
        private PCEBuffer pceBuffer_ = new PCEBuffer();
        private int strength_;
        private boolean toShift_;
        private int variableTop_;

        public CollationPCE(CollationElementIterator collationElementIterator) {
            this.init(collationElementIterator);
        }

        private void init(RuleBasedCollator ruleBasedCollator) {
            this.strength_ = ruleBasedCollator.getStrength();
            this.toShift_ = ruleBasedCollator.isAlternateHandlingShifted();
            this.isShifted_ = false;
            this.variableTop_ = ruleBasedCollator.getVariableTop();
        }

        private static boolean isContinuation(int n) {
            boolean bl = (n & 192) == 192;
            return bl;
        }

        private long processCE(int n) {
            long l;
            long l2 = 0L;
            long l3 = 0L;
            long l4 = 0L;
            int n2 = this.strength_;
            long l5 = l3;
            if (n2 != 0) {
                l5 = l3;
                if (n2 != 1) {
                    l5 = CollationElementIterator.tertiaryOrder(n);
                }
                l2 = CollationElementIterator.secondaryOrder(n);
            }
            l3 = CollationElementIterator.primaryOrder(n);
            if (this.toShift_ && this.variableTop_ > n && l3 != 0L || this.isShifted_ && l3 == 0L) {
                if (l3 == 0L) {
                    return 0L;
                }
                l2 = l4;
                if (this.strength_ >= 3) {
                    l2 = l3;
                }
                l = 0L;
                l5 = 0L;
                l3 = 0L;
                this.isShifted_ = true;
                l4 = l2;
            } else {
                if (this.strength_ >= 3) {
                    l4 = 65535L;
                }
                this.isShifted_ = false;
                l = l5;
                l5 = l2;
            }
            return l3 << 48 | l5 << 32 | l << 16 | l4;
        }

        public void init(CollationElementIterator collationElementIterator) {
            this.cei_ = collationElementIterator;
            this.init(collationElementIterator.getRuleBasedCollator());
        }

        public long nextProcessed(Range range) {
            long l;
            int n;
            int n2;
            int n3;
            this.pceBuffer_.reset();
            do {
                n2 = this.cei_.getOffset();
                n3 = this.cei_.next();
                n = this.cei_.getOffset();
                if (n3 != -1) continue;
                l = -1L;
                break;
            } while ((l = this.processCE(n3)) == 0L);
            if (range != null) {
                range.ixLow_ = n2;
                range.ixHigh_ = n;
            }
            return l;
        }

        public long previousProcessed(Range range) {
            Object object;
            while (this.pceBuffer_.empty()) {
                int n;
                RCEBuffer rCEBuffer = new RCEBuffer();
                boolean bl = false;
                do {
                    int n2 = this.cei_.getOffset();
                    n = this.cei_.previous();
                    int n3 = this.cei_.getOffset();
                    if (n == -1) {
                        if (!rCEBuffer.empty()) break;
                        bl = true;
                        break;
                    }
                    rCEBuffer.put(n, n3, n2);
                } while ((-65536 & n) == 0 || CollationPCE.isContinuation(n));
                if (bl) break;
                while (!rCEBuffer.empty()) {
                    object = rCEBuffer.get();
                    long l = this.processCE(((RCEI)object).ce_);
                    if (l == 0L) continue;
                    this.pceBuffer_.put(l, ((RCEI)object).low_, ((RCEI)object).high_);
                }
            }
            if (this.pceBuffer_.empty()) {
                if (range != null) {
                    range.ixLow_ = -1;
                    range.ixHigh_ = -1;
                }
                return -1L;
            }
            object = this.pceBuffer_.get();
            if (range != null) {
                range.ixLow_ = ((PCEI)object).low_;
                range.ixHigh_ = ((PCEI)object).high_;
            }
            return ((PCEI)object).ce_;
        }

        private static final class PCEBuffer {
            private int bufferIndex_ = 0;
            private PCEI[] buffer_ = new PCEI[16];

            private PCEBuffer() {
            }

            boolean empty() {
                boolean bl = this.bufferIndex_ <= 0;
                return bl;
            }

            PCEI get() {
                int n = this.bufferIndex_;
                if (n > 0) {
                    PCEI[] arrpCEI = this.buffer_;
                    this.bufferIndex_ = --n;
                    return arrpCEI[n];
                }
                return null;
            }

            void put(long l, int n, int n2) {
                int n3 = this.bufferIndex_;
                PCEI[] arrpCEI = this.buffer_;
                if (n3 >= arrpCEI.length) {
                    PCEI[] arrpCEI2 = new PCEI[arrpCEI.length + 8];
                    System.arraycopy(arrpCEI, 0, arrpCEI2, 0, arrpCEI.length);
                    this.buffer_ = arrpCEI2;
                }
                this.buffer_[this.bufferIndex_] = new PCEI();
                arrpCEI = this.buffer_;
                n3 = this.bufferIndex_;
                arrpCEI[n3].ce_ = l;
                arrpCEI[n3].low_ = n;
                arrpCEI[n3].high_ = n2;
                this.bufferIndex_ = n3 + 1;
            }

            void reset() {
                this.bufferIndex_ = 0;
            }
        }

        private static final class PCEI {
            long ce_;
            int high_;
            int low_;

            private PCEI() {
            }
        }

        private static final class RCEBuffer {
            private int bufferIndex_ = 0;
            private RCEI[] buffer_ = new RCEI[16];

            private RCEBuffer() {
            }

            boolean empty() {
                boolean bl = this.bufferIndex_ <= 0;
                return bl;
            }

            RCEI get() {
                int n = this.bufferIndex_;
                if (n > 0) {
                    RCEI[] arrrCEI = this.buffer_;
                    this.bufferIndex_ = --n;
                    return arrrCEI[n];
                }
                return null;
            }

            void put(int n, int n2, int n3) {
                RCEI[] arrrCEI;
                int n4 = this.bufferIndex_;
                RCEI[] arrrCEI2 = this.buffer_;
                if (n4 >= arrrCEI2.length) {
                    arrrCEI = new RCEI[arrrCEI2.length + 8];
                    System.arraycopy(arrrCEI2, 0, arrrCEI, 0, arrrCEI2.length);
                    this.buffer_ = arrrCEI;
                }
                this.buffer_[this.bufferIndex_] = new RCEI();
                arrrCEI = this.buffer_;
                n4 = this.bufferIndex_;
                arrrCEI[n4].ce_ = n;
                arrrCEI[n4].low_ = n2;
                arrrCEI[n4].high_ = n3;
                this.bufferIndex_ = n4 + 1;
            }
        }

        private static final class RCEI {
            int ce_;
            int high_;
            int low_;

            private RCEI() {
            }
        }

        public static final class Range {
            int ixHigh_;
            int ixLow_;
        }

    }

    private static class Match {
        int limit_ = -1;
        int start_ = -1;

        private Match() {
        }
    }

    private static final class Pattern {
        int CELength_ = 0;
        int[] CE_;
        int PCELength_ = 0;
        long[] PCE_;
        String text_;

        protected Pattern(String string) {
            this.text_ = string;
        }
    }

}

