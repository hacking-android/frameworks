/*
 * Decompiled with CFR 0.145.
 */
package sun.security.util;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ResourceBundle;
import sun.security.util.Resources;

public class ResourcesMgr {
    private static ResourceBundle altBundle;
    private static ResourceBundle bundle;

    public static String getString(String string) {
        if (bundle == null) {
            bundle = AccessController.doPrivileged(new PrivilegedAction<ResourceBundle>(){

                @Override
                public ResourceBundle run() {
                    return ResourceBundle.getBundle(Resources.class.getName());
                }
            });
        }
        return bundle.getString(string);
    }

    public static String getString(String string, final String string2) {
        if (altBundle == null) {
            altBundle = AccessController.doPrivileged(new PrivilegedAction<ResourceBundle>(){

                @Override
                public ResourceBundle run() {
                    return ResourceBundle.getBundle(string2);
                }
            });
        }
        return altBundle.getString(string);
    }

}

