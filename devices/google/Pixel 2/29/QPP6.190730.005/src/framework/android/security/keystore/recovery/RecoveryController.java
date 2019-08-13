/*
 * Decompiled with CFR 0.145.
 */
package android.security.keystore.recovery;

import android.annotation.SystemApi;
import android.app.KeyguardManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.ServiceSpecificException;
import android.security.KeyStore;
import android.security.keystore.AndroidKeyStoreProvider;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.recovery.InternalRecoveryServiceException;
import android.security.keystore.recovery.KeyChainSnapshot;
import android.security.keystore.recovery.LockScreenRequiredException;
import android.security.keystore.recovery.RecoverySession;
import android.security.keystore.recovery.TrustedRootCertificates;
import com.android.internal.widget.ILockSettings;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SystemApi
public class RecoveryController {
    public static final int ERROR_BAD_CERTIFICATE_FORMAT = 25;
    public static final int ERROR_DECRYPTION_FAILED = 26;
    public static final int ERROR_DOWNGRADE_CERTIFICATE = 29;
    public static final int ERROR_INSECURE_USER = 23;
    public static final int ERROR_INVALID_CERTIFICATE = 28;
    public static final int ERROR_INVALID_KEY_FORMAT = 27;
    public static final int ERROR_NO_SNAPSHOT_PENDING = 21;
    public static final int ERROR_SERVICE_INTERNAL_ERROR = 22;
    public static final int ERROR_SESSION_EXPIRED = 24;
    public static final int RECOVERY_STATUS_PERMANENT_FAILURE = 3;
    public static final int RECOVERY_STATUS_SYNCED = 0;
    public static final int RECOVERY_STATUS_SYNC_IN_PROGRESS = 1;
    private static final String TAG = "RecoveryController";
    private final ILockSettings mBinder;
    private final KeyStore mKeyStore;

    private RecoveryController(ILockSettings iLockSettings, KeyStore keyStore) {
        this.mBinder = iLockSettings;
        this.mKeyStore = keyStore;
    }

    public static RecoveryController getInstance(Context context) {
        return new RecoveryController(ILockSettings.Stub.asInterface(ServiceManager.getService("lock_settings")), KeyStore.getInstance());
    }

    public static boolean isRecoverableKeyStoreEnabled(Context object) {
        boolean bl = (object = ((Context)object).getSystemService(KeyguardManager.class)) != null && ((KeyguardManager)object).isDeviceSecure();
        return bl;
    }

    public RecoverySession createRecoverySession() {
        return RecoverySession.newInstance(this);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Deprecated
    public Key generateKey(String object) throws InternalRecoveryServiceException, LockScreenRequiredException {
        try {
            object = this.mBinder.generateKey((String)object);
            if (object != null) {
                return this.getKeyFromGrant((String)object);
            }
            object = new InternalRecoveryServiceException("null grant alias");
            throw object;
        }
        catch (ServiceSpecificException serviceSpecificException) {
            if (serviceSpecificException.errorCode == 23) {
                throw new LockScreenRequiredException(serviceSpecificException.getMessage());
            }
            throw this.wrapUnexpectedServiceSpecificException(serviceSpecificException);
        }
        catch (KeyPermanentlyInvalidatedException | UnrecoverableKeyException generalSecurityException) {
            throw new InternalRecoveryServiceException("Failed to get key from keystore", generalSecurityException);
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Key generateKey(String object, byte[] arrby) throws InternalRecoveryServiceException, LockScreenRequiredException {
        try {
            object = this.mBinder.generateKeyWithMetadata((String)object, arrby);
            if (object != null) {
                return this.getKeyFromGrant((String)object);
            }
            object = new InternalRecoveryServiceException("null grant alias");
            throw object;
        }
        catch (ServiceSpecificException serviceSpecificException) {
            if (serviceSpecificException.errorCode == 23) {
                throw new LockScreenRequiredException(serviceSpecificException.getMessage());
            }
            throw this.wrapUnexpectedServiceSpecificException(serviceSpecificException);
        }
        catch (KeyPermanentlyInvalidatedException | UnrecoverableKeyException generalSecurityException) {
            throw new InternalRecoveryServiceException("Failed to get key from keystore", generalSecurityException);
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public List<String> getAliases() throws InternalRecoveryServiceException {
        try {
            ArrayList<String> arrayList = new ArrayList<String>(this.mBinder.getRecoveryStatus().keySet());
            return arrayList;
        }
        catch (ServiceSpecificException serviceSpecificException) {
            throw this.wrapUnexpectedServiceSpecificException(serviceSpecificException);
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    ILockSettings getBinder() {
        return this.mBinder;
    }

    public Key getKey(String object) throws InternalRecoveryServiceException, UnrecoverableKeyException {
        block5 : {
            object = this.mBinder.getKey((String)object);
            if (object == null) break block5;
            try {
                if ("".equals(object)) break block5;
                object = this.getKeyFromGrant((String)object);
                return object;
            }
            catch (ServiceSpecificException serviceSpecificException) {
                throw this.wrapUnexpectedServiceSpecificException(serviceSpecificException);
            }
            catch (KeyPermanentlyInvalidatedException | UnrecoverableKeyException generalSecurityException) {
                throw new UnrecoverableKeyException(generalSecurityException.getMessage());
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return null;
    }

    public KeyChainSnapshot getKeyChainSnapshot() throws InternalRecoveryServiceException {
        try {
            KeyChainSnapshot keyChainSnapshot = this.mBinder.getKeyChainSnapshot();
            return keyChainSnapshot;
        }
        catch (ServiceSpecificException serviceSpecificException) {
            if (serviceSpecificException.errorCode == 21) {
                return null;
            }
            throw this.wrapUnexpectedServiceSpecificException(serviceSpecificException);
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    Key getKeyFromGrant(String string2) throws UnrecoverableKeyException, KeyPermanentlyInvalidatedException {
        return AndroidKeyStoreProvider.loadAndroidKeyStoreKeyFromKeystore(this.mKeyStore, string2, -1);
    }

    public int[] getRecoverySecretTypes() throws InternalRecoveryServiceException {
        try {
            int[] arrn = this.mBinder.getRecoverySecretTypes();
            return arrn;
        }
        catch (ServiceSpecificException serviceSpecificException) {
            throw this.wrapUnexpectedServiceSpecificException(serviceSpecificException);
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public int getRecoveryStatus(String object) throws InternalRecoveryServiceException {
        block4 : {
            object = (Integer)this.mBinder.getRecoveryStatus().get(object);
            if (object != null) break block4;
            return 3;
        }
        try {
            int n = (Integer)object;
            return n;
        }
        catch (ServiceSpecificException serviceSpecificException) {
            throw this.wrapUnexpectedServiceSpecificException(serviceSpecificException);
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public Map<String, X509Certificate> getRootCertificates() {
        return TrustedRootCertificates.getRootCertificates();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Deprecated
    public Key importKey(String object, byte[] arrby) throws InternalRecoveryServiceException, LockScreenRequiredException {
        try {
            object = this.mBinder.importKey((String)object, arrby);
            if (object != null) {
                return this.getKeyFromGrant((String)object);
            }
            object = new InternalRecoveryServiceException("Null grant alias");
            throw object;
        }
        catch (ServiceSpecificException serviceSpecificException) {
            if (serviceSpecificException.errorCode == 23) {
                throw new LockScreenRequiredException(serviceSpecificException.getMessage());
            }
            throw this.wrapUnexpectedServiceSpecificException(serviceSpecificException);
        }
        catch (KeyPermanentlyInvalidatedException | UnrecoverableKeyException generalSecurityException) {
            throw new InternalRecoveryServiceException("Failed to get key from keystore", generalSecurityException);
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Key importKey(String object, byte[] arrby, byte[] arrby2) throws InternalRecoveryServiceException, LockScreenRequiredException {
        try {
            object = this.mBinder.importKeyWithMetadata((String)object, arrby, arrby2);
            if (object != null) {
                return this.getKeyFromGrant((String)object);
            }
            object = new InternalRecoveryServiceException("Null grant alias");
            throw object;
        }
        catch (ServiceSpecificException serviceSpecificException) {
            if (serviceSpecificException.errorCode == 23) {
                throw new LockScreenRequiredException(serviceSpecificException.getMessage());
            }
            throw this.wrapUnexpectedServiceSpecificException(serviceSpecificException);
        }
        catch (KeyPermanentlyInvalidatedException | UnrecoverableKeyException generalSecurityException) {
            throw new InternalRecoveryServiceException("Failed to get key from keystore", generalSecurityException);
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void initRecoveryService(String string2, byte[] arrby, byte[] arrby2) throws CertificateException, InternalRecoveryServiceException {
        try {
            this.mBinder.initRecoveryServiceWithSigFile(string2, arrby, arrby2);
            return;
        }
        catch (ServiceSpecificException serviceSpecificException) {
            if (serviceSpecificException.errorCode != 25 && serviceSpecificException.errorCode != 28) {
                if (serviceSpecificException.errorCode == 29) {
                    throw new CertificateException("Downgrading certificate serial version isn't supported.", serviceSpecificException);
                }
                throw this.wrapUnexpectedServiceSpecificException(serviceSpecificException);
            }
            throw new CertificateException("Invalid certificate for recovery service", serviceSpecificException);
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void removeKey(String string2) throws InternalRecoveryServiceException {
        try {
            this.mBinder.removeKey(string2);
            return;
        }
        catch (ServiceSpecificException serviceSpecificException) {
            throw this.wrapUnexpectedServiceSpecificException(serviceSpecificException);
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void setRecoverySecretTypes(int[] arrn) throws InternalRecoveryServiceException {
        try {
            this.mBinder.setRecoverySecretTypes(arrn);
            return;
        }
        catch (ServiceSpecificException serviceSpecificException) {
            throw this.wrapUnexpectedServiceSpecificException(serviceSpecificException);
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void setRecoveryStatus(String string2, int n) throws InternalRecoveryServiceException {
        try {
            this.mBinder.setRecoveryStatus(string2, n);
            return;
        }
        catch (ServiceSpecificException serviceSpecificException) {
            throw this.wrapUnexpectedServiceSpecificException(serviceSpecificException);
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void setServerParams(byte[] arrby) throws InternalRecoveryServiceException {
        try {
            this.mBinder.setServerParams(arrby);
            return;
        }
        catch (ServiceSpecificException serviceSpecificException) {
            throw this.wrapUnexpectedServiceSpecificException(serviceSpecificException);
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void setSnapshotCreatedPendingIntent(PendingIntent pendingIntent) throws InternalRecoveryServiceException {
        try {
            this.mBinder.setSnapshotCreatedPendingIntent(pendingIntent);
            return;
        }
        catch (ServiceSpecificException serviceSpecificException) {
            throw this.wrapUnexpectedServiceSpecificException(serviceSpecificException);
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    InternalRecoveryServiceException wrapUnexpectedServiceSpecificException(ServiceSpecificException serviceSpecificException) {
        if (serviceSpecificException.errorCode == 22) {
            return new InternalRecoveryServiceException(serviceSpecificException.getMessage());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unexpected error code for method: ");
        stringBuilder.append(serviceSpecificException.errorCode);
        return new InternalRecoveryServiceException(stringBuilder.toString(), serviceSpecificException);
    }
}

