/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

import android.icu.impl.Trie2;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

public class Trie2_32
extends Trie2 {
    Trie2_32() {
    }

    public static Trie2_32 createFromSerialized(ByteBuffer byteBuffer) throws IOException {
        return (Trie2_32)Trie2.createFromSerialized(byteBuffer);
    }

    @Override
    public final int get(int n) {
        if (n >= 0) {
            if (n >= 55296 && (n <= 56319 || n > 65535)) {
                if (n <= 65535) {
                    char c = this.index[(n - 55296 >> 5) + 2048];
                    return this.data32[(c << 2) + (n & 31)];
                }
                if (n < this.highStart) {
                    char c = this.index[(n >> 11) + 2080];
                    c = this.index[c + (n >> 5 & 63)];
                    return this.data32[(c << 2) + (n & 31)];
                }
                if (n <= 1114111) {
                    return this.data32[this.highValueIndex];
                }
            } else {
                char c = this.index[n >> 5];
                return this.data32[(c << 2) + (n & 31)];
            }
        }
        return this.errorValue;
    }

    @Override
    public int getFromU16SingleLead(char c) {
        char c2 = this.index[c >> 5];
        return this.data32[(c2 << 2) + (c & 31)];
    }

    public int getSerializedLength() {
        return this.header.indexLength * 2 + 16 + this.dataLength * 4;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    int rangeEnd(int var1_1, int var2_2, int var3_3) {
        do {
            block10 : {
                block9 : {
                    block7 : {
                        block5 : {
                            block8 : {
                                block6 : {
                                    if (var1_1 >= var2_2) ** GOTO lbl38
                                    if (var1_1 < 55296 || var1_1 > 56319 && var1_1 <= 65535) break block5;
                                    if (var1_1 >= 65535) break block6;
                                    var4_4 = 2048;
                                    var5_5 = this.index[(var1_1 - 55296 >> 5) + 2048] << 2;
                                    break block7;
                                }
                                if (var1_1 >= this.highStart) break block8;
                                var4_4 = this.index[(var1_1 >> 11) + 2080];
                                var5_5 = this.index[(var1_1 >> 5 & 63) + var4_4] << 2;
                                break block7;
                            }
                            if (var3_3 == this.data32[this.highValueIndex]) {
                                var1_1 = var2_2;
                            }
                            ** GOTO lbl38
                        }
                        var4_4 = 0;
                        var5_5 = this.index[var1_1 >> 5] << 2;
                    }
                    if (var4_4 != this.index2NullOffset) break block9;
                    if (var3_3 == this.initialValue) {
                        var1_1 += 2048;
                        continue;
                    }
                    ** GOTO lbl38
                }
                if (var5_5 != this.dataNullOffset) break block10;
                if (var3_3 == this.initialValue) {
                    var1_1 += 32;
                    continue;
                }
                ** GOTO lbl38
            }
            var4_4 = (var1_1 & 31) + var5_5;
            var6_6 = var5_5 + 32;
            for (var5_5 = var4_4; var5_5 < var6_6; ++var5_5) {
                if (this.data32[var5_5] == var3_3) continue;
                var1_1 += var5_5 - var4_4;
lbl38: // 5 sources:
                var3_3 = var1_1;
                if (var1_1 <= var2_2) return var3_3 - 1;
                var3_3 = var2_2;
                return var3_3 - 1;
            }
            var1_1 += var6_6 - var4_4;
        } while (true);
    }

    public int serialize(OutputStream outputStream) throws IOException {
        outputStream = new DataOutputStream(outputStream);
        int n = this.serializeHeader((DataOutputStream)outputStream);
        for (int i = 0; i < this.dataLength; ++i) {
            ((DataOutputStream)outputStream).writeInt(this.data32[i]);
        }
        return 0 + n + this.dataLength * 4;
    }
}

