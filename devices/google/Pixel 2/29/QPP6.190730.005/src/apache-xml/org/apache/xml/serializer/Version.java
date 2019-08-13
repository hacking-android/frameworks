/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.serializer;

import java.io.PrintStream;

public final class Version {
    public static int getDevelopmentVersionNum() {
        block3 : {
            try {
                String string = new String("");
                if (string.length() != 0) break block3;
                return 0;
            }
            catch (NumberFormatException numberFormatException) {
                return 0;
            }
        }
        int n = Integer.parseInt("");
        return n;
    }

    public static String getImplementationLanguage() {
        return "Java";
    }

    public static int getMaintenanceVersionNum() {
        return 1;
    }

    public static int getMajorVersionNum() {
        return 2;
    }

    public static String getProduct() {
        return "Serializer";
    }

    public static int getReleaseVersionNum() {
        return 7;
    }

    public static String getVersion() {
        int n;
        StringBuilder stringBuilder;
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append(Version.getProduct());
        stringBuilder2.append(" ");
        stringBuilder2.append(Version.getImplementationLanguage());
        stringBuilder2.append(" ");
        stringBuilder2.append(Version.getMajorVersionNum());
        stringBuilder2.append(".");
        stringBuilder2.append(Version.getReleaseVersionNum());
        stringBuilder2.append(".");
        if (Version.getDevelopmentVersionNum() > 0) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("D");
            n = Version.getDevelopmentVersionNum();
        } else {
            stringBuilder = new StringBuilder();
            stringBuilder.append("");
            n = Version.getMaintenanceVersionNum();
        }
        stringBuilder.append(n);
        stringBuilder2.append(stringBuilder.toString());
        return stringBuilder2.toString();
    }

    public static void main(String[] arrstring) {
        System.out.println(Version.getVersion());
    }
}

