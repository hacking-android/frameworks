/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.system.ErrnoException
 *  android.system.Os
 *  android.system.OsConstants
 *  dalvik.system.CloseGuard
 */
package android.media;

import android.annotation.UnsupportedAppUsage;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.SQLException;
import android.drm.DrmManagerClient;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.media.MediaFile;
import android.media.MediaInserter;
import android.media.MediaScannerClient;
import android.media.RingtoneManager;
import android.mtp.MtpConstants;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Environment;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.provider.MediaStore;
import android.provider.Settings;
import android.sax.Element;
import android.sax.ElementListener;
import android.sax.RootElement;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.text.TextUtils;
import android.util.Log;
import dalvik.system.CloseGuard;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.atomic.AtomicBoolean;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;

@Deprecated
public class MediaScanner
implements AutoCloseable {
    private static final String ALARMS_DIR = "/alarms/";
    private static final String AUDIOBOOKS_DIR = "/audiobooks/";
    private static final int DATE_MODIFIED_PLAYLISTS_COLUMN_INDEX = 2;
    private static final String DEFAULT_RINGTONE_PROPERTY_PREFIX = "ro.config.";
    private static final boolean ENABLE_BULK_INSERTS = true;
    private static final int FILES_PRESCAN_DATE_MODIFIED_COLUMN_INDEX = 3;
    private static final int FILES_PRESCAN_FORMAT_COLUMN_INDEX = 2;
    private static final int FILES_PRESCAN_ID_COLUMN_INDEX = 0;
    private static final int FILES_PRESCAN_MEDIA_TYPE_COLUMN_INDEX = 4;
    private static final int FILES_PRESCAN_PATH_COLUMN_INDEX = 1;
    @UnsupportedAppUsage
    private static final String[] FILES_PRESCAN_PROJECTION;
    private static final String[] ID3_GENRES;
    private static final int ID_PLAYLISTS_COLUMN_INDEX = 0;
    private static final String[] ID_PROJECTION;
    public static final String LAST_INTERNAL_SCAN_FINGERPRINT = "lastScanFingerprint";
    private static final String MUSIC_DIR = "/music/";
    private static final String NOTIFICATIONS_DIR = "/notifications/";
    private static final String OEM_SOUNDS_DIR;
    private static final int PATH_PLAYLISTS_COLUMN_INDEX = 1;
    private static final String[] PLAYLIST_MEMBERS_PROJECTION;
    private static final String PODCASTS_DIR = "/podcasts/";
    private static final String PRODUCT_SOUNDS_DIR;
    private static final String RINGTONES_DIR = "/ringtones/";
    public static final String SCANNED_BUILD_PREFS_NAME = "MediaScanBuild";
    private static final String SYSTEM_SOUNDS_DIR;
    private static final String TAG = "MediaScanner";
    private static HashMap<String, String> mMediaPaths;
    private static HashMap<String, String> mNoMediaPaths;
    private static String sLastInternalScanFingerprint;
    @UnsupportedAppUsage
    private final Uri mAudioUri;
    private final BitmapFactory.Options mBitmapOptions = new BitmapFactory.Options();
    @UnsupportedAppUsage
    private final MyMediaScannerClient mClient = new MyMediaScannerClient();
    private final CloseGuard mCloseGuard = CloseGuard.get();
    private final AtomicBoolean mClosed = new AtomicBoolean();
    @UnsupportedAppUsage
    private final Context mContext;
    @UnsupportedAppUsage
    private String mDefaultAlarmAlertFilename;
    private boolean mDefaultAlarmSet;
    @UnsupportedAppUsage
    private String mDefaultNotificationFilename;
    private boolean mDefaultNotificationSet;
    @UnsupportedAppUsage
    private String mDefaultRingtoneFilename;
    private boolean mDefaultRingtoneSet;
    private DrmManagerClient mDrmManagerClient = null;
    private final Uri mFilesFullUri;
    @UnsupportedAppUsage
    private final Uri mFilesUri;
    private final Uri mImagesUri;
    @UnsupportedAppUsage
    private MediaInserter mMediaInserter;
    private final ContentProviderClient mMediaProvider;
    private int mMtpObjectHandle;
    private long mNativeContext;
    private int mOriginalCount;
    @UnsupportedAppUsage
    private final String mPackageName;
    private final ArrayList<FileEntry> mPlayLists = new ArrayList();
    private final ArrayList<PlaylistEntry> mPlaylistEntries = new ArrayList();
    private final Uri mPlaylistsUri;
    private final boolean mProcessGenres;
    private final boolean mProcessPlaylists;
    private final Uri mVideoUri;
    private final String mVolumeName;

    static {
        System.loadLibrary("media_jni");
        MediaScanner.native_init();
        FILES_PRESCAN_PROJECTION = new String[]{"_id", "_data", "format", "date_modified", "media_type"};
        ID_PROJECTION = new String[]{"_id"};
        PLAYLIST_MEMBERS_PROJECTION = new String[]{"playlist_id"};
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Environment.getRootDirectory());
        stringBuilder.append("/media/audio");
        SYSTEM_SOUNDS_DIR = stringBuilder.toString();
        stringBuilder = new StringBuilder();
        stringBuilder.append(Environment.getOemDirectory());
        stringBuilder.append("/media/audio");
        OEM_SOUNDS_DIR = stringBuilder.toString();
        stringBuilder = new StringBuilder();
        stringBuilder.append(Environment.getProductDirectory());
        stringBuilder.append("/media/audio");
        PRODUCT_SOUNDS_DIR = stringBuilder.toString();
        ID3_GENRES = new String[]{"Blues", "Classic Rock", "Country", "Dance", "Disco", "Funk", "Grunge", "Hip-Hop", "Jazz", "Metal", "New Age", "Oldies", "Other", "Pop", "R&B", "Rap", "Reggae", "Rock", "Techno", "Industrial", "Alternative", "Ska", "Death Metal", "Pranks", "Soundtrack", "Euro-Techno", "Ambient", "Trip-Hop", "Vocal", "Jazz+Funk", "Fusion", "Trance", "Classical", "Instrumental", "Acid", "House", "Game", "Sound Clip", "Gospel", "Noise", "AlternRock", "Bass", "Soul", "Punk", "Space", "Meditative", "Instrumental Pop", "Instrumental Rock", "Ethnic", "Gothic", "Darkwave", "Techno-Industrial", "Electronic", "Pop-Folk", "Eurodance", "Dream", "Southern Rock", "Comedy", "Cult", "Gangsta", "Top 40", "Christian Rap", "Pop/Funk", "Jungle", "Native American", "Cabaret", "New Wave", "Psychadelic", "Rave", "Showtunes", "Trailer", "Lo-Fi", "Tribal", "Acid Punk", "Acid Jazz", "Polka", "Retro", "Musical", "Rock & Roll", "Hard Rock", "Folk", "Folk-Rock", "National Folk", "Swing", "Fast Fusion", "Bebob", "Latin", "Revival", "Celtic", "Bluegrass", "Avantgarde", "Gothic Rock", "Progressive Rock", "Psychedelic Rock", "Symphonic Rock", "Slow Rock", "Big Band", "Chorus", "Easy Listening", "Acoustic", "Humour", "Speech", "Chanson", "Opera", "Chamber Music", "Sonata", "Symphony", "Booty Bass", "Primus", "Porn Groove", "Satire", "Slow Jam", "Club", "Tango", "Samba", "Folklore", "Ballad", "Power Ballad", "Rhythmic Soul", "Freestyle", "Duet", "Punk Rock", "Drum Solo", "A capella", "Euro-House", "Dance Hall", "Goa", "Drum & Bass", "Club-House", "Hardcore", "Terror", "Indie", "Britpop", null, "Polsk Punk", "Beat", "Christian Gangsta", "Heavy Metal", "Black Metal", "Crossover", "Contemporary Christian", "Christian Rock", "Merengue", "Salsa", "Thrash Metal", "Anime", "JPop", "Synthpop"};
        mNoMediaPaths = new HashMap();
        mMediaPaths = new HashMap();
    }

    @UnsupportedAppUsage
    public MediaScanner(Context object, String object2) {
        this.native_setup();
        this.mContext = object;
        this.mPackageName = ((Context)object).getPackageName();
        this.mVolumeName = object2;
        object = this.mBitmapOptions;
        ((BitmapFactory.Options)object).inSampleSize = 1;
        ((BitmapFactory.Options)object).inJustDecodeBounds = true;
        this.setDefaultRingtoneFileNames();
        this.mMediaProvider = this.mContext.getContentResolver().acquireContentProviderClient("media");
        if (sLastInternalScanFingerprint == null) {
            sLastInternalScanFingerprint = this.mContext.getSharedPreferences(SCANNED_BUILD_PREFS_NAME, 0).getString(LAST_INTERNAL_SCAN_FINGERPRINT, new String());
        }
        this.mAudioUri = MediaStore.Audio.Media.getContentUri((String)object2);
        this.mVideoUri = MediaStore.Video.Media.getContentUri((String)object2);
        this.mImagesUri = MediaStore.Images.Media.getContentUri((String)object2);
        this.mFilesUri = MediaStore.Files.getContentUri((String)object2);
        this.mFilesFullUri = MediaStore.setIncludeTrashed(MediaStore.setIncludePending(this.mFilesUri.buildUpon().appendQueryParameter("nonotify", "1").build()));
        if (!((String)object2).equals("internal")) {
            this.mProcessPlaylists = true;
            this.mProcessGenres = true;
            this.mPlaylistsUri = MediaStore.Audio.Playlists.getContentUri((String)object2);
        } else {
            this.mProcessPlaylists = false;
            this.mProcessGenres = false;
            this.mPlaylistsUri = null;
        }
        object2 = this.mContext.getResources().getConfiguration().locale;
        if (object2 != null) {
            object = ((Locale)object2).getLanguage();
            String string2 = ((Locale)object2).getCountry();
            if (object != null) {
                if (string2 != null) {
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append((String)object);
                    ((StringBuilder)object2).append("_");
                    ((StringBuilder)object2).append(string2);
                    this.setLocale(((StringBuilder)object2).toString());
                } else {
                    this.setLocale((String)object);
                }
            }
        }
        this.mCloseGuard.open("close");
    }

    static /* synthetic */ boolean access$1100(String string2) {
        return MediaScanner.isSystemSoundWithMetadata(string2);
    }

    static /* synthetic */ String access$1200() {
        return sLastInternalScanFingerprint;
    }

    static /* synthetic */ boolean access$1300(MediaScanner mediaScanner, String string2, String string3, MediaScannerClient mediaScannerClient) {
        return mediaScanner.processFile(string2, string3, mediaScannerClient);
    }

    private void cachePlaylistEntry(String charSequence, String string2) {
        PlaylistEntry playlistEntry;
        String string3;
        int n;
        block8 : {
            block7 : {
                char c;
                playlistEntry = new PlaylistEntry();
                for (n = charSequence.length(); n > 0 && Character.isWhitespace(((String)charSequence).charAt(n - 1)); --n) {
                }
                if (n < 3) {
                    return;
                }
                int n2 = ((String)charSequence).length();
                int n3 = 0;
                string3 = charSequence;
                if (n < n2) {
                    string3 = ((String)charSequence).substring(0, n);
                }
                if ((c = string3.charAt(0)) == '/') break block7;
                n = n3;
                if (!Character.isLetter(c)) break block8;
                n = n3;
                if (string3.charAt(1) != ':') break block8;
                n = n3;
                if (string3.charAt(2) != '\\') break block8;
            }
            n = 1;
        }
        charSequence = string3;
        if (n == 0) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(string2);
            ((StringBuilder)charSequence).append(string3);
            charSequence = ((StringBuilder)charSequence).toString();
        }
        playlistEntry.path = charSequence;
        this.mPlaylistEntries.add(playlistEntry);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void clearMediaPathCache(boolean bl, boolean bl2) {
        synchronized (MediaScanner.class) {
            Throwable throwable2;
            block5 : {
                if (bl) {
                    try {
                        mMediaPaths.clear();
                    }
                    catch (Throwable throwable2) {
                        break block5;
                    }
                }
                if (bl2) {
                    mNoMediaPaths.clear();
                }
                return;
            }
            throw throwable2;
        }
    }

    @UnsupportedAppUsage
    private boolean isDrmEnabled() {
        String string2 = SystemProperties.get("drm.service.enabled");
        boolean bl = string2 != null && string2.equals("true");
        return bl;
    }

    private static boolean isNoMediaFile(String string2) {
        if (new File(string2).isDirectory()) {
            return false;
        }
        int n = string2.lastIndexOf(47);
        if (n >= 0 && n + 2 < string2.length()) {
            if (string2.regionMatches(n + 1, "._", 0, 2)) {
                return true;
            }
            if (string2.regionMatches(true, string2.length() - 4, ".jpg", 0, 4)) {
                if (!string2.regionMatches(true, n + 1, "AlbumArt_{", 0, 10) && !string2.regionMatches(true, n + 1, "AlbumArt.", 0, 9)) {
                    int n2 = string2.length() - n - 1;
                    if (n2 == 17 && string2.regionMatches(true, n + 1, "AlbumArtSmall", 0, 13) || n2 == 10 && string2.regionMatches(true, n + 1, "Folder", 0, 6)) {
                        return true;
                    }
                } else {
                    return true;
                }
            }
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public static boolean isNoMediaPath(String string2) {
        if (string2 == null) {
            return false;
        }
        if (string2.indexOf("/.") >= 0) {
            return true;
        }
        int n = string2.lastIndexOf(47);
        if (n <= 0) {
            return false;
        }
        String string3 = string2.substring(0, n);
        synchronized (MediaScanner.class) {
            if (mNoMediaPaths.containsKey(string3)) {
                return true;
            }
            if (!mMediaPaths.containsKey(string3)) {
                int n2 = 1;
                while (n2 >= 0) {
                    int n3;
                    n = n3 = string2.indexOf(47, n2);
                    if (n3 > n2) {
                        n = n3 + 1;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(string2.substring(0, n));
                        stringBuilder.append(".nomedia");
                        File file = new File(stringBuilder.toString());
                        if (file.exists()) {
                            mNoMediaPaths.put(string3, "");
                            return true;
                        }
                    }
                    n2 = n;
                }
                mMediaPaths.put(string3, "");
            }
            return MediaScanner.isNoMediaFile(string2);
        }
    }

    private static boolean isSystemSoundWithMetadata(String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(SYSTEM_SOUNDS_DIR);
        stringBuilder.append(ALARMS_DIR);
        if (!string2.startsWith(stringBuilder.toString())) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(SYSTEM_SOUNDS_DIR);
            stringBuilder.append(RINGTONES_DIR);
            if (!string2.startsWith(stringBuilder.toString())) {
                stringBuilder = new StringBuilder();
                stringBuilder.append(SYSTEM_SOUNDS_DIR);
                stringBuilder.append(NOTIFICATIONS_DIR);
                if (!string2.startsWith(stringBuilder.toString())) {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(OEM_SOUNDS_DIR);
                    stringBuilder.append(ALARMS_DIR);
                    if (!string2.startsWith(stringBuilder.toString())) {
                        stringBuilder = new StringBuilder();
                        stringBuilder.append(OEM_SOUNDS_DIR);
                        stringBuilder.append(RINGTONES_DIR);
                        if (!string2.startsWith(stringBuilder.toString())) {
                            stringBuilder = new StringBuilder();
                            stringBuilder.append(OEM_SOUNDS_DIR);
                            stringBuilder.append(NOTIFICATIONS_DIR);
                            if (!string2.startsWith(stringBuilder.toString())) {
                                stringBuilder = new StringBuilder();
                                stringBuilder.append(PRODUCT_SOUNDS_DIR);
                                stringBuilder.append(ALARMS_DIR);
                                if (!string2.startsWith(stringBuilder.toString())) {
                                    stringBuilder = new StringBuilder();
                                    stringBuilder.append(PRODUCT_SOUNDS_DIR);
                                    stringBuilder.append(RINGTONES_DIR);
                                    if (!string2.startsWith(stringBuilder.toString())) {
                                        stringBuilder = new StringBuilder();
                                        stringBuilder.append(PRODUCT_SOUNDS_DIR);
                                        stringBuilder.append(NOTIFICATIONS_DIR);
                                        if (!string2.startsWith(stringBuilder.toString())) {
                                            return false;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    private boolean matchEntries(long l, String string2) {
        int n = this.mPlaylistEntries.size();
        boolean bl = true;
        for (int i = 0; i < n; ++i) {
            PlaylistEntry playlistEntry = this.mPlaylistEntries.get(i);
            if (playlistEntry.bestmatchlevel == Integer.MAX_VALUE) continue;
            boolean bl2 = false;
            if (string2.equalsIgnoreCase(playlistEntry.path)) {
                playlistEntry.bestmatchid = l;
                playlistEntry.bestmatchlevel = Integer.MAX_VALUE;
                bl = bl2;
                continue;
            }
            int n2 = this.matchPaths(string2, playlistEntry.path);
            bl = bl2;
            if (n2 <= playlistEntry.bestmatchlevel) continue;
            playlistEntry.bestmatchid = l;
            playlistEntry.bestmatchlevel = n2;
            bl = bl2;
        }
        return bl;
    }

    private int matchPaths(String string2, String string3) {
        int n = string2.length();
        int n2 = string3.length();
        int n3 = 0;
        while (n > 0 && n2 > 0) {
            int n4 = string2.lastIndexOf(47, n - 1);
            int n5 = string3.lastIndexOf(47, n2 - 1);
            int n6 = string2.lastIndexOf(92, n - 1);
            int n7 = string3.lastIndexOf(92, n2 - 1);
            if (n4 > n6) {
                n6 = n4;
            }
            if (n5 > n7) {
                n7 = n5;
            }
            n6 = n6 < 0 ? 0 : ++n6;
            n7 = n7 < 0 ? 0 : ++n7;
            if (n2 - n7 != (n -= n6) || !string2.regionMatches(true, n6, string3, n7, n)) break;
            ++n3;
            n = n6 - 1;
            n2 = n7 - 1;
        }
        return n3;
    }

    private final native void native_finalize();

    private static final native void native_init();

    private final native void native_setup();

    @UnsupportedAppUsage
    private void postscan(String[] arrstring) throws RemoteException {
        if (this.mProcessPlaylists) {
            this.processPlayLists();
        }
        this.mPlayLists.clear();
    }

    /*
     * Unable to fully structure code
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @UnsupportedAppUsage
    private void prescan(String var1_1, boolean var2_5) throws RemoteException {
        block39 : {
            var3_6 = null;
            var4_8 = null;
            var5_12 = null;
            this.mPlayLists.clear();
            if (var1_1 != null) {
                var6_14 = new String[]{"", var1_1};
                var7_15 = "_id>? AND _data=?";
            } else {
                var6_14 = new String[]{""};
                var7_15 = "_id>?";
            }
            this.mDefaultRingtoneSet = this.wasRingtoneAlreadySet("ringtone");
            this.mDefaultNotificationSet = this.wasRingtoneAlreadySet("notification_sound");
            this.mDefaultAlarmSet = this.wasRingtoneAlreadySet("alarm_alert");
            var8_16 = this.mFilesUri.buildUpon();
            var8_16.appendQueryParameter("deletedata", "false");
            var1_1 = new MediaBulkDeleter(this.mMediaProvider, var8_16.build());
            if (var2_5) {
                block38 : {
                    var4_8 = var3_6;
                    var9_17 = var1_1;
                    var10_18 = var8_16;
                    try {
                        var11_19 = this.mFilesUri.buildUpon().appendQueryParameter("limit", "1000").build();
                        var12_20 = Long.MIN_VALUE;
                    }
                    catch (Throwable var1_4) {
                        var5_12 = var4_8;
                        var4_8 = var9_17;
                    }
                    do {
                        block40 : {
                            block37 : {
                                var4_8 = var5_12;
                                var9_17 = var1_1;
                                var10_18 = var8_16;
                                var4_8 = var5_12;
                                var9_17 = var1_1;
                                var10_18 = var8_16;
                                var3_6 = new StringBuilder();
                                var4_8 = var5_12;
                                var9_17 = var1_1;
                                var10_18 = var8_16;
                                var3_6.append("");
                                var4_8 = var5_12;
                                var9_17 = var1_1;
                                var10_18 = var8_16;
                                var3_6.append(var12_20);
                                var4_8 = var5_12;
                                var9_17 = var1_1;
                                var10_18 = var8_16;
                                var6_14[0] = var3_6.toString();
                                var3_6 = var5_12;
                                if (var5_12 == null) break block37;
                                try {
                                    var5_12.close();
                                    var3_6 = null;
                                }
                                catch (Throwable var4_9) {
                                    var3_6 = var1_1;
                                    var1_1 = var4_9;
                                    var4_8 = var3_6;
                                    break block38;
                                }
                            }
                            var4_8 = var3_6;
                            var9_17 = var1_1;
                            var10_18 = var8_16;
                            var5_12 = this.mMediaProvider;
                            var4_8 = var3_6;
                            var9_17 = var1_1;
                            var10_18 = var8_16;
                            var10_18 = MediaScanner.FILES_PRESCAN_PROJECTION;
                            var4_8 = var5_12.query(var11_19, (String[])var10_18, var7_15, var6_14, "_id", null);
                            if (var4_8 == null) break block39;
                            var9_17 = var4_8;
                            var3_6 = var1_1;
                            if (var4_8.getCount() == 0) break block39;
                            var5_12 = var1_1;
                            var1_1 = var4_8;
                            do lbl-1000: // 8 sources:
                            {
                                var9_17 = var1_1;
                                var3_6 = var5_12;
                                if (!var1_1.moveToNext()) break block40;
                                var9_17 = var1_1;
                                var3_6 = var5_12;
                                var14_21 = var1_1.getLong(0);
                                var9_17 = var1_1;
                                var3_6 = var5_12;
                                var10_18 = var1_1.getString(1);
                                var9_17 = var1_1;
                                var3_6 = var5_12;
                                var16_22 = var1_1.getInt(2);
                                var9_17 = var1_1;
                                var3_6 = var5_12;
                                var1_1.getLong(3);
                                var12_20 = var14_21;
                                if (var10_18 == null) continue;
                                var9_17 = var1_1;
                                var3_6 = var5_12;
                                var2_5 = var10_18.startsWith("/");
                                if (!var2_5) continue;
                                var17_23 = false;
                                try {
                                    var2_5 = Os.access((String)var10_18, (int)OsConstants.F_OK);
                                }
                                catch (Throwable var4_10) {
                                    var3_6 = var1_1;
                                    var1_1 = var4_10;
                                    var4_8 = var5_12;
                                    var5_12 = var3_6;
                                    break block38;
                                }
                                catch (ErrnoException var4_11) {
                                    var2_5 = var17_23;
                                }
                                if (var2_5) continue;
                                var9_17 = var1_1;
                                var3_6 = var5_12;
                                if (MtpConstants.isAbstractObject(var16_22)) continue;
                                var9_17 = var1_1;
                                var3_6 = var5_12;
                                var2_5 = MediaFile.isPlayListMimeType(MediaFile.getMimeTypeForFile((String)var10_18));
                                if (var2_5) continue;
                                var4_8 = var5_12;
                                var4_8.delete(var14_21);
                                if (!var10_18.toLowerCase(Locale.US).endsWith("/.nomedia")) continue;
                                var4_8.flush();
                                var3_6 = new File((String)var10_18);
                                var9_17 = var3_6.getParent();
                                var10_18 = this.mMediaProvider;
                                var3_6 = var1_1;
                                break;
                            } while (true);
                            catch (Throwable var3_7) {
                                var5_12 = var1_1;
                                var1_1 = var3_7;
                                break block38;
                            }
                            {
                                try {
                                    var10_18.call("unhide", (String)var9_17, null);
                                    continue;
                                }
                                catch (Throwable var1_2) {
                                    var5_12 = var3_6;
                                    break block38;
                                }
                                ** while (true)
                            }
                        }
                        var4_8 = var5_12;
                        var5_12 = var1_1;
                        var1_1 = var4_8;
                    } while (true);
                    catch (Throwable var1_3) {
                        var5_12 = var9_17;
                        var4_8 = var3_6;
                        break block38;
                    }
                    catch (Throwable var5_13) {
                        var4_8 = var1_1;
                        var1_1 = var5_13;
                        var5_12 = var3_6;
                    }
                }
                if (var5_12 != null) {
                    var5_12.close();
                }
                var4_8.flush();
                throw var1_1;
            }
        }
        if (var4_8 != null) {
            var4_8.close();
        }
        var1_1.flush();
        this.mOriginalCount = 0;
        var1_1 = this.mMediaProvider.query(this.mImagesUri, MediaScanner.ID_PROJECTION, null, null, null, null);
        if (var1_1 == null) return;
        this.mOriginalCount = var1_1.getCount();
        var1_1.close();
    }

    private void processCachedPlaylist(Cursor object, ContentValues contentValues, Uri uri) {
        object.moveToPosition(-1);
        while (object.moveToNext() && !this.matchEntries(object.getLong(0), object.getString(1))) {
        }
        int n = this.mPlaylistEntries.size();
        int n2 = 0;
        for (int i = 0; i < n; ++i) {
            object = this.mPlaylistEntries.get(i);
            int n3 = n2;
            if (((PlaylistEntry)object).bestmatchlevel > 0) {
                try {
                    contentValues.clear();
                    contentValues.put("play_order", n2);
                    contentValues.put("audio_id", ((PlaylistEntry)object).bestmatchid);
                    this.mMediaProvider.insert(uri, contentValues);
                    n3 = n2 + 1;
                }
                catch (RemoteException remoteException) {
                    Log.e("MediaScanner", "RemoteException in MediaScanner.processCachedPlaylist()", remoteException);
                    return;
                }
            }
            n2 = n3;
        }
        this.mPlaylistEntries.clear();
    }

    private native void processDirectory(String var1, MediaScannerClient var2);

    private native boolean processFile(String var1, String var2, MediaScannerClient var3);

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void processM3uPlayList(String object, String string2, Uri uri, ContentValues contentValues, Cursor cursor) {
        Throwable throwable2222;
        Object object2 = null;
        BufferedReader bufferedReader = null;
        FileInputStream fileInputStream = null;
        Object object3 = object2;
        Object object4 = bufferedReader;
        object3 = object2;
        object4 = bufferedReader;
        File file = new File((String)object);
        object = fileInputStream;
        object3 = object2;
        object4 = bufferedReader;
        if (file.exists()) {
            object3 = object2;
            object4 = bufferedReader;
            object3 = object2;
            object4 = bufferedReader;
            object3 = object2;
            object4 = bufferedReader;
            object3 = object2;
            object4 = bufferedReader;
            fileInputStream = new FileInputStream(file);
            object3 = object2;
            object4 = bufferedReader;
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            object3 = object2;
            object4 = bufferedReader;
            object3 = object = new BufferedReader(inputStreamReader, 8192);
            object4 = object;
            object2 = ((BufferedReader)object).readLine();
            object3 = object;
            object4 = object;
            this.mPlaylistEntries.clear();
            while (object2 != null) {
                object3 = object;
                object4 = object;
                if (((String)object2).length() > 0) {
                    object3 = object;
                    object4 = object;
                    if (((String)object2).charAt(0) != '#') {
                        object3 = object;
                        object4 = object;
                        this.cachePlaylistEntry((String)object2, string2);
                    }
                }
                object3 = object;
                object4 = object;
                object2 = ((BufferedReader)object).readLine();
            }
            object3 = object;
            object4 = object;
            this.processCachedPlaylist(cursor, contentValues, uri);
        }
        if (object == null) return;
        ((BufferedReader)object).close();
        return;
        {
            catch (IOException iOException) {
                Log.e("MediaScanner", "IOException in MediaScanner.processM3uPlayList()", iOException);
                return;
            }
            catch (Throwable throwable2222) {
            }
            catch (IOException iOException) {}
            object3 = object4;
            {
                Log.e("MediaScanner", "IOException in MediaScanner.processM3uPlayList()", iOException);
                if (object4 == null) return;
            }
            {
                ((BufferedReader)object4).close();
                return;
            }
        }
        if (object3 == null) throw throwable2222;
        try {
            ((BufferedReader)object3).close();
            throw throwable2222;
        }
        catch (IOException iOException) {
            Log.e("MediaScanner", "IOException in MediaScanner.processM3uPlayList()", iOException);
            throw throwable2222;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void processPlayList(FileEntry object, Cursor cursor) throws RemoteException {
        String string2 = ((FileEntry)object).mPath;
        ContentValues contentValues = new ContentValues();
        int n = string2.lastIndexOf(47);
        if (n >= 0) {
            int n2;
            long l = ((FileEntry)object).mRowId;
            String string3 = contentValues.getAsString("name");
            if (string3 == null && (string3 = contentValues.getAsString("title")) == null) {
                n2 = string2.lastIndexOf(46);
                string3 = n2 < 0 ? string2.substring(n + 1) : string2.substring(n + 1, n2);
            }
            contentValues.put("name", string3);
            contentValues.put("date_modified", ((FileEntry)object).mLastModified);
            if (l == 0L) {
                contentValues.put("_data", string2);
                object = this.mMediaProvider.insert(this.mPlaylistsUri, contentValues);
                ContentUris.parseId((Uri)object);
                object = Uri.withAppendedPath((Uri)object, "members");
            } else {
                object = ContentUris.withAppendedId(this.mPlaylistsUri, l);
                this.mMediaProvider.update((Uri)object, contentValues, null, null);
                object = Uri.withAppendedPath((Uri)object, "members");
                this.mMediaProvider.delete((Uri)object, null, null);
            }
            n2 = 0;
            String string4 = string2.substring(0, n + 1);
            string3 = MediaFile.getMimeTypeForFile(string2);
            n = string3.hashCode();
            if (n != -1165508903) {
                if (n != 264230524) {
                    if (n != 1872259501) return;
                    if (!string3.equals("application/vnd.ms-wpl")) return;
                } else {
                    if (!string3.equals("audio/x-mpegurl")) return;
                    n2 = 1;
                }
            } else {
                if (!string3.equals("audio/x-scpls")) return;
                n2 = 2;
            }
            if (n2 != 0) {
                if (n2 != 1) {
                    if (n2 != 2) return;
                    this.processPlsPlayList(string2, string4, (Uri)object, contentValues, cursor);
                    return;
                } else {
                    this.processM3uPlayList(string2, string4, (Uri)object, contentValues, cursor);
                }
                return;
            } else {
                this.processWplPlayList(string2, string4, (Uri)object, contentValues, cursor);
            }
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("bad path ");
        ((StringBuilder)object).append(string2);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void processPlayLists() throws RemoteException {
        Iterator<FileEntry> iterator = this.mPlayLists.iterator();
        Cursor cursor = null;
        Cursor cursor2 = null;
        try {
            Cursor cursor3 = this.mMediaProvider.query(this.mFilesUri, FILES_PRESCAN_PROJECTION, "media_type=2", null, null, null);
            do {
                cursor2 = cursor3;
                cursor = cursor3;
                if (!iterator.hasNext()) break;
                cursor2 = cursor3;
                cursor = cursor3;
                FileEntry fileEntry = iterator.next();
                cursor2 = cursor3;
                cursor = cursor3;
                if (!fileEntry.mLastModifiedChanged) continue;
                cursor2 = cursor3;
                cursor = cursor3;
                this.processPlayList(fileEntry, cursor3);
            } while (true);
            if (cursor3 == null) return;
            cursor = cursor3;
        }
        catch (Throwable throwable) {
            if (cursor2 == null) throw throwable;
            cursor2.close();
            throw throwable;
        }
        catch (RemoteException remoteException) {
            if (cursor == null) return;
        }
        cursor.close();
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void processPlsPlayList(String object, String string2, Uri uri, ContentValues contentValues, Cursor cursor) {
        Throwable throwable2222;
        Object object2 = null;
        BufferedReader bufferedReader = null;
        FileInputStream fileInputStream = null;
        Object object3 = object2;
        Object object4 = bufferedReader;
        object3 = object2;
        object4 = bufferedReader;
        File file = new File((String)object);
        object = fileInputStream;
        object3 = object2;
        object4 = bufferedReader;
        if (file.exists()) {
            object3 = object2;
            object4 = bufferedReader;
            object3 = object2;
            object4 = bufferedReader;
            object3 = object2;
            object4 = bufferedReader;
            object3 = object2;
            object4 = bufferedReader;
            fileInputStream = new FileInputStream(file);
            object3 = object2;
            object4 = bufferedReader;
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            object3 = object2;
            object4 = bufferedReader;
            object3 = object = new BufferedReader(inputStreamReader, 8192);
            object4 = object;
            object2 = ((BufferedReader)object).readLine();
            object3 = object;
            object4 = object;
            this.mPlaylistEntries.clear();
            while (object2 != null) {
                object3 = object;
                object4 = object;
                if (((String)object2).startsWith("File")) {
                    object3 = object;
                    object4 = object;
                    int n = ((String)object2).indexOf(61);
                    if (n > 0) {
                        object3 = object;
                        object4 = object;
                        this.cachePlaylistEntry(((String)object2).substring(n + 1), string2);
                    }
                }
                object3 = object;
                object4 = object;
                object2 = ((BufferedReader)object).readLine();
            }
            object3 = object;
            object4 = object;
            this.processCachedPlaylist(cursor, contentValues, uri);
        }
        if (object == null) return;
        ((BufferedReader)object).close();
        return;
        {
            catch (IOException iOException) {
                Log.e("MediaScanner", "IOException in MediaScanner.processPlsPlayList()", iOException);
                return;
            }
            catch (Throwable throwable2222) {
            }
            catch (IOException iOException) {}
            object3 = object4;
            {
                Log.e("MediaScanner", "IOException in MediaScanner.processPlsPlayList()", iOException);
                if (object4 == null) return;
            }
            {
                ((BufferedReader)object4).close();
                return;
            }
        }
        if (object3 == null) throw throwable2222;
        try {
            ((BufferedReader)object3).close();
            throw throwable2222;
        }
        catch (IOException iOException) {
            Log.e("MediaScanner", "IOException in MediaScanner.processPlsPlayList()", iOException);
            throw throwable2222;
        }
    }

    /*
     * Exception decompiling
     */
    private void processWplPlayList(String var1_1, String var2_6, Uri var3_8, ContentValues var4_9, Cursor var5_10) {
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

    private void releaseResources() {
        DrmManagerClient drmManagerClient = this.mDrmManagerClient;
        if (drmManagerClient != null) {
            drmManagerClient.close();
            this.mDrmManagerClient = null;
        }
    }

    private void setDefaultRingtoneFileNames() {
        this.mDefaultRingtoneFilename = SystemProperties.get("ro.config.ringtone");
        this.mDefaultNotificationFilename = SystemProperties.get("ro.config.notification_sound");
        this.mDefaultAlarmAlertFilename = SystemProperties.get("ro.config.alarm_alert");
    }

    @UnsupportedAppUsage
    private native void setLocale(String var1);

    private String settingSetIndicatorName(String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append("_set");
        return stringBuilder.toString();
    }

    private boolean wasRingtoneAlreadySet(String string2) {
        ContentResolver contentResolver = this.mContext.getContentResolver();
        string2 = this.settingSetIndicatorName(string2);
        boolean bl = false;
        try {
            int n = Settings.System.getInt(contentResolver, string2);
            if (n != 0) {
                bl = true;
            }
            return bl;
        }
        catch (Settings.SettingNotFoundException settingNotFoundException) {
            return false;
        }
    }

    @Override
    public void close() {
        this.mCloseGuard.close();
        if (this.mClosed.compareAndSet(false, true)) {
            this.mMediaProvider.close();
            this.native_finalize();
        }
    }

    public native byte[] extractAlbumArt(FileDescriptor var1);

    protected void finalize() throws Throwable {
        try {
            if (this.mCloseGuard != null) {
                this.mCloseGuard.warnIfOpen();
            }
            this.close();
            return;
        }
        finally {
            super.finalize();
        }
    }

    @UnsupportedAppUsage
    FileEntry makeEntryFor(String object) {
        block8 : {
            Cursor cursor;
            block7 : {
                Cursor cursor2 = null;
                Cursor cursor3 = null;
                cursor = this.mMediaProvider.query(this.mFilesFullUri, FILES_PRESCAN_PROJECTION, "_data=?", new String[]{object}, null, null);
                if (cursor == null) break block7;
                cursor3 = cursor;
                cursor2 = cursor;
                if (!cursor.moveToFirst()) break block7;
                cursor3 = cursor;
                cursor2 = cursor;
                try {
                    object = new FileEntry(cursor.getLong(0), (String)object, cursor.getLong(3), cursor.getInt(2), cursor.getInt(4));
                    cursor.close();
                    return object;
                }
                catch (Throwable throwable) {
                    if (cursor3 != null) {
                        cursor3.close();
                    }
                    throw throwable;
                }
                catch (RemoteException remoteException) {
                    if (cursor2 == null) break block8;
                    cursor2.close();
                }
            }
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void scanDirectories(String[] arrstring) {
        Throwable throwable2;
        block9 : {
            try {
                MediaInserter mediaInserter;
                System.currentTimeMillis();
                this.prescan(null, true);
                System.currentTimeMillis();
                this.mMediaInserter = mediaInserter = new MediaInserter(this.mMediaProvider, 500);
                for (int i = 0; i < arrstring.length; ++i) {
                    this.processDirectory(arrstring[i], this.mClient);
                }
                this.mMediaInserter.flushAll();
                this.mMediaInserter = null;
                System.currentTimeMillis();
                this.postscan(arrstring);
                System.currentTimeMillis();
            }
            catch (Throwable throwable2) {
                break block9;
            }
            catch (RemoteException remoteException) {
                Log.e("MediaScanner", "RemoteException in MediaScanner.scan()", remoteException);
            }
            catch (UnsupportedOperationException unsupportedOperationException) {
                Log.e("MediaScanner", "UnsupportedOperationException in MediaScanner.scan()", unsupportedOperationException);
            }
            catch (SQLException sQLException) {
                Log.e("MediaScanner", "SQLException in MediaScanner.scan()", sQLException);
            }
            this.releaseResources();
            return;
        }
        this.releaseResources();
        throw throwable2;
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public void scanMtpFile(String object, int n, int n2) {
        Object object2;
        block17 : {
            block19 : {
                block20 : {
                    block18 : {
                        Object var10_18;
                        block16 : {
                            MyMediaScannerClient myMediaScannerClient;
                            long l;
                            Object var11_19;
                            String string2;
                            FileEntry fileEntry;
                            File file;
                            block15 : {
                                string2 = MediaFile.getMimeType((String)object, n2);
                                file = new File((String)object);
                                l = file.lastModified() / 1000L;
                                if (!(MediaFile.isAudioMimeType(string2) || MediaFile.isVideoMimeType(string2) || MediaFile.isImageMimeType(string2) || MediaFile.isPlayListMimeType(string2) || MediaFile.isDrmMimeType(string2))) {
                                    object = new ContentValues();
                                    ((ContentValues)object).put("_size", file.length());
                                    ((ContentValues)object).put("date_modified", l);
                                    try {
                                        String string3 = Integer.toString(n);
                                        this.mMediaProvider.update(MediaStore.Files.getMtpObjectsUri(this.mVolumeName), (ContentValues)object, "_id=?", new String[]{string3});
                                        return;
                                    }
                                    catch (RemoteException remoteException) {
                                        Log.e("MediaScanner", "RemoteException in scanMtpFile", remoteException);
                                    }
                                    return;
                                }
                                this.mMtpObjectHandle = n;
                                fileEntry = null;
                                var10_18 = null;
                                var11_19 = null;
                                object2 = null;
                                myMediaScannerClient = null;
                                if (!MediaFile.isPlayListMimeType(string2)) break block15;
                                this.prescan(null, true);
                                fileEntry = this.makeEntryFor((String)object);
                                object = myMediaScannerClient;
                                if (fileEntry == null) break block16;
                                object = this.mMediaProvider.query(this.mFilesUri, FILES_PRESCAN_PROJECTION, null, null, null, null);
                                try {
                                    this.processPlayList(fileEntry, (Cursor)object);
                                    break block16;
                                }
                                catch (Throwable throwable) {
                                    object2 = object;
                                    object = throwable;
                                    break block17;
                                }
                                catch (RemoteException remoteException) {
                                    break block18;
                                }
                            }
                            this.prescan((String)object, false);
                            myMediaScannerClient = this.mClient;
                            long l2 = file.length();
                            boolean bl = n2 == 12289;
                            boolean bl2 = MediaScanner.isNoMediaPath((String)object);
                            object2 = var11_19;
                            try {
                                myMediaScannerClient.doScanFile((String)object, string2, l, l2, bl, true, bl2);
                                object = fileEntry;
                            }
                            catch (RemoteException remoteException) {
                                object = var10_18;
                                break block18;
                            }
                        }
                        this.mMtpObjectHandle = 0;
                        if (object == null) break block19;
                        break block20;
                        catch (Throwable throwable) {
                            break block17;
                        }
                        catch (RemoteException remoteException) {
                            object = var10_18;
                        }
                    }
                    object2 = object;
                    try {
                        void var9_17;
                        Log.e("MediaScanner", "RemoteException in MediaScanner.scanFile()", (Throwable)var9_17);
                        this.mMtpObjectHandle = 0;
                        if (object == null) break block19;
                    }
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                object.close();
            }
            this.releaseResources();
            return;
        }
        this.mMtpObjectHandle = 0;
        if (object2 != null) {
            object2.close();
        }
        this.releaseResources();
        throw object;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @UnsupportedAppUsage
    public Uri scanSingleFile(String object, String string2) {
        Throwable throwable2222;
        this.prescan((String)object, true);
        File file = new File((String)object);
        if (file.exists() && file.canRead()) {
            long l = file.lastModified() / 1000L;
            object = this.mClient.doScanFile((String)object, string2, l, file.length(), false, true, MediaScanner.isNoMediaPath((String)object));
            this.releaseResources();
            return object;
        }
        this.releaseResources();
        return null;
        {
            catch (Throwable throwable2222) {
            }
            catch (RemoteException remoteException) {}
            {
                Log.e("MediaScanner", "RemoteException in MediaScanner.scanFile()", remoteException);
                this.releaseResources();
                return null;
            }
        }
        this.releaseResources();
        throw throwable2222;
    }

    private static class FileEntry {
        int mFormat;
        long mLastModified;
        @UnsupportedAppUsage
        boolean mLastModifiedChanged;
        int mMediaType;
        String mPath;
        @UnsupportedAppUsage
        long mRowId;

        @Deprecated
        @UnsupportedAppUsage
        FileEntry(long l, String string2, long l2, int n) {
            this(l, string2, l2, n, 0);
        }

        FileEntry(long l, String string2, long l2, int n, int n2) {
            this.mRowId = l;
            this.mPath = string2;
            this.mLastModified = l2;
            this.mFormat = n;
            this.mMediaType = n2;
            this.mLastModifiedChanged = false;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.mPath);
            stringBuilder.append(" mRowId: ");
            stringBuilder.append(this.mRowId);
            return stringBuilder.toString();
        }
    }

    static class MediaBulkDeleter {
        final Uri mBaseUri;
        final ContentProviderClient mProvider;
        ArrayList<String> whereArgs = new ArrayList(100);
        StringBuilder whereClause = new StringBuilder();

        public MediaBulkDeleter(ContentProviderClient contentProviderClient, Uri uri) {
            this.mProvider = contentProviderClient;
            this.mBaseUri = uri;
        }

        public void delete(long l) throws RemoteException {
            if (this.whereClause.length() != 0) {
                this.whereClause.append(",");
            }
            this.whereClause.append("?");
            ArrayList<String> arrayList = this.whereArgs;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("");
            stringBuilder.append(l);
            arrayList.add(stringBuilder.toString());
            if (this.whereArgs.size() > 100) {
                this.flush();
            }
        }

        public void flush() throws RemoteException {
            int n = this.whereArgs.size();
            if (n > 0) {
                Object object = new String[n];
                String[] arrstring = this.whereArgs.toArray((T[])object);
                object = this.mProvider;
                Uri uri = this.mBaseUri;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("_id IN (");
                stringBuilder.append(this.whereClause.toString());
                stringBuilder.append(")");
                ((ContentProviderClient)object).delete(uri, stringBuilder.toString(), arrstring);
                this.whereClause.setLength(0);
                this.whereArgs.clear();
            }
        }
    }

    private class MyMediaScannerClient
    implements MediaScannerClient {
        private String mAlbum;
        private String mAlbumArtist;
        private String mArtist;
        private int mColorRange;
        private int mColorStandard;
        private int mColorTransfer;
        private int mCompilation;
        private String mComposer;
        private long mDate;
        private final SimpleDateFormat mDateFormatter = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
        private int mDuration;
        private long mFileSize;
        @Deprecated
        @UnsupportedAppUsage
        private int mFileType;
        private String mGenre;
        private int mHeight;
        @UnsupportedAppUsage
        private boolean mIsDrm;
        private long mLastModified;
        @UnsupportedAppUsage
        private String mMimeType;
        @UnsupportedAppUsage
        private boolean mNoMedia;
        @UnsupportedAppUsage
        private String mPath;
        private boolean mScanSuccess;
        private String mTitle;
        private int mTrack;
        private int mWidth;
        private String mWriter;
        private int mYear;

        public MyMediaScannerClient() {
            this.mDateFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        }

        private boolean convertGenreCode(String string2, String string3) {
            String string4 = this.getGenreName(string2);
            if (string4.equals(string3)) {
                return true;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("'");
            stringBuilder.append(string2);
            stringBuilder.append("' -> '");
            stringBuilder.append(string4);
            stringBuilder.append("', expected '");
            stringBuilder.append(string3);
            stringBuilder.append("'");
            Log.d(MediaScanner.TAG, stringBuilder.toString());
            return false;
        }

        private boolean doesPathHaveFilename(String string2, String string3) {
            int n = string2.lastIndexOf(File.separatorChar);
            boolean bl = true;
            int n2 = string3.length();
            if (!string2.regionMatches(++n, string3, 0, n2) || n + n2 != string2.length()) {
                bl = false;
            }
            return bl;
        }

        @UnsupportedAppUsage
        private Uri endFile(FileEntry object, boolean bl, boolean bl2, boolean bl3, boolean bl4, boolean bl5, boolean bl6) throws RemoteException {
            Object object2;
            ContentValues contentValues;
            int n;
            long l;
            int n2;
            int n3;
            Object object3;
            Object object4;
            block52 : {
                block55 : {
                    block53 : {
                        block54 : {
                            block50 : {
                                block51 : {
                                    object4 = this.mArtist;
                                    if (object4 == null || ((String)object4).length() == 0) {
                                        this.mArtist = this.mAlbumArtist;
                                    }
                                    if ((object4 = (contentValues = this.toValues()).getAsString("title")) == null || TextUtils.isEmpty(((String)object4).trim())) {
                                        contentValues.put("title", MediaFile.getFileTitle(contentValues.getAsString("_data")));
                                    }
                                    if ("<unknown>".equals(contentValues.getAsString("album")) && (n3 = ((String)(object4 = contentValues.getAsString("_data"))).lastIndexOf(47)) >= 0) {
                                        n = 0;
                                        while ((n2 = ((String)object4).indexOf(47, n + 1)) >= 0 && n2 < n3) {
                                            n = n2;
                                        }
                                        if (n != 0) {
                                            contentValues.put("album", ((String)object4).substring(n + 1, n3));
                                        }
                                    }
                                    l = ((FileEntry)object).mRowId;
                                    if (MediaFile.isAudioMimeType(this.mMimeType) && (l == 0L || MediaScanner.this.mMtpObjectHandle != 0)) {
                                        contentValues.put("is_ringtone", bl);
                                        contentValues.put("is_notification", bl2);
                                        contentValues.put("is_alarm", bl3);
                                        contentValues.put("is_music", bl6);
                                        contentValues.put("is_podcast", bl4);
                                        contentValues.put("is_audiobook", bl5);
                                    } else if (MediaFile.isExifMimeType(this.mMimeType) && !this.mNoMedia) {
                                        object4 = null;
                                        try {
                                            object4 = object3 = new ExifInterface(((FileEntry)object).mPath);
                                        }
                                        catch (Exception exception) {
                                            // empty catch block
                                        }
                                        if (object4 != null) {
                                            long l2 = ((ExifInterface)object4).getGpsDateTime();
                                            if (l2 != -1L) {
                                                contentValues.put("datetaken", l2);
                                            } else {
                                                l2 = ((ExifInterface)object4).getDateTime();
                                                if (l2 != -1L && Math.abs(this.mLastModified * 1000L - l2) >= 86400000L) {
                                                    contentValues.put("datetaken", l2);
                                                }
                                            }
                                            n = ((ExifInterface)object4).getAttributeInt("Orientation", -1);
                                            if (n != -1) {
                                                n = n != 3 ? (n != 6 ? (n != 8 ? 0 : 270) : 90) : 180;
                                                contentValues.put("orientation", n);
                                            }
                                        }
                                    }
                                    object3 = MediaScanner.this.mFilesUri;
                                    n2 = 0;
                                    object2 = MediaScanner.this.mMediaInserter;
                                    object4 = object3;
                                    n = n2;
                                    if (!this.mNoMedia) {
                                        if (MediaFile.isVideoMimeType(this.mMimeType)) {
                                            object4 = MediaScanner.this.mVideoUri;
                                            n = 3;
                                        } else if (MediaFile.isImageMimeType(this.mMimeType)) {
                                            object4 = MediaScanner.this.mImagesUri;
                                            n = 1;
                                        } else if (MediaFile.isAudioMimeType(this.mMimeType)) {
                                            object4 = MediaScanner.this.mAudioUri;
                                            n = 2;
                                        } else {
                                            object4 = object3;
                                            n = n2;
                                            if (MediaFile.isPlayListMimeType(this.mMimeType)) {
                                                object4 = MediaScanner.this.mPlaylistsUri;
                                                n = 4;
                                            }
                                        }
                                    }
                                    object3 = null;
                                    n3 = 0;
                                    if (!bl2 || MediaScanner.this.mDefaultNotificationSet) break block50;
                                    if (TextUtils.isEmpty(MediaScanner.this.mDefaultNotificationFilename)) break block51;
                                    n2 = n3;
                                    if (!this.doesPathHaveFilename(((FileEntry)object).mPath, MediaScanner.this.mDefaultNotificationFilename)) break block52;
                                }
                                n2 = 1;
                                break block52;
                            }
                            if (!bl || MediaScanner.this.mDefaultRingtoneSet) break block53;
                            if (TextUtils.isEmpty(MediaScanner.this.mDefaultRingtoneFilename)) break block54;
                            n2 = n3;
                            if (!this.doesPathHaveFilename(((FileEntry)object).mPath, MediaScanner.this.mDefaultRingtoneFilename)) break block52;
                        }
                        n2 = 1;
                        break block52;
                    }
                    n2 = n3;
                    if (!bl3) break block52;
                    n2 = n3;
                    if (MediaScanner.this.mDefaultAlarmSet) break block52;
                    if (TextUtils.isEmpty(MediaScanner.this.mDefaultAlarmAlertFilename)) break block55;
                    n2 = n3;
                    if (!this.doesPathHaveFilename(((FileEntry)object).mPath, MediaScanner.this.mDefaultAlarmAlertFilename)) break block52;
                }
                n2 = 1;
            }
            if (l == 0L) {
                if (MediaScanner.this.mMtpObjectHandle != 0) {
                    contentValues.put("media_scanner_new_object_id", MediaScanner.this.mMtpObjectHandle);
                }
                if (object4 == MediaScanner.this.mFilesUri) {
                    n = n3 = ((FileEntry)object).mFormat;
                    if (n3 == 0) {
                        n = MediaFile.getFormatCode(((FileEntry)object).mPath, this.mMimeType);
                    }
                    contentValues.put("format", n);
                }
                if (object2 != null && n2 == 0) {
                    if (((FileEntry)object).mFormat == 12289) {
                        ((MediaInserter)object2).insertwithPriority((Uri)object4, contentValues);
                    } else {
                        ((MediaInserter)object2).insert((Uri)object4, contentValues);
                    }
                } else {
                    if (object2 != null) {
                        ((MediaInserter)object2).flushAll();
                    }
                    object3 = MediaScanner.this.mMediaProvider.insert((Uri)object4, contentValues);
                }
                object2 = object3;
                if (object3 != null) {
                    ((FileEntry)object).mRowId = l = ContentUris.parseId((Uri)object3);
                    object2 = object3;
                }
            } else {
                object2 = ContentUris.withAppendedId((Uri)object4, l);
                contentValues.remove("_data");
                if (!this.mNoMedia && n != ((FileEntry)object).mMediaType) {
                    object = new ContentValues();
                    ((ContentValues)object).put("media_type", n);
                    MediaScanner.this.mMediaProvider.update(ContentUris.withAppendedId(MediaScanner.this.mFilesUri, l), (ContentValues)object, null, null);
                }
                MediaScanner.this.mMediaProvider.update((Uri)object2, contentValues, null, null);
            }
            if (n2 != 0) {
                if (bl2) {
                    this.setRingtoneIfNotSet("notification_sound", (Uri)object4, l);
                    MediaScanner.this.mDefaultNotificationSet = true;
                } else if (bl) {
                    this.setRingtoneIfNotSet("ringtone", (Uri)object4, l);
                    MediaScanner.this.mDefaultRingtoneSet = true;
                } else if (bl3) {
                    this.setRingtoneIfNotSet("alarm_alert", (Uri)object4, l);
                    MediaScanner.this.mDefaultAlarmSet = true;
                }
            }
            return object2;
        }

        @Deprecated
        @UnsupportedAppUsage
        private int getFileTypeFromDrm(String string2) {
            return 0;
        }

        private void getMimeTypeFromDrm(String string2) {
            this.mMimeType = null;
            if (MediaScanner.this.mDrmManagerClient == null) {
                MediaScanner mediaScanner = MediaScanner.this;
                mediaScanner.mDrmManagerClient = new DrmManagerClient(mediaScanner.mContext);
            }
            if (MediaScanner.this.mDrmManagerClient.canHandle(string2, null)) {
                this.mIsDrm = true;
                this.mMimeType = MediaScanner.this.mDrmManagerClient.getOriginalMimeType(string2);
            }
            if (this.mMimeType == null) {
                this.mMimeType = "application/octet-stream";
            }
        }

        private long parseDate(String string2) {
            try {
                long l = this.mDateFormatter.parse(string2).getTime();
                return l;
            }
            catch (ParseException parseException) {
                return 0L;
            }
        }

        private int parseSubstring(String string2, int n, int n2) {
            int n3 = string2.length();
            if (n == n3) {
                return n2;
            }
            int n4 = n + 1;
            if ((n = (int)string2.charAt(n)) >= 48 && n <= 57) {
                n -= 48;
                for (n2 = n4; n2 < n3; ++n2) {
                    n4 = string2.charAt(n2);
                    if (n4 >= 48 && n4 <= 57) {
                        n = n * 10 + (n4 - 48);
                        continue;
                    }
                    return n;
                }
                return n;
            }
            return n2;
        }

        private boolean processImageFile(String string2) {
            boolean bl;
            boolean bl2 = false;
            try {
                MediaScanner.access$1600((MediaScanner)MediaScanner.this).outWidth = 0;
                MediaScanner.access$1600((MediaScanner)MediaScanner.this).outHeight = 0;
                BitmapFactory.decodeFile(string2, MediaScanner.this.mBitmapOptions);
                this.mWidth = MediaScanner.access$1600((MediaScanner)MediaScanner.this).outWidth;
                this.mHeight = MediaScanner.access$1600((MediaScanner)MediaScanner.this).outHeight;
                bl = bl2;
            }
            catch (Throwable throwable) {
                return false;
            }
            if (this.mWidth > 0) {
                int n = this.mHeight;
                bl = bl2;
                if (n > 0) {
                    bl = true;
                }
            }
            return bl;
        }

        private void setRingtoneIfNotSet(String string2, Uri uri, long l) {
            if (MediaScanner.this.wasRingtoneAlreadySet(string2)) {
                return;
            }
            ContentResolver contentResolver = MediaScanner.this.mContext.getContentResolver();
            if (TextUtils.isEmpty(Settings.System.getString(contentResolver, string2))) {
                Uri uri2 = Settings.System.getUriFor(string2);
                uri = ContentUris.withAppendedId(uri, l);
                RingtoneManager.setActualDefaultRingtoneUri(MediaScanner.this.mContext, RingtoneManager.getDefaultType(uri2), uri);
            }
            Settings.System.putInt(contentResolver, MediaScanner.this.settingSetIndicatorName(string2), 1);
        }

        private void testGenreNameConverter() {
            this.convertGenreCode("2", "Country");
            this.convertGenreCode("(2)", "Country");
            this.convertGenreCode("(2", "(2");
            this.convertGenreCode("2 Foo", "Country");
            this.convertGenreCode("(2) Foo", "Country");
            this.convertGenreCode("(2 Foo", "(2 Foo");
            this.convertGenreCode("2Foo", "2Foo");
            this.convertGenreCode("(2)Foo", "Country");
            this.convertGenreCode("200 Foo", "Foo");
            this.convertGenreCode("(200) Foo", "Foo");
            this.convertGenreCode("200Foo", "200Foo");
            this.convertGenreCode("(200)Foo", "Foo");
            this.convertGenreCode("200)Foo", "200)Foo");
            this.convertGenreCode("200) Foo", "200) Foo");
        }

        @UnsupportedAppUsage
        private ContentValues toValues() {
            ContentValues contentValues = new ContentValues();
            contentValues.put("_data", this.mPath);
            contentValues.put("title", this.mTitle);
            contentValues.put("date_modified", this.mLastModified);
            contentValues.put("_size", this.mFileSize);
            contentValues.put("mime_type", this.mMimeType);
            contentValues.put("is_drm", this.mIsDrm);
            contentValues.putNull("_hash");
            CharSequence charSequence = null;
            int n = this.mWidth;
            String string2 = charSequence;
            if (n > 0) {
                string2 = charSequence;
                if (this.mHeight > 0) {
                    contentValues.put("width", n);
                    contentValues.put("height", this.mHeight);
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append(this.mWidth);
                    ((StringBuilder)charSequence).append("x");
                    ((StringBuilder)charSequence).append(this.mHeight);
                    string2 = ((StringBuilder)charSequence).toString();
                }
            }
            if (!this.mNoMedia) {
                boolean bl = MediaFile.isVideoMimeType(this.mMimeType);
                charSequence = "<unknown>";
                if (bl) {
                    long l;
                    String string3 = this.mArtist;
                    string3 = string3 != null && string3.length() > 0 ? this.mArtist : "<unknown>";
                    contentValues.put("artist", string3);
                    string3 = this.mAlbum;
                    if (string3 != null && string3.length() > 0) {
                        charSequence = this.mAlbum;
                    }
                    contentValues.put("album", (String)charSequence);
                    contentValues.put("duration", this.mDuration);
                    if (string2 != null) {
                        contentValues.put("resolution", string2);
                    }
                    if ((n = this.mColorStandard) >= 0) {
                        contentValues.put("color_standard", n);
                    }
                    if ((n = this.mColorTransfer) >= 0) {
                        contentValues.put("color_transfer", n);
                    }
                    if ((n = this.mColorRange) >= 0) {
                        contentValues.put("color_range", n);
                    }
                    if ((l = this.mDate) > 0L) {
                        contentValues.put("datetaken", l);
                    }
                } else if (!MediaFile.isImageMimeType(this.mMimeType) && MediaFile.isAudioMimeType(this.mMimeType)) {
                    string2 = this.mArtist;
                    string2 = string2 != null && string2.length() > 0 ? this.mArtist : "<unknown>";
                    contentValues.put("artist", string2);
                    string2 = this.mAlbumArtist;
                    string2 = string2 != null && string2.length() > 0 ? this.mAlbumArtist : null;
                    contentValues.put("album_artist", string2);
                    string2 = this.mAlbum;
                    if (string2 != null && string2.length() > 0) {
                        charSequence = this.mAlbum;
                    }
                    contentValues.put("album", (String)charSequence);
                    contentValues.put("composer", this.mComposer);
                    contentValues.put("genre", this.mGenre);
                    n = this.mYear;
                    if (n != 0) {
                        contentValues.put("year", n);
                    }
                    contentValues.put("track", this.mTrack);
                    contentValues.put("duration", this.mDuration);
                    contentValues.put("compilation", this.mCompilation);
                }
            }
            return contentValues;
        }

        @UnsupportedAppUsage
        public FileEntry beginFile(String string2, String object, long l, long l2, boolean bl, boolean bl2) {
            Object object2;
            block11 : {
                int n;
                block10 : {
                    this.mMimeType = object;
                    this.mFileSize = l2;
                    this.mIsDrm = false;
                    this.mScanSuccess = true;
                    if (!bl) {
                        if (!bl2 && MediaScanner.isNoMediaFile(string2)) {
                            bl2 = true;
                        }
                        this.mNoMedia = bl2;
                        if (this.mMimeType == null) {
                            this.mMimeType = MediaFile.getMimeTypeForFile(string2);
                        }
                        if (MediaScanner.this.isDrmEnabled() && MediaFile.isDrmMimeType(this.mMimeType)) {
                            this.getMimeTypeFromDrm(string2);
                        }
                    }
                    n = (l2 = (object = MediaScanner.this.makeEntryFor(string2)) != null ? l - ((FileEntry)object).mLastModified : 0L) <= 1L && l2 >= -1L ? 0 : 1;
                    if (object == null) break block10;
                    object2 = object;
                    if (n == 0) break block11;
                }
                if (n != 0) {
                    ((FileEntry)object).mLastModified = l;
                } else {
                    n = bl ? 12289 : 0;
                    object = new FileEntry(0L, string2, l, n, 0);
                }
                ((FileEntry)object).mLastModifiedChanged = true;
                object2 = object;
            }
            if (MediaScanner.this.mProcessPlaylists && MediaFile.isPlayListMimeType(this.mMimeType)) {
                MediaScanner.this.mPlayLists.add(object2);
                return null;
            }
            this.mArtist = null;
            this.mAlbumArtist = null;
            this.mAlbum = null;
            this.mTitle = null;
            this.mComposer = null;
            this.mGenre = null;
            this.mTrack = 0;
            this.mYear = 0;
            this.mDuration = 0;
            this.mPath = string2;
            this.mDate = 0L;
            this.mLastModified = l;
            this.mWriter = null;
            this.mCompilation = 0;
            this.mWidth = 0;
            this.mHeight = 0;
            this.mColorStandard = -1;
            this.mColorTransfer = -1;
            this.mColorRange = -1;
            return object2;
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        @UnsupportedAppUsage
        public Uri doScanFile(String object, String string2, long l, long l2, boolean bl, boolean bl2, boolean bl3) {
            block40 : {
                block54 : {
                    block53 : {
                        block52 : {
                            block51 : {
                                block50 : {
                                    block49 : {
                                        block48 : {
                                            block47 : {
                                                block46 : {
                                                    block45 : {
                                                        block44 : {
                                                            block43 : {
                                                                block42 : {
                                                                    block41 : {
                                                                        block39 : {
                                                                            block38 : {
                                                                                block37 : {
                                                                                    var10_16 = null;
                                                                                    file = null;
                                                                                    fileEntry = this.beginFile((String)object, string2, l, l2, bl, bl3);
                                                                                    if (fileEntry == null) {
                                                                                        return null;
                                                                                    }
                                                                                    n = MediaScanner.access$400(MediaScanner.this);
                                                                                    if (n == 0) break block37;
                                                                                    try {
                                                                                        fileEntry.mRowId = 0L;
                                                                                    }
                                                                                    catch (RemoteException remoteException) {
                                                                                        break block40;
                                                                                    }
                                                                                }
                                                                                charSequence = fileEntry.mPath;
                                                                                if (charSequence == null) ** GOTO lbl-1000
                                                                                bl = MediaScanner.access$500(MediaScanner.this);
                                                                                if (!(bl == false && this.doesPathHaveFilename(fileEntry.mPath, MediaScanner.access$600(MediaScanner.this)) != false || MediaScanner.access$700(MediaScanner.this) == false && this.doesPathHaveFilename(fileEntry.mPath, MediaScanner.access$800(MediaScanner.this)) != false) && (MediaScanner.access$900(MediaScanner.this) || !this.doesPathHaveFilename(fileEntry.mPath, MediaScanner.access$1000(MediaScanner.this)))) break block38;
                                                                                charSequence = new StringBuilder();
                                                                                charSequence.append("forcing rescan of ");
                                                                                charSequence.append(fileEntry.mPath);
                                                                                charSequence.append("since ringtone setting didn't finish");
                                                                                Log.w("MediaScanner", charSequence.toString());
                                                                                bl = true;
                                                                                break block39;
                                                                            }
                                                                            if (MediaScanner.access$1100(fileEntry.mPath) && !Build.FINGERPRINT.equals(MediaScanner.access$1200())) {
                                                                                charSequence = new StringBuilder();
                                                                                charSequence.append("forcing rescan of ");
                                                                                charSequence.append(fileEntry.mPath);
                                                                                charSequence.append(" since build fingerprint changed");
                                                                                Log.i("MediaScanner", charSequence.toString());
                                                                                bl = true;
                                                                            } else lbl-1000: // 2 sources:
                                                                            {
                                                                                bl = bl2;
                                                                            }
                                                                        }
                                                                        bl2 = fileEntry.mLastModifiedChanged;
                                                                        if (!bl2 && !bl) {
                                                                            return file;
                                                                        }
                                                                        if (!bl3) break block41;
                                                                        try {
                                                                            return this.endFile(fileEntry, false, false, false, false, false, false);
                                                                        }
                                                                        catch (RemoteException remoteException) {
                                                                            break block40;
                                                                        }
                                                                    }
                                                                    bl3 = MediaFile.isAudioMimeType(this.mMimeType);
                                                                    bl2 = MediaFile.isVideoMimeType(this.mMimeType);
                                                                    bl = MediaFile.isImageMimeType(this.mMimeType);
                                                                    if (!bl3 && !bl2 && !bl) break block42;
                                                                    file = new File((String)object);
                                                                    object = Environment.maybeTranslateEmulatedPathToInternal(file).getAbsolutePath();
                                                                }
                                                                if (bl3 || bl2) {
                                                                    this.mScanSuccess = MediaScanner.access$1300(MediaScanner.this, (String)object, string2, this);
                                                                }
                                                                if (bl) {
                                                                    this.mScanSuccess = this.processImageFile((String)object);
                                                                }
                                                                object = object.toLowerCase(Locale.ROOT);
                                                                bl = this.mScanSuccess;
                                                                if (!bl) break block43;
                                                                try {
                                                                    n = object.indexOf("/ringtones/");
                                                                    if (n <= 0) break block43;
                                                                    bl = true;
                                                                    break block44;
                                                                }
                                                                catch (RemoteException remoteException) {
                                                                    break block40;
                                                                }
                                                            }
                                                            bl = false;
                                                        }
                                                        bl2 = this.mScanSuccess;
                                                        if (!bl2) break block45;
                                                        n = object.indexOf("/notifications/");
                                                        if (n <= 0) break block45;
                                                        bl2 = true;
                                                        break block46;
                                                    }
                                                    bl2 = false;
                                                }
                                                bl3 = this.mScanSuccess;
                                                if (!bl3) break block47;
                                                n = object.indexOf("/alarms/");
                                                if (n <= 0) break block47;
                                                bl3 = true;
                                                break block48;
                                            }
                                            bl3 = false;
                                        }
                                        bl6 = this.mScanSuccess;
                                        if (!bl6) break block49;
                                        n = object.indexOf("/podcasts/");
                                        if (n <= 0) break block49;
                                        bl6 = true;
                                        break block50;
                                    }
                                    bl6 = false;
                                }
                                bl5 = this.mScanSuccess;
                                if (!bl5) break block51;
                                n = object.indexOf("/audiobooks/");
                                if (n <= 0) break block51;
                                bl5 = true;
                                break block52;
                            }
                            bl5 = false;
                        }
                        try {
                            bl4 = this.mScanSuccess;
                            if (!bl4) break block53;
                        }
                        catch (RemoteException remoteException) {}
                        n = object.indexOf("/music/");
                        if (n <= 0 && (bl || bl2 || bl3 || bl6 || bl5)) break block53;
                        bl4 = true;
                        break block54;
                    }
                    bl4 = false;
                }
                try {
                    return this.endFile(fileEntry, bl, bl2, bl3, bl6, bl5, bl4);
                }
                catch (RemoteException remoteException) {
                    break block40;
                }
                break block40;
                catch (RemoteException remoteException) {}
                break block40;
                catch (RemoteException remoteException) {}
                break block40;
                catch (RemoteException remoteException) {
                    // empty catch block
                }
            }
            Log.e("MediaScanner", "RemoteException in MediaScanner.scanFile()", (Throwable)object);
            return var10_16;
        }

        public String getGenreName(String string2) {
            block13 : {
                if (string2 == null) {
                    return null;
                }
                int n = string2.length();
                if (n > 0) {
                    int n2;
                    short s;
                    short s2;
                    boolean bl = false;
                    CharSequence charSequence = new StringBuffer();
                    for (n2 = 0; n2 < n; ++n2) {
                        s2 = string2.charAt(n2);
                        if (n2 == 0 && s2 == 40) {
                            bl = true;
                            continue;
                        }
                        if (!Character.isDigit((char)s2)) break;
                        ((StringBuffer)charSequence).append((char)s2);
                    }
                    s2 = n2 < n ? (s = (short)string2.charAt(n2)) : (s = 32);
                    if (bl && s2 == 41 || !bl && Character.isWhitespace((char)s2)) {
                        block14 : {
                            block15 : {
                                s = Short.parseShort(((StringBuffer)charSequence).toString());
                                if (s < 0) break block13;
                                if (s < ID3_GENRES.length && ID3_GENRES[s] != null) {
                                    return ID3_GENRES[s];
                                }
                                if (s == 255) {
                                    return null;
                                }
                                if (s >= 255 || n2 + 1 >= n) break block14;
                                n = n2;
                                if (!bl) break block15;
                                n = n2;
                                if (s2 != 41) break block15;
                                n = n2 + 1;
                            }
                            charSequence = string2.substring(n).trim();
                            if (((String)charSequence).length() != 0) {
                                return charSequence;
                            }
                        }
                        try {
                            charSequence = ((StringBuffer)charSequence).toString();
                            return charSequence;
                        }
                        catch (NumberFormatException numberFormatException) {
                            // empty catch block
                        }
                    }
                }
            }
            return string2;
        }

        @UnsupportedAppUsage
        @Override
        public void handleStringTag(String string2, String string3) {
            if (!string2.equalsIgnoreCase("title") && !string2.startsWith("title;")) {
                if (!string2.equalsIgnoreCase("artist") && !string2.startsWith("artist;")) {
                    if (!(string2.equalsIgnoreCase("albumartist") || string2.startsWith("albumartist;") || string2.equalsIgnoreCase("band") || string2.startsWith("band;"))) {
                        if (!string2.equalsIgnoreCase("album") && !string2.startsWith("album;")) {
                            if (!string2.equalsIgnoreCase("composer") && !string2.startsWith("composer;")) {
                                if (MediaScanner.this.mProcessGenres && (string2.equalsIgnoreCase("genre") || string2.startsWith("genre;"))) {
                                    this.mGenre = this.getGenreName(string3);
                                } else {
                                    boolean bl = string2.equalsIgnoreCase("year");
                                    boolean bl2 = false;
                                    if (!bl && !string2.startsWith("year;")) {
                                        if (!string2.equalsIgnoreCase("tracknumber") && !string2.startsWith("tracknumber;")) {
                                            if (!(string2.equalsIgnoreCase("discnumber") || string2.equals("set") || string2.startsWith("set;"))) {
                                                if (string2.equalsIgnoreCase("duration")) {
                                                    this.mDuration = this.parseSubstring(string3, 0, 0);
                                                } else if (!string2.equalsIgnoreCase("writer") && !string2.startsWith("writer;")) {
                                                    if (string2.equalsIgnoreCase("compilation")) {
                                                        this.mCompilation = this.parseSubstring(string3, 0, 0);
                                                    } else if (string2.equalsIgnoreCase("isdrm")) {
                                                        if (this.parseSubstring(string3, 0, 0) == 1) {
                                                            bl2 = true;
                                                        }
                                                        this.mIsDrm = bl2;
                                                    } else if (string2.equalsIgnoreCase("date")) {
                                                        this.mDate = this.parseDate(string3);
                                                    } else if (string2.equalsIgnoreCase("width")) {
                                                        this.mWidth = this.parseSubstring(string3, 0, 0);
                                                    } else if (string2.equalsIgnoreCase("height")) {
                                                        this.mHeight = this.parseSubstring(string3, 0, 0);
                                                    } else if (string2.equalsIgnoreCase("colorstandard")) {
                                                        this.mColorStandard = this.parseSubstring(string3, 0, -1);
                                                    } else if (string2.equalsIgnoreCase("colortransfer")) {
                                                        this.mColorTransfer = this.parseSubstring(string3, 0, -1);
                                                    } else if (string2.equalsIgnoreCase("colorrange")) {
                                                        this.mColorRange = this.parseSubstring(string3, 0, -1);
                                                    }
                                                } else {
                                                    this.mWriter = string3.trim();
                                                }
                                            } else {
                                                this.mTrack = this.parseSubstring(string3, 0, 0) * 1000 + this.mTrack % 1000;
                                            }
                                        } else {
                                            int n = this.parseSubstring(string3, 0, 0);
                                            this.mTrack = this.mTrack / 1000 * 1000 + n;
                                        }
                                    } else {
                                        this.mYear = this.parseSubstring(string3, 0, 0);
                                    }
                                }
                            } else {
                                this.mComposer = string3.trim();
                            }
                        } else {
                            this.mAlbum = string3.trim();
                        }
                    } else {
                        this.mAlbumArtist = string3.trim();
                    }
                } else {
                    this.mArtist = string3.trim();
                }
            } else {
                this.mTitle = string3;
            }
        }

        @UnsupportedAppUsage
        @Override
        public void scanFile(String string2, long l, long l2, boolean bl, boolean bl2) {
            this.doScanFile(string2, null, l, l2, bl, false, bl2);
        }

        @UnsupportedAppUsage
        @Override
        public void setMimeType(String string2) {
            if ("audio/mp4".equals(this.mMimeType) && string2.startsWith("video")) {
                return;
            }
            this.mMimeType = string2;
        }
    }

    private static class PlaylistEntry {
        long bestmatchid;
        int bestmatchlevel;
        String path;

        private PlaylistEntry() {
        }
    }

    class WplHandler
    implements ElementListener {
        final ContentHandler handler;
        String playListDirectory;

        public WplHandler(String string2, Uri uri, Cursor cursor) {
            this.playListDirectory = string2;
            MediaScanner.this = new RootElement("smil");
            ((Element)MediaScanner.this).getChild("body").getChild("seq").getChild("media").setElementListener(this);
            this.handler = ((RootElement)MediaScanner.this).getContentHandler();
        }

        @Override
        public void end() {
        }

        ContentHandler getContentHandler() {
            return this.handler;
        }

        @Override
        public void start(Attributes object) {
            if ((object = object.getValue("", "src")) != null) {
                MediaScanner.this.cachePlaylistEntry((String)object, this.playListDirectory);
            }
        }
    }

}

