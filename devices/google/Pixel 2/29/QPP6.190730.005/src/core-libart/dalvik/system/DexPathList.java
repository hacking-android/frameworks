/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.system.-$
 *  dalvik.system.-$$Lambda
 *  dalvik.system.-$$Lambda$DexPathList
 *  dalvik.system.-$$Lambda$DexPathList$_CyMypnZmV6ArWiPOPB4EkAIeUc
 */
package dalvik.system;

import android.system.ErrnoException;
import android.system.OsConstants;
import android.system.StructStat;
import dalvik.annotation.compat.UnsupportedAppUsage;
import dalvik.system.-$;
import dalvik.system.DexFile;
import dalvik.system._$$Lambda$DexPathList$_CyMypnZmV6ArWiPOPB4EkAIeUc;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import libcore.io.ClassPathURLStreamHandler;
import libcore.io.IoUtils;
import libcore.io.Libcore;
import libcore.io.Os;

public final class DexPathList {
    private static final String DEX_SUFFIX = ".dex";
    private static final String zipSeparator = "!/";
    @UnsupportedAppUsage
    private final ClassLoader definingContext;
    @UnsupportedAppUsage
    private Element[] dexElements;
    @UnsupportedAppUsage
    private IOException[] dexElementsSuppressedExceptions;
    @UnsupportedAppUsage
    private final List<File> nativeLibraryDirectories;
    @UnsupportedAppUsage
    NativeLibraryElement[] nativeLibraryPathElements;
    @UnsupportedAppUsage
    private final List<File> systemNativeLibraryDirectories;

    public DexPathList(ClassLoader classLoader, String string) {
        if (classLoader != null) {
            this.definingContext = classLoader;
            this.nativeLibraryDirectories = DexPathList.splitPaths(string, false);
            this.systemNativeLibraryDirectories = DexPathList.splitPaths(System.getProperty("java.library.path"), true);
            this.nativeLibraryPathElements = DexPathList.makePathElements(this.getAllNativeLibraryDirectories());
            return;
        }
        throw new NullPointerException("definingContext == null");
    }

    @UnsupportedAppUsage
    public DexPathList(ClassLoader classLoader, String string, String string2, File file) {
        this(classLoader, string, string2, file, false);
    }

    DexPathList(ClassLoader object, String string, String string2, File file, boolean bl) {
        if (object != null) {
            if (string != null) {
                if (file != null) {
                    if (file.exists()) {
                        if (!file.canRead() || !file.canWrite()) {
                            object = new StringBuilder();
                            ((StringBuilder)object).append("optimizedDirectory not readable/writable: ");
                            ((StringBuilder)object).append(file);
                            throw new IllegalArgumentException(((StringBuilder)object).toString());
                        }
                    } else {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("optimizedDirectory doesn't exist: ");
                        ((StringBuilder)object).append(file);
                        throw new IllegalArgumentException(((StringBuilder)object).toString());
                    }
                }
                this.definingContext = object;
                ArrayList<IOException> arrayList = new ArrayList<IOException>();
                this.dexElements = DexPathList.makeDexElements(DexPathList.splitDexPath(string), file, arrayList, (ClassLoader)object, bl);
                this.nativeLibraryDirectories = DexPathList.splitPaths(string2, false);
                this.systemNativeLibraryDirectories = DexPathList.splitPaths(System.getProperty("java.library.path"), true);
                this.nativeLibraryPathElements = DexPathList.makePathElements(this.getAllNativeLibraryDirectories());
                this.dexElementsSuppressedExceptions = arrayList.size() > 0 ? arrayList.toArray(new IOException[arrayList.size()]) : null;
                return;
            }
            throw new NullPointerException("dexPath == null");
        }
        throw new NullPointerException("definingContext == null");
    }

    private List<File> getAllNativeLibraryDirectories() {
        ArrayList<File> arrayList = new ArrayList<File>(this.nativeLibraryDirectories);
        arrayList.addAll(this.systemNativeLibraryDirectories);
        return arrayList;
    }

    static /* synthetic */ boolean lambda$initByteBufferDexPath$0(ByteBuffer byteBuffer) {
        boolean bl = byteBuffer == null;
        return bl;
    }

    @UnsupportedAppUsage
    private static DexFile loadDexFile(File file, File object, ClassLoader classLoader, Element[] arrelement) throws IOException {
        if (object == null) {
            return new DexFile(file, classLoader, arrelement);
        }
        object = DexPathList.optimizedPathFor(file, (File)object);
        return DexFile.loadDex(file.getPath(), (String)object, 0, classLoader, arrelement);
    }

    @UnsupportedAppUsage
    private static Element[] makeDexElements(List<File> list, File file, List<IOException> list2, ClassLoader classLoader) {
        return DexPathList.makeDexElements(list, file, list2, classLoader, false);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static Element[] makeDexElements(List<File> object, File file, List<IOException> list, ClassLoader classLoader, boolean bl) {
        Element[] arrelement = new Element[object.size()];
        int n = 0;
        Iterator iterator = object.iterator();
        do {
            File file2;
            block13 : {
                block15 : {
                    int n2;
                    Object object2;
                    block14 : {
                        CharSequence charSequence;
                        block12 : {
                            block11 : {
                                if (!iterator.hasNext()) {
                                    object = arrelement;
                                    if (n == arrelement.length) return object;
                                    return Arrays.copyOf(arrelement, n);
                                }
                                file2 = (File)iterator.next();
                                if (file2.isDirectory()) {
                                    arrelement[n] = new Element(file2);
                                    ++n;
                                    continue;
                                }
                                if (!file2.isFile()) break block13;
                                charSequence = file2.getName();
                                object2 = null;
                                object = null;
                                if (!((String)charSequence).endsWith(DEX_SUFFIX)) break block14;
                                object = object2 = DexPathList.loadDexFile(file2, file, classLoader, arrelement);
                                n2 = n;
                                if (object == null) break block11;
                                n2 = n + 1;
                                try {
                                    arrelement[n] = object2 = new Element((DexFile)object, null);
                                }
                                catch (IOException iOException) {
                                    n = n2;
                                    break block12;
                                }
                            }
                            n = n2;
                            break block15;
                            catch (IOException iOException) {
                                // empty catch block
                            }
                        }
                        charSequence = new StringBuilder();
                        ((StringBuilder)charSequence).append("Unable to load dex file: ");
                        ((StringBuilder)charSequence).append(file2);
                        System.logE((String)((StringBuilder)charSequence).toString(), (Throwable)object2);
                        list.add((IOException)object2);
                        break block15;
                    }
                    try {
                        object = DexPathList.loadDexFile(file2, file, classLoader, arrelement);
                    }
                    catch (IOException iOException) {
                        list.add(iOException);
                        object = object2;
                    }
                    if (object == null) {
                        n2 = n + 1;
                        arrelement[n] = new Element(file2);
                        n = n2;
                    } else {
                        n2 = n + 1;
                        arrelement[n] = new Element((DexFile)object, file2);
                        n = n2;
                    }
                }
                if (object == null || !bl) continue;
                ((DexFile)object).setTrusted();
                continue;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("ClassLoader referenced unknown path: ");
            ((StringBuilder)object).append(file2);
            System.logW((String)((StringBuilder)object).toString());
        } while (true);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @UnsupportedAppUsage
    public static Element[] makeInMemoryDexElements(ByteBuffer[] arrobject, List<IOException> list) {
        Element[] arrelement = new Element[arrobject.length];
        int n = arrobject.length;
        int n2 = 0;
        int i = 0;
        do {
            block6 : {
                Object byteBuffer;
                Object object;
                DexFile dexFile;
                block7 : {
                    if (i >= n) {
                        arrobject = arrelement;
                        if (n2 == arrelement.length) return arrobject;
                        return Arrays.copyOf(arrelement, n2);
                    }
                    byteBuffer = arrobject[i];
                    dexFile = new DexFile(new ByteBuffer[]{byteBuffer}, null, null);
                    int n3 = n2 + 1;
                    try {
                        arrelement[n2] = object = new Element(dexFile);
                        n2 = n3;
                        break block6;
                    }
                    catch (IOException iOException) {
                        n2 = n3;
                        break block7;
                    }
                    catch (IOException iOException) {
                        // empty catch block
                    }
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("Unable to load dex file: ");
                ((StringBuilder)object).append(byteBuffer);
                System.logE((String)((StringBuilder)object).toString(), (Throwable)((Object)dexFile));
                list.add((IOException)((Object)dexFile));
            }
            ++i;
        } while (true);
    }

    @UnsupportedAppUsage
    private static Element[] makePathElements(List<File> list, File file, List<IOException> list2) {
        return DexPathList.makeDexElements(list, file, list2, null);
    }

    @UnsupportedAppUsage
    private static NativeLibraryElement[] makePathElements(List<File> arrnativeLibraryElement) {
        NativeLibraryElement[] arrnativeLibraryElement2 = new NativeLibraryElement[arrnativeLibraryElement.size()];
        int n = 0;
        arrnativeLibraryElement = arrnativeLibraryElement.iterator();
        while (arrnativeLibraryElement.hasNext()) {
            int n2;
            File file = (File)arrnativeLibraryElement.next();
            String string = file.getPath();
            if (string.contains(zipSeparator)) {
                String[] arrstring = string.split(zipSeparator, 2);
                arrnativeLibraryElement2[n] = new NativeLibraryElement(new File(arrstring[0]), arrstring[1]);
                n2 = n + 1;
            } else {
                n2 = n;
                if (file.isDirectory()) {
                    arrnativeLibraryElement2[n] = new NativeLibraryElement(file);
                    n2 = n + 1;
                }
            }
            n = n2;
        }
        arrnativeLibraryElement = arrnativeLibraryElement2;
        if (n != arrnativeLibraryElement2.length) {
            arrnativeLibraryElement = Arrays.copyOf(arrnativeLibraryElement2, n);
        }
        return arrnativeLibraryElement;
    }

    private static String optimizedPathFor(File object, File file) {
        String string = ((File)object).getName();
        object = string;
        if (!string.endsWith(DEX_SUFFIX)) {
            int n = string.lastIndexOf(".");
            if (n < 0) {
                object = new StringBuilder();
                ((StringBuilder)object).append(string);
                ((StringBuilder)object).append(DEX_SUFFIX);
                object = ((StringBuilder)object).toString();
            } else {
                object = new StringBuilder(n + 4);
                ((StringBuilder)object).append(string, 0, n);
                ((StringBuilder)object).append(DEX_SUFFIX);
                object = ((StringBuilder)object).toString();
            }
        }
        return new File(file, (String)object).getPath();
    }

    private static List<File> splitDexPath(String string) {
        return DexPathList.splitPaths(string, false);
    }

    @UnsupportedAppUsage
    private static List<File> splitPaths(String arrstring, boolean bl) {
        ArrayList<File> arrayList = new ArrayList<File>();
        if (arrstring != null) {
            for (String string : arrstring.split(File.pathSeparator)) {
                block6 : {
                    if (bl) {
                        try {
                            boolean bl2 = OsConstants.S_ISDIR(Libcore.os.stat((String)string).st_mode);
                            if (!bl2) {
                            }
                            break block6;
                        }
                        catch (ErrnoException errnoException) {}
                        continue;
                    }
                }
                arrayList.add(new File(string));
            }
        }
        return arrayList;
    }

    @UnsupportedAppUsage
    public void addDexPath(String string, File file) {
        this.addDexPath(string, file, false);
    }

    public void addDexPath(String arrobject, File arrobject2, boolean bl) {
        ArrayList<IOException> arrayList = new ArrayList<IOException>();
        if ((arrobject = DexPathList.makeDexElements(DexPathList.splitDexPath((String)arrobject), (File)arrobject2, arrayList, this.definingContext, bl)) != null && arrobject.length > 0) {
            arrobject2 = this.dexElements;
            this.dexElements = new Element[arrobject2.length + arrobject.length];
            System.arraycopy(arrobject2, 0, this.dexElements, 0, arrobject2.length);
            System.arraycopy(arrobject, 0, this.dexElements, arrobject2.length, arrobject.length);
        }
        if (arrayList.size() > 0) {
            arrobject2 = arrayList.toArray(new IOException[arrayList.size()]);
            if (this.dexElementsSuppressedExceptions != null) {
                arrobject = this.dexElementsSuppressedExceptions;
                this.dexElementsSuppressedExceptions = new IOException[arrobject.length + arrobject2.length];
                System.arraycopy(arrobject, 0, this.dexElementsSuppressedExceptions, 0, arrobject.length);
                System.arraycopy(arrobject2, 0, this.dexElementsSuppressedExceptions, arrobject.length, arrobject2.length);
            } else {
                this.dexElementsSuppressedExceptions = arrobject2;
            }
        }
    }

    @UnsupportedAppUsage
    public void addNativePath(Collection<String> collection) {
        if (collection.isEmpty()) {
            return;
        }
        NativeLibraryElement[] arrnativeLibraryElement = new ArrayList(collection.size());
        Iterator<String> object2 = collection.iterator();
        while (object2.hasNext()) {
            arrnativeLibraryElement.add(new File(object2.next()));
        }
        collection = new ArrayList<String>(this.nativeLibraryPathElements.length + collection.size());
        ((ArrayList)collection).addAll(Arrays.asList(this.nativeLibraryPathElements));
        for (NativeLibraryElement nativeLibraryElement : DexPathList.makePathElements(arrnativeLibraryElement)) {
            if (((ArrayList)collection).contains(nativeLibraryElement)) continue;
            ((ArrayList)collection).add((String)((Object)nativeLibraryElement));
        }
        this.nativeLibraryPathElements = ((ArrayList)collection).toArray(new NativeLibraryElement[((ArrayList)collection).size()]);
    }

    public Class<?> findClass(String arriOException, List<Throwable> list) {
        Element[] arrelement = this.dexElements;
        int n = arrelement.length;
        for (int i = 0; i < n; ++i) {
            Class<?> class_ = arrelement[i].findClass((String)arriOException, this.definingContext, list);
            if (class_ == null) continue;
            return class_;
        }
        arriOException = this.dexElementsSuppressedExceptions;
        if (arriOException != null) {
            list.addAll(Arrays.asList(arriOException));
        }
        return null;
    }

    public String findLibrary(String arrnativeLibraryElement) {
        String string = System.mapLibraryName((String)arrnativeLibraryElement);
        arrnativeLibraryElement = this.nativeLibraryPathElements;
        int n = arrnativeLibraryElement.length;
        for (int i = 0; i < n; ++i) {
            String string2 = arrnativeLibraryElement[i].findNativeLibrary(string);
            if (string2 == null) continue;
            return string2;
        }
        return null;
    }

    public URL findResource(String string) {
        Element[] arrelement = this.dexElements;
        int n = arrelement.length;
        for (int i = 0; i < n; ++i) {
            URL uRL = arrelement[i].findResource(string);
            if (uRL == null) continue;
            return uRL;
        }
        return null;
    }

    public Enumeration<URL> findResources(String string) {
        ArrayList<URL> arrayList = new ArrayList<URL>();
        Element[] arrelement = this.dexElements;
        int n = arrelement.length;
        for (int i = 0; i < n; ++i) {
            URL uRL = arrelement[i].findResource(string);
            if (uRL == null) continue;
            arrayList.add(uRL);
        }
        return Collections.enumeration(arrayList);
    }

    List<String> getDexPaths() {
        ArrayList<String> arrayList = new ArrayList<String>();
        Element[] arrelement = this.dexElements;
        int n = arrelement.length;
        for (int i = 0; i < n; ++i) {
            String string = arrelement[i].getDexPath();
            if (string == null) continue;
            arrayList.add(string);
        }
        return arrayList;
    }

    public List<File> getNativeLibraryDirectories() {
        return this.nativeLibraryDirectories;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void initByteBufferDexPath(ByteBuffer[] arrbyteBuffer) {
        if (arrbyteBuffer == null) {
            throw new NullPointerException("dexFiles == null");
        }
        if (Arrays.stream(arrbyteBuffer).anyMatch((Predicate<ByteBuffer>)_$$Lambda$DexPathList$_CyMypnZmV6ArWiPOPB4EkAIeUc.INSTANCE)) {
            throw new NullPointerException("dexFiles contains a null Buffer!");
        }
        if (this.dexElements == null && this.dexElementsSuppressedExceptions == null) {
            ArrayList<IOException> arrayList = new ArrayList<IOException>();
            try {
                void var1_4;
                DexFile dexFile = new DexFile(arrbyteBuffer, this.definingContext, null);
                if (dexFile.isBackedByOatFile()) {
                    Object var1_2 = null;
                } else {
                    String string = DexFile.getClassLoaderContext(this.definingContext, null);
                }
                Element element = new Element(dexFile);
                this.dexElements = new Element[]{element};
                if (var1_4 != null) {
                    dexFile.verifyInBackground(this.definingContext, (String)var1_4);
                }
            }
            catch (IOException iOException) {
                System.logE((String)"Unable to load dex files", (Throwable)iOException);
                arrayList.add(iOException);
                this.dexElements = new Element[0];
            }
            if (arrayList.size() > 0) {
                this.dexElementsSuppressedExceptions = arrayList.toArray(new IOException[arrayList.size()]);
            }
            return;
        }
        throw new IllegalStateException("Should only be called once");
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DexPathList[");
        stringBuilder.append(Arrays.toString(this.dexElements));
        stringBuilder.append(",nativeLibraryDirectories=");
        stringBuilder.append(Arrays.toString(this.getAllNativeLibraryDirectories().toArray()));
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    static class Element {
        @UnsupportedAppUsage
        private final DexFile dexFile;
        private boolean initialized;
        @UnsupportedAppUsage
        private final File path;
        private final Boolean pathIsDirectory;
        private ClassPathURLStreamHandler urlHandler;

        public Element(DexFile dexFile) {
            this(dexFile, null);
        }

        @UnsupportedAppUsage
        public Element(DexFile object, File file) {
            if (object == null && file == null) {
                throw new NullPointerException("Either dexFile or path must be non-null");
            }
            this.dexFile = object;
            this.path = file;
            object = this.path;
            object = object == null ? null : Boolean.valueOf(((File)object).isDirectory());
            this.pathIsDirectory = object;
        }

        public Element(File file) {
            this(null, file);
        }

        @Deprecated
        @UnsupportedAppUsage
        public Element(File file, boolean bl, File file2, DexFile dexFile) {
            DexFile dexFile2 = file != null ? null : dexFile;
            File file3 = file != null ? file : file2;
            this(dexFile2, file3);
            System.err.println("Warning: Using deprecated Element constructor. Do not use internal APIs, this constructor will be removed in the future.");
            if (file != null && (file2 != null || dexFile != null)) {
                throw new IllegalArgumentException("Using dir and zip|dexFile no longer supported.");
            }
            if (bl && (file2 != null || dexFile != null)) {
                throw new IllegalArgumentException("Unsupported argument combination.");
            }
        }

        private String getDexPath() {
            File file = this.path;
            Object object = null;
            if (file != null) {
                if (!file.isDirectory()) {
                    object = this.path.getAbsolutePath();
                }
                return object;
            }
            object = this.dexFile;
            if (object != null) {
                return ((DexFile)object).getName();
            }
            return null;
        }

        public Class<?> findClass(String class_, ClassLoader classLoader, List<Throwable> list) {
            DexFile dexFile = this.dexFile;
            class_ = dexFile != null ? dexFile.loadClassBinaryName((String)((Object)class_), classLoader, list) : null;
            return class_;
        }

        public URL findResource(String object) {
            this.maybeInit();
            Object object2 = this.urlHandler;
            if (object2 != null) {
                return ((ClassPathURLStreamHandler)object2).getEntryUrlOrNull((String)object);
            }
            object2 = this.path;
            if (object2 != null && ((File)object2).isDirectory() && ((File)(object = new File(this.path, (String)object))).exists()) {
                try {
                    object = ((File)object).toURI().toURL();
                    return object;
                }
                catch (MalformedURLException malformedURLException) {
                    throw new RuntimeException(malformedURLException);
                }
            }
            return null;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void maybeInit() {
            synchronized (this) {
                boolean bl = this.initialized;
                if (bl) {
                    return;
                }
                if (this.path != null && !(bl = this.pathIsDirectory.booleanValue())) {
                    try {
                        ClassPathURLStreamHandler classPathURLStreamHandler;
                        this.urlHandler = classPathURLStreamHandler = new ClassPathURLStreamHandler(this.path.getPath());
                    }
                    catch (IOException iOException) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Unable to open zip file: ");
                        stringBuilder.append(this.path);
                        System.logE((String)stringBuilder.toString(), (Throwable)iOException);
                        this.urlHandler = null;
                    }
                    this.initialized = true;
                    return;
                }
                this.initialized = true;
                return;
            }
        }

        public String toString() {
            Object object = this.dexFile;
            CharSequence charSequence = "zip file \"";
            if (object == null) {
                object = new StringBuilder();
                if (this.pathIsDirectory.booleanValue()) {
                    charSequence = "directory \"";
                }
                ((StringBuilder)object).append((String)charSequence);
                ((StringBuilder)object).append(this.path);
                ((StringBuilder)object).append("\"");
                return ((StringBuilder)object).toString();
            }
            if (this.path == null) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("dex file \"");
                ((StringBuilder)charSequence).append(this.dexFile);
                ((StringBuilder)charSequence).append("\"");
                return ((StringBuilder)charSequence).toString();
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("zip file \"");
            ((StringBuilder)charSequence).append(this.path);
            ((StringBuilder)charSequence).append("\"");
            return ((StringBuilder)charSequence).toString();
        }
    }

    static class NativeLibraryElement {
        private boolean initialized;
        @UnsupportedAppUsage
        private final File path;
        private ClassPathURLStreamHandler urlHandler;
        private final String zipDir;

        @UnsupportedAppUsage
        public NativeLibraryElement(File file) {
            this.path = file;
            this.zipDir = null;
        }

        public NativeLibraryElement(File file, String string) {
            this.path = file;
            this.zipDir = string;
            if (string != null) {
                return;
            }
            throw new IllegalArgumentException();
        }

        public boolean equals(Object object) {
            boolean bl = true;
            if (this == object) {
                return true;
            }
            if (!(object instanceof NativeLibraryElement)) {
                return false;
            }
            object = (NativeLibraryElement)object;
            if (!Objects.equals(this.path, ((NativeLibraryElement)object).path) || !Objects.equals(this.zipDir, ((NativeLibraryElement)object).zipDir)) {
                bl = false;
            }
            return bl;
        }

        public String findNativeLibrary(String string) {
            this.maybeInit();
            if (this.zipDir == null) {
                if (IoUtils.canOpenReadOnly(string = new File(this.path, string).getPath())) {
                    return string;
                }
            } else if (this.urlHandler != null) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(this.zipDir);
                stringBuilder.append('/');
                stringBuilder.append(string);
                string = stringBuilder.toString();
                if (this.urlHandler.isEntryStored(string)) {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(this.path.getPath());
                    stringBuilder.append(DexPathList.zipSeparator);
                    stringBuilder.append(string);
                    return stringBuilder.toString();
                }
            }
            return null;
        }

        public int hashCode() {
            return Objects.hash(this.path, this.zipDir);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void maybeInit() {
            synchronized (this) {
                boolean bl = this.initialized;
                if (bl) {
                    return;
                }
                if (this.zipDir == null) {
                    this.initialized = true;
                    return;
                }
                try {
                    ClassPathURLStreamHandler classPathURLStreamHandler;
                    this.urlHandler = classPathURLStreamHandler = new ClassPathURLStreamHandler(this.path.getPath());
                }
                catch (IOException iOException) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Unable to open zip file: ");
                    stringBuilder.append(this.path);
                    System.logE((String)stringBuilder.toString(), (Throwable)iOException);
                    this.urlHandler = null;
                }
                this.initialized = true;
                return;
            }
        }

        public String toString() {
            CharSequence charSequence;
            if (this.zipDir == null) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("directory \"");
                stringBuilder.append(this.path);
                stringBuilder.append("\"");
                return stringBuilder.toString();
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("zip file \"");
            stringBuilder.append(this.path);
            stringBuilder.append("\"");
            if (!this.zipDir.isEmpty()) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append(", dir \"");
                ((StringBuilder)charSequence).append(this.zipDir);
                ((StringBuilder)charSequence).append("\"");
                charSequence = ((StringBuilder)charSequence).toString();
            } else {
                charSequence = "";
            }
            stringBuilder.append((String)charSequence);
            return stringBuilder.toString();
        }
    }

}

