/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.compat.UnsupportedAppUsage
 */
package com.android.org.bouncycastle.jce.provider;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1InputStream;
import com.android.org.bouncycastle.asn1.ASN1Integer;
import com.android.org.bouncycastle.asn1.ASN1Object;
import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.ASN1OctetString;
import com.android.org.bouncycastle.asn1.ASN1OutputStream;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1Sequence;
import com.android.org.bouncycastle.asn1.ASN1String;
import com.android.org.bouncycastle.asn1.DERBitString;
import com.android.org.bouncycastle.asn1.DERIA5String;
import com.android.org.bouncycastle.asn1.DERNull;
import com.android.org.bouncycastle.asn1.DEROctetString;
import com.android.org.bouncycastle.asn1.misc.MiscObjectIdentifiers;
import com.android.org.bouncycastle.asn1.misc.NetscapeCertType;
import com.android.org.bouncycastle.asn1.misc.NetscapeRevocationURL;
import com.android.org.bouncycastle.asn1.misc.VerisignCzagExtension;
import com.android.org.bouncycastle.asn1.util.ASN1Dump;
import com.android.org.bouncycastle.asn1.x500.X500Name;
import com.android.org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import com.android.org.bouncycastle.asn1.x509.BasicConstraints;
import com.android.org.bouncycastle.asn1.x509.Extension;
import com.android.org.bouncycastle.asn1.x509.Extensions;
import com.android.org.bouncycastle.asn1.x509.GeneralName;
import com.android.org.bouncycastle.asn1.x509.KeyUsage;
import com.android.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import com.android.org.bouncycastle.asn1.x509.TBSCertificate;
import com.android.org.bouncycastle.asn1.x509.Time;
import com.android.org.bouncycastle.asn1.x509.X509Name;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.util.PKCS12BagAttributeCarrierImpl;
import com.android.org.bouncycastle.jce.X509Principal;
import com.android.org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier;
import com.android.org.bouncycastle.jce.provider.BouncyCastleProvider;
import com.android.org.bouncycastle.jce.provider.RFC3280CertPathUtilities;
import com.android.org.bouncycastle.jce.provider.X509SignatureUtil;
import com.android.org.bouncycastle.util.Arrays;
import com.android.org.bouncycastle.util.Integers;
import com.android.org.bouncycastle.util.Strings;
import com.android.org.bouncycastle.util.encoders.Hex;
import dalvik.annotation.compat.UnsupportedAppUsage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Principal;
import java.security.Provider;
import java.security.PublicKey;
import java.security.Security;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import javax.security.auth.x500.X500Principal;

public class X509CertificateObject
extends X509Certificate
implements PKCS12BagAttributeCarrier {
    private PKCS12BagAttributeCarrier attrCarrier;
    private BasicConstraints basicConstraints;
    private com.android.org.bouncycastle.asn1.x509.Certificate c;
    private byte[] encoded;
    private int hashValue;
    private boolean hashValueSet;
    private boolean[] keyUsage;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @UnsupportedAppUsage
    public X509CertificateObject(com.android.org.bouncycastle.asn1.x509.Certificate arrby) throws CertificateParsingException {
        boolean[] arrbl;
        int n;
        int n2;
        block9 : {
            this.attrCarrier = new PKCS12BagAttributeCarrierImpl();
            this.c = arrby;
            try {
                arrby = this.getExtensionBytes("2.5.29.19");
                if (arrby == null) break block9;
                this.basicConstraints = BasicConstraints.getInstance(ASN1Primitive.fromByteArray(arrby));
            }
            catch (Exception exception) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("cannot construct BasicConstraints: ");
                stringBuilder.append(exception);
                throw new CertificateParsingException(stringBuilder.toString());
            }
        }
        try {
            arrby = this.getExtensionBytes("2.5.29.15");
            if (arrby != null) {
                arrbl = DERBitString.getInstance(ASN1Primitive.fromByteArray(arrby));
                arrby = arrbl.getBytes();
                n2 = arrby.length * 8 - arrbl.getPadBits();
                n = 9;
                if (n2 >= 9) {
                    n = n2;
                }
            } else {
                this.keyUsage = null;
                return;
            }
            this.keyUsage = new boolean[n];
            n = 0;
        }
        catch (Exception exception) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("cannot construct KeyUsage: ");
            stringBuilder.append(exception);
            throw new CertificateParsingException(stringBuilder.toString());
        }
        while (n != n2) {
            arrbl = this.keyUsage;
            boolean bl = (arrby[n / 8] & 128 >>> n % 8) != 0;
            arrbl[n] = bl;
            ++n;
        }
    }

    private int calculateHashCode() {
        byte[] arrby;
        int n;
        int n2 = 0;
        try {
            arrby = this.getEncoded();
            n = 1;
        }
        catch (CertificateEncodingException certificateEncodingException) {
            return 0;
        }
        do {
            if (n >= arrby.length) break;
            byte by = arrby[n];
            n2 += by * n;
            ++n;
        } while (true);
        return n2;
    }

    private void checkSignature(PublicKey publicKey, Signature signature) throws CertificateException, NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        if (this.isAlgIdEqual(this.c.getSignatureAlgorithm(), this.c.getTBSCertificate().getSignature())) {
            X509SignatureUtil.setSignatureParameters(signature, this.c.getSignatureAlgorithm().getParameters());
            signature.initVerify(publicKey);
            signature.update(this.getTBSCertificate());
            if (signature.verify(this.getSignature())) {
                return;
            }
            throw new SignatureException("certificate does not verify with supplied key");
        }
        throw new CertificateException("signature algorithm in TBS cert not same as outer cert");
    }

    /*
     * Enabled aggressive exception aggregation
     */
    private static Collection getAlternativeNames(byte[] object) throws CertificateParsingException {
        if (object == null) {
            return null;
        }
        try {
            Serializable serializable = new ArrayList();
            object = ASN1Sequence.getInstance(object).getObjects();
            block11 : while (object.hasMoreElements()) {
                Object object2;
                block14 : {
                    object2 = GeneralName.getInstance(object.nextElement());
                    ArrayList<Object> arrayList = new ArrayList<Object>();
                    arrayList.add(Integers.valueOf(((GeneralName)object2).getTagNo()));
                    switch (((GeneralName)object2).getTagNo()) {
                        default: {
                            break block14;
                        }
                        case 8: {
                            arrayList.add(ASN1ObjectIdentifier.getInstance(((GeneralName)object2).getName()).getId());
                            break;
                        }
                        case 7: {
                            object2 = DEROctetString.getInstance(((GeneralName)object2).getName()).getOctets();
                            try {
                                object2 = InetAddress.getByAddress((byte[])object2).getHostAddress();
                                arrayList.add(object2);
                            }
                            catch (UnknownHostException unknownHostException) {
                                continue block11;
                            }
                            break;
                        }
                        case 4: {
                            arrayList.add(X509Name.getInstance(((GeneralName)object2).getName()).toString(true, X509Name.DefaultSymbols));
                            break;
                        }
                        case 1: 
                        case 2: 
                        case 6: {
                            arrayList.add(((ASN1String)((Object)((GeneralName)object2).getName())).getString());
                            break;
                        }
                        case 0: 
                        case 3: 
                        case 5: {
                            arrayList.add(((ASN1Object)object2).getEncoded());
                        }
                    }
                    serializable.add(Collections.unmodifiableList(arrayList));
                    continue;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("Bad tag number: ");
                ((StringBuilder)object).append(((GeneralName)object2).getTagNo());
                serializable = new IOException(((StringBuilder)object).toString());
                throw serializable;
            }
            if (serializable.size() == 0) {
                return null;
            }
            object = Collections.unmodifiableCollection(serializable);
            return object;
        }
        catch (Exception exception) {
            throw new CertificateParsingException(exception.getMessage());
        }
    }

    private byte[] getExtensionBytes(String object) {
        Extensions extensions = this.c.getTBSCertificate().getExtensions();
        if (extensions != null && (object = extensions.getExtension(new ASN1ObjectIdentifier((String)object))) != null) {
            return ((Extension)object).getExtnValue().getOctets();
        }
        return null;
    }

    private boolean isAlgIdEqual(AlgorithmIdentifier algorithmIdentifier, AlgorithmIdentifier algorithmIdentifier2) {
        if (!algorithmIdentifier.getAlgorithm().equals(algorithmIdentifier2.getAlgorithm())) {
            return false;
        }
        if (algorithmIdentifier.getParameters() == null) {
            return algorithmIdentifier2.getParameters() == null || algorithmIdentifier2.getParameters().equals(DERNull.INSTANCE);
        }
        if (algorithmIdentifier2.getParameters() == null) {
            return algorithmIdentifier.getParameters() == null || algorithmIdentifier.getParameters().equals(DERNull.INSTANCE);
        }
        return algorithmIdentifier.getParameters().equals(algorithmIdentifier2.getParameters());
    }

    @Override
    public void checkValidity() throws CertificateExpiredException, CertificateNotYetValidException {
        this.checkValidity(new Date());
    }

    @Override
    public void checkValidity(Date serializable) throws CertificateExpiredException, CertificateNotYetValidException {
        if (((Date)serializable).getTime() <= this.getNotAfter().getTime()) {
            if (((Date)serializable).getTime() >= this.getNotBefore().getTime()) {
                return;
            }
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("certificate not valid till ");
            ((StringBuilder)serializable).append(this.c.getStartDate().getTime());
            throw new CertificateNotYetValidException(((StringBuilder)serializable).toString());
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("certificate expired on ");
        ((StringBuilder)serializable).append(this.c.getEndDate().getTime());
        throw new CertificateExpiredException(((StringBuilder)serializable).toString());
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof Certificate)) {
            return false;
        }
        object = (Certificate)object;
        try {
            boolean bl = Arrays.areEqual(this.getEncoded(), ((Certificate)object).getEncoded());
            return bl;
        }
        catch (CertificateEncodingException certificateEncodingException) {
            return false;
        }
    }

    @Override
    public ASN1Encodable getBagAttribute(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        return this.attrCarrier.getBagAttribute(aSN1ObjectIdentifier);
    }

    @Override
    public Enumeration getBagAttributeKeys() {
        return this.attrCarrier.getBagAttributeKeys();
    }

    @Override
    public int getBasicConstraints() {
        BasicConstraints basicConstraints = this.basicConstraints;
        if (basicConstraints != null) {
            if (basicConstraints.isCA()) {
                if (this.basicConstraints.getPathLenConstraint() == null) {
                    return Integer.MAX_VALUE;
                }
                return this.basicConstraints.getPathLenConstraint().intValue();
            }
            return -1;
        }
        return -1;
    }

    public Set getCriticalExtensionOIDs() {
        if (this.getVersion() == 3) {
            HashSet<String> hashSet = new HashSet<String>();
            Extensions extensions = this.c.getTBSCertificate().getExtensions();
            if (extensions != null) {
                Enumeration enumeration = extensions.oids();
                while (enumeration.hasMoreElements()) {
                    ASN1ObjectIdentifier aSN1ObjectIdentifier = (ASN1ObjectIdentifier)enumeration.nextElement();
                    if (!extensions.getExtension(aSN1ObjectIdentifier).isCritical()) continue;
                    hashSet.add(aSN1ObjectIdentifier.getId());
                }
                return hashSet;
            }
        }
        return null;
    }

    @Override
    public byte[] getEncoded() throws CertificateEncodingException {
        try {
            if (this.encoded == null) {
                this.encoded = this.c.getEncoded("DER");
            }
            byte[] arrby = this.encoded;
            return arrby;
        }
        catch (IOException iOException) {
            throw new CertificateEncodingException(iOException.toString());
        }
    }

    public List getExtendedKeyUsage() throws CertificateParsingException {
        Object object = this.getExtensionBytes("2.5.29.37");
        if (object != null) {
            ArrayList<String> arrayList = new ArrayList<String>((byte[])object);
            object = (ASN1Sequence)((ASN1InputStream)((Object)arrayList)).readObject();
            arrayList = new ArrayList<String>();
            int n = 0;
            do {
                if (n == ((ASN1Sequence)object).size()) break;
                arrayList.add(((ASN1ObjectIdentifier)((ASN1Sequence)object).getObjectAt(n)).getId());
                ++n;
            } while (true);
            try {
                object = Collections.unmodifiableList(arrayList);
                return object;
            }
            catch (Exception exception) {
                throw new CertificateParsingException("error processing extended key usage extension");
            }
        }
        return null;
    }

    @Override
    public byte[] getExtensionValue(String object) {
        Extensions extensions = this.c.getTBSCertificate().getExtensions();
        if (extensions != null && (object = extensions.getExtension(new ASN1ObjectIdentifier((String)object))) != null) {
            try {
                object = ((Extension)object).getExtnValue().getEncoded();
                return object;
            }
            catch (Exception exception) {
                object = new StringBuilder();
                ((StringBuilder)object).append("error parsing ");
                ((StringBuilder)object).append(exception.toString());
                throw new IllegalStateException(((StringBuilder)object).toString());
            }
        }
        return null;
    }

    public Collection getIssuerAlternativeNames() throws CertificateParsingException {
        return X509CertificateObject.getAlternativeNames(this.getExtensionBytes(Extension.issuerAlternativeName.getId()));
    }

    @Override
    public Principal getIssuerDN() {
        try {
            X509Principal x509Principal = new X509Principal(X500Name.getInstance(this.c.getIssuer().getEncoded()));
            return x509Principal;
        }
        catch (IOException iOException) {
            return null;
        }
    }

    @Override
    public boolean[] getIssuerUniqueID() {
        boolean[] arrbl = this.c.getTBSCertificate().getIssuerUniqueId();
        if (arrbl != null) {
            byte[] arrby = arrbl.getBytes();
            arrbl = new boolean[arrby.length * 8 - arrbl.getPadBits()];
            for (int i = 0; i != arrbl.length; ++i) {
                boolean bl = (arrby[i / 8] & 128 >>> i % 8) != 0;
                arrbl[i] = bl;
            }
            return arrbl;
        }
        return null;
    }

    @Override
    public X500Principal getIssuerX500Principal() {
        try {
            Object object = new ByteArrayOutputStream();
            ASN1OutputStream aSN1OutputStream = new ASN1OutputStream((OutputStream)object);
            aSN1OutputStream.writeObject(this.c.getIssuer());
            object = new X500Principal(((ByteArrayOutputStream)object).toByteArray());
            return object;
        }
        catch (IOException iOException) {
            throw new IllegalStateException("can't encode issuer DN");
        }
    }

    @Override
    public boolean[] getKeyUsage() {
        return this.keyUsage;
    }

    public Set getNonCriticalExtensionOIDs() {
        if (this.getVersion() == 3) {
            HashSet<String> hashSet = new HashSet<String>();
            Extensions extensions = this.c.getTBSCertificate().getExtensions();
            if (extensions != null) {
                Enumeration enumeration = extensions.oids();
                while (enumeration.hasMoreElements()) {
                    ASN1ObjectIdentifier aSN1ObjectIdentifier = (ASN1ObjectIdentifier)enumeration.nextElement();
                    if (extensions.getExtension(aSN1ObjectIdentifier).isCritical()) continue;
                    hashSet.add(aSN1ObjectIdentifier.getId());
                }
                return hashSet;
            }
        }
        return null;
    }

    @Override
    public Date getNotAfter() {
        return this.c.getEndDate().getDate();
    }

    @Override
    public Date getNotBefore() {
        return this.c.getStartDate().getDate();
    }

    @Override
    public PublicKey getPublicKey() {
        try {
            PublicKey publicKey = BouncyCastleProvider.getPublicKey(this.c.getSubjectPublicKeyInfo());
            return publicKey;
        }
        catch (IOException iOException) {
            return null;
        }
    }

    @Override
    public BigInteger getSerialNumber() {
        return this.c.getSerialNumber().getValue();
    }

    @Override
    public String getSigAlgName() {
        Object object;
        Object object2 = Security.getProvider("BC");
        if (object2 != null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Alg.Alias.Signature.");
            ((StringBuilder)object).append(this.getSigAlgOID());
            object = ((Provider)object2).getProperty(((StringBuilder)object).toString());
            if (object != null) {
                return object;
            }
        }
        object = Security.getProviders();
        for (int i = 0; i != ((Object)object).length; ++i) {
            object2 = object[i];
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Alg.Alias.Signature.");
            stringBuilder.append(this.getSigAlgOID());
            object2 = ((Provider)object2).getProperty(stringBuilder.toString());
            if (object2 == null) continue;
            return object2;
        }
        return this.getSigAlgOID();
    }

    @Override
    public String getSigAlgOID() {
        return this.c.getSignatureAlgorithm().getAlgorithm().getId();
    }

    @Override
    public byte[] getSigAlgParams() {
        if (this.c.getSignatureAlgorithm().getParameters() != null) {
            try {
                byte[] arrby = this.c.getSignatureAlgorithm().getParameters().toASN1Primitive().getEncoded("DER");
                return arrby;
            }
            catch (IOException iOException) {
                return null;
            }
        }
        return null;
    }

    @Override
    public byte[] getSignature() {
        return this.c.getSignature().getOctets();
    }

    public Collection getSubjectAlternativeNames() throws CertificateParsingException {
        return X509CertificateObject.getAlternativeNames(this.getExtensionBytes(Extension.subjectAlternativeName.getId()));
    }

    @Override
    public Principal getSubjectDN() {
        return new X509Principal(X500Name.getInstance(this.c.getSubject().toASN1Primitive()));
    }

    @Override
    public boolean[] getSubjectUniqueID() {
        boolean[] arrbl = this.c.getTBSCertificate().getSubjectUniqueId();
        if (arrbl != null) {
            byte[] arrby = arrbl.getBytes();
            arrbl = new boolean[arrby.length * 8 - arrbl.getPadBits()];
            for (int i = 0; i != arrbl.length; ++i) {
                boolean bl = (arrby[i / 8] & 128 >>> i % 8) != 0;
                arrbl[i] = bl;
            }
            return arrbl;
        }
        return null;
    }

    @Override
    public X500Principal getSubjectX500Principal() {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            Object object = new ASN1OutputStream(byteArrayOutputStream);
            ((ASN1OutputStream)object).writeObject(this.c.getSubject());
            object = new X500Principal(byteArrayOutputStream.toByteArray());
            return object;
        }
        catch (IOException iOException) {
            throw new IllegalStateException("can't encode issuer DN");
        }
    }

    @Override
    public byte[] getTBSCertificate() throws CertificateEncodingException {
        try {
            byte[] arrby = this.c.getTBSCertificate().getEncoded("DER");
            return arrby;
        }
        catch (IOException iOException) {
            throw new CertificateEncodingException(iOException.toString());
        }
    }

    @Override
    public int getVersion() {
        return this.c.getVersionNumber();
    }

    @Override
    public boolean hasUnsupportedCriticalExtension() {
        Extensions extensions;
        if (this.getVersion() == 3 && (extensions = this.c.getTBSCertificate().getExtensions()) != null) {
            Enumeration enumeration = extensions.oids();
            while (enumeration.hasMoreElements()) {
                ASN1ObjectIdentifier aSN1ObjectIdentifier = (ASN1ObjectIdentifier)enumeration.nextElement();
                String string = aSN1ObjectIdentifier.getId();
                if (string.equals(RFC3280CertPathUtilities.KEY_USAGE) || string.equals(RFC3280CertPathUtilities.CERTIFICATE_POLICIES) || string.equals(RFC3280CertPathUtilities.POLICY_MAPPINGS) || string.equals(RFC3280CertPathUtilities.INHIBIT_ANY_POLICY) || string.equals(RFC3280CertPathUtilities.CRL_DISTRIBUTION_POINTS) || string.equals(RFC3280CertPathUtilities.ISSUING_DISTRIBUTION_POINT) || string.equals(RFC3280CertPathUtilities.DELTA_CRL_INDICATOR) || string.equals(RFC3280CertPathUtilities.POLICY_CONSTRAINTS) || string.equals(RFC3280CertPathUtilities.BASIC_CONSTRAINTS) || string.equals(RFC3280CertPathUtilities.SUBJECT_ALTERNATIVE_NAME) || string.equals(RFC3280CertPathUtilities.NAME_CONSTRAINTS) || !extensions.getExtension(aSN1ObjectIdentifier).isCritical()) continue;
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        synchronized (this) {
            if (!this.hashValueSet) {
                this.hashValue = this.calculateHashCode();
                this.hashValueSet = true;
            }
            int n = this.hashValue;
            return n;
        }
    }

    @Override
    public void setBagAttribute(ASN1ObjectIdentifier aSN1ObjectIdentifier, ASN1Encodable aSN1Encodable) {
        this.attrCarrier.setBagAttribute(aSN1ObjectIdentifier, aSN1Encodable);
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        String string = Strings.lineSeparator();
        stringBuffer.append("  [0]         Version: ");
        stringBuffer.append(this.getVersion());
        stringBuffer.append(string);
        stringBuffer.append("         SerialNumber: ");
        stringBuffer.append(this.getSerialNumber());
        stringBuffer.append(string);
        stringBuffer.append("             IssuerDN: ");
        stringBuffer.append(this.getIssuerDN());
        stringBuffer.append(string);
        stringBuffer.append("           Start Date: ");
        stringBuffer.append(this.getNotBefore());
        stringBuffer.append(string);
        stringBuffer.append("           Final Date: ");
        stringBuffer.append(this.getNotAfter());
        stringBuffer.append(string);
        stringBuffer.append("            SubjectDN: ");
        stringBuffer.append(this.getSubjectDN());
        stringBuffer.append(string);
        stringBuffer.append("           Public Key: ");
        stringBuffer.append(this.getPublicKey());
        stringBuffer.append(string);
        stringBuffer.append("  Signature Algorithm: ");
        stringBuffer.append(this.getSigAlgName());
        stringBuffer.append(string);
        Object object = this.getSignature();
        stringBuffer.append("            Signature: ");
        stringBuffer.append(new String(Hex.encode((byte[])object, 0, 20)));
        stringBuffer.append(string);
        for (int i = 20; i < ((byte[])object).length; i += 20) {
            if (i < ((byte[])object).length - 20) {
                stringBuffer.append("                       ");
                stringBuffer.append(new String(Hex.encode((byte[])object, i, 20)));
                stringBuffer.append(string);
                continue;
            }
            stringBuffer.append("                       ");
            stringBuffer.append(new String(Hex.encode((byte[])object, i, ((Object)object).length - i)));
            stringBuffer.append(string);
        }
        object = this.c.getTBSCertificate().getExtensions();
        if (object != null) {
            Enumeration enumeration = ((Extensions)object).oids();
            if (enumeration.hasMoreElements()) {
                stringBuffer.append("       Extensions: \n");
            }
            while (enumeration.hasMoreElements()) {
                ASN1ObjectIdentifier aSN1ObjectIdentifier = (ASN1ObjectIdentifier)enumeration.nextElement();
                ASN1Object aSN1Object = ((Extensions)object).getExtension(aSN1ObjectIdentifier);
                if (aSN1Object.getExtnValue() != null) {
                    ASN1InputStream aSN1InputStream = new ASN1InputStream(aSN1Object.getExtnValue().getOctets());
                    stringBuffer.append("                       critical(");
                    stringBuffer.append(aSN1Object.isCritical());
                    stringBuffer.append(") ");
                    try {
                        if (aSN1ObjectIdentifier.equals(Extension.basicConstraints)) {
                            stringBuffer.append(BasicConstraints.getInstance(aSN1InputStream.readObject()));
                            stringBuffer.append(string);
                            continue;
                        }
                        if (aSN1ObjectIdentifier.equals(Extension.keyUsage)) {
                            stringBuffer.append(KeyUsage.getInstance(aSN1InputStream.readObject()));
                            stringBuffer.append(string);
                            continue;
                        }
                        if (aSN1ObjectIdentifier.equals(MiscObjectIdentifiers.netscapeCertType)) {
                            aSN1Object = new NetscapeCertType((DERBitString)aSN1InputStream.readObject());
                            stringBuffer.append(aSN1Object);
                            stringBuffer.append(string);
                            continue;
                        }
                        if (aSN1ObjectIdentifier.equals(MiscObjectIdentifiers.netscapeRevocationURL)) {
                            aSN1Object = new NetscapeRevocationURL((DERIA5String)aSN1InputStream.readObject());
                            stringBuffer.append(aSN1Object);
                            stringBuffer.append(string);
                            continue;
                        }
                        if (aSN1ObjectIdentifier.equals(MiscObjectIdentifiers.verisignCzagExtension)) {
                            aSN1Object = new VerisignCzagExtension((DERIA5String)aSN1InputStream.readObject());
                            stringBuffer.append(aSN1Object);
                            stringBuffer.append(string);
                            continue;
                        }
                        stringBuffer.append(aSN1ObjectIdentifier.getId());
                        stringBuffer.append(" value = ");
                        stringBuffer.append(ASN1Dump.dumpAsString(aSN1InputStream.readObject()));
                        stringBuffer.append(string);
                    }
                    catch (Exception exception) {
                        stringBuffer.append(aSN1ObjectIdentifier.getId());
                        stringBuffer.append(" value = ");
                        stringBuffer.append("*****");
                        stringBuffer.append(string);
                    }
                    continue;
                }
                stringBuffer.append(string);
            }
        }
        return stringBuffer.toString();
    }

    @Override
    public final void verify(PublicKey publicKey) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {
        Signature signature;
        String string = X509SignatureUtil.getSignatureName(this.c.getSignatureAlgorithm());
        try {
            signature = Signature.getInstance(string, "BC");
        }
        catch (Exception exception) {
            signature = Signature.getInstance(string);
        }
        this.checkSignature(publicKey, signature);
    }

    @Override
    public final void verify(PublicKey publicKey, String object) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {
        String string = X509SignatureUtil.getSignatureName(this.c.getSignatureAlgorithm());
        object = object != null ? Signature.getInstance(string, (String)object) : Signature.getInstance(string);
        this.checkSignature(publicKey, (Signature)object);
    }

    @Override
    public final void verify(PublicKey publicKey, Provider object) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        String string = X509SignatureUtil.getSignatureName(this.c.getSignatureAlgorithm());
        object = object != null ? Signature.getInstance(string, (Provider)object) : Signature.getInstance(string);
        this.checkSignature(publicKey, (Signature)object);
    }
}

