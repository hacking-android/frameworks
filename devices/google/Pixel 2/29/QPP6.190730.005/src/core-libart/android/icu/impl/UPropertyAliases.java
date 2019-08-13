/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

import android.icu.impl.ICUBinary;
import android.icu.impl.IllegalIcuArgumentException;
import android.icu.util.BytesTrie;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.MissingResourceException;

public final class UPropertyAliases {
    private static final int DATA_FORMAT = 1886282093;
    public static final UPropertyAliases INSTANCE;
    private static final IsAcceptable IS_ACCEPTABLE;
    private static final int IX_BYTE_TRIES_OFFSET = 1;
    private static final int IX_NAME_GROUPS_OFFSET = 2;
    private static final int IX_RESERVED3_OFFSET = 3;
    private static final int IX_VALUE_MAPS_OFFSET = 0;
    private byte[] bytesTries;
    private String nameGroups;
    private int[] valueMaps;

    static {
        IS_ACCEPTABLE = new IsAcceptable();
        try {
            UPropertyAliases uPropertyAliases;
            INSTANCE = uPropertyAliases = new UPropertyAliases();
            return;
        }
        catch (IOException iOException) {
            MissingResourceException missingResourceException = new MissingResourceException("Could not construct UPropertyAliases. Missing pnames.icu", "", "");
            missingResourceException.initCause(iOException);
            throw missingResourceException;
        }
    }

    private UPropertyAliases() throws IOException {
        this.load(ICUBinary.getRequiredData("pnames.icu"));
    }

    private static int asciiToLowercase(int n) {
        block0 : {
            if (65 > n || n > 90) break block0;
            n += 32;
        }
        return n;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public static int compare(String var0, String var1_1) {
        var2_2 = 0;
        var3_3 = 0;
        var4_4 = 0;
        var5_5 = 0;
        do {
            var6_6 = var3_3;
            var7_7 = var5_5;
            if (var2_2 < var0.length()) {
                var4_4 = var0.charAt(var2_2);
                if (var4_4 != 32 && var4_4 != 45 && var4_4 != 95) {
                    switch (var4_4) {
                        default: {
                            var6_6 = var3_3;
                            var7_7 = var5_5;
                            ** break;
                        }
                        case 9: 
                        case 10: 
                        case 11: 
                        case 12: 
                        case 13: 
                    }
                }
                ++var2_2;
                continue;
            }
lbl19: // 3 sources:
            block7 : do {
                var3_3 = var7_7;
                if (var6_6 >= var1_1.length()) break;
                var3_3 = var1_1.charAt(var6_6);
                if (var3_3 != 32 && var3_3 != 45 && var3_3 != 95) {
                    switch (var3_3) {
                        default: {
                            break block7;
                        }
                        case 9: 
                        case 10: 
                        case 11: 
                        case 12: 
                        case 13: 
                    }
                }
                ++var6_6;
                var7_7 = var3_3;
            } while (true);
            var5_5 = var0.length();
            var8_8 = true;
            var5_5 = var2_2 == var5_5 ? 1 : 0;
            if (var6_6 != var1_1.length()) {
                var8_8 = false;
            }
            if (var5_5 != 0) {
                if (var8_8) {
                    return 0;
                }
                var7_7 = 0;
                var5_5 = var3_3;
            } else {
                var7_7 = var4_4;
                var5_5 = var3_3;
                if (var8_8) {
                    var5_5 = 0;
                    var7_7 = var4_4;
                }
            }
            var4_4 = UPropertyAliases.asciiToLowercase(var7_7) - UPropertyAliases.asciiToLowercase(var5_5);
            if (var4_4 != 0) {
                return var4_4;
            }
            ++var2_2;
            var3_3 = var6_6 + 1;
            var4_4 = var7_7;
        } while (true);
    }

    private boolean containsName(BytesTrie bytesTrie, CharSequence charSequence) {
        BytesTrie.Result result = BytesTrie.Result.NO_VALUE;
        for (int i = 0; i < charSequence.length(); ++i) {
            char c = charSequence.charAt(i);
            BytesTrie.Result result2 = result;
            if (c != '-') {
                result2 = result;
                if (c != '_') {
                    result2 = result;
                    if (c != ' ') {
                        if ('\t' <= c && c <= '\r') {
                            result2 = result;
                        } else {
                            if (!result.hasNext()) {
                                return false;
                            }
                            result2 = bytesTrie.next(UPropertyAliases.asciiToLowercase(c));
                        }
                    }
                }
            }
            result = result2;
        }
        return result.hasValue();
    }

    private int findProperty(int n) {
        int n2 = 1;
        for (int i = this.valueMaps[0]; i > 0; --i) {
            int[] arrn = this.valueMaps;
            int n3 = arrn[n2];
            int n4 = arrn[n2 + 1];
            n2 += 2;
            if (n < n3) break;
            if (n < n4) {
                return (n - n3) * 2 + n2;
            }
            n2 += (n4 - n3) * 2;
        }
        return 0;
    }

    private int findPropertyValueNameGroup(int n, int n2) {
        if (n == 0) {
            return 0;
        }
        int[] arrn = this.valueMaps;
        int n3 = ++n + 1;
        if ((n = arrn[n]) < 16) {
            while (n > 0) {
                arrn = this.valueMaps;
                int n4 = arrn[n3];
                int n5 = arrn[n3 + 1];
                n3 += 2;
                if (n2 >= n4) {
                    if (n2 < n5) {
                        return arrn[n3 + n2 - n4];
                    }
                    n3 += n5 - n4;
                    --n;
                    continue;
                }
                break;
            }
        } else {
            int n6;
            int n7 = n3 + n - 16;
            n = n3;
            while (n2 >= (n6 = (arrn = this.valueMaps)[n])) {
                if (n2 == n6) {
                    return arrn[n7 + n - n3];
                }
                n = n6 = n + 1;
                if (n6 < n7) continue;
            }
        }
        return 0;
    }

    private String getName(int n, int n2) {
        String string = this.nameGroups;
        int n3 = n + 1;
        n = string.charAt(n);
        if (n2 >= 0 && n > n2) {
            n = n3;
            while (n2 > 0) {
                n3 = n;
                do {
                    string = this.nameGroups;
                    n = n3 + 1;
                    if (string.charAt(n3) == '\u0000') break;
                    n3 = n;
                } while (true);
                --n2;
            }
            n2 = n;
            while (this.nameGroups.charAt(n2) != '\u0000') {
                ++n2;
            }
            if (n == n2) {
                return null;
            }
            return this.nameGroups.substring(n, n2);
        }
        throw new IllegalIcuArgumentException("Invalid property (value) name choice");
    }

    private int getPropertyOrValueEnum(int n, CharSequence charSequence) {
        BytesTrie bytesTrie = new BytesTrie(this.bytesTries, n);
        if (this.containsName(bytesTrie, charSequence)) {
            return bytesTrie.getValue();
        }
        return -1;
    }

    private void load(ByteBuffer byteBuffer) throws IOException {
        ICUBinary.readHeader(byteBuffer, 1886282093, IS_ACCEPTABLE);
        int n = byteBuffer.getInt() / 4;
        if (n >= 8) {
            int n2;
            Object object = new int[n];
            object[0] = n * 4;
            for (n2 = 1; n2 < n; ++n2) {
                object[n2] = byteBuffer.getInt();
            }
            n = object[0];
            n2 = object[1];
            this.valueMaps = ICUBinary.getInts(byteBuffer, (n2 - n) / 4, 0);
            n = object[2];
            this.bytesTries = new byte[n - n2];
            byteBuffer.get(this.bytesTries);
            n = object[3] - n;
            object = new StringBuilder(n);
            for (n2 = 0; n2 < n; ++n2) {
                ((StringBuilder)object).append((char)byteBuffer.get());
            }
            this.nameGroups = ((StringBuilder)object).toString();
            return;
        }
        throw new IOException("pnames.icu: not enough indexes");
    }

    public int getPropertyEnum(CharSequence charSequence) {
        return this.getPropertyOrValueEnum(0, charSequence);
    }

    public String getPropertyName(int n, int n2) {
        int n3 = this.findProperty(n);
        if (n3 != 0) {
            return this.getName(this.valueMaps[n3], n2);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid property enum ");
        stringBuilder.append(n);
        stringBuilder.append(" (0x");
        stringBuilder.append(Integer.toHexString(n));
        stringBuilder.append(")");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public int getPropertyValueEnum(int n, CharSequence charSequence) {
        int n2 = this.findProperty(n);
        if (n2 != 0) {
            int[] arrn = this.valueMaps;
            if ((n2 = arrn[n2 + 1]) != 0) {
                return this.getPropertyOrValueEnum(arrn[n2], charSequence);
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Property ");
            ((StringBuilder)charSequence).append(n);
            ((StringBuilder)charSequence).append(" (0x");
            ((StringBuilder)charSequence).append(Integer.toHexString(n));
            ((StringBuilder)charSequence).append(") does not have named values");
            throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Invalid property enum ");
        ((StringBuilder)charSequence).append(n);
        ((StringBuilder)charSequence).append(" (0x");
        ((StringBuilder)charSequence).append(Integer.toHexString(n));
        ((StringBuilder)charSequence).append(")");
        throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
    }

    public int getPropertyValueEnumNoThrow(int n, CharSequence charSequence) {
        if ((n = this.findProperty(n)) == 0) {
            return -1;
        }
        int[] arrn = this.valueMaps;
        if ((n = arrn[n + 1]) == 0) {
            return -1;
        }
        return this.getPropertyOrValueEnum(arrn[n], charSequence);
    }

    public String getPropertyValueName(int n, int n2, int n3) {
        int n4 = this.findProperty(n);
        if (n4 != 0) {
            if ((n2 = this.findPropertyValueNameGroup(this.valueMaps[n4 + 1], n2)) != 0) {
                return this.getName(n2, n3);
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Property ");
            stringBuilder.append(n);
            stringBuilder.append(" (0x");
            stringBuilder.append(Integer.toHexString(n));
            stringBuilder.append(") does not have named values");
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid property enum ");
        stringBuilder.append(n);
        stringBuilder.append(" (0x");
        stringBuilder.append(Integer.toHexString(n));
        stringBuilder.append(")");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    private static final class IsAcceptable
    implements ICUBinary.Authenticate {
        private IsAcceptable() {
        }

        @Override
        public boolean isDataVersionAcceptable(byte[] arrby) {
            boolean bl = false;
            if (arrby[0] == 2) {
                bl = true;
            }
            return bl;
        }
    }

}

