/*
 * Decompiled with CFR 0.145.
 */
package sun.security.x509;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import javax.security.auth.x500.X500Principal;
import sun.misc.CharacterEncoder;
import sun.misc.HexDumpEncoder;
import sun.security.provider.X509Factory;
import sun.security.util.DerEncoder;
import sun.security.util.DerInputStream;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import sun.security.util.ObjectIdentifier;
import sun.security.x509.AccessDescription;
import sun.security.x509.AlgorithmId;
import sun.security.x509.AttributeNameEnumeration;
import sun.security.x509.AuthorityInfoAccessExtension;
import sun.security.x509.AuthorityKeyIdentifierExtension;
import sun.security.x509.BasicConstraintsExtension;
import sun.security.x509.CRLDistributionPointsExtension;
import sun.security.x509.CertAttrSet;
import sun.security.x509.CertificateExtensions;
import sun.security.x509.CertificatePoliciesExtension;
import sun.security.x509.CertificateValidity;
import sun.security.x509.DNSName;
import sun.security.x509.ExtendedKeyUsageExtension;
import sun.security.x509.Extension;
import sun.security.x509.GeneralName;
import sun.security.x509.GeneralNameInterface;
import sun.security.x509.GeneralNames;
import sun.security.x509.IPAddressName;
import sun.security.x509.IssuerAlternativeNameExtension;
import sun.security.x509.KeyIdentifier;
import sun.security.x509.KeyUsageExtension;
import sun.security.x509.NameConstraintsExtension;
import sun.security.x509.OIDMap;
import sun.security.x509.OIDName;
import sun.security.x509.PKIXExtensions;
import sun.security.x509.PolicyConstraintsExtension;
import sun.security.x509.PolicyMappingsExtension;
import sun.security.x509.PrivateKeyUsageExtension;
import sun.security.x509.RFC822Name;
import sun.security.x509.SerialNumber;
import sun.security.x509.SubjectAlternativeNameExtension;
import sun.security.x509.SubjectKeyIdentifierExtension;
import sun.security.x509.URIName;
import sun.security.x509.UniqueIdentity;
import sun.security.x509.X500Name;
import sun.security.x509.X509AttributeName;
import sun.security.x509.X509CertInfo;

public class X509CertImpl
extends X509Certificate
implements DerEncoder {
    public static final String ALG_ID = "algorithm";
    private static final String AUTH_INFO_ACCESS_OID = "1.3.6.1.5.5.7.1.1";
    private static final String BASIC_CONSTRAINT_OID = "2.5.29.19";
    private static final String DOT = ".";
    private static final String EXTENDED_KEY_USAGE_OID = "2.5.29.37";
    public static final String INFO = "info";
    private static final String ISSUER_ALT_NAME_OID = "2.5.29.18";
    public static final String ISSUER_DN = "x509.info.issuer.dname";
    private static final String KEY_USAGE_OID = "2.5.29.15";
    public static final String NAME = "x509";
    private static final int NUM_STANDARD_KEY_USAGE = 9;
    public static final String PUBLIC_KEY = "x509.info.key.value";
    public static final String SERIAL_ID = "x509.info.serialNumber.number";
    public static final String SIG = "x509.signature";
    public static final String SIGNATURE = "signature";
    public static final String SIGNED_CERT = "signed_cert";
    public static final String SIG_ALG = "x509.algorithm";
    private static final String SUBJECT_ALT_NAME_OID = "2.5.29.17";
    public static final String SUBJECT_DN = "x509.info.subject.dname";
    public static final String VERSION = "x509.info.version.number";
    private static final long serialVersionUID = -3457612960190864406L;
    protected AlgorithmId algId = null;
    private Set<AccessDescription> authInfoAccess;
    private List<String> extKeyUsage;
    private ConcurrentHashMap<String, String> fingerprints = new ConcurrentHashMap(2);
    protected X509CertInfo info = null;
    private Collection<List<?>> issuerAlternativeNames;
    private boolean readOnly = false;
    protected byte[] signature = null;
    private byte[] signedCert = null;
    private Collection<List<?>> subjectAlternativeNames;
    private boolean verificationResult;
    private String verifiedProvider;
    private PublicKey verifiedPublicKey;

    public X509CertImpl() {
    }

    public X509CertImpl(DerValue derValue) throws CertificateException {
        try {
            this.parse(derValue);
            return;
        }
        catch (IOException iOException) {
            this.signedCert = null;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unable to initialize, ");
            stringBuilder.append(iOException);
            throw new CertificateException(stringBuilder.toString(), iOException);
        }
    }

    public X509CertImpl(DerValue derValue, byte[] object) throws CertificateException {
        try {
            this.parse(derValue, (byte[])object);
            return;
        }
        catch (IOException iOException) {
            this.signedCert = null;
            object = new StringBuilder();
            ((StringBuilder)object).append("Unable to initialize, ");
            ((StringBuilder)object).append(iOException);
            throw new CertificateException(((StringBuilder)object).toString(), iOException);
        }
    }

    public X509CertImpl(X509CertInfo x509CertInfo) {
        this.info = x509CertInfo;
    }

    public X509CertImpl(byte[] arrby) throws CertificateException {
        try {
            DerValue derValue = new DerValue(arrby);
            this.parse(derValue);
            return;
        }
        catch (IOException iOException) {
            this.signedCert = null;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unable to initialize, ");
            stringBuilder.append(iOException);
            throw new CertificateException(stringBuilder.toString(), iOException);
        }
    }

    private static void byte2hex(byte by, StringBuffer stringBuffer) {
        char[] arrc;
        char[] arrc2 = arrc = new char[16];
        arrc2[0] = 48;
        arrc2[1] = 49;
        arrc2[2] = 50;
        arrc2[3] = 51;
        arrc2[4] = 52;
        arrc2[5] = 53;
        arrc2[6] = 54;
        arrc2[7] = 55;
        arrc2[8] = 56;
        arrc2[9] = 57;
        arrc2[10] = 65;
        arrc2[11] = 66;
        arrc2[12] = 67;
        arrc2[13] = 68;
        arrc2[14] = 69;
        arrc2[15] = 70;
        stringBuffer.append(arrc[(by & 240) >> 4]);
        stringBuffer.append(arrc[by & 15]);
    }

    private static Collection<List<?>> cloneAltNames(Collection<List<?>> collection) {
        boolean bl = false;
        Object object = collection.iterator();
        while (object.hasNext()) {
            if (!(object.next().get(1) instanceof byte[])) continue;
            bl = true;
        }
        if (bl) {
            object = new ArrayList();
            for (List<?> list : collection) {
                collection = list.get(1);
                if (collection instanceof byte[]) {
                    list = new ArrayList(list);
                    list.set(1, ((byte[])collection).clone());
                    object.add(Collections.unmodifiableList(list));
                    continue;
                }
                object.add(list);
            }
            return Collections.unmodifiableCollection(object);
        }
        return collection;
    }

    public static byte[] getEncodedInternal(Certificate certificate) throws CertificateEncodingException {
        if (certificate instanceof X509CertImpl) {
            return ((X509CertImpl)certificate).getEncodedInternal();
        }
        return certificate.getEncoded();
    }

    public static List<String> getExtendedKeyUsage(X509Certificate object) throws CertificateParsingException {
        block3 : {
            try {
                object = object.getExtensionValue(EXTENDED_KEY_USAGE_OID);
                if (object != null) break block3;
                return null;
            }
            catch (IOException iOException) {
                throw new CertificateParsingException(iOException);
            }
        }
        Object object2 = new DerValue((byte[])object);
        object = ((DerValue)object2).getOctetString();
        object2 = new ExtendedKeyUsageExtension(Boolean.FALSE, object);
        object = Collections.unmodifiableList(((ExtendedKeyUsageExtension)object2).getExtendedKeyUsage());
        return object;
    }

    public static String getFingerprint(String object, X509Certificate object2) {
        String string = "";
        object2 = ((Certificate)object2).getEncoded();
        object = MessageDigest.getInstance((String)object).digest((byte[])object2);
        object2 = new StringBuffer();
        int n = 0;
        do {
            if (n >= ((byte[])object).length) break;
            X509CertImpl.byte2hex(object[n], (StringBuffer)object2);
            ++n;
            continue;
            break;
        } while (true);
        try {
            object = ((StringBuffer)object2).toString();
        }
        catch (NoSuchAlgorithmException | CertificateEncodingException generalSecurityException) {
            object = string;
        }
        return object;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static Collection<List<?>> getIssuerAlternativeNames(X509Certificate object) throws CertificateParsingException {
        try {
            byte[] arrby = object.getExtensionValue(ISSUER_ALT_NAME_OID);
            if (arrby == null) {
                return null;
            }
            object = new DerValue(arrby);
            arrby = ((DerValue)object).getOctetString();
            object = new IssuerAlternativeNameExtension(Boolean.FALSE, arrby);
        }
        catch (IOException iOException) {
            throw new CertificateParsingException(iOException);
        }
        object = ((IssuerAlternativeNameExtension)object).get("issuer_name");
        {
            catch (IOException iOException) {
                return Collections.emptySet();
            }
        }
        return X509CertImpl.makeAltNames((GeneralNames)object);
    }

    public static X500Principal getIssuerX500Principal(X509Certificate serializable) {
        try {
            serializable = X509CertImpl.getX500Principal(serializable, true);
            return serializable;
        }
        catch (Exception exception) {
            throw new RuntimeException("Could not parse issuer", exception);
        }
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static Collection<List<?>> getSubjectAlternativeNames(X509Certificate object) throws CertificateParsingException {
        try {
            object = object.getExtensionValue(SUBJECT_ALT_NAME_OID);
            if (object == null) {
                return null;
            }
            byte[] arrby = new DerValue((byte[])object);
            arrby = arrby.getOctetString();
            object = new SubjectAlternativeNameExtension(Boolean.FALSE, arrby);
        }
        catch (IOException iOException) {
            throw new CertificateParsingException(iOException);
        }
        object = ((SubjectAlternativeNameExtension)object).get("subject_name");
        {
            catch (IOException iOException) {
                return Collections.emptySet();
            }
        }
        return X509CertImpl.makeAltNames((GeneralNames)object);
    }

    public static X500Principal getSubjectX500Principal(X509Certificate serializable) {
        try {
            serializable = X509CertImpl.getX500Principal(serializable, false);
            return serializable;
        }
        catch (Exception exception) {
            throw new RuntimeException("Could not parse subject", exception);
        }
    }

    private static X500Principal getX500Principal(X509Certificate object, boolean bl) throws Exception {
        DerInputStream derInputStream = new DerInputStream((byte[])object.getEncoded()).getSequence((int)3)[0].data;
        if (derInputStream.getDerValue().isContextSpecific((byte)0)) {
            derInputStream.getDerValue();
        }
        derInputStream.getDerValue();
        object = derInputStream.getDerValue();
        if (!bl) {
            derInputStream.getDerValue();
            object = derInputStream.getDerValue();
        }
        return new X500Principal(((DerValue)object).toByteArray());
    }

    public static boolean isSelfIssued(X509Certificate x509Certificate) {
        return x509Certificate.getSubjectX500Principal().equals(x509Certificate.getIssuerX500Principal());
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static boolean isSelfSigned(X509Certificate var0, String var1_2) {
        if (X509CertImpl.isSelfIssued(var0) == false) return false;
        if (var1_2 != null) ** GOTO lbl6
        try {
            var0.verify(var0.getPublicKey());
            return true;
lbl6: // 1 sources:
            var0.verify(var0.getPublicKey(), var1_2);
            return true;
        }
        catch (Exception var0_1) {
            // empty catch block
        }
        return false;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static Collection<List<?>> makeAltNames(GeneralNames object) {
        if (((GeneralNames)((Object)object)).isEmpty()) {
            return Collections.emptySet();
        }
        ArrayList<List<Object>> arrayList = new ArrayList<List<Object>>();
        Iterator<GeneralName> iterator = ((GeneralNames)((Object)object)).names().iterator();
        while (iterator.hasNext()) {
            block12 : {
                GeneralNameInterface generalNameInterface;
                block6 : {
                    block7 : {
                        block8 : {
                            block9 : {
                                block10 : {
                                    block11 : {
                                        generalNameInterface = iterator.next().getName();
                                        object = new ArrayList(2);
                                        object.add(generalNameInterface.getType());
                                        int n = generalNameInterface.getType();
                                        if (n == 1) break block6;
                                        if (n == 2) break block7;
                                        if (n == 4) break block8;
                                        if (n == 6) break block9;
                                        if (n == 7) break block10;
                                        if (n == 8) break block11;
                                        DerOutputStream derOutputStream = new DerOutputStream();
                                        try {
                                            generalNameInterface.encode(derOutputStream);
                                            object.add(derOutputStream.toByteArray());
                                        }
                                        catch (IOException iOException) {
                                            throw new RuntimeException("name cannot be encoded", iOException);
                                        }
                                        break block12;
                                    }
                                    object.add(((OIDName)generalNameInterface).getOID().toString());
                                    break block12;
                                }
                                object.add(((IPAddressName)generalNameInterface).getName());
                                catch (IOException iOException) {
                                    throw new RuntimeException("IPAddress cannot be parsed", iOException);
                                }
                            }
                            object.add(((URIName)generalNameInterface).getName());
                            break block12;
                        }
                        object.add(((X500Name)generalNameInterface).getRFC2253Name());
                        break block12;
                    }
                    object.add(((DNSName)generalNameInterface).getName());
                    break block12;
                }
                object.add(((RFC822Name)generalNameInterface).getName());
            }
            arrayList.add(Collections.unmodifiableList(object));
        }
        return Collections.unmodifiableCollection(arrayList);
    }

    private void parse(DerValue derValue) throws CertificateException, IOException {
        this.parse(derValue, null);
    }

    private void parse(DerValue object, byte[] object2) throws CertificateException, IOException {
        if (!this.readOnly) {
            if (((DerValue)object).data != null && ((DerValue)object).tag == 48) {
                if (object2 == null) {
                    object2 = ((DerValue)object).toByteArray();
                }
                this.signedCert = object2;
                object2 = new DerValue[]{(byte)((DerValue)object).data.getDerValue(), (byte)((DerValue)object).data.getDerValue(), (byte)((DerValue)object).data.getDerValue()};
                if (((DerValue)object).data.available() == 0) {
                    if (object2[0].tag == 48) {
                        this.algId = AlgorithmId.parse((DerValue)object2[1]);
                        this.signature = ((DerValue)object2[2]).getBitString();
                        if (((DerValue)object2[1]).data.available() == 0) {
                            if (((DerValue)object2[2]).data.available() == 0) {
                                this.info = new X509CertInfo((DerValue)object2[0]);
                                object = (AlgorithmId)this.info.get("algorithmID.algorithm");
                                if (this.algId.equals((AlgorithmId)object)) {
                                    this.readOnly = true;
                                    return;
                                }
                                throw new CertificateException("Signature algorithm mismatch");
                            }
                            throw new CertificateParsingException("signed fields overrun");
                        }
                        throw new CertificateParsingException("algid field overrun");
                    }
                    throw new CertificateParsingException("signed fields invalid");
                }
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("signed overrun, bytes = ");
                ((StringBuilder)object2).append(((DerValue)object).data.available());
                throw new CertificateParsingException(((StringBuilder)object2).toString());
            }
            throw new CertificateParsingException("invalid DER-encoded certificate data");
        }
        throw new CertificateParsingException("cannot over-write existing certificate");
    }

    public static X509CertImpl toImpl(X509Certificate x509Certificate) throws CertificateException {
        if (x509Certificate instanceof X509CertImpl) {
            return (X509CertImpl)x509Certificate;
        }
        return X509Factory.intern(x509Certificate);
    }

    public static void verify(X509Certificate x509Certificate, PublicKey publicKey, Provider provider) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        x509Certificate.verify(publicKey, provider);
    }

    @Override
    public void checkValidity() throws CertificateExpiredException, CertificateNotYetValidException {
        this.checkValidity(new Date());
    }

    @Override
    public void checkValidity(Date date) throws CertificateExpiredException, CertificateNotYetValidException {
        block2 : {
            CertificateValidity certificateValidity;
            try {
                certificateValidity = (CertificateValidity)this.info.get("validity");
                if (certificateValidity == null) break block2;
            }
            catch (Exception exception) {
                throw new CertificateNotYetValidException("Incorrect validity period");
            }
            certificateValidity.valid(date);
            return;
        }
        throw new CertificateNotYetValidException("Null validity period");
    }

    public void delete(String string) throws CertificateException, IOException {
        block5 : {
            Object object;
            block6 : {
                block11 : {
                    block8 : {
                        block10 : {
                            block9 : {
                                block7 : {
                                    if (this.readOnly) break block5;
                                    object = new X509AttributeName(string);
                                    string = ((X509AttributeName)object).getPrefix();
                                    if (!string.equalsIgnoreCase(NAME)) break block6;
                                    string = ((X509AttributeName)(object = new X509AttributeName(((X509AttributeName)object).getSuffix()))).getPrefix();
                                    if (!string.equalsIgnoreCase(INFO)) break block7;
                                    if (((X509AttributeName)object).getSuffix() != null) {
                                        this.info = null;
                                    } else {
                                        this.info.delete(((X509AttributeName)object).getSuffix());
                                    }
                                    break block8;
                                }
                                if (!string.equalsIgnoreCase(ALG_ID)) break block9;
                                this.algId = null;
                                break block8;
                            }
                            if (!string.equalsIgnoreCase(SIGNATURE)) break block10;
                            this.signature = null;
                            break block8;
                        }
                        if (!string.equalsIgnoreCase(SIGNED_CERT)) break block11;
                        this.signedCert = null;
                    }
                    return;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("Attribute name not recognized or delete() not allowed for the same: ");
                ((StringBuilder)object).append(string);
                throw new CertificateException(((StringBuilder)object).toString());
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Invalid root of attribute name, expected [x509], received ");
            ((StringBuilder)object).append(string);
            throw new CertificateException(((StringBuilder)object).toString());
        }
        throw new CertificateException("cannot over-write existing certificate");
    }

    @Override
    public void derEncode(OutputStream outputStream) throws IOException {
        byte[] arrby = this.signedCert;
        if (arrby != null) {
            outputStream.write((byte[])arrby.clone());
            return;
        }
        throw new IOException("Null certificate to encode");
    }

    public void encode(OutputStream outputStream) throws CertificateEncodingException {
        byte[] arrby = this.signedCert;
        if (arrby != null) {
            try {
                outputStream.write((byte[])arrby.clone());
                return;
            }
            catch (IOException iOException) {
                throw new CertificateEncodingException(iOException.toString());
            }
        }
        throw new CertificateEncodingException("Null certificate to encode");
    }

    public Object get(String object) throws CertificateParsingException {
        Object object2 = new X509AttributeName((String)object);
        object = ((X509AttributeName)object2).getPrefix();
        if (object.equalsIgnoreCase(NAME)) {
            object = ((X509AttributeName)(object2 = new X509AttributeName(((X509AttributeName)object2).getSuffix()))).getPrefix();
            if (object.equalsIgnoreCase(INFO)) {
                if (this.info == null) {
                    return null;
                }
                if (((X509AttributeName)object2).getSuffix() != null) {
                    try {
                        object = this.info.get(((X509AttributeName)object2).getSuffix());
                        return object;
                    }
                    catch (CertificateException certificateException) {
                        throw new CertificateParsingException(certificateException.toString());
                    }
                    catch (IOException iOException) {
                        throw new CertificateParsingException(iOException.toString());
                    }
                }
                return this.info;
            }
            if (object.equalsIgnoreCase(ALG_ID)) {
                return this.algId;
            }
            if (object.equalsIgnoreCase(SIGNATURE)) {
                object = this.signature;
                if (object != null) {
                    return object.clone();
                }
                return null;
            }
            if (object.equalsIgnoreCase(SIGNED_CERT)) {
                object = this.signedCert;
                if (object != null) {
                    return object.clone();
                }
                return null;
            }
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Attribute name not recognized or get() not allowed for the same: ");
            ((StringBuilder)object2).append((String)object);
            throw new CertificateParsingException(((StringBuilder)object2).toString());
        }
        object2 = new StringBuilder();
        ((StringBuilder)object2).append("Invalid root of attribute name, expected [x509], received [");
        ((StringBuilder)object2).append((String)object);
        ((StringBuilder)object2).append("]");
        throw new CertificateParsingException(((StringBuilder)object2).toString());
    }

    public KeyIdentifier getAuthKeyId() {
        Object object = this.getAuthorityKeyIdentifierExtension();
        if (object != null) {
            try {
                object = (KeyIdentifier)((AuthorityKeyIdentifierExtension)object).get("key_id");
                return object;
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
        return null;
    }

    public AuthorityInfoAccessExtension getAuthorityInfoAccessExtension() {
        return (AuthorityInfoAccessExtension)this.getExtension(PKIXExtensions.AuthInfoAccess_Id);
    }

    public AuthorityKeyIdentifierExtension getAuthorityKeyIdentifierExtension() {
        return (AuthorityKeyIdentifierExtension)this.getExtension(PKIXExtensions.AuthorityKey_Id);
    }

    @Override
    public int getBasicConstraints() {
        Object object;
        block6 : {
            block5 : {
                try {
                    object = OIDMap.getName(PKIXExtensions.BasicConstraints_Id);
                    if (object != null) break block5;
                    return -1;
                }
                catch (Exception exception) {
                    return -1;
                }
            }
            object = (BasicConstraintsExtension)this.get((String)object);
            if (object != null) break block6;
            return -1;
        }
        if (((Boolean)((BasicConstraintsExtension)object).get("is_ca")).booleanValue()) {
            int n = (Integer)((BasicConstraintsExtension)object).get("path_len");
            return n;
        }
        return -1;
    }

    public BasicConstraintsExtension getBasicConstraintsExtension() {
        return (BasicConstraintsExtension)this.getExtension(PKIXExtensions.BasicConstraints_Id);
    }

    public CRLDistributionPointsExtension getCRLDistributionPointsExtension() {
        return (CRLDistributionPointsExtension)this.getExtension(PKIXExtensions.CRLDistributionPoints_Id);
    }

    public CertificatePoliciesExtension getCertificatePoliciesExtension() {
        return (CertificatePoliciesExtension)this.getExtension(PKIXExtensions.CertificatePolicies_Id);
    }

    @Override
    public Set<String> getCriticalExtensionOIDs() {
        CertificateExtensions certificateExtensions;
        Object object;
        block5 : {
            object = this.info;
            if (object == null) {
                return null;
            }
            certificateExtensions = (CertificateExtensions)((X509CertInfo)object).get("extensions");
            if (certificateExtensions != null) break block5;
            return null;
        }
        try {
            object = new Object();
            for (Extension extension : certificateExtensions.getAllExtensions()) {
                if (!extension.isCritical()) continue;
                object.add((String)extension.getExtensionId().toString());
            }
            return object;
        }
        catch (Exception exception) {
            return null;
        }
    }

    public Enumeration<String> getElements() {
        AttributeNameEnumeration attributeNameEnumeration = new AttributeNameEnumeration();
        attributeNameEnumeration.addElement("x509.info");
        attributeNameEnumeration.addElement(SIG_ALG);
        attributeNameEnumeration.addElement(SIG);
        attributeNameEnumeration.addElement("x509.signed_cert");
        return attributeNameEnumeration.elements();
    }

    @Override
    public byte[] getEncoded() throws CertificateEncodingException {
        return (byte[])this.getEncodedInternal().clone();
    }

    public byte[] getEncodedInternal() throws CertificateEncodingException {
        byte[] arrby = this.signedCert;
        if (arrby != null) {
            return arrby;
        }
        throw new CertificateEncodingException("Null certificate to encode");
    }

    @Override
    public List<String> getExtendedKeyUsage() throws CertificateParsingException {
        synchronized (this) {
            Object object;
            block6 : {
                block5 : {
                    if (!this.readOnly || this.extKeyUsage == null) break block5;
                    List<String> list = this.extKeyUsage;
                    return list;
                }
                object = this.getExtendedKeyUsageExtension();
                if (object != null) break block6;
                return null;
            }
            this.extKeyUsage = Collections.unmodifiableList(((ExtendedKeyUsageExtension)object).getExtendedKeyUsage());
            object = this.extKeyUsage;
            return object;
        }
    }

    public ExtendedKeyUsageExtension getExtendedKeyUsageExtension() {
        return (ExtendedKeyUsageExtension)this.getExtension(PKIXExtensions.ExtendedKeyUsage_Id);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Extension getExtension(ObjectIdentifier objectIdentifier) {
        Object object = this.info;
        if (object == null) {
            return null;
        }
        try {
            Extension extension;
            boolean bl;
            try {
                object = (CertificateExtensions)((X509CertInfo)object).get("extensions");
                if (object == null) {
                    return null;
                }
                extension = ((CertificateExtensions)object).getExtension(objectIdentifier.toString());
                if (extension != null) {
                    return extension;
                }
                object = ((CertificateExtensions)object).getAllExtensions().iterator();
            }
            catch (CertificateException certificateException) {
                return null;
            }
            do {
                if (object.hasNext()) continue;
                return null;
            } while (!(bl = (extension = (Extension)object.next()).getExtensionId().equals((Object)objectIdentifier)));
            return extension;
        }
        catch (IOException iOException) {
            return null;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public byte[] getExtensionValue(String arrby) {
        try {
            ObjectIdentifier objectIdentifier = new ObjectIdentifier((String)arrby);
            Object object = OIDMap.getName(objectIdentifier);
            Object object2 = null;
            Extension extension = null;
            CertificateExtensions certificateExtensions = (CertificateExtensions)this.info.get("extensions");
            if (object == null) {
                boolean bl;
                if (certificateExtensions == null) {
                    return null;
                }
                object = certificateExtensions.getAllExtensions().iterator();
                do {
                    object2 = extension;
                } while (object.hasNext() && !(bl = ((Extension)(object2 = (Extension)object.next())).getExtensionId().equals((Object)objectIdentifier)));
            } else {
                try {
                    extension = (Extension)this.get((String)object);
                    object2 = extension;
                }
                catch (CertificateException certificateException) {
                    // empty catch block
                }
            }
            extension = object2;
            if (object2 == null) {
                if (certificateExtensions != null) {
                    object2 = certificateExtensions.getUnparseableExtensions().get(arrby);
                }
                extension = object2;
                if (object2 == null) {
                    return null;
                }
            }
            if ((arrby = extension.getExtensionValue()) == null) {
                return null;
            }
            object2 = new DerOutputStream();
            ((DerOutputStream)object2).putOctetString(arrby);
            return ((ByteArrayOutputStream)object2).toByteArray();
        }
        catch (Exception exception) {
            return null;
        }
    }

    public IssuerAlternativeNameExtension getIssuerAlternativeNameExtension() {
        return (IssuerAlternativeNameExtension)this.getExtension(PKIXExtensions.IssuerAlternativeName_Id);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public Collection<List<?>> getIssuerAlternativeNames() throws CertificateParsingException {
        synchronized (this) {
            if (this.readOnly && this.issuerAlternativeNames != null) {
                return X509CertImpl.cloneAltNames(this.issuerAlternativeNames);
            }
            Object object = this.getIssuerAlternativeNameExtension();
            if (object == null) {
                return null;
            }
            try {
                object = ((IssuerAlternativeNameExtension)object).get("issuer_name");
            }
            catch (IOException iOException) {
                return Collections.emptySet();
            }
            this.issuerAlternativeNames = X509CertImpl.makeAltNames((GeneralNames)object);
            return this.issuerAlternativeNames;
        }
    }

    @Override
    public Principal getIssuerDN() {
        Object object = this.info;
        if (object == null) {
            return null;
        }
        try {
            object = (Principal)((X509CertInfo)object).get("issuer.dname");
            return object;
        }
        catch (Exception exception) {
            return null;
        }
    }

    @Override
    public boolean[] getIssuerUniqueID() {
        Object object;
        block4 : {
            object = this.info;
            if (object == null) {
                return null;
            }
            try {
                object = (UniqueIdentity)object.get("issuerID");
                if (object != null) break block4;
                return null;
            }
            catch (Exception exception) {
                return null;
            }
        }
        object = object.getId();
        return object;
    }

    @Override
    public X500Principal getIssuerX500Principal() {
        Object object = this.info;
        if (object == null) {
            return null;
        }
        try {
            object = (X500Principal)((X509CertInfo)object).get("issuer.x500principal");
            return object;
        }
        catch (Exception exception) {
            return null;
        }
    }

    @Override
    public boolean[] getKeyUsage() {
        boolean[] arrbl;
        boolean[] arrbl2;
        block7 : {
            block6 : {
                try {
                    arrbl2 = OIDMap.getName(PKIXExtensions.KeyUsage_Id);
                    if (arrbl2 != null) break block6;
                    return null;
                }
                catch (Exception exception) {
                    return null;
                }
            }
            arrbl2 = (KeyUsageExtension)this.get((String)arrbl2);
            if (arrbl2 != null) break block7;
            return null;
        }
        arrbl2 = arrbl = arrbl2.getBits();
        if (arrbl.length < 9) {
            arrbl2 = new boolean[9];
            System.arraycopy((Object)arrbl, 0, (Object)arrbl2, 0, arrbl.length);
        }
        return arrbl2;
    }

    public String getName() {
        return NAME;
    }

    public NameConstraintsExtension getNameConstraintsExtension() {
        return (NameConstraintsExtension)this.getExtension(PKIXExtensions.NameConstraints_Id);
    }

    @Override
    public Set<String> getNonCriticalExtensionOIDs() {
        CertificateExtensions certificateExtensions;
        block5 : {
            X509CertInfo object = this.info;
            if (object == null) {
                return null;
            }
            certificateExtensions = (CertificateExtensions)object.get("extensions");
            if (certificateExtensions != null) break block5;
            return null;
        }
        try {
            TreeSet<String> treeSet = new TreeSet<String>();
            for (Extension extension : certificateExtensions.getAllExtensions()) {
                if (extension.isCritical()) continue;
                treeSet.add(extension.getExtensionId().toString());
            }
            treeSet.addAll(certificateExtensions.getUnparseableExtensions().keySet());
            return treeSet;
        }
        catch (Exception exception) {
            return null;
        }
    }

    @Override
    public Date getNotAfter() {
        Object object = this.info;
        if (object == null) {
            return null;
        }
        try {
            object = (Date)((X509CertInfo)object).get("validity.notAfter");
            return object;
        }
        catch (Exception exception) {
            return null;
        }
    }

    @Override
    public Date getNotBefore() {
        Object object = this.info;
        if (object == null) {
            return null;
        }
        try {
            object = (Date)((X509CertInfo)object).get("validity.notBefore");
            return object;
        }
        catch (Exception exception) {
            return null;
        }
    }

    public PolicyConstraintsExtension getPolicyConstraintsExtension() {
        return (PolicyConstraintsExtension)this.getExtension(PKIXExtensions.PolicyConstraints_Id);
    }

    public PolicyMappingsExtension getPolicyMappingsExtension() {
        return (PolicyMappingsExtension)this.getExtension(PKIXExtensions.PolicyMappings_Id);
    }

    public PrivateKeyUsageExtension getPrivateKeyUsageExtension() {
        return (PrivateKeyUsageExtension)this.getExtension(PKIXExtensions.PrivateKeyUsage_Id);
    }

    @Override
    public PublicKey getPublicKey() {
        Object object = this.info;
        if (object == null) {
            return null;
        }
        try {
            object = (PublicKey)((X509CertInfo)object).get("key.value");
            return object;
        }
        catch (Exception exception) {
            return null;
        }
    }

    @Override
    public BigInteger getSerialNumber() {
        Object object = this.getSerialNumberObject();
        object = object != null ? ((SerialNumber)object).getNumber() : null;
        return object;
    }

    public SerialNumber getSerialNumberObject() {
        Object object = this.info;
        if (object == null) {
            return null;
        }
        try {
            object = (SerialNumber)((X509CertInfo)object).get("serialNumber.number");
            return object;
        }
        catch (Exception exception) {
            return null;
        }
    }

    @Override
    public String getSigAlgName() {
        AlgorithmId algorithmId = this.algId;
        if (algorithmId == null) {
            return null;
        }
        return algorithmId.getName();
    }

    @Override
    public String getSigAlgOID() {
        AlgorithmId algorithmId = this.algId;
        if (algorithmId == null) {
            return null;
        }
        return algorithmId.getOID().toString();
    }

    @Override
    public byte[] getSigAlgParams() {
        byte[] arrby = this.algId;
        if (arrby == null) {
            return null;
        }
        try {
            arrby = arrby.getEncodedParams();
            return arrby;
        }
        catch (IOException iOException) {
            return null;
        }
    }

    @Override
    public byte[] getSignature() {
        byte[] arrby = this.signature;
        if (arrby == null) {
            return null;
        }
        return (byte[])arrby.clone();
    }

    public SubjectAlternativeNameExtension getSubjectAlternativeNameExtension() {
        return (SubjectAlternativeNameExtension)this.getExtension(PKIXExtensions.SubjectAlternativeName_Id);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public Collection<List<?>> getSubjectAlternativeNames() throws CertificateParsingException {
        synchronized (this) {
            if (this.readOnly && this.subjectAlternativeNames != null) {
                return X509CertImpl.cloneAltNames(this.subjectAlternativeNames);
            }
            Object object = this.getSubjectAlternativeNameExtension();
            if (object == null) {
                return null;
            }
            try {
                object = ((SubjectAlternativeNameExtension)object).get("subject_name");
            }
            catch (IOException iOException) {
                return Collections.emptySet();
            }
            this.subjectAlternativeNames = X509CertImpl.makeAltNames((GeneralNames)object);
            return this.subjectAlternativeNames;
        }
    }

    @Override
    public Principal getSubjectDN() {
        Object object = this.info;
        if (object == null) {
            return null;
        }
        try {
            object = (Principal)((X509CertInfo)object).get("subject.dname");
            return object;
        }
        catch (Exception exception) {
            return null;
        }
    }

    public KeyIdentifier getSubjectKeyId() {
        Object object = this.getSubjectKeyIdentifierExtension();
        if (object != null) {
            try {
                object = ((SubjectKeyIdentifierExtension)object).get("key_id");
                return object;
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
        return null;
    }

    public SubjectKeyIdentifierExtension getSubjectKeyIdentifierExtension() {
        return (SubjectKeyIdentifierExtension)this.getExtension(PKIXExtensions.SubjectKey_Id);
    }

    @Override
    public boolean[] getSubjectUniqueID() {
        Object object;
        block4 : {
            object = this.info;
            if (object == null) {
                return null;
            }
            try {
                object = (UniqueIdentity)object.get("subjectID");
                if (object != null) break block4;
                return null;
            }
            catch (Exception exception) {
                return null;
            }
        }
        object = object.getId();
        return object;
    }

    @Override
    public X500Principal getSubjectX500Principal() {
        Object object = this.info;
        if (object == null) {
            return null;
        }
        try {
            object = (X500Principal)((X509CertInfo)object).get("subject.x500principal");
            return object;
        }
        catch (Exception exception) {
            return null;
        }
    }

    @Override
    public byte[] getTBSCertificate() throws CertificateEncodingException {
        X509CertInfo x509CertInfo = this.info;
        if (x509CertInfo != null) {
            return x509CertInfo.getEncodedInfo();
        }
        throw new CertificateEncodingException("Uninitialized certificate");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Extension getUnparseableExtension(ObjectIdentifier object) {
        CertAttrSet<String> certAttrSet = this.info;
        if (certAttrSet == null) {
            return null;
        }
        try {
            try {
                certAttrSet = (CertificateExtensions)((X509CertInfo)certAttrSet).get("extensions");
                if (certAttrSet != null) return ((CertificateExtensions)certAttrSet).getUnparseableExtensions().get(((ObjectIdentifier)object).toString());
                return null;
            }
            catch (CertificateException certificateException) {
                return null;
            }
        }
        catch (IOException iOException) {
            return null;
        }
    }

    @Override
    public int getVersion() {
        int n;
        X509CertInfo x509CertInfo = this.info;
        if (x509CertInfo == null) {
            return -1;
        }
        try {
            n = (Integer)x509CertInfo.get("version.number");
        }
        catch (Exception exception) {
            return -1;
        }
        return n + 1;
    }

    @Override
    public boolean hasUnsupportedCriticalExtension() {
        CertAttrSet<String> certAttrSet;
        block4 : {
            certAttrSet = this.info;
            if (certAttrSet == null) {
                return false;
            }
            try {
                certAttrSet = (CertificateExtensions)((X509CertInfo)certAttrSet).get("extensions");
                if (certAttrSet != null) break block4;
                return false;
            }
            catch (Exception exception) {
                return false;
            }
        }
        boolean bl = ((CertificateExtensions)certAttrSet).hasUnsupportedCriticalExtension();
        return bl;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void set(String string, Object object) throws CertificateException, IOException {
        if (this.readOnly) throw new CertificateException("cannot over-write existing certificate");
        X509AttributeName x509AttributeName = new X509AttributeName(string);
        string = x509AttributeName.getPrefix();
        if (string.equalsIgnoreCase(NAME)) {
            string = (x509AttributeName = new X509AttributeName(x509AttributeName.getSuffix())).getPrefix();
            if (string.equalsIgnoreCase(INFO)) {
                if (x509AttributeName.getSuffix() == null) {
                    if (!(object instanceof X509CertInfo)) throw new CertificateException("Attribute value should be of type X509CertInfo.");
                    this.info = (X509CertInfo)object;
                    this.signedCert = null;
                    return;
                } else {
                    this.info.set(x509AttributeName.getSuffix(), object);
                    this.signedCert = null;
                }
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Attribute name not recognized or set() not allowed for the same: ");
            ((StringBuilder)object).append(string);
            throw new CertificateException(((StringBuilder)object).toString());
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Invalid root of attribute name, expected [x509], received ");
        ((StringBuilder)object).append(string);
        throw new CertificateException(((StringBuilder)object).toString());
    }

    public void sign(PrivateKey privateKey, String string) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {
        this.sign(privateKey, string, null);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void sign(PrivateKey object, String object2, String object3) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {
        try {
            if (this.readOnly) {
                object = new CertificateEncodingException("cannot over-write existing certificate");
                throw object;
            }
            object2 = object3 != null && ((String)object3).length() != 0 ? Signature.getInstance((String)object2, (String)object3) : Signature.getInstance((String)object2);
            ((Signature)object2).initSign((PrivateKey)object);
            this.algId = AlgorithmId.get(((Signature)object2).getAlgorithm());
            DerOutputStream derOutputStream = new DerOutputStream();
            object3 = new DerOutputStream();
            this.info.encode((OutputStream)object3);
            object = ((ByteArrayOutputStream)object3).toByteArray();
            this.algId.encode((DerOutputStream)object3);
            ((Signature)object2).update((byte[])object, 0, ((byte[])object).length);
            this.signature = ((Signature)object2).sign();
            ((DerOutputStream)object3).putBitString(this.signature);
            derOutputStream.write((byte)48, (DerOutputStream)object3);
            this.signedCert = derOutputStream.toByteArray();
            this.readOnly = true;
            return;
        }
        catch (IOException iOException) {
            throw new CertificateEncodingException(iOException.toString());
        }
    }

    @Override
    public String toString() {
        if (this.info != null && this.algId != null && this.signature != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("[\n");
            Object object = new StringBuilder();
            ((StringBuilder)object).append(this.info.toString());
            ((StringBuilder)object).append("\n");
            stringBuilder.append(((StringBuilder)object).toString());
            object = new StringBuilder();
            ((StringBuilder)object).append("  Algorithm: [");
            ((StringBuilder)object).append(this.algId.toString());
            ((StringBuilder)object).append("]\n");
            stringBuilder.append(((StringBuilder)object).toString());
            object = new HexDumpEncoder();
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("  Signature:\n");
            stringBuilder2.append(((CharacterEncoder)object).encodeBuffer(this.signature));
            stringBuilder.append(stringBuilder2.toString());
            stringBuilder.append("\n]");
            return stringBuilder.toString();
        }
        return "";
    }

    @Override
    public void verify(PublicKey publicKey) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {
        this.verify(publicKey, "");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void verify(PublicKey serializable, String object) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {
        synchronized (this) {
            Signature signature;
            String string = signature;
            if (signature == null) {
                string = "";
            }
            if (this.verifiedPublicKey != null && this.verifiedPublicKey.equals(serializable) && string.equals(this.verifiedProvider)) {
                boolean bl = this.verificationResult;
                if (bl) {
                    return;
                }
                SignatureException signatureException = new SignatureException("Signature does not match.");
                throw signatureException;
            }
            if (this.signedCert == null) {
                CertificateEncodingException certificateEncodingException = new CertificateEncodingException("Uninitialized certificate");
                throw certificateEncodingException;
            }
            signature = string.length() == 0 ? Signature.getInstance(this.algId.getName()) : Signature.getInstance(this.algId.getName(), string);
            signature.initVerify((PublicKey)serializable);
            byte[] arrby = this.info.getEncodedInfo();
            signature.update(arrby, 0, arrby.length);
            this.verificationResult = signature.verify(this.signature);
            this.verifiedPublicKey = serializable;
            this.verifiedProvider = string;
            boolean bl = this.verificationResult;
            if (bl) {
                return;
            }
            SignatureException signatureException = new SignatureException("Signature does not match.");
            throw signatureException;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void verify(PublicKey serializable, Provider object) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        synchronized (this) {
            Signature signature;
            if (this.signedCert == null) {
                CertificateEncodingException certificateEncodingException = new CertificateEncodingException("Uninitialized certificate");
                throw certificateEncodingException;
            }
            signature = signature == null ? Signature.getInstance(this.algId.getName()) : Signature.getInstance(this.algId.getName(), (Provider)((Object)signature));
            signature.initVerify((PublicKey)serializable);
            byte[] arrby = this.info.getEncodedInfo();
            signature.update(arrby, 0, arrby.length);
            this.verificationResult = signature.verify(this.signature);
            this.verifiedPublicKey = serializable;
            boolean bl = this.verificationResult;
            if (bl) {
                return;
            }
            SignatureException signatureException = new SignatureException("Signature does not match.");
            throw signatureException;
        }
    }
}

