/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.conscrypt.ct;

import com.android.org.conscrypt.ct.CertificateEntry;
import com.android.org.conscrypt.ct.DigitallySigned;
import com.android.org.conscrypt.ct.SerializationException;
import com.android.org.conscrypt.ct.SignedCertificateTimestamp;
import com.android.org.conscrypt.ct.VerifiedSCT;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Arrays;

public class CTLogInfo {
    private final String description;
    private final byte[] logId;
    private final PublicKey publicKey;
    private final String url;

    public CTLogInfo(PublicKey publicKey, String string, String string2) {
        try {
            this.logId = MessageDigest.getInstance("SHA-256").digest(publicKey.getEncoded());
            this.publicKey = publicKey;
            this.description = string;
            this.url = string2;
            return;
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            throw new RuntimeException(noSuchAlgorithmException);
        }
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (!(object instanceof CTLogInfo)) {
            return false;
        }
        object = (CTLogInfo)object;
        if (!(this.publicKey.equals(((CTLogInfo)object).publicKey) && this.description.equals(((CTLogInfo)object).description) && this.url.equals(((CTLogInfo)object).url))) {
            bl = false;
        }
        return bl;
    }

    public String getDescription() {
        return this.description;
    }

    public byte[] getID() {
        return this.logId;
    }

    public PublicKey getPublicKey() {
        return this.publicKey;
    }

    public String getUrl() {
        return this.url;
    }

    public int hashCode() {
        return ((1 * 31 + this.publicKey.hashCode()) * 31 + this.description.hashCode()) * 31 + this.url.hashCode();
    }

    public VerifiedSCT.Status verifySingleSCT(SignedCertificateTimestamp object, CertificateEntry arrby) {
        Signature signature;
        if (!Arrays.equals(object.getLogID(), this.getID())) {
            return VerifiedSCT.Status.UNKNOWN_LOG;
        }
        try {
            arrby = object.encodeTBS((CertificateEntry)arrby);
        }
        catch (SerializationException serializationException) {
            return VerifiedSCT.Status.INVALID_SCT;
        }
        try {
            signature = Signature.getInstance(object.getSignature().getAlgorithm());
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            return VerifiedSCT.Status.INVALID_SCT;
        }
        try {
            signature.initVerify(this.publicKey);
        }
        catch (InvalidKeyException invalidKeyException) {
            return VerifiedSCT.Status.INVALID_SCT;
        }
        try {
            signature.update(arrby);
            if (!signature.verify(object.getSignature().getSignature())) {
                return VerifiedSCT.Status.INVALID_SIGNATURE;
            }
            object = VerifiedSCT.Status.VALID;
            return object;
        }
        catch (SignatureException signatureException) {
            throw new RuntimeException(signatureException);
        }
    }
}

