/*
 * Decompiled with CFR 0.145.
 */
package libcore.timezone;

import java.util.ArrayList;

public final class TimeZoneDataFiles {
    private static final String ANDROID_DATA_ENV = "ANDROID_DATA";
    private static final String ANDROID_ROOT_ENV = "ANDROID_ROOT";
    private static final String ANDROID_RUNTIME_ROOT_ENV = "ANDROID_RUNTIME_ROOT";
    private static final String ANDROID_TZDATA_ROOT_ENV = "ANDROID_TZDATA_ROOT";

    private TimeZoneDataFiles() {
    }

    public static String generateIcuDataPath() {
        ArrayList<String> arrayList = new ArrayList<String>(3);
        String string = TimeZoneDataFiles.getEnvironmentPath(ANDROID_DATA_ENV, "/misc/zoneinfo/current/icu/");
        if (string != null) {
            arrayList.add(string);
        }
        if ((string = TimeZoneDataFiles.getTimeZoneModuleFile("icu/")) != null) {
            arrayList.add(string);
        }
        if ((string = TimeZoneDataFiles.getRuntimeModuleFile("icu/")) != null) {
            arrayList.add(string);
        }
        return String.join((CharSequence)":", arrayList);
    }

    public static String getDataTimeZoneFile(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(TimeZoneDataFiles.getDataTimeZoneRootDir());
        stringBuilder.append("current/");
        stringBuilder.append(string);
        return stringBuilder.toString();
    }

    public static String getDataTimeZoneRootDir() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(System.getenv(ANDROID_DATA_ENV));
        stringBuilder.append("/misc/zoneinfo/");
        return stringBuilder.toString();
    }

    private static String getEnvironmentPath(String charSequence, String string) {
        String string2 = System.getenv((String)charSequence);
        if (string2 == null) {
            return null;
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(string2);
        ((StringBuilder)charSequence).append(string);
        return ((StringBuilder)charSequence).toString();
    }

    public static String getRuntimeModuleFile(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(System.getenv(ANDROID_RUNTIME_ROOT_ENV));
        stringBuilder.append("/etc/");
        stringBuilder.append(string);
        return stringBuilder.toString();
    }

    public static String getRuntimeModuleTzVersionFile() {
        return TimeZoneDataFiles.getRuntimeModuleFile("tz/tz_version");
    }

    public static String getSystemIcuFile(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("/usr/icu/");
        stringBuilder.append(string);
        return TimeZoneDataFiles.getEnvironmentPath(ANDROID_ROOT_ENV, stringBuilder.toString());
    }

    public static String getSystemTimeZoneFile(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("/usr/share/zoneinfo/");
        stringBuilder.append(string);
        return TimeZoneDataFiles.getEnvironmentPath(ANDROID_ROOT_ENV, stringBuilder.toString());
    }

    public static String[] getTimeZoneFilePaths(String string) {
        String string2 = TimeZoneDataFiles.getDataTimeZoneFile(string);
        CharSequence charSequence = new StringBuilder();
        charSequence.append("tz/");
        charSequence.append(string);
        charSequence = TimeZoneDataFiles.getTimeZoneModuleFile(charSequence.toString());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("tz/");
        stringBuilder.append(string);
        return new String[]{string2, charSequence, TimeZoneDataFiles.getRuntimeModuleFile(stringBuilder.toString()), TimeZoneDataFiles.getSystemTimeZoneFile(string)};
    }

    public static String getTimeZoneModuleFile(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(System.getenv(ANDROID_TZDATA_ROOT_ENV));
        stringBuilder.append("/etc/");
        stringBuilder.append(string);
        return stringBuilder.toString();
    }
}

