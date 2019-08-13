/*
 * Decompiled with CFR 0.145.
 */
package android.provider;

import android.annotation.SystemApi;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

@SystemApi
public abstract class SearchIndexablesProvider
extends ContentProvider {
    private static final int MATCH_NON_INDEXABLE_KEYS_CODE = 3;
    private static final int MATCH_RAW_CODE = 2;
    private static final int MATCH_RES_CODE = 1;
    private static final int MATCH_SITE_MAP_PAIRS_CODE = 4;
    private static final int MATCH_SLICE_URI_PAIRS_CODE = 5;
    private static final String TAG = "IndexablesProvider";
    private String mAuthority;
    private UriMatcher mMatcher;

    @Override
    public void attachInfo(Context context, ProviderInfo providerInfo) {
        this.mAuthority = providerInfo.authority;
        this.mMatcher = new UriMatcher(-1);
        this.mMatcher.addURI(this.mAuthority, "settings/indexables_xml_res", 1);
        this.mMatcher.addURI(this.mAuthority, "settings/indexables_raw", 2);
        this.mMatcher.addURI(this.mAuthority, "settings/non_indexables_key", 3);
        this.mMatcher.addURI(this.mAuthority, "settings/site_map_pairs", 4);
        this.mMatcher.addURI(this.mAuthority, "settings/slice_uri_pairs", 5);
        if (providerInfo.exported) {
            if (providerInfo.grantUriPermissions) {
                if ("android.permission.READ_SEARCH_INDEXABLES".equals(providerInfo.readPermission)) {
                    super.attachInfo(context, providerInfo);
                    return;
                }
                throw new SecurityException("Provider must be protected by READ_SEARCH_INDEXABLES");
            }
            throw new SecurityException("Provider must grantUriPermissions");
        }
        throw new SecurityException("Provider must be exported");
    }

    @Override
    public final int delete(Uri uri, String string2, String[] arrstring) {
        throw new UnsupportedOperationException("Delete not supported");
    }

    @Override
    public String getType(Uri uri) {
        int n = this.mMatcher.match(uri);
        if (n != 1) {
            if (n != 2) {
                if (n == 3) {
                    return "vnd.android.cursor.dir/non_indexables_key";
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unknown URI ");
                stringBuilder.append(uri);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            return "vnd.android.cursor.dir/indexables_raw";
        }
        return "vnd.android.cursor.dir/indexables_xml_res";
    }

    @Override
    public final Uri insert(Uri uri, ContentValues contentValues) {
        throw new UnsupportedOperationException("Insert not supported");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public Cursor query(Uri object, String[] object2, String charSequence, String[] arrstring, String string2) {
        try {
            int n = this.mMatcher.match((Uri)object);
            if (n == 1) {
                return this.queryXmlResources(null);
            }
            if (n == 2) {
                return this.queryRawData(null);
            }
            if (n == 3) {
                return this.queryNonIndexableKeys(null);
            }
            if (n == 4) {
                return this.querySiteMapPairs();
            }
            if (n == 5) {
                return this.querySliceUriPairs();
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unknown Uri ");
            stringBuilder.append(object);
            UnsupportedOperationException unsupportedOperationException = new UnsupportedOperationException(stringBuilder.toString());
            throw unsupportedOperationException;
        }
        catch (Exception exception) {
            Log.e(TAG, "Provider querying exception:", exception);
            return null;
        }
        catch (UnsupportedOperationException unsupportedOperationException) {
            throw unsupportedOperationException;
        }
    }

    public abstract Cursor queryNonIndexableKeys(String[] var1);

    public abstract Cursor queryRawData(String[] var1);

    public Cursor querySiteMapPairs() {
        return null;
    }

    public Cursor querySliceUriPairs() {
        return null;
    }

    public abstract Cursor queryXmlResources(String[] var1);

    @Override
    public final int update(Uri uri, ContentValues contentValues, String string2, String[] arrstring) {
        throw new UnsupportedOperationException("Update not supported");
    }
}

