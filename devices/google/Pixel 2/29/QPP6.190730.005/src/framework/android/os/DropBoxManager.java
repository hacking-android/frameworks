/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.TransactionTooLargeException;
import android.util.Log;
import com.android.internal.os.IDropBoxManagerService;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

public class DropBoxManager {
    public static final String ACTION_DROPBOX_ENTRY_ADDED = "android.intent.action.DROPBOX_ENTRY_ADDED";
    public static final String EXTRA_DROPPED_COUNT = "android.os.extra.DROPPED_COUNT";
    public static final String EXTRA_TAG = "tag";
    public static final String EXTRA_TIME = "time";
    private static final int HAS_BYTE_ARRAY = 8;
    public static final int IS_EMPTY = 1;
    public static final int IS_GZIPPED = 4;
    public static final int IS_TEXT = 2;
    private static final String TAG = "DropBoxManager";
    private final Context mContext;
    @UnsupportedAppUsage
    private final IDropBoxManagerService mService;

    protected DropBoxManager() {
        this.mContext = null;
        this.mService = null;
    }

    public DropBoxManager(Context context, IDropBoxManagerService iDropBoxManagerService) {
        this.mContext = context;
        this.mService = iDropBoxManagerService;
    }

    public void addData(String string2, byte[] arrby, int n) {
        if (arrby != null) {
            try {
                IDropBoxManagerService iDropBoxManagerService = this.mService;
                Entry entry = new Entry(string2, 0L, arrby, n);
                iDropBoxManagerService.add(entry);
                return;
            }
            catch (RemoteException remoteException) {
                if (remoteException instanceof TransactionTooLargeException && this.mContext.getApplicationInfo().targetSdkVersion < 24) {
                    Log.e(TAG, "App sent too much data, so it was ignored", remoteException);
                    return;
                }
                throw remoteException.rethrowFromSystemServer();
            }
        }
        throw new NullPointerException("data == null");
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public void addFile(String object, File file, int n) throws IOException {
        Throwable throwable2222;
        if (file == null) throw new NullPointerException("file == null");
        object = new Entry((String)object, 0L, file, n);
        this.mService.add((Entry)object);
        ((Entry)object).close();
        return;
        {
            catch (Throwable throwable2222) {
            }
            catch (RemoteException remoteException) {}
            {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        ((Entry)object).close();
        throw throwable2222;
    }

    public void addText(String string2, String string3) {
        try {
            IDropBoxManagerService iDropBoxManagerService = this.mService;
            Entry entry = new Entry(string2, 0L, string3);
            iDropBoxManagerService.add(entry);
            return;
        }
        catch (RemoteException remoteException) {
            if (remoteException instanceof TransactionTooLargeException && this.mContext.getApplicationInfo().targetSdkVersion < 24) {
                Log.e(TAG, "App sent too much data, so it was ignored", remoteException);
                return;
            }
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public Entry getNextEntry(String object, long l) {
        try {
            object = this.mService.getNextEntry((String)object, l, this.mContext.getOpPackageName());
            return object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
        catch (SecurityException securityException) {
            if (this.mContext.getApplicationInfo().targetSdkVersion < 28) {
                Log.w(TAG, securityException.getMessage());
                return null;
            }
            throw securityException;
        }
    }

    public boolean isTagEnabled(String string2) {
        try {
            boolean bl = this.mService.isTagEnabled(string2);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public static class Entry
    implements Parcelable,
    Closeable {
        public static final Parcelable.Creator<Entry> CREATOR = new Parcelable.Creator(){

            public Entry createFromParcel(Parcel parcel) {
                String string2 = parcel.readString();
                long l = parcel.readLong();
                int n = parcel.readInt();
                if ((n & 8) != 0) {
                    return new Entry(string2, l, parcel.createByteArray(), n & -9);
                }
                return new Entry(string2, l, ParcelFileDescriptor.CREATOR.createFromParcel(parcel), n);
            }

            public Entry[] newArray(int n) {
                return new Entry[n];
            }
        };
        private final byte[] mData;
        private final ParcelFileDescriptor mFileDescriptor;
        private final int mFlags;
        private final String mTag;
        private final long mTimeMillis;

        public Entry(String string2, long l) {
            if (string2 != null) {
                this.mTag = string2;
                this.mTimeMillis = l;
                this.mData = null;
                this.mFileDescriptor = null;
                this.mFlags = 1;
                return;
            }
            throw new NullPointerException("tag == null");
        }

        public Entry(String charSequence, long l, ParcelFileDescriptor parcelFileDescriptor, int n) {
            if (charSequence != null) {
                boolean bl = false;
                boolean bl2 = (n & 1) != 0;
                if (parcelFileDescriptor == null) {
                    bl = true;
                }
                if (bl2 == bl) {
                    this.mTag = charSequence;
                    this.mTimeMillis = l;
                    this.mData = null;
                    this.mFileDescriptor = parcelFileDescriptor;
                    this.mFlags = n;
                    return;
                }
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Bad flags: ");
                ((StringBuilder)charSequence).append(n);
                throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
            }
            throw new NullPointerException("tag == null");
        }

        public Entry(String charSequence, long l, File file, int n) throws IOException {
            if (charSequence != null) {
                if ((n & 1) == 0) {
                    this.mTag = charSequence;
                    this.mTimeMillis = l;
                    this.mData = null;
                    this.mFileDescriptor = ParcelFileDescriptor.open(file, 268435456);
                    this.mFlags = n;
                    return;
                }
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Bad flags: ");
                ((StringBuilder)charSequence).append(n);
                throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
            }
            throw new NullPointerException("tag == null");
        }

        public Entry(String string2, long l, String string3) {
            if (string2 != null) {
                if (string3 != null) {
                    this.mTag = string2;
                    this.mTimeMillis = l;
                    this.mData = string3.getBytes();
                    this.mFileDescriptor = null;
                    this.mFlags = 2;
                    return;
                }
                throw new NullPointerException("text == null");
            }
            throw new NullPointerException("tag == null");
        }

        public Entry(String charSequence, long l, byte[] arrby, int n) {
            if (charSequence != null) {
                boolean bl = false;
                boolean bl2 = (n & 1) != 0;
                if (arrby == null) {
                    bl = true;
                }
                if (bl2 == bl) {
                    this.mTag = charSequence;
                    this.mTimeMillis = l;
                    this.mData = arrby;
                    this.mFileDescriptor = null;
                    this.mFlags = n;
                    return;
                }
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Bad flags: ");
                ((StringBuilder)charSequence).append(n);
                throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
            }
            throw new NullPointerException("tag == null");
        }

        @Override
        public void close() {
            try {
                if (this.mFileDescriptor != null) {
                    this.mFileDescriptor.close();
                }
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }

        @Override
        public int describeContents() {
            int n = this.mFileDescriptor != null ? 1 : 0;
            return n;
        }

        public int getFlags() {
            return this.mFlags & -5;
        }

        public InputStream getInputStream() throws IOException {
            block6 : {
                Object object;
                block5 : {
                    block4 : {
                        object = this.mData;
                        if (object == null) break block4;
                        object = new ByteArrayInputStream((byte[])object);
                        break block5;
                    }
                    object = this.mFileDescriptor;
                    if (object == null) break block6;
                    object = new ParcelFileDescriptor.AutoCloseInputStream((ParcelFileDescriptor)object);
                }
                if ((this.mFlags & 4) != 0) {
                    object = new GZIPInputStream((InputStream)object);
                }
                return object;
            }
            return null;
        }

        public String getTag() {
            return this.mTag;
        }

        /*
         * Loose catch block
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        public String getText(int n) {
            Object object2;
            Object object;
            int n2;
            Object object3;
            block15 : {
                if ((this.mFlags & 2) == 0) {
                    return null;
                }
                object2 = this.mData;
                if (object2 != null) {
                    return new String((byte[])object2, 0, Math.min(n, ((byte[])object2).length));
                }
                object3 = null;
                object = null;
                object2 = this.getInputStream();
                if (object2 != null) break block15;
                if (object2 == null) return null;
                try {
                    ((InputStream)object2).close();
                    return null;
                }
                catch (IOException iOException) {
                    // empty catch block
                }
                return null;
            }
            object = object2;
            object3 = object2;
            Object object4 = new byte[n];
            int n3 = 0;
            int n4 = 0;
            do {
                n2 = n3;
                if (n4 < 0) break;
                n2 = n3 = (n4 = n3 + n4);
                if (n4 >= n) break;
                object = object2;
                object3 = object2;
                n4 = ((InputStream)object2).read((byte[])object4, n3, n - n3);
            } while (true);
            object = object2;
            object3 = object2;
            object4 = new String((byte[])object4, 0, n2);
            try {
                ((InputStream)object2).close();
                return object4;
            }
            catch (IOException iOException) {
                // empty catch block
            }
            return object4;
            catch (Throwable throwable) {
                if (object == null) throw throwable;
                try {
                    ((InputStream)object).close();
                    throw throwable;
                }
                catch (IOException iOException) {
                    // empty catch block
                }
                throw throwable;
            }
            catch (IOException iOException) {
                if (object3 == null) return null;
                try {
                    ((InputStream)object3).close();
                    return null;
                }
                catch (IOException iOException2) {
                    // empty catch block
                }
                return null;
            }
        }

        public long getTimeMillis() {
            return this.mTimeMillis;
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeString(this.mTag);
            parcel.writeLong(this.mTimeMillis);
            if (this.mFileDescriptor != null) {
                parcel.writeInt(this.mFlags & -9);
                this.mFileDescriptor.writeToParcel(parcel, n);
            } else {
                parcel.writeInt(this.mFlags | 8);
                parcel.writeByteArray(this.mData);
            }
        }

    }

}

