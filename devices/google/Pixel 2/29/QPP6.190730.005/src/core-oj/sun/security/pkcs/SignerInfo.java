/*
 * Decompiled with CFR 0.145.
 */
package sun.security.pkcs;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.AlgorithmParameters;
import java.security.CryptoPrimitive;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.Timestamp;
import java.security.cert.CertPath;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import sun.misc.HexDumpEncoder;
import sun.security.pkcs.ContentInfo;
import sun.security.pkcs.PKCS7;
import sun.security.pkcs.PKCS9Attribute;
import sun.security.pkcs.PKCS9Attributes;
import sun.security.pkcs.ParsingException;
import sun.security.timestamp.TimestampToken;
import sun.security.util.Debug;
import sun.security.util.DerEncoder;
import sun.security.util.DerInputStream;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import sun.security.util.DisabledAlgorithmConstraints;
import sun.security.util.KeyUtil;
import sun.security.util.ObjectIdentifier;
import sun.security.x509.AlgorithmId;
import sun.security.x509.KeyUsageExtension;
import sun.security.x509.X500Name;

public class SignerInfo
implements DerEncoder {
    private static final Set<CryptoPrimitive> DIGEST_PRIMITIVE_SET = Collections.unmodifiableSet(EnumSet.of(CryptoPrimitive.MESSAGE_DIGEST));
    private static final DisabledAlgorithmConstraints JAR_DISABLED_CHECK;
    private static final Set<CryptoPrimitive> SIG_PRIMITIVE_SET;
    PKCS9Attributes authenticatedAttributes;
    BigInteger certificateSerialNumber;
    AlgorithmId digestAlgorithmId;
    AlgorithmId digestEncryptionAlgorithmId;
    byte[] encryptedDigest;
    private boolean hasTimestamp = true;
    X500Name issuerName;
    Timestamp timestamp;
    PKCS9Attributes unauthenticatedAttributes;
    BigInteger version;

    static {
        SIG_PRIMITIVE_SET = Collections.unmodifiableSet(EnumSet.of(CryptoPrimitive.SIGNATURE));
        JAR_DISABLED_CHECK = new DisabledAlgorithmConstraints("jdk.jar.disabledAlgorithms");
    }

    public SignerInfo() {
    }

    public SignerInfo(DerInputStream derInputStream) throws IOException, ParsingException {
        this(derInputStream, false);
    }

    public SignerInfo(DerInputStream derInputStream, boolean bl) throws IOException, ParsingException {
        this.version = derInputStream.getBigInteger();
        DerValue[] arrderValue = derInputStream.getSequence(2);
        this.issuerName = new X500Name(new DerValue(48, arrderValue[0].toByteArray()));
        this.certificateSerialNumber = arrderValue[1].getBigInteger();
        this.digestAlgorithmId = AlgorithmId.parse(derInputStream.getDerValue());
        if (bl) {
            derInputStream.getSet(0);
        } else if ((byte)derInputStream.peekByte() == -96) {
            this.authenticatedAttributes = new PKCS9Attributes(derInputStream);
        }
        this.digestEncryptionAlgorithmId = AlgorithmId.parse(derInputStream.getDerValue());
        this.encryptedDigest = derInputStream.getOctetString();
        if (bl) {
            derInputStream.getSet(0);
        } else if (derInputStream.available() != 0 && (byte)derInputStream.peekByte() == -95) {
            this.unauthenticatedAttributes = new PKCS9Attributes(derInputStream, true);
        }
        if (derInputStream.available() == 0) {
            return;
        }
        throw new ParsingException("extra data at the end");
    }

    public SignerInfo(X500Name x500Name, BigInteger bigInteger, AlgorithmId algorithmId, PKCS9Attributes pKCS9Attributes, AlgorithmId algorithmId2, byte[] arrby, PKCS9Attributes pKCS9Attributes2) {
        this.version = BigInteger.ONE;
        this.issuerName = x500Name;
        this.certificateSerialNumber = bigInteger;
        this.digestAlgorithmId = algorithmId;
        this.authenticatedAttributes = pKCS9Attributes;
        this.digestEncryptionAlgorithmId = algorithmId2;
        this.encryptedDigest = arrby;
        this.unauthenticatedAttributes = pKCS9Attributes2;
    }

    public SignerInfo(X500Name x500Name, BigInteger bigInteger, AlgorithmId algorithmId, AlgorithmId algorithmId2, byte[] arrby) {
        this.version = BigInteger.ONE;
        this.issuerName = x500Name;
        this.certificateSerialNumber = bigInteger;
        this.digestAlgorithmId = algorithmId;
        this.digestEncryptionAlgorithmId = algorithmId2;
        this.encryptedDigest = arrby;
    }

    private void verifyTimestamp(TimestampToken object) throws NoSuchAlgorithmException, SignatureException {
        Object object2 = ((TimestampToken)object).getHashAlgorithm().getName();
        if (JAR_DISABLED_CHECK.permits(DIGEST_PRIMITIVE_SET, (String)object2, null)) {
            object2 = MessageDigest.getInstance((String)object2);
            if (Arrays.equals(((TimestampToken)object).getHashedMessage(), ((MessageDigest)object2).digest(this.encryptedDigest))) {
                return;
            }
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Signature timestamp (#");
            ((StringBuilder)object2).append(((TimestampToken)object).getSerialNumber());
            ((StringBuilder)object2).append(") generated on ");
            ((StringBuilder)object2).append(((TimestampToken)object).getDate());
            ((StringBuilder)object2).append(" is inapplicable");
            throw new SignatureException(((StringBuilder)object2).toString());
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Timestamp token digest check failed. Disabled algorithm used: ");
        ((StringBuilder)object).append((String)object2);
        throw new SignatureException(((StringBuilder)object).toString());
    }

    @Override
    public void derEncode(OutputStream outputStream) throws IOException {
        DerOutputStream derOutputStream = new DerOutputStream();
        derOutputStream.putInteger(this.version);
        Object object = new DerOutputStream();
        this.issuerName.encode((DerOutputStream)object);
        ((DerOutputStream)object).putInteger(this.certificateSerialNumber);
        derOutputStream.write((byte)48, (DerOutputStream)object);
        this.digestAlgorithmId.encode(derOutputStream);
        object = this.authenticatedAttributes;
        if (object != null) {
            ((PKCS9Attributes)object).encode((byte)-96, derOutputStream);
        }
        this.digestEncryptionAlgorithmId.encode(derOutputStream);
        derOutputStream.putOctetString(this.encryptedDigest);
        object = this.unauthenticatedAttributes;
        if (object != null) {
            ((PKCS9Attributes)object).encode((byte)-95, derOutputStream);
        }
        object = new DerOutputStream();
        ((DerOutputStream)object).write((byte)48, derOutputStream);
        outputStream.write(((ByteArrayOutputStream)object).toByteArray());
    }

    public void encode(DerOutputStream derOutputStream) throws IOException {
        this.derEncode(derOutputStream);
    }

    public PKCS9Attributes getAuthenticatedAttributes() {
        return this.authenticatedAttributes;
    }

    public X509Certificate getCertificate(PKCS7 pKCS7) throws IOException {
        return pKCS7.getCertificate(this.certificateSerialNumber, this.issuerName);
    }

    public ArrayList<X509Certificate> getCertificateChain(PKCS7 object) throws IOException {
        Object object2 = ((PKCS7)object).getCertificate(this.certificateSerialNumber, this.issuerName);
        if (object2 == null) {
            return null;
        }
        ArrayList<X509Certificate> arrayList = new ArrayList<X509Certificate>();
        arrayList.add((X509Certificate)object2);
        X509Certificate[] arrx509Certificate = ((PKCS7)object).getCertificates();
        if (arrx509Certificate != null && !((X509Certificate)object2).getSubjectDN().equals(((X509Certificate)object2).getIssuerDN())) {
            object = ((X509Certificate)object2).getIssuerDN();
            int n = 0;
            do {
                int n2;
                boolean bl;
                boolean bl2 = false;
                int n3 = n;
                do {
                    object2 = object;
                    n2 = n;
                    bl = bl2;
                    if (n3 >= arrx509Certificate.length) break;
                    if (object.equals(arrx509Certificate[n3].getSubjectDN())) {
                        arrayList.add(arrx509Certificate[n3]);
                        if (arrx509Certificate[n3].getSubjectDN().equals(arrx509Certificate[n3].getIssuerDN())) {
                            n = arrx509Certificate.length;
                        } else {
                            object = arrx509Certificate[n3].getIssuerDN();
                            object2 = arrx509Certificate[n];
                            arrx509Certificate[n] = arrx509Certificate[n3];
                            arrx509Certificate[n3] = object2;
                            ++n;
                        }
                        bl = true;
                        object2 = object;
                        n2 = n;
                        break;
                    }
                    ++n3;
                } while (true);
                if (!bl) {
                    return arrayList;
                }
                object = object2;
                n = n2;
            } while (true);
        }
        return arrayList;
    }

    public BigInteger getCertificateSerialNumber() {
        return this.certificateSerialNumber;
    }

    public AlgorithmId getDigestAlgorithmId() {
        return this.digestAlgorithmId;
    }

    public AlgorithmId getDigestEncryptionAlgorithmId() {
        return this.digestEncryptionAlgorithmId;
    }

    public byte[] getEncryptedDigest() {
        return this.encryptedDigest;
    }

    public X500Name getIssuerName() {
        return this.issuerName;
    }

    public Timestamp getTimestamp() throws IOException, NoSuchAlgorithmException, SignatureException, CertificateException {
        if (this.timestamp == null && this.hasTimestamp) {
            Object object = this.getTsToken();
            if (object == null) {
                this.hasTimestamp = false;
                return null;
            }
            Object object2 = ((PKCS7)object).getContentInfo().getData();
            object = ((PKCS7)object).verify((byte[])object2)[0].getCertificateChain((PKCS7)object);
            object = CertificateFactory.getInstance("X.509").generateCertPath((List<? extends Certificate>)object);
            object2 = new TimestampToken((byte[])object2);
            this.verifyTimestamp((TimestampToken)object2);
            this.timestamp = new Timestamp(((TimestampToken)object2).getDate(), (CertPath)object);
            return this.timestamp;
        }
        return this.timestamp;
    }

    public PKCS7 getTsToken() throws IOException {
        Object object = this.unauthenticatedAttributes;
        if (object == null) {
            return null;
        }
        if ((object = ((PKCS9Attributes)object).getAttribute(PKCS9Attribute.SIGNATURE_TIMESTAMP_TOKEN_OID)) == null) {
            return null;
        }
        return new PKCS7((byte[])((PKCS9Attribute)object).getValue());
    }

    public PKCS9Attributes getUnauthenticatedAttributes() {
        return this.unauthenticatedAttributes;
    }

    public BigInteger getVersion() {
        return this.version;
    }

    public String toString() {
        HexDumpEncoder hexDumpEncoder = new HexDumpEncoder();
        CharSequence charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("");
        ((StringBuilder)charSequence).append("Signer Info for (issuer): ");
        ((StringBuilder)charSequence).append(this.issuerName);
        ((StringBuilder)charSequence).append("\n");
        CharSequence charSequence2 = ((StringBuilder)charSequence).toString();
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append((String)charSequence2);
        ((StringBuilder)charSequence).append("\tversion: ");
        ((StringBuilder)charSequence).append(Debug.toHexString(this.version));
        ((StringBuilder)charSequence).append("\n");
        charSequence2 = ((StringBuilder)charSequence).toString();
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append((String)charSequence2);
        ((StringBuilder)charSequence).append("\tcertificateSerialNumber: ");
        ((StringBuilder)charSequence).append(Debug.toHexString(this.certificateSerialNumber));
        ((StringBuilder)charSequence).append("\n");
        charSequence2 = ((StringBuilder)charSequence).toString();
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append((String)charSequence2);
        ((StringBuilder)charSequence).append("\tdigestAlgorithmId: ");
        ((StringBuilder)charSequence).append(this.digestAlgorithmId);
        ((StringBuilder)charSequence).append("\n");
        charSequence2 = ((StringBuilder)charSequence).toString();
        charSequence = charSequence2;
        if (this.authenticatedAttributes != null) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append((String)charSequence2);
            ((StringBuilder)charSequence).append("\tauthenticatedAttributes: ");
            ((StringBuilder)charSequence).append(this.authenticatedAttributes);
            ((StringBuilder)charSequence).append("\n");
            charSequence = ((StringBuilder)charSequence).toString();
        }
        charSequence2 = new StringBuilder();
        ((StringBuilder)charSequence2).append((String)charSequence);
        ((StringBuilder)charSequence2).append("\tdigestEncryptionAlgorithmId: ");
        ((StringBuilder)charSequence2).append(this.digestEncryptionAlgorithmId);
        ((StringBuilder)charSequence2).append("\n");
        charSequence = ((StringBuilder)charSequence2).toString();
        charSequence2 = new StringBuilder();
        ((StringBuilder)charSequence2).append((String)charSequence);
        ((StringBuilder)charSequence2).append("\tencryptedDigest: \n");
        ((StringBuilder)charSequence2).append(hexDumpEncoder.encodeBuffer(this.encryptedDigest));
        ((StringBuilder)charSequence2).append("\n");
        charSequence2 = ((StringBuilder)charSequence2).toString();
        charSequence = charSequence2;
        if (this.unauthenticatedAttributes != null) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append((String)charSequence2);
            ((StringBuilder)charSequence).append("\tunauthenticatedAttributes: ");
            ((StringBuilder)charSequence).append(this.unauthenticatedAttributes);
            ((StringBuilder)charSequence).append("\n");
            charSequence = ((StringBuilder)charSequence).toString();
        }
        return charSequence;
    }

    SignerInfo verify(PKCS7 pKCS7) throws NoSuchAlgorithmException, SignatureException {
        return this.verify(pKCS7, (byte[])null);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    SignerInfo verify(PKCS7 var1_1, InputStream var2_10) throws NoSuchAlgorithmException, SignatureException, IOException {
        block24 : {
            block23 : {
                block22 : {
                    var3_11 = var1_1.getContentInfo();
                    if (var2_10 != null) break block22;
                    var2_10 = new ByteArrayInputStream(var3_11.getContentBytes());
                }
                try {
                    block25 : {
                        block26 : {
                            block27 : {
                                block28 : {
                                    var4_12 = this.getDigestAlgorithmId().getName();
                                    if (this.authenticatedAttributes != null) {
                                        var5_13 = (ObjectIdentifier)this.authenticatedAttributes.getAttributeValue(PKCS9Attribute.CONTENT_TYPE_OID);
                                        if (var5_13 == null) return null;
                                        if (!var5_13.equals((Object)var3_11.contentType)) {
                                            return null;
                                        }
                                        var3_11 = (byte[])this.authenticatedAttributes.getAttributeValue(PKCS9Attribute.MESSAGE_DIGEST_OID);
                                        if (var3_11 == null) {
                                            return null;
                                        }
                                        if (SignerInfo.JAR_DISABLED_CHECK.permits(SignerInfo.DIGEST_PRIMITIVE_SET, (String)var4_12, null)) {
                                            var6_14 = MessageDigest.getInstance((String)var4_12);
                                            var5_13 = new byte[4096];
                                            while ((var7_15 = var2_10.read((byte[])var5_13)) != -1) {
                                                var6_14.update((byte[])var5_13, 0, var7_15);
                                            }
                                            var2_10 = var6_14.digest();
                                            if (((byte[])var3_11).length != ((Object)var2_10).length) {
                                                return null;
                                            }
                                        } else {
                                            var2_10 = new StringBuilder();
                                            var2_10.append("Digest check failed. Disabled algorithm used: ");
                                            var2_10.append((String)var4_12);
                                            var1_1 = new SignatureException(var2_10.toString());
                                            throw var1_1;
                                        }
                                    }
                                    ** GOTO lbl45
                                    {
                                        catch (InvalidKeyException var1_6) {
                                            // empty catch block
                                            break block23;
                                        }
                                        catch (IOException var1_8) {
                                            // empty catch block
                                            break block24;
                                        }
                                    }
                                    for (var7_15 = 0; var7_15 < ((Object)var3_11).length; ++var7_15) {
                                        if (var3_11[var7_15] == var2_10[var7_15]) continue;
                                        return null;
                                    }
                                    var2_10 = new ByteArrayInputStream(this.authenticatedAttributes.getDerEncoding());
lbl45: // 2 sources:
                                    var3_11 = this.getDigestEncryptionAlgorithmId().getName();
                                    var5_13 = AlgorithmId.getEncAlgFromSigAlg((String)var3_11);
                                    if (var5_13 != null) {
                                        var3_11 = var5_13;
                                    }
                                    if (!SignerInfo.JAR_DISABLED_CHECK.permits(SignerInfo.SIG_PRIMITIVE_SET, (String)(var3_11 = AlgorithmId.makeSigAlg((String)var4_12, (String)var3_11)), null)) break block25;
                                    var5_13 = this.getCertificate((PKCS7)var1_1);
                                    var1_1 = var5_13.getPublicKey();
                                    if (!SignerInfo.JAR_DISABLED_CHECK.permits(SignerInfo.SIG_PRIMITIVE_SET, (Key)var1_1)) break block26;
                                    if (var5_13.hasUnsupportedCriticalExtension()) break block27;
                                    var4_12 = var5_13.getKeyUsage();
                                    if (var4_12 == null) break block28;
                                    try {
                                        var5_13 = new KeyUsageExtension(var4_12);
                                        var8_16 = var5_13.get("digital_signature");
                                        var9_17 = var5_13.get("non_repudiation");
                                        if (var8_16 || var9_17) break block28;
                                        var1_1 = new SignatureException("Key usage restricted: cannot be used for digital signatures");
                                    }
                                    catch (IOException var1_2) {
                                        var1_3 = new SignatureException("Failed to parse keyUsage extension");
                                        throw var1_3;
                                    }
                                    throw var1_1;
                                }
                                var3_11 = Signature.getInstance((String)var3_11);
                                var3_11.initVerify((PublicKey)var1_1);
                                var1_1 = new byte[4096];
                                do {
                                    if ((var7_15 = var2_10.read((byte[])var1_1)) == -1) {
                                        if (var3_11.verify(this.encryptedDigest) == false) return null;
                                        return this;
                                    }
                                    var3_11.update((byte[])var1_1, 0, var7_15);
                                } while (true);
                            }
                            var1_1 = new SignatureException("Certificate has unsupported critical extension(s)");
                            throw var1_1;
                        }
                        var2_10 = new StringBuilder();
                        var2_10.append("Public key check failed. Disabled key used: ");
                        var2_10.append(KeyUtil.getKeySize((Key)var1_1));
                        var2_10.append(" bit ");
                        var2_10.append(var1_1.getAlgorithm());
                        var3_11 = new SignatureException(var2_10.toString());
                        throw var3_11;
                    }
                    var1_1 = new StringBuilder();
                    var1_1.append("Signature check failed. Disabled algorithm used: ");
                    var1_1.append((String)var3_11);
                    var2_10 = new SignatureException(var1_1.toString());
                    throw var2_10;
                }
                catch (InvalidKeyException var1_4) {
                }
                catch (IOException var1_5) {
                    break block24;
                }
            }
            var2_10 = new StringBuilder();
            var2_10.append("InvalidKey: ");
            var2_10.append(var1_7.getMessage());
            throw new SignatureException(var2_10.toString());
        }
        var2_10 = new StringBuilder();
        var2_10.append("IO error verifying signature:\n");
        var2_10.append(var1_9.getMessage());
        throw new SignatureException(var2_10.toString());
    }

    SignerInfo verify(PKCS7 object, byte[] arrby) throws NoSuchAlgorithmException, SignatureException {
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(arrby);
            object = this.verify((PKCS7)object, byteArrayInputStream);
            return object;
        }
        catch (IOException iOException) {
            return null;
        }
    }
}

