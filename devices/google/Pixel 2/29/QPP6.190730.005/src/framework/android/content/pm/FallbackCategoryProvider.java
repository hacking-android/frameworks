/*
 * Decompiled with CFR 0.145.
 */
package android.content.pm;

import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.SystemProperties;
import android.util.ArrayMap;
import android.util.DisplayMetrics;
import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class FallbackCategoryProvider {
    private static final String TAG = "FallbackCategoryProvider";
    private static final ArrayMap<String, Integer> sFallbacks = new ArrayMap();

    public static int getFallbackCategory(String string2) {
        return sFallbacks.getOrDefault(string2, -1);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static void loadFallbacks() {
        sFallbacks.clear();
        if (SystemProperties.getBoolean("fw.ignore_fb_categories", false)) {
            Log.d(TAG, "Ignoring fallback categories");
            return;
        }
        AutoCloseable autoCloseable = new AssetManager();
        ((AssetManager)autoCloseable).addAssetPath("/system/framework/framework-res.apk");
        String[] arrstring = new Resources((AssetManager)autoCloseable, null, null);
        InputStreamReader inputStreamReader = new InputStreamReader(arrstring.openRawResource(17825796));
        autoCloseable = new BufferedReader(inputStreamReader);
        while ((arrstring = ((BufferedReader)autoCloseable).readLine()) != null) {
            if (arrstring.charAt(0) == '#' || (arrstring = arrstring.split(",")).length != 2) continue;
            sFallbacks.put(arrstring[0], Integer.parseInt(arrstring[1]));
        }
        arrstring = new StringBuilder();
        arrstring.append("Found ");
        arrstring.append(sFallbacks.size());
        arrstring.append(" fallback categories");
        Log.d(TAG, arrstring.toString());
        ((BufferedReader)autoCloseable).close();
        return;
        catch (Throwable throwable) {
            try {
                throw throwable;
            }
            catch (Throwable throwable2) {
                try {
                    ((BufferedReader)autoCloseable).close();
                    throw throwable2;
                }
                catch (Throwable throwable3) {
                    try {
                        throwable.addSuppressed(throwable3);
                        throw throwable2;
                    }
                    catch (IOException | NumberFormatException exception) {
                        Log.w(TAG, "Failed to read fallback categories", exception);
                    }
                }
            }
        }
    }
}

