/*
 * Decompiled with CFR 0.145.
 */
package android.view.textclassifier;

import android.provider.DeviceConfig;
import android.util.ArrayMap;
import android.util.KeyValueListParser;
import android.view.textclassifier.Log;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.Preconditions;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
public final class ConfigParser {
    static final boolean ENABLE_DEVICE_CONFIG = true;
    private static final String STRING_LIST_DELIMITER = ":";
    private static final String TAG = "ConfigParser";
    @GuardedBy(value={"mLock"})
    private final Map<String, Object> mCache = new ArrayMap<String, Object>();
    private final Supplier<String> mLegacySettingsSupplier;
    private final Object mLock = new Object();
    @GuardedBy(value={"mLock"})
    private KeyValueListParser mSettingsParser;

    public ConfigParser(Supplier<String> supplier) {
        this.mLegacySettingsSupplier = Preconditions.checkNotNull(supplier);
    }

    private static float[] getDeviceConfigFloatArray(String string2, float[] arrf) {
        return ConfigParser.parse(DeviceConfig.getString("textclassifier", string2, null), arrf);
    }

    private static List<String> getDeviceConfigStringList(String string2, List<String> list) {
        return ConfigParser.parse(DeviceConfig.getString("textclassifier", string2, null), list);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private KeyValueListParser getLegacySettings() {
        Object object = this.mLock;
        synchronized (object) {
            if (this.mSettingsParser != null) return this.mSettingsParser;
            String string2 = this.mLegacySettingsSupplier.get();
            try {
                KeyValueListParser keyValueListParser;
                this.mSettingsParser = keyValueListParser = new KeyValueListParser(',');
                this.mSettingsParser.setString(string2);
                return this.mSettingsParser;
            }
            catch (IllegalArgumentException illegalArgumentException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Bad text_classifier_constants: ");
                stringBuilder.append(string2);
                Log.w(TAG, stringBuilder.toString());
            }
            return this.mSettingsParser;
        }
    }

    private float[] getSettingsFloatArray(String string2, float[] arrf) {
        return ConfigParser.parse(this.mSettingsParser.getString(string2, null), arrf);
    }

    private List<String> getSettingsStringList(String string2, List<String> list) {
        return ConfigParser.parse(this.mSettingsParser.getString(string2, null), list);
    }

    private static List<String> parse(String string2, List<String> list) {
        if (string2 != null) {
            return Collections.unmodifiableList(Arrays.asList(string2.split(STRING_LIST_DELIMITER)));
        }
        return list;
    }

    private static float[] parse(String arrf, float[] arrf2) {
        if (arrf != null) {
            String[] arrstring = arrf.split(STRING_LIST_DELIMITER);
            if (arrstring.length != arrf2.length) {
                return arrf2;
            }
            arrf = new float[arrstring.length];
            for (int i = 0; i < arrstring.length; ++i) {
                try {
                    arrf[i] = Float.parseFloat(arrstring[i]);
                    continue;
                }
                catch (NumberFormatException numberFormatException) {
                    return arrf2;
                }
            }
            return arrf;
        }
        return arrf2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean getBoolean(String string2, boolean bl) {
        Object object = this.mLock;
        synchronized (object) {
            Object object2 = this.mCache.get(string2);
            if (object2 instanceof Boolean) {
                return (Boolean)object2;
            }
            bl = DeviceConfig.getBoolean("textclassifier", string2, this.getLegacySettings().getBoolean(string2, bl));
            this.mCache.put(string2, bl);
            return bl;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public float getFloat(String string2, float f) {
        Object object = this.mLock;
        synchronized (object) {
            Object object2 = this.mCache.get(string2);
            if (object2 instanceof Float) {
                return ((Float)object2).floatValue();
            }
            f = DeviceConfig.getFloat("textclassifier", string2, this.getLegacySettings().getFloat(string2, f));
            this.mCache.put(string2, Float.valueOf(f));
            return f;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public float[] getFloatArray(String arrf, float[] arrf2) {
        Object object = this.mLock;
        synchronized (object) {
            Object object2 = this.mCache.get(arrf);
            if (object2 instanceof float[]) {
                return (float[])object2;
            }
            arrf2 = ConfigParser.getDeviceConfigFloatArray((String)arrf, this.getSettingsFloatArray((String)arrf, arrf2));
            this.mCache.put((String)arrf, arrf2);
            return arrf2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int getInt(String string2, int n) {
        Object object = this.mLock;
        synchronized (object) {
            Object object2 = this.mCache.get(string2);
            if (object2 instanceof Integer) {
                return (Integer)object2;
            }
            n = DeviceConfig.getInt("textclassifier", string2, this.getLegacySettings().getInt(string2, n));
            this.mCache.put(string2, n);
            return n;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public String getString(String string2, String string3) {
        Object object = this.mLock;
        synchronized (object) {
            Object object2 = this.mCache.get(string2);
            if (object2 instanceof String) {
                return (String)object2;
            }
            string3 = DeviceConfig.getString("textclassifier", string2, this.getLegacySettings().getString(string2, string3));
            this.mCache.put(string2, string3);
            return string3;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public List<String> getStringList(String object, List<String> list) {
        Object object2 = this.mLock;
        synchronized (object2) {
            Object object3 = this.mCache.get(object);
            if (object3 instanceof List) {
                List list2 = (List)object3;
                if (list2.isEmpty()) {
                    return Collections.emptyList();
                }
                if (list2.get(0) instanceof String) {
                    return (List)object3;
                }
            }
            list = ConfigParser.getDeviceConfigStringList((String)object, this.getSettingsStringList((String)object, list));
            this.mCache.put((String)object, list);
            return list;
        }
    }
}

