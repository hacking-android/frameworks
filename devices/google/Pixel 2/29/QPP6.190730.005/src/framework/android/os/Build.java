/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.system.VMRuntime
 */
package android.os;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.app.ActivityThread;
import android.content.ContextWrapper;
import android.os.IDeviceIdentifiersPolicyService;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.VintfObject;
import android.text.TextUtils;
import android.util.Slog;
import dalvik.system.VMRuntime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Build {
    public static final String BOARD;
    public static final String BOOTLOADER;
    public static final String BRAND;
    @Deprecated
    public static final String CPU_ABI;
    @Deprecated
    public static final String CPU_ABI2;
    public static final String DEVICE;
    public static final String DISPLAY;
    public static final String FINGERPRINT;
    public static final String HARDWARE;
    public static final String HOST;
    public static final String ID;
    public static final boolean IS_CONTAINER;
    @UnsupportedAppUsage
    public static final boolean IS_DEBUGGABLE;
    public static final boolean IS_EMULATOR;
    public static final boolean IS_ENG;
    public static final boolean IS_TREBLE_ENABLED;
    public static final boolean IS_USER;
    public static final boolean IS_USERDEBUG;
    public static final String MANUFACTURER;
    public static final String MODEL;
    @SystemApi
    public static final boolean PERMISSIONS_REVIEW_REQUIRED = true;
    public static final String PRODUCT;
    @Deprecated
    public static final String RADIO;
    @Deprecated
    public static final String SERIAL;
    public static final String[] SUPPORTED_32_BIT_ABIS;
    public static final String[] SUPPORTED_64_BIT_ABIS;
    public static final String[] SUPPORTED_ABIS;
    private static final String TAG = "Build";
    public static final String TAGS;
    public static final long TIME;
    public static final String TYPE;
    public static final String UNKNOWN = "unknown";
    public static final String USER;

    static {
        ID = Build.getString("ro.build.id");
        DISPLAY = Build.getString("ro.build.display.id");
        PRODUCT = Build.getString("ro.product.name");
        DEVICE = Build.getString("ro.product.device");
        BOARD = Build.getString("ro.product.board");
        MANUFACTURER = Build.getString("ro.product.manufacturer");
        BRAND = Build.getString("ro.product.brand");
        MODEL = Build.getString("ro.product.model");
        BOOTLOADER = Build.getString("ro.bootloader");
        RADIO = Build.getString("gsm.version.baseband");
        HARDWARE = Build.getString("ro.hardware");
        IS_EMULATOR = Build.getString("ro.kernel.qemu").equals("1");
        SERIAL = Build.getString("no.such.thing");
        SUPPORTED_ABIS = Build.getStringList("ro.product.cpu.abilist", ",");
        SUPPORTED_32_BIT_ABIS = Build.getStringList("ro.product.cpu.abilist32", ",");
        SUPPORTED_64_BIT_ABIS = Build.getStringList("ro.product.cpu.abilist64", ",");
        String[] arrstring = VMRuntime.getRuntime().is64Bit() ? SUPPORTED_64_BIT_ABIS : SUPPORTED_32_BIT_ABIS;
        CPU_ABI = arrstring[0];
        int n = arrstring.length;
        boolean bl = true;
        CPU_ABI2 = n > 1 ? arrstring[1] : "";
        TYPE = Build.getString("ro.build.type");
        TAGS = Build.getString("ro.build.tags");
        FINGERPRINT = Build.deriveFingerprint();
        IS_TREBLE_ENABLED = SystemProperties.getBoolean("ro.treble.enabled", false);
        TIME = Build.getLong("ro.build.date.utc") * 1000L;
        USER = Build.getString("ro.build.user");
        HOST = Build.getString("ro.build.host");
        if (SystemProperties.getInt("ro.debuggable", 0) != 1) {
            bl = false;
        }
        IS_DEBUGGABLE = bl;
        IS_ENG = "eng".equals(TYPE);
        IS_USERDEBUG = "userdebug".equals(TYPE);
        IS_USER = "user".equals(TYPE);
        IS_CONTAINER = SystemProperties.getBoolean("ro.boot.container", false);
    }

    private static String deriveFingerprint() {
        String string2 = SystemProperties.get("ro.build.fingerprint");
        CharSequence charSequence = string2;
        if (TextUtils.isEmpty(string2)) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(Build.getString("ro.product.brand"));
            ((StringBuilder)charSequence).append('/');
            ((StringBuilder)charSequence).append(Build.getString("ro.product.name"));
            ((StringBuilder)charSequence).append('/');
            ((StringBuilder)charSequence).append(Build.getString("ro.product.device"));
            ((StringBuilder)charSequence).append(':');
            ((StringBuilder)charSequence).append(Build.getString("ro.build.version.release"));
            ((StringBuilder)charSequence).append('/');
            ((StringBuilder)charSequence).append(Build.getString("ro.build.id"));
            ((StringBuilder)charSequence).append('/');
            ((StringBuilder)charSequence).append(Build.getString("ro.build.version.incremental"));
            ((StringBuilder)charSequence).append(':');
            ((StringBuilder)charSequence).append(Build.getString("ro.build.type"));
            ((StringBuilder)charSequence).append('/');
            ((StringBuilder)charSequence).append(Build.getString("ro.build.tags"));
            charSequence = ((StringBuilder)charSequence).toString();
        }
        return charSequence;
    }

    public static void ensureFingerprintProperty() {
        if (TextUtils.isEmpty(SystemProperties.get("ro.build.fingerprint"))) {
            try {
                SystemProperties.set("ro.build.fingerprint", FINGERPRINT);
            }
            catch (IllegalArgumentException illegalArgumentException) {
                Slog.e(TAG, "Failed to set fingerprint property", illegalArgumentException);
            }
        }
    }

    public static List<Partition> getFingerprintedPartitions() {
        ArrayList<Partition> arrayList = new ArrayList<Partition>();
        for (String string2 : new String[]{"bootimage", "odm", "product", "product_services", "system", "vendor"}) {
            CharSequence charSequence = new StringBuilder();
            charSequence.append("ro.");
            charSequence.append(string2);
            charSequence.append(".build.fingerprint");
            charSequence = SystemProperties.get(charSequence.toString());
            if (TextUtils.isEmpty(charSequence)) continue;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("ro.");
            stringBuilder.append(string2);
            stringBuilder.append(".build.date.utc");
            arrayList.add(new Partition(string2, (String)charSequence, Build.getLong(stringBuilder.toString()) * 1000L));
        }
        return arrayList;
    }

    @UnsupportedAppUsage
    private static long getLong(String string2) {
        try {
            long l = Long.parseLong(SystemProperties.get(string2));
            return l;
        }
        catch (NumberFormatException numberFormatException) {
            return -1L;
        }
    }

    public static String getRadioVersion() {
        String string2;
        block0 : {
            string2 = SystemProperties.get("gsm.version.baseband");
            if (!TextUtils.isEmpty(string2)) break block0;
            string2 = null;
        }
        return string2;
    }

    public static String getSerial() {
        Object object;
        IDeviceIdentifiersPolicyService iDeviceIdentifiersPolicyService;
        block5 : {
            block4 : {
                iDeviceIdentifiersPolicyService = IDeviceIdentifiersPolicyService.Stub.asInterface(ServiceManager.getService("device_identifiers"));
                try {
                    object = ActivityThread.currentApplication();
                    if (object == null) break block4;
                }
                catch (RemoteException remoteException) {
                    remoteException.rethrowFromSystemServer();
                    return UNKNOWN;
                }
                object = ((ContextWrapper)object).getPackageName();
                break block5;
            }
            object = null;
        }
        object = iDeviceIdentifiersPolicyService.getSerialForPackage((String)object);
        return object;
    }

    @UnsupportedAppUsage
    private static String getString(String string2) {
        return SystemProperties.get(string2, UNKNOWN);
    }

    private static String[] getStringList(String string2, String string3) {
        if ((string2 = SystemProperties.get(string2)).isEmpty()) {
            return new String[0];
        }
        return string2.split(string3);
    }

    public static boolean is64BitAbi(String string2) {
        return VMRuntime.is64BitAbi((String)string2);
    }

    public static boolean isBuildConsistent() {
        boolean bl = IS_ENG;
        boolean bl2 = true;
        if (bl) {
            return true;
        }
        if (IS_TREBLE_ENABLED) {
            int n = VintfObject.verifyWithoutAvb();
            if (n != 0) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Vendor interface is incompatible, error=");
                stringBuilder.append(String.valueOf(n));
                Slog.e(TAG, stringBuilder.toString());
            }
            if (n != 0) {
                bl2 = false;
            }
            return bl2;
        }
        String string2 = SystemProperties.get("ro.build.fingerprint");
        String string3 = SystemProperties.get("ro.vendor.build.fingerprint");
        SystemProperties.get("ro.bootimage.build.fingerprint");
        SystemProperties.get("ro.build.expect.bootloader");
        SystemProperties.get("ro.bootloader");
        SystemProperties.get("ro.build.expect.baseband");
        SystemProperties.get("gsm.version.baseband");
        if (TextUtils.isEmpty(string2)) {
            Slog.e(TAG, "Required ro.build.fingerprint is empty!");
            return false;
        }
        if (!TextUtils.isEmpty(string3) && !Objects.equals(string2, string3)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Mismatched fingerprints; system reported ");
            stringBuilder.append(string2);
            stringBuilder.append(" but vendor reported ");
            stringBuilder.append(string3);
            Slog.e(TAG, stringBuilder.toString());
            return false;
        }
        return true;
    }

    public static class Partition {
        public static final String PARTITION_NAME_SYSTEM = "system";
        private final String mFingerprint;
        private final String mName;
        private final long mTimeMs;

        private Partition(String string2, String string3, long l) {
            this.mName = string2;
            this.mFingerprint = string3;
            this.mTimeMs = l;
        }

        public boolean equals(Object object) {
            boolean bl;
            block1 : {
                boolean bl2 = object instanceof Partition;
                bl = false;
                if (!bl2) {
                    return false;
                }
                object = (Partition)object;
                if (!this.mName.equals(((Partition)object).mName) || !this.mFingerprint.equals(((Partition)object).mFingerprint) || this.mTimeMs != ((Partition)object).mTimeMs) break block1;
                bl = true;
            }
            return bl;
        }

        public long getBuildTimeMillis() {
            return this.mTimeMs;
        }

        public String getFingerprint() {
            return this.mFingerprint;
        }

        public String getName() {
            return this.mName;
        }

        public int hashCode() {
            return Objects.hash(this.mName, this.mFingerprint, this.mTimeMs);
        }
    }

    public static class VERSION {
        @UnsupportedAppUsage
        public static final String[] ACTIVE_CODENAMES;
        private static final String[] ALL_CODENAMES;
        public static final String BASE_OS;
        public static final String CODENAME;
        public static final int FIRST_SDK_INT;
        public static final String INCREMENTAL;
        public static final int MIN_SUPPORTED_TARGET_SDK_INT;
        @SystemApi
        public static final String PREVIEW_SDK_FINGERPRINT;
        public static final int PREVIEW_SDK_INT;
        public static final String RELEASE;
        public static final int RESOURCES_SDK_INT;
        @Deprecated
        public static final String SDK;
        public static final int SDK_INT;
        public static final String SECURITY_PATCH;

        static {
            INCREMENTAL = Build.getString("ro.build.version.incremental");
            RELEASE = Build.getString("ro.build.version.release");
            BASE_OS = SystemProperties.get("ro.build.version.base_os", "");
            SECURITY_PATCH = SystemProperties.get("ro.build.version.security_patch", "");
            SDK = Build.getString("ro.build.version.sdk");
            SDK_INT = SystemProperties.getInt("ro.build.version.sdk", 0);
            FIRST_SDK_INT = SystemProperties.getInt("ro.product.first_api_level", 0);
            PREVIEW_SDK_INT = SystemProperties.getInt("ro.build.version.preview_sdk", 0);
            PREVIEW_SDK_FINGERPRINT = SystemProperties.get("ro.build.version.preview_sdk_fingerprint", "REL");
            CODENAME = Build.getString("ro.build.version.codename");
            ALL_CODENAMES = Build.getStringList("ro.build.version.all_codenames", ",");
            String[] arrstring = "REL".equals(ALL_CODENAMES[0]) ? new String[0] : ALL_CODENAMES;
            ACTIVE_CODENAMES = arrstring;
            RESOURCES_SDK_INT = SDK_INT + ACTIVE_CODENAMES.length;
            MIN_SUPPORTED_TARGET_SDK_INT = SystemProperties.getInt("ro.build.version.min_supported_target_sdk", 0);
        }
    }

    public static class VERSION_CODES {
        public static final int BASE = 1;
        public static final int BASE_1_1 = 2;
        public static final int CUPCAKE = 3;
        public static final int CUR_DEVELOPMENT = 10000;
        public static final int DONUT = 4;
        public static final int ECLAIR = 5;
        public static final int ECLAIR_0_1 = 6;
        public static final int ECLAIR_MR1 = 7;
        public static final int FROYO = 8;
        public static final int GINGERBREAD = 9;
        public static final int GINGERBREAD_MR1 = 10;
        public static final int HONEYCOMB = 11;
        public static final int HONEYCOMB_MR1 = 12;
        public static final int HONEYCOMB_MR2 = 13;
        public static final int ICE_CREAM_SANDWICH = 14;
        public static final int ICE_CREAM_SANDWICH_MR1 = 15;
        public static final int JELLY_BEAN = 16;
        public static final int JELLY_BEAN_MR1 = 17;
        public static final int JELLY_BEAN_MR2 = 18;
        public static final int KITKAT = 19;
        public static final int KITKAT_WATCH = 20;
        public static final int L = 21;
        public static final int LOLLIPOP = 21;
        public static final int LOLLIPOP_MR1 = 22;
        public static final int M = 23;
        public static final int N = 24;
        public static final int N_MR1 = 25;
        public static final int O = 26;
        public static final int O_MR1 = 27;
        public static final int P = 28;
        public static final int Q = 29;
    }

}

