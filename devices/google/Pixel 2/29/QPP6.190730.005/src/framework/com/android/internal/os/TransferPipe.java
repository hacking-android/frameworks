/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.io.IoUtils
 */
package com.android.internal.os;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.os.SystemClock;
import android.util.Slog;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import libcore.io.IoUtils;

public class TransferPipe
implements Runnable,
Closeable {
    static final boolean DEBUG = false;
    static final long DEFAULT_TIMEOUT = 5000L;
    static final String TAG = "TransferPipe";
    String mBufferPrefix;
    boolean mComplete;
    long mEndTime;
    String mFailure;
    final ParcelFileDescriptor[] mFds;
    FileDescriptor mOutFd;
    final Thread mThread;

    private static /* synthetic */ void $closeResource(Throwable throwable, AutoCloseable autoCloseable) {
        if (throwable != null) {
            try {
                autoCloseable.close();
            }
            catch (Throwable throwable2) {
                throwable.addSuppressed(throwable2);
            }
        } else {
            autoCloseable.close();
        }
    }

    public TransferPipe() throws IOException {
        this(null);
    }

    public TransferPipe(String string2) throws IOException {
        this(string2, TAG);
    }

    protected TransferPipe(String string2, String string3) throws IOException {
        this.mThread = new Thread((Runnable)this, string3);
        this.mFds = ParcelFileDescriptor.createPipe();
        this.mBufferPrefix = string2;
    }

    public static void dumpAsync(IBinder iBinder, FileDescriptor fileDescriptor, String[] arrstring) throws IOException, RemoteException {
        TransferPipe.goDump(iBinder, fileDescriptor, arrstring);
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static byte[] dumpAsync(IBinder object, String ... object2) throws IOException, RemoteException {
        int n;
        ParcelFileDescriptor[] arrparcelFileDescriptor = ParcelFileDescriptor.createPipe();
        TransferPipe.dumpAsync((IBinder)object, arrparcelFileDescriptor[1].getFileDescriptor(), (String[])object2);
        arrparcelFileDescriptor[1].close();
        arrparcelFileDescriptor[1] = null;
        byte[] arrby = new byte[4096];
        object = new ByteArrayOutputStream();
        object2 = new FileInputStream(arrparcelFileDescriptor[0].getFileDescriptor());
        do {
            n = ((FileInputStream)object2).read(arrby);
            if (n != -1) break block17;
            break;
        } while (true);
        catch (Throwable throwable) {
            try {
                throw throwable;
            }
            catch (Throwable throwable2) {
                try {
                    TransferPipe.$closeResource(throwable, (AutoCloseable)object2);
                    throw throwable2;
                }
                catch (Throwable throwable3) {
                    try {
                        throw throwable3;
                    }
                    catch (Throwable throwable4) {
                        TransferPipe.$closeResource(throwable3, (AutoCloseable)object);
                        throw throwable4;
                    }
                }
            }
        }
        {
            block17 : {
                TransferPipe.$closeResource(null, (AutoCloseable)object2);
                object2 = ((ByteArrayOutputStream)object).toByteArray();
                TransferPipe.$closeResource(null, (AutoCloseable)object);
                return object2;
            }
            ((ByteArrayOutputStream)object).write(arrby, 0, n);
            continue;
        }
        finally {
            arrparcelFileDescriptor[0].close();
            IoUtils.closeQuietly((AutoCloseable)arrparcelFileDescriptor[1]);
        }
    }

    static void go(Caller caller, IInterface iInterface, FileDescriptor fileDescriptor, String string2, String[] arrstring) throws IOException, RemoteException {
        TransferPipe.go(caller, iInterface, fileDescriptor, string2, arrstring, 5000L);
    }

    static void go(Caller caller, IInterface iInterface, FileDescriptor fileDescriptor, String string2, String[] arrstring, long l) throws IOException, RemoteException {
        if (iInterface.asBinder() instanceof Binder) {
            try {
                caller.go(iInterface, fileDescriptor, string2, arrstring);
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
            return;
        }
        TransferPipe transferPipe = new TransferPipe();
        try {
            caller.go(iInterface, transferPipe.getWriteFd().getFileDescriptor(), string2, arrstring);
            transferPipe.go(fileDescriptor, l);
        }
        catch (Throwable throwable) {
            try {
                throw throwable;
            }
            catch (Throwable throwable2) {
                TransferPipe.$closeResource(throwable, transferPipe);
                throw throwable2;
            }
        }
        TransferPipe.$closeResource(null, transferPipe);
    }

    static void goDump(IBinder iBinder, FileDescriptor fileDescriptor, String[] arrstring) throws IOException, RemoteException {
        TransferPipe.goDump(iBinder, fileDescriptor, arrstring, 5000L);
    }

    static void goDump(IBinder iBinder, FileDescriptor fileDescriptor, String[] arrstring, long l) throws IOException, RemoteException {
        if (iBinder instanceof Binder) {
            try {
                iBinder.dump(fileDescriptor, arrstring);
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
            return;
        }
        TransferPipe transferPipe = new TransferPipe();
        try {
            iBinder.dumpAsync(transferPipe.getWriteFd().getFileDescriptor(), arrstring);
            transferPipe.go(fileDescriptor, l);
        }
        catch (Throwable throwable) {
            try {
                throw throwable;
            }
            catch (Throwable throwable2) {
                TransferPipe.$closeResource(throwable, transferPipe);
                throw throwable2;
            }
        }
        TransferPipe.$closeResource(null, transferPipe);
    }

    @Override
    public void close() {
        this.kill();
    }

    void closeFd(int n) {
        ParcelFileDescriptor[] arrparcelFileDescriptor = this.mFds;
        if (arrparcelFileDescriptor[n] != null) {
            try {
                arrparcelFileDescriptor[n].close();
            }
            catch (IOException iOException) {
                // empty catch block
            }
            this.mFds[n] = null;
        }
    }

    protected OutputStream getNewOutputStream() {
        return new FileOutputStream(this.mOutFd);
    }

    ParcelFileDescriptor getReadFd() {
        return this.mFds[0];
    }

    public ParcelFileDescriptor getWriteFd() {
        return this.mFds[1];
    }

    public void go(FileDescriptor fileDescriptor) throws IOException {
        this.go(fileDescriptor, 5000L);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void go(FileDescriptor object, long l) throws IOException {
        try {
            synchronized (this) {
                this.mOutFd = object;
            }
        }
        catch (Throwable throwable) {
            this.kill();
            throw throwable;
        }
        {
            this.mEndTime = SystemClock.uptimeMillis() + l;
            this.closeFd(1);
            this.mThread.start();
            while (this.mFailure == null && !this.mComplete) {
                long l2 = this.mEndTime;
                l = SystemClock.uptimeMillis();
                if ((l = l2 - l) <= 0L) {
                    this.mThread.interrupt();
                    object = new IOException("Timeout");
                    throw object;
                }
                try {
                    this.wait(l);
                }
                catch (InterruptedException interruptedException) {}
            }
            if (this.mFailure == null) {
                // MONITOREXIT [6, 10] lbl24 : MonitorExitStatement: MONITOREXIT : this
                this.kill();
                return;
            }
            object = new IOException(this.mFailure);
            throw object;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void kill() {
        synchronized (this) {
            this.closeFd(0);
            this.closeFd(1);
            return;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public void run() {
        var1_1 = new byte[1024];
        // MONITORENTER : this
        var2_2 = this.getReadFd();
        if (var2_2 == null) {
            Slog.w("TransferPipe", "Pipe has been closed...");
            // MONITOREXIT : this
            return;
        }
        var3_4 = new FileInputStream(var2_2.getFileDescriptor());
        var4_5 = this.getNewOutputStream();
        // MONITOREXIT : this
        var2_2 = null;
        var5_6 = 1;
        var6_7 = this.mBufferPrefix;
        var7_8 = var5_6;
        if (var6_7 != null) {
            var2_2 = var6_7.getBytes();
            var7_8 = var5_6;
        }
        try {
            do {
                if ((var8_9 = var3_4.read(var1_1)) > 0) {
                    if (var2_2 == null) {
                        var4_5.write(var1_1, 0, var8_9);
                        continue;
                    }
                    var9_10 = 0;
                    var5_6 = 0;
                } else {
                    this.mThread.isInterrupted();
                    // MONITORENTER : this
                    this.mComplete = true;
                    this.notifyAll();
                    // MONITOREXIT : this
                    return;
                }
                while (var5_6 < var8_9) {
                    block21 : {
                        block20 : {
                            var10_11 = var7_8;
                            var11_12 = var9_10;
                            var12_13 = var5_6;
                            if (var1_1[var5_6] == 10) break block21;
                            if (var5_6 <= var9_10) ** GOTO lbl43
                            var4_5.write(var1_1, var9_10, var5_6 - var9_10);
lbl43: // 2 sources:
                            var9_10 = var5_6;
                            var10_11 = var7_8;
                            var11_12 = var5_6;
                            if (var7_8 == 0) break block20;
                            var4_5.write(var2_2);
                            var10_11 = 0;
                            var11_12 = var5_6;
                        }
                        while ((var5_6 = var11_12 + 1) < var8_9) {
                            var11_12 = var5_6;
                            if (var1_1[var5_6] != 10) continue;
                        }
                        var11_12 = var9_10;
                        var12_13 = var5_6;
                        if (var5_6 < var8_9) {
                            var10_11 = 1;
                            var12_13 = var5_6;
                            var11_12 = var9_10;
                        }
                    }
                    var5_6 = var12_13 + 1;
                    var7_8 = var10_11;
                    var9_10 = var11_12;
                }
                if (var8_9 <= var9_10) continue;
                var4_5.write(var1_1, var9_10, var8_9 - var9_10);
            } while (true);
        }
        catch (IOException var2_3) {
            // MONITORENTER : this
            this.mFailure = var2_3.toString();
            this.notifyAll();
            // MONITOREXIT : this
            return;
        }
    }

    public void setBufferPrefix(String string2) {
        this.mBufferPrefix = string2;
    }

    static interface Caller {
        public void go(IInterface var1, FileDescriptor var2, String var3, String[] var4) throws RemoteException;
    }

}

