/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.fingerprint;

import android.annotation.UnsupportedAppUsage;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.UserInfo;
import android.content.res.Resources;
import android.hardware.biometrics.BiometricAuthenticator;
import android.hardware.biometrics.BiometricFingerprintConstants;
import android.hardware.biometrics.IBiometricServiceLockoutResetCallback;
import android.hardware.fingerprint.Fingerprint;
import android.hardware.fingerprint.IFingerprintService;
import android.hardware.fingerprint.IFingerprintServiceReceiver;
import android.hardware.fingerprint._$$Lambda$FingerprintManager$1$4i3tUU8mafgvA9HaB2UPD31L6UY;
import android.os.Binder;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.IBinder;
import android.os.IRemoteCallback;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.RemoteException;
import android.util.Slog;
import java.security.Signature;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.Mac;

@Deprecated
public class FingerprintManager
implements BiometricAuthenticator,
BiometricFingerprintConstants {
    private static final boolean DEBUG = true;
    private static final int MSG_ACQUIRED = 101;
    private static final int MSG_AUTHENTICATION_FAILED = 103;
    private static final int MSG_AUTHENTICATION_SUCCEEDED = 102;
    private static final int MSG_ENROLL_RESULT = 100;
    private static final int MSG_ENUMERATED = 106;
    private static final int MSG_ERROR = 104;
    private static final int MSG_REMOVED = 105;
    private static final String TAG = "FingerprintManager";
    private AuthenticationCallback mAuthenticationCallback;
    private Context mContext;
    private CryptoObject mCryptoObject;
    private EnrollmentCallback mEnrollmentCallback;
    private EnumerateCallback mEnumerateCallback;
    private Handler mHandler;
    private RemovalCallback mRemovalCallback;
    private Fingerprint mRemovalFingerprint;
    private IFingerprintService mService;
    private IFingerprintServiceReceiver mServiceReceiver = new IFingerprintServiceReceiver.Stub(){

        @Override
        public void onAcquired(long l, int n, int n2) {
            FingerprintManager.this.mHandler.obtainMessage(101, n, n2, l).sendToTarget();
        }

        @Override
        public void onAuthenticationFailed(long l) {
            FingerprintManager.this.mHandler.obtainMessage(103).sendToTarget();
        }

        @Override
        public void onAuthenticationSucceeded(long l, Fingerprint fingerprint, int n) {
            FingerprintManager.this.mHandler.obtainMessage(102, n, 0, fingerprint).sendToTarget();
        }

        @Override
        public void onEnrollResult(long l, int n, int n2, int n3) {
            FingerprintManager.this.mHandler.obtainMessage(100, n3, 0, new Fingerprint(null, n2, n, l)).sendToTarget();
        }

        @Override
        public void onEnumerated(long l, int n, int n2, int n3) {
            FingerprintManager.this.mHandler.obtainMessage(106, n, n2, l).sendToTarget();
        }

        @Override
        public void onError(long l, int n, int n2) {
            FingerprintManager.this.mHandler.obtainMessage(104, n, n2, l).sendToTarget();
        }

        @Override
        public void onRemoved(long l, int n, int n2, int n3) {
            FingerprintManager.this.mHandler.obtainMessage(105, n3, 0, new Fingerprint(null, n2, n, l)).sendToTarget();
        }
    };
    private IBinder mToken = new Binder();

    public FingerprintManager(Context context, IFingerprintService iFingerprintService) {
        this.mContext = context;
        this.mService = iFingerprintService;
        if (this.mService == null) {
            Slog.v(TAG, "FingerprintManagerService was null");
        }
        this.mHandler = new MyHandler(context);
    }

    private void cancelAuthentication(android.hardware.biometrics.CryptoObject object) {
        object = this.mService;
        if (object != null) {
            try {
                object.cancelAuthentication(this.mToken, this.mContext.getOpPackageName());
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    private void cancelEnrollment() {
        IFingerprintService iFingerprintService = this.mService;
        if (iFingerprintService != null) {
            try {
                iFingerprintService.cancelEnrollment(this.mToken);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    public static String getAcquiredString(Context arrstring, int n, int n2) {
        switch (n) {
            default: {
                break;
            }
            case 6: {
                arrstring = arrstring.getResources().getStringArray(17236085);
                if (n2 >= arrstring.length) break;
                return arrstring[n2];
            }
            case 5: {
                return arrstring.getString(17040011);
            }
            case 4: {
                return arrstring.getString(17040012);
            }
            case 3: {
                return arrstring.getString(17040008);
            }
            case 2: {
                return arrstring.getString(17040009);
            }
            case 1: {
                return arrstring.getString(17040010);
            }
            case 0: {
                return null;
            }
        }
        arrstring = new StringBuilder();
        arrstring.append("Invalid acquired message: ");
        arrstring.append(n);
        arrstring.append(", ");
        arrstring.append(n2);
        Slog.w(TAG, arrstring.toString());
        return null;
    }

    private int getCurrentUserId() {
        try {
            int n = ActivityManager.getService().getCurrentUser().id;
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public static String getErrorString(Context arrstring, int n, int n2) {
        switch (n) {
            default: {
                break;
            }
            case 12: {
                return arrstring.getString(17040016);
            }
            case 11: {
                return arrstring.getString(17040019);
            }
            case 10: {
                return arrstring.getString(17040023);
            }
            case 9: {
                return arrstring.getString(17040018);
            }
            case 8: {
                arrstring = arrstring.getResources().getStringArray(17236086);
                if (n2 >= arrstring.length) break;
                return arrstring[n2];
            }
            case 7: {
                return arrstring.getString(17040017);
            }
            case 5: {
                return arrstring.getString(17040014);
            }
            case 4: {
                return arrstring.getString(17040020);
            }
            case 3: {
                return arrstring.getString(17040021);
            }
            case 2: {
                return arrstring.getString(17040022);
            }
            case 1: {
                return arrstring.getString(17040015);
            }
        }
        arrstring = new StringBuilder();
        arrstring.append("Invalid error message: ");
        arrstring.append(n);
        arrstring.append(", ");
        arrstring.append(n2);
        Slog.w(TAG, arrstring.toString());
        return null;
    }

    private void sendAcquiredResult(long l, int n, int n2) {
        Object object;
        Object object2 = this.mAuthenticationCallback;
        if (object2 != null) {
            ((AuthenticationCallback)object2).onAuthenticationAcquired(n);
        }
        if ((object2 = FingerprintManager.getAcquiredString(this.mContext, n, n2)) == null) {
            return;
        }
        if (n == 6) {
            n = n2 + 1000;
        }
        if ((object = this.mEnrollmentCallback) != null) {
            ((EnrollmentCallback)object).onEnrollmentHelp(n, (CharSequence)object2);
        } else {
            object = this.mAuthenticationCallback;
            if (object != null) {
                ((AuthenticationCallback)object).onAuthenticationHelp(n, (CharSequence)object2);
            }
        }
    }

    private void sendAuthenticatedFailed() {
        AuthenticationCallback authenticationCallback = this.mAuthenticationCallback;
        if (authenticationCallback != null) {
            authenticationCallback.onAuthenticationFailed();
        }
    }

    private void sendAuthenticatedSucceeded(Fingerprint object, int n) {
        if (this.mAuthenticationCallback != null) {
            object = new AuthenticationResult(this.mCryptoObject, (Fingerprint)object, n);
            this.mAuthenticationCallback.onAuthenticationSucceeded((AuthenticationResult)object);
        }
    }

    private void sendEnrollResult(Fingerprint object, int n) {
        object = this.mEnrollmentCallback;
        if (object != null) {
            ((EnrollmentCallback)object).onEnrollmentProgress(n);
        }
    }

    private void sendEnumeratedResult(long l, int n, int n2) {
        EnumerateCallback enumerateCallback = this.mEnumerateCallback;
        if (enumerateCallback != null) {
            enumerateCallback.onEnumerate(new Fingerprint(null, n2, n, l));
        }
    }

    private void sendErrorResult(long l, int n, int n2) {
        int n3 = n == 8 ? n2 + 1000 : n;
        Object object = this.mEnrollmentCallback;
        if (object != null) {
            ((EnrollmentCallback)object).onEnrollmentError(n3, FingerprintManager.getErrorString(this.mContext, n, n2));
        } else {
            object = this.mAuthenticationCallback;
            if (object != null) {
                ((AuthenticationCallback)object).onAuthenticationError(n3, FingerprintManager.getErrorString(this.mContext, n, n2));
            } else {
                object = this.mRemovalCallback;
                if (object != null) {
                    ((RemovalCallback)object).onRemovalError(this.mRemovalFingerprint, n3, FingerprintManager.getErrorString(this.mContext, n, n2));
                } else {
                    object = this.mEnumerateCallback;
                    if (object != null) {
                        ((EnumerateCallback)object).onEnumerateError(n3, FingerprintManager.getErrorString(this.mContext, n, n2));
                    }
                }
            }
        }
    }

    private void sendRemovedResult(Fingerprint object, int n) {
        if (this.mRemovalCallback == null) {
            return;
        }
        if (object == null) {
            Slog.e(TAG, "Received MSG_REMOVED, but fingerprint is null");
            return;
        }
        int n2 = ((BiometricAuthenticator.Identifier)object).getBiometricId();
        int n3 = this.mRemovalFingerprint.getBiometricId();
        if (n3 != 0 && n2 != 0 && n2 != n3) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Finger id didn't match: ");
            ((StringBuilder)object).append(n2);
            ((StringBuilder)object).append(" != ");
            ((StringBuilder)object).append(n3);
            Slog.w(TAG, ((StringBuilder)object).toString());
            return;
        }
        n2 = ((Fingerprint)object).getGroupId();
        if (n2 != (n3 = this.mRemovalFingerprint.getGroupId())) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Group id didn't match: ");
            ((StringBuilder)object).append(n2);
            ((StringBuilder)object).append(" != ");
            ((StringBuilder)object).append(n3);
            Slog.w(TAG, ((StringBuilder)object).toString());
            return;
        }
        this.mRemovalCallback.onRemovalSucceeded((Fingerprint)object, n);
    }

    private void useHandler(Handler handler) {
        if (handler != null) {
            this.mHandler = new MyHandler(handler.getLooper());
        } else if (this.mHandler.getLooper() != this.mContext.getMainLooper()) {
            this.mHandler = new MyHandler(this.mContext.getMainLooper());
        }
    }

    public void addLockoutResetCallback(final LockoutResetCallback lockoutResetCallback) {
        if (this.mService != null) {
            try {
                final PowerManager powerManager = this.mContext.getSystemService(PowerManager.class);
                IFingerprintService iFingerprintService = this.mService;
                IBiometricServiceLockoutResetCallback.Stub stub = new IBiometricServiceLockoutResetCallback.Stub(){

                    static /* synthetic */ void lambda$onLockoutReset$0(LockoutResetCallback lockoutResetCallback2, PowerManager.WakeLock wakeLock) {
                        try {
                            lockoutResetCallback2.onLockoutReset();
                            return;
                        }
                        finally {
                            wakeLock.release();
                        }
                    }

                    @Override
                    public void onLockoutReset(long l, IRemoteCallback iRemoteCallback) throws RemoteException {
                        try {
                            PowerManager.WakeLock wakeLock = powerManager.newWakeLock(1, "lockoutResetCallback");
                            wakeLock.acquire();
                            Handler handler = FingerprintManager.this.mHandler;
                            LockoutResetCallback lockoutResetCallback2 = lockoutResetCallback;
                            _$$Lambda$FingerprintManager$1$4i3tUU8mafgvA9HaB2UPD31L6UY _$$Lambda$FingerprintManager$1$4i3tUU8mafgvA9HaB2UPD31L6UY = new _$$Lambda$FingerprintManager$1$4i3tUU8mafgvA9HaB2UPD31L6UY(lockoutResetCallback2, wakeLock);
                            handler.post(_$$Lambda$FingerprintManager$1$4i3tUU8mafgvA9HaB2UPD31L6UY);
                            return;
                        }
                        finally {
                            iRemoteCallback.sendResult(null);
                        }
                    }
                };
                iFingerprintService.addLockoutResetCallback(stub);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        } else {
            Slog.w(TAG, "addLockoutResetCallback(): Service not connected!");
        }
    }

    @Deprecated
    public void authenticate(CryptoObject cryptoObject, CancellationSignal cancellationSignal, int n, AuthenticationCallback authenticationCallback, Handler handler) {
        this.authenticate(cryptoObject, cancellationSignal, n, authenticationCallback, handler, this.mContext.getUserId());
    }

    public void authenticate(CryptoObject cryptoObject, CancellationSignal cancellationSignal, int n, AuthenticationCallback authenticationCallback, Handler handler, int n2) {
        if (authenticationCallback != null) {
            if (cancellationSignal != null) {
                if (cancellationSignal.isCanceled()) {
                    Slog.w(TAG, "authentication already canceled");
                    return;
                }
                cancellationSignal.setOnCancelListener(new OnAuthenticationCancelListener(cryptoObject));
            }
            if (this.mService != null) {
                long l;
                block9 : {
                    block8 : {
                        this.useHandler(handler);
                        this.mAuthenticationCallback = authenticationCallback;
                        this.mCryptoObject = cryptoObject;
                        if (cryptoObject == null) break block8;
                        l = cryptoObject.getOpId();
                        break block9;
                    }
                    l = 0L;
                }
                try {
                    this.mService.authenticate(this.mToken, l, n2, this.mServiceReceiver, n, this.mContext.getOpPackageName());
                }
                catch (RemoteException remoteException) {
                    Slog.w(TAG, "Remote exception while authenticating: ", remoteException);
                    authenticationCallback.onAuthenticationError(1, FingerprintManager.getErrorString(this.mContext, 1, 0));
                }
            }
            return;
        }
        throw new IllegalArgumentException("Must supply an authentication callback");
    }

    public void enroll(byte[] arrby, CancellationSignal object, int n, int n2, EnrollmentCallback enrollmentCallback) {
        int n3 = n2;
        if (n2 == -2) {
            n3 = this.getCurrentUserId();
        }
        if (enrollmentCallback != null) {
            if (object != null) {
                if (((CancellationSignal)object).isCanceled()) {
                    Slog.w(TAG, "enrollment already canceled");
                    return;
                }
                ((CancellationSignal)object).setOnCancelListener(new OnEnrollCancelListener());
            }
            if ((object = this.mService) != null) {
                try {
                    this.mEnrollmentCallback = enrollmentCallback;
                    object.enroll(this.mToken, arrby, n3, this.mServiceReceiver, n, this.mContext.getOpPackageName());
                }
                catch (RemoteException remoteException) {
                    Slog.w(TAG, "Remote exception in enroll: ", remoteException);
                    enrollmentCallback.onEnrollmentError(1, FingerprintManager.getErrorString(this.mContext, 1, 0));
                }
            }
            return;
        }
        throw new IllegalArgumentException("Must supply an enrollment callback");
    }

    public void enumerate(int n, EnumerateCallback enumerateCallback) {
        block3 : {
            IFingerprintService iFingerprintService = this.mService;
            if (iFingerprintService != null) {
                try {
                    this.mEnumerateCallback = enumerateCallback;
                    iFingerprintService.enumerate(this.mToken, n, this.mServiceReceiver);
                }
                catch (RemoteException remoteException) {
                    Slog.w(TAG, "Remote exception in enumerate: ", remoteException);
                    if (enumerateCallback == null) break block3;
                    enumerateCallback.onEnumerateError(1, FingerprintManager.getErrorString(this.mContext, 1, 0));
                }
            }
        }
    }

    @UnsupportedAppUsage
    public long getAuthenticatorId() {
        IFingerprintService iFingerprintService = this.mService;
        if (iFingerprintService != null) {
            try {
                long l = iFingerprintService.getAuthenticatorId(this.mContext.getOpPackageName());
                return l;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        Slog.w(TAG, "getAuthenticatorId(): Service not connected!");
        return 0L;
    }

    @UnsupportedAppUsage
    public List<Fingerprint> getEnrolledFingerprints() {
        return this.getEnrolledFingerprints(this.mContext.getUserId());
    }

    @UnsupportedAppUsage
    public List<Fingerprint> getEnrolledFingerprints(int n) {
        Object object = this.mService;
        if (object != null) {
            try {
                object = object.getEnrolledFingerprints(n, this.mContext.getOpPackageName());
                return object;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return null;
    }

    @Deprecated
    public boolean hasEnrolledFingerprints() {
        IFingerprintService iFingerprintService = this.mService;
        if (iFingerprintService != null) {
            try {
                boolean bl = iFingerprintService.hasEnrolledFingerprints(this.mContext.getUserId(), this.mContext.getOpPackageName());
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public boolean hasEnrolledFingerprints(int n) {
        IFingerprintService iFingerprintService = this.mService;
        if (iFingerprintService != null) {
            try {
                boolean bl = iFingerprintService.hasEnrolledFingerprints(n, this.mContext.getOpPackageName());
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return false;
    }

    @Override
    public boolean hasEnrolledTemplates() {
        return this.hasEnrolledFingerprints();
    }

    @Override
    public boolean hasEnrolledTemplates(int n) {
        return this.hasEnrolledFingerprints(n);
    }

    @Deprecated
    @Override
    public boolean isHardwareDetected() {
        IFingerprintService iFingerprintService = this.mService;
        if (iFingerprintService != null) {
            try {
                boolean bl = iFingerprintService.isHardwareDetected(0L, this.mContext.getOpPackageName());
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        Slog.w(TAG, "isFingerprintHardwareDetected(): Service not connected!");
        return false;
    }

    public int postEnroll() {
        int n = 0;
        IFingerprintService iFingerprintService = this.mService;
        if (iFingerprintService != null) {
            try {
                n = iFingerprintService.postEnroll(this.mToken);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return n;
    }

    public long preEnroll() {
        long l = 0L;
        IFingerprintService iFingerprintService = this.mService;
        if (iFingerprintService != null) {
            try {
                l = iFingerprintService.preEnroll(this.mToken);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return l;
    }

    public void remove(Fingerprint fingerprint, int n, RemovalCallback removalCallback) {
        block3 : {
            IFingerprintService iFingerprintService = this.mService;
            if (iFingerprintService != null) {
                try {
                    this.mRemovalCallback = removalCallback;
                    this.mRemovalFingerprint = fingerprint;
                    iFingerprintService.remove(this.mToken, fingerprint.getBiometricId(), fingerprint.getGroupId(), n, this.mServiceReceiver);
                }
                catch (RemoteException remoteException) {
                    Slog.w(TAG, "Remote exception in remove: ", remoteException);
                    if (removalCallback == null) break block3;
                    removalCallback.onRemovalError(fingerprint, 1, FingerprintManager.getErrorString(this.mContext, 1, 0));
                }
            }
        }
    }

    public void rename(int n, int n2, String string2) {
        IFingerprintService iFingerprintService = this.mService;
        if (iFingerprintService != null) {
            try {
                iFingerprintService.rename(n, n2, string2);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        } else {
            Slog.w(TAG, "rename(): Service not connected!");
        }
    }

    @Override
    public void setActiveUser(int n) {
        IFingerprintService iFingerprintService = this.mService;
        if (iFingerprintService != null) {
            try {
                iFingerprintService.setActiveUser(n);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    @Deprecated
    public static abstract class AuthenticationCallback
    extends BiometricAuthenticator.AuthenticationCallback {
        @Override
        public void onAuthenticationAcquired(int n) {
        }

        @Override
        public void onAuthenticationError(int n, CharSequence charSequence) {
        }

        @Override
        public void onAuthenticationFailed() {
        }

        @Override
        public void onAuthenticationHelp(int n, CharSequence charSequence) {
        }

        public void onAuthenticationSucceeded(AuthenticationResult authenticationResult) {
        }
    }

    @Deprecated
    public static class AuthenticationResult {
        private CryptoObject mCryptoObject;
        private Fingerprint mFingerprint;
        private int mUserId;

        public AuthenticationResult(CryptoObject cryptoObject, Fingerprint fingerprint, int n) {
            this.mCryptoObject = cryptoObject;
            this.mFingerprint = fingerprint;
            this.mUserId = n;
        }

        public CryptoObject getCryptoObject() {
            return this.mCryptoObject;
        }

        @UnsupportedAppUsage
        public Fingerprint getFingerprint() {
            return this.mFingerprint;
        }

        public int getUserId() {
            return this.mUserId;
        }
    }

    @Deprecated
    public static final class CryptoObject
    extends android.hardware.biometrics.CryptoObject {
        public CryptoObject(Signature signature) {
            super(signature);
        }

        public CryptoObject(Cipher cipher) {
            super(cipher);
        }

        public CryptoObject(Mac mac) {
            super(mac);
        }

        @Override
        public Cipher getCipher() {
            return super.getCipher();
        }

        @Override
        public Mac getMac() {
            return super.getMac();
        }

        @Override
        public Signature getSignature() {
            return super.getSignature();
        }
    }

    public static abstract class EnrollmentCallback {
        public void onEnrollmentError(int n, CharSequence charSequence) {
        }

        public void onEnrollmentHelp(int n, CharSequence charSequence) {
        }

        public void onEnrollmentProgress(int n) {
        }
    }

    public static abstract class EnumerateCallback {
        public void onEnumerate(Fingerprint fingerprint) {
        }

        public void onEnumerateError(int n, CharSequence charSequence) {
        }
    }

    public static abstract class LockoutResetCallback {
        public void onLockoutReset() {
        }
    }

    private class MyHandler
    extends Handler {
        private MyHandler(Context context) {
            super(context.getMainLooper());
        }

        private MyHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message message) {
            switch (message.what) {
                default: {
                    break;
                }
                case 106: {
                    FingerprintManager.this.sendEnumeratedResult((Long)message.obj, message.arg1, message.arg2);
                    break;
                }
                case 105: {
                    FingerprintManager.this.sendRemovedResult((Fingerprint)message.obj, message.arg1);
                    break;
                }
                case 104: {
                    FingerprintManager.this.sendErrorResult((Long)message.obj, message.arg1, message.arg2);
                    break;
                }
                case 103: {
                    FingerprintManager.this.sendAuthenticatedFailed();
                    break;
                }
                case 102: {
                    FingerprintManager.this.sendAuthenticatedSucceeded((Fingerprint)message.obj, message.arg1);
                    break;
                }
                case 101: {
                    FingerprintManager.this.sendAcquiredResult((Long)message.obj, message.arg1, message.arg2);
                    break;
                }
                case 100: {
                    FingerprintManager.this.sendEnrollResult((Fingerprint)message.obj, message.arg1);
                }
            }
        }
    }

    private class OnAuthenticationCancelListener
    implements CancellationSignal.OnCancelListener {
        private android.hardware.biometrics.CryptoObject mCrypto;

        public OnAuthenticationCancelListener(android.hardware.biometrics.CryptoObject cryptoObject) {
            this.mCrypto = cryptoObject;
        }

        @Override
        public void onCancel() {
            FingerprintManager.this.cancelAuthentication(this.mCrypto);
        }
    }

    private class OnEnrollCancelListener
    implements CancellationSignal.OnCancelListener {
        private OnEnrollCancelListener() {
        }

        @Override
        public void onCancel() {
            FingerprintManager.this.cancelEnrollment();
        }
    }

    public static abstract class RemovalCallback {
        public void onRemovalError(Fingerprint fingerprint, int n, CharSequence charSequence) {
        }

        public void onRemovalSucceeded(Fingerprint fingerprint, int n) {
        }
    }

}

