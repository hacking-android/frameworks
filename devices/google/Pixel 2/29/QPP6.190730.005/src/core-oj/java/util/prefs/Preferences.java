/*
 * Decompiled with CFR 0.145.
 */
package java.util.prefs;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Permission;
import java.util.Iterator;
import java.util.ServiceLoader;
import java.util.prefs.BackingStoreException;
import java.util.prefs.FileSystemPreferencesFactory;
import java.util.prefs.InvalidPreferencesFormatException;
import java.util.prefs.NodeChangeListener;
import java.util.prefs.PreferenceChangeListener;
import java.util.prefs.PreferencesFactory;
import java.util.prefs.XmlSupport;

public abstract class Preferences {
    public static final int MAX_KEY_LENGTH = 80;
    public static final int MAX_NAME_LENGTH = 80;
    public static final int MAX_VALUE_LENGTH = 8192;
    private static PreferencesFactory factory = Preferences.factory();
    private static Permission prefsPerm = new RuntimePermission("preferences");

    protected Preferences() {
    }

    private static PreferencesFactory factory() {
        Iterator<PreferencesFactory> iterator = ServiceLoader.loadFromSystemProperty(PreferencesFactory.class);
        if (iterator != null) {
            return iterator;
        }
        iterator = ServiceLoader.load(PreferencesFactory.class).iterator();
        if (iterator.hasNext()) {
            return (PreferencesFactory)iterator.next();
        }
        return new FileSystemPreferencesFactory();
    }

    public static void importPreferences(InputStream inputStream) throws IOException, InvalidPreferencesFormatException {
        XmlSupport.importPreferences(inputStream);
    }

    private static String nodeName(Class<?> object) {
        if (!((Class)object).isArray()) {
            int n = ((String)(object = ((Class)object).getName())).lastIndexOf(46);
            if (n < 0) {
                return "/<unnamed>";
            }
            String string = ((String)object).substring(0, n);
            object = new StringBuilder();
            ((StringBuilder)object).append("/");
            ((StringBuilder)object).append(string.replace('.', '/'));
            return ((StringBuilder)object).toString();
        }
        throw new IllegalArgumentException("Arrays have no associated preferences node.");
    }

    public static PreferencesFactory setPreferencesFactory(PreferencesFactory preferencesFactory) {
        PreferencesFactory preferencesFactory2 = factory;
        factory = preferencesFactory;
        return preferencesFactory2;
    }

    public static Preferences systemNodeForPackage(Class<?> class_) {
        return Preferences.systemRoot().node(Preferences.nodeName(class_));
    }

    public static Preferences systemRoot() {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(prefsPerm);
        }
        return factory.systemRoot();
    }

    public static Preferences userNodeForPackage(Class<?> class_) {
        return Preferences.userRoot().node(Preferences.nodeName(class_));
    }

    public static Preferences userRoot() {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(prefsPerm);
        }
        return factory.userRoot();
    }

    public abstract String absolutePath();

    public abstract void addNodeChangeListener(NodeChangeListener var1);

    public abstract void addPreferenceChangeListener(PreferenceChangeListener var1);

    public abstract String[] childrenNames() throws BackingStoreException;

    public abstract void clear() throws BackingStoreException;

    public abstract void exportNode(OutputStream var1) throws IOException, BackingStoreException;

    public abstract void exportSubtree(OutputStream var1) throws IOException, BackingStoreException;

    public abstract void flush() throws BackingStoreException;

    public abstract String get(String var1, String var2);

    public abstract boolean getBoolean(String var1, boolean var2);

    public abstract byte[] getByteArray(String var1, byte[] var2);

    public abstract double getDouble(String var1, double var2);

    public abstract float getFloat(String var1, float var2);

    public abstract int getInt(String var1, int var2);

    public abstract long getLong(String var1, long var2);

    public abstract boolean isUserNode();

    public abstract String[] keys() throws BackingStoreException;

    public abstract String name();

    public abstract Preferences node(String var1);

    public abstract boolean nodeExists(String var1) throws BackingStoreException;

    public abstract Preferences parent();

    public abstract void put(String var1, String var2);

    public abstract void putBoolean(String var1, boolean var2);

    public abstract void putByteArray(String var1, byte[] var2);

    public abstract void putDouble(String var1, double var2);

    public abstract void putFloat(String var1, float var2);

    public abstract void putInt(String var1, int var2);

    public abstract void putLong(String var1, long var2);

    public abstract void remove(String var1);

    public abstract void removeNode() throws BackingStoreException;

    public abstract void removeNodeChangeListener(NodeChangeListener var1);

    public abstract void removePreferenceChangeListener(PreferenceChangeListener var1);

    public abstract void sync() throws BackingStoreException;

    public abstract String toString();
}

