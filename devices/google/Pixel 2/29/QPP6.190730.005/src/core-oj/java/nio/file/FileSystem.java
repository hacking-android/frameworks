/*
 * Decompiled with CFR 0.145.
 */
package java.nio.file;

import java.io.Closeable;
import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.WatchService;
import java.nio.file.attribute.UserPrincipalLookupService;
import java.nio.file.spi.FileSystemProvider;
import java.util.Set;

public abstract class FileSystem
implements Closeable {
    protected FileSystem() {
    }

    @Override
    public abstract void close() throws IOException;

    public abstract Iterable<FileStore> getFileStores();

    public abstract Path getPath(String var1, String ... var2);

    public abstract PathMatcher getPathMatcher(String var1);

    public abstract Iterable<Path> getRootDirectories();

    public abstract String getSeparator();

    public abstract UserPrincipalLookupService getUserPrincipalLookupService();

    public abstract boolean isOpen();

    public abstract boolean isReadOnly();

    public abstract WatchService newWatchService() throws IOException;

    public abstract FileSystemProvider provider();

    public abstract Set<String> supportedFileAttributeViews();
}

