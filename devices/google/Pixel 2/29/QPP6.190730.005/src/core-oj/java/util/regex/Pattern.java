/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.optimization.ReachabilitySensitive
 *  dalvik.system.VMRuntime
 *  libcore.util.EmptyArray
 *  libcore.util.NativeAllocationRegistry
 */
package java.util.regex;

import dalvik.annotation.optimization.ReachabilitySensitive;
import dalvik.system.VMRuntime;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Spliterators;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.PatternSyntaxException;
import java.util.regex._$$Lambda$Pattern$2enjXc1GhR_FNtcTfhbg9qy2smk;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import libcore.util.EmptyArray;
import libcore.util.NativeAllocationRegistry;

public final class Pattern
implements Serializable {
    public static final int CANON_EQ = 128;
    public static final int CASE_INSENSITIVE = 2;
    public static final int COMMENTS = 4;
    public static final int DOTALL = 32;
    private static final String FASTSPLIT_METACHARACTERS = "\\?*+[](){}^$.|";
    public static final int LITERAL = 16;
    public static final int MULTILINE = 8;
    public static final int UNICODE_CASE = 64;
    public static final int UNICODE_CHARACTER_CLASS = 256;
    public static final int UNIX_LINES = 1;
    private static final NativeAllocationRegistry registry = NativeAllocationRegistry.createMalloced((ClassLoader)Pattern.class.getClassLoader(), (long)Pattern.getNativeFinalizer());
    private static final long serialVersionUID = 5073258162644648461L;
    @ReachabilitySensitive
    transient long address;
    private final int flags;
    private final String pattern;

    private Pattern(String charSequence, int n) {
        this.pattern = charSequence;
        this.flags = n;
        if ((n & 128) == 0) {
            if ((127 & n) == 0) {
                this.compile();
                return;
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Unsupported flags: ");
            ((StringBuilder)charSequence).append(127 & n);
            throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
        }
        throw new UnsupportedOperationException("CANON_EQ flag not supported");
    }

    public static Pattern compile(String string) {
        return new Pattern(string, 0);
    }

    public static Pattern compile(String string, int n) {
        return new Pattern(string, n);
    }

    private void compile() throws PatternSyntaxException {
        String string = this.pattern;
        if (string != null) {
            String string2 = this.pattern;
            if ((this.flags & 16) != 0) {
                string2 = Pattern.quote(string);
            }
            this.address = Pattern.compileImpl(string2, this.flags & 47);
            registry.registerNativeAllocation((Object)this, this.address);
            return;
        }
        throw new NullPointerException("pattern == null");
    }

    private static native long compileImpl(String var0, int var1);

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static String[] fastSplit(String arrstring, String string, int n) {
        int n2;
        int n3 = arrstring.length();
        if (n3 == 0) {
            return null;
        }
        int n4 = arrstring.charAt(0);
        if (n3 != 1 || "\\?*+[](){}^$.|".indexOf(n4) != -1) {
            if (n3 != 2 || n4 != 92) return null;
            n4 = n3 = (int)arrstring.charAt(1);
            if ("\\?*+[](){}^$.|".indexOf(n3) == -1) {
                return null;
            }
        }
        if (string.isEmpty()) {
            return new String[]{""};
        }
        n3 = 0;
        int n5 = 0;
        while (n3 + 1 != n && (n2 = string.indexOf(n4, n5)) != -1) {
            ++n3;
            n5 = n2 + 1;
        }
        int n6 = string.length();
        int n7 = n3;
        n2 = n6;
        if (n == 0) {
            n7 = n3;
            n2 = n6;
            if (n5 == n6) {
                if (n3 == n6) {
                    return EmptyArray.STRING;
                }
                do {
                    n5 = n = n5 - 1;
                } while (string.charAt(n - 1) == n4);
                n7 = n3 - (string.length() - n);
                n2 = n;
            }
        }
        arrstring = new String[n7 + 1];
        n3 = 0;
        for (n = 0; n != n7; ++n) {
            n5 = string.indexOf(n4, n3);
            arrstring[n] = string.substring(n3, n5);
            n3 = n5 + 1;
        }
        arrstring[n7] = string.substring(n3, n2);
        return arrstring;
    }

    private static native long getNativeFinalizer();

    public static boolean matches(String string, CharSequence charSequence) {
        return Pattern.compile(string).matcher(charSequence).matches();
    }

    public static String quote(String string) {
        int n;
        if (string.indexOf("\\E") == -1) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("\\Q");
            stringBuilder.append(string);
            stringBuilder.append("\\E");
            return stringBuilder.toString();
        }
        StringBuilder stringBuilder = new StringBuilder(string.length() * 2);
        stringBuilder.append("\\Q");
        int n2 = 0;
        while ((n = string.indexOf("\\E", n2)) != -1) {
            stringBuilder.append(string.substring(n2, n));
            n2 = n + 2;
            stringBuilder.append("\\E\\\\E\\Q");
        }
        stringBuilder.append(string.substring(n2, string.length()));
        stringBuilder.append("\\E");
        return stringBuilder.toString();
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.compile();
    }

    public Predicate<String> asPredicate() {
        return new _$$Lambda$Pattern$2enjXc1GhR_FNtcTfhbg9qy2smk(this);
    }

    public int flags() {
        return this.flags;
    }

    public /* synthetic */ boolean lambda$asPredicate$0$Pattern(String string) {
        return this.matcher(string).find();
    }

    public Matcher matcher(CharSequence charSequence) {
        return new Matcher(this, charSequence);
    }

    public String pattern() {
        return this.pattern;
    }

    public String[] split(CharSequence charSequence) {
        return this.split(charSequence, 0);
    }

    public String[] split(CharSequence arrstring, int n) {
        Object object = Pattern.fastSplit(this.pattern, arrstring.toString(), n);
        if (object != null) {
            return object;
        }
        int n2 = 0;
        int n3 = n > 0 ? 1 : 0;
        object = new ArrayList<E>();
        Matcher matcher = this.matcher((CharSequence)arrstring);
        while (matcher.find()) {
            int n4;
            if (n3 != 0 && ((ArrayList)object).size() >= n - 1) {
                n4 = n2;
                if (((ArrayList)object).size() == n - 1) {
                    ((ArrayList)object).add(arrstring.subSequence(n2, arrstring.length()).toString());
                    n2 = matcher.end();
                    continue;
                }
            } else {
                if (n2 == 0 && n2 == matcher.start() && matcher.start() == matcher.end() && VMRuntime.getRuntime().getTargetSdkVersion() > 28) continue;
                ((ArrayList)object).add(arrstring.subSequence(n2, matcher.start()).toString());
                n4 = matcher.end();
            }
            n2 = n4;
        }
        if (n2 == 0) {
            return new String[]{arrstring.toString()};
        }
        if (n3 == 0 || ((ArrayList)object).size() < n) {
            ((ArrayList)object).add(arrstring.subSequence(n2, arrstring.length()).toString());
        }
        n2 = n3 = ((ArrayList)object).size();
        if (n == 0) {
            n = n3;
            do {
                n2 = n;
                if (n <= 0) break;
                n2 = n;
                if (!((String)((ArrayList)object).get(n - 1)).equals("")) break;
                --n;
            } while (true);
        }
        arrstring = new String[n2];
        return ((ArrayList)object).subList(0, n2).toArray(arrstring);
    }

    public Stream<String> splitAsStream(CharSequence charSequence) {
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(new 1MatcherIterator(this, charSequence), 272), false);
    }

    public String toString() {
        return this.pattern;
    }

    class 1MatcherIterator
    implements Iterator<String> {
        private int current;
        private int emptyElementCount;
        private final Matcher matcher;
        private String nextElement;
        final /* synthetic */ CharSequence val$input;

        1MatcherIterator() {
            this.val$input = var2_2;
            this.matcher = this$0.matcher(this.val$input);
        }

        @Override
        public boolean hasNext() {
            if (this.nextElement == null && this.emptyElementCount <= 0) {
                if (this.current == this.val$input.length()) {
                    return false;
                }
                while (this.matcher.find()) {
                    this.nextElement = this.val$input.subSequence(this.current, this.matcher.start()).toString();
                    this.current = this.matcher.end();
                    if (!this.nextElement.isEmpty()) {
                        return true;
                    }
                    if (this.current <= 0) continue;
                    ++this.emptyElementCount;
                }
                CharSequence charSequence = this.val$input;
                this.nextElement = charSequence.subSequence(this.current, charSequence.length()).toString();
                this.current = this.val$input.length();
                if (!this.nextElement.isEmpty()) {
                    return true;
                }
                this.emptyElementCount = 0;
                this.nextElement = null;
                return false;
            }
            return true;
        }

        @Override
        public String next() {
            if (this.hasNext()) {
                int n = this.emptyElementCount;
                if (n == 0) {
                    String string = this.nextElement;
                    this.nextElement = null;
                    return string;
                }
                this.emptyElementCount = n - 1;
                return "";
            }
            throw new NoSuchElementException();
        }
    }

}

