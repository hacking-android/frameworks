/*
 * Decompiled with CFR 0.145.
 */
package java.nio.file;

import java.io.IOException;
import java.nio.file.attribute.FileAttributeView;
import java.nio.file.attribute.FileStoreAttributeView;

public abstract class FileStore {
    protected FileStore() {
    }

    public abstract Object getAttribute(String var1) throws IOException;

    public abstract <V extends FileStoreAttributeView> V getFileStoreAttributeView(Class<V> var1);

    public abstract long getTotalSpace() throws IOException;

    public abstract long getUnallocatedSpace() throws IOException;

    public abstract long getUsableSpace() throws IOException;

    public abstract boolean isReadOnly();

    public abstract String name();

    public abstract boolean supportsFileAttributeView(Class<? extends FileAttributeView> var1);

    public abstract boolean supportsFileAttributeView(String var1);

    public abstract String type();
}

