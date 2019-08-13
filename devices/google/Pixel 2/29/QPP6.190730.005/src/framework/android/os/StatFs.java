/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.system.ErrnoException
 *  android.system.Os
 *  android.system.StructStatVfs
 */
package android.os;

import android.annotation.UnsupportedAppUsage;
import android.system.ErrnoException;
import android.system.Os;
import android.system.StructStatVfs;

public class StatFs {
    @UnsupportedAppUsage
    private StructStatVfs mStat;

    public StatFs(String string2) {
        this.mStat = StatFs.doStat(string2);
    }

    private static StructStatVfs doStat(String string2) {
        try {
            StructStatVfs structStatVfs = Os.statvfs((String)string2);
            return structStatVfs;
        }
        catch (ErrnoException errnoException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid path: ");
            stringBuilder.append(string2);
            throw new IllegalArgumentException(stringBuilder.toString(), errnoException);
        }
    }

    @Deprecated
    public int getAvailableBlocks() {
        return (int)this.mStat.f_bavail;
    }

    public long getAvailableBlocksLong() {
        return this.mStat.f_bavail;
    }

    public long getAvailableBytes() {
        return this.mStat.f_bavail * this.mStat.f_frsize;
    }

    @Deprecated
    public int getBlockCount() {
        return (int)this.mStat.f_blocks;
    }

    public long getBlockCountLong() {
        return this.mStat.f_blocks;
    }

    @Deprecated
    public int getBlockSize() {
        return (int)this.mStat.f_frsize;
    }

    public long getBlockSizeLong() {
        return this.mStat.f_frsize;
    }

    @Deprecated
    public int getFreeBlocks() {
        return (int)this.mStat.f_bfree;
    }

    public long getFreeBlocksLong() {
        return this.mStat.f_bfree;
    }

    public long getFreeBytes() {
        return this.mStat.f_bfree * this.mStat.f_frsize;
    }

    public long getTotalBytes() {
        return this.mStat.f_blocks * this.mStat.f_frsize;
    }

    public void restat(String string2) {
        this.mStat = StatFs.doStat(string2);
    }
}

