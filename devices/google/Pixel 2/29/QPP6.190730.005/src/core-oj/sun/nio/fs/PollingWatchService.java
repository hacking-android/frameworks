/*
 * Decompiled with CFR 0.145.
 */
package sun.nio.fs;

import com.sun.nio.file.SensitivityWatchEventModifier;
import java.io.IOException;
import java.nio.file.ClosedWatchServiceException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import sun.nio.fs.AbstractWatchKey;
import sun.nio.fs.AbstractWatchService;

class PollingWatchService
extends AbstractWatchService {
    private final Map<Object, PollingWatchKey> map = new HashMap<Object, PollingWatchKey>();
    private final ScheduledExecutorService scheduledExecutor = Executors.newSingleThreadScheduledExecutor(new ThreadFactory(){

        @Override
        public Thread newThread(Runnable runnable) {
            runnable = new Thread(runnable);
            ((Thread)runnable).setDaemon(true);
            return runnable;
        }
    });

    PollingWatchService() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private PollingWatchKey doPrivilegedRegister(Path object, Set<? extends WatchEvent.Kind<?>> set, SensitivityWatchEventModifier sensitivityWatchEventModifier) throws IOException {
        Object object2 = Files.readAttributes((Path)object, BasicFileAttributes.class, new LinkOption[0]);
        if (!object2.isDirectory()) {
            throw new NotDirectoryException(object.toString());
        }
        Object object3 = object2.fileKey();
        if (object3 == null) {
            throw new AssertionError((Object)"File keys must be supported");
        }
        Object object4 = this.closeLock();
        synchronized (object4) {
            if (!this.isOpen()) {
                object = new ClosedWatchServiceException();
                throw object;
            }
            Map<Object, PollingWatchKey> map = this.map;
            synchronized (map) {
                object2 = this.map.get(object3);
                if (object2 == null) {
                    object = object2 = new PollingWatchKey(this, (Path)object, this, object3);
                    this.map.put(object3, (PollingWatchKey)object);
                } else {
                    ((PollingWatchKey)object2).disable();
                    object = object2;
                }
            }
            ((PollingWatchKey)object).enable(set, sensitivityWatchEventModifier.sensitivityValueInSeconds());
            return object;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    void implClose() throws IOException {
        Map<Object, PollingWatchKey> map = this.map;
        synchronized (map) {
            Iterator<Map.Entry<Object, PollingWatchKey>> iterator = this.map.entrySet().iterator();
            do {
                if (!iterator.hasNext()) {
                    this.map.clear();
                    // MONITOREXIT [2, 3, 4] lbl7 : MonitorExitStatement: MONITOREXIT : var1_1
                    AccessController.doPrivileged(new PrivilegedAction<Void>(){

                        @Override
                        public Void run() {
                            PollingWatchService.this.scheduledExecutor.shutdown();
                            return null;
                        }
                    });
                    return;
                }
                PollingWatchKey pollingWatchKey = iterator.next().getValue();
                pollingWatchKey.disable();
                pollingWatchKey.invalidate();
            } while (true);
        }
    }

    @Override
    WatchKey register(Path object, WatchEvent.Kind<?>[] object2, WatchEvent.Modifier ... object3) throws IOException {
        WatchEvent.Kind<?> kind;
        int n;
        HashSet<Object> hashSet = new HashSet<Object>(((WatchEvent.Kind<?>[])object2).length);
        int n2 = ((WatchEvent.Kind<?>[])object2).length;
        int n3 = 0;
        for (n = 0; n < n2; ++n) {
            kind = object2[n];
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
            kind = SensitivityWatchEventModifier.MEDIUM;
            object2 = kind;
            if (((WatchEvent.Modifier[])object3).length > 0) {
                n2 = ((WatchEvent.Modifier[])object3).length;
                object2 = kind;
                for (n = n3; n < n2; ++n) {
                    object2 = object3[n];
                    if (object2 != null) {
                        if (object2 instanceof SensitivityWatchEventModifier) {
                            object2 = (SensitivityWatchEventModifier)object2;
                            continue;
                        }
                        throw new UnsupportedOperationException("Modifier not supported");
                    }
                    throw new NullPointerException();
                }
            }
            if (this.isOpen()) {
                try {
                    object3 = new PrivilegedExceptionAction<PollingWatchKey>((Path)object, hashSet, (SensitivityWatchEventModifier)object2){
                        final /* synthetic */ Set val$eventSet;
                        final /* synthetic */ Path val$path;
                        final /* synthetic */ SensitivityWatchEventModifier val$s;
                        {
                            this.val$path = path;
                            this.val$eventSet = set;
                            this.val$s = sensitivityWatchEventModifier;
                        }

                        @Override
                        public PollingWatchKey run() throws IOException {
                            return PollingWatchService.this.doPrivilegedRegister(this.val$path, this.val$eventSet, this.val$s);
                        }
                    };
                    object = (WatchKey)AccessController.doPrivileged(object3);
                    return object;
                }
                catch (PrivilegedActionException privilegedActionException) {
                    object = privilegedActionException.getCause();
                    if (object != null && object instanceof IOException) {
                        throw (IOException)object;
                    }
                    throw new AssertionError(privilegedActionException);
                }
            }
            throw new ClosedWatchServiceException();
        }
        throw new IllegalArgumentException("No events to register");
    }

    private static class CacheEntry {
        private long lastModified;
        private int lastTickCount;

        CacheEntry(long l, int n) {
            this.lastModified = l;
            this.lastTickCount = n;
        }

        long lastModified() {
            return this.lastModified;
        }

        int lastTickCount() {
            return this.lastTickCount;
        }

        void update(long l, int n) {
            this.lastModified = l;
            this.lastTickCount = n;
        }
    }

    private class PollingWatchKey
    extends AbstractWatchKey {
        private Map<Path, CacheEntry> entries;
        private Set<? extends WatchEvent.Kind<?>> events;
        private final Object fileKey;
        private ScheduledFuture<?> poller;
        final /* synthetic */ PollingWatchService this$0;
        private int tickCount;
        private volatile boolean valid;

        /*
         * Exception decompiling
         */
        PollingWatchKey(PollingWatchService var1_1, Path var2_4, PollingWatchService var3_6, Object var4_8) throws IOException {
            // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
            // org.benf.cfr.reader.util.ConfusedCFRException: Started 3 blocks at once
            // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:404)
            // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:482)
            // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
            // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
            // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
            // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
            // org.benf.cfr.reader.entities.ClassFile.analyseInnerClassesPass1(ClassFile.java:778)
            // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:886)
            // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
            // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
            // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
            // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
            // org.benf.cfr.reader.Main.main(Main.java:48)
            throw new IllegalStateException("Decompilation failed");
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void cancel() {
            this.valid = false;
            Map map = this.this$0.map;
            synchronized (map) {
                this.this$0.map.remove(this.fileKey());
            }
            this.disable();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        void disable() {
            synchronized (this) {
                if (this.poller != null) {
                    this.poller.cancel(false);
                }
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        void enable(Set<? extends WatchEvent.Kind<?>> runnable, long l) {
            synchronized (this) {
                this.events = runnable;
                runnable = new Runnable(){

                    @Override
                    public void run() {
                        PollingWatchKey.this.poll();
                    }
                };
                this.poller = this.this$0.scheduledExecutor.scheduleAtFixedRate(runnable, l, l, TimeUnit.SECONDS);
                return;
            }
        }

        Object fileKey() {
            return this.fileKey;
        }

        void invalidate() {
            this.valid = false;
        }

        @Override
        public boolean isValid() {
            return this.valid;
        }

        /*
         * WARNING - void declaration
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        void poll() {
            synchronized (this) {
                Iterator<Object> iterator;
                Object object;
                block26 : {
                    void var3_8;
                    block25 : {
                        boolean bl = this.valid;
                        if (!bl) {
                            return;
                        }
                        ++this.tickCount;
                        try {
                            object = Files.newDirectoryStream(this.watchable());
                        }
                        catch (IOException iOException) {
                            this.cancel();
                            this.signal();
                            return;
                        }
                        try {
                            iterator = object.iterator();
                        }
                        catch (Throwable throwable) {
                            // empty catch block
                            break block25;
                        }
                        catch (DirectoryIteratorException directoryIteratorException) {
                            // empty catch block
                            break block26;
                        }
                        while (bl = iterator.hasNext()) {
                            try {
                                Path path;
                                Object object2;
                                long l;
                                block27 : {
                                    path = iterator.next();
                                    try {
                                        l = Files.getLastModifiedTime(path, LinkOption.NOFOLLOW_LINKS).toMillis();
                                        object2 = this.entries.get(path.getFileName());
                                        if (object2 != null) break block27;
                                        object2 = this.entries;
                                        Path path2 = path.getFileName();
                                        CacheEntry cacheEntry = new CacheEntry(l, this.tickCount);
                                        object2.put(path2, cacheEntry);
                                    }
                                    catch (IOException iOException) {
                                        continue;
                                    }
                                    if (this.events.contains(StandardWatchEventKinds.ENTRY_CREATE)) {
                                        this.signalEvent(StandardWatchEventKinds.ENTRY_CREATE, path.getFileName());
                                        continue;
                                    }
                                    if (!this.events.contains(StandardWatchEventKinds.ENTRY_MODIFY)) continue;
                                    this.signalEvent(StandardWatchEventKinds.ENTRY_MODIFY, path.getFileName());
                                    continue;
                                }
                                if (((CacheEntry)object2).lastModified != l && this.events.contains(StandardWatchEventKinds.ENTRY_MODIFY)) {
                                    this.signalEvent(StandardWatchEventKinds.ENTRY_MODIFY, path.getFileName());
                                }
                                ((CacheEntry)object2).update(l, this.tickCount);
                            }
                            catch (Throwable throwable) {
                                break block25;
                            }
                            catch (DirectoryIteratorException directoryIteratorException) {
                                break block26;
                            }
                        }
                        object.close();
                    }
                    try {
                        object.close();
                        throw var3_8;
                    }
                    catch (IOException iOException) {
                        // empty catch block
                    }
                    throw var3_8;
                }
                try {
                    object.close();
                }
                catch (IOException iOException) {
                    // empty catch block
                }
                iterator = this.entries.entrySet().iterator();
                while (iterator.hasNext()) {
                    object = (Map.Entry)iterator.next();
                    if (((CacheEntry)object.getValue()).lastTickCount() == this.tickCount) continue;
                    object = (Path)object.getKey();
                    iterator.remove();
                    if (!this.events.contains(StandardWatchEventKinds.ENTRY_DELETE)) continue;
                    this.signalEvent(StandardWatchEventKinds.ENTRY_DELETE, object);
                }
                return;
            }
        }

    }

}

