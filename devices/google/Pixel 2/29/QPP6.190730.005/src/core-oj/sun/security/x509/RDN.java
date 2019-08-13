/*
 * Decompiled with CFR 0.145.
 */
package sun.security.x509;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import sun.security.util.DerEncoder;
import sun.security.util.DerInputStream;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import sun.security.util.ObjectIdentifier;
import sun.security.x509.AVA;
import sun.security.x509.AVAComparator;
import sun.security.x509.X500Name;

public class RDN {
    final AVA[] assertion;
    private volatile List<AVA> avaList;
    private volatile String canonicalString;

    RDN(int n) {
        this.assertion = new AVA[n];
    }

    public RDN(String string) throws IOException {
        this(string, Collections.emptyMap());
    }

    RDN(String string, String string2) throws IOException {
        this(string, string2, Collections.emptyMap());
    }

    RDN(String charSequence, String object, Map<String, String> map) throws IOException {
        if (((String)object).equalsIgnoreCase("RFC2253")) {
            String string;
            int n = 0;
            object = new ArrayList(3);
            int n2 = ((String)charSequence).indexOf(43);
            while (n2 >= 0) {
                int n3 = n;
                if (n2 > 0) {
                    n3 = n;
                    if (((String)charSequence).charAt(n2 - 1) != '\\') {
                        string = ((String)charSequence).substring(n, n2);
                        if (string.length() != 0) {
                            object.add(new AVA(new StringReader(string), 3, map));
                            n3 = n2 + 1;
                        } else {
                            object = new StringBuilder();
                            ((StringBuilder)object).append("empty AVA in RDN \"");
                            ((StringBuilder)object).append((String)charSequence);
                            ((StringBuilder)object).append("\"");
                            throw new IOException(((StringBuilder)object).toString());
                        }
                    }
                }
                n2 = ((String)charSequence).indexOf(43, n2 + 1);
                n = n3;
            }
            string = ((String)charSequence).substring(n);
            if (string.length() != 0) {
                object.add(new AVA(new StringReader(string), 3, map));
                this.assertion = object.toArray(new AVA[object.size()]);
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("empty AVA in RDN \"");
            ((StringBuilder)object).append((String)charSequence);
            ((StringBuilder)object).append("\"");
            throw new IOException(((StringBuilder)object).toString());
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Unsupported format ");
        ((StringBuilder)charSequence).append((String)object);
        throw new IOException(((StringBuilder)charSequence).toString());
    }

    public RDN(String string, Map<String, String> object) throws IOException {
        String string2;
        int n = 0;
        int n2 = 0;
        int n3 = 0;
        ArrayList<AVA> arrayList = new ArrayList<AVA>(3);
        int n4 = string.indexOf(43);
        while (n4 >= 0) {
            n = n2 = n + X500Name.countQuotes(string, n2, n4);
            int n5 = n3;
            if (n4 > 0) {
                n = n2;
                n5 = n3;
                if (string.charAt(n4 - 1) != '\\') {
                    n = n2;
                    n5 = n3;
                    if (n2 != 1) {
                        string2 = string.substring(n3, n4);
                        if (string2.length() != 0) {
                            arrayList.add(new AVA((Reader)new StringReader(string2), (Map<String, String>)object));
                            n5 = n4 + 1;
                            n = 0;
                        } else {
                            object = new StringBuilder();
                            ((StringBuilder)object).append("empty AVA in RDN \"");
                            ((StringBuilder)object).append(string);
                            ((StringBuilder)object).append("\"");
                            throw new IOException(((StringBuilder)object).toString());
                        }
                    }
                }
            }
            n2 = n4 + 1;
            n4 = string.indexOf(43, n2);
            n3 = n5;
        }
        string2 = string.substring(n3);
        if (string2.length() != 0) {
            arrayList.add(new AVA((Reader)new StringReader(string2), (Map<String, String>)object));
            this.assertion = arrayList.toArray(new AVA[arrayList.size()]);
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("empty AVA in RDN \"");
        ((StringBuilder)object).append(string);
        ((StringBuilder)object).append("\"");
        throw new IOException(((StringBuilder)object).toString());
    }

    RDN(DerValue arrderValue) throws IOException {
        if (arrderValue.tag == 49) {
            arrderValue = new DerInputStream(arrderValue.toByteArray()).getSet(5);
            this.assertion = new AVA[arrderValue.length];
            for (int i = 0; i < arrderValue.length; ++i) {
                this.assertion[i] = new AVA(arrderValue[i]);
            }
            return;
        }
        throw new IOException("X500 RDN");
    }

    public RDN(AVA aVA) {
        if (aVA != null) {
            this.assertion = new AVA[]{aVA};
            return;
        }
        throw new NullPointerException();
    }

    public RDN(AVA[] arraVA) {
        this.assertion = (AVA[])arraVA.clone();
        for (int i = 0; i < (arraVA = this.assertion).length; ++i) {
            if (arraVA[i] != null) {
                continue;
            }
            throw new NullPointerException();
        }
    }

    private String toRFC2253StringInternal(boolean bl, Map<String, String> object) {
        Object object2 = this.assertion;
        int n = ((AVA[])object2).length;
        int n2 = 0;
        if (n == 1) {
            object = bl ? object2[0].toRFC2253CanonicalString() : object2[0].toRFC2253String((Map<String, String>)object);
            return object;
        }
        AVA[] arraVA = this.assertion;
        if (bl) {
            arraVA = (AVA[])object2.clone();
            Arrays.sort(arraVA, AVAComparator.getInstance());
        }
        StringJoiner stringJoiner = new StringJoiner("+");
        n = arraVA.length;
        while (n2 < n) {
            object2 = arraVA[n2];
            object2 = bl ? ((AVA)object2).toRFC2253CanonicalString() : ((AVA)object2).toRFC2253String((Map<String, String>)object);
            stringJoiner.add((CharSequence)object2);
            ++n2;
        }
        return stringJoiner.toString();
    }

    public List<AVA> avas() {
        List<AVA> list;
        List<AVA> list2 = list = this.avaList;
        if (list == null) {
            this.avaList = list2 = Collections.unmodifiableList(Arrays.asList(this.assertion));
        }
        return list2;
    }

    void encode(DerOutputStream derOutputStream) throws IOException {
        derOutputStream.putOrderedSetOf((byte)49, this.assertion);
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof RDN)) {
            return false;
        }
        object = (RDN)object;
        if (this.assertion.length != ((RDN)object).assertion.length) {
            return false;
        }
        return this.toRFC2253String(true).equals(((RDN)object).toRFC2253String(true));
    }

    DerValue findAttribute(ObjectIdentifier objectIdentifier) {
        AVA[] arraVA;
        for (int i = 0; i < (arraVA = this.assertion).length; ++i) {
            if (!arraVA[i].oid.equals((Object)objectIdentifier)) continue;
            return this.assertion[i].value;
        }
        return null;
    }

    public int hashCode() {
        return this.toRFC2253String(true).hashCode();
    }

    public int size() {
        return this.assertion.length;
    }

    public String toRFC1779String() {
        return this.toRFC1779String(Collections.emptyMap());
    }

    public String toRFC1779String(Map<String, String> map) {
        Object object = this.assertion;
        if (((AVA[])object).length == 1) {
            return object[0].toRFC1779String(map);
        }
        object = new StringBuilder();
        for (int i = 0; i < this.assertion.length; ++i) {
            if (i != 0) {
                ((StringBuilder)object).append(" + ");
            }
            ((StringBuilder)object).append(this.assertion[i].toRFC1779String(map));
        }
        return ((StringBuilder)object).toString();
    }

    public String toRFC2253String() {
        return this.toRFC2253StringInternal(false, Collections.emptyMap());
    }

    public String toRFC2253String(Map<String, String> map) {
        return this.toRFC2253StringInternal(false, map);
    }

    public String toRFC2253String(boolean bl) {
        String string;
        if (!bl) {
            return this.toRFC2253StringInternal(false, Collections.emptyMap());
        }
        String string2 = string = this.canonicalString;
        if (string == null) {
            this.canonicalString = string2 = this.toRFC2253StringInternal(true, Collections.emptyMap());
        }
        return string2;
    }

    public String toString() {
        Object object = this.assertion;
        if (((AVA[])object).length == 1) {
            return object[0].toString();
        }
        object = new StringBuilder();
        for (int i = 0; i < this.assertion.length; ++i) {
            if (i != 0) {
                ((StringBuilder)object).append(" + ");
            }
            ((StringBuilder)object).append(this.assertion[i].toString());
        }
        return ((StringBuilder)object).toString();
    }
}

