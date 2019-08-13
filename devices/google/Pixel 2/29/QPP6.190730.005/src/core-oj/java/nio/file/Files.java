/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.nio.file.-$
 *  java.nio.file.-$$Lambda
 *  java.nio.file.-$$Lambda$Files
 *  java.nio.file.-$$Lambda$Files$cNMxoBpYNc_xj_crDjR6l6JHUZ0
 *  java.nio.file.-$$Lambda$Files$troLqSRHugOdjQwE7dW2qp22ctc
 */
package java.nio.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.io.Writer;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.-$;
import java.nio.file.AccessMode;
import java.nio.file.CopyMoveHelper;
import java.nio.file.CopyOption;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.FileSystemException;
import java.nio.file.FileTreeIterator;
import java.nio.file.FileTreeWalker;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.nio.file.TempFileHelper;
import java.nio.file._$$Lambda$Files$4idNQbLxq4bhe2g1MNnC6cjfF5E;
import java.nio.file._$$Lambda$Files$cNMxoBpYNc_xj_crDjR6l6JHUZ0;
import java.nio.file._$$Lambda$Files$powUktDqIsUPxzmcqaqk0NiO6iA;
import java.nio.file._$$Lambda$Files$troLqSRHugOdjQwE7dW2qp22ctc;
import java.nio.file._$$Lambda$sYbGIj22XbOmrXSY16DZsES4BAM;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.FileAttributeView;
import java.nio.file.attribute.FileOwnerAttributeView;
import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.PosixFileAttributeView;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.UserPrincipal;
import java.nio.file.spi.FileSystemProvider;
import java.nio.file.spi.FileTypeDetector;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.Spliterators;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import sun.nio.fs.DefaultFileTypeDetector;

public final class Files {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int BUFFER_SIZE = 8192;
    private static final int MAX_BUFFER_SIZE = 2147483639;

    private static /* synthetic */ /* end resource */ void $closeResource(Throwable throwable, AutoCloseable autoCloseable) {
        if (throwable != null) {
            try {
                autoCloseable.close();
            }
            catch (Throwable throwable2) {
                throwable.addSuppressed(throwable2);
            }
        } else {
            autoCloseable.close();
        }
    }

    private Files() {
    }

    private static Runnable asUncheckedRunnable(Closeable closeable) {
        return new _$$Lambda$Files$powUktDqIsUPxzmcqaqk0NiO6iA(closeable);
    }

    private static long copy(InputStream inputStream, OutputStream outputStream) throws IOException {
        int n;
        long l = 0L;
        byte[] arrby = new byte[8192];
        while ((n = inputStream.read(arrby)) > 0) {
            outputStream.write(arrby, 0, n);
            l += (long)n;
        }
        return l;
    }

    public static long copy(InputStream object, Path object2, CopyOption ... object3) throws IOException {
        long l;
        block14 : {
            CopyOption copyOption;
            Objects.requireNonNull(object);
            int n = ((CopyOption[])object3).length;
            boolean bl = false;
            for (int i = 0; i < n; ++i) {
                copyOption = object3[i];
                if (copyOption == StandardCopyOption.REPLACE_EXISTING) {
                    bl = true;
                    continue;
                }
                if (copyOption == null) {
                    throw new NullPointerException("options contains 'null'");
                }
                object = new StringBuilder();
                ((StringBuilder)object).append(copyOption);
                ((StringBuilder)object).append(" not supported");
                throw new UnsupportedOperationException(((StringBuilder)object).toString());
            }
            copyOption = null;
            object3 = copyOption;
            if (bl) {
                try {
                    Files.deleteIfExists((Path)object2);
                    object3 = copyOption;
                }
                catch (SecurityException securityException) {
                    // empty catch block
                }
            }
            try {
                object2 = Files.newOutputStream((Path)object2, StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE);
            }
            catch (FileAlreadyExistsException fileAlreadyExistsException) {
                if (object3 != null) {
                    throw object3;
                }
                throw fileAlreadyExistsException;
            }
            try {
                l = Files.copy((InputStream)object, (OutputStream)object2);
                if (object2 == null) break block14;
            }
            catch (Throwable throwable) {
                try {
                    throw throwable;
                }
                catch (Throwable throwable2) {
                    if (object2 != null) {
                        Files.$closeResource(throwable, (AutoCloseable)object2);
                    }
                    throw throwable2;
                }
            }
            Files.$closeResource(null, (AutoCloseable)object2);
        }
        return l;
    }

    public static long copy(Path object, OutputStream outputStream) throws IOException {
        long l;
        block5 : {
            Objects.requireNonNull(outputStream);
            object = Files.newInputStream((Path)object, new OpenOption[0]);
            try {
                l = Files.copy((InputStream)object, outputStream);
                if (object == null) break block5;
            }
            catch (Throwable throwable) {
                try {
                    throw throwable;
                }
                catch (Throwable throwable2) {
                    if (object != null) {
                        Files.$closeResource(throwable, (AutoCloseable)object);
                    }
                    throw throwable2;
                }
            }
            Files.$closeResource(null, (AutoCloseable)object);
        }
        return l;
    }

    public static Path copy(Path path, Path path2, CopyOption ... arrcopyOption) throws IOException {
        FileSystemProvider fileSystemProvider = Files.provider(path);
        if (Files.provider(path2) == fileSystemProvider) {
            fileSystemProvider.copy(path, path2, arrcopyOption);
        } else {
            CopyMoveHelper.copyToForeignTarget(path, path2, arrcopyOption);
        }
        return path2;
    }

    private static void createAndCheckIsDirectory(Path path, FileAttribute<?> ... arrfileAttribute) throws IOException {
        FileAlreadyExistsException fileAlreadyExistsException2;
        block2 : {
            try {
                Files.createDirectory(path, arrfileAttribute);
            }
            catch (FileAlreadyExistsException fileAlreadyExistsException2) {
                if (!Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS)) break block2;
            }
            return;
        }
        throw fileAlreadyExistsException2;
    }

    public static Path createDirectories(Path object, FileAttribute<?> ... arrfileAttribute) throws IOException {
        try {
            Files.createAndCheckIsDirectory((Path)object, arrfileAttribute);
            return object;
        }
        catch (IOException iOException) {
            Path path;
            Path path2 = null;
            try {
                path = object.toAbsolutePath();
            }
            catch (SecurityException securityException) {
                path = object;
            }
            for (object = path.getParent(); object != null; object = object.getParent()) {
                try {
                    Files.provider((Path)object).checkAccess((Path)object, new AccessMode[0]);
                    break;
                }
                catch (NoSuchFileException noSuchFileException) {
                    continue;
                }
            }
            if (object == null) {
                if (path2 == null) {
                    throw new FileSystemException(path.toString(), null, "Unable to determine if root directory exists");
                }
                throw path2;
            }
            path2 = object;
            object = object.relativize(path).iterator();
            while (object.hasNext()) {
                path2 = path2.resolve((Path)object.next());
                Files.createAndCheckIsDirectory(path2, arrfileAttribute);
            }
            return path;
        }
        catch (FileAlreadyExistsException fileAlreadyExistsException) {
            throw fileAlreadyExistsException;
        }
    }

    public static Path createDirectory(Path path, FileAttribute<?> ... arrfileAttribute) throws IOException {
        Files.provider(path).createDirectory(path, arrfileAttribute);
        return path;
    }

    public static Path createFile(Path path, FileAttribute<?> ... arrfileAttribute) throws IOException {
        Files.newByteChannel(path, EnumSet.of(StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE), arrfileAttribute).close();
        return path;
    }

    public static Path createLink(Path path, Path path2) throws IOException {
        Files.provider(path).createLink(path, path2);
        return path;
    }

    public static Path createSymbolicLink(Path path, Path path2, FileAttribute<?> ... arrfileAttribute) throws IOException {
        Files.provider(path).createSymbolicLink(path, path2, arrfileAttribute);
        return path;
    }

    public static Path createTempDirectory(String string, FileAttribute<?> ... arrfileAttribute) throws IOException {
        return TempFileHelper.createTempDirectory(null, string, arrfileAttribute);
    }

    public static Path createTempDirectory(Path path, String string, FileAttribute<?> ... arrfileAttribute) throws IOException {
        return TempFileHelper.createTempDirectory(Objects.requireNonNull(path), string, arrfileAttribute);
    }

    public static Path createTempFile(String string, String string2, FileAttribute<?> ... arrfileAttribute) throws IOException {
        return TempFileHelper.createTempFile(null, string, string2, arrfileAttribute);
    }

    public static Path createTempFile(Path path, String string, String string2, FileAttribute<?> ... arrfileAttribute) throws IOException {
        return TempFileHelper.createTempFile(Objects.requireNonNull(path), string, string2, arrfileAttribute);
    }

    public static void delete(Path path) throws IOException {
        Files.provider(path).delete(path);
    }

    public static boolean deleteIfExists(Path path) throws IOException {
        return Files.provider(path).deleteIfExists(path);
    }

    public static boolean exists(Path path, LinkOption ... arrlinkOption) {
        try {
            if (Files.followLinks(arrlinkOption)) {
                Files.provider(path).checkAccess(path, new AccessMode[0]);
            } else {
                Files.readAttributes(path, BasicFileAttributes.class, LinkOption.NOFOLLOW_LINKS);
            }
            return true;
        }
        catch (IOException iOException) {
            return false;
        }
    }

    public static Stream<Path> find(Path object, int n, BiPredicate<Path, BasicFileAttributes> object2, FileVisitOption ... object3) throws IOException {
        object = new FileTreeIterator((Path)object, n, (FileVisitOption[])object3);
        try {
            object3 = StreamSupport.stream(Spliterators.spliteratorUnknownSize(object, 1), false);
            Objects.requireNonNull(object);
            Object object4 = new _$$Lambda$sYbGIj22XbOmrXSY16DZsES4BAM((FileTreeIterator)object);
            object3 = (Stream)object3.onClose((Runnable)object4);
            object4 = new _$$Lambda$Files$4idNQbLxq4bhe2g1MNnC6cjfF5E((BiPredicate)object2);
            object2 = object3.filter(object4).map(_$$Lambda$Files$cNMxoBpYNc_xj_crDjR6l6JHUZ0.INSTANCE);
            return object2;
        }
        catch (Error | RuntimeException throwable) {
            ((FileTreeIterator)object).close();
            throw throwable;
        }
    }

    private static boolean followLinks(LinkOption ... arrlinkOption) {
        boolean bl = true;
        for (LinkOption linkOption : arrlinkOption) {
            if (linkOption == LinkOption.NOFOLLOW_LINKS) {
                bl = false;
                continue;
            }
            if (linkOption == null) {
                throw new NullPointerException();
            }
            throw new AssertionError((Object)"Should not get here");
        }
        return bl;
    }

    public static Object getAttribute(Path object, String string, LinkOption ... object2) throws IOException {
        if (string.indexOf(42) < 0 && string.indexOf(44) < 0) {
            object2 = Files.readAttributes((Path)object, string, (LinkOption[])object2);
            int n = string.indexOf(58);
            object = n == -1 ? string : (n == string.length() ? "" : string.substring(n + 1));
            return object2.get(object);
        }
        throw new IllegalArgumentException(string);
    }

    public static <V extends FileAttributeView> V getFileAttributeView(Path path, Class<V> class_, LinkOption ... arrlinkOption) {
        return Files.provider(path).getFileAttributeView(path, class_, arrlinkOption);
    }

    public static FileStore getFileStore(Path path) throws IOException {
        return Files.provider(path).getFileStore(path);
    }

    public static FileTime getLastModifiedTime(Path path, LinkOption ... arrlinkOption) throws IOException {
        return Files.readAttributes(path, BasicFileAttributes.class, arrlinkOption).lastModifiedTime();
    }

    public static UserPrincipal getOwner(Path object, LinkOption ... arrlinkOption) throws IOException {
        if ((object = Files.getFileAttributeView((Path)object, FileOwnerAttributeView.class, arrlinkOption)) != null) {
            return object.getOwner();
        }
        throw new UnsupportedOperationException();
    }

    public static Set<PosixFilePermission> getPosixFilePermissions(Path path, LinkOption ... arrlinkOption) throws IOException {
        return Files.readAttributes(path, PosixFileAttributes.class, arrlinkOption).permissions();
    }

    private static boolean isAccessible(Path path, AccessMode ... arraccessMode) {
        try {
            Files.provider(path).checkAccess(path, arraccessMode);
            return true;
        }
        catch (IOException iOException) {
            return false;
        }
    }

    public static boolean isDirectory(Path path, LinkOption ... arrlinkOption) {
        try {
            boolean bl = Files.readAttributes(path, BasicFileAttributes.class, arrlinkOption).isDirectory();
            return bl;
        }
        catch (IOException iOException) {
            return false;
        }
    }

    public static boolean isExecutable(Path path) {
        return Files.isAccessible(path, AccessMode.EXECUTE);
    }

    public static boolean isHidden(Path path) throws IOException {
        return Files.provider(path).isHidden(path);
    }

    public static boolean isReadable(Path path) {
        return Files.isAccessible(path, AccessMode.READ);
    }

    public static boolean isRegularFile(Path path, LinkOption ... arrlinkOption) {
        try {
            boolean bl = Files.readAttributes(path, BasicFileAttributes.class, arrlinkOption).isRegularFile();
            return bl;
        }
        catch (IOException iOException) {
            return false;
        }
    }

    public static boolean isSameFile(Path path, Path path2) throws IOException {
        return Files.provider(path).isSameFile(path, path2);
    }

    public static boolean isSymbolicLink(Path path) {
        try {
            boolean bl = Files.readAttributes(path, BasicFileAttributes.class, LinkOption.NOFOLLOW_LINKS).isSymbolicLink();
            return bl;
        }
        catch (IOException iOException) {
            return false;
        }
    }

    public static boolean isWritable(Path path) {
        return Files.isAccessible(path, AccessMode.WRITE);
    }

    static /* synthetic */ void lambda$asUncheckedRunnable$0(Closeable closeable) {
        try {
            closeable.close();
            return;
        }
        catch (IOException iOException) {
            throw new UncheckedIOException(iOException);
        }
    }

    static /* synthetic */ boolean lambda$find$2(BiPredicate biPredicate, FileTreeWalker.Event event) {
        return biPredicate.test(event.file(), event.attributes());
    }

    static /* synthetic */ Path lambda$find$3(FileTreeWalker.Event event) {
        return event.file();
    }

    static /* synthetic */ Path lambda$walk$1(FileTreeWalker.Event event) {
        return event.file();
    }

    public static Stream<String> lines(Path path) throws IOException {
        return Files.lines(path, StandardCharsets.UTF_8);
    }

    public static Stream<String> lines(Path object, Charset object2) throws IOException {
        object2 = Files.newBufferedReader((Path)object, (Charset)object2);
        try {
            object = (Stream)((BufferedReader)object2).lines().onClose(Files.asUncheckedRunnable((Closeable)object2));
            return object;
        }
        catch (Error | RuntimeException throwable) {
            try {
                ((BufferedReader)object2).close();
            }
            catch (IOException iOException) {
                try {
                    throwable.addSuppressed(iOException);
                }
                catch (Throwable throwable2) {}
            }
            throw throwable;
        }
    }

    public static Stream<Path> list(Path iterable) throws IOException {
        iterable = Files.newDirectoryStream((Path)iterable);
        try {
            final Iterator iterator = iterable.iterator();
            Iterator<Path> iterator2 = new Iterator<Path>(){

                @Override
                public boolean hasNext() {
                    try {
                        boolean bl = iterator.hasNext();
                        return bl;
                    }
                    catch (DirectoryIteratorException directoryIteratorException) {
                        throw new UncheckedIOException(directoryIteratorException.getCause());
                    }
                }

                @Override
                public Path next() {
                    try {
                        Path path = (Path)iterator.next();
                        return path;
                    }
                    catch (DirectoryIteratorException directoryIteratorException) {
                        throw new UncheckedIOException(directoryIteratorException.getCause());
                    }
                }
            };
            iterator2 = (Stream)StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator2, 1), false).onClose(Files.asUncheckedRunnable(iterable));
            return iterator2;
        }
        catch (Error | RuntimeException throwable) {
            try {
                iterable.close();
            }
            catch (IOException iOException) {
                try {
                    throwable.addSuppressed(iOException);
                }
                catch (Throwable throwable2) {}
            }
            throw throwable;
        }
    }

    public static Path move(Path path, Path path2, CopyOption ... arrcopyOption) throws IOException {
        FileSystemProvider fileSystemProvider = Files.provider(path);
        if (Files.provider(path2) == fileSystemProvider) {
            fileSystemProvider.move(path, path2, arrcopyOption);
        } else {
            CopyMoveHelper.moveToForeignTarget(path, path2, arrcopyOption);
        }
        return path2;
    }

    public static BufferedReader newBufferedReader(Path path) throws IOException {
        return Files.newBufferedReader(path, StandardCharsets.UTF_8);
    }

    public static BufferedReader newBufferedReader(Path path, Charset object) throws IOException {
        object = ((Charset)object).newDecoder();
        return new BufferedReader(new InputStreamReader(Files.newInputStream(path, new OpenOption[0]), (CharsetDecoder)object));
    }

    public static BufferedWriter newBufferedWriter(Path path, Charset object, OpenOption ... arropenOption) throws IOException {
        object = ((Charset)object).newEncoder();
        return new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(path, arropenOption), (CharsetEncoder)object));
    }

    public static BufferedWriter newBufferedWriter(Path path, OpenOption ... arropenOption) throws IOException {
        return Files.newBufferedWriter(path, StandardCharsets.UTF_8, arropenOption);
    }

    public static SeekableByteChannel newByteChannel(Path path, Set<? extends OpenOption> set, FileAttribute<?> ... arrfileAttribute) throws IOException {
        return Files.provider(path).newByteChannel(path, set, arrfileAttribute);
    }

    public static SeekableByteChannel newByteChannel(Path path, OpenOption ... arropenOption) throws IOException {
        HashSet hashSet = new HashSet(arropenOption.length);
        Collections.addAll(hashSet, arropenOption);
        return Files.newByteChannel(path, hashSet, new FileAttribute[0]);
    }

    public static DirectoryStream<Path> newDirectoryStream(Path path) throws IOException {
        return Files.provider(path).newDirectoryStream(path, AcceptAllFilter.FILTER);
    }

    public static DirectoryStream<Path> newDirectoryStream(Path path, String object) throws IOException {
        if (((String)object).equals("*")) {
            return Files.newDirectoryStream(path);
        }
        FileSystem fileSystem = path.getFileSystem();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("glob:");
        stringBuilder.append((String)object);
        object = new DirectoryStream.Filter<Path>(fileSystem.getPathMatcher(stringBuilder.toString())){
            final /* synthetic */ PathMatcher val$matcher;
            {
                this.val$matcher = pathMatcher;
            }

            @Override
            public boolean accept(Path path) {
                return this.val$matcher.matches(path.getFileName());
            }
        };
        return fileSystem.provider().newDirectoryStream(path, (DirectoryStream.Filter<? super Path>)object);
    }

    public static DirectoryStream<Path> newDirectoryStream(Path path, DirectoryStream.Filter<? super Path> filter) throws IOException {
        return Files.provider(path).newDirectoryStream(path, filter);
    }

    public static InputStream newInputStream(Path path, OpenOption ... arropenOption) throws IOException {
        return Files.provider(path).newInputStream(path, arropenOption);
    }

    public static OutputStream newOutputStream(Path path, OpenOption ... arropenOption) throws IOException {
        return Files.provider(path).newOutputStream(path, arropenOption);
    }

    public static boolean notExists(Path path, LinkOption ... arrlinkOption) {
        try {
            if (Files.followLinks(arrlinkOption)) {
                Files.provider(path).checkAccess(path, new AccessMode[0]);
            } else {
                Files.readAttributes(path, BasicFileAttributes.class, LinkOption.NOFOLLOW_LINKS);
            }
            return false;
        }
        catch (IOException iOException) {
            return false;
        }
        catch (NoSuchFileException noSuchFileException) {
            return true;
        }
    }

    public static String probeContentType(Path path) throws IOException {
        Iterator<FileTypeDetector> iterator = FileTypeDetectors.installeDetectors.iterator();
        while (iterator.hasNext()) {
            String string = iterator.next().probeContentType(path);
            if (string == null) continue;
            return string;
        }
        return FileTypeDetectors.defaultFileTypeDetector.probeContentType(path);
    }

    private static FileSystemProvider provider(Path path) {
        return path.getFileSystem().provider();
    }

    private static byte[] read(InputStream arrby, int n) throws IOException {
        byte[] arrby2;
        int n2;
        block4 : {
            n2 = n;
            arrby2 = new byte[n2];
            n = 0;
            do {
                int n3;
                if ((n3 = arrby.read(arrby2, n, n2 - n)) > 0) {
                    n += n3;
                    continue;
                }
                if (n3 < 0 || (n3 = arrby.read()) < 0) break block4;
                if (n2 <= 2147483639 - n2) {
                    n2 = Math.max(n2 << 1, 8192);
                } else {
                    if (n2 == 2147483639) break;
                    n2 = 2147483639;
                }
                arrby2 = Arrays.copyOf(arrby2, n2);
                arrby2[n] = (byte)n3;
                ++n;
            } while (true);
            throw new OutOfMemoryError("Required array size too large");
        }
        arrby = n2 == n ? arrby2 : Arrays.copyOf(arrby2, n);
        return arrby;
    }

    /*
     * Exception decompiling
     */
    public static byte[] readAllBytes(Path var0) throws IOException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:404)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:482)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    public static List<String> readAllLines(Path path) throws IOException {
        return Files.readAllLines(path, StandardCharsets.UTF_8);
    }

    public static List<String> readAllLines(Path object, Charset arrayList) throws IOException {
        String string;
        object = Files.newBufferedReader((Path)object, (Charset)((Object)arrayList));
        try {
            arrayList = new ArrayList<String>();
            do {
                if ((string = ((BufferedReader)object).readLine()) != null) break block7;
                break;
            } while (true);
        }
        catch (Throwable throwable) {
            try {
                throw throwable;
            }
            catch (Throwable throwable2) {
                if (object != null) {
                    Files.$closeResource(throwable, (AutoCloseable)object);
                }
                throw throwable2;
            }
        }
        {
            block7 : {
                Files.$closeResource(null, (AutoCloseable)object);
                return arrayList;
            }
            arrayList.add(string);
            continue;
        }
    }

    public static <A extends BasicFileAttributes> A readAttributes(Path path, Class<A> class_, LinkOption ... arrlinkOption) throws IOException {
        return Files.provider(path).readAttributes(path, class_, arrlinkOption);
    }

    public static Map<String, Object> readAttributes(Path path, String string, LinkOption ... arrlinkOption) throws IOException {
        return Files.provider(path).readAttributes(path, string, arrlinkOption);
    }

    public static Path readSymbolicLink(Path path) throws IOException {
        return Files.provider(path).readSymbolicLink(path);
    }

    public static Path setAttribute(Path path, String string, Object object, LinkOption ... arrlinkOption) throws IOException {
        Files.provider(path).setAttribute(path, string, object, arrlinkOption);
        return path;
    }

    public static Path setLastModifiedTime(Path path, FileTime fileTime) throws IOException {
        Files.getFileAttributeView(path, BasicFileAttributeView.class, new LinkOption[0]).setTimes(fileTime, null, null);
        return path;
    }

    public static Path setOwner(Path path, UserPrincipal userPrincipal) throws IOException {
        FileOwnerAttributeView fileOwnerAttributeView = Files.getFileAttributeView(path, FileOwnerAttributeView.class, new LinkOption[0]);
        if (fileOwnerAttributeView != null) {
            fileOwnerAttributeView.setOwner(userPrincipal);
            return path;
        }
        throw new UnsupportedOperationException();
    }

    public static Path setPosixFilePermissions(Path path, Set<PosixFilePermission> set) throws IOException {
        PosixFileAttributeView posixFileAttributeView = Files.getFileAttributeView(path, PosixFileAttributeView.class, new LinkOption[0]);
        if (posixFileAttributeView != null) {
            posixFileAttributeView.setPermissions(set);
            return path;
        }
        throw new UnsupportedOperationException();
    }

    public static long size(Path path) throws IOException {
        return Files.readAttributes(path, BasicFileAttributes.class, new LinkOption[0]).size();
    }

    public static Stream<Path> walk(Path object, int n, FileVisitOption ... object2) throws IOException {
        object = new FileTreeIterator((Path)object, n, (FileVisitOption)((Object)object2));
        try {
            Stream stream = StreamSupport.stream(Spliterators.spliteratorUnknownSize(object, 1), false);
            Objects.requireNonNull(object);
            object2 = new _$$Lambda$sYbGIj22XbOmrXSY16DZsES4BAM((FileTreeIterator)object);
            object2 = ((Stream)stream.onClose((Runnable)object2)).map(_$$Lambda$Files$troLqSRHugOdjQwE7dW2qp22ctc.INSTANCE);
            return object2;
        }
        catch (Error | RuntimeException throwable) {
            ((FileTreeIterator)object).close();
            throw throwable;
        }
    }

    public static Stream<Path> walk(Path path, FileVisitOption ... arrfileVisitOption) throws IOException {
        return Files.walk(path, Integer.MAX_VALUE, arrfileVisitOption);
    }

    public static Path walkFileTree(Path path, FileVisitor<? super Path> fileVisitor) throws IOException {
        return Files.walkFileTree(path, EnumSet.noneOf(FileVisitOption.class), Integer.MAX_VALUE, fileVisitor);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static Path walkFileTree(Path var0, Set<FileVisitOption> var1_2, int var2_4, FileVisitor<? super Path> var3_5) throws IOException {
        var4_17 = new FileTreeWalker((Collection<FileVisitOption>)var1_2 /* !! */ , var2_15);
        try {
            var1_3 = var4_17.walk((Path)var0);
            do {
                block15 : {
                    block13 : {
                        block16 : {
                            block14 : {
                                if ((var2_15 = 3.$SwitchMap$java$nio$file$FileTreeWalker$EventType[var1_4.type().ordinal()]) == 1) break block13;
                                if (var2_15 == 2) break block14;
                                if (var2_15 != 3) {
                                    var0 = new AssertionError((Object)"Should not get here");
                                    throw var0;
                                }
                                var1_5 = var5_19 = var3_16.postVisitDirectory(var1_4.file(), var1_4.ioeException());
                                if (var5_19 == FileVisitResult.SKIP_SIBLINGS) {
                                    var1_6 = FileVisitResult.CONTINUE;
                                }
                                break block15;
                            }
                            var5_20 = var3_16.preVisitDirectory(var1_4.file(), var1_4.attributes());
                            if (var5_20 == FileVisitResult.SKIP_SUBTREE) break block16;
                            var1_7 = var5_20;
                            if (var5_20 != FileVisitResult.SKIP_SIBLINGS) break block15;
                        }
                        var4_17.pop();
                        var1_9 = var5_20;
                        break block15;
                    }
                    var5_21 = var1_4.ioeException();
                    if (var5_21 == null) {
                        var1_10 = var3_16.visitFile(var1_4.file(), var1_4.attributes());
                    } else {
                        var1_11 = var3_16.visitFileFailed(var1_4.file(), var5_21);
                    }
                }
                if (Objects.requireNonNull(var1_12) != FileVisitResult.CONTINUE) {
                    if (var1_12 == FileVisitResult.TERMINATE) ** break;
                    if (var1_12 == FileVisitResult.SKIP_SIBLINGS) {
                        var4_17.skipRemainingSiblings();
                    }
                }
                var1_13 = var5_18 = var4_17.next();
            } while (var5_18 != null);
        }
        catch (Throwable var0_1) {
            try {
                throw var0_1;
            }
            catch (Throwable var1_14) {
                Files.$closeResource(var0_1, var4_17);
                throw var1_14;
            }
        }
        Files.$closeResource(null, var4_17);
        return var0;
    }

    public static Path write(Path path, Iterable<? extends CharSequence> object, Charset object2, OpenOption ... arropenOption) throws IOException {
        Objects.requireNonNull(object);
        object2 = ((Charset)object2).newEncoder();
        object2 = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(path, arropenOption), (CharsetEncoder)object2));
        try {
            object = object.iterator();
            while (object.hasNext()) {
                ((Writer)object2).append((CharSequence)object.next());
                ((BufferedWriter)object2).newLine();
            }
        }
        catch (Throwable throwable) {
            try {
                throw throwable;
            }
            catch (Throwable throwable2) {
                Files.$closeResource(throwable, (AutoCloseable)object2);
                throw throwable2;
            }
        }
        Files.$closeResource(null, (AutoCloseable)object2);
        return path;
    }

    public static Path write(Path path, Iterable<? extends CharSequence> iterable, OpenOption ... arropenOption) throws IOException {
        return Files.write(path, iterable, StandardCharsets.UTF_8, arropenOption);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Path write(Path path, byte[] arrby, OpenOption ... object) throws IOException {
        int n;
        int n2;
        Objects.requireNonNull(arrby);
        object = Files.newOutputStream(path, (OpenOption[])object);
        try {
        }
        catch (Throwable throwable) {
            try {
                throw throwable;
            }
            catch (Throwable throwable2) {
                if (object != null) {
                    Files.$closeResource(throwable, (AutoCloseable)object);
                }
                throw throwable2;
            }
        }
        for (int i = n2 = arrby.length; i > 0; i -= n) {
            n = Math.min(i, 8192);
            ((OutputStream)object).write(arrby, n2 - i, n);
        }
        if (object != null) {
            Files.$closeResource(null, (AutoCloseable)object);
        }
        return path;
    }

    private static class AcceptAllFilter
    implements DirectoryStream.Filter<Path> {
        static final AcceptAllFilter FILTER = new AcceptAllFilter();

        private AcceptAllFilter() {
        }

        @Override
        public boolean accept(Path path) {
            return true;
        }
    }

    private static class FileTypeDetectors {
        static final FileTypeDetector defaultFileTypeDetector = FileTypeDetectors.createDefaultFileTypeDetector();
        static final List<FileTypeDetector> installeDetectors = FileTypeDetectors.loadInstalledDetectors();

        private FileTypeDetectors() {
        }

        private static FileTypeDetector createDefaultFileTypeDetector() {
            return AccessController.doPrivileged(new PrivilegedAction<FileTypeDetector>(){

                @Override
                public FileTypeDetector run() {
                    return DefaultFileTypeDetector.create();
                }
            });
        }

        private static List<FileTypeDetector> loadInstalledDetectors() {
            return AccessController.doPrivileged(new PrivilegedAction<List<FileTypeDetector>>(){

                @Override
                public List<FileTypeDetector> run() {
                    ArrayList<FileTypeDetector> arrayList = new ArrayList<FileTypeDetector>();
                    Iterator<FileTypeDetector> iterator = ServiceLoader.load(FileTypeDetector.class, ClassLoader.getSystemClassLoader()).iterator();
                    while (iterator.hasNext()) {
                        arrayList.add(iterator.next());
                    }
                    return arrayList;
                }
            });
        }

    }

}

