/*
 * Decompiled with CFR 0.145.
 */
package sun.nio.fs;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Path;
import java.nio.file.attribute.DosFileAttributeView;
import java.nio.file.attribute.FileAttributeView;
import java.nio.file.attribute.PosixFileAttributeView;
import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.util.Arrays;
import sun.nio.fs.LinuxFileSystem;
import sun.nio.fs.LinuxNativeDispatcher;
import sun.nio.fs.UnixConstants;
import sun.nio.fs.UnixException;
import sun.nio.fs.UnixFileAttributes;
import sun.nio.fs.UnixFileStore;
import sun.nio.fs.UnixFileSystem;
import sun.nio.fs.UnixMountEntry;
import sun.nio.fs.UnixNativeDispatcher;
import sun.nio.fs.UnixPath;
import sun.nio.fs.Util;

class LinuxFileStore
extends UnixFileStore {
    private volatile boolean xattrChecked;
    private volatile boolean xattrEnabled;

    LinuxFileStore(UnixFileSystem unixFileSystem, UnixMountEntry unixMountEntry) throws IOException {
        super(unixFileSystem, unixMountEntry);
    }

    LinuxFileStore(UnixPath unixPath) throws IOException {
        super(unixPath);
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private boolean isExtendedAttributesEnabled(UnixPath var1_1) {
        var2_5 = var1_1.openForAttributeAccess(false);
        LinuxNativeDispatcher.fgetxattr(var2_5, Util.toBytes("user.java"), 0L, 0);
        UnixNativeDispatcher.close(var2_5);
        return true;
        {
            catch (Throwable var1_2) {
                ** GOTO lbl20
            }
            catch (UnixException var1_3) {}
            {
                var3_6 = var1_3.errno();
                var4_7 = UnixConstants.ENODATA;
                if (var3_6 != var4_7) ** GOTO lbl18
            }
            try {
                UnixNativeDispatcher.close(var2_5);
                return true;
lbl18: // 1 sources:
                UnixNativeDispatcher.close(var2_5);
                return false;
lbl20: // 1 sources:
                UnixNativeDispatcher.close(var2_5);
                throw var1_2;
            }
            catch (IOException var1_4) {
                // empty catch block
            }
        }
        return false;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    UnixMountEntry findMountEntry() throws IOException {
        Object object;
        void var2_5;
        Object object2;
        LinuxFileSystem linuxFileSystem = (LinuxFileSystem)this.file().getFileSystem();
        Object object32 = null;
        try {
            object2 = UnixNativeDispatcher.realpath(this.file());
            object = new UnixPath((UnixFileSystem)linuxFileSystem, (byte[])object2);
            Path path = object;
        }
        catch (UnixException unixException) {
            unixException.rethrowAsIOException(this.file());
        }
        for (object = var2_4.getParent(); object != null; object = object.getParent()) {
            object2 = null;
            try {
                UnixFileAttributes unixFileAttributes = UnixFileAttributes.get((UnixPath)object, true);
                object2 = unixFileAttributes;
            }
            catch (UnixException unixException) {
                unixException.rethrowAsIOException((UnixPath)object);
            }
            if (((UnixFileAttributes)object2).dev() != this.dev()) break;
            byte[] arrby = object;
        }
        object = var2_5.asByteArray();
        for (UnixMountEntry unixMountEntry : linuxFileSystem.getMountEntries("/proc/mounts")) {
            if (!Arrays.equals(object, unixMountEntry.dir())) continue;
            return unixMountEntry;
        }
        throw new IOException("Mount point not found");
    }

    @Override
    public boolean supportsFileAttributeView(Class<? extends FileAttributeView> object) {
        if (object != DosFileAttributeView.class && object != UserDefinedFileAttributeView.class) {
            if (object == PosixFileAttributeView.class && this.entry().fstype().equals("vfat")) {
                return false;
            }
            return super.supportsFileAttributeView((Class<? extends FileAttributeView>)object);
        }
        object = this.checkIfFeaturePresent("user_xattr");
        if (object == UnixFileStore.FeatureStatus.PRESENT) {
            return true;
        }
        if (object == UnixFileStore.FeatureStatus.NOT_PRESENT) {
            return false;
        }
        if (this.entry().hasOption("user_xattr")) {
            return true;
        }
        if (!this.entry().fstype().equals("ext3") && !this.entry().fstype().equals("ext4")) {
            if (!this.xattrChecked) {
                this.xattrEnabled = this.isExtendedAttributesEnabled(new UnixPath((UnixFileSystem)this.file().getFileSystem(), this.entry().dir()));
                this.xattrChecked = true;
            }
            return this.xattrEnabled;
        }
        return false;
    }

    @Override
    public boolean supportsFileAttributeView(String string) {
        if (string.equals("dos")) {
            return this.supportsFileAttributeView(DosFileAttributeView.class);
        }
        if (string.equals("user")) {
            return this.supportsFileAttributeView(UserDefinedFileAttributeView.class);
        }
        return super.supportsFileAttributeView(string);
    }
}

