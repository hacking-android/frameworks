/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1.x509;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1Object;
import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.ASN1OctetString;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1Sequence;
import com.android.org.bouncycastle.asn1.DERIA5String;
import com.android.org.bouncycastle.asn1.x500.X500Name;
import com.android.org.bouncycastle.asn1.x509.GeneralName;
import com.android.org.bouncycastle.asn1.x509.GeneralSubtree;
import com.android.org.bouncycastle.asn1.x509.NameConstraintValidator;
import com.android.org.bouncycastle.asn1.x509.NameConstraintValidatorException;
import com.android.org.bouncycastle.asn1.x509.OtherName;
import com.android.org.bouncycastle.util.Arrays;
import com.android.org.bouncycastle.util.Integers;
import com.android.org.bouncycastle.util.Strings;
import com.android.org.bouncycastle.util.encoders.Hex;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class PKIXNameConstraintValidator
implements NameConstraintValidator {
    private Set excludedSubtreesDN = new HashSet();
    private Set excludedSubtreesDNS = new HashSet();
    private Set excludedSubtreesEmail = new HashSet();
    private Set excludedSubtreesIP = new HashSet();
    private Set excludedSubtreesOtherName = new HashSet();
    private Set excludedSubtreesURI = new HashSet();
    private Set permittedSubtreesDN;
    private Set permittedSubtreesDNS;
    private Set permittedSubtreesEmail;
    private Set permittedSubtreesIP;
    private Set permittedSubtreesOtherName;
    private Set permittedSubtreesURI;

    private void checkExcludedDN(X500Name x500Name) throws NameConstraintValidatorException {
        this.checkExcludedDN(this.excludedSubtreesDN, ASN1Sequence.getInstance(x500Name));
    }

    private void checkExcludedDN(Set object, ASN1Sequence aSN1Sequence) throws NameConstraintValidatorException {
        if (object.isEmpty()) {
            return;
        }
        object = object.iterator();
        while (object.hasNext()) {
            if (!PKIXNameConstraintValidator.withinDNSubtree(aSN1Sequence, (ASN1Sequence)object.next())) continue;
            throw new NameConstraintValidatorException("Subject distinguished name is from an excluded subtree");
        }
    }

    private void checkExcludedDNS(Set object, String string) throws NameConstraintValidatorException {
        if (object.isEmpty()) {
            return;
        }
        Iterator iterator = object.iterator();
        while (iterator.hasNext()) {
            object = (String)iterator.next();
            if (!this.withinDomain(string, (String)object) && !string.equalsIgnoreCase((String)object)) continue;
            throw new NameConstraintValidatorException("DNS is from an excluded subtree.");
        }
    }

    private void checkExcludedEmail(Set object, String string) throws NameConstraintValidatorException {
        if (object.isEmpty()) {
            return;
        }
        object = object.iterator();
        while (object.hasNext()) {
            if (!this.emailIsConstrained(string, (String)object.next())) continue;
            throw new NameConstraintValidatorException("Email address is from an excluded subtree.");
        }
    }

    private void checkExcludedIP(Set object, byte[] arrby) throws NameConstraintValidatorException {
        if (object.isEmpty()) {
            return;
        }
        object = object.iterator();
        while (object.hasNext()) {
            if (!this.isIPConstrained(arrby, (byte[])object.next())) continue;
            throw new NameConstraintValidatorException("IP is from an excluded subtree.");
        }
    }

    private void checkExcludedOtherName(Set object, OtherName otherName) throws NameConstraintValidatorException {
        if (object.isEmpty()) {
            return;
        }
        object = object.iterator();
        while (object.hasNext()) {
            if (!this.otherNameIsConstrained(otherName, OtherName.getInstance(object.next()))) continue;
            throw new NameConstraintValidatorException("OtherName is from an excluded subtree.");
        }
    }

    private void checkExcludedURI(Set object, String string) throws NameConstraintValidatorException {
        if (object.isEmpty()) {
            return;
        }
        object = object.iterator();
        while (object.hasNext()) {
            if (!this.isUriConstrained(string, (String)object.next())) continue;
            throw new NameConstraintValidatorException("URI is from an excluded subtree.");
        }
    }

    private void checkPermittedDN(X500Name x500Name) throws NameConstraintValidatorException {
        this.checkPermittedDN(this.permittedSubtreesDN, ASN1Sequence.getInstance(x500Name.toASN1Primitive()));
    }

    private void checkPermittedDN(Set object, ASN1Sequence aSN1Sequence) throws NameConstraintValidatorException {
        if (object == null) {
            return;
        }
        if (object.isEmpty() && aSN1Sequence.size() == 0) {
            return;
        }
        object = object.iterator();
        while (object.hasNext()) {
            if (!PKIXNameConstraintValidator.withinDNSubtree(aSN1Sequence, (ASN1Sequence)object.next())) continue;
            return;
        }
        throw new NameConstraintValidatorException("Subject distinguished name is not from a permitted subtree");
    }

    private void checkPermittedDNS(Set set, String string) throws NameConstraintValidatorException {
        if (set == null) {
            return;
        }
        for (String string2 : set) {
            if (!this.withinDomain(string, string2) && !string.equalsIgnoreCase(string2)) continue;
            return;
        }
        if (string.length() == 0 && set.size() == 0) {
            return;
        }
        throw new NameConstraintValidatorException("DNS is not from a permitted subtree.");
    }

    private void checkPermittedEmail(Set set, String string) throws NameConstraintValidatorException {
        if (set == null) {
            return;
        }
        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            if (!this.emailIsConstrained(string, (String)iterator.next())) continue;
            return;
        }
        if (string.length() == 0 && set.size() == 0) {
            return;
        }
        throw new NameConstraintValidatorException("Subject email address is not from a permitted subtree.");
    }

    private void checkPermittedIP(Set set, byte[] arrby) throws NameConstraintValidatorException {
        if (set == null) {
            return;
        }
        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            if (!this.isIPConstrained(arrby, (byte[])iterator.next())) continue;
            return;
        }
        if (arrby.length == 0 && set.size() == 0) {
            return;
        }
        throw new NameConstraintValidatorException("IP is not from a permitted subtree.");
    }

    private void checkPermittedOtherName(Set object, OtherName otherName) throws NameConstraintValidatorException {
        if (object == null) {
            return;
        }
        object = object.iterator();
        while (object.hasNext()) {
            if (!this.otherNameIsConstrained(otherName, (OtherName)object.next())) continue;
            return;
        }
        throw new NameConstraintValidatorException("Subject OtherName is not from a permitted subtree.");
    }

    private void checkPermittedURI(Set set, String string) throws NameConstraintValidatorException {
        if (set == null) {
            return;
        }
        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            if (!this.isUriConstrained(string, (String)iterator.next())) continue;
            return;
        }
        if (string.length() == 0 && set.size() == 0) {
            return;
        }
        throw new NameConstraintValidatorException("URI is not from a permitted subtree.");
    }

    private boolean collectionsAreEqual(Collection object, Collection collection) {
        if (object == collection) {
            return true;
        }
        if (object != null && collection != null) {
            if (object.size() != collection.size()) {
                return false;
            }
            object = object.iterator();
            while (object.hasNext()) {
                boolean bl;
                block5 : {
                    Object e = object.next();
                    Iterator iterator = collection.iterator();
                    boolean bl2 = false;
                    do {
                        bl = bl2;
                        if (!iterator.hasNext()) break block5;
                    } while (!this.equals(e, iterator.next()));
                    bl = true;
                }
                if (bl) continue;
                return false;
            }
            return true;
        }
        return false;
    }

    private static int compareTo(byte[] arrby, byte[] arrby2) {
        if (Arrays.areEqual(arrby, arrby2)) {
            return 0;
        }
        if (Arrays.areEqual(PKIXNameConstraintValidator.max(arrby, arrby2), arrby)) {
            return 1;
        }
        return -1;
    }

    private boolean emailIsConstrained(String string, String string2) {
        String string3 = string.substring(string.indexOf(64) + 1);
        return string2.indexOf(64) != -1 ? string.equalsIgnoreCase(string2) : (string2.charAt(0) != '.' ? string3.equalsIgnoreCase(string2) : this.withinDomain(string3, string2));
    }

    private boolean equals(Object object, Object object2) {
        if (object == object2) {
            return true;
        }
        if (object != null && object2 != null) {
            if (object instanceof byte[] && object2 instanceof byte[]) {
                return Arrays.areEqual((byte[])object, (byte[])object2);
            }
            return object.equals(object2);
        }
        return false;
    }

    private static String extractHostFromURL(String string) {
        String string2;
        string = string2 = string.substring(string.indexOf(58) + 1);
        if (string2.indexOf("//") != -1) {
            string = string2.substring(string2.indexOf("//") + 2);
        }
        string2 = string;
        if (string.lastIndexOf(58) != -1) {
            string2 = string.substring(0, string.lastIndexOf(58));
        }
        string = string2.substring(string2.indexOf(58) + 1);
        string = string2 = string.substring(string.indexOf(64) + 1);
        if (string2.indexOf(47) != -1) {
            string = string2.substring(0, string2.indexOf(47));
        }
        return string;
    }

    private byte[][] extractIPsAndSubnetMasks(byte[] arrby, byte[] arrby2) {
        int n = arrby.length / 2;
        byte[] arrby3 = new byte[n];
        byte[] arrby4 = new byte[n];
        System.arraycopy((byte[])arrby, (int)0, (byte[])arrby3, (int)0, (int)n);
        System.arraycopy((byte[])arrby, (int)n, (byte[])arrby4, (int)0, (int)n);
        byte[] arrby5 = new byte[n];
        arrby = new byte[n];
        System.arraycopy((byte[])arrby2, (int)0, (byte[])arrby5, (int)0, (int)n);
        System.arraycopy((byte[])arrby2, (int)n, (byte[])arrby, (int)0, (int)n);
        return new byte[][]{arrby3, arrby4, arrby5, arrby};
    }

    private String extractNameAsString(GeneralName generalName) {
        return DERIA5String.getInstance(generalName.getName()).getString();
    }

    private int hashCollection(Collection object) {
        if (object == null) {
            return 0;
        }
        int n = 0;
        object = object.iterator();
        while (object.hasNext()) {
            Object e = object.next();
            if (e instanceof byte[]) {
                n += Arrays.hashCode((byte[])e);
                continue;
            }
            n += e.hashCode();
        }
        return n;
    }

    private Set intersectDN(Set set, Set object) {
        HashSet<ASN1Sequence> hashSet = new HashSet<ASN1Sequence>();
        object = object.iterator();
        while (object.hasNext()) {
            ASN1Sequence aSN1Sequence = ASN1Sequence.getInstance(((GeneralSubtree)object.next()).getBase().getName().toASN1Primitive());
            if (set == null) {
                if (aSN1Sequence == null) continue;
                hashSet.add(aSN1Sequence);
                continue;
            }
            for (ASN1Sequence aSN1Sequence2 : set) {
                if (PKIXNameConstraintValidator.withinDNSubtree(aSN1Sequence, aSN1Sequence2)) {
                    hashSet.add(aSN1Sequence);
                    continue;
                }
                if (!PKIXNameConstraintValidator.withinDNSubtree(aSN1Sequence2, aSN1Sequence)) continue;
                hashSet.add(aSN1Sequence2);
            }
        }
        return hashSet;
    }

    private Set intersectDNS(Set set, Set object2) {
        HashSet<Object> hashSet = new HashSet<Object>();
        Iterator iterator = object2.iterator();
        while (iterator.hasNext()) {
            String string = this.extractNameAsString(((GeneralSubtree)iterator.next()).getBase());
            if (set == null) {
                if (string == null) continue;
                hashSet.add(string);
                continue;
            }
            for (Object object2 : set) {
                if (this.withinDomain((String)object2, string)) {
                    hashSet.add(object2);
                    continue;
                }
                if (!this.withinDomain(string, (String)object2)) continue;
                hashSet.add(string);
            }
        }
        return hashSet;
    }

    private Set intersectEmail(Set set, Set object) {
        HashSet<String> hashSet = new HashSet<String>();
        Iterator iterator = object.iterator();
        while (iterator.hasNext()) {
            String string = this.extractNameAsString(((GeneralSubtree)iterator.next()).getBase());
            if (set == null) {
                if (string == null) continue;
                hashSet.add(string);
                continue;
            }
            object = set.iterator();
            while (object.hasNext()) {
                this.intersectEmail(string, (String)object.next(), hashSet);
            }
        }
        return hashSet;
    }

    private void intersectEmail(String string, String string2, Set set) {
        if (string.indexOf(64) != -1) {
            String string3 = string.substring(string.indexOf(64) + 1);
            if (string2.indexOf(64) != -1) {
                if (string.equalsIgnoreCase(string2)) {
                    set.add(string);
                }
            } else if (string2.startsWith(".")) {
                if (this.withinDomain(string3, string2)) {
                    set.add(string);
                }
            } else if (string3.equalsIgnoreCase(string2)) {
                set.add(string);
            }
        } else if (string.startsWith(".")) {
            if (string2.indexOf(64) != -1) {
                if (this.withinDomain(string2.substring(string.indexOf(64) + 1), string)) {
                    set.add(string2);
                }
            } else if (string2.startsWith(".")) {
                if (!this.withinDomain(string, string2) && !string.equalsIgnoreCase(string2)) {
                    if (this.withinDomain(string2, string)) {
                        set.add(string2);
                    }
                } else {
                    set.add(string);
                }
            } else if (this.withinDomain(string2, string)) {
                set.add(string2);
            }
        } else if (string2.indexOf(64) != -1) {
            if (string2.substring(string2.indexOf(64) + 1).equalsIgnoreCase(string)) {
                set.add(string2);
            }
        } else if (string2.startsWith(".")) {
            if (this.withinDomain(string, string2)) {
                set.add(string);
            }
        } else if (string.equalsIgnoreCase(string2)) {
            set.add(string);
        }
    }

    private Set intersectIP(Set set, Set object) {
        HashSet<byte[]> hashSet = new HashSet<byte[]>();
        object = object.iterator();
        while (object.hasNext()) {
            byte[] arrby = ASN1OctetString.getInstance(((GeneralSubtree)object.next()).getBase().getName()).getOctets();
            if (set == null) {
                if (arrby == null) continue;
                hashSet.add(arrby);
                continue;
            }
            Iterator iterator = set.iterator();
            while (iterator.hasNext()) {
                hashSet.addAll(this.intersectIPRange((byte[])iterator.next(), arrby));
            }
        }
        return hashSet;
    }

    private Set intersectIPRange(byte[] object, byte[] object2) {
        if (((byte[])object).length != ((byte[])object2).length) {
            return Collections.EMPTY_SET;
        }
        object2 = this.extractIPsAndSubnetMasks((byte[])object, (byte[])object2);
        Object object3 = object2[0];
        object = object2[1];
        Object object4 = object2[2];
        object2 = object2[3];
        object3 = this.minMaxIPs((byte[])object3, (byte[])object, (byte[])object4, (byte[])object2);
        object4 = PKIXNameConstraintValidator.min((byte[])object3[1], (byte[])object3[3]);
        if (PKIXNameConstraintValidator.compareTo(PKIXNameConstraintValidator.max((byte[])object3[0], (byte[])object3[2]), (byte[])object4) == 1) {
            return Collections.EMPTY_SET;
        }
        return Collections.singleton(this.ipWithSubnetMask(PKIXNameConstraintValidator.or((byte[])object3[0], (byte[])object3[2]), PKIXNameConstraintValidator.or(object, object2)));
    }

    private Set intersectOtherName(Set hashSet, Set set) {
        hashSet = new HashSet(hashSet);
        hashSet.retainAll(set);
        return hashSet;
    }

    private Set intersectURI(Set set, Set object) {
        HashSet<String> hashSet = new HashSet<String>();
        Iterator iterator = object.iterator();
        while (iterator.hasNext()) {
            String string = this.extractNameAsString(((GeneralSubtree)iterator.next()).getBase());
            if (set == null) {
                if (string == null) continue;
                hashSet.add(string);
                continue;
            }
            object = set.iterator();
            while (object.hasNext()) {
                this.intersectURI((String)object.next(), string, hashSet);
            }
        }
        return hashSet;
    }

    private void intersectURI(String string, String string2, Set set) {
        if (string.indexOf(64) != -1) {
            String string3 = string.substring(string.indexOf(64) + 1);
            if (string2.indexOf(64) != -1) {
                if (string.equalsIgnoreCase(string2)) {
                    set.add(string);
                }
            } else if (string2.startsWith(".")) {
                if (this.withinDomain(string3, string2)) {
                    set.add(string);
                }
            } else if (string3.equalsIgnoreCase(string2)) {
                set.add(string);
            }
        } else if (string.startsWith(".")) {
            if (string2.indexOf(64) != -1) {
                if (this.withinDomain(string2.substring(string.indexOf(64) + 1), string)) {
                    set.add(string2);
                }
            } else if (string2.startsWith(".")) {
                if (!this.withinDomain(string, string2) && !string.equalsIgnoreCase(string2)) {
                    if (this.withinDomain(string2, string)) {
                        set.add(string2);
                    }
                } else {
                    set.add(string);
                }
            } else if (this.withinDomain(string2, string)) {
                set.add(string2);
            }
        } else if (string2.indexOf(64) != -1) {
            if (string2.substring(string2.indexOf(64) + 1).equalsIgnoreCase(string)) {
                set.add(string2);
            }
        } else if (string2.startsWith(".")) {
            if (this.withinDomain(string, string2)) {
                set.add(string);
            }
        } else if (string.equalsIgnoreCase(string2)) {
            set.add(string);
        }
    }

    private byte[] ipWithSubnetMask(byte[] arrby, byte[] arrby2) {
        int n = arrby.length;
        byte[] arrby3 = new byte[n * 2];
        System.arraycopy((byte[])arrby, (int)0, (byte[])arrby3, (int)0, (int)n);
        System.arraycopy((byte[])arrby2, (int)0, (byte[])arrby3, (int)n, (int)n);
        return arrby3;
    }

    private boolean isIPConstrained(byte[] arrby, byte[] arrby2) {
        int n = arrby.length;
        if (n != arrby2.length / 2) {
            return false;
        }
        byte[] arrby3 = new byte[n];
        System.arraycopy((byte[])arrby2, (int)n, (byte[])arrby3, (int)0, (int)n);
        byte[] arrby4 = new byte[n];
        byte[] arrby5 = new byte[n];
        for (int i = 0; i < n; ++i) {
            arrby4[i] = (byte)(arrby2[i] & arrby3[i]);
            arrby5[i] = (byte)(arrby[i] & arrby3[i]);
        }
        return Arrays.areEqual(arrby4, arrby5);
    }

    private boolean isUriConstrained(String string, String string2) {
        string = PKIXNameConstraintValidator.extractHostFromURL(string);
        return !string2.startsWith(".") ? string.equalsIgnoreCase(string2) : this.withinDomain(string, string2);
    }

    private static byte[] max(byte[] arrby, byte[] arrby2) {
        for (int i = 0; i < arrby.length; ++i) {
            if ((arrby[i] & 65535) <= (65535 & arrby2[i])) continue;
            return arrby;
        }
        return arrby2;
    }

    private static byte[] min(byte[] arrby, byte[] arrby2) {
        for (int i = 0; i < arrby.length; ++i) {
            if ((arrby[i] & 65535) >= (65535 & arrby2[i])) continue;
            return arrby;
        }
        return arrby2;
    }

    private byte[][] minMaxIPs(byte[] arrby, byte[] arrby2, byte[] arrby3, byte[] arrby4) {
        int n = arrby.length;
        byte[] arrby5 = new byte[n];
        byte[] arrby6 = new byte[n];
        byte[] arrby7 = new byte[n];
        byte[] arrby8 = new byte[n];
        for (int i = 0; i < n; ++i) {
            arrby5[i] = (byte)(arrby[i] & arrby2[i]);
            arrby6[i] = (byte)(arrby[i] & arrby2[i] | arrby2[i]);
            arrby7[i] = (byte)(arrby3[i] & arrby4[i]);
            arrby8[i] = (byte)(arrby3[i] & arrby4[i] | arrby4[i]);
        }
        return new byte[][]{arrby5, arrby6, arrby7, arrby8};
    }

    private static byte[] or(byte[] arrby, byte[] arrby2) {
        byte[] arrby3 = new byte[arrby.length];
        for (int i = 0; i < arrby.length; ++i) {
            arrby3[i] = (byte)(arrby[i] | arrby2[i]);
        }
        return arrby3;
    }

    private boolean otherNameIsConstrained(OtherName otherName, OtherName otherName2) {
        return otherName2.equals(otherName);
    }

    private String stringifyIP(byte[] arrby) {
        int n;
        StringBuilder stringBuilder = new StringBuilder();
        for (n = 0; n < arrby.length / 2; ++n) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append(".");
            }
            stringBuilder.append(Integer.toString(arrby[n] & 255));
        }
        stringBuilder.append("/");
        boolean bl = true;
        for (n = arrby.length / 2; n < arrby.length; ++n) {
            if (bl) {
                bl = false;
            } else {
                stringBuilder.append(".");
            }
            stringBuilder.append(Integer.toString(arrby[n] & 255));
        }
        return stringBuilder.toString();
    }

    private String stringifyIPCollection(Set object) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        object = object.iterator();
        while (object.hasNext()) {
            if (stringBuilder.length() > 1) {
                stringBuilder.append(",");
            }
            stringBuilder.append(this.stringifyIP((byte[])object.next()));
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    private String stringifyOtherNameCollection(Set object) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        object = object.iterator();
        while (object.hasNext()) {
            if (stringBuilder.length() > 1) {
                stringBuilder.append(",");
            }
            OtherName otherName = OtherName.getInstance(object.next());
            stringBuilder.append(otherName.getTypeID().getId());
            stringBuilder.append(":");
            try {
                stringBuilder.append(Hex.toHexString(otherName.getValue().toASN1Primitive().getEncoded()));
            }
            catch (IOException iOException) {
                stringBuilder.append(iOException.toString());
            }
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    private Set unionDN(Set object, ASN1Sequence aSN1Sequence) {
        if (object.isEmpty()) {
            if (aSN1Sequence == null) {
                return object;
            }
            object.add(aSN1Sequence);
            return object;
        }
        HashSet<ASN1Sequence> hashSet = new HashSet<ASN1Sequence>();
        object = object.iterator();
        while (object.hasNext()) {
            ASN1Sequence aSN1Sequence2 = (ASN1Sequence)object.next();
            if (PKIXNameConstraintValidator.withinDNSubtree(aSN1Sequence, aSN1Sequence2)) {
                hashSet.add(aSN1Sequence2);
                continue;
            }
            if (PKIXNameConstraintValidator.withinDNSubtree(aSN1Sequence2, aSN1Sequence)) {
                hashSet.add(aSN1Sequence);
                continue;
            }
            hashSet.add(aSN1Sequence2);
            hashSet.add(aSN1Sequence);
        }
        return hashSet;
    }

    private Set unionDNS(Set object, String string) {
        if (object.isEmpty()) {
            if (string == null) {
                return object;
            }
            object.add(string);
            return object;
        }
        HashSet<Object> hashSet = new HashSet<Object>();
        Iterator iterator = object.iterator();
        while (iterator.hasNext()) {
            object = (String)iterator.next();
            if (this.withinDomain((String)object, string)) {
                hashSet.add(string);
                continue;
            }
            if (this.withinDomain(string, (String)object)) {
                hashSet.add(object);
                continue;
            }
            hashSet.add(object);
            hashSet.add(string);
        }
        return hashSet;
    }

    private Set unionEmail(Set object, String string) {
        if (object.isEmpty()) {
            if (string == null) {
                return object;
            }
            object.add(string);
            return object;
        }
        HashSet hashSet = new HashSet();
        object = object.iterator();
        while (object.hasNext()) {
            this.unionEmail((String)object.next(), string, hashSet);
        }
        return hashSet;
    }

    private void unionEmail(String string, String string2, Set set) {
        if (string.indexOf(64) != -1) {
            String string3 = string.substring(string.indexOf(64) + 1);
            if (string2.indexOf(64) != -1) {
                if (string.equalsIgnoreCase(string2)) {
                    set.add(string);
                } else {
                    set.add(string);
                    set.add(string2);
                }
            } else if (string2.startsWith(".")) {
                if (this.withinDomain(string3, string2)) {
                    set.add(string2);
                } else {
                    set.add(string);
                    set.add(string2);
                }
            } else if (string3.equalsIgnoreCase(string2)) {
                set.add(string2);
            } else {
                set.add(string);
                set.add(string2);
            }
        } else if (string.startsWith(".")) {
            if (string2.indexOf(64) != -1) {
                if (this.withinDomain(string2.substring(string.indexOf(64) + 1), string)) {
                    set.add(string);
                } else {
                    set.add(string);
                    set.add(string2);
                }
            } else if (string2.startsWith(".")) {
                if (!this.withinDomain(string, string2) && !string.equalsIgnoreCase(string2)) {
                    if (this.withinDomain(string2, string)) {
                        set.add(string);
                    } else {
                        set.add(string);
                        set.add(string2);
                    }
                } else {
                    set.add(string2);
                }
            } else if (this.withinDomain(string2, string)) {
                set.add(string);
            } else {
                set.add(string);
                set.add(string2);
            }
        } else if (string2.indexOf(64) != -1) {
            if (string2.substring(string.indexOf(64) + 1).equalsIgnoreCase(string)) {
                set.add(string);
            } else {
                set.add(string);
                set.add(string2);
            }
        } else if (string2.startsWith(".")) {
            if (this.withinDomain(string, string2)) {
                set.add(string2);
            } else {
                set.add(string);
                set.add(string2);
            }
        } else if (string.equalsIgnoreCase(string2)) {
            set.add(string);
        } else {
            set.add(string);
            set.add(string2);
        }
    }

    private Set unionIP(Set object, byte[] arrby) {
        if (object.isEmpty()) {
            if (arrby == null) {
                return object;
            }
            object.add(arrby);
            return object;
        }
        HashSet hashSet = new HashSet();
        object = object.iterator();
        while (object.hasNext()) {
            hashSet.addAll(this.unionIPRange((byte[])object.next(), arrby));
        }
        return hashSet;
    }

    private Set unionIPRange(byte[] arrby, byte[] arrby2) {
        HashSet<byte[]> hashSet = new HashSet<byte[]>();
        if (Arrays.areEqual(arrby, arrby2)) {
            hashSet.add(arrby);
        } else {
            hashSet.add(arrby);
            hashSet.add(arrby2);
        }
        return hashSet;
    }

    private Set unionOtherName(Set hashSet, OtherName otherName) {
        hashSet = new HashSet<OtherName>(hashSet);
        hashSet.add(otherName);
        return hashSet;
    }

    private Set unionURI(Set object, String string) {
        if (object.isEmpty()) {
            if (string == null) {
                return object;
            }
            object.add(string);
            return object;
        }
        HashSet hashSet = new HashSet();
        object = object.iterator();
        while (object.hasNext()) {
            this.unionURI((String)object.next(), string, hashSet);
        }
        return hashSet;
    }

    private void unionURI(String string, String string2, Set set) {
        if (string.indexOf(64) != -1) {
            String string3 = string.substring(string.indexOf(64) + 1);
            if (string2.indexOf(64) != -1) {
                if (string.equalsIgnoreCase(string2)) {
                    set.add(string);
                } else {
                    set.add(string);
                    set.add(string2);
                }
            } else if (string2.startsWith(".")) {
                if (this.withinDomain(string3, string2)) {
                    set.add(string2);
                } else {
                    set.add(string);
                    set.add(string2);
                }
            } else if (string3.equalsIgnoreCase(string2)) {
                set.add(string2);
            } else {
                set.add(string);
                set.add(string2);
            }
        } else if (string.startsWith(".")) {
            if (string2.indexOf(64) != -1) {
                if (this.withinDomain(string2.substring(string.indexOf(64) + 1), string)) {
                    set.add(string);
                } else {
                    set.add(string);
                    set.add(string2);
                }
            } else if (string2.startsWith(".")) {
                if (!this.withinDomain(string, string2) && !string.equalsIgnoreCase(string2)) {
                    if (this.withinDomain(string2, string)) {
                        set.add(string);
                    } else {
                        set.add(string);
                        set.add(string2);
                    }
                } else {
                    set.add(string2);
                }
            } else if (this.withinDomain(string2, string)) {
                set.add(string);
            } else {
                set.add(string);
                set.add(string2);
            }
        } else if (string2.indexOf(64) != -1) {
            if (string2.substring(string.indexOf(64) + 1).equalsIgnoreCase(string)) {
                set.add(string);
            } else {
                set.add(string);
                set.add(string2);
            }
        } else if (string2.startsWith(".")) {
            if (this.withinDomain(string, string2)) {
                set.add(string2);
            } else {
                set.add(string);
                set.add(string2);
            }
        } else if (string.equalsIgnoreCase(string2)) {
            set.add(string);
        } else {
            set.add(string);
            set.add(string2);
        }
    }

    private static boolean withinDNSubtree(ASN1Sequence aSN1Sequence, ASN1Sequence aSN1Sequence2) {
        if (aSN1Sequence2.size() < 1) {
            return false;
        }
        if (aSN1Sequence2.size() > aSN1Sequence.size()) {
            return false;
        }
        for (int i = aSN1Sequence2.size() - 1; i >= 0; --i) {
            if (aSN1Sequence2.getObjectAt(i).equals(aSN1Sequence.getObjectAt(i))) continue;
            return false;
        }
        return true;
    }

    private boolean withinDomain(String arrstring, String arrstring2) {
        String[] arrstring3;
        arrstring2 = arrstring3 = arrstring2;
        if (arrstring3.startsWith(".")) {
            arrstring2 = arrstring3.substring(1);
        }
        arrstring2 = Strings.split((String)arrstring2, '.');
        if ((arrstring = Strings.split((String)arrstring, '.')).length <= arrstring2.length) {
            return false;
        }
        int n = arrstring.length - arrstring2.length;
        for (int i = -1; i < arrstring2.length; ++i) {
            if (!(i == -1 ? arrstring[i + n].equals("") : !arrstring2[i].equalsIgnoreCase(arrstring[i + n]))) continue;
            return false;
        }
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void addExcludedSubtree(GeneralSubtree aSN1Object) {
        int n = ((GeneralName)(aSN1Object = ((GeneralSubtree)aSN1Object).getBase())).getTagNo();
        if (n == 0) {
            this.excludedSubtreesOtherName = this.unionOtherName(this.excludedSubtreesOtherName, OtherName.getInstance(((GeneralName)aSN1Object).getName()));
            return;
        }
        if (n == 1) {
            this.excludedSubtreesEmail = this.unionEmail(this.excludedSubtreesEmail, this.extractNameAsString((GeneralName)aSN1Object));
            return;
        }
        if (n == 2) {
            this.excludedSubtreesDNS = this.unionDNS(this.excludedSubtreesDNS, this.extractNameAsString((GeneralName)aSN1Object));
            return;
        }
        if (n == 4) {
            this.excludedSubtreesDN = this.unionDN(this.excludedSubtreesDN, (ASN1Sequence)((GeneralName)aSN1Object).getName().toASN1Primitive());
            return;
        }
        if (n == 6) {
            this.excludedSubtreesURI = this.unionURI(this.excludedSubtreesURI, this.extractNameAsString((GeneralName)aSN1Object));
            return;
        }
        if (n == 7) {
            this.excludedSubtreesIP = this.unionIP(this.excludedSubtreesIP, ASN1OctetString.getInstance(((GeneralName)aSN1Object).getName()).getOctets());
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unknown tag encountered: ");
        stringBuilder.append(((GeneralName)aSN1Object).getTagNo());
        throw new IllegalStateException(stringBuilder.toString());
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void checkExcluded(GeneralName arrby) throws NameConstraintValidatorException {
        int n = arrby.getTagNo();
        if (n == 0) {
            this.checkExcludedOtherName(this.excludedSubtreesOtherName, OtherName.getInstance(arrby.getName()));
            return;
        }
        if (n == 1) {
            this.checkExcludedEmail(this.excludedSubtreesEmail, this.extractNameAsString((GeneralName)arrby));
            return;
        }
        if (n == 2) {
            this.checkExcludedDNS(this.excludedSubtreesDNS, DERIA5String.getInstance(arrby.getName()).getString());
            return;
        }
        if (n == 4) {
            this.checkExcludedDN(X500Name.getInstance(arrby.getName()));
            return;
        }
        if (n == 6) {
            this.checkExcludedURI(this.excludedSubtreesURI, DERIA5String.getInstance(arrby.getName()).getString());
            return;
        }
        if (n == 7) {
            arrby = ASN1OctetString.getInstance(arrby.getName()).getOctets();
            this.checkExcludedIP(this.excludedSubtreesIP, arrby);
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unknown tag encountered: ");
        stringBuilder.append(arrby.getTagNo());
        throw new IllegalStateException(stringBuilder.toString());
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void checkPermitted(GeneralName arrby) throws NameConstraintValidatorException {
        int n = arrby.getTagNo();
        if (n == 0) {
            this.checkPermittedOtherName(this.permittedSubtreesOtherName, OtherName.getInstance(arrby.getName()));
            return;
        }
        if (n == 1) {
            this.checkPermittedEmail(this.permittedSubtreesEmail, this.extractNameAsString((GeneralName)arrby));
            return;
        }
        if (n == 2) {
            this.checkPermittedDNS(this.permittedSubtreesDNS, DERIA5String.getInstance(arrby.getName()).getString());
            return;
        }
        if (n == 4) {
            this.checkPermittedDN(X500Name.getInstance(arrby.getName()));
            return;
        }
        if (n == 6) {
            this.checkPermittedURI(this.permittedSubtreesURI, DERIA5String.getInstance(arrby.getName()).getString());
            return;
        }
        if (n == 7) {
            arrby = ASN1OctetString.getInstance(arrby.getName()).getOctets();
            this.checkPermittedIP(this.permittedSubtreesIP, arrby);
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unknown tag encountered: ");
        stringBuilder.append(arrby.getTagNo());
        throw new IllegalStateException(stringBuilder.toString());
    }

    public boolean equals(Object object) {
        boolean bl;
        block1 : {
            boolean bl2 = object instanceof PKIXNameConstraintValidator;
            bl = false;
            if (!bl2) {
                return false;
            }
            object = (PKIXNameConstraintValidator)object;
            if (!this.collectionsAreEqual(((PKIXNameConstraintValidator)object).excludedSubtreesDN, this.excludedSubtreesDN) || !this.collectionsAreEqual(((PKIXNameConstraintValidator)object).excludedSubtreesDNS, this.excludedSubtreesDNS) || !this.collectionsAreEqual(((PKIXNameConstraintValidator)object).excludedSubtreesEmail, this.excludedSubtreesEmail) || !this.collectionsAreEqual(((PKIXNameConstraintValidator)object).excludedSubtreesIP, this.excludedSubtreesIP) || !this.collectionsAreEqual(((PKIXNameConstraintValidator)object).excludedSubtreesURI, this.excludedSubtreesURI) || !this.collectionsAreEqual(((PKIXNameConstraintValidator)object).excludedSubtreesOtherName, this.excludedSubtreesOtherName) || !this.collectionsAreEqual(((PKIXNameConstraintValidator)object).permittedSubtreesDN, this.permittedSubtreesDN) || !this.collectionsAreEqual(((PKIXNameConstraintValidator)object).permittedSubtreesDNS, this.permittedSubtreesDNS) || !this.collectionsAreEqual(((PKIXNameConstraintValidator)object).permittedSubtreesEmail, this.permittedSubtreesEmail) || !this.collectionsAreEqual(((PKIXNameConstraintValidator)object).permittedSubtreesIP, this.permittedSubtreesIP) || !this.collectionsAreEqual(((PKIXNameConstraintValidator)object).permittedSubtreesURI, this.permittedSubtreesURI) || !this.collectionsAreEqual(((PKIXNameConstraintValidator)object).permittedSubtreesOtherName, this.permittedSubtreesOtherName)) break block1;
            bl = true;
        }
        return bl;
    }

    public int hashCode() {
        return this.hashCollection(this.excludedSubtreesDN) + this.hashCollection(this.excludedSubtreesDNS) + this.hashCollection(this.excludedSubtreesEmail) + this.hashCollection(this.excludedSubtreesIP) + this.hashCollection(this.excludedSubtreesURI) + this.hashCollection(this.excludedSubtreesOtherName) + this.hashCollection(this.permittedSubtreesDN) + this.hashCollection(this.permittedSubtreesDNS) + this.hashCollection(this.permittedSubtreesEmail) + this.hashCollection(this.permittedSubtreesIP) + this.hashCollection(this.permittedSubtreesURI) + this.hashCollection(this.permittedSubtreesOtherName);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void intersectEmptyPermittedSubtree(int n) {
        if (n == 0) {
            this.permittedSubtreesOtherName = new HashSet();
            return;
        }
        if (n == 1) {
            this.permittedSubtreesEmail = new HashSet();
            return;
        }
        if (n == 2) {
            this.permittedSubtreesDNS = new HashSet();
            return;
        }
        if (n == 4) {
            this.permittedSubtreesDN = new HashSet();
            return;
        }
        if (n == 6) {
            this.permittedSubtreesURI = new HashSet();
            return;
        }
        if (n == 7) {
            this.permittedSubtreesIP = new HashSet();
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unknown tag encountered: ");
        stringBuilder.append(n);
        throw new IllegalStateException(stringBuilder.toString());
    }

    @Override
    public void intersectPermittedSubtree(GeneralSubtree generalSubtree) {
        this.intersectPermittedSubtree(new GeneralSubtree[]{generalSubtree});
    }

    @Override
    public void intersectPermittedSubtree(GeneralSubtree[] object2) {
        int n;
        HashMap hashMap = new HashMap();
        for (n = 0; n != ((Object)object2).length; ++n) {
            Iterator iterator = object2[n];
            Integer n2 = Integers.valueOf(((GeneralSubtree)((Object)iterator)).getBase().getTagNo());
            if (hashMap.get(n2) == null) {
                hashMap.put(n2, new HashSet());
            }
            ((Set)hashMap.get(n2)).add(iterator);
        }
        for (Map.Entry entry : hashMap.entrySet()) {
            n = (Integer)entry.getKey();
            if (n != 0) {
                if (n != 1) {
                    if (n != 2) {
                        if (n != 4) {
                            if (n != 6) {
                                if (n == 7) {
                                    this.permittedSubtreesIP = this.intersectIP(this.permittedSubtreesIP, (Set)entry.getValue());
                                    continue;
                                }
                                StringBuilder stringBuilder = new StringBuilder();
                                stringBuilder.append("Unknown tag encountered: ");
                                stringBuilder.append(n);
                                throw new IllegalStateException(stringBuilder.toString());
                            }
                            this.permittedSubtreesURI = this.intersectURI(this.permittedSubtreesURI, (Set)entry.getValue());
                            continue;
                        }
                        this.permittedSubtreesDN = this.intersectDN(this.permittedSubtreesDN, (Set)entry.getValue());
                        continue;
                    }
                    this.permittedSubtreesDNS = this.intersectDNS(this.permittedSubtreesDNS, (Set)entry.getValue());
                    continue;
                }
                this.permittedSubtreesEmail = this.intersectEmail(this.permittedSubtreesEmail, (Set)entry.getValue());
                continue;
            }
            this.permittedSubtreesOtherName = this.intersectOtherName(this.permittedSubtreesOtherName, (Set)entry.getValue());
        }
    }

    public String toString() {
        CharSequence charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("");
        ((StringBuilder)charSequence).append("permitted:\n");
        CharSequence charSequence2 = ((StringBuilder)charSequence).toString();
        charSequence = charSequence2;
        if (this.permittedSubtreesDN != null) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append((String)charSequence2);
            ((StringBuilder)charSequence).append("DN:\n");
            charSequence = ((StringBuilder)charSequence).toString();
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append((String)charSequence);
            ((StringBuilder)charSequence2).append(this.permittedSubtreesDN.toString());
            ((StringBuilder)charSequence2).append("\n");
            charSequence = ((StringBuilder)charSequence2).toString();
        }
        CharSequence charSequence3 = charSequence;
        if (this.permittedSubtreesDNS != null) {
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append((String)charSequence);
            ((StringBuilder)charSequence2).append("DNS:\n");
            charSequence = ((StringBuilder)charSequence2).toString();
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append((String)charSequence);
            ((StringBuilder)charSequence2).append(this.permittedSubtreesDNS.toString());
            ((StringBuilder)charSequence2).append("\n");
            charSequence3 = ((StringBuilder)charSequence2).toString();
        }
        charSequence2 = charSequence3;
        if (this.permittedSubtreesEmail != null) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append((String)charSequence3);
            ((StringBuilder)charSequence).append("Email:\n");
            charSequence2 = ((StringBuilder)charSequence).toString();
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append((String)charSequence2);
            ((StringBuilder)charSequence).append(this.permittedSubtreesEmail.toString());
            ((StringBuilder)charSequence).append("\n");
            charSequence2 = ((StringBuilder)charSequence).toString();
        }
        charSequence = charSequence2;
        if (this.permittedSubtreesURI != null) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append((String)charSequence2);
            ((StringBuilder)charSequence).append("URI:\n");
            charSequence = ((StringBuilder)charSequence).toString();
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append((String)charSequence);
            ((StringBuilder)charSequence2).append(this.permittedSubtreesURI.toString());
            ((StringBuilder)charSequence2).append("\n");
            charSequence = ((StringBuilder)charSequence2).toString();
        }
        charSequence2 = charSequence;
        if (this.permittedSubtreesIP != null) {
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append((String)charSequence);
            ((StringBuilder)charSequence2).append("IP:\n");
            charSequence2 = ((StringBuilder)charSequence2).toString();
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append((String)charSequence2);
            ((StringBuilder)charSequence).append(this.stringifyIPCollection(this.permittedSubtreesIP));
            ((StringBuilder)charSequence).append("\n");
            charSequence2 = ((StringBuilder)charSequence).toString();
        }
        charSequence = charSequence2;
        if (this.permittedSubtreesOtherName != null) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append((String)charSequence2);
            ((StringBuilder)charSequence).append("OtherName:\n");
            charSequence = ((StringBuilder)charSequence).toString();
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append((String)charSequence);
            ((StringBuilder)charSequence2).append(this.stringifyOtherNameCollection(this.permittedSubtreesOtherName));
            ((StringBuilder)charSequence2).append("\n");
            charSequence = ((StringBuilder)charSequence2).toString();
        }
        charSequence2 = new StringBuilder();
        ((StringBuilder)charSequence2).append((String)charSequence);
        ((StringBuilder)charSequence2).append("excluded:\n");
        charSequence2 = ((StringBuilder)charSequence2).toString();
        charSequence = charSequence2;
        if (!this.excludedSubtreesDN.isEmpty()) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append((String)charSequence2);
            ((StringBuilder)charSequence).append("DN:\n");
            charSequence2 = ((StringBuilder)charSequence).toString();
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append((String)charSequence2);
            ((StringBuilder)charSequence).append(this.excludedSubtreesDN.toString());
            ((StringBuilder)charSequence).append("\n");
            charSequence = ((StringBuilder)charSequence).toString();
        }
        charSequence2 = charSequence;
        if (!this.excludedSubtreesDNS.isEmpty()) {
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append((String)charSequence);
            ((StringBuilder)charSequence2).append("DNS:\n");
            charSequence = ((StringBuilder)charSequence2).toString();
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append((String)charSequence);
            ((StringBuilder)charSequence2).append(this.excludedSubtreesDNS.toString());
            ((StringBuilder)charSequence2).append("\n");
            charSequence2 = ((StringBuilder)charSequence2).toString();
        }
        charSequence = charSequence2;
        if (!this.excludedSubtreesEmail.isEmpty()) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append((String)charSequence2);
            ((StringBuilder)charSequence).append("Email:\n");
            charSequence2 = ((StringBuilder)charSequence).toString();
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append((String)charSequence2);
            ((StringBuilder)charSequence).append(this.excludedSubtreesEmail.toString());
            ((StringBuilder)charSequence).append("\n");
            charSequence = ((StringBuilder)charSequence).toString();
        }
        charSequence2 = charSequence;
        if (!this.excludedSubtreesURI.isEmpty()) {
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append((String)charSequence);
            ((StringBuilder)charSequence2).append("URI:\n");
            charSequence2 = ((StringBuilder)charSequence2).toString();
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append((String)charSequence2);
            ((StringBuilder)charSequence).append(this.excludedSubtreesURI.toString());
            ((StringBuilder)charSequence).append("\n");
            charSequence2 = ((StringBuilder)charSequence).toString();
        }
        charSequence = charSequence2;
        if (!this.excludedSubtreesIP.isEmpty()) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append((String)charSequence2);
            ((StringBuilder)charSequence).append("IP:\n");
            charSequence = ((StringBuilder)charSequence).toString();
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append((String)charSequence);
            ((StringBuilder)charSequence2).append(this.stringifyIPCollection(this.excludedSubtreesIP));
            ((StringBuilder)charSequence2).append("\n");
            charSequence = ((StringBuilder)charSequence2).toString();
        }
        charSequence2 = charSequence;
        if (!this.excludedSubtreesOtherName.isEmpty()) {
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append((String)charSequence);
            ((StringBuilder)charSequence2).append("OtherName:\n");
            charSequence = ((StringBuilder)charSequence2).toString();
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append((String)charSequence);
            ((StringBuilder)charSequence2).append(this.stringifyOtherNameCollection(this.excludedSubtreesOtherName));
            ((StringBuilder)charSequence2).append("\n");
            charSequence2 = ((StringBuilder)charSequence2).toString();
        }
        return charSequence2;
    }
}

