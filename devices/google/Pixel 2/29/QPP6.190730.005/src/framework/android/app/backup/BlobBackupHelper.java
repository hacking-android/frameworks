/*
 * Decompiled with CFR 0.145.
 */
package android.app.backup;

import android.app.backup.BackupDataInputStream;
import android.app.backup.BackupDataOutput;
import android.app.backup.BackupHelper;
import android.os.ParcelFileDescriptor;
import android.util.ArrayMap;
import android.util.Log;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.CRC32;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;

public abstract class BlobBackupHelper
implements BackupHelper {
    private static final boolean DEBUG = false;
    private static final String TAG = "BlobBackupHelper";
    private final int mCurrentBlobVersion;
    private final String[] mKeys;

    public BlobBackupHelper(int n, String ... arrstring) {
        this.mCurrentBlobVersion = n;
        this.mKeys = arrstring;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private long checksum(byte[] arrby) {
        if (arrby == null) return -1L;
        try {
            CRC32 cRC32 = new CRC32();
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(arrby);
            arrby = new byte[4096];
            do {
                int n;
                if ((n = byteArrayInputStream.read(arrby)) < 0) {
                    return cRC32.getValue();
                }
                cRC32.update(arrby, 0, n);
            } while (true);
        }
        catch (Exception exception) {
            // empty catch block
        }
        return -1L;
    }

    private byte[] deflate(byte[] arrby) {
        ByteArrayOutputStream byteArrayOutputStream = null;
        byte[] arrby2 = byteArrayOutputStream;
        if (arrby != null) {
            try {
                arrby2 = new ByteArrayOutputStream();
                FilterOutputStream filterOutputStream = new DataOutputStream((OutputStream)arrby2);
                ((DataOutputStream)filterOutputStream).writeInt(this.mCurrentBlobVersion);
                filterOutputStream = new DeflaterOutputStream((OutputStream)arrby2);
                filterOutputStream.write(arrby);
                ((DeflaterOutputStream)filterOutputStream).close();
                arrby2 = arrby2.toByteArray();
            }
            catch (IOException iOException) {
                arrby2 = new StringBuilder();
                arrby2.append("Unable to process payload: ");
                arrby2.append(iOException.getMessage());
                Log.w(TAG, arrby2.toString());
                arrby2 = byteArrayOutputStream;
            }
        }
        return arrby2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private byte[] inflate(byte[] object) {
        ByteArrayInputStream byteArrayInputStream = null;
        byte[] arrby = byteArrayInputStream;
        if (object == null) return arrby;
        try {
            arrby = new ByteArrayInputStream((byte[])object);
            object = new DataInputStream((InputStream)arrby);
            int n = ((DataInputStream)object).readInt();
            if (n > this.mCurrentBlobVersion) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Saved payload from unrecognized version ");
                ((StringBuilder)object).append(n);
                Log.w(TAG, ((StringBuilder)object).toString());
                return null;
            }
            object = new InflaterInputStream((InputStream)arrby);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            arrby = new byte[4096];
            do {
                if ((n = ((FilterInputStream)object).read(arrby)) <= 0) {
                    ((InflaterInputStream)object).close();
                    byteArrayOutputStream.flush();
                    return byteArrayOutputStream.toByteArray();
                }
                byteArrayOutputStream.write(arrby, 0, n);
            } while (true);
        }
        catch (IOException iOException) {
            arrby = new StringBuilder();
            arrby.append("Unable to process restored payload: ");
            arrby.append(iOException.getMessage());
            Log.w(TAG, arrby.toString());
            return byteArrayInputStream;
        }
    }

    private ArrayMap<String, Long> readOldState(ParcelFileDescriptor object) {
        ArrayMap<String, Long> arrayMap;
        block7 : {
            int n;
            block6 : {
                arrayMap = new ArrayMap<String, Long>();
                object = new DataInputStream(new FileInputStream(((ParcelFileDescriptor)object).getFileDescriptor()));
                n = ((DataInputStream)object).readInt();
                if (n > this.mCurrentBlobVersion) break block6;
                int n2 = ((DataInputStream)object).readInt();
                for (n = 0; n < n2; ++n) {
                    arrayMap.put(((DataInputStream)object).readUTF(), ((DataInputStream)object).readLong());
                }
                break block7;
            }
            try {
                object = new StringBuilder();
                ((StringBuilder)object).append("Prior state from unrecognized version ");
                ((StringBuilder)object).append(n);
                Log.w(TAG, ((StringBuilder)object).toString());
            }
            catch (Exception exception) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Error examining prior backup state ");
                ((StringBuilder)object).append(exception.getMessage());
                Log.e(TAG, ((StringBuilder)object).toString());
                arrayMap.clear();
            }
            catch (EOFException eOFException) {
                arrayMap.clear();
            }
        }
        return arrayMap;
    }

    private void writeBackupState(ArrayMap<String, Long> arrayMap, ParcelFileDescriptor closeable) {
        int n;
        Object object;
        block7 : {
            block6 : {
                object = new FileOutputStream(((ParcelFileDescriptor)closeable).getFileDescriptor());
                closeable = new DataOutputStream((OutputStream)object);
                ((DataOutputStream)closeable).writeInt(this.mCurrentBlobVersion);
                if (arrayMap == null) break block6;
                n = arrayMap.size();
                break block7;
            }
            n = 0;
        }
        ((DataOutputStream)closeable).writeInt(n);
        for (int i = 0; i < n; ++i) {
            try {
                object = arrayMap.keyAt(i);
                long l = arrayMap.valueAt(i);
                ((DataOutputStream)closeable).writeUTF((String)object);
                ((DataOutputStream)closeable).writeLong(l);
                continue;
            }
            catch (IOException iOException) {
                Log.e(TAG, "Unable to write updated state", iOException);
                break;
            }
        }
    }

    protected abstract void applyRestoredPayload(String var1, byte[] var2);

    protected abstract byte[] getBackupPayload(String var1);

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public void performBackup(ParcelFileDescriptor object, BackupDataOutput object2, ParcelFileDescriptor parcelFileDescriptor) {
        Throwable throwable2222;
        block6 : {
            ArrayMap<String, Long> arrayMap = this.readOldState((ParcelFileDescriptor)((Object)object));
            object = new ArrayMap<K, V>();
            for (String string2 : this.mKeys) {
                byte[] arrby = this.deflate(this.getBackupPayload(string2));
                long l = this.checksum(arrby);
                object.put(string2, l);
                Long l2 = arrayMap.get(string2);
                if (l2 != null && l == l2) continue;
                if (arrby != null) {
                    ((BackupDataOutput)object2).writeEntityHeader(string2, arrby.length);
                    ((BackupDataOutput)object2).writeEntityData(arrby, arrby.length);
                    continue;
                }
                ((BackupDataOutput)object2).writeEntityHeader(string2, -1);
            }
            {
                catch (Throwable throwable2222) {
                    break block6;
                }
                catch (Exception exception) {}
                {
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("Unable to record notification state: ");
                    ((StringBuilder)object2).append(exception.getMessage());
                    Log.w("BlobBackupHelper", ((StringBuilder)object2).toString());
                    object.clear();
                }
            }
            this.writeBackupState(object, parcelFileDescriptor);
            return;
        }
        this.writeBackupState(object, parcelFileDescriptor);
        throw throwable2222;
    }

    @Override
    public void restoreEntity(BackupDataInputStream object) {
        String string2 = ((BackupDataInputStream)object).getKey();
        int n = 0;
        do {
            if (n >= this.mKeys.length || string2.equals(this.mKeys[n])) break;
            ++n;
        } while (true);
        try {
            if (n >= this.mKeys.length) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Unrecognized key ");
                ((StringBuilder)object).append(string2);
                ((StringBuilder)object).append(", ignoring");
                Log.e("BlobBackupHelper", ((StringBuilder)object).toString());
                return;
            }
            byte[] arrby = new byte[((BackupDataInputStream)object).size()];
            ((BackupDataInputStream)object).read(arrby);
            this.applyRestoredPayload(string2, this.inflate(arrby));
        }
        catch (Exception exception) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Exception restoring entity ");
            stringBuilder.append(string2);
            stringBuilder.append(" : ");
            stringBuilder.append(exception.getMessage());
            Log.e("BlobBackupHelper", stringBuilder.toString());
        }
    }

    @Override
    public void writeNewStateDescription(ParcelFileDescriptor parcelFileDescriptor) {
        this.writeBackupState(null, parcelFileDescriptor);
    }
}

