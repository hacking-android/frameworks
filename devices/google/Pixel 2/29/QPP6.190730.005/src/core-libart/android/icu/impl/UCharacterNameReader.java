/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

import android.icu.impl.ICUBinary;
import android.icu.impl.UCharacterName;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

final class UCharacterNameReader
implements ICUBinary.Authenticate {
    private static final int ALG_INFO_SIZE_ = 12;
    private static final int DATA_FORMAT_ID_ = 1970168173;
    private static final int GROUP_INFO_SIZE_ = 3;
    private int m_algnamesindex_;
    private ByteBuffer m_byteBuffer_;
    private int m_groupindex_;
    private int m_groupstringindex_;
    private int m_tokenstringindex_;

    protected UCharacterNameReader(ByteBuffer byteBuffer) throws IOException {
        ICUBinary.readHeader(byteBuffer, 1970168173, this);
        this.m_byteBuffer_ = byteBuffer;
    }

    private UCharacterName.AlgorithmName readAlg() throws IOException {
        byte by;
        char c;
        byte by2;
        UCharacterName.AlgorithmName algorithmName = new UCharacterName.AlgorithmName();
        int n = this.m_byteBuffer_.getInt();
        if (!algorithmName.setInfo(n, c = this.m_byteBuffer_.getInt(), by2 = this.m_byteBuffer_.get(), by = this.m_byteBuffer_.get())) {
            return null;
        }
        c = this.m_byteBuffer_.getChar();
        n = c;
        if (by2 == 1) {
            algorithmName.setFactor(ICUBinary.getChars(this.m_byteBuffer_, by, 0));
            n = c - (by << 1);
        }
        byte[] arrby = new StringBuilder();
        char c2 = c = (char)((char)(this.m_byteBuffer_.get() & 255));
        while (c2 != '\u0000') {
            arrby.append(c2);
            c2 = c = (char)(this.m_byteBuffer_.get() & 255);
        }
        algorithmName.setPrefix(arrby.toString());
        if ((n -= arrby.length() + 12 + 1) > 0) {
            arrby = new byte[n];
            this.m_byteBuffer_.get(arrby);
            algorithmName.setFactorString(arrby);
        }
        return algorithmName;
    }

    protected boolean authenticate(byte[] arrby, byte[] arrby2) {
        boolean bl = Arrays.equals(ICUBinary.getVersionByteArrayFromCompactInt(1970168173), arrby) && this.isDataVersionAcceptable(arrby2);
        return bl;
    }

    @Override
    public boolean isDataVersionAcceptable(byte[] arrby) {
        boolean bl = false;
        if (arrby[0] == 1) {
            bl = true;
        }
        return bl;
    }

    protected void read(UCharacterName uCharacterName) throws IOException {
        this.m_tokenstringindex_ = this.m_byteBuffer_.getInt();
        this.m_groupindex_ = this.m_byteBuffer_.getInt();
        this.m_groupstringindex_ = this.m_byteBuffer_.getInt();
        this.m_algnamesindex_ = this.m_byteBuffer_.getInt();
        int n = this.m_byteBuffer_.getChar();
        Object object = ICUBinary.getChars(this.m_byteBuffer_, n, 0);
        Object[] arrobject = new byte[this.m_groupindex_ - this.m_tokenstringindex_];
        this.m_byteBuffer_.get((byte[])arrobject);
        uCharacterName.setToken((char[])object, (byte[])arrobject);
        n = this.m_byteBuffer_.getChar();
        uCharacterName.setGroupCountSize(n, 3);
        object = ICUBinary.getChars(this.m_byteBuffer_, n * 3, 0);
        arrobject = new byte[this.m_algnamesindex_ - this.m_groupstringindex_];
        this.m_byteBuffer_.get((byte[])arrobject);
        uCharacterName.setGroup((char[])object, (byte[])arrobject);
        int n2 = this.m_byteBuffer_.getInt();
        arrobject = new UCharacterName.AlgorithmName[n2];
        for (n = 0; n < n2; ++n) {
            object = this.readAlg();
            if (object != null) {
                arrobject[n] = (byte)object;
                continue;
            }
            throw new IOException("unames.icu read error: Algorithmic names creation error");
        }
        uCharacterName.setAlgorithm((UCharacterName.AlgorithmName[])arrobject);
    }
}

