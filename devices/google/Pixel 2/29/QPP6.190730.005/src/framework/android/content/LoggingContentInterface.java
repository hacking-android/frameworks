/*
 * Decompiled with CFR 0.145.
 */
package android.content;

import android.content.ContentInterface;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.RemoteException;
import android.util.Log;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

public class LoggingContentInterface
implements ContentInterface {
    private final ContentInterface delegate;
    private final String tag;

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

    public LoggingContentInterface(String string2, ContentInterface contentInterface) {
        this.tag = string2;
        this.delegate = contentInterface;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public ContentProviderResult[] applyBatch(String arrcontentProviderResult, ArrayList<ContentProviderOperation> arrayList) throws RemoteException, OperationApplicationException {
        Throwable throwable2222;
        Logger logger = new Logger("applyBatch", arrcontentProviderResult, arrayList);
        arrcontentProviderResult = logger.setResult(this.delegate.applyBatch((String)arrcontentProviderResult, arrayList));
        LoggingContentInterface.$closeResource(null, logger);
        return arrcontentProviderResult;
        {
            catch (Throwable throwable2222) {
            }
            catch (Exception exception) {}
            {
                logger.setResult(exception);
                throw exception;
            }
        }
        try {
            throw throwable2222;
        }
        catch (Throwable throwable3) {
            LoggingContentInterface.$closeResource(throwable2222, logger);
            throw throwable3;
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public int bulkInsert(Uri uri, ContentValues[] arrcontentValues) throws RemoteException {
        Throwable throwable2222;
        Logger logger = new Logger("bulkInsert", uri, arrcontentValues);
        int n = logger.setResult(this.delegate.bulkInsert(uri, arrcontentValues));
        LoggingContentInterface.$closeResource(null, logger);
        return n;
        {
            catch (Throwable throwable2222) {
            }
            catch (Exception exception) {}
            {
                logger.setResult(exception);
                throw exception;
            }
        }
        try {
            throw throwable2222;
        }
        catch (Throwable throwable3) {
            LoggingContentInterface.$closeResource(throwable2222, logger);
            throw throwable3;
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public Bundle call(String object, String string2, String string3, Bundle bundle) throws RemoteException {
        Throwable throwable2222;
        Logger logger = new Logger("call", object, string2, string3, bundle);
        object = logger.setResult(this.delegate.call((String)object, string2, string3, bundle));
        LoggingContentInterface.$closeResource(null, logger);
        return object;
        {
            catch (Throwable throwable2222) {
            }
            catch (Exception exception) {}
            {
                logger.setResult(exception);
                throw exception;
            }
        }
        try {
            throw throwable2222;
        }
        catch (Throwable throwable3) {
            LoggingContentInterface.$closeResource(throwable2222, logger);
            throw throwable3;
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public Uri canonicalize(Uri uri) throws RemoteException {
        Throwable throwable2222;
        Logger logger = new Logger("canonicalize", uri);
        uri = logger.setResult(this.delegate.canonicalize(uri));
        LoggingContentInterface.$closeResource(null, logger);
        return uri;
        {
            catch (Throwable throwable2222) {
            }
            catch (Exception exception) {}
            {
                logger.setResult(exception);
                throw exception;
            }
        }
        try {
            throw throwable2222;
        }
        catch (Throwable throwable3) {
            LoggingContentInterface.$closeResource(throwable2222, logger);
            throw throwable3;
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public int delete(Uri uri, String string2, String[] arrstring) throws RemoteException {
        Throwable throwable2222;
        Logger logger = new Logger("delete", uri, string2, arrstring);
        int n = logger.setResult(this.delegate.delete(uri, string2, arrstring));
        LoggingContentInterface.$closeResource(null, logger);
        return n;
        {
            catch (Throwable throwable2222) {
            }
            catch (Exception exception) {}
            {
                logger.setResult(exception);
                throw exception;
            }
        }
        try {
            throw throwable2222;
        }
        catch (Throwable throwable3) {
            LoggingContentInterface.$closeResource(throwable2222, logger);
            throw throwable3;
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public String[] getStreamTypes(Uri arrstring, String string2) throws RemoteException {
        Throwable throwable2222;
        Logger logger = new Logger("getStreamTypes", arrstring, string2);
        arrstring = logger.setResult(this.delegate.getStreamTypes((Uri)arrstring, string2));
        LoggingContentInterface.$closeResource(null, logger);
        return arrstring;
        {
            catch (Throwable throwable2222) {
            }
            catch (Exception exception) {}
            {
                logger.setResult(exception);
                throw exception;
            }
        }
        try {
            throw throwable2222;
        }
        catch (Throwable throwable3) {
            LoggingContentInterface.$closeResource(throwable2222, logger);
            throw throwable3;
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public String getType(Uri object) throws RemoteException {
        Throwable throwable2222;
        Logger logger = new Logger("getType", object);
        object = logger.setResult(this.delegate.getType((Uri)object));
        LoggingContentInterface.$closeResource(null, logger);
        return object;
        {
            catch (Throwable throwable2222) {
            }
            catch (Exception exception) {}
            {
                logger.setResult(exception);
                throw exception;
            }
        }
        try {
            throw throwable2222;
        }
        catch (Throwable throwable3) {
            LoggingContentInterface.$closeResource(throwable2222, logger);
            throw throwable3;
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) throws RemoteException {
        Throwable throwable2222;
        Logger logger = new Logger("insert", uri, contentValues);
        uri = logger.setResult(this.delegate.insert(uri, contentValues));
        LoggingContentInterface.$closeResource(null, logger);
        return uri;
        {
            catch (Throwable throwable2222) {
            }
            catch (Exception exception) {}
            {
                logger.setResult(exception);
                throw exception;
            }
        }
        try {
            throw throwable2222;
        }
        catch (Throwable throwable3) {
            LoggingContentInterface.$closeResource(throwable2222, logger);
            throw throwable3;
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public AssetFileDescriptor openAssetFile(Uri parcelable, String string2, CancellationSignal cancellationSignal) throws RemoteException, FileNotFoundException {
        Throwable throwable2222;
        Logger logger = new Logger("openAssetFile", parcelable, string2, cancellationSignal);
        parcelable = logger.setResult(this.delegate.openAssetFile((Uri)parcelable, string2, cancellationSignal));
        LoggingContentInterface.$closeResource(null, logger);
        return parcelable;
        {
            catch (Throwable throwable2222) {
            }
            catch (Exception exception) {}
            {
                logger.setResult(exception);
                throw exception;
            }
        }
        try {
            throw throwable2222;
        }
        catch (Throwable throwable3) {
            LoggingContentInterface.$closeResource(throwable2222, logger);
            throw throwable3;
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public ParcelFileDescriptor openFile(Uri parcelable, String string2, CancellationSignal cancellationSignal) throws RemoteException, FileNotFoundException {
        Throwable throwable2222;
        Logger logger = new Logger("openFile", parcelable, string2, cancellationSignal);
        parcelable = logger.setResult(this.delegate.openFile((Uri)parcelable, string2, cancellationSignal));
        LoggingContentInterface.$closeResource(null, logger);
        return parcelable;
        {
            catch (Throwable throwable2222) {
            }
            catch (Exception exception) {}
            {
                logger.setResult(exception);
                throw exception;
            }
        }
        try {
            throw throwable2222;
        }
        catch (Throwable throwable3) {
            LoggingContentInterface.$closeResource(throwable2222, logger);
            throw throwable3;
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public AssetFileDescriptor openTypedAssetFile(Uri parcelable, String string2, Bundle bundle, CancellationSignal cancellationSignal) throws RemoteException, FileNotFoundException {
        Throwable throwable2222;
        Logger logger = new Logger("openTypedAssetFile", parcelable, string2, bundle, cancellationSignal);
        parcelable = logger.setResult(this.delegate.openTypedAssetFile((Uri)parcelable, string2, bundle, cancellationSignal));
        LoggingContentInterface.$closeResource(null, logger);
        return parcelable;
        {
            catch (Throwable throwable2222) {
            }
            catch (Exception exception) {}
            {
                logger.setResult(exception);
                throw exception;
            }
        }
        try {
            throw throwable2222;
        }
        catch (Throwable throwable3) {
            LoggingContentInterface.$closeResource(throwable2222, logger);
            throw throwable3;
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public Cursor query(Uri object, String[] arrstring, Bundle bundle, CancellationSignal cancellationSignal) throws RemoteException {
        Throwable throwable2222;
        Logger logger = new Logger("query", object, arrstring, bundle, cancellationSignal);
        object = logger.setResult(this.delegate.query((Uri)object, arrstring, bundle, cancellationSignal));
        LoggingContentInterface.$closeResource(null, logger);
        return object;
        {
            catch (Throwable throwable2222) {
            }
            catch (Exception exception) {}
            {
                logger.setResult(exception);
                throw exception;
            }
        }
        try {
            throw throwable2222;
        }
        catch (Throwable throwable3) {
            LoggingContentInterface.$closeResource(throwable2222, logger);
            throw throwable3;
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public boolean refresh(Uri uri, Bundle bundle, CancellationSignal cancellationSignal) throws RemoteException {
        Throwable throwable2222;
        Logger logger = new Logger("refresh", uri, bundle, cancellationSignal);
        boolean bl = logger.setResult(this.delegate.refresh(uri, bundle, cancellationSignal));
        LoggingContentInterface.$closeResource(null, logger);
        return bl;
        {
            catch (Throwable throwable2222) {
            }
            catch (Exception exception) {}
            {
                logger.setResult(exception);
                throw exception;
            }
        }
        try {
            throw throwable2222;
        }
        catch (Throwable throwable3) {
            LoggingContentInterface.$closeResource(throwable2222, logger);
            throw throwable3;
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public Uri uncanonicalize(Uri uri) throws RemoteException {
        Throwable throwable2222;
        Logger logger = new Logger("uncanonicalize", uri);
        uri = logger.setResult(this.delegate.uncanonicalize(uri));
        LoggingContentInterface.$closeResource(null, logger);
        return uri;
        {
            catch (Throwable throwable2222) {
            }
            catch (Exception exception) {}
            {
                logger.setResult(exception);
                throw exception;
            }
        }
        try {
            throw throwable2222;
        }
        catch (Throwable throwable3) {
            LoggingContentInterface.$closeResource(throwable2222, logger);
            throw throwable3;
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public int update(Uri uri, ContentValues contentValues, String string2, String[] arrstring) throws RemoteException {
        Throwable throwable2222;
        Logger logger = new Logger("update", uri, contentValues, string2, arrstring);
        int n = logger.setResult(this.delegate.update(uri, contentValues, string2, arrstring));
        LoggingContentInterface.$closeResource(null, logger);
        return n;
        {
            catch (Throwable throwable2222) {
            }
            catch (Exception exception) {}
            {
                logger.setResult(exception);
                throw exception;
            }
        }
        try {
            throw throwable2222;
        }
        catch (Throwable throwable3) {
            LoggingContentInterface.$closeResource(throwable2222, logger);
            throw throwable3;
        }
    }

    private class Logger
    implements AutoCloseable {
        private final StringBuilder sb = new StringBuilder();

        public Logger(String string2, Object ... arrobject) {
            int n = arrobject.length;
            for (int i = 0; i < n; ++i) {
                LoggingContentInterface.this = arrobject[i];
                if (!(LoggingContentInterface.this instanceof Bundle)) continue;
                ((Bundle)LoggingContentInterface.this).size();
            }
            LoggingContentInterface.this = this.sb;
            ((StringBuilder)LoggingContentInterface.this).append("callingUid=");
            ((StringBuilder)LoggingContentInterface.this).append(Binder.getCallingUid());
            ((StringBuilder)LoggingContentInterface.this).append(' ');
            this.sb.append(string2);
            LoggingContentInterface.this = this.sb;
            ((StringBuilder)LoggingContentInterface.this).append('(');
            ((StringBuilder)LoggingContentInterface.this).append(this.deepToString(arrobject));
            ((StringBuilder)LoggingContentInterface.this).append(')');
        }

        private String deepToString(Object object) {
            if (object != null && object.getClass().isArray()) {
                return Arrays.deepToString((Object[])object);
            }
            return String.valueOf(object);
        }

        @Override
        public void close() {
            Log.v(LoggingContentInterface.this.tag, this.sb.toString());
        }

        public <T> T setResult(T t) {
            if (t instanceof Cursor) {
                this.sb.append('\n');
                DatabaseUtils.dumpCursor((Cursor)t, this.sb);
            } else {
                StringBuilder stringBuilder = this.sb;
                stringBuilder.append(" = ");
                stringBuilder.append(this.deepToString(t));
            }
            return t;
        }
    }

}

