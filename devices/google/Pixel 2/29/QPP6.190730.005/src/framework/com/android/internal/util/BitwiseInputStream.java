/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.util;

import android.annotation.UnsupportedAppUsage;

public class BitwiseInputStream {
    private byte[] mBuf;
    private int mEnd;
    private int mPos;

    @UnsupportedAppUsage
    public BitwiseInputStream(byte[] arrby) {
        this.mBuf = arrby;
        this.mEnd = arrby.length << 3;
        this.mPos = 0;
    }

    @UnsupportedAppUsage
    public int available() {
        return this.mEnd - this.mPos;
    }

    @UnsupportedAppUsage
    public int read(int n) throws AccessException {
        int n2 = this.mPos;
        int n3 = n2 >>> 3;
        int n4 = 16 - (n2 & 7) - n;
        if (n >= 0 && n <= 8 && n2 + n <= this.mEnd) {
            int n5;
            byte[] arrby = this.mBuf;
            n2 = n5 = (arrby[n3] & 255) << 8;
            if (n4 < 8) {
                n2 = n5 | arrby[n3 + 1] & 255;
            }
            this.mPos += n;
            return n2 >>> n4 & -1 >>> 32 - n;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("illegal read (pos ");
        stringBuilder.append(this.mPos);
        stringBuilder.append(", end ");
        stringBuilder.append(this.mEnd);
        stringBuilder.append(", bits ");
        stringBuilder.append(n);
        stringBuilder.append(")");
        throw new AccessException(stringBuilder.toString());
    }

    @UnsupportedAppUsage
    public byte[] readByteArray(int n) throws AccessException {
        int n2 = (n & 7) > 0 ? 1 : 0;
        int n3 = (n >>> 3) + n2;
        byte[] arrby = new byte[n3];
        for (n2 = 0; n2 < n3; ++n2) {
            int n4 = Math.min(8, n - (n2 << 3));
            arrby[n2] = (byte)(this.read(n4) << 8 - n4);
        }
        return arrby;
    }

    @UnsupportedAppUsage
    public void skip(int n) throws AccessException {
        int n2 = this.mPos;
        if (n2 + n <= this.mEnd) {
            this.mPos = n2 + n;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("illegal skip (pos ");
        stringBuilder.append(this.mPos);
        stringBuilder.append(", end ");
        stringBuilder.append(this.mEnd);
        stringBuilder.append(", bits ");
        stringBuilder.append(n);
        stringBuilder.append(")");
        throw new AccessException(stringBuilder.toString());
    }

    public static class AccessException
    extends Exception {
        public AccessException(String string2) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("BitwiseInputStream access failed: ");
            stringBuilder.append(string2);
            super(stringBuilder.toString());
        }
    }

}

