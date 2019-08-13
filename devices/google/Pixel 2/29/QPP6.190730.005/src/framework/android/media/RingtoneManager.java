/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.annotation.UnsupportedAppUsage;
import android.app.Activity;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.UserInfo;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.ExternalRingtonesCursorWrapper;
import android.media.IAudioService;
import android.media.IRingtonePlayer;
import android.media.Ringtone;
import android.media.VolumeShaper;
import android.net.Uri;
import android.os.Environment;
import android.os.FileUtils;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import com.android.internal.database.SortCursor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class RingtoneManager {
    public static final String ACTION_RINGTONE_PICKER = "android.intent.action.RINGTONE_PICKER";
    public static final String EXTRA_RINGTONE_AUDIO_ATTRIBUTES_FLAGS = "android.intent.extra.ringtone.AUDIO_ATTRIBUTES_FLAGS";
    public static final String EXTRA_RINGTONE_DEFAULT_URI = "android.intent.extra.ringtone.DEFAULT_URI";
    public static final String EXTRA_RINGTONE_EXISTING_URI = "android.intent.extra.ringtone.EXISTING_URI";
    @Deprecated
    public static final String EXTRA_RINGTONE_INCLUDE_DRM = "android.intent.extra.ringtone.INCLUDE_DRM";
    public static final String EXTRA_RINGTONE_PICKED_URI = "android.intent.extra.ringtone.PICKED_URI";
    public static final String EXTRA_RINGTONE_SHOW_DEFAULT = "android.intent.extra.ringtone.SHOW_DEFAULT";
    public static final String EXTRA_RINGTONE_SHOW_SILENT = "android.intent.extra.ringtone.SHOW_SILENT";
    public static final String EXTRA_RINGTONE_TITLE = "android.intent.extra.ringtone.TITLE";
    public static final String EXTRA_RINGTONE_TYPE = "android.intent.extra.ringtone.TYPE";
    public static final int ID_COLUMN_INDEX = 0;
    private static final String[] INTERNAL_COLUMNS = new String[]{"_id", "title", "title", "title_key"};
    private static final String[] MEDIA_COLUMNS = new String[]{"_id", "title", "title", "title_key"};
    private static final String TAG = "RingtoneManager";
    public static final int TITLE_COLUMN_INDEX = 1;
    public static final int TYPE_ALARM = 4;
    public static final int TYPE_ALL = 7;
    public static final int TYPE_NOTIFICATION = 2;
    public static final int TYPE_RINGTONE = 1;
    public static final int URI_COLUMN_INDEX = 2;
    private final Activity mActivity;
    private final Context mContext;
    @UnsupportedAppUsage
    private Cursor mCursor;
    private final List<String> mFilterColumns = new ArrayList<String>();
    private boolean mIncludeParentRingtones;
    private Ringtone mPreviousRingtone;
    private boolean mStopPreviousRingtone = true;
    private int mType = 1;

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

    public RingtoneManager(Activity activity) {
        this(activity, false);
    }

    public RingtoneManager(Activity activity, boolean bl) {
        this.mActivity = activity;
        this.mContext = activity;
        this.setType(this.mType);
        this.mIncludeParentRingtones = bl;
    }

    public RingtoneManager(Context context) {
        this(context, false);
    }

    public RingtoneManager(Context context, boolean bl) {
        this.mActivity = null;
        this.mContext = context;
        this.setType(this.mType);
        this.mIncludeParentRingtones = bl;
    }

    private static String constructBooleanTrueWhereClause(List<String> list) {
        if (list == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("(");
        for (int i = list.size() - 1; i >= 0; --i) {
            stringBuilder.append(list.get(i));
            stringBuilder.append("=1 or ");
        }
        if (list.size() > 0) {
            stringBuilder.setLength(stringBuilder.length() - 4);
        }
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    private static Context createPackageContextAsUser(Context context, int n) {
        try {
            context = context.createPackageContextAsUser(context.getPackageName(), 0, UserHandle.of(n));
            return context;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            Log.e(TAG, "Unable to create package context", nameNotFoundException);
            return null;
        }
    }

    public static void disableSyncFromParent(Context context) {
        IAudioService iAudioService = IAudioService.Stub.asInterface(ServiceManager.getService("audio"));
        try {
            iAudioService.disableRingtoneSync(context.getUserId());
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "Unable to disable ringtone sync.");
        }
    }

    public static void enableSyncFromParent(Context context) {
        Settings.Secure.putIntForUser(context.getContentResolver(), "sync_parent_sounds", 1, context.getUserId());
    }

    public static Uri getActualDefaultRingtoneUri(Context context, int n) {
        Object object = RingtoneManager.getSettingForType(n);
        Uri uri = null;
        if (object == null) {
            return null;
        }
        object = Settings.System.getStringForUser(context.getContentResolver(), (String)object, context.getUserId());
        if (object != null) {
            uri = Uri.parse((String)object);
        }
        object = uri;
        if (uri != null) {
            object = uri;
            if (ContentProvider.getUserIdFromUri(uri) == context.getUserId()) {
                object = ContentProvider.getUriWithoutUserId(uri);
            }
        }
        return object;
    }

    public static Uri getCacheForType(int n) {
        return RingtoneManager.getCacheForType(n, UserHandle.getCallingUserId());
    }

    public static Uri getCacheForType(int n, int n2) {
        if ((n & 1) != 0) {
            return ContentProvider.maybeAddUserId(Settings.System.RINGTONE_CACHE_URI, n2);
        }
        if ((n & 2) != 0) {
            return ContentProvider.maybeAddUserId(Settings.System.NOTIFICATION_SOUND_CACHE_URI, n2);
        }
        if ((n & 4) != 0) {
            return ContentProvider.maybeAddUserId(Settings.System.ALARM_ALERT_CACHE_URI, n2);
        }
        return null;
    }

    public static int getDefaultType(Uri uri) {
        if ((uri = ContentProvider.getUriWithoutUserId(uri)) == null) {
            return -1;
        }
        if (uri.equals(Settings.System.DEFAULT_RINGTONE_URI)) {
            return 1;
        }
        if (uri.equals(Settings.System.DEFAULT_NOTIFICATION_URI)) {
            return 2;
        }
        if (uri.equals(Settings.System.DEFAULT_ALARM_ALERT_URI)) {
            return 4;
        }
        return -1;
    }

    public static Uri getDefaultUri(int n) {
        if ((n & 1) != 0) {
            return Settings.System.DEFAULT_RINGTONE_URI;
        }
        if ((n & 2) != 0) {
            return Settings.System.DEFAULT_NOTIFICATION_URI;
        }
        if ((n & 4) != 0) {
            return Settings.System.DEFAULT_ALARM_ALERT_URI;
        }
        return null;
    }

    private static final String getExternalDirectoryForType(int n) {
        if (n != 1) {
            if (n != 2) {
                if (n == 4) {
                    return Environment.DIRECTORY_ALARMS;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unsupported ringtone type: ");
                stringBuilder.append(n);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            return Environment.DIRECTORY_NOTIFICATIONS;
        }
        return Environment.DIRECTORY_RINGTONES;
    }

    @UnsupportedAppUsage
    private Cursor getInternalRingtones() {
        return new ExternalRingtonesCursorWrapper(this.query(MediaStore.Audio.Media.INTERNAL_CONTENT_URI, INTERNAL_COLUMNS, RingtoneManager.constructBooleanTrueWhereClause(this.mFilterColumns), null, "title_key"), MediaStore.Audio.Media.INTERNAL_CONTENT_URI);
    }

    private Cursor getMediaRingtones() {
        return new ExternalRingtonesCursorWrapper(this.getMediaRingtones(this.mContext), MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
    }

    @UnsupportedAppUsage
    private Cursor getMediaRingtones(Context context) {
        return this.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, MEDIA_COLUMNS, RingtoneManager.constructBooleanTrueWhereClause(this.mFilterColumns), null, "title_key", context);
    }

    private Cursor getParentProfileRingtones() {
        Context context;
        UserInfo userInfo = UserManager.get(this.mContext).getProfileParent(this.mContext.getUserId());
        if (userInfo != null && userInfo.id != this.mContext.getUserId() && (context = RingtoneManager.createPackageContextAsUser(this.mContext, userInfo.id)) != null) {
            return new ExternalRingtonesCursorWrapper(this.getMediaRingtones(context), ContentProvider.maybeAddUserId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, userInfo.id));
        }
        return null;
    }

    public static Ringtone getRingtone(Context context, Uri uri) {
        return RingtoneManager.getRingtone(context, uri, -1);
    }

    @UnsupportedAppUsage
    private static Ringtone getRingtone(Context context, Uri uri, int n) {
        return RingtoneManager.getRingtone(context, uri, n, null);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    private static Ringtone getRingtone(Context object, Uri uri, int n, VolumeShaper.Configuration configuration) {
        try {
            Ringtone ringtone = new Ringtone((Context)object, true);
            if (n >= 0) {
                ringtone.setStreamType(n);
            }
            ringtone.setUri(uri, configuration);
            return ringtone;
        }
        catch (Exception exception) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Failed to open ringtone ");
            ((StringBuilder)object).append(uri);
            ((StringBuilder)object).append(": ");
            ((StringBuilder)object).append(exception);
            Log.e(TAG, ((StringBuilder)object).toString());
            return null;
        }
    }

    public static Ringtone getRingtone(Context context, Uri uri, VolumeShaper.Configuration configuration) {
        return RingtoneManager.getRingtone(context, uri, -1, configuration);
    }

    private static String getSettingForType(int n) {
        if ((n & 1) != 0) {
            return "ringtone";
        }
        if ((n & 2) != 0) {
            return "notification_sound";
        }
        if ((n & 4) != 0) {
            return "alarm_alert";
        }
        return null;
    }

    private static Uri getUriFromCursor(Context context, Cursor object) {
        object = ContentUris.withAppendedId(Uri.parse(object.getString(2)), object.getLong(0));
        return context.getContentResolver().canonicalizeOrElse((Uri)object);
    }

    public static Uri getValidRingtoneUri(Context context) {
        Uri uri;
        RingtoneManager ringtoneManager = new RingtoneManager(context);
        Uri uri2 = uri = RingtoneManager.getValidRingtoneUriFromCursorAndClose(context, ringtoneManager.getInternalRingtones());
        if (uri == null) {
            uri2 = RingtoneManager.getValidRingtoneUriFromCursorAndClose(context, ringtoneManager.getMediaRingtones());
        }
        return uri2;
    }

    private static Uri getValidRingtoneUriFromCursorAndClose(Context context, Cursor cursor) {
        if (cursor != null) {
            Uri uri = null;
            if (cursor.moveToFirst()) {
                uri = RingtoneManager.getUriFromCursor(context, cursor);
            }
            cursor.close();
            return uri;
        }
        return null;
    }

    public static boolean hasHapticChannels(Uri uri) {
        return AudioManager.hasHapticChannels(uri);
    }

    public static boolean isDefault(Uri uri) {
        boolean bl = RingtoneManager.getDefaultType(uri) != -1;
        return bl;
    }

    private static boolean isExternalRingtoneUri(Uri uri) {
        return RingtoneManager.isRingtoneUriInStorage(uri, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
    }

    private static boolean isInternalRingtoneUri(Uri uri) {
        return RingtoneManager.isRingtoneUriInStorage(uri, MediaStore.Audio.Media.INTERNAL_CONTENT_URI);
    }

    private static boolean isRingtoneUriInStorage(Uri uri, Uri uri2) {
        boolean bl = (uri = ContentProvider.getUriWithoutUserId(uri)) == null ? false : uri.toString().startsWith(uri2.toString());
        return bl;
    }

    public static AssetFileDescriptor openDefaultRingtoneUri(Context object, Uri parcelable) throws FileNotFoundException {
        int n = RingtoneManager.getDefaultType(parcelable);
        parcelable = RingtoneManager.getCacheForType(n, ((Context)object).getUserId());
        Uri uri = RingtoneManager.getActualDefaultRingtoneUri((Context)object, n);
        ContentResolver contentResolver = ((Context)object).getContentResolver();
        object = null;
        if (parcelable != null) {
            parcelable = contentResolver.openAssetFileDescriptor((Uri)parcelable, "r");
            object = parcelable;
            if (parcelable != null) {
                return parcelable;
            }
        }
        if (uri != null) {
            object = contentResolver.openAssetFileDescriptor(uri, "r");
        }
        return object;
    }

    private static InputStream openRingtone(Context object, Uri uri) throws IOException {
        Object object2 = ((Context)object).getContentResolver();
        try {
            object2 = ((ContentResolver)object2).openInputStream(uri);
            return object2;
        }
        catch (IOException | SecurityException exception) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Failed to open directly; attempting failover: ");
            ((StringBuilder)object2).append(exception);
            Log.w(TAG, ((StringBuilder)object2).toString());
            object = ((Context)object).getSystemService(AudioManager.class).getRingtonePlayer();
            try {
                object = new ParcelFileDescriptor.AutoCloseInputStream(object.openRingtone(uri));
                return object;
            }
            catch (Exception exception2) {
                throw new IOException(exception2);
            }
        }
    }

    private Cursor query(Uri uri, String[] arrstring, String string2, String[] arrstring2, String string3) {
        return this.query(uri, arrstring, string2, arrstring2, string3, this.mContext);
    }

    private Cursor query(Uri uri, String[] arrstring, String string2, String[] arrstring2, String string3, Context context) {
        Activity activity = this.mActivity;
        if (activity != null) {
            return activity.managedQuery(uri, arrstring, string2, arrstring2, string3);
        }
        return context.getContentResolver().query(uri, arrstring, string2, arrstring2, string3);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static void setActualDefaultRingtoneUri(Context object, int n, Uri object2) {
        Object object3;
        block17 : {
            String string2 = RingtoneManager.getSettingForType(n);
            if (string2 == null) {
                return;
            }
            object3 = ((Context)object).getContentResolver();
            if (Settings.Secure.getIntForUser((ContentResolver)object3, "sync_parent_sounds", 0, ((Context)object).getUserId()) == 1) {
                RingtoneManager.disableSyncFromParent((Context)object);
            }
            Object object4 = object2;
            if (!RingtoneManager.isInternalRingtoneUri((Uri)object2)) {
                object4 = ContentProvider.maybeAddUserId((Uri)object2, ((Context)object).getUserId());
            }
            object2 = object4 != null ? ((Uri)object4).toString() : null;
            Settings.System.putStringForUser((ContentResolver)object3, string2, (String)object2, ((Context)object).getUserId());
            if (object4 == null) return;
            object2 = RingtoneManager.getCacheForType(n, ((Context)object).getUserId());
            object = RingtoneManager.openRingtone((Context)object, (Uri)object4);
            object3 = ((ContentResolver)object3).openOutputStream((Uri)object2);
            FileUtils.copy((InputStream)object, (OutputStream)object3);
            if (object3 == null) break block17;
            RingtoneManager.$closeResource(null, (AutoCloseable)object3);
        }
        if (object == null) return;
        RingtoneManager.$closeResource(null, (AutoCloseable)object);
        return;
        catch (Throwable throwable) {
            try {
                throw throwable;
            }
            catch (Throwable throwable2) {
                if (object3 == null) throw throwable2;
                try {
                    RingtoneManager.$closeResource(throwable, (AutoCloseable)object3);
                    throw throwable2;
                }
                catch (Throwable throwable3) {
                    try {
                        throw throwable3;
                    }
                    catch (Throwable throwable4) {
                        if (object == null) throw throwable4;
                        try {
                            RingtoneManager.$closeResource(throwable3, (AutoCloseable)object);
                            throw throwable4;
                        }
                        catch (IOException iOException) {
                            object2 = new StringBuilder();
                            ((StringBuilder)object2).append("Failed to cache ringtone: ");
                            ((StringBuilder)object2).append(iOException);
                            Log.w(TAG, ((StringBuilder)object2).toString());
                        }
                    }
                }
            }
        }
    }

    private void setFilterColumnsList(int n) {
        List<String> list = this.mFilterColumns;
        list.clear();
        if ((n & 1) != 0) {
            list.add("is_ringtone");
        }
        if ((n & 2) != 0) {
            list.add("is_notification");
        }
        if ((n & 4) != 0) {
            list.add("is_alarm");
        }
    }

    /*
     * Exception decompiling
     */
    public Uri addCustomExternalRingtone(Uri var1_1, int var2_2) throws FileNotFoundException, IllegalArgumentException, IOException {
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

    public Cursor getCursor() {
        Cursor cursor;
        Object object = this.mCursor;
        if (object != null && object.requery()) {
            return this.mCursor;
        }
        object = new ArrayList();
        ((ArrayList)object).add(this.getInternalRingtones());
        ((ArrayList)object).add(this.getMediaRingtones());
        if (this.mIncludeParentRingtones && (cursor = this.getParentProfileRingtones()) != null) {
            ((ArrayList)object).add(cursor);
        }
        this.mCursor = object = new SortCursor(((ArrayList)object).toArray(new Cursor[((ArrayList)object).size()]), "title_key");
        return object;
    }

    @Deprecated
    public boolean getIncludeDrm() {
        return false;
    }

    public Ringtone getRingtone(int n) {
        Ringtone ringtone;
        if (this.mStopPreviousRingtone && (ringtone = this.mPreviousRingtone) != null) {
            ringtone.stop();
        }
        this.mPreviousRingtone = RingtoneManager.getRingtone(this.mContext, this.getRingtoneUri(n), this.inferStreamType());
        return this.mPreviousRingtone;
    }

    public int getRingtonePosition(Uri object) {
        if (object == null) {
            return -1;
        }
        long l = ContentUris.parseId((Uri)object);
        object = this.getCursor();
        object.moveToPosition(-1);
        while (object.moveToNext()) {
            if (l != object.getLong(0)) continue;
            return object.getPosition();
        }
        return -1;
    }

    public Uri getRingtoneUri(int n) {
        Cursor cursor = this.mCursor;
        if (cursor != null && cursor.moveToPosition(n)) {
            return RingtoneManager.getUriFromCursor(this.mContext, this.mCursor);
        }
        return null;
    }

    public boolean getStopPreviousRingtone() {
        return this.mStopPreviousRingtone;
    }

    public boolean hasHapticChannels(int n) {
        return RingtoneManager.hasHapticChannels(this.getRingtoneUri(n));
    }

    public int inferStreamType() {
        int n = this.mType;
        if (n != 2) {
            if (n != 4) {
                return 2;
            }
            return 4;
        }
        return 5;
    }

    @Deprecated
    public void setIncludeDrm(boolean bl) {
        if (bl) {
            Log.w(TAG, "setIncludeDrm no longer supported");
        }
    }

    public void setStopPreviousRingtone(boolean bl) {
        this.mStopPreviousRingtone = bl;
    }

    public void setType(int n) {
        if (this.mCursor == null) {
            this.mType = n;
            this.setFilterColumnsList(n);
            return;
        }
        throw new IllegalStateException("Setting filter columns should be done before querying for ringtones.");
    }

    public void stopPreviousRingtone() {
        Ringtone ringtone = this.mPreviousRingtone;
        if (ringtone != null) {
            ringtone.stop();
        }
    }
}

