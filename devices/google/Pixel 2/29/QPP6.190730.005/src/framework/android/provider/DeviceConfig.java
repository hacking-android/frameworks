/*
 * Decompiled with CFR 0.145.
 */
package android.provider;

import android.annotation.SystemApi;
import android.app.ActivityThread;
import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Pair;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.util.Preconditions;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;

@SystemApi
public final class DeviceConfig {
    public static final Uri CONTENT_URI = Uri.parse("content://settings/config");
    @SystemApi
    public static final String NAMESPACE_ACTIVITY_MANAGER = "activity_manager";
    @SystemApi
    public static final String NAMESPACE_ACTIVITY_MANAGER_NATIVE_BOOT = "activity_manager_native_boot";
    @SystemApi
    public static final String NAMESPACE_APP_COMPAT = "app_compat";
    @SystemApi
    public static final String NAMESPACE_ATTENTION_MANAGER_SERVICE = "attention_manager_service";
    @SystemApi
    public static final String NAMESPACE_AUTOFILL = "autofill";
    @SystemApi
    public static final String NAMESPACE_CONNECTIVITY = "connectivity";
    public static final String NAMESPACE_CONTACTS_PROVIDER = "contacts_provider";
    @SystemApi
    public static final String NAMESPACE_CONTENT_CAPTURE = "content_capture";
    @SystemApi
    public static final String NAMESPACE_DEX_BOOT = "dex_boot";
    @SystemApi
    public static final String NAMESPACE_GAME_DRIVER = "game_driver";
    @SystemApi
    public static final String NAMESPACE_INPUT_NATIVE_BOOT = "input_native_boot";
    @SystemApi
    public static final String NAMESPACE_INTELLIGENCE_ATTENTION = "intelligence_attention";
    public static final String NAMESPACE_INTELLIGENCE_CONTENT_SUGGESTIONS = "intelligence_content_suggestions";
    @SystemApi
    public static final String NAMESPACE_MEDIA_NATIVE = "media_native";
    @SystemApi
    public static final String NAMESPACE_NETD_NATIVE = "netd_native";
    @SystemApi
    public static final String NAMESPACE_PRIVACY = "privacy";
    @SystemApi
    public static final String NAMESPACE_ROLLBACK = "rollback";
    @SystemApi
    public static final String NAMESPACE_ROLLBACK_BOOT = "rollback_boot";
    @SystemApi
    public static final String NAMESPACE_RUNTIME = "runtime";
    @SystemApi
    public static final String NAMESPACE_RUNTIME_NATIVE = "runtime_native";
    @SystemApi
    public static final String NAMESPACE_RUNTIME_NATIVE_BOOT = "runtime_native_boot";
    @SystemApi
    public static final String NAMESPACE_SCHEDULER = "scheduler";
    public static final String NAMESPACE_SETTINGS_UI = "settings_ui";
    @SystemApi
    public static final String NAMESPACE_STORAGE = "storage";
    @SystemApi
    public static final String NAMESPACE_SYSTEMUI = "systemui";
    @SystemApi
    public static final String NAMESPACE_TELEPHONY = "telephony";
    @SystemApi
    public static final String NAMESPACE_TEXTCLASSIFIER = "textclassifier";
    public static final String NAMESPACE_WINDOW_MANAGER = "android:window_manager";
    private static final List<String> PUBLIC_NAMESPACES = Arrays.asList("textclassifier", "runtime");
    private static final String TAG = "DeviceConfig";
    @GuardedBy(value={"sLock"})
    private static ArrayMap<OnPropertiesChangedListener, Pair<String, Executor>> sListeners;
    private static final Object sLock;
    @GuardedBy(value={"sLock"})
    private static Map<String, Pair<ContentObserver, Integer>> sNamespaces;
    @GuardedBy(value={"sLock"})
    private static ArrayMap<OnPropertyChangedListener, Pair<String, Executor>> sSingleListeners;

    static {
        sLock = new Object();
        sSingleListeners = new ArrayMap();
        sListeners = new ArrayMap();
        sNamespaces = new HashMap<String, Pair<ContentObserver, Integer>>();
    }

    private DeviceConfig() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @SystemApi
    public static void addOnPropertiesChangedListener(String string2, Executor executor, OnPropertiesChangedListener onPropertiesChangedListener) {
        DeviceConfig.enforceReadPermission(ActivityThread.currentApplication().getApplicationContext(), string2);
        Object object = sLock;
        synchronized (object) {
            Object object2 = sListeners.get(onPropertiesChangedListener);
            if (object2 == null) {
                ArrayMap<OnPropertiesChangedListener, Pair<String, Executor>> arrayMap = sListeners;
                object2 = new Pair(string2, executor);
                arrayMap.put(onPropertiesChangedListener, (Pair<String, Executor>)object2);
                DeviceConfig.incrementNamespace(string2);
            } else if (string2.equals(((Pair)object2).first)) {
                ArrayMap<OnPropertiesChangedListener, Pair<String, Executor>> arrayMap = sListeners;
                object2 = new Pair(string2, executor);
                arrayMap.put(onPropertiesChangedListener, (Pair<String, Executor>)object2);
            } else {
                DeviceConfig.decrementNamespace((String)DeviceConfig.sListeners.get((Object)onPropertiesChangedListener).first);
                object2 = sListeners;
                Pair<String, Executor> pair = new Pair<String, Executor>(string2, executor);
                ((ArrayMap)object2).put(onPropertiesChangedListener, pair);
                DeviceConfig.incrementNamespace(string2);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @SystemApi
    public static void addOnPropertyChangedListener(String string2, Executor executor, OnPropertyChangedListener onPropertyChangedListener) {
        DeviceConfig.enforceReadPermission(ActivityThread.currentApplication().getApplicationContext(), string2);
        Object object = sLock;
        synchronized (object) {
            Pair<String, Executor> pair = sSingleListeners.get(onPropertyChangedListener);
            if (pair == null) {
                pair = sSingleListeners;
                Pair<String, Executor> pair2 = new Pair<String, Executor>(string2, executor);
                ((ArrayMap)((Object)pair)).put((String)((Object)onPropertyChangedListener), (Executor)((Object)pair2));
                DeviceConfig.incrementNamespace(string2);
            } else if (string2.equals(pair.first)) {
                pair = sSingleListeners;
                Pair<String, Executor> pair3 = new Pair<String, Executor>(string2, executor);
                ((ArrayMap)((Object)pair)).put((String)((Object)onPropertyChangedListener), (Executor)((Object)pair3));
            } else {
                DeviceConfig.decrementNamespace((String)DeviceConfig.sSingleListeners.get((Object)onPropertyChangedListener).first);
                ArrayMap<OnPropertyChangedListener, Pair<String, Executor>> arrayMap = sSingleListeners;
                pair = new Pair<String, Executor>(string2, executor);
                arrayMap.put(onPropertyChangedListener, pair);
                DeviceConfig.incrementNamespace(string2);
            }
            return;
        }
    }

    private static String createCompositeName(String string2, String string3) {
        Preconditions.checkNotNull(string2);
        Preconditions.checkNotNull(string3);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append("/");
        stringBuilder.append(string3);
        return stringBuilder.toString();
    }

    private static Uri createNamespaceUri(String string2) {
        Preconditions.checkNotNull(string2);
        return CONTENT_URI.buildUpon().appendPath(string2).build();
    }

    @GuardedBy(value={"sLock"})
    private static void decrementNamespace(String string2) {
        Preconditions.checkNotNull(string2);
        Pair<ContentObserver, Integer> pair = sNamespaces.get(string2);
        if (pair == null) {
            return;
        }
        if ((Integer)pair.second > 1) {
            sNamespaces.put(string2, new Pair<ContentObserver, Integer>((ContentObserver)pair.first, (Integer)pair.second - 1));
        } else {
            ActivityThread.currentApplication().getContentResolver().unregisterContentObserver((ContentObserver)pair.first);
            sNamespaces.remove(string2);
        }
    }

    public static void enforceReadPermission(Context context, String string2) {
        if (context.checkCallingOrSelfPermission("android.permission.READ_DEVICE_CONFIG") != 0 && !PUBLIC_NAMESPACES.contains(string2)) {
            throw new SecurityException("Permission denial: reading from settings requires:android.permission.READ_DEVICE_CONFIG");
        }
    }

    @SystemApi
    public static boolean getBoolean(String string2, String string3, boolean bl) {
        block0 : {
            if ((string2 = DeviceConfig.getProperty(string2, string3)) == null) break block0;
            bl = Boolean.parseBoolean(string2);
        }
        return bl;
    }

    @SystemApi
    public static float getFloat(String string2, String string3, float f) {
        String string4 = DeviceConfig.getProperty(string2, string3);
        if (string4 == null) {
            return f;
        }
        try {
            float f2 = Float.parseFloat(string4);
            return f2;
        }
        catch (NumberFormatException numberFormatException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Parsing float failed for ");
            stringBuilder.append(string2);
            stringBuilder.append(":");
            stringBuilder.append(string3);
            Log.e(TAG, stringBuilder.toString());
            return f;
        }
    }

    @SystemApi
    public static int getInt(String string2, String string3, int n) {
        String string4 = DeviceConfig.getProperty(string2, string3);
        if (string4 == null) {
            return n;
        }
        try {
            int n2 = Integer.parseInt(string4);
            return n2;
        }
        catch (NumberFormatException numberFormatException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Parsing integer failed for ");
            stringBuilder.append(string2);
            stringBuilder.append(":");
            stringBuilder.append(string3);
            Log.e(TAG, stringBuilder.toString());
            return n;
        }
    }

    @SystemApi
    public static long getLong(String string2, String string3, long l) {
        String string4 = DeviceConfig.getProperty(string2, string3);
        if (string4 == null) {
            return l;
        }
        try {
            long l2 = Long.parseLong(string4);
            return l2;
        }
        catch (NumberFormatException numberFormatException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Parsing long failed for ");
            stringBuilder.append(string2);
            stringBuilder.append(":");
            stringBuilder.append(string3);
            Log.e(TAG, stringBuilder.toString());
            return l;
        }
    }

    @SystemApi
    public static String getProperty(String string2, String string3) {
        return Settings.Config.getString(ActivityThread.currentApplication().getContentResolver(), DeviceConfig.createCompositeName(string2, string3));
    }

    @SystemApi
    public static String getString(String string2, String string3, String string4) {
        if ((string2 = DeviceConfig.getProperty(string2, string3)) == null) {
            string2 = string4;
        }
        return string2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static void handleChange(Uri object) {
        String string2;
        Preconditions.checkNotNull(object);
        object = ((Uri)object).getPathSegments();
        final String string3 = (String)object.get(1);
        final String string4 = (String)object.get(2);
        try {
            string2 = DeviceConfig.getProperty(string3, string4);
            object = sLock;
        }
        catch (SecurityException securityException) {
            Log.e(TAG, "OnPropertyChangedListener update failed: permission violation.");
            return;
        }
        synchronized (object) {
            int n;
            Runnable runnable;
            Runnable runnable2;
            int n2 = 0;
            for (n = 0; n < sListeners.size(); ++n) {
                if (!string3.equals(DeviceConfig.sListeners.valueAt((int)n).first)) continue;
                runnable = (Executor)DeviceConfig.sListeners.valueAt((int)n).second;
                runnable2 = new Runnable(){

                    @Override
                    public void run() {
                        HashMap<String, String> hashMap = new HashMap<String, String>(1);
                        hashMap.put(String.this, string2);
                        ((OnPropertiesChangedListener)sListeners.keyAt(n)).onPropertiesChanged(new Properties(string3, hashMap));
                    }
                };
                runnable.execute(runnable2);
            }
            n = n2;
            do {
                if (n >= sSingleListeners.size()) {
                    return;
                }
                if (string3.equals(DeviceConfig.sSingleListeners.valueAt((int)n).first)) {
                    runnable2 = (Executor)DeviceConfig.sSingleListeners.valueAt((int)n).second;
                    runnable = new Runnable(){

                        @Override
                        public void run() {
                            ((OnPropertyChangedListener)sSingleListeners.keyAt(val$j)).onPropertyChanged(string3, string4, string2);
                        }
                    };
                    runnable2.execute(runnable);
                }
                ++n;
            } while (true);
        }
    }

    @GuardedBy(value={"sLock"})
    private static void incrementNamespace(String string2) {
        Preconditions.checkNotNull(string2);
        Object object = sNamespaces.get(string2);
        if (object != null) {
            sNamespaces.put(string2, new Pair<ContentObserver, Integer>((ContentObserver)((Pair)object).first, (Integer)((Pair)object).second + 1));
        } else {
            object = new ContentObserver(null){

                @Override
                public void onChange(boolean bl, Uri uri) {
                    if (uri != null) {
                        DeviceConfig.handleChange(uri);
                    }
                }
            };
            ActivityThread.currentApplication().getContentResolver().registerContentObserver(DeviceConfig.createNamespaceUri(string2), true, (ContentObserver)object);
            sNamespaces.put(string2, new Pair<Object, Integer>(object, 1));
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @SystemApi
    public static void removeOnPropertiesChangedListener(OnPropertiesChangedListener onPropertiesChangedListener) {
        Preconditions.checkNotNull(onPropertiesChangedListener);
        Object object = sLock;
        synchronized (object) {
            if (sListeners.containsKey(onPropertiesChangedListener)) {
                DeviceConfig.decrementNamespace((String)DeviceConfig.sListeners.get((Object)onPropertiesChangedListener).first);
                sListeners.remove(onPropertiesChangedListener);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @SystemApi
    public static void removeOnPropertyChangedListener(OnPropertyChangedListener onPropertyChangedListener) {
        Preconditions.checkNotNull(onPropertyChangedListener);
        Object object = sLock;
        synchronized (object) {
            if (sSingleListeners.containsKey(onPropertyChangedListener)) {
                DeviceConfig.decrementNamespace((String)DeviceConfig.sSingleListeners.get((Object)onPropertyChangedListener).first);
                sSingleListeners.remove(onPropertyChangedListener);
            }
            return;
        }
    }

    @SystemApi
    public static void resetToDefaults(int n, String string2) {
        Settings.Config.resetToDefaults(ActivityThread.currentApplication().getContentResolver(), n, string2);
    }

    @SystemApi
    public static boolean setProperty(String string2, String string3, String string4, boolean bl) {
        string2 = DeviceConfig.createCompositeName(string2, string3);
        return Settings.Config.putString(ActivityThread.currentApplication().getContentResolver(), string2, string4, bl);
    }

    @SystemApi
    public static interface OnPropertiesChangedListener {
        public void onPropertiesChanged(Properties var1);
    }

    @SystemApi
    public static interface OnPropertyChangedListener {
        public void onPropertyChanged(String var1, String var2, String var3);
    }

    @SystemApi
    public static class Properties {
        private final HashMap<String, String> mMap;
        private final String mNamespace;

        Properties(String string2, Map<String, String> map) {
            Preconditions.checkNotNull(string2);
            this.mNamespace = string2;
            this.mMap = new HashMap();
            if (map != null) {
                this.mMap.putAll(map);
            }
        }

        public boolean getBoolean(String string2, boolean bl) {
            block0 : {
                Preconditions.checkNotNull(string2);
                string2 = this.mMap.get(string2);
                if (string2 == null) break block0;
                bl = Boolean.parseBoolean(string2);
            }
            return bl;
        }

        public float getFloat(String string2, float f) {
            Preconditions.checkNotNull(string2);
            String string3 = this.mMap.get(string2);
            if (string3 == null) {
                return f;
            }
            try {
                float f2 = Float.parseFloat(string3);
                return f2;
            }
            catch (NumberFormatException numberFormatException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Parsing float failed for ");
                stringBuilder.append(string2);
                Log.e(DeviceConfig.TAG, stringBuilder.toString());
                return f;
            }
        }

        public int getInt(String string2, int n) {
            Preconditions.checkNotNull(string2);
            String string3 = this.mMap.get(string2);
            if (string3 == null) {
                return n;
            }
            try {
                int n2 = Integer.parseInt(string3);
                return n2;
            }
            catch (NumberFormatException numberFormatException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Parsing int failed for ");
                stringBuilder.append(string2);
                Log.e(DeviceConfig.TAG, stringBuilder.toString());
                return n;
            }
        }

        public Set<String> getKeyset() {
            return this.mMap.keySet();
        }

        public long getLong(String string2, long l) {
            Preconditions.checkNotNull(string2);
            String string3 = this.mMap.get(string2);
            if (string3 == null) {
                return l;
            }
            try {
                long l2 = Long.parseLong(string3);
                return l2;
            }
            catch (NumberFormatException numberFormatException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Parsing long failed for ");
                stringBuilder.append(string2);
                Log.e(DeviceConfig.TAG, stringBuilder.toString());
                return l;
            }
        }

        public String getNamespace() {
            return this.mNamespace;
        }

        public String getString(String string2, String string3) {
            Preconditions.checkNotNull(string2);
            string2 = this.mMap.get(string2);
            if (string2 == null) {
                string2 = string3;
            }
            return string2;
        }
    }

    public static interface WindowManager {
        public static final String KEY_SYSTEM_GESTURES_EXCLUDED_BY_PRE_Q_STICKY_IMMERSIVE = "system_gestures_excluded_by_pre_q_sticky_immersive";
        public static final String KEY_SYSTEM_GESTURE_EXCLUSION_LIMIT_DP = "system_gesture_exclusion_limit_dp";
    }

}

