/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.serializer;

import java.util.Properties;

public final class OutputPropertyUtils {
    public static boolean getBooleanProperty(String string, Properties properties) {
        return (string = properties.getProperty(string)) != null && string.equals("yes");
        {
        }
    }

    public static int getIntProperty(String string, Properties properties) {
        if ((string = properties.getProperty(string)) == null) {
            return 0;
        }
        return Integer.parseInt(string);
    }
}

