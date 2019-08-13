/*
 * Decompiled with CFR 0.145.
 */
package sun.security.x509;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.Principal;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.security.auth.x500.X500Principal;
import sun.security.util.DerInputStream;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import sun.security.util.ObjectIdentifier;
import sun.security.x509.AVA;
import sun.security.x509.GeneralNameInterface;
import sun.security.x509.RDN;

public class X500Name
implements GeneralNameInterface,
Principal {
    private static final int[] DNQUALIFIER_DATA;
    public static final ObjectIdentifier DNQUALIFIER_OID;
    private static final int[] DOMAIN_COMPONENT_DATA;
    public static final ObjectIdentifier DOMAIN_COMPONENT_OID;
    private static final int[] GENERATIONQUALIFIER_DATA;
    public static final ObjectIdentifier GENERATIONQUALIFIER_OID;
    private static final int[] GIVENNAME_DATA;
    public static final ObjectIdentifier GIVENNAME_OID;
    private static final int[] INITIALS_DATA;
    public static final ObjectIdentifier INITIALS_OID;
    private static final int[] SERIALNUMBER_DATA;
    public static final ObjectIdentifier SERIALNUMBER_OID;
    private static final int[] SURNAME_DATA;
    public static final ObjectIdentifier SURNAME_OID;
    private static final int[] commonName_data;
    public static final ObjectIdentifier commonName_oid;
    private static final int[] countryName_data;
    public static final ObjectIdentifier countryName_oid;
    private static final Map<ObjectIdentifier, ObjectIdentifier> internedOIDs;
    private static final int[] ipAddress_data;
    public static final ObjectIdentifier ipAddress_oid;
    private static final int[] localityName_data;
    public static final ObjectIdentifier localityName_oid;
    private static final int[] orgName_data;
    public static final ObjectIdentifier orgName_oid;
    private static final int[] orgUnitName_data;
    public static final ObjectIdentifier orgUnitName_oid;
    private static final Constructor<X500Principal> principalConstructor;
    private static final Field principalField;
    private static final int[] stateName_data;
    public static final ObjectIdentifier stateName_oid;
    private static final int[] streetAddress_data;
    public static final ObjectIdentifier streetAddress_oid;
    private static final int[] title_data;
    public static final ObjectIdentifier title_oid;
    private static final int[] userid_data;
    public static final ObjectIdentifier userid_oid;
    private volatile List<AVA> allAvaList;
    private String canonicalDn;
    private String dn;
    private byte[] encoded;
    private RDN[] names;
    private volatile List<RDN> rdnList;
    private String rfc1779Dn;
    private String rfc2253Dn;
    private X500Principal x500Principal;

    static {
        internedOIDs = new HashMap<ObjectIdentifier, ObjectIdentifier>();
        commonName_data = new int[]{2, 5, 4, 3};
        SURNAME_DATA = new int[]{2, 5, 4, 4};
        SERIALNUMBER_DATA = new int[]{2, 5, 4, 5};
        countryName_data = new int[]{2, 5, 4, 6};
        localityName_data = new int[]{2, 5, 4, 7};
        stateName_data = new int[]{2, 5, 4, 8};
        streetAddress_data = new int[]{2, 5, 4, 9};
        orgName_data = new int[]{2, 5, 4, 10};
        orgUnitName_data = new int[]{2, 5, 4, 11};
        title_data = new int[]{2, 5, 4, 12};
        GIVENNAME_DATA = new int[]{2, 5, 4, 42};
        INITIALS_DATA = new int[]{2, 5, 4, 43};
        GENERATIONQUALIFIER_DATA = new int[]{2, 5, 4, 44};
        DNQUALIFIER_DATA = new int[]{2, 5, 4, 46};
        ipAddress_data = new int[]{1, 3, 6, 1, 4, 1, 42, 2, 11, 2, 1};
        DOMAIN_COMPONENT_DATA = new int[]{0, 9, 2342, 19200300, 100, 1, 25};
        userid_data = new int[]{0, 9, 2342, 19200300, 100, 1, 1};
        commonName_oid = X500Name.intern(ObjectIdentifier.newInternal(commonName_data));
        SERIALNUMBER_OID = X500Name.intern(ObjectIdentifier.newInternal(SERIALNUMBER_DATA));
        countryName_oid = X500Name.intern(ObjectIdentifier.newInternal(countryName_data));
        localityName_oid = X500Name.intern(ObjectIdentifier.newInternal(localityName_data));
        orgName_oid = X500Name.intern(ObjectIdentifier.newInternal(orgName_data));
        orgUnitName_oid = X500Name.intern(ObjectIdentifier.newInternal(orgUnitName_data));
        stateName_oid = X500Name.intern(ObjectIdentifier.newInternal(stateName_data));
        streetAddress_oid = X500Name.intern(ObjectIdentifier.newInternal(streetAddress_data));
        title_oid = X500Name.intern(ObjectIdentifier.newInternal(title_data));
        DNQUALIFIER_OID = X500Name.intern(ObjectIdentifier.newInternal(DNQUALIFIER_DATA));
        SURNAME_OID = X500Name.intern(ObjectIdentifier.newInternal(SURNAME_DATA));
        GIVENNAME_OID = X500Name.intern(ObjectIdentifier.newInternal(GIVENNAME_DATA));
        INITIALS_OID = X500Name.intern(ObjectIdentifier.newInternal(INITIALS_DATA));
        GENERATIONQUALIFIER_OID = X500Name.intern(ObjectIdentifier.newInternal(GENERATIONQUALIFIER_DATA));
        ipAddress_oid = X500Name.intern(ObjectIdentifier.newInternal(ipAddress_data));
        DOMAIN_COMPONENT_OID = X500Name.intern(ObjectIdentifier.newInternal(DOMAIN_COMPONENT_DATA));
        userid_oid = X500Name.intern(ObjectIdentifier.newInternal(userid_data));
        PrivilegedExceptionAction<Object[]> privilegedExceptionAction = new PrivilegedExceptionAction<Object[]>(){

            @Override
            public Object[] run() throws Exception {
                Constructor constructor = X500Principal.class.getDeclaredConstructor(X500Name.class);
                constructor.setAccessible(true);
                Field field = X500Principal.class.getDeclaredField("thisX500Name");
                field.setAccessible(true);
                return new Object[]{constructor, field};
            }
        };
        try {
            privilegedExceptionAction = AccessController.doPrivileged(privilegedExceptionAction);
            principalConstructor = (Constructor)privilegedExceptionAction[0];
            principalField = (Field)privilegedExceptionAction[1];
            return;
        }
        catch (Exception exception) {
            throw new InternalError("Could not obtain X500Principal access", exception);
        }
    }

    public X500Name(String string) throws IOException {
        this(string, Collections.emptyMap());
    }

    public X500Name(String charSequence, String string) throws IOException {
        block2 : {
            block5 : {
                block4 : {
                    block3 : {
                        if (charSequence == null) break block2;
                        if (!string.equalsIgnoreCase("RFC2253")) break block3;
                        this.parseRFC2253DN((String)charSequence);
                        break block4;
                    }
                    if (!string.equalsIgnoreCase("DEFAULT")) break block5;
                    this.parseDN((String)charSequence, Collections.emptyMap());
                }
                return;
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Unsupported format ");
            ((StringBuilder)charSequence).append(string);
            throw new IOException(((StringBuilder)charSequence).toString());
        }
        throw new NullPointerException("Name must not be null");
    }

    public X500Name(String string, String string2, String string3, String string4) throws IOException {
        this.names = new RDN[4];
        this.names[3] = new RDN(1);
        this.names[3].assertion[0] = new AVA(commonName_oid, new DerValue(string));
        this.names[2] = new RDN(1);
        this.names[2].assertion[0] = new AVA(orgUnitName_oid, new DerValue(string2));
        this.names[1] = new RDN(1);
        this.names[1].assertion[0] = new AVA(orgName_oid, new DerValue(string3));
        this.names[0] = new RDN(1);
        this.names[0].assertion[0] = new AVA(countryName_oid, new DerValue(string4));
    }

    public X500Name(String string, String string2, String string3, String string4, String string5, String string6) throws IOException {
        this.names = new RDN[6];
        this.names[5] = new RDN(1);
        this.names[5].assertion[0] = new AVA(commonName_oid, new DerValue(string));
        this.names[4] = new RDN(1);
        this.names[4].assertion[0] = new AVA(orgUnitName_oid, new DerValue(string2));
        this.names[3] = new RDN(1);
        this.names[3].assertion[0] = new AVA(orgName_oid, new DerValue(string3));
        this.names[2] = new RDN(1);
        this.names[2].assertion[0] = new AVA(localityName_oid, new DerValue(string4));
        this.names[1] = new RDN(1);
        this.names[1].assertion[0] = new AVA(stateName_oid, new DerValue(string5));
        this.names[0] = new RDN(1);
        this.names[0].assertion[0] = new AVA(countryName_oid, new DerValue(string6));
    }

    public X500Name(String string, Map<String, String> map) throws IOException {
        this.parseDN(string, map);
    }

    public X500Name(DerInputStream derInputStream) throws IOException {
        this.parseDER(derInputStream);
    }

    public X500Name(DerValue derValue) throws IOException {
        this(derValue.toDerInputStream());
    }

    public X500Name(byte[] arrby) throws IOException {
        this.parseDER(new DerInputStream(arrby));
    }

    public X500Name(RDN[] arrrDN) throws IOException {
        if (arrrDN == null) {
            this.names = new RDN[0];
        } else {
            this.names = (RDN[])arrrDN.clone();
            for (int i = 0; i < (arrrDN = this.names).length; ++i) {
                if (arrrDN[i] != null) {
                    continue;
                }
                throw new IOException("Cannot create an X500Name");
            }
        }
    }

    public static X500Name asX500Name(X500Principal x500Principal) {
        try {
            X500Name x500Name = (X500Name)principalField.get(x500Principal);
            x500Name.x500Principal = x500Principal;
            return x500Name;
        }
        catch (Exception exception) {
            throw new RuntimeException("Unexpected exception", exception);
        }
    }

    private void checkNoNewLinesNorTabsAtBeginningOfDN(String string) {
        for (int i = 0; i < string.length(); ++i) {
            char c = string.charAt(i);
            if (c == ' ') continue;
            if (c != '\t' && c != '\n') break;
            throw new IllegalArgumentException("DN cannot start with newline nor tab");
        }
    }

    static int countQuotes(String string, int n, int n2) {
        int n3 = 0;
        int n4 = 0;
        int n5 = n;
        n = n4;
        while (n5 < n2) {
            n4 = n3;
            if (string.charAt(n5) == '\"') {
                n4 = n3;
                if (n % 2 == 0) {
                    n4 = n3 + 1;
                }
            }
            n = string.charAt(n5) == '\\' ? ++n : 0;
            ++n5;
            n3 = n4;
        }
        return n3;
    }

    private static boolean escaped(int n, int n2, String string) {
        boolean bl = true;
        if (n == 1 && string.charAt(n - 1) == '\\') {
            return true;
        }
        if (n > 1 && string.charAt(n - 1) == '\\' && string.charAt(n - 2) != '\\') {
            return true;
        }
        if (n > 1 && string.charAt(n - 1) == '\\' && string.charAt(n - 2) == '\\') {
            int n3 = 0;
            --n;
            while (n >= n2) {
                int n4 = n3;
                if (string.charAt(n) == '\\') {
                    n4 = n3 + 1;
                }
                --n;
                n3 = n4;
            }
            if (n3 % 2 == 0) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    private DerValue findAttribute(ObjectIdentifier objectIdentifier) {
        if (this.names != null) {
            Object object;
            for (int i = 0; i < ((RDN[])(object = this.names)).length; ++i) {
                if ((object = object[i].findAttribute(objectIdentifier)) == null) continue;
                return object;
            }
        }
        return null;
    }

    private void generateDN() {
        RDN[] arrrDN = this.names;
        if (arrrDN.length == 1) {
            this.dn = arrrDN[0].toString();
            return;
        }
        StringBuilder stringBuilder = new StringBuilder(48);
        arrrDN = this.names;
        if (arrrDN != null) {
            for (int i = arrrDN.length - 1; i >= 0; --i) {
                if (i != this.names.length - 1) {
                    stringBuilder.append(", ");
                }
                stringBuilder.append(this.names[i].toString());
            }
        }
        this.dn = stringBuilder.toString();
    }

    private String generateRFC1779DN(Map<String, String> map) {
        RDN[] arrrDN = this.names;
        if (arrrDN.length == 1) {
            return arrrDN[0].toRFC1779String(map);
        }
        StringBuilder stringBuilder = new StringBuilder(48);
        arrrDN = this.names;
        if (arrrDN != null) {
            for (int i = arrrDN.length - 1; i >= 0; --i) {
                if (i != this.names.length - 1) {
                    stringBuilder.append(", ");
                }
                stringBuilder.append(this.names[i].toRFC1779String(map));
            }
        }
        return stringBuilder.toString();
    }

    private String generateRFC2253DN(Map<String, String> map) {
        if (this.names.length == 0) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder(48);
        for (int i = this.names.length - 1; i >= 0; --i) {
            if (i < this.names.length - 1) {
                stringBuilder.append(',');
            }
            stringBuilder.append(this.names[i].toRFC2253String(map));
        }
        return stringBuilder.toString();
    }

    private String getString(DerValue derValue) throws IOException {
        if (derValue == null) {
            return null;
        }
        CharSequence charSequence = derValue.getAsString();
        if (charSequence != null) {
            return charSequence;
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("not a DER string encoding, ");
        ((StringBuilder)charSequence).append(derValue.tag);
        throw new IOException(((StringBuilder)charSequence).toString());
    }

    static ObjectIdentifier intern(ObjectIdentifier objectIdentifier) {
        ObjectIdentifier objectIdentifier2 = internedOIDs.putIfAbsent(objectIdentifier, objectIdentifier);
        if (objectIdentifier2 != null) {
            objectIdentifier = objectIdentifier2;
        }
        return objectIdentifier;
    }

    private boolean isWithinSubtree(X500Name x500Name) {
        if (this == x500Name) {
            return true;
        }
        if (x500Name == null) {
            return false;
        }
        RDN[] arrrDN = x500Name.names;
        if (arrrDN.length == 0) {
            return true;
        }
        RDN[] arrrDN2 = this.names;
        if (arrrDN2.length == 0) {
            return false;
        }
        if (arrrDN2.length < arrrDN.length) {
            return false;
        }
        for (int i = 0; i < (arrrDN2 = x500Name.names).length; ++i) {
            if (this.names[i].equals(arrrDN2[i])) continue;
            return false;
        }
        return true;
    }

    private void parseDER(DerInputStream object) throws IOException {
        byte[] arrby = object.toByteArray();
        try {
            object = object.getSequence(5);
        }
        catch (IOException iOException) {
            object = arrby == null ? null : new DerInputStream(new DerValue(48, arrby).toByteArray()).getSequence(5);
        }
        if (object == null) {
            this.names = new RDN[0];
        } else {
            this.names = new RDN[((DerValue[])object).length];
            for (int i = 0; i < ((DerValue[])object).length; ++i) {
                this.names[i] = new RDN(object[i]);
            }
        }
    }

    private void parseDN(String string, Map<String, String> map) throws IOException {
        if (string != null && string.length() != 0) {
            this.checkNoNewLinesNorTabsAtBeginningOfDN(string);
            ArrayList<RDN> arrayList = new ArrayList<RDN>();
            int n = 0;
            int n2 = 0;
            int n3 = 0;
            int n4 = string.indexOf(44);
            int n5 = string.indexOf(59);
            do {
                if (n4 < 0 && n5 < 0) {
                    arrayList.add(new RDN(string.substring(n), map));
                    Collections.reverse(arrayList);
                    this.names = arrayList.toArray(new RDN[arrayList.size()]);
                    return;
                }
                if (n5 < 0) {
                    n5 = n4;
                } else if (n4 >= 0) {
                    n5 = Math.min(n4, n5);
                }
                int n6 = n2 + X500Name.countQuotes(string, n3, n5);
                n2 = n;
                n4 = n6;
                if (n5 >= 0) {
                    n2 = n;
                    n4 = n6;
                    if (n6 != 1) {
                        n2 = n;
                        n4 = n6;
                        if (!X500Name.escaped(n5, n3, string)) {
                            arrayList.add(new RDN(string.substring(n, n5), map));
                            n2 = n5 + 1;
                            n4 = 0;
                        }
                    }
                }
                n3 = n5 + 1;
                n6 = string.indexOf(44, n3);
                n5 = string.indexOf(59, n3);
                n = n2;
                n2 = n4;
                n4 = n6;
            } while (true);
        }
        this.names = new RDN[0];
    }

    private void parseRFC2253DN(String string) throws IOException {
        if (string.length() == 0) {
            this.names = new RDN[0];
            return;
        }
        ArrayList<RDN> arrayList = new ArrayList<RDN>();
        int n = 0;
        int n2 = 0;
        int n3 = string.indexOf(44);
        while (n3 >= 0) {
            int n4 = n;
            if (n3 > 0) {
                n4 = n;
                if (!X500Name.escaped(n3, n2, string)) {
                    arrayList.add(new RDN(string.substring(n, n3), "RFC2253"));
                    n4 = n3 + 1;
                }
            }
            n2 = n3 + 1;
            n3 = string.indexOf(44, n2);
            n = n4;
        }
        arrayList.add(new RDN(string.substring(n), "RFC2253"));
        Collections.reverse(arrayList);
        this.names = arrayList.toArray(new RDN[arrayList.size()]);
    }

    public List<AVA> allAvas() {
        RDN[] arrrDN;
        Object object = arrrDN = this.allAvaList;
        if (arrrDN == null) {
            object = new ArrayList<AVA>();
            for (int i = 0; i < (arrrDN = this.names).length; ++i) {
                object.addAll(arrrDN[i].avas());
            }
            this.allAvaList = object = Collections.unmodifiableList(object);
        }
        return object;
    }

    public X500Principal asX500Principal() {
        if (this.x500Principal == null) {
            try {
                this.x500Principal = principalConstructor.newInstance(this);
            }
            catch (Exception exception) {
                throw new RuntimeException("Unexpected exception", exception);
            }
        }
        return this.x500Principal;
    }

    public int avaSize() {
        return this.allAvas().size();
    }

    public X500Name commonAncestor(X500Name object) {
        if (object == null) {
            return null;
        }
        int n = object.names.length;
        int n2 = this.names.length;
        if (n2 != 0 && n != 0) {
            if (n2 >= n) {
                n2 = n;
            }
            for (n = 0; n < n2; ++n) {
                if (this.names[n].equals(object.names[n])) continue;
                if (n != 0) break;
                return null;
            }
            object = new RDN[n];
            for (n2 = 0; n2 < n; ++n2) {
                object[n2] = this.names[n2];
            }
            try {
                object = new X500Name((RDN[])object);
                return object;
            }
            catch (IOException iOException) {
                return null;
            }
        }
        return null;
    }

    @Override
    public int constrains(GeneralNameInterface generalNameInterface) throws UnsupportedOperationException {
        int n = generalNameInterface == null ? -1 : (generalNameInterface.getType() != 4 ? -1 : (((X500Name)(generalNameInterface = (X500Name)generalNameInterface)).equals(this) ? 0 : (((X500Name)generalNameInterface).names.length == 0 ? 2 : (this.names.length == 0 ? 1 : (X500Name.super.isWithinSubtree(this) ? 1 : (this.isWithinSubtree((X500Name)generalNameInterface) ? 2 : 3))))));
        return n;
    }

    @Deprecated
    public void emit(DerOutputStream derOutputStream) throws IOException {
        this.encode(derOutputStream);
    }

    @Override
    public void encode(DerOutputStream derOutputStream) throws IOException {
        RDN[] arrrDN;
        DerOutputStream derOutputStream2 = new DerOutputStream();
        for (int i = 0; i < (arrrDN = this.names).length; ++i) {
            arrrDN[i].encode(derOutputStream2);
        }
        derOutputStream.write((byte)48, derOutputStream2);
    }

    @Override
    public boolean equals(Object object) {
        Object object2;
        if (this == object) {
            return true;
        }
        if (!(object instanceof X500Name)) {
            return false;
        }
        object = (X500Name)object;
        Object object3 = this.canonicalDn;
        if (object3 != null && (object2 = ((X500Name)object).canonicalDn) != null) {
            return ((String)object3).equals(object2);
        }
        int n = this.names.length;
        if (n != ((X500Name)object).names.length) {
            return false;
        }
        for (int i = 0; i < n; ++i) {
            object3 = this.names[i];
            object2 = ((X500Name)object).names[i];
            if (((RDN)object3).assertion.length == ((RDN)object2).assertion.length) continue;
            return false;
        }
        return this.getRFC2253CanonicalName().equals(((X500Name)object).getRFC2253CanonicalName());
    }

    public DerValue findMostSpecificAttribute(ObjectIdentifier objectIdentifier) {
        Object object = this.names;
        if (object != null) {
            for (int i = ((RDN[])object).length - 1; i >= 0; --i) {
                object = this.names[i].findAttribute(objectIdentifier);
                if (object == null) continue;
                return object;
            }
        }
        return null;
    }

    public String getCommonName() throws IOException {
        return this.getString(this.findAttribute(commonName_oid));
    }

    public String getCountry() throws IOException {
        return this.getString(this.findAttribute(countryName_oid));
    }

    public String getDNQualifier() throws IOException {
        return this.getString(this.findAttribute(DNQUALIFIER_OID));
    }

    public String getDomain() throws IOException {
        return this.getString(this.findAttribute(DOMAIN_COMPONENT_OID));
    }

    public byte[] getEncoded() throws IOException {
        return (byte[])this.getEncodedInternal().clone();
    }

    public byte[] getEncodedInternal() throws IOException {
        if (this.encoded == null) {
            RDN[] arrrDN;
            DerOutputStream derOutputStream = new DerOutputStream();
            DerOutputStream derOutputStream2 = new DerOutputStream();
            for (int i = 0; i < (arrrDN = this.names).length; ++i) {
                arrrDN[i].encode(derOutputStream2);
            }
            derOutputStream.write((byte)48, derOutputStream2);
            this.encoded = derOutputStream.toByteArray();
        }
        return this.encoded;
    }

    public String getGeneration() throws IOException {
        return this.getString(this.findAttribute(GENERATIONQUALIFIER_OID));
    }

    public String getGivenName() throws IOException {
        return this.getString(this.findAttribute(GIVENNAME_OID));
    }

    public String getIP() throws IOException {
        return this.getString(this.findAttribute(ipAddress_oid));
    }

    public String getInitials() throws IOException {
        return this.getString(this.findAttribute(INITIALS_OID));
    }

    public String getLocality() throws IOException {
        return this.getString(this.findAttribute(localityName_oid));
    }

    @Override
    public String getName() {
        return this.toString();
    }

    public String getOrganization() throws IOException {
        return this.getString(this.findAttribute(orgName_oid));
    }

    public String getOrganizationalUnit() throws IOException {
        return this.getString(this.findAttribute(orgUnitName_oid));
    }

    public String getRFC1779Name() {
        return this.getRFC1779Name(Collections.emptyMap());
    }

    public String getRFC1779Name(Map<String, String> map) throws IllegalArgumentException {
        if (map.isEmpty()) {
            String string = this.rfc1779Dn;
            if (string != null) {
                return string;
            }
            this.rfc1779Dn = this.generateRFC1779DN(map);
            return this.rfc1779Dn;
        }
        return this.generateRFC1779DN(map);
    }

    public String getRFC2253CanonicalName() {
        CharSequence charSequence = this.canonicalDn;
        if (charSequence != null) {
            return charSequence;
        }
        if (this.names.length == 0) {
            this.canonicalDn = "";
            return this.canonicalDn;
        }
        charSequence = new StringBuilder(48);
        for (int i = this.names.length - 1; i >= 0; --i) {
            if (i < this.names.length - 1) {
                ((StringBuilder)charSequence).append(',');
            }
            ((StringBuilder)charSequence).append(this.names[i].toRFC2253String(true));
        }
        this.canonicalDn = ((StringBuilder)charSequence).toString();
        return this.canonicalDn;
    }

    public String getRFC2253Name() {
        return this.getRFC2253Name(Collections.emptyMap());
    }

    public String getRFC2253Name(Map<String, String> map) {
        if (map.isEmpty()) {
            String string = this.rfc2253Dn;
            if (string != null) {
                return string;
            }
            this.rfc2253Dn = this.generateRFC2253DN(map);
            return this.rfc2253Dn;
        }
        return this.generateRFC2253DN(map);
    }

    public String getState() throws IOException {
        return this.getString(this.findAttribute(stateName_oid));
    }

    public String getSurname() throws IOException {
        return this.getString(this.findAttribute(SURNAME_OID));
    }

    @Override
    public int getType() {
        return 4;
    }

    @Override
    public int hashCode() {
        return this.getRFC2253CanonicalName().hashCode();
    }

    public boolean isEmpty() {
        int n = this.names.length;
        for (int i = 0; i < n; ++i) {
            if (this.names[i].assertion.length == 0) continue;
            return false;
        }
        return true;
    }

    public List<RDN> rdns() {
        List<RDN> list;
        List<RDN> list2 = list = this.rdnList;
        if (list == null) {
            this.rdnList = list2 = Collections.unmodifiableList(Arrays.asList(this.names));
        }
        return list2;
    }

    public int size() {
        return this.names.length;
    }

    @Override
    public int subtreeDepth() throws UnsupportedOperationException {
        return this.names.length;
    }

    @Override
    public String toString() {
        if (this.dn == null) {
            this.generateDN();
        }
        return this.dn;
    }

}

