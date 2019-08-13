/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.compat.UnsupportedAppUsage
 */
package com.android.org.bouncycastle.asn1.x509;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.org.bouncycastle.asn1.ASN1Object;
import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1Sequence;
import com.android.org.bouncycastle.asn1.ASN1Set;
import com.android.org.bouncycastle.asn1.ASN1String;
import com.android.org.bouncycastle.asn1.ASN1TaggedObject;
import com.android.org.bouncycastle.asn1.DERSequence;
import com.android.org.bouncycastle.asn1.DERSet;
import com.android.org.bouncycastle.asn1.DERUniversalString;
import com.android.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import com.android.org.bouncycastle.asn1.x500.X500Name;
import com.android.org.bouncycastle.asn1.x509.X509DefaultEntryConverter;
import com.android.org.bouncycastle.asn1.x509.X509NameEntryConverter;
import com.android.org.bouncycastle.asn1.x509.X509NameTokenizer;
import com.android.org.bouncycastle.asn1.x509.X509ObjectIdentifiers;
import com.android.org.bouncycastle.util.Strings;
import com.android.org.bouncycastle.util.encoders.Hex;
import dalvik.annotation.compat.UnsupportedAppUsage;
import java.io.IOException;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

public class X509Name
extends ASN1Object {
    public static final ASN1ObjectIdentifier BUSINESS_CATEGORY;
    public static final ASN1ObjectIdentifier C;
    @UnsupportedAppUsage
    public static final ASN1ObjectIdentifier CN;
    public static final ASN1ObjectIdentifier COUNTRY_OF_CITIZENSHIP;
    public static final ASN1ObjectIdentifier COUNTRY_OF_RESIDENCE;
    public static final ASN1ObjectIdentifier DATE_OF_BIRTH;
    public static final ASN1ObjectIdentifier DC;
    public static final ASN1ObjectIdentifier DMD_NAME;
    public static final ASN1ObjectIdentifier DN_QUALIFIER;
    public static final Hashtable DefaultLookUp;
    public static boolean DefaultReverse;
    public static final Hashtable DefaultSymbols;
    public static final ASN1ObjectIdentifier E;
    public static final ASN1ObjectIdentifier EmailAddress;
    private static final Boolean FALSE;
    public static final ASN1ObjectIdentifier GENDER;
    public static final ASN1ObjectIdentifier GENERATION;
    public static final ASN1ObjectIdentifier GIVENNAME;
    public static final ASN1ObjectIdentifier INITIALS;
    public static final ASN1ObjectIdentifier L;
    public static final ASN1ObjectIdentifier NAME;
    public static final ASN1ObjectIdentifier NAME_AT_BIRTH;
    public static final ASN1ObjectIdentifier O;
    public static final Hashtable OIDLookUp;
    public static final ASN1ObjectIdentifier OU;
    public static final ASN1ObjectIdentifier PLACE_OF_BIRTH;
    public static final ASN1ObjectIdentifier POSTAL_ADDRESS;
    public static final ASN1ObjectIdentifier POSTAL_CODE;
    public static final ASN1ObjectIdentifier PSEUDONYM;
    public static final Hashtable RFC1779Symbols;
    public static final Hashtable RFC2253Symbols;
    public static final ASN1ObjectIdentifier SERIALNUMBER;
    public static final ASN1ObjectIdentifier SN;
    public static final ASN1ObjectIdentifier ST;
    public static final ASN1ObjectIdentifier STREET;
    public static final ASN1ObjectIdentifier SURNAME;
    public static final Hashtable SymbolLookUp;
    public static final ASN1ObjectIdentifier T;
    public static final ASN1ObjectIdentifier TELEPHONE_NUMBER;
    private static final Boolean TRUE;
    public static final ASN1ObjectIdentifier UID;
    public static final ASN1ObjectIdentifier UNIQUE_IDENTIFIER;
    public static final ASN1ObjectIdentifier UnstructuredAddress;
    public static final ASN1ObjectIdentifier UnstructuredName;
    private Vector added = new Vector();
    private X509NameEntryConverter converter = null;
    private int hashCodeValue;
    private boolean isHashCodeCalculated;
    private Vector ordering = new Vector();
    private ASN1Sequence seq;
    private Vector values = new Vector();

    static {
        C = new ASN1ObjectIdentifier("2.5.4.6");
        O = new ASN1ObjectIdentifier("2.5.4.10");
        OU = new ASN1ObjectIdentifier("2.5.4.11");
        T = new ASN1ObjectIdentifier("2.5.4.12");
        CN = new ASN1ObjectIdentifier("2.5.4.3");
        SN = new ASN1ObjectIdentifier("2.5.4.5");
        STREET = new ASN1ObjectIdentifier("2.5.4.9");
        SERIALNUMBER = SN;
        L = new ASN1ObjectIdentifier("2.5.4.7");
        ST = new ASN1ObjectIdentifier("2.5.4.8");
        SURNAME = new ASN1ObjectIdentifier("2.5.4.4");
        GIVENNAME = new ASN1ObjectIdentifier("2.5.4.42");
        INITIALS = new ASN1ObjectIdentifier("2.5.4.43");
        GENERATION = new ASN1ObjectIdentifier("2.5.4.44");
        UNIQUE_IDENTIFIER = new ASN1ObjectIdentifier("2.5.4.45");
        BUSINESS_CATEGORY = new ASN1ObjectIdentifier("2.5.4.15");
        POSTAL_CODE = new ASN1ObjectIdentifier("2.5.4.17");
        DN_QUALIFIER = new ASN1ObjectIdentifier("2.5.4.46");
        PSEUDONYM = new ASN1ObjectIdentifier("2.5.4.65");
        DATE_OF_BIRTH = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.9.1");
        PLACE_OF_BIRTH = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.9.2");
        GENDER = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.9.3");
        COUNTRY_OF_CITIZENSHIP = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.9.4");
        COUNTRY_OF_RESIDENCE = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.9.5");
        NAME_AT_BIRTH = new ASN1ObjectIdentifier("1.3.36.8.3.14");
        POSTAL_ADDRESS = new ASN1ObjectIdentifier("2.5.4.16");
        DMD_NAME = new ASN1ObjectIdentifier("2.5.4.54");
        TELEPHONE_NUMBER = X509ObjectIdentifiers.id_at_telephoneNumber;
        NAME = X509ObjectIdentifiers.id_at_name;
        EmailAddress = PKCSObjectIdentifiers.pkcs_9_at_emailAddress;
        UnstructuredName = PKCSObjectIdentifiers.pkcs_9_at_unstructuredName;
        UnstructuredAddress = PKCSObjectIdentifiers.pkcs_9_at_unstructuredAddress;
        E = EmailAddress;
        DC = new ASN1ObjectIdentifier("0.9.2342.19200300.100.1.25");
        UID = new ASN1ObjectIdentifier("0.9.2342.19200300.100.1.1");
        DefaultReverse = false;
        DefaultSymbols = new Hashtable();
        RFC2253Symbols = new Hashtable();
        RFC1779Symbols = new Hashtable();
        DefaultLookUp = new Hashtable();
        OIDLookUp = DefaultSymbols;
        SymbolLookUp = DefaultLookUp;
        TRUE = Boolean.TRUE;
        FALSE = Boolean.FALSE;
        DefaultSymbols.put(C, "C");
        DefaultSymbols.put(O, "O");
        DefaultSymbols.put(T, "T");
        DefaultSymbols.put(OU, "OU");
        DefaultSymbols.put(CN, "CN");
        DefaultSymbols.put(L, "L");
        DefaultSymbols.put(ST, "ST");
        DefaultSymbols.put(SN, "SERIALNUMBER");
        DefaultSymbols.put(EmailAddress, "E");
        DefaultSymbols.put(DC, "DC");
        DefaultSymbols.put(UID, "UID");
        DefaultSymbols.put(STREET, "STREET");
        DefaultSymbols.put(SURNAME, "SURNAME");
        DefaultSymbols.put(GIVENNAME, "GIVENNAME");
        DefaultSymbols.put(INITIALS, "INITIALS");
        DefaultSymbols.put(GENERATION, "GENERATION");
        DefaultSymbols.put(UnstructuredAddress, "unstructuredAddress");
        DefaultSymbols.put(UnstructuredName, "unstructuredName");
        DefaultSymbols.put(UNIQUE_IDENTIFIER, "UniqueIdentifier");
        DefaultSymbols.put(DN_QUALIFIER, "DN");
        DefaultSymbols.put(PSEUDONYM, "Pseudonym");
        DefaultSymbols.put(POSTAL_ADDRESS, "PostalAddress");
        DefaultSymbols.put(NAME_AT_BIRTH, "NameAtBirth");
        DefaultSymbols.put(COUNTRY_OF_CITIZENSHIP, "CountryOfCitizenship");
        DefaultSymbols.put(COUNTRY_OF_RESIDENCE, "CountryOfResidence");
        DefaultSymbols.put(GENDER, "Gender");
        DefaultSymbols.put(PLACE_OF_BIRTH, "PlaceOfBirth");
        DefaultSymbols.put(DATE_OF_BIRTH, "DateOfBirth");
        DefaultSymbols.put(POSTAL_CODE, "PostalCode");
        DefaultSymbols.put(BUSINESS_CATEGORY, "BusinessCategory");
        DefaultSymbols.put(TELEPHONE_NUMBER, "TelephoneNumber");
        DefaultSymbols.put(NAME, "Name");
        RFC2253Symbols.put(C, "C");
        RFC2253Symbols.put(O, "O");
        RFC2253Symbols.put(OU, "OU");
        RFC2253Symbols.put(CN, "CN");
        RFC2253Symbols.put(L, "L");
        RFC2253Symbols.put(ST, "ST");
        RFC2253Symbols.put(STREET, "STREET");
        RFC2253Symbols.put(DC, "DC");
        RFC2253Symbols.put(UID, "UID");
        RFC1779Symbols.put(C, "C");
        RFC1779Symbols.put(O, "O");
        RFC1779Symbols.put(OU, "OU");
        RFC1779Symbols.put(CN, "CN");
        RFC1779Symbols.put(L, "L");
        RFC1779Symbols.put(ST, "ST");
        RFC1779Symbols.put(STREET, "STREET");
        DefaultLookUp.put("c", C);
        DefaultLookUp.put("o", O);
        DefaultLookUp.put("t", T);
        DefaultLookUp.put("ou", OU);
        DefaultLookUp.put("cn", CN);
        DefaultLookUp.put("l", L);
        DefaultLookUp.put("st", ST);
        DefaultLookUp.put("sn", SN);
        DefaultLookUp.put("serialnumber", SN);
        DefaultLookUp.put("street", STREET);
        DefaultLookUp.put("emailaddress", E);
        DefaultLookUp.put("dc", DC);
        DefaultLookUp.put("e", E);
        DefaultLookUp.put("uid", UID);
        DefaultLookUp.put("surname", SURNAME);
        DefaultLookUp.put("givenname", GIVENNAME);
        DefaultLookUp.put("initials", INITIALS);
        DefaultLookUp.put("generation", GENERATION);
        DefaultLookUp.put("unstructuredaddress", UnstructuredAddress);
        DefaultLookUp.put("unstructuredname", UnstructuredName);
        DefaultLookUp.put("uniqueidentifier", UNIQUE_IDENTIFIER);
        DefaultLookUp.put("dn", DN_QUALIFIER);
        DefaultLookUp.put("pseudonym", PSEUDONYM);
        DefaultLookUp.put("postaladdress", POSTAL_ADDRESS);
        DefaultLookUp.put("nameofbirth", NAME_AT_BIRTH);
        DefaultLookUp.put("countryofcitizenship", COUNTRY_OF_CITIZENSHIP);
        DefaultLookUp.put("countryofresidence", COUNTRY_OF_RESIDENCE);
        DefaultLookUp.put("gender", GENDER);
        DefaultLookUp.put("placeofbirth", PLACE_OF_BIRTH);
        DefaultLookUp.put("dateofbirth", DATE_OF_BIRTH);
        DefaultLookUp.put("postalcode", POSTAL_CODE);
        DefaultLookUp.put("businesscategory", BUSINESS_CATEGORY);
        DefaultLookUp.put("telephonenumber", TELEPHONE_NUMBER);
        DefaultLookUp.put("name", NAME);
    }

    protected X509Name() {
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @UnsupportedAppUsage
    public X509Name(ASN1Sequence object) {
        this.seq = object;
        Enumeration enumeration = ((ASN1Sequence)object).getObjects();
        block2 : do {
            if (!enumeration.hasMoreElements()) return;
            ASN1Set aSN1Set = ASN1Set.getInstance(((ASN1Encodable)enumeration.nextElement()).toASN1Primitive());
            int i = 0;
            do {
                Object object2;
                StringBuilder stringBuilder;
                if (i >= aSN1Set.size()) continue block2;
                object = ASN1Sequence.getInstance(aSN1Set.getObjectAt(i).toASN1Primitive());
                if (((ASN1Sequence)object).size() != 2) throw new IllegalArgumentException("badly sized pair");
                this.ordering.addElement(ASN1ObjectIdentifier.getInstance(((ASN1Sequence)object).getObjectAt(0)));
                object = ((ASN1Sequence)object).getObjectAt(1);
                if (object instanceof ASN1String && !(object instanceof DERUniversalString)) {
                    object2 = ((ASN1String)object).getString();
                    if (((String)object2).length() > 0 && ((String)object2).charAt(0) == '#') {
                        object = this.values;
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("\\");
                        stringBuilder.append((String)object2);
                        ((Vector)object).addElement(stringBuilder.toString());
                    } else {
                        this.values.addElement(object2);
                    }
                } else {
                    object2 = this.values;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("#");
                    stringBuilder.append(this.bytesToString(Hex.encode(object.toASN1Primitive().getEncoded("DER"))));
                    ((Vector)object2).addElement(stringBuilder.toString());
                }
                object2 = this.added;
                object = i != 0 ? TRUE : FALSE;
                ((Vector)object2).addElement(object);
                ++i;
            } while (true);
            break;
        } while (true);
        catch (IOException iOException) {
            throw new IllegalArgumentException("cannot encode value");
        }
    }

    @UnsupportedAppUsage
    public X509Name(String string) {
        this(DefaultReverse, DefaultLookUp, string);
    }

    public X509Name(String string, X509NameEntryConverter x509NameEntryConverter) {
        this(DefaultReverse, DefaultLookUp, string, x509NameEntryConverter);
    }

    public X509Name(Hashtable hashtable) {
        this(null, hashtable);
    }

    public X509Name(Vector vector, Hashtable hashtable) {
        this(vector, hashtable, (X509NameEntryConverter)new X509DefaultEntryConverter());
    }

    public X509Name(Vector object, Hashtable serializable, X509NameEntryConverter x509NameEntryConverter) {
        int n;
        this.converter = x509NameEntryConverter;
        if (object != null) {
            for (n = 0; n != ((Vector)object).size(); ++n) {
                this.ordering.addElement(((Vector)object).elementAt(n));
                this.added.addElement(FALSE);
            }
        } else {
            object = ((Hashtable)serializable).keys();
            while (object.hasMoreElements()) {
                this.ordering.addElement(object.nextElement());
                this.added.addElement(FALSE);
            }
        }
        for (n = 0; n != this.ordering.size(); ++n) {
            object = (ASN1ObjectIdentifier)this.ordering.elementAt(n);
            if (((Hashtable)serializable).get(object) != null) {
                this.values.addElement(((Hashtable)serializable).get(object));
                continue;
            }
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("No attribute for object id - ");
            ((StringBuilder)serializable).append(((ASN1ObjectIdentifier)object).getId());
            ((StringBuilder)serializable).append(" - passed to distinguished name");
            throw new IllegalArgumentException(((StringBuilder)serializable).toString());
        }
    }

    public X509Name(Vector vector, Vector vector2) {
        this(vector, vector2, (X509NameEntryConverter)new X509DefaultEntryConverter());
    }

    public X509Name(Vector vector, Vector vector2, X509NameEntryConverter x509NameEntryConverter) {
        this.converter = x509NameEntryConverter;
        if (vector.size() == vector2.size()) {
            for (int i = 0; i < vector.size(); ++i) {
                this.ordering.addElement(vector.elementAt(i));
                this.values.addElement(vector2.elementAt(i));
                this.added.addElement(FALSE);
            }
            return;
        }
        throw new IllegalArgumentException("oids vector must be same length as values.");
    }

    public X509Name(boolean bl, String string) {
        this(bl, DefaultLookUp, string);
    }

    public X509Name(boolean bl, String string, X509NameEntryConverter x509NameEntryConverter) {
        this(bl, DefaultLookUp, string, x509NameEntryConverter);
    }

    public X509Name(boolean bl, Hashtable hashtable, String string) {
        this(bl, hashtable, string, new X509DefaultEntryConverter());
    }

    public X509Name(boolean bl, Hashtable cloneable, String vector, X509NameEntryConverter object) {
        this.converter = object;
        vector = new X509NameTokenizer((String)((Object)vector));
        while (((X509NameTokenizer)((Object)vector)).hasMoreTokens()) {
            object = ((X509NameTokenizer)((Object)vector)).nextToken();
            if (((String)object).indexOf(43) > 0) {
                object = new X509NameTokenizer((String)object, '+');
                this.addEntry((Hashtable)cloneable, ((X509NameTokenizer)object).nextToken(), FALSE);
                while (((X509NameTokenizer)object).hasMoreTokens()) {
                    this.addEntry((Hashtable)cloneable, ((X509NameTokenizer)object).nextToken(), TRUE);
                }
                continue;
            }
            this.addEntry((Hashtable)cloneable, (String)object, FALSE);
        }
        if (bl) {
            cloneable = new Vector();
            object = new Vector();
            vector = new Vector();
            int n = 1;
            for (int i = 0; i < this.ordering.size(); ++i) {
                if (((Boolean)this.added.elementAt(i)).booleanValue()) {
                    ((Vector)cloneable).insertElementAt(this.ordering.elementAt(i), n);
                    ((Vector)object).insertElementAt(this.values.elementAt(i), n);
                    vector.insertElementAt(this.added.elementAt(i), n);
                    ++n;
                    continue;
                }
                ((Vector)cloneable).insertElementAt(this.ordering.elementAt(i), 0);
                ((Vector)object).insertElementAt(this.values.elementAt(i), 0);
                vector.insertElementAt(this.added.elementAt(i), 0);
                n = 1;
            }
            this.ordering = cloneable;
            this.values = object;
            this.added = vector;
        }
    }

    private void addEntry(Hashtable object, String string, Boolean bl) {
        Object object2 = new X509NameTokenizer(string, '=');
        string = ((X509NameTokenizer)object2).nextToken();
        if (((X509NameTokenizer)object2).hasMoreTokens()) {
            object2 = ((X509NameTokenizer)object2).nextToken();
            object = this.decodeOID(string, (Hashtable)object);
            this.ordering.addElement(object);
            this.values.addElement(this.unescape((String)object2));
            this.added.addElement(bl);
            return;
        }
        throw new IllegalArgumentException("badly formatted directory string");
    }

    private void appendValue(StringBuffer stringBuffer, Hashtable object, ASN1ObjectIdentifier aSN1ObjectIdentifier, String string) {
        if ((object = (String)((Hashtable)object).get(aSN1ObjectIdentifier)) != null) {
            stringBuffer.append((String)object);
        } else {
            stringBuffer.append(aSN1ObjectIdentifier.getId());
        }
        stringBuffer.append('=');
        int n = stringBuffer.length();
        stringBuffer.append(string);
        int n2 = stringBuffer.length();
        int n3 = n;
        int n4 = n2;
        if (string.length() >= 2) {
            n3 = n;
            n4 = n2;
            if (string.charAt(0) == '\\') {
                n3 = n;
                n4 = n2;
                if (string.charAt(1) == '#') {
                    n3 = n + 2;
                    n4 = n2;
                }
            }
        }
        do {
            n = n4;
            if (n3 >= n4) break;
            n = n4++;
            if (stringBuffer.charAt(n3) != ' ') break;
            stringBuffer.insert(n3, "\\");
            n3 += 2;
        } while (true);
        do {
            n4 = n3;
            n2 = --n;
            if (n <= n3) break;
            n4 = n3;
            n2 = n;
            if (stringBuffer.charAt(n) != ' ') break;
            stringBuffer.insert(n, '\\');
        } while (true);
        block5 : while (n4 <= n2) {
            n3 = stringBuffer.charAt(n4);
            if (n3 != 34 && n3 != 92 && n3 != 43 && n3 != 44) {
                switch (n3) {
                    default: {
                        ++n4;
                        continue block5;
                    }
                    case 59: 
                    case 60: 
                    case 61: 
                    case 62: 
                }
            }
            stringBuffer.insert(n4, "\\");
            n4 += 2;
            ++n2;
        }
    }

    private String bytesToString(byte[] arrby) {
        char[] arrc = new char[arrby.length];
        for (int i = 0; i != arrc.length; ++i) {
            arrc[i] = (char)(arrby[i] & 255);
        }
        return new String(arrc);
    }

    private String canonicalize(String string) {
        String string2;
        string = string2 = Strings.toLowerCase(string.trim());
        if (string2.length() > 0) {
            string = string2;
            if (string2.charAt(0) == '#') {
                ASN1Primitive aSN1Primitive = this.decodeObject(string2);
                string = string2;
                if (aSN1Primitive instanceof ASN1String) {
                    string = Strings.toLowerCase(((ASN1String)((Object)aSN1Primitive)).getString().trim());
                }
            }
        }
        return string;
    }

    private ASN1ObjectIdentifier decodeOID(String string, Hashtable object) {
        if (Strings.toUpperCase(string = string.trim()).startsWith("OID.")) {
            return new ASN1ObjectIdentifier(string.substring(4));
        }
        if (string.charAt(0) >= '0' && string.charAt(0) <= '9') {
            return new ASN1ObjectIdentifier(string);
        }
        if ((object = (ASN1ObjectIdentifier)((Hashtable)object).get(Strings.toLowerCase(string))) != null) {
            return object;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Unknown object id - ");
        ((StringBuilder)object).append(string);
        ((StringBuilder)object).append(" - passed to distinguished name");
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    private ASN1Primitive decodeObject(String object) {
        try {
            object = ASN1Primitive.fromByteArray(Hex.decode(((String)object).substring(1)));
            return object;
        }
        catch (IOException iOException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("unknown encoding in name: ");
            ((StringBuilder)object).append(iOException);
            throw new IllegalStateException(((StringBuilder)object).toString());
        }
    }

    private boolean equivalentStrings(String string, String string2) {
        return (string = this.canonicalize(string)).equals(string2 = this.canonicalize(string2)) || this.stripInternalSpaces(string).equals(this.stripInternalSpaces(string2));
    }

    public static X509Name getInstance(ASN1TaggedObject aSN1TaggedObject, boolean bl) {
        return X509Name.getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, bl));
    }

    public static X509Name getInstance(Object object) {
        if (object != null && !(object instanceof X509Name)) {
            if (object instanceof X500Name) {
                return new X509Name(ASN1Sequence.getInstance(((X500Name)object).toASN1Primitive()));
            }
            return new X509Name(ASN1Sequence.getInstance(object));
        }
        return (X509Name)object;
    }

    private String stripInternalSpaces(String string) {
        StringBuffer stringBuffer = new StringBuffer();
        if (string.length() != 0) {
            char c = string.charAt(0);
            stringBuffer.append(c);
            char c2 = c;
            for (int i = 1; i < string.length(); ++i) {
                c = string.charAt(i);
                if (c2 != ' ' || c != ' ') {
                    stringBuffer.append(c);
                }
                c2 = c;
            }
        }
        return stringBuffer.toString();
    }

    private String unescape(String charSequence) {
        if (((String)charSequence).length() != 0 && (((String)charSequence).indexOf(92) >= 0 || ((String)charSequence).indexOf(34) >= 0)) {
            int n;
            char[] arrc = ((String)charSequence).toCharArray();
            int n2 = 0;
            int n3 = 0;
            charSequence = new StringBuffer(((String)charSequence).length());
            int n4 = n = 0;
            if (arrc[0] == '\\') {
                n4 = n;
                if (arrc[1] == '#') {
                    n4 = 2;
                    ((StringBuffer)charSequence).append("\\#");
                }
            }
            boolean bl = false;
            int n5 = 0;
            n = n4;
            n4 = n2;
            while (n != arrc.length) {
                char c = arrc[n];
                if (c != ' ') {
                    bl = true;
                }
                if (c == '\"') {
                    if (n4 == 0) {
                        n4 = n3 == 0 ? 1 : 0;
                        n3 = n4;
                    } else {
                        ((StringBuffer)charSequence).append(c);
                    }
                    n4 = 0;
                } else if (c == '\\' && n4 == 0 && n3 == 0) {
                    n4 = 1;
                    n5 = ((StringBuffer)charSequence).length();
                } else if (c != ' ' || n4 != 0 || bl) {
                    ((StringBuffer)charSequence).append(c);
                    n4 = 0;
                }
                ++n;
            }
            if (((StringBuffer)charSequence).length() > 0) {
                while (((StringBuffer)charSequence).charAt(((StringBuffer)charSequence).length() - 1) == ' ' && n5 != ((StringBuffer)charSequence).length() - 1) {
                    ((StringBuffer)charSequence).setLength(((StringBuffer)charSequence).length() - 1);
                }
            }
            return ((StringBuffer)charSequence).toString();
        }
        return ((String)charSequence).trim();
    }

    @Override
    public boolean equals(Object arrbl) {
        X509Name x509Name;
        int n;
        int n2;
        int n3;
        if (arrbl == this) {
            return true;
        }
        if (!(arrbl instanceof X509Name) && !(arrbl instanceof ASN1Sequence)) {
            return false;
        }
        ASN1Primitive aSN1Primitive = ((ASN1Encodable)arrbl).toASN1Primitive();
        if (this.toASN1Primitive().equals(aSN1Primitive)) {
            return true;
        }
        try {
            x509Name = X509Name.getInstance(arrbl);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            return false;
        }
        int n4 = this.ordering.size();
        if (n4 != x509Name.ordering.size()) {
            return false;
        }
        arrbl = new boolean[n4];
        if (this.ordering.elementAt(0).equals(x509Name.ordering.elementAt(0))) {
            n = 0;
            n2 = n4;
            n3 = 1;
        } else {
            n = n4 - 1;
            n2 = -1;
            n3 = -1;
        }
        while (n != n2) {
            boolean bl;
            boolean bl2 = false;
            aSN1Primitive = (ASN1ObjectIdentifier)this.ordering.elementAt(n);
            String string = (String)this.values.elementAt(n);
            int n5 = 0;
            do {
                bl = bl2;
                if (n5 >= n4) break;
                if (!arrbl[n5] && aSN1Primitive.equals((ASN1ObjectIdentifier)x509Name.ordering.elementAt(n5)) && this.equivalentStrings(string, (String)x509Name.values.elementAt(n5))) {
                    arrbl[n5] = true;
                    bl = true;
                    break;
                }
                ++n5;
            } while (true);
            if (!bl) {
                return false;
            }
            n += n3;
        }
        return true;
    }

    public boolean equals(Object object, boolean bl) {
        if (!bl) {
            return this.equals(object);
        }
        if (object == this) {
            return true;
        }
        if (!(object instanceof X509Name) && !(object instanceof ASN1Sequence)) {
            return false;
        }
        ASN1Primitive aSN1Primitive = ((ASN1Encodable)object).toASN1Primitive();
        if (this.toASN1Primitive().equals(aSN1Primitive)) {
            return true;
        }
        try {
            object = X509Name.getInstance(object);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            return false;
        }
        int n = this.ordering.size();
        if (n != ((X509Name)object).ordering.size()) {
            return false;
        }
        for (int i = 0; i < n; ++i) {
            if (((ASN1ObjectIdentifier)this.ordering.elementAt(i)).equals((ASN1ObjectIdentifier)((X509Name)object).ordering.elementAt(i))) {
                if (this.equivalentStrings((String)this.values.elementAt(i), (String)((X509Name)object).values.elementAt(i))) continue;
                return false;
            }
            return false;
        }
        return true;
    }

    @UnsupportedAppUsage
    public Vector getOIDs() {
        Vector vector = new Vector();
        for (int i = 0; i != this.ordering.size(); ++i) {
            vector.addElement(this.ordering.elementAt(i));
        }
        return vector;
    }

    @UnsupportedAppUsage
    public Vector getValues() {
        Vector vector = new Vector();
        for (int i = 0; i != this.values.size(); ++i) {
            vector.addElement(this.values.elementAt(i));
        }
        return vector;
    }

    public Vector getValues(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        Vector<String> vector = new Vector<String>();
        for (int i = 0; i != this.values.size(); ++i) {
            if (!this.ordering.elementAt(i).equals(aSN1ObjectIdentifier)) continue;
            String string = (String)this.values.elementAt(i);
            if (string.length() > 2 && string.charAt(0) == '\\' && string.charAt(1) == '#') {
                vector.addElement(string.substring(1));
                continue;
            }
            vector.addElement(string);
        }
        return vector;
    }

    @Override
    public int hashCode() {
        if (this.isHashCodeCalculated) {
            return this.hashCodeValue;
        }
        this.isHashCodeCalculated = true;
        for (int i = 0; i != this.ordering.size(); ++i) {
            String string = this.stripInternalSpaces(this.canonicalize((String)this.values.elementAt(i)));
            this.hashCodeValue ^= this.ordering.elementAt(i).hashCode();
            this.hashCodeValue ^= string.hashCode();
        }
        return this.hashCodeValue;
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        if (this.seq == null) {
            ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
            ASN1EncodableVector aSN1EncodableVector2 = new ASN1EncodableVector();
            ASN1ObjectIdentifier aSN1ObjectIdentifier = null;
            for (int i = 0; i != this.ordering.size(); ++i) {
                ASN1EncodableVector aSN1EncodableVector3 = new ASN1EncodableVector();
                ASN1ObjectIdentifier aSN1ObjectIdentifier2 = (ASN1ObjectIdentifier)this.ordering.elementAt(i);
                aSN1EncodableVector3.add(aSN1ObjectIdentifier2);
                String string = (String)this.values.elementAt(i);
                aSN1EncodableVector3.add(this.converter.getConvertedValue(aSN1ObjectIdentifier2, string));
                if (aSN1ObjectIdentifier != null && !((Boolean)this.added.elementAt(i)).booleanValue()) {
                    aSN1EncodableVector.add(new DERSet(aSN1EncodableVector2));
                    aSN1EncodableVector2 = new ASN1EncodableVector();
                    aSN1EncodableVector2.add(new DERSequence(aSN1EncodableVector3));
                } else {
                    aSN1EncodableVector2.add(new DERSequence(aSN1EncodableVector3));
                }
                aSN1ObjectIdentifier = aSN1ObjectIdentifier2;
            }
            aSN1EncodableVector.add(new DERSet(aSN1EncodableVector2));
            this.seq = new DERSequence(aSN1EncodableVector);
        }
        return this.seq;
    }

    public String toString() {
        return this.toString(DefaultReverse, DefaultSymbols);
    }

    public String toString(boolean bl, Hashtable hashtable) {
        int n;
        StringBuffer stringBuffer = new StringBuffer();
        Vector<StringBuffer> vector = new Vector<StringBuffer>();
        boolean bl2 = true;
        boolean bl3 = true;
        StringBuffer stringBuffer2 = null;
        for (n = 0; n < this.ordering.size(); ++n) {
            if (((Boolean)this.added.elementAt(n)).booleanValue()) {
                stringBuffer2.append('+');
                this.appendValue(stringBuffer2, hashtable, (ASN1ObjectIdentifier)this.ordering.elementAt(n), (String)this.values.elementAt(n));
                continue;
            }
            stringBuffer2 = new StringBuffer();
            this.appendValue(stringBuffer2, hashtable, (ASN1ObjectIdentifier)this.ordering.elementAt(n), (String)this.values.elementAt(n));
            vector.addElement(stringBuffer2);
        }
        if (bl) {
            for (n = vector.size() - 1; n >= 0; --n) {
                if (bl3) {
                    bl3 = false;
                } else {
                    stringBuffer.append(',');
                }
                stringBuffer.append(vector.elementAt(n).toString());
            }
        } else {
            bl3 = bl2;
            for (n = 0; n < vector.size(); ++n) {
                if (bl3) {
                    bl3 = false;
                } else {
                    stringBuffer.append(',');
                }
                stringBuffer.append(vector.elementAt(n).toString());
            }
        }
        return stringBuffer.toString();
    }
}

