/*
 * Decompiled with CFR 0.145.
 */
package android.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.LruCache;
import com.android.internal.annotations.VisibleForTesting;
import java.io.File;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class HashedStringCache {
    private static final long DAYS_TO_MILLIS = 86400000L;
    private static final boolean DEBUG = false;
    private static final int HASH_CACHE_SIZE = 100;
    private static final int HASH_LENGTH = 8;
    @VisibleForTesting
    static final String HASH_SALT = "_hash_salt";
    @VisibleForTesting
    static final String HASH_SALT_DATE = "_hash_salt_date";
    @VisibleForTesting
    static final String HASH_SALT_GEN = "_hash_salt_gen";
    private static final int MAX_SALT_DAYS = 100;
    private static final String TAG = "HashedStringCache";
    private static final Charset UTF_8;
    private static HashedStringCache sHashedStringCache;
    private final MessageDigest mDigester;
    private final LruCache<String, String> mHashes = new LruCache(100);
    private final Object mPreferenceLock = new Object();
    private byte[] mSalt;
    private int mSaltGen;
    private final SecureRandom mSecureRandom = new SecureRandom();
    private SharedPreferences mSharedPreferences;

    static {
        sHashedStringCache = null;
        UTF_8 = Charset.forName("UTF-8");
    }

    private HashedStringCache() {
        try {
            this.mDigester = MessageDigest.getInstance("MD5");
            return;
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            throw new RuntimeException(noSuchAlgorithmException);
        }
    }

    private boolean checkNeedsNewSalt(String string2, int n, long l) {
        boolean bl = true;
        if (l != 0L && n >= -1) {
            int n2 = n;
            if (n > 100) {
                n2 = 100;
            }
            l = System.currentTimeMillis() - l;
            boolean bl2 = bl;
            if (l < (long)n2 * 86400000L) {
                bl2 = l < 0L ? bl : false;
            }
            return bl2;
        }
        return true;
    }

    private SharedPreferences getHashSharedPreferences(Context context) {
        return context.getSharedPreferences(new File(new File(Environment.getDataUserCePackageDirectory(StorageManager.UUID_PRIVATE_INTERNAL, context.getUserId(), context.getPackageName()), "shared_prefs"), "hashed_cache.xml"), 0);
    }

    public static HashedStringCache getInstance() {
        if (sHashedStringCache == null) {
            sHashedStringCache = new HashedStringCache();
        }
        return sHashedStringCache;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void populateSaltValues(Context object, String string2, int n) {
        Object object2 = this.mPreferenceLock;
        synchronized (object2) {
            this.mSharedPreferences = this.getHashSharedPreferences((Context)object);
            object = this.mSharedPreferences;
            Object object3 = new StringBuilder();
            ((StringBuilder)object3).append(string2);
            ((StringBuilder)object3).append(HASH_SALT_DATE);
            boolean bl = this.checkNeedsNewSalt(string2, n, object.getLong(((StringBuilder)object3).toString(), 0L));
            if (bl) {
                this.mHashes.evictAll();
            }
            if (this.mSalt == null || bl) {
                object = this.mSharedPreferences;
                object3 = new StringBuilder();
                ((StringBuilder)object3).append(string2);
                ((StringBuilder)object3).append(HASH_SALT);
                object = object.getString(((StringBuilder)object3).toString(), null);
                object3 = this.mSharedPreferences;
                Object object4 = new StringBuilder();
                ((StringBuilder)object4).append(string2);
                ((StringBuilder)object4).append(HASH_SALT_GEN);
                this.mSaltGen = object3.getInt(((StringBuilder)object4).toString(), 0);
                if (object == null || bl) {
                    ++this.mSaltGen;
                    object = new byte[16];
                    this.mSecureRandom.nextBytes((byte[])object);
                    object = Base64.encodeToString(object, 3);
                    object4 = this.mSharedPreferences.edit();
                    object3 = new StringBuilder();
                    ((StringBuilder)object3).append(string2);
                    ((StringBuilder)object3).append(HASH_SALT);
                    object3 = object4.putString(((StringBuilder)object3).toString(), (String)object);
                    object4 = new StringBuilder();
                    ((StringBuilder)object4).append(string2);
                    ((StringBuilder)object4).append(HASH_SALT_GEN);
                    object3 = object3.putInt(((StringBuilder)object4).toString(), this.mSaltGen);
                    object4 = new StringBuilder();
                    ((StringBuilder)object4).append(string2);
                    ((StringBuilder)object4).append(HASH_SALT_DATE);
                    object3.putLong(((StringBuilder)object4).toString(), System.currentTimeMillis()).apply();
                }
                this.mSalt = object.getBytes(UTF_8);
            }
            return;
        }
    }

    public HashResult hashString(Context object, String string2, String string3, int n) {
        if (n != -1 && object != null && !TextUtils.isEmpty(string3) && !TextUtils.isEmpty(string2)) {
            this.populateSaltValues((Context)object, string2, n);
            object = this.mHashes.get(string3);
            if (object != null) {
                return new HashResult((String)object, this.mSaltGen);
            }
            this.mDigester.reset();
            this.mDigester.update(this.mSalt);
            this.mDigester.update(string3.getBytes(UTF_8));
            object = this.mDigester.digest();
            object = Base64.encodeToString(object, 0, Math.min(8, ((byte[])object).length), 3);
            this.mHashes.put(string3, (String)object);
            return new HashResult((String)object, this.mSaltGen);
        }
        return null;
    }

    public class HashResult {
        public String hashedString;
        public int saltGeneration;

        public HashResult(String string2, int n) {
            this.hashedString = string2;
            this.saltGeneration = n;
        }
    }

}

