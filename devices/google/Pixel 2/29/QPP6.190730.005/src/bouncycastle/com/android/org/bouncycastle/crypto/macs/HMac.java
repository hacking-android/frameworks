/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.macs;

import com.android.org.bouncycastle.crypto.CipherParameters;
import com.android.org.bouncycastle.crypto.Digest;
import com.android.org.bouncycastle.crypto.ExtendedDigest;
import com.android.org.bouncycastle.crypto.Mac;
import com.android.org.bouncycastle.crypto.params.KeyParameter;
import com.android.org.bouncycastle.util.Integers;
import com.android.org.bouncycastle.util.Memoable;
import java.io.Serializable;
import java.util.Hashtable;

public class HMac
implements Mac {
    private static final byte IPAD = 54;
    private static final byte OPAD = 92;
    private static Hashtable blockLengths = new Hashtable();
    private int blockLength;
    private Digest digest;
    private int digestSize;
    private byte[] inputPad;
    private Memoable ipadState;
    private Memoable opadState;
    private byte[] outputBuf;

    static {
        blockLengths.put("MD5", Integers.valueOf(64));
        blockLengths.put("SHA-1", Integers.valueOf(64));
        blockLengths.put("SHA-224", Integers.valueOf(64));
        blockLengths.put("SHA-256", Integers.valueOf(64));
        blockLengths.put("SHA-384", Integers.valueOf(128));
        blockLengths.put("SHA-512", Integers.valueOf(128));
    }

    public HMac(Digest digest) {
        this(digest, HMac.getByteLength(digest));
    }

    private HMac(Digest digest, int n) {
        this.digest = digest;
        this.digestSize = digest.getDigestSize();
        n = this.blockLength = n;
        this.inputPad = new byte[n];
        this.outputBuf = new byte[n + this.digestSize];
    }

    private static int getByteLength(Digest digest) {
        if (digest instanceof ExtendedDigest) {
            return ((ExtendedDigest)digest).getByteLength();
        }
        Serializable serializable = (Integer)blockLengths.get(digest.getAlgorithmName());
        if (serializable != null) {
            return (Integer)serializable;
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("unknown digest passed: ");
        ((StringBuilder)serializable).append(digest.getAlgorithmName());
        throw new IllegalArgumentException(((StringBuilder)serializable).toString());
    }

    private static void xorPad(byte[] arrby, int n, byte by) {
        for (int i = 0; i < n; ++i) {
            arrby[i] = (byte)(arrby[i] ^ by);
        }
    }

    @Override
    public int doFinal(byte[] object, int n) {
        this.digest.doFinal(this.outputBuf, this.blockLength);
        Object object2 = this.opadState;
        if (object2 != null) {
            ((Memoable)((Object)this.digest)).reset((Memoable)object2);
            object2 = this.digest;
            object2.update(this.outputBuf, this.blockLength, object2.getDigestSize());
        } else {
            Digest digest = this.digest;
            object2 = this.outputBuf;
            digest.update((byte[])object2, 0, ((byte[])object2).length);
        }
        int n2 = this.digest.doFinal((byte[])object, n);
        for (n = this.blockLength; n < ((byte[])(object = this.outputBuf)).length; ++n) {
            object[n] = (byte)(false ? 1 : 0);
        }
        object = this.ipadState;
        if (object != null) {
            ((Memoable)((Object)this.digest)).reset((Memoable)object);
        } else {
            object2 = this.digest;
            object = this.inputPad;
            object2.update((byte[])object, 0, ((byte[])object).length);
        }
        return n2;
    }

    @Override
    public String getAlgorithmName() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.digest.getAlgorithmName());
        stringBuilder.append("/HMAC");
        return stringBuilder.toString();
    }

    @Override
    public int getMacSize() {
        return this.digestSize;
    }

    public Digest getUnderlyingDigest() {
        return this.digest;
    }

    @Override
    public void init(CipherParameters object) {
        this.digest.reset();
        object = ((KeyParameter)object).getKey();
        int n = ((byte[])object).length;
        if (n > this.blockLength) {
            this.digest.update((byte[])object, 0, n);
            this.digest.doFinal(this.inputPad, 0);
            n = this.digestSize;
        } else {
            System.arraycopy((byte[])object, (int)0, (byte[])this.inputPad, (int)0, (int)n);
        }
        while (n < ((byte[])(object = this.inputPad)).length) {
            object[n] = (byte)(false ? 1 : 0);
            ++n;
        }
        System.arraycopy((byte[])object, (int)0, (byte[])this.outputBuf, (int)0, (int)this.blockLength);
        HMac.xorPad(this.inputPad, this.blockLength, (byte)54);
        HMac.xorPad(this.outputBuf, this.blockLength, (byte)92);
        object = this.digest;
        if (object instanceof Memoable) {
            this.opadState = ((Memoable)object).copy();
            ((Digest)((Object)this.opadState)).update(this.outputBuf, 0, this.blockLength);
        }
        object = this.digest;
        byte[] arrby = this.inputPad;
        object.update(arrby, 0, arrby.length);
        object = this.digest;
        if (object instanceof Memoable) {
            this.ipadState = ((Memoable)object).copy();
        }
    }

    @Override
    public void reset() {
        this.digest.reset();
        Digest digest = this.digest;
        byte[] arrby = this.inputPad;
        digest.update(arrby, 0, arrby.length);
    }

    @Override
    public void update(byte by) {
        this.digest.update(by);
    }

    @Override
    public void update(byte[] arrby, int n, int n2) {
        this.digest.update(arrby, n, n2);
    }
}

