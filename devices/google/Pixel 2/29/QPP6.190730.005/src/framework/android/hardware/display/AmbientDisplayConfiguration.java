/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.display;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.os.SystemProperties;
import android.provider.Settings;
import android.text.TextUtils;

public class AmbientDisplayConfiguration {
    private final boolean mAlwaysOnByDefault;
    private final Context mContext;

    public AmbientDisplayConfiguration(Context context) {
        this.mContext = context;
        this.mAlwaysOnByDefault = this.mContext.getResources().getBoolean(17891418);
    }

    private boolean alwaysOnDisplayAvailable() {
        return this.mContext.getResources().getBoolean(17891417);
    }

    private boolean alwaysOnDisplayDebuggingEnabled() {
        boolean bl;
        boolean bl2 = bl = false;
        if (SystemProperties.getBoolean("debug.doze.aod", false)) {
            bl2 = bl;
            if (Build.IS_DEBUGGABLE) {
                bl2 = true;
            }
        }
        return bl2;
    }

    private boolean boolSetting(String string2, int n, int n2) {
        boolean bl = Settings.Secure.getIntForUser(this.mContext.getContentResolver(), string2, n2, n) != 0;
        return bl;
    }

    private boolean boolSettingDefaultOff(String string2, int n) {
        return this.boolSetting(string2, n, 0);
    }

    private boolean boolSettingDefaultOn(String string2, int n) {
        return this.boolSetting(string2, n, 1);
    }

    private boolean pulseOnLongPressAvailable() {
        return TextUtils.isEmpty(this.longPressSensorType()) ^ true;
    }

    public boolean accessibilityInversionEnabled(int n) {
        return this.boolSettingDefaultOff("accessibility_display_inversion_enabled", n);
    }

    public boolean alwaysOnAvailable() {
        boolean bl = (this.alwaysOnDisplayDebuggingEnabled() || this.alwaysOnDisplayAvailable()) && this.ambientDisplayAvailable();
        return bl;
    }

    public boolean alwaysOnAvailableForUser(int n) {
        boolean bl = this.alwaysOnAvailable() && !this.accessibilityInversionEnabled(n);
        return bl;
    }

    public boolean alwaysOnEnabled(int n) {
        boolean bl = this.boolSetting("doze_always_on", n, (int)this.mAlwaysOnByDefault) && this.alwaysOnAvailable() && !this.accessibilityInversionEnabled(n);
        return bl;
    }

    public boolean ambientDisplayAvailable() {
        return TextUtils.isEmpty(this.ambientDisplayComponent()) ^ true;
    }

    public String ambientDisplayComponent() {
        return this.mContext.getResources().getString(17039723);
    }

    public boolean doubleTapGestureEnabled(int n) {
        boolean bl = this.boolSettingDefaultOn("doze_pulse_on_double_tap", n) && this.doubleTapSensorAvailable();
        return bl;
    }

    public boolean doubleTapSensorAvailable() {
        return TextUtils.isEmpty(this.doubleTapSensorType()) ^ true;
    }

    public String doubleTapSensorType() {
        return this.mContext.getResources().getString(17039724);
    }

    public boolean dozePickupSensorAvailable() {
        return this.mContext.getResources().getBoolean(17891419);
    }

    public boolean enabled(int n) {
        boolean bl = this.pulseOnNotificationEnabled(n) || this.pulseOnLongPressEnabled(n) || this.alwaysOnEnabled(n) || this.wakeScreenGestureEnabled(n) || this.pickupGestureEnabled(n) || this.tapGestureEnabled(n) || this.doubleTapGestureEnabled(n);
        return bl;
    }

    public long getWakeLockScreenDebounce() {
        return this.mContext.getResources().getInteger(17694800);
    }

    public String longPressSensorType() {
        return this.mContext.getResources().getString(17039725);
    }

    public boolean pickupGestureEnabled(int n) {
        boolean bl = this.boolSettingDefaultOn("doze_pulse_on_pick_up", n) && this.dozePickupSensorAvailable();
        return bl;
    }

    public boolean pulseOnLongPressEnabled(int n) {
        boolean bl = this.pulseOnLongPressAvailable() && this.boolSettingDefaultOff("doze_pulse_on_long_press", n);
        return bl;
    }

    public boolean pulseOnNotificationAvailable() {
        return this.ambientDisplayAvailable();
    }

    public boolean pulseOnNotificationEnabled(int n) {
        boolean bl = this.boolSettingDefaultOn("doze_enabled", n) && this.pulseOnNotificationAvailable();
        return bl;
    }

    public boolean tapGestureEnabled(int n) {
        boolean bl = this.boolSettingDefaultOn("doze_tap_gesture", n) && this.tapSensorAvailable();
        return bl;
    }

    public boolean tapSensorAvailable() {
        return TextUtils.isEmpty(this.tapSensorType()) ^ true;
    }

    public String tapSensorType() {
        return this.mContext.getResources().getString(17039726);
    }

    public boolean wakeScreenGestureAvailable() {
        return this.mContext.getResources().getBoolean(17891421);
    }

    public boolean wakeScreenGestureEnabled(int n) {
        boolean bl = this.boolSettingDefaultOn("doze_wake_screen_gesture", n) && this.wakeScreenGestureAvailable();
        return bl;
    }
}

