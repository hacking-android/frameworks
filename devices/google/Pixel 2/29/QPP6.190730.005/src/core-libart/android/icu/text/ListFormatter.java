/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.impl.ICUCache;
import android.icu.impl.ICUResourceBundle;
import android.icu.impl.SimpleCache;
import android.icu.impl.SimpleFormatterImpl;
import android.icu.util.ICUUncheckedIOException;
import android.icu.util.ULocale;
import android.icu.util.UResourceBundle;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;

public final class ListFormatter {
    static Cache cache = new Cache();
    private final String end;
    private final ULocale locale;
    private final String middle;
    private final String start;
    private final String two;

    @Deprecated
    public ListFormatter(String string, String string2, String string3, String string4) {
        this(ListFormatter.compilePattern(string, new StringBuilder()), ListFormatter.compilePattern(string2, new StringBuilder()), ListFormatter.compilePattern(string3, new StringBuilder()), ListFormatter.compilePattern(string4, new StringBuilder()), null);
    }

    private ListFormatter(String string, String string2, String string3, String string4, ULocale uLocale) {
        this.two = string;
        this.start = string2;
        this.middle = string3;
        this.end = string4;
        this.locale = uLocale;
    }

    private static String compilePattern(String string, StringBuilder stringBuilder) {
        return SimpleFormatterImpl.compileToStringMinMaxArguments(string, stringBuilder, 2, 2);
    }

    public static ListFormatter getInstance() {
        return ListFormatter.getInstance(ULocale.getDefault(ULocale.Category.FORMAT));
    }

    public static ListFormatter getInstance(ULocale uLocale) {
        return ListFormatter.getInstance(uLocale, Style.STANDARD);
    }

    @Deprecated
    public static ListFormatter getInstance(ULocale uLocale, Style style) {
        return cache.get(uLocale, style.getName());
    }

    public static ListFormatter getInstance(Locale locale) {
        return ListFormatter.getInstance(ULocale.forLocale(locale), Style.STANDARD);
    }

    FormattedListBuilder format(Collection<?> object, int n) {
        Iterator<?> iterator = object.iterator();
        int n2 = object.size();
        boolean bl = false;
        boolean bl2 = false;
        boolean bl3 = false;
        if (n2 != 0) {
            if (n2 != 1) {
                if (n2 != 2) {
                    object = iterator.next();
                    bl2 = n == 0;
                    object = new FormattedListBuilder(object, bl2);
                    String string = this.start;
                    Object obj = iterator.next();
                    bl2 = n == 1;
                    ((FormattedListBuilder)object).append(string, obj, bl2);
                    for (int i = 2; i < n2 - 1; ++i) {
                        string = this.middle;
                        obj = iterator.next();
                        bl2 = n == i;
                        ((FormattedListBuilder)object).append(string, obj, bl2);
                    }
                    string = this.end;
                    iterator = iterator.next();
                    bl2 = bl3;
                    if (n == n2 - 1) {
                        bl2 = true;
                    }
                    return ((FormattedListBuilder)object).append(string, iterator, bl2);
                }
                object = iterator.next();
                bl2 = n == 0;
                FormattedListBuilder formattedListBuilder = new FormattedListBuilder(object, bl2);
                object = this.two;
                iterator = iterator.next();
                bl2 = bl;
                if (n == 1) {
                    bl2 = true;
                }
                return formattedListBuilder.append((String)object, iterator, bl2);
            }
            object = iterator.next();
            if (n == 0) {
                bl2 = true;
            }
            return new FormattedListBuilder(object, bl2);
        }
        return new FormattedListBuilder("", false);
    }

    public String format(Collection<?> collection) {
        return this.format(collection, -1).toString();
    }

    public String format(Object ... arrobject) {
        return this.format(Arrays.asList(arrobject));
    }

    @Deprecated
    public ULocale getLocale() {
        return this.locale;
    }

    public String getPatternForNumItems(int n) {
        if (n > 0) {
            ArrayList<String> arrayList = new ArrayList<String>();
            for (int i = 0; i < n; ++i) {
                arrayList.add(String.format("{%d}", i));
            }
            return this.format(arrayList);
        }
        throw new IllegalArgumentException("count must be > 0");
    }

    private static class Cache {
        private final ICUCache<String, ListFormatter> cache = new SimpleCache<String, ListFormatter>();

        private Cache() {
        }

        private static ListFormatter load(ULocale uLocale, String string) {
            ICUResourceBundle iCUResourceBundle = (ICUResourceBundle)UResourceBundle.getBundleInstance("android/icu/impl/data/icudt63b", uLocale);
            StringBuilder stringBuilder = new StringBuilder();
            CharSequence charSequence = new StringBuilder();
            charSequence.append("listPattern/");
            charSequence.append(string);
            charSequence.append("/2");
            charSequence = ListFormatter.compilePattern(iCUResourceBundle.getWithFallback(charSequence.toString()).getString(), stringBuilder);
            CharSequence charSequence2 = new StringBuilder();
            charSequence2.append("listPattern/");
            charSequence2.append(string);
            charSequence2.append("/start");
            charSequence2 = ListFormatter.compilePattern(iCUResourceBundle.getWithFallback(charSequence2.toString()).getString(), stringBuilder);
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("listPattern/");
            stringBuilder2.append(string);
            stringBuilder2.append("/middle");
            String string2 = ListFormatter.compilePattern(iCUResourceBundle.getWithFallback(stringBuilder2.toString()).getString(), stringBuilder);
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append("listPattern/");
            stringBuilder2.append(string);
            stringBuilder2.append("/end");
            return new ListFormatter((String)charSequence, (String)charSequence2, string2, ListFormatter.compilePattern(iCUResourceBundle.getWithFallback(stringBuilder2.toString()).getString(), stringBuilder), uLocale);
        }

        public ListFormatter get(ULocale uLocale, String string) {
            ListFormatter listFormatter;
            String string2 = String.format("%s:%s", uLocale.toString(), string);
            ListFormatter listFormatter2 = listFormatter = this.cache.get(string2);
            if (listFormatter == null) {
                listFormatter2 = Cache.load(uLocale, string);
                this.cache.put(string2, listFormatter2);
            }
            return listFormatter2;
        }
    }

    static class FormattedListBuilder {
        private StringBuilder current;
        private int offset;

        public FormattedListBuilder(Object object, boolean bl) {
            this.current = new StringBuilder(object.toString());
            int n = bl ? 0 : -1;
            this.offset = n;
        }

        private boolean offsetRecorded() {
            boolean bl = this.offset >= 0;
            return bl;
        }

        public FormattedListBuilder append(String string, Object object, boolean bl) {
            Object object2 = !bl && !this.offsetRecorded() ? null : new int[2];
            StringBuilder stringBuilder = this.current;
            SimpleFormatterImpl.formatAndReplace(string, stringBuilder, object2, new CharSequence[]{stringBuilder, object.toString()});
            if (object2 != null) {
                if (object2[0] != -1 && object2[1] != -1) {
                    this.offset = bl ? object2[1] : (this.offset += object2[0]);
                } else {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("{0} or {1} missing from pattern ");
                    ((StringBuilder)object).append(string);
                    throw new IllegalArgumentException(((StringBuilder)object).toString());
                }
            }
            return this;
        }

        public void appendTo(Appendable appendable) {
            try {
                appendable.append(this.current);
                return;
            }
            catch (IOException iOException) {
                throw new ICUUncheckedIOException(iOException);
            }
        }

        public int getOffset() {
            return this.offset;
        }

        public String toString() {
            return this.current.toString();
        }
    }

    @Deprecated
    public static enum Style {
        STANDARD("standard"),
        DURATION("unit"),
        DURATION_SHORT("unit-short"),
        DURATION_NARROW("unit-narrow");
        
        private final String name;

        private Style(String string2) {
            this.name = string2;
        }

        @Deprecated
        public String getName() {
            return this.name;
        }
    }

}

