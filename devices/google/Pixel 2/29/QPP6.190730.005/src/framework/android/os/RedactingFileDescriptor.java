/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.system.ErrnoException
 *  android.system.Os
 *  android.system.StructStat
 *  libcore.io.IoUtils
 *  libcore.util.EmptyArray
 */
package android.os;

import android.content.Context;
import android.os.FileUtils;
import android.os.ParcelFileDescriptor;
import android.os.ProxyFileDescriptorCallback;
import android.os.storage.StorageManager;
import android.system.ErrnoException;
import android.system.Os;
import android.system.StructStat;
import android.util.Slog;
import com.android.internal.annotations.VisibleForTesting;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.Arrays;
import libcore.io.IoUtils;
import libcore.util.EmptyArray;

public class RedactingFileDescriptor {
    private static final boolean DEBUG = true;
    private static final String TAG = "RedactingFileDescriptor";
    private final ProxyFileDescriptorCallback mCallback;
    private volatile long[] mFreeOffsets;
    private FileDescriptor mInner;
    private ParcelFileDescriptor mOuter;
    private volatile long[] mRedactRanges;

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private RedactingFileDescriptor(Context context, File file, int n, long[] arrl, long[] arrl2) throws IOException {
        IOException iOException222;
        block4 : {
            this.mInner = null;
            this.mOuter = null;
            this.mCallback = new ProxyFileDescriptorCallback(){

                @Override
                public void onFsync() throws ErrnoException {
                    Os.fsync((FileDescriptor)RedactingFileDescriptor.this.mInner);
                }

                @Override
                public long onGetSize() throws ErrnoException {
                    return Os.fstat((FileDescriptor)RedactingFileDescriptor.access$000((RedactingFileDescriptor)RedactingFileDescriptor.this)).st_size;
                }

                @Override
                public int onRead(long l, int n, byte[] arrby) throws ErrnoException {
                    int n2;
                    long[] arrl = this;
                    int n3 = 0;
                    while (n3 < n) {
                        try {
                            n2 = Os.pread((FileDescriptor)arrl.RedactingFileDescriptor.this.mInner, (byte[])arrby, (int)n3, (int)(n - n3), (long)(l + (long)n3));
                            if (n2 == 0) break;
                            n3 += n2;
                        }
                        catch (InterruptedIOException interruptedIOException) {
                            n3 += interruptedIOException.bytesTransferred;
                        }
                    }
                    arrl = arrl.RedactingFileDescriptor.this.mRedactRanges;
                    n2 = 0;
                    do {
                        long l2;
                        long l3 = l;
                        if (n2 >= arrl.length) break;
                        long l4 = Math.max(l3, arrl[n2]);
                        long l5 = Math.min((long)n + l3, arrl[n2 + 1]);
                        for (l2 = l4; l2 < l5; ++l2) {
                            arrby[(int)(l2 - l3)] = (byte)(false ? 1 : 0);
                        }
                        for (long l6 : RedactingFileDescriptor.this.mFreeOffsets) {
                            l2 = l6 + 4L;
                            long l7 = Math.min(l2, l5);
                            for (l3 = Math.max((long)l6, (long)l4); l3 < l7; ++l3) {
                                arrby[(int)(l3 - l)] = (byte)"free".charAt((int)(l3 - l6));
                            }
                        }
                        n2 += 2;
                    } while (true);
                    return n3;
                }

                @Override
                public void onRelease() {
                    Slog.v(RedactingFileDescriptor.TAG, "onRelease()");
                    IoUtils.closeQuietly((FileDescriptor)RedactingFileDescriptor.this.mInner);
                }

                @Override
                public int onWrite(long l, int n, byte[] object) throws ErrnoException {
                    int n2 = 0;
                    while (n2 < n) {
                        try {
                            int n3 = Os.pwrite((FileDescriptor)RedactingFileDescriptor.this.mInner, (byte[])object, (int)n2, (int)(n - n2), (long)(l + (long)n2));
                            if (n3 == 0) break;
                            n2 += n3;
                        }
                        catch (InterruptedIOException interruptedIOException) {
                            n2 += interruptedIOException.bytesTransferred;
                        }
                    }
                    object = RedactingFileDescriptor.this;
                    ((RedactingFileDescriptor)object).mRedactRanges = RedactingFileDescriptor.removeRange(((RedactingFileDescriptor)object).mRedactRanges, l, (long)n2 + l);
                    return n2;
                }
            };
            this.mRedactRanges = RedactingFileDescriptor.checkRangesArgument(arrl);
            this.mFreeOffsets = arrl2;
            try {
                this.mInner = Os.open((String)file.getAbsolutePath(), (int)FileUtils.translateModePfdToPosix(n), (int)0);
                this.mOuter = context.getSystemService(StorageManager.class).openProxyFileDescriptor(n, this.mCallback);
                return;
            }
            catch (IOException iOException222) {
                break block4;
            }
            catch (ErrnoException errnoException) {
                throw errnoException.rethrowAsIOException();
            }
        }
        IoUtils.closeQuietly((FileDescriptor)this.mInner);
        IoUtils.closeQuietly((AutoCloseable)this.mOuter);
        throw iOException222;
    }

    private static long[] checkRangesArgument(long[] arrl) {
        if (arrl.length % 2 == 0) {
            for (int i = 0; i < arrl.length - 1; i += 2) {
                if (arrl[i] <= arrl[i + 1]) {
                    continue;
                }
                throw new IllegalArgumentException();
            }
            return arrl;
        }
        throw new IllegalArgumentException();
    }

    public static ParcelFileDescriptor open(Context context, File file, int n, long[] arrl, long[] arrl2) throws IOException {
        return new RedactingFileDescriptor((Context)context, (File)file, (int)n, (long[])arrl, (long[])arrl2).mOuter;
    }

    @VisibleForTesting
    public static long[] removeRange(long[] arrl, long l, long l2) {
        if (l == l2) {
            return arrl;
        }
        if (l <= l2) {
            long[] arrl2 = EmptyArray.LONG;
            for (int i = 0; i < arrl.length; i += 2) {
                if (l <= arrl[i] && l2 >= arrl[i + 1]) continue;
                if (l >= arrl[i] && l2 <= arrl[i + 1]) {
                    arrl2 = Arrays.copyOf(arrl2, arrl2.length + 4);
                    arrl2[arrl2.length - 4] = arrl[i];
                    arrl2[arrl2.length - 3] = l;
                    arrl2[arrl2.length - 2] = l2;
                    arrl2[arrl2.length - 1] = arrl[i + 1];
                    continue;
                }
                arrl2 = Arrays.copyOf(arrl2, arrl2.length + 2);
                arrl2[arrl2.length - 2] = l2 >= arrl[i] && l2 <= arrl[i + 1] ? Math.max(arrl[i], l2) : arrl[i];
                arrl2[arrl2.length - 1] = l >= arrl[i] && l <= arrl[i + 1] ? Math.min(arrl[i + 1], l) : arrl[i + 1];
            }
            return arrl2;
        }
        throw new IllegalArgumentException();
    }

}

