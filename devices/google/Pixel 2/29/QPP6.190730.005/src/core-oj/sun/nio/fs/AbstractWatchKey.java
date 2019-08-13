/*
 * Decompiled with CFR 0.145.
 */
package sun.nio.fs;

import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.Watchable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import sun.nio.fs.AbstractWatchService;

abstract class AbstractWatchKey
implements WatchKey {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static final int MAX_EVENT_LIST_SIZE = 512;
    static final Event<Object> OVERFLOW_EVENT = new Event<Object>(StandardWatchEventKinds.OVERFLOW, null);
    private final Path dir;
    private List<WatchEvent<?>> events;
    private Map<Object, WatchEvent<?>> lastModifyEvents;
    private State state;
    private final AbstractWatchService watcher;

    protected AbstractWatchKey(Path path, AbstractWatchService abstractWatchService) {
        this.watcher = abstractWatchService;
        this.dir = path;
        this.state = State.READY;
        this.events = new ArrayList();
        this.lastModifyEvents = new HashMap();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final List<WatchEvent<?>> pollEvents() {
        synchronized (this) {
            List<WatchEvent<?>> list = this.events;
            ArrayList arrayList = new ArrayList();
            this.events = arrayList;
            this.lastModifyEvents.clear();
            return list;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final boolean reset() {
        synchronized (this) {
            if (this.state != State.SIGNALLED) return this.isValid();
            if (!this.isValid()) return this.isValid();
            if (this.events.isEmpty()) {
                this.state = State.READY;
                return this.isValid();
            } else {
                this.watcher.enqueueKey(this);
            }
            return this.isValid();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    final void signal() {
        synchronized (this) {
            if (this.state == State.READY) {
                this.state = State.SIGNALLED;
                this.watcher.enqueueKey(this);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    final void signalEvent(WatchEvent.Kind<?> event, Object object) {
        boolean bl = event == StandardWatchEventKinds.ENTRY_MODIFY;
        synchronized (this) {
            int n = this.events.size();
            boolean bl2 = bl;
            WatchEvent<?> watchEvent = event;
            Object object2 = object;
            if (n > 0) {
                watchEvent = this.events.get(n - 1);
                if (!(watchEvent.kind() == StandardWatchEventKinds.OVERFLOW || event == watchEvent.kind() && Objects.equals(object, watchEvent.context()))) {
                    if (!this.lastModifyEvents.isEmpty()) {
                        if (bl) {
                            watchEvent = this.lastModifyEvents.get(object);
                            if (watchEvent != null) {
                                ((Event)watchEvent).increment();
                                return;
                            }
                        } else {
                            this.lastModifyEvents.remove(object);
                        }
                    }
                    bl2 = bl;
                    watchEvent = event;
                    object2 = object;
                    if (n >= 512) {
                        watchEvent = StandardWatchEventKinds.OVERFLOW;
                        bl2 = false;
                        object2 = null;
                    }
                } else {
                    ((Event)watchEvent).increment();
                    return;
                }
            }
            event = new Event<Object>((WatchEvent.Kind<Object>)((Object)watchEvent), object2);
            if (bl2) {
                this.lastModifyEvents.put(object2, event);
            } else if (watchEvent == StandardWatchEventKinds.OVERFLOW) {
                this.events.clear();
                this.lastModifyEvents.clear();
            }
            this.events.add(event);
            this.signal();
            return;
        }
    }

    @Override
    public Path watchable() {
        return this.dir;
    }

    final AbstractWatchService watcher() {
        return this.watcher;
    }

    private static class Event<T>
    implements WatchEvent<T> {
        private final T context;
        private int count;
        private final WatchEvent.Kind<T> kind;

        Event(WatchEvent.Kind<T> kind, T t) {
            this.kind = kind;
            this.context = t;
            this.count = 1;
        }

        @Override
        public T context() {
            return this.context;
        }

        @Override
        public int count() {
            return this.count;
        }

        void increment() {
            ++this.count;
        }

        @Override
        public WatchEvent.Kind<T> kind() {
            return this.kind;
        }
    }

    private static enum State {
        READY,
        SIGNALLED;
        
    }

}

