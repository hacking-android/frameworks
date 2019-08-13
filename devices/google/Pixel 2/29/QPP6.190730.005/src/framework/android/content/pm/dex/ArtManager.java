/*
 * Decompiled with CFR 0.145.
 */
package android.content.pm.dex;

import android.annotation.SystemApi;
import android.content.Context;
import android.content.pm.dex.IArtManager;
import android.content.pm.dex.ISnapshotRuntimeProfileCallback;
import android.content.pm.dex._$$Lambda$ArtManager$SnapshotRuntimeProfileCallbackDelegate$OOdGv4iFxuVpH2kzFMr8KwX3X8s;
import android.content.pm.dex._$$Lambda$ArtManager$SnapshotRuntimeProfileCallbackDelegate$m2Wpsf6LxhWt_1tS6tQt3B8QcGo;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.util.Slog;
import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.Executor;

@SystemApi
public class ArtManager {
    public static final int PROFILE_APPS = 0;
    public static final int PROFILE_BOOT_IMAGE = 1;
    public static final int SNAPSHOT_FAILED_CODE_PATH_NOT_FOUND = 1;
    public static final int SNAPSHOT_FAILED_INTERNAL_ERROR = 2;
    public static final int SNAPSHOT_FAILED_PACKAGE_NOT_FOUND = 0;
    private static final String TAG = "ArtManager";
    private final IArtManager mArtManager;
    private final Context mContext;

    public ArtManager(Context context, IArtManager iArtManager) {
        this.mContext = context;
        this.mArtManager = iArtManager;
    }

    public static String getCurrentProfilePath(String string2, int n, String string3) {
        return new File(Environment.getDataProfilesDePackageDirectory(n, string2), ArtManager.getProfileName(string3)).getAbsolutePath();
    }

    public static String getProfileName(String string2) {
        if (string2 == null) {
            string2 = "primary.prof";
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append(".split.prof");
            string2 = stringBuilder.toString();
        }
        return string2;
    }

    public static File getProfileSnapshotFileForName(String object, String string2) {
        object = Environment.getDataRefProfilesDePackageDirectory((String)object);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append(".snapshot");
        return new File((File)object, stringBuilder.toString());
    }

    public boolean isRuntimeProfilingEnabled(int n) {
        try {
            boolean bl = this.mArtManager.isRuntimeProfilingEnabled(n, this.mContext.getOpPackageName());
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowAsRuntimeException();
        }
    }

    public void snapshotRuntimeProfile(int n, String string2, String string3, Executor object, SnapshotRuntimeProfileCallback snapshotRuntimeProfileCallback) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Requesting profile snapshot for ");
        stringBuilder.append(string2);
        stringBuilder.append(":");
        stringBuilder.append(string3);
        Slog.d(TAG, stringBuilder.toString());
        object = new SnapshotRuntimeProfileCallbackDelegate(snapshotRuntimeProfileCallback, (Executor)object);
        try {
            this.mArtManager.snapshotRuntimeProfile(n, string2, string3, (ISnapshotRuntimeProfileCallback)object, this.mContext.getOpPackageName());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowAsRuntimeException();
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface ProfileType {
    }

    public static abstract class SnapshotRuntimeProfileCallback {
        public abstract void onError(int var1);

        public abstract void onSuccess(ParcelFileDescriptor var1);
    }

    private static class SnapshotRuntimeProfileCallbackDelegate
    extends ISnapshotRuntimeProfileCallback.Stub {
        private final SnapshotRuntimeProfileCallback mCallback;
        private final Executor mExecutor;

        private SnapshotRuntimeProfileCallbackDelegate(SnapshotRuntimeProfileCallback snapshotRuntimeProfileCallback, Executor executor) {
            this.mCallback = snapshotRuntimeProfileCallback;
            this.mExecutor = executor;
        }

        public /* synthetic */ void lambda$onError$1$ArtManager$SnapshotRuntimeProfileCallbackDelegate(int n) {
            this.mCallback.onError(n);
        }

        public /* synthetic */ void lambda$onSuccess$0$ArtManager$SnapshotRuntimeProfileCallbackDelegate(ParcelFileDescriptor parcelFileDescriptor) {
            this.mCallback.onSuccess(parcelFileDescriptor);
        }

        @Override
        public void onError(int n) {
            this.mExecutor.execute(new _$$Lambda$ArtManager$SnapshotRuntimeProfileCallbackDelegate$m2Wpsf6LxhWt_1tS6tQt3B8QcGo(this, n));
        }

        @Override
        public void onSuccess(ParcelFileDescriptor parcelFileDescriptor) {
            this.mExecutor.execute(new _$$Lambda$ArtManager$SnapshotRuntimeProfileCallbackDelegate$OOdGv4iFxuVpH2kzFMr8KwX3X8s(this, parcelFileDescriptor));
        }
    }

}

