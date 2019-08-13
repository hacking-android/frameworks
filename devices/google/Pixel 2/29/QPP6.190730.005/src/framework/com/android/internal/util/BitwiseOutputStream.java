/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.util;

import android.annotation.UnsupportedAppUsage;

public class BitwiseOutputStream {
    private byte[] mBuf;
    private int mEnd;
    private int mPos;

    @UnsupportedAppUsage
    public BitwiseOutputStream(int n) {
        this.mBuf = new byte[n];
        this.mEnd = n << 3;
        this.mPos = 0;
    }

    private void possExpand(int n) {
        int n2 = this.mPos;
        int n3 = this.mEnd;
        if (n2 + n < n3) {
            return;
        }
        byte[] arrby = new byte[n2 + n >>> 2];
        System.arraycopy(this.mBuf, 0, arrby, 0, n3 >>> 3);
        this.mBuf = arrby;
        this.mEnd = arrby.length << 3;
    }

    public void skip(int n) {
        this.possExpand(n);
        this.mPos += n;
    }

    @UnsupportedAppUsage
    public byte[] toByteArray() {
        int n = this.mPos;
        int n2 = (n & 7) > 0 ? 1 : 0;
        n2 = (n >>> 3) + n2;
        byte[] arrby = new byte[n2];
        System.arraycopy(this.mBuf, 0, arrby, 0, n2);
        return arrby;
    }

    @UnsupportedAppUsage
    public void write(int n, int n2) throws AccessException {
        if (n >= 0 && n <= 8) {
            this.possExpand(n);
            int n3 = this.mPos;
            int n4 = n3 >>> 3;
            int n5 = 16 - (n3 & 7) - n;
            n2 = (n2 & -1 >>> 32 - n) << n5;
            this.mPos = n3 + n;
            byte[] arrby = this.mBuf;
            arrby[n4] = (byte)(arrby[n4] | n2 >>> 8);
            if (n5 < 8) {
                n = n4 + 1;
                arrby[n] = (byte)(arrby[n] | n2 & 255);
            }
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("illegal write (");
        stringBuilder.append(n);
        stringBuilder.append(" bits)");
        throw new AccessException(stringBuilder.toString());
    }

    @UnsupportedAppUsage
    public void writeByteArray(int n, byte[] arrby) throws AccessException {
        for (int i = 0; i < arrby.length; ++i) {
            int n2 = Math.min(8, n - (i << 3));
            if (n2 <= 0) continue;
            this.write(n2, (byte)(arrby[i] >>> 8 - n2));
        }
    }

    public static class AccessException
    extends Exception {
        public AccessException(String string2) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("BitwiseOutputStream access failed: ");
            stringBuilder.append(string2);
            super(stringBuilder.toString());
        }
    }

}

