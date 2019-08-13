/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.biometrics;

import android.content.Context;
import android.content.DialogInterface;
import android.hardware.biometrics.BiometricAuthenticator;
import android.hardware.biometrics.BiometricConstants;
import android.hardware.biometrics.BiometricManager;
import android.hardware.biometrics.IBiometricConfirmDeviceCredentialCallback;
import android.hardware.biometrics.IBiometricService;
import android.hardware.biometrics.IBiometricServiceReceiver;
import android.hardware.biometrics._$$Lambda$BiometricPrompt$1$AAMJr_dQQ3dkiYxppvTx2AjuvRQ;
import android.hardware.biometrics._$$Lambda$BiometricPrompt$1$G8c_A1luxVwjcfGpdSp4nNPnavM;
import android.hardware.biometrics._$$Lambda$BiometricPrompt$1$Kmc1otRcCm0Akw6_6yK5trqAv78;
import android.hardware.biometrics._$$Lambda$BiometricPrompt$1$_p2Kb7GLaNe_mSDlUdJIRLMJ5kQ;
import android.hardware.biometrics._$$Lambda$BiometricPrompt$1$aJtOJjyL74ZJt5iM1EsAPg6PHK4;
import android.hardware.biometrics._$$Lambda$BiometricPrompt$1$yfG83rs6eJM9CDMAlhftsvdKekY;
import android.hardware.biometrics._$$Lambda$BiometricPrompt$Dk3E1C_ccte_BJOnzgPmi2l5r0I;
import android.hardware.biometrics._$$Lambda$BiometricPrompt$FhnggONVmg0fSM3ar79llL7ZRYM;
import android.os.Binder;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.text.TextUtils;
import android.util.Log;
import java.security.Signature;
import java.util.concurrent.Executor;
import javax.crypto.Cipher;
import javax.crypto.Mac;

public class BiometricPrompt
implements BiometricAuthenticator,
BiometricConstants {
    public static final int DISMISSED_REASON_NEGATIVE = 2;
    public static final int DISMISSED_REASON_POSITIVE = 1;
    public static final int DISMISSED_REASON_USER_CANCEL = 3;
    public static final int HIDE_DIALOG_DELAY = 2000;
    public static final String KEY_ALLOW_DEVICE_CREDENTIAL = "allow_device_credential";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_FROM_CONFIRM_DEVICE_CREDENTIAL = "from_confirm_device_credential";
    public static final String KEY_NEGATIVE_TEXT = "negative_text";
    public static final String KEY_POSITIVE_TEXT = "positive_text";
    public static final String KEY_REQUIRE_CONFIRMATION = "require_confirmation";
    public static final String KEY_SUBTITLE = "subtitle";
    public static final String KEY_TITLE = "title";
    public static final String KEY_USE_DEFAULT_TITLE = "use_default_title";
    private static final String TAG = "BiometricPrompt";
    private AuthenticationCallback mAuthenticationCallback;
    private final IBiometricServiceReceiver mBiometricServiceReceiver = new IBiometricServiceReceiver.Stub(){

        public /* synthetic */ void lambda$onAcquired$3$BiometricPrompt$1(int n, String string2) {
            BiometricPrompt.this.mAuthenticationCallback.onAuthenticationHelp(n, string2);
        }

        public /* synthetic */ void lambda$onAuthenticationFailed$1$BiometricPrompt$1() {
            BiometricPrompt.this.mAuthenticationCallback.onAuthenticationFailed();
        }

        public /* synthetic */ void lambda$onAuthenticationSucceeded$0$BiometricPrompt$1() {
            AuthenticationResult authenticationResult = new AuthenticationResult(BiometricPrompt.this.mCryptoObject);
            BiometricPrompt.this.mAuthenticationCallback.onAuthenticationSucceeded(authenticationResult);
        }

        public /* synthetic */ void lambda$onDialogDismissed$4$BiometricPrompt$1() {
            BiometricPrompt.access$300((BiometricPrompt)BiometricPrompt.this).listener.onClick(null, -1);
        }

        public /* synthetic */ void lambda$onDialogDismissed$5$BiometricPrompt$1() {
            BiometricPrompt.access$400((BiometricPrompt)BiometricPrompt.this).listener.onClick(null, -2);
        }

        public /* synthetic */ void lambda$onError$2$BiometricPrompt$1(int n, String string2) {
            BiometricPrompt.this.mAuthenticationCallback.onAuthenticationError(n, string2);
        }

        @Override
        public void onAcquired(int n, String string2) throws RemoteException {
            BiometricPrompt.this.mExecutor.execute(new _$$Lambda$BiometricPrompt$1$yfG83rs6eJM9CDMAlhftsvdKekY(this, n, string2));
        }

        @Override
        public void onAuthenticationFailed() throws RemoteException {
            BiometricPrompt.this.mExecutor.execute(new _$$Lambda$BiometricPrompt$1$AAMJr_dQQ3dkiYxppvTx2AjuvRQ(this));
        }

        @Override
        public void onAuthenticationSucceeded() throws RemoteException {
            BiometricPrompt.this.mExecutor.execute(new _$$Lambda$BiometricPrompt$1$_p2Kb7GLaNe_mSDlUdJIRLMJ5kQ(this));
        }

        @Override
        public void onDialogDismissed(int n) throws RemoteException {
            if (n == 1) {
                BiometricPrompt.access$300((BiometricPrompt)BiometricPrompt.this).executor.execute(new _$$Lambda$BiometricPrompt$1$Kmc1otRcCm0Akw6_6yK5trqAv78(this));
            } else if (n == 2) {
                BiometricPrompt.access$400((BiometricPrompt)BiometricPrompt.this).executor.execute(new _$$Lambda$BiometricPrompt$1$G8c_A1luxVwjcfGpdSp4nNPnavM(this));
            }
        }

        @Override
        public void onError(int n, String string2) throws RemoteException {
            BiometricPrompt.this.mExecutor.execute(new _$$Lambda$BiometricPrompt$1$aJtOJjyL74ZJt5iM1EsAPg6PHK4(this, n, string2));
        }
    };
    private final Bundle mBundle;
    private final Context mContext;
    private CryptoObject mCryptoObject;
    private Executor mExecutor;
    private final ButtonInfo mNegativeButtonInfo;
    private final ButtonInfo mPositiveButtonInfo;
    private final IBiometricService mService;
    private final IBinder mToken = new Binder();

    private BiometricPrompt(Context context, Bundle bundle, ButtonInfo buttonInfo, ButtonInfo buttonInfo2) {
        this.mContext = context;
        this.mBundle = bundle;
        this.mPositiveButtonInfo = buttonInfo;
        this.mNegativeButtonInfo = buttonInfo2;
        this.mService = IBiometricService.Stub.asInterface(ServiceManager.getService("biometric"));
    }

    static /* synthetic */ ButtonInfo access$300(BiometricPrompt biometricPrompt) {
        return biometricPrompt.mPositiveButtonInfo;
    }

    static /* synthetic */ ButtonInfo access$400(BiometricPrompt biometricPrompt) {
        return biometricPrompt.mNegativeButtonInfo;
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void authenticateInternal(CryptoObject object, CancellationSignal object2, Executor executor, AuthenticationCallback authenticationCallback, int n, IBiometricConfirmDeviceCredentialCallback iBiometricConfirmDeviceCredentialCallback) {
        void var1_5;
        block8 : {
            if (((CancellationSignal)object2).isCanceled()) {
                Log.w(TAG, "Authentication already canceled");
                return;
            }
            OnAuthenticationCancelListener onAuthenticationCancelListener = new OnAuthenticationCancelListener();
            try {
                ((CancellationSignal)object2).setOnCancelListener(onAuthenticationCancelListener);
                this.mCryptoObject = object;
            }
            catch (RemoteException remoteException) {}
            try {
                this.mExecutor = executor;
                this.mAuthenticationCallback = authenticationCallback;
                long l = object != null ? ((android.hardware.biometrics.CryptoObject)object).getOpId() : 0L;
                if (BiometricManager.hasBiometrics(this.mContext)) {
                    this.mService.authenticate(this.mToken, l, n, this.mBiometricServiceReceiver, this.mContext.getOpPackageName(), this.mBundle, iBiometricConfirmDeviceCredentialCallback);
                    return;
                }
                object = this.mExecutor;
                object2 = new _$$Lambda$BiometricPrompt$Dk3E1C_ccte_BJOnzgPmi2l5r0I(this, authenticationCallback);
                object.execute((Runnable)object2);
                return;
            }
            catch (RemoteException remoteException) {}
            break block8;
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
        Log.e(TAG, "Remote exception while authenticating", (Throwable)var1_5);
        this.mExecutor.execute(new _$$Lambda$BiometricPrompt$FhnggONVmg0fSM3ar79llL7ZRYM(this, authenticationCallback));
    }

    private void cancelAuthentication() {
        IBiometricService iBiometricService = this.mService;
        if (iBiometricService != null) {
            try {
                iBiometricService.cancelAuthentication(this.mToken, this.mContext.getOpPackageName());
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Unable to cancel authentication", remoteException);
            }
        }
    }

    public void authenticate(CryptoObject cryptoObject, CancellationSignal cancellationSignal, Executor executor, AuthenticationCallback authenticationCallback) {
        if (cryptoObject != null) {
            if (cancellationSignal != null) {
                if (executor != null) {
                    if (authenticationCallback != null) {
                        if (!this.mBundle.getBoolean(KEY_ALLOW_DEVICE_CREDENTIAL)) {
                            this.authenticateInternal(cryptoObject, cancellationSignal, executor, authenticationCallback, this.mContext.getUserId(), null);
                            return;
                        }
                        throw new IllegalArgumentException("Device credential not supported with crypto");
                    }
                    throw new IllegalArgumentException("Must supply a callback");
                }
                throw new IllegalArgumentException("Must supply an executor");
            }
            throw new IllegalArgumentException("Must supply a cancellation signal");
        }
        throw new IllegalArgumentException("Must supply a crypto object");
    }

    public void authenticate(CancellationSignal cancellationSignal, Executor executor, AuthenticationCallback authenticationCallback) {
        if (cancellationSignal != null) {
            if (executor != null) {
                if (authenticationCallback != null) {
                    this.authenticateInternal(null, cancellationSignal, executor, authenticationCallback, this.mContext.getUserId(), null);
                    return;
                }
                throw new IllegalArgumentException("Must supply a callback");
            }
            throw new IllegalArgumentException("Must supply an executor");
        }
        throw new IllegalArgumentException("Must supply a cancellation signal");
    }

    public void authenticateUser(CancellationSignal cancellationSignal, Executor executor, AuthenticationCallback authenticationCallback, int n, IBiometricConfirmDeviceCredentialCallback iBiometricConfirmDeviceCredentialCallback) {
        if (cancellationSignal != null) {
            if (executor != null) {
                if (authenticationCallback != null) {
                    this.authenticateInternal(null, cancellationSignal, executor, authenticationCallback, n, iBiometricConfirmDeviceCredentialCallback);
                    return;
                }
                throw new IllegalArgumentException("Must supply a callback");
            }
            throw new IllegalArgumentException("Must supply an executor");
        }
        throw new IllegalArgumentException("Must supply a cancellation signal");
    }

    public /* synthetic */ void lambda$authenticateInternal$0$BiometricPrompt(AuthenticationCallback authenticationCallback) {
        authenticationCallback.onAuthenticationError(12, this.mContext.getString(17039612));
    }

    public /* synthetic */ void lambda$authenticateInternal$1$BiometricPrompt(AuthenticationCallback authenticationCallback) {
        authenticationCallback.onAuthenticationError(1, this.mContext.getString(17039612));
    }

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

    public static class AuthenticationResult
    extends BiometricAuthenticator.AuthenticationResult {
        public AuthenticationResult(CryptoObject cryptoObject) {
            super(cryptoObject, null, 0);
        }

        @Override
        public CryptoObject getCryptoObject() {
            return (CryptoObject)super.getCryptoObject();
        }
    }

    public static class Builder {
        private final Bundle mBundle = new Bundle();
        private Context mContext;
        private ButtonInfo mNegativeButtonInfo;
        private ButtonInfo mPositiveButtonInfo;

        public Builder(Context context) {
            this.mContext = context;
        }

        public BiometricPrompt build() {
            CharSequence charSequence = this.mBundle.getCharSequence(BiometricPrompt.KEY_TITLE);
            CharSequence charSequence2 = this.mBundle.getCharSequence(BiometricPrompt.KEY_NEGATIVE_TEXT);
            boolean bl = this.mBundle.getBoolean(BiometricPrompt.KEY_USE_DEFAULT_TITLE);
            boolean bl2 = this.mBundle.getBoolean(BiometricPrompt.KEY_ALLOW_DEVICE_CREDENTIAL);
            if (TextUtils.isEmpty(charSequence) && !bl) {
                throw new IllegalArgumentException("Title must be set and non-empty");
            }
            if (TextUtils.isEmpty(charSequence2) && !bl2) {
                throw new IllegalArgumentException("Negative text must be set and non-empty");
            }
            if (!TextUtils.isEmpty(charSequence2) && bl2) {
                throw new IllegalArgumentException("Can't have both negative button behavior and device credential enabled");
            }
            return new BiometricPrompt(this.mContext, this.mBundle, this.mPositiveButtonInfo, this.mNegativeButtonInfo);
        }

        public Builder setConfirmationRequired(boolean bl) {
            this.mBundle.putBoolean(BiometricPrompt.KEY_REQUIRE_CONFIRMATION, bl);
            return this;
        }

        public Builder setDescription(CharSequence charSequence) {
            this.mBundle.putCharSequence(BiometricPrompt.KEY_DESCRIPTION, charSequence);
            return this;
        }

        public Builder setDeviceCredentialAllowed(boolean bl) {
            this.mBundle.putBoolean(BiometricPrompt.KEY_ALLOW_DEVICE_CREDENTIAL, bl);
            return this;
        }

        public Builder setFromConfirmDeviceCredential() {
            this.mBundle.putBoolean(BiometricPrompt.KEY_FROM_CONFIRM_DEVICE_CREDENTIAL, true);
            return this;
        }

        public Builder setNegativeButton(CharSequence charSequence, Executor executor, DialogInterface.OnClickListener onClickListener) {
            if (!TextUtils.isEmpty(charSequence)) {
                if (executor != null) {
                    if (onClickListener != null) {
                        this.mBundle.putCharSequence(BiometricPrompt.KEY_NEGATIVE_TEXT, charSequence);
                        this.mNegativeButtonInfo = new ButtonInfo(executor, onClickListener);
                        return this;
                    }
                    throw new IllegalArgumentException("Listener must not be null");
                }
                throw new IllegalArgumentException("Executor must not be null");
            }
            throw new IllegalArgumentException("Text must be set and non-empty");
        }

        public Builder setPositiveButton(CharSequence charSequence, Executor executor, DialogInterface.OnClickListener onClickListener) {
            if (!TextUtils.isEmpty(charSequence)) {
                if (executor != null) {
                    if (onClickListener != null) {
                        this.mBundle.putCharSequence(BiometricPrompt.KEY_POSITIVE_TEXT, charSequence);
                        this.mPositiveButtonInfo = new ButtonInfo(executor, onClickListener);
                        return this;
                    }
                    throw new IllegalArgumentException("Listener must not be null");
                }
                throw new IllegalArgumentException("Executor must not be null");
            }
            throw new IllegalArgumentException("Text must be set and non-empty");
        }

        public Builder setSubtitle(CharSequence charSequence) {
            this.mBundle.putCharSequence(BiometricPrompt.KEY_SUBTITLE, charSequence);
            return this;
        }

        public Builder setTitle(CharSequence charSequence) {
            this.mBundle.putCharSequence(BiometricPrompt.KEY_TITLE, charSequence);
            return this;
        }

        public Builder setUseDefaultTitle() {
            this.mBundle.putBoolean(BiometricPrompt.KEY_USE_DEFAULT_TITLE, true);
            return this;
        }
    }

    private static class ButtonInfo {
        Executor executor;
        DialogInterface.OnClickListener listener;

        ButtonInfo(Executor executor, DialogInterface.OnClickListener onClickListener) {
            this.executor = executor;
            this.listener = onClickListener;
        }
    }

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

    private class OnAuthenticationCancelListener
    implements CancellationSignal.OnCancelListener {
        private OnAuthenticationCancelListener() {
        }

        @Override
        public void onCancel() {
            BiometricPrompt.this.cancelAuthentication();
        }
    }

}

