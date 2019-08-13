/*
 * Decompiled with CFR 0.145.
 */
package android.system;

import libcore.util.Objects;

public final class StructStatVfs {
    public final long f_bavail;
    public final long f_bfree;
    public final long f_blocks;
    public final long f_bsize;
    public final long f_favail;
    public final long f_ffree;
    public final long f_files;
    public final long f_flag;
    public final long f_frsize;
    public final long f_fsid;
    public final long f_namemax;

    public StructStatVfs(long l, long l2, long l3, long l4, long l5, long l6, long l7, long l8, long l9, long l10, long l11) {
        this.f_bsize = l;
        this.f_frsize = l2;
        this.f_blocks = l3;
        this.f_bfree = l4;
        this.f_bavail = l5;
        this.f_files = l6;
        this.f_ffree = l7;
        this.f_favail = l8;
        this.f_fsid = l9;
        this.f_flag = l10;
        this.f_namemax = l11;
    }

    public String toString() {
        return Objects.toString(this);
    }
}

