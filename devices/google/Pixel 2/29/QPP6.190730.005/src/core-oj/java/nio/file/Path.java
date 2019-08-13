/*
 * Decompiled with CFR 0.145.
 */
package java.nio.file;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.LinkOption;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.Watchable;
import java.util.Iterator;

public interface Path
extends Comparable<Path>,
Iterable<Path>,
Watchable {
    @Override
    public int compareTo(Path var1);

    public boolean endsWith(String var1);

    public boolean endsWith(Path var1);

    public boolean equals(Object var1);

    public Path getFileName();

    public FileSystem getFileSystem();

    public Path getName(int var1);

    public int getNameCount();

    public Path getParent();

    public Path getRoot();

    public int hashCode();

    public boolean isAbsolute();

    @Override
    public Iterator<Path> iterator();

    public Path normalize();

    @Override
    public WatchKey register(WatchService var1, WatchEvent.Kind<?> ... var2) throws IOException;

    @Override
    public WatchKey register(WatchService var1, WatchEvent.Kind<?>[] var2, WatchEvent.Modifier ... var3) throws IOException;

    public Path relativize(Path var1);

    public Path resolve(String var1);

    public Path resolve(Path var1);

    public Path resolveSibling(String var1);

    public Path resolveSibling(Path var1);

    public boolean startsWith(String var1);

    public boolean startsWith(Path var1);

    public Path subpath(int var1, int var2);

    public Path toAbsolutePath();

    public File toFile();

    public Path toRealPath(LinkOption ... var1) throws IOException;

    public String toString();

    public URI toUri();
}

