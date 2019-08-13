/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.view.textclassifier.-$
 *  android.view.textclassifier.-$$Lambda
 *  android.view.textclassifier.-$$Lambda$TextClassificationManager
 *  android.view.textclassifier.-$$Lambda$TextClassificationManager$VwZ4EV_1i6FbjO7TtyaAnFL3oe0
 */
package android.view.textclassifier;

import android.annotation.UnsupportedAppUsage;
import android.app.ActivityThread;
import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.ServiceManager;
import android.provider.DeviceConfig;
import android.provider.Settings;
import android.service.textclassifier.TextClassifierService;
import android.view.textclassifier.-$;
import android.view.textclassifier.Log;
import android.view.textclassifier.SystemTextClassifier;
import android.view.textclassifier.TextClassificationConstants;
import android.view.textclassifier.TextClassificationContext;
import android.view.textclassifier.TextClassificationSession;
import android.view.textclassifier.TextClassificationSessionFactory;
import android.view.textclassifier.TextClassifier;
import android.view.textclassifier.TextClassifierImpl;
import android.view.textclassifier._$$Lambda$TextClassificationManager$SIydN2POphTO3AmPTLEMmXPLSKY;
import android.view.textclassifier._$$Lambda$TextClassificationManager$VwZ4EV_1i6FbjO7TtyaAnFL3oe0;
import android.view.textclassifier._$$Lambda$TextClassificationManager$oweIEhDWxy3_0kZSXp3oRbSuNW4;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.IndentingPrintWriter;
import com.android.internal.util.Preconditions;
import java.lang.ref.WeakReference;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

public final class TextClassificationManager {
    private static final String LOG_TAG = "TextClassificationManager";
    private static final TextClassificationConstants sDefaultSettings = new TextClassificationConstants((Supplier<String>)_$$Lambda$TextClassificationManager$VwZ4EV_1i6FbjO7TtyaAnFL3oe0.INSTANCE);
    private final Context mContext;
    @GuardedBy(value={"mLock"})
    private TextClassifier mCustomTextClassifier;
    private final TextClassificationSessionFactory mDefaultSessionFactory = new _$$Lambda$TextClassificationManager$SIydN2POphTO3AmPTLEMmXPLSKY(this);
    @GuardedBy(value={"mLock"})
    private TextClassifier mLocalTextClassifier;
    private final Object mLock = new Object();
    @GuardedBy(value={"mLock"})
    private TextClassificationSessionFactory mSessionFactory;
    @GuardedBy(value={"mLock"})
    private TextClassificationConstants mSettings;
    private final SettingsObserver mSettingsObserver;
    @GuardedBy(value={"mLock"})
    private TextClassifier mSystemTextClassifier;

    public TextClassificationManager(Context context) {
        this.mContext = Preconditions.checkNotNull(context);
        this.mSessionFactory = this.mDefaultSessionFactory;
        this.mSettingsObserver = new SettingsObserver(this);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private TextClassifier getLocalTextClassifier() {
        Object object = this.mLock;
        synchronized (object) {
            if (this.mLocalTextClassifier != null) return this.mLocalTextClassifier;
            if (this.getSettings().isLocalTextClassifierEnabled()) {
                TextClassifier textClassifier;
                this.mLocalTextClassifier = textClassifier = new TextClassifierImpl(this.mContext, this.getSettings(), TextClassifier.NO_OP);
                return this.mLocalTextClassifier;
            } else {
                Log.d(LOG_TAG, "Local TextClassifier disabled");
                this.mLocalTextClassifier = TextClassifier.NO_OP;
            }
            return this.mLocalTextClassifier;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private TextClassificationConstants getSettings() {
        Object object = this.mLock;
        synchronized (object) {
            TextClassificationConstants textClassificationConstants;
            if (this.mSettings != null) return this.mSettings;
            _$$Lambda$TextClassificationManager$oweIEhDWxy3_0kZSXp3oRbSuNW4 _$$Lambda$TextClassificationManager$oweIEhDWxy3_0kZSXp3oRbSuNW4 = new _$$Lambda$TextClassificationManager$oweIEhDWxy3_0kZSXp3oRbSuNW4(this);
            this.mSettings = textClassificationConstants = new TextClassificationConstants(_$$Lambda$TextClassificationManager$oweIEhDWxy3_0kZSXp3oRbSuNW4);
            return this.mSettings;
        }
    }

    public static TextClassificationConstants getSettings(Context object) {
        Preconditions.checkNotNull(object);
        object = ((Context)object).getSystemService(TextClassificationManager.class);
        if (object != null) {
            return TextClassificationManager.super.getSettings();
        }
        return sDefaultSettings;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private TextClassifier getSystemTextClassifier() {
        Object object = this.mLock;
        synchronized (object) {
            boolean bl;
            if (this.mSystemTextClassifier == null && (bl = this.isSystemTextClassifierEnabled())) {
                try {
                    SystemTextClassifier systemTextClassifier = new SystemTextClassifier(this.mContext, this.getSettings());
                    this.mSystemTextClassifier = systemTextClassifier;
                    Log.d(LOG_TAG, "Initialized SystemTextClassifier");
                }
                catch (ServiceManager.ServiceNotFoundException serviceNotFoundException) {
                    Log.e(LOG_TAG, "Could not initialize SystemTextClassifier", serviceNotFoundException);
                }
            }
        }
        object = this.mSystemTextClassifier;
        if (object != null) {
            return object;
        }
        return TextClassifier.NO_OP;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void invalidate() {
        Object object = this.mLock;
        synchronized (object) {
            this.mSettings = null;
            this.mLocalTextClassifier = null;
            this.mSystemTextClassifier = null;
            return;
        }
    }

    private boolean isSystemTextClassifierEnabled() {
        boolean bl = this.getSettings().isSystemTextClassifierEnabled() && TextClassifierService.getServiceComponentName(this.mContext) != null;
        return bl;
    }

    static /* synthetic */ String lambda$static$0() {
        return null;
    }

    public TextClassifier createTextClassificationSession(TextClassificationContext object) {
        Preconditions.checkNotNull(object);
        object = this.mSessionFactory.createTextClassificationSession((TextClassificationContext)object);
        Preconditions.checkNotNull(object, "Session Factory should never return null");
        return object;
    }

    public TextClassifier createTextClassificationSession(TextClassificationContext textClassificationContext, TextClassifier textClassifier) {
        Preconditions.checkNotNull(textClassificationContext);
        Preconditions.checkNotNull(textClassifier);
        return new TextClassificationSession(textClassificationContext, textClassifier);
    }

    public void dump(IndentingPrintWriter indentingPrintWriter) {
        this.getLocalTextClassifier().dump(indentingPrintWriter);
        this.getSystemTextClassifier().dump(indentingPrintWriter);
        this.getSettings().dump(indentingPrintWriter);
    }

    protected void finalize() throws Throwable {
        try {
            if (this.mSettingsObserver != null) {
                this.getApplicationContext().getContentResolver().unregisterContentObserver(this.mSettingsObserver);
                DeviceConfig.removeOnPropertiesChangedListener(this.mSettingsObserver);
            }
            return;
        }
        finally {
            super.finalize();
        }
    }

    Context getApplicationContext() {
        Context context = this.mContext.getApplicationContext() != null ? this.mContext.getApplicationContext() : this.mContext;
        return context;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public TextClassifier getTextClassifier() {
        Object object = this.mLock;
        synchronized (object) {
            if (this.mCustomTextClassifier != null) {
                return this.mCustomTextClassifier;
            }
            if (!this.isSystemTextClassifierEnabled()) return this.getLocalTextClassifier();
            return this.getSystemTextClassifier();
        }
    }

    @UnsupportedAppUsage
    public TextClassifier getTextClassifier(int n) {
        if (n != 0) {
            return this.getSystemTextClassifier();
        }
        return this.getLocalTextClassifier();
    }

    @VisibleForTesting
    public void invalidateForTesting() {
        this.invalidate();
    }

    public /* synthetic */ String lambda$getSettings$2$TextClassificationManager() {
        return Settings.Global.getString(this.getApplicationContext().getContentResolver(), "text_classifier_constants");
    }

    public /* synthetic */ TextClassifier lambda$new$1$TextClassificationManager(TextClassificationContext textClassificationContext) {
        return new TextClassificationSession(textClassificationContext, this.getTextClassifier());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setTextClassificationSessionFactory(TextClassificationSessionFactory textClassificationSessionFactory) {
        Object object = this.mLock;
        synchronized (object) {
            this.mSessionFactory = textClassificationSessionFactory != null ? textClassificationSessionFactory : this.mDefaultSessionFactory;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setTextClassifier(TextClassifier textClassifier) {
        Object object = this.mLock;
        synchronized (object) {
            this.mCustomTextClassifier = textClassifier;
            return;
        }
    }

    private static final class SettingsObserver
    extends ContentObserver
    implements DeviceConfig.OnPropertiesChangedListener {
        private final WeakReference<TextClassificationManager> mTcm;

        SettingsObserver(TextClassificationManager textClassificationManager) {
            super(null);
            this.mTcm = new WeakReference<TextClassificationManager>(textClassificationManager);
            textClassificationManager.getApplicationContext().getContentResolver().registerContentObserver(Settings.Global.getUriFor("text_classifier_constants"), false, this);
            DeviceConfig.addOnPropertiesChangedListener("textclassifier", ActivityThread.currentApplication().getMainExecutor(), this);
        }

        private void invalidateSettings() {
            TextClassificationManager textClassificationManager = (TextClassificationManager)this.mTcm.get();
            if (textClassificationManager != null) {
                textClassificationManager.invalidate();
            }
        }

        @Override
        public void onChange(boolean bl) {
            this.invalidateSettings();
        }

        @Override
        public void onPropertiesChanged(DeviceConfig.Properties properties) {
            this.invalidateSettings();
        }
    }

}

