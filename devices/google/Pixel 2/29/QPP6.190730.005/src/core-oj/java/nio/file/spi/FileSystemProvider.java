/*
 * Decompiled with CFR 0.145.
 */
package java.nio.file.spi;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.AccessMode;
import java.nio.file.CopyOption;
import java.nio.file.DirectoryStream;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.FileAttributeView;
import java.security.AccessController;
import java.security.Permission;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.concurrent.ExecutorService;

public abstract class FileSystemProvider {
    private static volatile List<FileSystemProvider> installedProviders;
    private static boolean loadingProviders;
    private static final Object lock;

    static {
        lock = new Object();
        loadingProviders = false;
    }

    protected FileSystemProvider() {
        this(FileSystemProvider.checkPermission());
    }

    private FileSystemProvider(Void void_) {
    }

    private static Void checkPermission() {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(new RuntimePermission("fileSystemProvider"));
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static List<FileSystemProvider> installedProviders() {
        if (installedProviders != null) return installedProviders;
        Object object = FileSystems.getDefault().provider();
        Object object2 = lock;
        synchronized (object2) {
            if (installedProviders != null) return installedProviders;
            if (!loadingProviders) {
                loadingProviders = true;
                PrivilegedAction<List<FileSystemProvider>> privilegedAction = new PrivilegedAction<List<FileSystemProvider>>(){

                    @Override
                    public List<FileSystemProvider> run() {
                        return FileSystemProvider.loadInstalledProviders();
                    }
                };
                privilegedAction = AccessController.doPrivileged(privilegedAction);
                privilegedAction.add(0, (List<FileSystemProvider>)object);
                installedProviders = Collections.unmodifiableList(privilegedAction);
                return installedProviders;
            }
            object = new Error("Circular loading of installed providers detected");
            throw object;
        }
    }

    private static List<FileSystemProvider> loadInstalledProviders() {
        ArrayList<FileSystemProvider> arrayList = new ArrayList<FileSystemProvider>();
        for (FileSystemProvider fileSystemProvider : ServiceLoader.load(FileSystemProvider.class, ClassLoader.getSystemClassLoader())) {
            boolean bl;
            block2 : {
                String string = fileSystemProvider.getScheme();
                if (string.equalsIgnoreCase("file")) continue;
                boolean bl2 = false;
                Iterator iterator = arrayList.iterator();
                do {
                    bl = bl2;
                    if (!iterator.hasNext()) break block2;
                } while (!((FileSystemProvider)iterator.next()).getScheme().equalsIgnoreCase(string));
                bl = true;
            }
            if (bl) continue;
            arrayList.add(fileSystemProvider);
        }
        return arrayList;
    }

    public abstract void checkAccess(Path var1, AccessMode ... var2) throws IOException;

    public abstract void copy(Path var1, Path var2, CopyOption ... var3) throws IOException;

    public abstract void createDirectory(Path var1, FileAttribute<?> ... var2) throws IOException;

    public void createLink(Path path, Path path2) throws IOException {
        throw new UnsupportedOperationException();
    }

    public void createSymbolicLink(Path path, Path path2, FileAttribute<?> ... arrfileAttribute) throws IOException {
        throw new UnsupportedOperationException();
    }

    public abstract void delete(Path var1) throws IOException;

    public boolean deleteIfExists(Path path) throws IOException {
        try {
            this.delete(path);
            return true;
        }
        catch (NoSuchFileException noSuchFileException) {
            return false;
        }
    }

    public abstract <V extends FileAttributeView> V getFileAttributeView(Path var1, Class<V> var2, LinkOption ... var3);

    public abstract FileStore getFileStore(Path var1) throws IOException;

    public abstract FileSystem getFileSystem(URI var1);

    public abstract Path getPath(URI var1);

    public abstract String getScheme();

    public abstract boolean isHidden(Path var1) throws IOException;

    public abstract boolean isSameFile(Path var1, Path var2) throws IOException;

    public abstract void move(Path var1, Path var2, CopyOption ... var3) throws IOException;

    public AsynchronousFileChannel newAsynchronousFileChannel(Path path, Set<? extends OpenOption> set, ExecutorService executorService, FileAttribute<?> ... arrfileAttribute) throws IOException {
        throw new UnsupportedOperationException();
    }

    public abstract SeekableByteChannel newByteChannel(Path var1, Set<? extends OpenOption> var2, FileAttribute<?> ... var3) throws IOException;

    public abstract DirectoryStream<Path> newDirectoryStream(Path var1, DirectoryStream.Filter<? super Path> var2) throws IOException;

    public FileChannel newFileChannel(Path path, Set<? extends OpenOption> set, FileAttribute<?> ... arrfileAttribute) throws IOException {
        throw new UnsupportedOperationException();
    }

    public abstract FileSystem newFileSystem(URI var1, Map<String, ?> var2) throws IOException;

    public FileSystem newFileSystem(Path path, Map<String, ?> map) throws IOException {
        throw new UnsupportedOperationException();
    }

    public InputStream newInputStream(Path object, OpenOption ... arropenOption) throws IOException {
        if (arropenOption.length > 0) {
            int n = arropenOption.length;
            for (int i = 0; i < n; ++i) {
                OpenOption openOption = arropenOption[i];
                if (openOption != StandardOpenOption.APPEND && openOption != StandardOpenOption.WRITE) {
                    continue;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("'");
                ((StringBuilder)object).append(openOption);
                ((StringBuilder)object).append("' not allowed");
                throw new UnsupportedOperationException(((StringBuilder)object).toString());
            }
        }
        return Channels.newInputStream(Files.newByteChannel((Path)object, arropenOption));
    }

    public OutputStream newOutputStream(Path path, OpenOption ... arropenOption) throws IOException {
        int n = arropenOption.length;
        HashSet<OpenOption> hashSet = new HashSet<OpenOption>(n + 3);
        if (n == 0) {
            hashSet.add(StandardOpenOption.CREATE);
            hashSet.add(StandardOpenOption.TRUNCATE_EXISTING);
        } else {
            for (OpenOption openOption : arropenOption) {
                if (openOption != StandardOpenOption.READ) {
                    hashSet.add(openOption);
                    continue;
                }
                throw new IllegalArgumentException("READ not allowed");
            }
        }
        hashSet.add(StandardOpenOption.WRITE);
        return Channels.newOutputStream(this.newByteChannel(path, hashSet, new FileAttribute[0]));
    }

    public abstract <A extends BasicFileAttributes> A readAttributes(Path var1, Class<A> var2, LinkOption ... var3) throws IOException;

    public abstract Map<String, Object> readAttributes(Path var1, String var2, LinkOption ... var3) throws IOException;

    public Path readSymbolicLink(Path path) throws IOException {
        throw new UnsupportedOperationException();
    }

    public abstract void setAttribute(Path var1, String var2, Object var3, LinkOption ... var4) throws IOException;

}

