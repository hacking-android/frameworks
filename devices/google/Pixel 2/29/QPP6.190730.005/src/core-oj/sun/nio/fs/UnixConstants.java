/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.system.OsConstants
 */
package sun.nio.fs;

import android.system.OsConstants;

class UnixConstants {
    static final int AT_REMOVEDIR = 512;
    static final int AT_SYMLINK_NOFOLLOW = 256;
    static final int EACCES;
    static final int EAGAIN;
    static final int EEXIST;
    static final int EINVAL;
    static final int EISDIR;
    static final int ELOOP;
    static final int EMFILE;
    static final int ENODATA;
    static final int ENOENT;
    static final int ENOSPC;
    static final int ENOSYS;
    static final int ENOTDIR;
    static final int ENOTEMPTY;
    static final int ERANGE;
    static final int EROFS;
    static final int EXDEV;
    static final int F_OK;
    static final int O_APPEND;
    static final int O_CREAT;
    static final int O_DSYNC;
    static final int O_EXCL;
    static final int O_NOFOLLOW;
    static final int O_RDONLY;
    static final int O_RDWR;
    static final int O_SYNC;
    static final int O_TRUNC;
    static final int O_WRONLY;
    static final int R_OK;
    static final int S_IAMB;
    static final int S_IFBLK;
    static final int S_IFCHR;
    static final int S_IFDIR;
    static final int S_IFIFO;
    static final int S_IFLNK;
    static final int S_IFMT;
    static final int S_IFREG;
    static final int S_IRGRP;
    static final int S_IROTH;
    static final int S_IRUSR;
    static final int S_IWGRP;
    static final int S_IWOTH;
    static final int S_IWUSR;
    static final int S_IXGRP;
    static final int S_IXOTH;
    static final int S_IXUSR;
    static final int W_OK;
    static final int X_OK;

    static {
        O_RDONLY = OsConstants.O_RDONLY;
        O_WRONLY = OsConstants.O_WRONLY;
        O_RDWR = OsConstants.O_RDWR;
        O_APPEND = OsConstants.O_APPEND;
        O_CREAT = OsConstants.O_CREAT;
        O_EXCL = OsConstants.O_EXCL;
        O_TRUNC = OsConstants.O_TRUNC;
        O_SYNC = OsConstants.O_SYNC;
        O_DSYNC = OsConstants.O_DSYNC;
        O_NOFOLLOW = OsConstants.O_NOFOLLOW;
        S_IAMB = UnixConstants.get_S_IAMB();
        S_IRUSR = OsConstants.S_IRUSR;
        S_IWUSR = OsConstants.S_IWUSR;
        S_IXUSR = OsConstants.S_IXUSR;
        S_IRGRP = OsConstants.S_IRGRP;
        S_IWGRP = OsConstants.S_IWGRP;
        S_IXGRP = OsConstants.S_IXGRP;
        S_IROTH = OsConstants.S_IROTH;
        S_IWOTH = OsConstants.S_IWOTH;
        S_IXOTH = OsConstants.S_IXOTH;
        S_IFMT = OsConstants.S_IFMT;
        S_IFREG = OsConstants.S_IFREG;
        S_IFDIR = OsConstants.S_IFDIR;
        S_IFLNK = OsConstants.S_IFLNK;
        S_IFCHR = OsConstants.S_IFCHR;
        S_IFBLK = OsConstants.S_IFBLK;
        S_IFIFO = OsConstants.S_IFIFO;
        R_OK = OsConstants.R_OK;
        W_OK = OsConstants.W_OK;
        X_OK = OsConstants.X_OK;
        F_OK = OsConstants.F_OK;
        ENOENT = OsConstants.ENOENT;
        EACCES = OsConstants.EACCES;
        EEXIST = OsConstants.EEXIST;
        ENOTDIR = OsConstants.ENOTDIR;
        EINVAL = OsConstants.EINVAL;
        EXDEV = OsConstants.EXDEV;
        EISDIR = OsConstants.EISDIR;
        ENOTEMPTY = OsConstants.ENOTEMPTY;
        ENOSPC = OsConstants.ENOSPC;
        EAGAIN = OsConstants.EAGAIN;
        ENOSYS = OsConstants.ENOSYS;
        ELOOP = OsConstants.ELOOP;
        EROFS = OsConstants.EROFS;
        ENODATA = OsConstants.ENODATA;
        ERANGE = OsConstants.ERANGE;
        EMFILE = OsConstants.EMFILE;
    }

    private UnixConstants() {
    }

    private static int get_S_IAMB() {
        return OsConstants.S_IRUSR | OsConstants.S_IWUSR | OsConstants.S_IXUSR | OsConstants.S_IRGRP | OsConstants.S_IWGRP | OsConstants.S_IXGRP | OsConstants.S_IROTH | OsConstants.S_IWOTH | OsConstants.S_IXOTH;
    }
}

