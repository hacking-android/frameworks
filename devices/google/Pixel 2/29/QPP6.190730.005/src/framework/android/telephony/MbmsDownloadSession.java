/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.annotation.SystemApi;
import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.IBinder;
import android.os.RemoteException;
import android.telephony.SubscriptionManager;
import android.telephony.mbms.DownloadProgressListener;
import android.telephony.mbms.DownloadRequest;
import android.telephony.mbms.DownloadStatusListener;
import android.telephony.mbms.FileInfo;
import android.telephony.mbms.IDownloadProgressListener;
import android.telephony.mbms.IDownloadStatusListener;
import android.telephony.mbms.IMbmsDownloadSessionCallback;
import android.telephony.mbms.InternalDownloadProgressListener;
import android.telephony.mbms.InternalDownloadSessionCallback;
import android.telephony.mbms.InternalDownloadStatusListener;
import android.telephony.mbms.MbmsDownloadSessionCallback;
import android.telephony.mbms.MbmsTempFileProvider;
import android.telephony.mbms.MbmsUtils;
import android.telephony.mbms.vendor.IMbmsDownloadService;
import android.util.Log;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class MbmsDownloadSession
implements AutoCloseable {
    public static final String DEFAULT_TOP_LEVEL_TEMP_DIRECTORY = "androidMbmsTempFileRoot";
    private static final String DESTINATION_SANITY_CHECK_FILE_NAME = "destinationSanityCheckFile";
    public static final String EXTRA_MBMS_COMPLETED_FILE_URI = "android.telephony.extra.MBMS_COMPLETED_FILE_URI";
    public static final String EXTRA_MBMS_DOWNLOAD_REQUEST = "android.telephony.extra.MBMS_DOWNLOAD_REQUEST";
    public static final String EXTRA_MBMS_DOWNLOAD_RESULT = "android.telephony.extra.MBMS_DOWNLOAD_RESULT";
    public static final String EXTRA_MBMS_FILE_INFO = "android.telephony.extra.MBMS_FILE_INFO";
    private static final String LOG_TAG = MbmsDownloadSession.class.getSimpleName();
    @SystemApi
    public static final String MBMS_DOWNLOAD_SERVICE_ACTION = "android.telephony.action.EmbmsDownload";
    public static final String MBMS_DOWNLOAD_SERVICE_OVERRIDE_METADATA = "mbms-download-service-override";
    public static final int RESULT_CANCELLED = 2;
    public static final int RESULT_DOWNLOAD_FAILURE = 6;
    public static final int RESULT_EXPIRED = 3;
    public static final int RESULT_FILE_ROOT_UNREACHABLE = 8;
    public static final int RESULT_IO_ERROR = 4;
    public static final int RESULT_OUT_OF_STORAGE = 7;
    public static final int RESULT_SERVICE_ID_NOT_DEFINED = 5;
    public static final int RESULT_SUCCESSFUL = 1;
    public static final int STATUS_ACTIVELY_DOWNLOADING = 1;
    public static final int STATUS_PENDING_DOWNLOAD = 2;
    public static final int STATUS_PENDING_DOWNLOAD_WINDOW = 4;
    public static final int STATUS_PENDING_REPAIR = 3;
    public static final int STATUS_UNKNOWN = 0;
    private static AtomicBoolean sIsInitialized = new AtomicBoolean(false);
    private final Context mContext;
    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient(){

        @Override
        public void binderDied() {
            MbmsDownloadSession.this.sendErrorToApp(3, "Received death notification");
        }
    };
    private final InternalDownloadSessionCallback mInternalCallback;
    private final Map<DownloadProgressListener, InternalDownloadProgressListener> mInternalDownloadProgressListeners = new HashMap<DownloadProgressListener, InternalDownloadProgressListener>();
    private final Map<DownloadStatusListener, InternalDownloadStatusListener> mInternalDownloadStatusListeners = new HashMap<DownloadStatusListener, InternalDownloadStatusListener>();
    private AtomicReference<IMbmsDownloadService> mService = new AtomicReference<Object>(null);
    private int mSubscriptionId = -1;

    private MbmsDownloadSession(Context context, Executor executor, int n, MbmsDownloadSessionCallback mbmsDownloadSessionCallback) {
        this.mContext = context;
        this.mSubscriptionId = n;
        this.mInternalCallback = new InternalDownloadSessionCallback(mbmsDownloadSessionCallback, executor);
    }

    private int bindAndInitialize() {
        return MbmsUtils.startBinding(this.mContext, MBMS_DOWNLOAD_SERVICE_ACTION, new ServiceConnection(){

            /*
             * Loose catch block
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public void onServiceConnected(ComponentName object, IBinder iBinder) {
                block5 : {
                    block6 : {
                        object = IMbmsDownloadService.Stub.asInterface(iBinder);
                        int n = object.initialize(MbmsDownloadSession.this.mSubscriptionId, MbmsDownloadSession.this.mInternalCallback);
                        if (n == -1) break block5;
                        if (n == 0) break block6;
                        MbmsDownloadSession.this.sendErrorToApp(n, "Error returned during initialization");
                        sIsInitialized.set(false);
                        return;
                    }
                    try {
                        object.asBinder().linkToDeath(MbmsDownloadSession.this.mDeathRecipient, 0);
                        MbmsDownloadSession.this.mService.set(object);
                        return;
                    }
                    catch (RemoteException remoteException) {
                        MbmsDownloadSession.this.sendErrorToApp(3, "Middleware lost during initialization");
                        sIsInitialized.set(false);
                        return;
                    }
                }
                MbmsDownloadSession.this.close();
                throw new IllegalStateException("Middleware must not return an unknown error code");
                catch (RuntimeException runtimeException) {
                    Log.e(LOG_TAG, "Runtime exception during initialization");
                    MbmsDownloadSession.this.sendErrorToApp(103, runtimeException.toString());
                    sIsInitialized.set(false);
                    return;
                }
                catch (RemoteException remoteException) {
                    Log.e(LOG_TAG, "Service died before initialization");
                    sIsInitialized.set(false);
                    return;
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                Log.w(LOG_TAG, "bindAndInitialize: Remote service disconnected");
                sIsInitialized.set(false);
                MbmsDownloadSession.this.mService.set(null);
            }
        });
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void checkDownloadRequestDestination(DownloadRequest object) {
        Throwable throwable2222;
        File file;
        block7 : {
            block6 : {
                boolean bl;
                file = new File(((DownloadRequest)object).getDestinationUri().getPath());
                if (!file.isDirectory()) throw new IllegalArgumentException("The destination path must be a directory");
                object = new File(MbmsTempFileProvider.getEmbmsTempFileDir(this.mContext), DESTINATION_SANITY_CHECK_FILE_NAME);
                file = new File(file, DESTINATION_SANITY_CHECK_FILE_NAME);
                if (!((File)object).exists()) {
                    ((File)object).createNewFile();
                }
                if (!(bl = ((File)object).renameTo(file))) break block6;
                ((File)object).delete();
                file.delete();
                return;
            }
            try {
                IllegalArgumentException illegalArgumentException = new IllegalArgumentException("Destination provided in the download request is invalid -- files in the temp file directory cannot be directly moved there.");
                throw illegalArgumentException;
            }
            catch (Throwable throwable2222) {
                break block7;
            }
            catch (IOException iOException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Got IOException while testing out the destination: ");
                stringBuilder.append(iOException);
                IllegalStateException illegalStateException = new IllegalStateException(stringBuilder.toString());
                throw illegalStateException;
            }
        }
        ((File)object).delete();
        file.delete();
        throw throwable2222;
    }

    public static MbmsDownloadSession create(Context object, Executor executor, final int n, MbmsDownloadSessionCallback mbmsDownloadSessionCallback) {
        if (sIsInitialized.compareAndSet(false, true)) {
            object = new MbmsDownloadSession((Context)object, executor, n, mbmsDownloadSessionCallback);
            n = MbmsDownloadSession.super.bindAndInitialize();
            if (n != 0) {
                sIsInitialized.set(false);
                executor.execute(new Runnable(){

                    @Override
                    public void run() {
                        MbmsDownloadSessionCallback.this.onError(n, null);
                    }
                });
                return null;
            }
            return object;
        }
        throw new IllegalStateException("Cannot have two active instances");
    }

    public static MbmsDownloadSession create(Context context, Executor executor, MbmsDownloadSessionCallback mbmsDownloadSessionCallback) {
        return MbmsDownloadSession.create(context, executor, SubscriptionManager.getDefaultSubscriptionId(), mbmsDownloadSessionCallback);
    }

    private void deleteDownloadRequestToken(DownloadRequest object) {
        if (!((File)(object = this.getDownloadRequestTokenPath((DownloadRequest)object))).isFile()) {
            String string2 = LOG_TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Attempting to delete non-existent download token at ");
            stringBuilder.append(object);
            Log.w(string2, stringBuilder.toString());
            return;
        }
        if (!((File)object).delete()) {
            String string3 = LOG_TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Couldn't delete download token at ");
            stringBuilder.append(object);
            Log.w(string3, stringBuilder.toString());
        }
    }

    private File getDownloadRequestTokenPath(DownloadRequest downloadRequest) {
        File file = MbmsUtils.getEmbmsTempFileDirForService(this.mContext, downloadRequest.getFileServiceId());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(downloadRequest.getHash());
        stringBuilder.append(".download_token");
        return new File(file, stringBuilder.toString());
    }

    private void sendErrorToApp(int n, String string2) {
        this.mInternalCallback.onError(n, string2);
    }

    private void validateTempFileRootSanity(File object) throws IOException {
        if (((File)object).exists()) {
            if (((File)object).isDirectory()) {
                object = ((File)object).getCanonicalPath();
                if (!this.mContext.getDataDir().getCanonicalPath().equals(object)) {
                    if (!this.mContext.getCacheDir().getCanonicalPath().equals(object)) {
                        if (!this.mContext.getFilesDir().getCanonicalPath().equals(object)) {
                            return;
                        }
                        throw new IllegalArgumentException("Temp file root cannot be your files dir");
                    }
                    throw new IllegalArgumentException("Temp file root cannot be your cache dir");
                }
                throw new IllegalArgumentException("Temp file root cannot be your data dir");
            }
            throw new IllegalArgumentException("Provided File is not a directory");
        }
        throw new IllegalArgumentException("Provided directory does not exist");
    }

    private void writeDownloadRequestToken(DownloadRequest object) {
        File file = this.getDownloadRequestTokenPath((DownloadRequest)object);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (file.exists()) {
            object = LOG_TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Download token ");
            stringBuilder.append(file.getName());
            stringBuilder.append(" already exists");
            Log.w((String)object, stringBuilder.toString());
            return;
        }
        try {
            if (file.createNewFile()) {
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Failed to create download token for request ");
            stringBuilder.append(object);
            stringBuilder.append(". Token location is ");
            stringBuilder.append(file.getPath());
            RuntimeException runtimeException = new RuntimeException(stringBuilder.toString());
            throw runtimeException;
        }
        catch (IOException iOException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Failed to create download token for request ");
            stringBuilder.append(object);
            stringBuilder.append(" due to IOException ");
            stringBuilder.append(iOException);
            stringBuilder.append(". Attempted to write to ");
            stringBuilder.append(file.getPath());
            throw new RuntimeException(stringBuilder.toString());
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void addProgressListener(DownloadRequest object, Executor object2, DownloadProgressListener downloadProgressListener) {
        block6 : {
            IMbmsDownloadService iMbmsDownloadService = this.mService.get();
            if (iMbmsDownloadService == null) {
                throw new IllegalStateException("Middleware not yet bound");
            }
            object2 = new InternalDownloadProgressListener(downloadProgressListener, (Executor)object2);
            try {
                int n = iMbmsDownloadService.addProgressListener((DownloadRequest)object, (IDownloadProgressListener)object2);
                if (n == -1) break block6;
                if (n != 0) {
                    if (n != 402) {
                        this.sendErrorToApp(n, null);
                        return;
                    }
                    object = new IllegalArgumentException("Unknown download request.");
                    throw object;
                }
                this.mInternalDownloadProgressListeners.put(downloadProgressListener, (InternalDownloadProgressListener)object2);
            }
            catch (RemoteException remoteException) {
                this.mService.set(null);
                sIsInitialized.set(false);
                this.sendErrorToApp(3, null);
                return;
            }
            return;
        }
        this.close();
        object = new IllegalStateException("Middleware must not return an unknown error code");
        throw object;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void addStatusListener(DownloadRequest object, Executor object2, DownloadStatusListener downloadStatusListener) {
        block6 : {
            IMbmsDownloadService iMbmsDownloadService = this.mService.get();
            if (iMbmsDownloadService == null) {
                throw new IllegalStateException("Middleware not yet bound");
            }
            object2 = new InternalDownloadStatusListener(downloadStatusListener, (Executor)object2);
            try {
                int n = iMbmsDownloadService.addStatusListener((DownloadRequest)object, (IDownloadStatusListener)object2);
                if (n == -1) break block6;
                if (n != 0) {
                    if (n != 402) {
                        this.sendErrorToApp(n, null);
                        return;
                    }
                    object = new IllegalArgumentException("Unknown download request.");
                    throw object;
                }
                this.mInternalDownloadStatusListeners.put(downloadStatusListener, (InternalDownloadStatusListener)object2);
            }
            catch (RemoteException remoteException) {
                this.mService.set(null);
                sIsInitialized.set(false);
                this.sendErrorToApp(3, null);
                return;
            }
            return;
        }
        this.close();
        object = new IllegalStateException("Middleware must not return an unknown error code");
        throw object;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void cancelDownload(DownloadRequest object) {
        IMbmsDownloadService iMbmsDownloadService = this.mService.get();
        if (iMbmsDownloadService == null) throw new IllegalStateException("Middleware not yet bound");
        try {
            int n = iMbmsDownloadService.cancelDownload((DownloadRequest)object);
            if (n == -1) {
                this.close();
                object = new IllegalStateException("Middleware must not return an unknown error code");
                throw object;
            }
            if (n != 0) {
                this.sendErrorToApp(n, null);
                return;
            }
            this.deleteDownloadRequestToken((DownloadRequest)object);
            return;
        }
        catch (RemoteException remoteException) {
            this.mService.set(null);
            sIsInitialized.set(false);
            this.sendErrorToApp(3, null);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void close() {
        Throwable throwable2;
        block7 : {
            try {
                IMbmsDownloadService iMbmsDownloadService = this.mService.get();
                if (iMbmsDownloadService == null) {
                    Log.i(LOG_TAG, "Service already dead");
                    this.mService.set(null);
                    sIsInitialized.set(false);
                    this.mInternalCallback.stop();
                    return;
                }
                iMbmsDownloadService.dispose(this.mSubscriptionId);
            }
            catch (Throwable throwable2) {
                break block7;
            }
            catch (RemoteException remoteException) {
                Log.i(LOG_TAG, "Remote exception while disposing of service");
            }
            this.mService.set(null);
            sIsInitialized.set(false);
            this.mInternalCallback.stop();
            return;
        }
        this.mService.set(null);
        sIsInitialized.set(false);
        this.mInternalCallback.stop();
        throw throwable2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void download(DownloadRequest object) {
        IMbmsDownloadService iMbmsDownloadService = this.mService.get();
        if (iMbmsDownloadService == null) throw new IllegalStateException("Middleware not yet bound");
        if (this.mContext.getSharedPreferences("MbmsTempFileRootPrefs", 0).getString("mbms_temp_file_root", null) == null) {
            File file = new File(this.mContext.getFilesDir(), DEFAULT_TOP_LEVEL_TEMP_DIRECTORY);
            file.mkdirs();
            this.setTempFileRootDirectory(file);
        }
        this.checkDownloadRequestDestination((DownloadRequest)object);
        try {
            int n = iMbmsDownloadService.download((DownloadRequest)object);
            if (n == 0) {
                this.writeDownloadRequestToken((DownloadRequest)object);
                return;
            }
            if (n != -1) {
                this.sendErrorToApp(n, null);
                return;
            }
            this.close();
            object = new IllegalStateException("Middleware must not return an unknown error code");
            throw object;
        }
        catch (RemoteException remoteException) {
            this.mService.set(null);
            sIsInitialized.set(false);
            this.sendErrorToApp(3, null);
        }
    }

    public File getTempFileRootDirectory() {
        String string2 = this.mContext.getSharedPreferences("MbmsTempFileRootPrefs", 0).getString("mbms_temp_file_root", null);
        if (string2 != null) {
            return new File(string2);
        }
        return null;
    }

    public List<DownloadRequest> listPendingDownloads() {
        Object object = this.mService.get();
        if (object != null) {
            try {
                object = object.listPendingDownloads(this.mSubscriptionId);
                return object;
            }
            catch (RemoteException remoteException) {
                this.mService.set(null);
                sIsInitialized.set(false);
                this.sendErrorToApp(3, null);
                return Collections.emptyList();
            }
        }
        throw new IllegalStateException("Middleware not yet bound");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void removeProgressListener(DownloadRequest object, DownloadProgressListener object2) {
        try {
            IMbmsDownloadService iMbmsDownloadService = this.mService.get();
            if (iMbmsDownloadService != null) {
                block13 : {
                    InternalDownloadProgressListener internalDownloadProgressListener = this.mInternalDownloadProgressListeners.get(object2);
                    if (internalDownloadProgressListener == null) {
                        object = new IllegalArgumentException("Provided listener was never registered");
                        throw object;
                    }
                    try {
                        int n = iMbmsDownloadService.removeProgressListener((DownloadRequest)object, internalDownloadProgressListener);
                        if (n == -1) break block13;
                        if (n == 0) return;
                        if (n != 402) {
                            this.sendErrorToApp(n, null);
                            return;
                        }
                        object = new IllegalArgumentException("Unknown download request.");
                    }
                    catch (RemoteException remoteException) {
                        this.mService.set(null);
                        sIsInitialized.set(false);
                        this.sendErrorToApp(3, null);
                        return;
                    }
                    throw object;
                }
                this.close();
                object = new IllegalStateException("Middleware must not return an unknown error code");
                throw object;
            }
            object = new IllegalStateException("Middleware not yet bound");
            throw object;
        }
        finally {
            object = this.mInternalDownloadProgressListeners.remove(object2);
            if (object != null) {
                ((InternalDownloadProgressListener)object).stop();
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void removeStatusListener(DownloadRequest object, DownloadStatusListener object2) {
        try {
            IMbmsDownloadService iMbmsDownloadService = this.mService.get();
            if (iMbmsDownloadService != null) {
                block13 : {
                    InternalDownloadStatusListener internalDownloadStatusListener = this.mInternalDownloadStatusListeners.get(object2);
                    if (internalDownloadStatusListener == null) {
                        object = new IllegalArgumentException("Provided listener was never registered");
                        throw object;
                    }
                    try {
                        int n = iMbmsDownloadService.removeStatusListener((DownloadRequest)object, internalDownloadStatusListener);
                        if (n == -1) break block13;
                        if (n == 0) return;
                        if (n != 402) {
                            this.sendErrorToApp(n, null);
                            return;
                        }
                        object = new IllegalArgumentException("Unknown download request.");
                    }
                    catch (RemoteException remoteException) {
                        this.mService.set(null);
                        sIsInitialized.set(false);
                        this.sendErrorToApp(3, null);
                        return;
                    }
                    throw object;
                }
                this.close();
                object = new IllegalStateException("Middleware must not return an unknown error code");
                throw object;
            }
            object = new IllegalStateException("Middleware not yet bound");
            throw object;
        }
        finally {
            object = this.mInternalDownloadStatusListeners.remove(object2);
            if (object != null) {
                ((InternalDownloadStatusListener)object).stop();
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void requestDownloadState(DownloadRequest object, FileInfo fileInfo) {
        IMbmsDownloadService iMbmsDownloadService = this.mService.get();
        if (iMbmsDownloadService == null) throw new IllegalStateException("Middleware not yet bound");
        try {
            int n = iMbmsDownloadService.requestDownloadState((DownloadRequest)object, fileInfo);
            if (n == -1) {
                this.close();
                object = new IllegalStateException("Middleware must not return an unknown error code");
                throw object;
            }
            if (n == 0) return;
            if (n == 402) {
                object = new IllegalArgumentException("Unknown download request.");
                throw object;
            }
            if (n != 403) {
                this.sendErrorToApp(n, null);
                return;
            }
            object = new IllegalArgumentException("Unknown file.");
            throw object;
        }
        catch (RemoteException remoteException) {
            this.mService.set(null);
            sIsInitialized.set(false);
            this.sendErrorToApp(3, null);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void requestUpdateFileServices(List<String> object) {
        IMbmsDownloadService iMbmsDownloadService = this.mService.get();
        if (iMbmsDownloadService == null) throw new IllegalStateException("Middleware not yet bound");
        try {
            int n = iMbmsDownloadService.requestUpdateFileServices(this.mSubscriptionId, (List<String>)object);
            if (n != -1) {
                if (n == 0) return;
                this.sendErrorToApp(n, null);
                return;
            }
            this.close();
            object = new IllegalStateException("Middleware must not return an unknown error code");
            throw object;
        }
        catch (RemoteException remoteException) {
            Log.w(LOG_TAG, "Remote process died");
            this.mService.set(null);
            sIsInitialized.set(false);
            this.sendErrorToApp(3, null);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void resetDownloadKnowledge(DownloadRequest object) {
        IMbmsDownloadService iMbmsDownloadService = this.mService.get();
        if (iMbmsDownloadService == null) throw new IllegalStateException("Middleware not yet bound");
        try {
            int n = iMbmsDownloadService.resetDownloadKnowledge((DownloadRequest)object);
            if (n == -1) {
                this.close();
                object = new IllegalStateException("Middleware must not return an unknown error code");
                throw object;
            }
            if (n == 0) return;
            if (n != 402) {
                this.sendErrorToApp(n, null);
                return;
            }
            object = new IllegalArgumentException("Unknown download request.");
            throw object;
        }
        catch (RemoteException remoteException) {
            this.mService.set(null);
            sIsInitialized.set(false);
            this.sendErrorToApp(3, null);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setTempFileRootDirectory(File object) {
        Object object2 = this.mService.get();
        if (object2 == null) {
            throw new IllegalStateException("Middleware not yet bound");
        }
        try {
            this.validateTempFileRootSanity((File)object);
        }
        catch (IOException iOException) {
            throw new IllegalStateException("Got IOException checking directory sanity");
        }
        try {
            object = ((File)object).getCanonicalPath();
        }
        catch (IOException iOException) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Unable to canonicalize the provided path: ");
            ((StringBuilder)object2).append(iOException);
            throw new IllegalArgumentException(((StringBuilder)object2).toString());
        }
        try {
            int n = object2.setTempFileRootDirectory(this.mSubscriptionId, (String)object);
            if (n != -1) {
                if (n != 0) {
                    this.sendErrorToApp(n, null);
                    return;
                }
                this.mContext.getSharedPreferences("MbmsTempFileRootPrefs", 0).edit().putString("mbms_temp_file_root", (String)object).apply();
                return;
            }
            this.close();
            object = new IllegalStateException("Middleware must not return an unknown error code");
            throw object;
        }
        catch (RemoteException remoteException) {
            this.mService.set(null);
            sIsInitialized.set(false);
            this.sendErrorToApp(3, null);
            return;
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface DownloadResultCode {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface DownloadStatus {
    }

}

