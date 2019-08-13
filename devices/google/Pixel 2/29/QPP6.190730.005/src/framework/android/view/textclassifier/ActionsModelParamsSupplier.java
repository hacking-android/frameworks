/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.view.textclassifier.-$
 *  android.view.textclassifier.-$$Lambda
 *  android.view.textclassifier.-$$Lambda$ActionsModelParamsSupplier
 *  android.view.textclassifier.-$$Lambda$ActionsModelParamsSupplier$GCXILXtg_S2la6x__ANOhbYxetw
 */
package android.view.textclassifier;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Base64;
import android.util.KeyValueListParser;
import android.view.textclassifier.-$;
import android.view.textclassifier.Log;
import android.view.textclassifier.ModelFileManager;
import android.view.textclassifier._$$Lambda$ActionsModelParamsSupplier$GCXILXtg_S2la6x__ANOhbYxetw;
import android.view.textclassifier._$$Lambda$ActionsModelParamsSupplier$zElxNeuL3A8paTXvw8GWdpp4rFo;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.Preconditions;
import java.lang.ref.WeakReference;
import java.util.Objects;
import java.util.function.Supplier;

public final class ActionsModelParamsSupplier
implements Supplier<ActionsModelParams> {
    @VisibleForTesting
    static final String KEY_REQUIRED_LOCALES = "required_locales";
    @VisibleForTesting
    static final String KEY_REQUIRED_MODEL_VERSION = "required_model_version";
    @VisibleForTesting
    static final String KEY_SERIALIZED_PRECONDITIONS = "serialized_preconditions";
    private static final String TAG = "androidtc";
    @GuardedBy(value={"mLock"})
    private ActionsModelParams mActionsModelParams;
    private final Context mAppContext;
    private final Object mLock = new Object();
    private final Runnable mOnChangedListener;
    @GuardedBy(value={"mLock"})
    private boolean mParsed = true;
    private final SettingsObserver mSettingsObserver;

    public ActionsModelParamsSupplier(Context object, Runnable runnable) {
        this.mAppContext = Preconditions.checkNotNull(object).getApplicationContext();
        object = runnable == null ? _$$Lambda$ActionsModelParamsSupplier$GCXILXtg_S2la6x__ANOhbYxetw.INSTANCE : runnable;
        this.mOnChangedListener = object;
        this.mSettingsObserver = new SettingsObserver(this.mAppContext, new _$$Lambda$ActionsModelParamsSupplier$zElxNeuL3A8paTXvw8GWdpp4rFo(this));
    }

    static /* synthetic */ void lambda$new$0() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private ActionsModelParams parse(ContentResolver object) {
        String string2 = Settings.Global.getString((ContentResolver)object, "text_classifier_action_model_params");
        if (TextUtils.isEmpty(string2)) {
            return ActionsModelParams.INVALID;
        }
        try {
            object = new KeyValueListParser(',');
            ((KeyValueListParser)object).setString(string2);
            int n = ((KeyValueListParser)object).getInt(KEY_REQUIRED_MODEL_VERSION, -1);
            if (n == -1) {
                Log.w(TAG, "ActionsModelParams.Parse, invalid model version");
                return ActionsModelParams.INVALID;
            }
            string2 = ((KeyValueListParser)object).getString(KEY_REQUIRED_LOCALES, null);
            if (string2 == null) {
                Log.w(TAG, "ActionsModelParams.Parse, invalid locales");
                return ActionsModelParams.INVALID;
            }
            if ((object = ((KeyValueListParser)object).getString(KEY_SERIALIZED_PRECONDITIONS, null)) != null) return new ActionsModelParams(n, string2, Base64.decode((String)object, 2));
            Log.w(TAG, "ActionsModelParams.Parse, invalid preconditions");
            return ActionsModelParams.INVALID;
        }
        catch (Throwable throwable) {
            Log.e(TAG, "Invalid TEXT_CLASSIFIER_ACTION_MODEL_PARAMS, ignore", throwable);
            return ActionsModelParams.INVALID;
        }
    }

    protected void finalize() throws Throwable {
        try {
            this.mAppContext.getContentResolver().unregisterContentObserver(this.mSettingsObserver);
            return;
        }
        finally {
            super.finalize();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public ActionsModelParams get() {
        Object object = this.mLock;
        synchronized (object) {
            if (this.mParsed) {
                this.mActionsModelParams = this.parse(this.mAppContext.getContentResolver());
                this.mParsed = false;
            }
            return this.mActionsModelParams;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public /* synthetic */ void lambda$new$1$ActionsModelParamsSupplier() {
        Object object = this.mLock;
        synchronized (object) {
            Log.v(TAG, "Settings.Global.TEXT_CLASSIFIER_ACTION_MODEL_PARAMS is updated");
            this.mParsed = true;
            this.mOnChangedListener.run();
            return;
        }
    }

    public static final class ActionsModelParams {
        public static final ActionsModelParams INVALID = new ActionsModelParams(-1, "", new byte[0]);
        private final String mRequiredModelLocales;
        private final int mRequiredModelVersion;
        private final byte[] mSerializedPreconditions;

        public ActionsModelParams(int n, String string2, byte[] arrby) {
            this.mRequiredModelVersion = n;
            this.mRequiredModelLocales = Preconditions.checkNotNull(string2);
            this.mSerializedPreconditions = Preconditions.checkNotNull(arrby);
        }

        public byte[] getSerializedPreconditions(ModelFileManager.ModelFile modelFile) {
            int n;
            if (this == INVALID) {
                return null;
            }
            int n2 = modelFile.getVersion();
            if (n2 != (n = this.mRequiredModelVersion)) {
                Log.w(ActionsModelParamsSupplier.TAG, String.format("Not applying mSerializedPreconditions, required version=%d, actual=%d", n, modelFile.getVersion()));
                return null;
            }
            if (!Objects.equals(modelFile.getSupportedLocalesStr(), this.mRequiredModelLocales)) {
                Log.w(ActionsModelParamsSupplier.TAG, String.format("Not applying mSerializedPreconditions, required locales=%s, actual=%s", this.mRequiredModelLocales, modelFile.getSupportedLocalesStr()));
                return null;
            }
            return this.mSerializedPreconditions;
        }
    }

    private static final class SettingsObserver
    extends ContentObserver {
        private final WeakReference<Runnable> mOnChangedListener;

        SettingsObserver(Context context, Runnable runnable) {
            super(null);
            this.mOnChangedListener = new WeakReference<Runnable>(runnable);
            context.getContentResolver().registerContentObserver(Settings.Global.getUriFor("text_classifier_action_model_params"), false, this);
        }

        @Override
        public void onChange(boolean bl) {
            if (this.mOnChangedListener.get() != null) {
                ((Runnable)this.mOnChangedListener.get()).run();
            }
        }
    }

}

