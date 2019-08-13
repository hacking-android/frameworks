/*
 * Decompiled with CFR 0.145.
 */
package android.provider;

import android.accounts.Account;
import android.content.ContentProviderClient;
import android.content.ContentProviderOperation;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.RemoteException;
import android.provider.BaseColumns;
import android.util.Pair;

public class SyncStateContract {

    public static interface Columns
    extends BaseColumns {
        public static final String ACCOUNT_NAME = "account_name";
        public static final String ACCOUNT_TYPE = "account_type";
        public static final String DATA = "data";
    }

    public static class Constants
    implements Columns {
        public static final String CONTENT_DIRECTORY = "syncstate";
    }

    public static final class Helpers {
        private static final String[] DATA_PROJECTION = new String[]{"data", "_id"};
        private static final String SELECT_BY_ACCOUNT = "account_name=? AND account_type=?";

        public static byte[] get(ContentProviderClient autoCloseable, Uri arrby, Account account) throws RemoteException {
            if ((autoCloseable = ((ContentProviderClient)autoCloseable).query((Uri)arrby, DATA_PROJECTION, SELECT_BY_ACCOUNT, new String[]{account.name, account.type}, null)) != null) {
                try {
                    if (autoCloseable.moveToNext()) {
                        arrby = autoCloseable.getBlob(autoCloseable.getColumnIndexOrThrow("data"));
                        return arrby;
                    }
                    return null;
                }
                finally {
                    autoCloseable.close();
                }
            }
            throw new RemoteException();
        }

        public static Pair<Uri, byte[]> getWithUri(ContentProviderClient autoCloseable, Uri object, Account arrby) throws RemoteException {
            if ((autoCloseable = ((ContentProviderClient)autoCloseable).query((Uri)object, DATA_PROJECTION, SELECT_BY_ACCOUNT, new String[]{arrby.name, arrby.type}, null)) != null) {
                try {
                    if (autoCloseable.moveToNext()) {
                        long l = autoCloseable.getLong(1);
                        arrby = autoCloseable.getBlob(autoCloseable.getColumnIndexOrThrow("data"));
                        object = Pair.create(ContentUris.withAppendedId((Uri)object, l), arrby);
                        return object;
                    }
                    return null;
                }
                finally {
                    autoCloseable.close();
                }
            }
            throw new RemoteException();
        }

        public static Uri insert(ContentProviderClient contentProviderClient, Uri uri, Account account, byte[] arrby) throws RemoteException {
            ContentValues contentValues = new ContentValues();
            contentValues.put("data", arrby);
            contentValues.put("account_name", account.name);
            contentValues.put("account_type", account.type);
            return contentProviderClient.insert(uri, contentValues);
        }

        public static ContentProviderOperation newSetOperation(Uri uri, Account account, byte[] arrby) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("data", arrby);
            return ContentProviderOperation.newInsert(uri).withValue("account_name", account.name).withValue("account_type", account.type).withValues(contentValues).build();
        }

        public static ContentProviderOperation newUpdateOperation(Uri uri, byte[] arrby) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("data", arrby);
            return ContentProviderOperation.newUpdate(uri).withValues(contentValues).build();
        }

        public static void set(ContentProviderClient contentProviderClient, Uri uri, Account account, byte[] arrby) throws RemoteException {
            ContentValues contentValues = new ContentValues();
            contentValues.put("data", arrby);
            contentValues.put("account_name", account.name);
            contentValues.put("account_type", account.type);
            contentProviderClient.insert(uri, contentValues);
        }

        public static void update(ContentProviderClient contentProviderClient, Uri uri, byte[] arrby) throws RemoteException {
            ContentValues contentValues = new ContentValues();
            contentValues.put("data", arrby);
            contentProviderClient.update(uri, contentValues, null, null);
        }
    }

}

