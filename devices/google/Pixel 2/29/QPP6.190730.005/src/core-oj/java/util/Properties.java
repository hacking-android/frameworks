/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.InvalidPropertiesFormatException;
import java.util.Objects;
import java.util.Set;
import java.util.XMLUtils;

public class Properties
extends Hashtable<Object, Object> {
    private static final char[] hexDigit = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    private static final long serialVersionUID = 4112578634029874840L;
    protected Properties defaults;

    public Properties() {
        this(null);
    }

    public Properties(Properties properties) {
        this.defaults = properties;
    }

    private void enumerate(Hashtable<String, Object> hashtable) {
        synchronized (this) {
            if (this.defaults != null) {
                this.defaults.enumerate(hashtable);
            }
            Enumeration enumeration = this.keys();
            while (enumeration.hasMoreElements()) {
                String string = (String)enumeration.nextElement();
                hashtable.put(string, this.get(string));
            }
            return;
        }
    }

    private void enumerateStringProperties(Hashtable<String, String> hashtable) {
        synchronized (this) {
            if (this.defaults != null) {
                this.defaults.enumerateStringProperties(hashtable);
            }
            Enumeration enumeration = this.keys();
            while (enumeration.hasMoreElements()) {
                Object k = enumeration.nextElement();
                Object v = this.get(k);
                if (!(k instanceof String) || !(v instanceof String)) continue;
                hashtable.put((String)k, (String)v);
            }
            return;
        }
    }

    private void load0(LineReader lineReader) throws IOException {
        int n;
        char[] arrc = new char[1024];
        while ((n = lineReader.readLine()) >= 0) {
            int n2;
            char c;
            int n3;
            int n4 = 0;
            int n5 = n;
            int n6 = 0;
            int n7 = 0;
            do {
                int n8 = 0;
                n3 = n5;
                n2 = n6;
                if (n4 >= n) break;
                c = lineReader.lineBuf[n4];
                if ((c == '=' || c == ':') && n7 == 0) {
                    n3 = n4 + 1;
                    n2 = 1;
                    break;
                }
                if (Character.isWhitespace(c) && n7 == 0) {
                    n3 = n4 + 1;
                    n2 = n6;
                    break;
                }
                if (c == '\\') {
                    n3 = n8;
                    if (n7 == 0) {
                        n3 = 1;
                    }
                } else {
                    n3 = 0;
                }
                ++n4;
                n7 = n3;
            } while (true);
            while (n3 < n) {
                c = lineReader.lineBuf[n3];
                n5 = n2;
                if (!Character.isWhitespace(c)) {
                    if (n2 != 0 || c != '=' && c != ':') break;
                    n5 = 1;
                }
                ++n3;
                n2 = n5;
            }
            this.put(this.loadConvert(lineReader.lineBuf, 0, n4, arrc), this.loadConvert(lineReader.lineBuf, n3, n - n3, arrc));
        }
    }

    private String loadConvert(char[] arrc, int n, int n2, char[] arrc2) {
        int n3;
        int n4;
        int n5;
        char[] arrc3 = arrc2;
        if (arrc2.length < n2) {
            n5 = n4 = n2 * 2;
            if (n4 < 0) {
                n5 = Integer.MAX_VALUE;
            }
            arrc3 = new char[n5];
        }
        n4 = 0;
        n5 = n;
        while ((n3 = n5) < n + n2) {
            n5 = n3 + 1;
            if ((n3 = arrc[n3]) == 92) {
                n3 = n5 + 1;
                int n6 = arrc[n5];
                if (n6 == 117) {
                    n5 = 0;
                    n6 = 0;
                    while (n6 < 4) {
                        char c = arrc[n3];
                        block0 : switch (c) {
                            default: {
                                switch (c) {
                                    default: {
                                        switch (c) {
                                            default: {
                                                throw new IllegalArgumentException("Malformed \\uxxxx encoding.");
                                            }
                                            case 'a': 
                                            case 'b': 
                                            case 'c': 
                                            case 'd': 
                                            case 'e': 
                                            case 'f': 
                                        }
                                        n5 = (n5 << 4) + 10 + c - 97;
                                        break block0;
                                    }
                                    case 'A': 
                                    case 'B': 
                                    case 'C': 
                                    case 'D': 
                                    case 'E': 
                                    case 'F': 
                                }
                                n5 = (n5 << 4) + 10 + c - 65;
                                break;
                            }
                            case '0': 
                            case '1': 
                            case '2': 
                            case '3': 
                            case '4': 
                            case '5': 
                            case '6': 
                            case '7': 
                            case '8': 
                            case '9': {
                                n5 = (n5 << 4) + c - 48;
                            }
                        }
                        ++n6;
                        ++n3;
                    }
                    arrc3[n4] = (char)n5;
                    n5 = n3;
                    ++n4;
                    continue;
                }
                if (n6 == 116) {
                    n5 = 9;
                } else if (n6 == 114) {
                    n5 = 13;
                } else if (n6 == 110) {
                    n5 = 10;
                } else {
                    n5 = n6;
                    if (n6 == 102) {
                        n5 = 12;
                    }
                }
                arrc3[n4] = (char)n5;
                ++n4;
                n5 = n3;
                continue;
            }
            arrc3[n4] = (char)n3;
            ++n4;
        }
        return new String(arrc3, 0, n4);
    }

    private String saveConvert(String string, boolean bl, boolean bl2) {
        int n;
        int n2 = string.length();
        int n3 = n = n2 * 2;
        if (n < 0) {
            n3 = Integer.MAX_VALUE;
        }
        StringBuffer stringBuffer = new StringBuffer(n3);
        for (n3 = 0; n3 < n2; ++n3) {
            char c = string.charAt(n3);
            if (c > '=' && c < '') {
                if (c == '\\') {
                    stringBuffer.append('\\');
                    stringBuffer.append('\\');
                    continue;
                }
                stringBuffer.append(c);
                continue;
            }
            if (c != '\t') {
                if (c != '\n') {
                    if (c != '\f') {
                        if (c != '\r') {
                            if (c != ' ') {
                                if (c != '!' && c != '#' && c != ':' && c != '=') {
                                    n = c >= ' ' && c <= '~' ? 0 : 1;
                                    if ((n & bl2) != 0) {
                                        stringBuffer.append('\\');
                                        stringBuffer.append('u');
                                        stringBuffer.append(Properties.toHex(c >> 12 & 15));
                                        stringBuffer.append(Properties.toHex(c >> 8 & 15));
                                        stringBuffer.append(Properties.toHex(c >> 4 & 15));
                                        stringBuffer.append(Properties.toHex(c & 15));
                                        continue;
                                    }
                                    stringBuffer.append(c);
                                    continue;
                                }
                                stringBuffer.append('\\');
                                stringBuffer.append(c);
                                continue;
                            }
                            if (n3 == 0 || bl) {
                                stringBuffer.append('\\');
                            }
                            stringBuffer.append(' ');
                            continue;
                        }
                        stringBuffer.append('\\');
                        stringBuffer.append('r');
                        continue;
                    }
                    stringBuffer.append('\\');
                    stringBuffer.append('f');
                    continue;
                }
                stringBuffer.append('\\');
                stringBuffer.append('n');
                continue;
            }
            stringBuffer.append('\\');
            stringBuffer.append('t');
        }
        return stringBuffer.toString();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void store0(BufferedWriter bufferedWriter, String object, boolean bl) throws IOException {
        if (object != null) {
            Properties.writeComments(bufferedWriter, (String)object);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("#");
        ((StringBuilder)object).append(new Date().toString());
        bufferedWriter.write(((StringBuilder)object).toString());
        bufferedWriter.newLine();
        synchronized (this) {
            object = this.keys();
            do {
                if (!object.hasMoreElements()) {
                    // MONITOREXIT [2, 3, 4] lbl14 : MonitorExitStatement: MONITOREXIT : this
                    bufferedWriter.flush();
                    return;
                }
                String string = (String)object.nextElement();
                CharSequence charSequence = (String)this.get(string);
                string = this.saveConvert(string, true, bl);
                String string2 = this.saveConvert((String)charSequence, false, bl);
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append(string);
                ((StringBuilder)charSequence).append("=");
                ((StringBuilder)charSequence).append(string2);
                bufferedWriter.write(((StringBuilder)charSequence).toString());
                bufferedWriter.newLine();
            } while (true);
        }
    }

    private static char toHex(int n) {
        return hexDigit[n & 15];
    }

    private static void writeComments(BufferedWriter bufferedWriter, String string) throws IOException {
        bufferedWriter.write("#");
        int n = string.length();
        int n2 = 0;
        int n3 = 0;
        char[] arrc = new char[6];
        arrc[0] = (char)92;
        arrc[1] = (char)117;
        while (n2 < n) {
            int n4;
            int n5;
            block10 : {
                block12 : {
                    block13 : {
                        char c;
                        block11 : {
                            block9 : {
                                c = string.charAt(n2);
                                if (c > '\u00ff' || c == '\n') break block9;
                                n5 = n2;
                                n4 = n3;
                                if (c != '\r') break block10;
                            }
                            if (n3 != n2) {
                                bufferedWriter.write(string.substring(n3, n2));
                            }
                            if (c <= '\u00ff') break block11;
                            arrc[2] = Properties.toHex(c >> 12 & 15);
                            arrc[3] = Properties.toHex(c >> 8 & 15);
                            arrc[4] = Properties.toHex(c >> 4 & 15);
                            arrc[5] = Properties.toHex(c & 15);
                            bufferedWriter.write(new String(arrc));
                            break block12;
                        }
                        bufferedWriter.newLine();
                        n4 = n2;
                        if (c == '\r') {
                            n4 = n2;
                            if (n2 != n - 1) {
                                n4 = n2;
                                if (string.charAt(n2 + 1) == '\n') {
                                    n4 = n2 + 1;
                                }
                            }
                        }
                        if (n4 == n - 1) break block13;
                        n2 = n4;
                        if (string.charAt(n4 + 1) == '#') break block12;
                        n2 = n4;
                        if (string.charAt(n4 + 1) == '!') break block12;
                    }
                    bufferedWriter.write("#");
                    n2 = n4;
                }
                n4 = n2 + 1;
                n5 = n2;
            }
            n2 = n5 + 1;
            n3 = n4;
        }
        if (n3 != n2) {
            bufferedWriter.write(string.substring(n3, n2));
        }
        bufferedWriter.newLine();
    }

    public String getProperty(String string) {
        Object object;
        block0 : {
            Properties properties;
            object = super.get(string);
            object = object instanceof String ? (String)object : null;
            if (object != null || (properties = this.defaults) == null) break block0;
            object = properties.getProperty(string);
        }
        return object;
    }

    public String getProperty(String string, String string2) {
        if ((string = this.getProperty(string)) != null) {
            string2 = string;
        }
        return string2;
    }

    public void list(PrintStream printStream) {
        printStream.println("-- listing properties --");
        Hashtable<String, Object> hashtable = new Hashtable<String, Object>();
        this.enumerate(hashtable);
        Enumeration<String> enumeration = hashtable.keys();
        while (enumeration.hasMoreElements()) {
            CharSequence charSequence;
            String string = enumeration.nextElement();
            CharSequence charSequence2 = charSequence = (String)hashtable.get(string);
            if (((String)charSequence).length() > 40) {
                charSequence2 = new StringBuilder();
                ((StringBuilder)charSequence2).append(((String)charSequence).substring(0, 37));
                ((StringBuilder)charSequence2).append("...");
                charSequence2 = ((StringBuilder)charSequence2).toString();
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(string);
            ((StringBuilder)charSequence).append("=");
            ((StringBuilder)charSequence).append((String)charSequence2);
            printStream.println(((StringBuilder)charSequence).toString());
        }
    }

    public void list(PrintWriter printWriter) {
        printWriter.println("-- listing properties --");
        Hashtable<String, Object> hashtable = new Hashtable<String, Object>();
        this.enumerate(hashtable);
        Enumeration<String> enumeration = hashtable.keys();
        while (enumeration.hasMoreElements()) {
            CharSequence charSequence;
            String string = enumeration.nextElement();
            CharSequence charSequence2 = charSequence = (String)hashtable.get(string);
            if (((String)charSequence).length() > 40) {
                charSequence2 = new StringBuilder();
                ((StringBuilder)charSequence2).append(((String)charSequence).substring(0, 37));
                ((StringBuilder)charSequence2).append("...");
                charSequence2 = ((StringBuilder)charSequence2).toString();
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(string);
            ((StringBuilder)charSequence).append("=");
            ((StringBuilder)charSequence).append((String)charSequence2);
            printWriter.println(((StringBuilder)charSequence).toString());
        }
    }

    public void load(InputStream inputStream) throws IOException {
        synchronized (this) {
            LineReader lineReader = new LineReader(inputStream);
            this.load0(lineReader);
            return;
        }
    }

    public void load(Reader reader) throws IOException {
        synchronized (this) {
            LineReader lineReader = new LineReader(reader);
            this.load0(lineReader);
            return;
        }
    }

    public void loadFromXML(InputStream inputStream) throws IOException, InvalidPropertiesFormatException {
        synchronized (this) {
            XMLUtils.load(this, Objects.requireNonNull(inputStream));
            inputStream.close();
            return;
        }
    }

    public Enumeration<?> propertyNames() {
        Hashtable<String, Object> hashtable = new Hashtable<String, Object>();
        this.enumerate(hashtable);
        return hashtable.keys();
    }

    @Deprecated
    public void save(OutputStream outputStream, String string) {
        try {
            this.store(outputStream, string);
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

    public Object setProperty(String string, String string2) {
        synchronized (this) {
            string = this.put(string, string2);
            return string;
        }
    }

    public void store(OutputStream outputStream, String string) throws IOException {
        this.store0(new BufferedWriter(new OutputStreamWriter(outputStream, "8859_1")), string, true);
    }

    public void store(Writer writer, String string) throws IOException {
        writer = writer instanceof BufferedWriter ? (BufferedWriter)writer : new BufferedWriter(writer);
        this.store0((BufferedWriter)writer, string, false);
    }

    public void storeToXML(OutputStream outputStream, String string) throws IOException {
        this.storeToXML(outputStream, string, "UTF-8");
    }

    public void storeToXML(OutputStream outputStream, String string, String string2) throws IOException {
        XMLUtils.save(this, Objects.requireNonNull(outputStream), string, Objects.requireNonNull(string2));
    }

    public Set<String> stringPropertyNames() {
        Hashtable<String, String> hashtable = new Hashtable<String, String>();
        this.enumerateStringProperties(hashtable);
        return hashtable.keySet();
    }

    class LineReader {
        byte[] inByteBuf;
        char[] inCharBuf;
        int inLimit = 0;
        int inOff = 0;
        InputStream inStream;
        char[] lineBuf = new char[1024];
        Reader reader;

        public LineReader(InputStream inputStream) {
            this.inStream = inputStream;
            this.inByteBuf = new byte[8192];
        }

        public LineReader(Reader reader) {
            this.reader = reader;
            this.inCharBuf = new char[8192];
        }

        int readLine() throws IOException {
            int n = 0;
            int n2 = 1;
            boolean bl = false;
            int n3 = 1;
            int n4 = 0;
            int n5 = 0;
            int n6 = 0;
            do {
                int n7;
                Object object;
                int n8 = this.inOff;
                int n9 = this.inLimit;
                int n10 = 0;
                if (n8 >= n9) {
                    object = this.inStream;
                    n8 = object == null ? this.reader.read(this.inCharBuf) : ((InputStream)object).read(this.inByteBuf);
                    this.inLimit = n8;
                    this.inOff = 0;
                    if (this.inLimit <= 0) {
                        if (n != 0 && !bl) {
                            n2 = n;
                            if (n5 != 0) {
                                n2 = n - 1;
                            }
                            return n2;
                        }
                        return -1;
                    }
                }
                if (this.inStream != null) {
                    object = this.inByteBuf;
                    n8 = this.inOff;
                    this.inOff = n8 + 1;
                    n7 = n8 = (int)((char)(object[n8] & 255));
                } else {
                    object = this.inCharBuf;
                    n8 = this.inOff;
                    this.inOff = n8 + 1;
                    n7 = n8 = object[n8];
                }
                n8 = n6;
                if (n6 != 0) {
                    n8 = 0;
                    n6 = 0;
                    if (n7 == 10) continue;
                }
                int n11 = n2;
                n6 = n4;
                if (n2 != 0) {
                    if (Character.isWhitespace((char)n7)) {
                        n6 = n8;
                        continue;
                    }
                    if (n4 == 0) {
                        n6 = n8;
                        if (n7 == 13) continue;
                        if (n7 == 10) {
                            n6 = n8;
                            continue;
                        }
                    }
                    n11 = 0;
                    n6 = 0;
                }
                n9 = n3;
                if (n3 != 0) {
                    n9 = 0;
                    n3 = 0;
                    if (n7 == 35 || n7 == 33) {
                        bl = true;
                        n2 = n11;
                        n4 = n6;
                        n6 = n8;
                        continue;
                    }
                }
                if (n7 != 10 && n7 != 13) {
                    object = this.lineBuf;
                    n2 = n + 1;
                    object[n] = n7;
                    if (n2 == ((byte[])object).length) {
                        n = n3 = ((Object)object).length * 2;
                        if (n3 < 0) {
                            n = Integer.MAX_VALUE;
                        }
                        char[] arrc = new char[n];
                        object = this.lineBuf;
                        System.arraycopy(object, 0, (Object)arrc, 0, ((Object)object).length);
                        this.lineBuf = arrc;
                    }
                    if (n7 == 92) {
                        n = n10;
                        if (n5 == 0) {
                            n = 1;
                        }
                        n5 = n;
                        n = n2;
                        n2 = n11;
                        n3 = n9;
                        n4 = n6;
                        n6 = n8;
                        continue;
                    }
                    n5 = 0;
                    n = n2;
                    n2 = n11;
                    n3 = n9;
                    n4 = n6;
                    n6 = n8;
                    continue;
                }
                if (!bl && n != 0) {
                    if (this.inOff >= this.inLimit) {
                        object = this.inStream;
                        n2 = object == null ? this.reader.read(this.inCharBuf) : ((InputStream)object).read(this.inByteBuf);
                        this.inLimit = n2;
                        this.inOff = 0;
                        if (this.inLimit <= 0) {
                            n2 = n;
                            if (n5 != 0) {
                                n2 = n - 1;
                            }
                            return n2;
                        }
                    }
                    if (n5 != 0) {
                        int n12 = n - 1;
                        int n13 = 1;
                        n11 = 1;
                        n10 = 0;
                        n = n12;
                        n2 = n13;
                        n3 = n9;
                        n4 = n11;
                        n5 = n10;
                        n6 = n8;
                        if (n7 != 13) continue;
                        n6 = 1;
                        n = n12;
                        n2 = n13;
                        n3 = n9;
                        n4 = n11;
                        n5 = n10;
                        continue;
                    }
                    return n;
                }
                bl = false;
                n3 = 1;
                n2 = 1;
                n = 0;
                n4 = n6;
                n6 = n8;
            } while (true);
        }
    }

}

