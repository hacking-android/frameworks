/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce.io;

import com.android.org.bouncycastle.jcajce.io.DigestUpdatingOutputStream;
import com.android.org.bouncycastle.jcajce.io.MacUpdatingOutputStream;
import com.android.org.bouncycastle.jcajce.io.SignatureUpdatingOutputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.Signature;
import javax.crypto.Mac;

public class OutputStreamFactory {
    public static OutputStream createStream(MessageDigest messageDigest) {
        return new DigestUpdatingOutputStream(messageDigest);
    }

    public static OutputStream createStream(Signature signature) {
        return new SignatureUpdatingOutputStream(signature);
    }

    public static OutputStream createStream(Mac mac) {
        return new MacUpdatingOutputStream(mac);
    }
}

