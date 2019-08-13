/*
 * Decompiled with CFR 0.145.
 */
package sun.security.provider.certpath;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import javax.security.auth.x500.X500Principal;
import sun.misc.CharacterEncoder;
import sun.misc.HexDumpEncoder;
import sun.security.util.DerInputStream;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import sun.security.util.ObjectIdentifier;
import sun.security.x509.AlgorithmId;
import sun.security.x509.SerialNumber;

public class CertId {
    private static final AlgorithmId SHA1_ALGID = new AlgorithmId(AlgorithmId.SHA_oid);
    private static final boolean debug = false;
    private final SerialNumber certSerialNumber;
    private final AlgorithmId hashAlgId;
    private final byte[] issuerKeyHash;
    private final byte[] issuerNameHash;
    private int myhash = -1;

    public CertId(X509Certificate x509Certificate, SerialNumber serialNumber) throws IOException {
        this(x509Certificate.getSubjectX500Principal(), x509Certificate.getPublicKey(), serialNumber);
    }

    public CertId(X500Principal object, PublicKey publicKey, SerialNumber serialNumber) throws IOException {
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("SHA1");
            this.hashAlgId = SHA1_ALGID;
            messageDigest.update(((X500Principal)object).getEncoded());
            this.issuerNameHash = messageDigest.digest();
            object = new DerValue(publicKey.getEncoded());
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            throw new IOException("Unable to create CertId", noSuchAlgorithmException);
        }
        messageDigest.update(new DerValue[]{((DerValue)object).data.getDerValue(), ((DerValue)object).data.getDerValue()}[1].getBitString());
        this.issuerKeyHash = messageDigest.digest();
        this.certSerialNumber = serialNumber;
    }

    public CertId(DerInputStream derInputStream) throws IOException {
        this.hashAlgId = AlgorithmId.parse(derInputStream.getDerValue());
        this.issuerNameHash = derInputStream.getOctetString();
        this.issuerKeyHash = derInputStream.getOctetString();
        this.certSerialNumber = new SerialNumber(derInputStream);
    }

    public void encode(DerOutputStream derOutputStream) throws IOException {
        DerOutputStream derOutputStream2 = new DerOutputStream();
        this.hashAlgId.encode(derOutputStream2);
        derOutputStream2.putOctetString(this.issuerNameHash);
        derOutputStream2.putOctetString(this.issuerKeyHash);
        this.certSerialNumber.encode(derOutputStream2);
        derOutputStream.write((byte)48, derOutputStream2);
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object != null && object instanceof CertId) {
            return this.hashAlgId.equals(((CertId)(object = (CertId)object)).getHashAlgorithm()) && Arrays.equals(this.issuerNameHash, ((CertId)object).getIssuerNameHash()) && Arrays.equals(this.issuerKeyHash, ((CertId)object).getIssuerKeyHash()) && this.certSerialNumber.getNumber().equals(((CertId)object).getSerialNumber());
        }
        return false;
    }

    public AlgorithmId getHashAlgorithm() {
        return this.hashAlgId;
    }

    public byte[] getIssuerKeyHash() {
        return this.issuerKeyHash;
    }

    public byte[] getIssuerNameHash() {
        return this.issuerNameHash;
    }

    public BigInteger getSerialNumber() {
        return this.certSerialNumber.getNumber();
    }

    public int hashCode() {
        if (this.myhash == -1) {
            int n;
            byte[] arrby;
            this.myhash = this.hashAlgId.hashCode();
            for (n = 0; n < (arrby = this.issuerNameHash).length; ++n) {
                this.myhash += arrby[n] * n;
            }
            for (n = 0; n < (arrby = this.issuerKeyHash).length; ++n) {
                this.myhash += arrby[n] * n;
            }
            this.myhash += this.certSerialNumber.getNumber().hashCode();
        }
        return this.myhash;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CertId \n");
        Object object = new StringBuilder();
        ((StringBuilder)object).append("Algorithm: ");
        ((StringBuilder)object).append(this.hashAlgId.toString());
        ((StringBuilder)object).append("\n");
        stringBuilder.append(((StringBuilder)object).toString());
        stringBuilder.append("issuerNameHash \n");
        object = new HexDumpEncoder();
        stringBuilder.append(((CharacterEncoder)object).encode(this.issuerNameHash));
        stringBuilder.append("\nissuerKeyHash: \n");
        stringBuilder.append(((CharacterEncoder)object).encode(this.issuerKeyHash));
        object = new StringBuilder();
        ((StringBuilder)object).append("\n");
        ((StringBuilder)object).append(this.certSerialNumber.toString());
        stringBuilder.append(((StringBuilder)object).toString());
        return stringBuilder.toString();
    }
}

