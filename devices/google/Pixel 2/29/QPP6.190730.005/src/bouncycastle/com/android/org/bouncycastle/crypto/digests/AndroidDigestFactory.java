/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.digests;

import com.android.org.bouncycastle.crypto.Digest;
import com.android.org.bouncycastle.crypto.digests.AndroidDigestFactoryBouncyCastle;
import com.android.org.bouncycastle.crypto.digests.AndroidDigestFactoryInterface;
import com.android.org.bouncycastle.crypto.digests.AndroidDigestFactoryOpenSSL;
import java.security.Security;
import java.util.Locale;

public final class AndroidDigestFactory {
    private static final AndroidDigestFactoryInterface BC;
    private static final AndroidDigestFactoryInterface CONSCRYPT;

    static {
        block4 : {
            block3 : {
                block2 : {
                    BC = new AndroidDigestFactoryBouncyCastle();
                    if (Security.getProvider("AndroidOpenSSL") == null) break block2;
                    CONSCRYPT = new AndroidDigestFactoryOpenSSL();
                    break block3;
                }
                if (System.getProperty("java.vendor", "").toLowerCase(Locale.US).contains("android")) break block4;
                CONSCRYPT = null;
            }
            return;
        }
        throw new AssertionError((Object)"Provider AndroidOpenSSL must exist");
    }

    public static Digest getMD5() {
        Object object = CONSCRYPT;
        if (object != null) {
            try {
                object = object.getMD5();
                return object;
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        return BC.getMD5();
    }

    public static Digest getSHA1() {
        Object object = CONSCRYPT;
        if (object != null) {
            try {
                object = object.getSHA1();
                return object;
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        return BC.getSHA1();
    }

    public static Digest getSHA224() {
        Object object = CONSCRYPT;
        if (object != null) {
            try {
                object = object.getSHA224();
                return object;
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        return BC.getSHA224();
    }

    public static Digest getSHA256() {
        Object object = CONSCRYPT;
        if (object != null) {
            try {
                object = object.getSHA256();
                return object;
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        return BC.getSHA256();
    }

    public static Digest getSHA384() {
        Object object = CONSCRYPT;
        if (object != null) {
            try {
                object = object.getSHA384();
                return object;
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        return BC.getSHA384();
    }

    public static Digest getSHA512() {
        Object object = CONSCRYPT;
        if (object != null) {
            try {
                object = object.getSHA512();
                return object;
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        return BC.getSHA512();
    }
}

