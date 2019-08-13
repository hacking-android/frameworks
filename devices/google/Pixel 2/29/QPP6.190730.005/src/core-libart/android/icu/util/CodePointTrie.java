/*
 * Decompiled with CFR 0.145.
 */
package android.icu.util;

import android.icu.impl.ICUBinary;
import android.icu.impl.Normalizer2Impl;
import android.icu.util.CodePointMap;
import android.icu.util.ICUUncheckedIOException;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public abstract class CodePointTrie
extends CodePointMap {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int ASCII_LIMIT = 128;
    private static final int BMP_INDEX_LENGTH = 1024;
    static final int CP_PER_INDEX_2_ENTRY = 512;
    private static final int ERROR_VALUE_NEG_DATA_OFFSET = 1;
    static final int FAST_DATA_BLOCK_LENGTH = 64;
    private static final int FAST_DATA_MASK = 63;
    static final int FAST_SHIFT = 6;
    private static final int HIGH_VALUE_NEG_DATA_OFFSET = 2;
    static final int INDEX_2_BLOCK_LENGTH = 32;
    static final int INDEX_2_MASK = 31;
    static final int INDEX_3_BLOCK_LENGTH = 32;
    private static final int INDEX_3_MASK = 31;
    private static final int MAX_UNICODE = 1114111;
    static final int NO_DATA_NULL_OFFSET = 1048575;
    static final int NO_INDEX3_NULL_OFFSET = 32767;
    private static final int OMITTED_BMP_INDEX_1_LENGTH = 4;
    private static final int OPTIONS_DATA_LENGTH_MASK = 61440;
    private static final int OPTIONS_DATA_NULL_OFFSET_MASK = 3840;
    private static final int OPTIONS_RESERVED_MASK = 56;
    private static final int OPTIONS_VALUE_BITS_MASK = 7;
    private static final int SHIFT_1 = 14;
    static final int SHIFT_1_2 = 5;
    private static final int SHIFT_2 = 9;
    static final int SHIFT_2_3 = 5;
    static final int SHIFT_3 = 4;
    static final int SMALL_DATA_BLOCK_LENGTH = 16;
    static final int SMALL_DATA_MASK = 15;
    private static final int SMALL_INDEX_LENGTH = 64;
    static final int SMALL_LIMIT = 4096;
    private static final int SMALL_MAX = 4095;
    private final int[] ascii = new int[128];
    @Deprecated
    protected final Data data;
    @Deprecated
    protected final int dataLength;
    private final int dataNullOffset;
    @Deprecated
    protected final int highStart;
    private final char[] index;
    private final int index3NullOffset;
    private final int nullValue;

    private CodePointTrie(char[] arrc, Data data, int n, int n2, int n3) {
        this.index = arrc;
        this.data = data;
        this.dataLength = data.getDataLength();
        this.highStart = n;
        this.index3NullOffset = n2;
        this.dataNullOffset = n3;
        for (n = 0; n < 128; ++n) {
            this.ascii[n] = data.getFromIndex(n);
        }
        n2 = this.dataLength;
        n = n3;
        if (n3 >= n2) {
            n = n2 - 2;
        }
        this.nullValue = data.getFromIndex(n);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static CodePointTrie fromBinary(Type var0, ValueWidth var1_5, ByteBuffer var2_6) {
        block31 : {
            block32 : {
                block28 : {
                    block29 : {
                        block30 : {
                            block27 : {
                                block26 : {
                                    var3_7 = var2_6.order();
                                    if (var2_6.remaining() < 16) ** GOTO lbl103
                                    var4_8 = var2_6.getInt();
                                    if (var4_8 != 862548564) {
                                        if (var4_8 != 1416784179) {
                                            var0 /* !! */  = new ICUUncheckedIOException("Buffer does not contain a serialized CodePointTrie");
                                            throw var0 /* !! */ ;
                                        }
                                    } else {
                                        var4_8 = var3_7 == ByteOrder.BIG_ENDIAN ? 1 : 0;
                                        var5_9 /* !! */  = var4_8 != 0 ? ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN;
                                        var2_6.order((ByteOrder)var5_9 /* !! */ );
                                        var4_8 = 1416784179;
                                    }
                                    var6_10 = var2_6.getChar();
                                    var7_11 = var2_6.getChar();
                                    var8_12 = var2_6.getChar();
                                    var9_13 = var2_6.getChar();
                                    var10_14 = var2_6.getChar();
                                    var4_8 = var2_6.getChar();
                                    var11_15 = var6_10 >> 6 & 3;
                                    if (var11_15 != 0) {
                                        if (var11_15 != 1) {
                                            var0 /* !! */  = new ICUUncheckedIOException("CodePointTrie data header has an unsupported type");
                                            throw var0 /* !! */ ;
                                        }
                                        var12_16 = Type.SMALL;
                                    } else {
                                        var12_16 = Type.FAST;
                                    }
                                    var11_15 = var6_10 & 7;
                                    if (var11_15 != 0) {
                                        if (var11_15 != 1) {
                                            if (var11_15 != 2) {
                                                var0 /* !! */  = new ICUUncheckedIOException("CodePointTrie data header has an unsupported value width");
                                                throw var0 /* !! */ ;
                                            }
                                            var5_9 /* !! */  = ValueWidth.BITS_8;
                                        } else {
                                            var5_9 /* !! */  = ValueWidth.BITS_32;
                                        }
                                        break block26;
                                    }
                                    var5_9 /* !! */  = ValueWidth.BITS_16;
                                }
                                if ((var6_10 & 56) != 0) break block32;
                                if (var0 /* !! */  == null) {
                                    var0 /* !! */  = var12_16;
                                }
                                if (var1_5 == null) {
                                    var1_5 = var5_9 /* !! */ ;
                                }
                                if (var0 /* !! */  != var12_16 || var1_5 != var5_9 /* !! */ ) ** GOTO lbl96
                                var8_12 |= (var6_10 & 61440) << 4;
                                var6_10 = var10_14 | (var6_10 & 3840) << 8;
                                var10_14 = var4_8 << 9;
                                var4_8 = var7_11 * 2;
                                if (var1_5 == ValueWidth.BITS_16) {
                                    var4_8 += var8_12 * 2;
                                    break block27;
                                }
                                if (var1_5 == ValueWidth.BITS_32) {
                                    var4_8 += var8_12 * 4;
                                    break block27;
                                }
                                var4_8 += var8_12;
                            }
                            if (var2_6.remaining() < var4_8) break block28;
                            var5_9 /* !! */  = ICUBinary.getChars(var2_6, var7_11, 0);
                            var4_8 = 1.$SwitchMap$android$icu$util$CodePointTrie$ValueWidth[var1_5.ordinal()];
                            if (var4_8 == 1) break block29;
                            if (var4_8 == 2) ** GOTO lbl78
                            if (var4_8 != 3) break block30;
                            var1_5 = ICUBinary.getBytes(var2_6, var8_12, 0);
                            try {
                                var0 /* !! */  = var0 /* !! */  == Type.FAST ? new Fast8(var5_9 /* !! */ , (byte[])var1_5, var10_14, var9_13, var6_10) : new Small8(var5_9 /* !! */ , (byte[])var1_5, var10_14, var9_13, var6_10);
                                var2_6.order(var3_7);
                            }
                            catch (Throwable var0_2) {}
                            return var0 /* !! */ ;
                        }
                        var0 /* !! */  = new AssertionError((Object)"should be unreachable");
                        throw var0 /* !! */ ;
lbl78: // 1 sources:
                        var1_5 = ICUBinary.getInts(var2_6, var8_12, 0);
                        var0 /* !! */  = var0 /* !! */  == Type.FAST ? new Fast32(var5_9 /* !! */ , (int[])var1_5, var10_14, var9_13, var6_10) : new Small32(var5_9 /* !! */ , (int[])var1_5, var10_14, var9_13, var6_10);
                        var2_6.order(var3_7);
                        return var0 /* !! */ ;
                    }
                    var1_5 = ICUBinary.getChars(var2_6, var8_12, 0);
                    var0 /* !! */  = var0 /* !! */  == Type.FAST ? new Fast16(var5_9 /* !! */ , (char[])var1_5, var10_14, var9_13, var6_10) : new Small16(var5_9 /* !! */ , (char[])var1_5, var10_14, var9_13, var6_10);
                    var2_6.order(var3_7);
                    return var0 /* !! */ ;
                }
                var0 /* !! */  = new ICUUncheckedIOException("Buffer too short for the CodePointTrie data");
                throw var0 /* !! */ ;
                catch (Throwable var0_1) {}
                break block31;
lbl96: // 1 sources:
                var0 /* !! */  = new ICUUncheckedIOException("CodePointTrie data header has a different type or value width than required");
                throw var0 /* !! */ ;
                break block31;
            }
            var0 /* !! */  = new ICUUncheckedIOException("CodePointTrie data header has unsupported options");
            throw var0 /* !! */ ;
lbl103: // 1 sources:
            var0 /* !! */  = new ICUUncheckedIOException("Buffer too short for a CodePointTrie header");
            throw var0 /* !! */ ;
            catch (Throwable var0_3) {
                // empty catch block
            }
        }
        var2_6.order(var3_7);
        throw var0_4;
    }

    private final int internalSmallIndex(Type arrc, int n) {
        int n2 = n >> 14;
        n2 = arrc == Type.FAST ? (n2 += 1020) : (n2 += 64);
        arrc = this.index;
        n2 = arrc[arrc[n2] + (n >> 9 & 31)];
        int n3 = n >> 4 & 31;
        if ((32768 & n2) == 0) {
            n2 = arrc[n2 + n3];
        } else {
            n2 = (n2 & 32767) + (n3 & -8) + (n3 >> 3);
            int n4 = n3 & 7;
            n3 = arrc[n2];
            n2 = arrc[n2 + 1 + n4] | n3 << n4 * 2 + 2 & 196608;
        }
        return (n & 15) + n2;
    }

    private static final int maybeFilterValue(int n, int n2, int n3, CodePointMap.ValueFilter valueFilter) {
        if (n == n2) {
            n2 = n3;
        } else {
            n2 = n;
            if (valueFilter != null) {
                n2 = valueFilter.apply(n);
            }
        }
        return n2;
    }

    public final int asciiGet(int n) {
        return this.ascii[n];
    }

    @Deprecated
    protected abstract int cpIndex(int var1);

    @Deprecated
    protected final int fastIndex(int n) {
        return this.index[n >> 6] + (n & 63);
    }

    @Override
    public int get(int n) {
        return this.data.getFromIndex(this.cpIndex(n));
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    public final boolean getRange(int var1_1, CodePointMap.ValueFilter var2_2, CodePointMap.Range var3_3) {
        if (var1_1 < 0) return false;
        if (1114111 < var1_1) {
            return false;
        }
        if (var1_1 >= this.highStart) {
            var4_4 = this.dataLength;
            var4_4 = var5_6 = this.data.getFromIndex(var4_4 - 2);
            if (var2_2 != null) {
                var4_4 = var2_2.apply(var5_6);
            }
            var3_3.set(var1_1, 1114111, var4_4);
            return true;
        }
        var5_7 = var4_5 = this.nullValue;
        if (var2_2 != null) {
            var5_7 = var2_2.apply(var4_5);
        }
        var6_8 = this.getType();
        var7_9 = '\uffffffff';
        var8_10 = -1;
        var9_11 = var1_1;
        var10_12 = 0;
        var11_13 = 0;
        var4_5 = 0;
        block0 : do {
            block24 : {
                block26 : {
                    block25 : {
                        block23 : {
                            if (var9_11 > 65535 || var6_8 != Type.FAST && var9_11 > 4095) break block23;
                            var12_14 = '\u0000';
                            var13_15 = var9_11 >> 6;
                            var14_16 = var6_8 == Type.FAST ? 1024 : 64;
                            var15_17 = 64;
                            var16_18 = var8_10;
                            var17_19 = var4_5;
                            var18_20 = var14_16;
                            break block24;
                        }
                        var17_19 = var9_11 >> 14;
                        var17_19 = var6_8 == Type.FAST ? (var17_19 += 1020) : (var17_19 += 64);
                        var19_21 = this.index;
                        var12_14 = var19_21[var19_21[var17_19] + (var9_11 >> 9 & 31)];
                        if (var12_14 != var7_9 || var9_11 - var1_1 < 512) break block25;
                        var17_19 = var9_11 + 512;
                        var15_17 = var4_5;
                        ** GOTO lbl131
                    }
                    var7_9 = var12_14;
                    if (var12_14 != this.index3NullOffset) break block26;
                    if (var4_5 != 0) {
                        var15_17 = var11_13;
                        if (var5_7 != var11_13) {
                            var3_3.set(var1_1, var9_11 - 1, var11_13);
                            return true;
                        }
                    } else {
                        var10_12 = this.nullValue;
                        var15_17 = var5_7;
                        var4_5 = 1;
                    }
                    var8_10 = this.dataNullOffset;
                    var17_19 = var9_11 + 512 & -512;
                    var11_13 = var15_17;
                    var15_17 = var4_5;
                    ** GOTO lbl131
                }
                var18_20 = 32;
                var13_15 = var9_11 >> 4 & 31;
                var15_17 = 16;
                var17_19 = var4_5;
                var16_18 = var8_10;
            }
            do {
                block32 : {
                    block28 : {
                        block31 : {
                            block30 : {
                                block29 : {
                                    block27 : {
                                        if ((var12_14 & 32768) == 0) {
                                            var8_10 = this.index[var12_14 + var13_15];
                                        } else {
                                            var4_5 = (var12_14 & 32767) + (var13_15 & -8) + (var13_15 >> 3);
                                            var8_10 = var13_15 & 7;
                                            var19_21 = this.index;
                                            var8_10 = var19_21[var4_5] << var8_10 * 2 + 2 & 196608 | var19_21[var4_5 + 1 + var8_10];
                                        }
                                        if (var8_10 != var16_18 || var9_11 - var1_1 < var15_17) break block27;
                                        var4_5 = var9_11 + var15_17;
                                        var8_10 = var16_18;
                                        break block28;
                                    }
                                    var20_22 = var15_17 - 1;
                                    if (var8_10 != this.dataNullOffset) break block29;
                                    if (var17_19 != 0) {
                                        var14_16 = var11_13;
                                        if (var5_7 != var11_13) {
                                            var3_3.set(var1_1, var9_11 - 1, var11_13);
                                            return true;
                                        }
                                    } else {
                                        var10_12 = this.nullValue;
                                        var14_16 = var5_7;
                                        var17_19 = 1;
                                    }
                                    var4_5 = var20_22 & var9_11 + var15_17;
                                    var11_13 = var14_16;
                                    break block28;
                                }
                                var16_18 = (var9_11 & var20_22) + var8_10;
                                var14_16 = this.data.getFromIndex(var16_18);
                                if (var17_19 == 0) break block30;
                                if (var14_16 == var10_12) ** GOTO lbl102
                                if (var2_2 != null && CodePointTrie.maybeFilterValue(var14_16, this.nullValue, var5_7, var2_2) == var11_13) {
                                    var4_5 = var14_16;
                                    var14_16 = var16_18;
                                } else {
                                    var3_3.set(var1_1, var9_11 - 1, var11_13);
                                    return true;
lbl102: // 1 sources:
                                    var14_16 = var16_18;
                                    var4_5 = var10_12;
                                }
                                break block31;
                            }
                            var4_5 = var14_16;
                            var11_13 = CodePointTrie.maybeFilterValue(var14_16, this.nullValue, var5_7, var2_2);
                            var17_19 = 1;
                            var14_16 = var16_18;
                        }
                        while (((var16_18 = var9_11 + 1) & var20_22) != 0) {
                            var19_21 = this.data;
                            var10_12 = var14_16 + 1;
                            var21_23 = var19_21.getFromIndex(var10_12);
                            var14_16 = var10_12;
                            var9_11 = var16_18;
                            if (var21_23 == var4_5) continue;
                            if (var2_2 != null && CodePointTrie.maybeFilterValue(var21_23, this.nullValue, var5_7, var2_2) == var11_13) {
                                var4_5 = var21_23;
                                var14_16 = var10_12;
                                var9_11 = var16_18;
                                continue;
                            }
                            var3_3.set(var1_1, var16_18 - 1, var11_13);
                            return true;
                        }
                        var10_12 = var4_5;
                        var4_5 = var16_18;
                    }
                    if (++var13_15 < var18_20) break block32;
                    var15_17 = var17_19;
                    var17_19 = var4_5;
lbl131: // 3 sources:
                    if (var17_19 >= this.highStart) {
                        var4_5 = this.dataLength;
                        var4_5 = CodePointTrie.maybeFilterValue(this.data.getFromIndex(var4_5 - 2), this.nullValue, var5_7, var2_2) != var11_13 ? var17_19 - 1 : 1114111;
                        var3_3.set(var1_1, var4_5, var11_13);
                        return true;
                    }
                    var9_11 = var17_19;
                    var4_5 = var15_17;
                    continue block0;
                }
                var16_18 = var8_10;
                var9_11 = var4_5;
            } while (true);
            break;
        } while (true);
    }

    public abstract Type getType();

    public final ValueWidth getValueWidth() {
        return this.data.getValueWidth();
    }

    @Deprecated
    protected final int smallIndex(Type type, int n) {
        if (n >= this.highStart) {
            return this.dataLength - 2;
        }
        return this.internalSmallIndex(type, n);
    }

    public final int toBinary(OutputStream arrc) {
        int n;
        DataOutputStream dataOutputStream = new DataOutputStream((OutputStream)arrc);
        dataOutputStream.writeInt(1416784179);
        dataOutputStream.writeChar((this.dataLength & 983040) >> 4 | (983040 & this.dataNullOffset) >> 8 | this.getType().ordinal() << 6 | this.getValueWidth().ordinal());
        dataOutputStream.writeChar(this.index.length);
        dataOutputStream.writeChar(this.dataLength);
        dataOutputStream.writeChar(this.index3NullOffset);
        dataOutputStream.writeChar(this.dataNullOffset);
        dataOutputStream.writeChar(this.highStart >> 9);
        arrc = this.index;
        int n2 = arrc.length;
        for (n = 0; n < n2; ++n) {
            dataOutputStream.writeChar(arrc[n]);
            continue;
        }
        try {
            n = this.index.length;
            n2 = this.data.write(dataOutputStream);
            return 16 + n * 2 + n2;
        }
        catch (IOException iOException) {
            throw new ICUUncheckedIOException(iOException);
        }
    }

    private static abstract class Data {
        private Data() {
        }

        abstract int getDataLength();

        abstract int getFromIndex(int var1);

        abstract ValueWidth getValueWidth();

        abstract int write(DataOutputStream var1) throws IOException;
    }

    private static final class Data16
    extends Data {
        char[] array;

        Data16(char[] arrc) {
            this.array = arrc;
        }

        @Override
        int getDataLength() {
            return this.array.length;
        }

        @Override
        int getFromIndex(int n) {
            return this.array[n];
        }

        @Override
        ValueWidth getValueWidth() {
            return ValueWidth.BITS_16;
        }

        @Override
        int write(DataOutputStream dataOutputStream) throws IOException {
            char[] arrc = this.array;
            int n = arrc.length;
            for (int i = 0; i < n; ++i) {
                dataOutputStream.writeChar(arrc[i]);
            }
            return this.array.length * 2;
        }
    }

    private static final class Data32
    extends Data {
        int[] array;

        Data32(int[] arrn) {
            this.array = arrn;
        }

        @Override
        int getDataLength() {
            return this.array.length;
        }

        @Override
        int getFromIndex(int n) {
            return this.array[n];
        }

        @Override
        ValueWidth getValueWidth() {
            return ValueWidth.BITS_32;
        }

        @Override
        int write(DataOutputStream dataOutputStream) throws IOException {
            int[] arrn = this.array;
            int n = arrn.length;
            for (int i = 0; i < n; ++i) {
                dataOutputStream.writeInt(arrn[i]);
            }
            return this.array.length * 4;
        }
    }

    private static final class Data8
    extends Data {
        byte[] array;

        Data8(byte[] arrby) {
            this.array = arrby;
        }

        @Override
        int getDataLength() {
            return this.array.length;
        }

        @Override
        int getFromIndex(int n) {
            return this.array[n] & 255;
        }

        @Override
        ValueWidth getValueWidth() {
            return ValueWidth.BITS_8;
        }

        @Override
        int write(DataOutputStream dataOutputStream) throws IOException {
            byte[] arrby = this.array;
            int n = arrby.length;
            for (int i = 0; i < n; ++i) {
                dataOutputStream.writeByte(arrby[i]);
            }
            return this.array.length;
        }
    }

    public static abstract class Fast
    extends CodePointTrie {
        private Fast(char[] arrc, Data data, int n, int n2, int n3) {
            super(arrc, data, n, n2, n3);
        }

        public static Fast fromBinary(ValueWidth valueWidth, ByteBuffer byteBuffer) {
            return (Fast)CodePointTrie.fromBinary(Type.FAST, valueWidth, byteBuffer);
        }

        public abstract int bmpGet(int var1);

        @Deprecated
        @Override
        protected final int cpIndex(int n) {
            if (n >= 0) {
                if (n <= 65535) {
                    return this.fastIndex(n);
                }
                if (n <= 1114111) {
                    return this.smallIndex(Type.FAST, n);
                }
            }
            return this.dataLength - 1;
        }

        @Override
        public final Type getType() {
            return Type.FAST;
        }

        @Override
        public final CodePointMap.StringIterator stringIterator(CharSequence charSequence, int n) {
            return new FastStringIterator(charSequence, n);
        }

        public abstract int suppGet(int var1);

        private final class FastStringIterator
        extends CodePointMap.StringIterator {
            private FastStringIterator(CharSequence charSequence, int n) {
                super(charSequence, n);
            }

            @Override
            public boolean next() {
                char c;
                if (this.sIndex >= this.s.length()) {
                    return false;
                }
                CharSequence charSequence = this.s;
                int n = this.sIndex;
                this.sIndex = n + 1;
                char c2 = charSequence.charAt(n);
                this.c = c2;
                if (!Character.isSurrogate(c2)) {
                    n = Fast.this.fastIndex(this.c);
                } else if (Normalizer2Impl.UTF16Plus.isSurrogateLead(c2) && this.sIndex < this.s.length() && Character.isLowSurrogate(c = this.s.charAt(this.sIndex))) {
                    ++this.sIndex;
                    this.c = Character.toCodePoint(c2, c);
                    n = Fast.this.smallIndex(Type.FAST, this.c);
                } else {
                    n = Fast.this.dataLength - 1;
                }
                this.value = Fast.this.data.getFromIndex(n);
                return true;
            }

            @Override
            public boolean previous() {
                char c;
                int n;
                if (this.sIndex <= 0) {
                    return false;
                }
                CharSequence charSequence = this.s;
                this.sIndex = n = this.sIndex - 1;
                char c2 = charSequence.charAt(n);
                this.c = c2;
                if (!Character.isSurrogate(c2)) {
                    n = Fast.this.fastIndex(this.c);
                } else if (!Normalizer2Impl.UTF16Plus.isSurrogateLead(c2) && this.sIndex > 0 && Character.isHighSurrogate(c = this.s.charAt(this.sIndex - 1))) {
                    --this.sIndex;
                    this.c = Character.toCodePoint(c, c2);
                    n = Fast.this.smallIndex(Type.FAST, this.c);
                } else {
                    n = Fast.this.dataLength - 1;
                }
                this.value = Fast.this.data.getFromIndex(n);
                return true;
            }
        }

    }

    public static final class Fast16
    extends Fast {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private final char[] dataArray;

        Fast16(char[] arrc, char[] arrc2, int n, int n2, int n3) {
            super(arrc, new Data16(arrc2), n, n2, n3);
            this.dataArray = arrc2;
        }

        public static Fast16 fromBinary(ByteBuffer byteBuffer) {
            return (Fast16)CodePointTrie.fromBinary(Type.FAST, ValueWidth.BITS_16, byteBuffer);
        }

        @Override
        public final int bmpGet(int n) {
            return this.dataArray[this.fastIndex(n)];
        }

        @Override
        public final int get(int n) {
            return this.dataArray[this.cpIndex(n)];
        }

        @Override
        public final int suppGet(int n) {
            return this.dataArray[this.smallIndex(Type.FAST, n)];
        }
    }

    public static final class Fast32
    extends Fast {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private final int[] dataArray;

        Fast32(char[] arrc, int[] arrn, int n, int n2, int n3) {
            super(arrc, new Data32(arrn), n, n2, n3);
            this.dataArray = arrn;
        }

        public static Fast32 fromBinary(ByteBuffer byteBuffer) {
            return (Fast32)CodePointTrie.fromBinary(Type.FAST, ValueWidth.BITS_32, byteBuffer);
        }

        @Override
        public final int bmpGet(int n) {
            return this.dataArray[this.fastIndex(n)];
        }

        @Override
        public final int get(int n) {
            return this.dataArray[this.cpIndex(n)];
        }

        @Override
        public final int suppGet(int n) {
            return this.dataArray[this.smallIndex(Type.FAST, n)];
        }
    }

    public static final class Fast8
    extends Fast {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private final byte[] dataArray;

        Fast8(char[] arrc, byte[] arrby, int n, int n2, int n3) {
            super(arrc, new Data8(arrby), n, n2, n3);
            this.dataArray = arrby;
        }

        public static Fast8 fromBinary(ByteBuffer byteBuffer) {
            return (Fast8)CodePointTrie.fromBinary(Type.FAST, ValueWidth.BITS_8, byteBuffer);
        }

        @Override
        public final int bmpGet(int n) {
            return this.dataArray[this.fastIndex(n)] & 255;
        }

        @Override
        public final int get(int n) {
            return this.dataArray[this.cpIndex(n)] & 255;
        }

        @Override
        public final int suppGet(int n) {
            return this.dataArray[this.smallIndex(Type.FAST, n)] & 255;
        }
    }

    public static abstract class Small
    extends CodePointTrie {
        private Small(char[] arrc, Data data, int n, int n2, int n3) {
            super(arrc, data, n, n2, n3);
        }

        public static Small fromBinary(ValueWidth valueWidth, ByteBuffer byteBuffer) {
            return (Small)CodePointTrie.fromBinary(Type.SMALL, valueWidth, byteBuffer);
        }

        @Deprecated
        @Override
        protected final int cpIndex(int n) {
            if (n >= 0) {
                if (n <= 4095) {
                    return this.fastIndex(n);
                }
                if (n <= 1114111) {
                    return this.smallIndex(Type.SMALL, n);
                }
            }
            return this.dataLength - 1;
        }

        @Override
        public final Type getType() {
            return Type.SMALL;
        }

        @Override
        public final CodePointMap.StringIterator stringIterator(CharSequence charSequence, int n) {
            return new SmallStringIterator(charSequence, n);
        }

        private final class SmallStringIterator
        extends CodePointMap.StringIterator {
            private SmallStringIterator(CharSequence charSequence, int n) {
                super(charSequence, n);
            }

            @Override
            public boolean next() {
                char c;
                if (this.sIndex >= this.s.length()) {
                    return false;
                }
                CharSequence charSequence = this.s;
                int n = this.sIndex;
                this.sIndex = n + 1;
                char c2 = charSequence.charAt(n);
                this.c = c2;
                if (!Character.isSurrogate(c2)) {
                    n = Small.this.cpIndex(this.c);
                } else if (Normalizer2Impl.UTF16Plus.isSurrogateLead(c2) && this.sIndex < this.s.length() && Character.isLowSurrogate(c = this.s.charAt(this.sIndex))) {
                    ++this.sIndex;
                    this.c = Character.toCodePoint(c2, c);
                    n = Small.this.smallIndex(Type.SMALL, this.c);
                } else {
                    n = Small.this.dataLength - 1;
                }
                this.value = Small.this.data.getFromIndex(n);
                return true;
            }

            @Override
            public boolean previous() {
                char c;
                int n;
                if (this.sIndex <= 0) {
                    return false;
                }
                CharSequence charSequence = this.s;
                this.sIndex = n = this.sIndex - 1;
                char c2 = charSequence.charAt(n);
                this.c = c2;
                if (!Character.isSurrogate(c2)) {
                    n = Small.this.cpIndex(this.c);
                } else if (!Normalizer2Impl.UTF16Plus.isSurrogateLead(c2) && this.sIndex > 0 && Character.isHighSurrogate(c = this.s.charAt(this.sIndex - 1))) {
                    --this.sIndex;
                    this.c = Character.toCodePoint(c, c2);
                    n = Small.this.smallIndex(Type.SMALL, this.c);
                } else {
                    n = Small.this.dataLength - 1;
                }
                this.value = Small.this.data.getFromIndex(n);
                return true;
            }
        }

    }

    public static final class Small16
    extends Small {
        Small16(char[] arrc, char[] arrc2, int n, int n2, int n3) {
            super(arrc, new Data16(arrc2), n, n2, n3);
        }

        public static Small16 fromBinary(ByteBuffer byteBuffer) {
            return (Small16)CodePointTrie.fromBinary(Type.SMALL, ValueWidth.BITS_16, byteBuffer);
        }
    }

    public static final class Small32
    extends Small {
        Small32(char[] arrc, int[] arrn, int n, int n2, int n3) {
            super(arrc, new Data32(arrn), n, n2, n3);
        }

        public static Small32 fromBinary(ByteBuffer byteBuffer) {
            return (Small32)CodePointTrie.fromBinary(Type.SMALL, ValueWidth.BITS_32, byteBuffer);
        }
    }

    public static final class Small8
    extends Small {
        Small8(char[] arrc, byte[] arrby, int n, int n2, int n3) {
            super(arrc, new Data8(arrby), n, n2, n3);
        }

        public static Small8 fromBinary(ByteBuffer byteBuffer) {
            return (Small8)CodePointTrie.fromBinary(Type.SMALL, ValueWidth.BITS_8, byteBuffer);
        }
    }

    public static enum Type {
        FAST,
        SMALL;
        
    }

    public static enum ValueWidth {
        BITS_16,
        BITS_32,
        BITS_8;
        
    }

}

