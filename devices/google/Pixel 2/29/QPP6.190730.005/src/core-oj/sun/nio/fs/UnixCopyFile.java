/*
 * Decompiled with CFR 0.145.
 */
package sun.nio.fs;

import com.sun.nio.file.ExtendedCopyOption;
import java.io.IOException;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.CopyOption;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileSystem;
import java.nio.file.LinkOption;
import java.nio.file.LinkPermission;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileTime;
import java.security.Permission;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import sun.nio.fs.Cancellable;
import sun.nio.fs.UnixConstants;
import sun.nio.fs.UnixException;
import sun.nio.fs.UnixFileAttributes;
import sun.nio.fs.UnixFileSystem;
import sun.nio.fs.UnixNativeDispatcher;
import sun.nio.fs.UnixPath;

class UnixCopyFile {
    private UnixCopyFile() {
    }

    static void copy(UnixPath object, UnixPath unixPath, CopyOption ... object2) throws IOException {
        Flags flags;
        block21 : {
            Object object3 = System.getSecurityManager();
            if (object3 != null) {
                ((UnixPath)object).checkRead();
                unixPath.checkWrite();
            }
            flags = Flags.fromCopyOptions((CopyOption[])object2);
            try {
                object2 = UnixFileAttributes.get((UnixPath)object, flags.followLinks);
            }
            catch (UnixException unixException) {
                unixException.rethrowAsIOException((UnixPath)object);
                object2 = null;
            }
            if (object3 != null && ((UnixFileAttributes)object2).isSymbolicLink()) {
                ((SecurityManager)object3).checkPermission(new LinkPermission("symbolic"));
            }
            boolean bl = false;
            try {
                object3 = UnixFileAttributes.get(unixPath, false);
            }
            catch (UnixException unixException) {
                object3 = null;
            }
            if (object3 != null) {
                bl = true;
            }
            if (bl) {
                if (((UnixFileAttributes)object2).isSameFile((UnixFileAttributes)object3)) {
                    return;
                }
                if (flags.replaceExisting) {
                    try {
                        if (((UnixFileAttributes)object3).isDirectory()) {
                            UnixNativeDispatcher.rmdir(unixPath);
                            break block21;
                        }
                        UnixNativeDispatcher.unlink(unixPath);
                    }
                    catch (UnixException unixException) {
                        if (((UnixFileAttributes)object3).isDirectory() && (unixException.errno() == UnixConstants.EEXIST || unixException.errno() == UnixConstants.ENOTEMPTY)) {
                            throw new DirectoryNotEmptyException(unixPath.getPathForExceptionMessage());
                        }
                        unixException.rethrowAsIOException(unixPath);
                    }
                } else {
                    throw new FileAlreadyExistsException(unixPath.getPathForExceptionMessage());
                }
            }
        }
        if (((UnixFileAttributes)object2).isDirectory()) {
            UnixCopyFile.copyDirectory((UnixPath)object, (UnixFileAttributes)object2, unixPath, flags);
            return;
        }
        if (((UnixFileAttributes)object2).isSymbolicLink()) {
            UnixCopyFile.copyLink((UnixPath)object, (UnixFileAttributes)object2, unixPath, flags);
            return;
        }
        if (!flags.interruptible) {
            UnixCopyFile.copyFile((UnixPath)object, (UnixFileAttributes)object2, unixPath, flags, 0L);
            return;
        }
        object = new Cancellable((UnixPath)object, (UnixFileAttributes)object2, unixPath, flags){
            final /* synthetic */ UnixFileAttributes val$attrsToCopy;
            final /* synthetic */ Flags val$flags;
            final /* synthetic */ UnixPath val$source;
            final /* synthetic */ UnixPath val$target;
            {
                this.val$source = unixPath;
                this.val$attrsToCopy = unixFileAttributes;
                this.val$target = unixPath2;
                this.val$flags = flags;
            }

            @Override
            public void implRun() throws IOException {
                UnixCopyFile.copyFile(this.val$source, this.val$attrsToCopy, this.val$target, this.val$flags, this.addressToPollForCancel());
            }
        };
        try {
            Cancellable.runInterruptibly((Cancellable)object);
            return;
        }
        catch (ExecutionException executionException) {
            Throwable throwable = executionException.getCause();
            if (throwable instanceof IOException) {
                throw (IOException)throwable;
            }
            throw new IOException(throwable);
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static void copyDirectory(UnixPath var0, UnixFileAttributes var1_4, UnixPath var2_6, Flags var3_7) throws IOException {
        block25 : {
            try {
                UnixNativeDispatcher.mkdir(var2_6, var1_4.mode());
            }
            catch (UnixException var4_8) {
                var4_8.rethrowAsIOException(var2_6);
            }
            if (!(var3_7.copyBasicAttributes || var3_7.copyPosixAttributes || var3_7.copyNonPosixAttributes)) {
                return;
            }
            var5_13 = -1;
            try {
                var6_14 = UnixNativeDispatcher.open(var2_6, UnixConstants.O_RDONLY, 0);
            }
            catch (UnixException var4_9) {
                var6_14 = var5_13;
                if (!var3_7.copyNonPosixAttributes) break block25;
                var6_14 = var5_13;
                if (!var3_7.failIfUnableToCopyNonPosix) break block25;
                try {
                    UnixNativeDispatcher.rmdir(var2_6);
                }
                catch (UnixException var7_15) {
                    // empty catch block
                }
                var4_9.rethrowAsIOException(var2_6);
                var6_14 = var5_13;
            }
        }
        try {
            block26 : {
                var8_16 = var3_7.copyPosixAttributes;
                if (!var8_16) break block26;
                if (var6_14 < 0) ** GOTO lbl33
                try {
                    UnixNativeDispatcher.fchown(var6_14, var1_4.uid(), var1_4.gid());
                    UnixNativeDispatcher.fchmod(var6_14, var1_4.mode());
                    break block26;
lbl33: // 1 sources:
                    UnixNativeDispatcher.chown(var2_6, var1_4.uid(), var1_4.gid());
                    UnixNativeDispatcher.chmod(var2_6, var1_4.mode());
                }
                catch (UnixException var4_11) {
                    if (!var3_7.failIfUnableToCopyPosix) break block26;
                    var4_11.rethrowAsIOException(var2_6);
                }
            }
            if ((var8_16 = var3_7.copyNonPosixAttributes) && var6_14 >= 0) {
                block27 : {
                    var9_17 = -1;
                    try {
                        var5_13 = UnixNativeDispatcher.open(var0, UnixConstants.O_RDONLY, 0);
                    }
                    catch (UnixException var4_12) {
                        var5_13 = var9_17;
                        if (!var3_7.failIfUnableToCopyNonPosix) break block27;
                        var4_12.rethrowAsIOException(var0);
                        var5_13 = var9_17;
                    }
                }
                if (var5_13 >= 0) {
                    var0.getFileSystem().copyNonPosixAttributes(var5_13, var6_14);
                    UnixNativeDispatcher.close(var5_13);
                }
            }
            if ((var8_16 = var3_7.copyBasicAttributes) == false) return;
            if (var6_14 < 0) ** GOTO lbl60
            try {
                if (UnixNativeDispatcher.futimesSupported()) {
                    UnixNativeDispatcher.futimes(var6_14, var1_4.lastAccessTime().to(TimeUnit.MICROSECONDS), var1_4.lastModifiedTime().to(TimeUnit.MICROSECONDS));
                    return;
                }
lbl60: // 3 sources:
                UnixNativeDispatcher.utimes(var2_6, var1_4.lastAccessTime().to(TimeUnit.MICROSECONDS), var1_4.lastModifiedTime().to(TimeUnit.MICROSECONDS));
                return;
            }
            catch (UnixException var0_1) {
                if (var3_7.failIfUnableToCopyBasic == false) return;
                var0_1.rethrowAsIOException(var2_6);
                return;
            }
        }
        finally {
            if (var6_14 >= 0) {
                UnixNativeDispatcher.close(var6_14);
            }
            if (!true) {
                try {
                    UnixNativeDispatcher.rmdir(var2_6);
                }
                catch (UnixException var0_2) {}
            }
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static void copyFile(UnixPath unixPath, UnixFileAttributes unixFileAttributes, UnixPath unixPath2, Flags flags, long l) throws IOException {
        int n;
        int n2;
        Throwable throwable3;
        Throwable throwable22;
        block26 : {
            block25 : {
                n2 = -1;
                try {
                    n = UnixNativeDispatcher.open(unixPath, UnixConstants.O_RDONLY, 0);
                }
                catch (UnixException unixException) {
                    unixException.rethrowAsIOException(unixPath);
                    n = n2;
                }
                n2 = -1;
                try {
                    try {
                        int n3;
                        n2 = n3 = UnixNativeDispatcher.open(unixPath2, UnixConstants.O_WRONLY | UnixConstants.O_CREAT | UnixConstants.O_EXCL, unixFileAttributes.mode());
                    }
                    catch (UnixException unixException) {
                        unixException.rethrowAsIOException(unixPath2);
                    }
                }
                catch (Throwable throwable22) {}
                try {
                    boolean bl;
                    block24 : {
                        try {
                            UnixCopyFile.transfer(n2, n, l);
                        }
                        catch (UnixException unixException) {
                            unixException.rethrowAsIOException(unixPath, unixPath2);
                        }
                        bl = flags.copyPosixAttributes;
                        if (bl) {
                            try {
                                UnixNativeDispatcher.fchown(n2, unixFileAttributes.uid(), unixFileAttributes.gid());
                                UnixNativeDispatcher.fchmod(n2, unixFileAttributes.mode());
                            }
                            catch (UnixException unixException) {
                                if (!flags.failIfUnableToCopyPosix) break block24;
                                unixException.rethrowAsIOException(unixPath2);
                            }
                        }
                    }
                    if (flags.copyNonPosixAttributes) {
                        ((UnixFileSystem)unixPath.getFileSystem()).copyNonPosixAttributes(n, n2);
                    }
                    if (!(bl = flags.copyBasicAttributes)) break block25;
                    try {
                        if (UnixNativeDispatcher.futimesSupported()) {
                            UnixNativeDispatcher.futimes(n2, unixFileAttributes.lastAccessTime().to(TimeUnit.MICROSECONDS), unixFileAttributes.lastModifiedTime().to(TimeUnit.MICROSECONDS));
                        } else {
                            UnixNativeDispatcher.utimes(unixPath2, unixFileAttributes.lastAccessTime().to(TimeUnit.MICROSECONDS), unixFileAttributes.lastModifiedTime().to(TimeUnit.MICROSECONDS));
                        }
                    }
                    catch (UnixException unixException) {
                        if (!flags.failIfUnableToCopyBasic) break block25;
                        unixException.rethrowAsIOException(unixPath2);
                    }
                }
                catch (Throwable throwable3) {}
            }
            UnixNativeDispatcher.close(n2);
            if (true) break block26;
            try {
                UnixNativeDispatcher.unlink(unixPath2);
            }
            catch (UnixException unixException) {}
        }
        UnixNativeDispatcher.close(n);
        return;
        UnixNativeDispatcher.close(n);
        throw throwable22;
        {
            UnixNativeDispatcher.close(n2);
            if (false) throw throwable3;
            try {
                UnixNativeDispatcher.unlink(unixPath2);
                throw throwable3;
            }
            catch (UnixException unixException) {
                throw throwable3;
            }
        }
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static void copyLink(UnixPath arrby, UnixFileAttributes unixFileAttributes, UnixPath unixPath, Flags flags) throws IOException {
        Object var4_6 = null;
        try {
            byte[] arrby2;
            arrby = arrby2 = UnixNativeDispatcher.readlink((UnixPath)arrby);
        }
        catch (UnixException unixException) {
            unixException.rethrowAsIOException((UnixPath)arrby);
            arrby = var4_6;
        }
        UnixNativeDispatcher.symlink(arrby, unixPath);
        boolean bl = flags.copyPosixAttributes;
        if (!bl) return;
        try {
            UnixNativeDispatcher.lchown(unixPath, unixFileAttributes.uid(), unixFileAttributes.gid());
            return;
        }
        catch (UnixException unixException) {
            return;
        }
        catch (UnixException unixException) {
            unixException.rethrowAsIOException(unixPath);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static void copySpecial(UnixPath unixPath, UnixFileAttributes unixFileAttributes, UnixPath unixPath2, Flags flags) throws IOException {
        try {
            UnixNativeDispatcher.mknod(unixPath2, unixFileAttributes.mode(), unixFileAttributes.rdev());
        }
        catch (UnixException unixException) {
            unixException.rethrowAsIOException(unixPath2);
        }
        try {
            boolean bl;
            block15 : {
                bl = flags.copyPosixAttributes;
                if (bl) {
                    try {
                        UnixNativeDispatcher.chown(unixPath2, unixFileAttributes.uid(), unixFileAttributes.gid());
                        UnixNativeDispatcher.chmod(unixPath2, unixFileAttributes.mode());
                    }
                    catch (UnixException unixException) {
                        if (!flags.failIfUnableToCopyPosix) break block15;
                        unixException.rethrowAsIOException(unixPath2);
                    }
                }
            }
            if (!(bl = flags.copyBasicAttributes)) return;
            try {
                UnixNativeDispatcher.utimes(unixPath2, unixFileAttributes.lastAccessTime().to(TimeUnit.MICROSECONDS), unixFileAttributes.lastModifiedTime().to(TimeUnit.MICROSECONDS));
                return;
            }
            catch (UnixException unixException) {
                if (!flags.failIfUnableToCopyBasic) return;
                unixException.rethrowAsIOException(unixPath2);
                return;
            }
        }
        finally {
            if (!true) {
                try {
                    UnixNativeDispatcher.unlink(unixPath2);
                }
                catch (UnixException unixException) {}
            }
        }
    }

    static void move(UnixPath unixPath, UnixPath unixPath2, CopyOption ... object) throws IOException {
        Flags flags;
        block36 : {
            UnixFileAttributes unixFileAttributes;
            if (System.getSecurityManager() != null) {
                unixPath.checkWrite();
                unixPath2.checkWrite();
            }
            flags = Flags.fromMoveOptions((CopyOption[])object);
            if (flags.atomicMove) {
                UnixException unixException2;
                block35 : {
                    try {
                        UnixNativeDispatcher.rename(unixPath, unixPath2);
                    }
                    catch (UnixException unixException2) {
                        if (unixException2.errno() == UnixConstants.EXDEV) break block35;
                        unixException2.rethrowAsIOException(unixPath, unixPath2);
                    }
                    return;
                }
                throw new AtomicMoveNotSupportedException(unixPath.getPathForExceptionMessage(), unixPath2.getPathForExceptionMessage(), unixException2.errorString());
            }
            boolean bl = false;
            try {
                object = UnixFileAttributes.get(unixPath, false);
            }
            catch (UnixException unixException) {
                unixException.rethrowAsIOException(unixPath);
                object = null;
            }
            try {
                unixFileAttributes = UnixFileAttributes.get(unixPath2, false);
            }
            catch (UnixException unixException) {
                unixFileAttributes = null;
            }
            if (unixFileAttributes != null) {
                bl = true;
            }
            if (bl) {
                if (((UnixFileAttributes)object).isSameFile(unixFileAttributes)) {
                    return;
                }
                if (flags.replaceExisting) {
                    try {
                        if (unixFileAttributes.isDirectory()) {
                            UnixNativeDispatcher.rmdir(unixPath2);
                            break block36;
                        }
                        UnixNativeDispatcher.unlink(unixPath2);
                    }
                    catch (UnixException unixException) {
                        if (unixFileAttributes.isDirectory() && (unixException.errno() == UnixConstants.EEXIST || unixException.errno() == UnixConstants.ENOTEMPTY)) {
                            throw new DirectoryNotEmptyException(unixPath2.getPathForExceptionMessage());
                        }
                        unixException.rethrowAsIOException(unixPath2);
                    }
                } else {
                    throw new FileAlreadyExistsException(unixPath2.getPathForExceptionMessage());
                }
            }
        }
        try {
            UnixNativeDispatcher.rename(unixPath, unixPath2);
            return;
        }
        catch (UnixException unixException) {
            if (unixException.errno() != UnixConstants.EXDEV && unixException.errno() != UnixConstants.EISDIR) {
                unixException.rethrowAsIOException(unixPath, unixPath2);
            }
            if (((UnixFileAttributes)object).isDirectory()) {
                UnixCopyFile.copyDirectory(unixPath, (UnixFileAttributes)object, unixPath2, flags);
            } else if (((UnixFileAttributes)object).isSymbolicLink()) {
                UnixCopyFile.copyLink(unixPath, (UnixFileAttributes)object, unixPath2, flags);
            } else if (((UnixFileAttributes)object).isDevice()) {
                UnixCopyFile.copySpecial(unixPath, (UnixFileAttributes)object, unixPath2, flags);
            } else {
                UnixCopyFile.copyFile(unixPath, (UnixFileAttributes)object, unixPath2, flags, 0L);
            }
            try {
                if (((UnixFileAttributes)object).isDirectory()) {
                    UnixNativeDispatcher.rmdir(unixPath);
                } else {
                    UnixNativeDispatcher.unlink(unixPath);
                }
            }
            catch (UnixException unixException3) {
                try {
                    if (((UnixFileAttributes)object).isDirectory()) {
                        UnixNativeDispatcher.rmdir(unixPath2);
                    } else {
                        UnixNativeDispatcher.unlink(unixPath2);
                    }
                }
                catch (UnixException unixException4) {
                    // empty catch block
                }
                if (((UnixFileAttributes)object).isDirectory() && (unixException3.errno() == UnixConstants.EEXIST || unixException3.errno() == UnixConstants.ENOTEMPTY)) {
                    throw new DirectoryNotEmptyException(unixPath.getPathForExceptionMessage());
                }
                unixException3.rethrowAsIOException(unixPath);
            }
            return;
        }
    }

    static native void transfer(int var0, int var1, long var2) throws UnixException;

    private static class Flags {
        boolean atomicMove;
        boolean copyBasicAttributes;
        boolean copyNonPosixAttributes;
        boolean copyPosixAttributes;
        boolean failIfUnableToCopyBasic;
        boolean failIfUnableToCopyNonPosix;
        boolean failIfUnableToCopyPosix;
        boolean followLinks;
        boolean interruptible;
        boolean replaceExisting;

        private Flags() {
        }

        static Flags fromCopyOptions(CopyOption ... arrcopyOption) {
            Flags flags = new Flags();
            flags.followLinks = true;
            for (CopyOption copyOption : arrcopyOption) {
                if (copyOption == StandardCopyOption.REPLACE_EXISTING) {
                    flags.replaceExisting = true;
                    continue;
                }
                if (copyOption == LinkOption.NOFOLLOW_LINKS) {
                    flags.followLinks = false;
                    continue;
                }
                if (copyOption == StandardCopyOption.COPY_ATTRIBUTES) {
                    flags.copyBasicAttributes = true;
                    flags.copyPosixAttributes = true;
                    flags.copyNonPosixAttributes = true;
                    flags.failIfUnableToCopyBasic = true;
                    continue;
                }
                if (copyOption == ExtendedCopyOption.INTERRUPTIBLE) {
                    flags.interruptible = true;
                    continue;
                }
                if (copyOption == null) {
                    throw new NullPointerException();
                }
                throw new UnsupportedOperationException("Unsupported copy option");
            }
            return flags;
        }

        static Flags fromMoveOptions(CopyOption ... arrcopyOption) {
            Flags flags = new Flags();
            for (CopyOption copyOption : arrcopyOption) {
                if (copyOption == StandardCopyOption.ATOMIC_MOVE) {
                    flags.atomicMove = true;
                    continue;
                }
                if (copyOption == StandardCopyOption.REPLACE_EXISTING) {
                    flags.replaceExisting = true;
                    continue;
                }
                if (copyOption == LinkOption.NOFOLLOW_LINKS) {
                    continue;
                }
                if (copyOption == null) {
                    throw new NullPointerException();
                }
                throw new UnsupportedOperationException("Unsupported copy option");
            }
            flags.copyBasicAttributes = true;
            flags.copyPosixAttributes = true;
            flags.copyNonPosixAttributes = true;
            flags.failIfUnableToCopyBasic = true;
            return flags;
        }
    }

}

