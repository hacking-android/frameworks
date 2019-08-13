/*
 * Decompiled with CFR 0.145.
 */
package java.nio.file;

import java.io.Serializable;
import java.net.URI;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.spi.FileSystemProvider;

public final class Paths {
    private Paths() {
    }

    public static Path get(String string, String ... arrstring) {
        return FileSystems.getDefault().getPath(string, arrstring);
    }

    public static Path get(URI serializable) {
        String string = ((URI)serializable).getScheme();
        if (string != null) {
            if (string.equalsIgnoreCase("file")) {
                return FileSystems.getDefault().provider().getPath((URI)serializable);
            }
            for (FileSystemProvider fileSystemProvider : FileSystemProvider.installedProviders()) {
                if (!fileSystemProvider.getScheme().equalsIgnoreCase(string)) continue;
                return fileSystemProvider.getPath((URI)serializable);
            }
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("Provider \"");
            ((StringBuilder)serializable).append(string);
            ((StringBuilder)serializable).append("\" not installed");
            throw new FileSystemNotFoundException(((StringBuilder)serializable).toString());
        }
        throw new IllegalArgumentException("Missing scheme");
    }
}

