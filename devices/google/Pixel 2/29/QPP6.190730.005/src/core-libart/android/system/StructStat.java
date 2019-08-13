/*
 * Decompiled with CFR 0.145.
 */
package android.system;

import android.system.StructTimespec;
import libcore.util.Objects;

public final class StructStat {
    public final StructTimespec st_atim;
    public final long st_atime;
    public final long st_blksize;
    public final long st_blocks;
    public final StructTimespec st_ctim;
    public final long st_ctime;
    public final long st_dev;
    public final int st_gid;
    public final long st_ino;
    public final int st_mode;
    public final StructTimespec st_mtim;
    public final long st_mtime;
    public final long st_nlink;
    public final long st_rdev;
    public final long st_size;
    public final int st_uid;

    public StructStat(long l, long l2, int n, long l3, int n2, int n3, long l4, long l5, long l6, long l7, long l8, long l9, long l10) {
        this(l, l2, n, l3, n2, n3, l4, l5, new StructTimespec(l6, 0L), new StructTimespec(l7, 0L), new StructTimespec(l8, 0L), l9, l10);
    }

    public StructStat(long l, long l2, int n, long l3, int n2, int n3, long l4, long l5, StructTimespec structTimespec, StructTimespec structTimespec2, StructTimespec structTimespec3, long l6, long l7) {
        this.st_dev = l;
        this.st_ino = l2;
        this.st_mode = n;
        this.st_nlink = l3;
        this.st_uid = n2;
        this.st_gid = n3;
        this.st_rdev = l4;
        this.st_size = l5;
        this.st_atime = structTimespec.tv_sec;
        this.st_mtime = structTimespec2.tv_sec;
        this.st_ctime = structTimespec3.tv_sec;
        this.st_atim = structTimespec;
        this.st_mtim = structTimespec2;
        this.st_ctim = structTimespec3;
        this.st_blksize = l6;
        this.st_blocks = l7;
    }

    public String toString() {
        return Objects.toString(this);
    }
}

