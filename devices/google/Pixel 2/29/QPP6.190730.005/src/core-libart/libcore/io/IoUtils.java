/*
 * Decompiled with CFR 0.145.
 */
package libcore.io;

import android.system.ErrnoException;
import android.system.OsConstants;
import android.system.StructStat;
import dalvik.annotation.compat.UnsupportedAppUsage;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.RandomAccessFile;
import java.net.DatagramSocketImpl;
import java.net.Socket;
import java.net.SocketImpl;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import libcore.io.IoBridge;
import libcore.io.Libcore;
import libcore.io.Os;

public final class IoUtils {
    private IoUtils() {
    }

    public static int acquireRawFd(FileDescriptor fileDescriptor) {
        Objects.requireNonNull(fileDescriptor);
        fileDescriptor = fileDescriptor.release$();
        int n = fileDescriptor.getInt$();
        long l = fileDescriptor.getOwnerId$();
        if (n != -1 && l != 0L) {
            Libcore.os.android_fdsan_exchange_owner_tag(fileDescriptor, l, 0L);
        }
        return n;
    }

    public static boolean canOpenReadOnly(String object) {
        try {
            object = Libcore.os.open((String)object, OsConstants.O_RDONLY, 0);
            Libcore.os.close((FileDescriptor)object);
            return true;
        }
        catch (ErrnoException errnoException) {
            return false;
        }
    }

    public static void close(FileDescriptor fileDescriptor) throws IOException {
        IoBridge.closeAndSignalBlockedThreads(fileDescriptor);
    }

    @UnsupportedAppUsage
    public static void closeQuietly(FileDescriptor fileDescriptor) {
        try {
            IoUtils.close(fileDescriptor);
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

    @UnsupportedAppUsage
    public static void closeQuietly(AutoCloseable autoCloseable) {
        if (autoCloseable != null) {
            try {
                autoCloseable.close();
            }
            catch (Exception exception) {
            }
            catch (RuntimeException runtimeException) {
                throw runtimeException;
            }
        }
    }

    @UnsupportedAppUsage
    public static void closeQuietly(Socket socket) {
        if (socket != null) {
            try {
                socket.close();
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }

    @Deprecated
    public static File createTemporaryDirectory(String string) {
        Object object;
        do {
            object = new StringBuilder();
            ((StringBuilder)object).append(string);
            ((StringBuilder)object).append(Math.randomIntInternal());
            object = ((StringBuilder)object).toString();
        } while (!((File)(object = new File(System.getProperty("java.io.tmpdir"), (String)object))).mkdir());
        return object;
    }

    @Deprecated
    public static void deleteContents(File file2) throws IOException {
        File[] arrfile = file2.listFiles();
        if (arrfile != null) {
            for (File file2 : arrfile) {
                if (file2.isDirectory()) {
                    IoUtils.deleteContents(file2);
                }
                file2.delete();
            }
        }
    }

    private static long generateFdOwnerId(Object object) {
        if (object == null) {
            return 0L;
        }
        long l = object instanceof FileInputStream ? 5L : (object instanceof FileOutputStream ? 6L : (object instanceof RandomAccessFile ? 7L : (object instanceof DatagramSocketImpl ? 10L : (object instanceof SocketImpl ? 11L : (IoUtils.isParcelFileDescriptor(object) ? 8L : 255L)))));
        return l << 56 | (long)System.identityHashCode(object);
    }

    private static boolean isParcelFileDescriptor(Object object) {
        try {
            boolean bl = Class.forName("android.os.ParcelFileDescriptor").isInstance(object);
            return bl;
        }
        catch (ClassNotFoundException classNotFoundException) {
            return false;
        }
    }

    @UnsupportedAppUsage
    public static byte[] readFileAsByteArray(String string) throws IOException {
        return new FileReader(string).readFully().toByteArray();
    }

    @UnsupportedAppUsage
    public static String readFileAsString(String string) throws IOException {
        return new FileReader(string).readFully().toString(StandardCharsets.UTF_8);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public static void setBlocking(FileDescriptor fileDescriptor, boolean bl) throws IOException {
        try {
            int n = Libcore.os.fcntlVoid(fileDescriptor, OsConstants.F_GETFL);
            n = !bl ? (n |= OsConstants.O_NONBLOCK) : (n &= OsConstants.O_NONBLOCK);
            Libcore.os.fcntlInt(fileDescriptor, OsConstants.F_SETFL, n);
            return;
        }
        catch (ErrnoException errnoException) {
            throw errnoException.rethrowAsIOException();
        }
    }

    public static void setFdOwner(FileDescriptor fileDescriptor, Object object) {
        Objects.requireNonNull(fileDescriptor);
        Objects.requireNonNull(object);
        long l = fileDescriptor.getOwnerId$();
        if (l == 0L) {
            long l2 = IoUtils.generateFdOwnerId(object);
            fileDescriptor.setOwnerId$(l2);
            Libcore.os.android_fdsan_exchange_owner_tag(fileDescriptor, l, l2);
            return;
        }
        throw new IllegalStateException("Attempted to take ownership of already-owned FileDescriptor");
    }

    public static void throwInterruptedIoException() throws InterruptedIOException {
        Thread.currentThread().interrupt();
        throw new InterruptedIOException();
    }

    private static class FileReader {
        private byte[] bytes;
        private int count;
        private FileDescriptor fd;
        private boolean unknownLength;

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public FileReader(String string) throws IOException {
            int n;
            block4 : {
                this.fd = IoBridge.open(string, OsConstants.O_RDONLY);
                try {
                    int n2;
                    n = n2 = (int)Libcore.os.fstat((FileDescriptor)this.fd).st_size;
                    if (n2 != 0) break block4;
                    this.unknownLength = true;
                    n = 8192;
                }
                catch (ErrnoException errnoException) {
                    IoUtils.closeQuietly(this.fd);
                    throw errnoException.rethrowAsIOException();
                }
            }
            this.bytes = new byte[n];
        }

        /*
         * Exception decompiling
         */
        public FileReader readFully() throws IOException {
            // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
            // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[TRYBLOCK]], but top level block is 6[WHILELOOP]
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

        @FindBugsSuppressWarnings(value={"EI_EXPOSE_REP"})
        public byte[] toByteArray() {
            int n = this.count;
            byte[] arrby = this.bytes;
            if (n == arrby.length) {
                return arrby;
            }
            byte[] arrby2 = new byte[n];
            System.arraycopy((byte[])arrby, (int)0, (byte[])arrby2, (int)0, (int)n);
            return arrby2;
        }

        public String toString(Charset charset) {
            return new String(this.bytes, 0, this.count, charset);
        }
    }

}

