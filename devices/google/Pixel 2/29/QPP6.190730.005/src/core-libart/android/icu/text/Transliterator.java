/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.impl.ICUResourceBundle;
import android.icu.impl.Utility;
import android.icu.text.AnyTransliterator;
import android.icu.text.BreakTransliterator;
import android.icu.text.CaseFoldTransliterator;
import android.icu.text.CompoundTransliterator;
import android.icu.text.EscapeTransliterator;
import android.icu.text.LowercaseTransliterator;
import android.icu.text.NameUnicodeTransliterator;
import android.icu.text.NormalizationTransliterator;
import android.icu.text.NullTransliterator;
import android.icu.text.RemoveTransliterator;
import android.icu.text.Replaceable;
import android.icu.text.ReplaceableString;
import android.icu.text.RuleBasedTransliterator;
import android.icu.text.StringTransform;
import android.icu.text.TitlecaseTransliterator;
import android.icu.text.TransliteratorIDParser;
import android.icu.text.TransliteratorParser;
import android.icu.text.TransliteratorRegistry;
import android.icu.text.UTF16;
import android.icu.text.UnescapeTransliterator;
import android.icu.text.UnicodeFilter;
import android.icu.text.UnicodeNameTransliterator;
import android.icu.text.UnicodeSet;
import android.icu.text.UppercaseTransliterator;
import android.icu.util.CaseInsensitiveString;
import android.icu.util.ULocale;
import android.icu.util.UResourceBundle;
import java.text.Format;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Objects;
import java.util.ResourceBundle;

public abstract class Transliterator
implements StringTransform {
    static final boolean DEBUG = false;
    public static final int FORWARD = 0;
    static final char ID_DELIM = ';';
    static final char ID_SEP = '-';
    private static final String RB_DISPLAY_NAME_PATTERN = "TransliteratorNamePattern";
    private static final String RB_DISPLAY_NAME_PREFIX = "%Translit%%";
    private static final String RB_RULE_BASED_IDS = "RuleBasedTransliteratorIDs";
    private static final String RB_SCRIPT_DISPLAY_NAME_PREFIX = "%Translit%";
    public static final int REVERSE = 1;
    private static final String ROOT = "root";
    static final char VARIANT_SEP = '/';
    private static Map<CaseInsensitiveString, String> displayNameCache;
    private static TransliteratorRegistry registry;
    private String ID;
    private UnicodeSet filter;
    private int maximumContextLength = 0;

    /*
     * Enabled aggressive block sorting
     */
    static {
        registry = new TransliteratorRegistry();
        displayNameCache = Collections.synchronizedMap(new HashMap());
        Object object = UResourceBundle.getBundleInstance("android/icu/impl/data/icudt63b/translit", ROOT).get(RB_RULE_BASED_IDS);
        int n = ((UResourceBundle)object).getSize();
        int n2 = 0;
        do {
            if (n2 >= n) {
                Transliterator.registerSpecialInverse("Null", "Null", false);
                Transliterator.registerClass("Any-Null", NullTransliterator.class, null);
                RemoveTransliterator.register();
                EscapeTransliterator.register();
                UnescapeTransliterator.register();
                LowercaseTransliterator.register();
                UppercaseTransliterator.register();
                TitlecaseTransliterator.register();
                CaseFoldTransliterator.register();
                UnicodeNameTransliterator.register();
                NameUnicodeTransliterator.register();
                NormalizationTransliterator.register();
                BreakTransliterator.register();
                AnyTransliterator.register();
                return;
            }
            Object object2 = ((UResourceBundle)object).get(n2);
            String string = ((UResourceBundle)object2).getKey();
            if (string.indexOf("-t-") < 0) {
                Object object3 = ((UResourceBundle)object2).get(0);
                object2 = ((UResourceBundle)object3).getKey();
                if (!((String)object2).equals("file") && !((String)object2).equals("internal")) {
                    if (!((String)object2).equals("alias")) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Unknow type: ");
                        ((StringBuilder)object).append((String)object2);
                        throw new RuntimeException(((StringBuilder)object).toString());
                    }
                    object2 = ((UResourceBundle)object3).getString();
                    registry.put(string, (String)object2, true);
                } else {
                    String string2 = ((ResourceBundle)object3).getString("resource");
                    char c = ((String)(object3 = ((ResourceBundle)object3).getString("direction"))).charAt(0);
                    if (c != 'F') {
                        if (c != 'R') {
                            object = new StringBuilder();
                            ((StringBuilder)object).append("Can't parse direction: ");
                            ((StringBuilder)object).append((String)object3);
                            throw new RuntimeException(((StringBuilder)object).toString());
                        }
                        c = '\u0001';
                    } else {
                        c = '\u0000';
                    }
                    registry.put(string, string2, c, ((String)object2).equals("internal") ^ true);
                }
            }
            ++n2;
        } while (true);
    }

    protected Transliterator(String string, UnicodeFilter unicodeFilter) {
        if (string != null) {
            this.ID = string;
            this.setFilter(unicodeFilter);
            return;
        }
        throw new NullPointerException();
    }

    public static final Transliterator createFromRules(String string, String object, int n) {
        TransliteratorParser transliteratorParser = new TransliteratorParser();
        transliteratorParser.parse((String)object, n);
        if (transliteratorParser.idBlockVector.size() == 0 && transliteratorParser.dataVector.size() == 0) {
            object = new NullTransliterator();
        } else if (transliteratorParser.idBlockVector.size() == 0 && transliteratorParser.dataVector.size() == 1) {
            object = new RuleBasedTransliterator(string, transliteratorParser.dataVector.get(0), transliteratorParser.compoundFilter);
        } else if (transliteratorParser.idBlockVector.size() == 1 && transliteratorParser.dataVector.size() == 0) {
            Transliterator transliterator;
            if (transliteratorParser.compoundFilter != null) {
                object = new StringBuilder();
                ((StringBuilder)object).append(transliteratorParser.compoundFilter.toPattern(false));
                ((StringBuilder)object).append(";");
                ((StringBuilder)object).append(transliteratorParser.idBlockVector.get(0));
                transliterator = Transliterator.getInstance(((StringBuilder)object).toString());
            } else {
                transliterator = Transliterator.getInstance(transliteratorParser.idBlockVector.get(0));
            }
            object = transliterator;
            if (transliterator != null) {
                transliterator.setID(string);
                object = transliterator;
            }
        } else {
            Object object2;
            object = new ArrayList();
            int n2 = 1;
            int n3 = Math.max(transliteratorParser.idBlockVector.size(), transliteratorParser.dataVector.size());
            for (n = 0; n < n3; ++n) {
                if (n < transliteratorParser.idBlockVector.size() && ((String)(object2 = transliteratorParser.idBlockVector.get(n))).length() > 0 && !(Transliterator.getInstance((String)object2) instanceof NullTransliterator)) {
                    object.add(Transliterator.getInstance((String)object2));
                }
                int n4 = n2;
                if (n < transliteratorParser.dataVector.size()) {
                    object2 = transliteratorParser.dataVector.get(n);
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("%Pass");
                    stringBuilder.append(n2);
                    object.add(new RuleBasedTransliterator(stringBuilder.toString(), (RuleBasedTransliterator.Data)object2, null));
                    n4 = n2 + 1;
                }
                n2 = n4;
            }
            object2 = new CompoundTransliterator((List<Transliterator>)object, n2 - 1);
            ((Transliterator)object2).setID(string);
            object = object2;
            if (transliteratorParser.compoundFilter != null) {
                ((Transliterator)object2).setFilter(transliteratorParser.compoundFilter);
                object = object2;
            }
        }
        return object;
    }

    private void filteredTransliterate(Replaceable object, Position position, boolean bl, boolean bl2) {
        int n;
        block11 : {
            boolean bl3;
            if (this.filter == null && !bl2) {
                this.handleTransliterate((Replaceable)object, position, bl);
                return;
            }
            int n2 = position.limit;
            boolean bl4 = false;
            do {
                block12 : {
                    int n3;
                    if (this.filter != null) {
                        UnicodeSet unicodeSet;
                        while (position.start < n2 && !(unicodeSet = this.filter).contains(n = object.char32At(position.start))) {
                            position.start += UTF16.getCharCount(n);
                        }
                        position.limit = position.start;
                        while (position.limit < n2 && (unicodeSet = this.filter).contains(n = object.char32At(position.limit))) {
                            position.limit += UTF16.getCharCount(n);
                        }
                    }
                    if (position.start == position.limit) {
                        n = n2;
                        break block11;
                    }
                    bl3 = position.limit < n2 ? false : bl;
                    if (bl2 && bl3) {
                        int n4 = position.start;
                        int n5 = position.limit;
                        int n6 = n5 - n4;
                        int n7 = object.length();
                        object.copy(n4, n5, n7);
                        int n8 = n4;
                        int n9 = n7;
                        n3 = position.start;
                        n = 0;
                        int n10 = 0;
                        do {
                            int n11;
                            if ((n3 += (n11 = UTF16.getCharCount(object.char32At(n3)))) > n5) {
                                n = n7 + n10;
                                n2 += n10;
                                object.replace(n, n + n6, "");
                                position.start = n8;
                                break block12;
                            }
                            n11 = n + n11;
                            position.limit = n3;
                            this.handleTransliterate((Replaceable)object, position, true);
                            int n12 = position.limit - n3;
                            if (position.start != position.limit) {
                                n = n9 + n12 - (position.limit - n8);
                                object.replace(n8, position.limit, "");
                                object.copy(n, n + n11, n8);
                                position.start = n8;
                                position.limit = n3;
                                position.contextLimit -= n12;
                                n = n11;
                                continue;
                            }
                            n = position.start;
                            n9 += n12 + n11;
                            n5 += n12;
                            n10 += n12;
                            n8 = n;
                            n3 = n;
                            n = 0;
                        } while (true);
                    }
                    n3 = position.limit;
                    this.handleTransliterate((Replaceable)object, position, bl3);
                    n = position.limit;
                    if (!bl3 && position.start != position.limit) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("ERROR: Incomplete non-incremental transliteration by ");
                        ((StringBuilder)object).append(this.getID());
                        throw new RuntimeException(((StringBuilder)object).toString());
                    }
                    n2 += n - n3;
                }
                n = n2;
                if (this.filter == null) break block11;
            } while (!bl3);
            n = n2;
        }
        position.limit = n;
    }

    public static final Enumeration<String> getAvailableIDs() {
        return registry.getAvailableIDs();
    }

    public static final Enumeration<String> getAvailableSources() {
        return registry.getAvailableSources();
    }

    public static final Enumeration<String> getAvailableTargets(String string) {
        return registry.getAvailableTargets(string);
    }

    public static final Enumeration<String> getAvailableVariants(String string, String string2) {
        return registry.getAvailableVariants(string, string2);
    }

    static Transliterator getBasicInstance(String object, String string) {
        StringBuffer stringBuffer = new StringBuffer();
        object = registry.get((String)object, stringBuffer);
        if (stringBuffer.length() != 0) {
            object = Transliterator.getInstance(stringBuffer.toString(), 0);
        }
        if (object != null && string != null) {
            ((Transliterator)object).setID(string);
        }
        return object;
    }

    public static final String getDisplayName(String string) {
        return Transliterator.getDisplayName(string, ULocale.getDefault(ULocale.Category.DISPLAY));
    }

    public static String getDisplayName(String object, ULocale arrobject) {
        Object object2 = (ICUResourceBundle)UResourceBundle.getBundleInstance("android/icu/impl/data/icudt63b/translit", (ULocale)arrobject);
        String[] arrstring = TransliteratorIDParser.IDtoSTV((String)object);
        if (arrstring == null) {
            return "";
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(arrstring[0]);
        ((StringBuilder)object).append('-');
        ((StringBuilder)object).append(arrstring[1]);
        arrobject = ((StringBuilder)object).toString();
        object = arrobject;
        if (arrstring[2] != null) {
            object = arrobject;
            if (arrstring[2].length() > 0) {
                object = new StringBuilder();
                ((StringBuilder)object).append((String)arrobject);
                ((StringBuilder)object).append('/');
                ((StringBuilder)object).append(arrstring[2]);
                object = ((StringBuilder)object).toString();
            }
        }
        if ((arrobject = displayNameCache.get(new CaseInsensitiveString((String)object))) != null) {
            return arrobject;
        }
        try {
            arrobject = new StringBuilder();
            arrobject.append(RB_DISPLAY_NAME_PREFIX);
            arrobject.append((String)object);
            object = ((ResourceBundle)object2).getString(arrobject.toString());
            return object;
        }
        catch (MissingResourceException missingResourceException) {
            Object object3 = new MessageFormat(((ResourceBundle)object2).getString(RB_DISPLAY_NAME_PATTERN));
            arrobject = new Object[]{2, arrstring[0], arrstring[1]};
            for (int i = 1; i <= 2; ++i) {
                try {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(RB_SCRIPT_DISPLAY_NAME_PREFIX);
                    stringBuilder.append((String)arrobject[i]);
                    arrobject[i] = ((ResourceBundle)object2).getString(stringBuilder.toString());
                    continue;
                }
                catch (MissingResourceException missingResourceException2) {
                    // empty catch block
                }
            }
            try {
                if (arrstring[2].length() > 0) {
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append(((Format)object3).format(arrobject));
                    ((StringBuilder)object2).append('/');
                    ((StringBuilder)object2).append(arrstring[2]);
                    object3 = ((StringBuilder)object2).toString();
                } else {
                    object3 = ((Format)object3).format(arrobject);
                }
                return object3;
            }
            catch (MissingResourceException missingResourceException3) {
                throw new RuntimeException();
            }
        }
    }

    public static String getDisplayName(String string, Locale locale) {
        return Transliterator.getDisplayName(string, ULocale.forLocale(locale));
    }

    public static final Transliterator getInstance(String string) {
        return Transliterator.getInstance(string, 0);
    }

    public static Transliterator getInstance(String object, int n) {
        StringBuffer stringBuffer = new StringBuffer();
        ArrayList<TransliteratorIDParser.SingleID> arrayList = new ArrayList<TransliteratorIDParser.SingleID>();
        Object object2 = new UnicodeSet[1];
        if (TransliteratorIDParser.parseCompoundID((String)object, n, stringBuffer, arrayList, (UnicodeSet[])object2)) {
            object = TransliteratorIDParser.instantiateList(arrayList);
            object = arrayList.size() <= 1 && stringBuffer.indexOf(";") < 0 ? (Transliterator)object.get(0) : new CompoundTransliterator((List<Transliterator>)object);
            ((Transliterator)object).setID(stringBuffer.toString());
            if (object2[0] != null) {
                ((Transliterator)object).setFilter((UnicodeFilter)object2[0]);
            }
            return object;
        }
        object2 = new StringBuilder();
        ((StringBuilder)object2).append("Invalid ID ");
        ((StringBuilder)object2).append((String)object);
        throw new IllegalArgumentException(((StringBuilder)object2).toString());
    }

    public static void registerAlias(String string, String string2) {
        registry.put(string, string2, true);
    }

    @Deprecated
    public static void registerAny() {
        AnyTransliterator.register();
    }

    public static void registerClass(String string, Class<? extends Transliterator> class_, String string2) {
        registry.put(string, class_, true);
        if (string2 != null) {
            displayNameCache.put(new CaseInsensitiveString(string), string2);
        }
    }

    public static void registerFactory(String string, Factory factory) {
        registry.put(string, factory, true);
    }

    public static void registerInstance(Transliterator transliterator) {
        registry.put(transliterator.getID(), transliterator, true);
    }

    static void registerInstance(Transliterator transliterator, boolean bl) {
        registry.put(transliterator.getID(), transliterator, bl);
    }

    static void registerSpecialInverse(String string, String string2, boolean bl) {
        TransliteratorIDParser.registerSpecialInverse(string, string2, bl);
    }

    public static void unregister(String string) {
        displayNameCache.remove(new CaseInsensitiveString(string));
        registry.remove(string);
    }

    @Deprecated
    public void addSourceTargetSet(UnicodeSet object, UnicodeSet object2, UnicodeSet unicodeSet) {
        object = this.getFilterAsUnicodeSet((UnicodeSet)object);
        object = new UnicodeSet(this.handleGetSourceSet()).retainAll((UnicodeSet)object);
        ((UnicodeSet)object2).addAll((UnicodeSet)object);
        object = ((UnicodeSet)object).iterator();
        while (object.hasNext()) {
            String string;
            object2 = (String)object.next();
            if (((String)object2).equals(string = this.transliterate((String)object2))) continue;
            unicodeSet.addAll(string);
        }
    }

    protected final String baseToRules(boolean bl) {
        if (bl) {
            int n;
            StringBuffer stringBuffer = new StringBuffer();
            String string = this.getID();
            for (int i = 0; i < string.length(); i += UTF16.getCharCount((int)n)) {
                n = UTF16.charAt(string, i);
                if (Utility.escapeUnprintable(stringBuffer, n)) continue;
                UTF16.append(stringBuffer, n);
            }
            stringBuffer.insert(0, "::");
            stringBuffer.append(';');
            return stringBuffer.toString();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("::");
        stringBuilder.append(this.getID());
        stringBuilder.append(';');
        return stringBuilder.toString();
    }

    public void filteredTransliterate(Replaceable replaceable, Position position, boolean bl) {
        this.filteredTransliterate(replaceable, position, bl, false);
    }

    public final void finishTransliteration(Replaceable replaceable, Position position) {
        position.validate(replaceable.length());
        this.filteredTransliterate(replaceable, position, false, true);
    }

    public Transliterator[] getElements() {
        Transliterator[] arrtransliterator;
        if (this instanceof CompoundTransliterator) {
            CompoundTransliterator compoundTransliterator = (CompoundTransliterator)this;
            arrtransliterator = new Transliterator[compoundTransliterator.getCount()];
            for (int i = 0; i < arrtransliterator.length; ++i) {
                arrtransliterator[i] = compoundTransliterator.getTransliterator(i);
            }
        } else {
            arrtransliterator = new Transliterator[]{this};
        }
        return arrtransliterator;
    }

    public final UnicodeFilter getFilter() {
        return this.filter;
    }

    @Deprecated
    public UnicodeSet getFilterAsUnicodeSet(UnicodeSet unicodeSet) {
        if (this.filter == null) {
            return unicodeSet;
        }
        UnicodeSet unicodeSet2 = new UnicodeSet(unicodeSet);
        try {
            unicodeSet = this.filter;
        }
        catch (ClassCastException classCastException) {
            UnicodeSet unicodeSet3 = this.filter;
            unicodeSet = new UnicodeSet();
            unicodeSet3.addMatchSetTo(unicodeSet);
        }
        return unicodeSet2.retainAll(unicodeSet).freeze();
    }

    public final String getID() {
        return this.ID;
    }

    public final Transliterator getInverse() {
        return Transliterator.getInstance(this.ID, 1);
    }

    public final int getMaximumContextLength() {
        return this.maximumContextLength;
    }

    public final UnicodeSet getSourceSet() {
        UnicodeSet unicodeSet = new UnicodeSet();
        this.addSourceTargetSet(this.getFilterAsUnicodeSet(UnicodeSet.ALL_CODE_POINTS), unicodeSet, new UnicodeSet());
        return unicodeSet;
    }

    public UnicodeSet getTargetSet() {
        UnicodeSet unicodeSet = new UnicodeSet();
        this.addSourceTargetSet(this.getFilterAsUnicodeSet(UnicodeSet.ALL_CODE_POINTS), new UnicodeSet(), unicodeSet);
        return unicodeSet;
    }

    protected UnicodeSet handleGetSourceSet() {
        return new UnicodeSet();
    }

    protected abstract void handleTransliterate(Replaceable var1, Position var2, boolean var3);

    public void setFilter(UnicodeFilter unicodeFilter) {
        if (unicodeFilter == null) {
            this.filter = null;
        } else {
            try {
                UnicodeSet unicodeSet = new UnicodeSet((UnicodeSet)unicodeFilter);
                this.filter = unicodeSet.freeze();
            }
            catch (Exception exception) {
                this.filter = new UnicodeSet();
                unicodeFilter.addMatchSetTo(this.filter);
                this.filter.freeze();
            }
        }
    }

    protected final void setID(String string) {
        this.ID = string;
    }

    protected void setMaximumContextLength(int n) {
        if (n >= 0) {
            this.maximumContextLength = n;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid context length ");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public String toRules(boolean bl) {
        return this.baseToRules(bl);
    }

    @Override
    public String transform(String string) {
        return this.transliterate(string);
    }

    public final int transliterate(Replaceable replaceable, int n, int n2) {
        if (n >= 0 && n2 >= n && replaceable.length() >= n2) {
            Position position = new Position(n, n2, n);
            this.filteredTransliterate(replaceable, position, false, true);
            return position.limit;
        }
        return -1;
    }

    public final String transliterate(String object) {
        object = new ReplaceableString((String)object);
        this.transliterate((Replaceable)object);
        return ((ReplaceableString)object).toString();
    }

    public final void transliterate(Replaceable replaceable) {
        this.transliterate(replaceable, 0, replaceable.length());
    }

    public final void transliterate(Replaceable replaceable, Position position) {
        this.transliterate(replaceable, position, null);
    }

    public final void transliterate(Replaceable replaceable, Position position, int n) {
        this.transliterate(replaceable, position, UTF16.valueOf(n));
    }

    public final void transliterate(Replaceable replaceable, Position position, String string) {
        position.validate(replaceable.length());
        if (string != null) {
            replaceable.replace(position.limit, position.limit, string);
            position.limit += string.length();
            position.contextLimit += string.length();
        }
        if (position.limit > 0 && UTF16.isLeadSurrogate(replaceable.charAt(position.limit - 1))) {
            return;
        }
        this.filteredTransliterate(replaceable, position, true, true);
    }

    public static interface Factory {
        public Transliterator getInstance(String var1);
    }

    public static class Position {
        public int contextLimit;
        public int contextStart;
        public int limit;
        public int start;

        public Position() {
            this(0, 0, 0, 0);
        }

        public Position(int n, int n2, int n3) {
            this(n, n2, n3, n2);
        }

        public Position(int n, int n2, int n3, int n4) {
            this.contextStart = n;
            this.contextLimit = n2;
            this.start = n3;
            this.limit = n4;
        }

        public Position(Position position) {
            this.set(position);
        }

        public boolean equals(Object object) {
            boolean bl = object instanceof Position;
            boolean bl2 = false;
            if (bl) {
                object = (Position)object;
                bl = bl2;
                if (this.contextStart == ((Position)object).contextStart) {
                    bl = bl2;
                    if (this.contextLimit == ((Position)object).contextLimit) {
                        bl = bl2;
                        if (this.start == ((Position)object).start) {
                            bl = bl2;
                            if (this.limit == ((Position)object).limit) {
                                bl = true;
                            }
                        }
                    }
                }
                return bl;
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(this.contextStart, this.contextLimit, this.start, this.limit);
        }

        public void set(Position position) {
            this.contextStart = position.contextStart;
            this.contextLimit = position.contextLimit;
            this.start = position.start;
            this.limit = position.limit;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("[cs=");
            stringBuilder.append(this.contextStart);
            stringBuilder.append(", s=");
            stringBuilder.append(this.start);
            stringBuilder.append(", l=");
            stringBuilder.append(this.limit);
            stringBuilder.append(", cl=");
            stringBuilder.append(this.contextLimit);
            stringBuilder.append("]");
            return stringBuilder.toString();
        }

        public final void validate(int n) {
            int n2;
            int n3 = this.contextStart;
            if (n3 >= 0 && (n2 = this.start) >= n3 && (n3 = this.limit) >= n2 && (n2 = this.contextLimit) >= n3 && n >= n2) {
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid Position {cs=");
            stringBuilder.append(this.contextStart);
            stringBuilder.append(", s=");
            stringBuilder.append(this.start);
            stringBuilder.append(", l=");
            stringBuilder.append(this.limit);
            stringBuilder.append(", cl=");
            stringBuilder.append(this.contextLimit);
            stringBuilder.append("}, len=");
            stringBuilder.append(n);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
    }

}

