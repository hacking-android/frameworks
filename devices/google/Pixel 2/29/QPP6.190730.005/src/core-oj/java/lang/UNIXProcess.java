/*
 * Decompiled with CFR 0.145.
 */
package java.lang;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Arrays;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

final class UNIXProcess
extends Process {
    private static final Executor processReaperExecutor = AccessController.doPrivileged(new PrivilegedAction<Executor>(){

        @Override
        public Executor run() {
            return Executors.newCachedThreadPool(new ProcessReaperThreadFactory());
        }
    });
    private int exitcode;
    private boolean hasExited;
    private final int pid;
    private InputStream stderr;
    private OutputStream stdin;
    private InputStream stdout;

    static {
        UNIXProcess.initIDs();
    }

    UNIXProcess(byte[] object, byte[] arrby, int n, byte[] arrby2, int n2, byte[] arrby3, int[] arrn, boolean bl) throws IOException {
        this.pid = this.forkAndExec((byte[])object, arrby, n, arrby2, n2, arrby3, arrn, bl);
        try {
            super(arrn);
            AccessController.doPrivileged(object);
            return;
        }
        catch (PrivilegedActionException privilegedActionException) {
            throw (IOException)privilegedActionException.getException();
        }
    }

    private static native void destroyProcess(int var0);

    private native int forkAndExec(byte[] var1, byte[] var2, int var3, byte[] var4, int var5, byte[] var6, int[] var7, boolean var8) throws IOException;

    private static native void initIDs();

    static FileDescriptor newFileDescriptor(int n) {
        FileDescriptor fileDescriptor = new FileDescriptor();
        fileDescriptor.setInt$(n);
        return fileDescriptor;
    }

    private native int waitForProcessExit(int var1);

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void destroy() {
        synchronized (this) {
            if (!this.hasExited) {
                UNIXProcess.destroyProcess(this.pid);
            }
        }
        try {
            this.stdin.close();
        }
        catch (IOException iOException) {
            // empty catch block
        }
        try {
            this.stdout.close();
        }
        catch (IOException iOException) {
            // empty catch block
        }
        try {
            this.stderr.close();
            return;
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

    @Override
    public int exitValue() {
        synchronized (this) {
            block4 : {
                if (!this.hasExited) break block4;
                int n = this.exitcode;
                return n;
            }
            IllegalThreadStateException illegalThreadStateException = new IllegalThreadStateException("process hasn't exited");
            throw illegalThreadStateException;
        }
    }

    @Override
    public InputStream getErrorStream() {
        return this.stderr;
    }

    @Override
    public InputStream getInputStream() {
        return this.stdout;
    }

    @Override
    public OutputStream getOutputStream() {
        return this.stdin;
    }

    void initStreams(int[] object) throws IOException {
        Closeable closeable = object[0] == -1 ? ProcessBuilder.NullOutputStream.INSTANCE : new ProcessPipeOutputStream(object[0]);
        this.stdin = closeable;
        closeable = object[1] == -1 ? ProcessBuilder.NullInputStream.INSTANCE : new ProcessPipeInputStream(object[1]);
        this.stdout = closeable;
        object = object[2] == -1 ? ProcessBuilder.NullInputStream.INSTANCE : new ProcessPipeInputStream(object[2]);
        this.stderr = object;
        processReaperExecutor.execute(new Runnable(){

            @Override
            public void run() {
                UNIXProcess uNIXProcess = UNIXProcess.this;
                int n = uNIXProcess.waitForProcessExit(uNIXProcess.pid);
                UNIXProcess.this.processExited(n);
            }
        });
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void processExited(int n) {
        synchronized (this) {
            this.exitcode = n;
            this.hasExited = true;
            this.notifyAll();
        }
        Closeable closeable = this.stdout;
        if (closeable instanceof ProcessPipeInputStream) {
            ((ProcessPipeInputStream)closeable).processExited();
        }
        if ((closeable = this.stderr) instanceof ProcessPipeInputStream) {
            ((ProcessPipeInputStream)closeable).processExited();
        }
        if ((closeable = this.stdin) instanceof ProcessPipeOutputStream) {
            ((ProcessPipeOutputStream)closeable).processExited();
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Process[pid=");
        stringBuilder.append(this.pid);
        if (this.hasExited) {
            stringBuilder.append(" ,hasExited=true, exitcode=");
            stringBuilder.append(this.exitcode);
            stringBuilder.append("]");
        } else {
            stringBuilder.append(", hasExited=false]");
        }
        return stringBuilder.toString();
    }

    @Override
    public int waitFor() throws InterruptedException {
        synchronized (this) {
            while (!this.hasExited) {
                this.wait();
            }
            int n = this.exitcode;
            return n;
        }
    }

    static class ProcessPipeInputStream
    extends BufferedInputStream {
        ProcessPipeInputStream(int n) {
            super(new FileInputStream(UNIXProcess.newFileDescriptor(n), true));
        }

        private static byte[] drainInputStream(InputStream inputStream) throws IOException {
            byte[] arrby;
            block2 : {
                int n;
                if (inputStream == null) {
                    return null;
                }
                int n2 = 0;
                arrby = null;
                while ((n = inputStream.available()) > 0) {
                    arrby = arrby == null ? new byte[n] : Arrays.copyOf(arrby, n2 + n);
                    n2 += inputStream.read(arrby, n2, n);
                }
                if (arrby == null || n2 == arrby.length) break block2;
                arrby = Arrays.copyOf(arrby, n2);
            }
            return arrby;
        }

        /*
         * Loose catch block
         * WARNING - void declaration
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        void processExited() {
            // MONITORENTER : this
            InputStream inputStream = this.in;
            if (inputStream == null) return;
            try {
                void var2_5;
                byte[] arrby = ProcessPipeInputStream.drainInputStream(inputStream);
                inputStream.close();
                if (arrby == null) {
                    ProcessBuilder.NullInputStream nullInputStream = ProcessBuilder.NullInputStream.INSTANCE;
                } else {
                    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(arrby);
                }
                this.in = var2_5;
                if (this.buf != null) return;
                {
                    this.in = null;
                    return;
                }
            }
            catch (IOException iOException) {
                return;
            }
            catch (IOException iOException) {
                // empty catch block
            }
            // MONITOREXIT : this
        }
    }

    static class ProcessPipeOutputStream
    extends BufferedOutputStream {
        ProcessPipeOutputStream(int n) {
            super(new FileOutputStream(UNIXProcess.newFileDescriptor(n), true));
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        void processExited() {
            synchronized (this) {
                OutputStream outputStream = this.out;
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    }
                    catch (IOException iOException) {
                        // empty catch block
                    }
                    this.out = ProcessBuilder.NullOutputStream.INSTANCE;
                }
                return;
            }
        }
    }

    private static class ProcessReaperThreadFactory
    implements ThreadFactory {
        private static final ThreadGroup group = ProcessReaperThreadFactory.getRootThreadGroup();

        private ProcessReaperThreadFactory() {
        }

        private static ThreadGroup getRootThreadGroup() {
            return AccessController.doPrivileged(new PrivilegedAction<ThreadGroup>(){

                @Override
                public ThreadGroup run() {
                    ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
                    while (threadGroup.getParent() != null) {
                        threadGroup = threadGroup.getParent();
                    }
                    return threadGroup;
                }
            });
        }

        @Override
        public Thread newThread(Runnable runnable) {
            runnable = new Thread(group, runnable, "process reaper", 32768L);
            ((Thread)runnable).setDaemon(true);
            ((Thread)runnable).setPriority(10);
            return runnable;
        }

    }

}

