/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.locale;

import android.icu.impl.ValidIdentifiers;
import android.icu.impl.locale.AsciiUtil;
import android.icu.impl.locale.KeyTypeData;
import android.icu.util.IllformedLocaleException;
import android.icu.util.Output;
import android.icu.util.ULocale;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Pattern;

public class LocaleValidityChecker {
    static final Set<ValidIdentifiers.Datasubtype> REGULAR_ONLY;
    static final Set<String> REORDERING_EXCLUDE;
    static final Set<String> REORDERING_INCLUDE;
    static Pattern SEPARATOR;
    private static final Pattern VALID_X;
    private final boolean allowsDeprecated;
    private final Set<ValidIdentifiers.Datasubtype> datasubtypes;

    static {
        SEPARATOR = Pattern.compile("[-_]");
        VALID_X = Pattern.compile("[a-zA-Z0-9]{2,8}(-[a-zA-Z0-9]{2,8})*");
        REORDERING_INCLUDE = new HashSet<String>(Arrays.asList("space", "punct", "symbol", "currency", "digit", "others", "zzzz"));
        REORDERING_EXCLUDE = new HashSet<String>(Arrays.asList("zinh", "zyyy"));
        REGULAR_ONLY = EnumSet.of(ValidIdentifiers.Datasubtype.regular);
    }

    public LocaleValidityChecker(Set<ValidIdentifiers.Datasubtype> set) {
        this.datasubtypes = EnumSet.copyOf(set);
        this.allowsDeprecated = set.contains((Object)ValidIdentifiers.Datasubtype.deprecated);
    }

    public LocaleValidityChecker(ValidIdentifiers.Datasubtype ... arrdatasubtype) {
        this.datasubtypes = EnumSet.copyOf(Arrays.asList(arrdatasubtype));
        this.allowsDeprecated = this.datasubtypes.contains((Object)ValidIdentifiers.Datasubtype.deprecated);
    }

    private boolean isScriptReorder(String string) {
        string = AsciiUtil.toLowerString(string);
        boolean bl = REORDERING_INCLUDE.contains(string);
        boolean bl2 = true;
        if (bl) {
            return true;
        }
        if (REORDERING_EXCLUDE.contains(string)) {
            return false;
        }
        if (ValidIdentifiers.isValid(ValidIdentifiers.Datatype.script, REGULAR_ONLY, string) == null) {
            bl2 = false;
        }
        return bl2;
    }

    private boolean isSubdivision(ULocale uLocale, String string) {
        String string2;
        int n = string.length();
        int n2 = 3;
        if (n < 3) {
            return false;
        }
        if (string.charAt(0) > '9') {
            n2 = 2;
        }
        String string3 = string.substring(0, n2);
        string = string.substring(string3.length());
        if (ValidIdentifiers.isValid(ValidIdentifiers.Datatype.subdivision, this.datasubtypes, string3, string) == null) {
            return false;
        }
        string = string2 = uLocale.getCountry();
        if (string2.isEmpty()) {
            string = ULocale.addLikelySubtags(uLocale).getCountry();
        }
        return string3.equalsIgnoreCase(string);
    }

    private boolean isValid(ValidIdentifiers.Datatype datatype, String string, Where where) {
        boolean bl = string.isEmpty();
        boolean bl2 = true;
        if (bl) {
            return true;
        }
        if (datatype == ValidIdentifiers.Datatype.variant && "posix".equalsIgnoreCase(string)) {
            return true;
        }
        if (ValidIdentifiers.isValid(datatype, this.datasubtypes, string) == null) {
            bl2 = where == null ? false : where.set(datatype, string);
        }
        return bl2;
    }

    private boolean isValidLocale(String arrstring, Where where) {
        try {
            ULocale.Builder builder = new ULocale.Builder();
            boolean bl = this.isValid(builder.setLanguageTag((String)arrstring).build(), where);
            return bl;
        }
        catch (Exception exception) {
            return where.set(ValidIdentifiers.Datatype.t, exception.getMessage());
        }
        catch (IllformedLocaleException illformedLocaleException) {
            int n = illformedLocaleException.getErrorIndex();
            arrstring = SEPARATOR.split(arrstring.substring(n));
            return where.set(ValidIdentifiers.Datatype.t, arrstring[0]);
        }
    }

    /*
     * WARNING - void declaration
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean isValidU(ULocale serializable, ValidIdentifiers.Datatype datatype, String object, Where where) {
        void var9_14;
        String string = null;
        SpecialCase specialCase = null;
        StringBuilder stringBuilder = new StringBuilder();
        HashSet<void> hashSet = new HashSet<void>();
        if (datatype == ValidIdentifiers.Datatype.t) {
            StringBuilder stringBuilder2 = new StringBuilder();
        } else {
            Object var9_12 = null;
        }
        String[] arrstring = SEPARATOR.split((CharSequence)object);
        int n = arrstring.length;
        int n2 = 0;
        String string2 = "";
        object = string;
        string = string2;
        for (int i = 0; i < n; ++i) {
            String string3 = arrstring[i];
            if (string3.length() == 2 && (var9_14 == null || string3.charAt(1) <= '9')) {
                object = var9_14;
                if (var9_14 != null) {
                    if (var9_14.length() != 0 && !this.isValidLocale(var9_14.toString(), where)) {
                        return false;
                    }
                    object = null;
                }
                if ((string = KeyTypeData.toBcpKey(string3)) == null) {
                    return where.set(datatype, string3);
                }
                if (!this.allowsDeprecated && KeyTypeData.isDeprecated(string)) {
                    return where.set(datatype, string);
                }
                KeyTypeData.ValueType valueType = KeyTypeData.getValueType(string);
                specialCase = SpecialCase.get(string);
                n2 = 0;
                Object object2 = object;
                object = valueType;
                continue;
            }
            if (var9_14 != null) {
                if (var9_14.length() != 0) {
                    var9_14.append('-');
                }
                var9_14.append(string3);
                continue;
            }
            ++n2;
            int n3 = 1.$SwitchMap$android$icu$impl$locale$KeyTypeData$ValueType[((Enum)object).ordinal()];
            Object object3 = object;
            if (n3 != 1) {
                if (n3 != 2) {
                    if (n3 != 3) {
                        object = string3;
                    } else {
                        object = string3;
                        if (n2 == 1) {
                            hashSet.clear();
                            object = string3;
                        }
                    }
                } else if (n2 == 1) {
                    stringBuilder.setLength(0);
                    stringBuilder.append(string3);
                    object = string3;
                } else {
                    stringBuilder.append('-');
                    stringBuilder.append(string3);
                    object = stringBuilder.toString();
                }
            } else {
                object = string3;
                if (n2 > 1) {
                    serializable = new StringBuilder();
                    ((StringBuilder)serializable).append(string);
                    ((StringBuilder)serializable).append("-");
                    ((StringBuilder)serializable).append(string3);
                    return where.set(datatype, ((StringBuilder)serializable).toString());
                }
            }
            n3 = 1.$SwitchMap$android$icu$impl$locale$LocaleValidityChecker$SpecialCase[specialCase.ordinal()];
            if (n3 != 1) {
                if (n3 != 2) {
                    if (n3 != 3) {
                        if (n3 != 4) {
                            if (n3 != 5) {
                                if (KeyTypeData.toBcpType(string, (String)object, new Output<Boolean>(), new Output<Boolean>()) == null) {
                                    serializable = new StringBuilder();
                                    ((StringBuilder)serializable).append(string);
                                    ((StringBuilder)serializable).append("-");
                                    ((StringBuilder)serializable).append((String)object);
                                    return where.set(datatype, ((StringBuilder)serializable).toString());
                                }
                                if (!this.allowsDeprecated && KeyTypeData.isDeprecated(string, (String)object)) {
                                    serializable = new StringBuilder();
                                    ((StringBuilder)serializable).append(string);
                                    ((StringBuilder)serializable).append("-");
                                    ((StringBuilder)serializable).append((String)object);
                                    return where.set(datatype, ((StringBuilder)serializable).toString());
                                }
                            } else {
                                if (((String)object).length() < 6) return where.set(datatype, (String)object);
                                if (!((String)object).endsWith("zzzz")) return where.set(datatype, (String)object);
                                if (!this.isValid(ValidIdentifiers.Datatype.region, ((String)object).substring(0, ((String)object).length() - 4), where)) {
                                    return false;
                                }
                            }
                        } else if (!this.isSubdivision((ULocale)serializable, (String)object)) {
                            serializable = new StringBuilder();
                            ((StringBuilder)serializable).append(string);
                            ((StringBuilder)serializable).append("-");
                            ((StringBuilder)serializable).append((String)object);
                            return where.set(datatype, ((StringBuilder)serializable).toString());
                        }
                    } else {
                        void var15_28;
                        if (((String)object).equals("zzzz")) {
                            String string4 = "others";
                        } else {
                            Object object4 = object;
                        }
                        if (!hashSet.add(var15_28) || !this.isScriptReorder((String)object)) {
                            serializable = new StringBuilder();
                            ((StringBuilder)serializable).append(string);
                            ((StringBuilder)serializable).append("-");
                            ((StringBuilder)serializable).append((String)object);
                            return where.set(datatype, ((StringBuilder)serializable).toString());
                        }
                    }
                } else {
                    try {
                        if (Integer.parseInt((String)object, 16) > 1114111) {
                            serializable = new StringBuilder();
                            ((StringBuilder)serializable).append(string);
                            ((StringBuilder)serializable).append("-");
                            ((StringBuilder)serializable).append((String)object);
                            return where.set(datatype, ((StringBuilder)serializable).toString());
                        }
                    }
                    catch (NumberFormatException numberFormatException) {
                        StringBuilder stringBuilder3 = new StringBuilder();
                        stringBuilder3.append(string);
                        stringBuilder3.append("-");
                        stringBuilder3.append((String)object);
                        return where.set(datatype, stringBuilder3.toString());
                    }
                }
            }
            object = object3;
        }
        if (var9_14 == null) return true;
        if (var9_14.length() == 0) return true;
        if (this.isValidLocale(var9_14.toString(), where)) return true;
        return false;
    }

    public Set<ValidIdentifiers.Datasubtype> getDatasubtypes() {
        return EnumSet.copyOf(this.datasubtypes);
    }

    public boolean isValid(ULocale uLocale, Where where) {
        where.set(null, null);
        String string = uLocale.getLanguage();
        String string2 = uLocale.getScript();
        String object4 = uLocale.getCountry();
        Object object = uLocale.getVariant();
        Object object2 = uLocale.getExtensionKeys();
        if (!this.isValid(ValidIdentifiers.Datatype.language, string, where)) {
            if (string.equals("x")) {
                where.set(null, null);
                return true;
            }
            return false;
        }
        if (!this.isValid(ValidIdentifiers.Datatype.script, string2, where)) {
            return false;
        }
        if (!this.isValid(ValidIdentifiers.Datatype.region, object4, where)) {
            return false;
        }
        if (!((String)object).isEmpty()) {
            for (String string3 : SEPARATOR.split((CharSequence)object)) {
                if (this.isValid(ValidIdentifiers.Datatype.variant, string3, where)) continue;
                return false;
            }
        }
        Iterator<Character> iterator = object2.iterator();
        while (iterator.hasNext()) {
            block10 : {
                object2 = iterator.next();
                try {
                    object = new StringBuilder();
                    ((StringBuilder)object).append(object2);
                    ((StringBuilder)object).append("");
                    object = ValidIdentifiers.Datatype.valueOf(((StringBuilder)object).toString());
                    int n = 1.$SwitchMap$android$icu$impl$ValidIdentifiers$Datatype[((Enum)object).ordinal()];
                    if (n == 1) break block10;
                    if (n != 2 && n != 3) continue;
                }
                catch (Exception exception) {
                    ValidIdentifiers.Datatype datatype = ValidIdentifiers.Datatype.illegal;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(object2);
                    stringBuilder.append("");
                    return where.set(datatype, stringBuilder.toString());
                }
                boolean bl = this.isValidU(uLocale, (ValidIdentifiers.Datatype)((Object)object), uLocale.getExtension(((Character)object2).charValue()), where);
                if (bl) continue;
                return false;
            }
            return true;
        }
        return true;
    }

    static enum SpecialCase {
        normal,
        anything,
        reorder,
        codepoints,
        subdivision,
        rgKey;
        

        static SpecialCase get(String string) {
            if (string.equals("kr")) {
                return reorder;
            }
            if (string.equals("vt")) {
                return codepoints;
            }
            if (string.equals("sd")) {
                return subdivision;
            }
            if (string.equals("rg")) {
                return rgKey;
            }
            if (string.equals("x0")) {
                return anything;
            }
            return normal;
        }
    }

    public static class Where {
        public String codeFailure;
        public ValidIdentifiers.Datatype fieldFailure;

        public boolean set(ValidIdentifiers.Datatype datatype, String string) {
            this.fieldFailure = datatype;
            this.codeFailure = string;
            return false;
        }

        public String toString() {
            CharSequence charSequence;
            if (this.fieldFailure == null) {
                charSequence = "OK";
            } else {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("{");
                ((StringBuilder)charSequence).append((Object)this.fieldFailure);
                ((StringBuilder)charSequence).append(", ");
                ((StringBuilder)charSequence).append(this.codeFailure);
                ((StringBuilder)charSequence).append("}");
                charSequence = ((StringBuilder)charSequence).toString();
            }
            return charSequence;
        }
    }

}

