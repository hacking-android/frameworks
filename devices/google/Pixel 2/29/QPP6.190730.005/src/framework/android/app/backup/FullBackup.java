/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.system.ErrnoException
 *  android.system.Os
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 */
package android.app.backup;

import android.annotation.UnsupportedAppUsage;
import android.app.backup.FullBackupDataOutput;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.ParcelFileDescriptor;
import android.os.Process;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.system.ErrnoException;
import android.system.Os;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class FullBackup {
    public static final String APK_TREE_TOKEN = "a";
    public static final String APPS_PREFIX = "apps/";
    public static final String CACHE_TREE_TOKEN = "c";
    public static final String CONF_TOKEN_INTENT_EXTRA = "conftoken";
    public static final String DATABASE_TREE_TOKEN = "db";
    public static final String DEVICE_CACHE_TREE_TOKEN = "d_c";
    public static final String DEVICE_DATABASE_TREE_TOKEN = "d_db";
    public static final String DEVICE_FILES_TREE_TOKEN = "d_f";
    public static final String DEVICE_NO_BACKUP_TREE_TOKEN = "d_nb";
    public static final String DEVICE_ROOT_TREE_TOKEN = "d_r";
    public static final String DEVICE_SHAREDPREFS_TREE_TOKEN = "d_sp";
    public static final String FILES_TREE_TOKEN = "f";
    public static final String FLAG_REQUIRED_CLIENT_SIDE_ENCRYPTION = "clientSideEncryption";
    public static final String FLAG_REQUIRED_DEVICE_TO_DEVICE_TRANSFER = "deviceToDeviceTransfer";
    public static final String FLAG_REQUIRED_FAKE_CLIENT_SIDE_ENCRYPTION = "fakeClientSideEncryption";
    public static final String FULL_BACKUP_INTENT_ACTION = "fullback";
    public static final String FULL_RESTORE_INTENT_ACTION = "fullrest";
    public static final String KEY_VALUE_DATA_TOKEN = "k";
    public static final String MANAGED_EXTERNAL_TREE_TOKEN = "ef";
    public static final String NO_BACKUP_TREE_TOKEN = "nb";
    public static final String OBB_TREE_TOKEN = "obb";
    public static final String ROOT_TREE_TOKEN = "r";
    public static final String SHAREDPREFS_TREE_TOKEN = "sp";
    public static final String SHARED_PREFIX = "shared/";
    public static final String SHARED_STORAGE_TOKEN = "shared";
    static final String TAG = "FullBackup";
    static final String TAG_XML_PARSER = "BackupXmlParserLogging";
    private static final Map<String, BackupScheme> kPackageBackupSchemeMap = new ArrayMap<String, BackupScheme>();

    @UnsupportedAppUsage
    public static native int backupToTar(String var0, String var1, String var2, String var3, String var4, FullBackupDataOutput var5);

    static BackupScheme getBackupScheme(Context context) {
        synchronized (FullBackup.class) {
            BackupScheme backupScheme;
            block5 : {
                BackupScheme backupScheme2;
                backupScheme = backupScheme2 = kPackageBackupSchemeMap.get(context.getPackageName());
                if (backupScheme2 != null) break block5;
                backupScheme = new BackupScheme(context);
                kPackageBackupSchemeMap.put(context.getPackageName(), backupScheme);
            }
            return backupScheme;
            finally {
            }
        }
    }

    public static BackupScheme getBackupSchemeForTest(Context object) {
        object = new BackupScheme((Context)object);
        ((BackupScheme)object).mExcludes = new ArraySet<E>();
        ((BackupScheme)object).mIncludes = new ArrayMap<String, Set<BackupScheme.PathWithRequiredFlags>>();
        return object;
    }

    public static void restoreFile(ParcelFileDescriptor object, long l, int n, long l2, long l3, File file) throws IOException {
        block15 : {
            if (n == 2) {
                if (file != null) {
                    file.mkdirs();
                }
            } else {
                Object object2;
                byte[] arrby = null;
                Object object3 = null;
                if (file != null) {
                    try {
                        object3 = file.getParentFile();
                        if (!((File)object3).exists()) {
                            ((File)object3).mkdirs();
                        }
                        object3 = new FileOutputStream(file);
                    }
                    catch (IOException iOException) {
                        object2 = new StringBuilder();
                        ((StringBuilder)object2).append("Unable to create/open file ");
                        ((StringBuilder)object2).append(file.getPath());
                        Log.e("FullBackup", ((StringBuilder)object2).toString(), iOException);
                        object3 = arrby;
                    }
                }
                arrby = new byte[32768];
                object2 = new FileInputStream(((ParcelFileDescriptor)object).getFileDescriptor());
                for (long i = l; i > 0L; i -= (long)n) {
                    n = i > (long)arrby.length ? arrby.length : (int)i;
                    if ((n = ((FileInputStream)object2).read(arrby, 0, n)) <= 0) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Incomplete read: expected ");
                        ((StringBuilder)object).append(i);
                        ((StringBuilder)object).append(" but got ");
                        ((StringBuilder)object).append(l - i);
                        Log.w("FullBackup", ((StringBuilder)object).toString());
                        break;
                    }
                    object = object3;
                    if (object3 != null) {
                        try {
                            ((FileOutputStream)object3).write(arrby, 0, n);
                            object = object3;
                        }
                        catch (IOException iOException) {
                            object = new StringBuilder();
                            ((StringBuilder)object).append("Unable to write to file ");
                            ((StringBuilder)object).append(file.getPath());
                            Log.e("FullBackup", ((StringBuilder)object).toString(), iOException);
                            ((FileOutputStream)object3).close();
                            file.delete();
                            object = null;
                        }
                    }
                    object3 = object;
                }
                if (object3 != null) {
                    ((FileOutputStream)object3).close();
                }
            }
            if (l2 < 0L || file == null) break block15;
            try {
                Os.chmod((String)file.getPath(), (int)((int)(l2 & 448L)));
            }
            catch (ErrnoException errnoException) {
                errnoException.rethrowAsIOException();
            }
            file.setLastModified(l3);
        }
    }

    @VisibleForTesting
    public static class BackupScheme {
        private static final String TAG_EXCLUDE = "exclude";
        private static final String TAG_INCLUDE = "include";
        private final File CACHE_DIR;
        private final File DATABASE_DIR;
        private final File DEVICE_CACHE_DIR;
        private final File DEVICE_DATABASE_DIR;
        private final File DEVICE_FILES_DIR;
        private final File DEVICE_NOBACKUP_DIR;
        private final File DEVICE_ROOT_DIR;
        private final File DEVICE_SHAREDPREF_DIR;
        private final File EXTERNAL_DIR;
        private final File FILES_DIR;
        private final File NOBACKUP_DIR;
        private final File ROOT_DIR;
        private final File SHAREDPREF_DIR;
        ArraySet<PathWithRequiredFlags> mExcludes;
        final int mFullBackupContent;
        Map<String, Set<PathWithRequiredFlags>> mIncludes;
        final PackageManager mPackageManager;
        final String mPackageName;
        final StorageManager mStorageManager;
        private StorageVolume[] mVolumes = null;

        BackupScheme(Context context) {
            this.mFullBackupContent = context.getApplicationInfo().fullBackupContent;
            this.mStorageManager = (StorageManager)context.getSystemService("storage");
            this.mPackageManager = context.getPackageManager();
            this.mPackageName = context.getPackageName();
            Context context2 = context.createCredentialProtectedStorageContext();
            this.FILES_DIR = context2.getFilesDir();
            this.DATABASE_DIR = context2.getDatabasePath("foo").getParentFile();
            this.ROOT_DIR = context2.getDataDir();
            this.SHAREDPREF_DIR = context2.getSharedPreferencesPath("foo").getParentFile();
            this.CACHE_DIR = context2.getCacheDir();
            this.NOBACKUP_DIR = context2.getNoBackupFilesDir();
            context2 = context.createDeviceProtectedStorageContext();
            this.DEVICE_FILES_DIR = context2.getFilesDir();
            this.DEVICE_DATABASE_DIR = context2.getDatabasePath("foo").getParentFile();
            this.DEVICE_ROOT_DIR = context2.getDataDir();
            this.DEVICE_SHAREDPREF_DIR = context2.getSharedPreferencesPath("foo").getParentFile();
            this.DEVICE_CACHE_DIR = context2.getCacheDir();
            this.DEVICE_NOBACKUP_DIR = context2.getNoBackupFilesDir();
            this.EXTERNAL_DIR = Process.myUid() != 1000 ? context.getExternalFilesDir(null) : null;
        }

        private File extractCanonicalFile(File file, String charSequence) {
            String string2 = charSequence;
            if (charSequence == null) {
                string2 = "";
            }
            if (string2.contains("..")) {
                if (Log.isLoggable(FullBackup.TAG_XML_PARSER, 2)) {
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("...resolved \"");
                    ((StringBuilder)charSequence).append(file.getPath());
                    ((StringBuilder)charSequence).append(" ");
                    ((StringBuilder)charSequence).append(string2);
                    ((StringBuilder)charSequence).append("\", but the \"..\" path is not permitted; skipping.");
                    Log.v(FullBackup.TAG_XML_PARSER, ((StringBuilder)charSequence).toString());
                }
                return null;
            }
            if (string2.contains("//")) {
                if (Log.isLoggable(FullBackup.TAG_XML_PARSER, 2)) {
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("...resolved \"");
                    ((StringBuilder)charSequence).append(file.getPath());
                    ((StringBuilder)charSequence).append(" ");
                    ((StringBuilder)charSequence).append(string2);
                    ((StringBuilder)charSequence).append("\", which contains the invalid \"//\" sequence; skipping.");
                    Log.v(FullBackup.TAG_XML_PARSER, ((StringBuilder)charSequence).toString());
                }
                return null;
            }
            return new File(file, string2);
        }

        private File getDirectoryForCriteriaDomain(String string2) {
            if (TextUtils.isEmpty(string2)) {
                return null;
            }
            if ("file".equals(string2)) {
                return this.FILES_DIR;
            }
            if ("database".equals(string2)) {
                return this.DATABASE_DIR;
            }
            if ("root".equals(string2)) {
                return this.ROOT_DIR;
            }
            if ("sharedpref".equals(string2)) {
                return this.SHAREDPREF_DIR;
            }
            if ("device_file".equals(string2)) {
                return this.DEVICE_FILES_DIR;
            }
            if ("device_database".equals(string2)) {
                return this.DEVICE_DATABASE_DIR;
            }
            if ("device_root".equals(string2)) {
                return this.DEVICE_ROOT_DIR;
            }
            if ("device_sharedpref".equals(string2)) {
                return this.DEVICE_SHAREDPREF_DIR;
            }
            if ("external".equals(string2)) {
                return this.EXTERNAL_DIR;
            }
            return null;
        }

        private int getRequiredFlagsFromString(String charSequence) {
            if (charSequence != null && ((String)charSequence).length() != 0) {
                String[] arrstring = ((String)charSequence).split("\\|");
                int n = arrstring.length;
                int n2 = 0;
                for (int i = 0; i < n; ++i) {
                    String string2 = arrstring[i];
                    int n3 = -1;
                    int n4 = string2.hashCode();
                    if (n4 != 482744282) {
                        if (n4 != 1499007205) {
                            if (n4 == 1935925810 && string2.equals(FullBackup.FLAG_REQUIRED_DEVICE_TO_DEVICE_TRANSFER)) {
                                n3 = 1;
                            }
                        } else if (string2.equals(FullBackup.FLAG_REQUIRED_CLIENT_SIDE_ENCRYPTION)) {
                            n3 = 0;
                        }
                    } else if (string2.equals(FullBackup.FLAG_REQUIRED_FAKE_CLIENT_SIDE_ENCRYPTION)) {
                        n3 = 2;
                    }
                    if (n3 != 0) {
                        if (n3 != 1) {
                            n3 = n3 != 2 ? n2 : n2 | Integer.MIN_VALUE;
                            charSequence = new StringBuilder();
                            ((StringBuilder)charSequence).append("Unrecognized requiredFlag provided, value: \"");
                            ((StringBuilder)charSequence).append(string2);
                            ((StringBuilder)charSequence).append("\"");
                            Log.w(FullBackup.TAG, ((StringBuilder)charSequence).toString());
                        } else {
                            n3 = n2 | 2;
                        }
                    } else {
                        n3 = n2 | 1;
                    }
                    n2 = n3;
                }
                return n2;
            }
            return 0;
        }

        private String getTokenForXmlDomain(String string2) {
            if ("root".equals(string2)) {
                return FullBackup.ROOT_TREE_TOKEN;
            }
            if ("file".equals(string2)) {
                return FullBackup.FILES_TREE_TOKEN;
            }
            if ("database".equals(string2)) {
                return FullBackup.DATABASE_TREE_TOKEN;
            }
            if ("sharedpref".equals(string2)) {
                return FullBackup.SHAREDPREFS_TREE_TOKEN;
            }
            if ("device_root".equals(string2)) {
                return FullBackup.DEVICE_ROOT_TREE_TOKEN;
            }
            if ("device_file".equals(string2)) {
                return FullBackup.DEVICE_FILES_TREE_TOKEN;
            }
            if ("device_database".equals(string2)) {
                return FullBackup.DEVICE_DATABASE_TREE_TOKEN;
            }
            if ("device_sharedpref".equals(string2)) {
                return FullBackup.DEVICE_SHAREDPREFS_TREE_TOKEN;
            }
            if ("external".equals(string2)) {
                return FullBackup.MANAGED_EXTERNAL_TREE_TOKEN;
            }
            return null;
        }

        private StorageVolume[] getVolumeList() {
            StorageManager storageManager = this.mStorageManager;
            if (storageManager != null) {
                if (this.mVolumes == null) {
                    this.mVolumes = storageManager.getVolumeList();
                }
            } else {
                Log.e(FullBackup.TAG, "Unable to access Storage Manager");
            }
            return this.mVolumes;
        }

        /*
         * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
         * Loose catch block
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        private void maybeParseBackupSchemeLocked() throws IOException, XmlPullParserException {
            Throwable throwable2222;
            XmlResourceParser xmlResourceParser;
            this.mIncludes = new ArrayMap<String, Set<PathWithRequiredFlags>>();
            this.mExcludes = new ArraySet();
            if (this.mFullBackupContent == 0) {
                if (!Log.isLoggable(FullBackup.TAG_XML_PARSER, 2)) return;
                Log.v(FullBackup.TAG_XML_PARSER, "android:fullBackupContent - \"true\"");
                return;
            }
            if (Log.isLoggable(FullBackup.TAG_XML_PARSER, 2)) {
                Log.v(FullBackup.TAG_XML_PARSER, "android:fullBackupContent - found xml resource");
            }
            XmlResourceParser xmlResourceParser2 = null;
            XmlResourceParser xmlResourceParser3 = null;
            xmlResourceParser3 = xmlResourceParser = this.mPackageManager.getResourcesForApplication(this.mPackageName).getXml(this.mFullBackupContent);
            xmlResourceParser2 = xmlResourceParser;
            this.parseBackupSchemeFromXmlLocked(xmlResourceParser, this.mExcludes, this.mIncludes);
            if (xmlResourceParser == null) return;
            xmlResourceParser.close();
            return;
            {
                catch (Throwable throwable2222) {
                }
                catch (PackageManager.NameNotFoundException nameNotFoundException) {}
                xmlResourceParser3 = xmlResourceParser2;
                {
                    xmlResourceParser3 = xmlResourceParser2;
                    IOException iOException = new IOException(nameNotFoundException);
                    xmlResourceParser3 = xmlResourceParser2;
                    throw iOException;
                }
            }
            if (xmlResourceParser3 == null) throw throwable2222;
            xmlResourceParser3.close();
            throw throwable2222;
        }

        private Set<PathWithRequiredFlags> parseCurrentTagForDomain(XmlPullParser object, Set<PathWithRequiredFlags> object2, Map<String, Set<PathWithRequiredFlags>> map, String string2) throws XmlPullParserException {
            if (TAG_INCLUDE.equals(object.getName())) {
                string2 = this.getTokenForXmlDomain(string2);
                object2 = map.get(string2);
                object = object2;
                if (object2 == null) {
                    object = new ArraySet();
                    map.put(string2, (Set<PathWithRequiredFlags>)object);
                }
                return object;
            }
            if (TAG_EXCLUDE.equals(object.getName())) {
                return object2;
            }
            if (Log.isLoggable(FullBackup.TAG_XML_PARSER, 2)) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Invalid tag found in xml \"");
                ((StringBuilder)object2).append(object.getName());
                ((StringBuilder)object2).append("\"; aborting operation.");
                Log.v(FullBackup.TAG_XML_PARSER, ((StringBuilder)object2).toString());
            }
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Unrecognised tag in backup criteria xml (");
            ((StringBuilder)object2).append(object.getName());
            ((StringBuilder)object2).append(")");
            throw new XmlPullParserException(((StringBuilder)object2).toString());
        }

        private String sharedDomainToPath(String arrstorageVolume) throws IOException {
            String string2 = arrstorageVolume.substring(FullBackup.SHARED_PREFIX.length());
            arrstorageVolume = this.getVolumeList();
            int n = Integer.parseInt(string2);
            if (n < this.mVolumes.length) {
                return arrstorageVolume[n].getPathFile().getCanonicalPath();
            }
            return null;
        }

        private void validateInnerTagContents(XmlPullParser xmlPullParser) throws XmlPullParserException {
            block13 : {
                block12 : {
                    block10 : {
                        CharSequence charSequence;
                        block11 : {
                            if (xmlPullParser == null) {
                                return;
                            }
                            charSequence = xmlPullParser.getName();
                            int n = -1;
                            int n2 = ((String)charSequence).hashCode();
                            if (n2 != -1321148966) {
                                if (n2 == 1942574248 && ((String)charSequence).equals(TAG_INCLUDE)) {
                                    n = 0;
                                }
                            } else if (((String)charSequence).equals(TAG_EXCLUDE)) {
                                n = 1;
                            }
                            if (n == 0) break block10;
                            if (n != 1) break block11;
                            if (xmlPullParser.getAttributeCount() > 2) {
                                throw new XmlPullParserException("At most 2 tag attributes allowed for \"exclude\" tag (\"domain\" & \"path\".");
                            }
                            break block12;
                        }
                        charSequence = new StringBuilder();
                        ((StringBuilder)charSequence).append("A valid tag is one of \"<include/>\" or \"<exclude/>. You provided \"");
                        ((StringBuilder)charSequence).append(xmlPullParser.getName());
                        ((StringBuilder)charSequence).append("\"");
                        throw new XmlPullParserException(((StringBuilder)charSequence).toString());
                    }
                    if (xmlPullParser.getAttributeCount() > 3) break block13;
                }
                return;
            }
            throw new XmlPullParserException("At most 3 tag attributes allowed for \"include\" tag (\"domain\" & \"path\" & optional \"requiredFlags\").");
        }

        boolean isFullBackupContentEnabled() {
            if (this.mFullBackupContent < 0) {
                if (Log.isLoggable(FullBackup.TAG_XML_PARSER, 2)) {
                    Log.v(FullBackup.TAG_XML_PARSER, "android:fullBackupContent - \"false\"");
                }
                return false;
            }
            return true;
        }

        public ArraySet<PathWithRequiredFlags> maybeParseAndGetCanonicalExcludePaths() throws IOException, XmlPullParserException {
            synchronized (this) {
                if (this.mExcludes == null) {
                    this.maybeParseBackupSchemeLocked();
                }
                ArraySet<PathWithRequiredFlags> arraySet = this.mExcludes;
                return arraySet;
            }
        }

        public Map<String, Set<PathWithRequiredFlags>> maybeParseAndGetCanonicalIncludePaths() throws IOException, XmlPullParserException {
            synchronized (this) {
                if (this.mIncludes == null) {
                    this.maybeParseBackupSchemeLocked();
                }
                Map<String, Set<PathWithRequiredFlags>> map = this.mIncludes;
                return map;
            }
        }

        @VisibleForTesting
        public void parseBackupSchemeFromXmlLocked(XmlPullParser object, Set<PathWithRequiredFlags> iterator, Map<String, Set<PathWithRequiredFlags>> iterator22) throws IOException, XmlPullParserException {
            int n = object.getEventType();
            while (n != 2) {
                n = object.next();
            }
            if ("full-backup-content".equals(object.getName())) {
                Object object2;
                Object object32;
                if (Log.isLoggable(FullBackup.TAG_XML_PARSER, 2)) {
                    Log.v(FullBackup.TAG_XML_PARSER, "\n");
                    Log.v(FullBackup.TAG_XML_PARSER, "====================================================");
                    Log.v(FullBackup.TAG_XML_PARSER, "Found valid fullBackupContent; parsing xml resource.");
                    Log.v(FullBackup.TAG_XML_PARSER, "====================================================");
                    Log.v(FullBackup.TAG_XML_PARSER, "");
                }
                do {
                    object32 = object;
                    object2 = this;
                    n = object.next();
                    if (n == 1) break;
                    if (n != 2) continue;
                    this.validateInnerTagContents((XmlPullParser)object);
                    String string2 = object32.getAttributeValue(null, "domain");
                    File file = ((BackupScheme)object2).getDirectoryForCriteriaDomain(string2);
                    if (file == null) {
                        if (!Log.isLoggable(FullBackup.TAG_XML_PARSER, 2)) continue;
                        object32 = new StringBuilder();
                        ((StringBuilder)object32).append("...parsing \"");
                        ((StringBuilder)object32).append(object.getName());
                        ((StringBuilder)object32).append("\": domain=\"");
                        ((StringBuilder)object32).append(string2);
                        ((StringBuilder)object32).append("\" invalid; skipping");
                        Log.v(FullBackup.TAG_XML_PARSER, ((StringBuilder)object32).toString());
                        continue;
                    }
                    if ((file = ((BackupScheme)object2).extractCanonicalFile(file, object32.getAttributeValue(null, "path"))) == null) continue;
                    n = 0;
                    if (TAG_INCLUDE.equals(object.getName())) {
                        n = ((BackupScheme)object2).getRequiredFlagsFromString(object32.getAttributeValue(null, "requireFlags"));
                    }
                    object32 = ((BackupScheme)object2).parseCurrentTagForDomain((XmlPullParser)object32, (Set<PathWithRequiredFlags>)((Object)iterator), (Map<String, Set<PathWithRequiredFlags>>)((Object)iterator22), string2);
                    object32.add((PathWithRequiredFlags)new PathWithRequiredFlags(file.getCanonicalPath(), n));
                    if (Log.isLoggable(FullBackup.TAG_XML_PARSER, 2)) {
                        object2 = new StringBuilder();
                        ((StringBuilder)object2).append("...parsed ");
                        ((StringBuilder)object2).append(file.getCanonicalPath());
                        ((StringBuilder)object2).append(" for domain \"");
                        ((StringBuilder)object2).append(string2);
                        ((StringBuilder)object2).append("\", requiredFlags + \"");
                        ((StringBuilder)object2).append(n);
                        ((StringBuilder)object2).append("\"");
                        Log.v(FullBackup.TAG_XML_PARSER, ((StringBuilder)object2).toString());
                    }
                    if ("database".equals(string2) && !file.isDirectory()) {
                        object2 = new StringBuilder();
                        ((StringBuilder)object2).append(file.getCanonicalPath());
                        ((StringBuilder)object2).append("-journal");
                        String string3 = ((StringBuilder)object2).toString();
                        object32.add(new PathWithRequiredFlags(string3, n));
                        if (Log.isLoggable(FullBackup.TAG_XML_PARSER, 2)) {
                            object2 = new StringBuilder();
                            ((StringBuilder)object2).append("...automatically generated ");
                            ((StringBuilder)object2).append(string3);
                            ((StringBuilder)object2).append(". Ignore if nonexistent.");
                            Log.v(FullBackup.TAG_XML_PARSER, ((StringBuilder)object2).toString());
                        }
                        object2 = new StringBuilder();
                        ((StringBuilder)object2).append(file.getCanonicalPath());
                        ((StringBuilder)object2).append("-wal");
                        string3 = ((StringBuilder)object2).toString();
                        object32.add(new PathWithRequiredFlags(string3, n));
                        if (Log.isLoggable(FullBackup.TAG_XML_PARSER, 2)) {
                            object2 = new StringBuilder();
                            ((StringBuilder)object2).append("...automatically generated ");
                            ((StringBuilder)object2).append(string3);
                            ((StringBuilder)object2).append(". Ignore if nonexistent.");
                            Log.v(FullBackup.TAG_XML_PARSER, ((StringBuilder)object2).toString());
                        }
                    }
                    if (!"sharedpref".equals(string2) || file.isDirectory() || file.getCanonicalPath().endsWith(".xml")) continue;
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append(file.getCanonicalPath());
                    ((StringBuilder)object2).append(".xml");
                    object2 = ((StringBuilder)object2).toString();
                    object32.add(new PathWithRequiredFlags((String)object2, n));
                    if (!Log.isLoggable(FullBackup.TAG_XML_PARSER, 2)) continue;
                    object32 = new StringBuilder();
                    ((StringBuilder)object32).append("...automatically generated ");
                    ((StringBuilder)object32).append((String)object2);
                    ((StringBuilder)object32).append(". Ignore if nonexistent.");
                    Log.v(FullBackup.TAG_XML_PARSER, ((StringBuilder)object32).toString());
                } while (true);
                if (Log.isLoggable(FullBackup.TAG_XML_PARSER, 2)) {
                    Log.v(FullBackup.TAG_XML_PARSER, "\n");
                    Log.v(FullBackup.TAG_XML_PARSER, "Xml resource parsing complete.");
                    Log.v(FullBackup.TAG_XML_PARSER, "Final tally.");
                    Log.v(FullBackup.TAG_XML_PARSER, "Includes:");
                    if (iterator22.isEmpty()) {
                        Log.v(FullBackup.TAG_XML_PARSER, "  ...nothing specified (This means the entirety of app data minus excludes)");
                    } else {
                        for (Map.Entry entry : iterator22.entrySet()) {
                            object32 = new StringBuilder();
                            ((StringBuilder)object32).append("  domain=");
                            ((StringBuilder)object32).append((String)entry.getKey());
                            Log.v(FullBackup.TAG_XML_PARSER, ((StringBuilder)object32).toString());
                            for (Object object32 : (Set)entry.getValue()) {
                                object2 = new StringBuilder();
                                ((StringBuilder)object2).append(" path: ");
                                ((StringBuilder)object2).append(((PathWithRequiredFlags)object32).getPath());
                                ((StringBuilder)object2).append(" requiredFlags: ");
                                ((StringBuilder)object2).append(((PathWithRequiredFlags)object32).getRequiredFlags());
                                Log.v(FullBackup.TAG_XML_PARSER, ((StringBuilder)object2).toString());
                            }
                        }
                    }
                    Log.v(FullBackup.TAG_XML_PARSER, "Excludes:");
                    if (iterator.isEmpty()) {
                        Log.v(FullBackup.TAG_XML_PARSER, "  ...nothing to exclude.");
                    } else {
                        iterator = iterator.iterator();
                        while (iterator.hasNext()) {
                            object = (PathWithRequiredFlags)iterator.next();
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append(" path: ");
                            stringBuilder.append(((PathWithRequiredFlags)object).getPath());
                            stringBuilder.append(" requiredFlags: ");
                            stringBuilder.append(((PathWithRequiredFlags)object).getRequiredFlags());
                            Log.v(FullBackup.TAG_XML_PARSER, stringBuilder.toString());
                        }
                    }
                    Log.v(FullBackup.TAG_XML_PARSER, "  ");
                    Log.v(FullBackup.TAG_XML_PARSER, "====================================================");
                    Log.v(FullBackup.TAG_XML_PARSER, "\n");
                }
                return;
            }
            iterator = new StringBuilder();
            ((StringBuilder)((Object)iterator)).append("Xml file didn't start with correct tag (<full-backup-content>). Found \"");
            ((StringBuilder)((Object)iterator)).append(object.getName());
            ((StringBuilder)((Object)iterator)).append("\"");
            throw new XmlPullParserException(((StringBuilder)((Object)iterator)).toString());
        }

        String tokenToDirectoryPath(String string2) {
            block17 : {
                try {
                    if (string2.equals(FullBackup.FILES_TREE_TOKEN)) {
                        return this.FILES_DIR.getCanonicalPath();
                    }
                    if (string2.equals(FullBackup.DATABASE_TREE_TOKEN)) {
                        return this.DATABASE_DIR.getCanonicalPath();
                    }
                    if (string2.equals(FullBackup.ROOT_TREE_TOKEN)) {
                        return this.ROOT_DIR.getCanonicalPath();
                    }
                    if (string2.equals(FullBackup.SHAREDPREFS_TREE_TOKEN)) {
                        return this.SHAREDPREF_DIR.getCanonicalPath();
                    }
                    if (string2.equals(FullBackup.CACHE_TREE_TOKEN)) {
                        return this.CACHE_DIR.getCanonicalPath();
                    }
                    if (string2.equals(FullBackup.NO_BACKUP_TREE_TOKEN)) {
                        return this.NOBACKUP_DIR.getCanonicalPath();
                    }
                    if (string2.equals(FullBackup.DEVICE_FILES_TREE_TOKEN)) {
                        return this.DEVICE_FILES_DIR.getCanonicalPath();
                    }
                    if (string2.equals(FullBackup.DEVICE_DATABASE_TREE_TOKEN)) {
                        return this.DEVICE_DATABASE_DIR.getCanonicalPath();
                    }
                    if (string2.equals(FullBackup.DEVICE_ROOT_TREE_TOKEN)) {
                        return this.DEVICE_ROOT_DIR.getCanonicalPath();
                    }
                    if (string2.equals(FullBackup.DEVICE_SHAREDPREFS_TREE_TOKEN)) {
                        return this.DEVICE_SHAREDPREF_DIR.getCanonicalPath();
                    }
                    if (string2.equals(FullBackup.DEVICE_CACHE_TREE_TOKEN)) {
                        return this.DEVICE_CACHE_DIR.getCanonicalPath();
                    }
                    if (string2.equals(FullBackup.DEVICE_NO_BACKUP_TREE_TOKEN)) {
                        return this.DEVICE_NOBACKUP_DIR.getCanonicalPath();
                    }
                    if (!string2.equals(FullBackup.MANAGED_EXTERNAL_TREE_TOKEN)) break block17;
                    if (this.EXTERNAL_DIR != null) {
                        return this.EXTERNAL_DIR.getCanonicalPath();
                    }
                    return null;
                }
                catch (Exception exception) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Error reading directory for domain: ");
                    stringBuilder.append(string2);
                    Log.i(FullBackup.TAG, stringBuilder.toString());
                    return null;
                }
            }
            if (string2.startsWith(FullBackup.SHARED_PREFIX)) {
                return this.sharedDomainToPath(string2);
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unrecognized domain ");
            stringBuilder.append(string2);
            Log.i(FullBackup.TAG, stringBuilder.toString());
            return null;
        }

        public static class PathWithRequiredFlags {
            private final String mPath;
            private final int mRequiredFlags;

            public PathWithRequiredFlags(String string2, int n) {
                this.mPath = string2;
                this.mRequiredFlags = n;
            }

            public String getPath() {
                return this.mPath;
            }

            public int getRequiredFlags() {
                return this.mRequiredFlags;
            }
        }

    }

}

