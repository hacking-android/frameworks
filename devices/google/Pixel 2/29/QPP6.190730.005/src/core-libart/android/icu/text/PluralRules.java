/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.impl.PluralRulesLoader;
import android.icu.text.PluralRulesSerialProxy;
import android.icu.text.UnicodeSet;
import android.icu.util.Output;
import android.icu.util.ULocale;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;

public class PluralRules
implements Serializable {
    static final UnicodeSet ALLOWED_ID = new UnicodeSet("[a-z]").freeze();
    static final Pattern AND_SEPARATED;
    static final Pattern AT_SEPARATED;
    @Deprecated
    public static final String CATEGORY_SEPARATOR = ";  ";
    static final Pattern COMMA_SEPARATED;
    public static final PluralRules DEFAULT;
    private static final Rule DEFAULT_RULE;
    static final Pattern DOTDOT_SEPARATED;
    public static final String KEYWORD_FEW = "few";
    public static final String KEYWORD_MANY = "many";
    public static final String KEYWORD_ONE = "one";
    public static final String KEYWORD_OTHER = "other";
    @Deprecated
    public static final String KEYWORD_RULE_SEPARATOR = ": ";
    public static final String KEYWORD_TWO = "two";
    public static final String KEYWORD_ZERO = "zero";
    private static final Constraint NO_CONSTRAINT;
    public static final double NO_UNIQUE_VALUE = -0.00123456777;
    static final Pattern OR_SEPARATED;
    static final Pattern SEMI_SEPARATED;
    static final Pattern TILDE_SEPARATED;
    private static final long serialVersionUID = 1L;
    private final transient Set<String> keywords;
    private final RuleList rules;

    static {
        NO_CONSTRAINT = new Constraint(){
            private static final long serialVersionUID = 9163464945387899416L;

            @Override
            public boolean isFulfilled(IFixedDecimal iFixedDecimal) {
                return true;
            }

            @Override
            public boolean isLimited(SampleType sampleType) {
                return false;
            }

            public String toString() {
                return "";
            }
        };
        DEFAULT_RULE = new Rule(KEYWORD_OTHER, NO_CONSTRAINT, null, null);
        DEFAULT = new PluralRules(new RuleList().addRule(DEFAULT_RULE));
        AT_SEPARATED = Pattern.compile("\\s*\\Q\\E@\\s*");
        OR_SEPARATED = Pattern.compile("\\s*or\\s*");
        AND_SEPARATED = Pattern.compile("\\s*and\\s*");
        COMMA_SEPARATED = Pattern.compile("\\s*,\\s*");
        DOTDOT_SEPARATED = Pattern.compile("\\s*\\Q..\\E\\s*");
        TILDE_SEPARATED = Pattern.compile("\\s*~\\s*");
        SEMI_SEPARATED = Pattern.compile("\\s*;\\s*");
    }

    private PluralRules(RuleList ruleList) {
        this.rules = ruleList;
        this.keywords = Collections.unmodifiableSet(ruleList.getKeywords());
    }

    private boolean addConditional(Set<IFixedDecimal> set, Set<IFixedDecimal> set2, double d) {
        boolean bl;
        FixedDecimal fixedDecimal = new FixedDecimal(d);
        if (!set.contains(fixedDecimal) && !set2.contains(fixedDecimal)) {
            set2.add(fixedDecimal);
            bl = true;
        } else {
            bl = false;
        }
        return bl;
    }

    private static void addRange(StringBuilder stringBuilder, double d, double d2, boolean bl) {
        if (bl) {
            stringBuilder.append(",");
        }
        if (d == d2) {
            stringBuilder.append(PluralRules.format(d));
        } else {
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append(PluralRules.format(d));
            stringBuilder2.append("..");
            stringBuilder2.append(PluralRules.format(d2));
            stringBuilder.append(stringBuilder2.toString());
        }
    }

    public static PluralRules createRules(String object) {
        try {
            object = PluralRules.parseDescription((String)object);
            return object;
        }
        catch (Exception exception) {
            return null;
        }
    }

    public static PluralRules forLocale(ULocale uLocale) {
        return Factory.getDefaultFactory().forLocale(uLocale, PluralType.CARDINAL);
    }

    public static PluralRules forLocale(ULocale uLocale, PluralType pluralType) {
        return Factory.getDefaultFactory().forLocale(uLocale, pluralType);
    }

    public static PluralRules forLocale(Locale locale) {
        return PluralRules.forLocale(ULocale.forLocale(locale));
    }

    public static PluralRules forLocale(Locale locale, PluralType pluralType) {
        return PluralRules.forLocale(ULocale.forLocale(locale), pluralType);
    }

    private static String format(double d) {
        long l = (long)d;
        String string = d == (double)l ? String.valueOf(l) : String.valueOf(d);
        return string;
    }

    public static ULocale[] getAvailableULocales() {
        return Factory.getDefaultFactory().getAvailableULocales();
    }

    public static ULocale getFunctionalEquivalent(ULocale uLocale, boolean[] arrbl) {
        return Factory.getDefaultFactory().getFunctionalEquivalent(uLocale, arrbl);
    }

    private static boolean isValidKeyword(String string) {
        return ALLOWED_ID.containsAll(string);
    }

    private static String nextToken(String[] object, int n, String string) throws ParseException {
        if (n < ((String[])object).length) {
            return object[n];
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("missing token at end of '");
        ((StringBuilder)object).append(string);
        ((StringBuilder)object).append("'");
        throw new ParseException(((StringBuilder)object).toString(), -1);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static Constraint parseConstraint(String object) throws ParseException {
        String[] arrstring = OR_SEPARATED.split((CharSequence)object);
        Object object2 = null;
        for (int i = 0; i < arrstring.length; ++i) {
            String[] arrstring2 = AND_SEPARATED.split(arrstring[i]);
            object = null;
            Constraint constraint = object2;
            for (int j = 0; j < arrstring2.length; ++j) {
                Object object3;
                int n;
                block31 : {
                    block32 : {
                        long l;
                        double d;
                        String[] arrstring3;
                        int n2;
                        boolean bl;
                        String string;
                        long l2;
                        int n3;
                        boolean bl2;
                        Operand operand;
                        boolean bl3;
                        int n4;
                        block34 : {
                            block33 : {
                                object2 = NO_CONSTRAINT;
                                string = arrstring2[j].trim();
                                arrstring3 = SimpleTokenizer.split(string);
                                n4 = 0;
                                bl3 = true;
                                d = 9.223372036854776E18;
                                n2 = 0 + 1;
                                object3 = arrstring3[0];
                                bl2 = false;
                                try {
                                    operand = FixedDecimal.getOperand((String)object3);
                                }
                                catch (Exception exception) {
                                    throw PluralRules.unexpected((String)object3, string);
                                }
                                if (n2 >= arrstring3.length) break block32;
                                n = n2 + 1;
                                object3 = arrstring3[n2];
                                if ("mod".equals(object3)) break block33;
                                n2 = n;
                                object2 = object3;
                                if (!"%".equals(object3)) break block34;
                            }
                            n3 = n + 1;
                            n4 = Integer.parseInt(arrstring3[n]);
                            n2 = n3 + 1;
                            object2 = PluralRules.nextToken(arrstring3, n3, string);
                        }
                        if ("not".equals(object2)) {
                            bl3 = true ^ true;
                            object3 = PluralRules.nextToken(arrstring3, n2, string);
                            if ("=".equals(object3)) throw PluralRules.unexpected((String)object3, string);
                            n = n2 + 1;
                        } else {
                            n = n2;
                            object3 = object2;
                            if ("!".equals(object2)) {
                                bl3 = true ^ true;
                                object3 = PluralRules.nextToken(arrstring3, n2, string);
                                if (!"=".equals(object3)) throw PluralRules.unexpected((String)object3, string);
                                n = n2 + 1;
                            }
                        }
                        if (!("is".equals(object3) || "in".equals(object3) || "=".equals(object3))) {
                            if (!"within".equals(object3)) throw PluralRules.unexpected((String)object3, string);
                            bl = false;
                            n2 = n + 1;
                            object2 = PluralRules.nextToken(arrstring3, n, string);
                            n = n2;
                        } else {
                            bl2 = "is".equals(object3);
                            if (bl2 && !bl3) {
                                throw PluralRules.unexpected((String)object3, string);
                            }
                            n2 = n + 1;
                            object2 = PluralRules.nextToken(arrstring3, n, string);
                            bl = true;
                            n = n2;
                        }
                        if ("not".equals(object2)) {
                            if (!bl2 && !bl3) {
                                throw PluralRules.unexpected((String)object2, string);
                            }
                            bl3 = !bl3;
                            n2 = n + 1;
                            object2 = PluralRules.nextToken(arrstring3, n, string);
                        } else {
                            n2 = n;
                        }
                        ArrayList<Long> arrayList = new ArrayList<Long>();
                        double d2 = -9.223372036854776E18;
                        n = i;
                        object3 = object;
                        i = j;
                        j = n2;
                        object = object2;
                        do {
                            object2 = object3;
                            l = Long.parseLong((String)object);
                            if (j < arrstring3.length) {
                                n2 = j + 1;
                                object = PluralRules.nextToken(arrstring3, j, string);
                                if (((String)object).equals(".")) {
                                    n3 = n2 + 1;
                                    object = PluralRules.nextToken(arrstring3, n2, string);
                                    if (!((String)object).equals(".")) throw PluralRules.unexpected((String)object, string);
                                    j = n3 + 1;
                                    object = PluralRules.nextToken(arrstring3, n3, string);
                                    l2 = Long.parseLong((String)object);
                                    if (j < arrstring3.length) {
                                        object = PluralRules.nextToken(arrstring3, j, string);
                                        if (!((String)object).equals(",")) throw PluralRules.unexpected((String)object, string);
                                        ++j;
                                    }
                                } else {
                                    if (!((String)object).equals(",")) throw PluralRules.unexpected((String)object, string);
                                    j = n2;
                                    l2 = l;
                                }
                            } else {
                                l2 = l;
                            }
                            if (l > l2) break;
                            if (n4 != 0 && l2 >= (long)n4) {
                                object = new StringBuilder();
                                ((StringBuilder)object).append(l2);
                                ((StringBuilder)object).append(">mod=");
                                ((StringBuilder)object).append(n4);
                                throw PluralRules.unexpected(((StringBuilder)object).toString(), string);
                            }
                            arrayList.add(l);
                            arrayList.add(l2);
                            d = Math.min(d, (double)l);
                            d2 = Math.max(d2, (double)l2);
                            if (j >= arrstring3.length) {
                                if (((String)object).equals(",")) throw PluralRules.unexpected((String)object, string);
                                if (arrayList.size() == 2) {
                                    object = null;
                                } else {
                                    object3 = new long[arrayList.size()];
                                    j = 0;
                                    do {
                                        object = object3;
                                        if (j >= ((long[])object3).length) break;
                                        object3[j] = (Long)arrayList.get(j);
                                        ++j;
                                    } while (true);
                                }
                                if (d != d2 && bl2 && !bl3) {
                                    throw PluralRules.unexpected("is not <range>", string);
                                }
                                object = new RangeConstraint(n4, bl3, operand, bl, d, d2, (long[])object);
                                j = i;
                                object3 = object2;
                                break block31;
                            }
                            object = PluralRules.nextToken(arrstring3, j, string);
                            ++j;
                            object3 = object2;
                        } while (true);
                        object = new StringBuilder();
                        ((StringBuilder)object).append(l);
                        ((StringBuilder)object).append("~");
                        ((StringBuilder)object).append(l2);
                        throw PluralRules.unexpected(((StringBuilder)object).toString(), string);
                    }
                    n = i;
                    object3 = object;
                    object = object2;
                }
                if (object3 != null) {
                    object = new AndConstraint((Constraint)object3, (Constraint)object);
                }
                i = n;
                continue;
            }
            object2 = constraint == null ? object : new OrConstraint(constraint, (Constraint)object);
        }
        return object2;
    }

    public static PluralRules parseDescription(String object) throws ParseException {
        object = ((String)(object = ((String)object).trim())).length() == 0 ? DEFAULT : new PluralRules(PluralRules.parseRuleChain((String)object));
        return object;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static Rule parseRule(String object) throws ParseException {
        if (((String)object).length() == 0) {
            return DEFAULT_RULE;
        }
        int n = ((String)(object = ((String)object).toLowerCase(Locale.ENGLISH))).indexOf(58);
        if (n == -1) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("missing ':' in rule description '");
            stringBuilder.append((String)object);
            stringBuilder.append("'");
            throw new ParseException(stringBuilder.toString(), 0);
        }
        String string = ((String)object).substring(0, n).trim();
        if (!PluralRules.isValidKeyword(string)) {
            object = new StringBuilder();
            ((StringBuilder)object).append("keyword '");
            ((StringBuilder)object).append(string);
            ((StringBuilder)object).append(" is not valid");
            throw new ParseException(((StringBuilder)object).toString(), 0);
        }
        Object object2 = ((String)object).substring(n + 1).trim();
        String[] arrstring = AT_SEPARATED.split((CharSequence)object2);
        object = null;
        Object object3 = null;
        n = arrstring.length;
        boolean bl = true;
        if (n != 1) {
            if (n != 2) {
                if (n != 3) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Too many samples in ");
                    ((StringBuilder)object).append((String)object2);
                    throw new IllegalArgumentException(((StringBuilder)object).toString());
                }
                object = FixedDecimalSamples.parse(arrstring[1]);
                object3 = FixedDecimalSamples.parse(arrstring[2]);
                if (((FixedDecimalSamples)object).sampleType != SampleType.INTEGER || ((FixedDecimalSamples)object3).sampleType != SampleType.DECIMAL) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Must have @integer then @decimal in ");
                    ((StringBuilder)object).append((String)object2);
                    throw new IllegalArgumentException(((StringBuilder)object).toString());
                }
            } else {
                object = object2 = FixedDecimalSamples.parse(arrstring[1]);
                if (((FixedDecimalSamples)object2).sampleType == SampleType.DECIMAL) {
                    object3 = object2;
                    object = null;
                }
            }
        }
        if (false) throw new IllegalArgumentException("Ill-formed samples\u2014'@' characters.");
        boolean bl2 = string.equals(KEYWORD_OTHER);
        if (arrstring[0].length() != 0) {
            bl = false;
        }
        if (bl2 != bl) throw new IllegalArgumentException("The keyword 'other' must have no constraints, just samples.");
        if (bl2) {
            object2 = NO_CONSTRAINT;
            return new Rule(string, (Constraint)object2, (FixedDecimalSamples)object, (FixedDecimalSamples)object3);
        }
        object2 = PluralRules.parseConstraint(arrstring[0]);
        return new Rule(string, (Constraint)object2, (FixedDecimalSamples)object, (FixedDecimalSamples)object3);
    }

    private static RuleList parseRuleChain(String arrstring) throws ParseException {
        RuleList ruleList = new RuleList();
        Object object = arrstring;
        if (arrstring.endsWith(";")) {
            object = arrstring.substring(0, arrstring.length() - 1);
        }
        arrstring = SEMI_SEPARATED.split((CharSequence)object);
        for (int i = 0; i < arrstring.length; ++i) {
            object = PluralRules.parseRule(arrstring[i].trim());
            int n = ((Rule)object).integerSamples == null && ((Rule)object).decimalSamples == null ? 0 : 1;
            RuleList.access$276(ruleList, n);
            ruleList.addRule((Rule)object);
        }
        return ruleList.finish();
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        throw new NotSerializableException();
    }

    private static ParseException unexpected(String string, String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("unexpected token '");
        stringBuilder.append(string);
        stringBuilder.append("' in '");
        stringBuilder.append(string2);
        stringBuilder.append("'");
        return new ParseException(stringBuilder.toString(), -1);
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        throw new NotSerializableException();
    }

    private Object writeReplace() throws ObjectStreamException {
        return new PluralRulesSerialProxy(this.toString());
    }

    @Deprecated
    public boolean addSample(String string, Number number, int n, Set<Double> set) {
        String string2 = number instanceof FixedDecimal ? this.select((FixedDecimal)number) : this.select(number.doubleValue());
        if (string2.equals(string)) {
            set.add(number.doubleValue());
            if (n - 1 < 0) {
                return false;
            }
        }
        return true;
    }

    @Deprecated
    public int compareTo(PluralRules pluralRules) {
        return this.toString().compareTo(pluralRules.toString());
    }

    @Deprecated
    public boolean computeLimited(String string, SampleType sampleType) {
        return this.rules.computeLimited(string, sampleType);
    }

    public boolean equals(PluralRules pluralRules) {
        boolean bl = pluralRules != null && this.toString().equals(pluralRules.toString());
        return bl;
    }

    public boolean equals(Object object) {
        boolean bl = object instanceof PluralRules && this.equals((PluralRules)object);
        return bl;
    }

    public Collection<Double> getAllKeywordValues(String string) {
        return this.getAllKeywordValues(string, SampleType.INTEGER);
    }

    @Deprecated
    public Collection<Double> getAllKeywordValues(String collection, SampleType sampleType) {
        boolean bl = this.isLimited((String)((Object)collection), sampleType);
        Object var4_4 = null;
        if (!bl) {
            return null;
        }
        collection = (collection = this.getSamples((String)((Object)collection), sampleType)) == null ? var4_4 : Collections.unmodifiableCollection(collection);
        return collection;
    }

    @Deprecated
    public FixedDecimalSamples getDecimalSamples(String string, SampleType sampleType) {
        return this.rules.getDecimalSamples(string, sampleType);
    }

    public KeywordStatus getKeywordStatus(String string, int n, Set<Double> set, Output<Double> output) {
        return this.getKeywordStatus(string, n, set, output, SampleType.INTEGER);
    }

    @Deprecated
    public KeywordStatus getKeywordStatus(String object, int n, Set<Double> set, Output<Double> output, SampleType object2) {
        if (output != null) {
            output.value = null;
        }
        if (!this.keywords.contains(object)) {
            return KeywordStatus.INVALID;
        }
        if (!this.isLimited((String)object, (SampleType)((Object)object2))) {
            return KeywordStatus.UNBOUNDED;
        }
        object2 = this.getSamples((String)object, (SampleType)((Object)object2));
        int n2 = object2.size();
        object = set;
        if (set == null) {
            object = Collections.emptySet();
        }
        if (n2 > object.size()) {
            if (n2 == 1) {
                if (output != null) {
                    output.value = object2.iterator().next();
                }
                return KeywordStatus.UNIQUE;
            }
            return KeywordStatus.BOUNDED;
        }
        set = new HashSet<Double>((Collection<Double>)object2);
        object = object.iterator();
        while (object.hasNext()) {
            ((HashSet)set).remove((Double)object.next() - (double)n);
        }
        if (((HashSet)set).size() == 0) {
            return KeywordStatus.SUPPRESSED;
        }
        if (output != null && ((HashSet)set).size() == 1) {
            output.value = ((HashSet)set).iterator().next();
        }
        object = n2 == 1 ? KeywordStatus.UNIQUE : KeywordStatus.BOUNDED;
        return object;
    }

    public Set<String> getKeywords() {
        return this.keywords;
    }

    @Deprecated
    public String getRules(String string) {
        return this.rules.getRules(string);
    }

    public Collection<Double> getSamples(String string) {
        return this.getSamples(string, SampleType.INTEGER);
    }

    @Deprecated
    public Collection<Double> getSamples(String set, SampleType sampleType) {
        boolean bl = this.keywords.contains(set);
        Object var4_4 = null;
        if (!bl) {
            return null;
        }
        TreeSet<Double> treeSet = new TreeSet<Double>();
        if (this.rules.hasExplicitBoundingInfo) {
            set = (set = this.rules.getDecimalSamples((String)((Object)set), sampleType)) == null ? Collections.unmodifiableSet(treeSet) : Collections.unmodifiableSet(((FixedDecimalSamples)((Object)set)).addSamples(treeSet));
            return set;
        }
        int n = this.isLimited((String)((Object)set), sampleType) ? Integer.MAX_VALUE : 20;
        int n2 = 2.$SwitchMap$android$icu$text$PluralRules$SampleType[sampleType.ordinal()];
        if (n2 != 1) {
            if (n2 == 2) {
                for (n2 = 0; n2 < 2000 && this.addSample((String)((Object)set), new FixedDecimal((double)n2 / 10.0, 1), n, (Set<Double>)treeSet); ++n2) {
                }
                this.addSample((String)((Object)set), new FixedDecimal(1000000.0, 1), n, (Set<Double>)treeSet);
            }
        } else {
            for (n2 = 0; n2 < 200 && this.addSample((String)((Object)set), n2, n, (Set<Double>)treeSet); ++n2) {
            }
            this.addSample((String)((Object)set), 1000000, n, (Set<Double>)treeSet);
        }
        set = treeSet.size() == 0 ? var4_4 : Collections.unmodifiableSet(treeSet);
        return set;
    }

    public double getUniqueKeywordValue(String object) {
        if ((object = this.getAllKeywordValues((String)object)) != null && object.size() == 1) {
            return (Double)object.iterator().next();
        }
        return -0.00123456777;
    }

    public int hashCode() {
        return this.rules.hashCode();
    }

    @Deprecated
    public Boolean isLimited(String string) {
        return this.rules.isLimited(string, SampleType.INTEGER);
    }

    @Deprecated
    public boolean isLimited(String string, SampleType sampleType) {
        return this.rules.isLimited(string, sampleType);
    }

    @Deprecated
    public boolean matches(FixedDecimal fixedDecimal, String string) {
        return this.rules.select(fixedDecimal, string);
    }

    public String select(double d) {
        return this.rules.select(new FixedDecimal(d));
    }

    @Deprecated
    public String select(double d, int n, long l) {
        return this.rules.select(new FixedDecimal(d, n, l));
    }

    @Deprecated
    public String select(IFixedDecimal iFixedDecimal) {
        return this.rules.select(iFixedDecimal);
    }

    public String toString() {
        return this.rules.toString();
    }

    private static class AndConstraint
    extends BinaryConstraint {
        private static final long serialVersionUID = 7766999779862263523L;

        AndConstraint(Constraint constraint, Constraint constraint2) {
            super(constraint, constraint2);
        }

        @Override
        public boolean isFulfilled(IFixedDecimal iFixedDecimal) {
            boolean bl = this.a.isFulfilled(iFixedDecimal) && this.b.isFulfilled(iFixedDecimal);
            return bl;
        }

        @Override
        public boolean isLimited(SampleType sampleType) {
            boolean bl = this.a.isLimited(sampleType) || this.b.isLimited(sampleType);
            return bl;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.a.toString());
            stringBuilder.append(" and ");
            stringBuilder.append(this.b.toString());
            return stringBuilder.toString();
        }
    }

    private static abstract class BinaryConstraint
    implements Constraint,
    Serializable {
        private static final long serialVersionUID = 1L;
        protected final Constraint a;
        protected final Constraint b;

        protected BinaryConstraint(Constraint constraint, Constraint constraint2) {
            this.a = constraint;
            this.b = constraint2;
        }
    }

    private static interface Constraint
    extends Serializable {
        public boolean isFulfilled(IFixedDecimal var1);

        public boolean isLimited(SampleType var1);
    }

    @Deprecated
    public static abstract class Factory {
        @Deprecated
        protected Factory() {
        }

        @Deprecated
        public static PluralRulesLoader getDefaultFactory() {
            return PluralRulesLoader.loader;
        }

        @Deprecated
        public final PluralRules forLocale(ULocale uLocale) {
            return this.forLocale(uLocale, PluralType.CARDINAL);
        }

        @Deprecated
        public abstract PluralRules forLocale(ULocale var1, PluralType var2);

        @Deprecated
        public abstract ULocale[] getAvailableULocales();

        @Deprecated
        public abstract ULocale getFunctionalEquivalent(ULocale var1, boolean[] var2);

        @Deprecated
        public abstract boolean hasOverride(ULocale var1);
    }

    @Deprecated
    public static class FixedDecimal
    extends Number
    implements Comparable<FixedDecimal>,
    IFixedDecimal {
        static final long MAX = 1000000000000000000L;
        private static final long MAX_INTEGER_PART = 1000000000L;
        private static final long serialVersionUID = -4756200506571685661L;
        private final int baseFactor;
        final long decimalDigits;
        final long decimalDigitsWithoutTrailingZeros;
        final boolean hasIntegerValue;
        final long integerValue;
        final boolean isNegative;
        final double source;
        final int visibleDecimalDigitCount;
        final int visibleDecimalDigitCountWithoutTrailingZeros;

        @Deprecated
        public FixedDecimal(double d) {
            this(d, FixedDecimal.decimals(d));
        }

        @Deprecated
        public FixedDecimal(double d, int n) {
            this(d, n, FixedDecimal.getFractionalDigits(d, n));
        }

        @Deprecated
        public FixedDecimal(double d, int n, long l) {
            boolean bl = true;
            boolean bl2 = d < 0.0;
            this.isNegative = bl2;
            double d2 = this.isNegative ? -d : d;
            this.source = d2;
            this.visibleDecimalDigitCount = n;
            this.decimalDigits = l;
            long l2 = d > 1.0E18 ? 1000000000000000000L : (long)d;
            this.integerValue = l2;
            bl2 = this.source == (double)this.integerValue ? bl : false;
            this.hasIntegerValue = bl2;
            if (l == 0L) {
                this.decimalDigitsWithoutTrailingZeros = 0L;
                this.visibleDecimalDigitCountWithoutTrailingZeros = 0;
            } else {
                int n2 = n;
                while (l % 10L == 0L) {
                    l /= 10L;
                    --n2;
                }
                this.decimalDigitsWithoutTrailingZeros = l;
                this.visibleDecimalDigitCountWithoutTrailingZeros = n2;
            }
            this.baseFactor = (int)Math.pow(10.0, n);
        }

        @Deprecated
        public FixedDecimal(long l) {
            this(l, 0);
        }

        @Deprecated
        public FixedDecimal(String string) {
            this(Double.parseDouble(string), FixedDecimal.getVisibleFractionCount(string));
        }

        @Deprecated
        public static int decimals(double d) {
            if (!Double.isInfinite(d) && !Double.isNaN(d)) {
                int n;
                double d2 = d;
                if (d < 0.0) {
                    d2 = -d;
                }
                if (d2 == Math.floor(d2)) {
                    return 0;
                }
                if (d2 < 1.0E9) {
                    long l = (long)(1000000.0 * d2);
                    int n2 = 10;
                    for (int i = 6; i > 0; --i) {
                        if (l % 1000000L % (long)n2 != 0L) {
                            return i;
                        }
                        n2 *= 10;
                    }
                    return 0;
                }
                String string = String.format(Locale.ENGLISH, "%1.15e", d2);
                int n3 = string.lastIndexOf(101);
                int n4 = n = n3 + 1;
                if (string.charAt(n) == '+') {
                    n4 = n + 1;
                }
                if ((n4 = n3 - 2 - Integer.parseInt(string.substring(n4))) < 0) {
                    return 0;
                }
                n = n3 - 1;
                while (n4 > 0 && string.charAt(n) == '0') {
                    --n4;
                    --n;
                }
                return n4;
            }
            return 0;
        }

        private static int getFractionalDigits(double d, int n) {
            if (n == 0) {
                return 0;
            }
            double d2 = d;
            if (d < 0.0) {
                d2 = -d;
            }
            n = (int)Math.pow(10.0, n);
            return (int)(Math.round((double)n * d2) % (long)n);
        }

        @Deprecated
        public static Operand getOperand(String string) {
            return Operand.valueOf(string);
        }

        private static int getVisibleFractionCount(String string) {
            int n = (string = string.trim()).indexOf(46) + 1;
            if (n == 0) {
                return 0;
            }
            return string.length() - n;
        }

        private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
            throw new NotSerializableException();
        }

        private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
            throw new NotSerializableException();
        }

        @Deprecated
        @Override
        public int compareTo(FixedDecimal fixedDecimal) {
            long l = this.integerValue;
            long l2 = fixedDecimal.integerValue;
            int n = -1;
            if (l != l2) {
                if (l >= l2) {
                    n = 1;
                }
                return n;
            }
            double d = this.source;
            double d2 = fixedDecimal.source;
            if (d != d2) {
                if (!(d < d2)) {
                    n = 1;
                }
                return n;
            }
            int n2 = this.visibleDecimalDigitCount;
            int n3 = fixedDecimal.visibleDecimalDigitCount;
            if (n2 != n3) {
                if (n2 >= n3) {
                    n = 1;
                }
                return n;
            }
            l = this.decimalDigits - fixedDecimal.decimalDigits;
            if (l != 0L) {
                if (l >= 0L) {
                    n = 1;
                }
                return n;
            }
            return 0;
        }

        @Deprecated
        @Override
        public double doubleValue() {
            double d = this.isNegative ? -this.source : this.source;
            return d;
        }

        @Deprecated
        public boolean equals(Object object) {
            boolean bl = false;
            if (object == null) {
                return false;
            }
            if (object == this) {
                return true;
            }
            if (!(object instanceof FixedDecimal)) {
                return false;
            }
            object = (FixedDecimal)object;
            boolean bl2 = bl;
            if (this.source == ((FixedDecimal)object).source) {
                bl2 = bl;
                if (this.visibleDecimalDigitCount == ((FixedDecimal)object).visibleDecimalDigitCount) {
                    bl2 = bl;
                    if (this.decimalDigits == ((FixedDecimal)object).decimalDigits) {
                        bl2 = true;
                    }
                }
            }
            return bl2;
        }

        @Deprecated
        @Override
        public float floatValue() {
            return (float)this.source;
        }

        @Deprecated
        public int getBaseFactor() {
            return this.baseFactor;
        }

        @Deprecated
        public long getDecimalDigits() {
            return this.decimalDigits;
        }

        @Deprecated
        public long getDecimalDigitsWithoutTrailingZeros() {
            return this.decimalDigitsWithoutTrailingZeros;
        }

        @Deprecated
        public long getIntegerValue() {
            return this.integerValue;
        }

        @Deprecated
        @Override
        public double getPluralOperand(Operand operand) {
            switch (operand) {
                default: {
                    return this.source;
                }
                case w: {
                    return this.visibleDecimalDigitCountWithoutTrailingZeros;
                }
                case v: {
                    return this.visibleDecimalDigitCount;
                }
                case t: {
                    return this.decimalDigitsWithoutTrailingZeros;
                }
                case f: {
                    return this.decimalDigits;
                }
                case i: {
                    return this.integerValue;
                }
                case n: 
            }
            return this.source;
        }

        @Deprecated
        public long getShiftedValue() {
            return this.integerValue * (long)this.baseFactor + this.decimalDigits;
        }

        @Deprecated
        public double getSource() {
            return this.source;
        }

        @Deprecated
        public int getVisibleDecimalDigitCount() {
            return this.visibleDecimalDigitCount;
        }

        @Deprecated
        public int getVisibleDecimalDigitCountWithoutTrailingZeros() {
            return this.visibleDecimalDigitCountWithoutTrailingZeros;
        }

        @Deprecated
        public boolean hasIntegerValue() {
            return this.hasIntegerValue;
        }

        @Deprecated
        public int hashCode() {
            return (int)(this.decimalDigits + (long)((this.visibleDecimalDigitCount + (int)(this.source * 37.0)) * 37));
        }

        @Deprecated
        @Override
        public int intValue() {
            return (int)this.integerValue;
        }

        @Deprecated
        public boolean isHasIntegerValue() {
            return this.hasIntegerValue;
        }

        @Deprecated
        @Override
        public boolean isInfinite() {
            return Double.isInfinite(this.source);
        }

        @Deprecated
        @Override
        public boolean isNaN() {
            return Double.isNaN(this.source);
        }

        @Deprecated
        public boolean isNegative() {
            return this.isNegative;
        }

        @Deprecated
        @Override
        public long longValue() {
            return this.integerValue;
        }

        @Deprecated
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("%.");
            stringBuilder.append(this.visibleDecimalDigitCount);
            stringBuilder.append("f");
            return String.format(stringBuilder.toString(), this.source);
        }
    }

    @Deprecated
    public static class FixedDecimalRange {
        @Deprecated
        public final FixedDecimal end;
        @Deprecated
        public final FixedDecimal start;

        @Deprecated
        public FixedDecimalRange(FixedDecimal fixedDecimal, FixedDecimal fixedDecimal2) {
            if (fixedDecimal.visibleDecimalDigitCount == fixedDecimal2.visibleDecimalDigitCount) {
                this.start = fixedDecimal;
                this.end = fixedDecimal2;
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Ranges must have the same number of visible decimals: ");
            stringBuilder.append(fixedDecimal);
            stringBuilder.append("~");
            stringBuilder.append(fixedDecimal2);
            throw new IllegalArgumentException(stringBuilder.toString());
        }

        @Deprecated
        public String toString() {
            CharSequence charSequence;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.start);
            if (this.end == this.start) {
                charSequence = "";
            } else {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("~");
                ((StringBuilder)charSequence).append(this.end);
                charSequence = ((StringBuilder)charSequence).toString();
            }
            stringBuilder.append((String)charSequence);
            return stringBuilder.toString();
        }
    }

    @Deprecated
    public static class FixedDecimalSamples {
        @Deprecated
        public final boolean bounded;
        @Deprecated
        public final SampleType sampleType;
        @Deprecated
        public final Set<FixedDecimalRange> samples;

        private FixedDecimalSamples(SampleType sampleType, Set<FixedDecimalRange> set, boolean bl) {
            this.sampleType = sampleType;
            this.samples = set;
            this.bounded = bl;
        }

        private static void checkDecimal(SampleType object, FixedDecimal fixedDecimal) {
            SampleType sampleType = SampleType.INTEGER;
            boolean bl = true;
            boolean bl2 = object == sampleType;
            if (fixedDecimal.getVisibleDecimalDigitCount() != 0) {
                bl = false;
            }
            if (bl2 == bl) {
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Ill-formed number range: ");
            ((StringBuilder)object).append(fixedDecimal);
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }

        static FixedDecimalSamples parse(String object) {
            block10 : {
                SampleType sampleType;
                LinkedHashSet<FixedDecimalRange> linkedHashSet;
                block9 : {
                    block8 : {
                        linkedHashSet = new LinkedHashSet<FixedDecimalRange>();
                        if (!((String)object).startsWith("integer")) break block8;
                        sampleType = SampleType.INTEGER;
                        break block9;
                    }
                    if (!((String)object).startsWith("decimal")) break block10;
                    sampleType = SampleType.DECIMAL;
                }
                object = ((String)object).substring(7).trim();
                object = COMMA_SEPARATED.split((CharSequence)object);
                int n = ((String[])object).length;
                boolean bl = false;
                boolean bl2 = true;
                for (int i = 0; i < n; ++i) {
                    Object object2 = object[i];
                    if (!((String)object2).equals("\u2026") && !((String)object2).equals("...")) {
                        if (!bl) {
                            Object object3 = TILDE_SEPARATED.split((CharSequence)object2);
                            int n2 = ((String[])object3).length;
                            if (n2 != 1) {
                                if (n2 == 2) {
                                    object2 = new FixedDecimal(object3[0]);
                                    object3 = new FixedDecimal(object3[1]);
                                    FixedDecimalSamples.checkDecimal(sampleType, (FixedDecimal)object2);
                                    FixedDecimalSamples.checkDecimal(sampleType, (FixedDecimal)object3);
                                    linkedHashSet.add(new FixedDecimalRange((FixedDecimal)object2, (FixedDecimal)object3));
                                    continue;
                                }
                                object = new StringBuilder();
                                ((StringBuilder)object).append("Ill-formed number range: ");
                                ((StringBuilder)object).append((String)object2);
                                throw new IllegalArgumentException(((StringBuilder)object).toString());
                            }
                            object2 = new FixedDecimal(object3[0]);
                            FixedDecimalSamples.checkDecimal(sampleType, (FixedDecimal)object2);
                            linkedHashSet.add(new FixedDecimalRange((FixedDecimal)object2, (FixedDecimal)object2));
                            continue;
                        }
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Can only have \u2026 at the end of samples: ");
                        ((StringBuilder)object).append((String)object2);
                        throw new IllegalArgumentException(((StringBuilder)object).toString());
                    }
                    bl2 = false;
                    bl = true;
                }
                return new FixedDecimalSamples(sampleType, Collections.unmodifiableSet(linkedHashSet), bl2);
            }
            throw new IllegalArgumentException("Samples must start with 'integer' or 'decimal'");
        }

        @Deprecated
        public Set<Double> addSamples(Set<Double> set) {
            for (FixedDecimalRange fixedDecimalRange : this.samples) {
                long l = fixedDecimalRange.end.getShiftedValue();
                for (long i = fixedDecimalRange.start.getShiftedValue(); i <= l; ++i) {
                    set.add((double)i / (double)fixedDecimalRange.start.baseFactor);
                }
            }
            return set;
        }

        @Deprecated
        public Set<FixedDecimalRange> getSamples() {
            return this.samples;
        }

        @Deprecated
        public void getStartEndSamples(Set<FixedDecimal> set) {
            for (FixedDecimalRange fixedDecimalRange : this.samples) {
                set.add(fixedDecimalRange.start);
                set.add(fixedDecimalRange.end);
            }
        }

        @Deprecated
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder("@").append(this.sampleType.toString().toLowerCase(Locale.ENGLISH));
            boolean bl = true;
            for (FixedDecimalRange fixedDecimalRange : this.samples) {
                if (bl) {
                    bl = false;
                } else {
                    stringBuilder.append(",");
                }
                stringBuilder.append(' ');
                stringBuilder.append(fixedDecimalRange);
            }
            if (!this.bounded) {
                stringBuilder.append(", \u2026");
            }
            return stringBuilder.toString();
        }
    }

    @Deprecated
    public static interface IFixedDecimal {
        @Deprecated
        public double getPluralOperand(Operand var1);

        @Deprecated
        public boolean isInfinite();

        @Deprecated
        public boolean isNaN();
    }

    public static enum KeywordStatus {
        INVALID,
        SUPPRESSED,
        UNIQUE,
        BOUNDED,
        UNBOUNDED;
        
    }

    @Deprecated
    public static enum Operand {
        n,
        i,
        f,
        t,
        v,
        w,
        j;
        
    }

    private static class OrConstraint
    extends BinaryConstraint {
        private static final long serialVersionUID = 1405488568664762222L;

        OrConstraint(Constraint constraint, Constraint constraint2) {
            super(constraint, constraint2);
        }

        @Override
        public boolean isFulfilled(IFixedDecimal iFixedDecimal) {
            boolean bl = this.a.isFulfilled(iFixedDecimal) || this.b.isFulfilled(iFixedDecimal);
            return bl;
        }

        @Override
        public boolean isLimited(SampleType sampleType) {
            boolean bl = this.a.isLimited(sampleType) && this.b.isLimited(sampleType);
            return bl;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.a.toString());
            stringBuilder.append(" or ");
            stringBuilder.append(this.b.toString());
            return stringBuilder.toString();
        }
    }

    public static enum PluralType {
        CARDINAL,
        ORDINAL;
        
    }

    private static class RangeConstraint
    implements Constraint,
    Serializable {
        private static final long serialVersionUID = 1L;
        private final boolean inRange;
        private final boolean integersOnly;
        private final double lowerBound;
        private final int mod;
        private final Operand operand;
        private final long[] range_list;
        private final double upperBound;

        RangeConstraint(int n, boolean bl, Operand operand, boolean bl2, double d, double d2, long[] arrl) {
            this.mod = n;
            this.inRange = bl;
            this.integersOnly = bl2;
            this.lowerBound = d;
            this.upperBound = d2;
            this.range_list = arrl;
            this.operand = operand;
        }

        @Override
        public boolean isFulfilled(IFixedDecimal arrl) {
            double d = arrl.getPluralOperand(this.operand);
            if (this.integersOnly && d - (double)((long)d) != 0.0 || this.operand == Operand.j && arrl.getPluralOperand(Operand.v) != 0.0) {
                return this.inRange ^ true;
            }
            int n = this.mod;
            double d2 = d;
            if (n != 0) {
                d2 = d % (double)n;
            }
            d = this.lowerBound;
            boolean bl = false;
            boolean bl2 = d2 >= d && d2 <= this.upperBound;
            boolean bl3 = bl2;
            if (bl2) {
                bl3 = bl2;
                if (this.range_list != null) {
                    bl2 = false;
                    n = 0;
                    do {
                        bl3 = bl2;
                        if (bl2) break;
                        arrl = this.range_list;
                        bl3 = bl2;
                        if (n >= arrl.length) break;
                        bl2 = d2 >= (double)arrl[n] && d2 <= (double)arrl[n + 1];
                        n += 2;
                    } while (true);
                }
            }
            bl2 = bl;
            if (this.inRange == bl3) {
                bl2 = true;
            }
            return bl2;
        }

        @Override
        public boolean isLimited(SampleType sampleType) {
            double d = this.lowerBound;
            double d2 = this.upperBound;
            boolean bl = true;
            boolean bl2 = true;
            boolean bl3 = d == d2 && d == 0.0;
            boolean bl4 = (this.operand == Operand.v || this.operand == Operand.w || this.operand == Operand.f || this.operand == Operand.t) && this.inRange != bl3;
            int n = 2.$SwitchMap$android$icu$text$PluralRules$SampleType[sampleType.ordinal()];
            if (n != 1) {
                if (n != 2) {
                    return false;
                }
                bl3 = !(bl4 && this.operand != Operand.n && this.operand != Operand.j || !this.integersOnly && this.lowerBound != this.upperBound || this.mod != 0 || !this.inRange) ? bl2 : false;
                return bl3;
            }
            bl3 = bl;
            if (!bl4) {
                bl3 = (this.operand == Operand.n || this.operand == Operand.i || this.operand == Operand.j) && this.mod == 0 && this.inRange ? bl : false;
            }
            return bl3;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append((Object)this.operand);
            if (this.mod != 0) {
                stringBuilder.append(" % ");
                stringBuilder.append(this.mod);
            }
            int n = this.lowerBound != this.upperBound ? 1 : 0;
            Object object = " = ";
            if (n == 0) {
                if (!this.inRange) {
                    object = " != ";
                }
            } else if (this.integersOnly) {
                if (!this.inRange) {
                    object = " != ";
                }
            } else {
                object = this.inRange ? " within " : " not within ";
            }
            stringBuilder.append((String)object);
            if (this.range_list != null) {
                for (n = 0; n < ((long[])(object = this.range_list)).length; n += 2) {
                    double d = object[n];
                    double d2 = object[n + 1];
                    boolean bl = n != 0;
                    PluralRules.addRange(stringBuilder, d, d2, bl);
                }
            } else {
                PluralRules.addRange(stringBuilder, this.lowerBound, this.upperBound, false);
            }
            return stringBuilder.toString();
        }
    }

    private static class Rule
    implements Serializable {
        private static final long serialVersionUID = 1L;
        private final Constraint constraint;
        private final FixedDecimalSamples decimalSamples;
        private final FixedDecimalSamples integerSamples;
        private final String keyword;

        public Rule(String string, Constraint constraint, FixedDecimalSamples fixedDecimalSamples, FixedDecimalSamples fixedDecimalSamples2) {
            this.keyword = string;
            this.constraint = constraint;
            this.integerSamples = fixedDecimalSamples;
            this.decimalSamples = fixedDecimalSamples2;
        }

        public Rule and(Constraint constraint) {
            return new Rule(this.keyword, new AndConstraint(this.constraint, constraint), this.integerSamples, this.decimalSamples);
        }

        public boolean appliesTo(IFixedDecimal iFixedDecimal) {
            return this.constraint.isFulfilled(iFixedDecimal);
        }

        public String getConstraint() {
            return this.constraint.toString();
        }

        public String getKeyword() {
            return this.keyword;
        }

        public int hashCode() {
            return this.keyword.hashCode() ^ this.constraint.hashCode();
        }

        public boolean isLimited(SampleType sampleType) {
            return this.constraint.isLimited(sampleType);
        }

        public Rule or(Constraint constraint) {
            return new Rule(this.keyword, new OrConstraint(this.constraint, constraint), this.integerSamples, this.decimalSamples);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.keyword);
            stringBuilder.append(PluralRules.KEYWORD_RULE_SEPARATOR);
            stringBuilder.append(this.constraint.toString());
            Object object = this.integerSamples;
            String string = "";
            if (object == null) {
                object = "";
            } else {
                object = new StringBuilder();
                ((StringBuilder)object).append(" ");
                ((StringBuilder)object).append(this.integerSamples.toString());
                object = ((StringBuilder)object).toString();
            }
            stringBuilder.append((String)object);
            if (this.decimalSamples == null) {
                object = string;
            } else {
                object = new StringBuilder();
                ((StringBuilder)object).append(" ");
                ((StringBuilder)object).append(this.decimalSamples.toString());
                object = ((StringBuilder)object).toString();
            }
            stringBuilder.append((String)object);
            return stringBuilder.toString();
        }
    }

    private static class RuleList
    implements Serializable {
        private static final long serialVersionUID = 1L;
        private boolean hasExplicitBoundingInfo = false;
        private final List<Rule> rules = new ArrayList<Rule>();

        private RuleList() {
        }

        static /* synthetic */ boolean access$276(RuleList ruleList, int n) {
            boolean bl;
            ruleList.hasExplicitBoundingInfo = bl = (byte)(ruleList.hasExplicitBoundingInfo | n);
            return bl;
        }

        private Rule selectRule(IFixedDecimal iFixedDecimal) {
            for (Rule rule : this.rules) {
                if (!rule.appliesTo(iFixedDecimal)) continue;
                return rule;
            }
            return null;
        }

        public RuleList addRule(Rule serializable) {
            String string = ((Rule)serializable).getKeyword();
            Iterator<Rule> iterator = this.rules.iterator();
            while (iterator.hasNext()) {
                if (!string.equals(iterator.next().getKeyword())) continue;
                serializable = new StringBuilder();
                ((StringBuilder)serializable).append("Duplicate keyword: ");
                ((StringBuilder)serializable).append(string);
                throw new IllegalArgumentException(((StringBuilder)serializable).toString());
            }
            this.rules.add((Rule)serializable);
            return this;
        }

        public boolean computeLimited(String string, SampleType sampleType) {
            boolean bl = false;
            for (Rule rule : this.rules) {
                if (!string.equals(rule.getKeyword())) continue;
                if (!rule.isLimited(sampleType)) {
                    return false;
                }
                bl = true;
            }
            return bl;
        }

        public RuleList finish() throws ParseException {
            Rule rule;
            Rule rule2 = null;
            Iterator<Rule> iterator = this.rules.iterator();
            while (iterator.hasNext()) {
                rule = iterator.next();
                if (!PluralRules.KEYWORD_OTHER.equals(rule.getKeyword())) continue;
                rule2 = rule;
                iterator.remove();
            }
            rule = rule2;
            if (rule2 == null) {
                rule = PluralRules.parseRule("other:");
            }
            this.rules.add(rule);
            return this;
        }

        public FixedDecimalSamples getDecimalSamples(String object, SampleType sampleType) {
            for (Rule rule : this.rules) {
                if (!rule.getKeyword().equals(object)) continue;
                object = sampleType == SampleType.INTEGER ? rule.integerSamples : rule.decimalSamples;
                return object;
            }
            return null;
        }

        public Set<String> getKeywords() {
            LinkedHashSet<String> linkedHashSet = new LinkedHashSet<String>();
            Iterator<Rule> iterator = this.rules.iterator();
            while (iterator.hasNext()) {
                linkedHashSet.add(iterator.next().getKeyword());
            }
            return linkedHashSet;
        }

        public String getRules(String string) {
            for (Rule rule : this.rules) {
                if (!rule.getKeyword().equals(string)) continue;
                return rule.getConstraint();
            }
            return null;
        }

        public boolean isLimited(String object, SampleType sampleType) {
            if (this.hasExplicitBoundingInfo) {
                boolean bl = (object = this.getDecimalSamples((String)object, sampleType)) == null ? true : ((FixedDecimalSamples)object).bounded;
                return bl;
            }
            return this.computeLimited((String)object, sampleType);
        }

        public String select(IFixedDecimal iFixedDecimal) {
            if (!iFixedDecimal.isInfinite() && !iFixedDecimal.isNaN()) {
                return this.selectRule(iFixedDecimal).getKeyword();
            }
            return PluralRules.KEYWORD_OTHER;
        }

        public boolean select(IFixedDecimal iFixedDecimal, String string) {
            for (Rule rule : this.rules) {
                if (!rule.getKeyword().equals(string) || !rule.appliesTo(iFixedDecimal)) continue;
                return true;
            }
            return false;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            for (Rule rule : this.rules) {
                if (stringBuilder.length() != 0) {
                    stringBuilder.append(PluralRules.CATEGORY_SEPARATOR);
                }
                stringBuilder.append(rule);
            }
            return stringBuilder.toString();
        }
    }

    @Deprecated
    public static enum SampleType {
        INTEGER,
        DECIMAL;
        
    }

    static class SimpleTokenizer {
        static final UnicodeSet BREAK_AND_IGNORE = new UnicodeSet(9, 10, 12, 13, 32, 32).freeze();
        static final UnicodeSet BREAK_AND_KEEP = new UnicodeSet(33, 33, 37, 37, 44, 44, 46, 46, 61, 61).freeze();

        SimpleTokenizer() {
        }

        static String[] split(String string) {
            int n = -1;
            ArrayList<String> arrayList = new ArrayList<String>();
            for (int i = 0; i < string.length(); ++i) {
                int n2 = string.charAt(i);
                if (BREAK_AND_IGNORE.contains(n2)) {
                    n2 = n;
                    if (n >= 0) {
                        arrayList.add(string.substring(n, i));
                        n2 = -1;
                    }
                } else if (BREAK_AND_KEEP.contains(n2)) {
                    if (n >= 0) {
                        arrayList.add(string.substring(n, i));
                    }
                    arrayList.add(string.substring(i, i + 1));
                    n2 = -1;
                } else {
                    n2 = n;
                    if (n < 0) {
                        n2 = i;
                    }
                }
                n = n2;
            }
            if (n >= 0) {
                arrayList.add(string.substring(n));
            }
            return arrayList.toArray(new String[arrayList.size()]);
        }
    }

}

