/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.optimization.FastNative
 *  java.lang.CaseMapper
 *  java.lang.StringFactory
 *  libcore.util.CharsetUtils
 */
package java.lang;

import dalvik.annotation.optimization.FastNative;
import java.io.ObjectStreamField;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Comparator;
import java.util.Formatter;
import java.util.Iterator;
import java.util.Locale;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import libcore.util.CharsetUtils;

public final class String
implements Serializable,
Comparable<String>,
CharSequence {
    public static final Comparator<String> CASE_INSENSITIVE_ORDER;
    private static final ObjectStreamField[] serialPersistentFields;
    private static final long serialVersionUID = -6849794470754667710L;
    private final int count;
    private int hash;

    static {
        serialPersistentFields = new ObjectStreamField[0];
        CASE_INSENSITIVE_ORDER = new CaseInsensitiveComparator();
    }

    public String() {
        throw new UnsupportedOperationException("Use StringFactory instead.");
    }

    @Deprecated
    String(int n, int n2, char[] arrc) {
        throw new UnsupportedOperationException("Use StringFactory instead.");
    }

    public String(String string) {
        throw new UnsupportedOperationException("Use StringFactory instead.");
    }

    public String(StringBuffer stringBuffer) {
        throw new UnsupportedOperationException("Use StringFactory instead.");
    }

    public String(StringBuilder stringBuilder) {
        throw new UnsupportedOperationException("Use StringFactory instead.");
    }

    public String(byte[] arrby) {
        throw new UnsupportedOperationException("Use StringFactory instead.");
    }

    @Deprecated
    public String(byte[] arrby, int n) {
        throw new UnsupportedOperationException("Use StringFactory instead.");
    }

    public String(byte[] arrby, int n, int n2) {
        throw new UnsupportedOperationException("Use StringFactory instead.");
    }

    @Deprecated
    public String(byte[] arrby, int n, int n2, int n3) {
        throw new UnsupportedOperationException("Use StringFactory instead.");
    }

    public String(byte[] arrby, int n, int n2, String string) throws UnsupportedEncodingException {
        throw new UnsupportedOperationException("Use StringFactory instead.");
    }

    public String(byte[] arrby, int n, int n2, Charset charset) {
        throw new UnsupportedOperationException("Use StringFactory instead.");
    }

    public String(byte[] arrby, String string) throws UnsupportedEncodingException {
        throw new UnsupportedOperationException("Use StringFactory instead.");
    }

    public String(byte[] arrby, Charset charset) {
        throw new UnsupportedOperationException("Use StringFactory instead.");
    }

    public String(char[] arrc) {
        throw new UnsupportedOperationException("Use StringFactory instead.");
    }

    public String(char[] arrc, int n, int n2) {
        throw new UnsupportedOperationException("Use StringFactory instead.");
    }

    public String(int[] arrn, int n, int n2) {
        throw new UnsupportedOperationException("Use StringFactory instead.");
    }

    public static String copyValueOf(char[] arrc) {
        return new String(arrc);
    }

    public static String copyValueOf(char[] arrc, int n, int n2) {
        return new String(arrc, n, n2);
    }

    @FastNative
    private native String doReplace(char var1, char var2);

    @FastNative
    private native String fastSubstring(int var1, int var2);

    public static String format(String string, Object ... arrobject) {
        return new Formatter().format(string, arrobject).toString();
    }

    public static String format(Locale locale, String string, Object ... arrobject) {
        return new Formatter(locale).format(string, arrobject).toString();
    }

    private static int indexOf(String string, String string2, int n) {
        int n2 = string.length();
        int n3 = string2.length();
        int n4 = -1;
        if (n >= n2) {
            n = n4;
            if (n3 == 0) {
                n = n2;
            }
            return n;
        }
        n4 = n;
        if (n < 0) {
            n4 = 0;
        }
        if (n3 == 0) {
            return n4;
        }
        char c = string2.charAt(0);
        int n5 = n2 - n3;
        n = n4;
        while (n <= n5) {
            n4 = n;
            if (string.charAt(n) != c) {
                do {
                    n4 = ++n;
                    if (n > n5) break;
                    n4 = n;
                } while (string.charAt(n) != c);
            }
            if (n4 <= n5) {
                n2 = n4 + 1;
                int n6 = n2 + n3 - 1;
                n = 1;
                while (n2 < n6 && string.charAt(n2) == string2.charAt(n)) {
                    ++n2;
                    ++n;
                }
                if (n2 == n6) {
                    return n4;
                }
            }
            n = n4 + 1;
        }
        return -1;
    }

    static int indexOf(char[] arrc, int n, int n2, String string, int n3) {
        return String.indexOf(arrc, n, n2, string.toCharArray(), 0, string.length(), n3);
    }

    static int indexOf(char[] arrc, int n, int n2, char[] arrc2, int n3, int n4, int n5) {
        int n6 = -1;
        if (n5 >= n2) {
            n = n6;
            if (n4 == 0) {
                n = n2;
            }
            return n;
        }
        n6 = n5;
        if (n5 < 0) {
            n6 = 0;
        }
        if (n4 == 0) {
            return n6;
        }
        char c = arrc2[n3];
        int n7 = n2 - n4 + n;
        n2 = n + n6;
        while (n2 <= n7) {
            n5 = n2;
            if (arrc[n2] != c) {
                do {
                    n5 = ++n2;
                    if (n2 > n7) break;
                    n5 = n2;
                } while (arrc[n2] != c);
            }
            if (n5 <= n7) {
                n6 = n5 + 1;
                int n8 = n6 + n4 - 1;
                n2 = n3 + 1;
                while (n6 < n8 && arrc[n6] == arrc2[n2]) {
                    ++n6;
                    ++n2;
                }
                if (n6 == n8) {
                    return n5 - n;
                }
            }
            n2 = n5 + 1;
        }
        return -1;
    }

    private int indexOfSupplementary(int n, int n2) {
        if (Character.isValidCodePoint(n)) {
            char c = Character.highSurrogate(n);
            char c2 = Character.lowSurrogate(n);
            int n3 = this.length();
            for (n = n2; n < n3 - 1; ++n) {
                if (this.charAt(n) != c || this.charAt(n + 1) != c2) continue;
                return n;
            }
        }
        return -1;
    }

    public static String join(CharSequence object, Iterable<? extends CharSequence> object2) {
        Objects.requireNonNull(object);
        Objects.requireNonNull(object2);
        object = new StringJoiner((CharSequence)object);
        object2 = object2.iterator();
        while (object2.hasNext()) {
            ((StringJoiner)object).add((CharSequence)object2.next());
        }
        return ((StringJoiner)object).toString();
    }

    public static String join(CharSequence object, CharSequence ... arrcharSequence) {
        Objects.requireNonNull(object);
        Objects.requireNonNull(arrcharSequence);
        object = new StringJoiner((CharSequence)object);
        int n = arrcharSequence.length;
        for (int i = 0; i < n; ++i) {
            ((StringJoiner)object).add(arrcharSequence[i]);
        }
        return ((StringJoiner)object).toString();
    }

    private static int lastIndexOf(String string, String string2, int n) {
        int n2;
        int n3 = string.length();
        int n4 = string2.length();
        int n5 = n3 - n4;
        if (n < 0) {
            return -1;
        }
        n3 = n;
        if (n > n5) {
            n3 = n5;
        }
        if (n4 == 0) {
            return n3;
        }
        int n6 = n4 - 1;
        char c = string2.charAt(n6);
        int n7 = n4 - 1;
        n = n7 + n3;
        block0 : do {
            if (n >= n7 && string.charAt(n) != c) {
                --n;
                continue;
            }
            if (n < n7) {
                return -1;
            }
            n5 = n - 1;
            n2 = n5 - (n4 - 1);
            n3 = n6 - 1;
            while (n5 > n2) {
                if (string.charAt(n5) != string2.charAt(n3)) {
                    --n;
                    continue block0;
                }
                --n5;
                --n3;
            }
            break;
        } while (true);
        return n2 + 1;
    }

    static int lastIndexOf(char[] arrc, int n, int n2, String string, int n3) {
        return String.lastIndexOf(arrc, n, n2, string.toCharArray(), 0, string.length(), n3);
    }

    static int lastIndexOf(char[] arrc, int n, int n2, char[] arrc2, int n3, int n4, int n5) {
        int n6;
        int n7 = n2 - n4;
        if (n5 < 0) {
            return -1;
        }
        n2 = n5;
        if (n5 > n7) {
            n2 = n7;
        }
        if (n4 == 0) {
            return n2;
        }
        int n8 = n3 + n4 - 1;
        char c = arrc2[n8];
        n7 = n + n4 - 1;
        n2 = n7 + n2;
        block0 : do {
            if (n2 >= n7 && arrc[n2] != c) {
                --n2;
                continue;
            }
            if (n2 < n7) {
                return -1;
            }
            n5 = n2 - 1;
            n6 = n5 - (n4 - 1);
            n3 = n8 - 1;
            while (n5 > n6) {
                if (arrc[n5] != arrc2[n3]) {
                    --n2;
                    continue block0;
                }
                --n5;
                --n3;
            }
            break;
        } while (true);
        return n6 - n + 1;
    }

    private int lastIndexOfSupplementary(int n, int n2) {
        if (Character.isValidCodePoint(n)) {
            char c = Character.highSurrogate(n);
            char c2 = Character.lowSurrogate(n);
            for (n = Math.min((int)n2, (int)(this.length() - 2)); n >= 0; --n) {
                if (this.charAt(n) != c || this.charAt(n + 1) != c2) continue;
                return n;
            }
        }
        return -1;
    }

    private boolean nonSyncContentEquals(AbstractStringBuilder abstractStringBuilder) {
        char[] arrc = abstractStringBuilder.getValue();
        int n = this.length();
        if (n != abstractStringBuilder.length()) {
            return false;
        }
        for (int i = 0; i < n; ++i) {
            if (this.charAt(i) == arrc[i]) continue;
            return false;
        }
        return true;
    }

    public static String valueOf(char c) {
        return StringFactory.newStringFromChars((int)0, (int)1, (char[])new char[]{c});
    }

    public static String valueOf(double d) {
        return Double.toString(d);
    }

    public static String valueOf(float f) {
        return Float.toString(f);
    }

    public static String valueOf(int n) {
        return Integer.toString(n);
    }

    public static String valueOf(long l) {
        return Long.toString(l);
    }

    public static String valueOf(Object object) {
        object = object == null ? "null" : object.toString();
        return object;
    }

    public static String valueOf(boolean bl) {
        String string = bl ? "true" : "false";
        return string;
    }

    public static String valueOf(char[] arrc) {
        return new String(arrc);
    }

    public static String valueOf(char[] arrc, int n, int n2) {
        return new String(arrc, n, n2);
    }

    @FastNative
    @Override
    public native char charAt(int var1);

    public int codePointAt(int n) {
        if (n >= 0 && n < this.length()) {
            return Character.codePointAt(this, n);
        }
        throw new StringIndexOutOfBoundsException(n);
    }

    public int codePointBefore(int n) {
        int n2 = n - 1;
        if (n2 >= 0 && n2 < this.length()) {
            return Character.codePointBefore(this, n);
        }
        throw new StringIndexOutOfBoundsException(n);
    }

    public int codePointCount(int n, int n2) {
        if (n >= 0 && n2 <= this.length() && n <= n2) {
            return Character.codePointCount(this, n, n2);
        }
        throw new IndexOutOfBoundsException();
    }

    @FastNative
    @Override
    public native int compareTo(String var1);

    public int compareToIgnoreCase(String string) {
        return CASE_INSENSITIVE_ORDER.compare(this, string);
    }

    @FastNative
    public native String concat(String var1);

    public boolean contains(CharSequence charSequence) {
        boolean bl = this.indexOf(charSequence.toString()) > -1;
        return bl;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean contentEquals(CharSequence charSequence) {
        if (charSequence instanceof AbstractStringBuilder) {
            if (!(charSequence instanceof StringBuffer)) return this.nonSyncContentEquals((AbstractStringBuilder)charSequence);
            synchronized (charSequence) {
                return this.nonSyncContentEquals((AbstractStringBuilder)charSequence);
            }
        }
        if (charSequence instanceof String) {
            return this.equals(charSequence);
        }
        int n = this.length();
        if (n != charSequence.length()) {
            return false;
        }
        int n2 = 0;
        while (n2 < n) {
            if (this.charAt(n2) != charSequence.charAt(n2)) {
                return false;
            }
            ++n2;
        }
        return true;
    }

    public boolean contentEquals(StringBuffer stringBuffer) {
        return this.contentEquals((CharSequence)stringBuffer);
    }

    public boolean endsWith(String string) {
        return this.startsWith(string, this.length() - string.length());
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object instanceof String) {
            object = (String)object;
            int n = this.length();
            if (n == ((String)object).length()) {
                int n2 = 0;
                while (n != 0) {
                    if (this.charAt(n2) != ((String)object).charAt(n2)) {
                        return false;
                    }
                    ++n2;
                    --n;
                }
                return true;
            }
        }
        return false;
    }

    public boolean equalsIgnoreCase(String string) {
        int n = this.length();
        boolean bl = true;
        if (!(this == string || string != null && string.length() == n && this.regionMatches(true, 0, string, 0, n))) {
            bl = false;
        }
        return bl;
    }

    @Deprecated
    public void getBytes(int n, int n2, byte[] arrby, int n3) {
        if (n >= 0) {
            if (n2 <= this.length()) {
                if (n <= n2) {
                    while (n < n2) {
                        arrby[n3] = (byte)this.charAt(n);
                        ++n3;
                        ++n;
                    }
                    return;
                }
                throw new StringIndexOutOfBoundsException(this, n2 - n);
            }
            throw new StringIndexOutOfBoundsException(this, n2);
        }
        throw new StringIndexOutOfBoundsException(this, n);
    }

    public byte[] getBytes() {
        return this.getBytes(Charset.defaultCharset());
    }

    public byte[] getBytes(String string) throws UnsupportedEncodingException {
        if (string != null) {
            return this.getBytes(Charset.forNameUEE(string));
        }
        throw new NullPointerException();
    }

    public byte[] getBytes(Charset comparable) {
        if (comparable != null) {
            int n = this.length();
            byte[] arrby = ((Charset)comparable).name();
            if ("UTF-8".equals(arrby)) {
                return CharsetUtils.toUtf8Bytes((String)this, (int)0, (int)n);
            }
            if ("ISO-8859-1".equals(arrby)) {
                return CharsetUtils.toIsoLatin1Bytes((String)this, (int)0, (int)n);
            }
            if ("US-ASCII".equals(arrby)) {
                return CharsetUtils.toAsciiBytes((String)this, (int)0, (int)n);
            }
            if ("UTF-16BE".equals(arrby)) {
                return CharsetUtils.toBigEndianUtf16Bytes((String)this, (int)0, (int)n);
            }
            comparable = ((Charset)comparable).encode(this);
            arrby = new byte[((Buffer)((Object)comparable)).limit()];
            ((ByteBuffer)comparable).get(arrby);
            return arrby;
        }
        throw new NullPointerException("charset == null");
    }

    public void getChars(int n, int n2, char[] object, int n3) {
        if (object != null) {
            if (n >= 0) {
                if (n2 <= this.length()) {
                    int n4 = n2 - n;
                    if (n2 >= n) {
                        if (n3 >= 0) {
                            if (n3 <= ((char[])object).length) {
                                if (n4 <= ((char[])object).length - n3) {
                                    this.getCharsNoCheck(n, n2, (char[])object, n3);
                                    return;
                                }
                                StringBuilder stringBuilder = new StringBuilder();
                                stringBuilder.append("n > dst.length - dstBegin. n=");
                                stringBuilder.append(n4);
                                stringBuilder.append(", dst.length=");
                                stringBuilder.append(((Object)object).length);
                                stringBuilder.append("dstBegin=");
                                stringBuilder.append(n3);
                                throw new ArrayIndexOutOfBoundsException(stringBuilder.toString());
                            }
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("dstBegin > dst.length. dstBegin=");
                            stringBuilder.append(n3);
                            stringBuilder.append(", dst.length=");
                            stringBuilder.append(((Object)object).length);
                            throw new ArrayIndexOutOfBoundsException(stringBuilder.toString());
                        }
                        object = new StringBuilder();
                        ((StringBuilder)object).append("dstBegin < 0. dstBegin=");
                        ((StringBuilder)object).append(n3);
                        throw new ArrayIndexOutOfBoundsException(((StringBuilder)object).toString());
                    }
                    throw new StringIndexOutOfBoundsException(this, n, n4);
                }
                throw new StringIndexOutOfBoundsException(this, n2);
            }
            throw new StringIndexOutOfBoundsException(this, n);
        }
        throw new NullPointerException("dst == null");
    }

    void getChars(char[] arrc, int n) {
        this.getCharsNoCheck(0, this.length(), arrc, n);
    }

    @FastNative
    native void getCharsNoCheck(int var1, int var2, char[] var3, int var4);

    public int hashCode() {
        int n = this.hash;
        int n2 = this.length();
        int n3 = n;
        if (n == 0) {
            n3 = n;
            if (n2 > 0) {
                for (n3 = 0; n3 < n2; ++n3) {
                    n = n * 31 + this.charAt(n3);
                }
                this.hash = n;
                n3 = n;
            }
        }
        return n3;
    }

    public int indexOf(int n) {
        return this.indexOf(n, 0);
    }

    public int indexOf(int n, int n2) {
        int n3;
        int n4 = this.length();
        if (n2 < 0) {
            n3 = 0;
        } else {
            n3 = n2;
            if (n2 >= n4) {
                return -1;
            }
        }
        if (n < 65536) {
            while (n3 < n4) {
                if (this.charAt(n3) == n) {
                    return n3;
                }
                ++n3;
            }
            return -1;
        }
        return this.indexOfSupplementary(n, n3);
    }

    public int indexOf(String string) {
        return this.indexOf(string, 0);
    }

    public int indexOf(String string, int n) {
        return String.indexOf(this, string, n);
    }

    @FastNative
    public native String intern();

    public boolean isEmpty() {
        boolean bl = this.count == 0;
        return bl;
    }

    public int lastIndexOf(int n) {
        return this.lastIndexOf(n, this.length() - 1);
    }

    public int lastIndexOf(int n, int n2) {
        if (n < 65536) {
            for (n2 = Math.min((int)n2, (int)(this.length() - 1)); n2 >= 0; --n2) {
                if (this.charAt(n2) != n) continue;
                return n2;
            }
            return -1;
        }
        return this.lastIndexOfSupplementary(n, n2);
    }

    public int lastIndexOf(String string) {
        return this.lastIndexOf(string, this.length());
    }

    public int lastIndexOf(String string, int n) {
        return String.lastIndexOf(this, string, n);
    }

    @Override
    public int length() {
        return this.count >>> 1;
    }

    public boolean matches(String string) {
        return Pattern.matches(string, this);
    }

    public int offsetByCodePoints(int n, int n2) {
        if (n >= 0 && n <= this.length()) {
            return Character.offsetByCodePoints(this, n, n2);
        }
        throw new IndexOutOfBoundsException();
    }

    public boolean regionMatches(int n, String string, int n2, int n3) {
        int n4 = n;
        int n5 = n2;
        if (n2 >= 0 && n >= 0 && (long)n <= (long)this.length() - (long)n3) {
            n = n4;
            n4 = n3;
            if ((long)n2 <= (long)string.length() - (long)n3) {
                while (n4 > 0) {
                    if (this.charAt(n) != string.charAt(n5)) {
                        return false;
                    }
                    ++n;
                    --n4;
                    ++n5;
                }
                return true;
            }
        }
        return false;
    }

    public boolean regionMatches(boolean bl, int n, String string, int n2, int n3) {
        int n4 = n;
        int n5 = n2;
        if (n2 >= 0 && n >= 0 && (long)n <= (long)this.length() - (long)n3) {
            n = n4;
            n4 = n3;
            if ((long)n2 <= (long)string.length() - (long)n3) {
                while (n4 > 0) {
                    char c;
                    char c2 = this.charAt(n);
                    if (c2 == (c = string.charAt(n5)) || bl && ((c2 = Character.toUpperCase(c2)) == (c = Character.toUpperCase(c)) || Character.toLowerCase(c2) == Character.toLowerCase(c))) {
                        ++n;
                        --n4;
                        ++n5;
                        continue;
                    }
                    return false;
                }
                return true;
            }
        }
        return false;
    }

    public String replace(char c, char c2) {
        if (c != c2) {
            int n = this.length();
            for (int i = 0; i < n; ++i) {
                if (this.charAt(i) != c) continue;
                return this.doReplace(c, c2);
            }
        }
        return this;
    }

    public String replace(CharSequence charSequence, CharSequence charSequence2) {
        if (charSequence != null) {
            if (charSequence2 != null) {
                String string = charSequence2.toString();
                String string2 = charSequence.toString();
                int n = this.length();
                if (string2.isEmpty()) {
                    charSequence = new StringBuilder(string.length() * (n + 2) + n);
                    ((StringBuilder)charSequence).append(string);
                    for (int i = 0; i < n; ++i) {
                        ((StringBuilder)charSequence).append(this.charAt(i));
                        ((StringBuilder)charSequence).append(string);
                    }
                    return ((StringBuilder)charSequence).toString();
                }
                int n2 = 0;
                charSequence2 = null;
                do {
                    int n3;
                    if ((n3 = String.indexOf(this, string2, n2)) == -1) {
                        if (charSequence2 != null) {
                            ((StringBuilder)charSequence2).append(this, n2, n);
                            return ((StringBuilder)charSequence2).toString();
                        }
                        return this;
                    }
                    charSequence = charSequence2;
                    if (charSequence2 == null) {
                        charSequence = new StringBuilder(n);
                    }
                    ((StringBuilder)charSequence).append(this, n2, n3);
                    ((StringBuilder)charSequence).append(string);
                    n2 = n3 + string2.length();
                    charSequence2 = charSequence;
                } while (true);
            }
            throw new NullPointerException("replacement == null");
        }
        throw new NullPointerException("target == null");
    }

    public String replaceAll(String string, String string2) {
        return Pattern.compile(string).matcher(this).replaceAll(string2);
    }

    public String replaceFirst(String string, String string2) {
        return Pattern.compile(string).matcher(this).replaceFirst(string2);
    }

    public String[] split(String string) {
        return this.split(string, 0);
    }

    public String[] split(String string, int n) {
        String[] arrstring = Pattern.fastSplit(string, this, n);
        if (arrstring != null) {
            return arrstring;
        }
        return Pattern.compile(string).split(this, n);
    }

    public boolean startsWith(String string) {
        return this.startsWith(string, 0);
    }

    public boolean startsWith(String string, int n) {
        int n2 = n;
        int n3 = 0;
        int n4 = string.length();
        if (n >= 0) {
            int n5 = n4;
            if (n <= this.length() - n4) {
                while (--n5 >= 0) {
                    if (this.charAt(n2) != string.charAt(n3)) {
                        return false;
                    }
                    ++n2;
                    ++n3;
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public CharSequence subSequence(int n, int n2) {
        return this.substring(n, n2);
    }

    public String substring(int n) {
        if (n >= 0) {
            int n2 = this.length() - n;
            if (n2 >= 0) {
                String string = n == 0 ? this : this.fastSubstring(n, n2);
                return string;
            }
            throw new StringIndexOutOfBoundsException(this, n);
        }
        throw new StringIndexOutOfBoundsException(this, n);
    }

    public String substring(int n, int n2) {
        if (n >= 0) {
            if (n2 <= this.length()) {
                int n3 = n2 - n;
                if (n3 >= 0) {
                    String string = n == 0 && n2 == this.length() ? this : this.fastSubstring(n, n3);
                    return string;
                }
                throw new StringIndexOutOfBoundsException(n3);
            }
            throw new StringIndexOutOfBoundsException(this, n2);
        }
        throw new StringIndexOutOfBoundsException(this, n);
    }

    @FastNative
    public native char[] toCharArray();

    public String toLowerCase() {
        return this.toLowerCase(Locale.getDefault());
    }

    public String toLowerCase(Locale locale) {
        return CaseMapper.toLowerCase((Locale)locale, (String)this);
    }

    @Override
    public String toString() {
        return this;
    }

    public String toUpperCase() {
        return this.toUpperCase(Locale.getDefault());
    }

    public String toUpperCase(Locale locale) {
        return CaseMapper.toUpperCase((Locale)locale, (String)this, (int)this.length());
    }

    public String trim() {
        int n;
        int n2 = this.length();
        int n3 = 0;
        do {
            n = n2;
            if (n3 >= n2) break;
            n = n2;
            if (this.charAt(n3) > ' ') break;
            ++n3;
        } while (true);
        while (n3 < n && this.charAt(n - 1) <= ' ') {
            --n;
        }
        String string = n3 <= 0 && n >= this.length() ? this : this.substring(n3, n);
        return string;
    }

    private static class CaseInsensitiveComparator
    implements Comparator<String>,
    Serializable {
        private static final long serialVersionUID = 8575799808933029326L;

        private CaseInsensitiveComparator() {
        }

        private Object readResolve() {
            return String.CASE_INSENSITIVE_ORDER;
        }

        @Override
        public int compare(String string, String string2) {
            int n = string.length();
            int n2 = string2.length();
            int n3 = Math.min(n, n2);
            for (int i = 0; i < n3; ++i) {
                char c;
                char c2;
                char c3;
                char c4 = string.charAt(i);
                if (c4 == (c = string2.charAt(i)) || (c4 = Character.toUpperCase(c4)) == (c = Character.toUpperCase(c)) || (c3 = Character.toLowerCase(c4)) == (c2 = Character.toLowerCase(c))) continue;
                return c3 - c2;
            }
            return n - n2;
        }
    }

}

