/*
 * Decompiled with CFR 0.145.
 */
package android.content;

import android.annotation.UnsupportedAppUsage;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentValues;
import android.content.IContentProvider;
import android.content.OperationApplicationException;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ICancellationSignal;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.RemoteException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;

final class ContentProviderProxy
implements IContentProvider {
    @UnsupportedAppUsage
    private IBinder mRemote;

    public ContentProviderProxy(IBinder iBinder) {
        this.mRemote = iBinder;
    }

    @Override
    public ContentProviderResult[] applyBatch(String arrcontentProviderResult, String string2, ArrayList<ContentProviderOperation> arrayList) throws RemoteException, OperationApplicationException {
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
            parcel.writeInterfaceToken("android.content.IContentProvider");
            parcel.writeString((String)arrcontentProviderResult);
            parcel.writeString(string2);
            parcel.writeInt(arrayList.size());
            arrcontentProviderResult = arrayList.iterator();
            while (arrcontentProviderResult.hasNext()) {
                ((ContentProviderOperation)arrcontentProviderResult.next()).writeToParcel(parcel, 0);
            }
            this.mRemote.transact(20, parcel, parcel2, 0);
            DatabaseUtils.readExceptionWithOperationApplicationExceptionFromParcel(parcel2);
            arrcontentProviderResult = parcel2.createTypedArray(ContentProviderResult.CREATOR);
            return arrcontentProviderResult;
        }
        finally {
            parcel.recycle();
            parcel2.recycle();
        }
    }

    @Override
    public IBinder asBinder() {
        return this.mRemote;
    }

    @Override
    public int bulkInsert(String string2, Uri uri, ContentValues[] arrcontentValues) throws RemoteException {
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
            parcel.writeInterfaceToken("android.content.IContentProvider");
            parcel.writeString(string2);
            uri.writeToParcel(parcel, 0);
            parcel.writeTypedArray((Parcelable[])arrcontentValues, 0);
            this.mRemote.transact(13, parcel, parcel2, 0);
            DatabaseUtils.readExceptionFromParcel(parcel2);
            int n = parcel2.readInt();
            return n;
        }
        finally {
            parcel.recycle();
            parcel2.recycle();
        }
    }

    @Override
    public Bundle call(String object, String string2, String string3, String string4, Bundle bundle) throws RemoteException {
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
            parcel.writeInterfaceToken("android.content.IContentProvider");
            parcel.writeString((String)object);
            parcel.writeString(string2);
            parcel.writeString(string3);
            parcel.writeString(string4);
            parcel.writeBundle(bundle);
            this.mRemote.transact(21, parcel, parcel2, 0);
            DatabaseUtils.readExceptionFromParcel(parcel2);
            object = parcel2.readBundle();
            return object;
        }
        finally {
            parcel.recycle();
            parcel2.recycle();
        }
    }

    @Override
    public Uri canonicalize(String object, Uri uri) throws RemoteException {
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
            parcel.writeInterfaceToken("android.content.IContentProvider");
            parcel.writeString((String)object);
            uri.writeToParcel(parcel, 0);
            this.mRemote.transact(25, parcel, parcel2, 0);
            DatabaseUtils.readExceptionFromParcel(parcel2);
            object = Uri.CREATOR.createFromParcel(parcel2);
            return object;
        }
        finally {
            parcel.recycle();
            parcel2.recycle();
        }
    }

    @Override
    public ICancellationSignal createCancellationSignal() throws RemoteException {
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
            parcel.writeInterfaceToken("android.content.IContentProvider");
            this.mRemote.transact(24, parcel, parcel2, 0);
            DatabaseUtils.readExceptionFromParcel(parcel2);
            ICancellationSignal iCancellationSignal = ICancellationSignal.Stub.asInterface(parcel2.readStrongBinder());
            return iCancellationSignal;
        }
        finally {
            parcel.recycle();
            parcel2.recycle();
        }
    }

    @Override
    public int delete(String string2, Uri uri, String string3, String[] arrstring) throws RemoteException {
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
            parcel.writeInterfaceToken("android.content.IContentProvider");
            parcel.writeString(string2);
            uri.writeToParcel(parcel, 0);
            parcel.writeString(string3);
            parcel.writeStringArray(arrstring);
            this.mRemote.transact(4, parcel, parcel2, 0);
            DatabaseUtils.readExceptionFromParcel(parcel2);
            int n = parcel2.readInt();
            return n;
        }
        finally {
            parcel.recycle();
            parcel2.recycle();
        }
    }

    @Override
    public String[] getStreamTypes(Uri arrstring, String string2) throws RemoteException {
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
            parcel.writeInterfaceToken("android.content.IContentProvider");
            arrstring.writeToParcel(parcel, 0);
            parcel.writeString(string2);
            this.mRemote.transact(22, parcel, parcel2, 0);
            DatabaseUtils.readExceptionFromParcel(parcel2);
            arrstring = parcel2.createStringArray();
            return arrstring;
        }
        finally {
            parcel.recycle();
            parcel2.recycle();
        }
    }

    @Override
    public String getType(Uri object) throws RemoteException {
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
            parcel.writeInterfaceToken("android.content.IContentProvider");
            object.writeToParcel(parcel, 0);
            this.mRemote.transact(2, parcel, parcel2, 0);
            DatabaseUtils.readExceptionFromParcel(parcel2);
            object = parcel2.readString();
            return object;
        }
        finally {
            parcel.recycle();
            parcel2.recycle();
        }
    }

    @Override
    public Uri insert(String object, Uri uri, ContentValues contentValues) throws RemoteException {
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
            parcel.writeInterfaceToken("android.content.IContentProvider");
            parcel.writeString((String)object);
            uri.writeToParcel(parcel, 0);
            contentValues.writeToParcel(parcel, 0);
            this.mRemote.transact(3, parcel, parcel2, 0);
            DatabaseUtils.readExceptionFromParcel(parcel2);
            object = Uri.CREATOR.createFromParcel(parcel2);
            return object;
        }
        finally {
            parcel.recycle();
            parcel2.recycle();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public AssetFileDescriptor openAssetFile(String object, Uri uri, String string2, ICancellationSignal iCancellationSignal) throws RemoteException, FileNotFoundException {
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
            parcel.writeInterfaceToken("android.content.IContentProvider");
            parcel.writeString((String)object);
            uri.writeToParcel(parcel, 0);
            parcel.writeString(string2);
            uri = null;
            object = iCancellationSignal != null ? iCancellationSignal.asBinder() : null;
            parcel.writeStrongBinder((IBinder)object);
            this.mRemote.transact(15, parcel, parcel2, 0);
            DatabaseUtils.readExceptionWithFileNotFoundExceptionFromParcel(parcel2);
            object = uri;
            if (parcel2.readInt() == 0) return object;
            object = AssetFileDescriptor.CREATOR.createFromParcel(parcel2);
            return object;
        }
        finally {
            parcel.recycle();
            parcel2.recycle();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public ParcelFileDescriptor openFile(String object, Uri uri, String string2, ICancellationSignal iCancellationSignal, IBinder iBinder) throws RemoteException, FileNotFoundException {
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
            parcel.writeInterfaceToken("android.content.IContentProvider");
            parcel.writeString((String)object);
            uri.writeToParcel(parcel, 0);
            parcel.writeString(string2);
            uri = null;
            object = iCancellationSignal != null ? iCancellationSignal.asBinder() : null;
            parcel.writeStrongBinder((IBinder)object);
            parcel.writeStrongBinder(iBinder);
            this.mRemote.transact(14, parcel, parcel2, 0);
            DatabaseUtils.readExceptionWithFileNotFoundExceptionFromParcel(parcel2);
            object = uri;
            if (parcel2.readInt() == 0) return object;
            object = ParcelFileDescriptor.CREATOR.createFromParcel(parcel2);
            return object;
        }
        finally {
            parcel.recycle();
            parcel2.recycle();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public AssetFileDescriptor openTypedAssetFile(String object, Uri uri, String string2, Bundle bundle, ICancellationSignal iCancellationSignal) throws RemoteException, FileNotFoundException {
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
            parcel.writeInterfaceToken("android.content.IContentProvider");
            parcel.writeString((String)object);
            uri.writeToParcel(parcel, 0);
            parcel.writeString(string2);
            parcel.writeBundle(bundle);
            uri = null;
            object = iCancellationSignal != null ? iCancellationSignal.asBinder() : null;
            parcel.writeStrongBinder((IBinder)object);
            this.mRemote.transact(23, parcel, parcel2, 0);
            DatabaseUtils.readExceptionWithFileNotFoundExceptionFromParcel(parcel2);
            object = uri;
            if (parcel2.readInt() == 0) return object;
            object = AssetFileDescriptor.CREATOR.createFromParcel(parcel2);
            return object;
        }
        finally {
            parcel.recycle();
            parcel2.recycle();
        }
    }

    /*
     * Exception decompiling
     */
    @Override
    public Cursor query(String var1_1, Uri var2_5, String[] var3_6, Bundle var4_7, ICancellationSignal var5_8) throws RemoteException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [4[CATCHBLOCK]], but top level block is 2[TRYBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
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

    @Override
    public boolean refresh(String object, Uri uri, Bundle bundle, ICancellationSignal iCancellationSignal) throws RemoteException {
        boolean bl;
        Parcel parcel;
        Parcel parcel2;
        block7 : {
            block6 : {
                parcel2 = Parcel.obtain();
                parcel = Parcel.obtain();
                try {
                    parcel2.writeInterfaceToken("android.content.IContentProvider");
                    parcel2.writeString((String)object);
                    bl = false;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
                uri.writeToParcel(parcel2, 0);
                parcel2.writeBundle(bundle);
                if (iCancellationSignal == null) break block6;
                object = iCancellationSignal.asBinder();
                break block7;
            }
            object = null;
        }
        parcel2.writeStrongBinder((IBinder)object);
        this.mRemote.transact(27, parcel2, parcel, 0);
        DatabaseUtils.readExceptionFromParcel(parcel);
        int n = parcel.readInt();
        if (n == 0) {
            bl = true;
        }
        parcel2.recycle();
        parcel.recycle();
        return bl;
    }

    @Override
    public Uri uncanonicalize(String object, Uri uri) throws RemoteException {
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
            parcel.writeInterfaceToken("android.content.IContentProvider");
            parcel.writeString((String)object);
            uri.writeToParcel(parcel, 0);
            this.mRemote.transact(26, parcel, parcel2, 0);
            DatabaseUtils.readExceptionFromParcel(parcel2);
            object = Uri.CREATOR.createFromParcel(parcel2);
            return object;
        }
        finally {
            parcel.recycle();
            parcel2.recycle();
        }
    }

    @Override
    public int update(String string2, Uri uri, ContentValues contentValues, String string3, String[] arrstring) throws RemoteException {
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
            parcel.writeInterfaceToken("android.content.IContentProvider");
            parcel.writeString(string2);
            uri.writeToParcel(parcel, 0);
            contentValues.writeToParcel(parcel, 0);
            parcel.writeString(string3);
            parcel.writeStringArray(arrstring);
            this.mRemote.transact(10, parcel, parcel2, 0);
            DatabaseUtils.readExceptionFromParcel(parcel2);
            int n = parcel2.readInt();
            return n;
        }
        finally {
            parcel.recycle();
            parcel2.recycle();
        }
    }
}

