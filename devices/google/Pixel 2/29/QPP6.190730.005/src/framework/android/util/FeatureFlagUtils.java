/*
 * Decompiled with CFR 0.145.
 */
package android.util;

import android.content.ContentResolver;
import android.content.Context;
import android.os.SystemProperties;
import android.provider.Settings;
import android.text.TextUtils;
import java.util.HashMap;
import java.util.Map;

public class FeatureFlagUtils {
    private static final Map<String, String> DEFAULT_FLAGS = new HashMap<String, String>();
    public static final String DYNAMIC_SYSTEM = "settings_dynamic_system";
    public static final String FFLAG_OVERRIDE_PREFIX = "sys.fflag.override.";
    public static final String FFLAG_PREFIX = "sys.fflag.";
    public static final String HEARING_AID_SETTINGS = "settings_bluetooth_hearing_aid";
    public static final String PERSIST_PREFIX = "persist.sys.fflag.override.";
    public static final String PIXEL_WALLPAPER_CATEGORY_SWITCH = "settings_pixel_wallpaper_category_switch";
    public static final String SCREENRECORD_LONG_PRESS = "settings_screenrecord_long_press";
    public static final String SEAMLESS_TRANSFER = "settings_seamless_transfer";

    static {
        DEFAULT_FLAGS.put("settings_audio_switcher", "true");
        DEFAULT_FLAGS.put("settings_mobile_network_v2", "true");
        DEFAULT_FLAGS.put("settings_network_and_internet_v2", "true");
        DEFAULT_FLAGS.put("settings_systemui_theme", "true");
        DEFAULT_FLAGS.put(DYNAMIC_SYSTEM, "false");
        DEFAULT_FLAGS.put(SEAMLESS_TRANSFER, "false");
        DEFAULT_FLAGS.put(HEARING_AID_SETTINGS, "false");
        DEFAULT_FLAGS.put(SCREENRECORD_LONG_PRESS, "false");
        DEFAULT_FLAGS.put(PIXEL_WALLPAPER_CATEGORY_SWITCH, "false");
        DEFAULT_FLAGS.put("settings_wifi_details_datausage_header", "false");
    }

    public static Map<String, String> getAllFeatureFlags() {
        return DEFAULT_FLAGS;
    }

    public static boolean isEnabled(Context object, String string2) {
        if (object != null && !TextUtils.isEmpty((CharSequence)(object = Settings.Global.getString(((Context)object).getContentResolver(), string2)))) {
            return Boolean.parseBoolean((String)object);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(FFLAG_OVERRIDE_PREFIX);
        ((StringBuilder)object).append(string2);
        object = SystemProperties.get(((StringBuilder)object).toString());
        if (!TextUtils.isEmpty((CharSequence)object)) {
            return Boolean.parseBoolean((String)object);
        }
        return Boolean.parseBoolean(FeatureFlagUtils.getAllFeatureFlags().get(string2));
    }

    public static void setEnabled(Context object, String string2, boolean bl) {
        object = new StringBuilder();
        ((StringBuilder)object).append(FFLAG_OVERRIDE_PREFIX);
        ((StringBuilder)object).append(string2);
        string2 = ((StringBuilder)object).toString();
        object = bl ? "true" : "false";
        SystemProperties.set(string2, (String)object);
    }
}

