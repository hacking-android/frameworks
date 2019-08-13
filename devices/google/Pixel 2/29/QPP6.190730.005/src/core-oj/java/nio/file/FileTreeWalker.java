/*
 * Decompiled with CFR 0.145.
 */
package java.nio.file;

import java.io.Closeable;
import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystemLoopException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Iterator;
import sun.nio.fs.BasicFileAttributesHolder;

class FileTreeWalker
implements Closeable {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private boolean closed;
    private final boolean followLinks;
    private final LinkOption[] linkOptions;
    private final int maxDepth;
    private final ArrayDeque<DirectoryNode> stack = new ArrayDeque();

    FileTreeWalker(Collection<FileVisitOption> arrlinkOption, int n) {
        boolean bl = false;
        arrlinkOption = arrlinkOption.iterator();
        while (arrlinkOption.hasNext()) {
            FileVisitOption fileVisitOption = (FileVisitOption)((Object)arrlinkOption.next());
            if (1.$SwitchMap$java$nio$file$FileVisitOption[fileVisitOption.ordinal()] == 1) {
                bl = true;
                continue;
            }
            throw new AssertionError((Object)"Should not get here");
        }
        if (n >= 0) {
            this.followLinks = bl;
            arrlinkOption = bl ? new LinkOption[]{} : new LinkOption[]{LinkOption.NOFOLLOW_LINKS};
            this.linkOptions = arrlinkOption;
            this.maxDepth = n;
            return;
        }
        throw new IllegalArgumentException("'maxDepth' is negative");
    }

    private BasicFileAttributes getAttributes(Path object, boolean bl) throws IOException {
        IOException iOException2;
        block3 : {
            BasicFileAttributes basicFileAttributes;
            if (bl && object instanceof BasicFileAttributesHolder && System.getSecurityManager() == null && (basicFileAttributes = ((BasicFileAttributesHolder)object).get()) != null && (!this.followLinks || !basicFileAttributes.isSymbolicLink())) {
                return basicFileAttributes;
            }
            try {
                basicFileAttributes = Files.readAttributes((Path)object, BasicFileAttributes.class, this.linkOptions);
                object = basicFileAttributes;
            }
            catch (IOException iOException2) {
                if (!this.followLinks) break block3;
                object = Files.readAttributes((Path)object, BasicFileAttributes.class, LinkOption.NOFOLLOW_LINKS);
            }
            return object;
        }
        throw iOException2;
    }

    private Event visit(Path path, boolean bl, boolean bl2) {
        BasicFileAttributes basicFileAttributes;
        try {
            basicFileAttributes = this.getAttributes(path, bl2);
        }
        catch (SecurityException securityException) {
            if (bl) {
                return null;
            }
            throw securityException;
        }
        catch (IOException iOException) {
            return new Event(EventType.ENTRY, path, iOException);
        }
        if (this.stack.size() < this.maxDepth && basicFileAttributes.isDirectory()) {
            DirectoryStream<Path> directoryStream;
            if (this.followLinks && this.wouldLoop(path, basicFileAttributes.fileKey())) {
                return new Event(EventType.ENTRY, path, new FileSystemLoopException(path.toString()));
            }
            try {
                directoryStream = Files.newDirectoryStream(path);
            }
            catch (SecurityException securityException) {
                if (bl) {
                    return null;
                }
                throw securityException;
            }
            catch (IOException iOException) {
                return new Event(EventType.ENTRY, path, iOException);
            }
            this.stack.push(new DirectoryNode(path, basicFileAttributes.fileKey(), directoryStream));
            return new Event(EventType.START_DIRECTORY, path, basicFileAttributes);
        }
        return new Event(EventType.ENTRY, path, basicFileAttributes);
    }

    private boolean wouldLoop(Path path, Object object) {
        for (DirectoryNode directoryNode : this.stack) {
            Object object2 = directoryNode.key();
            if (object != null && object2 != null) {
                if (!object.equals(object2)) continue;
                return true;
            }
            try {
                boolean bl = Files.isSameFile(path, directoryNode.directory());
                if (!bl) continue;
                return true;
            }
            catch (IOException | SecurityException exception) {
            }
        }
        return false;
    }

    @Override
    public void close() {
        if (!this.closed) {
            while (!this.stack.isEmpty()) {
                this.pop();
            }
            this.closed = true;
        }
    }

    boolean isOpen() {
        return this.closed ^ true;
    }

    Event next() {
        Iterator<Path> iterator;
        Object object;
        DirectoryNode directoryNode = this.stack.peek();
        if (directoryNode == null) {
            return null;
        }
        do {
            Iterator<Path> iterator2 = null;
            Event event = null;
            Event event2 = null;
            iterator = iterator2;
            object = event2;
            if (!directoryNode.skipped()) {
                iterator = directoryNode.iterator();
                object = event;
                try {
                    if (iterator.hasNext()) {
                        object = iterator.next();
                    }
                    iterator = object;
                    object = event2;
                }
                catch (DirectoryIteratorException directoryIteratorException) {
                    object = directoryIteratorException.getCause();
                    iterator = iterator2;
                }
            }
            if (iterator != null) continue;
            try {
                directoryNode.stream().close();
            }
            catch (IOException iOException) {
                if (object != null) {
                    object = iOException;
                }
                ((Throwable)object).addSuppressed(iOException);
            }
            this.stack.pop();
            return new Event(EventType.END_DIRECTORY, directoryNode.directory(), (IOException)object);
        } while ((object = this.visit((Path)((Object)iterator), true, true)) == null);
        return object;
    }

    void pop() {
        if (!this.stack.isEmpty()) {
            DirectoryNode directoryNode = this.stack.pop();
            try {
                directoryNode.stream().close();
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
    }

    void skipRemainingSiblings() {
        if (!this.stack.isEmpty()) {
            this.stack.peek().skip();
        }
    }

    Event walk(Path object) {
        if (!this.closed) {
            object = this.visit((Path)object, false, false);
            return object;
        }
        throw new IllegalStateException("Closed");
    }

    private static class DirectoryNode {
        private final Path dir;
        private final Iterator<Path> iterator;
        private final Object key;
        private boolean skipped;
        private final DirectoryStream<Path> stream;

        DirectoryNode(Path path, Object object, DirectoryStream<Path> directoryStream) {
            this.dir = path;
            this.key = object;
            this.stream = directoryStream;
            this.iterator = directoryStream.iterator();
        }

        Path directory() {
            return this.dir;
        }

        Iterator<Path> iterator() {
            return this.iterator;
        }

        Object key() {
            return this.key;
        }

        void skip() {
            this.skipped = true;
        }

        boolean skipped() {
            return this.skipped;
        }

        DirectoryStream<Path> stream() {
            return this.stream;
        }
    }

    static class Event {
        private final BasicFileAttributes attrs;
        private final Path file;
        private final IOException ioe;
        private final EventType type;

        Event(EventType eventType, Path path, IOException iOException) {
            this(eventType, path, null, iOException);
        }

        Event(EventType eventType, Path path, BasicFileAttributes basicFileAttributes) {
            this(eventType, path, basicFileAttributes, null);
        }

        private Event(EventType eventType, Path path, BasicFileAttributes basicFileAttributes, IOException iOException) {
            this.type = eventType;
            this.file = path;
            this.attrs = basicFileAttributes;
            this.ioe = iOException;
        }

        BasicFileAttributes attributes() {
            return this.attrs;
        }

        Path file() {
            return this.file;
        }

        IOException ioeException() {
            return this.ioe;
        }

        EventType type() {
            return this.type;
        }
    }

    static enum EventType {
        START_DIRECTORY,
        END_DIRECTORY,
        ENTRY;
        
    }

}

