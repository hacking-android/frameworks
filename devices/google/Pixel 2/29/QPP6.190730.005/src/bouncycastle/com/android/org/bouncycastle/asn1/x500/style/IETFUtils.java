/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1.x500.style;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1String;
import com.android.org.bouncycastle.asn1.DERUniversalString;
import com.android.org.bouncycastle.asn1.x500.AttributeTypeAndValue;
import com.android.org.bouncycastle.asn1.x500.RDN;
import com.android.org.bouncycastle.asn1.x500.X500Name;
import com.android.org.bouncycastle.asn1.x500.X500NameBuilder;
import com.android.org.bouncycastle.asn1.x500.X500NameStyle;
import com.android.org.bouncycastle.asn1.x500.style.X500NameTokenizer;
import com.android.org.bouncycastle.util.Strings;
import com.android.org.bouncycastle.util.encoders.Hex;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

public class IETFUtils {
    public static void appendRDN(StringBuffer stringBuffer, RDN arrattributeTypeAndValue, Hashtable hashtable) {
        if (arrattributeTypeAndValue.isMultiValued()) {
            arrattributeTypeAndValue = arrattributeTypeAndValue.getTypesAndValues();
            boolean bl = true;
            for (int i = 0; i != arrattributeTypeAndValue.length; ++i) {
                if (bl) {
                    bl = false;
                } else {
                    stringBuffer.append('+');
                }
                IETFUtils.appendTypeAndValue(stringBuffer, arrattributeTypeAndValue[i], hashtable);
            }
        } else if (arrattributeTypeAndValue.getFirst() != null) {
            IETFUtils.appendTypeAndValue(stringBuffer, arrattributeTypeAndValue.getFirst(), hashtable);
        }
    }

    public static void appendTypeAndValue(StringBuffer stringBuffer, AttributeTypeAndValue attributeTypeAndValue, Hashtable object) {
        if ((object = (String)((Hashtable)object).get(attributeTypeAndValue.getType())) != null) {
            stringBuffer.append((String)object);
        } else {
            stringBuffer.append(attributeTypeAndValue.getType().getId());
        }
        stringBuffer.append('=');
        stringBuffer.append(IETFUtils.valueToString(attributeTypeAndValue.getValue()));
    }

    private static boolean atvAreEqual(AttributeTypeAndValue attributeTypeAndValue, AttributeTypeAndValue attributeTypeAndValue2) {
        if (attributeTypeAndValue == attributeTypeAndValue2) {
            return true;
        }
        if (attributeTypeAndValue == null) {
            return false;
        }
        if (attributeTypeAndValue2 == null) {
            return false;
        }
        if (!attributeTypeAndValue.getType().equals(attributeTypeAndValue2.getType())) {
            return false;
        }
        return IETFUtils.canonicalize(IETFUtils.valueToString(attributeTypeAndValue.getValue())).equals(IETFUtils.canonicalize(IETFUtils.valueToString(attributeTypeAndValue2.getValue())));
    }

    private static String bytesToString(byte[] arrby) {
        char[] arrc = new char[arrby.length];
        for (int i = 0; i != arrc.length; ++i) {
            arrc[i] = (char)(arrby[i] & 255);
        }
        return new String(arrc);
    }

    public static String canonicalize(String string) {
        String string2;
        block8 : {
            int n;
            int n2;
            block9 : {
                string = string2 = Strings.toLowerCase(string);
                if (string2.length() > 0) {
                    string = string2;
                    if (string2.charAt(0) == '#') {
                        ASN1Primitive aSN1Primitive = IETFUtils.decodeObject(string2);
                        string = string2;
                        if (aSN1Primitive instanceof ASN1String) {
                            string = Strings.toLowerCase(((ASN1String)((Object)aSN1Primitive)).getString());
                        }
                    }
                }
                string2 = string;
                if (string.length() <= 1) break block8;
                n2 = 0;
                while (n2 + 1 < string.length() && string.charAt(n2) == '\\' && string.charAt(n2 + 1) == ' ') {
                    n2 += 2;
                }
                n = string.length() - 1;
                while (n - 1 > 0 && string.charAt(n - 1) == '\\' && string.charAt(n) == ' ') {
                    n -= 2;
                }
                if (n2 > 0) break block9;
                string2 = string;
                if (n >= string.length() - 1) break block8;
            }
            string2 = string.substring(n2, n + 1);
        }
        return IETFUtils.stripInternalSpaces(string2);
    }

    private static int convertHex(char c) {
        if ('0' <= c && c <= '9') {
            return c - 48;
        }
        if ('a' <= c && c <= 'f') {
            return c - 97 + 10;
        }
        return c - 65 + 10;
    }

    public static ASN1ObjectIdentifier decodeAttrName(String string, Hashtable object) {
        if (Strings.toUpperCase(string).startsWith("OID.")) {
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

    private static ASN1Primitive decodeObject(String object) {
        try {
            object = ASN1Primitive.fromByteArray(Hex.decode(((String)object).substring(1)));
            return object;
        }
        catch (IOException iOException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("unknown encoding in name: ");
            stringBuilder.append(iOException);
            throw new IllegalStateException(stringBuilder.toString());
        }
    }

    public static String[] findAttrNamesForOID(ASN1ObjectIdentifier aSN1ObjectIdentifier, Hashtable hashtable) {
        int n = 0;
        Object object = hashtable.elements();
        while (object.hasMoreElements()) {
            if (!aSN1ObjectIdentifier.equals(object.nextElement())) continue;
            ++n;
        }
        String[] arrstring = new String[n];
        int n2 = 0;
        Enumeration enumeration = hashtable.keys();
        while (enumeration.hasMoreElements()) {
            object = (String)enumeration.nextElement();
            n = n2;
            if (aSN1ObjectIdentifier.equals(hashtable.get(object))) {
                arrstring[n2] = object;
                n = n2 + 1;
            }
            n2 = n;
        }
        return arrstring;
    }

    private static boolean isHexDigit(char c) {
        boolean bl = '0' <= c && c <= '9' || 'a' <= c && c <= 'f' || 'A' <= c && c <= 'F';
        return bl;
    }

    public static boolean rDNAreEqual(RDN arrattributeTypeAndValue, RDN arrattributeTypeAndValue2) {
        if (arrattributeTypeAndValue.isMultiValued()) {
            if (arrattributeTypeAndValue2.isMultiValued()) {
                if ((arrattributeTypeAndValue = arrattributeTypeAndValue.getTypesAndValues()).length != (arrattributeTypeAndValue2 = arrattributeTypeAndValue2.getTypesAndValues()).length) {
                    return false;
                }
                for (int i = 0; i != arrattributeTypeAndValue.length; ++i) {
                    if (IETFUtils.atvAreEqual(arrattributeTypeAndValue[i], arrattributeTypeAndValue2[i])) continue;
                    return false;
                }
                return true;
            }
            return false;
        }
        if (!arrattributeTypeAndValue2.isMultiValued()) {
            return IETFUtils.atvAreEqual(arrattributeTypeAndValue.getFirst(), arrattributeTypeAndValue2.getFirst());
        }
        return false;
    }

    public static RDN[] rDNsFromString(String object, X500NameStyle x500NameStyle) {
        object = new X500NameTokenizer((String)object);
        X500NameBuilder x500NameBuilder = new X500NameBuilder(x500NameStyle);
        while (((X500NameTokenizer)object).hasMoreTokens()) {
            Vector<ASN1ObjectIdentifier> vector;
            Object object2 = ((X500NameTokenizer)object).nextToken();
            if (((String)object2).indexOf(43) > 0) {
                object2 = new X500NameTokenizer((String)object2, '+');
                Object object3 = new X500NameTokenizer(((X500NameTokenizer)object2).nextToken(), '=');
                vector = ((X500NameTokenizer)object3).nextToken();
                if (((X500NameTokenizer)object3).hasMoreTokens()) {
                    String string = ((X500NameTokenizer)object3).nextToken();
                    Object object4 = x500NameStyle.attrNameToOID(((String)((Object)vector)).trim());
                    if (((X500NameTokenizer)object2).hasMoreTokens()) {
                        vector = new Vector<ASN1ObjectIdentifier>();
                        object3 = new Vector();
                        vector.addElement((ASN1ObjectIdentifier)object4);
                        ((Vector)object3).addElement(IETFUtils.unescape(string));
                        while (((X500NameTokenizer)object2).hasMoreTokens()) {
                            object4 = new X500NameTokenizer(((X500NameTokenizer)object2).nextToken(), '=');
                            string = ((X500NameTokenizer)object4).nextToken();
                            if (((X500NameTokenizer)object4).hasMoreTokens()) {
                                object4 = ((X500NameTokenizer)object4).nextToken();
                                vector.addElement(x500NameStyle.attrNameToOID(string.trim()));
                                ((Vector)object3).addElement(IETFUtils.unescape((String)object4));
                                continue;
                            }
                            throw new IllegalArgumentException("badly formatted directory string");
                        }
                        x500NameBuilder.addMultiValuedRDN(IETFUtils.toOIDArray(vector), IETFUtils.toValueArray((Vector)object3));
                        continue;
                    }
                    x500NameBuilder.addRDN((ASN1ObjectIdentifier)object4, IETFUtils.unescape(string));
                    continue;
                }
                throw new IllegalArgumentException("badly formatted directory string");
            }
            vector = new X500NameTokenizer((String)object2, '=');
            object2 = ((X500NameTokenizer)((Object)vector)).nextToken();
            if (((X500NameTokenizer)((Object)vector)).hasMoreTokens()) {
                vector = ((X500NameTokenizer)((Object)vector)).nextToken();
                x500NameBuilder.addRDN(x500NameStyle.attrNameToOID(((String)object2).trim()), IETFUtils.unescape((String)((Object)vector)));
                continue;
            }
            throw new IllegalArgumentException("badly formatted directory string");
        }
        return x500NameBuilder.build().getRDNs();
    }

    public static String stripInternalSpaces(String string) {
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

    private static ASN1ObjectIdentifier[] toOIDArray(Vector vector) {
        ASN1ObjectIdentifier[] arraSN1ObjectIdentifier = new ASN1ObjectIdentifier[vector.size()];
        for (int i = 0; i != arraSN1ObjectIdentifier.length; ++i) {
            arraSN1ObjectIdentifier[i] = (ASN1ObjectIdentifier)vector.elementAt(i);
        }
        return arraSN1ObjectIdentifier;
    }

    private static String[] toValueArray(Vector vector) {
        String[] arrstring = new String[vector.size()];
        for (int i = 0; i != arrstring.length; ++i) {
            arrstring[i] = (String)vector.elementAt(i);
        }
        return arrstring;
    }

    private static String unescape(String charSequence) {
        if (((String)charSequence).length() != 0 && (((String)charSequence).indexOf(92) >= 0 || ((String)charSequence).indexOf(34) >= 0)) {
            char c;
            char[] arrc = ((String)charSequence).toCharArray();
            char c2 = '\u0000';
            char c3 = '\u0000';
            charSequence = new StringBuffer(((String)charSequence).length());
            char c4 = c = '\u0000';
            if (arrc[0] == '\\') {
                c4 = c;
                if (arrc[1] == '#') {
                    c4 = '\u0002';
                    ((StringBuffer)charSequence).append("\\#");
                }
            }
            boolean bl = false;
            int n = 0;
            char c5 = '\u0000';
            c = c4;
            char c6 = c5;
            c4 = c2;
            while (c != arrc.length) {
                char c7 = arrc[c];
                if (c7 != ' ') {
                    bl = true;
                }
                if (c7 == '\"') {
                    if (c4 == '\u0000') {
                        c4 = c3 == '\u0000' ? (char)'\u0001' : '\u0000';
                        c3 = c4;
                    } else {
                        ((StringBuffer)charSequence).append(c7);
                    }
                    c4 = '\u0000';
                    c2 = c6;
                } else if (c7 == '\\' && c4 == '\u0000' && c3 == '\u0000') {
                    c4 = '\u0001';
                    n = ((StringBuffer)charSequence).length();
                    c2 = c6;
                } else if (c7 == ' ' && c4 == '\u0000' && !bl) {
                    c2 = c6;
                } else if (c4 != '\u0000' && IETFUtils.isHexDigit(c7)) {
                    if (c6 != '\u0000') {
                        ((StringBuffer)charSequence).append((char)(IETFUtils.convertHex(c6) * 16 + IETFUtils.convertHex(c7)));
                        c4 = '\u0000';
                        c2 = '\u0000';
                    } else {
                        c2 = c7;
                    }
                } else {
                    ((StringBuffer)charSequence).append(c7);
                    c4 = '\u0000';
                    c2 = c6;
                }
                ++c;
                c6 = c2;
            }
            if (((StringBuffer)charSequence).length() > 0) {
                while (((StringBuffer)charSequence).charAt(((StringBuffer)charSequence).length() - 1) == ' ' && n != ((StringBuffer)charSequence).length() - 1) {
                    ((StringBuffer)charSequence).setLength(((StringBuffer)charSequence).length() - 1);
                }
            }
            return ((StringBuffer)charSequence).toString();
        }
        return ((String)charSequence).trim();
    }

    public static ASN1Encodable valueFromHexString(String string, int n) throws IOException {
        byte[] arrby = new byte[(string.length() - n) / 2];
        for (int i = 0; i != arrby.length; ++i) {
            char c = string.charAt(i * 2 + n);
            char c2 = string.charAt(i * 2 + n + 1);
            arrby[i] = (byte)(IETFUtils.convertHex(c) << 4 | IETFUtils.convertHex(c2));
        }
        return ASN1Primitive.fromByteArray(arrby);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static String valueToString(ASN1Encodable object) {
        CharSequence charSequence;
        StringBuffer stringBuffer = new StringBuffer();
        if (object instanceof ASN1String && !(object instanceof DERUniversalString)) {
            charSequence = ((ASN1String)object).getString();
            if (((String)charSequence).length() > 0 && ((String)charSequence).charAt(0) == '#') {
                object = new StringBuilder();
                ((StringBuilder)object).append("\\");
                ((StringBuilder)object).append((String)charSequence);
                stringBuffer.append(((StringBuilder)object).toString());
            } else {
                stringBuffer.append((String)charSequence);
            }
        } else {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("#");
            ((StringBuilder)charSequence).append(IETFUtils.bytesToString(Hex.encode(object.toASN1Primitive().getEncoded("DER"))));
            stringBuffer.append(((StringBuilder)charSequence).toString());
        }
        int n = stringBuffer.length();
        int n2 = 0;
        int n3 = n;
        int n4 = n2;
        if (stringBuffer.length() >= 2) {
            n3 = n;
            n4 = n2;
            if (stringBuffer.charAt(0) == '\\') {
                n3 = n;
                n4 = n2;
                if (stringBuffer.charAt(1) == '#') {
                    n4 = 0 + 2;
                    n3 = n;
                }
            }
        }
        while (n4 != n3) {
            block17 : {
                block16 : {
                    if (stringBuffer.charAt(n4) == ',' || stringBuffer.charAt(n4) == '\"' || stringBuffer.charAt(n4) == '\\' || stringBuffer.charAt(n4) == '+' || stringBuffer.charAt(n4) == '=' || stringBuffer.charAt(n4) == '<' || stringBuffer.charAt(n4) == '>') break block16;
                    n = n3;
                    n2 = n4;
                    if (stringBuffer.charAt(n4) != ';') break block17;
                }
                stringBuffer.insert(n4, "\\");
                n2 = n4 + 1;
                n = n3 + 1;
            }
            n4 = n2 + 1;
            n3 = n;
        }
        if (stringBuffer.length() > 0) {
            for (n4 = 0; stringBuffer.length() > n4 && stringBuffer.charAt(n4) == ' '; n4 += 2) {
                stringBuffer.insert(n4, "\\");
            }
        }
        n4 = stringBuffer.length() - 1;
        while (n4 >= 0) {
            if (stringBuffer.charAt(n4) != ' ') return stringBuffer.toString();
            stringBuffer.insert(n4, '\\');
            --n4;
        }
        return stringBuffer.toString();
        catch (IOException iOException) {
            throw new IllegalArgumentException("Other value has no encoded form");
        }
    }
}

