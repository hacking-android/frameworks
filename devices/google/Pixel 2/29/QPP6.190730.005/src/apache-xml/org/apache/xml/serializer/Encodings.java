/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.serializer;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
import java.util.StringTokenizer;
import org.apache.xml.serializer.EncodingInfo;
import org.apache.xml.serializer.ObjectFactory;
import org.apache.xml.serializer.SecuritySupport;
import org.apache.xml.serializer.SerializerBase;
import org.apache.xml.serializer.utils.WrappedRuntimeException;

public final class Encodings {
    static final String DEFAULT_MIME_ENCODING = "UTF-8";
    private static final String ENCODINGS_FILE;
    private static final Hashtable _encodingTableKeyJava;
    private static final Hashtable _encodingTableKeyMime;
    private static final EncodingInfo[] _encodings;

    static {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(SerializerBase.PKG_PATH);
        stringBuilder.append("/Encodings.properties");
        ENCODINGS_FILE = stringBuilder.toString();
        _encodingTableKeyJava = new Hashtable();
        _encodingTableKeyMime = new Hashtable();
        _encodings = Encodings.loadEncodingInfo();
    }

    private static String convertJava2MimeEncoding(String string) {
        EncodingInfo encodingInfo = (EncodingInfo)_encodingTableKeyJava.get(Encodings.toUpperCaseFast(string));
        if (encodingInfo != null) {
            return encodingInfo.name;
        }
        return string;
    }

    public static String convertMime2JavaEncoding(String string) {
        EncodingInfo[] arrencodingInfo;
        for (int i = 0; i < (arrencodingInfo = _encodings).length; ++i) {
            if (!arrencodingInfo[i].name.equalsIgnoreCase(string)) continue;
            return Encodings._encodings[i].javaName;
        }
        return string;
    }

    static EncodingInfo getEncodingInfo(String object) {
        Object object2;
        String string = Encodings.toUpperCaseFast((String)object);
        object = object2 = (EncodingInfo)_encodingTableKeyJava.get(string);
        if (object2 == null) {
            object = (EncodingInfo)_encodingTableKeyMime.get(string);
        }
        object2 = object;
        if (object == null) {
            object2 = new EncodingInfo(null, null, '\u0000');
        }
        return object2;
    }

    public static char getHighChar(String object) {
        char c;
        char c2;
        String string = Encodings.toUpperCaseFast((String)object);
        EncodingInfo encodingInfo = (EncodingInfo)_encodingTableKeyJava.get(string);
        object = encodingInfo;
        if (encodingInfo == null) {
            object = (EncodingInfo)_encodingTableKeyMime.get(string);
        }
        char c3 = object != null ? (c2 = ((EncodingInfo)object).getHighChar()) : (c = '\u0000');
        return c3;
    }

    static String getMimeEncoding(String string) {
        block6 : {
            block5 : {
                block4 : {
                    if (string != null) break block5;
                    string = System.getProperty("file.encoding", "UTF8");
                    String string2 = DEFAULT_MIME_ENCODING;
                    if (string == null) break block4;
                    try {
                        string = !(string.equalsIgnoreCase("Cp1252") || string.equalsIgnoreCase("ISO8859_1") || string.equalsIgnoreCase("8859_1") || string.equalsIgnoreCase("UTF8")) ? Encodings.convertJava2MimeEncoding(string) : DEFAULT_MIME_ENCODING;
                        if (string != null) {
                            string2 = string;
                        }
                        string = string2;
                    }
                    catch (SecurityException securityException) {
                        string = DEFAULT_MIME_ENCODING;
                    }
                }
                string = DEFAULT_MIME_ENCODING;
                break block6;
                break block6;
            }
            string = Encodings.convertJava2MimeEncoding(string);
        }
        return string;
    }

    static Writer getWriter(OutputStream closeable, String string) throws UnsupportedEncodingException {
        Object object;
        for (int i = 0; i < ((EncodingInfo[])(object = _encodings)).length; ++i) {
            if (!object[i].name.equalsIgnoreCase(string)) continue;
            try {
                object = new OutputStreamWriter((OutputStream)closeable, Encodings._encodings[i].javaName);
                return object;
            }
            catch (UnsupportedEncodingException unsupportedEncodingException) {
                continue;
            }
            catch (IllegalArgumentException illegalArgumentException) {
                // empty catch block
            }
        }
        try {
            closeable = new OutputStreamWriter((OutputStream)closeable, string);
            return closeable;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw new UnsupportedEncodingException(string);
        }
    }

    static boolean isHighUTF16Surrogate(char c) {
        boolean bl = '\ud800' <= c && c <= '\udbff';
        return bl;
    }

    static boolean isLowUTF16Surrogate(char c) {
        boolean bl = '\udc00' <= c && c <= '\udfff';
        return bl;
    }

    public static boolean isRecognizedEncoding(String object) {
        String string = ((String)object).toUpperCase();
        EncodingInfo encodingInfo = (EncodingInfo)_encodingTableKeyJava.get(string);
        object = encodingInfo;
        if (encodingInfo == null) {
            object = (EncodingInfo)_encodingTableKeyMime.get(string);
        }
        return object != null;
    }

    private static int lengthOfMimeNames(String string) {
        int n;
        int n2 = n = string.indexOf(32);
        if (n < 0) {
            n2 = string.length();
        }
        return n2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static EncodingInfo[] loadEncodingInfo() {
        try {
            EncodingInfo[] arrencodingInfo = SecuritySupport.getInstance();
            ArrayList<EncodingInfo> arrayList = arrencodingInfo.getResourceAsStream(ObjectFactory.findClassLoader(), ENCODINGS_FILE);
            Properties properties = new Properties();
            if (arrayList != null) {
                properties.load((InputStream)((Object)arrayList));
                ((InputStream)((Object)arrayList)).close();
            }
            int n = properties.size();
            arrayList = new ArrayList<EncodingInfo>();
            Enumeration enumeration = properties.keys();
            for (int i = 0; i < n; ++i) {
                char c;
                String string = (String)enumeration.nextElement();
                String string2 = properties.getProperty(string);
                int n2 = Encodings.lengthOfMimeNames(string2);
                if (n2 == 0) continue;
                try {
                    c = Integer.decode(string2.substring(n2).trim()).intValue();
                }
                catch (NumberFormatException numberFormatException) {
                    c = '\u0000';
                }
                c = c;
                string2 = string2.substring(0, n2);
                StringTokenizer stringTokenizer = new StringTokenizer(string2, ",");
                n2 = 1;
                char c2 = c;
                while (stringTokenizer.hasMoreTokens()) {
                    string2 = stringTokenizer.nextToken();
                    EncodingInfo encodingInfo = new EncodingInfo(string2, string, c2);
                    arrayList.add(encodingInfo);
                    _encodingTableKeyMime.put(string2.toUpperCase(), encodingInfo);
                    if (n2 != 0) {
                        _encodingTableKeyJava.put(string.toUpperCase(), encodingInfo);
                    }
                    n2 = 0;
                    c2 = c = c2;
                }
            }
            arrencodingInfo = new EncodingInfo[arrayList.size()];
            arrayList.toArray(arrencodingInfo);
            return arrencodingInfo;
        }
        catch (IOException iOException) {
            throw new WrappedRuntimeException(iOException);
        }
        catch (MalformedURLException malformedURLException) {
            throw new WrappedRuntimeException(malformedURLException);
        }
    }

    static int toCodePoint(char c) {
        return c;
    }

    static int toCodePoint(char c, char c2) {
        return (c - 55296 << 10) + (c2 - 56320) + 65536;
    }

    private static String toUpperCaseFast(String string) {
        block3 : {
            boolean bl = false;
            int n = string.length();
            char[] arrc = new char[n];
            for (int i = 0; i < n; ++i) {
                char c = string.charAt(i);
                boolean bl2 = bl;
                char c2 = c;
                if ('a' <= c) {
                    bl2 = bl;
                    c2 = c;
                    if (c <= 'z') {
                        c2 = (char)(c - 32);
                        bl2 = true;
                    }
                }
                arrc[i] = c2;
                bl = bl2;
            }
            if (!bl) break block3;
            string = String.valueOf(arrc);
        }
        return string;
    }
}

