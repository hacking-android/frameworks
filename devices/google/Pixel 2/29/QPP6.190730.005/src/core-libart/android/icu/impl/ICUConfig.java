/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

import android.icu.impl.ICUData;
import java.io.IOException;
import java.io.InputStream;
import java.security.AccessControlException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.MissingResourceException;
import java.util.Properties;

public class ICUConfig {
    private static final Properties CONFIG_PROPS = new Properties();
    public static final String CONFIG_PROPS_FILE = "/android/icu/ICUConfig.properties";

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    static {
        InputStream inputStream = ICUData.getStream(CONFIG_PROPS_FILE);
        if (inputStream == null) return;
        CONFIG_PROPS.load(inputStream);
        {
            catch (Throwable throwable) {
                inputStream.close();
                throw throwable;
            }
        }
        try {
            inputStream.close();
            return;
        }
        catch (IOException iOException) {
            return;
        }
        catch (MissingResourceException missingResourceException) {
            // empty catch block
        }
    }

    public static String get(String string) {
        return ICUConfig.get(string, null);
    }

    public static String get(final String string, String string2) {
        PrivilegedAction<String> privilegedAction;
        Object object = null;
        if (System.getSecurityManager() != null) {
            try {
                privilegedAction = new PrivilegedAction<String>(){

                    @Override
                    public String run() {
                        return System.getProperty(string);
                    }
                };
                privilegedAction = (String)AccessController.doPrivileged(privilegedAction);
                object = privilegedAction;
            }
            catch (AccessControlException accessControlException) {}
        } else {
            object = System.getProperty(string);
        }
        privilegedAction = object;
        if (object == null) {
            privilegedAction = CONFIG_PROPS.getProperty(string, string2);
        }
        return privilegedAction;
    }

}

