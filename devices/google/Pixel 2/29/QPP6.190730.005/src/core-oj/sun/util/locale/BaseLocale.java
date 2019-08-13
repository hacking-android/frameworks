/*
 * Decompiled with CFR 0.145.
 */
package sun.util.locale;

import java.lang.ref.SoftReference;
import sun.util.locale.LocaleObjectCache;
import sun.util.locale.LocaleUtils;

public final class BaseLocale {
    private static final Cache CACHE = new Cache();
    public static final String SEP = "_";
    private volatile int hash = 0;
    private final String language;
    private final String region;
    private final String script;
    private final String variant;

    private BaseLocale(String string, String string2) {
        this.language = string;
        this.script = "";
        this.region = string2;
        this.variant = "";
    }

    private BaseLocale(String string, String string2, String string3, String string4) {
        String string5 = "";
        string = string != null ? LocaleUtils.toLowerString(string).intern() : "";
        this.language = string;
        string = string2 != null ? LocaleUtils.toTitleString(string2).intern() : "";
        this.script = string;
        string = string3 != null ? LocaleUtils.toUpperString(string3).intern() : "";
        this.region = string;
        string = string5;
        if (string4 != null) {
            string = string4.intern();
        }
        this.variant = string;
    }

    public static BaseLocale createInstance(String string, String string2) {
        BaseLocale baseLocale = new BaseLocale(string, string2);
        CACHE.put(new Key(string, string2), baseLocale);
        return baseLocale;
    }

    public static BaseLocale getInstance(String object, String string, String string2, String string3) {
        Object object2 = object;
        if (object != null) {
            if (LocaleUtils.caseIgnoreMatch((String)object, "he")) {
                object2 = "iw";
            } else if (LocaleUtils.caseIgnoreMatch((String)object, "yi")) {
                object2 = "ji";
            } else {
                object2 = object;
                if (LocaleUtils.caseIgnoreMatch((String)object, "id")) {
                    object2 = "in";
                }
            }
        }
        object = new Key((String)object2, string, string2, string3);
        return (BaseLocale)CACHE.get(object);
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (!(object instanceof BaseLocale)) {
            return false;
        }
        object = (BaseLocale)object;
        if (this.language != ((BaseLocale)object).language || this.script != ((BaseLocale)object).script || this.region != ((BaseLocale)object).region || this.variant != ((BaseLocale)object).variant) {
            bl = false;
        }
        return bl;
    }

    public String getLanguage() {
        return this.language;
    }

    public String getRegion() {
        return this.region;
    }

    public String getScript() {
        return this.script;
    }

    public String getVariant() {
        return this.variant;
    }

    public int hashCode() {
        int n;
        int n2 = n = this.hash;
        if (n == 0) {
            this.hash = n2 = ((this.language.hashCode() * 31 + this.script.hashCode()) * 31 + this.region.hashCode()) * 31 + this.variant.hashCode();
        }
        return n2;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (this.language.length() > 0) {
            stringBuilder.append("language=");
            stringBuilder.append(this.language);
        }
        if (this.script.length() > 0) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append(", ");
            }
            stringBuilder.append("script=");
            stringBuilder.append(this.script);
        }
        if (this.region.length() > 0) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append(", ");
            }
            stringBuilder.append("region=");
            stringBuilder.append(this.region);
        }
        if (this.variant.length() > 0) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append(", ");
            }
            stringBuilder.append("variant=");
            stringBuilder.append(this.variant);
        }
        return stringBuilder.toString();
    }

    private static class Cache
    extends LocaleObjectCache<Key, BaseLocale> {
        static final /* synthetic */ boolean $assertionsDisabled = false;

        @Override
        protected BaseLocale createObject(Key key) {
            return new BaseLocale((String)key.lang.get(), (String)key.scrt.get(), (String)key.regn.get(), (String)key.vart.get());
        }

        @Override
        protected Key normalizeKey(Key key) {
            return Key.normalize(key);
        }
    }

    private static final class Key {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private final int hash;
        private final SoftReference<String> lang;
        private final boolean normalized;
        private final SoftReference<String> regn;
        private final SoftReference<String> scrt;
        private final SoftReference<String> vart;

        private Key(String string, String string2) {
            int n;
            this.lang = new SoftReference<String>(string);
            this.scrt = new SoftReference<String>("");
            this.regn = new SoftReference<String>(string2);
            this.vart = new SoftReference<String>("");
            this.normalized = true;
            int n2 = n = string.hashCode();
            if (string2 != "") {
                int n3 = string2.length();
                int n4 = 0;
                do {
                    n2 = n;
                    if (n4 >= n3) break;
                    n = n * 31 + LocaleUtils.toLower(string2.charAt(n4));
                    ++n4;
                } while (true);
            }
            this.hash = n2;
        }

        public Key(String string, String string2, String string3, String string4) {
            this(string, string2, string3, string4, false);
        }

        private Key(String string, String string2, String string3, String string4, boolean bl) {
            int n;
            int n2 = 0;
            int n3 = 0;
            if (string != null) {
                this.lang = new SoftReference<String>(string);
                n = string.length();
                for (n2 = 0; n2 < n; ++n2) {
                    n3 = n3 * 31 + LocaleUtils.toLower(string.charAt(n2));
                }
            } else {
                this.lang = new SoftReference<String>("");
                n3 = n2;
            }
            if (string2 != null) {
                this.scrt = new SoftReference<String>(string2);
                n = string2.length();
                for (n2 = 0; n2 < n; ++n2) {
                    n3 = n3 * 31 + LocaleUtils.toLower(string2.charAt(n2));
                }
            } else {
                this.scrt = new SoftReference<String>("");
            }
            if (string3 != null) {
                this.regn = new SoftReference<String>(string3);
                n = string3.length();
                for (n2 = 0; n2 < n; ++n2) {
                    n3 = n3 * 31 + LocaleUtils.toLower(string3.charAt(n2));
                }
            } else {
                this.regn = new SoftReference<String>("");
            }
            if (string4 != null) {
                this.vart = new SoftReference<String>(string4);
                n = string4.length();
                for (n2 = 0; n2 < n; ++n2) {
                    n3 = n3 * 31 + string4.charAt(n2);
                }
            } else {
                this.vart = new SoftReference<String>("");
            }
            this.hash = n3;
            this.normalized = bl;
        }

        public static Key normalize(Key key) {
            if (key.normalized) {
                return key;
            }
            return new Key(LocaleUtils.toLowerString(key.lang.get()).intern(), LocaleUtils.toTitleString(key.scrt.get()).intern(), LocaleUtils.toUpperString(key.regn.get()).intern(), key.vart.get().intern(), true);
        }

        public boolean equals(Object object) {
            boolean bl = true;
            if (this == object) {
                return true;
            }
            if (object instanceof Key && this.hash == ((Key)object).hash) {
                String string = this.lang.get();
                String string2 = ((Key)object).lang.get();
                if (string != null && string2 != null && LocaleUtils.caseIgnoreMatch(string2, string)) {
                    string = this.scrt.get();
                    string2 = ((Key)object).scrt.get();
                    if (string != null && string2 != null && LocaleUtils.caseIgnoreMatch(string2, string)) {
                        string = this.regn.get();
                        string2 = ((Key)object).regn.get();
                        if (string != null && string2 != null && LocaleUtils.caseIgnoreMatch(string2, string)) {
                            string = this.vart.get();
                            object = ((Key)object).vart.get();
                            if (object == null || !((String)object).equals(string)) {
                                bl = false;
                            }
                            return bl;
                        }
                    }
                }
            }
            return false;
        }

        public int hashCode() {
            return this.hash;
        }
    }

}

