/*
 * Decompiled with CFR 0.145.
 */
package android.content;

import android.annotation.UnsupportedAppUsage;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ICancellationSignal;
import android.os.IInterface;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public interface IContentProvider
extends IInterface {
    public static final int APPLY_BATCH_TRANSACTION = 20;
    public static final int BULK_INSERT_TRANSACTION = 13;
    public static final int CALL_TRANSACTION = 21;
    public static final int CANONICALIZE_TRANSACTION = 25;
    public static final int CREATE_CANCELATION_SIGNAL_TRANSACTION = 24;
    public static final int DELETE_TRANSACTION = 4;
    public static final int GET_STREAM_TYPES_TRANSACTION = 22;
    public static final int GET_TYPE_TRANSACTION = 2;
    public static final int INSERT_TRANSACTION = 3;
    public static final int OPEN_ASSET_FILE_TRANSACTION = 15;
    public static final int OPEN_FILE_TRANSACTION = 14;
    public static final int OPEN_TYPED_ASSET_FILE_TRANSACTION = 23;
    @UnsupportedAppUsage
    public static final int QUERY_TRANSACTION = 1;
    public static final int REFRESH_TRANSACTION = 27;
    public static final int UNCANONICALIZE_TRANSACTION = 26;
    public static final int UPDATE_TRANSACTION = 10;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public static final String descriptor = "android.content.IContentProvider";

    public ContentProviderResult[] applyBatch(String var1, String var2, ArrayList<ContentProviderOperation> var3) throws RemoteException, OperationApplicationException;

    @Deprecated
    default public ContentProviderResult[] applyBatch(String string2, ArrayList<ContentProviderOperation> arrayList) throws RemoteException, OperationApplicationException {
        return this.applyBatch(string2, "unknown", arrayList);
    }

    @UnsupportedAppUsage
    public int bulkInsert(String var1, Uri var2, ContentValues[] var3) throws RemoteException;

    @Deprecated
    @UnsupportedAppUsage
    default public Bundle call(String string2, String string3, String string4, Bundle bundle) throws RemoteException {
        return this.call(string2, "unknown", string3, string4, bundle);
    }

    public Bundle call(String var1, String var2, String var3, String var4, Bundle var5) throws RemoteException;

    public Uri canonicalize(String var1, Uri var2) throws RemoteException;

    public ICancellationSignal createCancellationSignal() throws RemoteException;

    @UnsupportedAppUsage
    public int delete(String var1, Uri var2, String var3, String[] var4) throws RemoteException;

    public String[] getStreamTypes(Uri var1, String var2) throws RemoteException;

    public String getType(Uri var1) throws RemoteException;

    @UnsupportedAppUsage
    public Uri insert(String var1, Uri var2, ContentValues var3) throws RemoteException;

    public AssetFileDescriptor openAssetFile(String var1, Uri var2, String var3, ICancellationSignal var4) throws RemoteException, FileNotFoundException;

    public ParcelFileDescriptor openFile(String var1, Uri var2, String var3, ICancellationSignal var4, IBinder var5) throws RemoteException, FileNotFoundException;

    public AssetFileDescriptor openTypedAssetFile(String var1, Uri var2, String var3, Bundle var4, ICancellationSignal var5) throws RemoteException, FileNotFoundException;

    public Cursor query(String var1, Uri var2, String[] var3, Bundle var4, ICancellationSignal var5) throws RemoteException;

    public boolean refresh(String var1, Uri var2, Bundle var3, ICancellationSignal var4) throws RemoteException;

    public Uri uncanonicalize(String var1, Uri var2) throws RemoteException;

    @UnsupportedAppUsage
    public int update(String var1, Uri var2, ContentValues var3, String var4, String[] var5) throws RemoteException;
}

