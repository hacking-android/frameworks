/*
 * Decompiled with CFR 0.145.
 */
package android.security;

import android.content.ContentResolver;
import android.content.Context;
import android.os.IBinder;
import android.os.RemoteException;
import android.provider.Settings;
import android.security.ConfirmationAlreadyPresentingException;
import android.security.ConfirmationCallback;
import android.security.ConfirmationNotAvailableException;
import android.security.IConfirmationPromptCallback;
import android.security.KeyStore;
import android.text.TextUtils;
import android.util.Log;
import java.util.Locale;
import java.util.concurrent.Executor;

public class ConfirmationPrompt {
    private static final String TAG = "ConfirmationPrompt";
    private static final int UI_OPTION_ACCESSIBILITY_INVERTED_FLAG = 1;
    private static final int UI_OPTION_ACCESSIBILITY_MAGNIFIED_FLAG = 2;
    private ConfirmationCallback mCallback;
    private final IBinder mCallbackBinder = new IConfirmationPromptCallback.Stub(){

        @Override
        public void onConfirmationPromptCompleted(final int n, final byte[] arrby) throws RemoteException {
            if (ConfirmationPrompt.this.mCallback != null) {
                final ConfirmationCallback confirmationCallback = ConfirmationPrompt.this.mCallback;
                Executor executor = ConfirmationPrompt.this.mExecutor;
                ConfirmationPrompt.this.mCallback = null;
                ConfirmationPrompt.this.mExecutor = null;
                if (executor == null) {
                    ConfirmationPrompt.this.doCallback(n, arrby, confirmationCallback);
                } else {
                    executor.execute(new Runnable(){

                        @Override
                        public void run() {
                            ConfirmationPrompt.this.doCallback(n, arrby, confirmationCallback);
                        }
                    });
                }
            }
        }

    };
    private Context mContext;
    private Executor mExecutor;
    private byte[] mExtraData;
    private final KeyStore mKeyStore = KeyStore.getInstance();
    private CharSequence mPromptText;

    private ConfirmationPrompt(Context context, CharSequence charSequence, byte[] arrby) {
        this.mContext = context;
        this.mPromptText = charSequence;
        this.mExtraData = arrby;
    }

    private void doCallback(int n, byte[] object, ConfirmationCallback confirmationCallback) {
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 5) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Unexpected responseCode=");
                        ((StringBuilder)object).append(n);
                        ((StringBuilder)object).append(" from onConfirmtionPromptCompleted() callback.");
                        confirmationCallback.onError(new Exception(((StringBuilder)object).toString()));
                    } else {
                        confirmationCallback.onError(new Exception("System error returned by ConfirmationUI."));
                    }
                } else {
                    confirmationCallback.onCanceled();
                }
            } else {
                confirmationCallback.onDismissed();
            }
        } else {
            confirmationCallback.onConfirmed((byte[])object);
        }
    }

    private int getUiOptionsAsFlags() {
        float f;
        int n = 0;
        int n2 = 0;
        int n3 = n;
        ContentResolver contentResolver = this.mContext.getContentResolver();
        n3 = n;
        if (Settings.Secure.getInt(contentResolver, "accessibility_display_inversion_enabled") == 1) {
            n2 = false | true;
        }
        n3 = n2;
        try {
            f = Settings.System.getFloat(contentResolver, "font_scale");
            n3 = n2;
        }
        catch (Settings.SettingNotFoundException settingNotFoundException) {
            Log.w(TAG, "Unexpected SettingNotFoundException");
        }
        if ((double)f > 1.0) {
            n3 = n2 | 2;
        }
        return n3;
    }

    private static boolean isAccessibilityServiceRunning(Context context) {
        boolean bl = false;
        boolean bl2 = false;
        try {
            int n = Settings.Secure.getInt(context.getContentResolver(), "accessibility_enabled");
            if (n == 1) {
                bl2 = true;
            }
        }
        catch (Settings.SettingNotFoundException settingNotFoundException) {
            Log.w(TAG, "Unexpected SettingNotFoundException");
            settingNotFoundException.printStackTrace();
            bl2 = bl;
        }
        return bl2;
    }

    public static boolean isSupported(Context context) {
        if (ConfirmationPrompt.isAccessibilityServiceRunning(context)) {
            return false;
        }
        return KeyStore.getInstance().isConfirmationPromptSupported();
    }

    public void cancelPrompt() {
        int n = this.mKeyStore.cancelConfirmationPrompt(this.mCallbackBinder);
        if (n == 0) {
            return;
        }
        if (n == 3) {
            throw new IllegalStateException();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unexpected responseCode=");
        stringBuilder.append(n);
        stringBuilder.append(" from cancelConfirmationPrompt() call.");
        Log.w(TAG, stringBuilder.toString());
        throw new IllegalStateException();
    }

    public void presentPrompt(Executor object, ConfirmationCallback confirmationCallback) throws ConfirmationAlreadyPresentingException, ConfirmationNotAvailableException {
        if (this.mCallback == null) {
            if (!ConfirmationPrompt.isAccessibilityServiceRunning(this.mContext)) {
                this.mCallback = confirmationCallback;
                this.mExecutor = object;
                int n = this.getUiOptionsAsFlags();
                object = Locale.getDefault().toLanguageTag();
                n = this.mKeyStore.presentConfirmationPrompt(this.mCallbackBinder, this.mPromptText.toString(), this.mExtraData, (String)object, n);
                if (n != 0) {
                    if (n != 3) {
                        if (n != 6) {
                            if (n != 65536) {
                                object = new StringBuilder();
                                ((StringBuilder)object).append("Unexpected responseCode=");
                                ((StringBuilder)object).append(n);
                                ((StringBuilder)object).append(" from presentConfirmationPrompt() call.");
                                Log.w(TAG, ((StringBuilder)object).toString());
                                throw new IllegalArgumentException();
                            }
                            throw new IllegalArgumentException();
                        }
                        throw new ConfirmationNotAvailableException();
                    }
                    throw new ConfirmationAlreadyPresentingException();
                }
                return;
            }
            throw new ConfirmationNotAvailableException();
        }
        throw new ConfirmationAlreadyPresentingException();
    }

    public static final class Builder {
        private Context mContext;
        private byte[] mExtraData;
        private CharSequence mPromptText;

        public Builder(Context context) {
            this.mContext = context;
        }

        public ConfirmationPrompt build() {
            if (!TextUtils.isEmpty(this.mPromptText)) {
                byte[] arrby = this.mExtraData;
                if (arrby != null) {
                    return new ConfirmationPrompt(this.mContext, this.mPromptText, arrby);
                }
                throw new IllegalArgumentException("extraData must be set");
            }
            throw new IllegalArgumentException("prompt text must be set and non-empty");
        }

        public Builder setExtraData(byte[] arrby) {
            this.mExtraData = arrby;
            return this;
        }

        public Builder setPromptText(CharSequence charSequence) {
            this.mPromptText = charSequence;
            return this;
        }
    }

}

