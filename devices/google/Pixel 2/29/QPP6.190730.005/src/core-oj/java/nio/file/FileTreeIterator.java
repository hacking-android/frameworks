/*
 * Decompiled with CFR 0.145.
 */
package java.nio.file;

import java.io.Closeable;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.FileTreeWalker;
import java.nio.file.FileVisitOption;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

class FileTreeIterator
implements Iterator<FileTreeWalker.Event>,
Closeable {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private FileTreeWalker.Event next;
    private final FileTreeWalker walker;

    FileTreeIterator(Path object, int n, FileVisitOption ... arrfileVisitOption) throws IOException {
        this.walker = new FileTreeWalker(Arrays.asList(arrfileVisitOption), n);
        this.next = this.walker.walk((Path)object);
        object = this.next.ioeException();
        if (object == null) {
            return;
        }
        throw object;
    }

    private void fetchNextIfNeeded() {
        if (this.next == null) {
            FileTreeWalker.Event event = this.walker.next();
            while (event != null) {
                IOException iOException = event.ioeException();
                if (iOException == null) {
                    if (event.type() != FileTreeWalker.EventType.END_DIRECTORY) {
                        this.next = event;
                        return;
                    }
                    event = this.walker.next();
                    continue;
                }
                throw new UncheckedIOException(iOException);
            }
        }
    }

    @Override
    public void close() {
        this.walker.close();
    }

    @Override
    public boolean hasNext() {
        if (this.walker.isOpen()) {
            this.fetchNextIfNeeded();
            boolean bl = this.next != null;
            return bl;
        }
        throw new IllegalStateException();
    }

    @Override
    public FileTreeWalker.Event next() {
        if (this.walker.isOpen()) {
            this.fetchNextIfNeeded();
            if (this.next != null) {
                FileTreeWalker.Event event = this.next;
                this.next = null;
                return event;
            }
            throw new NoSuchElementException();
        }
        throw new IllegalStateException();
    }
}

