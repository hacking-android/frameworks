/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce.provider.asymmetric.util;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import com.android.org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import com.android.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;

public class KeyUtil {
    public static byte[] getEncodedPrivateKeyInfo(PrivateKeyInfo arrby) {
        try {
            arrby = arrby.getEncoded("DER");
            return arrby;
        }
        catch (Exception exception) {
            return null;
        }
    }

    public static byte[] getEncodedPrivateKeyInfo(AlgorithmIdentifier arrby, ASN1Encodable aSN1Encodable) {
        try {
            PrivateKeyInfo privateKeyInfo = new PrivateKeyInfo((AlgorithmIdentifier)arrby, aSN1Encodable.toASN1Primitive());
            arrby = KeyUtil.getEncodedPrivateKeyInfo(privateKeyInfo);
            return arrby;
        }
        catch (Exception exception) {
            return null;
        }
    }

    public static byte[] getEncodedSubjectPublicKeyInfo(AlgorithmIdentifier arrby, ASN1Encodable aSN1Encodable) {
        try {
            SubjectPublicKeyInfo subjectPublicKeyInfo = new SubjectPublicKeyInfo((AlgorithmIdentifier)arrby, aSN1Encodable);
            arrby = KeyUtil.getEncodedSubjectPublicKeyInfo(subjectPublicKeyInfo);
            return arrby;
        }
        catch (Exception exception) {
            return null;
        }
    }

    public static byte[] getEncodedSubjectPublicKeyInfo(AlgorithmIdentifier arrby, byte[] arrby2) {
        try {
            SubjectPublicKeyInfo subjectPublicKeyInfo = new SubjectPublicKeyInfo((AlgorithmIdentifier)arrby, arrby2);
            arrby = KeyUtil.getEncodedSubjectPublicKeyInfo(subjectPublicKeyInfo);
            return arrby;
        }
        catch (Exception exception) {
            return null;
        }
    }

    public static byte[] getEncodedSubjectPublicKeyInfo(SubjectPublicKeyInfo arrby) {
        try {
            arrby = arrby.getEncoded("DER");
            return arrby;
        }
        catch (Exception exception) {
            return null;
        }
    }
}

