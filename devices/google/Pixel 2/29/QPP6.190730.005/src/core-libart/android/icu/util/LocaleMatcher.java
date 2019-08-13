/*
 * Decompiled with CFR 0.145.
 */
package android.icu.util;

import android.icu.impl.ICUResourceBundle;
import android.icu.impl.Relation;
import android.icu.impl.Row;
import android.icu.impl.locale.XLocaleDistance;
import android.icu.impl.locale.XLocaleMatcher;
import android.icu.util.Freezable;
import android.icu.util.ICUCloneNotSupportedException;
import android.icu.util.ICUException;
import android.icu.util.LocalePriorityList;
import android.icu.util.Output;
import android.icu.util.ULocale;
import android.icu.util.UResourceBundle;
import android.icu.util.UResourceBundleIterator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LocaleMatcher {
    @Deprecated
    public static final boolean DEBUG = false;
    private static final double DEFAULT_THRESHOLD = 0.5;
    private static final ULocale UNKNOWN_LOCALE = new ULocale("und");
    private static HashMap<String, String> canonicalMap = new HashMap();
    private static final LanguageMatcherData defaultWritten;
    private final ULocale defaultLanguage;
    Map<String, Set<Row.R3<ULocale, ULocale, Double>>> desiredLanguageToPossibleLocalesToMaxLocaleToData = new LinkedHashMap<String, Set<Row.R3<ULocale, ULocale, Double>>>();
    LocalePriorityList languagePriorityList;
    Set<Row.R3<ULocale, ULocale, Double>> localeToMaxLocaleAndWeight = new LinkedHashSet<Row.R3<ULocale, ULocale, Double>>();
    LanguageMatcherData matcherData;
    private final double threshold;
    transient ULocale xDefaultLanguage;
    transient boolean xFavorScript;
    transient XLocaleMatcher xLocaleMatcher;

    static {
        canonicalMap.put("iw", "he");
        canonicalMap.put("mo", "ro");
        canonicalMap.put("tl", "fil");
        ICUResourceBundle iCUResourceBundle = (ICUResourceBundle)LocaleMatcher.getICUSupplementalData().findTopLevel("languageMatching").get("written");
        defaultWritten = new LanguageMatcherData();
        UResourceBundleIterator uResourceBundleIterator = iCUResourceBundle.getIterator();
        while (uResourceBundleIterator.hasNext()) {
            iCUResourceBundle = (ICUResourceBundle)uResourceBundleIterator.next();
            boolean bl = iCUResourceBundle.getSize() > 3 && "1".equals(iCUResourceBundle.getString(3));
            defaultWritten.addDistance(iCUResourceBundle.getString(0), iCUResourceBundle.getString(1), Integer.parseInt(iCUResourceBundle.getString(2)), bl);
        }
        defaultWritten.freeze();
    }

    public LocaleMatcher(LocalePriorityList localePriorityList) {
        this(localePriorityList, defaultWritten);
    }

    @Deprecated
    public LocaleMatcher(LocalePriorityList localePriorityList, LanguageMatcherData languageMatcherData) {
        this(localePriorityList, languageMatcherData, 0.5);
    }

    @Deprecated
    public LocaleMatcher(LocalePriorityList object, LanguageMatcherData object2, double d) {
        Object var5_4 = null;
        this.xLocaleMatcher = null;
        this.xDefaultLanguage = null;
        this.xFavorScript = false;
        object2 = object2 == null ? defaultWritten : ((LanguageMatcherData)object2).freeze();
        this.matcherData = object2;
        this.languagePriorityList = object;
        Iterator<ULocale> iterator = ((LocalePriorityList)object).iterator();
        while (iterator.hasNext()) {
            object2 = iterator.next();
            this.add((ULocale)object2, ((LocalePriorityList)object).getWeight((ULocale)object2));
        }
        this.processMapping();
        object2 = ((LocalePriorityList)object).iterator();
        object = var5_4;
        if (object2.hasNext()) {
            object = object2.next();
        }
        this.defaultLanguage = object;
        this.threshold = d;
    }

    public LocaleMatcher(String string) {
        this(LocalePriorityList.add(string).build());
    }

    private void add(ULocale comparable, Double d) {
        comparable = this.canonicalize((ULocale)comparable);
        comparable = Row.of(comparable, this.addLikelySubtags((ULocale)comparable), d);
        ((Row)comparable).freeze();
        this.localeToMaxLocaleAndWeight.add((Row.R3<ULocale, ULocale, Double>)comparable);
    }

    private void addFiltered(String string, Row.R3<ULocale, ULocale, Double> r3) {
        Set<Row.R3<ULocale, ULocale, Double>> set;
        Set<Row.R3<ULocale, ULocale, Double>> set2 = set = this.desiredLanguageToPossibleLocalesToMaxLocaleToData.get(string);
        if (set == null) {
            Map<String, Set<Row.R3<ULocale, ULocale, Double>>> map = this.desiredLanguageToPossibleLocalesToMaxLocaleToData;
            set2 = set = new LinkedHashSet<Row.R3<ULocale, ULocale, Double>>();
            map.put(string, set);
        }
        set2.add(r3);
    }

    private ULocale addLikelySubtags(ULocale object) {
        if (((ULocale)object).equals(UNKNOWN_LOCALE)) {
            return UNKNOWN_LOCALE;
        }
        Object object2 = ULocale.addLikelySubtags((ULocale)object);
        if (object2 != null && !((ULocale)object2).equals(object)) {
            return object2;
        }
        String string = ((ULocale)object).getLanguage();
        String string2 = ((ULocale)object).getScript();
        object2 = ((ULocale)object).getCountry();
        StringBuilder stringBuilder = new StringBuilder();
        object = string.length() == 0 ? "und" : string;
        stringBuilder.append((String)object);
        stringBuilder.append("_");
        object = string2.length() == 0 ? "Zzzz" : string2;
        stringBuilder.append((String)object);
        stringBuilder.append("_");
        object = ((String)object2).length() == 0 ? "ZZ" : object2;
        stringBuilder.append((String)object);
        return new ULocale(stringBuilder.toString());
    }

    private ULocale getBestMatchInternal(ULocale uLocale, OutputDouble outputDouble) {
        ULocale uLocale2 = this.canonicalize(uLocale);
        ULocale uLocale3 = this.addLikelySubtags(uLocale2);
        double d = 0.0;
        uLocale = null;
        ULocale uLocale4 = null;
        Iterator iterator = uLocale3.getLanguage();
        iterator = this.desiredLanguageToPossibleLocalesToMaxLocaleToData.get(iterator);
        double d2 = d;
        if (iterator != null) {
            iterator = iterator.iterator();
            do {
                d2 = d;
                uLocale = uLocale4;
                if (!iterator.hasNext()) break;
                Comparable comparable = (Row.R3)iterator.next();
                uLocale = (ULocale)((Row)comparable).get0();
                ULocale uLocale5 = (ULocale)((Row)comparable).get1();
                comparable = (Double)((Row)comparable).get2();
                d2 = this.match(uLocale2, uLocale3, uLocale, uLocale5);
                double d3 = (Double)comparable * d2;
                d2 = d;
                if (d3 > d) {
                    d2 = d = d3;
                    uLocale4 = uLocale;
                    if (d3 > 0.999) {
                        d2 = d;
                        break;
                    }
                }
                d = d2;
            } while (true);
        }
        if (d2 < this.threshold) {
            uLocale = this.defaultLanguage;
        }
        if (outputDouble != null) {
            outputDouble.value = d2;
        }
        return uLocale;
    }

    @Deprecated
    public static ICUResourceBundle getICUSupplementalData() {
        return (ICUResourceBundle)UResourceBundle.getBundleInstance("android/icu/impl/data/icudt63b", "supplementalData", ICUResourceBundle.ICU_DATA_CLASS_LOADER);
    }

    private XLocaleMatcher getLocaleMatcher() {
        synchronized (this) {
            Object object;
            if (this.xLocaleMatcher == null) {
                object = XLocaleMatcher.builder();
                ((XLocaleMatcher.Builder)object).setSupportedLocales(this.languagePriorityList);
                if (this.xDefaultLanguage != null) {
                    ((XLocaleMatcher.Builder)object).setDefaultLanguage(this.xDefaultLanguage);
                }
                if (this.xFavorScript) {
                    ((XLocaleMatcher.Builder)object).setDistanceOption(XLocaleDistance.DistanceOption.SCRIPT_FIRST);
                }
                this.xLocaleMatcher = ((XLocaleMatcher.Builder)object).build();
            }
            object = this.xLocaleMatcher;
            return object;
        }
    }

    @Deprecated
    public static double match(ULocale uLocale, ULocale uLocale2) {
        LocaleMatcher localeMatcher = new LocaleMatcher("");
        return localeMatcher.match(uLocale, localeMatcher.addLikelySubtags(uLocale), uLocale2, localeMatcher.addLikelySubtags(uLocale2));
    }

    private void processMapping() {
        for (Map.Entry<String, Set<String>> entry : this.matcherData.matchingLanguages().keyValuesSet()) {
            String string = entry.getKey();
            Set<String> set = entry.getValue();
            for (Row.R3<ULocale, ULocale, Double> r3 : this.localeToMaxLocaleAndWeight) {
                if (!set.contains(((ULocale)r3.get0()).getLanguage())) continue;
                this.addFiltered(string, r3);
            }
        }
        for (Row.R3 r3 : this.localeToMaxLocaleAndWeight) {
            this.addFiltered(((ULocale)r3.get0()).getLanguage(), r3);
        }
    }

    public ULocale canonicalize(ULocale object) {
        String string = ((ULocale)object).getLanguage();
        String string2 = canonicalMap.get(string);
        String string3 = ((ULocale)object).getScript();
        String string4 = canonicalMap.get(string3);
        String string5 = ((ULocale)object).getCountry();
        String string6 = canonicalMap.get(string5);
        if (string2 == null && string4 == null && string6 == null) {
            return object;
        }
        object = string2 == null ? string : string2;
        if (string4 == null) {
            string4 = string3;
        }
        if (string6 == null) {
            string6 = string5;
        }
        return new ULocale((String)object, string4, string6);
    }

    @Deprecated
    public int distance(ULocale uLocale, ULocale uLocale2) {
        return this.getLocaleMatcher().distance(uLocale, uLocale2);
    }

    public ULocale getBestMatch(LocalePriorityList localePriorityList) {
        double d = 0.0;
        ULocale uLocale = null;
        double d2 = 0.0;
        OutputDouble outputDouble = new OutputDouble();
        for (ULocale uLocale2 : localePriorityList) {
            ULocale uLocale3 = this.getBestMatchInternal(uLocale2, outputDouble);
            double d3 = outputDouble.value * localePriorityList.getWeight(uLocale2) - d2;
            double d4 = d;
            if (d3 > d) {
                d4 = d3;
                uLocale = uLocale3;
            }
            d2 += 0.07000001;
            d = d4;
        }
        if (d < this.threshold) {
            uLocale = this.defaultLanguage;
        }
        return uLocale;
    }

    public ULocale getBestMatch(ULocale uLocale) {
        return this.getBestMatchInternal(uLocale, null);
    }

    public ULocale getBestMatch(String string) {
        return this.getBestMatch(LocalePriorityList.add(string).build());
    }

    @Deprecated
    public ULocale getBestMatch(LinkedHashSet<ULocale> linkedHashSet, Output<ULocale> output) {
        return this.getLocaleMatcher().getBestMatch(linkedHashSet, output);
    }

    @Deprecated
    public ULocale getBestMatch(ULocale ... arruLocale) {
        return this.getBestMatch(LocalePriorityList.add(arruLocale).build());
    }

    public double match(ULocale uLocale, ULocale uLocale2, ULocale uLocale3, ULocale uLocale4) {
        return this.matcherData.match(uLocale, uLocale2, uLocale3, uLocale4);
    }

    @Deprecated
    public LocaleMatcher setDefaultLanguage(ULocale uLocale) {
        synchronized (this) {
            this.xDefaultLanguage = uLocale;
            this.xLocaleMatcher = null;
            return this;
        }
    }

    @Deprecated
    public LocaleMatcher setFavorScript(boolean bl) {
        synchronized (this) {
            this.xFavorScript = bl;
            this.xLocaleMatcher = null;
            return this;
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(this.defaultLanguage);
        stringBuilder.append(", ");
        stringBuilder.append(this.localeToMaxLocaleAndWeight);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Deprecated
    public static class LanguageMatcherData
    implements Freezable<LanguageMatcherData> {
        private volatile boolean frozen = false;
        private ScoreData languageScores = new ScoreData(Level.language);
        private Relation<String, String> matchingLanguages;
        private ScoreData regionScores = new ScoreData(Level.region);
        private ScoreData scriptScores = new ScoreData(Level.script);

        private LanguageMatcherData addDistance(String object, String object2, int n, boolean bl, String object3) {
            double d = 1.0 - (double)n / 100.0;
            Object object4 = new LocalePatternMatcher((String)object);
            Level level = ((LocalePatternMatcher)object4).getLevel();
            if (level == ((LocalePatternMatcher)(object3 = new LocalePatternMatcher((String)object2))).getLevel()) {
                object2 = Row.of(object4, object3, d);
                object = bl ? null : Row.of(object3, object4, d);
                boolean bl2 = ((LocalePatternMatcher)object4).equals(object3);
                n = 1.$SwitchMap$android$icu$util$LocaleMatcher$Level[level.ordinal()];
                if (n != 1) {
                    if (n != 2) {
                        if (n == 3) {
                            object4 = ((LocalePatternMatcher)object4).getRegion();
                            object3 = ((LocalePatternMatcher)object3).getRegion();
                            this.regionScores.addDataToScores((String)object4, (String)object3, (Row.R3<LocalePatternMatcher, LocalePatternMatcher, Double>)object2);
                            if (!bl && !bl2) {
                                this.regionScores.addDataToScores((String)object3, (String)object4, (Row.R3<LocalePatternMatcher, LocalePatternMatcher, Double>)object);
                            }
                        }
                    } else {
                        object4 = ((LocalePatternMatcher)object4).getScript();
                        object3 = ((LocalePatternMatcher)object3).getScript();
                        this.scriptScores.addDataToScores((String)object4, (String)object3, (Row.R3<LocalePatternMatcher, LocalePatternMatcher, Double>)object2);
                        if (!bl && !bl2) {
                            this.scriptScores.addDataToScores((String)object3, (String)object4, (Row.R3<LocalePatternMatcher, LocalePatternMatcher, Double>)object);
                        }
                    }
                } else {
                    object4 = ((LocalePatternMatcher)object4).getLanguage();
                    object3 = ((LocalePatternMatcher)object3).getLanguage();
                    this.languageScores.addDataToScores((String)object4, (String)object3, (Row.R3<LocalePatternMatcher, LocalePatternMatcher, Double>)object2);
                    if (!bl && !bl2) {
                        this.languageScores.addDataToScores((String)object3, (String)object4, (Row.R3<LocalePatternMatcher, LocalePatternMatcher, Double>)object);
                    }
                }
                return this;
            }
            object3 = new StringBuilder();
            ((StringBuilder)object3).append("Lengths unequal: ");
            ((StringBuilder)object3).append((String)object);
            ((StringBuilder)object3).append(", ");
            ((StringBuilder)object3).append((String)object2);
            throw new IllegalArgumentException(((StringBuilder)object3).toString());
        }

        @Deprecated
        public LanguageMatcherData addDistance(String string, String string2, int n, String string3) {
            return this.addDistance(string, string2, n, false, string3);
        }

        @Deprecated
        public LanguageMatcherData addDistance(String string, String string2, int n, boolean bl) {
            return this.addDistance(string, string2, n, bl, null);
        }

        @Deprecated
        @Override
        public LanguageMatcherData cloneAsThawed() {
            try {
                LanguageMatcherData languageMatcherData = (LanguageMatcherData)this.clone();
                languageMatcherData.languageScores = this.languageScores.cloneAsThawed();
                languageMatcherData.scriptScores = this.scriptScores.cloneAsThawed();
                languageMatcherData.regionScores = this.regionScores.cloneAsThawed();
                languageMatcherData.frozen = false;
                return languageMatcherData;
            }
            catch (CloneNotSupportedException cloneNotSupportedException) {
                throw new ICUCloneNotSupportedException(cloneNotSupportedException);
            }
        }

        @Deprecated
        @Override
        public LanguageMatcherData freeze() {
            this.languageScores.freeze();
            this.regionScores.freeze();
            this.scriptScores.freeze();
            this.matchingLanguages = this.languageScores.getMatchingLanguages();
            this.frozen = true;
            return this;
        }

        @Deprecated
        @Override
        public boolean isFrozen() {
            return this.frozen;
        }

        @Deprecated
        public double match(ULocale uLocale, ULocale uLocale2, ULocale uLocale3, ULocale uLocale4) {
            double d;
            double d2 = 0.0 + this.languageScores.getScore(uLocale2, uLocale.getLanguage(), uLocale2.getLanguage(), uLocale4, uLocale3.getLanguage(), uLocale4.getLanguage());
            if (d2 > 0.999) {
                return 0.0;
            }
            d2 = d = d2 + this.scriptScores.getScore(uLocale2, uLocale.getScript(), uLocale2.getScript(), uLocale4, uLocale3.getScript(), uLocale4.getScript()) + this.regionScores.getScore(uLocale2, uLocale.getCountry(), uLocale2.getCountry(), uLocale4, uLocale3.getCountry(), uLocale4.getCountry());
            if (!uLocale.getVariant().equals(uLocale3.getVariant())) {
                d2 = d + 0.01;
            }
            if (d2 < 0.0) {
                d = 0.0;
            } else {
                d = d2;
                if (d2 > 1.0) {
                    d = 1.0;
                }
            }
            return 1.0 - d;
        }

        @Deprecated
        public Relation<String, String> matchingLanguages() {
            return this.matchingLanguages;
        }

        @Deprecated
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.languageScores);
            stringBuilder.append("\n\t");
            stringBuilder.append(this.scriptScores);
            stringBuilder.append("\n\t");
            stringBuilder.append(this.regionScores);
            return stringBuilder.toString();
        }
    }

    static enum Level {
        language(0.99),
        script(0.2),
        region(0.04);
        
        final double worst;

        private Level(double d) {
            this.worst = d;
        }
    }

    private static class LocalePatternMatcher {
        static Pattern pattern = Pattern.compile("([a-z]{1,8}|\\*)(?:[_-]([A-Z][a-z]{3}|\\*))?(?:[_-]([A-Z]{2}|[0-9]{3}|\\*))?");
        private String lang;
        private Level level;
        private String region;
        private String script;

        public LocalePatternMatcher(String object) {
            Object object2 = pattern.matcher((CharSequence)object);
            if (((Matcher)object2).matches()) {
                this.lang = ((Matcher)object2).group(1);
                this.script = ((Matcher)object2).group(2);
                this.region = ((Matcher)object2).group(3);
                object = this.region != null ? Level.region : (this.script != null ? Level.script : Level.language);
                this.level = object;
                if (this.lang.equals("*")) {
                    this.lang = null;
                }
                if ((object = this.script) != null && object.equals("*")) {
                    this.script = null;
                }
                if ((object = this.region) != null && object.equals("*")) {
                    this.region = null;
                }
                return;
            }
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Bad pattern: ");
            ((StringBuilder)object2).append((String)object);
            throw new IllegalArgumentException(((StringBuilder)object2).toString());
        }

        public boolean equals(Object object) {
            boolean bl = true;
            if (object == this) {
                return true;
            }
            if (object != null && object instanceof LocalePatternMatcher) {
                object = (LocalePatternMatcher)object;
                if (!(Objects.equals((Object)this.level, (Object)((LocalePatternMatcher)object).level) && Objects.equals(this.lang, ((LocalePatternMatcher)object).lang) && Objects.equals(this.script, ((LocalePatternMatcher)object).script) && Objects.equals(this.region, ((LocalePatternMatcher)object).region))) {
                    bl = false;
                }
                return bl;
            }
            return false;
        }

        public String getLanguage() {
            String string;
            String string2 = string = this.lang;
            if (string == null) {
                string2 = "*";
            }
            return string2;
        }

        public Level getLevel() {
            return this.level;
        }

        public String getRegion() {
            String string;
            String string2 = string = this.region;
            if (string == null) {
                string2 = "*";
            }
            return string2;
        }

        public String getScript() {
            String string;
            String string2 = string = this.script;
            if (string == null) {
                string2 = "*";
            }
            return string2;
        }

        public int hashCode() {
            int n = this.level.ordinal();
            String string = this.lang;
            int n2 = 0;
            int n3 = string == null ? 0 : string.hashCode();
            string = this.script;
            int n4 = string == null ? 0 : string.hashCode();
            string = this.region;
            if (string != null) {
                n2 = string.hashCode();
            }
            return n ^ n3 ^ n4 ^ n2;
        }

        boolean matches(ULocale uLocale) {
            String string = this.lang;
            if (string != null && !string.equals(uLocale.getLanguage())) {
                return false;
            }
            string = this.script;
            if (string != null && !string.equals(uLocale.getScript())) {
                return false;
            }
            string = this.region;
            return string == null || string.equals(uLocale.getCountry());
        }

        public String toString() {
            String string = this.getLanguage();
            CharSequence charSequence = string;
            if (this.level != Level.language) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append(string);
                ((StringBuilder)charSequence).append("-");
                ((StringBuilder)charSequence).append(this.getScript());
                string = ((StringBuilder)charSequence).toString();
                charSequence = string;
                if (this.level != Level.script) {
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append(string);
                    ((StringBuilder)charSequence).append("-");
                    ((StringBuilder)charSequence).append(this.getRegion());
                    charSequence = ((StringBuilder)charSequence).toString();
                }
            }
            return charSequence;
        }
    }

    @Deprecated
    private static class OutputDouble {
        double value;

        private OutputDouble() {
        }
    }

    private static class ScoreData
    implements Freezable<ScoreData> {
        private static final double maxUnequal_changeD_sameS = 0.5;
        private static final double maxUnequal_changeEqual = 0.75;
        private volatile boolean frozen = false;
        final Level level;
        LinkedHashSet<Row.R3<LocalePatternMatcher, LocalePatternMatcher, Double>> scores = new LinkedHashSet();

        public ScoreData(Level level) {
            this.level = level;
        }

        private double getRawScore(ULocale uLocale, ULocale uLocale2) {
            for (Row.R3 r3 : this.scores) {
                if (!((LocalePatternMatcher)r3.get0()).matches(uLocale) || !((LocalePatternMatcher)r3.get1()).matches(uLocale2)) continue;
                return (Double)r3.get2();
            }
            return this.level.worst;
        }

        void addDataToScores(String charSequence, String string, Row.R3<LocalePatternMatcher, LocalePatternMatcher, Double> r3) {
            if (this.scores.add(r3)) {
                return;
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("trying to add duplicate data: ");
            ((StringBuilder)charSequence).append(r3);
            throw new ICUException(((StringBuilder)charSequence).toString());
        }

        @Override
        public ScoreData cloneAsThawed() {
            try {
                ScoreData scoreData = (ScoreData)this.clone();
                scoreData.scores = (LinkedHashSet)scoreData.scores.clone();
                scoreData.frozen = false;
                return scoreData;
            }
            catch (CloneNotSupportedException cloneNotSupportedException) {
                throw new ICUCloneNotSupportedException(cloneNotSupportedException);
            }
        }

        @Override
        public ScoreData freeze() {
            return this;
        }

        public Relation<String, String> getMatchingLanguages() {
            Relation<String, String> relation = Relation.of(new LinkedHashMap(), HashSet.class);
            for (Row.R3 r3 : this.scores) {
                LocalePatternMatcher localePatternMatcher = (LocalePatternMatcher)r3.get0();
                LocalePatternMatcher object = (LocalePatternMatcher)r3.get1();
                if (localePatternMatcher.lang == null || object.lang == null) continue;
                relation.put(localePatternMatcher.lang, object.lang);
            }
            relation.freeze();
            return relation;
        }

        double getScore(ULocale uLocale, String string, String string2, ULocale uLocale2, String string3, String string4) {
            double d = 0.0;
            if (!string2.equals(string4)) {
                d = this.getRawScore(uLocale, uLocale2);
            } else if (!string.equals(string3)) {
                d = 0.0 + 0.001;
            }
            return d;
        }

        @Override
        public boolean isFrozen() {
            return this.frozen;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder().append((Object)this.level);
            for (Row.R3 r3 : this.scores) {
                stringBuilder.append("\n\t\t");
                stringBuilder.append(r3);
            }
            return stringBuilder.toString();
        }
    }

}

