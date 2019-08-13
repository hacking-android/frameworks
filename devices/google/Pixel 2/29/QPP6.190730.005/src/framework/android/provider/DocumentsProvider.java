/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.io.IoUtils
 */
package android.provider;

import android.content.ClipDescription;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.IntentSender;
import android.content.MimeTypeFilter;
import android.content.UriMatcher;
import android.content.pm.ProviderInfo;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Point;
import android.net.Uri;
import android.os.BaseBundle;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.IBinder;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.ParcelableException;
import android.provider.DocumentsContract;
import android.util.Log;
import com.android.internal.util.Preconditions;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import libcore.io.IoUtils;

public abstract class DocumentsProvider
extends ContentProvider {
    private static final int MATCH_CHILDREN = 6;
    private static final int MATCH_CHILDREN_TREE = 8;
    private static final int MATCH_DOCUMENT = 5;
    private static final int MATCH_DOCUMENT_TREE = 7;
    private static final int MATCH_RECENT = 3;
    private static final int MATCH_ROOT = 2;
    private static final int MATCH_ROOTS = 1;
    private static final int MATCH_SEARCH = 4;
    private static final String TAG = "DocumentsProvider";
    private String mAuthority;
    private UriMatcher mMatcher;

    private Bundle callUnchecked(String object, String object2, Bundle object3) throws FileNotFoundException {
        Object object4;
        block19 : {
            block29 : {
                Bundle bundle;
                block21 : {
                    Parcelable parcelable;
                    boolean bl;
                    block28 : {
                        block27 : {
                            Context context;
                            block26 : {
                                block25 : {
                                    block24 : {
                                        block23 : {
                                            block22 : {
                                                block20 : {
                                                    context = this.getContext();
                                                    bundle = new Bundle();
                                                    if ("android:ejectRoot".equals(object)) {
                                                        object = (Uri)((Bundle)object3).getParcelable("uri");
                                                        this.enforceWritePermissionInner((Uri)object, this.getCallingPackage(), null);
                                                        this.ejectRoot(DocumentsContract.getRootId((Uri)object));
                                                        return bundle;
                                                    }
                                                    parcelable = (Uri)((Bundle)object3).getParcelable("uri");
                                                    object4 = ((Uri)parcelable).getAuthority();
                                                    object2 = DocumentsContract.getDocumentId((Uri)parcelable);
                                                    if (!this.mAuthority.equals(object4)) break block19;
                                                    this.enforceTree((Uri)parcelable);
                                                    boolean bl2 = "android:isChildDocument".equals(object);
                                                    bl = false;
                                                    if (!bl2) break block20;
                                                    this.enforceReadPermissionInner((Uri)parcelable, this.getCallingPackage(), null);
                                                    object3 = (Uri)((Bundle)object3).getParcelable("android.content.extra.TARGET_URI");
                                                    object = ((Uri)object3).getAuthority();
                                                    object3 = DocumentsContract.getDocumentId((Uri)object3);
                                                    if (this.mAuthority.equals(object) && this.isChildDocument((String)object2, (String)object3)) {
                                                        bl = true;
                                                    }
                                                    bundle.putBoolean("result", bl);
                                                    break block21;
                                                }
                                                if (!"android:createDocument".equals(object)) break block22;
                                                this.enforceWritePermissionInner((Uri)parcelable, this.getCallingPackage(), null);
                                                bundle.putParcelable("uri", DocumentsContract.buildDocumentUriMaybeUsingTree((Uri)parcelable, this.createDocument((String)object2, ((BaseBundle)object3).getString("mime_type"), ((BaseBundle)object3).getString("_display_name"))));
                                                break block21;
                                            }
                                            if (!"android:createWebLinkIntent".equals(object)) break block23;
                                            this.enforceWritePermissionInner((Uri)parcelable, this.getCallingPackage(), null);
                                            bundle.putParcelable("result", this.createWebLinkIntent((String)object2, ((Bundle)object3).getBundle("options")));
                                            break block21;
                                        }
                                        if (!"android:renameDocument".equals(object)) break block24;
                                        this.enforceWritePermissionInner((Uri)parcelable, this.getCallingPackage(), null);
                                        object = this.renameDocument((String)object2, ((BaseBundle)object3).getString("_display_name"));
                                        if (object != null) {
                                            if (!DocumentsContract.isTreeUri((Uri)(object = DocumentsContract.buildDocumentUriMaybeUsingTree((Uri)parcelable, (String)object)))) {
                                                int n = DocumentsProvider.getCallingOrSelfUriPermissionModeFlags(context, (Uri)parcelable);
                                                context.grantUriPermission(this.getCallingPackage(), (Uri)object, n);
                                            }
                                            bundle.putParcelable("uri", (Parcelable)object);
                                            this.revokeDocumentPermission((String)object2);
                                        }
                                        break block21;
                                    }
                                    if (!"android:deleteDocument".equals(object)) break block25;
                                    this.enforceWritePermissionInner((Uri)parcelable, this.getCallingPackage(), null);
                                    this.deleteDocument((String)object2);
                                    this.revokeDocumentPermission((String)object2);
                                    break block21;
                                }
                                if (!"android:copyDocument".equals(object)) break block26;
                                object3 = (Uri)((Bundle)object3).getParcelable("android.content.extra.TARGET_URI");
                                object = DocumentsContract.getDocumentId((Uri)object3);
                                this.enforceReadPermissionInner((Uri)parcelable, this.getCallingPackage(), null);
                                this.enforceWritePermissionInner((Uri)object3, this.getCallingPackage(), null);
                                object = this.copyDocument((String)object2, (String)object);
                                if (object != null) {
                                    if (!DocumentsContract.isTreeUri((Uri)(object = DocumentsContract.buildDocumentUriMaybeUsingTree((Uri)parcelable, (String)object)))) {
                                        int n = DocumentsProvider.getCallingOrSelfUriPermissionModeFlags(context, (Uri)parcelable);
                                        context.grantUriPermission(this.getCallingPackage(), (Uri)object, n);
                                    }
                                    bundle.putParcelable("uri", (Parcelable)object);
                                }
                                break block21;
                            }
                            if (!"android:moveDocument".equals(object)) break block27;
                            object4 = (Uri)((Bundle)object3).getParcelable("parentUri");
                            object = DocumentsContract.getDocumentId((Uri)object4);
                            object3 = (Uri)((Bundle)object3).getParcelable("android.content.extra.TARGET_URI");
                            String string2 = DocumentsContract.getDocumentId((Uri)object3);
                            this.enforceWritePermissionInner((Uri)parcelable, this.getCallingPackage(), null);
                            this.enforceReadPermissionInner((Uri)object4, this.getCallingPackage(), null);
                            this.enforceWritePermissionInner((Uri)object3, this.getCallingPackage(), null);
                            object = this.moveDocument((String)object2, (String)object, string2);
                            if (object != null) {
                                if (!DocumentsContract.isTreeUri((Uri)(object = DocumentsContract.buildDocumentUriMaybeUsingTree((Uri)parcelable, (String)object)))) {
                                    int n = DocumentsProvider.getCallingOrSelfUriPermissionModeFlags(context, (Uri)parcelable);
                                    context.grantUriPermission(this.getCallingPackage(), (Uri)object, n);
                                }
                                bundle.putParcelable("uri", (Parcelable)object);
                            }
                            break block21;
                        }
                        if (!"android:removeDocument".equals(object)) break block28;
                        object = (Uri)((Bundle)object3).getParcelable("parentUri");
                        object3 = DocumentsContract.getDocumentId((Uri)object);
                        this.enforceReadPermissionInner((Uri)object, this.getCallingPackage(), null);
                        this.enforceWritePermissionInner((Uri)parcelable, this.getCallingPackage(), null);
                        this.removeDocument((String)object2, (String)object3);
                        break block21;
                    }
                    if (!"android:findDocumentPath".equals(object)) break block29;
                    bl = DocumentsContract.isTreeUri((Uri)parcelable);
                    if (bl) {
                        this.enforceReadPermissionInner((Uri)parcelable, this.getCallingPackage(), null);
                    } else {
                        this.getContext().enforceCallingPermission("android.permission.MANAGE_DOCUMENTS", null);
                    }
                    object3 = bl ? DocumentsContract.getTreeDocumentId((Uri)parcelable) : null;
                    parcelable = this.findDocumentPath((String)object3, (String)object2);
                    object2 = parcelable;
                    if (bl) {
                        object = parcelable;
                        if (!Objects.equals(((DocumentsContract.Path)parcelable).getPath().get(0), object3)) {
                            object = new StringBuilder();
                            ((StringBuilder)object).append("Provider doesn't return path from the tree root. Expected: ");
                            ((StringBuilder)object).append((String)object3);
                            ((StringBuilder)object).append(" found: ");
                            ((StringBuilder)object).append(((DocumentsContract.Path)parcelable).getPath().get(0));
                            Log.wtf(TAG, ((StringBuilder)object).toString());
                            object = new LinkedList<String>(((DocumentsContract.Path)parcelable).getPath());
                            while (((LinkedList)object).size() > 1 && !Objects.equals(((LinkedList)object).getFirst(), object3)) {
                                ((LinkedList)object).removeFirst();
                            }
                            object = new DocumentsContract.Path(null, (List<String>)object);
                        }
                        object2 = object;
                        if (((DocumentsContract.Path)object).getRootId() != null) {
                            object2 = new StringBuilder();
                            ((StringBuilder)object2).append("Provider returns root id :");
                            ((StringBuilder)object2).append(((DocumentsContract.Path)object).getRootId());
                            ((StringBuilder)object2).append(" unexpectedly. Erase root id.");
                            Log.wtf(TAG, ((StringBuilder)object2).toString());
                            object2 = new DocumentsContract.Path(null, ((DocumentsContract.Path)object).getPath());
                        }
                    }
                    bundle.putParcelable("result", (Parcelable)object2);
                }
                return bundle;
            }
            if ("android:getDocumentMetadata".equals(object)) {
                return this.getDocumentMetadata((String)object2);
            }
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Method not supported ");
            ((StringBuilder)object2).append((String)object);
            throw new UnsupportedOperationException(((StringBuilder)object2).toString());
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Requested authority ");
        ((StringBuilder)object).append((String)object4);
        ((StringBuilder)object).append(" doesn't match provider ");
        ((StringBuilder)object).append(this.mAuthority);
        throw new SecurityException(((StringBuilder)object).toString());
    }

    private void enforceTree(Uri object) {
        if (DocumentsContract.isTreeUri((Uri)object)) {
            String string2 = DocumentsContract.getTreeDocumentId((Uri)object);
            if (Objects.equals(string2, object = DocumentsContract.getDocumentId((Uri)object))) {
                return;
            }
            if (!this.isChildDocument(string2, (String)object)) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Document ");
                stringBuilder.append((String)object);
                stringBuilder.append(" is not a descendant of ");
                stringBuilder.append(string2);
                throw new SecurityException(stringBuilder.toString());
            }
        }
    }

    private static int getCallingOrSelfUriPermissionModeFlags(Context context, Uri uri) {
        int n = 0;
        if (context.checkCallingOrSelfUriPermission(uri, 1) == 0) {
            n = false | true;
        }
        int n2 = n;
        if (context.checkCallingOrSelfUriPermission(uri, 2) == 0) {
            n2 = n | 2;
        }
        n = n2;
        if (context.checkCallingOrSelfUriPermission(uri, 65) == 0) {
            n = n2 | 64;
        }
        return n;
    }

    private static String getSortClause(Bundle bundle) {
        String string2;
        if (bundle == null) {
            bundle = Bundle.EMPTY;
        }
        String string3 = string2 = bundle.getString("android:query-arg-sql-sort-order");
        if (string2 == null) {
            string3 = string2;
            if (bundle.containsKey("android:query-arg-sort-columns")) {
                string3 = ContentResolver.createSqlSortClause(bundle);
            }
        }
        return string3;
    }

    private final AssetFileDescriptor openTypedAssetFileImpl(Uri uri, String string2, Bundle bundle, CancellationSignal cancellationSignal) throws FileNotFoundException {
        this.enforceTree(uri);
        String string3 = DocumentsContract.getDocumentId(uri);
        if (bundle != null && bundle.containsKey("android.content.extra.SIZE")) {
            return this.openDocumentThumbnail(string3, (Point)bundle.getParcelable("android.content.extra.SIZE"), cancellationSignal);
        }
        if ("*/*".equals(string2)) {
            return this.openAssetFile(uri, "r");
        }
        String string4 = this.getType(uri);
        if (string4 != null && ClipDescription.compareMimeTypes(string4, string2)) {
            return this.openAssetFile(uri, "r");
        }
        return this.openTypedDocument(string3, string2, bundle, cancellationSignal);
    }

    private void registerAuthority(String string2) {
        this.mAuthority = string2;
        this.mMatcher = new UriMatcher(-1);
        this.mMatcher.addURI(this.mAuthority, "root", 1);
        this.mMatcher.addURI(this.mAuthority, "root/*", 2);
        this.mMatcher.addURI(this.mAuthority, "root/*/recent", 3);
        this.mMatcher.addURI(this.mAuthority, "root/*/search", 4);
        this.mMatcher.addURI(this.mAuthority, "document/*", 5);
        this.mMatcher.addURI(this.mAuthority, "document/*/children", 6);
        this.mMatcher.addURI(this.mAuthority, "tree/*/document/*", 7);
        this.mMatcher.addURI(this.mAuthority, "tree/*/document/*/children", 8);
    }

    @Override
    public void attachInfo(Context context, ProviderInfo providerInfo) {
        this.registerAuthority(providerInfo.authority);
        if (providerInfo.exported) {
            if (providerInfo.grantUriPermissions) {
                if ("android.permission.MANAGE_DOCUMENTS".equals(providerInfo.readPermission) && "android.permission.MANAGE_DOCUMENTS".equals(providerInfo.writePermission)) {
                    super.attachInfo(context, providerInfo);
                    return;
                }
                throw new SecurityException("Provider must be protected by MANAGE_DOCUMENTS");
            }
            throw new SecurityException("Provider must grantUriPermissions");
        }
        throw new SecurityException("Provider must be exported");
    }

    @Override
    public void attachInfoForTesting(Context context, ProviderInfo providerInfo) {
        this.registerAuthority(providerInfo.authority);
        super.attachInfoForTesting(context, providerInfo);
    }

    @Override
    public Bundle call(String object, String string2, Bundle bundle) {
        if (!((String)object).startsWith("android:")) {
            return super.call((String)object, string2, bundle);
        }
        try {
            object = this.callUnchecked((String)object, string2, bundle);
            return object;
        }
        catch (FileNotFoundException fileNotFoundException) {
            throw new ParcelableException(fileNotFoundException);
        }
    }

    @Override
    public Uri canonicalize(Uri uri) {
        Context context = this.getContext();
        if (this.mMatcher.match(uri) != 7) {
            return null;
        }
        this.enforceTree(uri);
        Uri uri2 = DocumentsContract.buildDocumentUri(uri.getAuthority(), DocumentsContract.getDocumentId(uri));
        int n = DocumentsProvider.getCallingOrSelfUriPermissionModeFlags(context, uri);
        context.grantUriPermission(this.getCallingPackage(), uri2, n);
        return uri2;
    }

    public String copyDocument(String string2, String string3) throws FileNotFoundException {
        throw new UnsupportedOperationException("Copy not supported");
    }

    public String createDocument(String string2, String string3, String string4) throws FileNotFoundException {
        throw new UnsupportedOperationException("Create not supported");
    }

    public IntentSender createWebLinkIntent(String string2, Bundle bundle) throws FileNotFoundException {
        throw new UnsupportedOperationException("createWebLink is not supported.");
    }

    @Override
    public final int delete(Uri uri, String string2, String[] arrstring) {
        throw new UnsupportedOperationException("Delete not supported");
    }

    public void deleteDocument(String string2) throws FileNotFoundException {
        throw new UnsupportedOperationException("Delete not supported");
    }

    public void ejectRoot(String string2) {
        throw new UnsupportedOperationException("Eject not supported");
    }

    public DocumentsContract.Path findDocumentPath(String string2, String string3) throws FileNotFoundException {
        throw new UnsupportedOperationException("findDocumentPath not supported.");
    }

    public Bundle getDocumentMetadata(String string2) throws FileNotFoundException {
        throw new UnsupportedOperationException("Metadata not supported");
    }

    public String[] getDocumentStreamTypes(String object, String string2) {
        block7 : {
            Object object2 = null;
            Object object3 = null;
            object3 = object = this.queryDocument((String)object, null);
            object2 = object;
            if (!object.moveToFirst()) break block7;
            object3 = object;
            object2 = object;
            String string3 = object.getString(object.getColumnIndexOrThrow("mime_type"));
            object3 = object;
            object2 = object;
            if ((512L & object.getLong(object.getColumnIndexOrThrow("flags"))) != 0L || string3 == null) break block7;
            object3 = object;
            object2 = object;
            try {
                if (!MimeTypeFilter.matches(string3, string2)) break block7;
            }
            catch (Throwable throwable) {
                IoUtils.closeQuietly(object3);
                throw throwable;
            }
            catch (FileNotFoundException fileNotFoundException) {
                IoUtils.closeQuietly(object2);
                return null;
            }
            IoUtils.closeQuietly((AutoCloseable)object);
            return new String[]{string3};
        }
        IoUtils.closeQuietly((AutoCloseable)object);
        return null;
    }

    public String getDocumentType(String object) throws FileNotFoundException {
        object = this.queryDocument((String)object, null);
        try {
            if (object.moveToFirst()) {
                String string2 = object.getString(object.getColumnIndexOrThrow("mime_type"));
                return string2;
            }
            return null;
        }
        finally {
            IoUtils.closeQuietly((AutoCloseable)object);
        }
    }

    @Override
    public String[] getStreamTypes(Uri uri, String string2) {
        this.enforceTree(uri);
        return this.getDocumentStreamTypes(DocumentsContract.getDocumentId(uri), string2);
    }

    @Override
    public final String getType(Uri uri) {
        block3 : {
            block4 : {
                try {
                    int n = this.mMatcher.match(uri);
                    if (n == 2) break block3;
                    if (n == 5 || n == 7) break block4;
                    return null;
                }
                catch (FileNotFoundException fileNotFoundException) {
                    Log.w(TAG, "Failed during getType", fileNotFoundException);
                    return null;
                }
            }
            this.enforceTree(uri);
            return this.getDocumentType(DocumentsContract.getDocumentId(uri));
        }
        return "vnd.android.document/root";
    }

    @Override
    public final Uri insert(Uri uri, ContentValues contentValues) {
        throw new UnsupportedOperationException("Insert not supported");
    }

    public boolean isChildDocument(String string2, String string3) {
        return false;
    }

    public String moveDocument(String string2, String string3, String string4) throws FileNotFoundException {
        throw new UnsupportedOperationException("Move not supported");
    }

    @Override
    public final AssetFileDescriptor openAssetFile(Uri parcelable, String object) throws FileNotFoundException {
        this.enforceTree((Uri)parcelable);
        String string2 = DocumentsContract.getDocumentId(parcelable);
        parcelable = null;
        object = this.openDocument(string2, (String)object, null);
        if (object != null) {
            parcelable = new AssetFileDescriptor((ParcelFileDescriptor)object, 0L, -1L);
        }
        return parcelable;
    }

    @Override
    public final AssetFileDescriptor openAssetFile(Uri parcelable, String string2, CancellationSignal cancellationSignal) throws FileNotFoundException {
        this.enforceTree((Uri)parcelable);
        parcelable = this.openDocument(DocumentsContract.getDocumentId(parcelable), string2, cancellationSignal);
        parcelable = parcelable != null ? new AssetFileDescriptor((ParcelFileDescriptor)parcelable, 0L, -1L) : null;
        return parcelable;
    }

    public abstract ParcelFileDescriptor openDocument(String var1, String var2, CancellationSignal var3) throws FileNotFoundException;

    public AssetFileDescriptor openDocumentThumbnail(String string2, Point point, CancellationSignal cancellationSignal) throws FileNotFoundException {
        throw new UnsupportedOperationException("Thumbnails not supported");
    }

    @Override
    public final ParcelFileDescriptor openFile(Uri uri, String string2) throws FileNotFoundException {
        this.enforceTree(uri);
        return this.openDocument(DocumentsContract.getDocumentId(uri), string2, null);
    }

    @Override
    public final ParcelFileDescriptor openFile(Uri uri, String string2, CancellationSignal cancellationSignal) throws FileNotFoundException {
        this.enforceTree(uri);
        return this.openDocument(DocumentsContract.getDocumentId(uri), string2, cancellationSignal);
    }

    @Override
    public final AssetFileDescriptor openTypedAssetFile(Uri uri, String string2, Bundle bundle) throws FileNotFoundException {
        return this.openTypedAssetFileImpl(uri, string2, bundle, null);
    }

    @Override
    public final AssetFileDescriptor openTypedAssetFile(Uri uri, String string2, Bundle bundle, CancellationSignal cancellationSignal) throws FileNotFoundException {
        return this.openTypedAssetFileImpl(uri, string2, bundle, cancellationSignal);
    }

    public AssetFileDescriptor openTypedDocument(String string2, String string3, Bundle bundle, CancellationSignal cancellationSignal) throws FileNotFoundException {
        throw new FileNotFoundException("The requested MIME type is not supported.");
    }

    @Override
    public final Cursor query(Uri uri, String[] arrstring, Bundle object, CancellationSignal cancellationSignal) {
        try {
            switch (this.mMatcher.match(uri)) {
                default: {
                    break;
                }
                case 6: 
                case 8: {
                    this.enforceTree(uri);
                    if (DocumentsContract.isManageMode(uri)) {
                        return this.queryChildDocumentsForManage(DocumentsContract.getDocumentId(uri), arrstring, DocumentsProvider.getSortClause((Bundle)object));
                    }
                    return this.queryChildDocuments(DocumentsContract.getDocumentId(uri), arrstring, (Bundle)object);
                }
                case 5: 
                case 7: {
                    this.enforceTree(uri);
                    return this.queryDocument(DocumentsContract.getDocumentId(uri), arrstring);
                }
                case 4: {
                    return this.querySearchDocuments(DocumentsContract.getRootId(uri), arrstring, (Bundle)object);
                }
                case 3: {
                    return this.queryRecentDocuments(DocumentsContract.getRootId(uri), arrstring, (Bundle)object, cancellationSignal);
                }
                case 1: {
                    return this.queryRoots(arrstring);
                }
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Unsupported Uri ");
            ((StringBuilder)object).append(uri);
            arrstring = new UnsupportedOperationException(((StringBuilder)object).toString());
            throw arrstring;
        }
        catch (FileNotFoundException fileNotFoundException) {
            Log.w("DocumentsProvider", "Failed during query", fileNotFoundException);
            return null;
        }
    }

    @Override
    public final Cursor query(Uri uri, String[] arrstring, String string2, String[] arrstring2, String string3) {
        throw new UnsupportedOperationException("Pre-Android-O query format not supported.");
    }

    @Override
    public Cursor query(Uri uri, String[] arrstring, String string2, String[] arrstring2, String string3, CancellationSignal cancellationSignal) {
        throw new UnsupportedOperationException("Pre-Android-O query format not supported.");
    }

    public Cursor queryChildDocuments(String string2, String[] arrstring, Bundle bundle) throws FileNotFoundException {
        return this.queryChildDocuments(string2, arrstring, DocumentsProvider.getSortClause(bundle));
    }

    public abstract Cursor queryChildDocuments(String var1, String[] var2, String var3) throws FileNotFoundException;

    public Cursor queryChildDocumentsForManage(String string2, String[] arrstring, String string3) throws FileNotFoundException {
        throw new UnsupportedOperationException("Manage not supported");
    }

    public abstract Cursor queryDocument(String var1, String[] var2) throws FileNotFoundException;

    public Cursor queryRecentDocuments(String string2, String[] arrstring) throws FileNotFoundException {
        throw new UnsupportedOperationException("Recent not supported");
    }

    public Cursor queryRecentDocuments(String object, String[] object2, Bundle bundle, CancellationSignal cancellationSignal) throws FileNotFoundException {
        Preconditions.checkNotNull(object, "rootId can not be null");
        object = this.queryRecentDocuments((String)object, (String[])object2);
        object2 = new Bundle();
        object.setExtras((Bundle)object2);
        ((BaseBundle)object2).putStringArray("android.content.extra.HONORED_ARGS", new String[0]);
        return object;
    }

    public abstract Cursor queryRoots(String[] var1) throws FileNotFoundException;

    public Cursor querySearchDocuments(String string2, String string3, String[] arrstring) throws FileNotFoundException {
        throw new UnsupportedOperationException("Search not supported");
    }

    public Cursor querySearchDocuments(String string2, String[] arrstring, Bundle bundle) throws FileNotFoundException {
        Preconditions.checkNotNull(string2, "rootId can not be null");
        Preconditions.checkNotNull(bundle, "queryArgs can not be null");
        return this.querySearchDocuments(string2, DocumentsContract.getSearchDocumentsQuery(bundle), arrstring);
    }

    public void removeDocument(String string2, String string3) throws FileNotFoundException {
        throw new UnsupportedOperationException("Remove not supported");
    }

    public String renameDocument(String string2, String string3) throws FileNotFoundException {
        throw new UnsupportedOperationException("Rename not supported");
    }

    public final void revokeDocumentPermission(String string2) {
        Context context = this.getContext();
        context.revokeUriPermission(DocumentsContract.buildDocumentUri(this.mAuthority, string2), -1);
        context.revokeUriPermission(DocumentsContract.buildTreeDocumentUri(this.mAuthority, string2), -1);
    }

    @Override
    public final int update(Uri uri, ContentValues contentValues, String string2, String[] arrstring) {
        throw new UnsupportedOperationException("Update not supported");
    }
}

