/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.android.server.backup;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.backup.BackupDataInputStream;
import android.app.backup.BackupDataOutput;
import android.app.backup.BackupHelper;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncAdapterType;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AccountSyncSettingsBackupHelper
implements BackupHelper {
    private static final boolean DEBUG = false;
    private static final String JSON_FORMAT_ENCODING = "UTF-8";
    private static final String JSON_FORMAT_HEADER_KEY = "account_data";
    private static final int JSON_FORMAT_VERSION = 1;
    private static final String KEY_ACCOUNTS = "accounts";
    private static final String KEY_ACCOUNT_AUTHORITIES = "authorities";
    private static final String KEY_ACCOUNT_NAME = "name";
    private static final String KEY_ACCOUNT_TYPE = "type";
    private static final String KEY_AUTHORITY_NAME = "name";
    private static final String KEY_AUTHORITY_SYNC_ENABLED = "syncEnabled";
    private static final String KEY_AUTHORITY_SYNC_STATE = "syncState";
    private static final String KEY_MASTER_SYNC_ENABLED = "masterSyncEnabled";
    private static final String KEY_VERSION = "version";
    private static final int MD5_BYTE_SIZE = 16;
    private static final String STASH_FILE = "/backup/unadded_account_syncsettings.json";
    private static final int STATE_VERSION = 1;
    private static final int SYNC_REQUEST_LATCH_TIMEOUT_SECONDS = 1;
    private static final String TAG = "AccountSyncSettingsBackupHelper";
    private AccountManager mAccountManager;
    private Context mContext;
    private final int mUserId;

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

    public AccountSyncSettingsBackupHelper(Context context, int n) {
        this.mContext = context;
        this.mAccountManager = AccountManager.get(this.mContext);
        this.mUserId = n;
    }

    public static void accountAdded(Context context, int n) {
        new AccountSyncSettingsBackupHelper(context, n).accountAddedInternal(n);
    }

    private void accountAddedInternal(int n) {
        Object object;
        FileInputStream fileInputStream = new FileInputStream(AccountSyncSettingsBackupHelper.getStashFile(n));
        try {
            object = new DataInputStream(fileInputStream);
            object = ((DataInputStream)object).readUTF();
        }
        catch (Throwable throwable) {
            try {
                throw throwable;
            }
            catch (Throwable throwable2) {
                try {
                    AccountSyncSettingsBackupHelper.$closeResource(throwable, fileInputStream);
                    throw throwable2;
                }
                catch (IOException iOException) {
                    return;
                }
                catch (FileNotFoundException fileNotFoundException) {
                    return;
                }
            }
        }
        AccountSyncSettingsBackupHelper.$closeResource(null, fileInputStream);
        try {
            fileInputStream = new JSONArray((String)object);
            this.restoreFromJsonArray((JSONArray)fileInputStream, n);
        }
        catch (JSONException jSONException) {
            Log.e(TAG, "there was an error with the stashed sync settings", jSONException);
        }
    }

    private byte[] generateMd5Checksum(byte[] arrby) throws NoSuchAlgorithmException {
        if (arrby == null) {
            return null;
        }
        return MessageDigest.getInstance("MD5").digest(arrby);
    }

    private Set<Account> getAccounts(int n) {
        Account[] arraccount = this.mAccountManager.getAccountsAsUser(n);
        HashSet<Account> hashSet = new HashSet<Account>();
        int n2 = arraccount.length;
        for (n = 0; n < n2; ++n) {
            hashSet.add(arraccount[n]);
        }
        return hashSet;
    }

    private static File getStashFile(int n) {
        File file = n == 0 ? Environment.getDataDirectory() : Environment.getDataSystemCeDirectory(n);
        return new File(file, STASH_FILE);
    }

    private byte[] readOldMd5Checksum(ParcelFileDescriptor arrby) throws IOException {
        block6 : {
            Object object;
            int n;
            block5 : {
                object = new DataInputStream(new FileInputStream(arrby.getFileDescriptor()));
                arrby = new byte[16];
                n = ((DataInputStream)object).readInt();
                if (n > 1) break block5;
                for (n = 0; n < 16; ++n) {
                    arrby[n] = ((DataInputStream)object).readByte();
                    continue;
                }
                break block6;
            }
            try {
                object = new StringBuilder();
                ((StringBuilder)object).append("Backup state version is: ");
                ((StringBuilder)object).append(n);
                ((StringBuilder)object).append(" (support only up to version ");
                ((StringBuilder)object).append(1);
                ((StringBuilder)object).append(")");
                Log.i(TAG, ((StringBuilder)object).toString());
            }
            catch (EOFException eOFException) {
                // empty catch block
            }
        }
        return arrby;
    }

    private void restoreExistingAccountSyncSettingsFromJSON(JSONObject object, int n) throws JSONException {
        JSONArray jSONArray = object.getJSONArray(KEY_ACCOUNT_AUTHORITIES);
        object = new Account(object.getString("name"), object.getString(KEY_ACCOUNT_TYPE));
        for (int i = 0; i < jSONArray.length(); ++i) {
            JSONObject jSONObject = (JSONObject)jSONArray.get(i);
            String string2 = jSONObject.getString("name");
            boolean bl = jSONObject.getBoolean(KEY_AUTHORITY_SYNC_ENABLED);
            int n2 = jSONObject.getInt(KEY_AUTHORITY_SYNC_STATE);
            ContentResolver.setSyncAutomaticallyAsUser((Account)object, string2, bl, n);
            if (bl) continue;
            n2 = n2 == 0 ? 0 : 2;
            ContentResolver.setIsSyncableAsUser((Account)object, string2, n2, n);
        }
    }

    /*
     * Loose catch block
     * Enabled aggressive exception aggregation
     */
    private void restoreFromJsonArray(JSONArray object, int n) throws JSONException {
        Object object2 = this.getAccounts(n);
        Object object3 = new JSONArray();
        for (int i = 0; i < object.length(); ++i) {
            Account account;
            JSONObject jSONObject = (JSONObject)object.get(i);
            String string2 = jSONObject.getString("name");
            String string3 = jSONObject.getString(KEY_ACCOUNT_TYPE);
            try {
                account = new Account(string2, string3);
            }
            catch (IllegalArgumentException illegalArgumentException) {
                // empty catch block
            }
            if (object2.contains(account)) {
                this.restoreExistingAccountSyncSettingsFromJSON(jSONObject, n);
                continue;
            }
            object3.put((Object)jSONObject);
            continue;
        }
        if (object3.length() > 0) {
            object = new FileOutputStream(AccountSyncSettingsBackupHelper.getStashFile(n));
            object3 = object3.toString();
            object2 = new DataOutputStream((OutputStream)object);
            ((DataOutputStream)object2).writeUTF((String)object3);
            AccountSyncSettingsBackupHelper.$closeResource(null, (AutoCloseable)object);
            catch (Throwable throwable) {
                try {
                    throw throwable;
                }
                catch (Throwable throwable2) {
                    try {
                        AccountSyncSettingsBackupHelper.$closeResource(throwable, (AutoCloseable)object);
                        throw throwable2;
                    }
                    catch (IOException iOException) {
                        Log.e(TAG, "unable to write the sync settings to the stash file", iOException);
                    }
                }
            }
        } else {
            object = AccountSyncSettingsBackupHelper.getStashFile(n);
            if (((File)object).exists()) {
                ((File)object).delete();
            }
        }
    }

    private JSONObject serializeAccountSyncSettingsToJSON(int n) throws JSONException {
        SyncAdapterType syncAdapterType;
        int n2;
        Account[] arraccount = this.mAccountManager.getAccountsAsUser(n);
        SyncAdapterType[] arrsyncAdapterType = ContentResolver.getSyncAdapterTypesAsUser(n);
        HashMap hashMap = new HashMap();
        int n3 = arrsyncAdapterType.length;
        int n4 = 0;
        for (n2 = 0; n2 < n3; ++n2) {
            syncAdapterType = arrsyncAdapterType[n2];
            if (!syncAdapterType.isUserVisible()) continue;
            if (!hashMap.containsKey(syncAdapterType.accountType)) {
                hashMap.put(syncAdapterType.accountType, new ArrayList());
            }
            ((List)hashMap.get(syncAdapterType.accountType)).add(syncAdapterType.authority);
        }
        JSONObject jSONObject = new JSONObject();
        jSONObject.put(KEY_VERSION, 1);
        jSONObject.put(KEY_MASTER_SYNC_ENABLED, ContentResolver.getMasterSyncAutomaticallyAsUser(n));
        syncAdapterType = new JSONArray();
        n3 = arraccount.length;
        for (n2 = n4; n2 < n3; ++n2) {
            Account account = arraccount[n2];
            Object object = (List)hashMap.get(account.type);
            if (object == null || object.isEmpty()) continue;
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("name", (Object)account.name);
            jSONObject2.put(KEY_ACCOUNT_TYPE, (Object)account.type);
            JSONArray jSONArray = new JSONArray();
            object = object.iterator();
            do {
                n4 = n;
                if (!object.hasNext()) break;
                String string2 = (String)object.next();
                int n5 = ContentResolver.getIsSyncableAsUser(account, string2, n4);
                boolean bl = ContentResolver.getSyncAutomaticallyAsUser(account, string2, n4);
                JSONObject jSONObject3 = new JSONObject();
                jSONObject3.put("name", (Object)string2);
                jSONObject3.put(KEY_AUTHORITY_SYNC_STATE, n5);
                jSONObject3.put(KEY_AUTHORITY_SYNC_ENABLED, bl);
                jSONArray.put((Object)jSONObject3);
            } while (true);
            jSONObject2.put(KEY_ACCOUNT_AUTHORITIES, (Object)jSONArray);
            syncAdapterType.put((Object)jSONObject2);
        }
        jSONObject.put(KEY_ACCOUNTS, (Object)syncAdapterType);
        return jSONObject;
    }

    private void writeNewMd5Checksum(ParcelFileDescriptor closeable, byte[] arrby) throws IOException {
        closeable = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(((ParcelFileDescriptor)closeable).getFileDescriptor())));
        ((DataOutputStream)closeable).writeInt(1);
        ((FilterOutputStream)closeable).write(arrby);
    }

    @Override
    public void performBackup(ParcelFileDescriptor arrby, BackupDataOutput object, ParcelFileDescriptor parcelFileDescriptor) {
        try {
            byte[] arrby2 = this.serializeAccountSyncSettingsToJSON(this.mUserId).toString().getBytes(JSON_FORMAT_ENCODING);
            byte[] arrby3 = this.readOldMd5Checksum((ParcelFileDescriptor)arrby);
            arrby = this.generateMd5Checksum(arrby2);
            if (!Arrays.equals(arrby3, arrby)) {
                int n = arrby2.length;
                ((BackupDataOutput)object).writeEntityHeader(JSON_FORMAT_HEADER_KEY, n);
                ((BackupDataOutput)object).writeEntityData(arrby2, n);
                Log.i(TAG, "Backup successful.");
            } else {
                Log.i(TAG, "Old and new MD5 checksums match. Skipping backup.");
            }
            this.writeNewMd5Checksum(parcelFileDescriptor, arrby);
        }
        catch (IOException | NoSuchAlgorithmException | JSONException throwable) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Couldn't backup account sync settings\n");
            ((StringBuilder)object).append(throwable);
            Log.e(TAG, ((StringBuilder)object).toString());
        }
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public void restoreEntity(BackupDataInputStream object) {
        boolean bl;
        block5 : {
            JSONObject jSONObject = new byte[((BackupDataInputStream)object).size()];
            ((BackupDataInputStream)object).read((byte[])jSONObject);
            object = new String((byte[])jSONObject, JSON_FORMAT_ENCODING);
            jSONObject = new JSONObject((String)object);
            bl = jSONObject.getBoolean(KEY_MASTER_SYNC_ENABLED);
            object = jSONObject.getJSONArray(KEY_ACCOUNTS);
            if (!ContentResolver.getMasterSyncAutomaticallyAsUser(this.mUserId)) break block5;
            ContentResolver.setMasterSyncAutomaticallyAsUser(false, this.mUserId);
        }
        this.restoreFromJsonArray((JSONArray)object, this.mUserId);
        {
            catch (Throwable throwable) {
                ContentResolver.setMasterSyncAutomaticallyAsUser(bl, this.mUserId);
                throw throwable;
            }
        }
        try {
            ContentResolver.setMasterSyncAutomaticallyAsUser(bl, this.mUserId);
            Log.i(TAG, "Restore successful.");
            return;
        }
        catch (IOException | JSONException throwable2) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Couldn't restore account sync settings\n");
            ((StringBuilder)object).append(throwable2);
            Log.e(TAG, ((StringBuilder)object).toString());
        }
    }

    @Override
    public void writeNewStateDescription(ParcelFileDescriptor parcelFileDescriptor) {
    }
}

