/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.ContentResolver
 *  android.content.ContentValues
 *  android.database.Cursor
 *  android.net.Uri
 *  android.os.RemoteException
 *  android.provider.Telephony
 *  android.provider.Telephony$RcsColumns
 *  android.provider.Telephony$RcsColumns$RcsIncomingMessageColumns
 *  android.provider.Telephony$RcsColumns$RcsOutgoingMessageColumns
 *  android.text.TextUtils
 */
package com.android.internal.telephony.ims;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.RemoteException;
import android.provider.Telephony;
import android.text.TextUtils;

public class RcsMessageStoreUtil {
    private ContentResolver mContentResolver;

    private static /* synthetic */ /* end resource */ void $closeResource(Throwable throwable, AutoCloseable autoCloseable) {
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

    RcsMessageStoreUtil(ContentResolver contentResolver) {
        this.mContentResolver = contentResolver;
    }

    static Uri getMessageTableUri(boolean bl) {
        Uri uri = bl ? Telephony.RcsColumns.RcsIncomingMessageColumns.INCOMING_MESSAGE_URI : Telephony.RcsColumns.RcsOutgoingMessageColumns.OUTGOING_MESSAGE_URI;
        return uri;
    }

    private Cursor getValueFromTableRow(Uri uri, String string, String string2, int n) {
        ContentResolver contentResolver = this.mContentResolver;
        CharSequence charSequence = new StringBuilder();
        charSequence.append(string2);
        charSequence.append("=?");
        string2 = charSequence.toString();
        charSequence = Integer.toString(n);
        return contentResolver.query(uri, new String[]{string}, string2, new String[]{charSequence}, null);
    }

    private void performUpdate(Uri uri, ContentValues contentValues, String string) throws RemoteException {
        if (this.mContentResolver.update(uri, contentValues, null, null) > 0) {
            return;
        }
        throw new RemoteException(string);
    }

    double getDoubleValueFromTableRow(Uri uri, String charSequence, String string, int n) throws RemoteException {
        Cursor cursor;
        block7 : {
            cursor = this.getValueFromTableRow(uri, (String)charSequence, string, n);
            if (cursor != null) {
                if (!cursor.moveToFirst()) break block7;
                double d = cursor.getDouble(cursor.getColumnIndex((String)charSequence));
                RcsMessageStoreUtil.$closeResource(null, (AutoCloseable)cursor);
                return d;
            }
        }
        try {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("The row with (");
            ((StringBuilder)charSequence).append(string);
            ((StringBuilder)charSequence).append(" = ");
            ((StringBuilder)charSequence).append(n);
            ((StringBuilder)charSequence).append(") could not be found in ");
            ((StringBuilder)charSequence).append((Object)uri);
            RemoteException remoteException = new RemoteException(((StringBuilder)charSequence).toString());
            throw remoteException;
        }
        catch (Throwable throwable) {
            try {
                throw throwable;
            }
            catch (Throwable throwable2) {
                if (cursor != null) {
                    RcsMessageStoreUtil.$closeResource(throwable, (AutoCloseable)cursor);
                }
                throw throwable2;
            }
        }
    }

    int getIntValueFromTableRow(Uri uri, String object, String string, int n) throws RemoteException {
        Cursor cursor;
        block7 : {
            cursor = this.getValueFromTableRow(uri, (String)object, string, n);
            if (cursor != null) {
                if (!cursor.moveToFirst()) break block7;
                n = cursor.getInt(cursor.getColumnIndex(object));
                RcsMessageStoreUtil.$closeResource(null, (AutoCloseable)cursor);
                return n;
            }
        }
        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("The row with (");
            stringBuilder.append(string);
            stringBuilder.append(" = ");
            stringBuilder.append(n);
            stringBuilder.append(") could not be found in ");
            stringBuilder.append((Object)uri);
            object = new RemoteException(stringBuilder.toString());
            throw object;
        }
        catch (Throwable throwable) {
            try {
                throw throwable;
            }
            catch (Throwable throwable2) {
                if (cursor != null) {
                    RcsMessageStoreUtil.$closeResource(throwable, (AutoCloseable)cursor);
                }
                throw throwable2;
            }
        }
    }

    long getLongValueFromTableRow(Uri uri, String charSequence, String string, int n) throws RemoteException {
        Cursor cursor;
        block7 : {
            cursor = this.getValueFromTableRow(uri, (String)charSequence, string, n);
            if (cursor != null) {
                if (!cursor.moveToFirst()) break block7;
                long l = cursor.getLong(cursor.getColumnIndex((String)charSequence));
                RcsMessageStoreUtil.$closeResource(null, (AutoCloseable)cursor);
                return l;
            }
        }
        try {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("The row with (");
            ((StringBuilder)charSequence).append(string);
            ((StringBuilder)charSequence).append(" = ");
            ((StringBuilder)charSequence).append(n);
            ((StringBuilder)charSequence).append(") could not be found in ");
            ((StringBuilder)charSequence).append((Object)uri);
            RemoteException remoteException = new RemoteException(((StringBuilder)charSequence).toString());
            throw remoteException;
        }
        catch (Throwable throwable) {
            try {
                throw throwable;
            }
            catch (Throwable throwable2) {
                if (cursor != null) {
                    RcsMessageStoreUtil.$closeResource(throwable, (AutoCloseable)cursor);
                }
                throw throwable2;
            }
        }
    }

    String getStringValueFromTableRow(Uri object, String charSequence, String string, int n) throws RemoteException {
        Cursor cursor;
        block7 : {
            cursor = this.getValueFromTableRow((Uri)object, (String)charSequence, string, n);
            if (cursor != null) {
                if (!cursor.moveToFirst()) break block7;
                object = cursor.getString(cursor.getColumnIndex((String)charSequence));
                RcsMessageStoreUtil.$closeResource(null, (AutoCloseable)cursor);
                return object;
            }
        }
        try {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("The row with (");
            ((StringBuilder)charSequence).append(string);
            ((StringBuilder)charSequence).append(" = ");
            ((StringBuilder)charSequence).append(n);
            ((StringBuilder)charSequence).append(") could not be found in ");
            ((StringBuilder)charSequence).append(object);
            RemoteException remoteException = new RemoteException(((StringBuilder)charSequence).toString());
            throw remoteException;
        }
        catch (Throwable throwable) {
            try {
                throw throwable;
            }
            catch (Throwable throwable2) {
                if (cursor != null) {
                    RcsMessageStoreUtil.$closeResource(throwable, (AutoCloseable)cursor);
                }
                throw throwable2;
            }
        }
    }

    Uri getUriValueFromTableRow(Uri object, String charSequence, String string, int n) throws RemoteException {
        Cursor cursor;
        block7 : {
            cursor = this.getValueFromTableRow((Uri)object, (String)charSequence, string, n);
            if (cursor != null) {
                block8 : {
                    if (!cursor.moveToFirst()) break block7;
                    object = cursor.getString(cursor.getColumnIndex((String)charSequence));
                    if (TextUtils.isEmpty((CharSequence)object)) break block8;
                    object = Uri.parse((String)object);
                    RcsMessageStoreUtil.$closeResource(null, (AutoCloseable)cursor);
                    return object;
                }
                RcsMessageStoreUtil.$closeResource(null, (AutoCloseable)cursor);
                return null;
            }
        }
        try {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("The row with (");
            ((StringBuilder)charSequence).append(string);
            ((StringBuilder)charSequence).append(" = ");
            ((StringBuilder)charSequence).append(n);
            ((StringBuilder)charSequence).append(") could not be found in ");
            ((StringBuilder)charSequence).append(object);
            RemoteException remoteException = new RemoteException(((StringBuilder)charSequence).toString());
            throw remoteException;
        }
        catch (Throwable throwable) {
            try {
                throw throwable;
            }
            catch (Throwable throwable2) {
                if (cursor != null) {
                    RcsMessageStoreUtil.$closeResource(throwable, (AutoCloseable)cursor);
                }
                throw throwable2;
            }
        }
    }

    void updateValueOfProviderUri(Uri uri, String string, double d, String string2) throws RemoteException {
        ContentValues contentValues = new ContentValues(1);
        contentValues.put(string, Double.valueOf(d));
        this.performUpdate(uri, contentValues, string2);
    }

    void updateValueOfProviderUri(Uri uri, String string, int n, String string2) throws RemoteException {
        ContentValues contentValues = new ContentValues(1);
        contentValues.put(string, Integer.valueOf(n));
        this.performUpdate(uri, contentValues, string2);
    }

    void updateValueOfProviderUri(Uri uri, String string, long l, String string2) throws RemoteException {
        ContentValues contentValues = new ContentValues(1);
        contentValues.put(string, Long.valueOf(l));
        this.performUpdate(uri, contentValues, string2);
    }

    void updateValueOfProviderUri(Uri uri, String string, Uri object, String string2) throws RemoteException {
        ContentValues contentValues = new ContentValues(1);
        object = object == null ? null : object.toString();
        contentValues.put(string, (String)object);
        this.performUpdate(uri, contentValues, string2);
    }

    void updateValueOfProviderUri(Uri uri, String string, String string2, String string3) throws RemoteException {
        ContentValues contentValues = new ContentValues(1);
        contentValues.put(string, string2);
        this.performUpdate(uri, contentValues, string3);
    }
}

