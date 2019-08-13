/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.digests;

import com.android.org.bouncycastle.crypto.Digest;
import com.android.org.bouncycastle.util.Arrays;
import java.io.ByteArrayOutputStream;

public class NullDigest
implements Digest {
    private OpenByteArrayOutputStream bOut = new OpenByteArrayOutputStream();

    @Override
    public int doFinal(byte[] arrby, int n) {
        int n2 = this.bOut.size();
        this.bOut.copy(arrby, n);
        this.reset();
        return n2;
    }

    @Override
    public String getAlgorithmName() {
        return "NULL";
    }

    @Override
    public int getDigestSize() {
        return this.bOut.size();
    }

    @Override
    public void reset() {
        this.bOut.reset();
    }

    @Override
    public void update(byte by) {
        this.bOut.write(by);
    }

    @Override
    public void update(byte[] arrby, int n, int n2) {
        this.bOut.write(arrby, n, n2);
    }

    private static class OpenByteArrayOutputStream
    extends ByteArrayOutputStream {
        private OpenByteArrayOutputStream() {
        }

        void copy(byte[] arrby, int n) {
            System.arraycopy((byte[])this.buf, (int)0, (byte[])arrby, (int)n, (int)this.size());
        }

        @Override
        public void reset() {
            super.reset();
            Arrays.clear(this.buf);
        }
    }

}

