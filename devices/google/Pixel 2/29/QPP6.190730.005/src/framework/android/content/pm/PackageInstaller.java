/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.pm.-$
 *  android.content.pm.-$$Lambda
 *  android.content.pm.-$$Lambda$B12dZLpdwpXn89QSesmkaZjD72Q
 *  android.content.pm.-$$Lambda$T1UQAuePWRRmVQ1KzTyMAktZUPM
 *  android.content.pm.-$$Lambda$ciir_QAmv6RwJro4I58t77dPnxU
 *  android.content.pm.-$$Lambda$n3uXeb1v-YRmq_BWTfosEqUUr9g
 *  android.content.pm.-$$Lambda$zO9HBUVgPeroyDQPLJE-MNMvSqc
 *  android.system.ErrnoException
 *  android.system.Os
 */
package android.content.pm;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.app.AppGlobals;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.-$;
import android.content.pm.IPackageInstaller;
import android.content.pm.IPackageInstallerCallback;
import android.content.pm.IPackageInstallerSession;
import android.content.pm.PackageManager;
import android.content.pm.ParceledListSlice;
import android.content.pm.VersionedPackage;
import android.content.pm._$$Lambda$B12dZLpdwpXn89QSesmkaZjD72Q;
import android.content.pm._$$Lambda$T1UQAuePWRRmVQ1KzTyMAktZUPM;
import android.content.pm._$$Lambda$ciir_QAmv6RwJro4I58t77dPnxU;
import android.content.pm._$$Lambda$n3uXeb1v_YRmq_BWTfosEqUUr9g;
import android.content.pm._$$Lambda$zO9HBUVgPeroyDQPLJE_MNMvSqc;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.FileBridge;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.ParcelableException;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.system.ErrnoException;
import android.system.Os;
import android.util.ArraySet;
import android.util.ExceptionUtils;
import com.android.internal.util.IndentingPrintWriter;
import com.android.internal.util.Preconditions;
import com.android.internal.util.function.pooled.PooledLambda;
import java.io.Closeable;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;

public class PackageInstaller {
    public static final String ACTION_CONFIRM_INSTALL = "android.content.pm.action.CONFIRM_INSTALL";
    public static final String ACTION_SESSION_COMMITTED = "android.content.pm.action.SESSION_COMMITTED";
    public static final String ACTION_SESSION_DETAILS = "android.content.pm.action.SESSION_DETAILS";
    public static final String ACTION_SESSION_UPDATED = "android.content.pm.action.SESSION_UPDATED";
    public static final boolean ENABLE_REVOCABLE_FD = SystemProperties.getBoolean("fw.revocable_fd", false);
    public static final String EXTRA_CALLBACK = "android.content.pm.extra.CALLBACK";
    public static final String EXTRA_LEGACY_BUNDLE = "android.content.pm.extra.LEGACY_BUNDLE";
    public static final String EXTRA_LEGACY_STATUS = "android.content.pm.extra.LEGACY_STATUS";
    public static final String EXTRA_OTHER_PACKAGE_NAME = "android.content.pm.extra.OTHER_PACKAGE_NAME";
    public static final String EXTRA_PACKAGE_NAME = "android.content.pm.extra.PACKAGE_NAME";
    @Deprecated
    public static final String EXTRA_PACKAGE_NAMES = "android.content.pm.extra.PACKAGE_NAMES";
    public static final String EXTRA_SESSION = "android.content.pm.extra.SESSION";
    public static final String EXTRA_SESSION_ID = "android.content.pm.extra.SESSION_ID";
    public static final String EXTRA_STATUS = "android.content.pm.extra.STATUS";
    public static final String EXTRA_STATUS_MESSAGE = "android.content.pm.extra.STATUS_MESSAGE";
    public static final String EXTRA_STORAGE_PATH = "android.content.pm.extra.STORAGE_PATH";
    public static final int STATUS_FAILURE = 1;
    public static final int STATUS_FAILURE_ABORTED = 3;
    public static final int STATUS_FAILURE_BLOCKED = 2;
    public static final int STATUS_FAILURE_CONFLICT = 5;
    public static final int STATUS_FAILURE_INCOMPATIBLE = 7;
    public static final int STATUS_FAILURE_INVALID = 4;
    public static final int STATUS_FAILURE_STORAGE = 6;
    public static final int STATUS_PENDING_USER_ACTION = -1;
    public static final int STATUS_SUCCESS = 0;
    private static final String TAG = "PackageInstaller";
    private final ArrayList<SessionCallbackDelegate> mDelegates = new ArrayList();
    private final IPackageInstaller mInstaller;
    private final String mInstallerPackageName;
    private final int mUserId;

    public PackageInstaller(IPackageInstaller iPackageInstaller, String string2, int n) {
        this.mInstaller = iPackageInstaller;
        this.mInstallerPackageName = string2;
        this.mUserId = n;
    }

    public void abandonSession(int n) {
        try {
            this.mInstaller.abandonSession(n);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public void addSessionCallback(SessionCallback sessionCallback) {
        this.registerSessionCallback(sessionCallback);
    }

    @Deprecated
    public void addSessionCallback(SessionCallback sessionCallback, Handler handler) {
        this.registerSessionCallback(sessionCallback, handler);
    }

    public int createSession(SessionParams sessionParams) throws IOException {
        try {
            String string2 = sessionParams.installerPackageName == null ? this.mInstallerPackageName : sessionParams.installerPackageName;
            int n = this.mInstaller.createSession(sessionParams, string2, this.mUserId);
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
        catch (RuntimeException runtimeException) {
            ExceptionUtils.maybeUnwrapIOException(runtimeException);
            throw runtimeException;
        }
    }

    public SessionInfo getActiveStagedSession() {
        for (SessionInfo sessionInfo : this.getStagedSessions()) {
            if (sessionInfo.isStagedSessionApplied() || sessionInfo.isStagedSessionFailed() || sessionInfo.getParentSessionId() != -1 || !sessionInfo.isCommitted()) continue;
            return sessionInfo;
        }
        return null;
    }

    public List<SessionInfo> getAllSessions() {
        try {
            List list = this.mInstaller.getAllSessions(this.mUserId).getList();
            return list;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public List<SessionInfo> getMySessions() {
        try {
            List list = this.mInstaller.getMySessions(this.mInstallerPackageName, this.mUserId).getList();
            return list;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public SessionInfo getSessionInfo(int n) {
        try {
            SessionInfo sessionInfo = this.mInstaller.getSessionInfo(n);
            return sessionInfo;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public List<SessionInfo> getStagedSessions() {
        try {
            List list = this.mInstaller.getStagedSessions().getList();
            return list;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void installExistingPackage(String string2, int n, IntentSender intentSender) {
        Preconditions.checkNotNull(string2, "packageName cannot be null");
        try {
            this.mInstaller.installExistingPackage(string2, 4194304, n, intentSender, this.mUserId, null);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public Session openSession(int n) throws IOException {
        RuntimeException runtimeException222;
        block4 : {
            try {
                return new Session(this.mInstaller.openSession(n));
            }
            catch (RuntimeException runtimeException222) {
                break block4;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        ExceptionUtils.maybeUnwrapIOException(runtimeException222);
        throw runtimeException222;
    }

    public void registerSessionCallback(SessionCallback sessionCallback) {
        this.registerSessionCallback(sessionCallback, new Handler());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void registerSessionCallback(SessionCallback sessionCallback, Handler handler) {
        ArrayList<SessionCallbackDelegate> arrayList = this.mDelegates;
        synchronized (arrayList) {
            HandlerExecutor handlerExecutor = new HandlerExecutor(handler);
            SessionCallbackDelegate sessionCallbackDelegate = new SessionCallbackDelegate(sessionCallback, handlerExecutor);
            try {
                this.mInstaller.registerCallback(sessionCallbackDelegate, this.mUserId);
                this.mDelegates.add(sessionCallbackDelegate);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
            return;
        }
    }

    @Deprecated
    public void removeSessionCallback(SessionCallback sessionCallback) {
        this.unregisterSessionCallback(sessionCallback);
    }

    @SystemApi
    public void setPermissionsResult(int n, boolean bl) {
        try {
            this.mInstaller.setPermissionsResult(n, bl);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void uninstall(VersionedPackage versionedPackage, int n, IntentSender intentSender) {
        Preconditions.checkNotNull(versionedPackage, "versionedPackage cannot be null");
        try {
            this.mInstaller.uninstall(versionedPackage, this.mInstallerPackageName, n, intentSender, this.mUserId);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void uninstall(VersionedPackage versionedPackage, IntentSender intentSender) {
        this.uninstall(versionedPackage, 0, intentSender);
    }

    public void uninstall(String string2, int n, IntentSender intentSender) {
        this.uninstall(new VersionedPackage(string2, -1), n, intentSender);
    }

    public void uninstall(String string2, IntentSender intentSender) {
        this.uninstall(string2, 0, intentSender);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void unregisterSessionCallback(SessionCallback sessionCallback) {
        ArrayList<SessionCallbackDelegate> arrayList = this.mDelegates;
        synchronized (arrayList) {
            Iterator<SessionCallbackDelegate> iterator = this.mDelegates.iterator();
            while (iterator.hasNext()) {
                SessionCallbackDelegate sessionCallbackDelegate = iterator.next();
                SessionCallback sessionCallback2 = sessionCallbackDelegate.mCallback;
                if (sessionCallback2 != sessionCallback) continue;
                try {
                    this.mInstaller.unregisterCallback(sessionCallbackDelegate);
                    iterator.remove();
                }
                catch (RemoteException remoteException) {
                    throw remoteException.rethrowFromSystemServer();
                }
            }
            return;
        }
    }

    public void updateSessionAppIcon(int n, Bitmap bitmap) {
        try {
            this.mInstaller.updateSessionAppIcon(n, bitmap);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void updateSessionAppLabel(int n, CharSequence charSequence) {
        if (charSequence != null) {
            try {
                charSequence = charSequence.toString();
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        } else {
            charSequence = null;
        }
        this.mInstaller.updateSessionAppLabel(n, (String)charSequence);
    }

    public static class Session
    implements Closeable {
        protected final IPackageInstallerSession mSession;

        public Session(IPackageInstallerSession iPackageInstallerSession) {
            this.mSession = iPackageInstallerSession;
        }

        public void abandon() {
            try {
                this.mSession.abandon();
                return;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }

        public void addChildSessionId(int n) {
            try {
                this.mSession.addChildSessionId(n);
            }
            catch (RemoteException remoteException) {
                remoteException.rethrowFromSystemServer();
            }
        }

        @UnsupportedAppUsage
        public void addProgress(float f) {
            try {
                this.mSession.addClientProgress(f);
                return;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }

        @Override
        public void close() {
            try {
                this.mSession.close();
                return;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }

        public void commit(IntentSender intentSender) {
            try {
                this.mSession.commit(intentSender, false);
                return;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }

        @SystemApi
        public void commitTransferred(IntentSender intentSender) {
            try {
                this.mSession.commit(intentSender, true);
                return;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }

        public void fsync(OutputStream outputStream) throws IOException {
            block7 : {
                if (ENABLE_REVOCABLE_FD) {
                    if (outputStream instanceof ParcelFileDescriptor.AutoCloseOutputStream) {
                        try {
                            Os.fsync((FileDescriptor)((ParcelFileDescriptor.AutoCloseOutputStream)outputStream).getFD());
                        }
                        catch (ErrnoException errnoException) {
                            throw errnoException.rethrowAsIOException();
                        }
                    }
                    throw new IllegalArgumentException("Unrecognized stream");
                }
                if (!(outputStream instanceof FileBridge.FileBridgeOutputStream)) break block7;
                ((FileBridge.FileBridgeOutputStream)outputStream).fsync();
                return;
            }
            throw new IllegalArgumentException("Unrecognized stream");
        }

        public int[] getChildSessionIds() {
            try {
                int[] arrn = this.mSession.getChildSessionIds();
                return arrn;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }

        public String[] getNames() throws IOException {
            try {
                String[] arrstring = this.mSession.getNames();
                return arrstring;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
            catch (RuntimeException runtimeException) {
                ExceptionUtils.maybeUnwrapIOException(runtimeException);
                throw runtimeException;
            }
        }

        public int getParentSessionId() {
            try {
                int n = this.mSession.getParentSessionId();
                return n;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }

        public boolean isMultiPackage() {
            try {
                boolean bl = this.mSession.isMultiPackage();
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }

        public boolean isStaged() {
            try {
                boolean bl = this.mSession.isStaged();
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }

        public InputStream openRead(String object) throws IOException {
            try {
                object = new ParcelFileDescriptor.AutoCloseInputStream(this.mSession.openRead((String)object));
                return object;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
            catch (RuntimeException runtimeException) {
                ExceptionUtils.maybeUnwrapIOException(runtimeException);
                throw runtimeException;
            }
        }

        public OutputStream openWrite(String object, long l, long l2) throws IOException {
            try {
                if (ENABLE_REVOCABLE_FD) {
                    return new ParcelFileDescriptor.AutoCloseOutputStream(this.mSession.openWrite((String)object, l, l2));
                }
                object = new FileBridge.FileBridgeOutputStream(this.mSession.openWrite((String)object, l, l2));
                return object;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
            catch (RuntimeException runtimeException) {
                ExceptionUtils.maybeUnwrapIOException(runtimeException);
                throw runtimeException;
            }
        }

        public void removeChildSessionId(int n) {
            try {
                this.mSession.removeChildSessionId(n);
            }
            catch (RemoteException remoteException) {
                remoteException.rethrowFromSystemServer();
            }
        }

        public void removeSplit(String string2) throws IOException {
            try {
                this.mSession.removeSplit(string2);
                return;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
            catch (RuntimeException runtimeException) {
                ExceptionUtils.maybeUnwrapIOException(runtimeException);
                throw runtimeException;
            }
        }

        @Deprecated
        public void setProgress(float f) {
            this.setStagingProgress(f);
        }

        public void setStagingProgress(float f) {
            try {
                this.mSession.setClientProgress(f);
                return;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }

        public void transfer(String string2) throws PackageManager.NameNotFoundException {
            Preconditions.checkNotNull(string2);
            try {
                this.mSession.transfer(string2);
                return;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
            catch (ParcelableException parcelableException) {
                parcelableException.maybeRethrow(PackageManager.NameNotFoundException.class);
                throw new RuntimeException(parcelableException);
            }
        }

        public void write(String string2, long l, long l2, ParcelFileDescriptor parcelFileDescriptor) throws IOException {
            try {
                this.mSession.write(string2, l, l2, parcelFileDescriptor);
                return;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
            catch (RuntimeException runtimeException) {
                ExceptionUtils.maybeUnwrapIOException(runtimeException);
                throw runtimeException;
            }
        }
    }

    public static abstract class SessionCallback {
        public abstract void onActiveChanged(int var1, boolean var2);

        public abstract void onBadgingChanged(int var1);

        public abstract void onCreated(int var1);

        public abstract void onFinished(int var1, boolean var2);

        public abstract void onProgressChanged(int var1, float var2);
    }

    static class SessionCallbackDelegate
    extends IPackageInstallerCallback.Stub {
        private static final int MSG_SESSION_ACTIVE_CHANGED = 3;
        private static final int MSG_SESSION_BADGING_CHANGED = 2;
        private static final int MSG_SESSION_CREATED = 1;
        private static final int MSG_SESSION_FINISHED = 5;
        private static final int MSG_SESSION_PROGRESS_CHANGED = 4;
        final SessionCallback mCallback;
        final Executor mExecutor;

        SessionCallbackDelegate(SessionCallback sessionCallback, Executor executor) {
            this.mCallback = sessionCallback;
            this.mExecutor = executor;
        }

        @Override
        public void onSessionActiveChanged(int n, boolean bl) {
            this.mExecutor.execute((Runnable)((Object)PooledLambda.obtainRunnable(_$$Lambda$T1UQAuePWRRmVQ1KzTyMAktZUPM.INSTANCE, this.mCallback, n, bl).recycleOnUse()));
        }

        @Override
        public void onSessionBadgingChanged(int n) {
            this.mExecutor.execute((Runnable)((Object)PooledLambda.obtainRunnable(_$$Lambda$B12dZLpdwpXn89QSesmkaZjD72Q.INSTANCE, this.mCallback, n).recycleOnUse()));
        }

        @Override
        public void onSessionCreated(int n) {
            this.mExecutor.execute((Runnable)((Object)PooledLambda.obtainRunnable(_$$Lambda$ciir_QAmv6RwJro4I58t77dPnxU.INSTANCE, this.mCallback, n).recycleOnUse()));
        }

        @Override
        public void onSessionFinished(int n, boolean bl) {
            this.mExecutor.execute((Runnable)((Object)PooledLambda.obtainRunnable(_$$Lambda$zO9HBUVgPeroyDQPLJE_MNMvSqc.INSTANCE, this.mCallback, n, bl).recycleOnUse()));
        }

        @Override
        public void onSessionProgressChanged(int n, float f) {
            this.mExecutor.execute((Runnable)((Object)PooledLambda.obtainRunnable(_$$Lambda$n3uXeb1v_YRmq_BWTfosEqUUr9g.INSTANCE, this.mCallback, n, Float.valueOf(f)).recycleOnUse()));
        }
    }

    public static class SessionInfo
    implements Parcelable {
        public static final Parcelable.Creator<SessionInfo> CREATOR;
        public static final int INVALID_ID = -1;
        private static final int[] NO_SESSIONS;
        public static final int STAGED_SESSION_ACTIVATION_FAILED = 2;
        public static final int STAGED_SESSION_NO_ERROR = 0;
        public static final int STAGED_SESSION_UNKNOWN = 3;
        public static final int STAGED_SESSION_VERIFICATION_FAILED = 1;
        @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
        public boolean active;
        @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
        public Bitmap appIcon;
        @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
        public CharSequence appLabel;
        @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
        public String appPackageName;
        public int[] childSessionIds = NO_SESSIONS;
        public String[] grantedRuntimePermissions;
        public int installFlags;
        public int installLocation;
        public int installReason;
        @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
        public String installerPackageName;
        public boolean isCommitted;
        public boolean isMultiPackage;
        public boolean isStaged;
        public boolean isStagedSessionApplied;
        public boolean isStagedSessionFailed;
        public boolean isStagedSessionReady;
        private int mStagedSessionErrorCode;
        private String mStagedSessionErrorMessage;
        @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
        public int mode;
        public int originatingUid;
        public Uri originatingUri;
        public int parentSessionId = -1;
        @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
        public float progress;
        public Uri referrerUri;
        @UnsupportedAppUsage
        public String resolvedBaseCodePath;
        @UnsupportedAppUsage
        public boolean sealed;
        @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
        public int sessionId;
        @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
        public long sizeBytes;
        public long updatedMillis;
        public int userId;
        public List<String> whitelistedRestrictedPermissions;

        static {
            NO_SESSIONS = new int[0];
            CREATOR = new Parcelable.Creator<SessionInfo>(){

                @Override
                public SessionInfo createFromParcel(Parcel parcel) {
                    return new SessionInfo(parcel);
                }

                public SessionInfo[] newArray(int n) {
                    return new SessionInfo[n];
                }
            };
        }

        @UnsupportedAppUsage
        public SessionInfo() {
        }

        public SessionInfo(Parcel parcel) {
            this.sessionId = parcel.readInt();
            this.userId = parcel.readInt();
            this.installerPackageName = parcel.readString();
            this.resolvedBaseCodePath = parcel.readString();
            this.progress = parcel.readFloat();
            int n = parcel.readInt();
            boolean bl = true;
            boolean bl2 = n != 0;
            this.sealed = bl2;
            bl2 = parcel.readInt() != 0 ? bl : false;
            this.active = bl2;
            this.mode = parcel.readInt();
            this.installReason = parcel.readInt();
            this.sizeBytes = parcel.readLong();
            this.appPackageName = parcel.readString();
            this.appIcon = (Bitmap)parcel.readParcelable(null);
            this.appLabel = parcel.readString();
            this.installLocation = parcel.readInt();
            this.originatingUri = (Uri)parcel.readParcelable(null);
            this.originatingUid = parcel.readInt();
            this.referrerUri = (Uri)parcel.readParcelable(null);
            this.grantedRuntimePermissions = parcel.readStringArray();
            this.whitelistedRestrictedPermissions = parcel.createStringArrayList();
            this.installFlags = parcel.readInt();
            this.isMultiPackage = parcel.readBoolean();
            this.isStaged = parcel.readBoolean();
            this.parentSessionId = parcel.readInt();
            this.childSessionIds = parcel.createIntArray();
            if (this.childSessionIds == null) {
                this.childSessionIds = NO_SESSIONS;
            }
            this.isStagedSessionApplied = parcel.readBoolean();
            this.isStagedSessionReady = parcel.readBoolean();
            this.isStagedSessionFailed = parcel.readBoolean();
            this.mStagedSessionErrorCode = parcel.readInt();
            this.mStagedSessionErrorMessage = parcel.readString();
            this.isCommitted = parcel.readBoolean();
        }

        private void checkSessionIsStaged() {
            if (this.isStaged) {
                return;
            }
            throw new IllegalStateException("Session is not marked as staged.");
        }

        public Intent createDetailsIntent() {
            Intent intent = new Intent(PackageInstaller.ACTION_SESSION_DETAILS);
            intent.putExtra(PackageInstaller.EXTRA_SESSION_ID, this.sessionId);
            intent.setPackage(this.installerPackageName);
            intent.setFlags(268435456);
            return intent;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @SystemApi
        public boolean getAllocateAggressive() {
            boolean bl = (this.installFlags & 32768) != 0;
            return bl;
        }

        @SystemApi
        @Deprecated
        public boolean getAllowDowngrade() {
            return this.getRequestDowngrade();
        }

        public Bitmap getAppIcon() {
            if (this.appIcon == null) {
                Parcelable parcelable;
                block5 : {
                    try {
                        parcelable = AppGlobals.getPackageManager().getPackageInstaller().getSessionInfo(this.sessionId);
                        if (parcelable == null) break block5;
                    }
                    catch (RemoteException remoteException) {
                        throw remoteException.rethrowFromSystemServer();
                    }
                    parcelable = parcelable.appIcon;
                }
                parcelable = null;
                this.appIcon = parcelable;
            }
            return this.appIcon;
        }

        public CharSequence getAppLabel() {
            return this.appLabel;
        }

        public String getAppPackageName() {
            return this.appPackageName;
        }

        public int[] getChildSessionIds() {
            return this.childSessionIds;
        }

        @Deprecated
        public Intent getDetailsIntent() {
            return this.createDetailsIntent();
        }

        @SystemApi
        public boolean getDontKillApp() {
            boolean bl = (this.installFlags & 4096) != 0;
            return bl;
        }

        @SystemApi
        public boolean getEnableRollback() {
            boolean bl = (this.installFlags & 262144) != 0;
            return bl;
        }

        @SystemApi
        public String[] getGrantedRuntimePermissions() {
            return this.grantedRuntimePermissions;
        }

        @SystemApi
        public boolean getInstallAsFullApp(boolean bl) {
            bl = (this.installFlags & 16384) != 0;
            return bl;
        }

        @SystemApi
        public boolean getInstallAsInstantApp(boolean bl) {
            bl = (this.installFlags & 2048) != 0;
            return bl;
        }

        @SystemApi
        public boolean getInstallAsVirtualPreload() {
            boolean bl = (this.installFlags & 65536) != 0;
            return bl;
        }

        public int getInstallLocation() {
            return this.installLocation;
        }

        public int getInstallReason() {
            return this.installReason;
        }

        public String getInstallerPackageName() {
            return this.installerPackageName;
        }

        public int getMode() {
            return this.mode;
        }

        public int getOriginatingUid() {
            return this.originatingUid;
        }

        public Uri getOriginatingUri() {
            return this.originatingUri;
        }

        public int getParentSessionId() {
            return this.parentSessionId;
        }

        public float getProgress() {
            return this.progress;
        }

        public Uri getReferrerUri() {
            return this.referrerUri;
        }

        @SystemApi
        public boolean getRequestDowngrade() {
            boolean bl = (this.installFlags & 128) != 0;
            return bl;
        }

        public int getSessionId() {
            return this.sessionId;
        }

        public long getSize() {
            return this.sizeBytes;
        }

        public int getStagedSessionErrorCode() {
            this.checkSessionIsStaged();
            return this.mStagedSessionErrorCode;
        }

        public String getStagedSessionErrorMessage() {
            this.checkSessionIsStaged();
            return this.mStagedSessionErrorMessage;
        }

        public long getUpdatedMillis() {
            return this.updatedMillis;
        }

        public UserHandle getUser() {
            return new UserHandle(this.userId);
        }

        @SystemApi
        public Set<String> getWhitelistedRestrictedPermissions() {
            if ((this.installFlags & 4194304) != 0) {
                return SessionParams.RESTRICTED_PERMISSIONS_ALL;
            }
            List<String> list = this.whitelistedRestrictedPermissions;
            if (list != null) {
                return new ArraySet<String>(list);
            }
            return Collections.emptySet();
        }

        public boolean isActive() {
            return this.active;
        }

        public boolean isCommitted() {
            return this.isCommitted;
        }

        public boolean isMultiPackage() {
            return this.isMultiPackage;
        }

        @Deprecated
        public boolean isOpen() {
            return this.isActive();
        }

        public boolean isSealed() {
            return this.sealed;
        }

        public boolean isStaged() {
            return this.isStaged;
        }

        public boolean isStagedSessionApplied() {
            this.checkSessionIsStaged();
            return this.isStagedSessionApplied;
        }

        public boolean isStagedSessionFailed() {
            this.checkSessionIsStaged();
            return this.isStagedSessionFailed;
        }

        public boolean isStagedSessionReady() {
            this.checkSessionIsStaged();
            return this.isStagedSessionReady;
        }

        public void setStagedSessionErrorCode(int n, String string2) {
            this.mStagedSessionErrorCode = n;
            this.mStagedSessionErrorMessage = string2;
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeInt(this.sessionId);
            parcel.writeInt(this.userId);
            parcel.writeString(this.installerPackageName);
            parcel.writeString(this.resolvedBaseCodePath);
            parcel.writeFloat(this.progress);
            parcel.writeInt((int)this.sealed);
            parcel.writeInt((int)this.active);
            parcel.writeInt(this.mode);
            parcel.writeInt(this.installReason);
            parcel.writeLong(this.sizeBytes);
            parcel.writeString(this.appPackageName);
            parcel.writeParcelable(this.appIcon, n);
            CharSequence charSequence = this.appLabel;
            charSequence = charSequence != null ? charSequence.toString() : null;
            parcel.writeString((String)charSequence);
            parcel.writeInt(this.installLocation);
            parcel.writeParcelable(this.originatingUri, n);
            parcel.writeInt(this.originatingUid);
            parcel.writeParcelable(this.referrerUri, n);
            parcel.writeStringArray(this.grantedRuntimePermissions);
            parcel.writeStringList(this.whitelistedRestrictedPermissions);
            parcel.writeInt(this.installFlags);
            parcel.writeBoolean(this.isMultiPackage);
            parcel.writeBoolean(this.isStaged);
            parcel.writeInt(this.parentSessionId);
            parcel.writeIntArray(this.childSessionIds);
            parcel.writeBoolean(this.isStagedSessionApplied);
            parcel.writeBoolean(this.isStagedSessionReady);
            parcel.writeBoolean(this.isStagedSessionFailed);
            parcel.writeInt(this.mStagedSessionErrorCode);
            parcel.writeString(this.mStagedSessionErrorMessage);
            parcel.writeBoolean(this.isCommitted);
        }

        @Retention(value=RetentionPolicy.SOURCE)
        public static @interface StagedSessionErrorCode {
        }

    }

    public static class SessionParams
    implements Parcelable {
        public static final Parcelable.Creator<SessionParams> CREATOR;
        public static final int MODE_FULL_INSTALL = 1;
        public static final int MODE_INHERIT_EXISTING = 2;
        public static final int MODE_INVALID = -1;
        public static final Set<String> RESTRICTED_PERMISSIONS_ALL;
        public static final int UID_UNKNOWN = -1;
        public String abiOverride;
        @UnsupportedAppUsage
        public Bitmap appIcon;
        public long appIconLastModified = -1L;
        @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
        public String appLabel;
        @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
        public String appPackageName;
        public String[] grantedRuntimePermissions;
        @UnsupportedAppUsage
        public int installFlags = 4194304;
        public int installLocation = 1;
        public int installReason = 0;
        public String installerPackageName;
        public boolean isMultiPackage;
        public boolean isStaged;
        @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
        public int mode = -1;
        @UnsupportedAppUsage
        public int originatingUid = -1;
        public Uri originatingUri;
        public Uri referrerUri;
        public long requiredInstalledVersionCode = -1L;
        @UnsupportedAppUsage
        public long sizeBytes = -1L;
        public String volumeUuid;
        public List<String> whitelistedRestrictedPermissions;

        static {
            RESTRICTED_PERMISSIONS_ALL = new ArraySet<String>();
            CREATOR = new Parcelable.Creator<SessionParams>(){

                @Override
                public SessionParams createFromParcel(Parcel parcel) {
                    return new SessionParams(parcel);
                }

                public SessionParams[] newArray(int n) {
                    return new SessionParams[n];
                }
            };
        }

        public SessionParams(int n) {
            this.mode = n;
        }

        public SessionParams(Parcel parcel) {
            this.mode = parcel.readInt();
            this.installFlags = parcel.readInt();
            this.installLocation = parcel.readInt();
            this.installReason = parcel.readInt();
            this.sizeBytes = parcel.readLong();
            this.appPackageName = parcel.readString();
            this.appIcon = (Bitmap)parcel.readParcelable(null);
            this.appLabel = parcel.readString();
            this.originatingUri = (Uri)parcel.readParcelable(null);
            this.originatingUid = parcel.readInt();
            this.referrerUri = (Uri)parcel.readParcelable(null);
            this.abiOverride = parcel.readString();
            this.volumeUuid = parcel.readString();
            this.grantedRuntimePermissions = parcel.readStringArray();
            this.whitelistedRestrictedPermissions = parcel.createStringArrayList();
            this.installerPackageName = parcel.readString();
            this.isMultiPackage = parcel.readBoolean();
            this.isStaged = parcel.readBoolean();
            this.requiredInstalledVersionCode = parcel.readLong();
        }

        public boolean areHiddenOptionsSet() {
            int n = this.installFlags;
            boolean bl = (1169536 & n) != n || this.abiOverride != null || this.volumeUuid != null;
            return bl;
        }

        public SessionParams copy() {
            SessionParams sessionParams = new SessionParams(this.mode);
            sessionParams.installFlags = this.installFlags;
            sessionParams.installLocation = this.installLocation;
            sessionParams.installReason = this.installReason;
            sessionParams.sizeBytes = this.sizeBytes;
            sessionParams.appPackageName = this.appPackageName;
            sessionParams.appIcon = this.appIcon;
            sessionParams.appLabel = this.appLabel;
            sessionParams.originatingUri = this.originatingUri;
            sessionParams.originatingUid = this.originatingUid;
            sessionParams.referrerUri = this.referrerUri;
            sessionParams.abiOverride = this.abiOverride;
            sessionParams.volumeUuid = this.volumeUuid;
            sessionParams.grantedRuntimePermissions = this.grantedRuntimePermissions;
            sessionParams.whitelistedRestrictedPermissions = this.whitelistedRestrictedPermissions;
            sessionParams.installerPackageName = this.installerPackageName;
            sessionParams.isMultiPackage = this.isMultiPackage;
            sessionParams.isStaged = this.isStaged;
            sessionParams.requiredInstalledVersionCode = this.requiredInstalledVersionCode;
            return sessionParams;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public void dump(IndentingPrintWriter indentingPrintWriter) {
            indentingPrintWriter.printPair("mode", this.mode);
            indentingPrintWriter.printHexPair("installFlags", this.installFlags);
            indentingPrintWriter.printPair("installLocation", this.installLocation);
            indentingPrintWriter.printPair("sizeBytes", this.sizeBytes);
            indentingPrintWriter.printPair("appPackageName", this.appPackageName);
            boolean bl = this.appIcon != null;
            indentingPrintWriter.printPair("appIcon", bl);
            indentingPrintWriter.printPair("appLabel", this.appLabel);
            indentingPrintWriter.printPair("originatingUri", this.originatingUri);
            indentingPrintWriter.printPair("originatingUid", this.originatingUid);
            indentingPrintWriter.printPair("referrerUri", this.referrerUri);
            indentingPrintWriter.printPair("abiOverride", this.abiOverride);
            indentingPrintWriter.printPair("volumeUuid", this.volumeUuid);
            indentingPrintWriter.printPair("grantedRuntimePermissions", this.grantedRuntimePermissions);
            indentingPrintWriter.printPair("whitelistedRestrictedPermissions", this.whitelistedRestrictedPermissions);
            indentingPrintWriter.printPair("installerPackageName", this.installerPackageName);
            indentingPrintWriter.printPair("isMultiPackage", this.isMultiPackage);
            indentingPrintWriter.printPair("isStaged", this.isStaged);
            indentingPrintWriter.printPair("requiredInstalledVersionCode", this.requiredInstalledVersionCode);
            indentingPrintWriter.println();
        }

        public boolean getEnableRollback() {
            boolean bl = (this.installFlags & 262144) != 0;
            return bl;
        }

        @SystemApi
        public void setAllocateAggressive(boolean bl) {
            this.installFlags = bl ? (this.installFlags |= 32768) : (this.installFlags &= -32769);
        }

        @SystemApi
        @Deprecated
        public void setAllowDowngrade(boolean bl) {
            this.setRequestDowngrade(bl);
        }

        public void setAppIcon(Bitmap bitmap) {
            this.appIcon = bitmap;
        }

        public void setAppLabel(CharSequence charSequence) {
            charSequence = charSequence != null ? charSequence.toString() : null;
            this.appLabel = charSequence;
        }

        public void setAppPackageName(String string2) {
            this.appPackageName = string2;
        }

        @SystemApi
        public void setDontKillApp(boolean bl) {
            this.installFlags = bl ? (this.installFlags |= 4096) : (this.installFlags &= -4097);
        }

        @SystemApi
        public void setEnableRollback(boolean bl) {
            this.installFlags = bl ? (this.installFlags |= 262144) : (this.installFlags &= -262145);
        }

        @SystemApi
        public void setGrantedRuntimePermissions(String[] arrstring) {
            this.installFlags |= 256;
            this.grantedRuntimePermissions = arrstring;
        }

        @SystemApi
        public void setInstallAsApex() {
            this.installFlags |= 131072;
        }

        @SystemApi
        public void setInstallAsInstantApp(boolean bl) {
            if (bl) {
                this.installFlags |= 2048;
                this.installFlags &= -16385;
            } else {
                this.installFlags &= -2049;
                this.installFlags |= 16384;
            }
        }

        @SystemApi
        public void setInstallAsVirtualPreload() {
            this.installFlags |= 65536;
        }

        public void setInstallFlagsForcePermissionPrompt() {
            this.installFlags |= 1024;
        }

        public void setInstallLocation(int n) {
            this.installLocation = n;
        }

        public void setInstallReason(int n) {
            this.installReason = n;
        }

        public void setInstallerPackageName(String string2) {
            this.installerPackageName = string2;
        }

        public void setMultiPackage() {
            this.isMultiPackage = true;
        }

        public void setOriginatingUid(int n) {
            this.originatingUid = n;
        }

        public void setOriginatingUri(Uri uri) {
            this.originatingUri = uri;
        }

        public void setReferrerUri(Uri uri) {
            this.referrerUri = uri;
        }

        @SystemApi
        public void setRequestDowngrade(boolean bl) {
            this.installFlags = bl ? (this.installFlags |= 128) : (this.installFlags &= -129);
        }

        public void setRequiredInstalledVersionCode(long l) {
            this.requiredInstalledVersionCode = l;
        }

        public void setSize(long l) {
            this.sizeBytes = l;
        }

        @SystemApi
        public void setStaged() {
            this.isStaged = true;
        }

        public void setWhitelistedRestrictedPermissions(Set<String> collection) {
            Set<String> set = RESTRICTED_PERMISSIONS_ALL;
            Object var3_3 = null;
            if (collection == set) {
                this.installFlags |= 4194304;
                this.whitelistedRestrictedPermissions = null;
            } else {
                this.installFlags &= -4194305;
                collection = collection != null ? new ArrayList<String>(collection) : var3_3;
                this.whitelistedRestrictedPermissions = collection;
            }
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeInt(this.mode);
            parcel.writeInt(this.installFlags);
            parcel.writeInt(this.installLocation);
            parcel.writeInt(this.installReason);
            parcel.writeLong(this.sizeBytes);
            parcel.writeString(this.appPackageName);
            parcel.writeParcelable(this.appIcon, n);
            parcel.writeString(this.appLabel);
            parcel.writeParcelable(this.originatingUri, n);
            parcel.writeInt(this.originatingUid);
            parcel.writeParcelable(this.referrerUri, n);
            parcel.writeString(this.abiOverride);
            parcel.writeString(this.volumeUuid);
            parcel.writeStringArray(this.grantedRuntimePermissions);
            parcel.writeStringList(this.whitelistedRestrictedPermissions);
            parcel.writeString(this.installerPackageName);
            parcel.writeBoolean(this.isMultiPackage);
            parcel.writeBoolean(this.isStaged);
            parcel.writeLong(this.requiredInstalledVersionCode);
        }

    }

}

