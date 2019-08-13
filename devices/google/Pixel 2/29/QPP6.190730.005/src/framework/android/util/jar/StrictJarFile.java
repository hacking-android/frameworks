/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.system.ErrnoException
 *  android.system.Os
 *  android.system.OsConstants
 *  dalvik.system.CloseGuard
 *  libcore.io.IoBridge
 *  libcore.io.IoUtils
 *  libcore.io.Streams
 */
package android.util.jar;

import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.util.jar.StrictJarManifest;
import android.util.jar.StrictJarVerifier;
import dalvik.system.CloseGuard;
import java.io.FileDescriptor;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.Certificate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;
import java.util.zip.ZipEntry;
import libcore.io.IoBridge;
import libcore.io.IoUtils;
import libcore.io.Streams;

public final class StrictJarFile {
    private boolean closed;
    private final FileDescriptor fd;
    private final CloseGuard guard;
    private final boolean isSigned;
    private final StrictJarManifest manifest;
    private final long nativeHandle;
    private final StrictJarVerifier verifier;

    public StrictJarFile(FileDescriptor fileDescriptor) throws IOException, SecurityException {
        this(fileDescriptor, true, true);
    }

    public StrictJarFile(FileDescriptor fileDescriptor, boolean bl, boolean bl2) throws IOException, SecurityException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[fd:");
        stringBuilder.append(fileDescriptor.getInt$());
        stringBuilder.append("]");
        this(stringBuilder.toString(), fileDescriptor, bl, bl2);
    }

    public StrictJarFile(String string2) throws IOException, SecurityException {
        this(string2, true, true);
    }

    /*
     * Unable to fully structure code
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private StrictJarFile(String var1_1, FileDescriptor var2_3, boolean var3_4, boolean var4_5) throws IOException, SecurityException {
        block7 : {
            block6 : {
                super();
                this.guard = CloseGuard.get();
                this.nativeHandle = StrictJarFile.nativeOpenJarFile(var1_1, var2_3.getInt$());
                this.fd = var2_3;
                var5_6 = false;
                if (!var3_4) ** GOTO lbl36
                var6_7 = this.getMetaEntries();
                var7_8 = new StrictJarManifest(var6_7.get("META-INF/MANIFEST.MF"), true);
                this.manifest = var7_8;
                this.verifier = var7_8 = new StrictJarVerifier(var1_1, this.manifest, (HashMap<String, byte[]>)var6_7, var4_5);
                var6_7 = this.manifest.getEntries().keySet().iterator();
                while (var6_7.hasNext()) {
                    var1_1 = (String)var6_7.next();
                    if (this.findEntry(var1_1) != null) continue;
                    var6_7 = new StringBuilder();
                    var6_7.append("File ");
                    var6_7.append(var1_1);
                    var6_7.append(" in manifest does not exist");
                    var7_8 = new SecurityException(var6_7.toString());
                    throw var7_8;
                }
                var3_4 = var5_6;
                if (!this.verifier.readCertificates()) break block6;
                var3_4 = var5_6;
                if (!this.verifier.isSignedJar()) break block6;
                var3_4 = true;
            }
            try {
                this.isSigned = var3_4;
                break block7;
lbl36: // 1 sources:
                this.isSigned = false;
                this.manifest = null;
                this.verifier = null;
            }
            catch (IOException | SecurityException var1_2) {
                StrictJarFile.nativeClose(this.nativeHandle);
                IoUtils.closeQuietly((FileDescriptor)var2_3);
                this.closed = true;
                throw var1_2;
            }
        }
        this.guard.open("close");
        return;
    }

    public StrictJarFile(String string2, boolean bl, boolean bl2) throws IOException, SecurityException {
        this(string2, IoBridge.open((String)string2, (int)OsConstants.O_RDONLY), bl, bl2);
    }

    private HashMap<String, byte[]> getMetaEntries() throws IOException {
        HashMap<String, byte[]> hashMap = new HashMap<String, byte[]>();
        EntryIterator entryIterator = new EntryIterator(this.nativeHandle, "META-INF/");
        while (entryIterator.hasNext()) {
            ZipEntry zipEntry = (ZipEntry)entryIterator.next();
            hashMap.put(zipEntry.getName(), Streams.readFully((InputStream)this.getInputStream(zipEntry)));
        }
        return hashMap;
    }

    private InputStream getZipInputStream(ZipEntry zipEntry) {
        if (zipEntry.getMethod() == 0) {
            return new FDStream(this.fd, zipEntry.getDataOffset(), zipEntry.getDataOffset() + zipEntry.getSize());
        }
        FDStream fDStream = new FDStream(this.fd, zipEntry.getDataOffset(), zipEntry.getDataOffset() + zipEntry.getCompressedSize());
        int n = Math.max(1024, (int)Math.min(zipEntry.getSize(), 65535L));
        return new ZipInflaterInputStream(fDStream, new Inflater(true), n, zipEntry);
    }

    private static native void nativeClose(long var0);

    private static native ZipEntry nativeFindEntry(long var0, String var2);

    private static native ZipEntry nativeNextEntry(long var0);

    private static native long nativeOpenJarFile(String var0, int var1) throws IOException;

    private static native long nativeStartIteration(long var0, String var2);

    public void close() throws IOException {
        if (!this.closed) {
            CloseGuard closeGuard = this.guard;
            if (closeGuard != null) {
                closeGuard.close();
            }
            StrictJarFile.nativeClose(this.nativeHandle);
            IoUtils.closeQuietly((FileDescriptor)this.fd);
            this.closed = true;
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
            super.finalize();
        }
    }

    public ZipEntry findEntry(String string2) {
        return StrictJarFile.nativeFindEntry(this.nativeHandle, string2);
    }

    public Certificate[][] getCertificateChains(ZipEntry zipEntry) {
        if (this.isSigned) {
            return this.verifier.getCertificateChains(zipEntry.getName());
        }
        return null;
    }

    @Deprecated
    public Certificate[] getCertificates(ZipEntry arrcertificate) {
        if (this.isSigned) {
            int n;
            Certificate[][] arrcertificate2 = this.verifier.getCertificateChains(arrcertificate.getName());
            int n2 = arrcertificate2.length;
            int n3 = 0;
            for (n = 0; n < n2; ++n) {
                n3 += arrcertificate2[n].length;
            }
            Certificate[] arrcertificate3 = new Certificate[n3];
            n2 = arrcertificate2.length;
            n3 = 0;
            for (n = 0; n < n2; ++n) {
                arrcertificate = arrcertificate2[n];
                System.arraycopy(arrcertificate, 0, arrcertificate3, n3, arrcertificate.length);
                n3 += arrcertificate.length;
            }
            return arrcertificate3;
        }
        return null;
    }

    public InputStream getInputStream(ZipEntry zipEntry) {
        InputStream inputStream = this.getZipInputStream(zipEntry);
        if (this.isSigned) {
            StrictJarVerifier.VerifierEntry verifierEntry = this.verifier.initEntry(zipEntry.getName());
            if (verifierEntry == null) {
                return inputStream;
            }
            return new JarFileInputStream(inputStream, zipEntry.getSize(), verifierEntry);
        }
        return inputStream;
    }

    public StrictJarManifest getManifest() {
        return this.manifest;
    }

    public Iterator<ZipEntry> iterator() throws IOException {
        return new EntryIterator(this.nativeHandle, "");
    }

    static final class EntryIterator
    implements Iterator<ZipEntry> {
        private final long iterationHandle;
        private ZipEntry nextEntry;

        EntryIterator(long l, String string2) throws IOException {
            this.iterationHandle = StrictJarFile.nativeStartIteration(l, string2);
        }

        @Override
        public boolean hasNext() {
            if (this.nextEntry != null) {
                return true;
            }
            ZipEntry zipEntry = StrictJarFile.nativeNextEntry(this.iterationHandle);
            if (zipEntry == null) {
                return false;
            }
            this.nextEntry = zipEntry;
            return true;
        }

        @Override
        public ZipEntry next() {
            if (this.nextEntry != null) {
                ZipEntry zipEntry = this.nextEntry;
                this.nextEntry = null;
                return zipEntry;
            }
            return StrictJarFile.nativeNextEntry(this.iterationHandle);
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static class FDStream
    extends InputStream {
        private long endOffset;
        private final FileDescriptor fd;
        private long offset;

        public FDStream(FileDescriptor fileDescriptor, long l, long l2) {
            this.fd = fileDescriptor;
            this.offset = l;
            this.endOffset = l2;
        }

        @Override
        public int available() throws IOException {
            int n = this.offset < this.endOffset ? 1 : 0;
            return n;
        }

        @Override
        public int read() throws IOException {
            return Streams.readSingleByte((InputStream)this);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public int read(byte[] arrby, int n, int n2) throws IOException {
            FileDescriptor fileDescriptor = this.fd;
            synchronized (fileDescriptor) {
                long l = this.endOffset;
                long l2 = this.offset;
                int n3 = n2;
                if ((long)n2 > (l -= l2)) {
                    n3 = (int)l;
                }
                try {
                    Os.lseek((FileDescriptor)this.fd, (long)this.offset, (int)OsConstants.SEEK_SET);
                }
                catch (ErrnoException errnoException) {
                    IOException iOException = new IOException(errnoException);
                    throw iOException;
                }
                n = IoBridge.read((FileDescriptor)this.fd, (byte[])arrby, (int)n, (int)n3);
                if (n > 0) {
                    this.offset += (long)n;
                    return n;
                }
                return -1;
            }
        }

        @Override
        public long skip(long l) throws IOException {
            long l2 = this.endOffset;
            long l3 = this.offset;
            long l4 = l;
            if (l > l2 - l3) {
                l4 = l2 - l3;
            }
            this.offset += l4;
            return l4;
        }
    }

    static final class JarFileInputStream
    extends FilterInputStream {
        private long count;
        private boolean done = false;
        private final StrictJarVerifier.VerifierEntry entry;

        JarFileInputStream(InputStream inputStream, long l, StrictJarVerifier.VerifierEntry verifierEntry) {
            super(inputStream);
            this.entry = verifierEntry;
            this.count = l;
        }

        @Override
        public int available() throws IOException {
            if (this.done) {
                return 0;
            }
            return super.available();
        }

        @Override
        public int read() throws IOException {
            if (this.done) {
                return -1;
            }
            if (this.count > 0L) {
                int n = super.read();
                if (n != -1) {
                    this.entry.write(n);
                    --this.count;
                } else {
                    this.count = 0L;
                }
                if (this.count == 0L) {
                    this.done = true;
                    this.entry.verify();
                }
                return n;
            }
            this.done = true;
            this.entry.verify();
            return -1;
        }

        @Override
        public int read(byte[] arrby, int n, int n2) throws IOException {
            if (this.done) {
                return -1;
            }
            if (this.count > 0L) {
                int n3 = super.read(arrby, n, n2);
                if (n3 != -1) {
                    n2 = n3;
                    long l = this.count;
                    int n4 = n2;
                    if (l < (long)n2) {
                        n4 = (int)l;
                    }
                    this.entry.write(arrby, n, n4);
                    this.count -= (long)n4;
                } else {
                    this.count = 0L;
                }
                if (this.count == 0L) {
                    this.done = true;
                    this.entry.verify();
                }
                return n3;
            }
            this.done = true;
            this.entry.verify();
            return -1;
        }

        @Override
        public long skip(long l) throws IOException {
            return Streams.skipByReading((InputStream)this, (long)l);
        }
    }

    public static class ZipInflaterInputStream
    extends InflaterInputStream {
        private long bytesRead = 0L;
        private boolean closed;
        private final ZipEntry entry;

        public ZipInflaterInputStream(InputStream inputStream, Inflater inflater, int n, ZipEntry zipEntry) {
            super(inputStream, inflater, n);
            this.entry = zipEntry;
        }

        @Override
        public int available() throws IOException {
            boolean bl = this.closed;
            int n = 0;
            if (bl) {
                return 0;
            }
            if (super.available() != 0) {
                n = (int)(this.entry.getSize() - this.bytesRead);
            }
            return n;
        }

        @Override
        public void close() throws IOException {
            super.close();
            this.closed = true;
        }

        @Override
        public int read(byte[] object, int n, int n2) throws IOException {
            block3 : {
                block2 : {
                    try {
                        n = super.read((byte[])object, n, n2);
                        if (n != -1) break block2;
                        if (this.entry.getSize() == this.bytesRead) break block3;
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Size mismatch on inflated file: ");
                    }
                    catch (IOException iOException) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Error reading data for ");
                        stringBuilder.append(this.entry.getName());
                        stringBuilder.append(" near offset ");
                        stringBuilder.append(this.bytesRead);
                        throw new IOException(stringBuilder.toString(), iOException);
                    }
                    ((StringBuilder)object).append(this.bytesRead);
                    ((StringBuilder)object).append(" vs ");
                    ((StringBuilder)object).append(this.entry.getSize());
                    throw new IOException(((StringBuilder)object).toString());
                }
                this.bytesRead += (long)n;
            }
            return n;
        }
    }

}

