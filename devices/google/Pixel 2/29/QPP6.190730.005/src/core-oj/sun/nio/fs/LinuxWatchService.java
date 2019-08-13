/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.optimization.ReachabilitySensitive
 *  dalvik.system.CloseGuard
 */
package sun.nio.fs;

import com.sun.nio.file.SensitivityWatchEventModifier;
import dalvik.annotation.optimization.ReachabilitySensitive;
import dalvik.system.CloseGuard;
import java.io.IOException;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import sun.misc.Unsafe;
import sun.nio.fs.AbstractPoller;
import sun.nio.fs.AbstractWatchKey;
import sun.nio.fs.AbstractWatchService;
import sun.nio.fs.NativeBuffer;
import sun.nio.fs.NativeBuffers;
import sun.nio.fs.UnixConstants;
import sun.nio.fs.UnixException;
import sun.nio.fs.UnixFileAttributes;
import sun.nio.fs.UnixFileSystem;
import sun.nio.fs.UnixNativeDispatcher;
import sun.nio.fs.UnixPath;

class LinuxWatchService
extends AbstractWatchService {
    private static final Unsafe unsafe = Unsafe.getUnsafe();
    private final Poller poller;

    LinuxWatchService(UnixFileSystem unixFileSystem) throws IOException {
        int n;
        try {
            n = LinuxWatchService.inotifyInit();
        }
        catch (UnixException unixException) {
            String string = unixException.errno() == UnixConstants.EMFILE ? "User limit of inotify instances reached or too many open files" : unixException.errorString();
            throw new IOException(string);
        }
        int[] arrn = new int[2];
        try {
            LinuxWatchService.configureBlocking(n, false);
            LinuxWatchService.socketpair(arrn);
            LinuxWatchService.configureBlocking(arrn[0], false);
            this.poller = new Poller(unixFileSystem, this, n, arrn);
        }
        catch (UnixException unixException) {
            UnixNativeDispatcher.close(n);
            throw new IOException(unixException.errorString());
        }
        this.poller.start();
    }

    static /* synthetic */ int access$600(int n, int n2) throws UnixException {
        return LinuxWatchService.poll(n, n2);
    }

    private static native void configureBlocking(int var0, boolean var1) throws UnixException;

    private static native int[] eventOffsets();

    private static native int eventSize();

    private static native int inotifyAddWatch(int var0, long var1, int var3) throws UnixException;

    private static native int inotifyInit() throws UnixException;

    private static native void inotifyRmWatch(int var0, int var1) throws UnixException;

    private static native int poll(int var0, int var1) throws UnixException;

    private static native void socketpair(int[] var0) throws UnixException;

    @Override
    void implClose() throws IOException {
        this.poller.close();
    }

    @Override
    WatchKey register(Path path, WatchEvent.Kind<?>[] arrkind, WatchEvent.Modifier ... arrmodifier) throws IOException {
        return this.poller.register(path, arrkind, arrmodifier);
    }

    private static class LinuxWatchKey
    extends AbstractWatchKey {
        private final int ifd;
        private volatile int wd;

        LinuxWatchKey(UnixPath unixPath, LinuxWatchService linuxWatchService, int n, int n2) {
            super(unixPath, linuxWatchService);
            this.ifd = n;
            this.wd = n2;
        }

        @Override
        public void cancel() {
            if (this.isValid()) {
                ((LinuxWatchService)this.watcher()).poller.cancel(this);
            }
        }

        int descriptor() {
            return this.wd;
        }

        void invalidate(boolean bl) {
            if (bl) {
                try {
                    LinuxWatchService.inotifyRmWatch(this.ifd, this.wd);
                }
                catch (UnixException unixException) {
                    // empty catch block
                }
            }
            this.wd = -1;
        }

        @Override
        public boolean isValid() {
            boolean bl = this.wd != -1;
            return bl;
        }
    }

    private static class Poller
    extends AbstractPoller {
        private static final int BUFFER_SIZE = 8192;
        private static final int IN_ATTRIB = 4;
        private static final int IN_CREATE = 256;
        private static final int IN_DELETE = 512;
        private static final int IN_IGNORED = 32768;
        private static final int IN_MODIFY = 2;
        private static final int IN_MOVED_FROM = 64;
        private static final int IN_MOVED_TO = 128;
        private static final int IN_Q_OVERFLOW = 16384;
        private static final int IN_UNMOUNT = 8192;
        private static final int OFFSETOF_LEN;
        private static final int OFFSETOF_MASK;
        private static final int OFFSETOF_NAME;
        private static final int OFFSETOF_WD;
        private static final int SIZEOF_INOTIFY_EVENT;
        private static final int[] offsets;
        private final long address;
        private final UnixFileSystem fs;
        @ReachabilitySensitive
        private final CloseGuard guard = CloseGuard.get();
        private final int ifd;
        private final int[] socketpair;
        private final LinuxWatchService watcher;
        private final Map<Integer, LinuxWatchKey> wdToKey;

        static {
            SIZEOF_INOTIFY_EVENT = LinuxWatchService.eventSize();
            int[] arrn = offsets = LinuxWatchService.eventOffsets();
            OFFSETOF_WD = arrn[0];
            OFFSETOF_MASK = arrn[1];
            OFFSETOF_LEN = arrn[3];
            OFFSETOF_NAME = arrn[4];
        }

        Poller(UnixFileSystem unixFileSystem, LinuxWatchService linuxWatchService, int n, int[] arrn) {
            this.fs = unixFileSystem;
            this.watcher = linuxWatchService;
            this.ifd = n;
            this.socketpair = arrn;
            this.wdToKey = new HashMap<Integer, LinuxWatchKey>();
            this.address = unsafe.allocateMemory(8192L);
            this.guard.open("close");
        }

        private WatchEvent.Kind<?> maskToEventKind(int n) {
            if ((n & 2) > 0) {
                return StandardWatchEventKinds.ENTRY_MODIFY;
            }
            if ((n & 4) > 0) {
                return StandardWatchEventKinds.ENTRY_MODIFY;
            }
            if ((n & 256) > 0) {
                return StandardWatchEventKinds.ENTRY_CREATE;
            }
            if ((n & 128) > 0) {
                return StandardWatchEventKinds.ENTRY_CREATE;
            }
            if ((n & 512) > 0) {
                return StandardWatchEventKinds.ENTRY_DELETE;
            }
            if ((n & 64) > 0) {
                return StandardWatchEventKinds.ENTRY_DELETE;
            }
            return null;
        }

        private void processEvent(int n, int n2, UnixPath object) {
            if ((n2 & 16384) > 0) {
                object = this.wdToKey.entrySet().iterator();
                while (object.hasNext()) {
                    ((LinuxWatchKey)((Map.Entry)object.next()).getValue()).signalEvent(StandardWatchEventKinds.OVERFLOW, null);
                }
                return;
            }
            LinuxWatchKey linuxWatchKey = this.wdToKey.get(n);
            if (linuxWatchKey == null) {
                return;
            }
            if ((32768 & n2) > 0) {
                this.wdToKey.remove(n);
                linuxWatchKey.invalidate(false);
                linuxWatchKey.signal();
                return;
            }
            if (object == null) {
                return;
            }
            WatchEvent.Kind<?> kind = this.maskToEventKind(n2);
            if (kind != null) {
                linuxWatchKey.signalEvent(kind, object);
            }
        }

        protected void finalize() throws Throwable {
            try {
                if (this.guard != null) {
                    this.guard.warnIfOpen();
                }
                this.close();
                return;
            }
            finally {
                Object.super.finalize();
            }
        }

        @Override
        void implCancelKey(WatchKey watchKey) {
            if (((LinuxWatchKey)(watchKey = (LinuxWatchKey)watchKey)).isValid()) {
                this.wdToKey.remove(((LinuxWatchKey)watchKey).descriptor());
                ((LinuxWatchKey)watchKey).invalidate(true);
            }
        }

        @Override
        void implCloseAll() {
            this.guard.close();
            Iterator<Map.Entry<Integer, LinuxWatchKey>> iterator = this.wdToKey.entrySet().iterator();
            while (iterator.hasNext()) {
                iterator.next().getValue().invalidate(true);
            }
            this.wdToKey.clear();
            unsafe.freeMemory(this.address);
            UnixNativeDispatcher.close(this.socketpair[0]);
            UnixNativeDispatcher.close(this.socketpair[1]);
            UnixNativeDispatcher.close(this.ifd);
        }

        /*
         * Loose catch block
         * WARNING - void declaration
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        @Override
        Object implRegister(Path object3, Set<? extends WatchEvent.Kind<?>> object2, WatchEvent.Modifier ... arrmodifier) {
            Object object;
            void var1_10;
            UnixPath unixPath;
            int n;
            block14 : {
                void var3_16;
                unixPath = (UnixPath)object3;
                n = 0;
                object = object.iterator();
                while (object.hasNext()) {
                    WatchEvent.Kind unixException = (WatchEvent.Kind)object.next();
                    if (unixException == StandardWatchEventKinds.ENTRY_CREATE) {
                        n |= 384;
                        continue;
                    }
                    if (unixException == StandardWatchEventKinds.ENTRY_DELETE) {
                        n |= 576;
                        continue;
                    }
                    if (unixException != StandardWatchEventKinds.ENTRY_MODIFY) continue;
                    n |= 6;
                }
                if (((void)var3_16).length > 0) {
                    for (void var1_5 : var3_16) {
                        if (var1_5 == null) {
                            return new NullPointerException();
                        }
                        if (!(var1_5 instanceof SensitivityWatchEventModifier)) return new UnsupportedOperationException("Modifier not supported");
                    }
                }
                UnixFileAttributes unixFileAttributes = UnixFileAttributes.get(unixPath, true);
                if (unixFileAttributes.isDirectory()) break block14;
                return new NotDirectoryException(unixPath.getPathForExceptionMessage());
            }
            NativeBuffer nativeBuffer = NativeBuffers.asNativeBuffer(unixPath.getByteArrayForSysCalls());
            {
                catch (UnixException unixException) {
                    if (unixException.errno() != UnixConstants.ENOSPC) return unixException.asIOException(unixPath);
                    return new IOException("User limit of inotify watches reached");
                }
            }
            n = LinuxWatchService.inotifyAddWatch(this.ifd, nativeBuffer.address(), n);
            nativeBuffer.release();
            Object object4 = object = this.wdToKey.get(n);
            if (object != null) return var1_10;
            LinuxWatchKey linuxWatchKey = new LinuxWatchKey(unixPath, this.watcher, this.ifd, n);
            this.wdToKey.put(n, linuxWatchKey);
            return var1_10;
            catch (Throwable throwable) {
                nativeBuffer.release();
                throw throwable;
            }
            catch (UnixException unixException) {
                return unixException.asIOException(unixPath);
            }
        }

        /*
         * Exception decompiling
         */
        @Override
        public void run() {
            // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
            // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [4[TRYBLOCK]], but top level block is 11[UNCONDITIONALDOLOOP]
            // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
            // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
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

        @Override
        void wakeup() throws IOException {
            try {
                UnixNativeDispatcher.write(this.socketpair[1], this.address, 1);
                return;
            }
            catch (UnixException unixException) {
                throw new IOException(unixException.errorString());
            }
        }
    }

}

