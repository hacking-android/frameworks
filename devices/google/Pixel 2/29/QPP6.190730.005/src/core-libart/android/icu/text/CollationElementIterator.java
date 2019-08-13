/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.impl.CharacterIteratorWrapper;
import android.icu.impl.coll.CollationData;
import android.icu.impl.coll.CollationIterator;
import android.icu.impl.coll.CollationSettings;
import android.icu.impl.coll.CollationTailoring;
import android.icu.impl.coll.ContractionsAndExpansions;
import android.icu.impl.coll.FCDIterCollationIterator;
import android.icu.impl.coll.FCDUTF16CollationIterator;
import android.icu.impl.coll.IterCollationIterator;
import android.icu.impl.coll.SharedObject;
import android.icu.impl.coll.UTF16CollationIterator;
import android.icu.impl.coll.UVector32;
import android.icu.text.RuleBasedCollator;
import android.icu.text.UCharacterIterator;
import android.icu.text.UnicodeSet;
import java.text.CharacterIterator;
import java.util.HashMap;
import java.util.Map;

public final class CollationElementIterator {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int IGNORABLE = 0;
    public static final int NULLORDER = -1;
    private byte dir_;
    private CollationIterator iter_ = null;
    private UVector32 offsets_;
    private int otherHalf_;
    private RuleBasedCollator rbc_;
    private String string_;

    private CollationElementIterator(RuleBasedCollator ruleBasedCollator) {
        this.rbc_ = ruleBasedCollator;
        this.otherHalf_ = 0;
        this.dir_ = (byte)(false ? 1 : 0);
        this.offsets_ = null;
    }

    CollationElementIterator(UCharacterIterator uCharacterIterator, RuleBasedCollator ruleBasedCollator) {
        this(ruleBasedCollator);
        this.setText(uCharacterIterator);
    }

    CollationElementIterator(String string, RuleBasedCollator ruleBasedCollator) {
        this(ruleBasedCollator);
        this.setText(string);
    }

    CollationElementIterator(CharacterIterator characterIterator, RuleBasedCollator ruleBasedCollator) {
        this(ruleBasedCollator);
        this.setText(characterIterator);
    }

    private static final boolean ceNeedsTwoParts(long l) {
        boolean bl = (281470698455103L & l) != 0L;
        return bl;
    }

    static final Map<Integer, Integer> computeMaxExpansions(CollationData collationData) {
        HashMap<Integer, Integer> hashMap = new HashMap<Integer, Integer>();
        new ContractionsAndExpansions(null, null, new MaxExpSink(hashMap), true).forData(collationData);
        return hashMap;
    }

    private static final int getFirstHalf(long l, int n) {
        return (int)l & -65536 | n >> 16 & 65280 | n >> 8 & 255;
    }

    static int getMaxExpansion(Map<Integer, Integer> object, int n) {
        if (n == 0) {
            return 1;
        }
        if (object != null && (object = object.get(n)) != null) {
            return (Integer)object;
        }
        if ((n & 192) == 192) {
            return 2;
        }
        return 1;
    }

    private static final int getSecondHalf(long l, int n) {
        return (int)l << 16 | n >> 8 & 65280 | n & 63;
    }

    private byte normalizeDir() {
        byte by;
        byte by2 = by = this.dir_;
        if (by == 1) {
            by2 = by = 0;
        }
        return by2;
    }

    public static final int primaryOrder(int n) {
        return n >>> 16 & 65535;
    }

    public static final int secondaryOrder(int n) {
        return n >>> 8 & 255;
    }

    public static final int tertiaryOrder(int n) {
        return n & 255;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (object instanceof CollationElementIterator) {
            object = (CollationElementIterator)object;
            if (!(this.rbc_.equals(((CollationElementIterator)object).rbc_) && this.otherHalf_ == ((CollationElementIterator)object).otherHalf_ && this.normalizeDir() == CollationElementIterator.super.normalizeDir() && this.string_.equals(((CollationElementIterator)object).string_) && this.iter_.equals(((CollationElementIterator)object).iter_))) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    public int getMaxExpansion(int n) {
        return CollationElementIterator.getMaxExpansion(this.rbc_.tailoring.maxExpansions, n);
    }

    public int getOffset() {
        UVector32 uVector32;
        if (this.dir_ < 0 && (uVector32 = this.offsets_) != null && !uVector32.isEmpty()) {
            int n;
            int n2 = n = this.iter_.getCEsLength();
            if (this.otherHalf_ != 0) {
                n2 = n + 1;
            }
            return this.offsets_.elementAti(n2);
        }
        return this.iter_.getOffset();
    }

    @Deprecated
    public RuleBasedCollator getRuleBasedCollator() {
        return this.rbc_;
    }

    public int hashCode() {
        return 42;
    }

    public int next() {
        block10 : {
            int n;
            block8 : {
                block9 : {
                    block7 : {
                        n = this.dir_;
                        if (n <= 1) break block7;
                        if (this.otherHalf_ != 0) {
                            n = this.otherHalf_;
                            this.otherHalf_ = 0;
                            return n;
                        }
                        break block8;
                    }
                    if (n != 1) break block9;
                    this.dir_ = (byte)2;
                    break block8;
                }
                if (n != 0) break block10;
                this.dir_ = (byte)2;
            }
            this.iter_.clearCEsIfNoneRemaining();
            long l = this.iter_.nextCE();
            if (l == 0x101000100L) {
                return -1;
            }
            long l2 = l >>> 32;
            int n2 = (int)l;
            n = CollationElementIterator.getFirstHalf(l2, n2);
            if ((n2 = CollationElementIterator.getSecondHalf(l2, n2)) != 0) {
                this.otherHalf_ = n2 | 192;
            }
            return n;
        }
        throw new IllegalStateException("Illegal change of direction");
    }

    public int previous() {
        block13 : {
            int n;
            int n2;
            long l;
            block11 : {
                block12 : {
                    block10 : {
                        n = this.dir_;
                        n2 = 0;
                        if (n >= 0) break block10;
                        if (this.otherHalf_ != 0) {
                            n2 = this.otherHalf_;
                            this.otherHalf_ = 0;
                            return n2;
                        }
                        break block11;
                    }
                    if (n != 0) break block12;
                    this.iter_.resetToOffset(this.string_.length());
                    this.dir_ = (byte)-1;
                    break block11;
                }
                if (n != 1) break block13;
                this.dir_ = (byte)-1;
            }
            if (this.offsets_ == null) {
                this.offsets_ = new UVector32();
            }
            if (this.iter_.getCEsLength() == 0) {
                n2 = this.iter_.getOffset();
            }
            if ((l = this.iter_.previousCE(this.offsets_)) == 0x101000100L) {
                return -1;
            }
            long l2 = l >>> 32;
            int n3 = (int)l;
            n = CollationElementIterator.getFirstHalf(l2, n3);
            if ((n3 = CollationElementIterator.getSecondHalf(l2, n3)) != 0) {
                if (this.offsets_.isEmpty()) {
                    this.offsets_.addElement(this.iter_.getOffset());
                    this.offsets_.addElement(n2);
                }
                this.otherHalf_ = n;
                return n3 | 192;
            }
            return n;
        }
        throw new IllegalStateException("Illegal change of direction");
    }

    public void reset() {
        this.iter_.resetToOffset(0);
        this.otherHalf_ = 0;
        this.dir_ = (byte)(false ? 1 : 0);
    }

    public void setOffset(int n) {
        int n2 = n;
        if (n > 0) {
            n2 = n;
            if (n < this.string_.length()) {
                int n3;
                n2 = n;
                do {
                    char c = this.string_.charAt(n2);
                    n3 = n2;
                    if (!this.rbc_.isUnsafe(c)) break;
                    if (Character.isHighSurrogate(c) && !this.rbc_.isUnsafe(this.string_.codePointAt(n2))) {
                        n3 = n2;
                        break;
                    }
                    n2 = n3 = n2 - 1;
                } while (n3 > 0);
                n2 = n;
                if (n3 < n) {
                    int n4;
                    do {
                        this.iter_.resetToOffset(n3);
                        do {
                            this.iter_.nextCE();
                        } while ((n4 = this.iter_.getOffset()) == n3);
                        n2 = n3;
                        if (n4 <= n) {
                            n2 = n4;
                        }
                        n3 = n2;
                    } while (n4 < n);
                }
            }
        }
        this.iter_.resetToOffset(n2);
        this.otherHalf_ = 0;
        this.dir_ = (byte)(true ? 1 : 0);
    }

    public void setText(UCharacterIterator object) {
        this.string_ = ((UCharacterIterator)object).getText();
        try {
            UCharacterIterator uCharacterIterator = (UCharacterIterator)((UCharacterIterator)object).clone();
            uCharacterIterator.setToStart();
            boolean bl = this.rbc_.settings.readOnly().isNumeric();
            object = this.rbc_.settings.readOnly().dontCheckFCD() ? new IterCollationIterator(this.rbc_.data, bl, uCharacterIterator) : new FCDIterCollationIterator(this.rbc_.data, bl, uCharacterIterator, 0);
            this.iter_ = object;
            this.otherHalf_ = 0;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            this.setText(((UCharacterIterator)object).getText());
            return;
        }
        this.dir_ = (byte)(false ? 1 : 0);
    }

    public void setText(String object) {
        this.string_ = object;
        boolean bl = this.rbc_.settings.readOnly().isNumeric();
        object = this.rbc_.settings.readOnly().dontCheckFCD() ? new UTF16CollationIterator(this.rbc_.data, bl, this.string_, 0) : new FCDUTF16CollationIterator(this.rbc_.data, bl, this.string_, 0);
        this.iter_ = object;
        this.otherHalf_ = 0;
        this.dir_ = (byte)(false ? 1 : 0);
    }

    public void setText(CharacterIterator object) {
        object = new CharacterIteratorWrapper((CharacterIterator)object);
        ((UCharacterIterator)object).setToStart();
        this.string_ = ((UCharacterIterator)object).getText();
        boolean bl = this.rbc_.settings.readOnly().isNumeric();
        object = this.rbc_.settings.readOnly().dontCheckFCD() ? new IterCollationIterator(this.rbc_.data, bl, (UCharacterIterator)object) : new FCDIterCollationIterator(this.rbc_.data, bl, (UCharacterIterator)object, 0);
        this.iter_ = object;
        this.otherHalf_ = 0;
        this.dir_ = (byte)(false ? 1 : 0);
    }

    private static final class MaxExpSink
    implements ContractionsAndExpansions.CESink {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private Map<Integer, Integer> maxExpansions;

        MaxExpSink(Map<Integer, Integer> map) {
            this.maxExpansions = map;
        }

        @Override
        public void handleCE(long l) {
        }

        @Override
        public void handleExpansion(long[] object, int n, int n2) {
            if (n2 <= 1) {
                return;
            }
            int n3 = 0;
            for (int i = 0; i < n2; ++i) {
                int n4 = CollationElementIterator.ceNeedsTwoParts(object[n + i]) ? 2 : 1;
                n3 += n4;
            }
            long l = object[n + n2 - 1];
            long l2 = l >>> 32;
            n2 = (int)l;
            n = CollationElementIterator.getSecondHalf(l2, n2);
            n = n == 0 ? CollationElementIterator.getFirstHalf(l2, n2) : (n |= 192);
            object = this.maxExpansions.get(n);
            if (object == null || n3 > (Integer)object) {
                this.maxExpansions.put(n, n3);
            }
        }
    }

}

