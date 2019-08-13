/*
 * Decompiled with CFR 0.145.
 */
package android.provider;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.provider.Settings;
import android.text.TextUtils;
import com.android.internal.util.ArrayUtils;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.function.Function;

public class SettingsStringUtil {
    public static final String DELIMITER = ":";

    private SettingsStringUtil() {
    }

    public static abstract class ColonDelimitedSet<T>
    extends HashSet<T> {
        public ColonDelimitedSet(String arrstring) {
            arrstring = TextUtils.split(TextUtils.emptyIfNull((String)arrstring), SettingsStringUtil.DELIMITER);
            int n = arrstring.length;
            for (int i = 0; i < n; ++i) {
                this.add(this.itemFromString(arrstring[i]));
            }
        }

        protected abstract T itemFromString(String var1);

        protected String itemToString(T t) {
            return String.valueOf(t);
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            Iterator<E> iterator = this.iterator();
            if (iterator.hasNext()) {
                stringBuilder.append(this.itemToString(iterator.next()));
                while (iterator.hasNext()) {
                    stringBuilder.append(":");
                    stringBuilder.append(this.itemToString(iterator.next()));
                }
            }
            return stringBuilder.toString();
        }

        public static class OfStrings
        extends ColonDelimitedSet<String> {
            public OfStrings(String string2) {
                super(string2);
            }

            public static String add(String string2, String string3) {
                OfStrings ofStrings = new OfStrings(string2);
                if (ofStrings.contains(string3)) {
                    return string2;
                }
                ofStrings.add(string3);
                return ofStrings.toString();
            }

            public static String addAll(String string2, Collection<String> collection) {
                block0 : {
                    OfStrings ofStrings = new OfStrings(string2);
                    if (!ofStrings.addAll(collection)) break block0;
                    string2 = ofStrings.toString();
                }
                return string2;
            }

            public static boolean contains(String string2, String string3) {
                boolean bl = ArrayUtils.indexOf(TextUtils.split(string2, SettingsStringUtil.DELIMITER), string3) != -1;
                return bl;
            }

            public static String remove(String string2, String string3) {
                OfStrings ofStrings = new OfStrings(string2);
                if (!ofStrings.contains(string3)) {
                    return string2;
                }
                ofStrings.remove(string3);
                return ofStrings.toString();
            }

            @Override
            protected String itemFromString(String string2) {
                return string2;
            }
        }

    }

    public static class ComponentNameSet
    extends ColonDelimitedSet<ComponentName> {
        public ComponentNameSet(String string2) {
            super(string2);
        }

        public static String add(String string2, ComponentName componentName) {
            ComponentNameSet componentNameSet = new ComponentNameSet(string2);
            if (componentNameSet.contains(componentName)) {
                return string2;
            }
            componentNameSet.add(componentName);
            return componentNameSet.toString();
        }

        public static boolean contains(String string2, ComponentName componentName) {
            return ColonDelimitedSet.OfStrings.contains(string2, componentName.flattenToString());
        }

        public static String remove(String string2, ComponentName componentName) {
            ComponentNameSet componentNameSet = new ComponentNameSet(string2);
            if (!componentNameSet.contains(componentName)) {
                return string2;
            }
            componentNameSet.remove(componentName);
            return componentNameSet.toString();
        }

        @Override
        protected ComponentName itemFromString(String string2) {
            return ComponentName.unflattenFromString(string2);
        }

        @Override
        protected String itemToString(ComponentName componentName) {
            return componentName.flattenToString();
        }
    }

    public static class SettingStringHelper {
        private final ContentResolver mContentResolver;
        private final String mSettingName;
        private final int mUserId;

        public SettingStringHelper(ContentResolver contentResolver, String string2, int n) {
            this.mContentResolver = contentResolver;
            this.mUserId = n;
            this.mSettingName = string2;
        }

        public boolean modify(Function<String, String> function) {
            return this.write(function.apply(this.read()));
        }

        public String read() {
            return Settings.Secure.getStringForUser(this.mContentResolver, this.mSettingName, this.mUserId);
        }

        public boolean write(String string2) {
            return Settings.Secure.putStringForUser(this.mContentResolver, this.mSettingName, string2, this.mUserId);
        }
    }

}

