/*
 * Decompiled with CFR 0.145.
 */
package sun.nio.fs;

import java.io.IOException;
import java.nio.file.ClosedWatchServiceException;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

abstract class AbstractPoller
implements Runnable {
    private final LinkedList<Request> requestList = new LinkedList();
    private boolean shutdown = false;

    protected AbstractPoller() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private Object invoke(RequestType object, Object ... object2) throws IOException {
        object2 = new Request((RequestType)((Object)object), (Object[])object2);
        object = this.requestList;
        synchronized (object) {
            if (this.shutdown) {
                object2 = new ClosedWatchServiceException();
                throw object2;
            }
            this.requestList.add((Request)object2);
        }
        this.wakeup();
        object = ((Request)object2).awaitResult();
        if (object instanceof RuntimeException) {
            throw (RuntimeException)object;
        }
        if (!(object instanceof IOException)) {
            return object;
        }
        throw (IOException)object;
    }

    final void cancel(WatchKey watchKey) {
        try {
            this.invoke(RequestType.CANCEL, watchKey);
            return;
        }
        catch (IOException iOException) {
            throw new AssertionError((Object)iOException.getMessage());
        }
    }

    final void close() throws IOException {
        this.invoke(RequestType.CLOSE, new Object[0]);
    }

    abstract void implCancelKey(WatchKey var1);

    abstract void implCloseAll();

    abstract Object implRegister(Path var1, Set<? extends WatchEvent.Kind<?>> var2, WatchEvent.Modifier ... var3);

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    boolean processRequests() {
        LinkedList<Request> linkedList = this.requestList;
        synchronized (linkedList) {
            Request request;
            while ((request = this.requestList.poll()) != null) {
                int n;
                Object object;
                if (this.shutdown) {
                    object = new ClosedWatchServiceException();
                    request.release(object);
                }
                if ((n = 2.$SwitchMap$sun$nio$fs$AbstractPoller$RequestType[request.type().ordinal()]) != 1) {
                    if (n != 2) {
                        if (n != 3) {
                            object = new IOException("request not recognized");
                            request.release(object);
                            continue;
                        }
                        this.implCloseAll();
                        request.release(null);
                        this.shutdown = true;
                        continue;
                    }
                    this.implCancelKey((WatchKey)request.parameters()[0]);
                    request.release(null);
                    continue;
                }
                object = request.parameters();
                request.release(this.implRegister((Path)object[0], (Set)object[1], (WatchEvent.Modifier[])object[2]));
            }
            return this.shutdown;
        }
    }

    final WatchKey register(Path path, WatchEvent.Kind<?>[] arrkind, WatchEvent.Modifier ... arrmodifier) throws IOException {
        if (path != null) {
            HashSet<WatchEvent.Kind<?>> hashSet = new HashSet<WatchEvent.Kind<?>>(arrkind.length);
            for (WatchEvent.Kind<?> kind : arrkind) {
                if (kind != StandardWatchEventKinds.ENTRY_CREATE && kind != StandardWatchEventKinds.ENTRY_MODIFY && kind != StandardWatchEventKinds.ENTRY_DELETE) {
                    if (kind == StandardWatchEventKinds.OVERFLOW) continue;
                    if (kind == null) {
                        throw new NullPointerException("An element in event set is 'null'");
                    }
                    throw new UnsupportedOperationException(kind.name());
                }
                hashSet.add(kind);
            }
            if (!hashSet.isEmpty()) {
                return (WatchKey)this.invoke(RequestType.REGISTER, path, hashSet, arrmodifier);
            }
            throw new IllegalArgumentException("No events to register");
        }
        throw new NullPointerException();
    }

    public void start() {
        AccessController.doPrivileged(new PrivilegedAction<Object>(){

            @Override
            public Object run() {
                Thread thread = new Thread(this);
                thread.setDaemon(true);
                thread.start();
                return null;
            }
        });
    }

    abstract void wakeup() throws IOException;

    private static class Request {
        private boolean completed = false;
        private final Object[] params;
        private Object result = null;
        private final RequestType type;

        Request(RequestType requestType, Object ... arrobject) {
            this.type = requestType;
            this.params = arrobject;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        Object awaitResult() {
            boolean bl = false;
            synchronized (this) {
                boolean bl2;
                while (!(bl2 = this.completed)) {
                    try {
                        this.wait();
                    }
                    catch (InterruptedException interruptedException) {
                        bl = true;
                    }
                }
                if (!bl) return this.result;
                Thread.currentThread().interrupt();
                return this.result;
            }
        }

        Object[] parameters() {
            return this.params;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        void release(Object object) {
            synchronized (this) {
                this.completed = true;
                this.result = object;
                this.notifyAll();
                return;
            }
        }

        RequestType type() {
            return this.type;
        }
    }

    private static enum RequestType {
        REGISTER,
        CANCEL,
        CLOSE;
        
    }

}

