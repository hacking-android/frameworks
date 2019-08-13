/*
 * Decompiled with CFR 0.145.
 */
package java.security.cert;

import java.io.IOException;
import java.math.BigInteger;
import java.security.cert.CRL;
import java.security.cert.CRLSelector;
import java.security.cert.CertPathHelperImpl;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import javax.security.auth.x500.X500Principal;
import sun.security.util.Debug;
import sun.security.util.DerInputStream;
import sun.security.x509.CRLNumberExtension;
import sun.security.x509.X500Name;

public class X509CRLSelector
implements CRLSelector {
    private static final Debug debug;
    private X509Certificate certChecking;
    private Date dateAndTime;
    private HashSet<Object> issuerNames;
    private HashSet<X500Principal> issuerX500Principals;
    private BigInteger maxCRL;
    private BigInteger minCRL;
    private long skew = 0L;

    static {
        CertPathHelperImpl.initialize();
        debug = Debug.getInstance("certpath");
    }

    private void addIssuerNameInternal(Object object, X500Principal x500Principal) {
        if (this.issuerNames == null) {
            this.issuerNames = new HashSet();
        }
        if (this.issuerX500Principals == null) {
            this.issuerX500Principals = new HashSet();
        }
        this.issuerNames.add(object);
        this.issuerX500Principals.add(x500Principal);
    }

    private static HashSet<Object> cloneAndCheckIssuerNames(Collection<?> collection2) throws IOException {
        HashSet<Object> hashSet = new HashSet<Object>();
        for (Collection<?> collection2 : collection2) {
            if (!(collection2 instanceof byte[]) && !(collection2 instanceof String)) {
                throw new IOException("name not byte array or String");
            }
            if (collection2 instanceof byte[]) {
                hashSet.add(((byte[])collection2).clone());
                continue;
            }
            hashSet.add(collection2);
        }
        return hashSet;
    }

    private static HashSet<Object> cloneIssuerNames(Collection<Object> collection) {
        try {
            collection = X509CRLSelector.cloneAndCheckIssuerNames(collection);
            return collection;
        }
        catch (IOException iOException) {
            throw new RuntimeException(iOException);
        }
    }

    private static HashSet<X500Principal> parseIssuerNames(Collection<Object> object) throws IOException {
        HashSet<X500Principal> hashSet = new HashSet<X500Principal>();
        Iterator<Object> iterator = object.iterator();
        while (iterator.hasNext()) {
            Object object2 = iterator.next();
            if (object2 instanceof String) {
                hashSet.add(new X500Name((String)object2).asX500Principal());
                continue;
            }
            try {
                object = new X500Principal((byte[])object2);
                hashSet.add((X500Principal)object);
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw (IOException)new IOException("Invalid name").initCause(illegalArgumentException);
            }
        }
        return hashSet;
    }

    public void addIssuer(X500Principal x500Principal) {
        this.addIssuerNameInternal(x500Principal.getEncoded(), x500Principal);
    }

    public void addIssuerName(String string) throws IOException {
        this.addIssuerNameInternal(string, new X500Name(string).asX500Principal());
    }

    public void addIssuerName(byte[] arrby) throws IOException {
        this.addIssuerNameInternal(arrby.clone(), new X500Name(arrby).asX500Principal());
    }

    @Override
    public Object clone() {
        try {
            X509CRLSelector x509CRLSelector = (X509CRLSelector)super.clone();
            if (this.issuerNames != null) {
                HashSet<X500Principal> hashSet = new HashSet<X500Principal>(this.issuerNames);
                x509CRLSelector.issuerNames = hashSet;
                hashSet = new HashSet<X500Principal>(this.issuerX500Principals);
                x509CRLSelector.issuerX500Principals = hashSet;
            }
            return x509CRLSelector;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError(cloneNotSupportedException.toString(), cloneNotSupportedException);
        }
    }

    public X509Certificate getCertificateChecking() {
        return this.certChecking;
    }

    public Date getDateAndTime() {
        Date date = this.dateAndTime;
        if (date == null) {
            return null;
        }
        return (Date)date.clone();
    }

    public Collection<Object> getIssuerNames() {
        HashSet<Object> hashSet = this.issuerNames;
        if (hashSet == null) {
            return null;
        }
        return X509CRLSelector.cloneIssuerNames(hashSet);
    }

    public Collection<X500Principal> getIssuers() {
        HashSet<X500Principal> hashSet = this.issuerX500Principals;
        if (hashSet == null) {
            return null;
        }
        return Collections.unmodifiableCollection(hashSet);
    }

    public BigInteger getMaxCRL() {
        return this.maxCRL;
    }

    public BigInteger getMinCRL() {
        return this.minCRL;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public boolean match(CRL object) {
        Object object2;
        Object object3;
        block12 : {
            if (!(object instanceof X509CRL)) {
                return false;
            }
            object = (X509CRL)object;
            if (this.issuerNames != null) {
                object3 = ((X509CRL)object).getIssuerX500Principal();
                object2 = this.issuerX500Principals.iterator();
                boolean bl = false;
                while (!bl && object2.hasNext()) {
                    if (!((X500Principal)object2.next()).equals(object3)) continue;
                    bl = true;
                }
                if (!bl) {
                    object = debug;
                    if (object == null) return false;
                    ((Debug)object).println("X509CRLSelector.match: issuer DNs don't match");
                    return false;
                }
            }
            if (this.minCRL != null || this.maxCRL != null) {
                object2 = object.getExtensionValue("2.5.29.20");
                if (object2 == null && (object3 = debug) != null) {
                    object3.println("X509CRLSelector.match: no CRLNumber");
                }
                object3 = new DerInputStream((byte[])object2);
                object3 = object3.getOctetString();
                object2 = new CRLNumberExtension(Boolean.FALSE, object3);
                object2 = ((CRLNumberExtension)object2).get("value");
                object3 = this.minCRL;
                if (object3 != null && ((BigInteger)object2).compareTo((BigInteger)object3) < 0) {
                    object = debug;
                    if (object == null) return false;
                    ((Debug)object).println("X509CRLSelector.match: CRLNumber too small");
                    return false;
                }
                object3 = this.maxCRL;
                if (object3 == null || ((BigInteger)object2).compareTo((BigInteger)object3) <= 0) break block12;
                object = debug;
                if (object == null) return false;
                ((Debug)object).println("X509CRLSelector.match: CRLNumber too large");
                return false;
            }
        }
        if (this.dateAndTime == null) return true;
        object3 = ((X509CRL)object).getThisUpdate();
        Date date = ((X509CRL)object).getNextUpdate();
        if (date == null) {
            object = debug;
            if (object == null) return false;
            ((Debug)object).println("X509CRLSelector.match: nextUpdate null");
            return false;
        }
        object = this.dateAndTime;
        object2 = this.dateAndTime;
        if (this.skew > 0L) {
            object = new Date(this.dateAndTime.getTime() + this.skew);
            object2 = new Date(this.dateAndTime.getTime() - this.skew);
        }
        if (!((Date)object2).after(date)) {
            if (!((Date)object).before((Date)object3)) return true;
        }
        if ((object = debug) == null) return false;
        ((Debug)object).println("X509CRLSelector.match: update out-of-range");
        return false;
        catch (IOException iOException) {
            Debug debug = X509CRLSelector.debug;
            if (debug == null) return false;
            debug.println("X509CRLSelector.match: exception in decoding CRL number");
            return false;
        }
    }

    public void setCertificateChecking(X509Certificate x509Certificate) {
        this.certChecking = x509Certificate;
    }

    public void setDateAndTime(Date date) {
        this.dateAndTime = date == null ? null : new Date(date.getTime());
        this.skew = 0L;
    }

    void setDateAndTime(Date date, long l) {
        date = date == null ? null : new Date(date.getTime());
        this.dateAndTime = date;
        this.skew = l;
    }

    public void setIssuerNames(Collection<?> collection) throws IOException {
        if (collection != null && collection.size() != 0) {
            collection = X509CRLSelector.cloneAndCheckIssuerNames(collection);
            this.issuerX500Principals = X509CRLSelector.parseIssuerNames(collection);
            this.issuerNames = collection;
        } else {
            this.issuerNames = null;
            this.issuerX500Principals = null;
        }
    }

    public void setIssuers(Collection<X500Principal> object2) {
        if (object2 != null && !object2.isEmpty()) {
            this.issuerX500Principals = new HashSet(object2);
            this.issuerNames = new HashSet();
            for (X500Principal x500Principal : this.issuerX500Principals) {
                this.issuerNames.add(x500Principal.getEncoded());
            }
        } else {
            this.issuerNames = null;
            this.issuerX500Principals = null;
        }
    }

    public void setMaxCRLNumber(BigInteger bigInteger) {
        this.maxCRL = bigInteger;
    }

    public void setMinCRLNumber(BigInteger bigInteger) {
        this.minCRL = bigInteger;
    }

    public String toString() {
        Object object;
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("X509CRLSelector: [\n");
        if (this.issuerNames != null) {
            stringBuffer.append("  IssuerNames:\n");
            object = this.issuerNames.iterator();
            while (object.hasNext()) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("    ");
                stringBuilder.append(object.next());
                stringBuilder.append("\n");
                stringBuffer.append(stringBuilder.toString());
            }
        }
        if (this.minCRL != null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("  minCRLNumber: ");
            ((StringBuilder)object).append(this.minCRL);
            ((StringBuilder)object).append("\n");
            stringBuffer.append(((StringBuilder)object).toString());
        }
        if (this.maxCRL != null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("  maxCRLNumber: ");
            ((StringBuilder)object).append(this.maxCRL);
            ((StringBuilder)object).append("\n");
            stringBuffer.append(((StringBuilder)object).toString());
        }
        if (this.dateAndTime != null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("  dateAndTime: ");
            ((StringBuilder)object).append(this.dateAndTime);
            ((StringBuilder)object).append("\n");
            stringBuffer.append(((StringBuilder)object).toString());
        }
        if (this.certChecking != null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("  Certificate being checked: ");
            ((StringBuilder)object).append(this.certChecking);
            ((StringBuilder)object).append("\n");
            stringBuffer.append(((StringBuilder)object).toString());
        }
        stringBuffer.append("]");
        return stringBuffer.toString();
    }
}

