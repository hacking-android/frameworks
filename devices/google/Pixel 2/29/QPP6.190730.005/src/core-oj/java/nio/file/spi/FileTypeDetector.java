/*
 * Decompiled with CFR 0.145.
 */
package java.nio.file.spi;

import java.io.IOException;
import java.nio.file.Path;
import java.security.Permission;

public abstract class FileTypeDetector {
    protected FileTypeDetector() {
        this(FileTypeDetector.checkPermission());
    }

    private FileTypeDetector(Void void_) {
    }

    private static Void checkPermission() {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(new RuntimePermission("fileTypeDetector"));
        }
        return null;
    }

    public abstract String probeContentType(Path var1) throws IOException;
}

