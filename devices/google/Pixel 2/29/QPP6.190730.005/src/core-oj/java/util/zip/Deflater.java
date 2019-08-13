/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.optimization.ReachabilitySensitive
 *  dalvik.system.CloseGuard
 */
package java.util.zip;

import dalvik.annotation.optimization.ReachabilitySensitive;
import dalvik.system.CloseGuard;
import java.util.zip.ZStreamRef;

public class Deflater {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int BEST_COMPRESSION = 9;
    public static final int BEST_SPEED = 1;
    public static final int DEFAULT_COMPRESSION = -1;
    public static final int DEFAULT_STRATEGY = 0;
    public static final int DEFLATED = 8;
    public static final int FILTERED = 1;
    public static final int FULL_FLUSH = 3;
    public static final int HUFFMAN_ONLY = 2;
    public static final int NO_COMPRESSION = 0;
    public static final int NO_FLUSH = 0;
    public static final int SYNC_FLUSH = 2;
    private byte[] buf = new byte[0];
    private long bytesRead;
    private long bytesWritten;
    private boolean finish;
    private boolean finished;
    @ReachabilitySensitive
    private final CloseGuard guard = CloseGuard.get();
    private int len;
    private int level;
    private int off;
    private boolean setParams;
    private int strategy;
    @ReachabilitySensitive
    private final ZStreamRef zsRef;

    public Deflater() {
        this(-1, false);
    }

    public Deflater(int n) {
        this(n, false);
    }

    public Deflater(int n, boolean bl) {
        this.level = n;
        this.strategy = 0;
        this.zsRef = new ZStreamRef(Deflater.init(n, 0, bl));
        this.guard.open("end");
    }

    private native int deflateBytes(long var1, byte[] var3, int var4, int var5, int var6);

    private static native void end(long var0);

    private void ensureOpen() {
        if (this.zsRef.address() != 0L) {
            return;
        }
        throw new NullPointerException("Deflater has been closed");
    }

    private static native int getAdler(long var0);

    private static native long init(int var0, int var1, boolean var2);

    private static native void reset(long var0);

    private static native void setDictionary(long var0, byte[] var2, int var3, int var4);

    public int deflate(byte[] arrby) {
        return this.deflate(arrby, 0, arrby.length, 0);
    }

    public int deflate(byte[] arrby, int n, int n2) {
        return this.deflate(arrby, n, n2, 0);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int deflate(byte[] object, int n, int n2, int n3) {
        if (object == null) {
            throw new NullPointerException();
        }
        if (n >= 0 && n2 >= 0 && n <= ((byte[])object).length - n2) {
            ZStreamRef zStreamRef = this.zsRef;
            synchronized (zStreamRef) {
                this.ensureOpen();
                if (n3 != 0 && n3 != 2 && n3 != 3) {
                    object = new IllegalArgumentException();
                    throw object;
                }
                int n4 = this.len;
                n = this.deflateBytes(this.zsRef.address(), (byte[])object, n, n2, n3);
                this.bytesWritten += (long)n;
                this.bytesRead += (long)(n4 - this.len);
                return n;
            }
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void end() {
        ZStreamRef zStreamRef = this.zsRef;
        synchronized (zStreamRef) {
            this.guard.close();
            long l = this.zsRef.address();
            this.zsRef.clear();
            if (l != 0L) {
                Deflater.end(l);
                this.buf = null;
            }
            return;
        }
    }

    protected void finalize() {
        CloseGuard closeGuard = this.guard;
        if (closeGuard != null) {
            closeGuard.warnIfOpen();
        }
        this.end();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void finish() {
        ZStreamRef zStreamRef = this.zsRef;
        synchronized (zStreamRef) {
            this.finish = true;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean finished() {
        ZStreamRef zStreamRef = this.zsRef;
        synchronized (zStreamRef) {
            return this.finished;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int getAdler() {
        ZStreamRef zStreamRef = this.zsRef;
        synchronized (zStreamRef) {
            this.ensureOpen();
            return Deflater.getAdler(this.zsRef.address());
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public long getBytesRead() {
        ZStreamRef zStreamRef = this.zsRef;
        synchronized (zStreamRef) {
            this.ensureOpen();
            return this.bytesRead;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public long getBytesWritten() {
        ZStreamRef zStreamRef = this.zsRef;
        synchronized (zStreamRef) {
            this.ensureOpen();
            return this.bytesWritten;
        }
    }

    public int getTotalIn() {
        return (int)this.getBytesRead();
    }

    public int getTotalOut() {
        return (int)this.getBytesWritten();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean needsInput() {
        ZStreamRef zStreamRef = this.zsRef;
        synchronized (zStreamRef) {
            if (this.len > 0) return false;
            return true;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void reset() {
        ZStreamRef zStreamRef = this.zsRef;
        synchronized (zStreamRef) {
            this.ensureOpen();
            Deflater.reset(this.zsRef.address());
            this.finish = false;
            this.finished = false;
            this.len = 0;
            this.off = 0;
            this.bytesWritten = 0L;
            this.bytesRead = 0L;
            return;
        }
    }

    public void setDictionary(byte[] arrby) {
        this.setDictionary(arrby, 0, arrby.length);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setDictionary(byte[] arrby, int n, int n2) {
        if (arrby == null) {
            throw new NullPointerException();
        }
        if (n >= 0 && n2 >= 0 && n <= arrby.length - n2) {
            ZStreamRef zStreamRef = this.zsRef;
            synchronized (zStreamRef) {
                this.ensureOpen();
                Deflater.setDictionary(this.zsRef.address(), arrby, n, n2);
                return;
            }
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public void setInput(byte[] arrby) {
        this.setInput(arrby, 0, arrby.length);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setInput(byte[] arrby, int n, int n2) {
        if (arrby == null) {
            throw new NullPointerException();
        }
        if (n >= 0 && n2 >= 0 && n <= arrby.length - n2) {
            ZStreamRef zStreamRef = this.zsRef;
            synchronized (zStreamRef) {
                this.buf = arrby;
                this.off = n;
                this.len = n2;
                return;
            }
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setLevel(int n) {
        if ((n < 0 || n > 9) && n != -1) {
            throw new IllegalArgumentException("invalid compression level");
        }
        ZStreamRef zStreamRef = this.zsRef;
        synchronized (zStreamRef) {
            if (this.level != n) {
                this.level = n;
                this.setParams = true;
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setStrategy(int n) {
        if (n != 0 && n != 1 && n != 2) {
            throw new IllegalArgumentException();
        }
        ZStreamRef zStreamRef = this.zsRef;
        synchronized (zStreamRef) {
            if (this.strategy != n) {
                this.strategy = n;
                this.setParams = true;
            }
            return;
        }
    }
}

