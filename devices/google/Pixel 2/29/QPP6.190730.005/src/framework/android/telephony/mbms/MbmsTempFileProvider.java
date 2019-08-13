/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.mbms;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.telephony.mbms.MbmsUtils;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

public class MbmsTempFileProvider
extends ContentProvider {
    public static final String TEMP_FILE_ROOT_PREF_FILE_NAME = "MbmsTempFileRootPrefs";
    public static final String TEMP_FILE_ROOT_PREF_NAME = "mbms_temp_file_root";
    private String mAuthority;
    private Context mContext;

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static File getEmbmsTempFileDir(Context var0) {
        var1_2 = var0.getSharedPreferences("MbmsTempFileRootPrefs", 0).getString("mbms_temp_file_root", null);
        if (var1_2 == null) ** GOTO lbl6
        try {
            var0 = new File((String)var1_2);
            return var0.getCanonicalFile();
lbl6: // 1 sources:
            var1_2 = new File(var0.getFilesDir(), "androidMbmsTempFileRoot");
            return var1_2.getCanonicalFile();
        }
        catch (IOException var0_1) {
            var1_2 = new StringBuilder();
            var1_2.append("Unable to canonicalize temp file root path ");
            var1_2.append(var0_1);
            throw new RuntimeException(var1_2.toString());
        }
    }

    public static File getFileForUri(Context object, String object2, Uri comparable) throws FileNotFoundException {
        if ("content".equals(((Uri)comparable).getScheme())) {
            if (Objects.equals(object2, ((Uri)comparable).getAuthority())) {
                object2 = Uri.decode(((Uri)comparable).getEncodedPath());
                try {
                    object = MbmsTempFileProvider.getEmbmsTempFileDir((Context)object).getCanonicalFile();
                    comparable = new Comparable<Uri>((File)object, (String)object2);
                    object2 = ((File)comparable).getCanonicalFile();
                    if (((File)object2).getPath().startsWith(((File)object).getPath())) {
                        return object2;
                    }
                    throw new SecurityException("Resolved path jumped beyond configured root");
                }
                catch (IOException iOException) {
                    throw new FileNotFoundException("Could not resolve paths");
                }
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Uri does not have a matching authority: ");
            ((StringBuilder)object).append((String)object2);
            ((StringBuilder)object).append(", ");
            ((StringBuilder)object).append(((Uri)comparable).getAuthority());
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
        throw new IllegalArgumentException("Uri must have scheme content");
    }

    public static Uri getUriForFile(Context object, String charSequence, File object2) {
        String string2;
        try {
            string2 = ((File)object2).getCanonicalPath();
        }
        catch (IOException iOException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Could not get canonical path for file ");
            stringBuilder.append(object2);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        object = MbmsTempFileProvider.getEmbmsTempFileDir((Context)object);
        if (MbmsUtils.isContainedIn((File)object, (File)object2)) {
            try {
                object2 = ((File)object).getCanonicalPath();
                object = ((String)object2).endsWith("/") ? string2.substring(((String)object2).length()) : string2.substring(((String)object2).length() + 1);
            }
            catch (IOException iOException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Could not get canonical path for temp file root dir ");
                stringBuilder.append(object);
                throw new RuntimeException(stringBuilder.toString());
            }
            object = Uri.encode((String)object);
            return new Uri.Builder().scheme("content").authority((String)charSequence).encodedPath((String)object).build();
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("File ");
        ((StringBuilder)charSequence).append(object2);
        ((StringBuilder)charSequence).append(" is not contained in the temp file directory, which is ");
        ((StringBuilder)charSequence).append(object);
        throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
    }

    @Override
    public void attachInfo(Context context, ProviderInfo providerInfo) {
        super.attachInfo(context, providerInfo);
        if (!providerInfo.exported) {
            if (providerInfo.grantUriPermissions) {
                this.mAuthority = providerInfo.authority;
                this.mContext = context;
                return;
            }
            throw new SecurityException("Provider must grant uri permissions");
        }
        throw new SecurityException("Provider must not be exported");
    }

    @Override
    public int delete(Uri uri, String string2, String[] arrstring) {
        throw new UnsupportedOperationException("No deleting supported");
    }

    @Override
    public String getType(Uri uri) {
        return "application/octet-stream";
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        throw new UnsupportedOperationException("No inserting supported");
    }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Override
    public ParcelFileDescriptor openFile(Uri uri, String string2) throws FileNotFoundException {
        return ParcelFileDescriptor.open(MbmsTempFileProvider.getFileForUri(this.mContext, this.mAuthority, uri), ParcelFileDescriptor.parseMode(string2));
    }

    @Override
    public Cursor query(Uri uri, String[] arrstring, String string2, String[] arrstring2, String string3) {
        throw new UnsupportedOperationException("No querying supported");
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String string2, String[] arrstring) {
        throw new UnsupportedOperationException("No updating supported");
    }
}

