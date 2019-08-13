/*
 * Decompiled with CFR 0.145.
 */
package sun.security.x509;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateParsingException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.security.auth.x500.X500Principal;
import sun.misc.CharacterEncoder;
import sun.misc.HexDumpEncoder;
import sun.security.util.DerInputStream;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import sun.security.util.ObjectIdentifier;
import sun.security.x509.AttributeNameEnumeration;
import sun.security.x509.CertAttrSet;
import sun.security.x509.CertificateAlgorithmId;
import sun.security.x509.CertificateExtensions;
import sun.security.x509.CertificateSerialNumber;
import sun.security.x509.CertificateValidity;
import sun.security.x509.CertificateVersion;
import sun.security.x509.CertificateX509Key;
import sun.security.x509.Extension;
import sun.security.x509.GeneralNames;
import sun.security.x509.OIDMap;
import sun.security.x509.SubjectAlternativeNameExtension;
import sun.security.x509.UniqueIdentity;
import sun.security.x509.X500Name;
import sun.security.x509.X509AttributeName;

public class X509CertInfo
implements CertAttrSet<String> {
    public static final String ALGORITHM_ID = "algorithmID";
    private static final int ATTR_ALGORITHM = 3;
    private static final int ATTR_EXTENSIONS = 10;
    private static final int ATTR_ISSUER = 4;
    private static final int ATTR_ISSUER_ID = 8;
    private static final int ATTR_KEY = 7;
    private static final int ATTR_SERIAL = 2;
    private static final int ATTR_SUBJECT = 6;
    private static final int ATTR_SUBJECT_ID = 9;
    private static final int ATTR_VALIDITY = 5;
    private static final int ATTR_VERSION = 1;
    public static final String DN_NAME = "dname";
    public static final String EXTENSIONS = "extensions";
    public static final String IDENT = "x509.info";
    public static final String ISSUER = "issuer";
    public static final String ISSUER_ID = "issuerID";
    public static final String KEY = "key";
    public static final String NAME = "info";
    public static final String SERIAL_NUMBER = "serialNumber";
    public static final String SUBJECT = "subject";
    public static final String SUBJECT_ID = "subjectID";
    public static final String VALIDITY = "validity";
    public static final String VERSION = "version";
    private static final Map<String, Integer> map = new HashMap<String, Integer>();
    protected CertificateAlgorithmId algId = null;
    protected CertificateExtensions extensions = null;
    protected CertificateValidity interval = null;
    protected X500Name issuer = null;
    protected UniqueIdentity issuerUniqueId = null;
    protected CertificateX509Key pubKey = null;
    private byte[] rawCertInfo = null;
    protected CertificateSerialNumber serialNum = null;
    protected X500Name subject = null;
    protected UniqueIdentity subjectUniqueId = null;
    protected CertificateVersion version = new CertificateVersion();

    static {
        map.put(VERSION, 1);
        map.put(SERIAL_NUMBER, 2);
        map.put(ALGORITHM_ID, 3);
        map.put(ISSUER, 4);
        map.put(VALIDITY, 5);
        map.put(SUBJECT, 6);
        map.put(KEY, 7);
        map.put(ISSUER_ID, 8);
        map.put(SUBJECT_ID, 9);
        map.put(EXTENSIONS, 10);
    }

    public X509CertInfo() {
    }

    public X509CertInfo(DerValue derValue) throws CertificateParsingException {
        try {
            this.parse(derValue);
            return;
        }
        catch (IOException iOException) {
            throw new CertificateParsingException(iOException);
        }
    }

    public X509CertInfo(byte[] arrby) throws CertificateParsingException {
        try {
            DerValue derValue = new DerValue(arrby);
            this.parse(derValue);
            return;
        }
        catch (IOException iOException) {
            throw new CertificateParsingException(iOException);
        }
    }

    private int attributeMap(String object) {
        if ((object = map.get(object)) == null) {
            return 0;
        }
        return (Integer)object;
    }

    private void emit(DerOutputStream derOutputStream) throws CertificateException, IOException {
        DerOutputStream derOutputStream2 = new DerOutputStream();
        this.version.encode(derOutputStream2);
        this.serialNum.encode(derOutputStream2);
        this.algId.encode(derOutputStream2);
        if (this.version.compare(0) == 0 && this.issuer.toString() == null) {
            throw new CertificateParsingException("Null issuer DN not allowed in v1 certificate");
        }
        this.issuer.encode(derOutputStream2);
        this.interval.encode(derOutputStream2);
        if (this.version.compare(0) == 0 && this.subject.toString() == null) {
            throw new CertificateParsingException("Null subject DN not allowed in v1 certificate");
        }
        this.subject.encode(derOutputStream2);
        this.pubKey.encode(derOutputStream2);
        Object object = this.issuerUniqueId;
        if (object != null) {
            ((UniqueIdentity)object).encode(derOutputStream2, DerValue.createTag((byte)-128, false, (byte)1));
        }
        if ((object = this.subjectUniqueId) != null) {
            ((UniqueIdentity)object).encode(derOutputStream2, DerValue.createTag((byte)-128, false, (byte)2));
        }
        if ((object = this.extensions) != null) {
            ((CertificateExtensions)object).encode(derOutputStream2);
        }
        derOutputStream.write((byte)48, derOutputStream2);
    }

    private Object getX500Name(String object, boolean bl) throws IOException {
        if (((String)object).equalsIgnoreCase(DN_NAME)) {
            object = bl ? this.issuer : this.subject;
            return object;
        }
        if (((String)object).equalsIgnoreCase("x500principal")) {
            object = bl ? this.issuer.asX500Principal() : this.subject.asX500Principal();
            return object;
        }
        throw new IOException("Attribute name not recognized.");
    }

    private void parse(DerValue derValue) throws CertificateParsingException, IOException {
        if (derValue.tag == 48) {
            DerValue derValue2;
            this.rawCertInfo = derValue.toByteArray();
            DerInputStream derInputStream = derValue.data;
            derValue = derValue2 = derInputStream.getDerValue();
            if (derValue2.isContextSpecific((byte)0)) {
                this.version = new CertificateVersion(derValue2);
                derValue = derInputStream.getDerValue();
            }
            this.serialNum = new CertificateSerialNumber(derValue);
            this.algId = new CertificateAlgorithmId(derInputStream);
            this.issuer = new X500Name(derInputStream);
            if (!this.issuer.isEmpty()) {
                this.interval = new CertificateValidity(derInputStream);
                this.subject = new X500Name(derInputStream);
                if (this.version.compare(0) == 0 && this.subject.isEmpty()) {
                    throw new CertificateParsingException("Empty subject DN not allowed in v1 certificate");
                }
                this.pubKey = new CertificateX509Key(derInputStream);
                if (derInputStream.available() != 0) {
                    if (this.version.compare(0) != 0) {
                        derValue = derValue2 = derInputStream.getDerValue();
                        if (derValue2.isContextSpecific((byte)1)) {
                            this.issuerUniqueId = new UniqueIdentity(derValue2);
                            if (derInputStream.available() == 0) {
                                return;
                            }
                            derValue = derInputStream.getDerValue();
                        }
                        derValue2 = derValue;
                        if (derValue.isContextSpecific((byte)2)) {
                            this.subjectUniqueId = new UniqueIdentity(derValue);
                            if (derInputStream.available() == 0) {
                                return;
                            }
                            derValue2 = derInputStream.getDerValue();
                        }
                        if (this.version.compare(2) == 0) {
                            if (derValue2.isConstructed() && derValue2.isContextSpecific((byte)3)) {
                                this.extensions = new CertificateExtensions(derValue2.data);
                            }
                            this.verifyCert(this.subject, this.extensions);
                            return;
                        }
                        throw new CertificateParsingException("Extensions not allowed in v2 certificate");
                    }
                    throw new CertificateParsingException("no more data allowed for version 1 certificate");
                }
                return;
            }
            throw new CertificateParsingException("Empty issuer DN not allowed in X509Certificates");
        }
        throw new CertificateParsingException("signed fields invalid");
    }

    private void setAlgorithmId(Object object) throws CertificateException {
        if (object instanceof CertificateAlgorithmId) {
            this.algId = (CertificateAlgorithmId)object;
            return;
        }
        throw new CertificateException("AlgorithmId class type invalid.");
    }

    private void setExtensions(Object object) throws CertificateException {
        if (this.version.compare(2) >= 0) {
            if (object instanceof CertificateExtensions) {
                this.extensions = (CertificateExtensions)object;
                return;
            }
            throw new CertificateException("Extensions class type invalid.");
        }
        throw new CertificateException("Invalid version");
    }

    private void setIssuer(Object object) throws CertificateException {
        if (object instanceof X500Name) {
            this.issuer = (X500Name)object;
            return;
        }
        throw new CertificateException("Issuer class type invalid.");
    }

    private void setIssuerUniqueId(Object object) throws CertificateException {
        if (this.version.compare(1) >= 0) {
            if (object instanceof UniqueIdentity) {
                this.issuerUniqueId = (UniqueIdentity)object;
                return;
            }
            throw new CertificateException("IssuerUniqueId class type invalid.");
        }
        throw new CertificateException("Invalid version");
    }

    private void setKey(Object object) throws CertificateException {
        if (object instanceof CertificateX509Key) {
            this.pubKey = (CertificateX509Key)object;
            return;
        }
        throw new CertificateException("Key class type invalid.");
    }

    private void setSerialNumber(Object object) throws CertificateException {
        if (object instanceof CertificateSerialNumber) {
            this.serialNum = (CertificateSerialNumber)object;
            return;
        }
        throw new CertificateException("SerialNumber class type invalid.");
    }

    private void setSubject(Object object) throws CertificateException {
        if (object instanceof X500Name) {
            this.subject = (X500Name)object;
            return;
        }
        throw new CertificateException("Subject class type invalid.");
    }

    private void setSubjectUniqueId(Object object) throws CertificateException {
        if (this.version.compare(1) >= 0) {
            if (object instanceof UniqueIdentity) {
                this.subjectUniqueId = (UniqueIdentity)object;
                return;
            }
            throw new CertificateException("SubjectUniqueId class type invalid.");
        }
        throw new CertificateException("Invalid version");
    }

    private void setValidity(Object object) throws CertificateException {
        if (object instanceof CertificateValidity) {
            this.interval = (CertificateValidity)object;
            return;
        }
        throw new CertificateException("CertificateValidity class type invalid.");
    }

    private void setVersion(Object object) throws CertificateException {
        if (object instanceof CertificateVersion) {
            this.version = (CertificateVersion)object;
            return;
        }
        throw new CertificateException("Version class type invalid.");
    }

    private void verifyCert(X500Name object, CertificateExtensions object2) throws CertificateParsingException, IOException {
        block4 : {
            block5 : {
                block3 : {
                    if (!((X500Name)object).isEmpty()) break block4;
                    if (object2 == null) break block5;
                    try {
                        object = (SubjectAlternativeNameExtension)((CertificateExtensions)object2).get("SubjectAlternativeName");
                        object2 = ((SubjectAlternativeNameExtension)object).get("subject_name");
                        if (object2 == null || ((GeneralNames)object2).isEmpty()) break block3;
                    }
                    catch (IOException iOException) {
                        throw new CertificateParsingException("X.509 Certificate is incomplete: subject field is empty, and SubjectAlternativeName extension is absent");
                    }
                    if (!((Extension)object).isCritical()) {
                        throw new CertificateParsingException("X.509 Certificate is incomplete: SubjectAlternativeName extension MUST be marked critical when subject field is empty");
                    }
                    break block4;
                }
                throw new CertificateParsingException("X.509 Certificate is incomplete: subject field is empty, and SubjectAlternativeName extension is empty");
            }
            throw new CertificateParsingException("X.509 Certificate is incomplete: subject field is empty, and certificate has no extensions");
        }
    }

    @Override
    public void delete(String object) throws CertificateException, IOException {
        Object object2 = new X509AttributeName((String)object);
        int n = this.attributeMap(((X509AttributeName)object2).getPrefix());
        if (n != 0) {
            this.rawCertInfo = null;
            object2 = ((X509AttributeName)object2).getSuffix();
            switch (n) {
                default: {
                    break;
                }
                case 10: {
                    if (object2 == null) {
                        this.extensions = null;
                        break;
                    }
                    object = this.extensions;
                    if (object == null) break;
                    ((CertificateExtensions)object).delete((String)object2);
                    break;
                }
                case 9: {
                    this.subjectUniqueId = null;
                    break;
                }
                case 8: {
                    this.issuerUniqueId = null;
                    break;
                }
                case 7: {
                    if (object2 == null) {
                        this.pubKey = null;
                        break;
                    }
                    this.pubKey.delete((String)object2);
                    break;
                }
                case 6: {
                    this.subject = null;
                    break;
                }
                case 5: {
                    if (object2 == null) {
                        this.interval = null;
                        break;
                    }
                    this.interval.delete((String)object2);
                    break;
                }
                case 4: {
                    this.issuer = null;
                    break;
                }
                case 3: {
                    if (object2 == null) {
                        this.algId = null;
                        break;
                    }
                    this.algId.delete((String)object2);
                    break;
                }
                case 2: {
                    if (object2 == null) {
                        this.serialNum = null;
                        break;
                    }
                    this.serialNum.delete((String)object2);
                    break;
                }
                case 1: {
                    if (object2 == null) {
                        this.version = null;
                        break;
                    }
                    this.version.delete((String)object2);
                }
            }
            return;
        }
        object2 = new StringBuilder();
        ((StringBuilder)object2).append("Attribute name not recognized: ");
        ((StringBuilder)object2).append((String)object);
        throw new CertificateException(((StringBuilder)object2).toString());
    }

    @Override
    public void encode(OutputStream outputStream) throws CertificateException, IOException {
        if (this.rawCertInfo == null) {
            DerOutputStream derOutputStream = new DerOutputStream();
            this.emit(derOutputStream);
            this.rawCertInfo = derOutputStream.toByteArray();
        }
        outputStream.write((byte[])this.rawCertInfo.clone());
    }

    public boolean equals(Object object) {
        if (object instanceof X509CertInfo) {
            return this.equals((X509CertInfo)object);
        }
        return false;
    }

    public boolean equals(X509CertInfo x509CertInfo) {
        byte[] arrby;
        if (this == x509CertInfo) {
            return true;
        }
        byte[] arrby2 = this.rawCertInfo;
        if (arrby2 != null && (arrby = x509CertInfo.rawCertInfo) != null) {
            if (arrby2.length != arrby.length) {
                return false;
            }
            for (int i = 0; i < (arrby = this.rawCertInfo).length; ++i) {
                if (arrby[i] == x509CertInfo.rawCertInfo[i]) continue;
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public Object get(String string) throws CertificateException, IOException {
        Object object = new X509AttributeName(string);
        int n = this.attributeMap(((X509AttributeName)object).getPrefix());
        if (n != 0) {
            string = ((X509AttributeName)object).getSuffix();
            switch (n) {
                default: {
                    return null;
                }
                case 10: {
                    if (string == null) {
                        return this.extensions;
                    }
                    object = this.extensions;
                    if (object == null) {
                        return null;
                    }
                    return ((CertificateExtensions)object).get(string);
                }
                case 9: {
                    return this.subjectUniqueId;
                }
                case 8: {
                    return this.issuerUniqueId;
                }
                case 7: {
                    if (string == null) {
                        return this.pubKey;
                    }
                    return this.pubKey.get(string);
                }
                case 6: {
                    if (string == null) {
                        return this.subject;
                    }
                    return this.getX500Name(string, false);
                }
                case 5: {
                    if (string == null) {
                        return this.interval;
                    }
                    return this.interval.get(string);
                }
                case 4: {
                    if (string == null) {
                        return this.issuer;
                    }
                    return this.getX500Name(string, true);
                }
                case 3: {
                    if (string == null) {
                        return this.algId;
                    }
                    return this.algId.get(string);
                }
                case 2: {
                    if (string == null) {
                        return this.serialNum;
                    }
                    return this.serialNum.get(string);
                }
                case 1: 
            }
            if (string == null) {
                return this.version;
            }
            return this.version.get(string);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Attribute name not recognized: ");
        ((StringBuilder)object).append(string);
        throw new CertificateParsingException(((StringBuilder)object).toString());
    }

    @Override
    public Enumeration<String> getElements() {
        AttributeNameEnumeration attributeNameEnumeration = new AttributeNameEnumeration();
        attributeNameEnumeration.addElement(VERSION);
        attributeNameEnumeration.addElement(SERIAL_NUMBER);
        attributeNameEnumeration.addElement(ALGORITHM_ID);
        attributeNameEnumeration.addElement(ISSUER);
        attributeNameEnumeration.addElement(VALIDITY);
        attributeNameEnumeration.addElement(SUBJECT);
        attributeNameEnumeration.addElement(KEY);
        attributeNameEnumeration.addElement(ISSUER_ID);
        attributeNameEnumeration.addElement(SUBJECT_ID);
        attributeNameEnumeration.addElement(EXTENSIONS);
        return attributeNameEnumeration.elements();
    }

    public byte[] getEncodedInfo() throws CertificateEncodingException {
        try {
            byte[] arrby;
            if (this.rawCertInfo == null) {
                arrby = new DerOutputStream();
                this.emit((DerOutputStream)arrby);
                this.rawCertInfo = arrby.toByteArray();
            }
            arrby = (byte[])this.rawCertInfo.clone();
            return arrby;
        }
        catch (CertificateException certificateException) {
            throw new CertificateEncodingException(certificateException.toString());
        }
        catch (IOException iOException) {
            throw new CertificateEncodingException(iOException.toString());
        }
    }

    @Override
    public String getName() {
        return NAME;
    }

    public int hashCode() {
        byte[] arrby;
        int n = 0;
        for (int i = 1; i < (arrby = this.rawCertInfo).length; ++i) {
            n += arrby[i] * i;
        }
        return n;
    }

    @Override
    public void set(String string, Object object) throws CertificateException, IOException {
        X509AttributeName x509AttributeName = new X509AttributeName(string);
        int n = this.attributeMap(x509AttributeName.getPrefix());
        if (n != 0) {
            this.rawCertInfo = null;
            string = x509AttributeName.getSuffix();
            switch (n) {
                default: {
                    break;
                }
                case 10: {
                    if (string == null) {
                        this.setExtensions(object);
                        break;
                    }
                    if (this.extensions == null) {
                        this.extensions = new CertificateExtensions();
                    }
                    this.extensions.set(string, object);
                    break;
                }
                case 9: {
                    this.setSubjectUniqueId(object);
                    break;
                }
                case 8: {
                    this.setIssuerUniqueId(object);
                    break;
                }
                case 7: {
                    if (string == null) {
                        this.setKey(object);
                        break;
                    }
                    this.pubKey.set(string, object);
                    break;
                }
                case 6: {
                    this.setSubject(object);
                    break;
                }
                case 5: {
                    if (string == null) {
                        this.setValidity(object);
                        break;
                    }
                    this.interval.set(string, object);
                    break;
                }
                case 4: {
                    this.setIssuer(object);
                    break;
                }
                case 3: {
                    if (string == null) {
                        this.setAlgorithmId(object);
                        break;
                    }
                    this.algId.set(string, object);
                    break;
                }
                case 2: {
                    if (string == null) {
                        this.setSerialNumber(object);
                        break;
                    }
                    this.serialNum.set(string, object);
                    break;
                }
                case 1: {
                    if (string == null) {
                        this.setVersion(object);
                        break;
                    }
                    this.version.set(string, object);
                }
            }
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Attribute name not recognized: ");
        ((StringBuilder)object).append(string);
        throw new CertificateException(((StringBuilder)object).toString());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder;
        block9 : {
            Object object;
            Object object2;
            int n;
            block10 : {
                block8 : {
                    if (this.subject == null || this.pubKey == null || this.interval == null || this.issuer == null || this.algId == null || this.serialNum == null) break block8;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("[\n");
                    object = new StringBuilder();
                    ((StringBuilder)object).append("  ");
                    ((StringBuilder)object).append(this.version.toString());
                    ((StringBuilder)object).append("\n");
                    stringBuilder.append(((StringBuilder)object).toString());
                    object = new StringBuilder();
                    ((StringBuilder)object).append("  Subject: ");
                    ((StringBuilder)object).append(this.subject.toString());
                    ((StringBuilder)object).append("\n");
                    stringBuilder.append(((StringBuilder)object).toString());
                    object = new StringBuilder();
                    ((StringBuilder)object).append("  Signature Algorithm: ");
                    ((StringBuilder)object).append(this.algId.toString());
                    ((StringBuilder)object).append("\n");
                    stringBuilder.append(((StringBuilder)object).toString());
                    object = new StringBuilder();
                    ((StringBuilder)object).append("  Key:  ");
                    ((StringBuilder)object).append(this.pubKey.toString());
                    ((StringBuilder)object).append("\n");
                    stringBuilder.append(((StringBuilder)object).toString());
                    object = new StringBuilder();
                    ((StringBuilder)object).append("  ");
                    ((StringBuilder)object).append(this.interval.toString());
                    ((StringBuilder)object).append("\n");
                    stringBuilder.append(((StringBuilder)object).toString());
                    object = new StringBuilder();
                    ((StringBuilder)object).append("  Issuer: ");
                    ((StringBuilder)object).append(this.issuer.toString());
                    ((StringBuilder)object).append("\n");
                    stringBuilder.append(((StringBuilder)object).toString());
                    object = new StringBuilder();
                    ((StringBuilder)object).append("  ");
                    ((StringBuilder)object).append(this.serialNum.toString());
                    ((StringBuilder)object).append("\n");
                    stringBuilder.append(((StringBuilder)object).toString());
                    if (this.issuerUniqueId != null) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("  Issuer Id:\n");
                        ((StringBuilder)object).append(this.issuerUniqueId.toString());
                        ((StringBuilder)object).append("\n");
                        stringBuilder.append(((StringBuilder)object).toString());
                    }
                    if (this.subjectUniqueId != null) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("  Subject Id:\n");
                        ((StringBuilder)object).append(this.subjectUniqueId.toString());
                        ((StringBuilder)object).append("\n");
                        stringBuilder.append(((StringBuilder)object).toString());
                    }
                    if ((object = this.extensions) == null) break block9;
                    object = ((CertificateExtensions)object).getAllExtensions().toArray(new Extension[0]);
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("\nCertificate Extensions: ");
                    ((StringBuilder)object2).append(((Extension[])object).length);
                    stringBuilder.append(((StringBuilder)object2).toString());
                    break block10;
                }
                throw new NullPointerException("X.509 cert is incomplete");
            }
            for (n = 0; n < ((Extension[])object).length; ++n) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("\n[");
                ((StringBuilder)object2).append(n + 1);
                ((StringBuilder)object2).append("]: ");
                stringBuilder.append(((StringBuilder)object2).toString());
                object2 = object[n];
                try {
                    if (OIDMap.getClass(((Extension)object2).getExtensionId()) == null) {
                        stringBuilder.append(((Extension)object2).toString());
                        object2 = ((Extension)object2).getExtensionValue();
                        if (object2 == null) continue;
                        Object object3 = new DerOutputStream();
                        ((DerOutputStream)object3).putOctetString((byte[])object2);
                        object2 = ((ByteArrayOutputStream)object3).toByteArray();
                        object3 = new HexDumpEncoder();
                        StringBuilder stringBuilder2 = new StringBuilder();
                        stringBuilder2.append("Extension unknown: DER encoded OCTET string =\n");
                        stringBuilder2.append(((CharacterEncoder)object3).encodeBuffer((byte[])object2));
                        stringBuilder2.append("\n");
                        stringBuilder.append(stringBuilder2.toString());
                        continue;
                    }
                    stringBuilder.append(((Extension)object2).toString());
                    continue;
                }
                catch (Exception exception) {
                    stringBuilder.append(", Error parsing this extension");
                }
            }
            object2 = this.extensions.getUnparseableExtensions();
            if (!object2.isEmpty()) {
                object = new StringBuilder();
                ((StringBuilder)object).append("\nUnparseable certificate extensions: ");
                ((StringBuilder)object).append(object2.size());
                stringBuilder.append(((StringBuilder)object).toString());
                n = 1;
                for (Object object3 : object2.values()) {
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("\n[");
                    ((StringBuilder)object2).append(n);
                    ((StringBuilder)object2).append("]: ");
                    stringBuilder.append(((StringBuilder)object2).toString());
                    stringBuilder.append(object3);
                    ++n;
                }
            }
        }
        stringBuilder.append("\n]");
        return stringBuilder.toString();
    }
}

