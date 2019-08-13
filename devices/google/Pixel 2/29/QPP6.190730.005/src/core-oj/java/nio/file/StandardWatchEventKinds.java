/*
 * Decompiled with CFR 0.145.
 */
package java.nio.file;

import java.nio.file.Path;
import java.nio.file.WatchEvent;

public final class StandardWatchEventKinds {
    public static final WatchEvent.Kind<Path> ENTRY_CREATE;
    public static final WatchEvent.Kind<Path> ENTRY_DELETE;
    public static final WatchEvent.Kind<Path> ENTRY_MODIFY;
    public static final WatchEvent.Kind<Object> OVERFLOW;

    static {
        OVERFLOW = new StdWatchEventKind<Object>("OVERFLOW", Object.class);
        ENTRY_CREATE = new StdWatchEventKind<Path>("ENTRY_CREATE", Path.class);
        ENTRY_DELETE = new StdWatchEventKind<Path>("ENTRY_DELETE", Path.class);
        ENTRY_MODIFY = new StdWatchEventKind<Path>("ENTRY_MODIFY", Path.class);
    }

    private StandardWatchEventKinds() {
    }

    private static class StdWatchEventKind<T>
    implements WatchEvent.Kind<T> {
        private final String name;
        private final Class<T> type;

        StdWatchEventKind(String string, Class<T> class_) {
            this.name = string;
            this.type = class_;
        }

        @Override
        public String name() {
            return this.name;
        }

        public String toString() {
            return this.name;
        }

        @Override
        public Class<T> type() {
            return this.type;
        }
    }

}

