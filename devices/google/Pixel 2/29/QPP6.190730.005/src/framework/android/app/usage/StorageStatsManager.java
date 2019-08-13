/*
 * Decompiled with CFR 0.145.
 */
package android.app.usage;

import android.app.usage.ExternalStorageStats;
import android.app.usage.IStorageStatsManager;
import android.app.usage.StorageStats;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.ParcelableException;
import android.os.RemoteException;
import android.os.UserHandle;
import android.os.storage.StorageManager;
import com.android.internal.util.Preconditions;
import java.io.IOException;
import java.util.UUID;

public class StorageStatsManager {
    private final Context mContext;
    private final IStorageStatsManager mService;

    public StorageStatsManager(Context context, IStorageStatsManager iStorageStatsManager) {
        this.mContext = Preconditions.checkNotNull(context);
        this.mService = Preconditions.checkNotNull(iStorageStatsManager);
    }

    @Deprecated
    public long getCacheBytes(String string2) throws IOException {
        return this.getCacheBytes(StorageManager.convert(string2));
    }

    public long getCacheBytes(UUID uUID) throws IOException {
        try {
            long l = this.mService.getCacheBytes(StorageManager.convert(uUID), this.mContext.getOpPackageName());
            return l;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
        catch (ParcelableException parcelableException) {
            parcelableException.maybeRethrow(IOException.class);
            throw new RuntimeException(parcelableException);
        }
    }

    public long getCacheQuotaBytes(String string2, int n) {
        try {
            long l = this.mService.getCacheQuotaBytes(string2, n, this.mContext.getOpPackageName());
            return l;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public long getFreeBytes(String string2) throws IOException {
        return this.getFreeBytes(StorageManager.convert(string2));
    }

    public long getFreeBytes(UUID uUID) throws IOException {
        try {
            long l = this.mService.getFreeBytes(StorageManager.convert(uUID), this.mContext.getOpPackageName());
            return l;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
        catch (ParcelableException parcelableException) {
            parcelableException.maybeRethrow(IOException.class);
            throw new RuntimeException(parcelableException);
        }
    }

    @Deprecated
    public long getTotalBytes(String string2) throws IOException {
        return this.getTotalBytes(StorageManager.convert(string2));
    }

    public long getTotalBytes(UUID uUID) throws IOException {
        try {
            long l = this.mService.getTotalBytes(StorageManager.convert(uUID), this.mContext.getOpPackageName());
            return l;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
        catch (ParcelableException parcelableException) {
            parcelableException.maybeRethrow(IOException.class);
            throw new RuntimeException(parcelableException);
        }
    }

    @Deprecated
    public boolean isQuotaSupported(String string2) {
        return this.isQuotaSupported(StorageManager.convert(string2));
    }

    public boolean isQuotaSupported(UUID uUID) {
        try {
            boolean bl = this.mService.isQuotaSupported(StorageManager.convert(uUID), this.mContext.getOpPackageName());
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean isReservedSupported(UUID uUID) {
        try {
            boolean bl = this.mService.isReservedSupported(StorageManager.convert(uUID), this.mContext.getOpPackageName());
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public ExternalStorageStats queryExternalStatsForUser(String string2, UserHandle userHandle) throws IOException {
        return this.queryExternalStatsForUser(StorageManager.convert(string2), userHandle);
    }

    public ExternalStorageStats queryExternalStatsForUser(UUID object, UserHandle userHandle) throws IOException {
        try {
            object = this.mService.queryExternalStatsForUser(StorageManager.convert((UUID)object), userHandle.getIdentifier(), this.mContext.getOpPackageName());
            return object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
        catch (ParcelableException parcelableException) {
            parcelableException.maybeRethrow(IOException.class);
            throw new RuntimeException(parcelableException);
        }
    }

    @Deprecated
    public StorageStats queryStatsForPackage(String string2, String string3, UserHandle userHandle) throws PackageManager.NameNotFoundException, IOException {
        return this.queryStatsForPackage(StorageManager.convert(string2), string3, userHandle);
    }

    public StorageStats queryStatsForPackage(UUID object, String string2, UserHandle userHandle) throws PackageManager.NameNotFoundException, IOException {
        try {
            object = this.mService.queryStatsForPackage(StorageManager.convert((UUID)object), string2, userHandle.getIdentifier(), this.mContext.getOpPackageName());
            return object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
        catch (ParcelableException parcelableException) {
            parcelableException.maybeRethrow(PackageManager.NameNotFoundException.class);
            parcelableException.maybeRethrow(IOException.class);
            throw new RuntimeException(parcelableException);
        }
    }

    @Deprecated
    public StorageStats queryStatsForUid(String string2, int n) throws IOException {
        return this.queryStatsForUid(StorageManager.convert(string2), n);
    }

    public StorageStats queryStatsForUid(UUID object, int n) throws IOException {
        try {
            object = this.mService.queryStatsForUid(StorageManager.convert((UUID)object), n, this.mContext.getOpPackageName());
            return object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
        catch (ParcelableException parcelableException) {
            parcelableException.maybeRethrow(IOException.class);
            throw new RuntimeException(parcelableException);
        }
    }

    @Deprecated
    public StorageStats queryStatsForUser(String string2, UserHandle userHandle) throws IOException {
        return this.queryStatsForUser(StorageManager.convert(string2), userHandle);
    }

    public StorageStats queryStatsForUser(UUID object, UserHandle userHandle) throws IOException {
        try {
            object = this.mService.queryStatsForUser(StorageManager.convert((UUID)object), userHandle.getIdentifier(), this.mContext.getOpPackageName());
            return object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
        catch (ParcelableException parcelableException) {
            parcelableException.maybeRethrow(IOException.class);
            throw new RuntimeException(parcelableException);
        }
    }
}

