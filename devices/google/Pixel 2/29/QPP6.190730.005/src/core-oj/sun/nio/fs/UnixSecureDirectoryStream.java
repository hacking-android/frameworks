/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.optimization.ReachabilitySensitive
 *  dalvik.system.CloseGuard
 */
package sun.nio.fs;

import dalvik.annotation.optimization.ReachabilitySensitive;
import dalvik.system.CloseGuard;
import java.io.IOException;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.ClosedDirectoryStreamException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.DirectoryStream;
import java.nio.file.LinkOption;
import java.nio.file.NotDirectoryException;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.ProviderMismatchException;
import java.nio.file.SecureDirectoryStream;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.FileAttributeView;
import java.nio.file.attribute.FileOwnerAttributeView;
import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.GroupPrincipal;
import java.nio.file.attribute.PosixFileAttributeView;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.UserPrincipal;
import java.security.Permission;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import sun.nio.fs.UnixChannelFactory;
import sun.nio.fs.UnixConstants;
import sun.nio.fs.UnixDirectoryStream;
import sun.nio.fs.UnixException;
import sun.nio.fs.UnixFileAttributes;
import sun.nio.fs.UnixFileModeAttribute;
import sun.nio.fs.UnixNativeDispatcher;
import sun.nio.fs.UnixPath;
import sun.nio.fs.UnixUserPrincipals;
import sun.nio.fs.Util;

class UnixSecureDirectoryStream
implements SecureDirectoryStream<Path> {
    @ReachabilitySensitive
    private final int dfd;
    private final UnixDirectoryStream ds;
    @ReachabilitySensitive
    private final CloseGuard guard = CloseGuard.get();

    UnixSecureDirectoryStream(UnixPath unixPath, long l, int n, DirectoryStream.Filter<? super Path> filter) {
        this.ds = new UnixDirectoryStream(unixPath, l, filter);
        this.dfd = n;
        if (n != -1) {
            this.guard.open("close");
        }
    }

    private <V extends FileAttributeView> V getFileAttributeViewImpl(UnixPath unixPath, Class<V> class_, boolean bl) {
        if (class_ != null) {
            if (class_ == BasicFileAttributeView.class) {
                return (V)new BasicFileAttributeViewImpl(unixPath, bl);
            }
            if (class_ != PosixFileAttributeView.class && class_ != FileOwnerAttributeView.class) {
                return null;
            }
            return (V)new PosixFileAttributeViewImpl(unixPath, bl);
        }
        throw new NullPointerException();
    }

    private UnixPath getName(Path path) {
        if (path != null) {
            if (path instanceof UnixPath) {
                return (UnixPath)path;
            }
            throw new ProviderMismatchException();
        }
        throw new NullPointerException();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void implDelete(Path object, boolean bl, int n) throws IOException {
        UnixPath unixPath = this.getName((Path)object);
        if (System.getSecurityManager() != null) {
            ((UnixPath)this.ds.directory().resolve(unixPath)).checkDelete();
        }
        this.ds.readLock().lock();
        try {
            boolean bl2 = this.ds.isOpen();
            if (bl2) {
                if (!bl) {
                    object = null;
                    n = 0;
                    try {
                        UnixFileAttributes unixFileAttributes = UnixFileAttributes.get(this.dfd, unixPath, false);
                        object = unixFileAttributes;
                    }
                    catch (UnixException unixException) {
                        unixException.rethrowAsIOException(unixPath);
                    }
                    bl = ((UnixFileAttributes)object).isDirectory();
                    if (bl) {
                        n = 512;
                    }
                }
                try {
                    UnixNativeDispatcher.unlinkat(this.dfd, unixPath.asByteArray(), n);
                    return;
                }
                catch (UnixException unixException) {
                    if ((n & 512) != 0 && (unixException.errno() == UnixConstants.EEXIST || unixException.errno() == UnixConstants.ENOTEMPTY)) {
                        DirectoryNotEmptyException directoryNotEmptyException = new DirectoryNotEmptyException(null);
                        throw directoryNotEmptyException;
                    }
                    unixException.rethrowAsIOException(unixPath);
                    return;
                }
            }
            object = new ClosedDirectoryStreamException();
            throw object;
        }
        finally {
            this.ds.readLock().unlock();
        }
    }

    @Override
    public void close() throws IOException {
        this.ds.writeLock().lock();
        try {
            if (this.ds.closeImpl()) {
                UnixNativeDispatcher.close(this.dfd);
            }
            this.guard.close();
            return;
        }
        finally {
            this.ds.writeLock().unlock();
        }
    }

    @Override
    public void deleteDirectory(Path path) throws IOException {
        this.implDelete(path, true, 512);
    }

    @Override
    public void deleteFile(Path path) throws IOException {
        this.implDelete(path, true, 0);
    }

    protected void finalize() throws IOException {
        CloseGuard closeGuard = this.guard;
        if (closeGuard != null) {
            closeGuard.warnIfOpen();
        }
        this.close();
    }

    @Override
    public <V extends FileAttributeView> V getFileAttributeView(Class<V> class_) {
        return this.getFileAttributeViewImpl(null, class_, false);
    }

    @Override
    public <V extends FileAttributeView> V getFileAttributeView(Path path, Class<V> class_, LinkOption ... arrlinkOption) {
        return this.getFileAttributeViewImpl(this.getName(path), class_, Util.followLinks(arrlinkOption));
    }

    @Override
    public Iterator<Path> iterator() {
        return this.ds.iterator(this);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public void move(Path var1_1, SecureDirectoryStream<Path> var2_3, Path var3_5) throws IOException {
        block11 : {
            var4_7 = this.getName(var1_1 /* !! */ );
            var3_6 = this.getName(var3_6);
            if (var2_4 /* !! */  == null) throw new NullPointerException();
            if (var2_4 /* !! */  instanceof UnixSecureDirectoryStream == false) throw new ProviderMismatchException();
            var1_2 = (UnixSecureDirectoryStream)var2_4 /* !! */ ;
            if (System.getSecurityManager() != null) {
                this.ds.directory().resolve(var4_7).checkWrite();
                var1_2.ds.directory().resolve(var3_6).checkWrite();
            }
            this.ds.readLock().lock();
            var1_2.ds.readLock().lock();
            try {
                if (!this.ds.isOpen() || !(var5_8 = var1_2.ds.isOpen())) ** GOTO lbl33
                try {
                    UnixNativeDispatcher.renameat(this.dfd, var4_7.asByteArray(), var1_2.dfd, var3_6.asByteArray());
                }
                catch (UnixException var6_9) {
                    if (var6_9.errno() == UnixConstants.EXDEV) break block11;
                    var6_9.rethrowAsIOException(var4_7, var3_6);
                }
            }
            catch (Throwable var2_5) {
                var1_2.ds.readLock().unlock();
                throw var2_5;
            }
            var1_2.ds.readLock().unlock();
            return;
        }
        try {
            var2_4 /* !! */  = new AtomicMoveNotSupportedException(var4_7.toString(), var3_6.toString(), var6_9.errorString());
            throw var2_4 /* !! */ ;
lbl33: // 1 sources:
            var2_4 /* !! */  = new ClosedDirectoryStreamException();
            throw var2_4 /* !! */ ;
        }
        finally {
            this.ds.readLock().unlock();
        }
    }

    @Override
    public SeekableByteChannel newByteChannel(Path object, Set<? extends OpenOption> object2, FileAttribute<?> ... object3) throws IOException {
        object = this.getName((Path)object);
        int n = UnixFileModeAttribute.toUnixMode(UnixFileModeAttribute.ALL_READWRITE, object3);
        object3 = ((UnixPath)this.ds.directory().resolve((Path)object)).getPathForPermissionCheck();
        this.ds.readLock().lock();
        try {
            boolean bl = this.ds.isOpen();
            if (bl) {
                object2 = UnixChannelFactory.newFileChannel(this.dfd, (UnixPath)object, object3, object2, n);
                return object2;
            }
            object = new ClosedDirectoryStreamException();
            throw object;
        }
        finally {
            this.ds.readLock().unlock();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public SecureDirectoryStream<Path> newDirectoryStream(Path object, LinkOption ... arrlinkOption) throws IOException {
        object = this.getName((Path)object);
        Path path = this.ds.directory().resolve((Path)object);
        boolean bl = Util.followLinks(arrlinkOption);
        if (System.getSecurityManager() != null) {
            ((UnixPath)path).checkRead();
        }
        this.ds.readLock().lock();
        try {
            boolean bl2 = this.ds.isOpen();
            if (bl2) {
                block11 : {
                    long l;
                    int n = -1;
                    int n2 = -1;
                    int n3 = n;
                    int n4 = n2;
                    try {
                        int n5;
                        int n6 = n5 = UnixConstants.O_RDONLY;
                        if (!bl) {
                            n3 = n;
                            n4 = n2;
                            n6 = n5 | UnixConstants.O_NOFOLLOW;
                        }
                        n3 = n;
                        n4 = n2;
                        n3 = n = UnixNativeDispatcher.openat(this.dfd, ((UnixPath)object).asByteArray(), n6, 0);
                        n4 = n2;
                        n6 = UnixNativeDispatcher.dup(n);
                        n3 = n;
                        n4 = n6;
                        l = UnixNativeDispatcher.fdopendir(n);
                        n4 = n6;
                    }
                    catch (UnixException unixException) {
                        if (n3 != -1) {
                            UnixNativeDispatcher.close(n3);
                        }
                        if (n4 != -1) {
                            UnixNativeDispatcher.close(n4);
                        }
                        if (unixException.errno() == UnixConstants.ENOTDIR) break block11;
                        unixException.rethrowAsIOException((UnixPath)object);
                        l = 0L;
                    }
                    object = new UnixSecureDirectoryStream((UnixPath)path, l, n4, null);
                    return object;
                }
                NotDirectoryException notDirectoryException = new NotDirectoryException(((UnixPath)object).toString());
                throw notDirectoryException;
            }
            object = new ClosedDirectoryStreamException();
            throw object;
        }
        finally {
            this.ds.readLock().unlock();
        }
    }

    private class BasicFileAttributeViewImpl
    implements BasicFileAttributeView {
        final UnixPath file;
        final boolean followLinks;

        BasicFileAttributeViewImpl(UnixPath unixPath, boolean bl) {
            this.file = unixPath;
            this.followLinks = bl;
        }

        private void checkWriteAccess() {
            if (System.getSecurityManager() != null) {
                if (this.file == null) {
                    UnixSecureDirectoryStream.this.ds.directory().checkWrite();
                } else {
                    ((UnixPath)UnixSecureDirectoryStream.this.ds.directory().resolve(this.file)).checkWrite();
                }
            }
        }

        @Override
        public String name() {
            return "basic";
        }

        int open() throws IOException {
            int n;
            int n2 = n = UnixConstants.O_RDONLY;
            if (!this.followLinks) {
                n2 = n | UnixConstants.O_NOFOLLOW;
            }
            try {
                n2 = UnixNativeDispatcher.openat(UnixSecureDirectoryStream.this.dfd, this.file.asByteArray(), n2, 0);
                return n2;
            }
            catch (UnixException unixException) {
                unixException.rethrowAsIOException(this.file);
                return -1;
            }
        }

        /*
         * Loose catch block
         * Enabled aggressive exception aggregation
         */
        @Override
        public BasicFileAttributes readAttributes() throws IOException {
            UnixSecureDirectoryStream.this.ds.readLock().lock();
            try {
                if (UnixSecureDirectoryStream.this.ds.isOpen()) {
                    if (System.getSecurityManager() != null) {
                        if (this.file == null) {
                            UnixSecureDirectoryStream.this.ds.directory().checkRead();
                        } else {
                            ((UnixPath)UnixSecureDirectoryStream.this.ds.directory().resolve(this.file)).checkRead();
                        }
                    }
                    try {
                        BasicFileAttributes basicFileAttributes = this.file == null ? UnixFileAttributes.get(UnixSecureDirectoryStream.this.dfd) : UnixFileAttributes.get(UnixSecureDirectoryStream.this.dfd, this.file, this.followLinks);
                        basicFileAttributes = basicFileAttributes.asBasicFileAttributes();
                        return basicFileAttributes;
                    }
                    catch (UnixException unixException) {
                        unixException.rethrowAsIOException(this.file);
                        UnixSecureDirectoryStream.this.ds.readLock().unlock();
                        return null;
                    }
                }
                ClosedDirectoryStreamException closedDirectoryStreamException = new ClosedDirectoryStreamException();
                throw closedDirectoryStreamException;
                {
                    catch (Throwable throwable) {
                        throw throwable;
                    }
                }
            }
            finally {
                UnixSecureDirectoryStream.this.ds.readLock().unlock();
            }
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        @Override
        public void setTimes(FileTime var1_1, FileTime var2_6, FileTime var3_7) throws IOException {
            block15 : {
                block14 : {
                    this.checkWriteAccess();
                    UnixSecureDirectoryStream.access$100(UnixSecureDirectoryStream.this).readLock().lock();
                    if (!UnixSecureDirectoryStream.access$100(UnixSecureDirectoryStream.this).isOpen()) ** GOTO lbl47
                    var4_8 = this.file == null ? UnixSecureDirectoryStream.access$000(UnixSecureDirectoryStream.this) : this.open();
                    if (var1_1 != null) {
                        var3_7 = var1_1;
                        var5_9 = var2_6;
                        if (var2_6 != null) break block14;
                    }
                    var6_10 = var1_1;
                    try {
                        var5_9 = UnixFileAttributes.get(var4_8);
                        var3_7 = var1_1;
                        if (var1_1 == null) {
                            var6_10 = var1_1;
                            var3_7 = var5_9.lastModifiedTime();
                        }
                        var1_1 = var2_6;
                        if (var2_6 == null) {
                            var6_10 = var3_7;
                            var1_1 = var5_9.lastAccessTime();
                        }
                        var5_9 = var1_1;
                        break block14;
                    }
                    catch (Throwable var1_2) {
                        break block15;
                    }
                    catch (UnixException var1_3) {
                        var1_3.rethrowAsIOException(this.file);
                        var5_9 = var2_6;
                        var3_7 = var6_10;
                    }
                }
                try {
                    UnixNativeDispatcher.futimes(var4_8, var5_9.to(TimeUnit.MICROSECONDS), var3_7.to(TimeUnit.MICROSECONDS));
                }
                catch (UnixException var1_4) {
                    var1_4.rethrowAsIOException(this.file);
                }
                if (this.file == null) return;
                UnixNativeDispatcher.close(var4_8);
                return;
            }
            try {
                if (this.file == null) throw var1_2;
                UnixNativeDispatcher.close(var4_8);
                throw var1_2;
lbl47: // 1 sources:
                var1_1 = new ClosedDirectoryStreamException();
                throw var1_1;
            }
            finally {
                UnixSecureDirectoryStream.access$100(UnixSecureDirectoryStream.this).readLock().unlock();
            }
        }
    }

    private class PosixFileAttributeViewImpl
    extends BasicFileAttributeViewImpl
    implements PosixFileAttributeView {
        PosixFileAttributeViewImpl(UnixPath unixPath, boolean bl) {
            super(unixPath, bl);
        }

        private void checkWriteAndUserAccess() {
            SecurityManager securityManager = System.getSecurityManager();
            if (securityManager != null) {
                this.checkWriteAccess();
                securityManager.checkPermission(new RuntimePermission("accessUserInformation"));
            }
        }

        /*
         * Exception decompiling
         */
        private void setOwners(int var1_1, int var2_2) throws IOException {
            // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
            // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [2[TRYBLOCK]], but top level block is 5[TRYBLOCK]
            // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
            // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
            // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
            // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
            // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
            // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
            // org.benf.cfr.reader.entities.ClassFile.analyseInnerClassesPass1(ClassFile.java:778)
            // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:886)
            // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
            // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
            // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
            // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
            // org.benf.cfr.reader.Main.main(Main.java:48)
            throw new IllegalStateException("Decompilation failed");
        }

        @Override
        public UserPrincipal getOwner() throws IOException {
            return this.readAttributes().owner();
        }

        @Override
        public String name() {
            return "posix";
        }

        @Override
        public PosixFileAttributes readAttributes() throws IOException {
            Object object = System.getSecurityManager();
            if (object != null) {
                if (this.file == null) {
                    UnixSecureDirectoryStream.this.ds.directory().checkRead();
                } else {
                    ((UnixPath)UnixSecureDirectoryStream.this.ds.directory().resolve(this.file)).checkRead();
                }
                ((SecurityManager)object).checkPermission(new RuntimePermission("accessUserInformation"));
            }
            UnixSecureDirectoryStream.this.ds.readLock().lock();
            try {
                boolean bl = UnixSecureDirectoryStream.this.ds.isOpen();
                if (bl) {
                    object = this.file == null ? UnixFileAttributes.get(UnixSecureDirectoryStream.this.dfd) : UnixFileAttributes.get(UnixSecureDirectoryStream.this.dfd, this.file, this.followLinks);
                    return object;
                }
                object = new ClosedDirectoryStreamException();
                throw object;
            }
            finally {
                UnixSecureDirectoryStream.this.ds.readLock().unlock();
            }
        }

        @Override
        public void setGroup(GroupPrincipal groupPrincipal) throws IOException {
            if (groupPrincipal instanceof UnixUserPrincipals.Group) {
                this.setOwners(-1, ((UnixUserPrincipals.Group)groupPrincipal).gid());
                return;
            }
            throw new ProviderMismatchException();
        }

        @Override
        public void setOwner(UserPrincipal userPrincipal) throws IOException {
            if (userPrincipal instanceof UnixUserPrincipals.User) {
                if (!(userPrincipal instanceof UnixUserPrincipals.Group)) {
                    this.setOwners(((UnixUserPrincipals.User)userPrincipal).uid(), -1);
                    return;
                }
                throw new IOException("'owner' parameter can't be a group");
            }
            throw new ProviderMismatchException();
        }

        /*
         * Exception decompiling
         */
        @Override
        public void setPermissions(Set<PosixFilePermission> var1_1) throws IOException {
            // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
            // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [2[TRYBLOCK]], but top level block is 5[TRYBLOCK]
            // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
            // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
            // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
            // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
            // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
            // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
            // org.benf.cfr.reader.entities.ClassFile.analyseInnerClassesPass1(ClassFile.java:778)
            // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:886)
            // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
            // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
            // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
            // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
            // org.benf.cfr.reader.Main.main(Main.java:48)
            throw new IllegalStateException("Decompilation failed");
        }
    }

}

