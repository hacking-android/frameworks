/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.system.VMRuntime
 */
package android.os;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.BaseBundle;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemProperties;
import android.os.Trace;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;
import dalvik.system.VMRuntime;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class GraphicsEnvironment {
    private static final String ACTION_ANGLE_FOR_ANDROID = "android.app.action.ANGLE_FOR_ANDROID";
    private static final String ACTION_ANGLE_FOR_ANDROID_TOAST_MESSAGE = "android.app.action.ANGLE_FOR_ANDROID_TOAST_MESSAGE";
    private static final String ANGLE_RULES_FILE = "a4a_rules.json";
    private static final String ANGLE_TEMP_RULES = "debug.angle.rules";
    private static final boolean DEBUG = false;
    private static final int GAME_DRIVER_GLOBAL_OPT_IN_DEFAULT = 0;
    private static final int GAME_DRIVER_GLOBAL_OPT_IN_GAME_DRIVER = 1;
    private static final int GAME_DRIVER_GLOBAL_OPT_IN_OFF = 3;
    private static final int GAME_DRIVER_GLOBAL_OPT_IN_PRERELEASE_DRIVER = 2;
    private static final String GAME_DRIVER_SPHAL_LIBRARIES_FILENAME = "sphal_libraries.txt";
    private static final String GAME_DRIVER_WHITELIST_ALL = "*";
    private static final String INTENT_KEY_A4A_TOAST_MESSAGE = "A4A Toast Message";
    private static final String METADATA_DRIVER_BUILD_TIME = "com.android.gamedriver.build_time";
    private static final String PROPERTY_GFX_DRIVER = "ro.gfx.driver.0";
    private static final String PROPERTY_GFX_DRIVER_BUILD_TIME = "ro.gfx.driver_build_time";
    private static final String PROPERTY_GFX_DRIVER_PRERELEASE = "ro.gfx.driver.1";
    private static final String SYSTEM_DRIVER_NAME = "system";
    private static final long SYSTEM_DRIVER_VERSION_CODE = 0L;
    private static final String SYSTEM_DRIVER_VERSION_NAME = "";
    private static final String TAG = "GraphicsEnvironment";
    private static final int VULKAN_1_0 = 4194304;
    private static final int VULKAN_1_1 = 4198400;
    private static final Map<OpenGlDriverChoice, String> sDriverMap;
    private static final GraphicsEnvironment sInstance;
    private ClassLoader mClassLoader;
    private String mDebugLayerPath;
    private String mLayerPath;

    static {
        sInstance = new GraphicsEnvironment();
        sDriverMap = GraphicsEnvironment.buildMap();
    }

    private static Map<OpenGlDriverChoice, String> buildMap() {
        HashMap<OpenGlDriverChoice, String> hashMap = new HashMap<OpenGlDriverChoice, String>();
        hashMap.put(OpenGlDriverChoice.DEFAULT, "default");
        hashMap.put(OpenGlDriverChoice.ANGLE, "angle");
        hashMap.put(OpenGlDriverChoice.NATIVE, "native");
        return hashMap;
    }

    private static boolean checkAngleWhitelist(Context object, Bundle bundle, String string2) {
        object = ((Context)object).getContentResolver();
        return GraphicsEnvironment.getGlobalSettingsString((ContentResolver)object, bundle, "angle_whitelist").contains(string2);
    }

    private static String chooseAbi(ApplicationInfo applicationInfo) {
        String string2 = VMRuntime.getCurrentInstructionSet();
        if (applicationInfo.primaryCpuAbi != null && string2.equals(VMRuntime.getInstructionSet((String)applicationInfo.primaryCpuAbi))) {
            return applicationInfo.primaryCpuAbi;
        }
        if (applicationInfo.secondaryCpuAbi != null && string2.equals(VMRuntime.getInstructionSet((String)applicationInfo.secondaryCpuAbi))) {
            return applicationInfo.secondaryCpuAbi;
        }
        return null;
    }

    private static boolean chooseDriver(Context object, Bundle object2, PackageManager object3, String string2) {
        PackageInfo packageInfo;
        block6 : {
            if ((object2 = GraphicsEnvironment.chooseDriverInternal((Context)object, (Bundle)object2)) == null) {
                return false;
            }
            try {
                packageInfo = ((PackageManager)object3).getPackageInfo((String)object2, 1048704);
                object3 = packageInfo.applicationInfo;
                if (((ApplicationInfo)object3).targetSdkVersion >= 26) break block6;
                return false;
            }
            catch (PackageManager.NameNotFoundException nameNotFoundException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("driver package '");
                stringBuilder.append((String)object2);
                stringBuilder.append("' not installed");
                Log.w(TAG, stringBuilder.toString());
                return false;
            }
        }
        String string3 = GraphicsEnvironment.chooseAbi((ApplicationInfo)object3);
        if (string3 == null) {
            return false;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(((ApplicationInfo)object3).nativeLibraryDir);
        stringBuilder.append(File.pathSeparator);
        stringBuilder.append(((ApplicationInfo)object3).sourceDir);
        stringBuilder.append("!/lib/");
        stringBuilder.append(string3);
        GraphicsEnvironment.setDriverPathAndSphalLibraries(stringBuilder.toString(), GraphicsEnvironment.getSphalLibraries((Context)object, (String)object2));
        if (((ApplicationInfo)object3).metaData != null) {
            object = ((ApplicationInfo)object3).metaData.getString(METADATA_DRIVER_BUILD_TIME);
            if (object != null && !((String)object).isEmpty()) {
                GraphicsEnvironment.setGpuStats((String)object2, packageInfo.versionName, ((ApplicationInfo)object3).longVersionCode, Long.parseLong(((String)object).substring(1)), string2, 0);
                return true;
            }
            throw new IllegalArgumentException("com.android.gamedriver.build_time is not set");
        }
        throw new NullPointerException("apk's meta-data cannot be null");
    }

    private static String chooseDriverInternal(Context object, Bundle bundle) {
        String string2 = SystemProperties.get(PROPERTY_GFX_DRIVER);
        boolean bl = string2 != null && !string2.isEmpty();
        Object object2 = SystemProperties.get(PROPERTY_GFX_DRIVER_PRERELEASE);
        boolean bl2 = object2 != null && !((String)object2).isEmpty();
        Object var6_6 = null;
        Object var7_7 = null;
        Object var8_8 = null;
        if (!bl && !bl2) {
            return null;
        }
        if (!(((ApplicationInfo)(object = ((Context)object).getApplicationInfo())).isPrivilegedApp() || ((ApplicationInfo)object).isSystemApp() && !((ApplicationInfo)object).isUpdatedSystemApp())) {
            int n = bundle.getInt("game_driver_all_apps", 0);
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        object = ((ApplicationInfo)object).packageName;
                        if (GraphicsEnvironment.getGlobalSettingsString(null, bundle, "game_driver_opt_out_apps").contains(object)) {
                            return null;
                        }
                        if (GraphicsEnvironment.getGlobalSettingsString(null, bundle, "game_driver_prerelease_opt_in_apps").contains(object)) {
                            object = var8_8;
                            if (bl2) {
                                object = object2;
                            }
                            return object;
                        }
                        if (!bl) {
                            return null;
                        }
                        boolean bl3 = GraphicsEnvironment.getGlobalSettingsString(null, bundle, "game_driver_opt_in_apps").contains(object);
                        object2 = GraphicsEnvironment.getGlobalSettingsString(null, bundle, "game_driver_whitelist");
                        if (!bl3 && object2.indexOf(GAME_DRIVER_WHITELIST_ALL) != 0 && !object2.contains(object)) {
                            return null;
                        }
                        if (!bl3 && GraphicsEnvironment.getGlobalSettingsString(null, bundle, "game_driver_blacklist").contains(object)) {
                            return null;
                        }
                        return string2;
                    }
                    return null;
                }
                object = var6_6;
                if (bl2) {
                    object = object2;
                }
                return object;
            }
            object = var7_7;
            if (bl) {
                object = string2;
            }
            return object;
        }
        return null;
    }

    private String getAngleDebugPackage(Context object, Bundle bundle) {
        boolean bl = GraphicsEnvironment.isDebuggable((Context)object);
        int n = GraphicsEnvironment.getCanLoadSystemLibraries();
        boolean bl2 = true;
        if (n != 1) {
            bl2 = false;
        }
        if ((bl || bl2) && (object = bundle != null ? bundle.getString("angle_debug_package") : Settings.Global.getString(((Context)object).getContentResolver(), "angle_debug_package")) != null && !((String)object).isEmpty()) {
            return object;
        }
        return SYSTEM_DRIVER_VERSION_NAME;
    }

    private String getAnglePackageName(PackageManager object) {
        Object object2 = new Intent(ACTION_ANGLE_FOR_ANDROID);
        if ((object2 = ((PackageManager)object).queryIntentActivities((Intent)object2, 1048576)).size() != 1) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Invalid number of ANGLE packages. Required: 1, Found: ");
            ((StringBuilder)object).append(object2.size());
            Log.e(TAG, ((StringBuilder)object).toString());
            Iterator iterator = object2.iterator();
            while (iterator.hasNext()) {
                object = (ResolveInfo)iterator.next();
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Found ANGLE package: ");
                ((StringBuilder)object2).append(object.activityInfo.packageName);
                Log.e(TAG, ((StringBuilder)object2).toString());
            }
            return SYSTEM_DRIVER_VERSION_NAME;
        }
        return ((ResolveInfo)object2.get((int)0)).activityInfo.packageName;
    }

    private static native int getCanLoadSystemLibraries();

    private static String getDebugLayerAppPaths(PackageManager object, String charSequence) {
        try {
            object = ((PackageManager)object).getApplicationInfo((String)charSequence, 131072);
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Debug layer app '");
            stringBuilder.append((String)charSequence);
            stringBuilder.append("' not installed");
            Log.w("GraphicsEnvironment", stringBuilder.toString());
            return null;
        }
        String string2 = GraphicsEnvironment.chooseAbi((ApplicationInfo)object);
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(((ApplicationInfo)object).nativeLibraryDir);
        ((StringBuilder)charSequence).append(File.pathSeparator);
        ((StringBuilder)charSequence).append(((ApplicationInfo)object).sourceDir);
        ((StringBuilder)charSequence).append("!/lib/");
        ((StringBuilder)charSequence).append(string2);
        return ((StringBuilder)charSequence).toString();
    }

    private static String getDriverForPkg(Context object, Bundle object2, String charSequence) {
        Object object3 = object2 != null ? ((BaseBundle)object2).getString("angle_gl_driver_all_angle") : Settings.Global.getString(((Context)object).getContentResolver(), "angle_gl_driver_all_angle");
        if (object3 != null && ((String)object3).equals("1")) {
            return sDriverMap.get((Object)((Object)OpenGlDriverChoice.ANGLE));
        }
        object3 = ((Context)object).getContentResolver();
        object = GraphicsEnvironment.getGlobalSettingsString((ContentResolver)object3, (Bundle)object2, "angle_gl_driver_selection_pkgs");
        object2 = GraphicsEnvironment.getGlobalSettingsString((ContentResolver)object3, (Bundle)object2, "angle_gl_driver_selection_values");
        if (charSequence != null && !((String)charSequence).isEmpty()) {
            if (object.size() != object2.size()) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Global.Settings values are invalid: globalSettingsDriverPkgs.size = ");
                ((StringBuilder)charSequence).append(object.size());
                ((StringBuilder)charSequence).append(", globalSettingsDriverValues.size = ");
                ((StringBuilder)charSequence).append(object2.size());
                Log.w("GraphicsEnvironment", ((StringBuilder)charSequence).toString());
                return sDriverMap.get((Object)((Object)OpenGlDriverChoice.DEFAULT));
            }
            int n = GraphicsEnvironment.getGlobalSettingsPkgIndex((String)charSequence, (List<String>)object);
            if (n < 0) {
                return sDriverMap.get((Object)((Object)OpenGlDriverChoice.DEFAULT));
            }
            return (String)object2.get(n);
        }
        return sDriverMap.get((Object)((Object)OpenGlDriverChoice.DEFAULT));
    }

    private static int getGlobalSettingsPkgIndex(String string2, List<String> list) {
        for (int i = 0; i < list.size(); ++i) {
            if (!list.get(i).equals(string2)) continue;
            return i;
        }
        return -1;
    }

    private static List<String> getGlobalSettingsString(ContentResolver arrayList, Bundle bundle, String string2) {
        arrayList = bundle != null ? bundle.getString(string2) : Settings.Global.getString((ContentResolver)((Object)arrayList), string2);
        arrayList = arrayList != null ? new ArrayList<String>(Arrays.asList(((String)((Object)arrayList)).split(","))) : new ArrayList<String>();
        return arrayList;
    }

    public static GraphicsEnvironment getInstance() {
        return sInstance;
    }

    private static native boolean getShouldUseAngle(String var0);

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static String getSphalLibraries(Context object, String arrayList) {
        try {
            arrayList = ((Context)object).createPackageContext((String)((Object)arrayList), 4);
            Object object2 = new InputStreamReader(((Context)((Object)arrayList)).getAssets().open("sphal_libraries.txt"));
            object = new BufferedReader((Reader)object2);
            arrayList = new ArrayList<Object>();
            do {
                if ((object2 = ((BufferedReader)object).readLine()) == null) {
                    return String.join((CharSequence)":", arrayList);
                }
                arrayList.add(object2);
            } while (true);
        }
        catch (IOException iOException) {
            return "";
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            // empty catch block
        }
        return "";
    }

    private static int getVulkanVersion(PackageManager packageManager) {
        if (packageManager.hasSystemFeature("android.hardware.vulkan.version", 4198400)) {
            return 4198400;
        }
        if (packageManager.hasSystemFeature("android.hardware.vulkan.version", 4194304)) {
            return 4194304;
        }
        return 0;
    }

    public static native void hintActivityLaunch();

    private static boolean isDebuggable(Context context) {
        boolean bl = (context.getApplicationInfo().flags & 2) > 0;
        return bl;
    }

    private static native void setAngleInfo(String var0, String var1, String var2, FileDescriptor var3, long var4, long var6);

    private static native void setDebugLayers(String var0);

    private static native void setDebugLayersGLES(String var0);

    private static native void setDriverPathAndSphalLibraries(String var0, String var1);

    private static native void setGpuStats(String var0, String var1, long var2, long var4, String var6, int var7);

    private static native void setLayerPaths(ClassLoader var0, String var1);

    private boolean setupAndUseAngle(Context object, String string2) {
        if (!this.setupAngle((Context)object, null, ((Context)object).getPackageManager(), string2)) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Package '");
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append("' should not use ANGLE");
            Log.v("GraphicsEnvironment", ((StringBuilder)object).toString());
            return false;
        }
        boolean bl = GraphicsEnvironment.getShouldUseAngle(string2);
        object = new StringBuilder();
        ((StringBuilder)object).append("Package '");
        ((StringBuilder)object).append(string2);
        ((StringBuilder)object).append("' should use ANGLE = '");
        ((StringBuilder)object).append(bl);
        ((StringBuilder)object).append("'");
        Log.v("GraphicsEnvironment", ((StringBuilder)object).toString());
        return bl;
    }

    private static boolean setupAngleRulesApk(String string2, ApplicationInfo object, PackageManager packageManager, String string3, String string4, String string5) {
        object = packageManager.getResourcesForApplication((ApplicationInfo)object).getAssets();
        try {
            object = ((AssetManager)object).openFd("a4a_rules.json");
            GraphicsEnvironment.setAngleInfo(string4, string3, string5, ((AssetFileDescriptor)object).getFileDescriptor(), ((AssetFileDescriptor)object).getStartOffset(), ((AssetFileDescriptor)object).getLength());
            ((AssetFileDescriptor)object).close();
            return true;
        }
        catch (IOException iOException) {
            try {
                object = new StringBuilder();
                ((StringBuilder)object).append("Failed to get AssetFileDescriptor for a4a_rules.json from '");
                ((StringBuilder)object).append(string2);
                ((StringBuilder)object).append("': ");
                ((StringBuilder)object).append(iOException);
                Log.w("GraphicsEnvironment", ((StringBuilder)object).toString());
            }
            catch (PackageManager.NameNotFoundException nameNotFoundException) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Failed to get AssetManager for '");
                ((StringBuilder)object).append(string2);
                ((StringBuilder)object).append("': ");
                ((StringBuilder)object).append(nameNotFoundException);
                Log.w("GraphicsEnvironment", ((StringBuilder)object).toString());
            }
        }
        return false;
    }

    private static boolean setupAngleWithTempRulesFile(Context object, String charSequence, String string2, String string3) {
        boolean bl = GraphicsEnvironment.isDebuggable((Context)object);
        boolean bl2 = GraphicsEnvironment.getCanLoadSystemLibraries() == 1;
        if (!bl && !bl2) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Skipping loading temporary rules file: appIsDebuggable = ");
            ((StringBuilder)object).append(bl);
            ((StringBuilder)object).append(", adbRootEnabled = ");
            ((StringBuilder)object).append(bl2);
            Log.v("GraphicsEnvironment", ((StringBuilder)object).toString());
            return false;
        }
        object = SystemProperties.get("debug.angle.rules");
        if (object != null && !((String)object).isEmpty()) {
            Object object2 = new StringBuilder();
            ((StringBuilder)object2).append("Detected system property debug.angle.rules: ");
            ((StringBuilder)object2).append((String)object);
            Log.i("GraphicsEnvironment", ((StringBuilder)object2).toString());
            if (new File((String)object).exists()) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append((String)object);
                ((StringBuilder)object2).append(" exists, loading file.");
                Log.i("GraphicsEnvironment", ((StringBuilder)object2).toString());
                object2 = new FileInputStream((String)object);
                try {
                    FileDescriptor fileDescriptor = ((FileInputStream)object2).getFD();
                    long l = ((FileInputStream)object2).getChannel().size();
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Loaded temporary ANGLE rules from ");
                    stringBuilder.append((String)object);
                    Log.i("GraphicsEnvironment", stringBuilder.toString());
                    GraphicsEnvironment.setAngleInfo(string2, (String)charSequence, string3, fileDescriptor, 0L, l);
                    ((FileInputStream)object2).close();
                    return true;
                }
                catch (IOException iOException) {
                    try {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Hit IOException thrown by FileInputStream: ");
                        ((StringBuilder)object).append(iOException);
                        Log.w("GraphicsEnvironment", ((StringBuilder)object).toString());
                    }
                    catch (SecurityException securityException) {
                        charSequence = new StringBuilder();
                        ((StringBuilder)charSequence).append("Temp ANGLE rules file not accessible: ");
                        ((StringBuilder)charSequence).append(securityException);
                        Log.w("GraphicsEnvironment", ((StringBuilder)charSequence).toString());
                    }
                    catch (FileNotFoundException fileNotFoundException) {
                        charSequence = new StringBuilder();
                        ((StringBuilder)charSequence).append("Temp ANGLE rules file not found: ");
                        ((StringBuilder)charSequence).append(fileNotFoundException);
                        Log.w("GraphicsEnvironment", ((StringBuilder)charSequence).toString());
                    }
                }
            }
            return false;
        }
        Log.v("GraphicsEnvironment", "System property 'debug.angle.rules' is not set or is empty");
        return false;
    }

    private void setupGpuLayers(Context object, Bundle object2, PackageManager object3, String object4) {
        Object object5;
        block17 : {
            Object object6;
            block16 : {
                object6 = "";
                if (GraphicsEnvironment.isDebuggable((Context)object)) break block16;
                object5 = object6;
                if (GraphicsEnvironment.getCanLoadSystemLibraries() != 1) break block17;
            }
            object5 = object6;
            if (((BaseBundle)object2).getInt("enable_gpu_debug_layers", 0) != 0) {
                object = ((BaseBundle)object2).getString("gpu_debug_app");
                object5 = object6;
                if (object != null) {
                    object5 = object6;
                    if (object4 != null) {
                        object5 = object6;
                        if (!((String)object).isEmpty()) {
                            object5 = object6;
                            if (!((String)object4).isEmpty()) {
                                object5 = object6;
                                if (((String)object).equals(object4)) {
                                    object = new StringBuilder();
                                    ((StringBuilder)object).append("GPU debug layers enabled for ");
                                    ((StringBuilder)object).append((String)object4);
                                    Log.i("GraphicsEnvironment", ((StringBuilder)object).toString());
                                    object = new StringBuilder();
                                    ((StringBuilder)object).append(this.mDebugLayerPath);
                                    ((StringBuilder)object).append(":");
                                    object4 = ((StringBuilder)object).toString();
                                    object5 = ((BaseBundle)object2).getString("gpu_debug_layer_app");
                                    object = object4;
                                    if (object5 != null) {
                                        object = object4;
                                        if (!object5.isEmpty()) {
                                            object = new StringBuilder();
                                            ((StringBuilder)object).append("GPU debug layer app: ");
                                            ((StringBuilder)object).append((String)object5);
                                            Log.i("GraphicsEnvironment", ((StringBuilder)object).toString());
                                            object5 = object5.split(":");
                                            int n = 0;
                                            do {
                                                object = object4;
                                                if (n >= ((String[])object5).length) break;
                                                object6 = GraphicsEnvironment.getDebugLayerAppPaths((PackageManager)object3, object5[n]);
                                                object = object4;
                                                if (object6 != null) {
                                                    object = new StringBuilder();
                                                    ((StringBuilder)object).append((String)object4);
                                                    ((StringBuilder)object).append((String)object6);
                                                    ((StringBuilder)object).append(":");
                                                    object = ((StringBuilder)object).toString();
                                                }
                                                ++n;
                                                object4 = object;
                                            } while (true);
                                        }
                                    }
                                    object3 = ((BaseBundle)object2).getString("gpu_debug_layers");
                                    object4 = new StringBuilder();
                                    ((StringBuilder)object4).append("Vulkan debug layer list: ");
                                    ((StringBuilder)object4).append((String)object3);
                                    Log.i("GraphicsEnvironment", ((StringBuilder)object4).toString());
                                    if (object3 != null && !((String)object3).isEmpty()) {
                                        GraphicsEnvironment.setDebugLayers((String)object3);
                                    }
                                    object2 = ((BaseBundle)object2).getString("gpu_debug_layers_gles");
                                    object3 = new StringBuilder();
                                    ((StringBuilder)object3).append("GLES debug layer list: ");
                                    ((StringBuilder)object3).append((String)object2);
                                    Log.i("GraphicsEnvironment", ((StringBuilder)object3).toString());
                                    object5 = object;
                                    if (object2 != null) {
                                        object5 = object;
                                        if (!((String)object2).isEmpty()) {
                                            GraphicsEnvironment.setDebugLayersGLES((String)object2);
                                            object5 = object;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        object = new StringBuilder();
        ((StringBuilder)object).append((String)object5);
        ((StringBuilder)object).append(this.mLayerPath);
        object = ((StringBuilder)object).toString();
        GraphicsEnvironment.setLayerPaths(this.mClassLoader, (String)object);
    }

    private boolean shouldShowAngleInUseDialogBox(Context context) {
        boolean bl = false;
        try {
            int n = Settings.Global.getInt(context.getContentResolver(), "show_angle_in_use_dialog_box");
            if (n == 1) {
                bl = true;
            }
            return bl;
        }
        catch (Settings.SettingNotFoundException | SecurityException exception) {
            return false;
        }
    }

    public static boolean shouldUseAngle(Context object, Bundle bundle, String string2) {
        if (string2.isEmpty()) {
            Log.v("GraphicsEnvironment", "No package name available yet, ANGLE should not be used");
            return false;
        }
        String string3 = GraphicsEnvironment.getDriverForPkg((Context)object, bundle, string2);
        boolean bl = GraphicsEnvironment.checkAngleWhitelist((Context)object, bundle, string2);
        boolean bl2 = string3.equals(sDriverMap.get((Object)((Object)OpenGlDriverChoice.ANGLE)));
        boolean bl3 = bl || bl2;
        if (!bl3) {
            return false;
        }
        if (bl) {
            object = new StringBuilder();
            ((StringBuilder)object).append("ANGLE whitelist includes ");
            ((StringBuilder)object).append(string2);
            Log.v("GraphicsEnvironment", ((StringBuilder)object).toString());
        }
        if (bl2) {
            object = new StringBuilder();
            ((StringBuilder)object).append("ANGLE developer option for ");
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append(": ");
            ((StringBuilder)object).append(string3);
            Log.v("GraphicsEnvironment", ((StringBuilder)object).toString());
        }
        return true;
    }

    public void setLayerPaths(ClassLoader classLoader, String string2, String string3) {
        this.mClassLoader = classLoader;
        this.mLayerPath = string2;
        this.mDebugLayerPath = string3;
    }

    public void setup(Context context, Bundle bundle) {
        PackageManager packageManager = context.getPackageManager();
        String string2 = context.getPackageName();
        Trace.traceBegin(2L, "setupGpuLayers");
        this.setupGpuLayers(context, bundle, packageManager, string2);
        Trace.traceEnd(2L);
        Trace.traceBegin(2L, "setupAngle");
        this.setupAngle(context, bundle, packageManager, string2);
        Trace.traceEnd(2L);
        Trace.traceBegin(2L, "chooseDriver");
        if (!GraphicsEnvironment.chooseDriver(context, bundle, packageManager, string2)) {
            GraphicsEnvironment.setGpuStats("system", "", 0L, SystemProperties.getLong("ro.gfx.driver_build_time", 0L), string2, GraphicsEnvironment.getVulkanVersion(packageManager));
        }
        Trace.traceEnd(2L);
    }

    public boolean setupAngle(Context context, Bundle object, PackageManager packageManager, String string2) {
        if (!GraphicsEnvironment.shouldUseAngle(context, (Bundle)object, string2)) {
            return false;
        }
        Object object2 = null;
        String string3 = this.getAngleDebugPackage(context, (Bundle)object);
        if (!string3.isEmpty()) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("ANGLE debug package enabled: ");
            ((StringBuilder)object2).append(string3);
            Log.i("GraphicsEnvironment", ((StringBuilder)object2).toString());
            try {
                object2 = packageManager.getApplicationInfo(string3, 0);
            }
            catch (PackageManager.NameNotFoundException nameNotFoundException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("ANGLE debug package '");
                stringBuilder.append(string3);
                stringBuilder.append("' not installed");
                Log.w("GraphicsEnvironment", stringBuilder.toString());
                return false;
            }
        }
        if (object2 == null) {
            string3 = this.getAnglePackageName(packageManager);
            if (!string3.isEmpty()) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("ANGLE package enabled: ");
                ((StringBuilder)object2).append(string3);
                Log.i("GraphicsEnvironment", ((StringBuilder)object2).toString());
                try {
                    object2 = packageManager.getApplicationInfo(string3, 1048576);
                }
                catch (PackageManager.NameNotFoundException nameNotFoundException) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("ANGLE package '");
                    stringBuilder.append(string3);
                    stringBuilder.append("' not installed");
                    Log.w("GraphicsEnvironment", stringBuilder.toString());
                    return false;
                }
            } else {
                Log.e("GraphicsEnvironment", "Failed to find ANGLE package.");
                return false;
            }
        }
        String string4 = GraphicsEnvironment.chooseAbi((ApplicationInfo)object2);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(((ApplicationInfo)object2).nativeLibraryDir);
        stringBuilder.append(File.pathSeparator);
        stringBuilder.append(((ApplicationInfo)object2).sourceDir);
        stringBuilder.append("!/lib/");
        stringBuilder.append(string4);
        string4 = stringBuilder.toString();
        object = GraphicsEnvironment.getDriverForPkg(context, (Bundle)object, string2);
        if (GraphicsEnvironment.setupAngleWithTempRulesFile(context, string2, string4, (String)object)) {
            return true;
        }
        return GraphicsEnvironment.setupAngleRulesApk(string3, (ApplicationInfo)object2, packageManager, string2, string4, (String)object);
    }

    public void showAngleInUseDialogBox(Context context) {
        Object object = context.getPackageName();
        if (this.shouldShowAngleInUseDialogBox(context) && this.setupAndUseAngle(context, (String)object)) {
            object = new Intent("android.app.action.ANGLE_FOR_ANDROID_TOAST_MESSAGE");
            ((Intent)object).setPackage(this.getAnglePackageName(context.getPackageManager()));
            context.sendOrderedBroadcast((Intent)object, null, new BroadcastReceiver(){

                @Override
                public void onReceive(Context context, Intent intent) {
                    Toast.makeText(context, this.getResultExtras(true).getString(GraphicsEnvironment.INTENT_KEY_A4A_TOAST_MESSAGE), 1).show();
                }
            }, null, -1, null, null);
        }
    }

    static enum OpenGlDriverChoice {
        DEFAULT,
        NATIVE,
        ANGLE;
        
    }

}

