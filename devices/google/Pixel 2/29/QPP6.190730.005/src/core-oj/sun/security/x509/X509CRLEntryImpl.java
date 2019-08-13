/*
 * Decompiled with CFR 0.145.
 */
package sun.security.x509;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.cert.CRLException;
import java.security.cert.CRLReason;
import java.security.cert.X509CRLEntry;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import javax.security.auth.x500.X500Principal;
import sun.misc.HexDumpEncoder;
import sun.security.util.DerInputStream;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import sun.security.util.ObjectIdentifier;
import sun.security.x509.CRLExtensions;
import sun.security.x509.CRLReasonCodeExtension;
import sun.security.x509.CertificateIssuerExtension;
import sun.security.x509.Extension;
import sun.security.x509.OIDMap;
import sun.security.x509.PKIXExtensions;
import sun.security.x509.SerialNumber;

public class X509CRLEntryImpl
extends X509CRLEntry
implements Comparable<X509CRLEntryImpl> {
    private static final long YR_2050 = 2524636800000L;
    private static final boolean isExplicit = false;
    private X500Principal certIssuer;
    private CRLExtensions extensions = null;
    private Date revocationDate = null;
    private byte[] revokedCert = null;
    private SerialNumber serialNumber = null;

    public X509CRLEntryImpl(BigInteger bigInteger, Date date) {
        this.serialNumber = new SerialNumber(bigInteger);
        this.revocationDate = date;
    }

    public X509CRLEntryImpl(BigInteger bigInteger, Date date, CRLExtensions cRLExtensions) {
        this.serialNumber = new SerialNumber(bigInteger);
        this.revocationDate = date;
        this.extensions = cRLExtensions;
    }

    public X509CRLEntryImpl(DerValue derValue) throws CRLException {
        try {
            this.parse(derValue);
            return;
        }
        catch (IOException iOException) {
            this.revokedCert = null;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Parsing error: ");
            stringBuilder.append(iOException.toString());
            throw new CRLException(stringBuilder.toString());
        }
    }

    public X509CRLEntryImpl(byte[] arrby) throws CRLException {
        try {
            DerValue derValue = new DerValue(arrby);
            this.parse(derValue);
            return;
        }
        catch (IOException iOException) {
            this.revokedCert = null;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Parsing error: ");
            stringBuilder.append(iOException.toString());
            throw new CRLException(stringBuilder.toString());
        }
    }

    private byte[] getEncoded0() throws CRLException {
        if (this.revokedCert == null) {
            this.encode(new DerOutputStream());
        }
        return this.revokedCert;
    }

    public static CRLReason getRevocationReason(X509CRLEntry object) {
        block3 : {
            try {
                object = object.getExtensionValue("2.5.29.21");
                if (object != null) break block3;
                return null;
            }
            catch (IOException iOException) {
                return null;
            }
        }
        Object object2 = new DerValue((byte[])object);
        object = ((DerValue)object2).getOctetString();
        object2 = new CRLReasonCodeExtension(Boolean.FALSE, object);
        object = ((CRLReasonCodeExtension)object2).getReasonCode();
        return object;
    }

    private void parse(DerValue derValue) throws CRLException, IOException {
        block4 : {
            block5 : {
                block8 : {
                    block7 : {
                        int n;
                        block6 : {
                            if (derValue.tag != 48) break block4;
                            if (derValue.data.available() == 0) break block5;
                            this.revokedCert = derValue.toByteArray();
                            this.serialNumber = new SerialNumber(derValue.toDerInputStream().getDerValue());
                            n = derValue.data.peekByte();
                            if ((byte)n != 23) break block6;
                            this.revocationDate = derValue.data.getUTCTime();
                            break block7;
                        }
                        if ((byte)n != 24) break block8;
                        this.revocationDate = derValue.data.getGeneralizedTime();
                    }
                    if (derValue.data.available() == 0) {
                        return;
                    }
                    this.extensions = new CRLExtensions(derValue.toDerInputStream());
                    return;
                }
                throw new CRLException("Invalid encoding for revocation date");
            }
            throw new CRLException("No data encoded for RevokedCertificates");
        }
        throw new CRLException("Invalid encoded RevokedCertificate, starting sequence tag missing.");
    }

    public static X509CRLEntryImpl toImpl(X509CRLEntry x509CRLEntry) throws CRLException {
        if (x509CRLEntry instanceof X509CRLEntryImpl) {
            return (X509CRLEntryImpl)x509CRLEntry;
        }
        return new X509CRLEntryImpl(x509CRLEntry.getEncoded());
    }

    @Override
    public int compareTo(X509CRLEntryImpl arrby) {
        int n;
        int n2 = this.getSerialNumber().compareTo(arrby.getSerialNumber());
        if (n2 != 0) {
            return n2;
        }
        byte[] arrby2 = this.getEncoded0();
        arrby = arrby.getEncoded0();
        n2 = 0;
        do {
            if (n2 >= arrby2.length || n2 >= arrby.length) break;
            int n3 = arrby2[n2] & 255;
            n = arrby[n2] & 255;
            if (n3 != n) {
                return n3 - n;
            }
            ++n2;
        } while (true);
        try {
            n = arrby2.length;
            n2 = arrby.length;
            return n - n2;
        }
        catch (CRLException cRLException) {
            return -1;
        }
    }

    public void encode(DerOutputStream object) throws CRLException {
        try {
            if (this.revokedCert == null) {
                DerOutputStream derOutputStream = new DerOutputStream();
                this.serialNumber.encode(derOutputStream);
                if (this.revocationDate.getTime() < 2524636800000L) {
                    derOutputStream.putUTCTime(this.revocationDate);
                } else {
                    derOutputStream.putGeneralizedTime(this.revocationDate);
                }
                if (this.extensions != null) {
                    this.extensions.encode(derOutputStream, false);
                }
                DerOutputStream derOutputStream2 = new DerOutputStream();
                derOutputStream2.write((byte)48, derOutputStream);
                this.revokedCert = derOutputStream2.toByteArray();
            }
            ((OutputStream)object).write(this.revokedCert);
            return;
        }
        catch (IOException iOException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Encoding error: ");
            ((StringBuilder)object).append(iOException.toString());
            throw new CRLException(((StringBuilder)object).toString());
        }
    }

    @Override
    public X500Principal getCertificateIssuer() {
        return this.certIssuer;
    }

    CertificateIssuerExtension getCertificateIssuerExtension() {
        return (CertificateIssuerExtension)this.getExtension(PKIXExtensions.CertificateIssuer_Id);
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

    @Override
    public byte[] getEncoded() throws CRLException {
        return (byte[])this.getEncoded0().clone();
    }

    public Extension getExtension(ObjectIdentifier objectIdentifier) {
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
            byte[] arrby = new ObjectIdentifier((String)object);
            Object object2 = OIDMap.getName((ObjectIdentifier)arrby);
            arrby = null;
            if (object2 == null) {
                object2 = new ObjectIdentifier((String)object);
                Enumeration<Extension> enumeration = this.extensions.getElements();
                do {
                    object = arrby;
                } while (enumeration.hasMoreElements() && !((Extension)(object = enumeration.nextElement())).getExtensionId().equals(object2));
            } else {
                object = this.extensions.get((String)object2);
            }
            if (object == null) {
                return null;
            }
            arrby = ((Extension)object).getExtensionValue();
            if (arrby == null) {
                return null;
            }
            object = new DerOutputStream();
            ((DerOutputStream)object).putOctetString(arrby);
            return ((ByteArrayOutputStream)object).toByteArray();
        }
        catch (Exception exception) {
            return null;
        }
    }

    public Map<String, java.security.cert.Extension> getExtensions() {
        Object object = this.extensions;
        if (object == null) {
            return Collections.emptyMap();
        }
        Object object2 = ((CRLExtensions)object).getAllExtensions();
        object = new TreeMap();
        object2 = object2.iterator();
        while (object2.hasNext()) {
            Extension extension = (Extension)object2.next();
            object.put(extension.getId(), extension);
        }
        return object;
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

    public Integer getReasonCode() throws IOException {
        Extension extension = this.getExtension(PKIXExtensions.ReasonCode_Id);
        if (extension == null) {
            return null;
        }
        return ((CRLReasonCodeExtension)extension).get("reason");
    }

    @Override
    public Date getRevocationDate() {
        return new Date(this.revocationDate.getTime());
    }

    @Override
    public CRLReason getRevocationReason() {
        Extension extension = this.getExtension(PKIXExtensions.ReasonCode_Id);
        if (extension == null) {
            return null;
        }
        return ((CRLReasonCodeExtension)extension).getReasonCode();
    }

    @Override
    public BigInteger getSerialNumber() {
        return this.serialNumber.getNumber();
    }

    @Override
    public boolean hasExtensions() {
        boolean bl = this.extensions != null;
        return bl;
    }

    @Override
    public boolean hasUnsupportedCriticalExtension() {
        CRLExtensions cRLExtensions = this.extensions;
        if (cRLExtensions == null) {
            return false;
        }
        return cRLExtensions.hasUnsupportedCriticalExtension();
    }

    void setCertificateIssuer(X500Principal x500Principal, X500Principal x500Principal2) {
        this.certIssuer = x500Principal.equals(x500Principal2) ? null : x500Principal2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.serialNumber.toString());
        Extension[] arrextension = new StringBuilder();
        arrextension.append("  On: ");
        arrextension.append(this.revocationDate.toString());
        stringBuilder.append(arrextension.toString());
        if (this.certIssuer != null) {
            arrextension = new StringBuilder();
            arrextension.append("\n    Certificate issuer: ");
            arrextension.append(this.certIssuer);
            stringBuilder.append(arrextension.toString());
        }
        if ((arrextension = this.extensions) != null) {
            arrextension = arrextension.getAllExtensions().toArray(new Extension[0]);
            byte[] arrby = new StringBuilder();
            arrby.append("\n    CRL Entry Extensions: ");
            arrby.append(arrextension.length);
            stringBuilder.append(arrby.toString());
            for (int i = 0; i < arrextension.length; ++i) {
                arrby = new StringBuilder();
                arrby.append("\n    [");
                arrby.append(i + 1);
                arrby.append("]: ");
                stringBuilder.append(arrby.toString());
                arrby = arrextension[i];
                try {
                    if (OIDMap.getClass(arrby.getExtensionId()) == null) {
                        stringBuilder.append(arrby.toString());
                        Object object = arrby.getExtensionValue();
                        if (object == null) continue;
                        arrby = new DerOutputStream();
                        arrby.putOctetString((byte[])object);
                        arrby = arrby.toByteArray();
                        HexDumpEncoder hexDumpEncoder = new HexDumpEncoder();
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Extension unknown: DER encoded OCTET string =\n");
                        ((StringBuilder)object).append(hexDumpEncoder.encodeBuffer(arrby));
                        ((StringBuilder)object).append("\n");
                        stringBuilder.append(((StringBuilder)object).toString());
                        continue;
                    }
                    stringBuilder.append(arrby.toString());
                    continue;
                }
                catch (Exception exception) {
                    stringBuilder.append(", Error parsing this extension");
                }
            }
        }
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }
}

