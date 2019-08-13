/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.locale;

import android.icu.impl.locale.AsciiUtil;
import android.icu.impl.locale.LocaleObjectCache;

public final class BaseLocale {
    private static final Cache CACHE = new Cache();
    private static final boolean JDKIMPL = false;
    public static final BaseLocale ROOT = BaseLocale.getInstance("", "", "", "");
    public static final String SEP = "_";
    private volatile transient int _hash = 0;
    private String _language = "";
    private String _region = "";
    private String _script = "";
    private String _variant = "";

    private BaseLocale(String string, String string2, String string3, String string4) {
        if (string != null) {
            this._language = AsciiUtil.toLowerString(string).intern();
        }
        if (string2 != null) {
            this._script = AsciiUtil.toTitleString(string2).intern();
        }
        if (string3 != null) {
            this._region = AsciiUtil.toUpperString(string3).intern();
        }
        if (string4 != null) {
            this._variant = AsciiUtil.toUpperString(string4).intern();
        }
    }

    public static BaseLocale getInstance(String object, String string, String string2, String string3) {
        object = new Key((String)object, string, string2, string3);
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
        if (!(this.hashCode() == ((BaseLocale)object).hashCode() && this._language.equals(((BaseLocale)object)._language) && this._script.equals(((BaseLocale)object)._script) && this._region.equals(((BaseLocale)object)._region) && this._variant.equals(((BaseLocale)object)._variant))) {
            bl = false;
        }
        return bl;
    }

    public String getLanguage() {
        return this._language;
    }

    public String getRegion() {
        return this._region;
    }

    public String getScript() {
        return this._script;
    }

    public String getVariant() {
        return this._variant;
    }

    public int hashCode() {
        int n;
        int n2 = n = this._hash;
        if (n == 0) {
            for (n2 = 0; n2 < this._language.length(); ++n2) {
                n = n * 31 + this._language.charAt(n2);
            }
            for (n2 = 0; n2 < this._script.length(); ++n2) {
                n = n * 31 + this._script.charAt(n2);
            }
            for (n2 = 0; n2 < this._region.length(); ++n2) {
                n = n * 31 + this._region.charAt(n2);
            }
            for (n2 = 0; n2 < this._variant.length(); ++n2) {
                n = n * 31 + this._variant.charAt(n2);
            }
            this._hash = n;
            n2 = n;
        }
        return n2;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (this._language.length() > 0) {
            stringBuilder.append("language=");
            stringBuilder.append(this._language);
        }
        if (this._script.length() > 0) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append(", ");
            }
            stringBuilder.append("script=");
            stringBuilder.append(this._script);
        }
        if (this._region.length() > 0) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append(", ");
            }
            stringBuilder.append("region=");
            stringBuilder.append(this._region);
        }
        if (this._variant.length() > 0) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append(", ");
            }
            stringBuilder.append("variant=");
            stringBuilder.append(this._variant);
        }
        return stringBuilder.toString();
    }

    private static class Cache
    extends LocaleObjectCache<Key, BaseLocale> {
        @Override
        protected BaseLocale createObject(Key key) {
            return new BaseLocale(key._lang, key._scrt, key._regn, key._vart);
        }

        @Override
        protected Key normalizeKey(Key key) {
            return Key.normalize(key);
        }
    }

    private static class Key
    implements Comparable<Key> {
        private volatile int _hash;
        private String _lang = "";
        private String _regn = "";
        private String _scrt = "";
        private String _vart = "";

        public Key(String string, String string2, String string3, String string4) {
            if (string != null) {
                this._lang = string;
            }
            if (string2 != null) {
                this._scrt = string2;
            }
            if (string3 != null) {
                this._regn = string3;
            }
            if (string4 != null) {
                this._vart = string4;
            }
        }

        public static Key normalize(Key key) {
            return new Key(AsciiUtil.toLowerString(key._lang).intern(), AsciiUtil.toTitleString(key._scrt).intern(), AsciiUtil.toUpperString(key._regn).intern(), AsciiUtil.toUpperString(key._vart).intern());
        }

        @Override
        public int compareTo(Key key) {
            int n;
            int n2 = n = AsciiUtil.caseIgnoreCompare(this._lang, key._lang);
            if (n == 0) {
                n2 = n = AsciiUtil.caseIgnoreCompare(this._scrt, key._scrt);
                if (n == 0) {
                    n2 = n = AsciiUtil.caseIgnoreCompare(this._regn, key._regn);
                    if (n == 0) {
                        n2 = AsciiUtil.caseIgnoreCompare(this._vart, key._vart);
                    }
                }
            }
            return n2;
        }

        public boolean equals(Object object) {
            boolean bl = this == object || object instanceof Key && AsciiUtil.caseIgnoreMatch(((Key)object)._lang, this._lang) && AsciiUtil.caseIgnoreMatch(((Key)object)._scrt, this._scrt) && AsciiUtil.caseIgnoreMatch(((Key)object)._regn, this._regn) && AsciiUtil.caseIgnoreMatch(((Key)object)._vart, this._vart);
            return bl;
        }

        public int hashCode() {
            int n;
            int n2 = n = this._hash;
            if (n == 0) {
                for (n2 = 0; n2 < this._lang.length(); ++n2) {
                    n = n * 31 + AsciiUtil.toLower(this._lang.charAt(n2));
                }
                for (n2 = 0; n2 < this._scrt.length(); ++n2) {
                    n = n * 31 + AsciiUtil.toLower(this._scrt.charAt(n2));
                }
                for (n2 = 0; n2 < this._regn.length(); ++n2) {
                    n = n * 31 + AsciiUtil.toLower(this._regn.charAt(n2));
                }
                for (n2 = 0; n2 < this._vart.length(); ++n2) {
                    n = n * 31 + AsciiUtil.toLower(this._vart.charAt(n2));
                }
                this._hash = n;
                n2 = n;
            }
            return n2;
        }
    }

}

