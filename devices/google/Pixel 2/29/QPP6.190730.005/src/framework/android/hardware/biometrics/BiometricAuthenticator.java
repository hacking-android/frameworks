/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.biometrics;

import android.hardware.biometrics.CryptoObject;
import android.os.CancellationSignal;
import android.os.Parcelable;
import java.util.concurrent.Executor;

public interface BiometricAuthenticator {
    public static final int TYPE_FACE = 4;
    public static final int TYPE_FINGERPRINT = 1;
    public static final int TYPE_IRIS = 2;
    public static final int TYPE_NONE = 0;

    default public void authenticate(CryptoObject cryptoObject, CancellationSignal cancellationSignal, Executor executor, AuthenticationCallback authenticationCallback) {
        throw new UnsupportedOperationException("Stub!");
    }

    default public void authenticate(CancellationSignal cancellationSignal, Executor executor, AuthenticationCallback authenticationCallback) {
        throw new UnsupportedOperationException("Stub!");
    }

    default public boolean hasEnrolledTemplates() {
        throw new UnsupportedOperationException("Stub!");
    }

    default public boolean hasEnrolledTemplates(int n) {
        throw new UnsupportedOperationException("Stub!");
    }

    default public boolean isHardwareDetected() {
        throw new UnsupportedOperationException("Stub!");
    }

    default public void setActiveUser(int n) {
        throw new UnsupportedOperationException("Stub!");
    }

    public static abstract class AuthenticationCallback {
        public void onAuthenticationAcquired(int n) {
        }

        public void onAuthenticationError(int n, CharSequence charSequence) {
        }

        public void onAuthenticationFailed() {
        }

        public void onAuthenticationHelp(int n, CharSequence charSequence) {
        }
    }

    public static class AuthenticationResult {
        private CryptoObject mCryptoObject;
        private Identifier mIdentifier;
        private int mUserId;

        public AuthenticationResult() {
        }

        public AuthenticationResult(CryptoObject cryptoObject, Identifier identifier, int n) {
            this.mCryptoObject = cryptoObject;
            this.mIdentifier = identifier;
            this.mUserId = n;
        }

        public CryptoObject getCryptoObject() {
            return this.mCryptoObject;
        }

        public Identifier getId() {
            return this.mIdentifier;
        }

        public int getUserId() {
            return this.mUserId;
        }
    }

    public static abstract class Identifier
    implements Parcelable {
        private int mBiometricId;
        private long mDeviceId;
        private CharSequence mName;

        public Identifier() {
        }

        public Identifier(CharSequence charSequence, int n, long l) {
            this.mName = charSequence;
            this.mBiometricId = n;
            this.mDeviceId = l;
        }

        public int getBiometricId() {
            return this.mBiometricId;
        }

        public long getDeviceId() {
            return this.mDeviceId;
        }

        public CharSequence getName() {
            return this.mName;
        }

        public void setDeviceId(long l) {
            this.mDeviceId = l;
        }

        public void setName(CharSequence charSequence) {
            this.mName = charSequence;
        }
    }

}

