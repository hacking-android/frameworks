/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamField;
import java.io.Serializable;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.LongBuffer;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util._$$Lambda$BitSet$ifk7HV8_2uu42BYsPVrvRaHrugk;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

public class BitSet
implements Cloneable,
Serializable {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int ADDRESS_BITS_PER_WORD = 6;
    private static final int BITS_PER_WORD = 64;
    private static final int BIT_INDEX_MASK = 63;
    private static final long WORD_MASK = -1L;
    private static final ObjectStreamField[] serialPersistentFields = new ObjectStreamField[]{new ObjectStreamField("bits", long[].class)};
    private static final long serialVersionUID = 7997698588986878753L;
    private transient boolean sizeIsSticky = false;
    private long[] words;
    private transient int wordsInUse = 0;

    public BitSet() {
        this.initWords(64);
        this.sizeIsSticky = false;
    }

    public BitSet(int n) {
        if (n >= 0) {
            this.initWords(n);
            this.sizeIsSticky = true;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("nbits < 0: ");
        stringBuilder.append(n);
        throw new NegativeArraySizeException(stringBuilder.toString());
    }

    private BitSet(long[] arrl) {
        this.words = arrl;
        this.wordsInUse = arrl.length;
        this.checkInvariants();
    }

    private void checkInvariants() {
    }

    private static void checkRange(int n, int n2) {
        if (n >= 0) {
            if (n2 >= 0) {
                if (n <= n2) {
                    return;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("fromIndex: ");
                stringBuilder.append(n);
                stringBuilder.append(" > toIndex: ");
                stringBuilder.append(n2);
                throw new IndexOutOfBoundsException(stringBuilder.toString());
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("toIndex < 0: ");
            stringBuilder.append(n2);
            throw new IndexOutOfBoundsException(stringBuilder.toString());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("fromIndex < 0: ");
        stringBuilder.append(n);
        throw new IndexOutOfBoundsException(stringBuilder.toString());
    }

    private void ensureCapacity(int n) {
        long[] arrl = this.words;
        if (arrl.length < n) {
            n = Math.max(arrl.length * 2, n);
            this.words = Arrays.copyOf(this.words, n);
            this.sizeIsSticky = false;
        }
    }

    private void expandTo(int n) {
        if (this.wordsInUse < ++n) {
            this.ensureCapacity(n);
            this.wordsInUse = n;
        }
    }

    private void initWords(int n) {
        this.words = new long[BitSet.wordIndex(n - 1) + 1];
    }

    private void readObject(ObjectInputStream arrl) throws IOException, ClassNotFoundException {
        this.words = (long[])arrl.readFields().get("bits", null);
        this.wordsInUse = this.words.length;
        this.recalculateWordsInUse();
        arrl = this.words;
        int n = arrl.length;
        boolean bl = true;
        if (n <= 0 || arrl[arrl.length - 1] != 0L) {
            bl = false;
        }
        this.sizeIsSticky = bl;
        this.checkInvariants();
    }

    private void recalculateWordsInUse() {
        int n;
        for (n = this.wordsInUse - 1; n >= 0 && this.words[n] == 0L; --n) {
        }
        this.wordsInUse = n + 1;
    }

    private void trimToSize() {
        int n = this.wordsInUse;
        long[] arrl = this.words;
        if (n != arrl.length) {
            this.words = Arrays.copyOf(arrl, n);
            this.checkInvariants();
        }
    }

    public static BitSet valueOf(ByteBuffer byteBuffer) {
        int n;
        byteBuffer = byteBuffer.slice().order(ByteOrder.LITTLE_ENDIAN);
        for (n = byteBuffer.remaining(); n > 0 && byteBuffer.get(n - 1) == 0; --n) {
        }
        long[] arrl = new long[(n + 7) / 8];
        byteBuffer.limit(n);
        n = 0;
        while (byteBuffer.remaining() >= 8) {
            arrl[n] = byteBuffer.getLong();
            ++n;
        }
        int n2 = byteBuffer.remaining();
        for (int i = 0; i < n2; ++i) {
            arrl[n] = arrl[n] | ((long)byteBuffer.get() & 255L) << i * 8;
        }
        return new BitSet(arrl);
    }

    public static BitSet valueOf(LongBuffer longBuffer) {
        int n;
        longBuffer = longBuffer.slice();
        for (n = longBuffer.remaining(); n > 0 && longBuffer.get(n - 1) == 0L; --n) {
        }
        long[] arrl = new long[n];
        longBuffer.get(arrl);
        return new BitSet(arrl);
    }

    public static BitSet valueOf(byte[] arrby) {
        return BitSet.valueOf(ByteBuffer.wrap(arrby));
    }

    public static BitSet valueOf(long[] arrl) {
        int n;
        for (n = arrl.length; n > 0 && arrl[n - 1] == 0L; --n) {
        }
        return new BitSet(Arrays.copyOf(arrl, n));
    }

    private static int wordIndex(int n) {
        return n >> 6;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        this.checkInvariants();
        if (!this.sizeIsSticky) {
            this.trimToSize();
        }
        objectOutputStream.putFields().put("bits", this.words);
        objectOutputStream.writeFields();
    }

    public void and(BitSet bitSet) {
        long[] arrl;
        int n;
        if (this == bitSet) {
            return;
        }
        while ((n = this.wordsInUse) > bitSet.wordsInUse) {
            arrl = this.words;
            this.wordsInUse = --n;
            arrl[n] = 0L;
        }
        for (n = 0; n < this.wordsInUse; ++n) {
            arrl = this.words;
            arrl[n] = arrl[n] & bitSet.words[n];
        }
        this.recalculateWordsInUse();
        this.checkInvariants();
    }

    public void andNot(BitSet bitSet) {
        for (int i = Math.min((int)this.wordsInUse, (int)bitSet.wordsInUse) - 1; i >= 0; --i) {
            long[] arrl = this.words;
            arrl[i] = arrl[i] & bitSet.words[i];
        }
        this.recalculateWordsInUse();
        this.checkInvariants();
    }

    public int cardinality() {
        int n = 0;
        for (int i = 0; i < this.wordsInUse; ++i) {
            n += Long.bitCount(this.words[i]);
        }
        return n;
    }

    public void clear() {
        int n;
        while ((n = this.wordsInUse) > 0) {
            long[] arrl = this.words;
            this.wordsInUse = --n;
            arrl[n] = 0L;
        }
    }

    public void clear(int n) {
        if (n >= 0) {
            int n2 = BitSet.wordIndex(n);
            if (n2 >= this.wordsInUse) {
                return;
            }
            long[] arrl = this.words;
            arrl[n2] = arrl[n2] & 1L << n;
            this.recalculateWordsInUse();
            this.checkInvariants();
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("bitIndex < 0: ");
        stringBuilder.append(n);
        throw new IndexOutOfBoundsException(stringBuilder.toString());
    }

    public void clear(int n, int n2) {
        int n3;
        BitSet.checkRange(n, n2);
        if (n == n2) {
            return;
        }
        int n4 = BitSet.wordIndex(n);
        if (n4 >= this.wordsInUse) {
            return;
        }
        int n5 = n3 = BitSet.wordIndex(n2 - 1);
        if (n3 >= this.wordsInUse) {
            n2 = this.length();
            n5 = this.wordsInUse - 1;
        }
        long l = -1L << n;
        long l2 = -1L >>> -n2;
        if (n4 == n5) {
            long[] arrl = this.words;
            arrl[n4] = arrl[n4] & (l & l2);
        } else {
            long[] arrl = this.words;
            arrl[n4] = arrl[n4] & l;
            for (n = n4 + 1; n < n5; ++n) {
                this.words[n] = 0L;
            }
            arrl = this.words;
            arrl[n5] = arrl[n5] & l2;
        }
        this.recalculateWordsInUse();
        this.checkInvariants();
    }

    public Object clone() {
        if (!this.sizeIsSticky) {
            this.trimToSize();
        }
        try {
            BitSet bitSet = (BitSet)super.clone();
            bitSet.words = (long[])this.words.clone();
            bitSet.checkInvariants();
            return bitSet;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError(cloneNotSupportedException);
        }
    }

    public boolean equals(Object object) {
        if (!(object instanceof BitSet)) {
            return false;
        }
        if (this == object) {
            return true;
        }
        object = (BitSet)object;
        this.checkInvariants();
        BitSet.super.checkInvariants();
        if (this.wordsInUse != ((BitSet)object).wordsInUse) {
            return false;
        }
        for (int i = 0; i < this.wordsInUse; ++i) {
            if (this.words[i] == ((BitSet)object).words[i]) continue;
            return false;
        }
        return true;
    }

    public void flip(int n) {
        if (n >= 0) {
            int n2 = BitSet.wordIndex(n);
            this.expandTo(n2);
            long[] arrl = this.words;
            arrl[n2] = arrl[n2] ^ 1L << n;
            this.recalculateWordsInUse();
            this.checkInvariants();
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("bitIndex < 0: ");
        stringBuilder.append(n);
        throw new IndexOutOfBoundsException(stringBuilder.toString());
    }

    public void flip(int n, int n2) {
        BitSet.checkRange(n, n2);
        if (n == n2) {
            return;
        }
        int n3 = BitSet.wordIndex(n);
        int n4 = BitSet.wordIndex(n2 - 1);
        this.expandTo(n4);
        long l = -1L << n;
        long l2 = -1L >>> -n2;
        if (n3 == n4) {
            long[] arrl = this.words;
            arrl[n3] = arrl[n3] ^ l & l2;
        } else {
            long[] arrl = this.words;
            arrl[n3] = arrl[n3] ^ l;
            for (n = n3 + 1; n < n4; ++n) {
                arrl = this.words;
                arrl[n] = arrl[n];
            }
            arrl = this.words;
            arrl[n4] = arrl[n4] ^ l2;
        }
        this.recalculateWordsInUse();
        this.checkInvariants();
    }

    public BitSet get(int n, int n2) {
        int n3 = n2;
        BitSet.checkRange(n, n2);
        this.checkInvariants();
        int n4 = this.length();
        int n5 = 0;
        if (n4 > n && n != n3) {
            long[] arrl;
            long l;
            long[] arrl2;
            n2 = n3;
            if (n3 > n4) {
                n2 = n4;
            }
            BitSet bitSet = new BitSet(n2 - n);
            int n6 = BitSet.wordIndex(n2 - n - 1) + 1;
            n4 = BitSet.wordIndex(n);
            n3 = n5;
            if ((n & 63) == 0) {
                n3 = 1;
            }
            n5 = 0;
            while (n5 < n6 - 1) {
                arrl2 = bitSet.words;
                if (n3 != 0) {
                    l = this.words[n4];
                } else {
                    arrl = this.words;
                    l = arrl[n4] >>> n | arrl[n4 + 1] << -n;
                }
                arrl2[n5] = l;
                ++n5;
                ++n4;
            }
            l = -1L >>> -n2;
            arrl2 = bitSet.words;
            if ((n2 - 1 & 63) < (n & 63)) {
                arrl = this.words;
                l = arrl[n4] >>> n | (arrl[n4 + 1] & l) << -n;
            } else {
                l = (this.words[n4] & l) >>> n;
            }
            arrl2[n6 - 1] = l;
            bitSet.wordsInUse = n6;
            bitSet.recalculateWordsInUse();
            bitSet.checkInvariants();
            return bitSet;
        }
        return new BitSet(0);
    }

    public boolean get(int n) {
        if (n >= 0) {
            this.checkInvariants();
            int n2 = BitSet.wordIndex(n);
            boolean bl = n2 < this.wordsInUse && (this.words[n2] & 1L << n) != 0L;
            return bl;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("bitIndex < 0: ");
        stringBuilder.append(n);
        throw new IndexOutOfBoundsException(stringBuilder.toString());
    }

    public int hashCode() {
        long l = 1234L;
        int n = this.wordsInUse;
        while (--n >= 0) {
            l ^= this.words[n] * (long)(n + 1);
        }
        return (int)(l >> 32 ^ l);
    }

    public boolean intersects(BitSet bitSet) {
        for (int i = Math.min((int)this.wordsInUse, (int)bitSet.wordsInUse) - 1; i >= 0; --i) {
            if ((this.words[i] & bitSet.words[i]) == 0L) continue;
            return true;
        }
        return false;
    }

    public boolean isEmpty() {
        boolean bl = this.wordsInUse == 0;
        return bl;
    }

    public /* synthetic */ Spliterator.OfInt lambda$stream$0$BitSet() {
        return Spliterators.spliterator(new 1BitSetIterator(), (long)this.cardinality(), 21);
    }

    public int length() {
        int n = this.wordsInUse;
        if (n == 0) {
            return 0;
        }
        return (n - 1) * 64 + (64 - Long.numberOfLeadingZeros(this.words[n - 1]));
    }

    public int nextClearBit(int n) {
        if (n >= 0) {
            this.checkInvariants();
            int n2 = BitSet.wordIndex(n);
            if (n2 >= this.wordsInUse) {
                return n;
            }
            long l = this.words[n2] & -1L << n;
            n = n2;
            do {
                if (l != 0L) {
                    return n * 64 + Long.numberOfTrailingZeros(l);
                }
                n2 = this.wordsInUse;
                if (++n == n2) {
                    return n2 * 64;
                }
                l = this.words[n];
            } while (true);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("fromIndex < 0: ");
        stringBuilder.append(n);
        throw new IndexOutOfBoundsException(stringBuilder.toString());
    }

    public int nextSetBit(int n) {
        if (n >= 0) {
            this.checkInvariants();
            int n2 = BitSet.wordIndex(n);
            if (n2 >= this.wordsInUse) {
                return -1;
            }
            long l = this.words[n2] & -1L << n;
            n = n2;
            do {
                if (l != 0L) {
                    return n * 64 + Long.numberOfTrailingZeros(l);
                }
                if (++n == this.wordsInUse) {
                    return -1;
                }
                l = this.words[n];
            } while (true);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("fromIndex < 0: ");
        stringBuilder.append(n);
        throw new IndexOutOfBoundsException(stringBuilder.toString());
    }

    public void or(BitSet bitSet) {
        if (this == bitSet) {
            return;
        }
        int n = Math.min(this.wordsInUse, bitSet.wordsInUse);
        int n2 = this.wordsInUse;
        int n3 = bitSet.wordsInUse;
        if (n2 < n3) {
            this.ensureCapacity(n3);
            this.wordsInUse = bitSet.wordsInUse;
        }
        for (n3 = 0; n3 < n; ++n3) {
            long[] arrl = this.words;
            arrl[n3] = arrl[n3] | bitSet.words[n3];
        }
        if (n < bitSet.wordsInUse) {
            System.arraycopy((Object)bitSet.words, n, (Object)this.words, n, this.wordsInUse - n);
        }
        this.checkInvariants();
    }

    public int previousClearBit(int n) {
        if (n < 0) {
            if (n == -1) {
                return -1;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("fromIndex < -1: ");
            stringBuilder.append(n);
            throw new IndexOutOfBoundsException(stringBuilder.toString());
        }
        this.checkInvariants();
        int n2 = BitSet.wordIndex(n);
        if (n2 >= this.wordsInUse) {
            return n;
        }
        long l = this.words[n2] & -1L >>> -(n + 1);
        n = n2;
        while (l == 0L) {
            n2 = n - 1;
            if (n == 0) {
                return -1;
            }
            l = this.words[n2];
            n = n2;
        }
        return (n + 1) * 64 - 1 - Long.numberOfLeadingZeros(l);
    }

    public int previousSetBit(int n) {
        if (n < 0) {
            if (n == -1) {
                return -1;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("fromIndex < -1: ");
            stringBuilder.append(n);
            throw new IndexOutOfBoundsException(stringBuilder.toString());
        }
        this.checkInvariants();
        int n2 = BitSet.wordIndex(n);
        if (n2 >= this.wordsInUse) {
            return this.length() - 1;
        }
        long l = this.words[n2] & -1L >>> -(n + 1);
        n = n2;
        while (l == 0L) {
            n2 = n - 1;
            if (n == 0) {
                return -1;
            }
            l = this.words[n2];
            n = n2;
        }
        return (n + 1) * 64 - 1 - Long.numberOfLeadingZeros(l);
    }

    public void set(int n) {
        if (n >= 0) {
            int n2 = BitSet.wordIndex(n);
            this.expandTo(n2);
            long[] arrl = this.words;
            arrl[n2] = arrl[n2] | 1L << n;
            this.checkInvariants();
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("bitIndex < 0: ");
        stringBuilder.append(n);
        throw new IndexOutOfBoundsException(stringBuilder.toString());
    }

    public void set(int n, int n2) {
        BitSet.checkRange(n, n2);
        if (n == n2) {
            return;
        }
        int n3 = BitSet.wordIndex(n);
        int n4 = BitSet.wordIndex(n2 - 1);
        this.expandTo(n4);
        long l = -1L << n;
        long l2 = -1L >>> -n2;
        if (n3 == n4) {
            long[] arrl = this.words;
            arrl[n3] = arrl[n3] | l & l2;
        } else {
            long[] arrl = this.words;
            arrl[n3] = arrl[n3] | l;
            for (n = n3 + 1; n < n4; ++n) {
                this.words[n] = -1L;
            }
            arrl = this.words;
            arrl[n4] = arrl[n4] | l2;
        }
        this.checkInvariants();
    }

    public void set(int n, int n2, boolean bl) {
        if (bl) {
            this.set(n, n2);
        } else {
            this.clear(n, n2);
        }
    }

    public void set(int n, boolean bl) {
        if (bl) {
            this.set(n);
        } else {
            this.clear(n);
        }
    }

    public int size() {
        return this.words.length * 64;
    }

    public IntStream stream() {
        return StreamSupport.intStream(new _$$Lambda$BitSet$ifk7HV8_2uu42BYsPVrvRaHrugk(this), 16469, false);
    }

    public byte[] toByteArray() {
        long l;
        int n = this.wordsInUse;
        if (n == 0) {
            return new byte[0];
        }
        int n2 = (n - 1) * 8;
        for (l = this.words[n - 1]; l != 0L; l >>>= 8) {
            ++n2;
        }
        byte[] arrby = new byte[n2];
        ByteBuffer byteBuffer = ByteBuffer.wrap(arrby).order(ByteOrder.LITTLE_ENDIAN);
        for (n2 = 0; n2 < n - 1; ++n2) {
            byteBuffer.putLong(this.words[n2]);
        }
        for (l = this.words[n - 1]; l != 0L; l >>>= 8) {
            byteBuffer.put((byte)(255L & l));
        }
        return arrby;
    }

    public long[] toLongArray() {
        return Arrays.copyOf(this.words, this.wordsInUse);
    }

    public String toString() {
        this.checkInvariants();
        int n = this.wordsInUse;
        n = n > 128 ? this.cardinality() : (n *= 64);
        StringBuilder stringBuilder = new StringBuilder(n * 6 + 2);
        stringBuilder.append('{');
        n = this.nextSetBit(0);
        if (n != -1) {
            stringBuilder.append(n);
            while (++n >= 0) {
                int n2 = n = this.nextSetBit(n);
                if (n < 0) break;
                int n3 = this.nextClearBit(n2);
                do {
                    stringBuilder.append(", ");
                    stringBuilder.append(n2);
                    n2 = n = n2 + 1;
                } while (n != n3);
            }
        }
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    public void xor(BitSet bitSet) {
        int n = Math.min(this.wordsInUse, bitSet.wordsInUse);
        int n2 = this.wordsInUse;
        int n3 = bitSet.wordsInUse;
        if (n2 < n3) {
            this.ensureCapacity(n3);
            this.wordsInUse = bitSet.wordsInUse;
        }
        for (n3 = 0; n3 < n; ++n3) {
            long[] arrl = this.words;
            arrl[n3] = arrl[n3] ^ bitSet.words[n3];
        }
        n3 = bitSet.wordsInUse;
        if (n < n3) {
            System.arraycopy((Object)bitSet.words, n, (Object)this.words, n, n3 - n);
        }
        this.recalculateWordsInUse();
        this.checkInvariants();
    }

    class 1BitSetIterator
    implements PrimitiveIterator.OfInt {
        int next;

        1BitSetIterator() {
            this.next = BitSet.this.nextSetBit(0);
        }

        @Override
        public boolean hasNext() {
            boolean bl = this.next != -1;
            return bl;
        }

        @Override
        public int nextInt() {
            int n = this.next;
            if (n != -1) {
                int n2 = this.next;
                this.next = BitSet.this.nextSetBit(n + 1);
                return n2;
            }
            throw new NoSuchElementException();
        }
    }

}

