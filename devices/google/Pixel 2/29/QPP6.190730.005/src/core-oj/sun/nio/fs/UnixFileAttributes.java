/*
 * Decompiled with CFR 0.145.
 */
package sun.nio.fs;

import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.GroupPrincipal;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.UserPrincipal;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import sun.nio.fs.UnixConstants;
import sun.nio.fs.UnixException;
import sun.nio.fs.UnixFileKey;
import sun.nio.fs.UnixNativeDispatcher;
import sun.nio.fs.UnixPath;
import sun.nio.fs.UnixUserPrincipals;

class UnixFileAttributes
implements PosixFileAttributes {
    private volatile GroupPrincipal group;
    private volatile UnixFileKey key;
    private volatile UserPrincipal owner;
    private long st_atime_nsec;
    private long st_atime_sec;
    private long st_birthtime_sec;
    private long st_ctime_nsec;
    private long st_ctime_sec;
    private long st_dev;
    private int st_gid;
    private long st_ino;
    private int st_mode;
    private long st_mtime_nsec;
    private long st_mtime_sec;
    private int st_nlink;
    private long st_rdev;
    private long st_size;
    private int st_uid;

    private UnixFileAttributes() {
    }

    static UnixFileAttributes get(int n) throws UnixException {
        UnixFileAttributes unixFileAttributes = new UnixFileAttributes();
        UnixNativeDispatcher.fstat(n, unixFileAttributes);
        return unixFileAttributes;
    }

    static UnixFileAttributes get(int n, UnixPath unixPath, boolean bl) throws UnixException {
        UnixFileAttributes unixFileAttributes = new UnixFileAttributes();
        int n2 = bl ? 0 : 256;
        UnixNativeDispatcher.fstatat(n, unixPath.asByteArray(), n2, unixFileAttributes);
        return unixFileAttributes;
    }

    static UnixFileAttributes get(UnixPath unixPath, boolean bl) throws UnixException {
        UnixFileAttributes unixFileAttributes = new UnixFileAttributes();
        if (bl) {
            UnixNativeDispatcher.stat(unixPath, unixFileAttributes);
        } else {
            UnixNativeDispatcher.lstat(unixPath, unixFileAttributes);
        }
        return unixFileAttributes;
    }

    private static FileTime toFileTime(long l, long l2) {
        if (l2 == 0L) {
            return FileTime.from(l, TimeUnit.SECONDS);
        }
        return FileTime.from(1000000L * l + l2 / 1000L, TimeUnit.MICROSECONDS);
    }

    static UnixFileAttributes toUnixFileAttributes(BasicFileAttributes basicFileAttributes) {
        if (basicFileAttributes instanceof UnixFileAttributes) {
            return (UnixFileAttributes)basicFileAttributes;
        }
        if (basicFileAttributes instanceof UnixAsBasicFileAttributes) {
            return ((UnixAsBasicFileAttributes)basicFileAttributes).unwrap();
        }
        return null;
    }

    BasicFileAttributes asBasicFileAttributes() {
        return UnixAsBasicFileAttributes.wrap(this);
    }

    @Override
    public FileTime creationTime() {
        if (UnixNativeDispatcher.birthtimeSupported()) {
            return FileTime.from(this.st_birthtime_sec, TimeUnit.SECONDS);
        }
        return this.lastModifiedTime();
    }

    FileTime ctime() {
        return UnixFileAttributes.toFileTime(this.st_ctime_sec, this.st_ctime_nsec);
    }

    long dev() {
        return this.st_dev;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public UnixFileKey fileKey() {
        if (this.key != null) return this.key;
        synchronized (this) {
            UnixFileKey unixFileKey;
            if (this.key != null) return this.key;
            this.key = unixFileKey = new UnixFileKey(this.st_dev, this.st_ino);
            return this.key;
        }
    }

    int gid() {
        return this.st_gid;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public GroupPrincipal group() {
        if (this.group != null) return this.group;
        synchronized (this) {
            if (this.group != null) return this.group;
            this.group = UnixUserPrincipals.fromGid(this.st_gid);
            return this.group;
        }
    }

    long ino() {
        return this.st_ino;
    }

    boolean isDevice() {
        int n = this.st_mode & UnixConstants.S_IFMT;
        boolean bl = n == UnixConstants.S_IFCHR || n == UnixConstants.S_IFBLK || n == UnixConstants.S_IFIFO;
        return bl;
    }

    @Override
    public boolean isDirectory() {
        boolean bl = (this.st_mode & UnixConstants.S_IFMT) == UnixConstants.S_IFDIR;
        return bl;
    }

    @Override
    public boolean isOther() {
        int n = this.st_mode & UnixConstants.S_IFMT;
        boolean bl = n != UnixConstants.S_IFREG && n != UnixConstants.S_IFDIR && n != UnixConstants.S_IFLNK;
        return bl;
    }

    @Override
    public boolean isRegularFile() {
        boolean bl = (this.st_mode & UnixConstants.S_IFMT) == UnixConstants.S_IFREG;
        return bl;
    }

    boolean isSameFile(UnixFileAttributes unixFileAttributes) {
        boolean bl = this.st_ino == unixFileAttributes.st_ino && this.st_dev == unixFileAttributes.st_dev;
        return bl;
    }

    @Override
    public boolean isSymbolicLink() {
        boolean bl = (this.st_mode & UnixConstants.S_IFMT) == UnixConstants.S_IFLNK;
        return bl;
    }

    @Override
    public FileTime lastAccessTime() {
        return UnixFileAttributes.toFileTime(this.st_atime_sec, this.st_atime_nsec);
    }

    @Override
    public FileTime lastModifiedTime() {
        return UnixFileAttributes.toFileTime(this.st_mtime_sec, this.st_mtime_nsec);
    }

    int mode() {
        return this.st_mode;
    }

    int nlink() {
        return this.st_nlink;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public UserPrincipal owner() {
        if (this.owner != null) return this.owner;
        synchronized (this) {
            if (this.owner != null) return this.owner;
            this.owner = UnixUserPrincipals.fromUid(this.st_uid);
            return this.owner;
        }
    }

    @Override
    public Set<PosixFilePermission> permissions() {
        int n = this.st_mode & UnixConstants.S_IAMB;
        HashSet<PosixFilePermission> hashSet = new HashSet<PosixFilePermission>();
        if ((UnixConstants.S_IRUSR & n) > 0) {
            hashSet.add(PosixFilePermission.OWNER_READ);
        }
        if ((UnixConstants.S_IWUSR & n) > 0) {
            hashSet.add(PosixFilePermission.OWNER_WRITE);
        }
        if ((UnixConstants.S_IXUSR & n) > 0) {
            hashSet.add(PosixFilePermission.OWNER_EXECUTE);
        }
        if ((UnixConstants.S_IRGRP & n) > 0) {
            hashSet.add(PosixFilePermission.GROUP_READ);
        }
        if ((UnixConstants.S_IWGRP & n) > 0) {
            hashSet.add(PosixFilePermission.GROUP_WRITE);
        }
        if ((UnixConstants.S_IXGRP & n) > 0) {
            hashSet.add(PosixFilePermission.GROUP_EXECUTE);
        }
        if ((UnixConstants.S_IROTH & n) > 0) {
            hashSet.add(PosixFilePermission.OTHERS_READ);
        }
        if ((UnixConstants.S_IWOTH & n) > 0) {
            hashSet.add(PosixFilePermission.OTHERS_WRITE);
        }
        if ((UnixConstants.S_IXOTH & n) > 0) {
            hashSet.add(PosixFilePermission.OTHERS_EXECUTE);
        }
        return hashSet;
    }

    long rdev() {
        return this.st_rdev;
    }

    @Override
    public long size() {
        return this.st_size;
    }

    int uid() {
        return this.st_uid;
    }

    private static class UnixAsBasicFileAttributes
    implements BasicFileAttributes {
        private final UnixFileAttributes attrs;

        private UnixAsBasicFileAttributes(UnixFileAttributes unixFileAttributes) {
            this.attrs = unixFileAttributes;
        }

        static UnixAsBasicFileAttributes wrap(UnixFileAttributes unixFileAttributes) {
            return new UnixAsBasicFileAttributes(unixFileAttributes);
        }

        @Override
        public FileTime creationTime() {
            return this.attrs.creationTime();
        }

        @Override
        public Object fileKey() {
            return this.attrs.fileKey();
        }

        @Override
        public boolean isDirectory() {
            return this.attrs.isDirectory();
        }

        @Override
        public boolean isOther() {
            return this.attrs.isOther();
        }

        @Override
        public boolean isRegularFile() {
            return this.attrs.isRegularFile();
        }

        @Override
        public boolean isSymbolicLink() {
            return this.attrs.isSymbolicLink();
        }

        @Override
        public FileTime lastAccessTime() {
            return this.attrs.lastAccessTime();
        }

        @Override
        public FileTime lastModifiedTime() {
            return this.attrs.lastModifiedTime();
        }

        @Override
        public long size() {
            return this.attrs.size();
        }

        UnixFileAttributes unwrap() {
            return this.attrs;
        }
    }

}

