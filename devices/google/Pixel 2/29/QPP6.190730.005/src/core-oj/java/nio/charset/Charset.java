/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.icu.NativeConverter
 */
package java.nio.charset;

import java.io.UnsupportedEncodingException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;
import java.nio.charset.spi.CharsetProvider;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.AbstractMap;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import libcore.icu.NativeConverter;
import sun.misc.ASCIICaseInsensitiveComparator;
import sun.misc.VM;
import sun.nio.cs.ThreadLocalCoders;
import sun.security.action.GetPropertyAction;

public abstract class Charset
implements Comparable<Charset> {
    private static volatile String bugLevel = null;
    private static volatile Map.Entry<String, Charset> cache1 = null;
    private static final HashMap<String, Charset> cache2 = new HashMap();
    private static Charset defaultCharset;
    private static ThreadLocal<ThreadLocal<?>> gate;
    private Set<String> aliasSet = null;
    private final String[] aliases;
    private final String name;

    static {
        gate = new ThreadLocal();
    }

    protected Charset(String string, String[] arrstring) {
        Charset.checkName(string);
        if (arrstring == null) {
            arrstring = new String[]{};
        }
        for (int i = 0; i < arrstring.length; ++i) {
            Charset.checkName(arrstring[i]);
        }
        this.name = string;
        this.aliases = arrstring;
    }

    static boolean atBugLevel(String string) {
        String string2;
        String string3 = string2 = bugLevel;
        if (string2 == null) {
            if (!VM.isBooted()) {
                return false;
            }
            string3 = string2 = AccessController.doPrivileged(new GetPropertyAction("sun.nio.cs.bugLevel", ""));
            bugLevel = string2;
        }
        return string3.equals(string);
    }

    public static SortedMap<String, Charset> availableCharsets() {
        return AccessController.doPrivileged(new PrivilegedAction<SortedMap<String, Charset>>(){

            @Override
            public SortedMap<String, Charset> run() {
                Object object;
                TreeMap<String, Object> treeMap = new TreeMap<String, Object>(ASCIICaseInsensitiveComparator.CASE_INSENSITIVE_ORDER);
                String[] arrstring = NativeConverter.getAvailableCharsetNames();
                int n = arrstring.length;
                for (int i = 0; i < n; ++i) {
                    object = NativeConverter.charsetForName((String)arrstring[i]);
                    treeMap.put(((Charset)object).name(), object);
                }
                object = Charset.providers();
                while (object.hasNext()) {
                    Charset.put(((CharsetProvider)object.next()).charsets(), treeMap);
                }
                return Collections.unmodifiableSortedMap(treeMap);
            }
        });
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static void cache(String string, Charset charset) {
        Object object;
        HashMap<String, Charset> hashMap = cache2;
        synchronized (hashMap) {
            Object object2 = charset.name();
            object = cache2.get(object2);
            if (object == null) {
                cache2.put((String)object2, charset);
                object2 = charset.aliases().iterator();
                do {
                    object = charset;
                    if (!object2.hasNext()) break;
                    object = (String)object2.next();
                    cache2.put((String)object, charset);
                } while (true);
            }
            cache2.put(string, (Charset)object);
        }
        cache1 = new AbstractMap.SimpleImmutableEntry<String, Charset>(string, (Charset)object);
    }

    private static void checkName(String string) {
        int n = string.length();
        if (!Charset.atBugLevel("1.4") && n == 0) {
            throw new IllegalCharsetNameException(string);
        }
        for (int i = 0; i < n; ++i) {
            char c = string.charAt(i);
            if (c >= 'A' && c <= 'Z' || c >= 'a' && c <= 'z' || c >= '0' && c <= '9' || c == '-' && i != 0 || c == '+' && i != 0 || c == ':' && i != 0 || c == '_' && i != 0 || c == '.' && i != 0) {
                continue;
            }
            throw new IllegalCharsetNameException(string);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Charset defaultCharset() {
        synchronized (Charset.class) {
            if (defaultCharset != null) return defaultCharset;
            defaultCharset = StandardCharsets.UTF_8;
            return defaultCharset;
        }
    }

    public static Charset forName(String string) {
        Charset charset = Charset.lookup(string);
        if (charset != null) {
            return charset;
        }
        throw new UnsupportedCharsetException(string);
    }

    public static Charset forNameUEE(String object) throws UnsupportedEncodingException {
        try {
            Charset charset = Charset.forName((String)object);
            return charset;
        }
        catch (Exception exception) {
            object = new UnsupportedEncodingException((String)object);
            ((Throwable)object).initCause(exception);
            throw object;
        }
    }

    public static boolean isSupported(String string) {
        boolean bl = Charset.lookup(string) != null;
        return bl;
    }

    private static Charset lookup(String string) {
        if (string != null) {
            Map.Entry<String, Charset> entry = cache1;
            if (entry != null && string.equals(entry.getKey())) {
                return entry.getValue();
            }
            return Charset.lookup2(string);
        }
        throw new IllegalArgumentException("Null charset name");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static Charset lookup2(String string) {
        Object object = cache2;
        synchronized (object) {
            Charset charset = cache2.get(string);
            if (charset != null) {
                AbstractMap.SimpleImmutableEntry<String, Charset> simpleImmutableEntry = new AbstractMap.SimpleImmutableEntry<String, Charset>(string, charset);
                cache1 = simpleImmutableEntry;
                return charset;
            }
        }
        Charset charset = NativeConverter.charsetForName((String)string);
        object = charset;
        if (charset == null) {
            charset = Charset.lookupViaProviders(string);
            object = charset;
            if (charset == null) {
                Charset.checkName(string);
                return null;
            }
        }
        Charset.cache(string, (Charset)object);
        return object;
    }

    private static Charset lookupViaProviders(String object) {
        if (!VM.isBooted()) {
            return null;
        }
        if (gate.get() != null) {
            return null;
        }
        try {
            gate.set(gate);
            PrivilegedAction<Charset> privilegedAction = new PrivilegedAction<Charset>((String)object){
                final /* synthetic */ String val$charsetName;
                {
                    this.val$charsetName = string;
                }

                @Override
                public Charset run() {
                    Iterator iterator = Charset.providers();
                    while (iterator.hasNext()) {
                        Charset charset = ((CharsetProvider)iterator.next()).charsetForName(this.val$charsetName);
                        if (charset == null) continue;
                        return charset;
                    }
                    return null;
                }
            };
            object = AccessController.doPrivileged(privilegedAction);
            return object;
        }
        finally {
            gate.set(null);
        }
    }

    private static Iterator<CharsetProvider> providers() {
        return new Iterator<CharsetProvider>(){
            Iterator<CharsetProvider> i = this.sl.iterator();
            CharsetProvider next = null;
            ServiceLoader<CharsetProvider> sl = ServiceLoader.load(CharsetProvider.class);

            private boolean getNext() {
                while (this.next == null) {
                    block4 : {
                        try {
                            if (this.i.hasNext()) break block4;
                            return false;
                        }
                        catch (ServiceConfigurationError serviceConfigurationError) {
                            if (serviceConfigurationError.getCause() instanceof SecurityException) continue;
                            throw serviceConfigurationError;
                        }
                    }
                    this.next = this.i.next();
                }
                return true;
            }

            @Override
            public boolean hasNext() {
                return this.getNext();
            }

            @Override
            public CharsetProvider next() {
                if (this.getNext()) {
                    CharsetProvider charsetProvider = this.next;
                    this.next = null;
                    return charsetProvider;
                }
                throw new NoSuchElementException();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    private static void put(Iterator<Charset> iterator, Map<String, Charset> map) {
        while (iterator.hasNext()) {
            Charset charset = iterator.next();
            if (map.containsKey(charset.name())) continue;
            map.put(charset.name(), charset);
        }
    }

    public final Set<String> aliases() {
        Set<String> set = this.aliasSet;
        if (set != null) {
            return set;
        }
        int n = this.aliases.length;
        set = new HashSet<String>(n);
        for (int i = 0; i < n; ++i) {
            ((HashSet)set).add(this.aliases[i]);
        }
        this.aliasSet = Collections.unmodifiableSet(set);
        return this.aliasSet;
    }

    public boolean canEncode() {
        return true;
    }

    @Override
    public final int compareTo(Charset charset) {
        return this.name().compareToIgnoreCase(charset.name());
    }

    public abstract boolean contains(Charset var1);

    public final CharBuffer decode(ByteBuffer buffer) {
        try {
            buffer = ThreadLocalCoders.decoderFor(this).onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE).decode((ByteBuffer)buffer);
            return buffer;
        }
        catch (CharacterCodingException characterCodingException) {
            throw new Error(characterCodingException);
        }
    }

    public String displayName() {
        return this.name;
    }

    public String displayName(Locale locale) {
        return this.name;
    }

    public final ByteBuffer encode(String string) {
        return this.encode(CharBuffer.wrap(string));
    }

    public final ByteBuffer encode(CharBuffer buffer) {
        try {
            buffer = ThreadLocalCoders.encoderFor(this).onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE).encode((CharBuffer)buffer);
            return buffer;
        }
        catch (CharacterCodingException characterCodingException) {
            throw new Error(characterCodingException);
        }
    }

    public final boolean equals(Object object) {
        if (!(object instanceof Charset)) {
            return false;
        }
        if (this == object) {
            return true;
        }
        return this.name.equals(((Charset)object).name());
    }

    public final int hashCode() {
        return this.name().hashCode();
    }

    public final boolean isRegistered() {
        boolean bl = !this.name.startsWith("X-") && !this.name.startsWith("x-");
        return bl;
    }

    public final String name() {
        return this.name;
    }

    public abstract CharsetDecoder newDecoder();

    public abstract CharsetEncoder newEncoder();

    public final String toString() {
        return this.name();
    }

}

