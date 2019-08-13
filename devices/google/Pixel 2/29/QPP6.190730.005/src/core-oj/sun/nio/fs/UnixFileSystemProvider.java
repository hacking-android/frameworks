/*
 * Decompiled with CFR 0.145.
 */
package sun.nio.fs;

import java.io.FilePermission;
import java.io.IOException;
import java.net.URI;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.AccessMode;
import java.nio.file.CopyOption;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.FileSystemAlreadyExistsException;
import java.nio.file.LinkOption;
import java.nio.file.LinkPermission;
import java.nio.file.NotDirectoryException;
import java.nio.file.NotLinkException;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.ProviderMismatchException;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.FileAttributeView;
import java.nio.file.attribute.FileOwnerAttributeView;
import java.nio.file.attribute.PosixFileAttributeView;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.spi.FileTypeDetector;
import java.security.Permission;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import sun.nio.ch.ThreadPool;
import sun.nio.fs.AbstractFileSystemProvider;
import sun.nio.fs.AbstractFileTypeDetector;
import sun.nio.fs.DynamicFileAttributeView;
import sun.nio.fs.UnixChannelFactory;
import sun.nio.fs.UnixConstants;
import sun.nio.fs.UnixCopyFile;
import sun.nio.fs.UnixDirectoryStream;
import sun.nio.fs.UnixException;
import sun.nio.fs.UnixFileAttributeViews;
import sun.nio.fs.UnixFileAttributes;
import sun.nio.fs.UnixFileModeAttribute;
import sun.nio.fs.UnixFileSystem;
import sun.nio.fs.UnixNativeDispatcher;
import sun.nio.fs.UnixPath;
import sun.nio.fs.UnixSecureDirectoryStream;
import sun.nio.fs.UnixUriUtils;
import sun.nio.fs.Util;

public abstract class UnixFileSystemProvider
extends AbstractFileSystemProvider {
    private static final String USER_DIR = "user.dir";
    private final UnixFileSystem theFileSystem = this.newFileSystem(System.getProperty("user.dir"));

    private void checkUri(URI uRI) {
        if (uRI.getScheme().equalsIgnoreCase(this.getScheme())) {
            if (uRI.getAuthority() == null) {
                if (uRI.getPath() != null) {
                    if (uRI.getPath().equals("/")) {
                        if (uRI.getQuery() == null) {
                            if (uRI.getFragment() == null) {
                                return;
                            }
                            throw new IllegalArgumentException("Fragment component present");
                        }
                        throw new IllegalArgumentException("Query component present");
                    }
                    throw new IllegalArgumentException("Path component should be '/'");
                }
                throw new IllegalArgumentException("Path component is undefined");
            }
            throw new IllegalArgumentException("Authority component present");
        }
        throw new IllegalArgumentException("URI does not match this provider");
    }

    final FileTypeDetector chain(final AbstractFileTypeDetector ... arrabstractFileTypeDetector) {
        return new AbstractFileTypeDetector(){

            @Override
            protected String implProbeContentType(Path path) throws IOException {
                AbstractFileTypeDetector[] arrabstractFileTypeDetector2 = arrabstractFileTypeDetector;
                int n = arrabstractFileTypeDetector2.length;
                for (int i = 0; i < n; ++i) {
                    String string = arrabstractFileTypeDetector2[i].implProbeContentType(path);
                    if (string == null || string.isEmpty()) continue;
                    return string;
                }
                return null;
            }
        };
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public void checkAccess(Path path, AccessMode ... object) throws IOException {
        boolean bl;
        path = UnixPath.toUnixPath(path);
        boolean bl2 = false;
        boolean bl3 = false;
        boolean bl4 = false;
        int n = 0;
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        if (((Object)object).length == 0) {
            bl = true;
        } else {
            int n5 = ((Object)object).length;
            int n6 = 0;
            do {
                bl = bl2;
                bl3 = bl4;
                n = n2;
                n3 = n4;
                if (n6 >= n5) break;
                Object object2 = object[n6];
                n3 = 3.$SwitchMap$java$nio$file$AccessMode[((Enum)object2).ordinal()];
                if (n3 != 1) {
                    if (n3 != 2) {
                        if (n3 != 3) throw new AssertionError((Object)"Should not get here");
                        n4 = 1;
                    } else {
                        n2 = 1;
                    }
                } else {
                    bl4 = true;
                }
                ++n6;
            } while (true);
        }
        n4 = 0;
        if (bl || bl3) {
            ((UnixPath)path).checkRead();
            n4 = bl3 ? UnixConstants.R_OK : UnixConstants.F_OK;
            n4 = 0 | n4;
        }
        n2 = n4;
        if (n != 0) {
            ((UnixPath)path).checkWrite();
            n2 = n4 | UnixConstants.W_OK;
        }
        n4 = n2;
        if (n3 != 0) {
            object = System.getSecurityManager();
            if (object != null) {
                ((SecurityManager)object).checkExec(((UnixPath)path).getPathForPermissionCheck());
            }
            n4 = n2 | UnixConstants.X_OK;
        }
        try {
            UnixNativeDispatcher.access((UnixPath)path, n4);
            return;
        }
        catch (UnixException unixException) {
            unixException.rethrowAsIOException((UnixPath)path);
        }
    }

    UnixPath checkPath(Path path) {
        if (path != null) {
            if (path instanceof UnixPath) {
                return (UnixPath)path;
            }
            throw new ProviderMismatchException();
        }
        throw new NullPointerException();
    }

    @Override
    public void copy(Path path, Path path2, CopyOption ... arrcopyOption) throws IOException {
        UnixCopyFile.copy(UnixPath.toUnixPath(path), UnixPath.toUnixPath(path2), arrcopyOption);
    }

    @Override
    public void createDirectory(Path path, FileAttribute<?> ... arrfileAttribute) throws IOException {
        block2 : {
            path = UnixPath.toUnixPath(path);
            ((UnixPath)path).checkWrite();
            int n = UnixFileModeAttribute.toUnixMode(UnixFileModeAttribute.ALL_PERMISSIONS, arrfileAttribute);
            try {
                UnixNativeDispatcher.mkdir((UnixPath)path, n);
            }
            catch (UnixException unixException) {
                if (unixException.errno() == UnixConstants.EISDIR) break block2;
                unixException.rethrowAsIOException((UnixPath)path);
            }
            return;
        }
        throw new FileAlreadyExistsException(((UnixPath)path).toString());
    }

    @Override
    public void createLink(Path path, Path path2) throws IOException {
        path = UnixPath.toUnixPath(path);
        path2 = UnixPath.toUnixPath(path2);
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(new LinkPermission("hard"));
            ((UnixPath)path).checkWrite();
            ((UnixPath)path2).checkWrite();
        }
        try {
            UnixNativeDispatcher.link((UnixPath)path2, (UnixPath)path);
        }
        catch (UnixException unixException) {
            unixException.rethrowAsIOException((UnixPath)path, (UnixPath)path2);
        }
    }

    @Override
    public void createSymbolicLink(Path path, Path path2, FileAttribute<?> ... object) throws IOException {
        path = UnixPath.toUnixPath(path);
        path2 = UnixPath.toUnixPath(path2);
        if (((FileAttribute<?>[])object).length <= 0) {
            object = System.getSecurityManager();
            if (object != null) {
                ((SecurityManager)object).checkPermission(new LinkPermission("symbolic"));
                ((UnixPath)path).checkWrite();
            }
            try {
                UnixNativeDispatcher.symlink(((UnixPath)path2).asByteArray(), (UnixPath)path);
            }
            catch (UnixException unixException) {
                unixException.rethrowAsIOException((UnixPath)path);
            }
            return;
        }
        UnixFileModeAttribute.toUnixMode(0, object);
        throw new UnsupportedOperationException("Initial file attributesnot supported when creating symbolic link");
    }

    @Override
    public <V extends FileAttributeView> V getFileAttributeView(Path path, Class<V> class_, LinkOption ... arrlinkOption) {
        path = UnixPath.toUnixPath(path);
        boolean bl = Util.followLinks(arrlinkOption);
        if (class_ == BasicFileAttributeView.class) {
            return (V)UnixFileAttributeViews.createBasicView((UnixPath)path, bl);
        }
        if (class_ == PosixFileAttributeView.class) {
            return (V)UnixFileAttributeViews.createPosixView((UnixPath)path, bl);
        }
        if (class_ == FileOwnerAttributeView.class) {
            return (V)UnixFileAttributeViews.createOwnerView((UnixPath)path, bl);
        }
        if (class_ != null) {
            return null;
        }
        throw new NullPointerException();
    }

    @Override
    protected DynamicFileAttributeView getFileAttributeView(Path path, String string, LinkOption ... arrlinkOption) {
        path = UnixPath.toUnixPath(path);
        boolean bl = Util.followLinks(arrlinkOption);
        if (string.equals("basic")) {
            return UnixFileAttributeViews.createBasicView((UnixPath)path, bl);
        }
        if (string.equals("posix")) {
            return UnixFileAttributeViews.createPosixView((UnixPath)path, bl);
        }
        if (string.equals("unix")) {
            return UnixFileAttributeViews.createUnixView((UnixPath)path, bl);
        }
        if (string.equals("owner")) {
            return UnixFileAttributeViews.createOwnerView((UnixPath)path, bl);
        }
        return null;
    }

    @Override
    public FileStore getFileStore(Path path) throws IOException {
        throw new SecurityException("getFileStore");
    }

    abstract FileStore getFileStore(UnixPath var1) throws IOException;

    @Override
    public final FileSystem getFileSystem(URI uRI) {
        this.checkUri(uRI);
        return this.theFileSystem;
    }

    FileTypeDetector getFileTypeDetector() {
        return new AbstractFileTypeDetector(){

            @Override
            public String implProbeContentType(Path path) {
                return null;
            }
        };
    }

    @Override
    public Path getPath(URI uRI) {
        return UnixUriUtils.fromUri(this.theFileSystem, uRI);
    }

    @Override
    public final String getScheme() {
        return "file";
    }

    @Override
    boolean implDelete(Path object, boolean bl) throws IOException {
        block8 : {
            UnixPath unixPath;
            UnixFileAttributes unixFileAttributes;
            block7 : {
                unixPath = UnixPath.toUnixPath((Path)object);
                unixPath.checkDelete();
                object = null;
                try {
                    unixFileAttributes = UnixFileAttributes.get(unixPath, false);
                    object = unixFileAttributes;
                }
                catch (UnixException unixException) {
                    if (!bl && unixException.errno() == UnixConstants.ENOENT) {
                        return false;
                    }
                    if (object != null && ((UnixFileAttributes)object).isDirectory() && (unixException.errno() == UnixConstants.EEXIST || unixException.errno() == UnixConstants.ENOTEMPTY)) {
                        throw new DirectoryNotEmptyException(unixPath.getPathForExceptionMessage());
                    }
                    unixException.rethrowAsIOException(unixPath);
                    return false;
                }
                if (!unixFileAttributes.isDirectory()) break block7;
                object = unixFileAttributes;
                UnixNativeDispatcher.rmdir(unixPath);
                break block8;
            }
            object = unixFileAttributes;
            UnixNativeDispatcher.unlink(unixPath);
        }
        return true;
    }

    @Override
    public boolean isHidden(Path path) {
        path = UnixPath.toUnixPath(path);
        ((UnixPath)path).checkRead();
        path = ((UnixPath)path).getFileName();
        boolean bl = false;
        if (path == null) {
            return false;
        }
        if (((UnixPath)path).asByteArray()[0] == 46) {
            bl = true;
        }
        return bl;
    }

    @Override
    public boolean isSameFile(Path path, Path object) throws IOException {
        Object object2 = UnixPath.toUnixPath(path);
        if (((UnixPath)object2).equals(object)) {
            return true;
        }
        if (object != null) {
            if (!(object instanceof UnixPath)) {
                return false;
            }
            path = (UnixPath)object;
            ((UnixPath)object2).checkRead();
            ((UnixPath)path).checkRead();
            try {
                object = UnixFileAttributes.get((UnixPath)object2, true);
            }
            catch (UnixException unixException) {
                unixException.rethrowAsIOException((UnixPath)object2);
                return false;
            }
            try {
                object2 = UnixFileAttributes.get((UnixPath)path, true);
                return ((UnixFileAttributes)object).isSameFile((UnixFileAttributes)object2);
            }
            catch (UnixException unixException) {
                unixException.rethrowAsIOException((UnixPath)path);
                return false;
            }
        }
        throw new NullPointerException();
    }

    @Override
    public void move(Path path, Path path2, CopyOption ... arrcopyOption) throws IOException {
        UnixCopyFile.move(UnixPath.toUnixPath(path), UnixPath.toUnixPath(path2), arrcopyOption);
    }

    @Override
    public AsynchronousFileChannel newAsynchronousFileChannel(Path object, Set<? extends OpenOption> set, ExecutorService executorService, FileAttribute<?> ... arrfileAttribute) throws IOException {
        UnixPath unixPath = this.checkPath((Path)object);
        int n = UnixFileModeAttribute.toUnixMode(UnixFileModeAttribute.ALL_READWRITE, arrfileAttribute);
        object = executorService == null ? null : ThreadPool.wrap(executorService, 0);
        try {
            object = UnixChannelFactory.newAsynchronousFileChannel(unixPath, set, n, (ThreadPool)object);
            return object;
        }
        catch (UnixException unixException) {
            unixException.rethrowAsIOException(unixPath);
            return null;
        }
    }

    @Override
    public SeekableByteChannel newByteChannel(Path path, Set<? extends OpenOption> object, FileAttribute<?> ... arrfileAttribute) throws IOException {
        path = UnixPath.toUnixPath(path);
        int n = UnixFileModeAttribute.toUnixMode(UnixFileModeAttribute.ALL_READWRITE, arrfileAttribute);
        try {
            object = UnixChannelFactory.newFileChannel((UnixPath)path, object, n);
            return object;
        }
        catch (UnixException unixException) {
            unixException.rethrowAsIOException((UnixPath)path);
            return null;
        }
    }

    @Override
    public DirectoryStream<Path> newDirectoryStream(Path path, DirectoryStream.Filter<? super Path> filter) throws IOException {
        path = UnixPath.toUnixPath(path);
        ((UnixPath)path).checkRead();
        if (filter != null) {
            block10 : {
                block11 : {
                    int n;
                    long l;
                    int n2;
                    if (!UnixNativeDispatcher.openatSupported() || UnixConstants.O_NOFOLLOW == 0) {
                        try {
                            UnixDirectoryStream unixDirectoryStream = new UnixDirectoryStream((UnixPath)path, UnixNativeDispatcher.opendir((UnixPath)path), filter);
                            return unixDirectoryStream;
                        }
                        catch (UnixException unixException) {
                            if (unixException.errno() == UnixConstants.ENOTDIR) break block10;
                            unixException.rethrowAsIOException((UnixPath)path);
                        }
                    }
                    int n3 = -1;
                    int n4 = n = -1;
                    n3 = n2 = UnixNativeDispatcher.open((UnixPath)path, UnixConstants.O_RDONLY, 0);
                    n4 = n;
                    n = UnixNativeDispatcher.dup(n2);
                    n3 = n2;
                    n4 = n;
                    try {
                        l = UnixNativeDispatcher.fdopendir(n2);
                        n4 = n;
                    }
                    catch (UnixException unixException) {
                        if (n3 != -1) {
                            UnixNativeDispatcher.close(n3);
                        }
                        if (n4 != -1) {
                            UnixNativeDispatcher.close(n4);
                        }
                        if (unixException.errno() == UnixConstants.ENOTDIR) break block11;
                        unixException.rethrowAsIOException((UnixPath)path);
                        l = 0L;
                    }
                    return new UnixSecureDirectoryStream((UnixPath)path, l, n4, filter);
                }
                throw new NotDirectoryException(((UnixPath)path).getPathForExceptionMessage());
            }
            throw new NotDirectoryException(((UnixPath)path).getPathForExceptionMessage());
        }
        throw new NullPointerException();
    }

    @Override
    public FileChannel newFileChannel(Path path, Set<? extends OpenOption> object, FileAttribute<?> ... arrfileAttribute) throws IOException {
        path = this.checkPath(path);
        int n = UnixFileModeAttribute.toUnixMode(UnixFileModeAttribute.ALL_READWRITE, arrfileAttribute);
        try {
            object = UnixChannelFactory.newFileChannel((UnixPath)path, object, n);
            return object;
        }
        catch (UnixException unixException) {
            unixException.rethrowAsIOException((UnixPath)path);
            return null;
        }
    }

    @Override
    public final FileSystem newFileSystem(URI uRI, Map<String, ?> map) {
        this.checkUri(uRI);
        throw new FileSystemAlreadyExistsException();
    }

    abstract UnixFileSystem newFileSystem(String var1);

    @Override
    public <A extends BasicFileAttributes> A readAttributes(Path path, Class<A> class_, LinkOption ... arrlinkOption) throws IOException {
        block6 : {
            block5 : {
                block4 : {
                    if (class_ != BasicFileAttributes.class) break block4;
                    class_ = BasicFileAttributeView.class;
                    break block5;
                }
                if (class_ != PosixFileAttributes.class) break block6;
                class_ = PosixFileAttributeView.class;
            }
            return (A)((BasicFileAttributeView)this.getFileAttributeView(path, class_, arrlinkOption)).readAttributes();
        }
        if (class_ == null) {
            throw new NullPointerException();
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public Path readSymbolicLink(Path path) throws IOException {
        path = UnixPath.toUnixPath(path);
        Object object = System.getSecurityManager();
        if (object != null) {
            object.checkPermission(new FilePermission(((UnixPath)path).getPathForPermissionCheck(), "readlink"));
        }
        try {
            object = UnixNativeDispatcher.readlink((UnixPath)path);
            object = new UnixPath((UnixFileSystem)((UnixPath)path).getFileSystem(), (byte[])object);
            return object;
        }
        catch (UnixException unixException) {
            if (unixException.errno() != UnixConstants.EINVAL) {
                unixException.rethrowAsIOException((UnixPath)path);
                return null;
            }
            throw new NotLinkException(((UnixPath)path).getPathForExceptionMessage());
        }
    }

}

