/*
 * Decompiled with CFR 0.145.
 */
package java.util.logging;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class Level
implements Serializable {
    public static final Level ALL;
    public static final Level CONFIG;
    public static final Level FINE;
    public static final Level FINER;
    public static final Level FINEST;
    public static final Level INFO;
    public static final Level OFF;
    public static final Level SEVERE;
    public static final Level WARNING;
    private static final String defaultBundle = "sun.util.logging.resources.logging";
    private static final long serialVersionUID = -8176160795706313070L;
    private transient Locale cachedLocale;
    private transient String localizedLevelName;
    private final String name;
    private final String resourceBundleName;
    private final int value;

    static {
        OFF = new Level("OFF", Integer.MAX_VALUE, defaultBundle);
        SEVERE = new Level("SEVERE", 1000, defaultBundle);
        WARNING = new Level("WARNING", 900, defaultBundle);
        INFO = new Level("INFO", 800, defaultBundle);
        CONFIG = new Level("CONFIG", 700, defaultBundle);
        FINE = new Level("FINE", 500, defaultBundle);
        FINER = new Level("FINER", 400, defaultBundle);
        FINEST = new Level("FINEST", 300, defaultBundle);
        ALL = new Level("ALL", Integer.MIN_VALUE, defaultBundle);
    }

    protected Level(String string, int n) {
        this(string, n, null);
    }

    protected Level(String string, int n, String string2) {
        this(string, n, string2, true);
    }

    private Level(String string, int n, String string2, boolean bl) {
        if (string != null) {
            this.name = string;
            this.value = n;
            this.resourceBundleName = string2;
            if (string2 != null) {
                string = null;
            }
            this.localizedLevelName = string;
            this.cachedLocale = null;
            if (bl) {
                KnownLevel.add(this);
            }
            return;
        }
        throw new NullPointerException();
    }

    private String computeLocalizedLevelName(Locale object) {
        object = ResourceBundle.getBundle(this.resourceBundleName, (Locale)object, Thread.currentThread().getContextClassLoader());
        String string = ((ResourceBundle)object).getString(this.name);
        if (!defaultBundle.equals(this.resourceBundleName)) {
            return string;
        }
        if (Locale.ROOT.equals(object = ((ResourceBundle)object).getLocale()) || this.name.equals(string.toUpperCase(Locale.ROOT))) {
            object = Locale.ROOT;
        }
        object = Locale.ROOT.equals(object) ? this.name : string.toUpperCase((Locale)object);
        return object;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static Level findLevel(String object) {
        int n;
        if (object == null) throw new NullPointerException();
        Object object2 = KnownLevel.findByName((String)object);
        if (object2 != null) {
            return ((KnownLevel)object2).mirroredLevel;
        }
        try {
            n = Integer.parseInt((String)object);
            KnownLevel knownLevel = KnownLevel.findByValue(n);
            object2 = knownLevel;
            if (knownLevel != null) return ((KnownLevel)object2).mirroredLevel;
        }
        catch (NumberFormatException numberFormatException) {
            object = KnownLevel.findByLocalizedLevelName((String)object);
            if (object == null) return null;
            return ((KnownLevel)object).mirroredLevel;
        }
        new java.util.logging.Level((String)object, n);
        object2 = KnownLevel.findByValue(n);
        return ((KnownLevel)object2).mirroredLevel;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static Level parse(String object) throws IllegalArgumentException {
        synchronized (Level.class) {
            Object object2;
            ((String)object).length();
            Object object3 = KnownLevel.findByName((String)object);
            if (object3 != null) {
                object = ((KnownLevel)object3).levelObject;
                return object;
            }
            int n = Integer.parseInt((String)object);
            object3 = object2 = KnownLevel.findByValue(n);
            if (object2 != null) return ((KnownLevel)object3).levelObject;
            try {
                new java.util.logging.Level((String)object, n);
                object3 = KnownLevel.findByValue(n);
                return ((KnownLevel)object3).levelObject;
            }
            catch (NumberFormatException numberFormatException) {
                Object object4 = KnownLevel.findByLocalizedLevelName((String)object);
                if (object4 != null) {
                    return ((KnownLevel)object4).levelObject;
                }
                object4 = new StringBuilder();
                ((StringBuilder)object4).append("Bad level \"");
                ((StringBuilder)object4).append((String)object);
                ((StringBuilder)object4).append("\"");
                object2 = new IllegalArgumentException(((StringBuilder)object4).toString());
                throw object2;
            }
            finally {
            }
        }
    }

    private Object readResolve() {
        KnownLevel knownLevel = KnownLevel.matches(this);
        if (knownLevel != null) {
            return knownLevel.levelObject;
        }
        return new Level(this.name, this.value, this.resourceBundleName);
    }

    public boolean equals(Object object) {
        boolean bl = false;
        try {
            int n = ((Level)object).value;
            int n2 = this.value;
            if (n == n2) {
                bl = true;
            }
            return bl;
        }
        catch (Exception exception) {
            return false;
        }
    }

    final String getCachedLocalizedLevelName() {
        Locale locale;
        if (this.localizedLevelName != null && (locale = this.cachedLocale) != null && locale.equals(Locale.getDefault())) {
            return this.localizedLevelName;
        }
        if (this.resourceBundleName == null) {
            return this.name;
        }
        return null;
    }

    final String getLevelName() {
        return this.name;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    final String getLocalizedLevelName() {
        synchronized (this) {
            String string = this.getCachedLocalizedLevelName();
            if (string != null) {
                return string;
            }
            Locale locale = Locale.getDefault();
            try {
                this.localizedLevelName = this.computeLocalizedLevelName(locale);
            }
            catch (Exception exception) {
                this.localizedLevelName = this.name;
            }
            this.cachedLocale = locale;
            return this.localizedLevelName;
        }
    }

    public String getLocalizedName() {
        return this.getLocalizedLevelName();
    }

    public String getName() {
        return this.name;
    }

    public String getResourceBundleName() {
        return this.resourceBundleName;
    }

    public int hashCode() {
        return this.value;
    }

    public final int intValue() {
        return this.value;
    }

    public final String toString() {
        return this.name;
    }

    static final class KnownLevel {
        private static Map<Integer, List<KnownLevel>> intToLevels;
        private static Map<String, List<KnownLevel>> nameToLevels;
        final Level levelObject;
        final Level mirroredLevel;

        static {
            nameToLevels = new HashMap<String, List<KnownLevel>>();
            intToLevels = new HashMap<Integer, List<KnownLevel>>();
        }

        KnownLevel(Level level) {
            this.levelObject = level;
            this.mirroredLevel = level.getClass() == Level.class ? level : new Level(level.name, level.value, level.resourceBundleName, false);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        static void add(Level level) {
            synchronized (KnownLevel.class) {
                KnownLevel knownLevel = new KnownLevel(level);
                ArrayList<KnownLevel> arrayList = nameToLevels.get(level.name);
                ArrayList arrayList2 = arrayList;
                if (arrayList == null) {
                    arrayList2 = new ArrayList();
                    nameToLevels.put(level.name, arrayList2);
                }
                arrayList2.add(knownLevel);
                List<KnownLevel> list = intToLevels.get(level.value);
                arrayList2 = list;
                if (list == null) {
                    arrayList2 = new ArrayList();
                    intToLevels.put(level.value, arrayList2);
                }
                arrayList2.add(knownLevel);
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        static KnownLevel findByLocalizedLevelName(String string) {
            synchronized (KnownLevel.class) {
                KnownLevel knownLevel;
                Iterator<List<KnownLevel>> iterator = nameToLevels.values().iterator();
                block2 : do {
                    boolean bl;
                    if (!iterator.hasNext()) {
                        return null;
                    }
                    Iterator<KnownLevel> iterator2 = iterator.next().iterator();
                    do {
                        if (!iterator2.hasNext()) continue block2;
                        knownLevel = iterator2.next();
                    } while (!(bl = string.equals(knownLevel.levelObject.getLocalizedLevelName())));
                    break;
                } while (true);
                return knownLevel;
            }
        }

        static KnownLevel findByName(String object) {
            synchronized (KnownLevel.class) {
                block5 : {
                    object = nameToLevels.get(object);
                    if (object == null) break block5;
                    object = (KnownLevel)object.get(0);
                    return object;
                }
                return null;
                finally {
                }
            }
        }

        static KnownLevel findByValue(int n) {
            synchronized (KnownLevel.class) {
                block5 : {
                    Object object = intToLevels.get(n);
                    if (object == null) break block5;
                    object = object.get(0);
                    return object;
                }
                return null;
                finally {
                }
            }
        }

        static KnownLevel matches(Level level) {
            synchronized (KnownLevel.class) {
                block5 : {
                    Object object = nameToLevels.get(level.name);
                    if (object == null) break block5;
                    Iterator<KnownLevel> iterator = object.iterator();
                    while (iterator.hasNext()) {
                        object = iterator.next();
                        Serializable serializable = ((KnownLevel)object).mirroredLevel;
                        Class<?> class_ = ((KnownLevel)object).levelObject.getClass();
                        if (level.value != serializable.value || level.resourceBundleName != serializable.resourceBundleName && (level.resourceBundleName == null || !level.resourceBundleName.equals(serializable.resourceBundleName)) || class_ != (serializable = level.getClass())) continue;
                    }
                    return object;
                }
                return null;
            }
        }
    }

}

