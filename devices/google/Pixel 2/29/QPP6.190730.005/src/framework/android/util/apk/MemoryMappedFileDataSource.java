/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.system.ErrnoException
 *  android.system.Os
 *  android.system.OsConstants
 */
package android.util.apk;

import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.util.apk.DataDigester;
import android.util.apk.DataSource;
import java.io.FileDescriptor;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.DirectByteBuffer;
import java.security.DigestException;

class MemoryMappedFileDataSource
implements DataSource {
    private static final long MEMORY_PAGE_SIZE_BYTES = Os.sysconf((int)OsConstants._SC_PAGESIZE);
    private final FileDescriptor mFd;
    private final long mFilePosition;
    private final long mSize;

    MemoryMappedFileDataSource(FileDescriptor fileDescriptor, long l, long l2) {
        this.mFd = fileDescriptor;
        this.mFilePosition = l;
        this.mSize = l2;
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
    public void feedIntoDataDigester(DataDigester dataDigester, long l, int n) throws IOException, DigestException {
        long l2;
        void var1_13;
        block18 : {
            Object object;
            block19 : {
                l = this.mFilePosition + l;
                long l3 = MEMORY_PAGE_SIZE_BYTES;
                l3 *= l / l3;
                int n2 = (int)(l - l3);
                l2 = n + n2;
                l = 0L;
                int n3 = OsConstants.PROT_READ;
                int n4 = OsConstants.MAP_SHARED;
                int n5 = OsConstants.MAP_POPULATE;
                object = this.mFd;
                l = l3 = Os.mmap((long)0L, (long)l2, (int)n3, (int)(n4 | n5), (FileDescriptor)object, (long)l3);
                object = new DirectByteBuffer(n, l + (long)n2, this.mFd, null, true);
                dataDigester.consume((ByteBuffer)object);
                if (l == 0L) return;
                try {
                    Os.munmap((long)l, (long)l2);
                    return;
                }
                catch (ErrnoException errnoException) {
                    return;
                }
                catch (Throwable throwable) {
                    break block18;
                }
                catch (ErrnoException errnoException) {
                    break block19;
                }
                catch (Throwable throwable) {
                    break block18;
                }
                catch (ErrnoException errnoException) {
                    break block19;
                }
                catch (Throwable throwable) {
                    l = 0L;
                    break block18;
                }
                catch (ErrnoException errnoException) {
                    break block19;
                }
                catch (Throwable throwable) {
                    l = 0L;
                    break block18;
                }
                catch (ErrnoException errnoException) {
                    // empty catch block
                }
            }
            try {
                void var1_11;
                object = new StringBuilder();
                ((StringBuilder)object).append("Failed to mmap ");
                ((StringBuilder)object).append(l2);
                ((StringBuilder)object).append(" bytes");
                IOException iOException = new IOException(((StringBuilder)object).toString(), (Throwable)var1_11);
                throw iOException;
            }
            catch (Throwable throwable) {
                // empty catch block
            }
        }
        if (l == 0L) throw var1_13;
        try {
            Os.munmap((long)l, (long)l2);
            throw var1_13;
        }
        catch (ErrnoException errnoException) {
            throw var1_13;
        }
    }

    @Override
    public long size() {
        return this.mSize;
    }
}

