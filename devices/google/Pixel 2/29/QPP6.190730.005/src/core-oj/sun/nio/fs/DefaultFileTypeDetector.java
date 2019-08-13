/*
 * Decompiled with CFR 0.145.
 */
package sun.nio.fs;

import java.nio.file.FileSystems;
import java.nio.file.spi.FileSystemProvider;
import java.nio.file.spi.FileTypeDetector;
import sun.nio.fs.UnixFileSystemProvider;

public class DefaultFileTypeDetector {
    private DefaultFileTypeDetector() {
    }

    public static FileTypeDetector create() {
        return ((UnixFileSystemProvider)FileSystems.getDefault().provider()).getFileTypeDetector();
    }
}

