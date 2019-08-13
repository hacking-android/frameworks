/*
 * Decompiled with CFR 0.145.
 */
package android.security.keystore.recovery;

import android.annotation.SystemApi;
import android.os.RemoteException;
import android.os.ServiceSpecificException;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.recovery.DecryptionFailedException;
import android.security.keystore.recovery.InternalRecoveryServiceException;
import android.security.keystore.recovery.KeyChainProtectionParams;
import android.security.keystore.recovery.RecoveryCertPath;
import android.security.keystore.recovery.RecoveryController;
import android.security.keystore.recovery.SessionExpiredException;
import android.security.keystore.recovery.WrappedApplicationKey;
import android.util.ArrayMap;
import android.util.Log;
import com.android.internal.widget.ILockSettings;
import java.io.Serializable;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertPath;
import java.security.cert.CertificateException;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

@SystemApi
public class RecoverySession
implements AutoCloseable {
    private static final int SESSION_ID_LENGTH_BYTES = 16;
    private static final String TAG = "RecoverySession";
    private final RecoveryController mRecoveryController;
    private final String mSessionId;

    private RecoverySession(RecoveryController recoveryController, String string2) {
        this.mRecoveryController = recoveryController;
        this.mSessionId = string2;
    }

    private Map<String, Key> getKeysFromGrants(Map<String, String> map) throws InternalRecoveryServiceException {
        ArrayMap<String, Key> arrayMap = new ArrayMap<String, Key>(map.size());
        for (String string2 : map.keySet()) {
            String string3 = map.get(string2);
            try {
                Key key = this.mRecoveryController.getKeyFromGrant(string3);
                arrayMap.put(string2, key);
            }
            catch (KeyPermanentlyInvalidatedException | UnrecoverableKeyException generalSecurityException) {
                throw new InternalRecoveryServiceException(String.format(Locale.US, "Failed to get key '%s' from grant '%s'", string2, string3), generalSecurityException);
            }
        }
        return arrayMap;
    }

    static RecoverySession newInstance(RecoveryController recoveryController) {
        return new RecoverySession(recoveryController, RecoverySession.newSessionId());
    }

    private static String newSessionId() {
        Serializable serializable = new SecureRandom();
        byte[] arrby = new byte[16];
        ((SecureRandom)serializable).nextBytes(arrby);
        serializable = new StringBuilder();
        int n = arrby.length;
        for (int i = 0; i < n; ++i) {
            ((StringBuilder)serializable).append(Byte.toHexString((byte)arrby[i], (boolean)false));
        }
        return ((StringBuilder)serializable).toString();
    }

    @Override
    public void close() {
        try {
            this.mRecoveryController.getBinder().closeSession(this.mSessionId);
        }
        catch (RemoteException | ServiceSpecificException exception) {
            Log.e(TAG, "Unexpected error trying to close session", exception);
        }
    }

    String getSessionId() {
        return this.mSessionId;
    }

    public Map<String, Key> recoverKeyChainSnapshot(byte[] object, List<WrappedApplicationKey> list) throws SessionExpiredException, DecryptionFailedException, InternalRecoveryServiceException {
        try {
            object = this.getKeysFromGrants(this.mRecoveryController.getBinder().recoverKeyChainSnapshot(this.mSessionId, (byte[])object, list));
            return object;
        }
        catch (ServiceSpecificException serviceSpecificException) {
            if (serviceSpecificException.errorCode != 26) {
                if (serviceSpecificException.errorCode == 24) {
                    throw new SessionExpiredException(serviceSpecificException.getMessage());
                }
                throw this.mRecoveryController.wrapUnexpectedServiceSpecificException(serviceSpecificException);
            }
            throw new DecryptionFailedException(serviceSpecificException.getMessage());
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public byte[] start(String arrby, CertPath object, byte[] arrby2, byte[] arrby3, List<KeyChainProtectionParams> list) throws CertificateException, InternalRecoveryServiceException {
        object = RecoveryCertPath.createRecoveryCertPath((CertPath)object);
        try {
            arrby = this.mRecoveryController.getBinder().startRecoverySessionWithCertPath(this.mSessionId, (String)arrby, (RecoveryCertPath)object, arrby2, arrby3, list);
            return arrby;
        }
        catch (ServiceSpecificException serviceSpecificException) {
            if (serviceSpecificException.errorCode != 25 && serviceSpecificException.errorCode != 28) {
                throw this.mRecoveryController.wrapUnexpectedServiceSpecificException(serviceSpecificException);
            }
            throw new CertificateException("Invalid certificate for recovery session", serviceSpecificException);
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }
}

