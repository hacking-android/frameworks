/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce;

import com.android.org.bouncycastle.crypto.PBEParametersGenerator;
import com.android.org.bouncycastle.jcajce.PBKDFKey;

public class PKCS12Key
implements PBKDFKey {
    private final char[] password;
    private final boolean useWrongZeroLengthConversion;

    public PKCS12Key(char[] arrc) {
        this(arrc, false);
    }

    public PKCS12Key(char[] arrc, boolean bl) {
        char[] arrc2 = arrc;
        if (arrc == null) {
            arrc2 = new char[]{};
        }
        this.password = new char[arrc2.length];
        this.useWrongZeroLengthConversion = bl;
        System.arraycopy(arrc2, 0, this.password, 0, arrc2.length);
    }

    @Override
    public String getAlgorithm() {
        return "PKCS12";
    }

    @Override
    public byte[] getEncoded() {
        if (this.useWrongZeroLengthConversion && this.password.length == 0) {
            return new byte[2];
        }
        return PBEParametersGenerator.PKCS12PasswordToBytes(this.password);
    }

    @Override
    public String getFormat() {
        return "PKCS12";
    }

    public char[] getPassword() {
        return this.password;
    }
}

