/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.optimization.ReachabilitySensitive
 *  dalvik.system.BlockGuard
 *  dalvik.system.CloseGuard
 */
package sun.nio.ch;

import dalvik.annotation.optimization.ReachabilitySensitive;
import dalvik.system.BlockGuard;
import dalvik.system.CloseGuard;
import java.io.Closeable;
import java.io.FileDescriptor;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.ClosedByInterruptException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.NonReadableChannelException;
import java.nio.channels.NonWritableChannelException;
import java.nio.channels.OverlappingFileLockException;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.channels.SelectableChannel;
import java.nio.channels.WritableByteChannel;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import sun.misc.Cleaner;
import sun.nio.ch.DirectBuffer;
import sun.nio.ch.FileDispatcher;
import sun.nio.ch.FileDispatcherImpl;
import sun.nio.ch.FileLockImpl;
import sun.nio.ch.FileLockTable;
import sun.nio.ch.IOStatus;
import sun.nio.ch.IOUtil;
import sun.nio.ch.NativeDispatcher;
import sun.nio.ch.NativeThreadSet;
import sun.nio.ch.SelChImpl;
import sun.nio.ch.SinkChannelImpl;
import sun.nio.ch.Util;
import sun.security.action.GetPropertyAction;

public class FileChannelImpl
extends FileChannel {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final long MAPPED_TRANSFER_SIZE = 0x800000L;
    private static final int MAP_PV = 2;
    private static final int MAP_RO = 0;
    private static final int MAP_RW = 1;
    private static final int TRANSFER_SIZE = 8192;
    private static final long allocationGranularity;
    private static volatile boolean fileSupported;
    private static boolean isSharedFileLockTable;
    private static volatile boolean pipeSupported;
    private static volatile boolean propertyChecked;
    private static volatile boolean transferSupported;
    private final boolean append;
    @ReachabilitySensitive
    public final FileDescriptor fd;
    private volatile FileLockTable fileLockTable;
    @ReachabilitySensitive
    private final CloseGuard guard = CloseGuard.get();
    private final FileDispatcher nd;
    private final Object parent;
    private final String path;
    private final Object positionLock = new Object();
    private final boolean readable;
    private final NativeThreadSet threads = new NativeThreadSet(2);
    private final boolean writable;

    static {
        transferSupported = true;
        pipeSupported = true;
        fileSupported = true;
        allocationGranularity = FileChannelImpl.initIDs();
    }

    private FileChannelImpl(FileDescriptor fileDescriptor, String string, boolean bl, boolean bl2, boolean bl3, Object object) {
        this.fd = fileDescriptor;
        this.readable = bl;
        this.writable = bl2;
        this.append = bl3;
        this.parent = object;
        this.path = string;
        this.nd = new FileDispatcherImpl(bl3);
        if (fileDescriptor != null && fileDescriptor.valid()) {
            this.guard.open("close");
        }
    }

    private void ensureOpen() throws IOException {
        if (this.isOpen()) {
            return;
        }
        throw new ClosedChannelException();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private FileLockTable fileLockTable() throws IOException {
        if (this.fileLockTable != null) return this.fileLockTable;
        synchronized (this) {
            if (this.fileLockTable != null) return this.fileLockTable;
            if (FileChannelImpl.isSharedFileLockTable()) {
                int n = this.threads.add();
                try {
                    this.ensureOpen();
                    this.fileLockTable = FileLockTable.newSharedFileLockTable(this, this.fd);
                }
                finally {
                    this.threads.remove(n);
                }
            } else {
                SimpleFileLockTable simpleFileLockTable = new SimpleFileLockTable();
                this.fileLockTable = simpleFileLockTable;
            }
            return this.fileLockTable;
        }
    }

    private static native long initIDs();

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static boolean isSharedFileLockTable() {
        if (propertyChecked) return isSharedFileLockTable;
        synchronized (FileChannelImpl.class) {
            if (propertyChecked) return isSharedFileLockTable;
            Object object = new GetPropertyAction("sun.nio.ch.disableSystemWideOverlappingFileLockCheck");
            boolean bl = (object = AccessController.doPrivileged(object)) == null || ((String)object).equals("false");
            isSharedFileLockTable = bl;
            propertyChecked = true;
            return isSharedFileLockTable;
        }
    }

    private native long map0(int var1, long var2, long var4) throws IOException;

    public static FileChannel open(FileDescriptor fileDescriptor, String string, boolean bl, boolean bl2, Object object) {
        return new FileChannelImpl(fileDescriptor, string, bl, bl2, false, object);
    }

    public static FileChannel open(FileDescriptor fileDescriptor, String string, boolean bl, boolean bl2, boolean bl3, Object object) {
        return new FileChannelImpl(fileDescriptor, string, bl, bl2, bl3, object);
    }

    private native long position0(FileDescriptor var1, long var2);

    private int readInternal(ByteBuffer byteBuffer, long l) throws IOException {
        int n;
        int n2;
        int n3;
        boolean bl;
        boolean bl2;
        boolean bl3;
        int n4;
        int n5;
        block8 : {
            block9 : {
                n2 = 0;
                n = 0;
                n4 = -1;
                bl3 = true;
                bl2 = true;
                bl = true;
                n3 = n2;
                n5 = n4;
                this.begin();
                n3 = n2;
                n5 = n4;
                n4 = this.threads.add();
                n3 = n2;
                n5 = n4;
                boolean bl4 = this.isOpen();
                if (bl4) break block8;
                this.threads.remove(n4);
                if (0 > 0) break block9;
                bl = false;
            }
            this.end(bl);
            return -1;
        }
        do {
            n3 = n;
            n5 = n4;
            n2 = IOUtil.read(this.fd, byteBuffer, l, this.nd);
            if (n2 != -3) break;
            n = n2;
            n3 = n2;
            n5 = n4;
            if (this.isOpen()) continue;
            break;
        } while (true);
        n3 = n2;
        n5 = n4;
        try {
            n = IOStatus.normalize(n2);
            this.threads.remove(n4);
            bl = n2 > 0 ? bl3 : false;
        }
        catch (Throwable throwable) {
            this.threads.remove(n5);
            bl = n3 > 0 ? bl2 : false;
            this.end(bl);
            throw throwable;
        }
        this.end(bl);
        return n;
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private long transferFromArbitraryChannel(ReadableByteChannel readableByteChannel, long l, long l2) throws IOException {
        void var1_8;
        ByteBuffer byteBuffer;
        block12 : {
            block13 : {
                int n2;
                long l4;
                long l3;
                byteBuffer = Util.getTemporaryDirectBuffer((int)Math.min(l2, 8192L));
                long l5 = l3 = 0L;
                try {
                    Util.erase(byteBuffer);
                    l4 = l;
                }
                catch (Throwable throwable) {
                    break block12;
                }
                catch (IOException iOException) {
                    l = l5;
                    break block13;
                }
                for (l = l3; l < l2; l += (long)n2, l4 += (long)n2) {
                    l5 = l;
                    byteBuffer.limit((int)Math.min(l2 - l, 8192L));
                    int n = readableByteChannel.read(byteBuffer);
                    if (n <= 0) break;
                    byteBuffer.flip();
                    l5 = l;
                    try {
                        n2 = this.write(byteBuffer, l4);
                        if (n2 != n) break;
                        l5 = l;
                        byteBuffer.clear();
                        continue;
                    }
                    catch (IOException iOException) {
                        l = l5;
                        break block13;
                    }
                    catch (Throwable throwable) {
                        break block12;
                    }
                    catch (IOException iOException) {
                        break block13;
                    }
                }
                Util.releaseTemporaryDirectBuffer(byteBuffer);
                return l;
            }
            if (l > 0L) {
                Util.releaseTemporaryDirectBuffer(byteBuffer);
                return l;
            }
            try {
                throw readableByteChannel;
            }
            catch (Throwable throwable) {
                // empty catch block
            }
        }
        Util.releaseTemporaryDirectBuffer(byteBuffer);
        throw var1_8;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private long transferFromFileChannel(FileChannelImpl fileChannelImpl, long l, long l2) throws IOException {
        void var1_5;
        if (!fileChannelImpl.readable) throw new NonReadableChannelException();
        Object object = fileChannelImpl.positionLock;
        // MONITORENTER : object
        long l3 = fileChannelImpl.position();
        long l4 = Math.min(l2, fileChannelImpl.size() - l3);
        long l5 = l3;
        l2 = l4;
        long l6 = l;
        l = l2;
        l2 = l6;
        while (l > 0L) {
            Throwable throwable42;
            long l7 = Math.min(l, 0x800000L);
            Object object2 = FileChannel.MapMode.READ_ONLY;
            l6 = l;
            {
                catch (Throwable throwable2) {
                    throw var1_5;
                }
            }
            object2 = fileChannelImpl.map((FileChannel.MapMode)object2, l5, l7);
            {
                catch (Throwable throwable3) {
                    throw var1_5;
                }
            }
            int n = this.write((ByteBuffer)object2, l2);
            l7 = n;
            l5 += l7;
            l = l6 - l7;
            FileChannelImpl.unmap((MappedByteBuffer)object2);
            l2 += l7;
            continue;
            {
                catch (Throwable throwable42) {
                }
                catch (IOException iOException) {}
                if (l6 == l4) throw iOException;
                FileChannelImpl.unmap((MappedByteBuffer)object2);
                break;
            }
            FileChannelImpl.unmap((MappedByteBuffer)object2);
            throw throwable42;
        }
        l = l4 - l;
        try {
            fileChannelImpl.position(l3 + l);
            // MONITOREXIT : object
            return l;
        }
        catch (Throwable throwable) {
            throw var1_5;
        }
    }

    private native long transferTo0(FileDescriptor var1, long var2, long var4, FileDescriptor var6);

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private long transferToArbitraryChannel(long l, int n, WritableByteChannel writableByteChannel) throws IOException {
        long l2;
        Throwable throwable22;
        ByteBuffer byteBuffer = Util.getTemporaryDirectBuffer(Math.min(n, 8192));
        long l3 = l2 = 0L;
        Util.erase(byteBuffer);
        long l4 = l;
        l = l2;
        do {
            l3 = l;
            if (l >= (long)n) break;
            l3 = l;
            byteBuffer.limit(Math.min((int)((long)n - l), 8192));
            l3 = l;
            int n2 = this.read(byteBuffer, l4);
            if (n2 <= 0) {
                l3 = l;
                break;
            }
            l3 = l;
            byteBuffer.flip();
            l3 = l;
            int n3 = writableByteChannel.write(byteBuffer);
            l += (long)n3;
            if (n3 != n2) {
                l3 = l;
                break;
            }
            l4 += (long)n3;
            l3 = l;
            byteBuffer.clear();
        } while (true);
        Util.releaseTemporaryDirectBuffer(byteBuffer);
        return l3;
        {
            catch (Throwable throwable22) {
            }
            catch (IOException iOException) {}
            if (l3 <= 0L) throw iOException;
            Util.releaseTemporaryDirectBuffer(byteBuffer);
            return l3;
        }
        Util.releaseTemporaryDirectBuffer(byteBuffer);
        throw throwable22;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private long transferToDirectly(long l, int n, WritableByteChannel writableByteChannel) throws IOException {
        Object object;
        if (!transferSupported) {
            return -4L;
        }
        if (writableByteChannel instanceof FileChannelImpl) {
            if (!fileSupported) {
                return -6L;
            }
            object = ((FileChannelImpl)writableByteChannel).fd;
        } else {
            if (!(writableByteChannel instanceof SelChImpl)) return -4L;
            if (writableByteChannel instanceof SinkChannelImpl && !pipeSupported) {
                return -6L;
            }
            object = (SelectableChannel)((Object)writableByteChannel);
            if (!this.nd.canTransferToDirectly((SelectableChannel)object)) {
                return -6L;
            }
            object = ((SelChImpl)((Object)writableByteChannel)).getFD();
        }
        if (object == null) {
            return -4L;
        }
        if (IOUtil.fdVal(this.fd) == IOUtil.fdVal((FileDescriptor)object)) {
            return -4L;
        }
        if (!this.nd.transferToDirectlyNeedsPositionLock()) {
            return this.transferToDirectlyInternal(l, n, writableByteChannel, (FileDescriptor)object);
        }
        Object object2 = this.positionLock;
        synchronized (object2) {
            long l2 = this.position();
            try {
                l = this.transferToDirectlyInternal(l, n, writableByteChannel, (FileDescriptor)object);
                return l;
            }
            finally {
                this.position(l2);
            }
        }
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private long transferToDirectlyInternal(long l, int n, WritableByteChannel writableByteChannel, FileDescriptor fileDescriptor) throws IOException {
        boolean bl;
        long l2;
        void var4_7;
        boolean bl2;
        int n2;
        block21 : {
            long l3;
            boolean bl5;
            boolean bl4;
            boolean bl3;
            block17 : {
                block18 : {
                    bl5 = true;
                    bl4 = true;
                    bl3 = true;
                    bl2 = true;
                    bl = true;
                    this.begin();
                    n2 = this.threads.add();
                    boolean bl6 = this.isOpen();
                    if (bl6) break block17;
                    this.threads.remove(n2);
                    if (-1L > -1L) break block18;
                    bl = false;
                }
                this.end(bl);
                return -1L;
            }
            BlockGuard.getThreadPolicy().onWriteToDisk();
            long l4 = -1L;
            do {
                l2 = l4;
                l3 = this.transferTo0(this.fd, l, n, fileDescriptor);
                if (l3 != -3L) break;
                l4 = l3;
                l2 = l3;
                if (this.isOpen()) continue;
                break;
            } while (true);
            if (l3 == -6L) {
                block20 : {
                    block19 : {
                        l2 = l3;
                        if (!(writableByteChannel instanceof SinkChannelImpl)) break block19;
                        l2 = l3;
                        pipeSupported = false;
                    }
                    l2 = l3;
                    if (!(writableByteChannel instanceof FileChannelImpl)) break block20;
                    l2 = l3;
                    fileSupported = false;
                }
                this.threads.remove(n2);
                bl = l3 > -1L ? bl5 : false;
                this.end(bl);
                return -6L;
            }
            if (l3 == -4L) {
                l2 = l3;
                transferSupported = false;
                this.threads.remove(n2);
                bl = l3 > -1L ? bl4 : false;
                this.end(bl);
                return -4L;
            }
            l2 = l3;
            try {
                l = IOStatus.normalize(l3);
                this.threads.remove(n2);
                bl = l3 > -1L ? bl3 : false;
            }
            catch (Throwable throwable) {}
            this.end(bl);
            return l;
            break block21;
            catch (Throwable throwable) {
                l2 = -1L;
            }
            break block21;
            catch (Throwable throwable) {
                l2 = -1L;
                n2 = -1;
            }
        }
        this.threads.remove(n2);
        bl = l2 > -1L ? bl2 : false;
        this.end(bl);
        throw var4_7;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private long transferToTrustedChannel(long l, long l2, WritableByteChannel writableByteChannel) throws IOException {
        bl = writableByteChannel instanceof SelChImpl;
        if (!(writableByteChannel instanceof FileChannelImpl) && !bl) {
            return -4L;
        }
        l3 = l2;
        l4 = l;
        l = l3;
        do {
            l3 = l;
            if (l <= 0L) return l2 - l3;
            l5 = Math.min(l, 0x800000L);
            l3 = l;
            mappedByteBuffer = this.map(FileChannel.MapMode.READ_ONLY, l4, l5);
            n = writableByteChannel.write(mappedByteBuffer);
            l -= (long)n;
            if (!bl) ** GOTO lbl28
            l3 = l;
            break;
        } while (true);
        {
            catch (Throwable throwable) {
                l3 = l;
                FileChannelImpl.unmap(mappedByteBuffer);
                l3 = l;
                throw throwable;
            }
        }
        {
            FileChannelImpl.unmap(mappedByteBuffer);
            l3 = l;
            return l2 - l3;
lbl28: // 1 sources:
            l4 += (long)n;
            l3 = l;
            FileChannelImpl.unmap(mappedByteBuffer);
            continue;
        }
        catch (IOException iOException) {
            if (l3 == l2) throw iOException;
            return l2 - l3;
        }
        catch (ClosedByInterruptException closedByInterruptException) {
            try {
                this.close();
                throw closedByInterruptException;
            }
            catch (Throwable throwable2) {
                closedByInterruptException.addSuppressed(throwable2);
            }
            throw closedByInterruptException;
        }
    }

    private static void unmap(MappedByteBuffer object) {
        if ((object = ((DirectBuffer)object).cleaner()) != null) {
            ((Cleaner)object).clean();
        }
    }

    private static native int unmap0(long var0, long var2);

    private int writeInternal(ByteBuffer byteBuffer, long l) throws IOException {
        int n;
        int n2;
        int n3;
        boolean bl;
        boolean bl2;
        boolean bl3;
        int n4;
        int n5;
        block8 : {
            block9 : {
                n2 = 0;
                n = 0;
                n4 = -1;
                bl3 = true;
                bl2 = true;
                bl = true;
                n3 = n2;
                n5 = n4;
                this.begin();
                n3 = n2;
                n5 = n4;
                n4 = this.threads.add();
                n3 = n2;
                n5 = n4;
                boolean bl4 = this.isOpen();
                if (bl4) break block8;
                this.threads.remove(n4);
                if (0 > 0) break block9;
                bl = false;
            }
            this.end(bl);
            return -1;
        }
        do {
            n3 = n;
            n5 = n4;
            n2 = IOUtil.write(this.fd, byteBuffer, l, this.nd);
            if (n2 != -3) break;
            n = n2;
            n3 = n2;
            n5 = n4;
            if (this.isOpen()) continue;
            break;
        } while (true);
        n3 = n2;
        n5 = n4;
        try {
            n = IOStatus.normalize(n2);
            this.threads.remove(n4);
            bl = n2 > 0 ? bl3 : false;
        }
        catch (Throwable throwable) {
            this.threads.remove(n5);
            bl = n3 > 0 ? bl2 : false;
            this.end(bl);
            throw throwable;
        }
        this.end(bl);
        return n;
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
    public void force(boolean bl) throws IOException {
        int n;
        boolean bl2;
        int n2;
        boolean bl3;
        int n3;
        int n4;
        block7 : {
            this.ensureOpen();
            n4 = -1;
            n2 = -1;
            bl3 = true;
            boolean bl4 = true;
            bl2 = true;
            n3 = n4;
            n = n2;
            this.begin();
            n3 = n4;
            n = n2;
            n2 = this.threads.add();
            n3 = n4;
            n = n2;
            try {
                boolean bl5 = this.isOpen();
                n3 = n4;
                if (bl5) break block7;
                this.threads.remove(n2);
                bl = -1 > -1 ? bl2 : false;
            }
            catch (Throwable throwable) {
                this.threads.remove(n);
                bl = n3 > -1 ? bl4 : false;
                this.end(bl);
                throw throwable;
            }
            this.end(bl);
            return;
        }
        do {
            n = n2;
            n4 = this.nd.force(this.fd, bl);
            if (n4 != -3) break;
            n3 = n4;
            n = n2;
            bl2 = this.isOpen();
            n3 = n4;
            if (bl2) continue;
            break;
        } while (true);
        this.threads.remove(n2);
        bl = n4 > -1 ? bl3 : false;
        this.end(bl);
        return;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected void implCloseChannel() throws IOException {
        Object object2;
        this.guard.close();
        if (this.fileLockTable != null) {
            for (Object object2 : this.fileLockTable.removeAll()) {
                synchronized (object2) {
                    if (((FileLock)object2).isValid()) {
                        this.nd.release(this.fd, ((FileLock)object2).position(), ((FileLock)object2).size());
                        ((FileLockImpl)object2).invalidate();
                    }
                }
            }
        }
        this.threads.signalAndWait();
        object2 = this.parent;
        if (object2 != null) {
            ((Closeable)object2).close();
            return;
        }
        this.nd.close(this.fd);
    }

    /*
     * Exception decompiling
     */
    @Override
    public FileLock lock(long var1_1, long var3_2, boolean var5_3) throws IOException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [16[DOLOOP]], but top level block is 4[TRYBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    /*
     * Exception decompiling
     */
    @Override
    public MappedByteBuffer map(FileChannel.MapMode var1_1, long var2_10, long var4_11) throws IOException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [23[DOLOOP]], but top level block is 2[TRYBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
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
    public long position() throws IOException {
        this.ensureOpen();
        Object object = this.positionLock;
        synchronized (object) {
            int n;
            boolean bl;
            long l;
            long l2;
            boolean bl2;
            int n2;
            boolean bl3;
            block10 : {
                block11 : {
                    l = -1L;
                    n = -1;
                    bl3 = true;
                    bl = true;
                    bl2 = true;
                    l2 = l;
                    n2 = n;
                    this.begin();
                    l2 = l;
                    n2 = n;
                    n = this.threads.add();
                    l2 = l;
                    n2 = n;
                    boolean bl4 = this.isOpen();
                    if (bl4) break block10;
                    this.threads.remove(n);
                    if (-1L > -1L) break block11;
                    bl2 = false;
                }
                this.end(bl2);
                return 0L;
            }
            long l3 = l;
            l2 = l;
            n2 = n;
            try {
                if (this.append) {
                    l2 = l;
                    n2 = n;
                    BlockGuard.getThreadPolicy().onWriteToDisk();
                    l3 = l;
                }
                do {
                    l2 = l3;
                    n2 = n;
                    if (this.append) {
                        l2 = l3;
                        n2 = n;
                        l = this.nd.size(this.fd);
                    } else {
                        l2 = l3;
                        n2 = n;
                        l = this.position0(this.fd, -1L);
                    }
                    if (l != -3L) break;
                    l3 = l;
                    l2 = l;
                    n2 = n;
                } while (this.isOpen());
                l2 = l;
                n2 = n;
                l3 = IOStatus.normalize(l);
                this.threads.remove(n);
                bl2 = l > -1L ? bl3 : false;
            }
            catch (Throwable throwable) {
                this.threads.remove(n2);
                bl2 = l2 > -1L ? bl : false;
                this.end(bl2);
                throw throwable;
            }
            this.end(bl2);
            return l3;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public FileChannel position(long l) throws IOException {
        this.ensureOpen();
        if (l < 0L) {
            throw new IllegalArgumentException();
        }
        Object object = this.positionLock;
        synchronized (object) {
            boolean bl;
            block9 : {
                boolean bl2;
                int n;
                long l2;
                int n2;
                long l3;
                boolean bl3;
                block8 : {
                    l3 = -1L;
                    n2 = -1;
                    bl = true;
                    bl3 = true;
                    bl2 = true;
                    l2 = l3;
                    n = n2;
                    this.begin();
                    l2 = l3;
                    n = n2;
                    n2 = this.threads.add();
                    l2 = l3;
                    n = n2;
                    boolean bl4 = this.isOpen();
                    if (bl4) break block8;
                    this.threads.remove(n2);
                    bl = -1L > -1L ? bl2 : false;
                    this.end(bl);
                    return null;
                }
                l2 = l3;
                n = n2;
                try {
                    BlockGuard.getThreadPolicy().onReadFromDisk();
                    l2 = l3;
                    do {
                        n = n2;
                        l3 = this.position0(this.fd, l);
                        if (l3 != -3L) break;
                        l2 = l3;
                        n = n2;
                        bl2 = this.isOpen();
                        l2 = l3;
                    } while (bl2);
                    this.threads.remove(n2);
                    if (l3 > -1L) break block9;
                    bl = false;
                }
                catch (Throwable throwable) {
                    this.threads.remove(n);
                    bl = l2 > -1L ? bl3 : false;
                    this.end(bl);
                    throw throwable;
                }
            }
            this.end(bl);
            return this;
        }
    }

    /*
     * Exception decompiling
     */
    @Override
    public int read(ByteBuffer var1_1) throws IOException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [6[DOLOOP]], but top level block is 2[TRYBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
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
    public int read(ByteBuffer byteBuffer, long l) throws IOException {
        if (byteBuffer == null) {
            throw new NullPointerException();
        }
        if (l < 0L) {
            throw new IllegalArgumentException("Negative position");
        }
        if (!this.readable) {
            throw new NonReadableChannelException();
        }
        this.ensureOpen();
        if (!this.nd.needsPositionLock()) return this.readInternal(byteBuffer, l);
        Object object = this.positionLock;
        synchronized (object) {
            return this.readInternal(byteBuffer, l);
        }
    }

    /*
     * Exception decompiling
     */
    @Override
    public long read(ByteBuffer[] var1_1, int var2_3, int var3_4) throws IOException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [6[DOLOOP]], but top level block is 2[TRYBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    void release(FileLockImpl fileLockImpl) throws IOException {
        int n = this.threads.add();
        try {
            this.ensureOpen();
            this.nd.release(this.fd, fileLockImpl.position(), fileLockImpl.size());
            this.fileLockTable.remove(fileLockImpl);
            return;
        }
        finally {
            this.threads.remove(n);
        }
    }

    /*
     * Exception decompiling
     */
    @Override
    public long size() throws IOException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [6[DOLOOP]], but top level block is 2[TRYBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    @Override
    public long transferFrom(ReadableByteChannel readableByteChannel, long l, long l2) throws IOException {
        this.ensureOpen();
        if (readableByteChannel.isOpen()) {
            if (this.writable) {
                if (l >= 0L && l2 >= 0L) {
                    if (l > this.size()) {
                        return 0L;
                    }
                    if (readableByteChannel instanceof FileChannelImpl) {
                        return this.transferFromFileChannel((FileChannelImpl)readableByteChannel, l, l2);
                    }
                    return this.transferFromArbitraryChannel(readableByteChannel, l, l2);
                }
                throw new IllegalArgumentException();
            }
            throw new NonWritableChannelException();
        }
        throw new ClosedChannelException();
    }

    @Override
    public long transferTo(long l, long l2, WritableByteChannel writableByteChannel) throws IOException {
        this.ensureOpen();
        if (writableByteChannel.isOpen()) {
            if (this.readable) {
                if (writableByteChannel instanceof FileChannelImpl && !((FileChannelImpl)writableByteChannel).writable) {
                    throw new NonWritableChannelException();
                }
                if (l >= 0L && l2 >= 0L) {
                    long l3 = this.size();
                    if (l > l3) {
                        return 0L;
                    }
                    int n = (int)Math.min(l2, Integer.MAX_VALUE);
                    if (l3 - l < (long)n) {
                        n = (int)(l3 - l);
                    }
                    if ((l2 = this.transferToDirectly(l, n, writableByteChannel)) >= 0L) {
                        return l2;
                    }
                    l2 = this.transferToTrustedChannel(l, n, writableByteChannel);
                    if (l2 >= 0L) {
                        return l2;
                    }
                    return this.transferToArbitraryChannel(l, n, writableByteChannel);
                }
                throw new IllegalArgumentException();
            }
            throw new NonReadableChannelException();
        }
        throw new ClosedChannelException();
    }

    /*
     * Exception decompiling
     */
    @Override
    public FileChannel truncate(long var1_1) throws IOException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [12[DOLOOP]], but top level block is 2[TRYBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
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
    public FileLock tryLock(long l, long l2, boolean bl) throws IOException {
        int n;
        void var6_8;
        block13 : {
            FileLockTable fileLockTable;
            FileLockImpl fileLockImpl;
            int n2;
            block12 : {
                this.ensureOpen();
                if (bl) {
                    if (!this.readable) throw new NonReadableChannelException();
                }
                if (!bl) {
                    if (!this.writable) throw new NonWritableChannelException();
                }
                fileLockImpl = new FileLockImpl(this, l, l2, bl);
                fileLockTable = this.fileLockTable();
                fileLockTable.add(fileLockImpl);
                n = this.threads.add();
                this.ensureOpen();
                n2 = this.nd.lock(this.fd, false, l, l2, bl);
                if (n2 != -1) break block12;
                try {
                    fileLockTable.remove(fileLockImpl);
                    this.threads.remove(n);
                    return null;
                }
                catch (Throwable throwable) {}
                break block13;
            }
            if (n2 != 1) {
                this.threads.remove(n);
                return fileLockImpl;
            }
            FileLockImpl fileLockImpl2 = new FileLockImpl(this, l, l2, false);
            fileLockTable.replace(fileLockImpl, fileLockImpl2);
            this.threads.remove(n);
            return fileLockImpl2;
            catch (Throwable throwable) {
            }
            catch (IOException iOException) {
                try {
                    fileLockTable.remove(fileLockImpl);
                    throw iOException;
                }
                catch (Throwable throwable) {
                    // empty catch block
                }
            }
        }
        this.threads.remove(n);
        throw var6_8;
    }

    /*
     * Exception decompiling
     */
    @Override
    public int write(ByteBuffer var1_1) throws IOException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [6[DOLOOP]], but top level block is 2[TRYBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
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
    public int write(ByteBuffer byteBuffer, long l) throws IOException {
        if (byteBuffer == null) {
            throw new NullPointerException();
        }
        if (l < 0L) {
            throw new IllegalArgumentException("Negative position");
        }
        if (!this.writable) {
            throw new NonWritableChannelException();
        }
        this.ensureOpen();
        if (!this.nd.needsPositionLock()) return this.writeInternal(byteBuffer, l);
        Object object = this.positionLock;
        synchronized (object) {
            return this.writeInternal(byteBuffer, l);
        }
    }

    /*
     * Exception decompiling
     */
    @Override
    public long write(ByteBuffer[] var1_1, int var2_3, int var3_4) throws IOException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [6[DOLOOP]], but top level block is 2[TRYBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    private static class SimpleFileLockTable
    extends FileLockTable {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private final List<FileLock> lockList = new ArrayList<FileLock>(2);

        private void checkList(long l, long l2) throws OverlappingFileLockException {
            Iterator<FileLock> iterator = this.lockList.iterator();
            while (iterator.hasNext()) {
                if (!iterator.next().overlaps(l, l2)) continue;
                throw new OverlappingFileLockException();
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void add(FileLock fileLock) throws OverlappingFileLockException {
            List<FileLock> list = this.lockList;
            synchronized (list) {
                this.checkList(fileLock.position(), fileLock.size());
                this.lockList.add(fileLock);
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void remove(FileLock fileLock) {
            List<FileLock> list = this.lockList;
            synchronized (list) {
                this.lockList.remove(fileLock);
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public List<FileLock> removeAll() {
            List<FileLock> list = this.lockList;
            synchronized (list) {
                ArrayList<FileLock> arrayList = new ArrayList<FileLock>(this.lockList);
                this.lockList.clear();
                return arrayList;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void replace(FileLock fileLock, FileLock fileLock2) {
            List<FileLock> list = this.lockList;
            synchronized (list) {
                this.lockList.remove(fileLock);
                this.lockList.add(fileLock2);
                return;
            }
        }
    }

    private static class Unmapper
    implements Runnable {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        static volatile int count;
        private static final NativeDispatcher nd;
        static volatile long totalCapacity;
        static volatile long totalSize;
        private volatile long address;
        private final int cap;
        private final FileDescriptor fd;
        private final long size;

        static {
            nd = new FileDispatcherImpl();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private Unmapper(long l, long l2, int n, FileDescriptor fileDescriptor) {
            this.address = l;
            this.size = l2;
            this.cap = n;
            this.fd = fileDescriptor;
            synchronized (Unmapper.class) {
                ++count;
                totalSize += l2;
                totalCapacity += (long)n;
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void run() {
            if (this.address == 0L) {
                return;
            }
            FileChannelImpl.unmap0(this.address, this.size);
            this.address = 0L;
            if (this.fd.valid()) {
                try {
                    nd.close(this.fd);
                }
                catch (IOException iOException) {
                    // empty catch block
                }
            }
            synchronized (Unmapper.class) {
                --count;
                totalSize -= this.size;
                totalCapacity -= (long)this.cap;
                return;
            }
        }
    }

}

