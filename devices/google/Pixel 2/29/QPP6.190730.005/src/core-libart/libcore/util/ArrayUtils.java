/*
 * Decompiled with CFR 0.145.
 */
package libcore.util;

public final class ArrayUtils {
    private ArrayUtils() {
    }

    public static void throwsIfOutOfBounds(int n, int n2, int n3) {
        if (n >= 0) {
            if ((n2 | n3) >= 0 && n2 <= n - n3) {
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("length=");
            stringBuilder.append(n);
            stringBuilder.append("; regionStart=");
            stringBuilder.append(n2);
            stringBuilder.append("; regionLength=");
            stringBuilder.append(n3);
            throw new ArrayIndexOutOfBoundsException(stringBuilder.toString());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Negative length: ");
        stringBuilder.append(n);
        throw new ArrayIndexOutOfBoundsException(stringBuilder.toString());
    }
}

