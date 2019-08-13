/*
 * Decompiled with CFR 0.145.
 */
package java.util.jar;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.jar.Manifest;
import sun.misc.ASCIICaseInsensitiveComparator;
import sun.util.logging.PlatformLogger;

public class Attributes
implements Map<Object, Object>,
Cloneable {
    protected Map<Object, Object> map;

    public Attributes() {
        this(11);
    }

    public Attributes(int n) {
        this.map = new HashMap<Object, Object>(n);
    }

    public Attributes(Attributes attributes) {
        this.map = new HashMap<Object, Object>(attributes);
    }

    @Override
    public void clear() {
        this.map.clear();
    }

    public Object clone() {
        return new Attributes(this);
    }

    @Override
    public boolean containsKey(Object object) {
        return this.map.containsKey(object);
    }

    @Override
    public boolean containsValue(Object object) {
        return this.map.containsValue(object);
    }

    @Override
    public Set<Map.Entry<Object, Object>> entrySet() {
        return this.map.entrySet();
    }

    @Override
    public boolean equals(Object object) {
        return this.map.equals(object);
    }

    @Override
    public Object get(Object object) {
        return this.map.get(object);
    }

    public String getValue(String string) {
        return (String)this.get(new Name(string));
    }

    public String getValue(Name name) {
        return (String)this.get(name);
    }

    @Override
    public int hashCode() {
        return this.map.hashCode();
    }

    @Override
    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    @Override
    public Set<Object> keySet() {
        return this.map.keySet();
    }

    @Override
    public Object put(Object object, Object object2) {
        return this.map.put((Name)object, (String)object2);
    }

    @Override
    public void putAll(Map<?, ?> object2) {
        if (Attributes.class.isInstance(object2)) {
            for (Map.Entry entry : object2.entrySet()) {
                this.put(entry.getKey(), entry.getValue());
            }
            return;
        }
        throw new ClassCastException();
    }

    public String putValue(String string, String string2) {
        return (String)this.put(new Name(string), string2);
    }

    void read(Manifest.FastInputStream fastInputStream, byte[] arrby) throws IOException {
        int n;
        String string = null;
        byte[] arrby2 = null;
        while ((n = fastInputStream.readLine(arrby)) != -1) {
            block13 : {
                block17 : {
                    Object object;
                    block16 : {
                        int n2;
                        int n3;
                        int n4;
                        block12 : {
                            block14 : {
                                block15 : {
                                    n4 = 0;
                                    n3 = n - 1;
                                    if (arrby[n3] != 10) break block13;
                                    n = n3;
                                    if (n3 > 0) {
                                        n = n3;
                                        if (arrby[n3 - 1] == 13) {
                                            n = n3 - 1;
                                        }
                                    }
                                    if (n == 0) break;
                                    n3 = 0;
                                    if (arrby[0] != 32) break block14;
                                    if (string == null) break block15;
                                    n3 = 1;
                                    object = new byte[arrby2.length + n - 1];
                                    System.arraycopy(arrby2, 0, (byte[])object, 0, arrby2.length);
                                    System.arraycopy(arrby, 1, (byte[])object, arrby2.length, n - 1);
                                    if (fastInputStream.peek() == 32) {
                                        arrby2 = object;
                                        continue;
                                    }
                                    object = new String((byte[])object, 0, ((byte[])object).length, "UTF8");
                                    arrby2 = null;
                                    n = n3;
                                    break block16;
                                }
                                throw new IOException("misplaced continuation line");
                            }
                            do {
                                n2 = n3 + 1;
                                if (arrby[n3] == 58) break block12;
                                if (n2 >= n) break;
                                n3 = n2;
                            } while (true);
                            throw new IOException("invalid header field");
                        }
                        n3 = n2 + 1;
                        if (arrby[n2] != 32) break block17;
                        string = new String(arrby, 0, 0, n3 - 2);
                        if (fastInputStream.peek() == 32) {
                            arrby2 = new byte[n - n3];
                            System.arraycopy(arrby, n3, arrby2, 0, n - n3);
                            continue;
                        }
                        object = new String(arrby, n3, n - n3, "UTF8");
                        n = n4;
                    }
                    try {
                        if (this.putValue(string, (String)object) == null || n != 0) continue;
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("invalid header field name: ");
                        stringBuilder.append(string);
                        throw new IOException(stringBuilder.toString());
                    }
                    object = PlatformLogger.getLogger("java.util.jar");
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Duplicate name in Manifest: ");
                    stringBuilder.append(string);
                    stringBuilder.append(".\nEnsure that the manifest does not have duplicate entries, and\nthat blank lines separate individual sections in both your\nmanifest and in the META-INF/MANIFEST.MF entry in the jar file.");
                    ((PlatformLogger)object).warning(stringBuilder.toString());
                    continue;
                }
                throw new IOException("invalid header field");
            }
            throw new IOException("line too long");
        }
    }

    @Override
    public Object remove(Object object) {
        return this.map.remove(object);
    }

    @Override
    public int size() {
        return this.map.size();
    }

    @Override
    public Collection<Object> values() {
        return this.map.values();
    }

    /*
     * WARNING - void declaration
     */
    void write(DataOutputStream dataOutputStream) throws IOException {
        for (Map.Entry<Object, Object> entry : this.entrySet()) {
            String string;
            void object;
            StringBuffer stringBuffer = new StringBuffer(((Name)entry.getKey()).toString());
            stringBuffer.append(": ");
            String string2 = string = (String)entry.getValue();
            if (string != null) {
                byte[] arrby = string.getBytes("UTF8");
                String string3 = new String(arrby, 0, 0, arrby.length);
            }
            stringBuffer.append((String)object);
            stringBuffer.append("\r\n");
            Manifest.make72Safe(stringBuffer);
            dataOutputStream.writeBytes(stringBuffer.toString());
        }
        dataOutputStream.writeBytes("\r\n");
    }

    /*
     * WARNING - void declaration
     */
    void writeMain(DataOutputStream dataOutputStream) throws IOException {
        String object2;
        String string = Name.MANIFEST_VERSION.toString();
        String string2 = object2 = this.getValue(string);
        if (object2 == null) {
            string = Name.SIGNATURE_VERSION.toString();
            string2 = this.getValue(string);
        }
        if (string2 != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string);
            stringBuilder.append(": ");
            stringBuilder.append(string2);
            stringBuilder.append("\r\n");
            dataOutputStream.writeBytes(stringBuilder.toString());
        }
        for (Map.Entry<Object, Object> entry : this.entrySet()) {
            void var3_11;
            String string3 = ((Name)entry.getKey()).toString();
            if (string2 == null || string3.equalsIgnoreCase(string)) continue;
            StringBuffer stringBuffer = new StringBuffer(string3);
            stringBuffer.append(": ");
            String string4 = string3 = (String)entry.getValue();
            if (string3 != null) {
                byte[] arrby = string3.getBytes("UTF8");
                String string5 = new String(arrby, 0, 0, arrby.length);
            }
            stringBuffer.append((String)var3_11);
            stringBuffer.append("\r\n");
            Manifest.make72Safe(stringBuffer);
            dataOutputStream.writeBytes(stringBuffer.toString());
        }
        dataOutputStream.writeBytes("\r\n");
    }

    public static class Name {
        public static final Name CLASS_PATH;
        public static final Name CONTENT_TYPE;
        @Deprecated
        public static final Name EXTENSION_INSTALLATION;
        public static final Name EXTENSION_LIST;
        public static final Name EXTENSION_NAME;
        public static final Name IMPLEMENTATION_TITLE;
        @Deprecated
        public static final Name IMPLEMENTATION_URL;
        public static final Name IMPLEMENTATION_VENDOR;
        @Deprecated
        public static final Name IMPLEMENTATION_VENDOR_ID;
        public static final Name IMPLEMENTATION_VERSION;
        public static final Name MAIN_CLASS;
        public static final Name MANIFEST_VERSION;
        public static final Name SEALED;
        public static final Name SIGNATURE_VERSION;
        public static final Name SPECIFICATION_TITLE;
        public static final Name SPECIFICATION_VENDOR;
        public static final Name SPECIFICATION_VERSION;
        private int hashCode = -1;
        private String name;

        static {
            MANIFEST_VERSION = new Name("Manifest-Version");
            SIGNATURE_VERSION = new Name("Signature-Version");
            CONTENT_TYPE = new Name("Content-Type");
            CLASS_PATH = new Name("Class-Path");
            MAIN_CLASS = new Name("Main-Class");
            SEALED = new Name("Sealed");
            EXTENSION_LIST = new Name("Extension-List");
            EXTENSION_NAME = new Name("Extension-Name");
            EXTENSION_INSTALLATION = new Name("Extension-Installation");
            IMPLEMENTATION_TITLE = new Name("Implementation-Title");
            IMPLEMENTATION_VERSION = new Name("Implementation-Version");
            IMPLEMENTATION_VENDOR = new Name("Implementation-Vendor");
            IMPLEMENTATION_VENDOR_ID = new Name("Implementation-Vendor-Id");
            IMPLEMENTATION_URL = new Name("Implementation-URL");
            SPECIFICATION_TITLE = new Name("Specification-Title");
            SPECIFICATION_VERSION = new Name("Specification-Version");
            SPECIFICATION_VENDOR = new Name("Specification-Vendor");
        }

        public Name(String string) {
            if (string != null) {
                if (Name.isValid(string)) {
                    this.name = string.intern();
                    return;
                }
                throw new IllegalArgumentException(string);
            }
            throw new NullPointerException("name");
        }

        private static boolean isAlpha(char c) {
            boolean bl = c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z';
            return bl;
        }

        private static boolean isDigit(char c) {
            boolean bl = c >= '0' && c <= '9';
            return bl;
        }

        private static boolean isValid(char c) {
            boolean bl = Name.isAlpha(c) || Name.isDigit(c) || c == '_' || c == '-';
            return bl;
        }

        private static boolean isValid(String string) {
            int n = string.length();
            if (n <= 70 && n != 0) {
                for (int i = 0; i < n; ++i) {
                    if (Name.isValid(string.charAt(i))) continue;
                    return false;
                }
                return true;
            }
            return false;
        }

        public boolean equals(Object object) {
            boolean bl = object instanceof Name;
            boolean bl2 = false;
            if (bl) {
                if (ASCIICaseInsensitiveComparator.CASE_INSENSITIVE_ORDER.compare(this.name, ((Name)object).name) == 0) {
                    bl2 = true;
                }
                return bl2;
            }
            return false;
        }

        public int hashCode() {
            if (this.hashCode == -1) {
                this.hashCode = ASCIICaseInsensitiveComparator.lowerCaseHashCode(this.name);
            }
            return this.hashCode;
        }

        public String toString() {
            return this.name;
        }
    }

}

