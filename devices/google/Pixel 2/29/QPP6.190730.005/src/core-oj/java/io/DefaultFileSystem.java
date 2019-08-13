/*
 * Decompiled with CFR 0.145.
 */
package java.io;

import java.io.FileSystem;
import java.io.UnixFileSystem;

class DefaultFileSystem {
    DefaultFileSystem() {
    }

    public static FileSystem getFileSystem() {
        return new UnixFileSystem();
    }
}

