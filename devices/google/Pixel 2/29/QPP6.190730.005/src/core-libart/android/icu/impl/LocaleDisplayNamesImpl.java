/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

import android.icu.impl.CurrencyData;
import android.icu.impl.ICUResourceBundle;
import android.icu.impl.ICUResourceTableAccess;
import android.icu.impl.SimpleFormatterImpl;
import android.icu.impl.UResource;
import android.icu.impl.locale.AsciiUtil;
import android.icu.lang.UCharacter;
import android.icu.lang.UScript;
import android.icu.text.BreakIterator;
import android.icu.text.CaseMap;
import android.icu.text.DisplayContext;
import android.icu.text.LocaleDisplayNames;
import android.icu.util.ULocale;
import android.icu.util.UResourceBundle;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Set;

public class LocaleDisplayNamesImpl
extends LocaleDisplayNames {
    private static final CaseMap.Title TO_TITLE_WHOLE_STRING_NO_LOWERCASE;
    private static final Cache cache;
    private static final Map<String, CapitalizationContextUsage> contextUsageTypeMap;
    private final DisplayContext capitalization;
    private transient BreakIterator capitalizationBrkIter;
    private boolean[] capitalizationUsage;
    private final CurrencyData.CurrencyDisplayInfo currencyDisplayInfo;
    private final LocaleDisplayNames.DialectHandling dialectHandling;
    private final String format;
    private final char formatCloseParen;
    private final char formatOpenParen;
    private final char formatReplaceCloseParen;
    private final char formatReplaceOpenParen;
    private final String keyTypeFormat;
    private final DataTable langData;
    private final ULocale locale;
    private final DisplayContext nameLength;
    private final DataTable regionData;
    private final String separatorFormat;
    private final DisplayContext substituteHandling;

    static {
        cache = new Cache();
        contextUsageTypeMap = new HashMap<String, CapitalizationContextUsage>();
        contextUsageTypeMap.put("languages", CapitalizationContextUsage.LANGUAGE);
        contextUsageTypeMap.put("script", CapitalizationContextUsage.SCRIPT);
        contextUsageTypeMap.put("territory", CapitalizationContextUsage.TERRITORY);
        contextUsageTypeMap.put("variant", CapitalizationContextUsage.VARIANT);
        contextUsageTypeMap.put("key", CapitalizationContextUsage.KEY);
        contextUsageTypeMap.put("keyValue", CapitalizationContextUsage.KEYVALUE);
        TO_TITLE_WHOLE_STRING_NO_LOWERCASE = CaseMap.toTitle().wholeString().noLowercase();
    }

    public LocaleDisplayNamesImpl(ULocale uLocale, LocaleDisplayNames.DialectHandling enum_) {
        enum_ = enum_ == LocaleDisplayNames.DialectHandling.STANDARD_NAMES ? DisplayContext.STANDARD_NAMES : DisplayContext.DIALECT_NAMES;
        this(uLocale, new DisplayContext[]{enum_, DisplayContext.CAPITALIZATION_NONE});
    }

    public LocaleDisplayNamesImpl(ULocale uLocale, DisplayContext ... object) {
        Object object2;
        boolean bl;
        Object object3;
        DisplayContext displayContext;
        block34 : {
            block33 : {
                block32 : {
                    block31 : {
                        block30 : {
                            block29 : {
                                boolean bl2;
                                this.capitalizationUsage = null;
                                this.capitalizationBrkIter = null;
                                Enum enum_ = LocaleDisplayNames.DialectHandling.STANDARD_NAMES;
                                displayContext = DisplayContext.CAPITALIZATION_NONE;
                                DisplayContext displayContext2 = DisplayContext.LENGTH_FULL;
                                object3 = DisplayContext.SUBSTITUTE;
                                int n = ((DisplayContext[])object).length;
                                int n2 = 0;
                                do {
                                    bl2 = true;
                                    if (n2 >= n) break;
                                    object2 = object[n2];
                                    int n3 = 1.$SwitchMap$android$icu$text$DisplayContext$Type[((DisplayContext)((Object)object2)).type().ordinal()];
                                    if (n3 != 1) {
                                        if (n3 != 2) {
                                            if (n3 != 3) {
                                                if (n3 == 4) {
                                                    object3 = object2;
                                                }
                                            } else {
                                                displayContext2 = object2;
                                            }
                                        } else {
                                            displayContext = object2;
                                        }
                                    } else {
                                        object2 = ((DisplayContext)((Object)object2)).value() == DisplayContext.STANDARD_NAMES.value() ? LocaleDisplayNames.DialectHandling.STANDARD_NAMES : LocaleDisplayNames.DialectHandling.DIALECT_NAMES;
                                        enum_ = object2;
                                    }
                                    ++n2;
                                } while (true);
                                this.dialectHandling = enum_;
                                this.capitalization = displayContext;
                                this.nameLength = displayContext2;
                                this.substituteHandling = object3;
                                object = LangDataTables.impl;
                                bl = object3 == DisplayContext.NO_SUBSTITUTE;
                                this.langData = ((DataTables)object).get(uLocale, bl);
                                object = RegionDataTables.impl;
                                bl = object3 == DisplayContext.NO_SUBSTITUTE ? bl2 : false;
                                this.regionData = ((DataTables)object).get(uLocale, bl);
                                object = ULocale.ROOT.equals(this.langData.getLocale()) ? this.regionData.getLocale() : this.langData.getLocale();
                                this.locale = object;
                                object2 = this.langData.get("localeDisplayPattern", "separator");
                                if (object2 == null) break block29;
                                object = object2;
                                if (!"separator".equals(object2)) break block30;
                            }
                            object = "{0}, {1}";
                        }
                        object3 = new StringBuilder();
                        this.separatorFormat = SimpleFormatterImpl.compileToStringMinMaxArguments((CharSequence)object, (StringBuilder)object3, 2, 2);
                        object2 = this.langData.get("localeDisplayPattern", "pattern");
                        if (object2 == null) break block31;
                        object = object2;
                        if (!"pattern".equals(object2)) break block32;
                    }
                    object = "{0} ({1})";
                }
                this.format = SimpleFormatterImpl.compileToStringMinMaxArguments((CharSequence)object, (StringBuilder)object3, 2, 2);
                if (((String)object).contains("\uff08")) {
                    this.formatOpenParen = (char)65288;
                    this.formatCloseParen = (char)65289;
                    this.formatReplaceOpenParen = (char)65339;
                    this.formatReplaceCloseParen = (char)65341;
                } else {
                    this.formatOpenParen = (char)40;
                    this.formatCloseParen = (char)41;
                    this.formatReplaceOpenParen = (char)91;
                    this.formatReplaceCloseParen = (char)93;
                }
                object2 = this.langData.get("localeDisplayPattern", "keyTypePattern");
                if (object2 == null) break block33;
                object = object2;
                if (!"keyTypePattern".equals(object2)) break block34;
            }
            object = "{0}={1}";
        }
        this.keyTypeFormat = SimpleFormatterImpl.compileToStringMinMaxArguments((CharSequence)object, (StringBuilder)object3, 2, 2);
        bl = false;
        if (displayContext == DisplayContext.CAPITALIZATION_FOR_UI_LIST_OR_MENU || displayContext == DisplayContext.CAPITALIZATION_FOR_STANDALONE) {
            this.capitalizationUsage = new boolean[CapitalizationContextUsage.values().length];
            object2 = (ICUResourceBundle)UResourceBundle.getBundleInstance("android/icu/impl/data/icudt63b", uLocale);
            object = new CapitalizationContextSink();
            try {
                ((ICUResourceBundle)object2).getAllItemsWithFallback("contextTransforms", (UResource.Sink)object);
            }
            catch (MissingResourceException missingResourceException) {
                // empty catch block
            }
            bl = ((CapitalizationContextSink)object).hasCapitalizationUsage;
        }
        if (bl || displayContext == DisplayContext.CAPITALIZATION_FOR_BEGINNING_OF_SENTENCE) {
            this.capitalizationBrkIter = BreakIterator.getSentenceInstance(uLocale);
        }
        this.currencyDisplayInfo = CurrencyData.provider.getInstance(uLocale, false);
    }

    static /* synthetic */ boolean[] access$300(LocaleDisplayNamesImpl localeDisplayNamesImpl) {
        return localeDisplayNamesImpl.capitalizationUsage;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private String adjustForUsageAndContext(CapitalizationContextUsage object, String string) {
        if (string == null) return string;
        if (string.length() <= 0) return string;
        if (!UCharacter.isLowerCase(string.codePointAt(0))) return string;
        if (this.capitalization != DisplayContext.CAPITALIZATION_FOR_BEGINNING_OF_SENTENCE) {
            boolean[] arrbl = this.capitalizationUsage;
            if (arrbl == null) return string;
            if (!arrbl[object.ordinal()]) return string;
        }
        synchronized (this) {
            if (this.capitalizationBrkIter != null) return UCharacter.toTitleCase(this.locale, string, this.capitalizationBrkIter, 768);
            this.capitalizationBrkIter = BreakIterator.getSentenceInstance(this.locale);
            return UCharacter.toTitleCase(this.locale, string, this.capitalizationBrkIter, 768);
        }
    }

    private StringBuilder appendWithSep(String string, StringBuilder stringBuilder) {
        if (stringBuilder.length() == 0) {
            stringBuilder.append(string);
        } else {
            SimpleFormatterImpl.formatAndReplace(this.separatorFormat, stringBuilder, null, stringBuilder, string);
        }
        return stringBuilder;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static LocaleDisplayNames getInstance(ULocale object, LocaleDisplayNames.DialectHandling dialectHandling) {
        Cache cache = LocaleDisplayNamesImpl.cache;
        synchronized (cache) {
            return LocaleDisplayNamesImpl.cache.get((ULocale)object, dialectHandling);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static LocaleDisplayNames getInstance(ULocale object, DisplayContext ... arrdisplayContext) {
        Cache cache = LocaleDisplayNamesImpl.cache;
        synchronized (cache) {
            return LocaleDisplayNamesImpl.cache.get((ULocale)object, arrdisplayContext);
        }
    }

    public static boolean haveData(DataTableType dataTableType) {
        int n = 1.$SwitchMap$android$icu$impl$LocaleDisplayNamesImpl$DataTableType[dataTableType.ordinal()];
        if (n != 1) {
            if (n == 2) {
                return RegionDataTables.impl instanceof ICUDataTables;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("unknown type: ");
            stringBuilder.append((Object)dataTableType);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        return LangDataTables.impl instanceof ICUDataTables;
    }

    private String keyDisplayName(String string, boolean bl) {
        string = this.langData.get("Keys", string);
        if (!bl) {
            string = this.adjustForUsageAndContext(CapitalizationContextUsage.KEY, string);
        }
        return string;
    }

    private String keyValueDisplayName(String string, String string2, boolean bl) {
        String string3;
        String string4 = null;
        if (string.equals("currency")) {
            string3 = string = this.currencyDisplayInfo.getName(AsciiUtil.toUpperString(string2));
            if (string == null) {
                string3 = string2;
            }
        } else {
            String string5 = string4;
            if (this.nameLength == DisplayContext.LENGTH_SHORT) {
                string3 = this.langData.get("Types%short", string, string2);
                string5 = string4;
                if (string3 != null) {
                    string5 = string4;
                    if (!string3.equals(string2)) {
                        string5 = string3;
                    }
                }
            }
            string3 = string5;
            if (string5 == null) {
                string3 = this.langData.get("Types", string, string2);
            }
        }
        string = bl ? string3 : this.adjustForUsageAndContext(CapitalizationContextUsage.KEYVALUE, string3);
        return string;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private String localeDisplayNameInternal(ULocale var1_1) {
        block21 : {
            block22 : {
                var2_2 = null;
                var3_6 = var1_1.getLanguage();
                if (var1_1.getBaseName().length() == 0) {
                    var3_6 = "root";
                }
                var4_7 = var1_1.getScript();
                var5_8 = var1_1.getCountry();
                var6_9 = var1_1.getVariant();
                var7_10 = var4_7.length() > 0;
                var8_11 = var5_8.length() > 0;
                var9_12 = var6_9.length() > 0;
                var10_13 = var2_2;
                var11_14 = var7_10;
                var12_15 = var8_11;
                if (this.dialectHandling != LocaleDisplayNames.DialectHandling.DIALECT_NAMES) break block21;
                if (!var7_10 || !var8_11) break block22;
                var10_13 = new StringBuilder();
                var10_13.append(var3_6);
                var10_13.append('_');
                var10_13.append(var4_7);
                var10_13.append('_');
                var10_13.append((String)var5_8);
                var13_16 = var10_13.toString();
                var10_13 = this.localeIdName((String)var13_16);
                if (var10_13 == null || var10_13.equals(var13_16)) break block22;
                var11_14 = false;
                var12_15 = false;
                break block21;
            }
            if (!var7_10) ** GOTO lbl-1000
            var10_13 = new StringBuilder();
            var10_13.append(var3_6);
            var10_13.append('_');
            var10_13.append(var4_7);
            var13_16 = var10_13.toString();
            var10_13 = this.localeIdName((String)var13_16);
            if (var10_13 != null && !var10_13.equals(var13_16)) {
                var11_14 = false;
                var12_15 = var8_11;
            } else lbl-1000: // 2 sources:
            {
                var10_13 = var2_2;
                var11_14 = var7_10;
                var12_15 = var8_11;
                if (var8_11) {
                    var10_13 = new StringBuilder();
                    var10_13.append(var3_6);
                    var10_13.append('_');
                    var10_13.append((String)var5_8);
                    var14_17 = var10_13.toString();
                    var13_16 = this.localeIdName(var14_17);
                    var10_13 = var2_2;
                    var11_14 = var7_10;
                    var12_15 = var8_11;
                    if (var13_16 != null) {
                        var10_13 = var2_2;
                        var11_14 = var7_10;
                        var12_15 = var8_11;
                        if (!var13_16.equals(var14_17)) {
                            var10_13 = var13_16;
                            var12_15 = false;
                            var11_14 = var7_10;
                        }
                    }
                }
            }
        }
        var2_3 = var10_13;
        if (var10_13 == null) {
            var10_13 = this.localeIdName(var3_6);
            if (var10_13 == null) {
                return null;
            }
            var2_4 = var10_13.replace(this.formatOpenParen, this.formatReplaceOpenParen).replace(this.formatCloseParen, this.formatReplaceCloseParen);
        }
        var13_16 = new StringBuilder();
        if (var11_14) {
            var10_13 = this.scriptDisplayNameInContext(var4_7, true);
            if (var10_13 == null) {
                return null;
            }
            var13_16.append(var10_13.replace(this.formatOpenParen, this.formatReplaceOpenParen).replace(this.formatCloseParen, this.formatReplaceCloseParen));
        }
        if (var12_15) {
            var10_13 = this.regionDisplayName((String)var5_8, true);
            if (var10_13 == null) {
                return null;
            }
            this.appendWithSep(var10_13.replace(this.formatOpenParen, this.formatReplaceOpenParen).replace(this.formatCloseParen, this.formatReplaceCloseParen), (StringBuilder)var13_16);
        }
        if (var9_12) {
            var10_13 = this.variantDisplayName((String)var6_9, true);
            if (var10_13 == null) {
                return null;
            }
            this.appendWithSep(var10_13.replace(this.formatOpenParen, this.formatReplaceOpenParen).replace(this.formatCloseParen, this.formatReplaceCloseParen), (StringBuilder)var13_16);
        }
        if ((var6_9 = var1_1.getKeywords()) != null) {
            var10_13 = var5_8;
            while (var6_9.hasNext()) {
                var5_8 = (String)var6_9.next();
                var14_17 = var1_1.getKeywordValue((String)var5_8);
                var15_18 = this.keyDisplayName((String)var5_8, true);
                if (var15_18 == null) {
                    return null;
                }
                var15_18 = var15_18.replace(this.formatOpenParen, this.formatReplaceOpenParen).replace(this.formatCloseParen, this.formatReplaceCloseParen);
                var16_19 = this.keyValueDisplayName((String)var5_8, var14_17, true);
                if (var16_19 == null) {
                    return null;
                }
                if (!(var16_19 = var16_19.replace(this.formatOpenParen, this.formatReplaceOpenParen).replace(this.formatCloseParen, this.formatReplaceCloseParen)).equals(var14_17)) {
                    this.appendWithSep(var16_19, (StringBuilder)var13_16);
                    continue;
                }
                if (!var5_8.equals(var15_18)) {
                    this.appendWithSep(SimpleFormatterImpl.formatCompiledPattern(this.keyTypeFormat, new CharSequence[]{var15_18, var16_19}), (StringBuilder)var13_16);
                    continue;
                }
                var5_8 = this.appendWithSep(var15_18, (StringBuilder)var13_16);
                var5_8.append("=");
                var5_8.append(var16_19);
            }
        }
        var1_1 = null;
        if (var13_16.length() > 0) {
            var1_1 = var13_16.toString();
        }
        var10_13 = var2_5;
        if (var1_1 == null) return this.adjustForUsageAndContext(CapitalizationContextUsage.LANGUAGE, (String)var10_13);
        var10_13 = SimpleFormatterImpl.formatCompiledPattern(this.format, new CharSequence[]{var2_5, var1_1});
        return this.adjustForUsageAndContext(CapitalizationContextUsage.LANGUAGE, (String)var10_13);
    }

    private String localeIdName(String string) {
        String string2;
        if (this.nameLength == DisplayContext.LENGTH_SHORT && (string2 = this.langData.get("Languages%short", string)) != null && !string2.equals(string)) {
            return string2;
        }
        return this.langData.get("Languages", string);
    }

    private LocaleDisplayNames.UiListItem newRow(ULocale uLocale, DisplayContext object) {
        ULocale uLocale2 = ULocale.minimizeSubtags(uLocale, ULocale.Minimize.FAVOR_SCRIPT);
        String string = uLocale.getDisplayName(this.locale);
        boolean bl = object == DisplayContext.CAPITALIZATION_FOR_UI_LIST_OR_MENU;
        if (bl) {
            string = LocaleDisplayNamesImpl.toTitleWholeStringNoLowercase(this.locale, string);
        }
        String string2 = uLocale.getDisplayName(uLocale);
        object = object == DisplayContext.CAPITALIZATION_FOR_UI_LIST_OR_MENU ? LocaleDisplayNamesImpl.toTitleWholeStringNoLowercase(uLocale, string2) : string2;
        return new LocaleDisplayNames.UiListItem(uLocale2, uLocale, string, (String)object);
    }

    private String regionDisplayName(String string, boolean bl) {
        String string2;
        if (this.nameLength == DisplayContext.LENGTH_SHORT && (string2 = this.regionData.get("Countries%short", string)) != null && !string2.equals(string)) {
            string = bl ? string2 : this.adjustForUsageAndContext(CapitalizationContextUsage.TERRITORY, string2);
            return string;
        }
        string = this.regionData.get("Countries", string);
        if (!bl) {
            string = this.adjustForUsageAndContext(CapitalizationContextUsage.TERRITORY, string);
        }
        return string;
    }

    private String scriptDisplayNameInContext(String string, boolean bl) {
        String string2;
        if (this.nameLength == DisplayContext.LENGTH_SHORT && (string2 = this.langData.get("Scripts%short", string)) != null && !string2.equals(string)) {
            string = bl ? string2 : this.adjustForUsageAndContext(CapitalizationContextUsage.SCRIPT, string2);
            return string;
        }
        string = this.langData.get("Scripts", string);
        if (!bl) {
            string = this.adjustForUsageAndContext(CapitalizationContextUsage.SCRIPT, string);
        }
        return string;
    }

    private static String toTitleWholeStringNoLowercase(ULocale uLocale, String string) {
        return TO_TITLE_WHOLE_STRING_NO_LOWERCASE.apply(uLocale.toLocale(), null, string);
    }

    private String variantDisplayName(String string, boolean bl) {
        string = this.langData.get("Variants", string);
        if (!bl) {
            string = this.adjustForUsageAndContext(CapitalizationContextUsage.VARIANT, string);
        }
        return string;
    }

    @Override
    public DisplayContext getContext(DisplayContext.Type enum_) {
        int n = 1.$SwitchMap$android$icu$text$DisplayContext$Type[enum_.ordinal()];
        enum_ = n != 1 ? (n != 2 ? (n != 3 ? (n != 4 ? DisplayContext.STANDARD_NAMES : this.substituteHandling) : this.nameLength) : this.capitalization) : (this.dialectHandling == LocaleDisplayNames.DialectHandling.STANDARD_NAMES ? DisplayContext.STANDARD_NAMES : DisplayContext.DIALECT_NAMES);
        return enum_;
    }

    @Override
    public LocaleDisplayNames.DialectHandling getDialectHandling() {
        return this.dialectHandling;
    }

    @Override
    public ULocale getLocale() {
        return this.locale;
    }

    @Override
    public List<LocaleDisplayNames.UiListItem> getUiListCompareWholeItems(Set<ULocale> object, Comparator<LocaleDisplayNames.UiListItem> comparator) {
        Serializable serializable;
        ULocale uLocale;
        HashSet<ULocale> hashSet;
        DisplayContext displayContext = this.getContext(DisplayContext.Type.CAPITALIZATION);
        ArrayList<LocaleDisplayNames.UiListItem> arrayList = new ArrayList<LocaleDisplayNames.UiListItem>();
        Object object2 = new HashMap();
        Object object3 = new ULocale.Builder();
        Object object4 = object.iterator();
        while (object4.hasNext()) {
            object = object4.next();
            ((ULocale.Builder)object3).setLocale((ULocale)object);
            uLocale = ULocale.addLikelySubtags(object);
            serializable = new ULocale(uLocale.getLanguage());
            hashSet = (Set)object2.get(serializable);
            object = hashSet;
            if (hashSet == null) {
                hashSet = new HashSet<ULocale>();
                object = hashSet;
                object2.put(serializable, hashSet);
            }
            object.add(uLocale);
        }
        hashSet = object2.entrySet().iterator();
        object = object2;
        while (hashSet.hasNext()) {
            object2 = hashSet.next();
            uLocale = (ULocale)object2.getKey();
            object4 = (Set)object2.getValue();
            if (object4.size() == 1) {
                arrayList.add(this.newRow(ULocale.minimizeSubtags((ULocale)object4.iterator().next(), ULocale.Minimize.FAVOR_SCRIPT), displayContext));
                object2 = object3;
                object3 = object;
                object = object2;
            } else {
                object2 = new HashSet<String>();
                serializable = new HashSet();
                uLocale = ULocale.addLikelySubtags(uLocale);
                object2.add(uLocale.getScript());
                serializable.add(uLocale.getCountry());
                Iterator iterator = object4.iterator();
                while (iterator.hasNext()) {
                    uLocale = (ULocale)iterator.next();
                    object2.add(uLocale.getScript());
                    serializable.add(uLocale.getCountry());
                }
                int n = object2.size();
                boolean bl = false;
                n = n > 1 ? 1 : 0;
                if (serializable.size() > 1) {
                    bl = true;
                }
                object2 = object4.iterator();
                while (object2.hasNext()) {
                    object4 = ((ULocale.Builder)object3).setLocale((ULocale)object2.next());
                    if (n == 0) {
                        ((ULocale.Builder)object4).setScript("");
                    }
                    if (!bl) {
                        ((ULocale.Builder)object4).setRegion("");
                    }
                    arrayList.add(this.newRow(((ULocale.Builder)object4).build(), displayContext));
                }
                object2 = object;
                object = object3;
                object3 = object2;
            }
            object2 = object3;
            object3 = object;
            object = object2;
        }
        Collections.sort(arrayList, comparator);
        return arrayList;
    }

    @Override
    public String keyDisplayName(String string) {
        return this.keyDisplayName(string, false);
    }

    @Override
    public String keyValueDisplayName(String string, String string2) {
        return this.keyValueDisplayName(string, string2, false);
    }

    @Override
    public String languageDisplayName(String string) {
        if (!string.equals("root") && string.indexOf(95) == -1) {
            String string2;
            if (this.nameLength == DisplayContext.LENGTH_SHORT && (string2 = this.langData.get("Languages%short", string)) != null && !string2.equals(string)) {
                return this.adjustForUsageAndContext(CapitalizationContextUsage.LANGUAGE, string2);
            }
            return this.adjustForUsageAndContext(CapitalizationContextUsage.LANGUAGE, this.langData.get("Languages", string));
        }
        if (this.substituteHandling != DisplayContext.SUBSTITUTE) {
            string = null;
        }
        return string;
    }

    @Override
    public String localeDisplayName(ULocale uLocale) {
        return this.localeDisplayNameInternal(uLocale);
    }

    @Override
    public String localeDisplayName(String string) {
        return this.localeDisplayNameInternal(new ULocale(string));
    }

    @Override
    public String localeDisplayName(Locale locale) {
        return this.localeDisplayNameInternal(ULocale.forLocale(locale));
    }

    @Override
    public String regionDisplayName(String string) {
        return this.regionDisplayName(string, false);
    }

    @Override
    public String scriptDisplayName(int n) {
        return this.scriptDisplayName(UScript.getShortName(n));
    }

    @Override
    public String scriptDisplayName(String string) {
        String string2;
        block5 : {
            block4 : {
                String string3 = this.langData.get("Scripts%stand-alone", string);
                if (string3 == null) break block4;
                string2 = string3;
                if (!string3.equals(string)) break block5;
            }
            if (this.nameLength == DisplayContext.LENGTH_SHORT && (string2 = this.langData.get("Scripts%short", string)) != null && !string2.equals(string)) {
                return this.adjustForUsageAndContext(CapitalizationContextUsage.SCRIPT, string2);
            }
            string2 = this.langData.get("Scripts", string);
        }
        return this.adjustForUsageAndContext(CapitalizationContextUsage.SCRIPT, string2);
    }

    @Override
    public String scriptDisplayNameInContext(String string) {
        return this.scriptDisplayNameInContext(string, false);
    }

    @Override
    public String variantDisplayName(String string) {
        return this.variantDisplayName(string, false);
    }

    private static class Cache {
        private LocaleDisplayNames cache;
        private DisplayContext capitalization;
        private LocaleDisplayNames.DialectHandling dialectHandling;
        private ULocale locale;
        private DisplayContext nameLength;
        private DisplayContext substituteHandling;

        private Cache() {
        }

        public LocaleDisplayNames get(ULocale uLocale, LocaleDisplayNames.DialectHandling dialectHandling) {
            if (dialectHandling != this.dialectHandling || DisplayContext.CAPITALIZATION_NONE != this.capitalization || DisplayContext.LENGTH_FULL != this.nameLength || DisplayContext.SUBSTITUTE != this.substituteHandling || !uLocale.equals(this.locale)) {
                this.locale = uLocale;
                this.dialectHandling = dialectHandling;
                this.capitalization = DisplayContext.CAPITALIZATION_NONE;
                this.nameLength = DisplayContext.LENGTH_FULL;
                this.substituteHandling = DisplayContext.SUBSTITUTE;
                this.cache = new LocaleDisplayNamesImpl(uLocale, dialectHandling);
            }
            return this.cache;
        }

        public LocaleDisplayNames get(ULocale uLocale, DisplayContext ... arrdisplayContext) {
            Enum enum_ = LocaleDisplayNames.DialectHandling.STANDARD_NAMES;
            DisplayContext displayContext = DisplayContext.CAPITALIZATION_NONE;
            DisplayContext displayContext2 = DisplayContext.LENGTH_FULL;
            Enum enum_2 = DisplayContext.SUBSTITUTE;
            int n = arrdisplayContext.length;
            for (int i = 0; i < n; ++i) {
                Enum enum_3 = arrdisplayContext[i];
                int n2 = 1.$SwitchMap$android$icu$text$DisplayContext$Type[((DisplayContext)enum_3).type().ordinal()];
                if (n2 != 1) {
                    if (n2 != 2) {
                        if (n2 != 3) {
                            if (n2 != 4) continue;
                            enum_2 = enum_3;
                            continue;
                        }
                        displayContext2 = enum_3;
                        continue;
                    }
                    displayContext = enum_3;
                    continue;
                }
                enum_3 = ((DisplayContext)enum_3).value() == DisplayContext.STANDARD_NAMES.value() ? LocaleDisplayNames.DialectHandling.STANDARD_NAMES : LocaleDisplayNames.DialectHandling.DIALECT_NAMES;
                enum_ = enum_3;
            }
            if (enum_ != this.dialectHandling || displayContext != this.capitalization || displayContext2 != this.nameLength || enum_2 != this.substituteHandling || !uLocale.equals(this.locale)) {
                this.locale = uLocale;
                this.dialectHandling = enum_;
                this.capitalization = displayContext;
                this.nameLength = displayContext2;
                this.substituteHandling = enum_2;
                this.cache = new LocaleDisplayNamesImpl(uLocale, arrdisplayContext);
            }
            return this.cache;
        }
    }

    private final class CapitalizationContextSink
    extends UResource.Sink {
        boolean hasCapitalizationUsage = false;

        private CapitalizationContextSink() {
        }

        @Override
        public void put(UResource.Key key, UResource.Value value, boolean bl) {
            UResource.Table table = value.getTable();
            int n = 0;
            while (table.getKeyAndValue(n, key, value)) {
                int n2;
                int[] arrn;
                CapitalizationContextUsage capitalizationContextUsage = (CapitalizationContextUsage)((Object)contextUsageTypeMap.get(key.toString()));
                if (capitalizationContextUsage != null && (arrn = value.getIntVector()).length >= 2 && (n2 = LocaleDisplayNamesImpl.this.capitalization == DisplayContext.CAPITALIZATION_FOR_UI_LIST_OR_MENU ? arrn[0] : arrn[1]) != 0) {
                    LocaleDisplayNamesImpl.access$300((LocaleDisplayNamesImpl)LocaleDisplayNamesImpl.this)[capitalizationContextUsage.ordinal()] = true;
                    this.hasCapitalizationUsage = true;
                }
                ++n;
            }
        }
    }

    private static enum CapitalizationContextUsage {
        LANGUAGE,
        SCRIPT,
        TERRITORY,
        VARIANT,
        KEY,
        KEYVALUE;
        
    }

    public static class DataTable {
        final boolean nullIfNotFound;

        DataTable(boolean bl) {
            this.nullIfNotFound = bl;
        }

        String get(String string, String string2) {
            return this.get(string, null, string2);
        }

        String get(String string, String string2, String string3) {
            block0 : {
                if (!this.nullIfNotFound) break block0;
                string3 = null;
            }
            return string3;
        }

        ULocale getLocale() {
            return ULocale.ROOT;
        }
    }

    public static enum DataTableType {
        LANG,
        REGION;
        
    }

    static abstract class DataTables {
        DataTables() {
        }

        public static DataTables load(String object) {
            try {
                object = (DataTables)Class.forName((String)object).newInstance();
                return object;
            }
            catch (Throwable throwable) {
                return new DataTables(){

                    @Override
                    public DataTable get(ULocale uLocale, boolean bl) {
                        return new DataTable(bl);
                    }
                };
            }
        }

        public abstract DataTable get(ULocale var1, boolean var2);

    }

    static class ICUDataTable
    extends DataTable {
        private final ICUResourceBundle bundle;

        public ICUDataTable(String string, ULocale uLocale, boolean bl) {
            super(bl);
            this.bundle = (ICUResourceBundle)UResourceBundle.getBundleInstance(string, uLocale.getBaseName());
        }

        @Override
        public String get(String string, String string2, String string3) {
            ICUResourceBundle iCUResourceBundle = this.bundle;
            String string4 = this.nullIfNotFound ? null : string3;
            return ICUResourceTableAccess.getTableString(iCUResourceBundle, string, string2, string3, string4);
        }

        @Override
        public ULocale getLocale() {
            return this.bundle.getULocale();
        }
    }

    static abstract class ICUDataTables
    extends DataTables {
        private final String path;

        protected ICUDataTables(String string) {
            this.path = string;
        }

        @Override
        public DataTable get(ULocale uLocale, boolean bl) {
            return new ICUDataTable(this.path, uLocale, bl);
        }
    }

    static class LangDataTables {
        static final DataTables impl = DataTables.load("android.icu.impl.ICULangDataTables");

        LangDataTables() {
        }
    }

    static class RegionDataTables {
        static final DataTables impl = DataTables.load("android.icu.impl.ICURegionDataTables");

        RegionDataTables() {
        }
    }

}

