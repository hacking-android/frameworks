/*
 * Decompiled with CFR 0.145.
 */
package sun.security.x509;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.security.AccessController;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import sun.security.action.GetBooleanAction;
import sun.security.pkcs.PKCS9Attribute;
import sun.security.util.Debug;
import sun.security.util.DerEncoder;
import sun.security.util.DerInputStream;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import sun.security.util.ObjectIdentifier;
import sun.security.x509.AVAKeyword;
import sun.security.x509.X500Name;

public class AVA
implements DerEncoder {
    static final int DEFAULT = 1;
    private static final boolean PRESERVE_OLD_DC_ENCODING;
    static final int RFC1779 = 2;
    static final int RFC2253 = 3;
    private static final Debug debug;
    private static final String escapedDefault = ",+<>;\"";
    private static final String hexDigits = "0123456789ABCDEF";
    private static final String specialChars1779 = ",=\n+<>#;\\\"";
    private static final String specialChars2253 = ",=+<>#;\\\"";
    private static final String specialCharsDefault = ",=\n+<>#;\\\" ";
    final ObjectIdentifier oid;
    final DerValue value;

    static {
        debug = Debug.getInstance("x509", "\t[AVA]");
        PRESERVE_OLD_DC_ENCODING = AccessController.doPrivileged(new GetBooleanAction("com.sun.security.preserveOldDCEncoding"));
    }

    AVA(Reader reader) throws IOException {
        this(reader, 1);
    }

    AVA(Reader reader, int n) throws IOException {
        this(reader, n, Collections.emptyMap());
    }

    AVA(Reader reader, int n, Map<String, String> map) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        do {
            int n2;
            if ((n2 = AVA.readChar(reader, "Incorrect AVA format")) == 61) {
                this.oid = AVAKeyword.getOID(stringBuilder.toString(), n, map);
                stringBuilder.setLength(0);
                if (n == 3) {
                    n2 = reader.read();
                    if (n2 == 32) {
                        throw new IOException("Incorrect AVA RFC2253 format - leading space must be escaped");
                    }
                } else {
                    while ((n2 = reader.read()) == 32 || n2 == 10) {
                    }
                }
                if (n2 == -1) {
                    this.value = new DerValue("");
                    return;
                }
                this.value = n2 == 35 ? AVA.parseHexString(reader, n) : (n2 == 34 && n != 3 ? this.parseQuotedString(reader, stringBuilder) : this.parseString(reader, n2, n, stringBuilder));
                return;
            }
            stringBuilder.append((char)n2);
        } while (true);
    }

    AVA(Reader reader, Map<String, String> map) throws IOException {
        this(reader, 1, map);
    }

    AVA(DerInputStream derInputStream) throws IOException {
        this(derInputStream.getDerValue());
    }

    AVA(DerValue derValue) throws IOException {
        if (derValue.tag == 48) {
            this.oid = X500Name.intern(derValue.data.getOID());
            this.value = derValue.data.getDerValue();
            if (derValue.data.available() == 0) {
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("AVA, extra bytes = ");
            stringBuilder.append(derValue.data.available());
            throw new IOException(stringBuilder.toString());
        }
        throw new IOException("AVA not a sequence");
    }

    public AVA(ObjectIdentifier objectIdentifier, DerValue derValue) {
        if (objectIdentifier != null && derValue != null) {
            this.oid = objectIdentifier;
            this.value = derValue;
            return;
        }
        throw new NullPointerException();
    }

    private static Byte getEmbeddedHexPair(int n, Reader reader) throws IOException {
        if (hexDigits.indexOf(Character.toUpperCase((char)n)) >= 0) {
            int n2 = AVA.readChar(reader, "unexpected EOF - escaped hex value must include two valid digits");
            if (hexDigits.indexOf(Character.toUpperCase((char)n2)) >= 0) {
                return new Byte((byte)((Character.digit((char)n, 16) << 4) + Character.digit((char)n2, 16)));
            }
            throw new IOException("escaped hex value must include two valid digits");
        }
        return null;
    }

    private static String getEmbeddedHexString(List<Byte> list) throws IOException {
        int n = list.size();
        byte[] arrby = new byte[n];
        for (int i = 0; i < n; ++i) {
            arrby[i] = list.get(i);
        }
        return new String(arrby, "UTF8");
    }

    private static boolean isDerString(DerValue derValue, boolean bl) {
        if (bl) {
            byte by = derValue.tag;
            return by == 12 || by == 19;
        }
        byte by = derValue.tag;
        return by == 12 || by == 22 || by == 27 || by == 30 || by == 19 || by == 20;
    }

    private static boolean isTerminator(int n, int n2) {
        boolean bl = true;
        if (n != -1) {
            if (n != 59) {
                if (n != 43 && n != 44) {
                    return false;
                }
            } else {
                if (n2 == 3) {
                    bl = false;
                }
                return bl;
            }
        }
        return true;
    }

    private static DerValue parseHexString(Reader object, int n) throws IOException {
        int n2;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int n3 = 0;
        int n4 = 0;
        while (!AVA.isTerminator(n2 = ((Reader)object).read(), n)) {
            int n5 = n2;
            if (n2 != 32) {
                if (n2 == 10) {
                    n5 = n2;
                } else {
                    n5 = hexDigits.indexOf(Character.toUpperCase((char)n2));
                    if (n5 != -1) {
                        if (n4 % 2 == 1) {
                            n3 = (byte)(n3 * 16 + (byte)n5);
                            byteArrayOutputStream.write(n3);
                        } else {
                            n3 = (byte)n5;
                        }
                        ++n4;
                        continue;
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append("AVA parse, invalid hex digit: ");
                    ((StringBuilder)object).append((char)n2);
                    throw new IOException(((StringBuilder)object).toString());
                }
            }
            do {
                if (n5 == 32 || n5 == 10) continue;
                object = new StringBuilder();
                ((StringBuilder)object).append("AVA parse, invalid hex digit: ");
                ((StringBuilder)object).append((char)n5);
                throw new IOException(((StringBuilder)object).toString());
            } while (!AVA.isTerminator(n5 = ((Reader)object).read(), n));
        }
        if (n4 != 0) {
            if (n4 % 2 != 1) {
                return new DerValue(byteArrayOutputStream.toByteArray());
            }
            throw new IOException("AVA parse, odd number of hex digits");
        }
        throw new IOException("AVA parse, zero hex digits");
    }

    private DerValue parseQuotedString(Reader object, StringBuilder stringBuilder) throws IOException {
        int n = AVA.readChar((Reader)object, "Quoted string did not end in quote");
        ArrayList<Byte> arrayList = new ArrayList<Byte>();
        boolean bl = true;
        while (n != 34) {
            int n2 = n;
            if (n == 92) {
                n2 = AVA.readChar((Reader)object, "Quoted string did not end in quote");
                Byte by = AVA.getEmbeddedHexPair(n2, (Reader)object);
                if (by != null) {
                    bl = false;
                    arrayList.add(by);
                    n = ((Reader)object).read();
                    continue;
                }
                if (specialChars1779.indexOf((char)n2) < 0) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Invalid escaped character in AVA: ");
                    ((StringBuilder)object).append((char)n2);
                    throw new IOException(((StringBuilder)object).toString());
                }
            }
            if (arrayList.size() > 0) {
                stringBuilder.append(AVA.getEmbeddedHexString(arrayList));
                arrayList.clear();
            }
            bl &= DerValue.isPrintableStringChar((char)n2);
            stringBuilder.append((char)n2);
            n = AVA.readChar((Reader)object, "Quoted string did not end in quote");
        }
        if (arrayList.size() > 0) {
            stringBuilder.append(AVA.getEmbeddedHexString(arrayList));
            arrayList.clear();
        }
        while ((n = ((Reader)object).read()) == 10 || n == 32) {
        }
        if (n == -1) {
            if (!(this.oid.equals((Object)PKCS9Attribute.EMAIL_ADDRESS_OID) || this.oid.equals((Object)X500Name.DOMAIN_COMPONENT_OID) && !PRESERVE_OLD_DC_ENCODING)) {
                if (bl) {
                    return new DerValue(stringBuilder.toString());
                }
                return new DerValue(12, stringBuilder.toString());
            }
            return new DerValue(22, stringBuilder.toString());
        }
        throw new IOException("AVA had characters other than whitespace after terminating quote");
    }

    private DerValue parseString(Reader object, int n, int n2, StringBuilder stringBuilder) throws IOException {
        ArrayList<Byte> arrayList = new ArrayList<Byte>();
        int n3 = 0;
        int n4 = 1;
        boolean bl = true;
        int n5 = n;
        n = n3;
        do {
            block24 : {
                block25 : {
                    int n6;
                    block22 : {
                        block23 : {
                            Object object2 = object;
                            n6 = 0;
                            if (n5 != 92) break block22;
                            n3 = 1;
                            n5 = AVA.readChar((Reader)object2, "Invalid trailing backslash");
                            if ((object2 = AVA.getEmbeddedHexPair(n5, (Reader)object2)) == null) break block23;
                            bl = false;
                            arrayList.add((Byte)object2);
                            n5 = ((Reader)object).read();
                            break block24;
                        }
                        if (n2 == 1 && specialCharsDefault.indexOf((char)n5) == -1) {
                            object = new StringBuilder();
                            ((StringBuilder)object).append("Invalid escaped character in AVA: '");
                            ((StringBuilder)object).append((char)n5);
                            ((StringBuilder)object).append("'");
                            throw new IOException(((StringBuilder)object).toString());
                        }
                        if (n2 == 3) {
                            if (n5 == 32) {
                                if (n4 == 0 && !AVA.trailingSpace((Reader)object)) {
                                    throw new IOException("Invalid escaped space character in AVA.  Only a leading or trailing space character can be escaped.");
                                }
                            } else if (n5 == 35) {
                                if (n4 == 0) {
                                    throw new IOException("Invalid escaped '#' character in AVA.  Only a leading '#' can be escaped.");
                                }
                            } else if (specialChars2253.indexOf((char)n5) == -1) {
                                object = new StringBuilder();
                                ((StringBuilder)object).append("Invalid escaped character in AVA: '");
                                ((StringBuilder)object).append((char)n5);
                                ((StringBuilder)object).append("'");
                                throw new IOException(((StringBuilder)object).toString());
                            }
                        }
                        n4 = n5;
                        break block25;
                    }
                    n4 = n5;
                    n3 = n6;
                    if (n2 == 3) {
                        if (specialChars2253.indexOf((char)n5) == -1) {
                            n4 = n5;
                            n3 = n6;
                        } else {
                            object = new StringBuilder();
                            ((StringBuilder)object).append("Character '");
                            ((StringBuilder)object).append((char)n5);
                            ((StringBuilder)object).append("' in AVA appears without escape");
                            throw new IOException(((StringBuilder)object).toString());
                        }
                    }
                }
                n5 = n;
                if (arrayList.size() > 0) {
                    for (n5 = 0; n5 < n; ++n5) {
                        stringBuilder.append(" ");
                    }
                    n5 = 0;
                    stringBuilder.append(AVA.getEmbeddedHexString(arrayList));
                    arrayList.clear();
                }
                boolean bl2 = DerValue.isPrintableStringChar((char)n4);
                if (n4 == 32 && n3 == 0) {
                    n = n5 + 1;
                } else {
                    for (n = 0; n < n5; ++n) {
                        stringBuilder.append(" ");
                    }
                    n = 0;
                    stringBuilder.append((char)n4);
                }
                n5 = ((Reader)object).read();
                bl = bl2 & bl;
            }
            n4 = 0;
        } while (!AVA.isTerminator(n5, n2));
        if (n2 == 3 && n > 0) {
            throw new IOException("Incorrect AVA RFC2253 format - trailing space must be escaped");
        }
        if (arrayList.size() > 0) {
            stringBuilder.append(AVA.getEmbeddedHexString(arrayList));
            arrayList.clear();
        }
        if (!(this.oid.equals((Object)PKCS9Attribute.EMAIL_ADDRESS_OID) || this.oid.equals((Object)X500Name.DOMAIN_COMPONENT_OID) && !PRESERVE_OLD_DC_ENCODING)) {
            if (bl) {
                return new DerValue(stringBuilder.toString());
            }
            return new DerValue(12, stringBuilder.toString());
        }
        return new DerValue(22, stringBuilder.toString());
    }

    private static int readChar(Reader reader, String string) throws IOException {
        int n = reader.read();
        if (n != -1) {
            return n;
        }
        throw new IOException(string);
    }

    private String toKeyword(int n, Map<String, String> map) {
        return AVAKeyword.getKeyword(this.oid, n, map);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private String toKeywordValueString(String var1_1) {
        block29 : {
            block30 : {
                var2_3 = new StringBuilder(40);
                var2_3.append((String)var1_1);
                var2_3.append("=");
                try {
                    var3_4 = this.value.getAsString();
                    if (var3_4 == null) {
                        var1_1 = this.value.toByteArray();
                        var2_3.append('#');
                        var4_5 = 0;
                        while (var4_5 < ((Object)var1_1).length) {
                            var2_3.append("0123456789ABCDEF".charAt(var1_1[var4_5] >> 4 & 15));
                            var2_3.append("0123456789ABCDEF".charAt(var1_1[var4_5] & 15));
                            ++var4_5;
                        }
                        return var2_3.toString();
                    }
                    var4_6 = 0;
                    var1_1 = new StringBuilder();
                    var5_7 = 0;
                    var6_8 = var3_4.length();
                    var7_9 = var6_8 > 1 && var3_4.charAt(0) == '\"' && var3_4.charAt(var6_8 - 1) == '\"';
                    for (var8_10 = 0; var8_10 < var6_8; ++var8_10) {
                        var9_11 = var3_4.charAt(var8_10);
                        if (var7_9 && (var8_10 == 0 || var8_10 == var6_8 - 1)) {
                            var1_1.append(var9_11);
                            var10_12 = var5_7;
                            break block25;
                        }
                        if (DerValue.isPrintableStringChar(var9_11) || ",+=\n<>#;\\\"".indexOf(var9_11) >= 0) ** GOTO lbl55
                        if (AVA.debug == null || !Debug.isOn("ava")) ** break block26
                        var5_7 = 0;
                        var11_13 = Character.toString(var9_11).getBytes("UTF8");
                        for (var10_12 = 0; var10_12 < var11_13.length; ++var10_12) {
                            var1_1.append('\\');
                            var1_1.append(Character.toUpperCase(Character.forDigit(var11_13[var10_12] >>> 4 & 15, 16)));
                            var1_1.append(Character.toUpperCase(Character.forDigit(var11_13[var10_12] & 15, 16)));
                        }
                        var10_12 = var5_7;
                        break block25;
                    }
                }
                catch (IOException var1_2) {
                    throw new IllegalArgumentException("DER Value conversion");
                }
                {
                    block25 : {
                        block27 : {
                            block28 : {
                                var1_1.append(var9_11);
                                var10_12 = 0;
                                break block25;
lbl55: // 1 sources:
                                var10_12 = var4_6;
                                if (var4_6 != 0) break block27;
                                if (var8_10 == 0 && (var9_11 == ' ' || var9_11 == '\n')) break block28;
                                var10_12 = var4_6;
                                if (",+=\n<>#;\\\"".indexOf(var9_11) < 0) break block27;
                            }
                            var10_12 = 1;
                        }
                        if (var9_11 != ' ' && var9_11 != '\n') {
                            if (var9_11 == '\"' || var9_11 == '\\') {
                                var1_1.append('\\');
                            }
                            var5_7 = 0;
                            var4_6 = var10_12;
                            var10_12 = var5_7;
                        } else {
                            var4_6 = var10_12;
                            if (var10_12 == 0) {
                                var4_6 = var10_12;
                                if (var5_7 != 0) {
                                    var4_6 = 1;
                                }
                            }
                            var10_12 = 1;
                        }
                        var1_1.append(var9_11);
                    }
                    var5_7 = var10_12;
                    continue;
                }
                var10_12 = var4_6;
                if (var1_1.length() <= 0) break block29;
                var8_10 = var1_1.charAt(var1_1.length() - 1);
                if (var8_10 == 32) break block30;
                var10_12 = var4_6;
                if (var8_10 != 10) break block29;
            }
            var10_12 = 1;
        }
        if (!var7_9 && var10_12 != 0) {
            var11_13 = new StringBuilder();
            var11_13.append("\"");
            var11_13.append(var1_1.toString());
            var11_13.append("\"");
            var2_3.append(var11_13.toString());
            return var2_3.toString();
        }
        var2_3.append(var1_1.toString());
        return var2_3.toString();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private static boolean trailingSpace(Reader var0) throws IOException {
        block3 : {
            block4 : {
                if (!var0.markSupported()) {
                    return true;
                }
                var0.mark(9999);
                do lbl-1000: // 3 sources:
                {
                    if ((var1_1 = var0.read()) == -1) {
                        var2_2 = true;
                        break block3;
                    }
                    if (var1_1 == 32) ** GOTO lbl-1000
                    if (var1_1 != 92) break block4;
                } while (var0.read() == 32);
                var2_2 = false;
                break block3;
            }
            var2_2 = false;
        }
        var0.reset();
        return var2_2;
    }

    @Override
    public void derEncode(OutputStream outputStream) throws IOException {
        DerOutputStream derOutputStream = new DerOutputStream();
        DerOutputStream derOutputStream2 = new DerOutputStream();
        derOutputStream.putOID(this.oid);
        this.value.encode(derOutputStream);
        derOutputStream2.write((byte)48, derOutputStream);
        outputStream.write(derOutputStream2.toByteArray());
    }

    public void encode(DerOutputStream derOutputStream) throws IOException {
        this.derEncode(derOutputStream);
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof AVA)) {
            return false;
        }
        object = (AVA)object;
        return this.toRFC2253CanonicalString().equals(((AVA)object).toRFC2253CanonicalString());
    }

    public DerValue getDerValue() {
        return this.value;
    }

    public ObjectIdentifier getObjectIdentifier() {
        return this.oid;
    }

    public String getValueString() {
        Object object;
        block3 : {
            try {
                object = this.value.getAsString();
                if (object == null) break block3;
                return object;
            }
            catch (IOException iOException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("AVA error: ");
                stringBuilder.append(iOException);
                throw new RuntimeException(stringBuilder.toString(), iOException);
            }
        }
        object = new RuntimeException("AVA string is null");
        throw object;
    }

    boolean hasRFC2253Keyword() {
        return AVAKeyword.hasKeyword(this.oid, 3);
    }

    public int hashCode() {
        return this.toRFC2253CanonicalString().hashCode();
    }

    public String toRFC1779String() {
        return this.toRFC1779String(Collections.emptyMap());
    }

    public String toRFC1779String(Map<String, String> map) {
        return this.toKeywordValueString(this.toKeyword(2, map));
    }

    public String toRFC2253CanonicalString() {
        StringBuilder stringBuilder;
        block14 : {
            StringBuilder stringBuilder2;
            String string;
            int n;
            block13 : {
                byte[] arrby;
                stringBuilder = new StringBuilder(40);
                stringBuilder.append(this.toKeyword(3, Collections.emptyMap()));
                stringBuilder.append('=');
                if ((stringBuilder.charAt(0) < '0' || stringBuilder.charAt(0) > '9') && (AVA.isDerString(this.value, true) || this.value.tag == 20)) break block13;
                try {
                    arrby = this.value.toByteArray();
                    stringBuilder.append('#');
                }
                catch (IOException iOException) {
                    throw new IllegalArgumentException("DER Value conversion");
                }
                for (int i = 0; i < arrby.length; ++i) {
                    byte by = arrby[i];
                    stringBuilder.append(Character.forDigit(by >>> 4 & 15, 16));
                    stringBuilder.append(Character.forDigit(by & 15, 16));
                }
                break block14;
            }
            try {
                string = new String(this.value.getDataBytes(), "UTF8");
                stringBuilder2 = new StringBuilder();
                n = 0;
            }
            catch (IOException iOException) {
                throw new IllegalArgumentException("DER Value conversion");
            }
            for (int i = 0; i < string.length(); ++i) {
                int n2;
                block17 : {
                    char c;
                    block15 : {
                        block16 : {
                            byte[] arrby;
                            c = string.charAt(i);
                            if (DerValue.isPrintableStringChar(c) || ",+<>;\"\\".indexOf(c) >= 0 || i == 0 && c == '#') break block15;
                            if (debug == null || !Debug.isOn("ava")) break block16;
                            n = 0;
                            try {
                                arrby = Character.toString(c).getBytes("UTF8");
                            }
                            catch (IOException iOException) {
                                throw new IllegalArgumentException("DER Value conversion");
                            }
                            for (n2 = 0; n2 < arrby.length; ++n2) {
                                stringBuilder2.append('\\');
                                stringBuilder2.append(Character.forDigit(arrby[n2] >>> 4 & 15, 16));
                                stringBuilder2.append(Character.forDigit(arrby[n2] & 15, 16));
                            }
                            n2 = n;
                            break block17;
                        }
                        n2 = 0;
                        stringBuilder2.append(c);
                        break block17;
                    }
                    if (i == 0 && c == '#' || ",+<>;\"\\".indexOf(c) >= 0) {
                        stringBuilder2.append('\\');
                    }
                    if (!Character.isWhitespace(c)) {
                        n2 = 0;
                        stringBuilder2.append(c);
                    } else {
                        n2 = n;
                        if (n == 0) {
                            n2 = 1;
                            stringBuilder2.append(c);
                        }
                    }
                }
                n = n2;
            }
            stringBuilder.append(stringBuilder2.toString().trim());
        }
        return Normalizer.normalize(stringBuilder.toString().toUpperCase(Locale.US).toLowerCase(Locale.US), Normalizer.Form.NFKD);
    }

    public String toRFC2253String() {
        return this.toRFC2253String(Collections.emptyMap());
    }

    public String toRFC2253String(Map<String, String> object) {
        StringBuilder stringBuilder;
        block18 : {
            char c;
            StringBuilder stringBuilder2;
            int n;
            Object[] arrobject;
            int n2;
            block17 : {
                stringBuilder = new StringBuilder(100);
                stringBuilder.append(this.toKeyword(3, (Map<String, String>)object));
                stringBuilder.append('=');
                if ((stringBuilder.charAt(0) < '0' || stringBuilder.charAt(0) > '9') && AVA.isDerString(this.value, false)) break block17;
                try {
                    object = this.value.toByteArray();
                    stringBuilder.append('#');
                }
                catch (IOException iOException) {
                    throw new IllegalArgumentException("DER Value conversion");
                }
                for (int i = 0; i < ((byte[])object).length; ++i) {
                    byte by = object[i];
                    stringBuilder.append(Character.forDigit(by >>> 4 & 15, 16));
                    stringBuilder.append(Character.forDigit(by & 15, 16));
                }
                break block18;
            }
            try {
                object = new String(this.value.getDataBytes(), "UTF8");
                stringBuilder2 = new StringBuilder();
            }
            catch (IOException iOException) {
                throw new IllegalArgumentException("DER Value conversion");
            }
            for (n2 = 0; n2 < ((String)object).length(); ++n2) {
                c = ((String)object).charAt(n2);
                if (!DerValue.isPrintableStringChar(c) && ",=+<>#;\"\\".indexOf(c) < 0) {
                    if (c == '\u0000') {
                        stringBuilder2.append("\\00");
                        continue;
                    }
                    if (debug != null && Debug.isOn("ava")) {
                        try {
                            arrobject = Character.toString(c).getBytes("UTF8");
                        }
                        catch (IOException iOException) {
                            throw new IllegalArgumentException("DER Value conversion");
                        }
                        for (n = 0; n < arrobject.length; ++n) {
                            stringBuilder2.append('\\');
                            stringBuilder2.append(Character.toUpperCase(Character.forDigit(arrobject[n] >>> 4 & 15, 16)));
                            stringBuilder2.append(Character.toUpperCase(Character.forDigit(arrobject[n] & 15, 16)));
                        }
                        continue;
                    }
                    stringBuilder2.append(c);
                    continue;
                }
                if (",=+<>#;\"\\".indexOf(c) >= 0) {
                    stringBuilder2.append('\\');
                }
                stringBuilder2.append(c);
            }
            arrobject = stringBuilder2.toString().toCharArray();
            object = new StringBuilder();
            for (n2 = 0; n2 < arrobject.length && (arrobject[n2] == ' ' || arrobject[n2] == '\r'); ++n2) {
            }
            for (n = arrobject.length - 1; n >= 0 && (arrobject[n] == ' ' || arrobject[n] == '\r'); --n) {
            }
            for (int i = 0; i < arrobject.length; ++i) {
                c = arrobject[i];
                if (i < n2 || i > n) {
                    ((StringBuilder)object).append('\\');
                }
                ((StringBuilder)object).append(c);
            }
            stringBuilder.append(((StringBuilder)object).toString());
        }
        return stringBuilder.toString();
    }

    public String toString() {
        return this.toKeywordValueString(this.toKeyword(1, Collections.emptyMap()));
    }
}

