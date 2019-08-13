/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

import android.icu.impl.ICUBinary;
import android.icu.impl.Trie2_16;
import android.icu.impl.Trie2_32;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Iterator;
import java.util.NoSuchElementException;

public abstract class Trie2
implements Iterable<Range> {
    static final int UNEWTRIE2_INDEX_1_LENGTH = 544;
    static final int UNEWTRIE2_INDEX_GAP_LENGTH = 576;
    static final int UNEWTRIE2_INDEX_GAP_OFFSET = 2080;
    static final int UNEWTRIE2_MAX_DATA_LENGTH = 1115264;
    static final int UNEWTRIE2_MAX_INDEX_2_LENGTH = 35488;
    static final int UTRIE2_BAD_UTF8_DATA_OFFSET = 128;
    static final int UTRIE2_CP_PER_INDEX_1_ENTRY = 2048;
    static final int UTRIE2_DATA_BLOCK_LENGTH = 32;
    static final int UTRIE2_DATA_GRANULARITY = 4;
    static final int UTRIE2_DATA_MASK = 31;
    static final int UTRIE2_DATA_START_OFFSET = 192;
    static final int UTRIE2_INDEX_1_OFFSET = 2112;
    static final int UTRIE2_INDEX_2_BLOCK_LENGTH = 64;
    static final int UTRIE2_INDEX_2_BMP_LENGTH = 2080;
    static final int UTRIE2_INDEX_2_MASK = 63;
    static final int UTRIE2_INDEX_2_OFFSET = 0;
    static final int UTRIE2_INDEX_SHIFT = 2;
    static final int UTRIE2_LSCP_INDEX_2_LENGTH = 32;
    static final int UTRIE2_LSCP_INDEX_2_OFFSET = 2048;
    static final int UTRIE2_MAX_INDEX_1_LENGTH = 512;
    static final int UTRIE2_OMITTED_BMP_INDEX_1_LENGTH = 32;
    static final int UTRIE2_OPTIONS_VALUE_BITS_MASK = 15;
    static final int UTRIE2_SHIFT_1 = 11;
    static final int UTRIE2_SHIFT_1_2 = 6;
    static final int UTRIE2_SHIFT_2 = 5;
    static final int UTRIE2_UTF8_2B_INDEX_2_LENGTH = 32;
    static final int UTRIE2_UTF8_2B_INDEX_2_OFFSET = 2080;
    private static ValueMapper defaultValueMapper = new ValueMapper(){

        @Override
        public int map(int n) {
            return n;
        }
    };
    int data16;
    int[] data32;
    int dataLength;
    int dataNullOffset;
    int errorValue;
    int fHash;
    UTrie2Header header;
    int highStart;
    int highValueIndex;
    char[] index;
    int index2NullOffset;
    int indexLength;
    int initialValue;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Trie2 createFromSerialized(ByteBuffer byteBuffer) throws IOException {
        ByteOrder byteOrder = byteBuffer.order();
        try {
            Object object;
            UTrie2Header uTrie2Header = new UTrie2Header();
            int n = uTrie2Header.signature = byteBuffer.getInt();
            if (n != 845771348) {
                if (n != 1416784178) {
                    IllegalArgumentException illegalArgumentException = new IllegalArgumentException("Buffer does not contain a serialized UTrie2");
                    throw illegalArgumentException;
                }
            } else {
                n = byteOrder == ByteOrder.BIG_ENDIAN ? 1 : 0;
                object = n != 0 ? ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN;
                byteBuffer.order((ByteOrder)object);
                uTrie2Header.signature = 1416784178;
            }
            uTrie2Header.options = byteBuffer.getChar();
            uTrie2Header.indexLength = byteBuffer.getChar();
            uTrie2Header.shiftedDataLength = byteBuffer.getChar();
            uTrie2Header.index2NullOffset = byteBuffer.getChar();
            uTrie2Header.dataNullOffset = byteBuffer.getChar();
            uTrie2Header.shiftedHighStart = byteBuffer.getChar();
            n = uTrie2Header.options;
            if ((n & 15) <= 1) {
                ValueWidth valueWidth;
                int n2;
                if ((uTrie2Header.options & 15) == 0) {
                    valueWidth = ValueWidth.BITS_16;
                    object = new Trie2_16();
                } else {
                    valueWidth = ValueWidth.BITS_32;
                    object = new Trie2_32();
                }
                ((Trie2)object).header = uTrie2Header;
                ((Trie2)object).indexLength = uTrie2Header.indexLength;
                ((Trie2)object).dataLength = uTrie2Header.shiftedDataLength << 2;
                ((Trie2)object).index2NullOffset = uTrie2Header.index2NullOffset;
                ((Trie2)object).dataNullOffset = uTrie2Header.dataNullOffset;
                ((Trie2)object).highStart = uTrie2Header.shiftedHighStart << 11;
                ((Trie2)object).highValueIndex = ((Trie2)object).dataLength - 4;
                if (valueWidth == ValueWidth.BITS_16) {
                    ((Trie2)object).highValueIndex += ((Trie2)object).indexLength;
                }
                n = n2 = ((Trie2)object).indexLength;
                if (valueWidth == ValueWidth.BITS_16) {
                    n = n2 + ((Trie2)object).dataLength;
                }
                ((Trie2)object).index = ICUBinary.getChars(byteBuffer, n, 0);
                if (valueWidth == ValueWidth.BITS_16) {
                    ((Trie2)object).data16 = ((Trie2)object).indexLength;
                } else {
                    ((Trie2)object).data32 = ICUBinary.getInts(byteBuffer, ((Trie2)object).dataLength, 0);
                }
                n = 2.$SwitchMap$android$icu$impl$Trie2$ValueWidth[valueWidth.ordinal()];
                if (n == 1) {
                    ((Trie2)object).data32 = null;
                    ((Trie2)object).initialValue = ((Trie2)object).index[((Trie2)object).dataNullOffset];
                    ((Trie2)object).errorValue = ((Trie2)object).index[((Trie2)object).data16 + 128];
                    return object;
                }
                if (n == 2) {
                    ((Trie2)object).data16 = 0;
                    ((Trie2)object).initialValue = ((Trie2)object).data32[((Trie2)object).dataNullOffset];
                    ((Trie2)object).errorValue = ((Trie2)object).data32[128];
                    return object;
                }
                object = new IllegalArgumentException("UTrie2 serialized format error.");
                throw object;
            }
            object = new IllegalArgumentException("UTrie2 serialized format error.");
            throw object;
        }
        finally {
            byteBuffer.order(byteOrder);
        }
    }

    public static int getVersion(InputStream inputStream, boolean bl) throws IOException {
        if (inputStream.markSupported()) {
            inputStream.mark(4);
            byte[] arrby = new byte[4];
            int n = inputStream.read(arrby);
            inputStream.reset();
            if (n != arrby.length) {
                return 0;
            }
            if (arrby[0] == 84 && arrby[1] == 114 && arrby[2] == 105 && arrby[3] == 101) {
                return 1;
            }
            if (arrby[0] == 84 && arrby[1] == 114 && arrby[2] == 105 && arrby[3] == 50) {
                return 2;
            }
            if (bl) {
                if (arrby[0] == 101 && arrby[1] == 105 && arrby[2] == 114 && arrby[3] == 84) {
                    return 1;
                }
                if (arrby[0] == 50 && arrby[1] == 105 && arrby[2] == 114 && arrby[3] == 84) {
                    return 2;
                }
            }
            return 0;
        }
        throw new IllegalArgumentException("Input stream must support mark().");
    }

    private static int hashByte(int n, int n2) {
        return n * 16777619 ^ n2;
    }

    private static int hashInt(int n, int n2) {
        return Trie2.hashByte(Trie2.hashByte(Trie2.hashByte(Trie2.hashByte(n, n2 & 255), n2 >> 8 & 255), n2 >> 16 & 255), n2 >> 24 & 255);
    }

    private static int hashUChar32(int n, int n2) {
        return Trie2.hashByte(Trie2.hashByte(Trie2.hashByte(n, n2 & 255), n2 >> 8 & 255), n2 >> 16);
    }

    private static int initHash() {
        return -2128831035;
    }

    public CharSequenceIterator charSequenceIterator(CharSequence charSequence, int n) {
        return new CharSequenceIterator(charSequence, n);
    }

    public final boolean equals(Object iterator) {
        if (!(iterator instanceof Trie2)) {
            return false;
        }
        Trie2 trie2 = (Trie2)((Object)iterator);
        Iterator<Range> iterator2 = trie2.iterator();
        for (Range range : this) {
            if (!iterator2.hasNext()) {
                return false;
            }
            if (range.equals(iterator2.next())) continue;
            return false;
        }
        if (iterator2.hasNext()) {
            return false;
        }
        return this.errorValue == trie2.errorValue && this.initialValue == trie2.initialValue;
        {
        }
    }

    public abstract int get(int var1);

    public abstract int getFromU16SingleLead(char var1);

    public int hashCode() {
        if (this.fHash == 0) {
            int n = Trie2.initHash();
            Iterator<Range> iterator = this.iterator();
            while (iterator.hasNext()) {
                n = Trie2.hashInt(n, iterator.next().hashCode());
            }
            int n2 = n;
            if (n == 0) {
                n2 = 1;
            }
            this.fHash = n2;
        }
        return this.fHash;
    }

    @Override
    public Iterator<Range> iterator() {
        return this.iterator(defaultValueMapper);
    }

    public Iterator<Range> iterator(ValueMapper valueMapper) {
        return new Trie2Iterator(valueMapper);
    }

    public Iterator<Range> iteratorForLeadSurrogate(char c) {
        return new Trie2Iterator(c, defaultValueMapper);
    }

    public Iterator<Range> iteratorForLeadSurrogate(char c, ValueMapper valueMapper) {
        return new Trie2Iterator(c, valueMapper);
    }

    int rangeEnd(int n, int n2, int n3) {
        int n4 = Math.min(this.highStart, n2);
        ++n;
        while (n < n4 && this.get(n) == n3) {
            ++n;
        }
        n3 = n;
        if (n >= this.highStart) {
            n3 = n2;
        }
        return n3 - 1;
    }

    protected int serializeHeader(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeInt(this.header.signature);
        dataOutputStream.writeShort(this.header.options);
        dataOutputStream.writeShort(this.header.indexLength);
        dataOutputStream.writeShort(this.header.shiftedDataLength);
        dataOutputStream.writeShort(this.header.index2NullOffset);
        dataOutputStream.writeShort(this.header.dataNullOffset);
        dataOutputStream.writeShort(this.header.shiftedHighStart);
        for (int i = 0; i < this.header.indexLength; ++i) {
            dataOutputStream.writeChar(this.index[i]);
        }
        return 0 + 16 + this.header.indexLength;
    }

    public class CharSequenceIterator
    implements Iterator<CharSequenceValues> {
        private CharSequenceValues fResults = new CharSequenceValues();
        private int index;
        private CharSequence text;
        private int textLength;

        CharSequenceIterator(CharSequence charSequence, int n) {
            this.text = charSequence;
            this.textLength = this.text.length();
            this.set(n);
        }

        @Override
        public final boolean hasNext() {
            boolean bl = this.index < this.textLength;
            return bl;
        }

        public final boolean hasPrevious() {
            boolean bl = this.index > 0;
            return bl;
        }

        @Override
        public CharSequenceValues next() {
            int n;
            int n2 = Character.codePointAt(this.text, this.index);
            int n3 = Trie2.this.get(n2);
            CharSequenceValues charSequenceValues = this.fResults;
            charSequenceValues.index = n = this.index;
            charSequenceValues.codePoint = n2;
            charSequenceValues.value = n3;
            this.index = n + 1;
            if (n2 >= 65536) {
                ++this.index;
            }
            return this.fResults;
        }

        public CharSequenceValues previous() {
            int n = Character.codePointBefore(this.text, this.index);
            int n2 = Trie2.this.get(n);
            --this.index;
            if (n >= 65536) {
                --this.index;
            }
            CharSequenceValues charSequenceValues = this.fResults;
            charSequenceValues.index = this.index;
            charSequenceValues.codePoint = n;
            charSequenceValues.value = n2;
            return charSequenceValues;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Trie2.CharSequenceIterator does not support remove().");
        }

        public void set(int n) {
            if (n >= 0 && n <= this.textLength) {
                this.index = n;
                return;
            }
            throw new IndexOutOfBoundsException();
        }
    }

    public static class CharSequenceValues {
        public int codePoint;
        public int index;
        public int value;
    }

    public static class Range {
        public int endCodePoint;
        public boolean leadSurrogate;
        public int startCodePoint;
        public int value;

        public boolean equals(Object object) {
            boolean bl = false;
            if (object != null && object.getClass().equals(this.getClass())) {
                object = (Range)object;
                boolean bl2 = bl;
                if (this.startCodePoint == ((Range)object).startCodePoint) {
                    bl2 = bl;
                    if (this.endCodePoint == ((Range)object).endCodePoint) {
                        bl2 = bl;
                        if (this.value == ((Range)object).value) {
                            bl2 = bl;
                            if (this.leadSurrogate == ((Range)object).leadSurrogate) {
                                bl2 = true;
                            }
                        }
                    }
                }
                return bl2;
            }
            return false;
        }

        public int hashCode() {
            return Trie2.hashByte(Trie2.hashInt(Trie2.hashUChar32(Trie2.hashUChar32(Trie2.initHash(), this.startCodePoint), this.endCodePoint), this.value), (int)this.leadSurrogate);
        }
    }

    class Trie2Iterator
    implements Iterator<Range> {
        private boolean doLeadSurrogates = true;
        private boolean doingCodePoints = true;
        private int limitCP;
        private ValueMapper mapper;
        private int nextStart;
        private Range returnValue = new Range();

        Trie2Iterator(char c, ValueMapper valueMapper) {
            if (c >= '\ud800' && c <= '\udbff') {
                this.mapper = valueMapper;
                this.nextStart = c - 55232 << 10;
                this.limitCP = this.nextStart + 1024;
                this.doLeadSurrogates = false;
                return;
            }
            throw new IllegalArgumentException("Bad lead surrogate value.");
        }

        Trie2Iterator(ValueMapper valueMapper) {
            this.mapper = valueMapper;
            this.nextStart = 0;
            this.limitCP = 1114112;
            this.doLeadSurrogates = true;
        }

        private int rangeEndLS(char c) {
            int n;
            if (c >= '\udbff') {
                return 56319;
            }
            int n2 = Trie2.this.getFromU16SingleLead(c);
            for (n = c + '\u0001'; n <= 56319 && Trie2.this.getFromU16SingleLead((char)n) == n2; ++n) {
            }
            return n - 1;
        }

        @Override
        public boolean hasNext() {
            boolean bl = this.doingCodePoints && (this.doLeadSurrogates || this.nextStart < this.limitCP) || this.nextStart < 56320;
            return bl;
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public Range next() {
            int n;
            int n2;
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            if (this.nextStart >= this.limitCP) {
                this.doingCodePoints = false;
                this.nextStart = 55296;
            }
            if (this.doingCodePoints) {
                int n3;
                n = Trie2.this.get(this.nextStart);
                n2 = this.mapper.map(n);
                n = Trie2.this.rangeEnd(this.nextStart, this.limitCP, n);
                while (n < this.limitCP - 1 && this.mapper.map(n3 = Trie2.this.get(n + 1)) == n2) {
                    n = Trie2.this.rangeEnd(n + 1, this.limitCP, n3);
                }
            } else {
                int n4;
                n = Trie2.this.getFromU16SingleLead((char)this.nextStart);
                n2 = this.mapper.map(n);
                n = this.rangeEndLS((char)this.nextStart);
                while (n < 56319 && this.mapper.map(n4 = Trie2.this.getFromU16SingleLead((char)(n + 1))) == n2) {
                    n = this.rangeEndLS((char)(n + 1));
                }
            }
            Range range = this.returnValue;
            range.startCodePoint = this.nextStart;
            range.endCodePoint = n;
            range.value = n2;
            range.leadSurrogate = this.doingCodePoints ^ true;
            this.nextStart = n + 1;
            return range;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    static class UTrie2Header {
        int dataNullOffset;
        int index2NullOffset;
        int indexLength;
        int options;
        int shiftedDataLength;
        int shiftedHighStart;
        int signature;

        UTrie2Header() {
        }
    }

    public static interface ValueMapper {
        public int map(int var1);
    }

    static enum ValueWidth {
        BITS_16,
        BITS_32;
        
    }

}

