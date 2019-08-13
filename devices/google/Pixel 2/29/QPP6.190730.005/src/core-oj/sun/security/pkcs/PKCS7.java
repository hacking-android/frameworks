/*
 * Decompiled with CFR 0.145.
 */
package sun.security.pkcs;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Principal;
import java.security.Provider;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.cert.CRLException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.security.cert.X509Extension;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import javax.security.auth.x500.X500Principal;
import sun.security.pkcs.ContentInfo;
import sun.security.pkcs.ParsingException;
import sun.security.pkcs.SignerInfo;
import sun.security.util.Debug;
import sun.security.util.DerEncoder;
import sun.security.util.DerInputStream;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import sun.security.util.ObjectIdentifier;
import sun.security.x509.AlgorithmId;
import sun.security.x509.X500Name;
import sun.security.x509.X509CRLImpl;
import sun.security.x509.X509CertImpl;
import sun.security.x509.X509CertInfo;

public class PKCS7 {
    private Principal[] certIssuerNames;
    private X509Certificate[] certificates = null;
    private ContentInfo contentInfo = null;
    private ObjectIdentifier contentType;
    private X509CRL[] crls = null;
    private AlgorithmId[] digestAlgorithmIds = null;
    private boolean oldStyle = false;
    private SignerInfo[] signerInfos = null;
    private BigInteger version = null;

    public PKCS7(InputStream inputStream) throws ParsingException, IOException {
        inputStream = new DataInputStream(inputStream);
        byte[] arrby = new byte[((FilterInputStream)inputStream).available()];
        ((DataInputStream)inputStream).readFully(arrby);
        this.parse(new DerInputStream(arrby));
    }

    public PKCS7(DerInputStream derInputStream) throws ParsingException {
        this.parse(derInputStream);
    }

    public PKCS7(byte[] object) throws ParsingException {
        try {
            DerInputStream derInputStream = new DerInputStream((byte[])object);
            this.parse(derInputStream);
            return;
        }
        catch (IOException iOException) {
            object = new ParsingException("Unable to parse the encoded bytes");
            ((Throwable)object).initCause(iOException);
            throw object;
        }
    }

    public PKCS7(AlgorithmId[] arralgorithmId, ContentInfo contentInfo, X509Certificate[] arrx509Certificate, X509CRL[] arrx509CRL, SignerInfo[] arrsignerInfo) {
        this.version = BigInteger.ONE;
        this.digestAlgorithmIds = arralgorithmId;
        this.contentInfo = contentInfo;
        this.certificates = arrx509Certificate;
        this.crls = arrx509CRL;
        this.signerInfos = arrsignerInfo;
    }

    public PKCS7(AlgorithmId[] arralgorithmId, ContentInfo contentInfo, X509Certificate[] arrx509Certificate, SignerInfo[] arrsignerInfo) {
        this(arralgorithmId, contentInfo, arrx509Certificate, null, arrsignerInfo);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void parse(DerInputStream object) throws ParsingException {
        try {
            ((DerInputStream)object).mark(((DerInputStream)object).available());
            this.parse((DerInputStream)object, false);
            return;
        }
        catch (IOException iOException) {
            try {
                ((DerInputStream)object).reset();
                this.parse((DerInputStream)object, true);
                this.oldStyle = true;
                return;
            }
            catch (IOException iOException2) {
                object = new ParsingException(iOException2.getMessage());
                ((Throwable)object).initCause(iOException);
                ((Throwable)object).addSuppressed(iOException2);
                throw object;
            }
        }
    }

    private void parse(DerInputStream object, boolean bl) throws IOException {
        block5 : {
            block3 : {
                block4 : {
                    block2 : {
                        this.contentInfo = new ContentInfo((DerInputStream)object, bl);
                        this.contentType = this.contentInfo.contentType;
                        object = this.contentInfo.getContent();
                        if (!this.contentType.equals((Object)ContentInfo.SIGNED_DATA_OID)) break block2;
                        this.parseSignedData((DerValue)object);
                        break block3;
                    }
                    if (!this.contentType.equals((Object)ContentInfo.OLD_SIGNED_DATA_OID)) break block4;
                    this.parseOldSignedData((DerValue)object);
                    break block3;
                }
                if (!this.contentType.equals((Object)ContentInfo.NETSCAPE_CERT_SEQUENCE_OID)) break block5;
                this.parseNetscapeCertChain((DerValue)object);
            }
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("content type ");
        ((StringBuilder)object).append(this.contentType);
        ((StringBuilder)object).append(" not supported.");
        throw new ParsingException(((StringBuilder)object).toString());
    }

    /*
     * Exception decompiling
     */
    private void parseNetscapeCertChain(DerValue var1_1) throws ParsingException, IOException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [5[CATCHBLOCK]], but top level block is 2[TRYBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    /*
     * Exception decompiling
     */
    private void parseOldSignedData(DerValue var1_1) throws ParsingException, IOException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [7[CATCHBLOCK]], but top level block is 3[TRYBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    /*
     * Exception decompiling
     */
    private void parseSignedData(DerValue var1_1) throws ParsingException, IOException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [9[CATCHBLOCK]], but top level block is 3[TRYBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    private void populateCertIssuerNames() {
        Object object = this.certificates;
        if (object == null) {
            return;
        }
        this.certIssuerNames = new Principal[((X509Certificate[])object).length];
        for (int i = 0; i < ((X509Certificate[])(object = this.certificates)).length; ++i) {
            X509Certificate x509Certificate = object[i];
            Principal principal = x509Certificate.getIssuerDN();
            object = principal;
            if (!(principal instanceof X500Name)) {
                try {
                    object = new X509CertInfo(x509Certificate.getTBSCertificate());
                    object = (Principal)((X509CertInfo)object).get("issuer.dname");
                }
                catch (Exception exception) {
                    object = principal;
                }
            }
            this.certIssuerNames[i] = object;
        }
    }

    public void encodeSignedData(OutputStream outputStream) throws IOException {
        DerOutputStream derOutputStream = new DerOutputStream();
        this.encodeSignedData(derOutputStream);
        outputStream.write(derOutputStream.toByteArray());
    }

    public void encodeSignedData(DerOutputStream derOutputStream) throws IOException {
        Object object = new DerOutputStream();
        ((DerOutputStream)object).putInteger(this.version);
        ((DerOutputStream)object).putOrderedSetOf((byte)49, this.digestAlgorithmIds);
        this.contentInfo.encode((DerOutputStream)object);
        Object object2 = this.certificates;
        if (object2 != null && ((X509Certificate[])object2).length != 0) {
            X509Extension[] arrx509Extension = new X509CertImpl[((X509Extension[])object2).length];
            for (int i = 0; i < ((X509Extension[])(object2 = this.certificates)).length; ++i) {
                if (object2[i] instanceof X509CertImpl) {
                    arrx509Extension[i] = (X509CertImpl)object2[i];
                    continue;
                }
                try {
                    arrx509Extension[i] = new X509CertImpl(((Certificate)((Object)object2[i])).getEncoded());
                }
                catch (CertificateException certificateException) {
                    throw new IOException(certificateException);
                }
            }
            ((DerOutputStream)object).putOrderedSetOf((byte)-96, (DerEncoder[])arrx509Extension);
        }
        if ((object2 = this.crls) != null && ((X509Extension[])object2).length != 0) {
            object2 = new HashSet(((X509Extension[])object2).length);
            for (X509Extension x509Extension : this.crls) {
                if (x509Extension instanceof X509CRLImpl) {
                    object2.add((X509CRLImpl)x509Extension);
                    continue;
                }
                try {
                    byte[] arrby = ((X509CRL)x509Extension).getEncoded();
                    X509CRLImpl x509CRLImpl = new X509CRLImpl(arrby);
                    object2.add(x509CRLImpl);
                }
                catch (CRLException cRLException) {
                    throw new IOException(cRLException);
                }
            }
            ((DerOutputStream)object).putOrderedSetOf((byte)-95, object2.toArray(new X509CRLImpl[object2.size()]));
        }
        ((DerOutputStream)object).putOrderedSetOf((byte)49, this.signerInfos);
        object = new DerValue(48, ((ByteArrayOutputStream)object).toByteArray());
        new ContentInfo(ContentInfo.SIGNED_DATA_OID, (DerValue)object).encode(derOutputStream);
    }

    public X509CRL[] getCRLs() {
        X509CRL[] arrx509CRL = this.crls;
        if (arrx509CRL != null) {
            return (X509CRL[])arrx509CRL.clone();
        }
        return null;
    }

    public X509Certificate getCertificate(BigInteger bigInteger, X500Name x500Name) {
        if (this.certificates != null) {
            Object object;
            if (this.certIssuerNames == null) {
                this.populateCertIssuerNames();
            }
            for (int i = 0; i < ((X509Certificate[])(object = this.certificates)).length; ++i) {
                if (!bigInteger.equals(((X509Certificate)(object = object[i])).getSerialNumber()) || !x500Name.equals(this.certIssuerNames[i])) continue;
                return object;
            }
        }
        return null;
    }

    public X509Certificate[] getCertificates() {
        X509Certificate[] arrx509Certificate = this.certificates;
        if (arrx509Certificate != null) {
            return (X509Certificate[])arrx509Certificate.clone();
        }
        return null;
    }

    public ContentInfo getContentInfo() {
        return this.contentInfo;
    }

    public AlgorithmId[] getDigestAlgorithmIds() {
        return this.digestAlgorithmIds;
    }

    public SignerInfo[] getSignerInfos() {
        return this.signerInfos;
    }

    public BigInteger getVersion() {
        return this.version;
    }

    public boolean isOldStyle() {
        return this.oldStyle;
    }

    public String toString() {
        int n;
        CharSequence charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("");
        ((StringBuilder)charSequence).append(this.contentInfo);
        ((StringBuilder)charSequence).append("\n");
        CharSequence charSequence2 = charSequence = ((StringBuilder)charSequence).toString();
        if (this.version != null) {
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append((String)charSequence);
            ((StringBuilder)charSequence2).append("PKCS7 :: version: ");
            ((StringBuilder)charSequence2).append(Debug.toHexString(this.version));
            ((StringBuilder)charSequence2).append("\n");
            charSequence2 = ((StringBuilder)charSequence2).toString();
        }
        charSequence = charSequence2;
        if (this.digestAlgorithmIds != null) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append((String)charSequence2);
            ((StringBuilder)charSequence).append("PKCS7 :: digest AlgorithmIds: \n");
            charSequence2 = ((StringBuilder)charSequence).toString();
            n = 0;
            do {
                charSequence = charSequence2;
                if (n >= this.digestAlgorithmIds.length) break;
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append((String)charSequence2);
                ((StringBuilder)charSequence).append("\t");
                ((StringBuilder)charSequence).append(this.digestAlgorithmIds[n]);
                ((StringBuilder)charSequence).append("\n");
                charSequence2 = ((StringBuilder)charSequence).toString();
                ++n;
            } while (true);
        }
        charSequence2 = charSequence;
        if (this.certificates != null) {
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append((String)charSequence);
            ((StringBuilder)charSequence2).append("PKCS7 :: certificates: \n");
            charSequence = ((StringBuilder)charSequence2).toString();
            n = 0;
            do {
                charSequence2 = charSequence;
                if (n >= this.certificates.length) break;
                charSequence2 = new StringBuilder();
                ((StringBuilder)charSequence2).append((String)charSequence);
                ((StringBuilder)charSequence2).append("\t");
                ((StringBuilder)charSequence2).append(n);
                ((StringBuilder)charSequence2).append(".   ");
                ((StringBuilder)charSequence2).append(this.certificates[n]);
                ((StringBuilder)charSequence2).append("\n");
                charSequence = ((StringBuilder)charSequence2).toString();
                ++n;
            } while (true);
        }
        charSequence = charSequence2;
        if (this.crls != null) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append((String)charSequence2);
            ((StringBuilder)charSequence).append("PKCS7 :: crls: \n");
            charSequence2 = ((StringBuilder)charSequence).toString();
            n = 0;
            do {
                charSequence = charSequence2;
                if (n >= this.crls.length) break;
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append((String)charSequence2);
                ((StringBuilder)charSequence).append("\t");
                ((StringBuilder)charSequence).append(n);
                ((StringBuilder)charSequence).append(".   ");
                ((StringBuilder)charSequence).append(this.crls[n]);
                ((StringBuilder)charSequence).append("\n");
                charSequence2 = ((StringBuilder)charSequence).toString();
                ++n;
            } while (true);
        }
        charSequence2 = charSequence;
        if (this.signerInfos != null) {
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append((String)charSequence);
            ((StringBuilder)charSequence2).append("PKCS7 :: signer infos: \n");
            charSequence = ((StringBuilder)charSequence2).toString();
            n = 0;
            do {
                charSequence2 = charSequence;
                if (n >= this.signerInfos.length) break;
                charSequence2 = new StringBuilder();
                ((StringBuilder)charSequence2).append((String)charSequence);
                ((StringBuilder)charSequence2).append("\t");
                ((StringBuilder)charSequence2).append(n);
                ((StringBuilder)charSequence2).append(".  ");
                ((StringBuilder)charSequence2).append(this.signerInfos[n]);
                ((StringBuilder)charSequence2).append("\n");
                charSequence = ((StringBuilder)charSequence2).toString();
                ++n;
            } while (true);
        }
        return charSequence2;
    }

    public SignerInfo verify(SignerInfo signerInfo, InputStream inputStream) throws NoSuchAlgorithmException, SignatureException, IOException {
        return signerInfo.verify(this, inputStream);
    }

    public SignerInfo verify(SignerInfo signerInfo, byte[] arrby) throws NoSuchAlgorithmException, SignatureException {
        return signerInfo.verify(this, arrby);
    }

    public SignerInfo[] verify() throws NoSuchAlgorithmException, SignatureException {
        return this.verify(null);
    }

    public SignerInfo[] verify(byte[] arrobject) throws NoSuchAlgorithmException, SignatureException {
        Object object;
        Vector<SignerInfo[]> vector = new Vector<SignerInfo[]>();
        for (int i = 0; i < ((SignerInfo[])(object = this.signerInfos)).length; ++i) {
            if ((object = this.verify(object[i], (byte[])arrobject)) == null) continue;
            vector.addElement((SignerInfo[])object);
        }
        if (!vector.isEmpty()) {
            arrobject = new SignerInfo[vector.size()];
            vector.copyInto(arrobject);
            return arrobject;
        }
        return null;
    }

    private static class VerbatimX509Certificate
    extends WrappedX509Certificate {
        private byte[] encodedVerbatim;

        public VerbatimX509Certificate(X509Certificate x509Certificate, byte[] arrby) {
            super(x509Certificate);
            this.encodedVerbatim = arrby;
        }

        @Override
        public byte[] getEncoded() throws CertificateEncodingException {
            return this.encodedVerbatim;
        }
    }

    private static class WrappedX509Certificate
    extends X509Certificate {
        private final X509Certificate wrapped;

        public WrappedX509Certificate(X509Certificate x509Certificate) {
            this.wrapped = x509Certificate;
        }

        @Override
        public void checkValidity() throws CertificateExpiredException, CertificateNotYetValidException {
            this.wrapped.checkValidity();
        }

        @Override
        public void checkValidity(Date date) throws CertificateExpiredException, CertificateNotYetValidException {
            this.wrapped.checkValidity(date);
        }

        @Override
        public int getBasicConstraints() {
            return this.wrapped.getBasicConstraints();
        }

        @Override
        public Set<String> getCriticalExtensionOIDs() {
            return this.wrapped.getCriticalExtensionOIDs();
        }

        @Override
        public byte[] getEncoded() throws CertificateEncodingException {
            return this.wrapped.getEncoded();
        }

        @Override
        public List<String> getExtendedKeyUsage() throws CertificateParsingException {
            return this.wrapped.getExtendedKeyUsage();
        }

        @Override
        public byte[] getExtensionValue(String string) {
            return this.wrapped.getExtensionValue(string);
        }

        @Override
        public Collection<List<?>> getIssuerAlternativeNames() throws CertificateParsingException {
            return this.wrapped.getIssuerAlternativeNames();
        }

        @Override
        public Principal getIssuerDN() {
            return this.wrapped.getIssuerDN();
        }

        @Override
        public boolean[] getIssuerUniqueID() {
            return this.wrapped.getIssuerUniqueID();
        }

        @Override
        public X500Principal getIssuerX500Principal() {
            return this.wrapped.getIssuerX500Principal();
        }

        @Override
        public boolean[] getKeyUsage() {
            return this.wrapped.getKeyUsage();
        }

        @Override
        public Set<String> getNonCriticalExtensionOIDs() {
            return this.wrapped.getNonCriticalExtensionOIDs();
        }

        @Override
        public Date getNotAfter() {
            return this.wrapped.getNotAfter();
        }

        @Override
        public Date getNotBefore() {
            return this.wrapped.getNotBefore();
        }

        @Override
        public PublicKey getPublicKey() {
            return this.wrapped.getPublicKey();
        }

        @Override
        public BigInteger getSerialNumber() {
            return this.wrapped.getSerialNumber();
        }

        @Override
        public String getSigAlgName() {
            return this.wrapped.getSigAlgName();
        }

        @Override
        public String getSigAlgOID() {
            return this.wrapped.getSigAlgOID();
        }

        @Override
        public byte[] getSigAlgParams() {
            return this.wrapped.getSigAlgParams();
        }

        @Override
        public byte[] getSignature() {
            return this.wrapped.getSignature();
        }

        @Override
        public Collection<List<?>> getSubjectAlternativeNames() throws CertificateParsingException {
            return this.wrapped.getSubjectAlternativeNames();
        }

        @Override
        public Principal getSubjectDN() {
            return this.wrapped.getSubjectDN();
        }

        @Override
        public boolean[] getSubjectUniqueID() {
            return this.wrapped.getSubjectUniqueID();
        }

        @Override
        public X500Principal getSubjectX500Principal() {
            return this.wrapped.getSubjectX500Principal();
        }

        @Override
        public byte[] getTBSCertificate() throws CertificateEncodingException {
            return this.wrapped.getTBSCertificate();
        }

        @Override
        public int getVersion() {
            return this.wrapped.getVersion();
        }

        @Override
        public boolean hasUnsupportedCriticalExtension() {
            return this.wrapped.hasUnsupportedCriticalExtension();
        }

        @Override
        public String toString() {
            return this.wrapped.toString();
        }

        @Override
        public void verify(PublicKey publicKey) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {
            this.wrapped.verify(publicKey);
        }

        @Override
        public void verify(PublicKey publicKey, String string) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {
            this.wrapped.verify(publicKey, string);
        }

        @Override
        public void verify(PublicKey publicKey, Provider provider) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
            this.wrapped.verify(publicKey, provider);
        }
    }

}

