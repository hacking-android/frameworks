/*
 * Decompiled with CFR 0.145.
 */
package sun.nio.fs;

import sun.nio.fs.UnixException;
import sun.nio.fs.UnixNativeDispatcher;
import sun.nio.fs.UnixPath;

class UnixFileStoreAttributes {
    private long f_bavail;
    private long f_bfree;
    private long f_blocks;
    private long f_frsize;

    private UnixFileStoreAttributes() {
    }

    static UnixFileStoreAttributes get(UnixPath unixPath) throws UnixException {
        UnixFileStoreAttributes unixFileStoreAttributes = new UnixFileStoreAttributes();
        UnixNativeDispatcher.statvfs(unixPath, unixFileStoreAttributes);
        return unixFileStoreAttributes;
    }

    long availableBlocks() {
        return this.f_bavail;
    }

    long blockSize() {
        return this.f_frsize;
    }

    long freeBlocks() {
        return this.f_bfree;
    }

    long totalBlocks() {
        return this.f_blocks;
    }
}

