/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.system.VMRuntime
 */
package android.provider;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.content.ContentInterface;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.MimeTypeFilter;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.BaseBundle;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.OperationCanceledException;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.ParcelableException;
import android.os.RemoteException;
import android.util.Log;
import com.android.internal.util.Preconditions;
import dalvik.system.VMRuntime;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public final class DocumentsContract {
    @SystemApi
    public static final String ACTION_DOCUMENT_ROOT_SETTINGS = "android.provider.action.DOCUMENT_ROOT_SETTINGS";
    public static final String ACTION_DOCUMENT_SETTINGS = "android.provider.action.DOCUMENT_SETTINGS";
    @SystemApi
    public static final String ACTION_MANAGE_DOCUMENT = "android.provider.action.MANAGE_DOCUMENT";
    public static final String EXTERNAL_STORAGE_PRIMARY_EMULATED_ROOT_ID = "primary";
    public static final String EXTERNAL_STORAGE_PROVIDER_AUTHORITY = "com.android.externalstorage.documents";
    public static final String EXTRA_ERROR = "error";
    public static final String EXTRA_EXCLUDE_SELF = "android.provider.extra.EXCLUDE_SELF";
    public static final String EXTRA_INFO = "info";
    public static final String EXTRA_INITIAL_URI = "android.provider.extra.INITIAL_URI";
    public static final String EXTRA_LOADING = "loading";
    public static final String EXTRA_OPTIONS = "options";
    public static final String EXTRA_ORIENTATION = "android.provider.extra.ORIENTATION";
    @Deprecated
    public static final String EXTRA_PACKAGE_NAME = "android.intent.extra.PACKAGE_NAME";
    public static final String EXTRA_PARENT_URI = "parentUri";
    public static final String EXTRA_PROMPT = "android.provider.extra.PROMPT";
    public static final String EXTRA_RESULT = "result";
    @SystemApi
    public static final String EXTRA_SHOW_ADVANCED = "android.provider.extra.SHOW_ADVANCED";
    public static final String EXTRA_TARGET_URI = "android.content.extra.TARGET_URI";
    public static final String EXTRA_URI = "uri";
    public static final String EXTRA_URI_PERMISSIONS = "uriPermissions";
    public static final String METADATA_EXIF = "android:documentExif";
    public static final String METADATA_TREE_COUNT = "android:metadataTreeCount";
    public static final String METADATA_TREE_SIZE = "android:metadataTreeSize";
    public static final String METADATA_TYPES = "android:documentMetadataTypes";
    public static final String METHOD_COPY_DOCUMENT = "android:copyDocument";
    @UnsupportedAppUsage
    public static final String METHOD_CREATE_DOCUMENT = "android:createDocument";
    public static final String METHOD_CREATE_WEB_LINK_INTENT = "android:createWebLinkIntent";
    public static final String METHOD_DELETE_DOCUMENT = "android:deleteDocument";
    public static final String METHOD_EJECT_ROOT = "android:ejectRoot";
    public static final String METHOD_FIND_DOCUMENT_PATH = "android:findDocumentPath";
    public static final String METHOD_GET_DOCUMENT_METADATA = "android:getDocumentMetadata";
    public static final String METHOD_IS_CHILD_DOCUMENT = "android:isChildDocument";
    public static final String METHOD_MOVE_DOCUMENT = "android:moveDocument";
    public static final String METHOD_REMOVE_DOCUMENT = "android:removeDocument";
    public static final String METHOD_RENAME_DOCUMENT = "android:renameDocument";
    public static final String PACKAGE_DOCUMENTS_UI = "com.android.documentsui";
    private static final String PARAM_MANAGE = "manage";
    private static final String PARAM_QUERY = "query";
    private static final String PATH_CHILDREN = "children";
    @UnsupportedAppUsage
    private static final String PATH_DOCUMENT = "document";
    private static final String PATH_RECENT = "recent";
    private static final String PATH_ROOT = "root";
    private static final String PATH_SEARCH = "search";
    @UnsupportedAppUsage
    private static final String PATH_TREE = "tree";
    public static final String PROVIDER_INTERFACE = "android.content.action.DOCUMENTS_PROVIDER";
    public static final String QUERY_ARG_DISPLAY_NAME = "android:query-arg-display-name";
    public static final String QUERY_ARG_EXCLUDE_MEDIA = "android:query-arg-exclude-media";
    public static final String QUERY_ARG_FILE_SIZE_OVER = "android:query-arg-file-size-over";
    public static final String QUERY_ARG_LAST_MODIFIED_AFTER = "android:query-arg-last-modified-after";
    public static final String QUERY_ARG_MIME_TYPES = "android:query-arg-mime-types";
    private static final String TAG = "DocumentsContract";

    private DocumentsContract() {
    }

    public static Uri buildBaseDocumentUri(String string2) {
        return DocumentsContract.getBaseDocumentUriBuilder(string2).build();
    }

    public static Uri buildChildDocumentsUri(String string2, String string3) {
        return new Uri.Builder().scheme("content").authority(string2).appendPath(PATH_DOCUMENT).appendPath(string3).appendPath(PATH_CHILDREN).build();
    }

    public static Uri buildChildDocumentsUriUsingTree(Uri uri, String string2) {
        return new Uri.Builder().scheme("content").authority(uri.getAuthority()).appendPath(PATH_TREE).appendPath(DocumentsContract.getTreeDocumentId(uri)).appendPath(PATH_DOCUMENT).appendPath(string2).appendPath(PATH_CHILDREN).build();
    }

    public static Uri buildDocumentUri(String string2, String string3) {
        return DocumentsContract.getBaseDocumentUriBuilder(string2).appendPath(string3).build();
    }

    public static Uri buildDocumentUriMaybeUsingTree(Uri uri, String string2) {
        if (DocumentsContract.isTreeUri(uri)) {
            return DocumentsContract.buildDocumentUriUsingTree(uri, string2);
        }
        return DocumentsContract.buildDocumentUri(uri.getAuthority(), string2);
    }

    public static Uri buildDocumentUriUsingTree(Uri uri, String string2) {
        return new Uri.Builder().scheme("content").authority(uri.getAuthority()).appendPath(PATH_TREE).appendPath(DocumentsContract.getTreeDocumentId(uri)).appendPath(PATH_DOCUMENT).appendPath(string2).build();
    }

    public static Uri buildRecentDocumentsUri(String string2, String string3) {
        return new Uri.Builder().scheme("content").authority(string2).appendPath(PATH_ROOT).appendPath(string3).appendPath(PATH_RECENT).build();
    }

    public static Uri buildRootUri(String string2, String string3) {
        return new Uri.Builder().scheme("content").authority(string2).appendPath(PATH_ROOT).appendPath(string3).build();
    }

    public static Uri buildRootsUri(String string2) {
        return new Uri.Builder().scheme("content").authority(string2).appendPath(PATH_ROOT).build();
    }

    public static Uri buildSearchDocumentsUri(String string2, String string3, String string4) {
        return new Uri.Builder().scheme("content").authority(string2).appendPath(PATH_ROOT).appendPath(string3).appendPath(PATH_SEARCH).appendQueryParameter(PARAM_QUERY, string4).build();
    }

    public static Uri buildTreeDocumentUri(String string2, String string3) {
        return new Uri.Builder().scheme("content").authority(string2).appendPath(PATH_TREE).appendPath(string3).build();
    }

    public static Uri copyDocument(ContentResolver object, Uri uri, Uri uri2) throws FileNotFoundException {
        try {
            Bundle bundle = new Bundle();
            bundle.putParcelable(EXTRA_URI, uri);
            bundle.putParcelable(EXTRA_TARGET_URI, uri2);
            object = (Uri)((ContentResolver)object).call(uri.getAuthority(), METHOD_COPY_DOCUMENT, null, bundle).getParcelable(EXTRA_URI);
            return object;
        }
        catch (Exception exception) {
            Log.w(TAG, "Failed to copy document", exception);
            DocumentsContract.rethrowIfNecessary(exception);
            return null;
        }
    }

    public static Uri createDocument(ContentResolver object, Uri uri, String string2, String string3) throws FileNotFoundException {
        try {
            Bundle bundle = new Bundle();
            bundle.putParcelable(EXTRA_URI, uri);
            bundle.putString("mime_type", string2);
            bundle.putString("_display_name", string3);
            object = (Uri)((ContentResolver)object).call(uri.getAuthority(), METHOD_CREATE_DOCUMENT, null, bundle).getParcelable(EXTRA_URI);
            return object;
        }
        catch (Exception exception) {
            Log.w(TAG, "Failed to create document", exception);
            DocumentsContract.rethrowIfNecessary(exception);
            return null;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static IntentSender createWebLinkIntent(ContentResolver object, Uri uri, Bundle bundle) throws FileNotFoundException {
        Bundle bundle2;
        try {
            bundle2 = new Bundle();
            bundle2.putParcelable(EXTRA_URI, uri);
            if (bundle == null) return (IntentSender)((ContentResolver)object).call(uri.getAuthority(), METHOD_CREATE_WEB_LINK_INTENT, null, bundle2).getParcelable(EXTRA_RESULT);
        }
        catch (Exception exception) {
            Log.w(TAG, "Failed to create a web link intent", exception);
            DocumentsContract.rethrowIfNecessary(exception);
            return null;
        }
        bundle2.putBundle(EXTRA_OPTIONS, bundle);
        return (IntentSender)((ContentResolver)object).call(uri.getAuthority(), METHOD_CREATE_WEB_LINK_INTENT, null, bundle2).getParcelable(EXTRA_RESULT);
    }

    public static boolean deleteDocument(ContentResolver contentResolver, Uri uri) throws FileNotFoundException {
        try {
            Bundle bundle = new Bundle();
            bundle.putParcelable(EXTRA_URI, uri);
            contentResolver.call(uri.getAuthority(), METHOD_DELETE_DOCUMENT, null, bundle);
            return true;
        }
        catch (Exception exception) {
            Log.w(TAG, "Failed to delete document", exception);
            DocumentsContract.rethrowIfNecessary(exception);
            return false;
        }
    }

    public static void ejectRoot(ContentResolver contentResolver, Uri uri) {
        try {
            Bundle bundle = new Bundle();
            bundle.putParcelable(EXTRA_URI, uri);
            contentResolver.call(uri.getAuthority(), METHOD_EJECT_ROOT, null, bundle);
        }
        catch (Exception exception) {
            Log.w(TAG, "Failed to eject", exception);
        }
    }

    public static Path findDocumentPath(ContentResolver object, Uri uri) throws FileNotFoundException {
        try {
            Bundle bundle = new Bundle();
            bundle.putParcelable(EXTRA_URI, uri);
            object = (Path)((ContentResolver)object).call(uri.getAuthority(), METHOD_FIND_DOCUMENT_PATH, null, bundle).getParcelable(EXTRA_RESULT);
            return object;
        }
        catch (Exception exception) {
            Log.w(TAG, "Failed to find path", exception);
            DocumentsContract.rethrowIfNecessary(exception);
            return null;
        }
    }

    private static Uri.Builder getBaseDocumentUriBuilder(String string2) {
        return new Uri.Builder().scheme("content").authority(string2).appendPath(PATH_DOCUMENT);
    }

    public static String getDocumentId(Uri uri) {
        List<String> list = uri.getPathSegments();
        if (list.size() >= 2 && PATH_DOCUMENT.equals(list.get(0))) {
            return list.get(1);
        }
        if (list.size() >= 4 && PATH_TREE.equals(list.get(0)) && PATH_DOCUMENT.equals(list.get(2))) {
            return (String)list.get(3);
        }
        list = new StringBuilder();
        ((StringBuilder)((Object)list)).append("Invalid URI: ");
        ((StringBuilder)((Object)list)).append(uri);
        throw new IllegalArgumentException(((StringBuilder)((Object)list)).toString());
    }

    public static Bundle getDocumentMetadata(ContentResolver object, Uri uri) throws FileNotFoundException {
        Preconditions.checkNotNull(object, "content can not be null");
        Preconditions.checkNotNull(uri, "documentUri can not be null");
        try {
            Bundle bundle = new Bundle();
            bundle.putParcelable(EXTRA_URI, uri);
            object = ((ContentResolver)object).call(uri.getAuthority(), METHOD_GET_DOCUMENT_METADATA, null, bundle);
            return object;
        }
        catch (Exception exception) {
            Log.w(TAG, "Failed to get document metadata");
            DocumentsContract.rethrowIfNecessary(exception);
            return null;
        }
    }

    public static Bitmap getDocumentThumbnail(ContentResolver object, Uri uri, Point object2, CancellationSignal cancellationSignal) throws FileNotFoundException {
        try {
            object = ContentResolver.loadThumbnail((ContentInterface)object, uri, Point.convert((Point)object2), cancellationSignal, 1);
            return object;
        }
        catch (Exception exception) {
            if (!(exception instanceof OperationCanceledException)) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Failed to load thumbnail for ");
                ((StringBuilder)object2).append(uri);
                ((StringBuilder)object2).append(": ");
                ((StringBuilder)object2).append(exception);
                Log.w(TAG, ((StringBuilder)object2).toString());
            }
            DocumentsContract.rethrowIfNecessary(exception);
            return null;
        }
    }

    public static String[] getHandledQueryArguments(Bundle bundle) {
        if (bundle == null) {
            return new String[0];
        }
        ArrayList<String> arrayList = new ArrayList<String>();
        if (bundle.keySet().contains(QUERY_ARG_EXCLUDE_MEDIA)) {
            arrayList.add(QUERY_ARG_EXCLUDE_MEDIA);
        }
        if (bundle.keySet().contains(QUERY_ARG_DISPLAY_NAME)) {
            arrayList.add(QUERY_ARG_DISPLAY_NAME);
        }
        if (bundle.keySet().contains(QUERY_ARG_FILE_SIZE_OVER)) {
            arrayList.add(QUERY_ARG_FILE_SIZE_OVER);
        }
        if (bundle.keySet().contains(QUERY_ARG_LAST_MODIFIED_AFTER)) {
            arrayList.add(QUERY_ARG_LAST_MODIFIED_AFTER);
        }
        if (bundle.keySet().contains(QUERY_ARG_MIME_TYPES)) {
            arrayList.add(QUERY_ARG_MIME_TYPES);
        }
        return arrayList.toArray(new String[0]);
    }

    public static String getRootId(Uri uri) {
        List<String> list = uri.getPathSegments();
        if (list.size() >= 2 && PATH_ROOT.equals(list.get(0))) {
            return (String)list.get(1);
        }
        list = new StringBuilder();
        ((StringBuilder)((Object)list)).append("Invalid URI: ");
        ((StringBuilder)((Object)list)).append(uri);
        throw new IllegalArgumentException(((StringBuilder)((Object)list)).toString());
    }

    public static String getSearchDocumentsQuery(Uri uri) {
        return uri.getQueryParameter(PARAM_QUERY);
    }

    public static String getSearchDocumentsQuery(Bundle bundle) {
        Preconditions.checkNotNull(bundle, "bundle can not be null");
        return bundle.getString(QUERY_ARG_DISPLAY_NAME, "");
    }

    public static String getTreeDocumentId(Uri uri) {
        List<String> list = uri.getPathSegments();
        if (list.size() >= 2 && PATH_TREE.equals(list.get(0))) {
            return (String)list.get(1);
        }
        list = new StringBuilder();
        ((StringBuilder)((Object)list)).append("Invalid URI: ");
        ((StringBuilder)((Object)list)).append(uri);
        throw new IllegalArgumentException(((StringBuilder)((Object)list)).toString());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static boolean isChildDocument(ContentResolver object, Uri uri, Uri uri2) throws FileNotFoundException {
        Preconditions.checkNotNull(object, "content can not be null");
        Preconditions.checkNotNull(uri, "parentDocumentUri can not be null");
        Preconditions.checkNotNull(uri2, "childDocumentUri can not be null");
        try {
            Bundle bundle = new Bundle();
            bundle.putParcelable(EXTRA_URI, uri);
            bundle.putParcelable(EXTRA_TARGET_URI, uri2);
            object = ((ContentResolver)object).call(uri.getAuthority(), METHOD_IS_CHILD_DOCUMENT, null, bundle);
            if (object == null) {
                object = new RemoteException("Failed to get a response from isChildDocument query.");
                throw object;
            }
            if (((BaseBundle)object).containsKey(EXTRA_RESULT)) {
                return ((BaseBundle)object).getBoolean(EXTRA_RESULT);
            }
            object = new RemoteException("Response did not include result field..");
            throw object;
        }
        catch (Exception exception) {
            Log.w(TAG, "Failed to create document", exception);
            DocumentsContract.rethrowIfNecessary(exception);
            return false;
        }
    }

    public static boolean isContentUri(Uri uri) {
        boolean bl = uri != null && "content".equals(uri.getScheme());
        return bl;
    }

    public static boolean isDocumentUri(Context list, Uri uri) {
        boolean bl = DocumentsContract.isContentUri(uri);
        boolean bl2 = false;
        if (bl && DocumentsContract.isDocumentsProvider((Context)((Object)list), uri.getAuthority())) {
            list = uri.getPathSegments();
            if (list.size() == 2) {
                return PATH_DOCUMENT.equals(list.get(0));
            }
            if (list.size() == 4) {
                bl = bl2;
                if (PATH_TREE.equals(list.get(0))) {
                    bl = bl2;
                    if (PATH_DOCUMENT.equals(list.get(2))) {
                        bl = true;
                    }
                }
                return bl;
            }
        }
        return false;
    }

    private static boolean isDocumentsProvider(Context object, String string2) {
        Intent intent = new Intent(PROVIDER_INTERFACE);
        object = ((Context)object).getPackageManager().queryIntentContentProviders(intent, 0).iterator();
        while (object.hasNext()) {
            if (!string2.equals(((ResolveInfo)object.next()).providerInfo.authority)) continue;
            return true;
        }
        return false;
    }

    @SystemApi
    public static boolean isManageMode(Uri uri) {
        Preconditions.checkNotNull(uri, "uri can not be null");
        return uri.getBooleanQueryParameter(PARAM_MANAGE, false);
    }

    public static boolean isRootUri(Context context, Uri uri) {
        Preconditions.checkNotNull(context, "context can not be null");
        return DocumentsContract.isRootUri(context, uri, 2);
    }

    private static boolean isRootUri(Context object, Uri uri, int n) {
        boolean bl = DocumentsContract.isContentUri(uri);
        boolean bl2 = false;
        if (bl && DocumentsContract.isDocumentsProvider((Context)object, uri.getAuthority())) {
            object = uri.getPathSegments();
            bl = bl2;
            if (object.size() == n) {
                bl = bl2;
                if (PATH_ROOT.equals(object.get(0))) {
                    bl = true;
                }
            }
            return bl;
        }
        return false;
    }

    public static boolean isRootsUri(Context context, Uri uri) {
        Preconditions.checkNotNull(context, "context can not be null");
        return DocumentsContract.isRootUri(context, uri, 1);
    }

    public static boolean isTreeUri(Uri object) {
        boolean bl;
        object = ((Uri)object).getPathSegments();
        int n = object.size();
        boolean bl2 = bl = false;
        if (n >= 2) {
            bl2 = bl;
            if (PATH_TREE.equals(object.get(0))) {
                bl2 = true;
            }
        }
        return bl2;
    }

    public static boolean matchSearchQueryArguments(Bundle arrstring, String string2, String string3, long l, long l2) {
        if (arrstring == null) {
            return true;
        }
        String string4 = arrstring.getString(QUERY_ARG_DISPLAY_NAME, "");
        if (!string4.isEmpty() && !string2.toLowerCase().contains(string4.toLowerCase())) {
            return false;
        }
        long l3 = arrstring.getLong(QUERY_ARG_FILE_SIZE_OVER, -1L);
        if (l3 != -1L && l2 < l3) {
            return false;
        }
        l2 = arrstring.getLong(QUERY_ARG_LAST_MODIFIED_AFTER, -1L);
        if (l2 != -1L && l < l2) {
            return false;
        }
        if ((arrstring = arrstring.getStringArray(QUERY_ARG_MIME_TYPES)) != null && arrstring.length > 0) {
            string2 = Intent.normalizeMimeType(string3);
            int n = arrstring.length;
            for (int i = 0; i < n; ++i) {
                if (!MimeTypeFilter.matches(string2, Intent.normalizeMimeType(arrstring[i]))) continue;
                return true;
            }
            return false;
        }
        return true;
    }

    public static Uri moveDocument(ContentResolver object, Uri uri, Uri uri2, Uri uri3) throws FileNotFoundException {
        try {
            Bundle bundle = new Bundle();
            bundle.putParcelable(EXTRA_URI, uri);
            bundle.putParcelable(EXTRA_PARENT_URI, uri2);
            bundle.putParcelable(EXTRA_TARGET_URI, uri3);
            object = (Uri)((ContentResolver)object).call(uri.getAuthority(), METHOD_MOVE_DOCUMENT, null, bundle).getParcelable(EXTRA_URI);
            return object;
        }
        catch (Exception exception) {
            Log.w(TAG, "Failed to move document", exception);
            DocumentsContract.rethrowIfNecessary(exception);
            return null;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static AssetFileDescriptor openImageThumbnail(File object) throws FileNotFoundException {
        ParcelFileDescriptor parcelFileDescriptor = ParcelFileDescriptor.open((File)object, 268435456);
        try {
            ExifInterface exifInterface = new ExifInterface(((File)object).getAbsolutePath());
            long[] arrl = exifInterface.getThumbnailRange();
            if (arrl == null) return new AssetFileDescriptor(parcelFileDescriptor, 0L, -1L, null);
            int n = exifInterface.getAttributeInt("Orientation", -1);
            if (n != 3) {
                if (n != 6) {
                    if (n != 8) {
                        object = null;
                        return new AssetFileDescriptor(parcelFileDescriptor, arrl[0], arrl[1], (Bundle)object);
                    } else {
                        object = new Bundle(1);
                        ((BaseBundle)object).putInt(EXTRA_ORIENTATION, 270);
                    }
                    return new AssetFileDescriptor(parcelFileDescriptor, arrl[0], arrl[1], (Bundle)object);
                } else {
                    object = new Bundle(1);
                    ((BaseBundle)object).putInt(EXTRA_ORIENTATION, 90);
                }
                return new AssetFileDescriptor(parcelFileDescriptor, arrl[0], arrl[1], (Bundle)object);
            } else {
                object = new Bundle(1);
                ((BaseBundle)object).putInt(EXTRA_ORIENTATION, 180);
            }
            return new AssetFileDescriptor(parcelFileDescriptor, arrl[0], arrl[1], (Bundle)object);
        }
        catch (IOException iOException) {
            // empty catch block
        }
        return new AssetFileDescriptor(parcelFileDescriptor, 0L, -1L, null);
    }

    public static boolean removeDocument(ContentResolver contentResolver, Uri uri, Uri uri2) throws FileNotFoundException {
        try {
            Bundle bundle = new Bundle();
            bundle.putParcelable(EXTRA_URI, uri);
            bundle.putParcelable(EXTRA_PARENT_URI, uri2);
            contentResolver.call(uri.getAuthority(), METHOD_REMOVE_DOCUMENT, null, bundle);
            return true;
        }
        catch (Exception exception) {
            Log.w(TAG, "Failed to remove document", exception);
            DocumentsContract.rethrowIfNecessary(exception);
            return false;
        }
    }

    public static Uri renameDocument(ContentResolver object, Uri uri, String string2) throws FileNotFoundException {
        try {
            Bundle bundle = new Bundle();
            bundle.putParcelable(EXTRA_URI, uri);
            bundle.putString("_display_name", string2);
            object = (Uri)((ContentResolver)object).call(uri.getAuthority(), METHOD_RENAME_DOCUMENT, null, bundle).getParcelable(EXTRA_URI);
            if (object == null) {
                object = uri;
            }
            return object;
        }
        catch (Exception exception) {
            Log.w(TAG, "Failed to rename document", exception);
            DocumentsContract.rethrowIfNecessary(exception);
            return null;
        }
    }

    private static void rethrowIfNecessary(Exception exception) throws FileNotFoundException {
        if (VMRuntime.getRuntime().getTargetSdkVersion() >= 26) {
            if (exception instanceof ParcelableException) {
                ((ParcelableException)exception).maybeRethrow(FileNotFoundException.class);
            } else if (exception instanceof RemoteException) {
                ((RemoteException)exception).rethrowAsRuntimeException();
            } else if (exception instanceof RuntimeException) {
                throw (RuntimeException)exception;
            }
        }
    }

    @SystemApi
    public static Uri setManageMode(Uri uri) {
        Preconditions.checkNotNull(uri, "uri can not be null");
        return uri.buildUpon().appendQueryParameter(PARAM_MANAGE, "true").build();
    }

    public static final class Document {
        public static final String COLUMN_DISPLAY_NAME = "_display_name";
        public static final String COLUMN_DOCUMENT_ID = "document_id";
        public static final String COLUMN_FLAGS = "flags";
        public static final String COLUMN_ICON = "icon";
        public static final String COLUMN_LAST_MODIFIED = "last_modified";
        public static final String COLUMN_MIME_TYPE = "mime_type";
        public static final String COLUMN_SIZE = "_size";
        public static final String COLUMN_SUMMARY = "summary";
        public static final int FLAG_DIR_PREFERS_GRID = 16;
        public static final int FLAG_DIR_PREFERS_LAST_MODIFIED = 32;
        public static final int FLAG_DIR_SUPPORTS_CREATE = 8;
        public static final int FLAG_PARTIAL = 8192;
        public static final int FLAG_SUPPORTS_COPY = 128;
        public static final int FLAG_SUPPORTS_DELETE = 4;
        public static final int FLAG_SUPPORTS_METADATA = 16384;
        public static final int FLAG_SUPPORTS_MOVE = 256;
        public static final int FLAG_SUPPORTS_REMOVE = 1024;
        public static final int FLAG_SUPPORTS_RENAME = 64;
        public static final int FLAG_SUPPORTS_SETTINGS = 2048;
        public static final int FLAG_SUPPORTS_THUMBNAIL = 1;
        public static final int FLAG_SUPPORTS_WRITE = 2;
        public static final int FLAG_VIRTUAL_DOCUMENT = 512;
        public static final int FLAG_WEB_LINKABLE = 4096;
        public static final String MIME_TYPE_DIR = "vnd.android.document/directory";

        private Document() {
        }
    }

    public static final class Path
    implements Parcelable {
        public static final Parcelable.Creator<Path> CREATOR = new Parcelable.Creator<Path>(){

            @Override
            public Path createFromParcel(Parcel parcel) {
                return new Path(parcel.readString(), parcel.createStringArrayList());
            }

            public Path[] newArray(int n) {
                return new Path[n];
            }
        };
        private final List<String> mPath;
        private final String mRootId;

        public Path(String string2, List<String> list) {
            Preconditions.checkCollectionNotEmpty(list, "path");
            Preconditions.checkCollectionElementsNotNull(list, "path");
            this.mRootId = string2;
            this.mPath = list;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public boolean equals(Object object) {
            boolean bl = true;
            if (this == object) {
                return true;
            }
            if (object != null && object instanceof Path) {
                object = (Path)object;
                if (!Objects.equals(this.mRootId, ((Path)object).mRootId) || !Objects.equals(this.mPath, ((Path)object).mPath)) {
                    bl = false;
                }
                return bl;
            }
            return false;
        }

        public List<String> getPath() {
            return this.mPath;
        }

        public String getRootId() {
            return this.mRootId;
        }

        public int hashCode() {
            return Objects.hash(this.mRootId, this.mPath);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("DocumentsContract.Path{");
            stringBuilder.append("rootId=");
            stringBuilder.append(this.mRootId);
            stringBuilder.append(", path=");
            stringBuilder.append(this.mPath);
            stringBuilder.append("}");
            return stringBuilder.toString();
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeString(this.mRootId);
            parcel.writeStringList(this.mPath);
        }

    }

    public static final class Root {
        public static final String COLUMN_AVAILABLE_BYTES = "available_bytes";
        public static final String COLUMN_CAPACITY_BYTES = "capacity_bytes";
        public static final String COLUMN_DOCUMENT_ID = "document_id";
        public static final String COLUMN_FLAGS = "flags";
        public static final String COLUMN_ICON = "icon";
        public static final String COLUMN_MIME_TYPES = "mime_types";
        public static final String COLUMN_QUERY_ARGS = "query_args";
        public static final String COLUMN_ROOT_ID = "root_id";
        public static final String COLUMN_SUMMARY = "summary";
        public static final String COLUMN_TITLE = "title";
        @SystemApi
        public static final int FLAG_ADVANCED = 65536;
        public static final int FLAG_EMPTY = 64;
        @SystemApi
        public static final int FLAG_HAS_SETTINGS = 131072;
        public static final int FLAG_LOCAL_ONLY = 2;
        @SystemApi
        public static final int FLAG_REMOVABLE_SD = 262144;
        @SystemApi
        public static final int FLAG_REMOVABLE_USB = 524288;
        public static final int FLAG_SUPPORTS_CREATE = 1;
        public static final int FLAG_SUPPORTS_EJECT = 32;
        public static final int FLAG_SUPPORTS_IS_CHILD = 16;
        public static final int FLAG_SUPPORTS_RECENTS = 4;
        public static final int FLAG_SUPPORTS_SEARCH = 8;
        public static final String MIME_TYPE_ITEM = "vnd.android.document/root";

        private Root() {
        }
    }

}

