/*
 * Decompiled with CFR 0.145.
 */
package sun.nio.fs;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Iterator;
import java.util.NoSuchElementException;

abstract class AbstractPath
implements Path {
    protected AbstractPath() {
    }

    @Override
    public final boolean endsWith(String string) {
        return this.endsWith(this.getFileSystem().getPath(string, new String[0]));
    }

    @Override
    public final Iterator<Path> iterator() {
        return new Iterator<Path>(){
            private int i = 0;

            @Override
            public boolean hasNext() {
                boolean bl = this.i < AbstractPath.this.getNameCount();
                return bl;
            }

            @Override
            public Path next() {
                if (this.i < AbstractPath.this.getNameCount()) {
                    Path path = AbstractPath.this.getName(this.i);
                    ++this.i;
                    return path;
                }
                throw new NoSuchElementException();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    @Override
    public final WatchKey register(WatchService watchService, WatchEvent.Kind<?> ... arrkind) throws IOException {
        return this.register(watchService, arrkind, new WatchEvent.Modifier[0]);
    }

    @Override
    public final Path resolve(String string) {
        return this.resolve(this.getFileSystem().getPath(string, new String[0]));
    }

    @Override
    public final Path resolveSibling(String string) {
        return this.resolveSibling(this.getFileSystem().getPath(string, new String[0]));
    }

    @Override
    public final Path resolveSibling(Path path) {
        if (path != null) {
            Path path2 = this.getParent();
            if (path2 != null) {
                path = path2.resolve(path);
            }
            return path;
        }
        throw new NullPointerException();
    }

    @Override
    public final boolean startsWith(String string) {
        return this.startsWith(this.getFileSystem().getPath(string, new String[0]));
    }

    @Override
    public final File toFile() {
        return new File(this.toString());
    }

}

