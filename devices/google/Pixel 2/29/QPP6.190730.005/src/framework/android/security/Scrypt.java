/*
 * Decompiled with CFR 0.145.
 */
package android.security;

public class Scrypt {
    native byte[] nativeScrypt(byte[] var1, byte[] var2, int var3, int var4, int var5, int var6);

    public byte[] scrypt(byte[] arrby, byte[] arrby2, int n, int n2, int n3, int n4) {
        return this.nativeScrypt(arrby, arrby2, n, n2, n3, n4);
    }
}

