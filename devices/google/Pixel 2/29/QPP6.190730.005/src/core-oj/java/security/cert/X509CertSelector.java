/*
 * Decompiled with CFR 0.145.
 */
package java.security.cert;

import java.io.IOException;
import java.math.BigInteger;
import java.security.Principal;
import java.security.PublicKey;
import java.security.cert.CertPathHelperImpl;
import java.security.cert.CertSelector;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.Extension;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import javax.security.auth.x500.X500Principal;
import sun.misc.CharacterEncoder;
import sun.misc.HexDumpEncoder;
import sun.security.util.Debug;
import sun.security.util.DerInputStream;
import sun.security.util.DerValue;
import sun.security.util.ObjectIdentifier;
import sun.security.x509.AlgorithmId;
import sun.security.x509.CertificatePoliciesExtension;
import sun.security.x509.CertificatePolicyId;
import sun.security.x509.CertificatePolicySet;
import sun.security.x509.DNSName;
import sun.security.x509.EDIPartyName;
import sun.security.x509.ExtendedKeyUsageExtension;
import sun.security.x509.GeneralName;
import sun.security.x509.GeneralNameInterface;
import sun.security.x509.GeneralNames;
import sun.security.x509.GeneralSubtree;
import sun.security.x509.GeneralSubtrees;
import sun.security.x509.IPAddressName;
import sun.security.x509.NameConstraintsExtension;
import sun.security.x509.OIDName;
import sun.security.x509.OtherName;
import sun.security.x509.PolicyInformation;
import sun.security.x509.PrivateKeyUsageExtension;
import sun.security.x509.RFC822Name;
import sun.security.x509.SubjectAlternativeNameExtension;
import sun.security.x509.URIName;
import sun.security.x509.X400Address;
import sun.security.x509.X500Name;
import sun.security.x509.X509CertImpl;
import sun.security.x509.X509Key;

public class X509CertSelector
implements CertSelector {
    private static final ObjectIdentifier ANY_EXTENDED_KEY_USAGE;
    private static final int CERT_POLICIES_ID = 3;
    private static final int EXTENDED_KEY_USAGE_ID = 4;
    private static final String[] EXTENSION_OIDS;
    private static final Boolean FALSE;
    static final int NAME_ANY = 0;
    private static final int NAME_CONSTRAINTS_ID = 2;
    static final int NAME_DIRECTORY = 4;
    static final int NAME_DNS = 2;
    static final int NAME_EDI = 5;
    static final int NAME_IP = 7;
    static final int NAME_OID = 8;
    static final int NAME_RFC822 = 1;
    static final int NAME_URI = 6;
    static final int NAME_X400 = 3;
    private static final int NUM_OF_EXTENSIONS = 5;
    private static final int PRIVATE_KEY_USAGE_ID = 0;
    private static final int SUBJECT_ALT_NAME_ID = 1;
    private static final Debug debug;
    private byte[] authorityKeyID;
    private int basicConstraints = -1;
    private Date certificateValid;
    private X500Principal issuer;
    private Set<ObjectIdentifier> keyPurposeOIDSet;
    private Set<String> keyPurposeSet;
    private boolean[] keyUsage;
    private boolean matchAllSubjectAltNames = true;
    private NameConstraintsExtension nc;
    private byte[] ncBytes;
    private Set<GeneralNameInterface> pathToGeneralNames;
    private Set<List<?>> pathToNames;
    private CertificatePolicySet policy;
    private Set<String> policySet;
    private Date privateKeyValid;
    private BigInteger serialNumber;
    private X500Principal subject;
    private Set<GeneralNameInterface> subjectAlternativeGeneralNames;
    private Set<List<?>> subjectAlternativeNames;
    private byte[] subjectKeyID;
    private PublicKey subjectPublicKey;
    private ObjectIdentifier subjectPublicKeyAlgID;
    private byte[] subjectPublicKeyBytes;
    private X509Certificate x509Cert;

    static {
        debug = Debug.getInstance("certpath");
        ANY_EXTENDED_KEY_USAGE = ObjectIdentifier.newInternal(new int[]{2, 5, 29, 37, 0});
        CertPathHelperImpl.initialize();
        FALSE = Boolean.FALSE;
        String[] arrstring = EXTENSION_OIDS = new String[5];
        arrstring[0] = "2.5.29.16";
        arrstring[1] = "2.5.29.17";
        arrstring[2] = "2.5.29.30";
        arrstring[3] = "2.5.29.32";
        arrstring[4] = "2.5.29.37";
    }

    private void addPathToNameInternal(int n, Object object) throws IOException {
        GeneralNameInterface generalNameInterface = X509CertSelector.makeGeneralNameInterface(n, object);
        if (this.pathToGeneralNames == null) {
            this.pathToNames = new HashSet();
            this.pathToGeneralNames = new HashSet<GeneralNameInterface>();
        }
        ArrayList<Object> arrayList = new ArrayList<Object>(2);
        arrayList.add(n);
        arrayList.add(object);
        this.pathToNames.add(arrayList);
        this.pathToGeneralNames.add(generalNameInterface);
    }

    private void addSubjectAlternativeNameInternal(int n, Object object) throws IOException {
        GeneralNameInterface generalNameInterface = X509CertSelector.makeGeneralNameInterface(n, object);
        if (this.subjectAlternativeNames == null) {
            this.subjectAlternativeNames = new HashSet();
        }
        if (this.subjectAlternativeGeneralNames == null) {
            this.subjectAlternativeGeneralNames = new HashSet<GeneralNameInterface>();
        }
        ArrayList<Object> arrayList = new ArrayList<Object>(2);
        arrayList.add(n);
        arrayList.add(object);
        this.subjectAlternativeNames.add(arrayList);
        this.subjectAlternativeGeneralNames.add(generalNameInterface);
    }

    private static Set<List<?>> cloneAndCheckNames(Collection<List<?>> object) throws IOException {
        HashSet hashSet = new HashSet();
        object = object.iterator();
        while (object.hasNext()) {
            hashSet.add(new ArrayList((List)object.next()));
        }
        object = hashSet.iterator();
        while (object.hasNext()) {
            List list = (List)object.next();
            if (list.size() == 2) {
                Object e = list.get(0);
                if (e instanceof Integer) {
                    int n = (Integer)e;
                    if (n >= 0 && n <= 8) {
                        e = list.get(1);
                        if (!(e instanceof byte[]) && !(e instanceof String)) {
                            object = debug;
                            if (object != null) {
                                ((Debug)object).println("X509CertSelector.cloneAndCheckNames() name not byte array");
                            }
                            throw new IOException("name not byte array or String");
                        }
                        if (!(e instanceof byte[])) continue;
                        list.set(1, ((byte[])e).clone());
                        continue;
                    }
                    throw new IOException("name type not 0-8");
                }
                throw new IOException("expected an Integer");
            }
            throw new IOException("name list size not 2");
        }
        return hashSet;
    }

    private static Set<List<?>> cloneNames(Collection<List<?>> object) {
        try {
            object = X509CertSelector.cloneAndCheckNames(object);
            return object;
        }
        catch (IOException iOException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("cloneNames encountered IOException: ");
            ((StringBuilder)object).append(iOException.getMessage());
            throw new RuntimeException(((StringBuilder)object).toString());
        }
    }

    private static <T> Set<T> cloneSet(Set<T> set) {
        if (set instanceof HashSet) {
            return (Set)((HashSet)set).clone();
        }
        return new HashSet<T>(set);
    }

    static boolean equalNames(Collection<?> collection, Collection<?> collection2) {
        if (collection != null && collection2 != null) {
            return collection.equals(collection2);
        }
        boolean bl = collection == collection2;
        return bl;
    }

    private static Extension getExtensionObject(X509Certificate object, int n) throws IOException {
        if (object instanceof X509CertImpl) {
            object = (X509CertImpl)object;
            if (n != 0) {
                if (n != 1) {
                    if (n != 2) {
                        if (n != 3) {
                            if (n != 4) {
                                return null;
                            }
                            return object.getExtendedKeyUsageExtension();
                        }
                        return object.getCertificatePoliciesExtension();
                    }
                    return object.getNameConstraintsExtension();
                }
                return object.getSubjectAlternativeNameExtension();
            }
            return object.getPrivateKeyUsageExtension();
        }
        if ((object = object.getExtensionValue(EXTENSION_OIDS[n])) == null) {
            return null;
        }
        object = new DerInputStream((byte[])object).getOctetString();
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            return null;
                        }
                        return new ExtendedKeyUsageExtension(FALSE, object);
                    }
                    return new CertificatePoliciesExtension(FALSE, object);
                }
                return new NameConstraintsExtension(FALSE, object);
            }
            return new SubjectAlternativeNameExtension(FALSE, object);
        }
        try {
            object = new PrivateKeyUsageExtension(FALSE, object);
            return object;
        }
        catch (CertificateException certificateException) {
            throw new IOException(certificateException.getMessage());
        }
    }

    private static String keyUsageToString(boolean[] object) {
        Object object2;
        block54 : {
            CharSequence charSequence;
            CharSequence charSequence2 = charSequence = "KeyUsage [\n";
            if (object[0]) {
                object2 = charSequence;
                object2 = charSequence;
                charSequence2 = new StringBuilder();
                object2 = charSequence;
                ((StringBuilder)charSequence2).append("KeyUsage [\n");
                object2 = charSequence;
                ((StringBuilder)charSequence2).append("  DigitalSignature\n");
                object2 = charSequence;
                charSequence2 = ((StringBuilder)charSequence2).toString();
            }
            charSequence = charSequence2;
            if (object[1]) {
                object2 = charSequence2;
                object2 = charSequence2;
                charSequence = new StringBuilder();
                object2 = charSequence2;
                ((StringBuilder)charSequence).append((String)charSequence2);
                object2 = charSequence2;
                ((StringBuilder)charSequence).append("  Non_repudiation\n");
                object2 = charSequence2;
                charSequence = ((StringBuilder)charSequence).toString();
            }
            charSequence2 = charSequence;
            if (object[2]) {
                object2 = charSequence;
                object2 = charSequence;
                charSequence2 = new StringBuilder();
                object2 = charSequence;
                ((StringBuilder)charSequence2).append((String)charSequence);
                object2 = charSequence;
                ((StringBuilder)charSequence2).append("  Key_Encipherment\n");
                object2 = charSequence;
                charSequence2 = ((StringBuilder)charSequence2).toString();
            }
            charSequence = charSequence2;
            if (object[3] != false) {
                object2 = charSequence2;
                object2 = charSequence2;
                charSequence = new StringBuilder();
                object2 = charSequence2;
                ((StringBuilder)charSequence).append((String)charSequence2);
                object2 = charSequence2;
                ((StringBuilder)charSequence).append("  Data_Encipherment\n");
                object2 = charSequence2;
                charSequence = ((StringBuilder)charSequence).toString();
            }
            charSequence2 = charSequence;
            if (object[4] != false) {
                object2 = charSequence;
                object2 = charSequence;
                charSequence2 = new StringBuilder();
                object2 = charSequence;
                ((StringBuilder)charSequence2).append((String)charSequence);
                object2 = charSequence;
                ((StringBuilder)charSequence2).append("  Key_Agreement\n");
                object2 = charSequence;
                charSequence2 = ((StringBuilder)charSequence2).toString();
            }
            charSequence = charSequence2;
            if (object[5] != false) {
                object2 = charSequence2;
                object2 = charSequence2;
                charSequence = new StringBuilder();
                object2 = charSequence2;
                ((StringBuilder)charSequence).append((String)charSequence2);
                object2 = charSequence2;
                ((StringBuilder)charSequence).append("  Key_CertSign\n");
                object2 = charSequence2;
                charSequence = ((StringBuilder)charSequence).toString();
            }
            charSequence2 = charSequence;
            if (object[6] != false) {
                object2 = charSequence;
                object2 = charSequence;
                charSequence2 = new StringBuilder();
                object2 = charSequence;
                ((StringBuilder)charSequence2).append((String)charSequence);
                object2 = charSequence;
                ((StringBuilder)charSequence2).append("  Crl_Sign\n");
                object2 = charSequence;
                charSequence2 = ((StringBuilder)charSequence2).toString();
            }
            charSequence = charSequence2;
            if (object[7] != false) {
                object2 = charSequence2;
                object2 = charSequence2;
                charSequence = new StringBuilder();
                object2 = charSequence2;
                ((StringBuilder)charSequence).append((String)charSequence2);
                object2 = charSequence2;
                ((StringBuilder)charSequence).append("  Encipher_Only\n");
                object2 = charSequence2;
                charSequence = ((StringBuilder)charSequence).toString();
            }
            object2 = charSequence;
            if (object[8] == false) break block54;
            object2 = charSequence;
            object2 = charSequence;
            object = new StringBuilder();
            object2 = charSequence;
            ((StringBuilder)object).append((String)charSequence);
            object2 = charSequence;
            ((StringBuilder)object).append("  Decipher_Only\n");
            object2 = charSequence;
            try {
                object2 = object = ((StringBuilder)object).toString();
            }
            catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
                // empty catch block
            }
        }
        object = new StringBuilder();
        ((StringBuilder)object).append((String)object2);
        ((StringBuilder)object).append("]\n");
        return ((StringBuilder)object).toString();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    static GeneralNameInterface makeGeneralNameInterface(int var0, Object var1_1) throws IOException {
        var2_2 = X509CertSelector.debug;
        if (var2_2 != null) {
            var3_3 = new StringBuilder();
            var3_3.append("X509CertSelector.makeGeneralNameInterface(");
            var3_3.append(var0);
            var3_3.append(")...");
            var2_2.println(var3_3.toString());
        }
        if (var1_1 instanceof String) {
            var3_3 = X509CertSelector.debug;
            if (var3_3 != null) {
                var2_2 = new StringBuilder();
                var2_2.append("X509CertSelector.makeGeneralNameInterface() name is String: ");
                var2_2.append(var1_1);
                var3_3.println(var2_2.toString());
            }
            if (var0 != 1) {
                if (var0 != 2) {
                    if (var0 != 4) {
                        if (var0 != 6) {
                            if (var0 != 7) {
                                if (var0 != 8) {
                                    var1_1 = new StringBuilder();
                                    var1_1.append("unable to parse String names of type ");
                                    var1_1.append(var0);
                                    throw new IOException(var1_1.toString());
                                }
                                var1_1 = new OIDName((String)var1_1);
                            } else {
                                var1_1 = new IPAddressName((String)var1_1);
                            }
                        } else {
                            var1_1 = new URIName((String)var1_1);
                        }
                    } else {
                        var1_1 = new X500Name((String)var1_1);
                    }
                } else {
                    var1_1 = new DNSName((String)var1_1);
                }
            } else {
                var1_1 = new RFC822Name((String)var1_1);
            }
            var2_2 = X509CertSelector.debug;
            var3_3 = var1_1;
            if (var2_2 == null) return var3_3;
            var3_3 = new StringBuilder();
            var3_3.append("X509CertSelector.makeGeneralNameInterface() result: ");
            var3_3.append(var1_1.toString());
            var2_2.println(var3_3.toString());
            return var1_1;
        }
        if (!(var1_1 instanceof byte[])) {
            var1_1 = X509CertSelector.debug;
            if (var1_1 == null) throw new IOException("name not String or byte array");
            var1_1.println("X509CertSelector.makeGeneralName() input name not String or byte array");
            throw new IOException("name not String or byte array");
        }
        var3_3 = new DerValue((byte[])var1_1);
        var1_1 = X509CertSelector.debug;
        if (var1_1 != null) {
            var1_1.println("X509CertSelector.makeGeneralNameInterface() is byte[]");
        }
        switch (var0) {
            default: {
                var1_1 = new StringBuilder();
                var1_1.append("unable to parse byte array names of type ");
                var1_1.append(var0);
                throw new IOException(var1_1.toString());
            }
            case 8: {
                var1_1 = new OIDName((DerValue)var3_3);
                ** break;
            }
            case 7: {
                var1_1 = new IPAddressName((DerValue)var3_3);
                ** break;
            }
            case 6: {
                var1_1 = new URIName((DerValue)var3_3);
                ** break;
            }
            case 5: {
                var1_1 = new EDIPartyName((DerValue)var3_3);
                ** break;
            }
            case 4: {
                var1_1 = new X500Name((DerValue)var3_3);
                ** break;
            }
            case 3: {
                var1_1 = new X400Address((DerValue)var3_3);
                ** break;
            }
            case 2: {
                var1_1 = new DNSName((DerValue)var3_3);
                ** break;
            }
            case 1: {
                var1_1 = new RFC822Name((DerValue)var3_3);
                ** break;
            }
            case 0: 
        }
        var1_1 = new OtherName((DerValue)var3_3);
lbl96: // 9 sources:
        var3_3 = X509CertSelector.debug;
        if (var3_3 == null) return var1_1;
        var2_2 = new StringBuilder();
        var2_2.append("X509CertSelector.makeGeneralNameInterface() result: ");
        var2_2.append(var1_1.toString());
        var3_3.println(var2_2.toString());
        return var1_1;
    }

    private boolean matchAuthorityKeyID(X509Certificate arrby) {
        block11 : {
            block10 : {
                if (this.authorityKeyID == null) {
                    return true;
                }
                try {
                    arrby = arrby.getExtensionValue("2.5.29.35");
                    if (arrby != null) break block10;
                }
                catch (IOException iOException) {
                    Debug debug = X509CertSelector.debug;
                    if (debug != null) {
                        debug.println("X509CertSelector.match: exception in authority key ID check");
                    }
                    return false;
                }
                if (debug != null) {
                    debug.println("X509CertSelector.match: no authority key ID extension");
                }
                return false;
            }
            DerInputStream derInputStream = new DerInputStream(arrby);
            arrby = derInputStream.getOctetString();
            if (arrby == null) break block11;
            if (!Arrays.equals(this.authorityKeyID, arrby)) break block11;
            return true;
        }
        if (debug != null) {
            debug.println("X509CertSelector.match: authority key IDs don't match");
        }
        return false;
    }

    private boolean matchBasicConstraints(X509Certificate object) {
        if (this.basicConstraints == -1) {
            return true;
        }
        int n = ((X509Certificate)object).getBasicConstraints();
        int n2 = this.basicConstraints;
        if (n2 == -2) {
            if (n != -1) {
                object = debug;
                if (object != null) {
                    ((Debug)object).println("X509CertSelector.match: not an EE cert");
                }
                return false;
            }
        } else if (n < n2) {
            Debug debug = X509CertSelector.debug;
            if (debug != null) {
                object = new StringBuilder();
                ((StringBuilder)object).append("X509CertSelector.match: cert's maxPathLen is less than the min maxPathLen set by basicConstraints. (");
                ((StringBuilder)object).append(n);
                ((StringBuilder)object).append(" < ");
                ((StringBuilder)object).append(this.basicConstraints);
                ((StringBuilder)object).append(")");
                debug.println(((StringBuilder)object).toString());
            }
            return false;
        }
        return true;
    }

    private boolean matchExcluded(GeneralSubtrees object) {
        Iterator<GeneralSubtree> iterator = ((GeneralSubtrees)object).iterator();
        while (iterator.hasNext()) {
            Object object2 = iterator.next().getName().getName();
            Object object3 = this.pathToGeneralNames.iterator();
            while (object3.hasNext()) {
                int n;
                object = object3.next();
                if (object2.getType() != object.getType() || (n = object.constrains((GeneralNameInterface)object2)) != 0 && n != 2) continue;
                object3 = debug;
                if (object3 != null) {
                    ((Debug)object3).println("X509CertSelector.match: name constraints inhibit path to specified name");
                    object3 = debug;
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("X509CertSelector.match: excluded name: ");
                    ((StringBuilder)object2).append(object);
                    ((Debug)object3).println(((StringBuilder)object2).toString());
                }
                return false;
            }
        }
        return true;
    }

    private boolean matchExtendedKeyUsage(X509Certificate object) {
        Set<String> set = this.keyPurposeSet;
        if (set != null && !set.isEmpty()) {
            block6 : {
                try {
                    object = (ExtendedKeyUsageExtension)X509CertSelector.getExtensionObject((X509Certificate)object, 4);
                    if (object == null) break block6;
                }
                catch (IOException iOException) {
                    Debug debug = X509CertSelector.debug;
                    if (debug != null) {
                        debug.println("X509CertSelector.match: IOException in extended key usage check");
                    }
                    return false;
                }
                object = ((ExtendedKeyUsageExtension)object).get("usages");
                if (((Vector)object).contains(ANY_EXTENDED_KEY_USAGE) || ((Vector)object).containsAll(this.keyPurposeOIDSet)) break block6;
                if (debug != null) {
                    debug.println("X509CertSelector.match: cert failed extendedKeyUsage criterion");
                }
                return false;
            }
            return true;
        }
        return true;
    }

    private boolean matchKeyUsage(X509Certificate object) {
        if (this.keyUsage == null) {
            return true;
        }
        if ((object = ((X509Certificate)object).getKeyUsage()) != null) {
            boolean[] arrbl;
            for (int i = 0; i < (arrbl = this.keyUsage).length; ++i) {
                if (!arrbl[i] || i < ((boolean[])object).length && object[i]) continue;
                object = debug;
                if (object != null) {
                    ((Debug)object).println("X509CertSelector.match: key usage bits don't match");
                }
                return false;
            }
        }
        return true;
    }

    private boolean matchNameConstraints(X509Certificate x509Certificate) {
        NameConstraintsExtension nameConstraintsExtension = this.nc;
        if (nameConstraintsExtension == null) {
            return true;
        }
        try {
            if (!nameConstraintsExtension.verify(x509Certificate)) {
                if (debug != null) {
                    debug.println("X509CertSelector.match: name constraints not satisfied");
                }
                return false;
            }
            return true;
        }
        catch (IOException iOException) {
            Debug debug = X509CertSelector.debug;
            if (debug != null) {
                debug.println("X509CertSelector.match: IOException in name constraints check");
            }
            return false;
        }
    }

    private boolean matchPathToNames(X509Certificate object) {
        block12 : {
            Iterator<GeneralNameInterface> iterator;
            block11 : {
                block10 : {
                    if (this.pathToGeneralNames == null) {
                        return true;
                    }
                    object = (NameConstraintsExtension)X509CertSelector.getExtensionObject((X509Certificate)object, 2);
                    if (object != null) break block10;
                    return true;
                }
                try {
                    if (debug != null && Debug.isOn("certpath")) {
                        debug.println("X509CertSelector.match pathToNames:\n");
                        iterator = this.pathToGeneralNames.iterator();
                        while (iterator.hasNext()) {
                            Debug debug = X509CertSelector.debug;
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("    ");
                            stringBuilder.append(iterator.next());
                            stringBuilder.append("\n");
                            debug.println(stringBuilder.toString());
                        }
                    }
                    iterator = ((NameConstraintsExtension)object).get("permitted_subtrees");
                    if ((object = ((NameConstraintsExtension)object).get("excluded_subtrees")) == null) break block11;
                }
                catch (IOException iOException) {
                    Debug debug = X509CertSelector.debug;
                    if (debug != null) {
                        debug.println("X509CertSelector.match: IOException in name constraints check");
                    }
                    return false;
                }
                if (this.matchExcluded((GeneralSubtrees)object)) break block11;
                return false;
            }
            if (iterator != null) {
                boolean bl = this.matchPermitted((GeneralSubtrees)((Object)iterator));
                if (bl) break block12;
                return false;
            }
        }
        return true;
    }

    private boolean matchPermitted(GeneralSubtrees object) {
        for (GeneralNameInterface generalNameInterface : this.pathToGeneralNames) {
            CharSequence charSequence;
            Iterator<GeneralSubtree> iterator = ((GeneralSubtrees)object).iterator();
            int n = 0;
            boolean bl = false;
            CharSequence charSequence2 = "";
            while (iterator.hasNext() && n == 0) {
                GeneralNameInterface generalNameInterface2 = iterator.next().getName().getName();
                int n2 = n;
                charSequence = charSequence2;
                if (generalNameInterface2.getType() == generalNameInterface.getType()) {
                    bl = true;
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append((String)charSequence2);
                    ((StringBuilder)charSequence).append("  ");
                    ((StringBuilder)charSequence).append(generalNameInterface2);
                    charSequence = ((StringBuilder)charSequence).toString();
                    n2 = generalNameInterface.constrains(generalNameInterface2);
                    n2 = n2 != 0 && n2 != 2 ? n : 1;
                }
                n = n2;
                charSequence2 = charSequence;
            }
            if (n != 0 || !bl) continue;
            object = debug;
            if (object != null) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("X509CertSelector.match: name constraints inhibit path to specified name; permitted names of type ");
                ((StringBuilder)charSequence).append(generalNameInterface.getType());
                ((StringBuilder)charSequence).append(": ");
                ((StringBuilder)charSequence).append((String)charSequence2);
                ((Debug)object).println(((StringBuilder)charSequence).toString());
            }
            return false;
        }
        return true;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean matchPolicy(X509Certificate arrayList) {
        if (this.policy == null) {
            return true;
        }
        try {
            boolean bl;
            block7 : {
                arrayList = (CertificatePoliciesExtension)X509CertSelector.getExtensionObject(arrayList, 3);
                if (arrayList == null) {
                    if (debug == null) return false;
                    debug.println("X509CertSelector.match: no certificate policy extension");
                    return false;
                }
                Iterator<Object> iterator = ((CertificatePoliciesExtension)((Object)arrayList)).get("policies");
                arrayList = new ArrayList<CertificatePolicyId>(iterator.size());
                iterator = iterator.iterator();
                while (iterator.hasNext()) {
                    arrayList.add(((PolicyInformation)iterator.next()).getPolicyIdentifier());
                }
                if (this.policy == null) return true;
                boolean bl2 = false;
                boolean bl3 = this.policy.getCertPolicyIds().isEmpty();
                if (bl3) {
                    if (!arrayList.isEmpty()) return true;
                    if (debug == null) return false;
                    debug.println("X509CertSelector.match: cert failed policyAny criterion");
                    return false;
                }
                iterator = this.policy.getCertPolicyIds().iterator();
                do {
                    bl = bl2;
                    if (!iterator.hasNext()) break block7;
                } while (!arrayList.contains((CertificatePolicyId)iterator.next()));
                return true;
            }
            if (bl) return true;
            if (debug == null) return false;
            debug.println("X509CertSelector.match: cert failed policyAny criterion");
            return false;
        }
        catch (IOException iOException) {
            Debug debug = X509CertSelector.debug;
            if (debug == null) return false;
            debug.println("X509CertSelector.match: IOException in certificate policy ID check");
            return false;
        }
    }

    private boolean matchPrivateKeyValid(X509Certificate object) {
        block13 : {
            if (this.privateKeyValid == null) {
                return true;
            }
            Object object2 = null;
            Object object3 = null;
            object = (PrivateKeyUsageExtension)X509CertSelector.getExtensionObject((X509Certificate)object, 0);
            if (object == null) break block13;
            object3 = object;
            object2 = object;
            try {
                ((PrivateKeyUsageExtension)object).valid(this.privateKeyValid);
            }
            catch (IOException iOException) {
                object = debug;
                if (object != null) {
                    object3 = new StringBuilder();
                    ((StringBuilder)object3).append("X509CertSelector.match: IOException in private key usage check; X509CertSelector: ");
                    ((StringBuilder)object3).append(this.toString());
                    ((Debug)object).println(((StringBuilder)object3).toString());
                    iOException.printStackTrace();
                }
                return false;
            }
            catch (CertificateNotYetValidException certificateNotYetValidException) {
                if (debug != null) {
                    object = "n/a";
                    try {
                        object = object3 = ((Date)((PrivateKeyUsageExtension)object3).get("not_before")).toString();
                    }
                    catch (CertificateException certificateException) {
                        // empty catch block
                    }
                    Debug debug = X509CertSelector.debug;
                    object3 = new StringBuilder();
                    ((StringBuilder)object3).append("X509CertSelector.match: private key usage not within validity date; ext.NOT_BEFORE: ");
                    ((StringBuilder)object3).append((String)object);
                    ((StringBuilder)object3).append("; X509CertSelector: ");
                    ((StringBuilder)object3).append(this.toString());
                    debug.println(((StringBuilder)object3).toString());
                    certificateNotYetValidException.printStackTrace();
                }
                return false;
            }
            catch (CertificateExpiredException certificateExpiredException) {
                if (debug != null) {
                    object = "n/a";
                    try {
                        object = object3 = ((Date)((PrivateKeyUsageExtension)object2).get("not_after")).toString();
                    }
                    catch (CertificateException certificateException) {
                        // empty catch block
                    }
                    object3 = debug;
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("X509CertSelector.match: private key usage not within validity date; ext.NOT_After: ");
                    ((StringBuilder)object2).append((String)object);
                    ((StringBuilder)object2).append("; X509CertSelector: ");
                    ((StringBuilder)object2).append(this.toString());
                    ((Debug)object3).println(((StringBuilder)object2).toString());
                    certificateExpiredException.printStackTrace();
                }
                return false;
            }
        }
        return true;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean matchSubjectAlternativeNames(X509Certificate object) {
        Set set = this.subjectAlternativeNames;
        if (set == null) return true;
        if (set.isEmpty()) {
            return true;
        }
        try {
            boolean bl;
            object = (SubjectAlternativeNameExtension)X509CertSelector.getExtensionObject((X509Certificate)object, 1);
            if (object == null) {
                if (debug == null) return false;
                debug.println("X509CertSelector.match: no subject alternative name extension");
                return false;
            }
            Object object2 = ((SubjectAlternativeNameExtension)object).get("subject_name");
            set = this.subjectAlternativeGeneralNames.iterator();
            do {
                if (!set.hasNext()) return true;
                object = (GeneralNameInterface)set.next();
                bl = false;
                Object object3 = ((GeneralNames)object2).iterator();
                while (object3.hasNext() && !bl) {
                    bl = object3.next().getName().equals(object);
                }
                if (bl || !this.matchAllSubjectAltNames && set.hasNext()) continue;
                if (debug == null) return false;
                object3 = debug;
                set = new StringBuilder();
                ((StringBuilder)((Object)set)).append("X509CertSelector.match: subject alternative name ");
                ((StringBuilder)((Object)set)).append(object);
                ((StringBuilder)((Object)set)).append(" not found");
                ((Debug)object3).println(((StringBuilder)((Object)set)).toString());
                return false;
            } while (!bl || (bl = this.matchAllSubjectAltNames));
            return true;
        }
        catch (IOException iOException) {
            Debug debug = X509CertSelector.debug;
            if (debug == null) return false;
            debug.println("X509CertSelector.match: IOException in subject alternative name check");
            return false;
        }
    }

    private boolean matchSubjectKeyID(X509Certificate arrby) {
        block11 : {
            block10 : {
                if (this.subjectKeyID == null) {
                    return true;
                }
                try {
                    arrby = arrby.getExtensionValue("2.5.29.14");
                    if (arrby != null) break block10;
                }
                catch (IOException iOException) {
                    Debug debug = X509CertSelector.debug;
                    if (debug != null) {
                        debug.println("X509CertSelector.match: exception in subject key ID check");
                    }
                    return false;
                }
                if (debug != null) {
                    debug.println("X509CertSelector.match: no subject key ID extension");
                }
                return false;
            }
            DerInputStream derInputStream = new DerInputStream(arrby);
            arrby = derInputStream.getOctetString();
            if (arrby == null) break block11;
            if (!Arrays.equals(this.subjectKeyID, arrby)) break block11;
            return true;
        }
        if (debug != null) {
            debug.println("X509CertSelector.match: subject key IDs don't match");
        }
        return false;
    }

    private boolean matchSubjectPublicKeyAlgID(X509Certificate object) {
        block8 : {
            if (this.subjectPublicKeyAlgID == null) {
                return true;
            }
            try {
                Object object2 = ((Certificate)object).getPublicKey().getEncoded();
                object = new DerValue((byte[])object2);
                if (((DerValue)object).tag != 48) break block8;
                AlgorithmId algorithmId = AlgorithmId.parse(((DerValue)object).data.getDerValue());
                if (debug != null) {
                    object = debug;
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("X509CertSelector.match: subjectPublicKeyAlgID = ");
                    ((StringBuilder)object2).append(this.subjectPublicKeyAlgID);
                    ((StringBuilder)object2).append(", xcert subjectPublicKeyAlgID = ");
                    ((StringBuilder)object2).append(algorithmId.getOID());
                    ((Debug)object).println(((StringBuilder)object2).toString());
                }
                if (!this.subjectPublicKeyAlgID.equals((Object)algorithmId.getOID())) {
                    if (debug != null) {
                        debug.println("X509CertSelector.match: subject public key alg IDs don't match");
                    }
                    return false;
                }
                return true;
            }
            catch (IOException iOException) {
                Debug debug = X509CertSelector.debug;
                if (debug != null) {
                    debug.println("X509CertSelector.match: IOException in subject public key algorithm OID check");
                }
                return false;
            }
        }
        object = new IOException("invalid key format");
        throw object;
    }

    private static Set<GeneralNameInterface> parseNames(Collection<List<?>> object) throws IOException {
        HashSet<GeneralNameInterface> hashSet = new HashSet<GeneralNameInterface>();
        object = object.iterator();
        while (object.hasNext()) {
            List list = (List)object.next();
            if (list.size() == 2) {
                Object e = list.get(0);
                if (e instanceof Integer) {
                    hashSet.add(X509CertSelector.makeGeneralNameInterface((Integer)e, list.get(1)));
                    continue;
                }
                throw new IOException("expected an Integer");
            }
            throw new IOException("name list size not 2");
        }
        return hashSet;
    }

    public void addPathToName(int n, String string) throws IOException {
        this.addPathToNameInternal(n, string);
    }

    public void addPathToName(int n, byte[] arrby) throws IOException {
        this.addPathToNameInternal(n, arrby.clone());
    }

    public void addSubjectAlternativeName(int n, String string) throws IOException {
        this.addSubjectAlternativeNameInternal(n, string);
    }

    public void addSubjectAlternativeName(int n, byte[] arrby) throws IOException {
        this.addSubjectAlternativeNameInternal(n, arrby.clone());
    }

    @Override
    public Object clone() {
        try {
            X509CertSelector x509CertSelector = (X509CertSelector)super.clone();
            if (this.subjectAlternativeNames != null) {
                x509CertSelector.subjectAlternativeNames = X509CertSelector.cloneSet(this.subjectAlternativeNames);
                x509CertSelector.subjectAlternativeGeneralNames = X509CertSelector.cloneSet(this.subjectAlternativeGeneralNames);
            }
            if (this.pathToGeneralNames != null) {
                x509CertSelector.pathToNames = X509CertSelector.cloneSet(this.pathToNames);
                x509CertSelector.pathToGeneralNames = X509CertSelector.cloneSet(this.pathToGeneralNames);
            }
            return x509CertSelector;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError(cloneNotSupportedException.toString(), cloneNotSupportedException);
        }
    }

    public byte[] getAuthorityKeyIdentifier() {
        byte[] arrby = this.authorityKeyID;
        if (arrby == null) {
            return null;
        }
        return (byte[])arrby.clone();
    }

    public int getBasicConstraints() {
        return this.basicConstraints;
    }

    public X509Certificate getCertificate() {
        return this.x509Cert;
    }

    public Date getCertificateValid() {
        Date date = this.certificateValid;
        if (date == null) {
            return null;
        }
        return (Date)date.clone();
    }

    public Set<String> getExtendedKeyUsage() {
        return this.keyPurposeSet;
    }

    public X500Principal getIssuer() {
        return this.issuer;
    }

    public byte[] getIssuerAsBytes() throws IOException {
        Object object = this.issuer;
        object = object == null ? null : object.getEncoded();
        return object;
    }

    public String getIssuerAsString() {
        Object object = this.issuer;
        object = object == null ? null : ((X500Principal)object).getName();
        return object;
    }

    public boolean[] getKeyUsage() {
        boolean[] arrbl = this.keyUsage;
        if (arrbl == null) {
            return null;
        }
        return (boolean[])arrbl.clone();
    }

    public boolean getMatchAllSubjectAltNames() {
        return this.matchAllSubjectAltNames;
    }

    public byte[] getNameConstraints() {
        byte[] arrby = this.ncBytes;
        if (arrby == null) {
            return null;
        }
        return (byte[])arrby.clone();
    }

    public Collection<List<?>> getPathToNames() {
        Set<List<?>> set = this.pathToNames;
        if (set == null) {
            return null;
        }
        return X509CertSelector.cloneNames(set);
    }

    public Set<String> getPolicy() {
        return this.policySet;
    }

    public Date getPrivateKeyValid() {
        Date date = this.privateKeyValid;
        if (date == null) {
            return null;
        }
        return (Date)date.clone();
    }

    public BigInteger getSerialNumber() {
        return this.serialNumber;
    }

    public X500Principal getSubject() {
        return this.subject;
    }

    public Collection<List<?>> getSubjectAlternativeNames() {
        Set<List<?>> set = this.subjectAlternativeNames;
        if (set == null) {
            return null;
        }
        return X509CertSelector.cloneNames(set);
    }

    public byte[] getSubjectAsBytes() throws IOException {
        Object object = this.subject;
        object = object == null ? null : object.getEncoded();
        return object;
    }

    public String getSubjectAsString() {
        Object object = this.subject;
        object = object == null ? null : ((X500Principal)object).getName();
        return object;
    }

    public byte[] getSubjectKeyIdentifier() {
        byte[] arrby = this.subjectKeyID;
        if (arrby == null) {
            return null;
        }
        return (byte[])arrby.clone();
    }

    public PublicKey getSubjectPublicKey() {
        return this.subjectPublicKey;
    }

    public String getSubjectPublicKeyAlgID() {
        ObjectIdentifier objectIdentifier = this.subjectPublicKeyAlgID;
        if (objectIdentifier == null) {
            return null;
        }
        return objectIdentifier.toString();
    }

    @Override
    public boolean match(Certificate object) {
        boolean bl = object instanceof X509Certificate;
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        object = (X509Certificate)object;
        Object object2 = debug;
        if (object2 != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("X509CertSelector.match(SN: ");
            stringBuilder.append(((X509Certificate)object).getSerialNumber().toString(16));
            stringBuilder.append("\n  Issuer: ");
            stringBuilder.append(((X509Certificate)object).getIssuerDN());
            stringBuilder.append("\n  Subject: ");
            stringBuilder.append(((X509Certificate)object).getSubjectDN());
            stringBuilder.append(")");
            object2.println(stringBuilder.toString());
        }
        if ((object2 = this.x509Cert) != null && !object2.equals(object)) {
            object = debug;
            if (object != null) {
                ((Debug)object).println("X509CertSelector.match: certs don't match");
            }
            return false;
        }
        object2 = this.serialNumber;
        if (object2 != null && !object2.equals(((X509Certificate)object).getSerialNumber())) {
            object = debug;
            if (object != null) {
                ((Debug)object).println("X509CertSelector.match: serial numbers don't match");
            }
            return false;
        }
        object2 = this.issuer;
        if (object2 != null && !object2.equals(((X509Certificate)object).getIssuerX500Principal())) {
            object = debug;
            if (object != null) {
                ((Debug)object).println("X509CertSelector.match: issuer DNs don't match");
            }
            return false;
        }
        object2 = this.subject;
        if (object2 != null && !object2.equals(((X509Certificate)object).getSubjectX500Principal())) {
            object = debug;
            if (object != null) {
                ((Debug)object).println("X509CertSelector.match: subject DNs don't match");
            }
            return false;
        }
        object2 = this.certificateValid;
        if (object2 != null) {
            try {
                ((X509Certificate)object).checkValidity((Date)object2);
            }
            catch (CertificateException certificateException) {
                Debug debug = X509CertSelector.debug;
                if (debug != null) {
                    debug.println("X509CertSelector.match: certificate not within validity period");
                }
                return false;
            }
        }
        if (this.subjectPublicKeyBytes != null && !Arrays.equals(this.subjectPublicKeyBytes, object2 = ((Certificate)object).getPublicKey().getEncoded())) {
            object = debug;
            if (object != null) {
                ((Debug)object).println("X509CertSelector.match: subject public keys don't match");
            }
            return false;
        }
        if (this.matchBasicConstraints((X509Certificate)object) && this.matchKeyUsage((X509Certificate)object) && this.matchExtendedKeyUsage((X509Certificate)object) && this.matchSubjectKeyID((X509Certificate)object) && this.matchAuthorityKeyID((X509Certificate)object) && this.matchPrivateKeyValid((X509Certificate)object) && this.matchSubjectPublicKeyAlgID((X509Certificate)object) && this.matchPolicy((X509Certificate)object) && this.matchSubjectAlternativeNames((X509Certificate)object) && this.matchPathToNames((X509Certificate)object) && this.matchNameConstraints((X509Certificate)object)) {
            bl2 = true;
        }
        if (bl2 && (object = debug) != null) {
            ((Debug)object).println("X509CertSelector.match returning: true");
        }
        return bl2;
    }

    public void setAuthorityKeyIdentifier(byte[] arrby) {
        this.authorityKeyID = arrby == null ? null : (byte[])arrby.clone();
    }

    public void setBasicConstraints(int n) {
        if (n >= -2) {
            this.basicConstraints = n;
            return;
        }
        throw new IllegalArgumentException("basic constraints less than -2");
    }

    public void setCertificate(X509Certificate x509Certificate) {
        this.x509Cert = x509Certificate;
    }

    public void setCertificateValid(Date date) {
        this.certificateValid = date == null ? null : (Date)date.clone();
    }

    public void setExtendedKeyUsage(Set<String> object2) throws IOException {
        if (object2 != null && !object2.isEmpty()) {
            this.keyPurposeSet = Collections.unmodifiableSet(new HashSet(object2));
            this.keyPurposeOIDSet = new HashSet<ObjectIdentifier>();
            for (String string : this.keyPurposeSet) {
                this.keyPurposeOIDSet.add(new ObjectIdentifier(string));
            }
        } else {
            this.keyPurposeSet = null;
            this.keyPurposeOIDSet = null;
        }
    }

    public void setIssuer(String string) throws IOException {
        this.issuer = string == null ? null : new X500Name(string).asX500Principal();
    }

    public void setIssuer(X500Principal x500Principal) {
        this.issuer = x500Principal;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public void setIssuer(byte[] var1_1) throws IOException {
        block2 : {
            if (var1_1 /* !! */  != null) break block2;
            var1_2 = null;
            ** GOTO lbl7
        }
        try {
            var1_3 = new X500Principal(var1_1 /* !! */ );
lbl7: // 2 sources:
            this.issuer = var1_4;
            return;
        }
        catch (IllegalArgumentException var1_5) {
            throw new IOException("Invalid name", var1_5);
        }
    }

    public void setKeyUsage(boolean[] arrbl) {
        this.keyUsage = arrbl == null ? null : (boolean[])arrbl.clone();
    }

    public void setMatchAllSubjectAltNames(boolean bl) {
        this.matchAllSubjectAltNames = bl;
    }

    public void setNameConstraints(byte[] arrby) throws IOException {
        if (arrby == null) {
            this.ncBytes = null;
            this.nc = null;
        } else {
            this.ncBytes = (byte[])arrby.clone();
            this.nc = new NameConstraintsExtension(FALSE, arrby);
        }
    }

    public void setPathToNames(Collection<List<?>> collection) throws IOException {
        if (collection != null && !collection.isEmpty()) {
            collection = X509CertSelector.cloneAndCheckNames(collection);
            this.pathToGeneralNames = X509CertSelector.parseNames(collection);
            this.pathToNames = collection;
        } else {
            this.pathToNames = null;
            this.pathToGeneralNames = null;
        }
    }

    void setPathToNamesInternal(Set<GeneralNameInterface> set) {
        this.pathToNames = Collections.emptySet();
        this.pathToGeneralNames = set;
    }

    public void setPolicy(Set<String> object) throws IOException {
        if (object == null) {
            this.policySet = null;
            this.policy = null;
        } else {
            Set<String> set = Collections.unmodifiableSet(new HashSet<String>((Collection<String>)object));
            object = set.iterator();
            Vector<CertificatePolicyId> vector = new Vector<CertificatePolicyId>();
            while (object.hasNext()) {
                Object e = object.next();
                if (e instanceof String) {
                    vector.add(new CertificatePolicyId(new ObjectIdentifier((String)e)));
                    continue;
                }
                throw new IOException("non String in certPolicySet");
            }
            this.policySet = set;
            this.policy = new CertificatePolicySet(vector);
        }
    }

    public void setPrivateKeyValid(Date date) {
        this.privateKeyValid = date == null ? null : (Date)date.clone();
    }

    public void setSerialNumber(BigInteger bigInteger) {
        this.serialNumber = bigInteger;
    }

    public void setSubject(String string) throws IOException {
        this.subject = string == null ? null : new X500Name(string).asX500Principal();
    }

    public void setSubject(X500Principal x500Principal) {
        this.subject = x500Principal;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public void setSubject(byte[] var1_1) throws IOException {
        block2 : {
            if (var1_1 /* !! */  != null) break block2;
            var1_2 = null;
            ** GOTO lbl7
        }
        try {
            var1_3 = new X500Principal(var1_1 /* !! */ );
lbl7: // 2 sources:
            this.subject = var1_4;
            return;
        }
        catch (IllegalArgumentException var1_5) {
            throw new IOException("Invalid name", var1_5);
        }
    }

    public void setSubjectAlternativeNames(Collection<List<?>> collection) throws IOException {
        if (collection == null) {
            this.subjectAlternativeNames = null;
            this.subjectAlternativeGeneralNames = null;
        } else {
            if (collection.isEmpty()) {
                this.subjectAlternativeNames = null;
                this.subjectAlternativeGeneralNames = null;
                return;
            }
            collection = X509CertSelector.cloneAndCheckNames(collection);
            this.subjectAlternativeGeneralNames = X509CertSelector.parseNames(collection);
            this.subjectAlternativeNames = collection;
        }
    }

    public void setSubjectKeyIdentifier(byte[] arrby) {
        this.subjectKeyID = arrby == null ? null : (byte[])arrby.clone();
    }

    public void setSubjectPublicKey(PublicKey publicKey) {
        if (publicKey == null) {
            this.subjectPublicKey = null;
            this.subjectPublicKeyBytes = null;
        } else {
            this.subjectPublicKey = publicKey;
            this.subjectPublicKeyBytes = publicKey.getEncoded();
        }
    }

    public void setSubjectPublicKey(byte[] arrby) throws IOException {
        if (arrby == null) {
            this.subjectPublicKey = null;
            this.subjectPublicKeyBytes = null;
        } else {
            this.subjectPublicKeyBytes = (byte[])arrby.clone();
            this.subjectPublicKey = X509Key.parse(new DerValue(this.subjectPublicKeyBytes));
        }
    }

    public void setSubjectPublicKeyAlgID(String string) throws IOException {
        this.subjectPublicKeyAlgID = string == null ? null : new ObjectIdentifier(string);
    }

    public String toString() {
        Object object2;
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("X509CertSelector: [\n");
        if (this.x509Cert != null) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("  Certificate: ");
            ((StringBuilder)object2).append(this.x509Cert.toString());
            ((StringBuilder)object2).append("\n");
            stringBuffer.append(((StringBuilder)object2).toString());
        }
        if (this.serialNumber != null) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("  Serial Number: ");
            ((StringBuilder)object2).append(this.serialNumber.toString());
            ((StringBuilder)object2).append("\n");
            stringBuffer.append(((StringBuilder)object2).toString());
        }
        if (this.issuer != null) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("  Issuer: ");
            ((StringBuilder)object2).append(this.getIssuerAsString());
            ((StringBuilder)object2).append("\n");
            stringBuffer.append(((StringBuilder)object2).toString());
        }
        if (this.subject != null) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("  Subject: ");
            ((StringBuilder)object2).append(this.getSubjectAsString());
            ((StringBuilder)object2).append("\n");
            stringBuffer.append(((StringBuilder)object2).toString());
        }
        object2 = new StringBuilder();
        ((StringBuilder)object2).append("  matchAllSubjectAltNames flag: ");
        ((StringBuilder)object2).append(String.valueOf(this.matchAllSubjectAltNames));
        ((StringBuilder)object2).append("\n");
        stringBuffer.append(((StringBuilder)object2).toString());
        if (this.subjectAlternativeNames != null) {
            stringBuffer.append("  SubjectAlternativeNames:\n");
            for (List<?> object3 : this.subjectAlternativeNames) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("    type ");
                ((StringBuilder)object2).append(object3.get(0));
                ((StringBuilder)object2).append(", name ");
                ((StringBuilder)object2).append(object3.get(1));
                ((StringBuilder)object2).append("\n");
                stringBuffer.append(((StringBuilder)object2).toString());
            }
        }
        if (this.subjectKeyID != null) {
            HexDumpEncoder hexDumpEncoder = new HexDumpEncoder();
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("  Subject Key Identifier: ");
            ((StringBuilder)object2).append(hexDumpEncoder.encodeBuffer(this.subjectKeyID));
            ((StringBuilder)object2).append("\n");
            stringBuffer.append(((StringBuilder)object2).toString());
        }
        if (this.authorityKeyID != null) {
            object2 = new HexDumpEncoder();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("  Authority Key Identifier: ");
            stringBuilder.append(((CharacterEncoder)object2).encodeBuffer(this.authorityKeyID));
            stringBuilder.append("\n");
            stringBuffer.append(stringBuilder.toString());
        }
        if (this.certificateValid != null) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("  Certificate Valid: ");
            ((StringBuilder)object2).append(this.certificateValid.toString());
            ((StringBuilder)object2).append("\n");
            stringBuffer.append(((StringBuilder)object2).toString());
        }
        if (this.privateKeyValid != null) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("  Private Key Valid: ");
            ((StringBuilder)object2).append(this.privateKeyValid.toString());
            ((StringBuilder)object2).append("\n");
            stringBuffer.append(((StringBuilder)object2).toString());
        }
        if (this.subjectPublicKeyAlgID != null) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("  Subject Public Key AlgID: ");
            ((StringBuilder)object2).append(this.subjectPublicKeyAlgID.toString());
            ((StringBuilder)object2).append("\n");
            stringBuffer.append(((StringBuilder)object2).toString());
        }
        if (this.subjectPublicKey != null) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("  Subject Public Key: ");
            ((StringBuilder)object2).append(this.subjectPublicKey.toString());
            ((StringBuilder)object2).append("\n");
            stringBuffer.append(((StringBuilder)object2).toString());
        }
        if (this.keyUsage != null) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("  Key Usage: ");
            ((StringBuilder)object2).append(X509CertSelector.keyUsageToString(this.keyUsage));
            ((StringBuilder)object2).append("\n");
            stringBuffer.append(((StringBuilder)object2).toString());
        }
        if (this.keyPurposeSet != null) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("  Extended Key Usage: ");
            ((StringBuilder)object2).append(this.keyPurposeSet.toString());
            ((StringBuilder)object2).append("\n");
            stringBuffer.append(((StringBuilder)object2).toString());
        }
        if (this.policy != null) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("  Policy: ");
            ((StringBuilder)object2).append(this.policy.toString());
            ((StringBuilder)object2).append("\n");
            stringBuffer.append(((StringBuilder)object2).toString());
        }
        if (this.pathToGeneralNames != null) {
            stringBuffer.append("  Path to names:\n");
            Iterator<GeneralNameInterface> iterator = this.pathToGeneralNames.iterator();
            while (iterator.hasNext()) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("    ");
                ((StringBuilder)object2).append(iterator.next());
                ((StringBuilder)object2).append("\n");
                stringBuffer.append(((StringBuilder)object2).toString());
            }
        }
        stringBuffer.append("]");
        return stringBuffer.toString();
    }
}

