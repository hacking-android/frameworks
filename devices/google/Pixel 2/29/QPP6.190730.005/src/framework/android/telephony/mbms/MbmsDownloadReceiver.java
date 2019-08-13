/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.mbms;

import android.annotation.SystemApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.telephony.mbms.DownloadRequest;
import android.telephony.mbms.FileInfo;
import android.telephony.mbms.MbmsTempFileProvider;
import android.telephony.mbms.MbmsUtils;
import android.telephony.mbms.UriPathPair;
import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class MbmsDownloadReceiver
extends BroadcastReceiver {
    public static final String DOWNLOAD_TOKEN_SUFFIX = ".download_token";
    private static final String EMBMS_INTENT_PERMISSION = "android.permission.SEND_EMBMS_INTENTS";
    private static final String LOG_TAG = "MbmsDownloadReceiver";
    private static final int MAX_TEMP_FILE_RETRIES = 5;
    public static final String MBMS_FILE_PROVIDER_META_DATA_KEY = "mbms-file-provider-authority";
    @SystemApi
    public static final int RESULT_APP_NOTIFICATION_ERROR = 6;
    @SystemApi
    public static final int RESULT_BAD_TEMP_FILE_ROOT = 3;
    @SystemApi
    public static final int RESULT_DOWNLOAD_FINALIZATION_ERROR = 4;
    @SystemApi
    public static final int RESULT_INVALID_ACTION = 1;
    @SystemApi
    public static final int RESULT_MALFORMED_INTENT = 2;
    @SystemApi
    public static final int RESULT_OK = 0;
    @SystemApi
    public static final int RESULT_TEMP_FILE_GENERATION_ERROR = 5;
    private static final String TEMP_FILE_STAGING_LOCATION = "staged_completed_files";
    private static final String TEMP_FILE_SUFFIX = ".embms.temp";
    private String mFileProviderAuthorityCache = null;
    private String mMiddlewarePackageNameCache = null;

    private void cleanupPostMove(Context context, Intent object) {
        DownloadRequest downloadRequest = (DownloadRequest)((Intent)object).getParcelableExtra("android.telephony.extra.MBMS_DOWNLOAD_REQUEST");
        if (downloadRequest == null) {
            Log.w(LOG_TAG, "Intent does not include a DownloadRequest. Ignoring.");
            return;
        }
        if ((object = ((Intent)object).getParcelableArrayListExtra("android.telephony.mbms.extra.TEMP_LIST")) == null) {
            return;
        }
        object = object.iterator();
        while (object.hasNext()) {
            File file;
            Object object2 = (Uri)object.next();
            if (!MbmsDownloadReceiver.verifyTempFilePath(context, downloadRequest.getFileServiceId(), (Uri)object2) || (file = new File(((Uri)object2).getSchemeSpecificPart())).delete()) continue;
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Failed to delete temp file at ");
            ((StringBuilder)object2).append(file.getPath());
            Log.w(LOG_TAG, ((StringBuilder)object2).toString());
        }
    }

    private void cleanupTempFiles(Context arrfile, Intent intent) {
        arrfile = MbmsUtils.getEmbmsTempFileDirForService((Context)arrfile, intent.getStringExtra("android.telephony.mbms.extra.SERVICE_ID"));
        arrfile = arrfile.listFiles(new FileFilter(intent.getParcelableArrayListExtra("android.telephony.mbms.extra.TEMP_FILES_IN_USE")){
            final /* synthetic */ List val$filesInUse;
            {
                this.val$filesInUse = list;
            }

            @Override
            public boolean accept(File comparable) {
                File file;
                block2 : {
                    try {
                        file = comparable.getCanonicalFile();
                        if (file.getName().endsWith(MbmsDownloadReceiver.TEMP_FILE_SUFFIX)) break block2;
                        return false;
                    }
                    catch (IOException iOException) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Got IOException canonicalizing ");
                        stringBuilder.append(comparable);
                        stringBuilder.append(", not deleting.");
                        Log.w(MbmsDownloadReceiver.LOG_TAG, stringBuilder.toString());
                        return false;
                    }
                }
                comparable = Uri.fromFile(file);
                return this.val$filesInUse.contains(comparable) ^ true;
            }
        });
        int n = arrfile.length;
        for (int i = 0; i < n; ++i) {
            arrfile[i].delete();
        }
    }

    private ArrayList<UriPathPair> generateFreshTempFiles(Context context, String object, int n) {
        File file = MbmsUtils.getEmbmsTempFileDirForService(context, (String)object);
        if (!file.exists()) {
            file.mkdirs();
        }
        object = new ArrayList(n);
        for (int i = 0; i < n; ++i) {
            Comparable<File> comparable = MbmsDownloadReceiver.generateSingleTempFile(file);
            if (comparable == null) {
                this.setResultCode(5);
                Log.w(LOG_TAG, "Failed to generate a temp file. Moving on.");
                continue;
            }
            Uri uri = Uri.fromFile(comparable);
            comparable = MbmsTempFileProvider.getUriForFile(context, this.getFileProviderAuthorityCached(context), comparable);
            context.grantUriPermission(this.getMiddlewarePackageCached(context), (Uri)comparable, 3);
            ((ArrayList)object).add(new UriPathPair(uri, (Uri)comparable));
        }
        return object;
    }

    private static File generateSingleTempFile(File file) {
        for (int i = 0; i < 5; ++i) {
            Serializable serializable = new StringBuilder();
            ((StringBuilder)serializable).append(UUID.randomUUID());
            ((StringBuilder)serializable).append(TEMP_FILE_SUFFIX);
            serializable = new File(file, ((StringBuilder)serializable).toString());
            try {
                if (!((File)serializable).createNewFile()) continue;
                serializable = ((File)serializable).getCanonicalFile();
                return serializable;
            }
            catch (IOException iOException) {
                continue;
            }
        }
        return null;
    }

    private void generateTempFiles(Context object, Intent cloneable) {
        Object object2 = cloneable.getStringExtra("android.telephony.mbms.extra.SERVICE_ID");
        if (object2 == null) {
            Log.w(LOG_TAG, "Temp file request did not include the associated service id. Ignoring.");
            this.setResultCode(2);
            return;
        }
        int n = cloneable.getIntExtra("android.telephony.mbms.extra.FD_COUNT", 0);
        ArrayList<Uri> arrayList = cloneable.getParcelableArrayListExtra("android.telephony.mbms.extra.PAUSED_LIST");
        if (n == 0 && (arrayList == null || arrayList.size() == 0)) {
            Log.i(LOG_TAG, "No temp files actually requested. Ending.");
            this.setResultCode(0);
            this.setResultExtras(Bundle.EMPTY);
            return;
        }
        cloneable = this.generateFreshTempFiles((Context)object, (String)object2, n);
        object2 = this.generateUrisForPausedFiles((Context)object, (String)object2, arrayList);
        object = new Bundle();
        ((Bundle)object).putParcelableArrayList("android.telephony.mbms.extra.FREE_URI_LIST", (ArrayList<? extends Parcelable>)cloneable);
        ((Bundle)object).putParcelableArrayList("android.telephony.mbms.extra.PAUSED_URI_LIST", (ArrayList<? extends Parcelable>)object2);
        this.setResultCode(0);
        this.setResultExtras((Bundle)object);
    }

    private ArrayList<UriPathPair> generateUrisForPausedFiles(Context context, String string2, List<Uri> object) {
        if (object == null) {
            return new ArrayList<UriPathPair>(0);
        }
        ArrayList<UriPathPair> arrayList = new ArrayList<UriPathPair>(object.size());
        object = object.iterator();
        while (object.hasNext()) {
            Object object2;
            Uri uri = (Uri)object.next();
            if (!MbmsDownloadReceiver.verifyTempFilePath(context, string2, uri)) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Supplied file ");
                ((StringBuilder)object2).append(uri);
                ((StringBuilder)object2).append(" is not a valid temp file to resume");
                Log.w(LOG_TAG, ((StringBuilder)object2).toString());
                this.setResultCode(5);
                continue;
            }
            object2 = new File(uri.getSchemeSpecificPart());
            if (!((File)object2).exists()) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Supplied file ");
                ((StringBuilder)object2).append(uri);
                ((StringBuilder)object2).append(" does not exist.");
                Log.w(LOG_TAG, ((StringBuilder)object2).toString());
                this.setResultCode(5);
                continue;
            }
            object2 = MbmsTempFileProvider.getUriForFile(context, this.getFileProviderAuthorityCached(context), (File)object2);
            context.grantUriPermission(this.getMiddlewarePackageCached(context), (Uri)object2, 3);
            arrayList.add(new UriPathPair(uri, (Uri)object2));
        }
        return arrayList;
    }

    private static String getFileProviderAuthority(Context object) {
        block3 : {
            ApplicationInfo applicationInfo;
            try {
                applicationInfo = ((Context)object).getPackageManager().getApplicationInfo(((Context)object).getPackageName(), 128);
                if (applicationInfo.metaData == null) break block3;
            }
            catch (PackageManager.NameNotFoundException nameNotFoundException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Package manager couldn't find ");
                stringBuilder.append(((Context)object).getPackageName());
                throw new RuntimeException(stringBuilder.toString());
            }
            object = applicationInfo.metaData.getString(MBMS_FILE_PROVIDER_META_DATA_KEY);
            if (object != null) {
                return object;
            }
            throw new RuntimeException("App must declare the file provider authority as metadata in the manifest.");
        }
        throw new RuntimeException("App must declare the file provider authority as metadata in the manifest.");
    }

    private String getFileProviderAuthorityCached(Context context) {
        String string2 = this.mFileProviderAuthorityCache;
        if (string2 != null) {
            return string2;
        }
        this.mFileProviderAuthorityCache = MbmsDownloadReceiver.getFileProviderAuthority(context);
        return this.mFileProviderAuthorityCache;
    }

    @VisibleForTesting
    public static String getFileRelativePath(String charSequence, String string2) {
        String string3 = charSequence;
        if (((String)charSequence).endsWith("*")) {
            string3 = ((String)charSequence).substring(0, ((String)charSequence).lastIndexOf(47));
        }
        if (!string2.startsWith(string3)) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("File location specified in FileInfo does not match the source URI. source: ");
            ((StringBuilder)charSequence).append(string3);
            ((StringBuilder)charSequence).append(" fileinfo path: ");
            ((StringBuilder)charSequence).append(string2);
            Log.e(LOG_TAG, ((StringBuilder)charSequence).toString());
            return null;
        }
        if (string2.length() == string3.length()) {
            return string3.substring(string3.lastIndexOf(47) + 1);
        }
        string2 = string2.substring(string3.length());
        charSequence = string2;
        if (string2.startsWith("/")) {
            charSequence = string2.substring(1);
        }
        return charSequence;
    }

    private String getMiddlewarePackageCached(Context context) {
        if (this.mMiddlewarePackageNameCache == null) {
            this.mMiddlewarePackageNameCache = MbmsUtils.getMiddlewareServiceInfo((Context)context, (String)"android.telephony.action.EmbmsDownload").packageName;
        }
        return this.mMiddlewarePackageNameCache;
    }

    private void moveDownloadedFile(Context object, Intent parcelable) {
        DownloadRequest downloadRequest = (DownloadRequest)((Intent)parcelable).getParcelableExtra("android.telephony.extra.MBMS_DOWNLOAD_REQUEST");
        Intent intent = downloadRequest.getIntentForApp();
        if (intent == null) {
            Log.i(LOG_TAG, "Malformed app notification intent");
            this.setResultCode(6);
            return;
        }
        int n = ((Intent)parcelable).getIntExtra("android.telephony.extra.MBMS_DOWNLOAD_RESULT", 2);
        intent.putExtra("android.telephony.extra.MBMS_DOWNLOAD_RESULT", n);
        intent.putExtra("android.telephony.extra.MBMS_DOWNLOAD_REQUEST", downloadRequest);
        if (n != 1) {
            Log.i(LOG_TAG, "Download request indicated a failed download. Aborting.");
            ((Context)object).sendBroadcast(intent);
            this.setResultCode(0);
            return;
        }
        Uri uri = (Uri)((Intent)parcelable).getParcelableExtra("android.telephony.mbms.extra.FINAL_URI");
        if (!MbmsDownloadReceiver.verifyTempFilePath((Context)object, downloadRequest.getFileServiceId(), uri)) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Download result specified an invalid temp file ");
            ((StringBuilder)object).append(uri);
            Log.w(LOG_TAG, ((StringBuilder)object).toString());
            this.setResultCode(4);
            return;
        }
        parcelable = (FileInfo)((Intent)parcelable).getParcelableExtra("android.telephony.extra.MBMS_FILE_INFO");
        Path path = FileSystems.getDefault().getPath(downloadRequest.getDestinationUri().getPath(), new String[0]);
        try {
            uri = MbmsDownloadReceiver.moveToFinalLocation(uri, path, MbmsDownloadReceiver.getFileRelativePath(downloadRequest.getSourceUri().getPath(), ((FileInfo)parcelable).getUri().getPath()));
            intent.putExtra("android.telephony.extra.MBMS_COMPLETED_FILE_URI", uri);
        }
        catch (IOException iOException) {
            Log.w(LOG_TAG, "Failed to move temp file to final destination");
            this.setResultCode(4);
            return;
        }
        intent.putExtra("android.telephony.extra.MBMS_FILE_INFO", parcelable);
        ((Context)object).sendBroadcast(intent);
        this.setResultCode(0);
    }

    private static Uri moveToFinalLocation(Uri comparable, Path object, String string2) throws IOException {
        if (!"file".equals(comparable.getScheme())) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Downloaded file location uri ");
            ((StringBuilder)object).append(comparable);
            ((StringBuilder)object).append(" does not have a file scheme");
            Log.w(LOG_TAG, ((StringBuilder)object).toString());
            return null;
        }
        comparable = FileSystems.getDefault().getPath(comparable.getPath(), new String[0]);
        if (!Files.isDirectory((object = object.resolve(string2)).getParent(), new LinkOption[0])) {
            Files.createDirectories(object.getParent(), new FileAttribute[0]);
        }
        return Uri.fromFile(Files.move((Path)comparable, (Path)object, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE).toFile());
    }

    private boolean verifyIntentContents(Context object, Intent object2) {
        if ("android.telephony.mbms.action.DOWNLOAD_RESULT_INTERNAL".equals(((Intent)object2).getAction())) {
            if (!((Intent)object2).hasExtra("android.telephony.extra.MBMS_DOWNLOAD_RESULT")) {
                Log.w(LOG_TAG, "Download result did not include a result code. Ignoring.");
                return false;
            }
            if (!((Intent)object2).hasExtra("android.telephony.extra.MBMS_DOWNLOAD_REQUEST")) {
                Log.w(LOG_TAG, "Download result did not include the associated request. Ignoring.");
                return false;
            }
            if (1 != ((Intent)object2).getIntExtra("android.telephony.extra.MBMS_DOWNLOAD_RESULT", 2)) {
                return true;
            }
            if (!((Intent)object2).hasExtra("android.telephony.mbms.extra.TEMP_FILE_ROOT")) {
                Log.w(LOG_TAG, "Download result did not include the temp file root. Ignoring.");
                return false;
            }
            if (!((Intent)object2).hasExtra("android.telephony.extra.MBMS_FILE_INFO")) {
                Log.w(LOG_TAG, "Download result did not include the associated file info. Ignoring.");
                return false;
            }
            if (!((Intent)object2).hasExtra("android.telephony.mbms.extra.FINAL_URI")) {
                Log.w(LOG_TAG, "Download result did not include the path to the final temp file. Ignoring.");
                return false;
            }
            object2 = (DownloadRequest)((Intent)object2).getParcelableExtra("android.telephony.extra.MBMS_DOWNLOAD_REQUEST");
            CharSequence charSequence = new StringBuilder();
            charSequence.append(((DownloadRequest)object2).getHash());
            charSequence.append(DOWNLOAD_TOKEN_SUFFIX);
            charSequence = charSequence.toString();
            object = new File(MbmsUtils.getEmbmsTempFileDirForService((Context)object, ((DownloadRequest)object2).getFileServiceId()), (String)charSequence);
            if (!((File)object).exists()) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Supplied download request does not match a token that we have. Expected ");
                ((StringBuilder)object2).append(object);
                Log.w(LOG_TAG, ((StringBuilder)object2).toString());
                return false;
            }
        } else if ("android.telephony.mbms.action.FILE_DESCRIPTOR_REQUEST".equals(((Intent)object2).getAction())) {
            if (!((Intent)object2).hasExtra("android.telephony.mbms.extra.SERVICE_ID")) {
                Log.w(LOG_TAG, "Temp file request did not include the associated service id. Ignoring.");
                return false;
            }
            if (!((Intent)object2).hasExtra("android.telephony.mbms.extra.TEMP_FILE_ROOT")) {
                Log.w(LOG_TAG, "Download result did not include the temp file root. Ignoring.");
                return false;
            }
        } else if ("android.telephony.mbms.action.CLEANUP".equals(((Intent)object2).getAction())) {
            if (!((Intent)object2).hasExtra("android.telephony.mbms.extra.SERVICE_ID")) {
                Log.w(LOG_TAG, "Cleanup request did not include the associated service id. Ignoring.");
                return false;
            }
            if (!((Intent)object2).hasExtra("android.telephony.mbms.extra.TEMP_FILE_ROOT")) {
                Log.w(LOG_TAG, "Cleanup request did not include the temp file root. Ignoring.");
                return false;
            }
            if (!((Intent)object2).hasExtra("android.telephony.mbms.extra.TEMP_FILES_IN_USE")) {
                Log.w(LOG_TAG, "Cleanup request did not include the list of temp files in use. Ignoring.");
                return false;
            }
        }
        return true;
    }

    private void verifyPermissionIntegrity(Context context) {
        Object object = context.getPackageManager().queryBroadcastReceivers(new Intent(context, MbmsDownloadReceiver.class), 0);
        if (object.size() == 1) {
            object = object.get((int)0).activityInfo;
            if (object != null) {
                if (MbmsUtils.getOverrideServiceName(context, "android.telephony.action.EmbmsDownload") != null) {
                    if (((ActivityInfo)object).permission != null) {
                        return;
                    }
                    throw new IllegalStateException("MbmsDownloadReceiver must require some permission");
                }
                if (Objects.equals(EMBMS_INTENT_PERMISSION, ((ActivityInfo)object).permission)) {
                    return;
                }
                throw new IllegalStateException("MbmsDownloadReceiver must require the SEND_EMBMS_INTENTS permission.");
            }
            throw new IllegalStateException("Queried ResolveInfo does not contain a receiver");
        }
        throw new IllegalStateException("Non-unique download receiver in your app");
    }

    private static boolean verifyTempFilePath(Context object, String string2, Uri object2) {
        if (!"file".equals(((Uri)object2).getScheme())) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Uri ");
            ((StringBuilder)object).append(object2);
            ((StringBuilder)object).append(" does not have a file scheme");
            Log.w(LOG_TAG, ((StringBuilder)object).toString());
            return false;
        }
        Serializable serializable = new File((String)(object2 = ((Uri)object2).getSchemeSpecificPart()));
        if (!((File)serializable).exists()) {
            object = new StringBuilder();
            ((StringBuilder)object).append("File at ");
            ((StringBuilder)object).append((String)object2);
            ((StringBuilder)object).append(" does not exist.");
            Log.w(LOG_TAG, ((StringBuilder)object).toString());
            return false;
        }
        if (!MbmsUtils.isContainedIn(MbmsUtils.getEmbmsTempFileDirForService((Context)object, string2), (File)serializable)) {
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("File at ");
            ((StringBuilder)serializable).append((String)object2);
            ((StringBuilder)serializable).append(" is not contained in the temp file root, which is ");
            ((StringBuilder)serializable).append(MbmsUtils.getEmbmsTempFileDirForService((Context)object, string2));
            Log.w(LOG_TAG, ((StringBuilder)serializable).toString());
            return false;
        }
        return true;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        this.verifyPermissionIntegrity(context);
        if (!this.verifyIntentContents(context, intent)) {
            this.setResultCode(2);
            return;
        }
        if (!Objects.equals(intent.getStringExtra("android.telephony.mbms.extra.TEMP_FILE_ROOT"), MbmsTempFileProvider.getEmbmsTempFileDir(context).getPath())) {
            this.setResultCode(3);
            return;
        }
        if ("android.telephony.mbms.action.DOWNLOAD_RESULT_INTERNAL".equals(intent.getAction())) {
            this.moveDownloadedFile(context, intent);
            this.cleanupPostMove(context, intent);
        } else if ("android.telephony.mbms.action.FILE_DESCRIPTOR_REQUEST".equals(intent.getAction())) {
            this.generateTempFiles(context, intent);
        } else if ("android.telephony.mbms.action.CLEANUP".equals(intent.getAction())) {
            this.cleanupTempFiles(context, intent);
        } else {
            this.setResultCode(1);
        }
    }

}

