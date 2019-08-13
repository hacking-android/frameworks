/*
 * Decompiled with CFR 0.145.
 */
package sun.security.x509;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import sun.security.pkcs.PKCS9Attribute;
import sun.security.util.ObjectIdentifier;
import sun.security.x509.X500Name;

class AVAKeyword {
    private static final Map<String, AVAKeyword> keywordMap;
    private static final Map<ObjectIdentifier, AVAKeyword> oidMap;
    private String keyword;
    private ObjectIdentifier oid;
    private boolean rfc1779Compliant;
    private boolean rfc2253Compliant;

    static {
        oidMap = new HashMap<ObjectIdentifier, AVAKeyword>();
        keywordMap = new HashMap<String, AVAKeyword>();
        new AVAKeyword("CN", X500Name.commonName_oid, true, true);
        new AVAKeyword("C", X500Name.countryName_oid, true, true);
        new AVAKeyword("L", X500Name.localityName_oid, true, true);
        new AVAKeyword("S", X500Name.stateName_oid, false, false);
        new AVAKeyword("ST", X500Name.stateName_oid, true, true);
        new AVAKeyword("O", X500Name.orgName_oid, true, true);
        new AVAKeyword("OU", X500Name.orgUnitName_oid, true, true);
        new AVAKeyword("T", X500Name.title_oid, false, false);
        new AVAKeyword("IP", X500Name.ipAddress_oid, false, false);
        new AVAKeyword("STREET", X500Name.streetAddress_oid, true, true);
        new AVAKeyword("DC", X500Name.DOMAIN_COMPONENT_OID, false, true);
        new AVAKeyword("DNQUALIFIER", X500Name.DNQUALIFIER_OID, false, false);
        new AVAKeyword("DNQ", X500Name.DNQUALIFIER_OID, false, false);
        new AVAKeyword("SURNAME", X500Name.SURNAME_OID, false, false);
        new AVAKeyword("GIVENNAME", X500Name.GIVENNAME_OID, false, false);
        new AVAKeyword("INITIALS", X500Name.INITIALS_OID, false, false);
        new AVAKeyword("GENERATION", X500Name.GENERATIONQUALIFIER_OID, false, false);
        new AVAKeyword("EMAIL", PKCS9Attribute.EMAIL_ADDRESS_OID, false, false);
        new AVAKeyword("EMAILADDRESS", PKCS9Attribute.EMAIL_ADDRESS_OID, false, false);
        new AVAKeyword("UID", X500Name.userid_oid, false, true);
        new AVAKeyword("SERIALNUMBER", X500Name.SERIALNUMBER_OID, false, false);
    }

    private AVAKeyword(String string, ObjectIdentifier objectIdentifier, boolean bl, boolean bl2) {
        this.keyword = string;
        this.oid = objectIdentifier;
        this.rfc1779Compliant = bl;
        this.rfc2253Compliant = bl2;
        oidMap.put(objectIdentifier, this);
        keywordMap.put(string, this);
    }

    static String getKeyword(ObjectIdentifier objectIdentifier, int n) {
        return AVAKeyword.getKeyword(objectIdentifier, n, Collections.emptyMap());
    }

    static String getKeyword(ObjectIdentifier object, int n, Map<String, String> object2) {
        String string = ((ObjectIdentifier)object).toString();
        if ((object2 = object2.get(string)) == null) {
            if ((object = oidMap.get(object)) != null && AVAKeyword.super.isCompliant(n)) {
                return ((AVAKeyword)object).keyword;
            }
            if (n == 3) {
                return string;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("OID.");
            ((StringBuilder)object).append(string);
            return ((StringBuilder)object).toString();
        }
        if (((String)object2).length() != 0) {
            object = ((String)object2).trim();
            n = ((String)object).charAt(0);
            if (n >= 65 && n <= 122 && (n <= 90 || n >= 97)) {
                for (n = 1; n < ((String)object).length(); ++n) {
                    char c = ((String)object).charAt(n);
                    if (c >= 'A' && c <= 'z' && (c <= 'Z' || c >= 'a') || c >= '0' && c <= '9' || c == '_') {
                        continue;
                    }
                    throw new IllegalArgumentException("keyword character is not a letter, digit, or underscore");
                }
                return object;
            }
            throw new IllegalArgumentException("keyword does not start with letter");
        }
        throw new IllegalArgumentException("keyword cannot be empty");
    }

    static ObjectIdentifier getOID(String charSequence, int n, Map<String, String> object) throws IOException {
        charSequence = ((String)charSequence).toUpperCase(Locale.ENGLISH);
        if (n == 3) {
            if (((String)charSequence).startsWith(" ") || ((String)charSequence).endsWith(" ")) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Invalid leading or trailing space in keyword \"");
                ((StringBuilder)object).append((String)charSequence);
                ((StringBuilder)object).append("\"");
                throw new IOException(((StringBuilder)object).toString());
            }
        } else {
            charSequence = ((String)charSequence).trim();
        }
        object = object.get(charSequence);
        if (object == null) {
            int n2;
            object = keywordMap.get(charSequence);
            if (object != null && AVAKeyword.super.isCompliant(n)) {
                return ((AVAKeyword)object).oid;
            }
            object = charSequence;
            if (n == 1) {
                object = charSequence;
                if (((String)charSequence).startsWith("OID.")) {
                    object = ((String)charSequence).substring(4);
                }
            }
            n = n2 = 0;
            if (((String)object).length() != 0) {
                char c = ((String)object).charAt(0);
                n = n2;
                if (c >= '0') {
                    n = n2;
                    if (c <= '9') {
                        n = 1;
                    }
                }
            }
            if (n != 0) {
                return new ObjectIdentifier((String)object);
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Invalid keyword \"");
            ((StringBuilder)charSequence).append((String)object);
            ((StringBuilder)charSequence).append("\"");
            throw new IOException(((StringBuilder)charSequence).toString());
        }
        return new ObjectIdentifier((String)object);
    }

    static boolean hasKeyword(ObjectIdentifier object, int n) {
        if ((object = oidMap.get(object)) == null) {
            return false;
        }
        return AVAKeyword.super.isCompliant(n);
    }

    private boolean isCompliant(int n) {
        if (n != 1) {
            if (n != 2) {
                if (n == 3) {
                    return this.rfc2253Compliant;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Invalid standard ");
                stringBuilder.append(n);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            return this.rfc1779Compliant;
        }
        return true;
    }
}

