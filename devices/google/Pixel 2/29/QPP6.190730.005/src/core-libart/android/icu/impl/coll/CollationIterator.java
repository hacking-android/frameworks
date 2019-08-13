/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.coll;

import android.icu.impl.Trie2_32;
import android.icu.impl.coll.Collation;
import android.icu.impl.coll.CollationData;
import android.icu.impl.coll.CollationFCD;
import android.icu.impl.coll.UVector32;
import android.icu.util.BytesTrie;
import android.icu.util.CharsTrie;
import android.icu.util.ICUException;

public abstract class CollationIterator {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    protected static final long NO_CP_AND_CE32 = -4294967104L;
    private CEBuffer ceBuffer;
    private int cesIndex;
    protected final CollationData data;
    private boolean isNumeric;
    private int numCpFwd;
    private SkippedState skipped;
    protected final Trie2_32 trie;

    public CollationIterator(CollationData collationData) {
        this.trie = collationData.trie;
        this.data = collationData;
        this.numCpFwd = -1;
        this.isNumeric = false;
        this.ceBuffer = null;
    }

    public CollationIterator(CollationData collationData, boolean bl) {
        this.trie = collationData.trie;
        this.data = collationData;
        this.numCpFwd = -1;
        this.isNumeric = bl;
        this.ceBuffer = new CEBuffer();
    }

    /*
     * Enabled aggressive block sorting
     */
    private final void appendNumericCEs(int n, boolean bl) {
        int n2;
        int n3;
        StringBuilder stringBuilder;
        block13 : {
            stringBuilder = new StringBuilder();
            n2 = n;
            if (bl) {
                do {
                    stringBuilder.append(Collation.digitFromCE32(n));
                    if (this.numCpFwd == 0 || (n3 = this.nextCodePoint()) < 0) break block13;
                    n = n2 = this.data.getCE32(n3);
                    if (n2 == 192) {
                        n = this.data.base.getCE32(n3);
                    }
                    if (!Collation.hasCE32Tag(n, 10)) {
                        this.backwardNumCodePoints(1);
                        break block13;
                    }
                    n2 = this.numCpFwd;
                    if (n2 <= 0) continue;
                    this.numCpFwd = n2 - 1;
                } while (true);
            }
            do {
                block16 : {
                    block15 : {
                        block14 : {
                            stringBuilder.append(Collation.digitFromCE32(n2));
                            n3 = this.previousCodePoint();
                            if (n3 >= 0) break block14;
                            n = n2;
                            break block15;
                        }
                        n = n2 = this.data.getCE32(n3);
                        if (n2 == 192) {
                            n = this.data.base.getCE32(n3);
                        }
                        if (Collation.hasCE32Tag(n, 10)) break block16;
                        this.forwardNumCodePoints(1);
                    }
                    stringBuilder.reverse();
                    break;
                }
                n2 = n;
            } while (true);
        }
        n = 0;
        do {
            if (n < stringBuilder.length() - 1 && stringBuilder.charAt(n) == '\u0000') {
                ++n;
                continue;
            }
            n2 = n3 = stringBuilder.length() - n;
            if (n3 > 254) {
                n2 = 254;
            }
            this.appendNumericSegmentCEs(stringBuilder.subSequence(n, n + n2));
            n = n2 = n + n2;
            if (n2 >= stringBuilder.length()) break;
        } while (true);
    }

    private final void appendNumericSegmentCEs(CharSequence charSequence) {
        int n;
        int n2;
        int n3 = charSequence.length();
        long l = this.data.numericPrimary;
        if (n3 <= 7) {
            n = charSequence.charAt(0);
            for (n2 = 1; n2 < n3; ++n2) {
                n = n * 10 + charSequence.charAt(n2);
            }
            if (n < 74) {
                long l2 = 2 + n << 16;
                this.ceBuffer.append(Collation.makeCE(l2 | l));
                return;
            }
            n2 = 2 + 74;
            if ((n -= 74) < 40 * 254) {
                long l3 = n / 254 + n2 << 16;
                long l4 = n % 254 + 2 << 8;
                this.ceBuffer.append(Collation.makeCE(l3 | l | l4));
                return;
            }
            if ((n -= 40 * 254) < 16 * 254 * 254) {
                long l5 = n % 254 + 2;
                long l6 = (n /= 254) % 254 + 2 << 8;
                long l7 = n / 254 % 254 + (n2 + 40) << 16;
                this.ceBuffer.append(Collation.makeCE(l5 | l | l6 | l7));
                return;
            }
        }
        long l8 = (long)((n3 + 1) / 2 + 128 << 16) | l;
        n = n3;
        while (charSequence.charAt(n - 1) == '\u0000' && charSequence.charAt(n - 2) == '\u0000') {
            n -= 2;
        }
        if ((n & 1) != 0) {
            n3 = charSequence.charAt(0);
            n2 = 1;
        } else {
            n3 = charSequence.charAt(0) * 10 + charSequence.charAt(1);
            n2 = 2;
        }
        int n4 = n3 * 2 + 11;
        int n5 = 8;
        n3 = n2;
        n2 = n5;
        while (n3 < n) {
            if (n2 == 0) {
                long l9 = n4;
                this.ceBuffer.append(Collation.makeCE(l8 | l9));
                l8 = l;
                n2 = 16;
            } else {
                l8 |= (long)(n4 << n2);
                n2 -= 8;
            }
            n4 = (charSequence.charAt(n3) * 10 + charSequence.charAt(n3 + 1)) * 2 + 11;
            n3 += 2;
        }
        l = n4 - 1 << n2;
        this.ceBuffer.append(Collation.makeCE(l8 | l));
    }

    private final void backwardNumSkipped(int n) {
        SkippedState skippedState = this.skipped;
        int n2 = n;
        if (skippedState != null) {
            n2 = n;
            if (!skippedState.isEmpty()) {
                n2 = this.skipped.backwardNumCodePoints(n);
            }
        }
        this.backwardNumCodePoints(n2);
        n = this.numCpFwd;
        if (n >= 0) {
            this.numCpFwd = n + n2;
        }
    }

    private final int getCE32FromPrefix(CollationData object, int n) {
        int n2 = Collation.indexFromCE32(n);
        n = ((CollationData)object).getCE32FromContexts(n2);
        int n3 = 0;
        object = new CharsTrie(((CollationData)object).contexts, n2 + 2);
        while ((n2 = this.previousCodePoint()) >= 0) {
            ++n3;
            BytesTrie.Result result = ((CharsTrie)object).nextForCodePoint(n2);
            if (result.hasValue()) {
                n = ((CharsTrie)object).getValue();
            }
            if (result.hasNext()) continue;
        }
        this.forwardNumCodePoints(n3);
        return n;
    }

    protected static final boolean isLeadSurrogate(int n) {
        boolean bl = (n & -1024) == 55296;
        return bl;
    }

    private static final boolean isSurrogate(int n) {
        boolean bl = (n & -2048) == 55296;
        return bl;
    }

    protected static final boolean isTrailSurrogate(int n) {
        boolean bl = (n & -1024) == 56320;
        return bl;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private final int nextCE32FromContraction(CollationData collationData, int n, CharSequence object, int n2, int n3, int n4) {
        int n5 = 1;
        int n6 = 1;
        CharsTrie charsTrie = new CharsTrie((CharSequence)object, n2);
        object = this.skipped;
        if (object != null && !((SkippedState)object).isEmpty()) {
            this.skipped.saveTrieState(charsTrie);
        }
        object = charsTrie.firstForCodePoint(n4);
        int n7 = n3;
        n2 = n6;
        n3 = n5;
        do {
            if (((BytesTrie.Result)((Object)object)).hasValue()) {
                n7 = charsTrie.getValue();
                if (!((BytesTrie.Result)((Object)object)).hasNext()) return n7;
                n4 = n2 = this.nextSkippedCodePoint();
                if (n2 < 0) return n7;
                object = this.skipped;
                if (object != null && !((SkippedState)object).isEmpty()) {
                    this.skipped.saveTrieState(charsTrie);
                }
                n2 = 1;
            } else {
                if (object == BytesTrie.Result.NO_MATCH || (n5 = this.nextSkippedCodePoint()) < 0) break;
                n4 = n5;
                ++n2;
            }
            ++n3;
            object = charsTrie.nextForCodePoint(n4);
        } while (true);
        if ((n & 1024) != 0 && ((n & 256) == 0 || n2 < n3)) {
            if (n2 > 1) {
                this.backwardNumSkipped(n2);
                n4 = this.nextSkippedCodePoint();
                n2 = n3 - (n2 - 1);
                n = 1;
                n3 = n4;
            } else {
                n = n2;
                n2 = n3;
                n3 = n4;
            }
            if (collationData.getFCD16(n3) > 255) {
                return this.nextCE32FromDiscontiguousContraction(collationData, charsTrie, n7, n2, n3);
            }
        } else {
            n = n2;
        }
        this.backwardNumSkipped(n);
        return n7;
    }

    private final int nextCE32FromDiscontiguousContraction(CollationData collationData, CharsTrie charsTrie, int n, int n2, int n3) {
        int n4 = collationData.getFCD16(n3);
        int n5 = this.nextSkippedCodePoint();
        if (n5 < 0) {
            this.backwardNumSkipped(1);
            return n;
        }
        int n6 = n2 + 1;
        int n7 = n4 & 255;
        n4 = collationData.getFCD16(n5);
        if (n4 <= 255) {
            this.backwardNumSkipped(2);
            return n;
        }
        Object object = this.skipped;
        if (object != null && !((SkippedState)object).isEmpty()) {
            this.skipped.resetToTrieState(charsTrie);
        } else {
            if (this.skipped == null) {
                this.skipped = new SkippedState();
            }
            charsTrie.reset();
            if (n6 > 2) {
                this.backwardNumCodePoints(n6);
                charsTrie.firstForCodePoint(this.nextCodePoint());
                for (n2 = 3; n2 < n6; ++n2) {
                    charsTrie.nextForCodePoint(this.nextCodePoint());
                }
                this.forwardNumCodePoints(2);
            }
            this.skipped.saveTrieState(charsTrie);
        }
        this.skipped.setFirstSkipped(n3);
        n2 = 2;
        n3 = n7;
        do {
            if (n3 < n4 >> 8 && ((BytesTrie.Result)((Object)(object = charsTrie.nextForCodePoint(n5)))).hasValue()) {
                n = charsTrie.getValue();
                n5 = 0;
                n2 = 0;
                this.skipped.recordMatch();
                if (!((BytesTrie.Result)((Object)object)).hasNext()) {
                    n2 = n5;
                    break;
                }
                this.skipped.saveTrieState(charsTrie);
            } else {
                this.skipped.skip(n5);
                this.skipped.resetToTrieState(charsTrie);
                n3 = n4 & 255;
            }
            n5 = n4 = this.nextSkippedCodePoint();
            if (n4 < 0) break;
            ++n2;
        } while ((n4 = collationData.getFCD16(n5)) > 255);
        this.backwardNumSkipped(n2);
        boolean bl = this.skipped.isEmpty();
        this.skipped.replaceMatch();
        n2 = n;
        if (bl) {
            n2 = n;
            if (!this.skipped.isEmpty()) {
                n2 = -1;
                do {
                    this.appendCEsFromCE32(collationData, n2, n, true);
                    if (!this.skipped.hasNext()) {
                        this.skipped.clear();
                        n2 = 1;
                        break;
                    }
                    n2 = this.skipped.next();
                    n = this.getDataCE32(n2);
                    if (n == 192) {
                        collationData = this.data.base;
                        n = collationData.getCE32(n2);
                        continue;
                    }
                    collationData = this.data;
                } while (true);
            }
        }
        return n2;
    }

    private final long nextCEFromCE32(CollationData object, int n, int n2) {
        CEBuffer cEBuffer = this.ceBuffer;
        --cEBuffer.length;
        this.appendCEsFromCE32((CollationData)object, n, n2, true);
        object = this.ceBuffer;
        n = this.cesIndex;
        this.cesIndex = n + 1;
        return ((CEBuffer)object).get(n);
    }

    private final int nextSkippedCodePoint() {
        int n;
        SkippedState skippedState = this.skipped;
        if (skippedState != null && skippedState.hasNext()) {
            return this.skipped.next();
        }
        if (this.numCpFwd == 0) {
            return -1;
        }
        int n2 = this.nextCodePoint();
        skippedState = this.skipped;
        if (skippedState != null && !skippedState.isEmpty() && n2 >= 0) {
            this.skipped.incBeyond();
        }
        if ((n = this.numCpFwd) > 0 && n2 >= 0) {
            this.numCpFwd = n - 1;
        }
        return n2;
    }

    private final long previousCEUnsafe(int n, UVector32 object) {
        int n2;
        int n3;
        n = 1;
        do {
            n3 = this.previousCodePoint();
            n2 = n;
            if (n3 < 0) break;
            n = n2 = n + 1;
        } while (this.data.isUnsafeBackward(n3, this.isNumeric));
        this.numCpFwd = n2;
        this.cesIndex = 0;
        n = this.getOffset();
        block1 : while ((n3 = this.numCpFwd) > 0) {
            this.numCpFwd = n3 - 1;
            this.nextCE();
            this.cesIndex = this.ceBuffer.length;
            ((UVector32)object).addElement(n);
            n3 = this.getOffset();
            do {
                n = n3;
                if (((UVector32)object).size() >= this.ceBuffer.length) continue block1;
                ((UVector32)object).addElement(n3);
            } while (true);
        }
        ((UVector32)object).addElement(n);
        this.numCpFwd = -1;
        this.backwardNumCodePoints(n2);
        this.cesIndex = 0;
        object = this.ceBuffer;
        ((CEBuffer)object).length = n = ((CEBuffer)object).length - 1;
        return ((CEBuffer)object).get(n);
    }

    protected final void appendCEsFromCE32(CollationData object, int object2, int n, boolean bl) {
        int n2 = n;
        int n3 = object2;
        Object object3 = object;
        while (Collation.isSpecialCE32(n2)) {
            switch (Collation.tagFromCE32(n2)) {
                default: {
                    object = object3;
                    n = n3;
                    object2 = n2;
                    break;
                }
                case 15: {
                    if (CollationIterator.isSurrogate(n3) && this.forbidSurrogateCodePoints()) {
                        object2 = -195323;
                        object = object3;
                        n = n3;
                        break;
                    }
                    this.ceBuffer.append(Collation.unassignedCEFromCodePoint(n3));
                    return;
                }
                case 14: {
                    this.ceBuffer.append(((CollationData)object3).getCEFromOffsetCE32(n3, n2));
                    return;
                }
                case 13: {
                    char c = this.handleGetTrailSurrogate();
                    if (Character.isLowSurrogate(c)) {
                        n3 = Character.toCodePoint((char)n3, c);
                        object2 = n2 & 768;
                        if (object2 == 0) {
                            object2 = -1;
                            object = object3;
                            n = n3;
                            break;
                        }
                        if (object2 != 256) {
                            object2 = n2 = ((CollationData)object3).getCE32FromSupplementary(n3);
                            object = object3;
                            n = n3;
                            if (n2 != 192) break;
                        }
                        object = ((CollationData)object3).base;
                        object2 = ((CollationData)object).getCE32FromSupplementary(n3);
                        n = n3;
                        break;
                    }
                    object2 = -1;
                    object = object3;
                    n = n3;
                    break;
                }
                case 12: {
                    object = ((CollationData)object3).jamoCE32s;
                    object2 = n3 - 44032;
                    n = object2 % 28;
                    n3 = (object2 /= 28) % 21;
                    int n4 = object2 / 21;
                    if ((n2 & 256) != 0) {
                        object3 = this.ceBuffer;
                        object2 = n == 0 ? 2 : 3;
                        ((CEBuffer)object3).ensureAppendCapacity((int)object2);
                        object3 = this.ceBuffer;
                        ((CEBuffer)object3).set(((CEBuffer)object3).length, Collation.ceFromCE32((int)object[n4]));
                        object3 = this.ceBuffer;
                        ((CEBuffer)object3).set(((CEBuffer)object3).length + 1, Collation.ceFromCE32((int)object[n3 + 19]));
                        object3 = this.ceBuffer;
                        ((CEBuffer)object3).length += 2;
                        if (n != 0) {
                            this.ceBuffer.appendUnsafe(Collation.ceFromCE32((int)object[n + 39]));
                        }
                        return;
                    }
                    this.appendCEsFromCE32((CollationData)object3, -1, (int)object[n4], bl);
                    this.appendCEsFromCE32((CollationData)object3, -1, (int)object[n3 + 19], bl);
                    if (n == 0) {
                        return;
                    }
                    object2 = object[n + 39];
                    n = -1;
                    object = object3;
                    break;
                }
                case 11: {
                    object2 = ((CollationData)object3).ce32s[0];
                    object = object3;
                    n = n3;
                    break;
                }
                case 10: {
                    if (this.isNumeric) {
                        this.appendNumericCEs(n2, bl);
                        return;
                    }
                    object2 = ((CollationData)object3).ce32s[Collation.indexFromCE32(n2)];
                    object = object3;
                    n = n3;
                    break;
                }
                case 9: {
                    int n4;
                    int n5 = Collation.indexFromCE32(n2);
                    object2 = ((CollationData)object3).getCE32FromContexts(n5);
                    if (!bl) {
                        object = object3;
                        n = n3;
                        break;
                    }
                    if (this.skipped == null && this.numCpFwd < 0) {
                        n4 = this.nextCodePoint();
                        if (n4 < 0) {
                            object = object3;
                            n = n3;
                            break;
                        }
                        n = n4;
                        if ((n2 & 512) != 0) {
                            n = n4;
                            if (!CollationFCD.mayHaveLccc(n4)) {
                                this.backwardNumCodePoints(1);
                                object = object3;
                                n = n3;
                                break;
                            }
                        }
                    } else {
                        n4 = this.nextSkippedCodePoint();
                        if (n4 < 0) {
                            object = object3;
                            n = n3;
                            break;
                        }
                        n = n4;
                        if ((n2 & 512) != 0) {
                            n = n4;
                            if (!CollationFCD.mayHaveLccc(n4)) {
                                this.backwardNumSkipped(1);
                                object = object3;
                                n = n3;
                                break;
                            }
                        }
                    }
                    n2 = this.nextCE32FromContraction((CollationData)object3, n2, ((CollationData)object3).contexts, n5 + 2, (int)object2, n);
                    object = object3;
                    n = n3;
                    object2 = n2;
                    if (n2 != 1) break;
                    return;
                }
                case 8: {
                    if (bl) {
                        this.backwardNumCodePoints(1);
                    }
                    n2 = this.getCE32FromPrefix((CollationData)object3, n2);
                    object = object3;
                    n = n3;
                    object2 = n2;
                    if (!bl) break;
                    this.forwardNumCodePoints(1);
                    object = object3;
                    n = n3;
                    object2 = n2;
                    break;
                }
                case 7: {
                    n2 = this.getCE32FromBuilderData(n2);
                    object = object3;
                    n = n3;
                    object2 = n2;
                    if (n2 != 192) break;
                    object = this.data.base;
                    object2 = ((CollationData)object).getCE32(n3);
                    n = n3;
                    break;
                }
                case 6: {
                    n = Collation.indexFromCE32(n2);
                    object2 = Collation.lengthFromCE32(n2);
                    this.ceBuffer.ensureAppendCapacity((int)object2);
                    do {
                        this.ceBuffer.appendUnsafe(((CollationData)object3).ces[n]);
                        if (--object2 <= 0) {
                            return;
                        }
                        ++n;
                    } while (true);
                }
                case 5: {
                    object2 = Collation.indexFromCE32(n2);
                    n = Collation.lengthFromCE32(n2);
                    this.ceBuffer.ensureAppendCapacity(n);
                    do {
                        this.ceBuffer.appendUnsafe(Collation.ceFromCE32(((CollationData)object3).ce32s[object2]));
                        if (--n <= 0) {
                            return;
                        }
                        ++object2;
                    } while (true);
                }
                case 4: {
                    this.ceBuffer.ensureAppendCapacity(2);
                    object = this.ceBuffer;
                    ((CEBuffer)object).set(((CEBuffer)object).length, Collation.latinCE0FromCE32(n2));
                    object = this.ceBuffer;
                    ((CEBuffer)object).set(((CEBuffer)object).length + 1, Collation.latinCE1FromCE32(n2));
                    object = this.ceBuffer;
                    ((CEBuffer)object).length += 2;
                    return;
                }
                case 2: {
                    this.ceBuffer.append(Collation.ceFromLongSecondaryCE32(n2));
                    return;
                }
                case 1: {
                    this.ceBuffer.append(Collation.ceFromLongPrimaryCE32(n2));
                    return;
                }
                case 0: 
                case 3: {
                    throw new ICUException("internal program error: should be unreachable");
                }
            }
            object3 = object;
            n3 = n;
            n2 = object2;
        }
        this.ceBuffer.append(Collation.ceFromSimpleCE32(n2));
    }

    protected abstract void backwardNumCodePoints(int var1);

    final void clearCEs() {
        this.ceBuffer.length = 0;
        this.cesIndex = 0;
    }

    public final void clearCEsIfNoneRemaining() {
        if (this.cesIndex == this.ceBuffer.length) {
            this.clearCEs();
        }
    }

    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        if (!this.getClass().equals(object.getClass())) {
            return false;
        }
        object = (CollationIterator)object;
        if (this.ceBuffer.length == object.ceBuffer.length && this.cesIndex == ((CollationIterator)object).cesIndex && this.numCpFwd == ((CollationIterator)object).numCpFwd && this.isNumeric == ((CollationIterator)object).isNumeric) {
            for (int i = 0; i < this.ceBuffer.length; ++i) {
                if (this.ceBuffer.get(i) == ((CollationIterator)object).ceBuffer.get(i)) continue;
                return false;
            }
            return true;
        }
        return false;
    }

    public final int fetchCEs() {
        while (this.nextCE() != 0x101000100L) {
            this.cesIndex = this.ceBuffer.length;
        }
        return this.ceBuffer.length;
    }

    protected boolean forbidSurrogateCodePoints() {
        return false;
    }

    protected abstract void forwardNumCodePoints(int var1);

    public final long getCE(int n) {
        return this.ceBuffer.get(n);
    }

    protected int getCE32FromBuilderData(int n) {
        throw new ICUException("internal program error: should be unreachable");
    }

    public final long[] getCEs() {
        return this.ceBuffer.getCEs();
    }

    public final int getCEsLength() {
        return this.ceBuffer.length;
    }

    protected int getDataCE32(int n) {
        return this.data.getCE32(n);
    }

    public abstract int getOffset();

    protected char handleGetTrailSurrogate() {
        return '\u0000';
    }

    protected long handleNextCE32() {
        int n = this.nextCodePoint();
        if (n < 0) {
            return -4294967104L;
        }
        return this.makeCodePointAndCE32Pair(n, this.data.getCE32(n));
    }

    public int hashCode() {
        return 0;
    }

    protected long makeCodePointAndCE32Pair(int n, int n2) {
        return (long)n << 32 | (long)n2 & 0xFFFFFFFFL;
    }

    public final long nextCE() {
        Object object;
        int n;
        if (this.cesIndex < this.ceBuffer.length) {
            CEBuffer cEBuffer = this.ceBuffer;
            int n2 = this.cesIndex;
            this.cesIndex = n2 + 1;
            return cEBuffer.get(n2);
        }
        this.ceBuffer.incLength();
        long l = this.handleNextCE32();
        int n3 = (int)(l >> 32);
        int n4 = (int)l;
        int n5 = n4 & 255;
        if (n5 < 192) {
            CEBuffer cEBuffer = this.ceBuffer;
            int n6 = this.cesIndex;
            this.cesIndex = n6 + 1;
            l = -65536 & n4;
            return cEBuffer.set(n6, (long)(n4 & 65280) << 16 | l << 32 | (long)(n5 << 8));
        }
        if (n5 == 192) {
            if (n3 < 0) {
                CEBuffer cEBuffer = this.ceBuffer;
                n5 = this.cesIndex;
                this.cesIndex = n5 + 1;
                return cEBuffer.set(n5, 0x101000100L);
            }
            object = this.data.base;
            n = ((CollationData)object).getCE32(n3);
            int n7 = n & 255;
            n4 = n;
            n5 = n7;
            if (n7 < 192) {
                object = this.ceBuffer;
                n5 = this.cesIndex;
                this.cesIndex = n5 + 1;
                l = -65536 & n;
                return ((CEBuffer)object).set(n5, (long)(n & 65280) << 16 | l << 32 | (long)(n7 << 8));
            }
        } else {
            object = this.data;
        }
        if (n5 == 193) {
            object = this.ceBuffer;
            n = this.cesIndex;
            this.cesIndex = n + 1;
            return ((CEBuffer)object).set(n, (long)(n4 - n5) << 32 | 0x5000500L);
        }
        return this.nextCEFromCE32((CollationData)object, n3, n4);
    }

    public abstract int nextCodePoint();

    public final long previousCE(UVector32 object) {
        CollationData collationData;
        if (this.ceBuffer.length > 0) {
            int n;
            object = this.ceBuffer;
            ((CEBuffer)object).length = n = ((CEBuffer)object).length - 1;
            return ((CEBuffer)object).get(n);
        }
        ((UVector32)object).removeAllElements();
        int n = this.getOffset();
        int n2 = this.previousCodePoint();
        if (n2 < 0) {
            return 0x101000100L;
        }
        if (this.data.isUnsafeBackward(n2, this.isNumeric)) {
            return this.previousCEUnsafe(n2, (UVector32)object);
        }
        int n3 = this.data.getCE32(n2);
        if (n3 == 192) {
            collationData = this.data.base;
            n3 = collationData.getCE32(n2);
        } else {
            collationData = this.data;
        }
        if (Collation.isSimpleOrLongCE32(n3)) {
            return Collation.ceFromCE32(n3);
        }
        this.appendCEsFromCE32(collationData, n2, n3, false);
        if (this.ceBuffer.length > 1) {
            ((UVector32)object).addElement(this.getOffset());
            while (((UVector32)object).size() <= this.ceBuffer.length) {
                ((UVector32)object).addElement(n);
            }
        }
        object = this.ceBuffer;
        ((CEBuffer)object).length = n3 = ((CEBuffer)object).length - 1;
        return ((CEBuffer)object).get(n3);
    }

    public abstract int previousCodePoint();

    protected final void reset() {
        this.ceBuffer.length = 0;
        this.cesIndex = 0;
        SkippedState skippedState = this.skipped;
        if (skippedState != null) {
            skippedState.clear();
        }
    }

    protected final void reset(boolean bl) {
        if (this.ceBuffer == null) {
            this.ceBuffer = new CEBuffer();
        }
        this.reset();
        this.isNumeric = bl;
    }

    public abstract void resetToOffset(int var1);

    final void setCurrentCE(long l) {
        this.ceBuffer.set(this.cesIndex - 1, l);
    }

    private static final class CEBuffer {
        private static final int INITIAL_CAPACITY = 40;
        private long[] buffer = new long[40];
        int length = 0;

        CEBuffer() {
        }

        void append(long l) {
            if (this.length >= 40) {
                this.ensureAppendCapacity(1);
            }
            long[] arrl = this.buffer;
            int n = this.length;
            this.length = n + 1;
            arrl[n] = l;
        }

        void appendUnsafe(long l) {
            long[] arrl = this.buffer;
            int n = this.length;
            this.length = n + 1;
            arrl[n] = l;
        }

        void ensureAppendCapacity(int n) {
            int n2;
            int n3 = n2 = this.buffer.length;
            if (this.length + n <= n2) {
                return;
            }
            do {
                if (n3 < 1000) {
                    n3 *= 4;
                    continue;
                }
                n3 *= 2;
            } while (n3 < (n2 = this.length) + n);
            long[] arrl = new long[n3];
            System.arraycopy(this.buffer, 0, arrl, 0, n2);
            this.buffer = arrl;
        }

        long get(int n) {
            return this.buffer[n];
        }

        long[] getCEs() {
            return this.buffer;
        }

        void incLength() {
            if (this.length >= 40) {
                this.ensureAppendCapacity(1);
            }
            ++this.length;
        }

        long set(int n, long l) {
            this.buffer[n] = l;
            return l;
        }
    }

    private static final class SkippedState {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private final StringBuilder newBuffer = new StringBuilder();
        private final StringBuilder oldBuffer = new StringBuilder();
        private int pos;
        private int skipLengthAtMatch;
        private CharsTrie.State state = new CharsTrie.State();

        SkippedState() {
        }

        int backwardNumCodePoints(int n) {
            int n2 = this.pos;
            int n3 = this.oldBuffer.length();
            int n4 = n2 - n3;
            if (n4 > 0) {
                if (n4 >= n) {
                    this.pos = n2 - n;
                    return n;
                }
                this.pos = this.oldBuffer.offsetByCodePoints(n3, n4 - n);
                return n4;
            }
            this.pos = this.oldBuffer.offsetByCodePoints(n2, -n);
            return 0;
        }

        void clear() {
            this.oldBuffer.setLength(0);
            this.pos = 0;
        }

        boolean hasNext() {
            boolean bl = this.pos < this.oldBuffer.length();
            return bl;
        }

        void incBeyond() {
            ++this.pos;
        }

        boolean isEmpty() {
            boolean bl = this.oldBuffer.length() == 0;
            return bl;
        }

        int next() {
            int n = this.oldBuffer.codePointAt(this.pos);
            this.pos += Character.charCount(n);
            return n;
        }

        void recordMatch() {
            this.skipLengthAtMatch = this.newBuffer.length();
        }

        void replaceMatch() {
            int n = this.oldBuffer.length();
            if (this.pos > n) {
                this.pos = n;
            }
            this.oldBuffer.delete(0, this.pos).insert(0, this.newBuffer, 0, this.skipLengthAtMatch);
            this.pos = 0;
        }

        void resetToTrieState(CharsTrie charsTrie) {
            charsTrie.resetToState(this.state);
        }

        void saveTrieState(CharsTrie charsTrie) {
            charsTrie.saveState(this.state);
        }

        void setFirstSkipped(int n) {
            this.skipLengthAtMatch = 0;
            this.newBuffer.setLength(0);
            this.newBuffer.appendCodePoint(n);
        }

        void skip(int n) {
            this.newBuffer.appendCodePoint(n);
        }
    }

}

