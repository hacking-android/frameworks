/*
 * Decompiled with CFR 0.145.
 */
package android.content.res;

import android.annotation.UnsupportedAppUsage;
import android.os.Bundle;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import java.io.Closeable;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class AssetFileDescriptor
implements Parcelable,
Closeable {
    public static final Parcelable.Creator<AssetFileDescriptor> CREATOR = new Parcelable.Creator<AssetFileDescriptor>(){

        @Override
        public AssetFileDescriptor createFromParcel(Parcel parcel) {
            return new AssetFileDescriptor(parcel);
        }

        public AssetFileDescriptor[] newArray(int n) {
            return new AssetFileDescriptor[n];
        }
    };
    public static final long UNKNOWN_LENGTH = -1L;
    private final Bundle mExtras;
    @UnsupportedAppUsage
    private final ParcelFileDescriptor mFd;
    @UnsupportedAppUsage
    private final long mLength;
    @UnsupportedAppUsage
    private final long mStartOffset;

    AssetFileDescriptor(Parcel parcel) {
        this.mFd = ParcelFileDescriptor.CREATOR.createFromParcel(parcel);
        this.mStartOffset = parcel.readLong();
        this.mLength = parcel.readLong();
        this.mExtras = parcel.readInt() != 0 ? parcel.readBundle() : null;
    }

    public AssetFileDescriptor(ParcelFileDescriptor parcelFileDescriptor, long l, long l2) {
        this(parcelFileDescriptor, l, l2, null);
    }

    public AssetFileDescriptor(ParcelFileDescriptor parcelFileDescriptor, long l, long l2, Bundle bundle) {
        if (parcelFileDescriptor != null) {
            if (l2 < 0L && l != 0L) {
                throw new IllegalArgumentException("startOffset must be 0 when using UNKNOWN_LENGTH");
            }
            this.mFd = parcelFileDescriptor;
            this.mStartOffset = l;
            this.mLength = l2;
            this.mExtras = bundle;
            return;
        }
        throw new IllegalArgumentException("fd must not be null");
    }

    @Override
    public void close() throws IOException {
        this.mFd.close();
    }

    public FileInputStream createInputStream() throws IOException {
        if (this.mLength < 0L) {
            return new ParcelFileDescriptor.AutoCloseInputStream(this.mFd);
        }
        return new AutoCloseInputStream(this);
    }

    public FileOutputStream createOutputStream() throws IOException {
        if (this.mLength < 0L) {
            return new ParcelFileDescriptor.AutoCloseOutputStream(this.mFd);
        }
        return new AutoCloseOutputStream(this);
    }

    @Override
    public int describeContents() {
        return this.mFd.describeContents();
    }

    public long getDeclaredLength() {
        return this.mLength;
    }

    public Bundle getExtras() {
        return this.mExtras;
    }

    public FileDescriptor getFileDescriptor() {
        return this.mFd.getFileDescriptor();
    }

    public long getLength() {
        long l = this.mLength;
        if (l >= 0L) {
            return l;
        }
        l = this.mFd.getStatSize();
        if (l < 0L) {
            l = -1L;
        }
        return l;
    }

    public ParcelFileDescriptor getParcelFileDescriptor() {
        return this.mFd;
    }

    public long getStartOffset() {
        return this.mStartOffset;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{AssetFileDescriptor: ");
        stringBuilder.append(this.mFd);
        stringBuilder.append(" start=");
        stringBuilder.append(this.mStartOffset);
        stringBuilder.append(" len=");
        stringBuilder.append(this.mLength);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        this.mFd.writeToParcel(parcel, n);
        parcel.writeLong(this.mStartOffset);
        parcel.writeLong(this.mLength);
        if (this.mExtras != null) {
            parcel.writeInt(1);
            parcel.writeBundle(this.mExtras);
        } else {
            parcel.writeInt(0);
        }
    }

    public static class AutoCloseInputStream
    extends ParcelFileDescriptor.AutoCloseInputStream {
        private long mRemaining;

        public AutoCloseInputStream(AssetFileDescriptor assetFileDescriptor) throws IOException {
            super(assetFileDescriptor.getParcelFileDescriptor());
            super.skip(assetFileDescriptor.getStartOffset());
            this.mRemaining = (int)assetFileDescriptor.getLength();
        }

        @Override
        public int available() throws IOException {
            long l = this.mRemaining;
            int n = l >= 0L ? (l < Integer.MAX_VALUE ? (int)l : Integer.MAX_VALUE) : super.available();
            return n;
        }

        @Override
        public void mark(int n) {
            if (this.mRemaining >= 0L) {
                return;
            }
            super.mark(n);
        }

        @Override
        public boolean markSupported() {
            if (this.mRemaining >= 0L) {
                return false;
            }
            return super.markSupported();
        }

        @Override
        public int read() throws IOException {
            byte[] arrby = new byte[1];
            int n = this.read(arrby, 0, 1);
            int n2 = -1;
            if (n != -1) {
                n2 = arrby[0] & 255;
            }
            return n2;
        }

        @Override
        public int read(byte[] arrby) throws IOException {
            return this.read(arrby, 0, arrby.length);
        }

        @Override
        public int read(byte[] arrby, int n, int n2) throws IOException {
            long l = this.mRemaining;
            if (l >= 0L) {
                if (l == 0L) {
                    return -1;
                }
                int n3 = n2;
                if ((long)n2 > l) {
                    n3 = (int)l;
                }
                if ((n = super.read(arrby, n, n3)) >= 0) {
                    this.mRemaining -= (long)n;
                }
                return n;
            }
            return super.read(arrby, n, n2);
        }

        @Override
        public void reset() throws IOException {
            synchronized (this) {
                block4 : {
                    long l = this.mRemaining;
                    if (l < 0L) break block4;
                    return;
                }
                super.reset();
                return;
            }
        }

        @Override
        public long skip(long l) throws IOException {
            long l2 = this.mRemaining;
            if (l2 >= 0L) {
                if (l2 == 0L) {
                    return -1L;
                }
                long l3 = l;
                if (l > l2) {
                    l3 = this.mRemaining;
                }
                if ((l = super.skip(l3)) >= 0L) {
                    this.mRemaining -= l;
                }
                return l;
            }
            return super.skip(l);
        }
    }

    public static class AutoCloseOutputStream
    extends ParcelFileDescriptor.AutoCloseOutputStream {
        private long mRemaining;

        public AutoCloseOutputStream(AssetFileDescriptor assetFileDescriptor) throws IOException {
            super(assetFileDescriptor.getParcelFileDescriptor());
            if (assetFileDescriptor.getParcelFileDescriptor().seekTo(assetFileDescriptor.getStartOffset()) >= 0L) {
                this.mRemaining = (int)assetFileDescriptor.getLength();
                return;
            }
            throw new IOException("Unable to seek");
        }

        @Override
        public void write(int n) throws IOException {
            long l;
            if ((l = this.mRemaining--) >= 0L) {
                if (l == 0L) {
                    return;
                }
                super.write(n);
                return;
            }
            super.write(n);
        }

        @Override
        public void write(byte[] arrby) throws IOException {
            long l = this.mRemaining;
            if (l >= 0L) {
                int n;
                if (l == 0L) {
                    return;
                }
                int n2 = n = arrby.length;
                if ((long)n > l) {
                    n2 = (int)l;
                }
                super.write(arrby);
                this.mRemaining -= (long)n2;
                return;
            }
            super.write(arrby);
        }

        @Override
        public void write(byte[] arrby, int n, int n2) throws IOException {
            long l = this.mRemaining;
            if (l >= 0L) {
                if (l == 0L) {
                    return;
                }
                int n3 = n2;
                if ((long)n2 > l) {
                    n3 = (int)l;
                }
                super.write(arrby, n, n3);
                this.mRemaining -= (long)n3;
                return;
            }
            super.write(arrby, n, n2);
        }
    }

}

