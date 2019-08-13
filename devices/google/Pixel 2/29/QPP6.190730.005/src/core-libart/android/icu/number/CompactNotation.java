/*
 * Decompiled with CFR 0.145.
 */
package android.icu.number;

import android.icu.impl.StandardPlural;
import android.icu.impl.number.AffixPatternProvider;
import android.icu.impl.number.CompactData;
import android.icu.impl.number.DecimalQuantity;
import android.icu.impl.number.MicroProps;
import android.icu.impl.number.MicroPropsGenerator;
import android.icu.impl.number.Modifier;
import android.icu.impl.number.MultiplierProducer;
import android.icu.impl.number.MutablePatternModifier;
import android.icu.impl.number.PatternStringParser;
import android.icu.number.Notation;
import android.icu.number.Precision;
import android.icu.text.CompactDecimalFormat;
import android.icu.text.PluralRules;
import android.icu.util.ULocale;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class CompactNotation
extends Notation {
    final Map<String, Map<String, String>> compactCustomData;
    final CompactDecimalFormat.CompactStyle compactStyle;

    CompactNotation(CompactDecimalFormat.CompactStyle compactStyle) {
        this.compactCustomData = null;
        this.compactStyle = compactStyle;
    }

    CompactNotation(Map<String, Map<String, String>> map) {
        this.compactStyle = null;
        this.compactCustomData = map;
    }

    @Deprecated
    public static CompactNotation forCustomData(Map<String, Map<String, String>> map) {
        return new CompactNotation(map);
    }

    MicroPropsGenerator withLocaleData(ULocale uLocale, String string, CompactData.CompactType compactType, PluralRules pluralRules, MutablePatternModifier mutablePatternModifier, MicroPropsGenerator microPropsGenerator) {
        return new CompactHandler(this, uLocale, string, compactType, pluralRules, mutablePatternModifier, microPropsGenerator);
    }

    private static class CompactHandler
    implements MicroPropsGenerator {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        final CompactData data;
        final MicroPropsGenerator parent;
        final Map<String, MutablePatternModifier.ImmutablePatternModifier> precomputedMods;
        final PluralRules rules;

        private CompactHandler(CompactNotation compactNotation, ULocale uLocale, String string, CompactData.CompactType compactType, PluralRules pluralRules, MutablePatternModifier mutablePatternModifier, MicroPropsGenerator microPropsGenerator) {
            this.rules = pluralRules;
            this.parent = microPropsGenerator;
            this.data = new CompactData();
            if (compactNotation.compactStyle != null) {
                this.data.populate(uLocale, string, compactNotation.compactStyle, compactType);
            } else {
                this.data.populate(compactNotation.compactCustomData);
            }
            if (mutablePatternModifier != null) {
                this.precomputedMods = new HashMap<String, MutablePatternModifier.ImmutablePatternModifier>();
                this.precomputeAllModifiers(mutablePatternModifier);
            } else {
                this.precomputedMods = null;
            }
        }

        private void precomputeAllModifiers(MutablePatternModifier mutablePatternModifier) {
            Object object = new HashSet<String>();
            this.data.getUniquePatterns((Set<String>)object);
            Iterator iterator = object.iterator();
            while (iterator.hasNext()) {
                object = (String)iterator.next();
                mutablePatternModifier.setPatternInfo(PatternStringParser.parseToPatternInfo((String)object));
                this.precomputedMods.put((String)object, mutablePatternModifier.createImmutable());
            }
        }

        @Override
        public MicroProps processQuantity(DecimalQuantity object) {
            int n;
            MicroProps microProps = this.parent.processQuantity((DecimalQuantity)object);
            if (object.isZero()) {
                n = 0;
                microProps.rounder.apply((DecimalQuantity)object);
            } else {
                int n2 = microProps.rounder.chooseMultiplierAndApply((DecimalQuantity)object, this.data);
                n = object.isZero() ? 0 : object.getMagnitude();
                n -= n2;
            }
            Object object2 = object.getStandardPlural(this.rules);
            object2 = this.data.getPattern(n, (StandardPlural)((Object)object2));
            if (object2 != null) {
                Map<String, MutablePatternModifier.ImmutablePatternModifier> map = this.precomputedMods;
                if (map != null) {
                    map.get(object2).applyToMicros(microProps, (DecimalQuantity)object);
                } else {
                    object = PatternStringParser.parseToPatternInfo((String)object2);
                    ((MutablePatternModifier)microProps.modMiddle).setPatternInfo((AffixPatternProvider)object);
                }
            }
            microProps.rounder = Precision.constructPassThrough();
            return microProps;
        }
    }

}

