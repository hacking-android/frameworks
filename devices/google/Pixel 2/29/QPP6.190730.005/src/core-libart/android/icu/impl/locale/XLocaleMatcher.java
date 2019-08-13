/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.locale;

import android.icu.impl.locale.XCldrStub;
import android.icu.impl.locale.XLikelySubtags;
import android.icu.impl.locale.XLocaleDistance;
import android.icu.util.LocalePriorityList;
import android.icu.util.Output;
import android.icu.util.ULocale;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class XLocaleMatcher {
    private static final boolean TRACE_MATCHER = false;
    private static final XLikelySubtags.LSR UND = new XLikelySubtags.LSR("und", "", "");
    private static final ULocale UND_LOCALE = new ULocale("und");
    private final ULocale defaultLanguage;
    private final int demotionPerAdditionalDesiredLocale;
    private final XLocaleDistance.DistanceOption distanceOption;
    private final Set<ULocale> exactSupportedLocales;
    private final XLocaleDistance localeDistance;
    private final Map<XLikelySubtags.LSR, Set<ULocale>> supportedLanguages;
    private final int thresholdDistance;

    private XLocaleMatcher(Builder builder) {
        Object object = builder.localeDistance == null ? XLocaleDistance.getDefault() : builder.localeDistance;
        this.localeDistance = object;
        int n = builder.thresholdDistance < 0 ? this.localeDistance.getDefaultScriptDistance() : builder.thresholdDistance;
        this.thresholdDistance = n;
        object = this.extractLsrSet(this.localeDistance.getParadigms());
        object = this.extractLsrMap(builder.supportedLanguagesList, (Set<XLikelySubtags.LSR>)object);
        this.supportedLanguages = ((XCldrStub.Multimap)object).asMap();
        this.exactSupportedLocales = XCldrStub.ImmutableSet.copyOf(((XCldrStub.Multimap)object).values());
        object = builder.defaultLanguage != null ? builder.defaultLanguage : (this.supportedLanguages.isEmpty() ? null : this.supportedLanguages.entrySet().iterator().next().getValue().iterator().next());
        this.defaultLanguage = object;
        n = builder.demotionPerAdditionalDesiredLocale < 0 ? this.localeDistance.getDefaultRegionDistance() + 1 : builder.demotionPerAdditionalDesiredLocale;
        this.demotionPerAdditionalDesiredLocale = n;
        this.distanceOption = builder.distanceOption;
    }

    public XLocaleMatcher(LocalePriorityList localePriorityList) {
        this(XLocaleMatcher.builder().setSupportedLocales(localePriorityList));
    }

    public XLocaleMatcher(String string) {
        this(XLocaleMatcher.builder().setSupportedLocales(string));
    }

    public XLocaleMatcher(Set<ULocale> set) {
        this(XLocaleMatcher.builder().setSupportedLocales(set));
    }

    private static Set<ULocale> asSet(LocalePriorityList object) {
        LinkedHashSet<ULocale> linkedHashSet = new LinkedHashSet<ULocale>();
        object = ((LocalePriorityList)object).iterator();
        while (object.hasNext()) {
            linkedHashSet.add((ULocale)object.next());
        }
        return linkedHashSet;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static ULocale combine(ULocale object, ULocale uLocale) {
        Object object2 = object;
        if (!((ULocale)object).equals(uLocale)) {
            object2 = object;
            if (uLocale != null) {
                object = new ULocale.Builder().setLocale((ULocale)object);
                object2 = uLocale.getCountry();
                if (!((String)object2).isEmpty()) {
                    ((ULocale.Builder)object).setRegion((String)object2);
                }
                if (!((String)(object2 = uLocale.getVariant())).isEmpty()) {
                    ((ULocale.Builder)object).setVariant((String)object2);
                }
                object2 = uLocale.getExtensionKeys().iterator();
                while (object2.hasNext()) {
                    char c = object2.next().charValue();
                    ((ULocale.Builder)object).setExtension(c, uLocale.getExtension(c));
                }
                object2 = ((ULocale.Builder)object).build();
            }
        }
        return object2;
    }

    private XCldrStub.Multimap<XLikelySubtags.LSR, ULocale> extractLsrMap(Set<ULocale> object, Set<XLikelySubtags.LSR> set) {
        Object object2;
        XCldrStub.LinkedHashMultimap linkedHashMultimap = XCldrStub.LinkedHashMultimap.create();
        Iterator<Object> iterator = object.iterator();
        while (iterator.hasNext()) {
            object2 = (ULocale)iterator.next();
            object = ((ULocale)object2).equals(UND_LOCALE) ? UND : XLikelySubtags.LSR.fromMaximalized((ULocale)object2);
            linkedHashMultimap.put(object, (ULocale)object2);
        }
        object = linkedHashMultimap;
        if (linkedHashMultimap.size() > 1) {
            object = linkedHashMultimap;
            if (set != null) {
                object = XCldrStub.LinkedHashMultimap.create();
                boolean bl = true;
                for (Map.Entry entry : linkedHashMultimap.asMap().entrySet()) {
                    object2 = (XLikelySubtags.LSR)entry.getKey();
                    if (!bl && !set.contains(object2)) continue;
                    ((XCldrStub.Multimap)object).putAll(object2, (Collection)entry.getValue());
                    bl = false;
                }
                ((XCldrStub.Multimap)object).putAll(linkedHashMultimap);
                if (!((XCldrStub.Multimap)object).equals(linkedHashMultimap)) {
                    throw new IllegalArgumentException();
                }
            }
        }
        return XCldrStub.ImmutableMultimap.copyOf(object);
    }

    private Set<XLikelySubtags.LSR> extractLsrSet(Set<ULocale> object) {
        LinkedHashSet<XLikelySubtags.LSR> linkedHashSet = new LinkedHashSet<XLikelySubtags.LSR>();
        Iterator<ULocale> iterator = object.iterator();
        while (iterator.hasNext()) {
            object = iterator.next();
            object = ((ULocale)object).equals(UND_LOCALE) ? UND : XLikelySubtags.LSR.fromMaximalized((ULocale)object);
            linkedHashSet.add((XLikelySubtags.LSR)object);
        }
        return linkedHashSet;
    }

    public ULocale canonicalize(ULocale uLocale) {
        return null;
    }

    public int distance(ULocale uLocale, ULocale uLocale2) {
        return this.localeDistance.distanceRaw(XLikelySubtags.LSR.fromMaximalized(uLocale), XLikelySubtags.LSR.fromMaximalized(uLocale2), this.thresholdDistance, this.distanceOption);
    }

    public int distance(String string, String string2) {
        return this.localeDistance.distanceRaw(XLikelySubtags.LSR.fromMaximalized(new ULocale(string)), XLikelySubtags.LSR.fromMaximalized(new ULocale(string2)), this.thresholdDistance, this.distanceOption);
    }

    public ULocale getBestMatch(LocalePriorityList localePriorityList) {
        return this.getBestMatch(localePriorityList, null);
    }

    public ULocale getBestMatch(LocalePriorityList localePriorityList, Output<ULocale> output) {
        return this.getBestMatch(XLocaleMatcher.asSet(localePriorityList), output);
    }

    public ULocale getBestMatch(ULocale uLocale) {
        return this.getBestMatch(uLocale, (Output<ULocale>)null);
    }

    public ULocale getBestMatch(ULocale uLocale, Output<ULocale> output) {
        Object object;
        Object object2;
        int n;
        int n2 = Integer.MAX_VALUE;
        Object object3 = null;
        Object object4 = null;
        XLikelySubtags.LSR lSR = uLocale.equals(UND_LOCALE) ? UND : XLikelySubtags.LSR.fromMaximalized(uLocale);
        if (this.exactSupportedLocales.contains(uLocale)) {
            if (output != null) {
                output.value = uLocale;
            }
            return uLocale;
        }
        if (this.distanceOption == XLocaleDistance.DistanceOption.REGION_FIRST && (object2 = (Collection)this.supportedLanguages.get(lSR)) != null) {
            if (output != null) {
                output.value = uLocale;
            }
            return (ULocale)object2.iterator().next();
        }
        Iterator<Map.Entry<XLikelySubtags.LSR, Set<ULocale>>> iterator = this.supportedLanguages.entrySet().iterator();
        do {
            n = n2;
            object2 = object3;
            object = object4;
            if (!iterator.hasNext()) break;
            object = iterator.next();
            int n3 = this.localeDistance.distanceRaw(lSR, object.getKey(), this.thresholdDistance, this.distanceOption);
            n = n2;
            object2 = object4;
            if (n3 < n2) {
                n2 = n3;
                object4 = uLocale;
                object = object.getValue();
                n = n2;
                object3 = object4;
                object2 = object;
                if (n3 == 0) {
                    n = n2;
                    object2 = object4;
                    break;
                }
            }
            n2 = n;
            object4 = object2;
        } while (true);
        if (n >= this.thresholdDistance) {
            if (output != null) {
                output.value = null;
            }
            return this.defaultLanguage;
        }
        if (output != null) {
            output.value = object2;
        }
        if (object.contains(object2)) {
            return object2;
        }
        return (ULocale)object.iterator().next();
    }

    public ULocale getBestMatch(String string) {
        return this.getBestMatch(LocalePriorityList.add(string).build(), null);
    }

    public ULocale getBestMatch(Set<ULocale> set) {
        return this.getBestMatch(set, null);
    }

    /*
     * WARNING - void declaration
     */
    public ULocale getBestMatch(Set<ULocale> object, Output<ULocale> output) {
        if (object.size() == 1) {
            return this.getBestMatch((ULocale)object.iterator().next(), output);
        }
        XCldrStub.Multimap<XLikelySubtags.LSR, ULocale> multimap = this.extractLsrMap((Set<ULocale>)object, null);
        int n = Integer.MAX_VALUE;
        object = null;
        Object object2 = null;
        int n2 = 0;
        block0 : for (Map.Entry<XLikelySubtags.LSR, Set<ULocale>> entry : multimap.asMap().entrySet()) {
            XLikelySubtags.LSR lSR = entry.getKey();
            for (ULocale uLocale : entry.getValue()) {
                void var8_13;
                if (n2 < n) {
                    if (this.exactSupportedLocales.contains(uLocale)) {
                        if (output != null) {
                            output.value = uLocale;
                        }
                        return uLocale;
                    }
                    Collection collection = this.supportedLanguages.get(lSR);
                    if (collection != null) {
                        if (output != null) {
                            output.value = uLocale;
                        }
                        return (ULocale)collection.iterator().next();
                    }
                }
                Iterator<Map.Entry<XLikelySubtags.LSR, Set<ULocale>>> iterator = this.supportedLanguages.entrySet().iterator();
                Collection collection = object2;
                object2 = object;
                int n3 = n;
                while (iterator.hasNext()) {
                    Map.Entry<XLikelySubtags.LSR, Set<ULocale>> entry2 = iterator.next();
                    int n4 = this.localeDistance.distanceRaw(lSR, entry2.getKey(), this.thresholdDistance, this.distanceOption) + n2;
                    if (n4 >= n3) continue;
                    n = n4;
                    object = uLocale;
                    entry2 = entry2.getValue();
                    n3 = n;
                    object2 = object;
                    Map.Entry<XLikelySubtags.LSR, Set<ULocale>> entry3 = entry2;
                    if (n4 != 0) continue;
                    object2 = entry2;
                    break block0;
                }
                n2 += this.demotionPerAdditionalDesiredLocale;
                n = n3;
                object = object2;
                object2 = var8_13;
            }
        }
        if (n >= this.thresholdDistance) {
            if (output != null) {
                output.value = null;
            }
            return this.defaultLanguage;
        }
        if (output != null) {
            output.value = object;
        }
        if (object2.contains(object)) {
            return object;
        }
        return (ULocale)object2.iterator().next();
    }

    public ULocale getBestMatch(ULocale ... arruLocale) {
        return this.getBestMatch(new LinkedHashSet<ULocale>(Arrays.asList(arruLocale)), null);
    }

    public int getThresholdDistance() {
        return this.thresholdDistance;
    }

    public double match(ULocale uLocale, ULocale uLocale2) {
        return (double)(100 - this.distance(uLocale, uLocale2)) / 100.0;
    }

    @Deprecated
    public double match(ULocale uLocale, ULocale uLocale2, ULocale uLocale3, ULocale uLocale4) {
        return this.match(uLocale, uLocale3);
    }

    public String toString() {
        return this.exactSupportedLocales.toString();
    }

    public static class Builder {
        private ULocale defaultLanguage;
        private int demotionPerAdditionalDesiredLocale = -1;
        private XLocaleDistance.DistanceOption distanceOption;
        private XLocaleDistance localeDistance;
        private Set<ULocale> supportedLanguagesList;
        private int thresholdDistance = -1;

        public XLocaleMatcher build() {
            return new XLocaleMatcher(this);
        }

        public Builder setDefaultLanguage(ULocale uLocale) {
            this.defaultLanguage = uLocale;
            return this;
        }

        public Builder setDemotionPerAdditionalDesiredLocale(int n) {
            this.demotionPerAdditionalDesiredLocale = n;
            return this;
        }

        public Builder setDistanceOption(XLocaleDistance.DistanceOption distanceOption) {
            this.distanceOption = distanceOption;
            return this;
        }

        public Builder setLocaleDistance(XLocaleDistance xLocaleDistance) {
            this.localeDistance = xLocaleDistance;
            return this;
        }

        public Builder setSupportedLocales(LocalePriorityList localePriorityList) {
            this.supportedLanguagesList = XLocaleMatcher.asSet(localePriorityList);
            return this;
        }

        public Builder setSupportedLocales(String string) {
            this.supportedLanguagesList = XLocaleMatcher.asSet(LocalePriorityList.add(string).build());
            return this;
        }

        public Builder setSupportedLocales(Set<ULocale> set) {
            LinkedHashSet<ULocale> linkedHashSet = new LinkedHashSet<ULocale>();
            linkedHashSet.addAll(set);
            this.supportedLanguagesList = linkedHashSet;
            return this;
        }

        public Builder setThresholdDistance(int n) {
            this.thresholdDistance = n;
            return this;
        }

        public String toString() {
            int n;
            StringBuilder stringBuilder = new StringBuilder().append("{XLocaleMatcher.Builder");
            if (!this.supportedLanguagesList.isEmpty()) {
                stringBuilder.append(" supported={");
                stringBuilder.append(this.supportedLanguagesList.toString());
                stringBuilder.append("}");
            }
            if (this.defaultLanguage != null) {
                stringBuilder.append(" default=");
                stringBuilder.append(this.defaultLanguage.toString());
            }
            if ((n = this.thresholdDistance) >= 0) {
                stringBuilder.append(String.format(" thresholdDistance=%d", n));
            }
            stringBuilder.append(" preference=");
            stringBuilder.append(this.distanceOption.name());
            stringBuilder.append("}");
            return stringBuilder.toString();
        }
    }

}

