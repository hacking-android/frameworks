/*
 * Decompiled with CFR 0.145.
 */
package sun.misc;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class IOUtils {
    public static byte[] readFully(InputStream inputStream, int n, boolean bl) throws IOException {
        byte[] arrby;
        byte[] arrby2 = new byte[]{};
        int n2 = n;
        if (n == -1) {
            n2 = Integer.MAX_VALUE;
        }
        n = 0;
        do {
            int n3;
            byte[] arrby3;
            arrby = arrby2;
            if (n >= n2) break;
            if (n >= arrby2.length) {
                int n4 = Math.min(n2 - n, arrby2.length + 1024);
                arrby3 = arrby2;
                n3 = n4;
                if (arrby2.length < n + n4) {
                    arrby3 = Arrays.copyOf(arrby2, n + n4);
                    n3 = n4;
                }
            } else {
                n3 = arrby2.length - n;
                arrby3 = arrby2;
            }
            if ((n3 = inputStream.read(arrby3, n, n3)) < 0) {
                if (bl && n2 != Integer.MAX_VALUE) {
                    throw new EOFException("Detect premature EOF");
                }
                arrby = arrby3;
                if (arrby3.length == n) break;
                arrby = Arrays.copyOf(arrby3, n);
                break;
            }
            n += n3;
            arrby2 = arrby3;
        } while (true);
        return arrby;
    }
}

