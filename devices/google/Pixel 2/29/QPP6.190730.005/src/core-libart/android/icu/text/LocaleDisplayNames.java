/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.impl.ICUConfig;
import android.icu.lang.UScript;
import android.icu.text.DisplayContext;
import android.icu.util.ULocale;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public abstract class LocaleDisplayNames {
    private static final Method FACTORY_DIALECTHANDLING;
    private static final Method FACTORY_DISPLAYCONTEXT;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static {
        Object object = ICUConfig.get("android.icu.text.LocaleDisplayNames.impl", "android.icu.impl.LocaleDisplayNamesImpl");
        Method method3 = null;
        Method method = null;
        Method method4 = null;
        Method method2 = null;
        Method method5 = method3;
        try {
            object = Class.forName((String)object);
            method5 = method3;
            try {
                method = method3 = ((Class)object).getMethod("getInstance", ULocale.class, DialectHandling.class);
            }
            catch (NoSuchMethodException noSuchMethodException) {
                // empty catch block
            }
            method5 = method;
            try {
                method5 = method3 = ((Class)object).getMethod("getInstance", ULocale.class, DisplayContext[].class);
            }
            catch (NoSuchMethodException noSuchMethodException) {
                method5 = method2;
            }
            method2 = method;
            method = method5;
        }
        catch (ClassNotFoundException classNotFoundException) {
            method = method4;
            method2 = method5;
        }
        FACTORY_DIALECTHANDLING = method2;
        FACTORY_DISPLAYCONTEXT = method;
    }

    @Deprecated
    protected LocaleDisplayNames() {
    }

    public static LocaleDisplayNames getInstance(ULocale uLocale) {
        return LocaleDisplayNames.getInstance(uLocale, DialectHandling.STANDARD_NAMES);
    }

    public static LocaleDisplayNames getInstance(ULocale uLocale, DialectHandling dialectHandling) {
        LastResortLocaleDisplayNames lastResortLocaleDisplayNames = null;
        LastResortLocaleDisplayNames lastResortLocaleDisplayNames2 = null;
        Method method = FACTORY_DIALECTHANDLING;
        LocaleDisplayNames localeDisplayNames = lastResortLocaleDisplayNames;
        if (method != null) {
            try {
                localeDisplayNames = (LocaleDisplayNames)method.invoke(null, new Object[]{uLocale, dialectHandling});
            }
            catch (IllegalAccessException illegalAccessException) {
                localeDisplayNames = lastResortLocaleDisplayNames;
            }
            catch (InvocationTargetException invocationTargetException) {
                localeDisplayNames = lastResortLocaleDisplayNames2;
            }
        }
        lastResortLocaleDisplayNames2 = localeDisplayNames;
        if (localeDisplayNames == null) {
            lastResortLocaleDisplayNames2 = new LastResortLocaleDisplayNames(uLocale, dialectHandling);
        }
        return lastResortLocaleDisplayNames2;
    }

    public static LocaleDisplayNames getInstance(ULocale uLocale, DisplayContext ... arrdisplayContext) {
        LastResortLocaleDisplayNames lastResortLocaleDisplayNames = null;
        LastResortLocaleDisplayNames lastResortLocaleDisplayNames2 = null;
        Method method = FACTORY_DISPLAYCONTEXT;
        LocaleDisplayNames localeDisplayNames = lastResortLocaleDisplayNames;
        if (method != null) {
            try {
                localeDisplayNames = (LocaleDisplayNames)method.invoke(null, new Object[]{uLocale, arrdisplayContext});
            }
            catch (IllegalAccessException illegalAccessException) {
                localeDisplayNames = lastResortLocaleDisplayNames;
            }
            catch (InvocationTargetException invocationTargetException) {
                localeDisplayNames = lastResortLocaleDisplayNames2;
            }
        }
        lastResortLocaleDisplayNames2 = localeDisplayNames;
        if (localeDisplayNames == null) {
            lastResortLocaleDisplayNames2 = new LastResortLocaleDisplayNames(uLocale, arrdisplayContext);
        }
        return lastResortLocaleDisplayNames2;
    }

    public static LocaleDisplayNames getInstance(Locale locale) {
        return LocaleDisplayNames.getInstance(ULocale.forLocale(locale));
    }

    public static LocaleDisplayNames getInstance(Locale locale, DisplayContext ... arrdisplayContext) {
        return LocaleDisplayNames.getInstance(ULocale.forLocale(locale), arrdisplayContext);
    }

    public abstract DisplayContext getContext(DisplayContext.Type var1);

    public abstract DialectHandling getDialectHandling();

    public abstract ULocale getLocale();

    public List<UiListItem> getUiList(Set<ULocale> set, boolean bl, Comparator<Object> comparator) {
        return this.getUiListCompareWholeItems(set, UiListItem.getComparator(comparator, bl));
    }

    public abstract List<UiListItem> getUiListCompareWholeItems(Set<ULocale> var1, Comparator<UiListItem> var2);

    public abstract String keyDisplayName(String var1);

    public abstract String keyValueDisplayName(String var1, String var2);

    public abstract String languageDisplayName(String var1);

    public abstract String localeDisplayName(ULocale var1);

    public abstract String localeDisplayName(String var1);

    public abstract String localeDisplayName(Locale var1);

    public abstract String regionDisplayName(String var1);

    public abstract String scriptDisplayName(int var1);

    public abstract String scriptDisplayName(String var1);

    @Deprecated
    public String scriptDisplayNameInContext(String string) {
        return this.scriptDisplayName(string);
    }

    public abstract String variantDisplayName(String var1);

    public static enum DialectHandling {
        STANDARD_NAMES,
        DIALECT_NAMES;
        
    }

    private static class LastResortLocaleDisplayNames
    extends LocaleDisplayNames {
        private DisplayContext[] contexts;
        private ULocale locale;

        private LastResortLocaleDisplayNames(ULocale object, DialectHandling dialectHandling) {
            this.locale = object;
            object = dialectHandling == DialectHandling.DIALECT_NAMES ? DisplayContext.DIALECT_NAMES : DisplayContext.STANDARD_NAMES;
            this.contexts = new DisplayContext[]{object};
        }

        private LastResortLocaleDisplayNames(ULocale uLocale, DisplayContext ... arrdisplayContext) {
            this.locale = uLocale;
            this.contexts = new DisplayContext[arrdisplayContext.length];
            System.arraycopy(arrdisplayContext, 0, this.contexts, 0, arrdisplayContext.length);
        }

        @Override
        public DisplayContext getContext(DisplayContext.Type type) {
            DisplayContext displayContext;
            DisplayContext displayContext2 = DisplayContext.STANDARD_NAMES;
            DisplayContext[] arrdisplayContext = this.contexts;
            int n = arrdisplayContext.length;
            int n2 = 0;
            do {
                displayContext = displayContext2;
                if (n2 >= n || (displayContext = arrdisplayContext[n2]).type() == type) break;
                ++n2;
            } while (true);
            return displayContext;
        }

        @Override
        public DialectHandling getDialectHandling() {
            Enum enum_;
            DialectHandling dialectHandling = DialectHandling.STANDARD_NAMES;
            DisplayContext[] arrdisplayContext = this.contexts;
            int n = arrdisplayContext.length;
            int n2 = 0;
            do {
                enum_ = dialectHandling;
                if (n2 >= n) break;
                enum_ = arrdisplayContext[n2];
                if (((DisplayContext)enum_).type() == DisplayContext.Type.DIALECT_HANDLING && ((DisplayContext)enum_).value() == DisplayContext.DIALECT_NAMES.ordinal()) {
                    enum_ = DialectHandling.DIALECT_NAMES;
                    break;
                }
                ++n2;
            } while (true);
            return enum_;
        }

        @Override
        public ULocale getLocale() {
            return this.locale;
        }

        @Override
        public List<UiListItem> getUiListCompareWholeItems(Set<ULocale> set, Comparator<UiListItem> comparator) {
            return Collections.emptyList();
        }

        @Override
        public String keyDisplayName(String string) {
            return string;
        }

        @Override
        public String keyValueDisplayName(String string, String string2) {
            return string2;
        }

        @Override
        public String languageDisplayName(String string) {
            return string;
        }

        @Override
        public String localeDisplayName(ULocale uLocale) {
            return uLocale.getName();
        }

        @Override
        public String localeDisplayName(String string) {
            return new ULocale(string).getName();
        }

        @Override
        public String localeDisplayName(Locale locale) {
            return ULocale.forLocale(locale).getName();
        }

        @Override
        public String regionDisplayName(String string) {
            return string;
        }

        @Override
        public String scriptDisplayName(int n) {
            return UScript.getShortName(n);
        }

        @Override
        public String scriptDisplayName(String string) {
            return string;
        }

        @Override
        public String variantDisplayName(String string) {
            return string;
        }
    }

    public static class UiListItem {
        public final ULocale minimized;
        public final ULocale modified;
        public final String nameInDisplayLocale;
        public final String nameInSelf;

        public UiListItem(ULocale uLocale, ULocale uLocale2, String string, String string2) {
            this.minimized = uLocale;
            this.modified = uLocale2;
            this.nameInDisplayLocale = string;
            this.nameInSelf = string2;
        }

        public static Comparator<UiListItem> getComparator(Comparator<Object> comparator, boolean bl) {
            return new UiListItemComparator(comparator, bl);
        }

        public boolean equals(Object object) {
            boolean bl = true;
            if (this == object) {
                return true;
            }
            if (object != null && object instanceof UiListItem) {
                object = (UiListItem)object;
                if (!(this.nameInDisplayLocale.equals(((UiListItem)object).nameInDisplayLocale) && this.nameInSelf.equals(((UiListItem)object).nameInSelf) && this.minimized.equals(((UiListItem)object).minimized) && this.modified.equals(((UiListItem)object).modified))) {
                    bl = false;
                }
                return bl;
            }
            return false;
        }

        public int hashCode() {
            return this.modified.hashCode() ^ this.nameInDisplayLocale.hashCode();
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("{");
            stringBuilder.append(this.minimized);
            stringBuilder.append(", ");
            stringBuilder.append(this.modified);
            stringBuilder.append(", ");
            stringBuilder.append(this.nameInDisplayLocale);
            stringBuilder.append(", ");
            stringBuilder.append(this.nameInSelf);
            stringBuilder.append("}");
            return stringBuilder.toString();
        }

        private static class UiListItemComparator
        implements Comparator<UiListItem> {
            private final Comparator<Object> collator;
            private final boolean useSelf;

            UiListItemComparator(Comparator<Object> comparator, boolean bl) {
                this.collator = comparator;
                this.useSelf = bl;
            }

            @Override
            public int compare(UiListItem uiListItem, UiListItem uiListItem2) {
                int n = this.useSelf ? this.collator.compare(uiListItem.nameInSelf, uiListItem2.nameInSelf) : this.collator.compare(uiListItem.nameInDisplayLocale, uiListItem2.nameInDisplayLocale);
                if (n == 0) {
                    n = uiListItem.modified.compareTo(uiListItem2.modified);
                }
                return n;
            }
        }

    }

}

