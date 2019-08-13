/*
 * Decompiled with CFR 0.145.
 */
package sun.security.x509;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.CRLException;
import java.security.cert.Certificate;
import java.security.cert.X509CRL;
import java.security.cert.X509CRLEntry;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import javax.security.auth.x500.X500Principal;
import sun.misc.CharacterEncoder;
import sun.misc.HexDumpEncoder;
import sun.security.provider.X509Factory;
import sun.security.util.DerEncoder;
import sun.security.util.DerInputStream;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import sun.security.util.ObjectIdentifier;
import sun.security.x509.AlgorithmId;
import sun.security.x509.AuthorityKeyIdentifierExtension;
import sun.security.x509.CRLExtensions;
import sun.security.x509.CRLNumberExtension;
import sun.security.x509.CertificateIssuerExtension;
import sun.security.x509.DeltaCRLIndicatorExtension;
import sun.security.x509.Extension;
import sun.security.x509.GeneralName;
import sun.security.x509.GeneralNameInterface;
import sun.security.x509.GeneralNames;
import sun.security.x509.IssuerAlternativeNameExtension;
import sun.security.x509.IssuingDistributionPointExtension;
import sun.security.x509.KeyIdentifier;
import sun.security.x509.OIDMap;
import sun.security.x509.PKIXExtensions;
import sun.security.x509.X500Name;
import sun.security.x509.X509CRLEntryImpl;

public class X509CRLImpl
extends X509CRL
implements DerEncoder {
    private static final long YR_2050 = 2524636800000L;
    private static final boolean isExplicit = true;
    private CRLExtensions extensions = null;
    private AlgorithmId infoSigAlgId;
    private X500Name issuer = null;
    private X500Principal issuerPrincipal = null;
    private Date nextUpdate = null;
    private boolean readOnly = false;
    private List<X509CRLEntry> revokedList = new LinkedList<X509CRLEntry>();
    private Map<X509IssuerSerial, X509CRLEntry> revokedMap = new TreeMap<X509IssuerSerial, X509CRLEntry>();
    private AlgorithmId sigAlgId = null;
    private byte[] signature = null;
    private byte[] signedCRL = null;
    private byte[] tbsCertList = null;
    private Date thisUpdate = null;
    private String verifiedProvider;
    private PublicKey verifiedPublicKey;
    private int version;

    private X509CRLImpl() {
    }

    public X509CRLImpl(InputStream inputStream) throws CRLException {
        try {
            DerValue derValue = new DerValue(inputStream);
            this.parse(derValue);
            return;
        }
        catch (IOException iOException) {
            this.signedCRL = null;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Parsing error: ");
            stringBuilder.append(iOException.getMessage());
            throw new CRLException(stringBuilder.toString());
        }
    }

    public X509CRLImpl(DerValue derValue) throws CRLException {
        try {
            this.parse(derValue);
            return;
        }
        catch (IOException iOException) {
            this.signedCRL = null;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Parsing error: ");
            stringBuilder.append(iOException.getMessage());
            throw new CRLException(stringBuilder.toString());
        }
    }

    public X509CRLImpl(X500Name x500Name, Date date, Date date2) {
        this.issuer = x500Name;
        this.thisUpdate = date;
        this.nextUpdate = date2;
    }

    public X509CRLImpl(X500Name object, Date serializable, Date comparable, X509CRLEntry[] arrx509CRLEntry) throws CRLException {
        this.issuer = object;
        this.thisUpdate = serializable;
        this.nextUpdate = comparable;
        if (arrx509CRLEntry != null) {
            serializable = this.getIssuerX500Principal();
            object = serializable;
            for (int i = 0; i < arrx509CRLEntry.length; ++i) {
                comparable = (X509CRLEntryImpl)arrx509CRLEntry[i];
                try {
                    object = this.getCertIssuer((X509CRLEntryImpl)comparable, (X500Principal)object);
                    ((X509CRLEntryImpl)comparable).setCertificateIssuer((X500Principal)serializable, (X500Principal)object);
                    X509IssuerSerial x509IssuerSerial = new X509IssuerSerial((X500Principal)object, ((X509CRLEntryImpl)comparable).getSerialNumber());
                    this.revokedMap.put(x509IssuerSerial, (X509CRLEntry)((Object)comparable));
                }
                catch (IOException iOException) {
                    throw new CRLException(iOException);
                }
                this.revokedList.add((X509CRLEntry)((Object)comparable));
                if (!((X509CRLEntryImpl)comparable).hasExtensions()) continue;
                this.version = 1;
            }
        }
    }

    public X509CRLImpl(X500Name x500Name, Date date, Date date2, X509CRLEntry[] arrx509CRLEntry, CRLExtensions cRLExtensions) throws CRLException {
        this(x500Name, date, date2, arrx509CRLEntry);
        if (cRLExtensions != null) {
            this.extensions = cRLExtensions;
            this.version = 1;
        }
    }

    public X509CRLImpl(byte[] arrby) throws CRLException {
        try {
            DerValue derValue = new DerValue(arrby);
            this.parse(derValue);
            return;
        }
        catch (IOException iOException) {
            this.signedCRL = null;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Parsing error: ");
            stringBuilder.append(iOException.getMessage());
            throw new CRLException(stringBuilder.toString());
        }
    }

    private X500Principal getCertIssuer(X509CRLEntryImpl object, X500Principal x500Principal) throws IOException {
        if ((object = ((X509CRLEntryImpl)object).getCertificateIssuerExtension()) != null) {
            return ((X500Name)((GeneralNames)((CertificateIssuerExtension)object).get("issuer")).get(0).getName()).asX500Principal();
        }
        return x500Principal;
    }

    public static byte[] getEncodedInternal(X509CRL x509CRL) throws CRLException {
        if (x509CRL instanceof X509CRLImpl) {
            return ((X509CRLImpl)x509CRL).getEncodedInternal();
        }
        return x509CRL.getEncoded();
    }

    public static X500Principal getIssuerX500Principal(X509CRL object) {
        try {
            object = ((X509CRL)object).getEncoded();
            DerInputStream derInputStream = new DerInputStream((byte[])object);
            object = derInputStream.getSequence((int)3)[0].data;
            if ((byte)((DerInputStream)object).peekByte() == 2) {
                ((DerInputStream)object).getDerValue();
            }
            ((DerInputStream)object).getDerValue();
            object = new X500Principal(((DerInputStream)object).getDerValue().toByteArray());
            return object;
        }
        catch (Exception exception) {
            throw new RuntimeException("Could not parse issuer", exception);
        }
    }

    private void parse(DerValue object) throws CRLException, IOException {
        block14 : {
            block15 : {
                Object object2;
                block16 : {
                    block17 : {
                        block18 : {
                            block19 : {
                                block20 : {
                                    block21 : {
                                        int n;
                                        block24 : {
                                            DerInputStream derInputStream;
                                            block23 : {
                                                block22 : {
                                                    if (this.readOnly) break block14;
                                                    if (((DerValue)object).getData() == null || ((DerValue)object).tag != 48) break block15;
                                                    this.signedCRL = ((DerValue)object).toByteArray();
                                                    object2 = new DerValue[]{((DerValue)object).data.getDerValue(), ((DerValue)object).data.getDerValue(), ((DerValue)object).data.getDerValue()};
                                                    if (((DerValue)object).data.available() != 0) break block16;
                                                    if (object2[0].tag != 48) break block17;
                                                    this.sigAlgId = AlgorithmId.parse((DerValue)object2[1]);
                                                    this.signature = ((DerValue)object2[2]).getBitString();
                                                    if (((DerValue)object2[1]).data.available() != 0) break block18;
                                                    if (((DerValue)object2[2]).data.available() != 0) break block19;
                                                    this.tbsCertList = ((DerValue)object2[0]).toByteArray();
                                                    derInputStream = ((DerValue)object2[0]).data;
                                                    this.version = 0;
                                                    if ((byte)derInputStream.peekByte() == 2) {
                                                        this.version = derInputStream.getInteger();
                                                        if (this.version != 1) {
                                                            throw new CRLException("Invalid version");
                                                        }
                                                    }
                                                    if (!((AlgorithmId)(object = AlgorithmId.parse(derInputStream.getDerValue()))).equals(this.sigAlgId)) break block20;
                                                    this.infoSigAlgId = object;
                                                    this.issuer = new X500Name(derInputStream);
                                                    if (this.issuer.isEmpty()) break block21;
                                                    n = derInputStream.peekByte();
                                                    if (n != 23) break block22;
                                                    this.thisUpdate = derInputStream.getUTCTime();
                                                    break block23;
                                                }
                                                if (n != 24) break block24;
                                                this.thisUpdate = derInputStream.getGeneralizedTime();
                                            }
                                            if (derInputStream.available() == 0) {
                                                return;
                                            }
                                            n = (byte)derInputStream.peekByte();
                                            if (n == 23) {
                                                this.nextUpdate = derInputStream.getUTCTime();
                                            } else if (n == 24) {
                                                this.nextUpdate = derInputStream.getGeneralizedTime();
                                            }
                                            if (derInputStream.available() == 0) {
                                                return;
                                            }
                                            n = (byte)derInputStream.peekByte();
                                            if (n == 48 && (n & 192) != 128) {
                                                DerValue[] arrderValue = derInputStream.getSequence(4);
                                                object = object2 = this.getIssuerX500Principal();
                                                for (n = 0; n < arrderValue.length; ++n) {
                                                    X509CRLEntryImpl x509CRLEntryImpl = new X509CRLEntryImpl(arrderValue[n]);
                                                    object = this.getCertIssuer(x509CRLEntryImpl, (X500Principal)object);
                                                    x509CRLEntryImpl.setCertificateIssuer((X500Principal)object2, (X500Principal)object);
                                                    X509IssuerSerial x509IssuerSerial = new X509IssuerSerial((X500Principal)object, x509CRLEntryImpl.getSerialNumber());
                                                    this.revokedMap.put(x509IssuerSerial, x509CRLEntryImpl);
                                                    this.revokedList.add(x509CRLEntryImpl);
                                                }
                                            }
                                            if (derInputStream.available() == 0) {
                                                return;
                                            }
                                            object = derInputStream.getDerValue();
                                            if (((DerValue)object).isConstructed() && ((DerValue)object).isContextSpecific((byte)0)) {
                                                this.extensions = new CRLExtensions(((DerValue)object).data);
                                            }
                                            this.readOnly = true;
                                            return;
                                        }
                                        object = new StringBuilder();
                                        ((StringBuilder)object).append("Invalid encoding for thisUpdate (tag=");
                                        ((StringBuilder)object).append(n);
                                        ((StringBuilder)object).append(")");
                                        throw new CRLException(((StringBuilder)object).toString());
                                    }
                                    throw new CRLException("Empty issuer DN not allowed in X509CRLs");
                                }
                                throw new CRLException("Signature algorithm mismatch");
                            }
                            throw new CRLException("Signature field overrun");
                        }
                        throw new CRLException("AlgorithmId field overrun");
                    }
                    throw new CRLException("signed CRL fields invalid");
                }
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("signed overrun, bytes = ");
                ((StringBuilder)object2).append(((DerValue)object).data.available());
                throw new CRLException(((StringBuilder)object2).toString());
            }
            throw new CRLException("Invalid DER-encoded CRL data");
        }
        throw new CRLException("cannot over-write existing CRL");
    }

    public static X509CRLImpl toImpl(X509CRL x509CRL) throws CRLException {
        if (x509CRL instanceof X509CRLImpl) {
            return (X509CRLImpl)x509CRL;
        }
        return X509Factory.intern(x509CRL);
    }

    @Override
    public void derEncode(OutputStream outputStream) throws IOException {
        byte[] arrby = this.signedCRL;
        if (arrby != null) {
            outputStream.write((byte[])arrby.clone());
            return;
        }
        throw new IOException("Null CRL to encode");
    }

    public void encodeInfo(OutputStream object) throws CRLException {
        try {
            DerOutputStream derOutputStream = new DerOutputStream();
            DerOutputStream derOutputStream2 = new DerOutputStream();
            DerOutputStream derOutputStream3 = new DerOutputStream();
            if (this.version != 0) {
                derOutputStream.putInteger(this.version);
            }
            this.infoSigAlgId.encode(derOutputStream);
            if (this.version == 0 && this.issuer.toString() == null) {
                object = new CRLException("Null Issuer DN not allowed in v1 CRL");
                throw object;
            }
            this.issuer.encode(derOutputStream);
            if (this.thisUpdate.getTime() < 2524636800000L) {
                derOutputStream.putUTCTime(this.thisUpdate);
            } else {
                derOutputStream.putGeneralizedTime(this.thisUpdate);
            }
            if (this.nextUpdate != null) {
                if (this.nextUpdate.getTime() < 2524636800000L) {
                    derOutputStream.putUTCTime(this.nextUpdate);
                } else {
                    derOutputStream.putGeneralizedTime(this.nextUpdate);
                }
            }
            if (!this.revokedList.isEmpty()) {
                Iterator<X509CRLEntry> iterator = this.revokedList.iterator();
                while (iterator.hasNext()) {
                    ((X509CRLEntryImpl)iterator.next()).encode(derOutputStream2);
                }
                derOutputStream.write((byte)48, derOutputStream2);
            }
            if (this.extensions != null) {
                this.extensions.encode(derOutputStream, true);
            }
            derOutputStream3.write((byte)48, derOutputStream);
            this.tbsCertList = derOutputStream3.toByteArray();
            ((OutputStream)object).write(this.tbsCertList);
            return;
        }
        catch (IOException iOException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Encoding error: ");
            ((StringBuilder)object).append(iOException.getMessage());
            throw new CRLException(((StringBuilder)object).toString());
        }
    }

    public KeyIdentifier getAuthKeyId() throws IOException {
        AuthorityKeyIdentifierExtension authorityKeyIdentifierExtension = this.getAuthKeyIdExtension();
        if (authorityKeyIdentifierExtension != null) {
            return (KeyIdentifier)authorityKeyIdentifierExtension.get("key_id");
        }
        return null;
    }

    public AuthorityKeyIdentifierExtension getAuthKeyIdExtension() throws IOException {
        return (AuthorityKeyIdentifierExtension)this.getExtension(PKIXExtensions.AuthorityKey_Id);
    }

    public BigInteger getBaseCRLNumber() throws IOException {
        DeltaCRLIndicatorExtension deltaCRLIndicatorExtension = this.getDeltaCRLIndicatorExtension();
        if (deltaCRLIndicatorExtension != null) {
            return deltaCRLIndicatorExtension.get("value");
        }
        return null;
    }

    public BigInteger getCRLNumber() throws IOException {
        CRLNumberExtension cRLNumberExtension = this.getCRLNumberExtension();
        if (cRLNumberExtension != null) {
            return cRLNumberExtension.get("value");
        }
        return null;
    }

    public CRLNumberExtension getCRLNumberExtension() throws IOException {
        return (CRLNumberExtension)this.getExtension(PKIXExtensions.CRLNumber_Id);
    }

    @Override
    public Set<String> getCriticalExtensionOIDs() {
        if (this.extensions == null) {
            return null;
        }
        TreeSet<String> treeSet = new TreeSet<String>();
        for (Extension extension : this.extensions.getAllExtensions()) {
            if (!extension.isCritical()) continue;
            treeSet.add(extension.getExtensionId().toString());
        }
        return treeSet;
    }

    public DeltaCRLIndicatorExtension getDeltaCRLIndicatorExtension() throws IOException {
        return (DeltaCRLIndicatorExtension)this.getExtension(PKIXExtensions.DeltaCRLIndicator_Id);
    }

    @Override
    public byte[] getEncoded() throws CRLException {
        return (byte[])this.getEncodedInternal().clone();
    }

    public byte[] getEncodedInternal() throws CRLException {
        byte[] arrby = this.signedCRL;
        if (arrby != null) {
            return arrby;
        }
        throw new CRLException("Null CRL to encode");
    }

    public Object getExtension(ObjectIdentifier objectIdentifier) {
        CRLExtensions cRLExtensions = this.extensions;
        if (cRLExtensions == null) {
            return null;
        }
        return cRLExtensions.get(OIDMap.getName(objectIdentifier));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public byte[] getExtensionValue(String object) {
        if (this.extensions == null) {
            return null;
        }
        try {
            Object object2 = new ObjectIdentifier((String)object);
            Object object3 = OIDMap.getName((ObjectIdentifier)object2);
            object2 = null;
            if (object3 == null) {
                object3 = new ObjectIdentifier((String)object);
                Enumeration<Extension> enumeration = this.extensions.getElements();
                do {
                    object = object2;
                } while (enumeration.hasMoreElements() && !((Extension)(object = enumeration.nextElement())).getExtensionId().equals(object3));
            } else {
                object = this.extensions.get((String)object3);
            }
            if (object == null) {
                return null;
            }
            if ((object = ((Extension)object).getExtensionValue()) == null) {
                return null;
            }
            object2 = new DerOutputStream();
            ((DerOutputStream)object2).putOctetString((byte[])object);
            return ((ByteArrayOutputStream)object2).toByteArray();
        }
        catch (Exception exception) {
            return null;
        }
    }

    public IssuerAlternativeNameExtension getIssuerAltNameExtension() throws IOException {
        return (IssuerAlternativeNameExtension)this.getExtension(PKIXExtensions.IssuerAlternativeName_Id);
    }

    @Override
    public Principal getIssuerDN() {
        return this.issuer;
    }

    @Override
    public X500Principal getIssuerX500Principal() {
        if (this.issuerPrincipal == null) {
            this.issuerPrincipal = this.issuer.asX500Principal();
        }
        return this.issuerPrincipal;
    }

    public IssuingDistributionPointExtension getIssuingDistributionPointExtension() throws IOException {
        return (IssuingDistributionPointExtension)this.getExtension(PKIXExtensions.IssuingDistributionPoint_Id);
    }

    @Override
    public Date getNextUpdate() {
        Date date = this.nextUpdate;
        if (date == null) {
            return null;
        }
        return new Date(date.getTime());
    }

    @Override
    public Set<String> getNonCriticalExtensionOIDs() {
        if (this.extensions == null) {
            return null;
        }
        TreeSet<String> treeSet = new TreeSet<String>();
        for (Extension extension : this.extensions.getAllExtensions()) {
            if (extension.isCritical()) continue;
            treeSet.add(extension.getExtensionId().toString());
        }
        return treeSet;
    }

    @Override
    public X509CRLEntry getRevokedCertificate(BigInteger comparable) {
        if (this.revokedMap.isEmpty()) {
            return null;
        }
        comparable = new X509IssuerSerial(this.getIssuerX500Principal(), (BigInteger)comparable);
        return this.revokedMap.get(comparable);
    }

    @Override
    public X509CRLEntry getRevokedCertificate(X509Certificate object) {
        if (this.revokedMap.isEmpty()) {
            return null;
        }
        object = new X509IssuerSerial((X509Certificate)object);
        return this.revokedMap.get(object);
    }

    public Set<X509CRLEntry> getRevokedCertificates() {
        if (this.revokedList.isEmpty()) {
            return null;
        }
        return new TreeSet<X509CRLEntry>(this.revokedList);
    }

    public AlgorithmId getSigAlgId() {
        return this.sigAlgId;
    }

    @Override
    public String getSigAlgName() {
        AlgorithmId algorithmId = this.sigAlgId;
        if (algorithmId == null) {
            return null;
        }
        return algorithmId.getName();
    }

    @Override
    public String getSigAlgOID() {
        AlgorithmId algorithmId = this.sigAlgId;
        if (algorithmId == null) {
            return null;
        }
        return algorithmId.getOID().toString();
    }

    @Override
    public byte[] getSigAlgParams() {
        byte[] arrby = this.sigAlgId;
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

    @Override
    public byte[] getTBSCertList() throws CRLException {
        byte[] arrby = this.tbsCertList;
        if (arrby != null) {
            return (byte[])arrby.clone();
        }
        throw new CRLException("Uninitialized CRL");
    }

    @Override
    public Date getThisUpdate() {
        return new Date(this.thisUpdate.getTime());
    }

    @Override
    public int getVersion() {
        return this.version + 1;
    }

    @Override
    public boolean hasUnsupportedCriticalExtension() {
        CRLExtensions cRLExtensions = this.extensions;
        if (cRLExtensions == null) {
            return false;
        }
        return cRLExtensions.hasUnsupportedCriticalExtension();
    }

    @Override
    public boolean isRevoked(Certificate object) {
        if (!this.revokedMap.isEmpty() && object instanceof X509Certificate) {
            object = new X509IssuerSerial((X509Certificate)object);
            return this.revokedMap.containsKey(object);
        }
        return false;
    }

    public void sign(PrivateKey privateKey, String string) throws CRLException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {
        this.sign(privateKey, string, null);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void sign(PrivateKey object, String object2, String object3) throws CRLException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {
        try {
            if (this.readOnly) {
                object = new CRLException("cannot over-write existing CRL");
                throw object;
            }
            object2 = object3 != null && ((String)object3).length() != 0 ? Signature.getInstance((String)object2, (String)object3) : Signature.getInstance((String)object2);
            ((Signature)object2).initSign((PrivateKey)object);
            this.infoSigAlgId = this.sigAlgId = AlgorithmId.get(((Signature)object2).getAlgorithm());
            object3 = new DerOutputStream();
            object = new DerOutputStream();
            this.encodeInfo((OutputStream)object);
            this.sigAlgId.encode((DerOutputStream)object);
            ((Signature)object2).update(this.tbsCertList, 0, this.tbsCertList.length);
            this.signature = ((Signature)object2).sign();
            ((DerOutputStream)object).putBitString(this.signature);
            ((DerOutputStream)object3).write((byte)48, (DerOutputStream)object);
            this.signedCRL = ((ByteArrayOutputStream)object3).toByteArray();
            this.readOnly = true;
            return;
        }
        catch (IOException iOException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Error while encoding data: ");
            ((StringBuilder)object).append(iOException.getMessage());
            throw new CRLException(((StringBuilder)object).toString());
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public String toString() {
        Object object;
        byte[] arrby;
        int n;
        StringBuffer stringBuffer = new StringBuffer();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("X.509 CRL v");
        stringBuilder.append(this.version + 1);
        stringBuilder.append("\n");
        stringBuffer.append(stringBuilder.toString());
        if (this.sigAlgId != null) {
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("Signature Algorithm: ");
            stringBuilder2.append(this.sigAlgId.toString());
            stringBuilder2.append(", OID=");
            stringBuilder2.append(this.sigAlgId.getOID().toString());
            stringBuilder2.append("\n");
            stringBuffer.append(stringBuilder2.toString());
        }
        if (this.issuer != null) {
            StringBuilder stringBuilder3 = new StringBuilder();
            stringBuilder3.append("Issuer: ");
            stringBuilder3.append(this.issuer.toString());
            stringBuilder3.append("\n");
            stringBuffer.append(stringBuilder3.toString());
        }
        if (this.thisUpdate != null) {
            StringBuilder stringBuilder4 = new StringBuilder();
            stringBuilder4.append("\nThis Update: ");
            stringBuilder4.append(this.thisUpdate.toString());
            stringBuilder4.append("\n");
            stringBuffer.append(stringBuilder4.toString());
        }
        if (this.nextUpdate != null) {
            StringBuilder stringBuilder5 = new StringBuilder();
            stringBuilder5.append("Next Update: ");
            stringBuilder5.append(this.nextUpdate.toString());
            stringBuilder5.append("\n");
            stringBuffer.append(stringBuilder5.toString());
        }
        if (this.revokedList.isEmpty()) {
            stringBuffer.append("\nNO certificates have been revoked\n");
        } else {
            StringBuilder stringBuilder6 = new StringBuilder();
            stringBuilder6.append("\nRevoked Certificates: ");
            stringBuilder6.append(this.revokedList.size());
            stringBuffer.append(stringBuilder6.toString());
            n = 1;
            for (X509CRLEntry x509CRLEntry : this.revokedList) {
                arrby = new StringBuilder();
                arrby.append("\n[");
                arrby.append(n);
                arrby.append("] ");
                arrby.append(x509CRLEntry.toString());
                stringBuffer.append(arrby.toString());
                ++n;
            }
        }
        CRLExtensions cRLExtensions = this.extensions;
        if (cRLExtensions != null) {
            Object[] arrobject = cRLExtensions.getAllExtensions().toArray();
            object = new StringBuilder();
            ((StringBuilder)object).append("\nCRL Extensions: ");
            ((StringBuilder)object).append(arrobject.length);
            stringBuffer.append(((StringBuilder)object).toString());
            for (n = 0; n < arrobject.length; ++n) {
                object = new StringBuilder();
                ((StringBuilder)object).append("\n[");
                ((StringBuilder)object).append(n + 1);
                ((StringBuilder)object).append("]: ");
                stringBuffer.append(((StringBuilder)object).toString());
                object = (Extension)arrobject[n];
                try {
                    if (OIDMap.getClass(((Extension)object).getExtensionId()) == null) {
                        stringBuffer.append(((Extension)object).toString());
                        arrby = ((Extension)object).getExtensionValue();
                        if (arrby == null) continue;
                        object = new DerOutputStream();
                        ((DerOutputStream)object).putOctetString(arrby);
                        arrby = ((ByteArrayOutputStream)object).toByteArray();
                        object = new HexDumpEncoder();
                        StringBuilder stringBuilder7 = new StringBuilder();
                        stringBuilder7.append("Extension unknown: DER encoded OCTET string =\n");
                        stringBuilder7.append(((CharacterEncoder)object).encodeBuffer(arrby));
                        stringBuilder7.append("\n");
                        stringBuffer.append(stringBuilder7.toString());
                        continue;
                    }
                    stringBuffer.append(((Extension)object).toString());
                    continue;
                }
                catch (Exception exception) {
                    stringBuffer.append(", Error parsing this extension");
                }
            }
        }
        if (this.signature != null) {
            object = new HexDumpEncoder();
            StringBuilder stringBuilder8 = new StringBuilder();
            stringBuilder8.append("\nSignature:\n");
            stringBuilder8.append(((CharacterEncoder)object).encodeBuffer(this.signature));
            stringBuilder8.append("\n");
            stringBuffer.append(stringBuilder8.toString());
            return stringBuffer.toString();
        }
        stringBuffer.append("NOT signed yet\n");
        return stringBuffer.toString();
    }

    @Override
    public void verify(PublicKey publicKey) throws CRLException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {
        this.verify(publicKey, "");
    }

    @Override
    public void verify(PublicKey serializable, String object) throws CRLException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {
        synchronized (this) {
            String string;
            block8 : {
                boolean bl;
                string = object;
                if (object == null) {
                    string = "";
                }
                if (this.verifiedPublicKey == null || !this.verifiedPublicKey.equals(serializable) || !(bl = string.equals(this.verifiedProvider))) break block8;
                return;
            }
            if (this.signedCRL != null) {
                object = string.length() == 0 ? Signature.getInstance(this.sigAlgId.getName()) : Signature.getInstance(this.sigAlgId.getName(), string);
                ((Signature)object).initVerify((PublicKey)serializable);
                if (this.tbsCertList != null) {
                    ((Signature)object).update(this.tbsCertList, 0, this.tbsCertList.length);
                    if (((Signature)object).verify(this.signature)) {
                        this.verifiedPublicKey = serializable;
                        this.verifiedProvider = string;
                        return;
                    }
                    serializable = new SignatureException("Signature does not match.");
                    throw serializable;
                }
                serializable = new CRLException("Uninitialized CRL");
                throw serializable;
            }
            serializable = new CRLException("Uninitialized CRL");
            throw serializable;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void verify(PublicKey serializable, Provider object) throws CRLException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        synchronized (this) {
            Signature signature;
            if (this.signedCRL == null) {
                CRLException cRLException = new CRLException("Uninitialized CRL");
                throw cRLException;
            }
            signature = signature == null ? Signature.getInstance(this.sigAlgId.getName()) : Signature.getInstance(this.sigAlgId.getName(), (Provider)((Object)signature));
            signature.initVerify((PublicKey)serializable);
            if (this.tbsCertList == null) {
                CRLException cRLException = new CRLException("Uninitialized CRL");
                throw cRLException;
            }
            signature.update(this.tbsCertList, 0, this.tbsCertList.length);
            if (signature.verify(this.signature)) {
                this.verifiedPublicKey = serializable;
                return;
            }
            SignatureException signatureException = new SignatureException("Signature does not match.");
            throw signatureException;
        }
    }

    private static final class X509IssuerSerial
    implements Comparable<X509IssuerSerial> {
        volatile int hashcode = 0;
        final X500Principal issuer;
        final BigInteger serial;

        X509IssuerSerial(X509Certificate x509Certificate) {
            this(x509Certificate.getIssuerX500Principal(), x509Certificate.getSerialNumber());
        }

        X509IssuerSerial(X500Principal x500Principal, BigInteger bigInteger) {
            this.issuer = x500Principal;
            this.serial = bigInteger;
        }

        @Override
        public int compareTo(X509IssuerSerial x509IssuerSerial) {
            int n = this.issuer.toString().compareTo(x509IssuerSerial.issuer.toString());
            if (n != 0) {
                return n;
            }
            return this.serial.compareTo(x509IssuerSerial.serial);
        }

        public boolean equals(Object object) {
            if (object == this) {
                return true;
            }
            if (!(object instanceof X509IssuerSerial)) {
                return false;
            }
            return this.serial.equals(((X509IssuerSerial)(object = (X509IssuerSerial)object)).getSerial()) && this.issuer.equals(((X509IssuerSerial)object).getIssuer());
        }

        X500Principal getIssuer() {
            return this.issuer;
        }

        BigInteger getSerial() {
            return this.serial;
        }

        public int hashCode() {
            if (this.hashcode == 0) {
                this.hashcode = (17 * 37 + this.issuer.hashCode()) * 37 + this.serial.hashCode();
            }
            return this.hashcode;
        }
    }

}

