/*
 * Decompiled with CFR 0.145.
 */
package sun.net;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Properties;

public class NetProperties {
    private static Properties props = new Properties();

    static {
        AccessController.doPrivileged(new PrivilegedAction<Void>(){

            @Override
            public Void run() {
                NetProperties.loadDefaultProperties();
                return null;
            }
        });
    }

    private NetProperties() {
    }

    public static String get(String string) {
        String string2 = props.getProperty(string);
        try {
            string = System.getProperty(string, string2);
            return string;
        }
        catch (NullPointerException nullPointerException) {
        }
        catch (IllegalArgumentException illegalArgumentException) {
            // empty catch block
        }
        return null;
    }

    public static Boolean getBoolean(String object) {
        Object var1_4 = null;
        Object var2_5 = null;
        try {
            object = System.getProperty((String)object, props.getProperty((String)object));
        }
        catch (NullPointerException nullPointerException) {
            object = var1_4;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            object = var2_5;
        }
        if (object != null) {
            try {
                object = Boolean.valueOf((String)object);
                return object;
            }
            catch (NumberFormatException numberFormatException) {
                // empty catch block
            }
        }
        return null;
    }

    public static Integer getInteger(String object, int n) {
        Object var2_5 = null;
        Object var3_6 = null;
        try {
            object = System.getProperty((String)object, props.getProperty((String)object));
        }
        catch (NullPointerException nullPointerException) {
            object = var2_5;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            object = var3_6;
        }
        if (object != null) {
            try {
                object = Integer.decode((String)object);
                return object;
            }
            catch (NumberFormatException numberFormatException) {
                // empty catch block
            }
        }
        return new Integer(n);
    }

    private static void loadDefaultProperties() {
        Object object = System.getProperty("java.home");
        if (object != null) {
            try {
                Object object2 = new File((String)object, "lib");
                object = new File((File)object2, "net.properties");
                object = ((File)object).getCanonicalPath();
                object2 = new FileInputStream((String)object);
                object = new BufferedInputStream((InputStream)object2);
                props.load((InputStream)object);
                ((BufferedInputStream)object).close();
            }
            catch (Exception exception) {
                // empty catch block
            }
            return;
        }
        throw new Error("Can't find java.home ??");
    }

}

