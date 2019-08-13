/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.system.CloseGuard
 */
package java.util.zip;

import dalvik.system.CloseGuard;
import java.io.Closeable;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.Spliterators;
import java.util.WeakHashMap;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;
import java.util.zip.ZipCoder;
import java.util.zip.ZipConstants;
import java.util.zip.ZipEntry;
import java.util.zip.ZipError;
import java.util.zip.ZipException;

public class ZipFile
implements ZipConstants,
Closeable {
    private static final int DEFLATED = 8;
    private static final int JZENTRY_COMMENT = 2;
    private static final int JZENTRY_EXTRA = 1;
    private static final int JZENTRY_NAME = 0;
    public static final int OPEN_DELETE = 4;
    public static final int OPEN_READ = 1;
    private static final int STORED = 0;
    private static final boolean usemmap = true;
    private volatile boolean closeRequested = false;
    private final File fileToRemoveOnClose;
    private final CloseGuard guard = CloseGuard.get();
    private Deque<Inflater> inflaterCache = new ArrayDeque<Inflater>();
    private long jzfile;
    private final boolean locsig;
    private final String name;
    private final Map<InputStream, Inflater> streams = new WeakHashMap<InputStream, Inflater>();
    private final int total;
    private ZipCoder zc;

    public ZipFile(File file) throws ZipException, IOException {
        this(file, 1);
    }

    public ZipFile(File file, int n) throws IOException {
        this(file, n, StandardCharsets.UTF_8);
    }

    public ZipFile(File serializable, int n, Charset charset) throws IOException {
        if ((n & 1) != 0 && (n & -6) == 0) {
            String string = ((File)serializable).getPath();
            Serializable serializable2 = (n & 4) != 0 ? serializable : null;
            this.fileToRemoveOnClose = serializable2;
            if (charset != null) {
                this.zc = ZipCoder.get(charset);
                this.jzfile = ZipFile.open(string, n, ((File)serializable).lastModified(), usemmap);
                this.name = string;
                this.total = ZipFile.getTotal(this.jzfile);
                this.locsig = ZipFile.startsWithLOC(this.jzfile);
                this.guard.open("close");
                return;
            }
            throw new NullPointerException("charset is null");
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("Illegal mode: 0x");
        ((StringBuilder)serializable).append(Integer.toHexString(n));
        throw new IllegalArgumentException(((StringBuilder)serializable).toString());
    }

    public ZipFile(File file, Charset charset) throws IOException {
        this(file, 1, charset);
    }

    public ZipFile(String string) throws IOException {
        this(new File(string), 1);
    }

    public ZipFile(String string, Charset charset) throws IOException {
        this(new File(string), 1, charset);
    }

    private static native void close(long var0);

    private void ensureOpen() {
        if (!this.closeRequested) {
            if (this.jzfile != 0L) {
                return;
            }
            throw new IllegalStateException("The object is not initialized.");
        }
        throw new IllegalStateException("zip file closed");
    }

    private void ensureOpenOrZipException() throws IOException {
        if (!this.closeRequested) {
            return;
        }
        throw new ZipException("ZipFile closed");
    }

    private static native void freeEntry(long var0, long var2);

    private static native byte[] getCommentBytes(long var0);

    private static native long getEntry(long var0, byte[] var2, boolean var3);

    private static native byte[] getEntryBytes(long var0, int var2);

    private static native long getEntryCSize(long var0);

    private static native long getEntryCrc(long var0);

    private static native int getEntryFlag(long var0);

    private static native int getEntryMethod(long var0);

    private static native long getEntrySize(long var0);

    private static native long getEntryTime(long var0);

    private static native int getFileDescriptor(long var0);

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private Inflater getInflater() {
        Deque<Inflater> deque = this.inflaterCache;
        synchronized (deque) {
            Inflater inflater;
            do {
                if ((inflater = this.inflaterCache.poll()) != null) continue;
                return new Inflater(true);
            } while (inflater.ended());
            return inflater;
        }
    }

    private static native long getNextEntry(long var0, int var2);

    private static native int getTotal(long var0);

    private ZipEntry getZipEntry(String arrby, long l) {
        ZipEntry zipEntry = new ZipEntry();
        zipEntry.flag = ZipFile.getEntryFlag(l);
        if (arrby != null) {
            zipEntry.name = arrby;
        } else {
            arrby = ZipFile.getEntryBytes(l, 0);
            zipEntry.name = !this.zc.isUTF8() && (zipEntry.flag & 2048) != 0 ? this.zc.toStringUTF8(arrby, arrby.length) : this.zc.toString(arrby, arrby.length);
        }
        zipEntry.xdostime = ZipFile.getEntryTime(l);
        zipEntry.crc = ZipFile.getEntryCrc(l);
        zipEntry.size = ZipFile.getEntrySize(l);
        zipEntry.csize = ZipFile.getEntryCSize(l);
        zipEntry.method = ZipFile.getEntryMethod(l);
        zipEntry.setExtra0(ZipFile.getEntryBytes(l, 1), false);
        arrby = ZipFile.getEntryBytes(l, 2);
        zipEntry.comment = arrby == null ? null : (!this.zc.isUTF8() && (zipEntry.flag & 2048) != 0 ? this.zc.toStringUTF8(arrby, arrby.length) : this.zc.toString(arrby, arrby.length));
        return zipEntry;
    }

    private static native String getZipMessage(long var0);

    private static native long open(String var0, int var1, long var2, boolean var4) throws IOException;

    private static native int read(long var0, long var2, long var4, byte[] var6, int var7, int var8);

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void releaseInflater(Inflater inflater) {
        if (inflater.ended()) return;
        inflater.reset();
        Deque<Inflater> deque = this.inflaterCache;
        synchronized (deque) {
            this.inflaterCache.add(inflater);
            return;
        }
    }

    private static native boolean startsWithLOC(long var0);

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void close() throws IOException {
        if (this.closeRequested) {
            return;
        }
        Object object = this.guard;
        if (object != null) {
            object.close();
        }
        this.closeRequested = true;
        synchronized (this) {
            Iterator<Map.Entry<K, V>> iterator;
            if (this.streams != null) {
                object = this.streams;
                synchronized (object) {
                    if (!this.streams.isEmpty()) {
                        iterator = new Iterator<Map.Entry<K, V>>(this.streams);
                        this.streams.clear();
                        iterator = iterator.entrySet().iterator();
                        while (iterator.hasNext()) {
                            Object object2 = iterator.next();
                            ((InputStream)object2.getKey()).close();
                            if ((object2 = (Inflater)object2.getValue()) == null) continue;
                            ((Inflater)object2).end();
                        }
                    }
                }
            }
            if (this.inflaterCache != null) {
                object = this.inflaterCache;
                synchronized (object) {
                    while ((iterator = this.inflaterCache.poll()) != null) {
                        ((Inflater)((Object)iterator)).end();
                    }
                }
            }
            if (this.jzfile != 0L) {
                long l = this.jzfile;
                this.jzfile = 0L;
                ZipFile.close(l);
            }
            if (this.fileToRemoveOnClose != null) {
                this.fileToRemoveOnClose.delete();
            }
            return;
        }
    }

    public Enumeration<? extends ZipEntry> entries() {
        return new ZipEntryIterator();
    }

    protected void finalize() throws IOException {
        CloseGuard closeGuard = this.guard;
        if (closeGuard != null) {
            closeGuard.warnIfOpen();
        }
        this.close();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public String getComment() {
        synchronized (this) {
            this.ensureOpen();
            Object object = ZipFile.getCommentBytes(this.jzfile);
            if (object != null) return this.zc.toString((byte[])object, ((byte[])object).length);
            return null;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public ZipEntry getEntry(String object) {
        if (object == null) {
            throw new NullPointerException("name");
        }
        synchronized (this) {
            this.ensureOpen();
            long l = ZipFile.getEntry(this.jzfile, this.zc.getBytes((String)object), true);
            if (l != 0L) {
                object = this.getZipEntry((String)object, l);
                ZipFile.freeEntry(this.jzfile, l);
                return object;
            }
            return null;
        }
    }

    public int getFileDescriptor() {
        return ZipFile.getFileDescriptor(this.jzfile);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public InputStream getInputStream(ZipEntry object) throws IOException {
        if (object == null) {
            throw new NullPointerException("entry");
        }
        synchronized (this) {
            long l;
            this.ensureOpen();
            long l2 = !this.zc.isUTF8() && (((ZipEntry)object).flag & 2048) != 0 ? ZipFile.getEntry(this.jzfile, this.zc.getBytesUTF8(((ZipEntry)object).name), true) : ZipFile.getEntry(this.jzfile, this.zc.getBytes(((ZipEntry)object).name), true);
            if (l2 == 0L) {
                return null;
            }
            Object object2 = new ZipFileInputStream(l2);
            int n = ZipFile.getEntryMethod(l2);
            if (n == 0) {
                object = this.streams;
                synchronized (object) {
                    this.streams.put((InputStream)object2, null);
                    return object2;
                }
            }
            if (n != 8) {
                object = new ZipException("invalid compression method");
                throw object;
            }
            l2 = l = ZipFile.getEntrySize(l2) + 2L;
            if (l > 65536L) {
                l2 = 65536L;
            }
            l = l2;
            if (l2 <= 0L) {
                l = 4096L;
            }
            Inflater inflater = this.getInflater();
            object = new ZipFileInflaterInputStream((ZipFileInputStream)object2, inflater, (int)l);
            object2 = this.streams;
            synchronized (object2) {
                this.streams.put((InputStream)object, inflater);
                return object;
            }
        }
    }

    public String getName() {
        return this.name;
    }

    public int size() {
        this.ensureOpen();
        return this.total;
    }

    public boolean startsWithLocHeader() {
        return this.locsig;
    }

    public Stream<? extends ZipEntry> stream() {
        return StreamSupport.stream(Spliterators.spliterator(new ZipEntryIterator(), (long)this.size(), 1297), false);
    }

    private class ZipEntryIterator
    implements Enumeration<ZipEntry>,
    Iterator<ZipEntry> {
        private int i = 0;

        public ZipEntryIterator() {
            ZipFile.this.ensureOpen();
        }

        @Override
        public boolean hasMoreElements() {
            return this.hasNext();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public boolean hasNext() {
            ZipFile zipFile = ZipFile.this;
            synchronized (zipFile) {
                ZipFile.this.ensureOpen();
                if (this.i >= ZipFile.this.total) return false;
                return true;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public ZipEntry next() {
            ZipFile zipFile = ZipFile.this;
            synchronized (zipFile) {
                ZipFile.this.ensureOpen();
                if (this.i >= ZipFile.this.total) {
                    NoSuchElementException noSuchElementException = new NoSuchElementException();
                    throw noSuchElementException;
                }
                long l = ZipFile.this.jzfile;
                int n = this.i;
                this.i = n + 1;
                if ((l = ZipFile.getNextEntry(l, n)) != 0L) {
                    ZipEntry zipEntry = ZipFile.this.getZipEntry(null, l);
                    ZipFile.freeEntry(ZipFile.this.jzfile, l);
                    return zipEntry;
                }
                String string = ZipFile.this.closeRequested ? "ZipFile concurrently closed" : ZipFile.getZipMessage(ZipFile.this.jzfile);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("jzentry == 0,\n jzfile = ");
                stringBuilder.append(ZipFile.this.jzfile);
                stringBuilder.append(",\n total = ");
                stringBuilder.append(ZipFile.this.total);
                stringBuilder.append(",\n name = ");
                stringBuilder.append(ZipFile.this.name);
                stringBuilder.append(",\n i = ");
                stringBuilder.append(this.i);
                stringBuilder.append(",\n message = ");
                stringBuilder.append(string);
                ZipError zipError = new ZipError(stringBuilder.toString());
                throw zipError;
            }
        }

        @Override
        public ZipEntry nextElement() {
            return this.next();
        }
    }

    private class ZipFileInflaterInputStream
    extends InflaterInputStream {
        private volatile boolean closeRequested;
        private boolean eof;
        private final ZipFileInputStream zfin;

        ZipFileInflaterInputStream(ZipFileInputStream zipFileInputStream, Inflater inflater, int n) {
            super(zipFileInputStream, inflater, n);
            this.closeRequested = false;
            this.eof = false;
            this.zfin = zipFileInputStream;
        }

        @Override
        public int available() throws IOException {
            if (this.closeRequested) {
                return 0;
            }
            long l = this.zfin.size() - this.inf.getBytesWritten();
            int n = l > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int)l;
            return n;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        @Override
        public void close() throws IOException {
            if (this.closeRequested) {
                return;
            }
            this.closeRequested = true;
            super.close();
            Map map = ZipFile.this.streams;
            // MONITORENTER : map
            Inflater inflater = (Inflater)ZipFile.this.streams.remove(this);
            // MONITOREXIT : map
            if (inflater == null) return;
            ZipFile.this.releaseInflater(inflater);
        }

        @Override
        protected void fill() throws IOException {
            if (!this.eof) {
                this.len = this.in.read(this.buf, 0, this.buf.length);
                if (this.len == -1) {
                    this.buf[0] = (byte)(false ? 1 : 0);
                    this.len = 1;
                    this.eof = true;
                }
                this.inf.setInput(this.buf, 0, this.len);
                return;
            }
            throw new EOFException("Unexpected end of ZLIB input stream");
        }

        protected void finalize() throws Throwable {
            this.close();
        }
    }

    private class ZipFileInputStream
    extends InputStream {
        protected long jzentry;
        private long pos = 0L;
        protected long rem;
        protected long size;
        private volatile boolean zfisCloseRequested = false;

        ZipFileInputStream(long l) {
            this.rem = ZipFile.getEntryCSize(l);
            this.size = ZipFile.getEntrySize(l);
            this.jzentry = l;
        }

        @Override
        public int available() {
            long l = this.rem;
            int n = l > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int)l;
            return n;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void close() {
            if (this.zfisCloseRequested) {
                return;
            }
            this.zfisCloseRequested = true;
            this.rem = 0L;
            Object object = ZipFile.this;
            synchronized (object) {
                if (this.jzentry != 0L && ZipFile.this.jzfile != 0L) {
                    ZipFile.freeEntry(ZipFile.this.jzfile, this.jzentry);
                    this.jzentry = 0L;
                }
            }
            object = ZipFile.this.streams;
            synchronized (object) {
                ZipFile.this.streams.remove(this);
                return;
            }
        }

        protected void finalize() {
            this.close();
        }

        @Override
        public int read() throws IOException {
            byte[] arrby = new byte[1];
            if (this.read(arrby, 0, 1) == 1) {
                return arrby[0] & 255;
            }
            return -1;
        }

        /*
         * WARNING - void declaration
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        @Override
        public int read(byte[] arrby, int n, int n2) throws IOException {
            int n3;
            void var3_4;
            ZipFile.this.ensureOpenOrZipException();
            ZipFile zipFile = ZipFile.this;
            // MONITORENTER : zipFile
            long l = this.rem;
            long l2 = this.pos;
            if (l == 0L) {
                // MONITOREXIT : zipFile
                return -1;
            }
            if (var3_4 <= 0) {
                // MONITOREXIT : zipFile
                return 0;
            }
            int n4 = var3_4;
            if ((long)var3_4 > l) {
                n4 = (int)l;
            }
            n3 = ZipFile.read(ZipFile.this.jzfile, this.jzentry, l2, arrby, n3, n4);
            if (n3 > 0) {
                this.pos = (long)n3 + l2;
                this.rem = l - (long)n3;
            }
            // MONITOREXIT : zipFile
            if (this.rem != 0L) return n3;
            this.close();
            return n3;
        }

        public long size() {
            return this.size;
        }

        @Override
        public long skip(long l) {
            long l2 = l;
            if (l > this.rem) {
                l2 = this.rem;
            }
            this.pos += l2;
            this.rem -= l2;
            if (this.rem == 0L) {
                this.close();
            }
            return l2;
        }
    }

}

