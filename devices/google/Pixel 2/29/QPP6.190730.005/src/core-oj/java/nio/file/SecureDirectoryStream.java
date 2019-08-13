/*
 * Decompiled with CFR 0.145.
 */
package java.nio.file;

import java.io.IOException;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.DirectoryStream;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.FileAttributeView;
import java.util.Set;

public interface SecureDirectoryStream<T>
extends DirectoryStream<T> {
    public void deleteDirectory(T var1) throws IOException;

    public void deleteFile(T var1) throws IOException;

    public <V extends FileAttributeView> V getFileAttributeView(Class<V> var1);

    public <V extends FileAttributeView> V getFileAttributeView(T var1, Class<V> var2, LinkOption ... var3);

    public void move(T var1, SecureDirectoryStream<T> var2, T var3) throws IOException;

    public SeekableByteChannel newByteChannel(T var1, Set<? extends OpenOption> var2, FileAttribute<?> ... var3) throws IOException;

    public SecureDirectoryStream<T> newDirectoryStream(T var1, LinkOption ... var2) throws IOException;
}

