/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.util.HexEncoding
 */
package com.android.internal.widget;

import android.annotation.UnsupportedAppUsage;
import android.app.admin.DevicePolicyManager;
import android.app.admin.PasswordMetrics;
import android.app.trust.IStrongAuthTracker;
import android.app.trust.TrustManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.UserInfo;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.UserHandle;
import android.os.UserManager;
import android.os.storage.IStorageManager;
import android.os.storage.StorageManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseIntArray;
import android.util.SparseLongArray;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.widget.ICheckCredentialProgressCallback;
import com.android.internal.widget.ILockSettings;
import com.android.internal.widget.LockPatternView;
import com.android.internal.widget.LockSettingsInternal;
import com.android.internal.widget.VerifyCredentialResponse;
import com.android.internal.widget._$$Lambda$gPQuiuEDuOmrh2MixBcV6a5gu5s;
import com.android.server.LocalServices;
import com.google.android.collect.Lists;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;
import libcore.util.HexEncoding;

public class LockPatternUtils {
    @Deprecated
    public static final String BIOMETRIC_WEAK_EVER_CHOSEN_KEY = "lockscreen.biometricweakeverchosen";
    public static final int CREDENTIAL_TYPE_NONE = -1;
    public static final int CREDENTIAL_TYPE_PASSWORD = 2;
    public static final int CREDENTIAL_TYPE_PATTERN = 1;
    public static final String DISABLE_LOCKSCREEN_KEY = "lockscreen.disabled";
    private static final String ENABLED_TRUST_AGENTS = "lockscreen.enabledtrustagents";
    public static final int FAILED_ATTEMPTS_BEFORE_WIPE_GRACE = 5;
    public static final long FAILED_ATTEMPT_COUNTDOWN_INTERVAL_MS = 1000L;
    private static final boolean FRP_CREDENTIAL_ENABLED = true;
    private static final String HISTORY_DELIMITER = ",";
    private static final String IS_TRUST_USUALLY_MANAGED = "lockscreen.istrustusuallymanaged";
    public static final String LEGACY_LOCK_PATTERN_ENABLED = "legacy_lock_pattern_enabled";
    @Deprecated
    public static final String LOCKOUT_PERMANENT_KEY = "lockscreen.lockedoutpermanently";
    @Deprecated
    public static final String LOCKSCREEN_BIOMETRIC_WEAK_FALLBACK = "lockscreen.biometric_weak_fallback";
    public static final String LOCKSCREEN_OPTIONS = "lockscreen.options";
    public static final String LOCKSCREEN_POWER_BUTTON_INSTANTLY_LOCKS = "lockscreen.power_button_instantly_locks";
    @Deprecated
    public static final String LOCKSCREEN_WIDGETS_ENABLED = "lockscreen.widgets_enabled";
    public static final String LOCK_PASSWORD_SALT_KEY = "lockscreen.password_salt";
    private static final String LOCK_SCREEN_DEVICE_OWNER_INFO = "lockscreen.device_owner_info";
    private static final String LOCK_SCREEN_OWNER_INFO = "lock_screen_owner_info";
    private static final String LOCK_SCREEN_OWNER_INFO_ENABLED = "lock_screen_owner_info_enabled";
    public static final int MIN_LOCK_PASSWORD_SIZE = 4;
    public static final int MIN_LOCK_PATTERN_SIZE = 4;
    public static final int MIN_PATTERN_REGISTER_FAIL = 4;
    public static final String PASSWORD_HISTORY_KEY = "lockscreen.passwordhistory";
    @Deprecated
    public static final String PASSWORD_TYPE_ALTERNATE_KEY = "lockscreen.password_type_alternate";
    public static final String PASSWORD_TYPE_KEY = "lockscreen.password_type";
    public static final String PATTERN_EVER_CHOSEN_KEY = "lockscreen.patterneverchosen";
    public static final String PROFILE_KEY_NAME_DECRYPT = "profile_key_name_decrypt_";
    public static final String PROFILE_KEY_NAME_ENCRYPT = "profile_key_name_encrypt_";
    public static final String SYNTHETIC_PASSWORD_ENABLED_KEY = "enable-sp";
    public static final String SYNTHETIC_PASSWORD_HANDLE_KEY = "sp-handle";
    public static final String SYNTHETIC_PASSWORD_KEY_PREFIX = "synthetic_password_";
    private static final String TAG = "LockPatternUtils";
    public static final int USER_FRP = -9999;
    @UnsupportedAppUsage
    private final ContentResolver mContentResolver;
    @UnsupportedAppUsage
    private final Context mContext;
    private DevicePolicyManager mDevicePolicyManager;
    private final Handler mHandler;
    private Boolean mHasSecureLockScreen;
    private ILockSettings mLockSettingsService;
    private final SparseLongArray mLockoutDeadlines = new SparseLongArray();
    private UserManager mUserManager;

    @UnsupportedAppUsage
    public LockPatternUtils(Context object) {
        this.mContext = object;
        this.mContentResolver = ((Context)object).getContentResolver();
        object = Looper.myLooper();
        object = object != null ? new Handler((Looper)object) : null;
        this.mHandler = object;
    }

    public static List<LockPatternView.Cell> byteArrayToPattern(byte[] arrby) {
        if (arrby == null) {
            return null;
        }
        ArrayList<LockPatternView.Cell> arrayList = Lists.newArrayList();
        for (int i = 0; i < arrby.length; ++i) {
            byte by = (byte)(arrby[i] - 49);
            arrayList.add(LockPatternView.Cell.of(by / 3, by % 3));
        }
        return arrayList;
    }

    public static byte[] charSequenceToByteArray(CharSequence charSequence) {
        if (charSequence == null) {
            return null;
        }
        byte[] arrby = new byte[charSequence.length()];
        for (int i = 0; i < charSequence.length(); ++i) {
            arrby[i] = (byte)charSequence.charAt(i);
        }
        return arrby;
    }

    private boolean checkCredential(byte[] object, int n, int n2, CheckCredentialProgressCallback object2) throws RequestThrottledException {
        block5 : {
            block4 : {
                try {
                    object2 = this.getLockSettings().checkCredential((byte[])object, n, n2, this.wrapCallback((CheckCredentialProgressCallback)object2));
                    if (((VerifyCredentialResponse)object2).getResponseCode() != 0) break block4;
                    return true;
                }
                catch (RemoteException remoteException) {
                    return false;
                }
            }
            if (((VerifyCredentialResponse)object2).getResponseCode() == 1) break block5;
            return false;
        }
        object = new RequestThrottledException(((VerifyCredentialResponse)object2).getTimeout());
        throw object;
    }

    private int computeKeyguardQuality(int n, int n2, int n3) {
        block0 : {
            if (n != 2) break block0;
            n3 = Math.max(n3, n2);
        }
        return n3;
    }

    public static boolean frpCredentialEnabled(Context context) {
        return context.getResources().getBoolean(17891435);
    }

    private boolean getBoolean(String string2, boolean bl, int n) {
        try {
            boolean bl2 = this.getLockSettings().getBoolean(string2, bl, n);
            return bl2;
        }
        catch (RemoteException remoteException) {
            return bl;
        }
    }

    private LockSettingsInternal getLockSettingsInternal() {
        LockSettingsInternal lockSettingsInternal = LocalServices.getService(LockSettingsInternal.class);
        if (lockSettingsInternal != null) {
            return lockSettingsInternal;
        }
        throw new SecurityException("Only available to system server itself");
    }

    private long getLong(String string2, long l, int n) {
        try {
            long l2 = this.getLockSettings().getLong(string2, l, n);
            return l2;
        }
        catch (RemoteException remoteException) {
            return l;
        }
    }

    private int getRequestedPasswordHistoryLength(int n) {
        return this.getDevicePolicyManager().getPasswordHistoryLength(null, n);
    }

    private String getSalt(int n) {
        long l;
        long l2 = l = this.getLong(LOCK_PASSWORD_SALT_KEY, 0L, n);
        if (l == 0L) {
            try {
                l2 = SecureRandom.getInstance("SHA1PRNG").nextLong();
                this.setLong(LOCK_PASSWORD_SALT_KEY, l2, n);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Initialized lock password salt for user: ");
                stringBuilder.append(n);
                Log.v(TAG, stringBuilder.toString());
            }
            catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                throw new IllegalStateException("Couldn't get SecureRandom number", noSuchAlgorithmException);
            }
        }
        return Long.toHexString(l2);
    }

    @UnsupportedAppUsage
    private String getString(String string2, int n) {
        try {
            string2 = this.getLockSettings().getString(string2, null, n);
            return string2;
        }
        catch (RemoteException remoteException) {
            return null;
        }
    }

    private TrustManager getTrustManager() {
        TrustManager trustManager = (TrustManager)this.mContext.getSystemService("trust");
        if (trustManager == null) {
            Log.e(TAG, "Can't get TrustManagerService: is it running?", new IllegalStateException("Stack trace:"));
        }
        return trustManager;
    }

    private UserManager getUserManager() {
        if (this.mUserManager == null) {
            this.mUserManager = UserManager.get(this.mContext);
        }
        return this.mUserManager;
    }

    private boolean hasSeparateChallenge(int n) {
        try {
            boolean bl = this.getLockSettings().getSeparateProfileChallengeEnabled(n);
            return bl;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "Couldn't get separate profile challenge enabled");
            return false;
        }
    }

    @UnsupportedAppUsage
    public static boolean isDeviceEncryptionEnabled() {
        return StorageManager.isEncrypted();
    }

    private boolean isDoNotAskCredentialsOnBootSet() {
        return this.getDevicePolicyManager().getDoNotAskCredentialsOnBoot();
    }

    public static boolean isFileEncryptionEnabled() {
        return StorageManager.isFileEncryptedNativeOrEmulated();
    }

    private boolean isLockPasswordEnabled(int n, int n2) {
        boolean bl = false;
        n = n != 262144 && n != 131072 && n != 196608 && n != 327680 && n != 393216 && n != 524288 ? 0 : 1;
        boolean bl2 = bl;
        if (n != 0) {
            bl2 = bl;
            if (this.savedPasswordExists(n2)) {
                bl2 = true;
            }
        }
        return bl2;
    }

    private boolean isLockPatternEnabled(int n, int n2) {
        boolean bl = n == 65536 && this.savedPatternExists(n2);
        return bl;
    }

    private boolean isManagedProfile(int n) {
        UserInfo userInfo = this.getUserManager().getUserInfo(n);
        boolean bl = userInfo != null && userInfo.isManagedProfile();
        return bl;
    }

    private void onAfterChangingPassword(int n) {
        this.getTrustManager().reportEnabledTrustAgentsChanged(n);
    }

    private String passwordToHistoryHash(byte[] object, byte[] arrby, int n) {
        if (object != null && ((byte[])object).length != 0 && arrby != null) {
            try {
                MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
                messageDigest.update(arrby);
                byte[] arrby2 = this.getSalt(n).getBytes();
                arrby = Arrays.copyOf(object, ((byte[])object).length + arrby2.length);
                System.arraycopy(arrby2, 0, arrby, ((byte[])object).length, arrby2.length);
                messageDigest.update(arrby);
                Arrays.fill(arrby, (byte)0);
                object = new String(HexEncoding.encode((byte[])messageDigest.digest()));
                return object;
            }
            catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                throw new AssertionError("Missing digest algorithm: ", noSuchAlgorithmException);
            }
        }
        return null;
    }

    public static byte[] patternByteArrayToBaseZero(byte[] arrby) {
        if (arrby == null) {
            return new byte[0];
        }
        int n = arrby.length;
        byte[] arrby2 = new byte[n];
        for (int i = 0; i < n; ++i) {
            arrby2[i] = (byte)(arrby[i] - 49);
        }
        return arrby2;
    }

    public static byte[] patternToByteArray(List<LockPatternView.Cell> list) {
        if (list == null) {
            return new byte[0];
        }
        int n = list.size();
        byte[] arrby = new byte[n];
        for (int i = 0; i < n; ++i) {
            LockPatternView.Cell cell = list.get(i);
            arrby[i] = (byte)(cell.getRow() * 3 + cell.getColumn() + 49);
        }
        return arrby;
    }

    @UnsupportedAppUsage
    public static byte[] patternToHash(List<LockPatternView.Cell> arrby) {
        if (arrby == null) {
            return null;
        }
        int n = arrby.size();
        byte[] arrby2 = new byte[n];
        for (int i = 0; i < n; ++i) {
            LockPatternView.Cell cell = arrby.get(i);
            arrby2[i] = (byte)(cell.getRow() * 3 + cell.getColumn());
        }
        try {
            arrby = MessageDigest.getInstance("SHA-1").digest(arrby2);
            return arrby;
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            return arrby2;
        }
    }

    @Deprecated
    @UnsupportedAppUsage
    public static String patternToString(List<LockPatternView.Cell> list) {
        return new String(LockPatternUtils.patternToByteArray(list));
    }

    private boolean savedPasswordExists(int n) {
        try {
            boolean bl = this.getLockSettings().havePassword(n);
            return bl;
        }
        catch (RemoteException remoteException) {
            return false;
        }
    }

    private boolean savedPatternExists(int n) {
        try {
            boolean bl = this.getLockSettings().havePattern(n);
            return bl;
        }
        catch (RemoteException remoteException) {
            return false;
        }
    }

    private void setBoolean(String string2, boolean bl, int n) {
        try {
            this.getLockSettings().setBoolean(string2, bl, n);
        }
        catch (RemoteException remoteException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Couldn't write boolean ");
            stringBuilder.append(string2);
            stringBuilder.append(remoteException);
            Log.e(TAG, stringBuilder.toString());
        }
    }

    private void setKeyguardStoredPasswordQuality(int n, int n2) {
        this.setLong(PASSWORD_TYPE_KEY, n, n2);
    }

    @UnsupportedAppUsage
    private void setLong(String string2, long l, int n) {
        try {
            this.getLockSettings().setLong(string2, l, n);
        }
        catch (RemoteException remoteException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Couldn't write long ");
            stringBuilder.append(string2);
            stringBuilder.append(remoteException);
            Log.e(TAG, stringBuilder.toString());
        }
    }

    @UnsupportedAppUsage
    private void setString(String string2, String charSequence, int n) {
        try {
            this.getLockSettings().setString(string2, (String)charSequence, n);
        }
        catch (RemoteException remoteException) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Couldn't write string ");
            ((StringBuilder)charSequence).append(string2);
            ((StringBuilder)charSequence).append(remoteException);
            Log.e(TAG, ((StringBuilder)charSequence).toString());
        }
    }

    private boolean shouldEncryptWithCredentials(boolean bl) {
        bl = this.isCredentialRequiredToDecrypt(bl) && !this.isDoNotAskCredentialsOnBootSet();
        return bl;
    }

    @Deprecated
    public static List<LockPatternView.Cell> stringToPattern(String string2) {
        if (string2 == null) {
            return null;
        }
        return LockPatternUtils.byteArrayToPattern(string2.getBytes());
    }

    private void throwIfCalledOnMainThread() {
        if (!Looper.getMainLooper().isCurrentThread()) {
            return;
        }
        throw new IllegalStateException("should not be called from the main thread.");
    }

    private void updateCryptoUserInfo(int n) {
        if (n != 0) {
            return;
        }
        String string2 = this.isOwnerInfoEnabled(n) ? this.getOwnerInfo(n) : "";
        Object object = ServiceManager.getService("mount");
        if (object == null) {
            Log.e(TAG, "Could not find the mount service to update the user info");
            return;
        }
        object = IStorageManager.Stub.asInterface((IBinder)object);
        try {
            Log.d(TAG, "Setting owner info");
            object.setField("OwnerInfo", string2);
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "Error changing user info", remoteException);
        }
    }

    private void updateEncryptionPassword(final int n, byte[] object) {
        if (this.hasSecureLockScreen()) {
            if (!LockPatternUtils.isDeviceEncryptionEnabled()) {
                return;
            }
            final IBinder iBinder = ServiceManager.getService("mount");
            if (iBinder == null) {
                Log.e(TAG, "Could not find the mount service to update the encryption password");
                return;
            }
            object = object != null ? new String((byte[])object) : null;
            new AsyncTask<Void, Void, Void>((String)object){
                final /* synthetic */ String val$passwordString;
                {
                    this.val$passwordString = string2;
                }

                protected Void doInBackground(Void ... object) {
                    object = IStorageManager.Stub.asInterface(iBinder);
                    try {
                        object.changeEncryptionPassword(n, this.val$passwordString);
                    }
                    catch (RemoteException remoteException) {
                        Log.e(LockPatternUtils.TAG, "Error changing encryption password", remoteException);
                    }
                    return null;
                }
            }.execute(new Void[0]);
            return;
        }
        throw new UnsupportedOperationException("This operation requires the lock screen feature.");
    }

    private void updateEncryptionPasswordIfNeeded(byte[] arrby, int n, int n2) {
        if (n2 == 0 && LockPatternUtils.isDeviceEncryptionEnabled()) {
            int n3 = 1;
            if (!this.shouldEncryptWithCredentials(true)) {
                this.clearEncryptionPassword();
            } else {
                int n4 = 0;
                n2 = n == 131072 ? 1 : 0;
                n = n == 196608 ? n3 : 0;
                n = n2 == 0 && n == 0 ? n4 : 3;
                this.updateEncryptionPassword(n, arrby);
            }
        }
    }

    private void updatePasswordHistory(byte[] object, int n) {
        if (object != null && ((byte[])object).length != 0) {
            int n2;
            Object object2;
            String[] arrstring = object2 = this.getString(PASSWORD_HISTORY_KEY, n);
            if (object2 == null) {
                arrstring = "";
            }
            if ((n2 = this.getRequestedPasswordHistoryLength(n)) == 0) {
                object = "";
            } else {
                String string2 = this.passwordToHistoryHash((byte[])object, this.getPasswordHistoryHashFactor((byte[])object, n), n);
                object2 = string2;
                if (string2 == null) {
                    Log.e(TAG, "Compute new style password hash failed, fallback to legacy style");
                    object2 = this.legacyPasswordToHash((byte[])object, n);
                }
                if (TextUtils.isEmpty((CharSequence)arrstring)) {
                    object = object2;
                } else {
                    arrstring = arrstring.split(HISTORY_DELIMITER);
                    object = new StringJoiner(HISTORY_DELIMITER);
                    ((StringJoiner)object).add((CharSequence)object2);
                    for (int i = 0; i < n2 - 1 && i < arrstring.length; ++i) {
                        ((StringJoiner)object).add(arrstring[i]);
                    }
                    object = ((StringJoiner)object).toString();
                }
            }
            this.setString(PASSWORD_HISTORY_KEY, (String)object, n);
            return;
        }
        Log.e(TAG, "checkPasswordHistory: empty password");
    }

    public static boolean userOwnsFrpCredential(Context context, UserInfo userInfo) {
        boolean bl = userInfo != null && userInfo.isPrimary() && userInfo.isAdmin() && LockPatternUtils.frpCredentialEnabled(context);
        return bl;
    }

    private byte[] verifyCredential(byte[] object, int n, long l, int n2) throws RequestThrottledException {
        VerifyCredentialResponse verifyCredentialResponse;
        block4 : {
            try {
                verifyCredentialResponse = this.getLockSettings().verifyCredential((byte[])object, n, l, n2);
                if (verifyCredentialResponse.getResponseCode() == 0) {
                    return verifyCredentialResponse.getPayload();
                }
                if (verifyCredentialResponse.getResponseCode() == 1) break block4;
                return null;
            }
            catch (RemoteException remoteException) {
                return null;
            }
        }
        object = new RequestThrottledException(verifyCredentialResponse.getTimeout());
        throw object;
    }

    private ICheckCredentialProgressCallback wrapCallback(final CheckCredentialProgressCallback checkCredentialProgressCallback) {
        if (checkCredentialProgressCallback == null) {
            return null;
        }
        if (this.mHandler != null) {
            return new ICheckCredentialProgressCallback.Stub(){

                @Override
                public void onCredentialVerified() throws RemoteException {
                    Handler handler = LockPatternUtils.this.mHandler;
                    CheckCredentialProgressCallback checkCredentialProgressCallback2 = checkCredentialProgressCallback;
                    Objects.requireNonNull(checkCredentialProgressCallback2);
                    handler.post(new _$$Lambda$gPQuiuEDuOmrh2MixBcV6a5gu5s(checkCredentialProgressCallback2));
                }
            };
        }
        throw new IllegalStateException("Must construct LockPatternUtils on a looper thread to use progress callbacks.");
    }

    public long addEscrowToken(byte[] arrby, int n, EscrowTokenStateChangeCallback escrowTokenStateChangeCallback) {
        return this.getLockSettingsInternal().addEscrowToken(arrby, n, escrowTokenStateChangeCallback);
    }

    @UnsupportedAppUsage
    public boolean checkPassword(String object, int n) throws RequestThrottledException {
        object = object != null ? object.getBytes() : null;
        return this.checkPassword((byte[])object, n, null);
    }

    public boolean checkPassword(String object, int n, CheckCredentialProgressCallback checkCredentialProgressCallback) throws RequestThrottledException {
        object = object != null ? object.getBytes() : null;
        this.throwIfCalledOnMainThread();
        return this.checkCredential((byte[])object, 2, n, checkCredentialProgressCallback);
    }

    public boolean checkPassword(byte[] arrby, int n) throws RequestThrottledException {
        return this.checkPassword(arrby, n, null);
    }

    public boolean checkPassword(byte[] arrby, int n, CheckCredentialProgressCallback checkCredentialProgressCallback) throws RequestThrottledException {
        this.throwIfCalledOnMainThread();
        return this.checkCredential(arrby, 2, n, checkCredentialProgressCallback);
    }

    public boolean checkPasswordHistory(byte[] object, byte[] arrobject, int n) {
        if (object != null && ((byte[])object).length != 0) {
            String string2 = this.getString(PASSWORD_HISTORY_KEY, n);
            if (TextUtils.isEmpty(string2)) {
                return false;
            }
            int n2 = this.getRequestedPasswordHistoryLength(n);
            if (n2 == 0) {
                return false;
            }
            String string3 = this.legacyPasswordToHash((byte[])object, n);
            object = this.passwordToHistoryHash((byte[])object, (byte[])arrobject, n);
            arrobject = string2.split(HISTORY_DELIMITER);
            for (n = 0; n < Math.min(n2, arrobject.length); ++n) {
                if (!arrobject[n].equals(string3) && !arrobject[n].equals(object)) {
                    continue;
                }
                return true;
            }
            return false;
        }
        Log.e(TAG, "checkPasswordHistory: empty password");
        return false;
    }

    public boolean checkPattern(List<LockPatternView.Cell> list, int n) throws RequestThrottledException {
        return this.checkPattern(list, n, null);
    }

    public boolean checkPattern(List<LockPatternView.Cell> list, int n, CheckCredentialProgressCallback checkCredentialProgressCallback) throws RequestThrottledException {
        this.throwIfCalledOnMainThread();
        return this.checkCredential(LockPatternUtils.patternToByteArray(list), 1, n, checkCredentialProgressCallback);
    }

    public boolean checkVoldPassword(int n) {
        try {
            boolean bl = this.getLockSettings().checkVoldPassword(n);
            return bl;
        }
        catch (RemoteException remoteException) {
            return false;
        }
    }

    public void clearEncryptionPassword() {
        this.updateEncryptionPassword(1, null);
    }

    public boolean clearLock(byte[] arrby, int n) {
        return this.clearLock(arrby, n, false);
    }

    public boolean clearLock(byte[] arrby, int n, boolean bl) {
        int n2 = this.getKeyguardStoredPasswordQuality(n);
        this.setKeyguardStoredPasswordQuality(0, n);
        try {
            this.getLockSettings().setLockCredential(null, -1, arrby, 0, n, bl);
            if (n == 0) {
                this.updateEncryptionPassword(1, null);
                this.setCredentialRequiredToDecrypt(false);
            }
            this.onAfterChangingPassword(n);
            return true;
        }
        catch (Exception exception) {
            Log.e(TAG, "Failed to clear lock", exception);
            this.setKeyguardStoredPasswordQuality(n2, n);
            return false;
        }
    }

    public void disableSyntheticPassword() {
        this.setLong(SYNTHETIC_PASSWORD_ENABLED_KEY, 0L, 0);
    }

    public void enableSyntheticPassword() {
        this.setLong(SYNTHETIC_PASSWORD_ENABLED_KEY, 1L, 0);
    }

    @UnsupportedAppUsage
    public int getActivePasswordQuality(int n) {
        int n2 = this.getKeyguardStoredPasswordQuality(n);
        if (this.isLockPasswordEnabled(n2, n)) {
            return n2;
        }
        if (this.isLockPatternEnabled(n2, n)) {
            return n2;
        }
        return 0;
    }

    public int getCurrentFailedPasswordAttempts(int n) {
        if (n == -9999 && LockPatternUtils.frpCredentialEnabled(this.mContext)) {
            return 0;
        }
        return this.getDevicePolicyManager().getCurrentFailedPasswordAttempts(n);
    }

    public String getDeviceOwnerInfo() {
        return this.getString(LOCK_SCREEN_DEVICE_OWNER_INFO, 0);
    }

    @UnsupportedAppUsage
    public DevicePolicyManager getDevicePolicyManager() {
        if (this.mDevicePolicyManager == null) {
            this.mDevicePolicyManager = (DevicePolicyManager)this.mContext.getSystemService("device_policy");
            if (this.mDevicePolicyManager == null) {
                Log.e(TAG, "Can't get DevicePolicyManagerService: is it running?", new IllegalStateException("Stack trace:"));
            }
        }
        return this.mDevicePolicyManager;
    }

    public List<ComponentName> getEnabledTrustAgents(int n) {
        Object object = this.getString(ENABLED_TRUST_AGENTS, n);
        if (TextUtils.isEmpty((CharSequence)object)) {
            return null;
        }
        String[] arrstring = ((String)object).split(HISTORY_DELIMITER);
        object = new ArrayList(arrstring.length);
        for (String string2 : arrstring) {
            if (TextUtils.isEmpty(string2)) continue;
            ((ArrayList)object).add(ComponentName.unflattenFromString(string2));
        }
        return object;
    }

    @UnsupportedAppUsage
    public int getKeyguardStoredPasswordQuality(int n) {
        return (int)this.getLong(PASSWORD_TYPE_KEY, 0L, n);
    }

    @UnsupportedAppUsage
    @VisibleForTesting
    public ILockSettings getLockSettings() {
        if (this.mLockSettingsService == null) {
            this.mLockSettingsService = ILockSettings.Stub.asInterface(ServiceManager.getService("lock_settings"));
        }
        return this.mLockSettingsService;
    }

    public long getLockoutAttemptDeadline(int n) {
        long l = this.mLockoutDeadlines.get(n, 0L);
        if (l < SystemClock.elapsedRealtime() && l != 0L) {
            this.mLockoutDeadlines.put(n, 0L);
            return 0L;
        }
        return l;
    }

    public int getMaximumFailedPasswordsForWipe(int n) {
        if (n == -9999 && LockPatternUtils.frpCredentialEnabled(this.mContext)) {
            return 0;
        }
        return this.getDevicePolicyManager().getMaximumFailedPasswordsForWipe(null, n);
    }

    public int getMaximumPasswordLength(int n) {
        return this.getDevicePolicyManager().getPasswordMaximumLength(n);
    }

    @UnsupportedAppUsage
    public String getOwnerInfo(int n) {
        return this.getString(LOCK_SCREEN_OWNER_INFO, n);
    }

    public byte[] getPasswordHistoryHashFactor(byte[] arrby, int n) {
        try {
            arrby = this.getLockSettings().getHashFactor(arrby, n);
            return arrby;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "failed to get hash factor", remoteException);
            return null;
        }
    }

    @UnsupportedAppUsage
    public boolean getPowerButtonInstantlyLocks(int n) {
        return this.getBoolean(LOCKSCREEN_POWER_BUTTON_INSTANTLY_LOCKS, true, n);
    }

    public int getRequestedMinimumPasswordLength(int n) {
        return this.getDevicePolicyManager().getPasswordMinimumLength(null, n);
    }

    public int getRequestedPasswordMinimumLetters(int n) {
        return this.getDevicePolicyManager().getPasswordMinimumLetters(null, n);
    }

    public int getRequestedPasswordMinimumLowerCase(int n) {
        return this.getDevicePolicyManager().getPasswordMinimumLowerCase(null, n);
    }

    public int getRequestedPasswordMinimumNonLetter(int n) {
        return this.getDevicePolicyManager().getPasswordMinimumNonLetter(null, n);
    }

    public int getRequestedPasswordMinimumNumeric(int n) {
        return this.getDevicePolicyManager().getPasswordMinimumNumeric(null, n);
    }

    public int getRequestedPasswordMinimumSymbols(int n) {
        return this.getDevicePolicyManager().getPasswordMinimumSymbols(null, n);
    }

    public int getRequestedPasswordMinimumUpperCase(int n) {
        return this.getDevicePolicyManager().getPasswordMinimumUpperCase(null, n);
    }

    public int getRequestedPasswordQuality(int n) {
        return this.getDevicePolicyManager().getPasswordQuality(null, n);
    }

    public int getStrongAuthForUser(int n) {
        try {
            n = this.getLockSettings().getStrongAuthForUser(n);
            return n;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "Could not get StrongAuth", remoteException);
            return StrongAuthTracker.getDefaultFlags(this.mContext);
        }
    }

    public boolean hasPendingEscrowToken(int n) {
        try {
            boolean bl = this.getLockSettings().hasPendingEscrowToken(n);
            return bl;
        }
        catch (RemoteException remoteException) {
            remoteException.rethrowFromSystemServer();
            return false;
        }
    }

    public boolean hasSecureLockScreen() {
        if (this.mHasSecureLockScreen == null) {
            this.mHasSecureLockScreen = this.mContext.getPackageManager().hasSystemFeature("android.software.secure_lock_screen");
        }
        return this.mHasSecureLockScreen;
    }

    public boolean isBiometricAllowedForUser(int n) {
        boolean bl = (this.getStrongAuthForUser(n) & -5) == 0;
        return bl;
    }

    public boolean isCredentialRequiredToDecrypt(boolean bl) {
        int n = Settings.Global.getInt(this.mContentResolver, "require_password_to_decrypt", -1);
        if (n != -1) {
            bl = n != 0;
        }
        return bl;
    }

    public boolean isDeviceOwnerInfoEnabled() {
        boolean bl = this.getDeviceOwnerInfo() != null;
        return bl;
    }

    public boolean isEscrowTokenActive(long l, int n) {
        return this.getLockSettingsInternal().isEscrowTokenActive(l, n);
    }

    @Deprecated
    public boolean isLegacyLockPatternEnabled(int n) {
        return this.getBoolean(LEGACY_LOCK_PATTERN_ENABLED, true, n);
    }

    @UnsupportedAppUsage
    public boolean isLockPasswordEnabled(int n) {
        return this.isLockPasswordEnabled(this.getKeyguardStoredPasswordQuality(n), n);
    }

    @UnsupportedAppUsage
    public boolean isLockPatternEnabled(int n) {
        return this.isLockPatternEnabled(this.getKeyguardStoredPasswordQuality(n), n);
    }

    @UnsupportedAppUsage
    public boolean isLockScreenDisabled(int n) {
        boolean bl = this.isSecure(n);
        boolean bl2 = false;
        if (bl) {
            return false;
        }
        bl = this.mContext.getResources().getBoolean(17891406);
        boolean bl3 = UserManager.isSplitSystemUser() && n == 0;
        UserInfo userInfo = this.getUserManager().getUserInfo(n);
        boolean bl4 = UserManager.isDeviceInDemoMode(this.mContext) && userInfo != null && userInfo.isDemo();
        if (this.getBoolean(DISABLE_LOCKSCREEN_KEY, false, n) || bl && !bl3 || bl4) {
            bl2 = true;
        }
        return bl2;
    }

    public boolean isManagedProfileWithUnifiedChallenge(int n) {
        boolean bl = this.isManagedProfile(n) && !this.hasSeparateChallenge(n);
        return bl;
    }

    public boolean isOwnerInfoEnabled(int n) {
        return this.getBoolean(LOCK_SCREEN_OWNER_INFO_ENABLED, false, n);
    }

    public boolean isPatternEverChosen(int n) {
        return this.getBoolean(PATTERN_EVER_CHOSEN_KEY, false, n);
    }

    public boolean isPowerButtonInstantlyLocksEverChosen(int n) {
        boolean bl = this.getString(LOCKSCREEN_POWER_BUTTON_INSTANTLY_LOCKS, n) != null;
        return bl;
    }

    @UnsupportedAppUsage
    public boolean isSecure(int n) {
        int n2 = this.getKeyguardStoredPasswordQuality(n);
        boolean bl = this.isLockPatternEnabled(n2, n) || this.isLockPasswordEnabled(n2, n);
        return bl;
    }

    public boolean isSeparateProfileChallengeAllowed(int n) {
        boolean bl = this.isManagedProfile(n) && this.getDevicePolicyManager().isSeparateProfileChallengeAllowed(n);
        return bl;
    }

    public boolean isSeparateProfileChallengeAllowedToUnify(int n) {
        boolean bl = this.getDevicePolicyManager().isProfileActivePasswordSufficientForParent(n) && !this.getUserManager().hasUserRestriction("no_unified_password", UserHandle.of(n));
        return bl;
    }

    public boolean isSeparateProfileChallengeEnabled(int n) {
        boolean bl = this.isManagedProfile(n) && this.hasSeparateChallenge(n);
        return bl;
    }

    public boolean isSyntheticPasswordEnabled() {
        boolean bl = false;
        if (this.getLong(SYNTHETIC_PASSWORD_ENABLED_KEY, 0L, 0) != 0L) {
            bl = true;
        }
        return bl;
    }

    @UnsupportedAppUsage
    public boolean isTactileFeedbackEnabled() {
        ContentResolver contentResolver = this.mContentResolver;
        boolean bl = true;
        if (Settings.System.getIntForUser(contentResolver, "haptic_feedback_enabled", 1, -2) == 0) {
            bl = false;
        }
        return bl;
    }

    public boolean isTrustAllowedForUser(int n) {
        boolean bl = this.getStrongAuthForUser(n) == 0;
        return bl;
    }

    public boolean isTrustUsuallyManaged(int n) {
        if (this.mLockSettingsService instanceof ILockSettings.Stub) {
            try {
                boolean bl = this.getLockSettings().getBoolean(IS_TRUST_USUALLY_MANAGED, false, n);
                return bl;
            }
            catch (RemoteException remoteException) {
                return false;
            }
        }
        throw new IllegalStateException("May only be called by TrustManagerService. Use TrustManager.isTrustUsuallyManaged()");
    }

    public boolean isUserInLockdown(int n) {
        boolean bl = this.getStrongAuthForUser(n) == 32;
        return bl;
    }

    @UnsupportedAppUsage
    public boolean isVisiblePatternEnabled(int n) {
        return this.getBoolean("lock_pattern_visible_pattern", false, n);
    }

    public boolean isVisiblePatternEverChosen(int n) {
        boolean bl = this.getString("lock_pattern_visible_pattern", n) != null;
        return bl;
    }

    public String legacyPasswordToHash(byte[] object, int n) {
        if (object != null && ((byte[])object).length != 0) {
            try {
                byte[] arrby = this.getSalt(n).getBytes();
                byte[] arrby2 = Arrays.copyOf(object, ((byte[])object).length + arrby.length);
                System.arraycopy(arrby, 0, arrby2, ((byte[])object).length, arrby.length);
                object = MessageDigest.getInstance("SHA-1").digest(arrby2);
                arrby = MessageDigest.getInstance("MD5").digest(arrby2);
                byte[] arrby3 = new byte[((byte[])object).length + arrby.length];
                System.arraycopy(object, 0, arrby3, 0, ((byte[])object).length);
                System.arraycopy(arrby, 0, arrby3, ((byte[])object).length, arrby.length);
                object = HexEncoding.encode((byte[])arrby3);
                Arrays.fill(arrby2, (byte)0);
                object = new String((char[])object);
                return object;
            }
            catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                throw new AssertionError("Missing digest algorithm: ", noSuchAlgorithmException);
            }
        }
        return null;
    }

    public void registerStrongAuthTracker(StrongAuthTracker strongAuthTracker) {
        try {
            this.getLockSettings().registerStrongAuthTracker(strongAuthTracker.mStub);
            return;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException("Could not register StrongAuthTracker");
        }
    }

    public boolean removeEscrowToken(long l, int n) {
        return this.getLockSettingsInternal().removeEscrowToken(l, n);
    }

    @UnsupportedAppUsage
    public void reportFailedPasswordAttempt(int n) {
        if (n == -9999 && LockPatternUtils.frpCredentialEnabled(this.mContext)) {
            return;
        }
        this.getDevicePolicyManager().reportFailedPasswordAttempt(n);
        this.getTrustManager().reportUnlockAttempt(false, n);
    }

    public void reportPasswordLockout(int n, int n2) {
        if (n2 == -9999 && LockPatternUtils.frpCredentialEnabled(this.mContext)) {
            return;
        }
        this.getTrustManager().reportUnlockLockout(n, n2);
    }

    public void reportPatternWasChosen(int n) {
        this.setBoolean(PATTERN_EVER_CHOSEN_KEY, true, n);
    }

    @UnsupportedAppUsage
    public void reportSuccessfulPasswordAttempt(int n) {
        if (n == -9999 && LockPatternUtils.frpCredentialEnabled(this.mContext)) {
            return;
        }
        this.getDevicePolicyManager().reportSuccessfulPasswordAttempt(n);
        this.getTrustManager().reportUnlockAttempt(true, n);
    }

    public void requireCredentialEntry(int n) {
        this.requireStrongAuth(4, n);
    }

    public void requireStrongAuth(int n, int n2) {
        try {
            this.getLockSettings().requireStrongAuth(n, n2);
        }
        catch (RemoteException remoteException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Error while requesting strong auth: ");
            stringBuilder.append(remoteException);
            Log.e(TAG, stringBuilder.toString());
        }
    }

    public void resetKeyStore(int n) {
        try {
            this.getLockSettings().resetKeyStore(n);
        }
        catch (RemoteException remoteException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Couldn't reset keystore ");
            stringBuilder.append(remoteException);
            Log.e(TAG, stringBuilder.toString());
        }
    }

    @Deprecated
    public boolean saveLockPassword(String object, String string2, int n, int n2) {
        byte[] arrby = null;
        object = object != null ? object.getBytes() : null;
        if (string2 != null) {
            arrby = string2.getBytes();
        }
        return this.saveLockPassword((byte[])object, arrby, n, n2);
    }

    public boolean saveLockPassword(byte[] arrby, byte[] arrby2, int n, int n2) {
        return this.saveLockPassword(arrby, arrby2, n, n2, false);
    }

    public boolean saveLockPassword(byte[] object, byte[] arrby, int n, int n2, boolean bl) {
        if (this.hasSecureLockScreen()) {
            if (object != null && ((byte[])object).length >= 4) {
                if (n >= 131072) {
                    int n3 = this.getKeyguardStoredPasswordQuality(n2);
                    int n4 = PasswordMetrics.computeForPassword((byte[])object).quality;
                    this.setKeyguardStoredPasswordQuality(this.computeKeyguardQuality(2, n, n4), n2);
                    try {
                        this.getLockSettings().setLockCredential((byte[])object, 2, arrby, n, n2, bl);
                        this.updateEncryptionPasswordIfNeeded((byte[])object, n4, n2);
                        this.updatePasswordHistory((byte[])object, n2);
                        this.onAfterChangingPassword(n2);
                        return true;
                    }
                    catch (Exception exception) {
                        Log.e(TAG, "Unable to save lock password", exception);
                        this.setKeyguardStoredPasswordQuality(n3, n2);
                        return false;
                    }
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("quality must be at least NUMERIC, but was ");
                ((StringBuilder)object).append(n);
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
            throw new IllegalArgumentException("password must not be null and at least of length 4");
        }
        throw new UnsupportedOperationException("This operation requires the lock screen feature.");
    }

    public boolean saveLockPattern(List<LockPatternView.Cell> list, byte[] arrby, int n) {
        return this.saveLockPattern(list, arrby, n, false);
    }

    public boolean saveLockPattern(List<LockPatternView.Cell> arrby, byte[] arrby2, int n, boolean bl) {
        if (this.hasSecureLockScreen()) {
            if (arrby != null && arrby.size() >= 4) {
                arrby = LockPatternUtils.patternToByteArray(arrby);
                int n2 = this.getKeyguardStoredPasswordQuality(n);
                this.setKeyguardStoredPasswordQuality(65536, n);
                try {
                    this.getLockSettings().setLockCredential(arrby, 1, arrby2, 65536, n, bl);
                }
                catch (Exception exception) {
                    Log.e(TAG, "Couldn't save lock pattern", exception);
                    this.setKeyguardStoredPasswordQuality(n2, n);
                    return false;
                }
                if (n == 0 && LockPatternUtils.isDeviceEncryptionEnabled()) {
                    if (!this.shouldEncryptWithCredentials(true)) {
                        this.clearEncryptionPassword();
                    } else {
                        this.updateEncryptionPassword(2, arrby);
                    }
                }
                this.reportPatternWasChosen(n);
                this.onAfterChangingPassword(n);
                return true;
            }
            throw new IllegalArgumentException("pattern must not be null and at least 4 dots long.");
        }
        throw new UnsupportedOperationException("This operation requires the lock screen feature.");
    }

    public void setCredentialRequiredToDecrypt(boolean bl) {
        if (!this.getUserManager().isSystemUser() && !this.getUserManager().isPrimaryUser()) {
            throw new IllegalStateException("Only the system or primary user may call setCredentialRequiredForDecrypt()");
        }
        if (LockPatternUtils.isDeviceEncryptionEnabled()) {
            ContentResolver contentResolver = this.mContext.getContentResolver();
            Settings.Global.putInt(contentResolver, "require_password_to_decrypt", (int)bl);
        }
    }

    public void setDeviceOwnerInfo(String string2) {
        String string3 = string2;
        if (string2 != null) {
            string3 = string2;
            if (string2.isEmpty()) {
                string3 = null;
            }
        }
        this.setString(LOCK_SCREEN_DEVICE_OWNER_INFO, string3, 0);
    }

    public void setEnabledTrustAgents(Collection<ComponentName> object, int n) {
        StringBuilder stringBuilder = new StringBuilder();
        object = object.iterator();
        while (object.hasNext()) {
            ComponentName componentName = (ComponentName)object.next();
            if (stringBuilder.length() > 0) {
                stringBuilder.append(',');
            }
            stringBuilder.append(componentName.flattenToShortString());
        }
        this.setString(ENABLED_TRUST_AGENTS, stringBuilder.toString(), n);
        this.getTrustManager().reportEnabledTrustAgentsChanged(n);
    }

    @Deprecated
    public void setLegacyLockPatternEnabled(int n) {
        this.setBoolean("lock_pattern_autolock", true, n);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean setLockCredentialWithToken(byte[] arrby, int n, int n2, long l, byte[] arrby2, int n3) {
        if (!this.hasSecureLockScreen()) throw new UnsupportedOperationException("This operation requires the lock screen feature.");
        LockSettingsInternal lockSettingsInternal = this.getLockSettingsInternal();
        if (n != -1) {
            if (arrby == null || arrby.length < 4) throw new IllegalArgumentException("password must not be null and at least of length 4");
            int n4 = PasswordMetrics.computeForCredential((int)n, (byte[])arrby).quality;
            if (!lockSettingsInternal.setLockCredentialWithToken(arrby, n, l, arrby2, this.computeKeyguardQuality(n, n4, n2), n3)) {
                return false;
            }
            this.setKeyguardStoredPasswordQuality(n4, n3);
            this.updateEncryptionPasswordIfNeeded(arrby, n4, n3);
            this.updatePasswordHistory(arrby, n3);
            this.onAfterChangingPassword(n3);
        } else {
            if (arrby != null && arrby.length != 0) {
                throw new IllegalArgumentException("password must be emtpy for NONE type");
            }
            if (!lockSettingsInternal.setLockCredentialWithToken(null, -1, l, arrby2, 0, n3)) {
                return false;
            }
            this.setKeyguardStoredPasswordQuality(0, n3);
            if (n3 == 0) {
                this.updateEncryptionPassword(1, null);
                this.setCredentialRequiredToDecrypt(false);
            }
        }
        this.onAfterChangingPassword(n3);
        return true;
    }

    public void setLockScreenDisabled(boolean bl, int n) {
        this.setBoolean(DISABLE_LOCKSCREEN_KEY, bl, n);
    }

    @UnsupportedAppUsage
    public long setLockoutAttemptDeadline(int n, int n2) {
        long l = SystemClock.elapsedRealtime() + (long)n2;
        if (n == -9999) {
            return l;
        }
        this.mLockoutDeadlines.put(n, l);
        return l;
    }

    @UnsupportedAppUsage
    public void setOwnerInfo(String string2, int n) {
        this.setString(LOCK_SCREEN_OWNER_INFO, string2, n);
        this.updateCryptoUserInfo(n);
    }

    @UnsupportedAppUsage
    public void setOwnerInfoEnabled(boolean bl, int n) {
        this.setBoolean(LOCK_SCREEN_OWNER_INFO_ENABLED, bl, n);
        this.updateCryptoUserInfo(n);
    }

    public void setPowerButtonInstantlyLocks(boolean bl, int n) {
        this.setBoolean(LOCKSCREEN_POWER_BUTTON_INSTANTLY_LOCKS, bl, n);
    }

    public void setSeparateProfileChallengeEnabled(int n, boolean bl, byte[] arrby) {
        if (!this.isManagedProfile(n)) {
            return;
        }
        try {
            this.getLockSettings().setSeparateProfileChallengeEnabled(n, bl, arrby);
            this.onAfterChangingPassword(n);
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "Couldn't update work profile challenge enabled");
        }
    }

    public void setTrustUsuallyManaged(boolean bl, int n) {
        try {
            this.getLockSettings().setBoolean(IS_TRUST_USUALLY_MANAGED, bl, n);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public void setVisiblePasswordEnabled(boolean bl, int n) {
        if (n != 0) {
            return;
        }
        Object object = ServiceManager.getService("mount");
        if (object == null) {
            Log.e(TAG, "Could not find the mount service to update the user info");
            return;
        }
        IStorageManager iStorageManager = IStorageManager.Stub.asInterface((IBinder)object);
        object = bl ? "1" : "0";
        try {
            iStorageManager.setField("PasswordVisible", (String)object);
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "Error changing password visible state", remoteException);
        }
    }

    public void setVisiblePatternEnabled(boolean bl, int n) {
        this.setBoolean("lock_pattern_visible_pattern", bl, n);
        if (n != 0) {
            return;
        }
        Object object = ServiceManager.getService("mount");
        if (object == null) {
            Log.e(TAG, "Could not find the mount service to update the user info");
            return;
        }
        IStorageManager iStorageManager = IStorageManager.Stub.asInterface((IBinder)object);
        object = bl ? "1" : "0";
        try {
            iStorageManager.setField("PatternVisible", (String)object);
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "Error changing pattern visible state", remoteException);
        }
    }

    public boolean unlockUserWithToken(long l, byte[] arrby, int n) {
        return this.getLockSettingsInternal().unlockUserWithToken(l, arrby, n);
    }

    public void unregisterStrongAuthTracker(StrongAuthTracker strongAuthTracker) {
        try {
            this.getLockSettings().unregisterStrongAuthTracker(strongAuthTracker.mStub);
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "Could not unregister StrongAuthTracker", remoteException);
        }
    }

    public void userPresent(int n) {
        try {
            this.getLockSettings().userPresent(n);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public byte[] verifyPassword(byte[] arrby, long l, int n) throws RequestThrottledException {
        this.throwIfCalledOnMainThread();
        return this.verifyCredential(arrby, 2, l, n);
    }

    public byte[] verifyPattern(List<LockPatternView.Cell> list, long l, int n) throws RequestThrottledException {
        this.throwIfCalledOnMainThread();
        return this.verifyCredential(LockPatternUtils.patternToByteArray(list), 1, l, n);
    }

    public byte[] verifyTiedProfileChallenge(byte[] object, boolean bl, long l, int n) throws RequestThrottledException {
        Object object2;
        block5 : {
            int n2;
            this.throwIfCalledOnMainThread();
            try {
                object2 = this.getLockSettings();
                n2 = bl ? 1 : 2;
            }
            catch (RemoteException remoteException) {
                return null;
            }
            object2 = object2.verifyTiedProfileChallenge((byte[])object, n2, l, n);
            if (((VerifyCredentialResponse)object2).getResponseCode() == 0) {
                return ((VerifyCredentialResponse)object2).getPayload();
            }
            if (((VerifyCredentialResponse)object2).getResponseCode() == 1) break block5;
            return null;
        }
        object = new RequestThrottledException(((VerifyCredentialResponse)object2).getTimeout());
        throw object;
    }

    public static interface CheckCredentialProgressCallback {
        public void onEarlyMatched();
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface CredentialType {
    }

    public static interface EscrowTokenStateChangeCallback {
        public void onEscrowTokenActivated(long var1, int var3);
    }

    public static final class RequestThrottledException
    extends Exception {
        private int mTimeoutMs;

        @UnsupportedAppUsage
        public RequestThrottledException(int n) {
            this.mTimeoutMs = n;
        }

        @UnsupportedAppUsage
        public int getTimeoutMs() {
            return this.mTimeoutMs;
        }
    }

    public static class StrongAuthTracker {
        private static final int ALLOWING_BIOMETRIC = 4;
        public static final int SOME_AUTH_REQUIRED_AFTER_USER_REQUEST = 4;
        public static final int STRONG_AUTH_NOT_REQUIRED = 0;
        public static final int STRONG_AUTH_REQUIRED_AFTER_BOOT = 1;
        public static final int STRONG_AUTH_REQUIRED_AFTER_DPM_LOCK_NOW = 2;
        public static final int STRONG_AUTH_REQUIRED_AFTER_LOCKOUT = 8;
        public static final int STRONG_AUTH_REQUIRED_AFTER_TIMEOUT = 16;
        public static final int STRONG_AUTH_REQUIRED_AFTER_USER_LOCKDOWN = 32;
        private final int mDefaultStrongAuthFlags;
        private final H mHandler;
        private final SparseIntArray mStrongAuthRequiredForUser = new SparseIntArray();
        protected final IStrongAuthTracker.Stub mStub = new IStrongAuthTracker.Stub(){

            @Override
            public void onStrongAuthRequiredChanged(int n, int n2) {
                StrongAuthTracker.this.mHandler.obtainMessage(1, n, n2).sendToTarget();
            }
        };

        public StrongAuthTracker(Context context) {
            this(context, Looper.myLooper());
        }

        public StrongAuthTracker(Context context, Looper looper) {
            this.mHandler = new H(looper);
            this.mDefaultStrongAuthFlags = StrongAuthTracker.getDefaultFlags(context);
        }

        public static int getDefaultFlags(Context context) {
            return (int)context.getResources().getBoolean(17891527);
        }

        public int getStrongAuthForUser(int n) {
            return this.mStrongAuthRequiredForUser.get(n, this.mDefaultStrongAuthFlags);
        }

        protected void handleStrongAuthRequiredChanged(int n, int n2) {
            if (n != this.getStrongAuthForUser(n2)) {
                if (n == this.mDefaultStrongAuthFlags) {
                    this.mStrongAuthRequiredForUser.delete(n2);
                } else {
                    this.mStrongAuthRequiredForUser.put(n2, n);
                }
                this.onStrongAuthRequiredChanged(n2);
            }
        }

        public boolean isBiometricAllowedForUser(int n) {
            boolean bl = (this.getStrongAuthForUser(n) & -5) == 0;
            return bl;
        }

        public boolean isTrustAllowedForUser(int n) {
            boolean bl = this.getStrongAuthForUser(n) == 0;
            return bl;
        }

        public void onStrongAuthRequiredChanged(int n) {
        }

        private class H
        extends Handler {
            static final int MSG_ON_STRONG_AUTH_REQUIRED_CHANGED = 1;

            public H(Looper looper) {
                super(looper);
            }

            @Override
            public void handleMessage(Message message) {
                if (message.what == 1) {
                    StrongAuthTracker.this.handleStrongAuthRequiredChanged(message.arg1, message.arg2);
                }
            }
        }

        @Retention(value=RetentionPolicy.SOURCE)
        public static @interface StrongAuthFlags {
        }

    }

}

