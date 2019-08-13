/*
 * Decompiled with CFR 0.145.
 */
package android.content;

import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public interface ContentInterface {
    public ContentProviderResult[] applyBatch(String var1, ArrayList<ContentProviderOperation> var2) throws RemoteException, OperationApplicationException;

    public int bulkInsert(Uri var1, ContentValues[] var2) throws RemoteException;

    public Bundle call(String var1, String var2, String var3, Bundle var4) throws RemoteException;

    public Uri canonicalize(Uri var1) throws RemoteException;

    public int delete(Uri var1, String var2, String[] var3) throws RemoteException;

    public String[] getStreamTypes(Uri var1, String var2) throws RemoteException;

    public String getType(Uri var1) throws RemoteException;

    public Uri insert(Uri var1, ContentValues var2) throws RemoteException;

    public AssetFileDescriptor openAssetFile(Uri var1, String var2, CancellationSignal var3) throws RemoteException, FileNotFoundException;

    public ParcelFileDescriptor openFile(Uri var1, String var2, CancellationSignal var3) throws RemoteException, FileNotFoundException;

    public AssetFileDescriptor openTypedAssetFile(Uri var1, String var2, Bundle var3, CancellationSignal var4) throws RemoteException, FileNotFoundException;

    public Cursor query(Uri var1, String[] var2, Bundle var3, CancellationSignal var4) throws RemoteException;

    public boolean refresh(Uri var1, Bundle var2, CancellationSignal var3) throws RemoteException;

    public Uri uncanonicalize(Uri var1) throws RemoteException;

    public int update(Uri var1, ContentValues var2, String var3, String[] var4) throws RemoteException;
}

