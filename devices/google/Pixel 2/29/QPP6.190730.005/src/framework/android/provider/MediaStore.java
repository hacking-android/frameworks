/*
 * Decompiled with CFR 0.145.
 */
package android.provider;

import android.annotation.UnsupportedAppUsage;
import android.app.AppGlobals;
import android.app.Application;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.BaseBundle;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Environment;
import android.os.FileUtils;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.UserHandle;
import android.os.UserManager;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.os.storage.VolumeInfo;
import android.provider.BaseColumns;
import android.provider.Column;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import android.util.Size;
import com.android.internal.annotations.GuardedBy;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class MediaStore {
    public static final String ACTION_IMAGE_CAPTURE = "android.media.action.IMAGE_CAPTURE";
    public static final String ACTION_IMAGE_CAPTURE_SECURE = "android.media.action.IMAGE_CAPTURE_SECURE";
    public static final String ACTION_REVIEW = "android.provider.action.REVIEW";
    public static final String ACTION_REVIEW_SECURE = "android.provider.action.REVIEW_SECURE";
    public static final String ACTION_VIDEO_CAPTURE = "android.media.action.VIDEO_CAPTURE";
    public static final String AUTHORITY = "media";
    public static final Uri AUTHORITY_URI = Uri.parse("content://media");
    public static final String DELETE_CONTRIBUTED_MEDIA_CALL = "delete_contributed_media";
    public static final String EXTRA_BRIGHTNESS = "android.provider.extra.BRIGHTNESS";
    public static final String EXTRA_DURATION_LIMIT = "android.intent.extra.durationLimit";
    public static final String EXTRA_FINISH_ON_COMPLETION = "android.intent.extra.finishOnCompletion";
    public static final String EXTRA_FULL_SCREEN = "android.intent.extra.fullScreen";
    public static final String EXTRA_MEDIA_ALBUM = "android.intent.extra.album";
    public static final String EXTRA_MEDIA_ARTIST = "android.intent.extra.artist";
    public static final String EXTRA_MEDIA_FOCUS = "android.intent.extra.focus";
    public static final String EXTRA_MEDIA_GENRE = "android.intent.extra.genre";
    public static final String EXTRA_MEDIA_PLAYLIST = "android.intent.extra.playlist";
    public static final String EXTRA_MEDIA_RADIO_CHANNEL = "android.intent.extra.radio_channel";
    public static final String EXTRA_MEDIA_TITLE = "android.intent.extra.title";
    public static final String EXTRA_ORIGINATED_FROM_SHELL = "android.intent.extra.originated_from_shell";
    public static final String EXTRA_OUTPUT = "output";
    public static final String EXTRA_SCREEN_ORIENTATION = "android.intent.extra.screenOrientation";
    public static final String EXTRA_SHOW_ACTION_ICONS = "android.intent.extra.showActionIcons";
    public static final String EXTRA_SIZE_LIMIT = "android.intent.extra.sizeLimit";
    public static final String EXTRA_VIDEO_QUALITY = "android.intent.extra.videoQuality";
    public static final String GET_CONTRIBUTED_MEDIA_CALL = "get_contributed_media";
    public static final String GET_DOCUMENT_URI_CALL = "get_document_uri";
    public static final String GET_MEDIA_URI_CALL = "get_media_uri";
    public static final String GET_VERSION_CALL = "get_version";
    public static final String INTENT_ACTION_MEDIA_PLAY_FROM_SEARCH = "android.media.action.MEDIA_PLAY_FROM_SEARCH";
    public static final String INTENT_ACTION_MEDIA_SEARCH = "android.intent.action.MEDIA_SEARCH";
    @Deprecated
    public static final String INTENT_ACTION_MUSIC_PLAYER = "android.intent.action.MUSIC_PLAYER";
    public static final String INTENT_ACTION_STILL_IMAGE_CAMERA = "android.media.action.STILL_IMAGE_CAMERA";
    public static final String INTENT_ACTION_STILL_IMAGE_CAMERA_SECURE = "android.media.action.STILL_IMAGE_CAMERA_SECURE";
    public static final String INTENT_ACTION_TEXT_OPEN_FROM_SEARCH = "android.media.action.TEXT_OPEN_FROM_SEARCH";
    public static final String INTENT_ACTION_VIDEO_CAMERA = "android.media.action.VIDEO_CAMERA";
    public static final String INTENT_ACTION_VIDEO_PLAY_FROM_SEARCH = "android.media.action.VIDEO_PLAY_FROM_SEARCH";
    public static final String MEDIA_IGNORE_FILENAME = ".nomedia";
    public static final String MEDIA_SCANNER_VOLUME = "volume";
    public static final String META_DATA_STILL_IMAGE_CAMERA_PREWARM_SERVICE = "android.media.still_image_camera_preview_service";
    public static final String PARAM_DELETE_DATA = "deletedata";
    public static final String PARAM_INCLUDE_PENDING = "includePending";
    public static final String PARAM_INCLUDE_TRASHED = "includeTrashed";
    public static final String PARAM_LIMIT = "limit";
    public static final String PARAM_PROGRESS = "progress";
    public static final String PARAM_REQUIRE_ORIGINAL = "requireOriginal";
    public static final String RETRANSLATE_CALL = "update_titles";
    public static final String SCAN_FILE_CALL = "scan_file";
    public static final String SCAN_VOLUME_CALL = "scan_volume";
    private static final String TAG = "MediaStore";
    @Deprecated
    public static final String UNHIDE_CALL = "unhide";
    public static final String UNKNOWN_STRING = "<unknown>";
    public static final String VOLUME_EXTERNAL = "external";
    public static final String VOLUME_EXTERNAL_PRIMARY = "external_primary";
    public static final String VOLUME_INTERNAL = "internal";

    private static /* synthetic */ void $closeResource(Throwable throwable, AutoCloseable autoCloseable) {
        if (throwable != null) {
            try {
                autoCloseable.close();
            }
            catch (Throwable throwable2) {
                throwable.addSuppressed(throwable2);
            }
        } else {
            autoCloseable.close();
        }
    }

    private static void addCanonicalFile(List<File> list, File file) {
        try {
            list.add(file.getCanonicalFile());
        }
        catch (IOException iOException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Failed to resolve ");
            stringBuilder.append(file);
            stringBuilder.append(": ");
            stringBuilder.append(iOException);
            Log.w(TAG, stringBuilder.toString());
            list.add(file);
        }
    }

    public static String checkArgumentVolumeName(String string2) {
        if (!TextUtils.isEmpty(string2)) {
            if (VOLUME_INTERNAL.equals(string2)) {
                return string2;
            }
            if (VOLUME_EXTERNAL.equals(string2)) {
                return string2;
            }
            if (VOLUME_EXTERNAL_PRIMARY.equals(string2)) {
                return string2;
            }
            for (int i = 0; i < string2.length(); ++i) {
                char c = string2.charAt(i);
                if ('a' <= c && c <= 'f' || '0' <= c && c <= '9' || c == '-') {
                    continue;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Invalid volume name: ");
                stringBuilder.append(string2);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            return string2;
        }
        throw new IllegalArgumentException();
    }

    @Deprecated
    public static Uri createPending(Context context, PendingParams pendingParams) {
        return context.getContentResolver().insert(pendingParams.insertUri, pendingParams.insertValues);
    }

    public static void deleteContributedMedia(Context object, String string2, UserHandle parcelable) throws IOException {
        UserManager userManager = ((Context)object).getSystemService(UserManager.class);
        if (userManager.isUserUnlocked((UserHandle)parcelable) && userManager.isUserRunning((UserHandle)parcelable)) {
            try {
                object = ((Context)object).createPackageContextAsUser(string2, 0, (UserHandle)parcelable).getContentResolver();
                parcelable = new Bundle();
                ((BaseBundle)((Object)parcelable)).putString("android.intent.extra.PACKAGE_NAME", string2);
                ((ContentResolver)object).call(AUTHORITY, DELETE_CONTRIBUTED_MEDIA_CALL, null, (Bundle)parcelable);
                return;
            }
            catch (Exception exception) {
                throw new IOException(exception);
            }
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("User ");
        ((StringBuilder)object).append(parcelable);
        ((StringBuilder)object).append(" must be unlocked and running");
        throw new IOException(((StringBuilder)object).toString());
    }

    @Deprecated
    public static Set<String> getAllVolumeNames(Context context) {
        return MediaStore.getExternalVolumeNames(context);
    }

    public static long getContributedMediaSize(Context object, String string2, UserHandle parcelable) throws IOException {
        UserManager userManager = ((Context)object).getSystemService(UserManager.class);
        if (userManager.isUserUnlocked((UserHandle)parcelable) && userManager.isUserRunning((UserHandle)parcelable)) {
            try {
                object = ((Context)object).createPackageContextAsUser(string2, 0, (UserHandle)parcelable).getContentResolver();
                parcelable = new Bundle();
                ((BaseBundle)((Object)parcelable)).putString("android.intent.extra.PACKAGE_NAME", string2);
                long l = ((ContentResolver)object).call(AUTHORITY, GET_CONTRIBUTED_MEDIA_CALL, null, (Bundle)parcelable).getLong("android.intent.extra.INDEX");
                return l;
            }
            catch (Exception exception) {
                throw new IOException(exception);
            }
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("User ");
        ((StringBuilder)object).append(parcelable);
        ((StringBuilder)object).append(" must be unlocked and running");
        throw new IOException(((StringBuilder)object).toString());
    }

    /*
     * Exception decompiling
     */
    public static Uri getDocumentUri(Context var0, Uri var1_2) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:404)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:482)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    public static Set<String> getExternalVolumeNames(Context object) {
        StorageManager storageManager = ((Context)object).getSystemService(StorageManager.class);
        object = new ArraySet();
        for (VolumeInfo volumeInfo : storageManager.getVolumes()) {
            if (!volumeInfo.isVisibleForUser(UserHandle.myUserId()) || !volumeInfo.isMountedReadable()) continue;
            if (volumeInfo.isPrimary()) {
                object.add(VOLUME_EXTERNAL_PRIMARY);
                continue;
            }
            object.add(volumeInfo.getNormalizedFsUuid());
        }
        return object;
    }

    public static Uri getMediaScannerUri() {
        return AUTHORITY_URI.buildUpon().appendPath("none").appendPath("media_scanner").build();
    }

    /*
     * Exception decompiling
     */
    public static Uri getMediaUri(Context var0, Uri var1_2) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:404)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:482)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    public static String getVersion(Context context) {
        return MediaStore.getVersion(context, VOLUME_EXTERNAL_PRIMARY);
    }

    /*
     * Exception decompiling
     */
    public static String getVersion(Context var0, String var1_2) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:404)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:482)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    public static String getVolumeName(Uri uri) {
        Object object = uri.getPathSegments();
        if (uri.getAuthority().equals(AUTHORITY) && object != null && object.size() > 0) {
            return (String)object.get(0);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Missing volume name: ");
        ((StringBuilder)object).append(uri);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public static String getVolumeName(File file) {
        if (FileUtils.contains(Environment.getStorageDirectory(), file)) {
            Object object = AppGlobals.getInitialApplication().getSystemService(StorageManager.class).getStorageVolume(file);
            if (object != null) {
                if (((StorageVolume)object).isPrimary()) {
                    return VOLUME_EXTERNAL_PRIMARY;
                }
                return MediaStore.checkArgumentVolumeName(((StorageVolume)object).getNormalizedUuid());
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Unknown volume at ");
            ((StringBuilder)object).append(file);
            throw new IllegalStateException(((StringBuilder)object).toString());
        }
        return VOLUME_INTERNAL;
    }

    public static File getVolumePath(String string2) throws FileNotFoundException {
        return MediaStore.getVolumePath(AppGlobals.getInitialApplication().getSystemService(StorageManager.class).getVolumes(), string2);
    }

    public static File getVolumePath(List<VolumeInfo> object, String string2) throws FileNotFoundException {
        if (!TextUtils.isEmpty(string2)) {
            int n = -1;
            int n2 = string2.hashCode();
            if (n2 != -1820761141) {
                if (n2 == 570410685 && string2.equals(VOLUME_INTERNAL)) {
                    n = 0;
                }
            } else if (string2.equals(VOLUME_EXTERNAL)) {
                n = 1;
            }
            if (n != 0 && n != 1) {
                boolean bl = VOLUME_EXTERNAL_PRIMARY.equals(string2);
                object = object.iterator();
                while (object.hasNext()) {
                    Object object2 = (VolumeInfo)object.next();
                    n = bl && ((VolumeInfo)object2).isPrimary() ? 1 : 0;
                    n2 = !bl && Objects.equals(((VolumeInfo)object2).getNormalizedFsUuid(), string2) ? 1 : 0;
                    if (n == 0 && n2 == 0 || (object2 = ((VolumeInfo)object2).getPathForUser(UserHandle.myUserId())) == null) continue;
                    return object2;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("Failed to find path for ");
                ((StringBuilder)object).append(string2);
                throw new FileNotFoundException(((StringBuilder)object).toString());
            }
            object = new StringBuilder();
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append(" has no associated path");
            throw new FileNotFoundException(((StringBuilder)object).toString());
        }
        throw new IllegalArgumentException();
    }

    public static Collection<File> getVolumeScanPaths(String object) throws FileNotFoundException {
        if (!TextUtils.isEmpty((CharSequence)object)) {
            Application application = AppGlobals.getInitialApplication();
            UserManager userManager = application.getSystemService(UserManager.class);
            ArrayList<File> arrayList = new ArrayList<File>();
            if (VOLUME_INTERNAL.equals(object)) {
                MediaStore.addCanonicalFile(arrayList, new File(Environment.getRootDirectory(), AUTHORITY));
                MediaStore.addCanonicalFile(arrayList, new File(Environment.getOemDirectory(), AUTHORITY));
                MediaStore.addCanonicalFile(arrayList, new File(Environment.getProductDirectory(), AUTHORITY));
            } else if (VOLUME_EXTERNAL.equals(object)) {
                object = MediaStore.getExternalVolumeNames(application).iterator();
                while (object.hasNext()) {
                    MediaStore.addCanonicalFile(arrayList, MediaStore.getVolumePath((String)object.next()));
                }
                if (userManager.isDemoUser()) {
                    MediaStore.addCanonicalFile(arrayList, Environment.getDataPreloadsMediaDirectory());
                }
            } else {
                MediaStore.addCanonicalFile(arrayList, MediaStore.getVolumePath((String)object));
                if (VOLUME_EXTERNAL_PRIMARY.equals(object) && userManager.isDemoUser()) {
                    MediaStore.addCanonicalFile(arrayList, Environment.getDataPreloadsMediaDirectory());
                }
            }
            return arrayList;
        }
        throw new IllegalArgumentException();
    }

    @Deprecated
    public static PendingSession openPending(Context context, Uri uri) {
        return new PendingSession(context, uri);
    }

    /*
     * Exception decompiling
     */
    private static Uri scan(Context var0, String var1_2, File var2_4, boolean var3_6) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:404)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:482)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    public static Uri scanFile(Context context, File file) {
        return MediaStore.scan(context, SCAN_FILE_CALL, file, false);
    }

    public static Uri scanFileFromShell(Context context, File file) {
        return MediaStore.scan(context, SCAN_FILE_CALL, file, true);
    }

    public static void scanVolume(Context context, File file) {
        MediaStore.scan(context, SCAN_VOLUME_CALL, file, false);
    }

    public static Uri.Builder setIncludePending(Uri.Builder builder) {
        return builder.appendQueryParameter(PARAM_INCLUDE_PENDING, "1");
    }

    public static Uri setIncludePending(Uri uri) {
        return MediaStore.setIncludePending(uri.buildUpon()).build();
    }

    @Deprecated
    public static Uri setIncludeTrashed(Uri uri) {
        return uri.buildUpon().appendQueryParameter(PARAM_INCLUDE_TRASHED, "1").build();
    }

    public static Uri setRequireOriginal(Uri uri) {
        return uri.buildUpon().appendQueryParameter(PARAM_REQUIRE_ORIGINAL, "1").build();
    }

    @Deprecated
    public static void trash(Context context, Uri uri) {
        MediaStore.trash(context, uri, 172800000L);
    }

    @Deprecated
    public static void trash(Context context, Uri uri, long l) {
        if (l >= 0L) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("is_trashed", 1);
            contentValues.put("date_expires", (System.currentTimeMillis() + l) / 1000L);
            context.getContentResolver().update(uri, contentValues, null, null);
            return;
        }
        throw new IllegalArgumentException();
    }

    @Deprecated
    public static void untrash(Context context, Uri uri) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("is_trashed", 0);
        contentValues.putNull("date_expires");
        context.getContentResolver().update(uri, contentValues, null, null);
    }

    public static final class Audio {
        public static String keyFor(String charSequence) {
            block11 : {
                String string2;
                boolean bl;
                block13 : {
                    block12 : {
                        if (charSequence == null) break block11;
                        bl = false;
                        if (((String)charSequence).equals(MediaStore.UNKNOWN_STRING)) {
                            return "\u0001";
                        }
                        if (((String)charSequence).startsWith("\u0001")) {
                            bl = true;
                        }
                        string2 = ((String)charSequence).trim().toLowerCase();
                        charSequence = string2;
                        if (string2.startsWith("the ")) {
                            charSequence = string2.substring(4);
                        }
                        string2 = charSequence;
                        if (((String)charSequence).startsWith("an ")) {
                            string2 = ((String)charSequence).substring(3);
                        }
                        charSequence = string2;
                        if (string2.startsWith("a ")) {
                            charSequence = string2.substring(2);
                        }
                        if (((String)charSequence).endsWith(", the") || ((String)charSequence).endsWith(",the") || ((String)charSequence).endsWith(", an") || ((String)charSequence).endsWith(",an") || ((String)charSequence).endsWith(", a")) break block12;
                        string2 = charSequence;
                        if (!((String)charSequence).endsWith(",a")) break block13;
                    }
                    string2 = ((String)charSequence).substring(0, ((String)charSequence).lastIndexOf(44));
                }
                if ((string2 = string2.replaceAll("[\\[\\]\\(\\)\"'.,?!]", "").trim()).length() > 0) {
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append('.');
                    int n = string2.length();
                    for (int i = 0; i < n; ++i) {
                        ((StringBuilder)charSequence).append(string2.charAt(i));
                        ((StringBuilder)charSequence).append('.');
                    }
                    string2 = DatabaseUtils.getCollationKey(((StringBuilder)charSequence).toString());
                    charSequence = string2;
                    if (bl) {
                        charSequence = new StringBuilder();
                        ((StringBuilder)charSequence).append("\u0001");
                        ((StringBuilder)charSequence).append(string2);
                        charSequence = ((StringBuilder)charSequence).toString();
                    }
                    return charSequence;
                }
                return "";
            }
            return null;
        }

        public static interface AlbumColumns {
            @Column(readOnly=true, value=3)
            public static final String ALBUM = "album";
            @Column(value=3)
            @Deprecated
            public static final String ALBUM_ART = "album_art";
            @Column(readOnly=true, value=1)
            public static final String ALBUM_ID = "album_id";
            @Column(readOnly=true, value=3)
            public static final String ALBUM_KEY = "album_key";
            @Column(readOnly=true, value=3)
            public static final String ARTIST = "artist";
            @Column(readOnly=true, value=1)
            public static final String ARTIST_ID = "artist_id";
            @Column(readOnly=true, value=1)
            public static final String FIRST_YEAR = "minyear";
            @Column(readOnly=true, value=1)
            public static final String LAST_YEAR = "maxyear";
            @Column(readOnly=true, value=1)
            public static final String NUMBER_OF_SONGS = "numsongs";
            @Column(readOnly=true, value=1)
            public static final String NUMBER_OF_SONGS_FOR_ARTIST = "numsongs_by_artist";
        }

        public static final class Albums
        implements BaseColumns,
        AlbumColumns {
            public static final String CONTENT_TYPE = "vnd.android.cursor.dir/albums";
            public static final String DEFAULT_SORT_ORDER = "album_key";
            public static final String ENTRY_CONTENT_TYPE = "vnd.android.cursor.item/album";
            public static final Uri EXTERNAL_CONTENT_URI;
            public static final Uri INTERNAL_CONTENT_URI;

            static {
                INTERNAL_CONTENT_URI = Albums.getContentUri(MediaStore.VOLUME_INTERNAL);
                EXTERNAL_CONTENT_URI = Albums.getContentUri(MediaStore.VOLUME_EXTERNAL);
            }

            public static Uri getContentUri(String string2) {
                return AUTHORITY_URI.buildUpon().appendPath(string2).appendPath("audio").appendPath("albums").build();
            }
        }

        public static interface ArtistColumns {
            @Column(readOnly=true, value=3)
            public static final String ARTIST = "artist";
            @Column(readOnly=true, value=3)
            public static final String ARTIST_KEY = "artist_key";
            @Column(readOnly=true, value=1)
            public static final String NUMBER_OF_ALBUMS = "number_of_albums";
            @Column(readOnly=true, value=1)
            public static final String NUMBER_OF_TRACKS = "number_of_tracks";
        }

        public static final class Artists
        implements BaseColumns,
        ArtistColumns {
            public static final String CONTENT_TYPE = "vnd.android.cursor.dir/artists";
            public static final String DEFAULT_SORT_ORDER = "artist_key";
            public static final String ENTRY_CONTENT_TYPE = "vnd.android.cursor.item/artist";
            public static final Uri EXTERNAL_CONTENT_URI;
            public static final Uri INTERNAL_CONTENT_URI;

            static {
                INTERNAL_CONTENT_URI = Artists.getContentUri(MediaStore.VOLUME_INTERNAL);
                EXTERNAL_CONTENT_URI = Artists.getContentUri(MediaStore.VOLUME_EXTERNAL);
            }

            public static Uri getContentUri(String string2) {
                return AUTHORITY_URI.buildUpon().appendPath(string2).appendPath("audio").appendPath("artists").build();
            }

            public static final class Albums
            implements AlbumColumns {
                public static final Uri getContentUri(String string2, long l) {
                    return ContentUris.withAppendedId(Artists.getContentUri(string2), l).buildUpon().appendPath("albums").build();
                }
            }

        }

        public static interface AudioColumns
        extends MediaColumns {
            @Column(readOnly=true, value=3)
            public static final String ALBUM = "album";
            @Column(readOnly=true, value=3)
            public static final String ALBUM_ARTIST = "album_artist";
            @Column(readOnly=true, value=1)
            public static final String ALBUM_ID = "album_id";
            @Column(readOnly=true, value=3)
            public static final String ALBUM_KEY = "album_key";
            @Column(readOnly=true, value=3)
            public static final String ARTIST = "artist";
            @Column(readOnly=true, value=1)
            public static final String ARTIST_ID = "artist_id";
            @Column(readOnly=true, value=3)
            public static final String ARTIST_KEY = "artist_key";
            @Column(value=1)
            public static final String BOOKMARK = "bookmark";
            @Deprecated
            public static final String COMPILATION = "compilation";
            @Column(readOnly=true, value=3)
            public static final String COMPOSER = "composer";
            public static final String DURATION = "duration";
            @Deprecated
            public static final String GENRE = "genre";
            @Column(readOnly=true, value=1)
            public static final String IS_ALARM = "is_alarm";
            @Column(readOnly=true, value=1)
            public static final String IS_AUDIOBOOK = "is_audiobook";
            @Column(readOnly=true, value=1)
            public static final String IS_MUSIC = "is_music";
            @Column(readOnly=true, value=1)
            public static final String IS_NOTIFICATION = "is_notification";
            @Column(readOnly=true, value=1)
            public static final String IS_PODCAST = "is_podcast";
            @Column(readOnly=true, value=1)
            public static final String IS_RINGTONE = "is_ringtone";
            @Column(readOnly=true, value=3)
            public static final String TITLE_KEY = "title_key";
            @Column(readOnly=true, value=3)
            public static final String TITLE_RESOURCE_URI = "title_resource_uri";
            @Column(readOnly=true, value=1)
            public static final String TRACK = "track";
            @Column(readOnly=true, value=1)
            public static final String YEAR = "year";
        }

        public static final class Genres
        implements BaseColumns,
        GenresColumns {
            public static final String CONTENT_TYPE = "vnd.android.cursor.dir/genre";
            public static final String DEFAULT_SORT_ORDER = "name";
            public static final String ENTRY_CONTENT_TYPE = "vnd.android.cursor.item/genre";
            public static final Uri EXTERNAL_CONTENT_URI;
            public static final Uri INTERNAL_CONTENT_URI;

            static {
                INTERNAL_CONTENT_URI = Genres.getContentUri(MediaStore.VOLUME_INTERNAL);
                EXTERNAL_CONTENT_URI = Genres.getContentUri(MediaStore.VOLUME_EXTERNAL);
            }

            public static Uri getContentUri(String string2) {
                return AUTHORITY_URI.buildUpon().appendPath(string2).appendPath("audio").appendPath("genres").build();
            }

            public static Uri getContentUriForAudioId(String string2, int n) {
                return ContentUris.withAppendedId(Media.getContentUri(string2), n).buildUpon().appendPath("genres").build();
            }

            public static final class Members
            implements AudioColumns {
                @Column(value=1)
                public static final String AUDIO_ID = "audio_id";
                public static final String CONTENT_DIRECTORY = "members";
                public static final String DEFAULT_SORT_ORDER = "title_key";
                @Column(value=1)
                public static final String GENRE_ID = "genre_id";

                public static final Uri getContentUri(String string2, long l) {
                    return ContentUris.withAppendedId(Genres.getContentUri(string2), l).buildUpon().appendPath(CONTENT_DIRECTORY).build();
                }
            }

        }

        public static interface GenresColumns {
            @Column(value=3)
            public static final String NAME = "name";
        }

        public static final class Media
        implements AudioColumns {
            public static final String CONTENT_TYPE = "vnd.android.cursor.dir/audio";
            public static final String DEFAULT_SORT_ORDER = "title_key";
            public static final String ENTRY_CONTENT_TYPE = "vnd.android.cursor.item/audio";
            public static final Uri EXTERNAL_CONTENT_URI;
            public static final String EXTRA_MAX_BYTES = "android.provider.MediaStore.extra.MAX_BYTES";
            public static final Uri INTERNAL_CONTENT_URI;
            public static final String RECORD_SOUND_ACTION = "android.provider.MediaStore.RECORD_SOUND";

            static {
                INTERNAL_CONTENT_URI = Media.getContentUri(MediaStore.VOLUME_INTERNAL);
                EXTERNAL_CONTENT_URI = Media.getContentUri(MediaStore.VOLUME_EXTERNAL);
            }

            public static Uri getContentUri(String string2) {
                return AUTHORITY_URI.buildUpon().appendPath(string2).appendPath("audio").appendPath(MediaStore.AUTHORITY).build();
            }

            public static Uri getContentUri(String string2, long l) {
                return ContentUris.withAppendedId(Media.getContentUri(string2), l);
            }

            @Deprecated
            public static Uri getContentUriForPath(String string2) {
                return Media.getContentUri(MediaStore.getVolumeName(new File(string2)));
            }
        }

        public static final class Playlists
        implements BaseColumns,
        PlaylistsColumns {
            public static final String CONTENT_TYPE = "vnd.android.cursor.dir/playlist";
            public static final String DEFAULT_SORT_ORDER = "name";
            public static final String ENTRY_CONTENT_TYPE = "vnd.android.cursor.item/playlist";
            public static final Uri EXTERNAL_CONTENT_URI;
            public static final Uri INTERNAL_CONTENT_URI;

            static {
                INTERNAL_CONTENT_URI = Playlists.getContentUri(MediaStore.VOLUME_INTERNAL);
                EXTERNAL_CONTENT_URI = Playlists.getContentUri(MediaStore.VOLUME_EXTERNAL);
            }

            public static Uri getContentUri(String string2) {
                return AUTHORITY_URI.buildUpon().appendPath(string2).appendPath("audio").appendPath("playlists").build();
            }

            public static final class Members
            implements AudioColumns {
                @Column(value=1)
                public static final String AUDIO_ID = "audio_id";
                public static final String CONTENT_DIRECTORY = "members";
                public static final String DEFAULT_SORT_ORDER = "play_order";
                @Column(value=1)
                public static final String PLAYLIST_ID = "playlist_id";
                @Column(value=1)
                public static final String PLAY_ORDER = "play_order";
                @Column(value=1)
                public static final String _ID = "_id";

                public static final Uri getContentUri(String string2, long l) {
                    return ContentUris.withAppendedId(Playlists.getContentUri(string2), l).buildUpon().appendPath(CONTENT_DIRECTORY).build();
                }

                public static final boolean moveItem(ContentResolver contentResolver, long l, int n, int n2) {
                    Uri uri = Members.getContentUri(MediaStore.VOLUME_EXTERNAL, l).buildUpon().appendEncodedPath(String.valueOf(n)).appendQueryParameter("move", "true").build();
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("play_order", n2);
                    boolean bl = contentResolver.update(uri, contentValues, null, null) != 0;
                    return bl;
                }
            }

        }

        public static interface PlaylistsColumns {
            @Column(value=3)
            @Deprecated
            public static final String DATA = "_data";
            @Column(readOnly=true, value=1)
            public static final String DATE_ADDED = "date_added";
            @Column(readOnly=true, value=1)
            public static final String DATE_MODIFIED = "date_modified";
            @Column(value=3)
            public static final String NAME = "name";
        }

        public static final class Radio {
            public static final String ENTRY_CONTENT_TYPE = "vnd.android.cursor.item/radio";

            private Radio() {
            }
        }

        @Deprecated
        public static class Thumbnails
        implements BaseColumns {
            @Column(value=1)
            public static final String ALBUM_ID = "album_id";
            @Column(value=3)
            @Deprecated
            public static final String DATA = "_data";
        }

    }

    public static interface DownloadColumns
    extends MediaColumns {
        @Column(value=3)
        @Deprecated
        public static final String DESCRIPTION = "description";
        @Column(value=3)
        public static final String DOWNLOAD_URI = "download_uri";
        @Column(value=3)
        public static final String REFERER_URI = "referer_uri";
    }

    public static final class Downloads
    implements DownloadColumns {
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/download";
        public static final Uri EXTERNAL_CONTENT_URI;
        public static final Uri INTERNAL_CONTENT_URI;
        private static final Pattern PATTERN_DOWNLOADS_DIRECTORY;
        public static final Pattern PATTERN_DOWNLOADS_FILE;

        static {
            INTERNAL_CONTENT_URI = Downloads.getContentUri(MediaStore.VOLUME_INTERNAL);
            EXTERNAL_CONTENT_URI = Downloads.getContentUri(MediaStore.VOLUME_EXTERNAL);
            PATTERN_DOWNLOADS_FILE = Pattern.compile("(?i)^/storage/[^/]+/(?:[0-9]+/)?(?:Android/sandbox/[^/]+/)?Download/.+");
            PATTERN_DOWNLOADS_DIRECTORY = Pattern.compile("(?i)^/storage/[^/]+/(?:[0-9]+/)?(?:Android/sandbox/[^/]+/)?Download/?");
        }

        private Downloads() {
        }

        public static Uri getContentUri(String string2) {
            return AUTHORITY_URI.buildUpon().appendPath(string2).appendPath("downloads").build();
        }

        public static Uri getContentUri(String string2, long l) {
            return ContentUris.withAppendedId(Downloads.getContentUri(string2), l);
        }

        public static Uri getContentUriForPath(String string2) {
            return Downloads.getContentUri(MediaStore.getVolumeName(new File(string2)));
        }

        public static boolean isDownload(String string2) {
            return PATTERN_DOWNLOADS_FILE.matcher(string2).matches();
        }

        public static boolean isDownloadDir(String string2) {
            return PATTERN_DOWNLOADS_DIRECTORY.matcher(string2).matches();
        }
    }

    public static final class Files {
        public static final Uri EXTERNAL_CONTENT_URI = Files.getContentUri("external");
        public static final String TABLE = "files";

        public static Uri getContentUri(String string2) {
            return AUTHORITY_URI.buildUpon().appendPath(string2).appendPath("file").build();
        }

        public static final Uri getContentUri(String string2, long l) {
            return ContentUris.withAppendedId(Files.getContentUri(string2), l);
        }

        public static final Uri getContentUriForPath(String string2) {
            return Files.getContentUri(MediaStore.getVolumeName(new File(string2)));
        }

        public static final Uri getDirectoryUri(String string2) {
            return AUTHORITY_URI.buildUpon().appendPath(string2).appendPath("dir").build();
        }

        @UnsupportedAppUsage
        public static Uri getMtpObjectsUri(String string2) {
            return AUTHORITY_URI.buildUpon().appendPath(string2).appendPath("object").build();
        }

        @UnsupportedAppUsage
        public static final Uri getMtpObjectsUri(String string2, long l) {
            return ContentUris.withAppendedId(Files.getMtpObjectsUri(string2), l);
        }

        @UnsupportedAppUsage
        public static final Uri getMtpReferencesUri(String string2, long l) {
            return Files.getMtpObjectsUri(string2, l).buildUpon().appendPath("references").build();
        }

        public static interface FileColumns
        extends MediaColumns {
            @Column(readOnly=true, value=1)
            @UnsupportedAppUsage
            public static final String FORMAT = "format";
            @Column(readOnly=true, value=1)
            public static final String IS_DOWNLOAD = "is_download";
            @Column(value=1)
            public static final String MEDIA_TYPE = "media_type";
            public static final int MEDIA_TYPE_AUDIO = 2;
            public static final int MEDIA_TYPE_IMAGE = 1;
            public static final int MEDIA_TYPE_NONE = 0;
            public static final int MEDIA_TYPE_PLAYLIST = 4;
            public static final int MEDIA_TYPE_VIDEO = 3;
            @Column(value=3)
            public static final String MIME_TYPE = "mime_type";
            @Column(readOnly=true, value=1)
            public static final String PARENT = "parent";
            @Deprecated
            @UnsupportedAppUsage
            public static final String STORAGE_ID = "storage_id";
            @Column(readOnly=true, value=3)
            public static final String TITLE = "title";
        }

    }

    public static final class Images {

        public static interface ImageColumns
        extends MediaColumns {
            public static final String BUCKET_DISPLAY_NAME = "bucket_display_name";
            public static final String BUCKET_ID = "bucket_id";
            public static final String DATE_TAKEN = "datetaken";
            @Column(readOnly=true, value=3)
            public static final String DESCRIPTION = "description";
            public static final String GROUP_ID = "group_id";
            @Column(value=1)
            public static final String IS_PRIVATE = "isprivate";
            @Column(readOnly=true, value=2)
            @Deprecated
            public static final String LATITUDE = "latitude";
            @Column(readOnly=true, value=2)
            @Deprecated
            public static final String LONGITUDE = "longitude";
            @Column(value=1)
            @Deprecated
            public static final String MINI_THUMB_MAGIC = "mini_thumb_magic";
            public static final String ORIENTATION = "orientation";
            @Column(value=3)
            @Deprecated
            public static final String PICASA_ID = "picasa_id";
        }

        public static final class Media
        implements ImageColumns {
            public static final String CONTENT_TYPE = "vnd.android.cursor.dir/image";
            public static final String DEFAULT_SORT_ORDER = "bucket_display_name";
            public static final Uri EXTERNAL_CONTENT_URI;
            public static final Uri INTERNAL_CONTENT_URI;

            private static /* synthetic */ void $closeResource(Throwable throwable, AutoCloseable autoCloseable) {
                if (throwable != null) {
                    try {
                        autoCloseable.close();
                    }
                    catch (Throwable throwable2) {
                        throwable.addSuppressed(throwable2);
                    }
                } else {
                    autoCloseable.close();
                }
            }

            static {
                INTERNAL_CONTENT_URI = Media.getContentUri(MediaStore.VOLUME_INTERNAL);
                EXTERNAL_CONTENT_URI = Media.getContentUri(MediaStore.VOLUME_EXTERNAL);
            }

            @Deprecated
            public static final Bitmap getBitmap(ContentResolver object, Uri object2) throws FileNotFoundException, IOException {
                object2 = ((ContentResolver)object).openInputStream((Uri)object2);
                object = BitmapFactory.decodeStream((InputStream)object2);
                ((InputStream)object2).close();
                return object;
            }

            public static Uri getContentUri(String string2) {
                return AUTHORITY_URI.buildUpon().appendPath(string2).appendPath("images").appendPath(MediaStore.AUTHORITY).build();
            }

            public static Uri getContentUri(String string2, long l) {
                return ContentUris.withAppendedId(Media.getContentUri(string2), l);
            }

            /*
             * Exception decompiling
             */
            @Deprecated
            public static final String insertImage(ContentResolver var0, Bitmap var1_1, String var2_5, String var3_6) {
                // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
                // org.benf.cfr.reader.util.ConfusedCFRException: Started 4 blocks at once
                // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:404)
                // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:482)
                // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
                // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
                // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
                // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
                // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
                // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
                // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
                // org.benf.cfr.reader.entities.ClassFile.analyseInnerClassesPass1(ClassFile.java:778)
                // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:886)
                // org.benf.cfr.reader.entities.ClassFile.analyseInnerClassesPass1(ClassFile.java:778)
                // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:886)
                // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
                // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
                // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
                // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
                // org.benf.cfr.reader.Main.main(Main.java:48)
                throw new IllegalStateException("Decompilation failed");
            }

            /*
             * Exception decompiling
             */
            @Deprecated
            public static final String insertImage(ContentResolver var0, String var1_1, String var2_2, String var3_4) throws FileNotFoundException {
                // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
                // org.benf.cfr.reader.util.ConfusedCFRException: Started 6 blocks at once
                // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:404)
                // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:482)
                // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
                // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
                // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
                // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
                // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
                // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
                // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
                // org.benf.cfr.reader.entities.ClassFile.analyseInnerClassesPass1(ClassFile.java:778)
                // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:886)
                // org.benf.cfr.reader.entities.ClassFile.analyseInnerClassesPass1(ClassFile.java:778)
                // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:886)
                // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
                // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
                // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
                // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
                // org.benf.cfr.reader.Main.main(Main.java:48)
                throw new IllegalStateException("Decompilation failed");
            }

            @Deprecated
            public static final Cursor query(ContentResolver contentResolver, Uri uri, String[] arrstring) {
                return contentResolver.query(uri, arrstring, null, null, DEFAULT_SORT_ORDER);
            }

            @Deprecated
            public static final Cursor query(ContentResolver contentResolver, Uri uri, String[] arrstring, String string2, String string3) {
                block0 : {
                    if (string3 != null) break block0;
                    string3 = DEFAULT_SORT_ORDER;
                }
                return contentResolver.query(uri, arrstring, string2, null, string3);
            }

            @Deprecated
            public static final Cursor query(ContentResolver contentResolver, Uri uri, String[] arrstring, String string2, String[] arrstring2, String string3) {
                block0 : {
                    if (string3 != null) break block0;
                    string3 = DEFAULT_SORT_ORDER;
                }
                return contentResolver.query(uri, arrstring, string2, arrstring2, string3);
            }
        }

        @Deprecated
        public static class Thumbnails
        implements BaseColumns {
            @Column(value=3)
            @Deprecated
            public static final String DATA = "_data";
            public static final String DEFAULT_SORT_ORDER = "image_id ASC";
            public static final Uri EXTERNAL_CONTENT_URI;
            public static final int FULL_SCREEN_KIND = 2;
            @Column(readOnly=true, value=1)
            public static final String HEIGHT = "height";
            @Column(value=1)
            public static final String IMAGE_ID = "image_id";
            public static final Uri INTERNAL_CONTENT_URI;
            @Column(value=1)
            public static final String KIND = "kind";
            public static final int MICRO_KIND = 3;
            public static final int MINI_KIND = 1;
            @Column(value=4)
            @Deprecated
            public static final String THUMB_DATA = "thumb_data";
            @Column(readOnly=true, value=1)
            public static final String WIDTH = "width";

            static {
                INTERNAL_CONTENT_URI = Thumbnails.getContentUri(MediaStore.VOLUME_INTERNAL);
                EXTERNAL_CONTENT_URI = Thumbnails.getContentUri(MediaStore.VOLUME_EXTERNAL);
            }

            @Deprecated
            public static void cancelThumbnailRequest(ContentResolver contentResolver, long l) {
                InternalThumbnails.cancelThumbnail(contentResolver, ContentUris.withAppendedId(Media.EXTERNAL_CONTENT_URI, l));
            }

            @Deprecated
            public static void cancelThumbnailRequest(ContentResolver contentResolver, long l, long l2) {
                Thumbnails.cancelThumbnailRequest(contentResolver, l);
            }

            public static Uri getContentUri(String string2) {
                return AUTHORITY_URI.buildUpon().appendPath(string2).appendPath("images").appendPath("thumbnails").build();
            }

            @Deprecated
            public static Bitmap getThumbnail(ContentResolver contentResolver, long l, int n, BitmapFactory.Options options) {
                return InternalThumbnails.getThumbnail(contentResolver, ContentUris.withAppendedId(Media.EXTERNAL_CONTENT_URI, l), n, options);
            }

            @Deprecated
            public static Bitmap getThumbnail(ContentResolver contentResolver, long l, long l2, int n, BitmapFactory.Options options) {
                return Thumbnails.getThumbnail(contentResolver, l, n, options);
            }

            @Deprecated
            public static final Cursor query(ContentResolver contentResolver, Uri uri, String[] arrstring) {
                return contentResolver.query(uri, arrstring, null, null, DEFAULT_SORT_ORDER);
            }

            @Deprecated
            public static final Cursor queryMiniThumbnail(ContentResolver contentResolver, long l, int n, String[] arrstring) {
                Uri uri = EXTERNAL_CONTENT_URI;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("image_id = ");
                stringBuilder.append(l);
                stringBuilder.append(" AND ");
                stringBuilder.append(KIND);
                stringBuilder.append(" = ");
                stringBuilder.append(n);
                return contentResolver.query(uri, arrstring, stringBuilder.toString(), null, null);
            }

            @Deprecated
            public static final Cursor queryMiniThumbnails(ContentResolver contentResolver, Uri uri, int n, String[] arrstring) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("kind = ");
                stringBuilder.append(n);
                return contentResolver.query(uri, arrstring, stringBuilder.toString(), null, DEFAULT_SORT_ORDER);
            }
        }

    }

    @Deprecated
    private static class InternalThumbnails
    implements BaseColumns {
        @GuardedBy(value={"sPending"})
        private static ArrayMap<Uri, CancellationSignal> sPending = new ArrayMap();

        private InternalThumbnails() {
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Deprecated
        static void cancelThumbnail(ContentResolver object, Uri object2) {
            object = sPending;
            synchronized (object) {
                object2 = sPending.get(object2);
                if (object2 != null) {
                    ((CancellationSignal)object2).cancel();
                }
                return;
            }
        }

        /*
         * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
         * Loose catch block
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        @Deprecated
        static Bitmap getThumbnail(ContentResolver arrayMap, Uri uri, int n, BitmapFactory.Options object) {
            Throwable throwable2222;
            CancellationSignal cancellationSignal2;
            if (n == 3) {
                object = ThumbnailConstants.MICRO_SIZE;
            } else if (n == 2) {
                object = ThumbnailConstants.FULL_SCREEN_SIZE;
            } else {
                if (n != 1) {
                    arrayMap = new StringBuilder();
                    ((StringBuilder)((Object)arrayMap)).append("Unsupported kind: ");
                    ((StringBuilder)((Object)arrayMap)).append(n);
                    throw new IllegalArgumentException(((StringBuilder)((Object)arrayMap)).toString());
                }
                object = ThumbnailConstants.MINI_SIZE;
            }
            ArrayMap<Uri, CancellationSignal> arrayMap2 = sPending;
            // MONITORENTER : arrayMap2
            CancellationSignal cancellationSignal = cancellationSignal2 = sPending.get(uri);
            if (cancellationSignal2 == null) {
                cancellationSignal = new CancellationSignal();
                sPending.put(uri, cancellationSignal);
            }
            // MONITOREXIT : arrayMap2
            object = ((ContentResolver)((Object)arrayMap)).loadThumbnail(uri, Point.convert((Point)object), cancellationSignal);
            arrayMap = sPending;
            sPending.remove(uri);
            // MONITOREXIT : arrayMap
            return object;
            {
                catch (Throwable throwable2222) {
                }
                catch (IOException iOException) {}
                {
                    arrayMap = new ArrayMap<Uri, CancellationSignal>();
                    ((StringBuilder)((Object)arrayMap)).append("Failed to obtain thumbnail for ");
                    ((StringBuilder)((Object)arrayMap)).append(uri);
                    Log.w(MediaStore.TAG, ((StringBuilder)((Object)arrayMap)).toString(), iOException);
                    arrayMap = sPending;
                }
                // MONITORENTER : arrayMap
                sPending.remove(uri);
                // MONITOREXIT : arrayMap
                return null;
            }
            arrayMap = sPending;
            // MONITORENTER : arrayMap
            sPending.remove(uri);
            // MONITOREXIT : arrayMap
            throw throwable2222;
        }
    }

    public static interface MediaColumns
    extends BaseColumns {
        @Column(readOnly=true, value=3)
        public static final String BUCKET_DISPLAY_NAME = "bucket_display_name";
        @Column(readOnly=true, value=1)
        public static final String BUCKET_ID = "bucket_id";
        @Column(value=3)
        @Deprecated
        public static final String DATA = "_data";
        @Column(readOnly=true, value=1)
        public static final String DATE_ADDED = "date_added";
        @Column(value=1)
        public static final String DATE_EXPIRES = "date_expires";
        @Column(readOnly=true, value=1)
        public static final String DATE_MODIFIED = "date_modified";
        @Column(readOnly=true, value=1)
        public static final String DATE_TAKEN = "datetaken";
        @Column(value=3)
        public static final String DISPLAY_NAME = "_display_name";
        @Column(readOnly=true, value=3)
        public static final String DOCUMENT_ID = "document_id";
        @Column(readOnly=true, value=1)
        public static final String DURATION = "duration";
        @Column(readOnly=true, value=1)
        @Deprecated
        public static final String GROUP_ID = "group_id";
        @Column(readOnly=true, value=4)
        @Deprecated
        public static final String HASH = "_hash";
        @Column(readOnly=true, value=1)
        public static final String HEIGHT = "height";
        @Column(readOnly=true, value=3)
        public static final String INSTANCE_ID = "instance_id";
        @Column(value=1)
        @Deprecated
        @UnsupportedAppUsage
        public static final String IS_DRM = "is_drm";
        @Column(value=1)
        public static final String IS_PENDING = "is_pending";
        @Column(value=1)
        @Deprecated
        public static final String IS_TRASHED = "is_trashed";
        @Deprecated
        public static final String MEDIA_SCANNER_NEW_OBJECT_ID = "media_scanner_new_object_id";
        @Column(value=3)
        public static final String MIME_TYPE = "mime_type";
        @Column(readOnly=true, value=1)
        public static final String ORIENTATION = "orientation";
        @Column(readOnly=true, value=3)
        public static final String ORIGINAL_DOCUMENT_ID = "original_document_id";
        @Column(readOnly=true, value=3)
        public static final String OWNER_PACKAGE_NAME = "owner_package_name";
        @Column(value=3)
        @Deprecated
        public static final String PRIMARY_DIRECTORY = "primary_directory";
        @Column(value=3)
        public static final String RELATIVE_PATH = "relative_path";
        @Column(value=3)
        @Deprecated
        public static final String SECONDARY_DIRECTORY = "secondary_directory";
        @Column(readOnly=true, value=1)
        public static final String SIZE = "_size";
        @Column(readOnly=true, value=3)
        public static final String TITLE = "title";
        @Column(readOnly=true, value=3)
        public static final String VOLUME_NAME = "volume_name";
        @Column(readOnly=true, value=1)
        public static final String WIDTH = "width";
    }

    @Deprecated
    public static class PendingParams {
        public final Uri insertUri;
        public final ContentValues insertValues;

        public PendingParams(Uri uri, String string2, String string3) {
            this.insertUri = Objects.requireNonNull(uri);
            long l = System.currentTimeMillis() / 1000L;
            this.insertValues = new ContentValues();
            this.insertValues.put("_display_name", Objects.requireNonNull(string2));
            this.insertValues.put("mime_type", Objects.requireNonNull(string3));
            this.insertValues.put("date_added", l);
            this.insertValues.put("date_modified", l);
            this.insertValues.put("is_pending", 1);
            this.insertValues.put("date_expires", (System.currentTimeMillis() + 86400000L) / 1000L);
        }

        public void setDownloadUri(Uri uri) {
            if (uri == null) {
                this.insertValues.remove("download_uri");
            } else {
                this.insertValues.put("download_uri", uri.toString());
            }
        }

        public void setPrimaryDirectory(String string2) {
            if (string2 == null) {
                this.insertValues.remove("primary_directory");
            } else {
                this.insertValues.put("primary_directory", string2);
            }
        }

        public void setRefererUri(Uri uri) {
            if (uri == null) {
                this.insertValues.remove("referer_uri");
            } else {
                this.insertValues.put("referer_uri", uri.toString());
            }
        }

        public void setSecondaryDirectory(String string2) {
            if (string2 == null) {
                this.insertValues.remove("secondary_directory");
            } else {
                this.insertValues.put("secondary_directory", string2);
            }
        }
    }

    @Deprecated
    public static class PendingSession
    implements AutoCloseable {
        private final Context mContext;
        private final Uri mUri;

        public PendingSession(Context context, Uri uri) {
            this.mContext = Objects.requireNonNull(context);
            this.mUri = Objects.requireNonNull(uri);
        }

        public void abandon() {
            this.mContext.getContentResolver().delete(this.mUri, null, null);
        }

        @Override
        public void close() {
            this.notifyProgress(-1);
        }

        public void notifyProgress(int n) {
            Uri uri = this.mUri.buildUpon().appendQueryParameter(MediaStore.PARAM_PROGRESS, Integer.toString(n)).build();
            this.mContext.getContentResolver().notifyChange(uri, null, 0);
        }

        public ParcelFileDescriptor open() throws FileNotFoundException {
            return this.mContext.getContentResolver().openFileDescriptor(this.mUri, "rw");
        }

        public OutputStream openOutputStream() throws FileNotFoundException {
            return this.mContext.getContentResolver().openOutputStream(this.mUri);
        }

        public Uri publish() {
            ContentValues contentValues = new ContentValues();
            contentValues.put("is_pending", 0);
            contentValues.putNull("date_expires");
            this.mContext.getContentResolver().update(this.mUri, contentValues, null, null);
            return this.mUri;
        }
    }

    public static class ThumbnailConstants {
        public static final int FULL_SCREEN_KIND = 2;
        public static final Point FULL_SCREEN_SIZE;
        public static final int MICRO_KIND = 3;
        public static final Point MICRO_SIZE;
        public static final int MINI_KIND = 1;
        public static final Point MINI_SIZE;

        static {
            MINI_SIZE = new Point(512, 384);
            FULL_SCREEN_SIZE = new Point(1024, 786);
            MICRO_SIZE = new Point(96, 96);
        }
    }

    public static final class Video {
        public static final String DEFAULT_SORT_ORDER = "_display_name";

        @Deprecated
        public static final Cursor query(ContentResolver contentResolver, Uri uri, String[] arrstring) {
            return contentResolver.query(uri, arrstring, null, null, DEFAULT_SORT_ORDER);
        }

        public static final class Media
        implements VideoColumns {
            public static final String CONTENT_TYPE = "vnd.android.cursor.dir/video";
            public static final String DEFAULT_SORT_ORDER = "title";
            public static final Uri EXTERNAL_CONTENT_URI;
            public static final Uri INTERNAL_CONTENT_URI;

            static {
                INTERNAL_CONTENT_URI = Media.getContentUri(MediaStore.VOLUME_INTERNAL);
                EXTERNAL_CONTENT_URI = Media.getContentUri(MediaStore.VOLUME_EXTERNAL);
            }

            public static Uri getContentUri(String string2) {
                return AUTHORITY_URI.buildUpon().appendPath(string2).appendPath("video").appendPath(MediaStore.AUTHORITY).build();
            }

            public static Uri getContentUri(String string2, long l) {
                return ContentUris.withAppendedId(Media.getContentUri(string2), l);
            }
        }

        @Deprecated
        public static class Thumbnails
        implements BaseColumns {
            @Column(value=3)
            @Deprecated
            public static final String DATA = "_data";
            public static final String DEFAULT_SORT_ORDER = "video_id ASC";
            public static final Uri EXTERNAL_CONTENT_URI;
            public static final int FULL_SCREEN_KIND = 2;
            @Column(readOnly=true, value=1)
            public static final String HEIGHT = "height";
            public static final Uri INTERNAL_CONTENT_URI;
            @Column(value=1)
            public static final String KIND = "kind";
            public static final int MICRO_KIND = 3;
            public static final int MINI_KIND = 1;
            @Column(value=1)
            public static final String VIDEO_ID = "video_id";
            @Column(readOnly=true, value=1)
            public static final String WIDTH = "width";

            static {
                INTERNAL_CONTENT_URI = Thumbnails.getContentUri(MediaStore.VOLUME_INTERNAL);
                EXTERNAL_CONTENT_URI = Thumbnails.getContentUri(MediaStore.VOLUME_EXTERNAL);
            }

            @Deprecated
            public static void cancelThumbnailRequest(ContentResolver contentResolver, long l) {
                InternalThumbnails.cancelThumbnail(contentResolver, ContentUris.withAppendedId(Media.EXTERNAL_CONTENT_URI, l));
            }

            @Deprecated
            public static void cancelThumbnailRequest(ContentResolver contentResolver, long l, long l2) {
                Thumbnails.cancelThumbnailRequest(contentResolver, l);
            }

            public static Uri getContentUri(String string2) {
                return AUTHORITY_URI.buildUpon().appendPath(string2).appendPath("video").appendPath("thumbnails").build();
            }

            @Deprecated
            public static Bitmap getThumbnail(ContentResolver contentResolver, long l, int n, BitmapFactory.Options options) {
                return InternalThumbnails.getThumbnail(contentResolver, ContentUris.withAppendedId(Media.EXTERNAL_CONTENT_URI, l), n, options);
            }

            @Deprecated
            public static Bitmap getThumbnail(ContentResolver contentResolver, long l, long l2, int n, BitmapFactory.Options options) {
                return Thumbnails.getThumbnail(contentResolver, l, n, options);
            }
        }

        public static interface VideoColumns
        extends MediaColumns {
            @Column(readOnly=true, value=3)
            public static final String ALBUM = "album";
            @Column(readOnly=true, value=3)
            public static final String ARTIST = "artist";
            @Column(value=1)
            public static final String BOOKMARK = "bookmark";
            public static final String BUCKET_DISPLAY_NAME = "bucket_display_name";
            public static final String BUCKET_ID = "bucket_id";
            @Column(value=3)
            public static final String CATEGORY = "category";
            @Column(readOnly=true, value=1)
            public static final String COLOR_RANGE = "color_range";
            @Column(readOnly=true, value=1)
            public static final String COLOR_STANDARD = "color_standard";
            @Column(readOnly=true, value=1)
            public static final String COLOR_TRANSFER = "color_transfer";
            public static final String DATE_TAKEN = "datetaken";
            @Column(readOnly=true, value=3)
            public static final String DESCRIPTION = "description";
            public static final String DURATION = "duration";
            public static final String GROUP_ID = "group_id";
            @Column(value=1)
            public static final String IS_PRIVATE = "isprivate";
            @Column(value=3)
            public static final String LANGUAGE = "language";
            @Column(readOnly=true, value=2)
            @Deprecated
            public static final String LATITUDE = "latitude";
            @Column(readOnly=true, value=2)
            @Deprecated
            public static final String LONGITUDE = "longitude";
            @Column(value=1)
            @Deprecated
            public static final String MINI_THUMB_MAGIC = "mini_thumb_magic";
            @Column(readOnly=true, value=3)
            public static final String RESOLUTION = "resolution";
            @Column(value=3)
            public static final String TAGS = "tags";
        }

    }

}

