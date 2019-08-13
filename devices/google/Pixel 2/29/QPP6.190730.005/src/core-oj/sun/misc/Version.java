/*
 * Decompiled with CFR 0.145.
 */
package sun.misc;

import java.io.PrintStream;

public class Version {
    private static final String java_profile_name = "";
    private static final String java_runtime_name = "Android Runtime";
    private static final String java_runtime_version = "0.9";
    private static final String java_version = "0";
    private static int jdk_build_number = 0;
    private static int jdk_major_version = 0;
    private static int jdk_micro_version = 0;
    private static int jdk_minor_version = 0;
    private static String jdk_special_version;
    private static int jdk_update_version = 0;
    private static boolean jvmVersionInfoAvailable = false;
    private static int jvm_build_number = 0;
    private static int jvm_major_version = 0;
    private static int jvm_micro_version = 0;
    private static int jvm_minor_version = 0;
    private static String jvm_special_version;
    private static int jvm_update_version = 0;
    private static final String launcher_name = "";
    private static boolean versionsInitialized;

    static {
        versionsInitialized = false;
        jvm_major_version = 0;
        jvm_minor_version = 0;
        jvm_micro_version = 0;
        jvm_update_version = 0;
        jvm_build_number = 0;
        jvm_special_version = null;
        jdk_major_version = 0;
        jdk_minor_version = 0;
        jdk_micro_version = 0;
        jdk_update_version = 0;
        jdk_build_number = 0;
        jdk_special_version = null;
    }

    public static native String getJdkSpecialVersion();

    private static native void getJdkVersionInfo();

    public static native String getJvmSpecialVersion();

    private static native boolean getJvmVersionInfo();

    public static void initSystemProperties() {
        System.setUnchangeableSystemProperty("java.version", "0");
        System.setUnchangeableSystemProperty("java.runtime.version", "0.9");
        System.setUnchangeableSystemProperty("java.runtime.name", "Android Runtime");
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static void initVersions() {
        synchronized (Version.class) {
            block17 : {
                CharSequence charSequence;
                boolean bl = versionsInitialized;
                if (bl) {
                    return;
                }
                jvmVersionInfoAvailable = Version.getJvmVersionInfo();
                if (jvmVersionInfoAvailable || (charSequence = System.getProperty("java.vm.version")).length() < 5 || !Character.isDigit(charSequence.charAt(0)) || charSequence.charAt(1) != '.' || !Character.isDigit(charSequence.charAt(2)) || charSequence.charAt(3) != '.' || !Character.isDigit(charSequence.charAt(4))) break block17;
                jvm_major_version = Character.digit(charSequence.charAt(0), 10);
                jvm_minor_version = Character.digit(charSequence.charAt(2), 10);
                jvm_micro_version = Character.digit(charSequence.charAt(4), 10);
                CharSequence charSequence2 = charSequence.subSequence(5, charSequence.length());
                charSequence = charSequence2;
                if (charSequence2.charAt(0) == '_') {
                    charSequence = charSequence2;
                    if (charSequence2.length() >= 3) {
                        int n;
                        block16 : {
                            int n2;
                            n = 0;
                            if (Character.isDigit(charSequence2.charAt(1)) && Character.isDigit(charSequence2.charAt(2)) && Character.isDigit(charSequence2.charAt(3))) {
                                n2 = 4;
                            } else {
                                n2 = n;
                                if (Character.isDigit(charSequence2.charAt(1))) {
                                    bl = Character.isDigit(charSequence2.charAt(2));
                                    n2 = n;
                                    if (bl) {
                                        n2 = 3;
                                    }
                                }
                            }
                            try {
                                jvm_update_version = Integer.valueOf(charSequence2.subSequence(1, n2).toString());
                                n = n2;
                                if (charSequence2.length() < n2 + 1) break block16;
                                char c = charSequence2.charAt(n2);
                                n = n2;
                                if (c < 'a') break block16;
                                n = n2;
                                if (c > 'z') break block16;
                                jvm_special_version = Character.toString(c);
                                n = n2 + 1;
                            }
                            catch (NumberFormatException numberFormatException) {
                                return;
                            }
                        }
                        charSequence = charSequence2.subSequence(n, charSequence2.length());
                    }
                }
                if (charSequence.charAt(0) == '-') {
                    for (CharSequence charSequence3 : charSequence.subSequence(1, charSequence.length()).toString().split("-")) {
                        if (((String)charSequence3).charAt(0) != 'b' || ((String)charSequence3).length() != 3 || !Character.isDigit(((String)charSequence3).charAt(1)) || !Character.isDigit(((String)charSequence3).charAt(2))) continue;
                        jvm_build_number = Integer.valueOf(((String)charSequence3).substring(1, 3));
                        break;
                    }
                }
            }
            Version.getJdkVersionInfo();
            versionsInitialized = true;
            return;
        }
    }

    public static int jdkBuildNumber() {
        synchronized (Version.class) {
            if (!versionsInitialized) {
                Version.initVersions();
            }
            int n = jdk_build_number;
            return n;
        }
    }

    public static int jdkMajorVersion() {
        synchronized (Version.class) {
            if (!versionsInitialized) {
                Version.initVersions();
            }
            int n = jdk_major_version;
            return n;
        }
    }

    public static int jdkMicroVersion() {
        synchronized (Version.class) {
            if (!versionsInitialized) {
                Version.initVersions();
            }
            int n = jdk_micro_version;
            return n;
        }
    }

    public static int jdkMinorVersion() {
        synchronized (Version.class) {
            if (!versionsInitialized) {
                Version.initVersions();
            }
            int n = jdk_minor_version;
            return n;
        }
    }

    public static String jdkSpecialVersion() {
        synchronized (Version.class) {
            if (!versionsInitialized) {
                Version.initVersions();
            }
            if (jdk_special_version == null) {
                jdk_special_version = Version.getJdkSpecialVersion();
            }
            String string = jdk_special_version;
            return string;
        }
    }

    public static int jdkUpdateVersion() {
        synchronized (Version.class) {
            if (!versionsInitialized) {
                Version.initVersions();
            }
            int n = jdk_update_version;
            return n;
        }
    }

    public static int jvmBuildNumber() {
        synchronized (Version.class) {
            if (!versionsInitialized) {
                Version.initVersions();
            }
            int n = jvm_build_number;
            return n;
        }
    }

    public static int jvmMajorVersion() {
        synchronized (Version.class) {
            if (!versionsInitialized) {
                Version.initVersions();
            }
            int n = jvm_major_version;
            return n;
        }
    }

    public static int jvmMicroVersion() {
        synchronized (Version.class) {
            if (!versionsInitialized) {
                Version.initVersions();
            }
            int n = jvm_micro_version;
            return n;
        }
    }

    public static int jvmMinorVersion() {
        synchronized (Version.class) {
            if (!versionsInitialized) {
                Version.initVersions();
            }
            int n = jvm_minor_version;
            return n;
        }
    }

    public static String jvmSpecialVersion() {
        synchronized (Version.class) {
            if (!versionsInitialized) {
                Version.initVersions();
            }
            if (jvm_special_version == null) {
                jvm_special_version = Version.getJvmSpecialVersion();
            }
            String string = jvm_special_version;
            return string;
        }
    }

    public static int jvmUpdateVersion() {
        synchronized (Version.class) {
            if (!versionsInitialized) {
                Version.initVersions();
            }
            int n = jvm_update_version;
            return n;
        }
    }

    public static void print() {
        Version.print(System.err);
    }

    public static void print(PrintStream printStream) {
        boolean bl = false;
        String string = System.getProperty("java.awt.headless");
        boolean bl2 = bl;
        if (string != null) {
            bl2 = bl;
            if (string.equalsIgnoreCase("true")) {
                bl2 = true;
            }
        }
        printStream.println(" version \"0\"");
        printStream.print("Android Runtime (build 0.9");
        if ("".length() > 0) {
            printStream.print(", profile ");
        }
        if ("Android Runtime".indexOf("Embedded") != -1 && bl2) {
            printStream.print(", headless");
        }
        printStream.println(')');
        String string2 = System.getProperty("java.vm.name");
        string = System.getProperty("java.vm.version");
        String string3 = System.getProperty("java.vm.info");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append(" (build ");
        stringBuilder.append(string);
        stringBuilder.append(", ");
        stringBuilder.append(string3);
        stringBuilder.append(")");
        printStream.println(stringBuilder.toString());
    }

    public static void println() {
        Version.print(System.err);
        System.err.println();
    }
}

