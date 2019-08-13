/*
 * Decompiled with CFR 0.145.
 */
package javax.crypto.spec;

import java.security.spec.KeySpec;

public class PBEKeySpec
implements KeySpec {
    private int iterationCount = 0;
    private int keyLength = 0;
    private char[] password;
    private byte[] salt = null;

    public PBEKeySpec(char[] arrc) {
        this.password = arrc != null && arrc.length != 0 ? (char[])arrc.clone() : new char[0];
    }

    public PBEKeySpec(char[] arrc, byte[] arrby, int n) {
        this.password = arrc != null && arrc.length != 0 ? (char[])arrc.clone() : new char[0];
        if (arrby != null) {
            if (arrby.length != 0) {
                this.salt = (byte[])arrby.clone();
                if (n > 0) {
                    this.iterationCount = n;
                    return;
                }
                throw new IllegalArgumentException("invalid iterationCount value");
            }
            throw new IllegalArgumentException("the salt parameter must not be empty");
        }
        throw new NullPointerException("the salt parameter must be non-null");
    }

    public PBEKeySpec(char[] arrc, byte[] arrby, int n, int n2) {
        this.password = arrc != null && arrc.length != 0 ? (char[])arrc.clone() : new char[0];
        if (arrby != null) {
            if (arrby.length != 0) {
                this.salt = (byte[])arrby.clone();
                if (n > 0) {
                    if (n2 > 0) {
                        this.iterationCount = n;
                        this.keyLength = n2;
                        return;
                    }
                    throw new IllegalArgumentException("invalid keyLength value");
                }
                throw new IllegalArgumentException("invalid iterationCount value");
            }
            throw new IllegalArgumentException("the salt parameter must not be empty");
        }
        throw new NullPointerException("the salt parameter must be non-null");
    }

    public final void clearPassword() {
        if (this.password != null) {
            char[] arrc;
            for (int i = 0; i < (arrc = this.password).length; ++i) {
                arrc[i] = (char)32;
            }
            this.password = null;
        }
    }

    public final int getIterationCount() {
        return this.iterationCount;
    }

    public final int getKeyLength() {
        return this.keyLength;
    }

    public final char[] getPassword() {
        char[] arrc = this.password;
        if (arrc != null) {
            return (char[])arrc.clone();
        }
        throw new IllegalStateException("password has been cleared");
    }

    public final byte[] getSalt() {
        byte[] arrby = this.salt;
        if (arrby != null) {
            return (byte[])arrby.clone();
        }
        return null;
    }
}

