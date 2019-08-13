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
import java.util.zip.DataFormatException;
import java.util.zip.ZStreamRef;

public class Inflater {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final byte[] defaultBuf = new byte[0];
    private byte[] buf = defaultBuf;
    private long bytesRead;
    private long bytesWritten;
    private boolean finished;
    @ReachabilitySensitive
    private final CloseGuard guard = CloseGuard.get();
    private int len;
    private boolean needDict;
    private int off;
    @ReachabilitySensitive
    private final ZStreamRef zsRef;

    public Inflater() {
        this(false);
    }

    public Inflater(boolean bl) {
        this.zsRef = new ZStreamRef(Inflater.init(bl));
        this.guard.open("end");
    }

    private static native void end(long var0);

    private void ensureOpen() {
        if (this.zsRef.address() != 0L) {
            return;
        }
        throw new NullPointerException("Inflater has been closed");
    }

    private static native int getAdler(long var0);

    private native int inflateBytes(long var1, byte[] var3, int var4, int var5) throws DataFormatException;

    private static native long init(boolean var0);

    private static native void reset(long var0);

    private static native void setDictionary(long var0, byte[] var2, int var3, int var4);

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
                Inflater.end(l);
                this.buf = null;
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    boolean ended() {
        ZStreamRef zStreamRef = this.zsRef;
        synchronized (zStreamRef) {
            if (this.zsRef.address() != 0L) return false;
            return true;
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
            return Inflater.getAdler(this.zsRef.address());
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

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int getRemaining() {
        ZStreamRef zStreamRef = this.zsRef;
        synchronized (zStreamRef) {
            return this.len;
        }
    }

    public int getTotalIn() {
        return (int)this.getBytesRead();
    }

    public int getTotalOut() {
        return (int)this.getBytesWritten();
    }

    public int inflate(byte[] arrby) throws DataFormatException {
        return this.inflate(arrby, 0, arrby.length);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int inflate(byte[] arrby, int n, int n2) throws DataFormatException {
        if (arrby == null) {
            throw new NullPointerException();
        }
        if (n >= 0 && n2 >= 0 && n <= arrby.length - n2) {
            ZStreamRef zStreamRef = this.zsRef;
            synchronized (zStreamRef) {
                this.ensureOpen();
                int n3 = this.len;
                n = this.inflateBytes(this.zsRef.address(), arrby, n, n2);
                this.bytesWritten += (long)n;
                this.bytesRead += (long)(n3 - this.len);
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
    public boolean needsDictionary() {
        ZStreamRef zStreamRef = this.zsRef;
        synchronized (zStreamRef) {
            return this.needDict;
        }
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
            Inflater.reset(this.zsRef.address());
            this.buf = defaultBuf;
            this.finished = false;
            this.needDict = false;
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
                Inflater.setDictionary(this.zsRef.address(), arrby, n, n2);
                this.needDict = false;
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
}

