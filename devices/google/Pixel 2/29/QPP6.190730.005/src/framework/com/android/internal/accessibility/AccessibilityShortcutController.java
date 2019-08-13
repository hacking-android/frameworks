/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.android.internal.accessibility.-$
 *  com.android.internal.accessibility.-$$Lambda
 *  com.android.internal.accessibility.-$$Lambda$AccessibilityShortcutController
 *  com.android.internal.accessibility.-$$Lambda$AccessibilityShortcutController$TtsPrompt
 *  com.android.internal.accessibility.-$$Lambda$AccessibilityShortcutController$TtsPrompt$HwizF4cvqRFiaqAcMrC7W8y6zYA
 *  com.android.internal.accessibility.-$$Lambda$qdzoyIBhDB17ZFWPp1Rf8ICv-R8
 */
package com.android.internal.accessibility;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.ActivityManager;
import android.app.ActivityThread;
import android.app.AlertDialog;
import android.app.ContextImpl;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.media.AudioAttributes;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.provider.Settings;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Slog;
import android.view.Window;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import android.widget.Toast;
import com.android.internal.accessibility.-$;
import com.android.internal.accessibility._$$Lambda$AccessibilityShortcutController$2NcDVJHkpsPbwr45v1_NfIM8row;
import com.android.internal.accessibility._$$Lambda$AccessibilityShortcutController$T96D356_n5VObNOonEIYV8s83Fc;
import com.android.internal.accessibility._$$Lambda$AccessibilityShortcutController$TtsPrompt$HwizF4cvqRFiaqAcMrC7W8y6zYA;
import com.android.internal.accessibility._$$Lambda$AccessibilityShortcutController$cQtLiNhDc4H3BvMBZy00zj21oKg;
import com.android.internal.accessibility._$$Lambda$qdzoyIBhDB17ZFWPp1Rf8ICv_R8;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.function.pooled.PooledLambda;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class AccessibilityShortcutController {
    public static final ComponentName COLOR_INVERSION_COMPONENT_NAME = new ComponentName("com.android.server.accessibility", "ColorInversion");
    public static final ComponentName DALTONIZER_COMPONENT_NAME = new ComponentName("com.android.server.accessibility", "Daltonizer");
    private static final String TAG = "AccessibilityShortcutController";
    private static final AudioAttributes VIBRATION_ATTRIBUTES = new AudioAttributes.Builder().setContentType(4).setUsage(11).build();
    private static Map<ComponentName, ToggleableFrameworkFeatureInfo> sFrameworkShortcutFeaturesMap;
    private AlertDialog mAlertDialog;
    private final Context mContext;
    private boolean mEnabledOnLockScreen;
    public FrameworkObjectProvider mFrameworkObjectProvider = new FrameworkObjectProvider();
    private final Handler mHandler;
    private boolean mIsShortcutEnabled;
    private int mUserId;

    public AccessibilityShortcutController(Context object, Handler handler, int n) {
        this.mContext = object;
        this.mHandler = handler;
        this.mUserId = n;
        object = new ContentObserver(handler){

            @Override
            public void onChange(boolean bl, Uri uri, int n) {
                if (n == AccessibilityShortcutController.this.mUserId) {
                    AccessibilityShortcutController.this.onSettingsChanged();
                }
            }
        };
        this.mContext.getContentResolver().registerContentObserver(Settings.Secure.getUriFor("accessibility_shortcut_target_service"), false, (ContentObserver)object, -1);
        this.mContext.getContentResolver().registerContentObserver(Settings.Secure.getUriFor("accessibility_shortcut_enabled"), false, (ContentObserver)object, -1);
        this.mContext.getContentResolver().registerContentObserver(Settings.Secure.getUriFor("accessibility_shortcut_on_lock_screen"), false, (ContentObserver)object, -1);
        this.mContext.getContentResolver().registerContentObserver(Settings.Secure.getUriFor("accessibility_shortcut_dialog_shown"), false, (ContentObserver)object, -1);
        this.setCurrentUser(this.mUserId);
    }

    private AlertDialog createShortcutWarningDialog(int n) {
        String string2 = this.getShortcutFeatureDescription(true);
        if (string2 == null) {
            return null;
        }
        string2 = String.format(this.mContext.getString(17039442), string2);
        FrameworkObjectProvider frameworkObjectProvider = this.mFrameworkObjectProvider;
        return frameworkObjectProvider.getAlertDialogBuilder(frameworkObjectProvider.getSystemUiContext()).setTitle(17039443).setMessage(string2).setCancelable(false).setPositiveButton(17040232, null).setNegativeButton(17039864, (DialogInterface.OnClickListener)new _$$Lambda$AccessibilityShortcutController$2NcDVJHkpsPbwr45v1_NfIM8row(this, n)).setOnCancelListener(new _$$Lambda$AccessibilityShortcutController$T96D356_n5VObNOonEIYV8s83Fc(this, n)).create();
    }

    public static Map<ComponentName, ToggleableFrameworkFeatureInfo> getFrameworkShortcutFeaturesMap() {
        if (sFrameworkShortcutFeaturesMap == null) {
            ArrayMap<ComponentName, ToggleableFrameworkFeatureInfo> arrayMap = new ArrayMap<ComponentName, ToggleableFrameworkFeatureInfo>(2);
            arrayMap.put(COLOR_INVERSION_COMPONENT_NAME, new ToggleableFrameworkFeatureInfo("accessibility_display_inversion_enabled", "1", "0", 17039661));
            arrayMap.put(DALTONIZER_COMPONENT_NAME, new ToggleableFrameworkFeatureInfo("accessibility_display_daltonizer_enabled", "1", "0", 17039660));
            sFrameworkShortcutFeaturesMap = Collections.unmodifiableMap(arrayMap);
        }
        return sFrameworkShortcutFeaturesMap;
    }

    private AccessibilityServiceInfo getInfoForTargetService() {
        String string2 = AccessibilityShortcutController.getTargetServiceComponentNameString(this.mContext, -2);
        if (string2 == null) {
            return null;
        }
        AccessibilityManager accessibilityManager = this.mFrameworkObjectProvider.getAccessibilityManagerInstance(this.mContext);
        return accessibilityManager.getInstalledServiceInfoWithComponentName(ComponentName.unflattenFromString(string2));
    }

    private String getShortcutFeatureDescription(boolean bl) {
        Object object = AccessibilityShortcutController.getTargetServiceComponentNameString(this.mContext, -2);
        if (object == null) {
            return null;
        }
        object = ComponentName.unflattenFromString((String)object);
        Object object2 = AccessibilityShortcutController.getFrameworkShortcutFeaturesMap().get(object);
        if (object2 != null) {
            return ((ToggleableFrameworkFeatureInfo)object2).getLabel(this.mContext);
        }
        object2 = this.mFrameworkObjectProvider.getAccessibilityManagerInstance(this.mContext).getInstalledServiceInfoWithComponentName((ComponentName)object);
        if (object2 == null) {
            return null;
        }
        PackageManager packageManager = this.mContext.getPackageManager();
        object = ((AccessibilityServiceInfo)object2).getResolveInfo().loadLabel(packageManager).toString();
        object2 = ((AccessibilityServiceInfo)object2).loadSummary(packageManager);
        if (bl && !TextUtils.isEmpty((CharSequence)object2)) {
            return String.format("%s\n%s", object, object2);
        }
        return object;
    }

    public static String getTargetServiceComponentNameString(Context context, int n) {
        String string2 = Settings.Secure.getStringForUser(context.getContentResolver(), "accessibility_shortcut_target_service", n);
        if (string2 != null) {
            return string2;
        }
        return context.getString(17039695);
    }

    private boolean hasFeatureLeanback() {
        return this.mContext.getPackageManager().hasSystemFeature("android.software.leanback");
    }

    private boolean isServiceEnabled(AccessibilityServiceInfo accessibilityServiceInfo) {
        return this.mFrameworkObjectProvider.getAccessibilityManagerInstance(this.mContext).getEnabledAccessibilityServiceList(-1).contains(accessibilityServiceInfo);
    }

    static /* synthetic */ void lambda$performTtsPrompt$2(TtsPrompt ttsPrompt, DialogInterface dialogInterface) {
        ttsPrompt.dismiss();
    }

    private boolean performTtsPrompt(AlertDialog alertDialog) {
        String string2 = this.getShortcutFeatureDescription(false);
        AccessibilityServiceInfo accessibilityServiceInfo = this.getInfoForTargetService();
        if (!TextUtils.isEmpty(string2) && accessibilityServiceInfo != null) {
            if ((accessibilityServiceInfo.flags & 1024) == 0) {
                return false;
            }
            alertDialog.setOnDismissListener(new _$$Lambda$AccessibilityShortcutController$cQtLiNhDc4H3BvMBZy00zj21oKg(new TtsPrompt(string2)));
            return true;
        }
        return false;
    }

    private void playNotificationTone() {
        int n = this.hasFeatureLeanback() ? 11 : 10;
        Ringtone ringtone = this.mFrameworkObjectProvider.getRingtone(this.mContext, Settings.System.DEFAULT_NOTIFICATION_URI);
        if (ringtone != null) {
            ringtone.setAudioAttributes(new AudioAttributes.Builder().setUsage(n).build());
            ringtone.play();
        }
    }

    public boolean isAccessibilityShortcutAvailable(boolean bl) {
        bl = this.mIsShortcutEnabled && (!bl || this.mEnabledOnLockScreen);
        return bl;
    }

    public /* synthetic */ void lambda$createShortcutWarningDialog$0$AccessibilityShortcutController(int n, DialogInterface dialogInterface, int n2) {
        Settings.Secure.putStringForUser(this.mContext.getContentResolver(), "accessibility_shortcut_target_service", "", n);
    }

    public /* synthetic */ void lambda$createShortcutWarningDialog$1$AccessibilityShortcutController(int n, DialogInterface dialogInterface) {
        Settings.Secure.putIntForUser(this.mContext.getContentResolver(), "accessibility_shortcut_dialog_shown", 0, n);
    }

    public void onSettingsChanged() {
        boolean bl = TextUtils.isEmpty(AccessibilityShortcutController.getTargetServiceComponentNameString(this.mContext, this.mUserId));
        boolean bl2 = true;
        ContentResolver contentResolver = this.mContext.getContentResolver();
        boolean bl3 = Settings.Secure.getIntForUser(contentResolver, "accessibility_shortcut_enabled", 1, this.mUserId) == 1;
        boolean bl4 = Settings.Secure.getIntForUser(contentResolver, "accessibility_shortcut_on_lock_screen", Settings.Secure.getIntForUser(contentResolver, "accessibility_shortcut_dialog_shown", 0, this.mUserId), this.mUserId) == 1;
        this.mEnabledOnLockScreen = bl4;
        bl4 = bl3 && bl ^ true ? bl2 : false;
        this.mIsShortcutEnabled = bl4;
    }

    public void performAccessibilityShortcut() {
        Slog.d(TAG, "Accessibility shortcut activated");
        Object object = this.mContext.getContentResolver();
        int n = ActivityManager.getCurrentUser();
        int n2 = Settings.Secure.getIntForUser((ContentResolver)object, "accessibility_shortcut_dialog_shown", 0, n);
        Object object2 = (Vibrator)this.mContext.getSystemService("vibrator");
        if (object2 != null && ((Vibrator)object2).hasVibrator()) {
            ((Vibrator)object2).vibrate(ArrayUtils.convertToLongArray(this.mContext.getResources().getIntArray(17236034)), -1, VIBRATION_ATTRIBUTES);
        }
        if (n2 == 0) {
            this.mAlertDialog = this.createShortcutWarningDialog(n);
            object2 = this.mAlertDialog;
            if (object2 == null) {
                return;
            }
            if (!this.performTtsPrompt((AlertDialog)object2)) {
                this.playNotificationTone();
            }
            Window window = this.mAlertDialog.getWindow();
            object2 = window.getAttributes();
            ((WindowManager.LayoutParams)object2).type = 2009;
            window.setAttributes((WindowManager.LayoutParams)object2);
            this.mAlertDialog.show();
            Settings.Secure.putIntForUser((ContentResolver)object, "accessibility_shortcut_dialog_shown", 1, n);
        } else {
            String string2;
            this.playNotificationTone();
            object = this.mAlertDialog;
            if (object != null) {
                ((Dialog)object).dismiss();
                this.mAlertDialog = null;
            }
            if ((string2 = this.getShortcutFeatureDescription(false)) == null) {
                Slog.e(TAG, "Accessibility shortcut set to invalid service");
                return;
            }
            object2 = this.getInfoForTargetService();
            if (object2 != null) {
                object = this.mContext;
                n = this.isServiceEnabled((AccessibilityServiceInfo)object2) ? 17039439 : 17039440;
                object = String.format(((Context)object).getString(n), string2);
                object2 = this.mFrameworkObjectProvider.makeToastFromText(this.mContext, (CharSequence)object, 1);
                object = ((Toast)object2).getWindowParams();
                ((WindowManager.LayoutParams)object).privateFlags |= 16;
                ((Toast)object2).show();
            }
            this.mFrameworkObjectProvider.getAccessibilityManagerInstance(this.mContext).performAccessibilityShortcut();
        }
    }

    public void setCurrentUser(int n) {
        this.mUserId = n;
        this.onSettingsChanged();
    }

    public static class FrameworkObjectProvider {
        public AccessibilityManager getAccessibilityManagerInstance(Context context) {
            return AccessibilityManager.getInstance(context);
        }

        public AlertDialog.Builder getAlertDialogBuilder(Context context) {
            return new AlertDialog.Builder(context);
        }

        public Ringtone getRingtone(Context context, Uri uri) {
            return RingtoneManager.getRingtone(context, uri);
        }

        public Context getSystemUiContext() {
            return ActivityThread.currentActivityThread().getSystemUiContext();
        }

        public TextToSpeech getTextToSpeech(Context context, TextToSpeech.OnInitListener onInitListener) {
            return new TextToSpeech(context, onInitListener);
        }

        public Toast makeToastFromText(Context context, CharSequence charSequence, int n) {
            return Toast.makeText(context, charSequence, n);
        }
    }

    public static class ToggleableFrameworkFeatureInfo {
        private int mIconDrawableId;
        private final int mLabelStringResourceId;
        private final String mSettingKey;
        private final String mSettingOffValue;
        private final String mSettingOnValue;

        ToggleableFrameworkFeatureInfo(String string2, String string3, String string4, int n) {
            this.mSettingKey = string2;
            this.mSettingOnValue = string3;
            this.mSettingOffValue = string4;
            this.mLabelStringResourceId = n;
        }

        public String getLabel(Context context) {
            return context.getString(this.mLabelStringResourceId);
        }

        public String getSettingKey() {
            return this.mSettingKey;
        }

        public String getSettingOffValue() {
            return this.mSettingOffValue;
        }

        public String getSettingOnValue() {
            return this.mSettingOnValue;
        }
    }

    private class TtsPrompt
    implements TextToSpeech.OnInitListener {
        private boolean mDismiss;
        private final CharSequence mText;
        private TextToSpeech mTts;

        TtsPrompt(String string2) {
            this.mText = AccessibilityShortcutController.this.mContext.getString(17039441, string2);
            this.mTts = AccessibilityShortcutController.this.mFrameworkObjectProvider.getTextToSpeech(AccessibilityShortcutController.this.mContext, this);
        }

        public static /* synthetic */ void lambda$HwizF4cvqRFiaqAcMrC7W8y6zYA(TtsPrompt ttsPrompt) {
            ttsPrompt.play();
        }

        private void play() {
            if (this.mDismiss) {
                return;
            }
            int n = -1;
            if (this.setLanguage(Locale.getDefault())) {
                n = this.mTts.speak(this.mText, 0, null, null);
            }
            if (n != 0) {
                Slog.d(AccessibilityShortcutController.TAG, "Tts play fail");
                AccessibilityShortcutController.this.playNotificationTone();
            }
        }

        private boolean setLanguage(Locale object) {
            int n = this.mTts.isLanguageAvailable((Locale)object);
            if (n != -1 && n != -2) {
                this.mTts.setLanguage((Locale)object);
                object = this.mTts.getVoice();
                return !(object == null || ((Voice)object).getFeatures() != null && ((Voice)object).getFeatures().contains("notInstalled"));
                {
                }
            }
            return false;
        }

        public void dismiss() {
            this.mDismiss = true;
            AccessibilityShortcutController.this.mHandler.sendMessage(PooledLambda.obtainMessage(_$$Lambda$qdzoyIBhDB17ZFWPp1Rf8ICv_R8.INSTANCE, this.mTts));
        }

        @Override
        public void onInit(int n) {
            if (n != 0) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Tts init fail, status=");
                stringBuilder.append(Integer.toString(n));
                Slog.d(AccessibilityShortcutController.TAG, stringBuilder.toString());
                AccessibilityShortcutController.this.playNotificationTone();
                return;
            }
            AccessibilityShortcutController.this.mHandler.sendMessage(PooledLambda.obtainMessage(_$$Lambda$AccessibilityShortcutController$TtsPrompt$HwizF4cvqRFiaqAcMrC7W8y6zYA.INSTANCE, this));
        }
    }

}

