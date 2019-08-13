/*
 * Decompiled with CFR 0.145.
 */
package java.lang;

public final class AndroidHardcodedSystemProperties {
    public static final String JAVA_VERSION = "0";
    static final String[][] STATIC_PROPERTIES;

    static {
        String[] arrstring = new String[]{"java.compiler", ""};
        String[] arrstring2 = new String[]{"java.specification.vendor", "The Android Project"};
        String[] arrstring3 = new String[]{"java.vendor.url", "http://www.android.com/"};
        String[] arrstring4 = new String[]{"java.vm.specification.vendor", "The Android Project"};
        String[] arrstring5 = new String[]{"file.encoding", "UTF-8"};
        String[] arrstring6 = new String[]{"ICUDebug", null};
        String[] arrstring7 = new String[]{"http.maxConnections", null};
        STATIC_PROPERTIES = new String[][]{{"java.class.version", "50.0"}, {"java.version", JAVA_VERSION}, arrstring, {"java.ext.dirs", ""}, {"java.specification.name", "Dalvik Core Library"}, arrstring2, {"java.specification.version", "0.9"}, {"java.vendor", "The Android Project"}, arrstring3, {"java.vm.name", "Dalvik"}, {"java.vm.specification.name", "Dalvik Virtual Machine Specification"}, arrstring4, {"java.vm.specification.version", "0.9"}, {"java.vm.vendor", "The Android Project"}, {"java.vm.vendor.url", "http://www.android.com/"}, {"java.net.preferIPv6Addresses", "false"}, arrstring5, {"file.separator", "/"}, {"line.separator", "\n"}, {"path.separator", ":"}, arrstring6, {"android.icu.text.DecimalFormat.SkipExtendedSeparatorParsing", null}, {"android.icu.text.MessagePattern.ApostropheMode", null}, {"sun.io.useCanonCaches", null}, {"sun.io.useCanonPrefixCache", null}, {"http.keepAlive", null}, {"http.keepAliveDuration", null}, arrstring7, {"javax.net.debug", null}, {"com.sun.security.preserveOldDCEncoding", null}, {"java.util.logging.manager", null}};
    }
}

