/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.impl.PatternProps;
import android.icu.number.LocalizedNumberFormatter;
import android.icu.number.NumberFormatter;
import android.icu.text.DateFormat;
import android.icu.text.DecimalFormat;
import android.icu.text.DecimalFormatSymbols;
import android.icu.text.MessagePattern;
import android.icu.text.NumberFormat;
import android.icu.text.PluralFormat;
import android.icu.text.PluralRules;
import android.icu.text.RuleBasedNumberFormat;
import android.icu.text.SelectFormat;
import android.icu.text.SimpleDateFormat;
import android.icu.text.UFormat;
import android.icu.util.ICUUncheckedIOException;
import android.icu.util.ULocale;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.text.CharacterIterator;
import java.text.ChoiceFormat;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class MessageFormat
extends UFormat {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final char CURLY_BRACE_LEFT = '{';
    private static final char CURLY_BRACE_RIGHT = '}';
    private static final int DATE_MODIFIER_EMPTY = 0;
    private static final int DATE_MODIFIER_FULL = 4;
    private static final int DATE_MODIFIER_LONG = 3;
    private static final int DATE_MODIFIER_MEDIUM = 2;
    private static final int DATE_MODIFIER_SHORT = 1;
    private static final int MODIFIER_CURRENCY = 1;
    private static final int MODIFIER_EMPTY = 0;
    private static final int MODIFIER_INTEGER = 3;
    private static final int MODIFIER_PERCENT = 2;
    private static final char SINGLE_QUOTE = '\'';
    private static final int STATE_INITIAL = 0;
    private static final int STATE_IN_QUOTE = 2;
    private static final int STATE_MSG_ELEMENT = 3;
    private static final int STATE_SINGLE_QUOTE = 1;
    private static final int TYPE_DATE = 1;
    private static final int TYPE_DURATION = 5;
    private static final int TYPE_NUMBER = 0;
    private static final int TYPE_ORDINAL = 4;
    private static final int TYPE_SPELLOUT = 3;
    private static final int TYPE_TIME = 2;
    private static final String[] dateModifierList;
    private static final String[] modifierList;
    private static final Locale rootLocale;
    static final long serialVersionUID = 7136212545847378652L;
    private static final String[] typeList;
    private transient Map<Integer, Format> cachedFormatters;
    private transient Set<Integer> customFormatArgStarts;
    private transient MessagePattern msgPattern;
    private transient PluralSelectorProvider ordinalProvider;
    private transient PluralSelectorProvider pluralProvider;
    private transient DateFormat stockDateFormatter;
    private transient NumberFormat stockNumberFormatter;
    private transient ULocale ulocale;

    static {
        typeList = new String[]{"number", "date", "time", "spellout", "ordinal", "duration"};
        modifierList = new String[]{"", "currency", "percent", "integer"};
        dateModifierList = new String[]{"", "short", "medium", "long", "full"};
        rootLocale = new Locale("");
    }

    public MessageFormat(String string) {
        this.ulocale = ULocale.getDefault(ULocale.Category.FORMAT);
        this.applyPattern(string);
    }

    public MessageFormat(String string, ULocale uLocale) {
        this.ulocale = uLocale;
        this.applyPattern(string);
    }

    public MessageFormat(String string, Locale locale) {
        this(string, ULocale.forLocale(locale));
    }

    private boolean argNameMatches(int n, String string, int n2) {
        MessagePattern.Part part = this.msgPattern.getPart(n);
        boolean bl = part.getType() == MessagePattern.Part.Type.ARG_NAME ? this.msgPattern.partSubstringMatches(part, string) : part.getValue() == n2;
        return bl;
    }

    public static String autoQuoteApostrophe(String string) {
        StringBuilder stringBuilder = new StringBuilder(string.length() * 2);
        int n = 0;
        int n2 = 0;
        int n3 = string.length();
        for (int i = 0; i < n3; ++i) {
            char c = string.charAt(i);
            if (n != 0) {
                if (n != 1) {
                    if (n != 2) {
                        if (n == 3) {
                            if (c != '{') {
                                if (c == '}') {
                                    int n4;
                                    n2 = n4 = n2 - 1;
                                    if (n4 == 0) {
                                        n = 0;
                                        n2 = n4;
                                    }
                                }
                            } else {
                                ++n2;
                            }
                        }
                    } else if (c == '\'') {
                        n = 0;
                    }
                } else if (c != '\'') {
                    if (c != '{' && c != '}') {
                        stringBuilder.append('\'');
                        n = 0;
                    } else {
                        n = 2;
                    }
                } else {
                    n = 0;
                }
            } else if (c != '\'') {
                if (c == '{') {
                    n = 3;
                    ++n2;
                }
            } else {
                n = 1;
            }
            stringBuilder.append(c);
        }
        if (n == 1 || n == 2) {
            stringBuilder.append('\'');
        }
        return new String(stringBuilder);
    }

    private void cacheExplicitFormats() {
        Object object = this.cachedFormatters;
        if (object != null) {
            object.clear();
        }
        this.customFormatArgStarts = null;
        int n = this.msgPattern.countParts();
        for (int i = 1; i < n - 2; ++i) {
            object = this.msgPattern.getPart(i);
            if (((MessagePattern.Part)object).getType() != MessagePattern.Part.Type.ARG_START || ((MessagePattern.Part)object).getArgType() != MessagePattern.ArgType.SIMPLE) continue;
            int n2 = i + 2;
            object = this.msgPattern;
            int n3 = n2 + 1;
            String string = ((MessagePattern)object).getSubstring(((MessagePattern)object).getPart(n2));
            object = "";
            MessagePattern.Part part = this.msgPattern.getPart(n3);
            n2 = n3;
            if (part.getType() == MessagePattern.Part.Type.ARG_STYLE) {
                object = this.msgPattern.getSubstring(part);
                n2 = n3 + 1;
            }
            this.setArgStartFormat(i, this.createAppropriateFormat(string, (String)object));
            i = n2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private Format createAppropriateFormat(String object, String charSequence) {
        int n = MessageFormat.findKeyword((String)object, typeList);
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            if (n != 5) {
                                charSequence = new StringBuilder();
                                ((StringBuilder)charSequence).append("Unknown format type \"");
                                ((StringBuilder)charSequence).append((String)object);
                                ((StringBuilder)charSequence).append("\"");
                                throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
                            }
                            object = new RuleBasedNumberFormat(this.ulocale, 3);
                            if (((String)(charSequence = ((String)charSequence).trim())).length() == 0) return object;
                            try {
                                ((RuleBasedNumberFormat)object).setDefaultRuleSet((String)charSequence);
                                return object;
                            }
                            catch (Exception exception) {
                                return object;
                            }
                        }
                        object = new RuleBasedNumberFormat(this.ulocale, 2);
                        if (((String)(charSequence = ((String)charSequence).trim())).length() == 0) return object;
                        try {
                            ((RuleBasedNumberFormat)object).setDefaultRuleSet((String)charSequence);
                            return object;
                        }
                        catch (Exception exception) {
                            return object;
                        }
                    }
                    object = new RuleBasedNumberFormat(this.ulocale, 1);
                    if (((String)(charSequence = ((String)charSequence).trim())).length() == 0) return object;
                    try {
                        ((RuleBasedNumberFormat)object).setDefaultRuleSet((String)charSequence);
                        return object;
                    }
                    catch (Exception exception) {
                        return object;
                    }
                }
                n = MessageFormat.findKeyword((String)charSequence, dateModifierList);
                if (n == 0) {
                    return DateFormat.getTimeInstance(2, this.ulocale);
                }
                if (n == 1) {
                    return DateFormat.getTimeInstance(3, this.ulocale);
                }
                if (n == 2) {
                    return DateFormat.getTimeInstance(2, this.ulocale);
                }
                if (n == 3) {
                    return DateFormat.getTimeInstance(1, this.ulocale);
                }
                if (n == 4) return DateFormat.getTimeInstance(0, this.ulocale);
                return new SimpleDateFormat((String)charSequence, this.ulocale);
            }
            n = MessageFormat.findKeyword((String)charSequence, dateModifierList);
            if (n == 0) {
                return DateFormat.getDateInstance(2, this.ulocale);
            }
            if (n == 1) {
                return DateFormat.getDateInstance(3, this.ulocale);
            }
            if (n == 2) {
                return DateFormat.getDateInstance(2, this.ulocale);
            }
            if (n == 3) {
                return DateFormat.getDateInstance(1, this.ulocale);
            }
            if (n == 4) return DateFormat.getDateInstance(0, this.ulocale);
            return new SimpleDateFormat((String)charSequence, this.ulocale);
        }
        n = MessageFormat.findKeyword((String)charSequence, modifierList);
        if (n == 0) {
            return NumberFormat.getInstance(this.ulocale);
        }
        if (n == 1) {
            return NumberFormat.getCurrencyInstance(this.ulocale);
        }
        if (n == 2) {
            return NumberFormat.getPercentInstance(this.ulocale);
        }
        if (n == 3) {
            return NumberFormat.getIntegerInstance(this.ulocale);
        }
        n = 0;
        while (PatternProps.isWhiteSpace(((String)charSequence).charAt(n))) {
            ++n;
        }
        if (!((String)charSequence).regionMatches(n, "::", 0, 2)) return new DecimalFormat((String)charSequence, new DecimalFormatSymbols(this.ulocale));
        return NumberFormatter.forSkeleton(((String)charSequence).substring(n + 2)).locale(this.ulocale).toFormat();
    }

    private static int findChoiceSubMessage(MessagePattern messagePattern, int n, double d) {
        int n2 = messagePattern.countParts();
        n += 2;
        do {
            int n3;
            block4 : {
                block3 : {
                    int n4;
                    if ((n4 = messagePattern.getLimitPartIndex(n) + 1) >= n2) break block3;
                    n3 = n4 + 1;
                    MessagePattern.Part part = messagePattern.getPart(n4);
                    if (part.getType() == MessagePattern.Part.Type.ARG_LIMIT) break block3;
                    double d2 = messagePattern.getNumericValue(part);
                    n4 = messagePattern.getPatternIndex(n3);
                    if (!(messagePattern.getPatternString().charAt(n4) == '<' ? !(d > d2) : !(d >= d2))) break block4;
                }
                return n;
            }
            n = n3 + 1;
        } while (true);
    }

    private int findFirstPluralNumberArg(int n, String string) {
        ++n;
        Object object;
        MessagePattern.Part part;
        while ((object = (part = this.msgPattern.getPart(n)).getType()) != MessagePattern.Part.Type.MSG_LIMIT) {
            if (object == MessagePattern.Part.Type.REPLACE_NUMBER) {
                return -1;
            }
            int n2 = n;
            if (object == MessagePattern.Part.Type.ARG_START) {
                object = part.getArgType();
                if (string.length() != 0 && (object == MessagePattern.ArgType.NONE || object == MessagePattern.ArgType.SIMPLE) && this.msgPattern.partSubstringMatches((MessagePattern.Part)(object = this.msgPattern.getPart(n + 1)), string)) {
                    return n;
                }
                n2 = this.msgPattern.getLimitPartIndex(n);
            }
            n = n2 + 1;
        }
        return 0;
    }

    private static final int findKeyword(String string, String[] arrstring) {
        string = PatternProps.trimWhiteSpace(string).toLowerCase(rootLocale);
        for (int i = 0; i < arrstring.length; ++i) {
            if (!string.equals(arrstring[i])) continue;
            return i;
        }
        return -1;
    }

    private int findOtherSubMessage(int n) {
        int n2 = this.msgPattern.countParts();
        int n3 = n;
        if (this.msgPattern.getPart(n).getType().hasNumericValue()) {
            n3 = n + 1;
        }
        do {
            Object object = this.msgPattern;
            int n4 = n3 + 1;
            if (((MessagePattern.Part)(object = ((MessagePattern)object).getPart(n3))).getType() == MessagePattern.Part.Type.ARG_LIMIT) break;
            if (this.msgPattern.partSubstringMatches((MessagePattern.Part)object, "other")) {
                return n4;
            }
            n = n4;
            if (this.msgPattern.getPartType(n4).hasNumericValue()) {
                n = n4 + 1;
            }
            n3 = n = this.msgPattern.getLimitPartIndex(n) + 1;
        } while (n < n2);
        return 0;
    }

    public static String format(String string, Map<String, Object> map) {
        return new MessageFormat(string).format(map);
    }

    public static String format(String string, Object ... arrobject) {
        return new MessageFormat(string).format(arrobject);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private void format(int var1_1, PluralSelectorContext var2_2, Object[] var3_3, Map<String, Object> var4_4, AppendableWrapper var5_5, FieldPosition var6_6) {
        var7_7 = var5_5;
        var8_8 = this.msgPattern.getPatternString();
        var9_9 = this.msgPattern.getPart(var1_1).getLimit();
        ++var1_1;
        var10_10 = var6_6;
        var6_6 = var7_7;
        var7_7 = var8_8;
        do {
            block43 : {
                block45 : {
                    block44 : {
                        block42 : {
                            block41 : {
                                block40 : {
                                    var11_11 = var2_2;
                                    var8_8 = var3_3;
                                    var12_12 = var4_4;
                                    var13_14 = this.msgPattern.getPart(var1_1);
                                    var14_21 = var13_14.getType();
                                    var6_6.append((CharSequence)var7_7, var9_9, var13_14.getIndex());
                                    if (var14_21 == MessagePattern.Part.Type.MSG_LIMIT) {
                                        return;
                                    }
                                    var15_22 = var13_14.getLimit();
                                    if (var14_21 != MessagePattern.Part.Type.REPLACE_NUMBER) break block40;
                                    if (var11_11.forReplaceNumber) {
                                        var6_6.formatAndAppend(var11_11.formatter, var11_11.number, var11_11.numberString);
                                    } else {
                                        var6_6.formatAndAppend(this.getStockNumberFormatter(), var11_11.number);
                                    }
                                    break block41;
                                }
                                if (var14_21 == MessagePattern.Part.Type.ARG_START) break block42;
                            }
                            var9_9 = var1_1;
                            var1_1 = var15_22;
                            var15_22 = var9_9;
                            break block43;
                        }
                        var15_22 = this.msgPattern.getLimitPartIndex(var1_1);
                        var16_23 = var13_14.getArgType();
                        var13_15 = this.msgPattern;
                        var9_9 = var1_1 + 1;
                        var17_24 = var13_15.getPart(var9_9);
                        var1_1 = 0;
                        var13_16 = null;
                        var14_21 = this.msgPattern.getSubstring(var17_24);
                        if (var8_8 != null) {
                            var18_25 = var17_24.getValue();
                            if (AppendableWrapper.access$000(var5_5) != null) {
                                var13_17 = var18_25;
                            }
                            if (var18_25 >= 0 && var18_25 < ((Object[])var8_8).length) {
                                var8_8 = var8_8[var18_25];
                            } else {
                                var8_8 = null;
                                var1_1 = 1;
                            }
                        } else {
                            var13_19 /* !! */  = var14_21;
                            if (var12_12 != null && var12_12.containsKey(var14_21)) {
                                var8_8 = var12_12.get(var14_21);
                                var1_1 = 0;
                            } else {
                                var8_8 = null;
                                var1_1 = 1;
                            }
                        }
                        var18_25 = var9_9 + 1;
                        var9_9 = AppendableWrapper.access$500(var5_5);
                        if (var1_1 == 0) break block44;
                        var8_8 = new StringBuilder();
                        var8_8.append("{");
                        var8_8.append((String)var14_21);
                        var8_8.append("}");
                        var6_6.append(var8_8.toString());
                        ** GOTO lbl79
                    }
                    if (var8_8 != null) break block45;
                    var6_6.append("null");
                    ** GOTO lbl79
                }
                if (var11_11 != null && var11_11.numberArgIndex == var18_25 - 2) {
                    if (var11_11.offset == 0.0) {
                        var6_6.formatAndAppend(var11_11.formatter, var11_11.number, var11_11.numberString);
                    } else {
                        var6_6.formatAndAppend(var11_11.formatter, var8_8);
                    }
lbl79: // 4 sources:
                    var1_1 = var9_9;
                } else {
                    var11_11 = this.cachedFormatters;
                    if (var11_11 != null && (var11_11 = var11_11.get(var18_25 - 2)) != null) {
                        if (!(var11_11 instanceof ChoiceFormat || var11_11 instanceof PluralFormat || var11_11 instanceof SelectFormat)) {
                            var6_6.formatAndAppend((Format)var11_11, var8_8);
                        } else {
                            var14_21 = var11_11.format(var8_8);
                            if (var14_21.indexOf(123) < 0 && (var14_21.indexOf(39) < 0 || this.msgPattern.jdkAposMode())) {
                                if (AppendableWrapper.access$000(var5_5) == null) {
                                    var6_6.append((CharSequence)var14_21);
                                } else {
                                    var6_6.formatAndAppend((Format)var11_11, var8_8);
                                }
                            } else {
                                new MessageFormat((String)var14_21, this.ulocale).format(0, null, var3_3, var4_4, var5_5, null);
                            }
                        }
                        var1_1 = var9_9;
                        var6_6 = var5_5;
                    } else {
                        var1_1 = var9_9;
                        if (!(var16_23 == MessagePattern.ArgType.NONE || (var6_6 = this.cachedFormatters) != null && var6_6.containsKey(var18_25 - 2))) {
                            if (var16_23 == MessagePattern.ArgType.CHOICE) {
                                if (!(var8_8 instanceof Number)) {
                                    var2_2 = new StringBuilder();
                                    var2_2.append("'");
                                    var2_2.append(var8_8);
                                    var2_2.append("' is not a Number");
                                    throw new IllegalArgumentException(var2_2.toString());
                                }
                                var19_26 = ((Number)var8_8).doubleValue();
                                var9_9 = MessageFormat.findChoiceSubMessage(this.msgPattern, var18_25, var19_26);
                                this.formatComplexSubMessage(var9_9, null, var3_3, var4_4, var5_5);
                                var6_6 = var5_5;
                            } else if (var16_23.hasPluralStyle()) {
                                if (!(var8_8 instanceof Number)) {
                                    var2_2 = new StringBuilder();
                                    var2_2.append("'");
                                    var2_2.append(var8_8);
                                    var2_2.append("' is not a Number");
                                    throw new IllegalArgumentException(var2_2.toString());
                                }
                                if (var16_23 == MessagePattern.ArgType.PLURAL) {
                                    if (this.pluralProvider == null) {
                                        this.pluralProvider = new PluralSelectorProvider(this, PluralRules.PluralType.CARDINAL);
                                    }
                                    var6_6 = this.pluralProvider;
                                } else {
                                    if (this.ordinalProvider == null) {
                                        this.ordinalProvider = new PluralSelectorProvider(this, PluralRules.PluralType.ORDINAL);
                                    }
                                    var6_6 = this.ordinalProvider;
                                }
                                var8_8 = (Number)var8_8;
                                var14_21 = new PluralSelectorContext(var18_25, (String)var14_21, (Number)var8_8, this.msgPattern.getPluralOffset(var18_25));
                                this.formatComplexSubMessage(PluralFormat.findSubMessage(this.msgPattern, var18_25, (PluralFormat.PluralSelector)var6_6, var14_21, var8_8.doubleValue()), (PluralSelectorContext)var14_21, var3_3, var4_4, var5_5);
                                var6_6 = var5_5;
                            } else {
                                if (var16_23 != MessagePattern.ArgType.SELECT) {
                                    var2_2 = new StringBuilder();
                                    var2_2.append("unexpected argType ");
                                    var2_2.append((Object)var16_23);
                                    throw new IllegalStateException(var2_2.toString());
                                }
                                this.formatComplexSubMessage(SelectFormat.findSubMessage(this.msgPattern, var18_25, var8_8.toString()), null, var3_3, var4_4, var5_5);
                                var6_6 = var5_5;
                            }
                        } else if (var8_8 instanceof Number) {
                            var14_21 = this.getStockNumberFormatter();
                            var6_6 = var5_5;
                            var6_6.formatAndAppend((Format)var14_21, var8_8);
                        } else {
                            var6_6 = var5_5;
                            if (var8_8 instanceof Date) {
                                var6_6.formatAndAppend(this.getStockDateFormatter(), var8_8);
                            } else {
                                var6_6.append(var8_8.toString());
                            }
                        }
                    }
                }
                var9_9 = var15_22;
                var10_10 = this.updateMetaData((AppendableWrapper)var6_6, var1_1, (FieldPosition)var10_10, var13_20);
                var15_22 = this.msgPattern.getPart(var9_9).getLimit();
                var1_1 = var9_9;
                var9_9 = var15_22;
                var15_22 = var1_1;
                var1_1 = var9_9;
            }
            var9_9 = var1_1;
            var1_1 = ++var15_22;
        } while (true);
    }

    private void format(Object object, AppendableWrapper appendableWrapper, FieldPosition fieldPosition) {
        if (object != null && !(object instanceof Map)) {
            this.format((Object[])object, null, appendableWrapper, fieldPosition);
        } else {
            this.format(null, (Map)object, appendableWrapper, fieldPosition);
        }
    }

    private void format(Object[] arrobject, Map<String, Object> map, AppendableWrapper appendableWrapper, FieldPosition fieldPosition) {
        if (arrobject != null && this.msgPattern.hasNamedArguments()) {
            throw new IllegalArgumentException("This method is not available in MessageFormat objects that use alphanumeric argument names.");
        }
        this.format(0, null, arrobject, map, appendableWrapper, fieldPosition);
    }

    private void formatComplexSubMessage(int n, PluralSelectorContext object, Object[] arrobject, Map<String, Object> map, AppendableWrapper appendableWrapper) {
        if (!this.msgPattern.jdkAposMode()) {
            this.format(n, (PluralSelectorContext)object, arrobject, map, appendableWrapper, null);
            return;
        }
        String string = this.msgPattern.getPatternString();
        Object object2 = null;
        int n2 = this.msgPattern.getPart(n).getLimit();
        do {
            int n3;
            Object object3 = this.msgPattern;
            int n4 = n + 1;
            MessagePattern.Part part = ((MessagePattern)object3).getPart(n4);
            MessagePattern.Part.Type type = part.getType();
            int n5 = part.getIndex();
            if (type == MessagePattern.Part.Type.MSG_LIMIT) {
                if (object2 == null) {
                    object = string.substring(n2, n5);
                } else {
                    ((StringBuilder)object2).append(string, n2, n5);
                    object = ((StringBuilder)object2).toString();
                }
                if (((String)object).indexOf(123) >= 0) {
                    object2 = new MessageFormat("", this.ulocale);
                    ((MessageFormat)object2).applyPattern((String)object, MessagePattern.ApostropheMode.DOUBLE_REQUIRED);
                    MessageFormat.super.format(0, null, arrobject, map, appendableWrapper, null);
                } else {
                    appendableWrapper.append((CharSequence)object);
                }
                return;
            }
            if (type != MessagePattern.Part.Type.REPLACE_NUMBER && type != MessagePattern.Part.Type.SKIP_SYNTAX) {
                object3 = object2;
                n3 = n2;
                n = n4;
                if (type == MessagePattern.Part.Type.ARG_START) {
                    object3 = object2;
                    if (object2 == null) {
                        object3 = new StringBuilder();
                    }
                    ((StringBuilder)object3).append(string, n2, n5);
                    n = this.msgPattern.getLimitPartIndex(n4);
                    n3 = this.msgPattern.getPart(n).getLimit();
                    MessagePattern.appendReducedApostrophes(string, n5, n3, (StringBuilder)object3);
                }
            } else {
                object3 = object2;
                if (object2 == null) {
                    object3 = new StringBuilder();
                }
                ((StringBuilder)object3).append(string, n2, n5);
                if (type == MessagePattern.Part.Type.REPLACE_NUMBER) {
                    if (((PluralSelectorContext)object).forReplaceNumber) {
                        ((StringBuilder)object3).append(((PluralSelectorContext)object).numberString);
                    } else {
                        ((StringBuilder)object3).append(this.getStockNumberFormatter().format(((PluralSelectorContext)object).number));
                    }
                }
                n3 = part.getLimit();
                n = n4;
            }
            object2 = object3;
            n2 = n3;
        } while (true);
    }

    private String getArgName(int n) {
        MessagePattern.Part part = this.msgPattern.getPart(n);
        if (part.getType() == MessagePattern.Part.Type.ARG_NAME) {
            return this.msgPattern.getSubstring(part);
        }
        return Integer.toString(part.getValue());
    }

    private String getLiteralStringUntilNextArgument(int n) {
        StringBuilder stringBuilder = new StringBuilder();
        String string = this.msgPattern.getPatternString();
        int n2 = this.msgPattern.getPart(n).getLimit();
        ++n;
        do {
            MessagePattern.Part part = this.msgPattern.getPart(n);
            MessagePattern.Part.Type type = part.getType();
            stringBuilder.append(string, n2, part.getIndex());
            if (type == MessagePattern.Part.Type.ARG_START || type == MessagePattern.Part.Type.MSG_LIMIT) break;
            n2 = part.getLimit();
            ++n;
        } while (true);
        return stringBuilder.toString();
    }

    private DateFormat getStockDateFormatter() {
        if (this.stockDateFormatter == null) {
            this.stockDateFormatter = DateFormat.getDateTimeInstance(3, 3, this.ulocale);
        }
        return this.stockDateFormatter;
    }

    private NumberFormat getStockNumberFormatter() {
        if (this.stockNumberFormatter == null) {
            this.stockNumberFormatter = NumberFormat.getInstance(this.ulocale);
        }
        return this.stockNumberFormatter;
    }

    private static int matchStringUntilLimitPart(MessagePattern messagePattern, int n, int n2, String string, int n3) {
        int n4 = 0;
        String string2 = messagePattern.getPatternString();
        int n5 = messagePattern.getPart(n).getLimit();
        int n6 = n;
        n = n5;
        do {
            int n7;
            block7 : {
                MessagePattern.Part part;
                block6 : {
                    n7 = n6 + 1;
                    part = messagePattern.getPart(n7);
                    if (n7 == n2) break block6;
                    n5 = n4;
                    n6 = n;
                    if (part.getType() != MessagePattern.Part.Type.SKIP_SYNTAX) break block7;
                }
                if ((n6 = part.getIndex() - n) != 0 && !string.regionMatches(n3, string2, n, n6)) {
                    return -1;
                }
                n5 = n4 + n6;
                if (n7 == n2) {
                    return n5;
                }
                n6 = part.getLimit();
            }
            n4 = n5;
            n = n6;
            n6 = n7;
        } while (true);
    }

    private int nextTopLevelArgStart(int n) {
        Object object;
        int n2 = n;
        if (n != 0) {
            n2 = this.msgPattern.getLimitPartIndex(n);
        }
        do {
            object = this.msgPattern;
            if ((object = object.getPartType(++n2)) != MessagePattern.Part.Type.ARG_START) continue;
            return n2;
        } while (object != MessagePattern.Part.Type.MSG_LIMIT);
        return -1;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private void parse(int var1_1, String var2_2, ParsePosition var3_3, Object[] var4_4, Map<String, Object> var5_5) {
        if (var2_2 == null) {
            return;
        }
        var6_6 = this.msgPattern.getPatternString();
        var7_7 = this.msgPattern.getPart(var1_1).getLimit();
        var8_8 = var3_3.getIndex();
        var9_9 = new ParsePosition(0);
        ++var1_1;
        do {
            block21 : {
                block18 : {
                    block20 : {
                        block19 : {
                            var10_10 = var2_2;
                            var11_11 = this.msgPattern.getPart(var1_1);
                            var12_12 = var11_11.getType();
                            var13_13 = var11_11.getIndex() - var7_7;
                            if (var13_13 != 0 && !var6_6.regionMatches(var7_7, (String)var10_10, var8_8, var13_13)) {
                                var3_3.setErrorIndex(var8_8);
                                return;
                            }
                            var8_8 += var13_13;
                            if (var12_12 == MessagePattern.Part.Type.MSG_LIMIT) {
                                var3_3.setIndex(var8_8);
                                return;
                            }
                            if (var12_12 == MessagePattern.Part.Type.SKIP_SYNTAX || var12_12 == MessagePattern.Part.Type.INSERT_CHAR) break block18;
                            var13_13 = this.msgPattern.getLimitPartIndex(var1_1);
                            var14_14 = var11_11.getArgType();
                            var12_12 = this.msgPattern;
                            var12_12 = var12_12.getPart(++var1_1);
                            var7_7 = 0;
                            if (var4_4 != null) {
                                var7_7 = var12_12.getValue();
                                var12_12 = var7_7;
                                var11_11 = null;
                            } else {
                                var12_12 = var12_12.getType() == MessagePattern.Part.Type.ARG_NAME ? this.msgPattern.getSubstring((MessagePattern.Part)var12_12) : Integer.toString(var12_12.getValue());
                                var15_15 = var12_12;
                                var11_11 = var12_12;
                                var12_12 = var15_15;
                            }
                            ++var1_1;
                            var16_16 = null;
                            var17_17 = null;
                            var15_15 = this.cachedFormatters;
                            if (var15_15 == null) break block19;
                            var18_18 = var15_15.get(var1_1 - 2);
                            var16_16 = var15_15 = var18_18;
                            if (var18_18 == null) break block19;
                            var9_9.setIndex(var8_8);
                            var12_12 = var15_15.parseObject((String)var10_10, var9_9);
                            if (var9_9.getIndex() == var8_8) {
                                var3_3.setErrorIndex(var8_8);
                                return;
                            }
                            var8_8 = 1;
                            var1_1 = var9_9.getIndex();
                            break block20;
                        }
                        if (var14_14 == MessagePattern.ArgType.NONE || (var15_15 = this.cachedFormatters) != null && var15_15.containsKey(var1_1 - 2)) ** GOTO lbl72
                        if (var14_14 == MessagePattern.ArgType.CHOICE) {
                            var9_9.setIndex(var8_8);
                            var19_19 = MessageFormat.parseChoiceArgument(this.msgPattern, var1_1, (String)var10_10, var9_9);
                            if (var9_9.getIndex() == var8_8) {
                                var3_3.setErrorIndex(var8_8);
                                return;
                            }
                            var1_1 = var9_9.getIndex();
                            var12_12 = var19_19;
                            var8_8 = 1;
                        } else {
                            if (var14_14.hasPluralStyle() != false) throw new UnsupportedOperationException("Parsing of plural/select/selectordinal argument is not supported.");
                            if (var14_14 == MessagePattern.ArgType.SELECT) {
                                throw new UnsupportedOperationException("Parsing of plural/select/selectordinal argument is not supported.");
                            }
                            var2_2 = new StringBuilder();
                            var2_2.append("unexpected argType ");
                            var2_2.append((Object)var14_14);
                            throw new IllegalStateException(var2_2.toString());
lbl72: // 1 sources:
                            var15_15 = this.getLiteralStringUntilNextArgument(var13_13);
                            var1_1 = var15_15.length() != 0 ? var10_10.indexOf((String)var15_15, var8_8) : var2_2.length();
                            if (var1_1 < 0) {
                                var3_3.setErrorIndex(var8_8);
                                return;
                            }
                            var15_15 = var10_10.substring(var8_8, var1_1);
                            var16_16 = new StringBuilder();
                            var16_16.append("{");
                            var16_16.append(var12_12.toString());
                            var16_16.append("}");
                            if (!var15_15.equals(var16_16.toString())) {
                                var12_12 = var15_15;
                                var8_8 = 1;
                            } else {
                                var8_8 = 0;
                                var12_12 = var17_17;
                            }
                        }
                    }
                    if (var8_8 != 0) {
                        if (var4_4 != null) {
                            var4_4[var7_7] = var12_12;
                        } else if (var5_5 != null) {
                            var5_5.put((String)var11_11, var12_12);
                        }
                    }
                    var8_8 = this.msgPattern.getPart(var13_13).getLimit();
                    var7_7 = var13_13;
                    break block21;
                }
                var13_13 = var11_11.getLimit();
                var7_7 = var1_1;
                var1_1 = var8_8;
                var8_8 = var13_13;
            }
            var13_13 = var7_7 + 1;
            var7_7 = var8_8;
            var8_8 = var1_1;
            var1_1 = var13_13;
        } while (true);
    }

    private static double parseChoiceArgument(MessagePattern messagePattern, int n, String string, ParsePosition parsePosition) {
        double d;
        int n2;
        int n3 = n2 = parsePosition.getIndex();
        double d2 = Double.NaN;
        int n4 = n;
        n = n3;
        do {
            n3 = n;
            d = d2;
            if (messagePattern.getPartType(n4) == MessagePattern.Part.Type.ARG_LIMIT) break;
            double d3 = messagePattern.getNumericValue(messagePattern.getPart(n4));
            int n5 = messagePattern.getLimitPartIndex(n4 += 2);
            n3 = MessageFormat.matchStringUntilLimitPart(messagePattern, n4, n5, string, n2);
            n4 = n;
            d = d2;
            if (n3 >= 0) {
                n3 = n2 + n3;
                n4 = n;
                d = d2;
                if (n3 > n) {
                    n = n3;
                    d2 = d3;
                    n4 = n;
                    d = d2;
                    if (n == string.length()) {
                        n3 = n;
                        d = d2;
                        break;
                    }
                }
            }
            n3 = n5 + 1;
            n = n4;
            d2 = d;
            n4 = n3;
        } while (true);
        if (n3 == n2) {
            parsePosition.setErrorIndex(n2);
        } else {
            parsePosition.setIndex(n3);
        }
        return d;
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        int n;
        objectInputStream.defaultReadObject();
        this.ulocale = ULocale.forLanguageTag((String)objectInputStream.readObject());
        Object object = (MessagePattern.ApostropheMode)((Object)objectInputStream.readObject());
        MessagePattern messagePattern = this.msgPattern;
        if (messagePattern == null || object != messagePattern.getApostropheMode()) {
            this.msgPattern = new MessagePattern((MessagePattern.ApostropheMode)((Object)object));
        }
        if ((object = (String)objectInputStream.readObject()) != null) {
            this.applyPattern((String)object);
        }
        for (n = objectInputStream.readInt(); n > 0; --n) {
            this.setFormat(objectInputStream.readInt(), (Format)objectInputStream.readObject());
        }
        for (n = objectInputStream.readInt(); n > 0; --n) {
            objectInputStream.readInt();
            objectInputStream.readObject();
        }
    }

    private void resetPattern() {
        Object object = this.msgPattern;
        if (object != null) {
            ((MessagePattern)object).clear();
        }
        if ((object = this.cachedFormatters) != null) {
            object.clear();
        }
        this.customFormatArgStarts = null;
    }

    private void setArgStartFormat(int n, Format format) {
        if (this.cachedFormatters == null) {
            this.cachedFormatters = new HashMap<Integer, Format>();
        }
        this.cachedFormatters.put(n, format);
    }

    private void setCustomArgStartFormat(int n, Format format) {
        this.setArgStartFormat(n, format);
        if (this.customFormatArgStarts == null) {
            this.customFormatArgStarts = new HashSet<Integer>();
        }
        this.customFormatArgStarts.add(n);
    }

    private FieldPosition updateMetaData(AppendableWrapper appendableWrapper, int n, FieldPosition fieldPosition, Object object) {
        if (appendableWrapper.attributes != null && n < appendableWrapper.length) {
            appendableWrapper.attributes.add(new AttributeAndPosition(object, n, appendableWrapper.length));
        }
        if (fieldPosition != null && Field.ARGUMENT.equals(fieldPosition.getFieldAttribute())) {
            fieldPosition.setBeginIndex(n);
            fieldPosition.setEndIndex(appendableWrapper.length);
            return null;
        }
        return fieldPosition;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeObject(this.ulocale.toLanguageTag());
        if (this.msgPattern == null) {
            this.msgPattern = new MessagePattern();
        }
        objectOutputStream.writeObject((Object)this.msgPattern.getApostropheMode());
        objectOutputStream.writeObject(this.msgPattern.getPatternString());
        Set<Integer> set = this.customFormatArgStarts;
        if (set != null && !set.isEmpty()) {
            objectOutputStream.writeInt(this.customFormatArgStarts.size());
            int n = 0;
            int n2 = 0;
            do {
                int n3;
                n2 = n3 = this.nextTopLevelArgStart(n2);
                if (n3 >= 0) {
                    if (this.customFormatArgStarts.contains(n2)) {
                        objectOutputStream.writeInt(n);
                        objectOutputStream.writeObject(this.cachedFormatters.get(n2));
                    }
                    ++n;
                    continue;
                }
                break;
            } while (true);
        } else {
            objectOutputStream.writeInt(0);
        }
        objectOutputStream.writeInt(0);
    }

    public void applyPattern(String string) {
        try {
            if (this.msgPattern == null) {
                MessagePattern messagePattern;
                this.msgPattern = messagePattern = new MessagePattern(string);
            } else {
                this.msgPattern.parse(string);
            }
            this.cacheExplicitFormats();
            return;
        }
        catch (RuntimeException runtimeException) {
            this.resetPattern();
            throw runtimeException;
        }
    }

    public void applyPattern(String string, MessagePattern.ApostropheMode apostropheMode) {
        MessagePattern messagePattern = this.msgPattern;
        if (messagePattern == null) {
            this.msgPattern = new MessagePattern(apostropheMode);
        } else if (apostropheMode != messagePattern.getApostropheMode()) {
            this.msgPattern.clearPatternAndSetApostropheMode(apostropheMode);
        }
        this.applyPattern(string);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public Object clone() {
        MessagePattern messagePattern;
        void var3_13;
        void var3_17;
        void var3_9;
        MessageFormat messageFormat = (MessageFormat)super.clone();
        if (this.customFormatArgStarts != null) {
            messageFormat.customFormatArgStarts = new HashSet<Integer>();
            for (Integer object2 : this.customFormatArgStarts) {
                messageFormat.customFormatArgStarts.add(object2);
            }
        } else {
            messageFormat.customFormatArgStarts = null;
        }
        if (this.cachedFormatters != null) {
            messageFormat.cachedFormatters = new HashMap<Integer, Format>();
            for (Map.Entry entry : this.cachedFormatters.entrySet()) {
                messageFormat.cachedFormatters.put((Integer)entry.getKey(), (Format)entry.getValue());
            }
        } else {
            messageFormat.cachedFormatters = null;
        }
        if ((messagePattern = this.msgPattern) == null) {
            Object var3_7 = null;
        } else {
            MessagePattern messagePattern2 = (MessagePattern)messagePattern.clone();
        }
        messageFormat.msgPattern = var3_9;
        DateFormat dateFormat = this.stockDateFormatter;
        if (dateFormat == null) {
            Object var3_11 = null;
        } else {
            DateFormat dateFormat2 = (DateFormat)dateFormat.clone();
        }
        messageFormat.stockDateFormatter = var3_13;
        NumberFormat numberFormat = this.stockNumberFormatter;
        if (numberFormat == null) {
            Object var3_15 = null;
        } else {
            NumberFormat numberFormat2 = (NumberFormat)numberFormat.clone();
        }
        messageFormat.stockNumberFormatter = var3_17;
        messageFormat.pluralProvider = null;
        messageFormat.ordinalProvider = null;
        return messageFormat;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object != null && this.getClass() == object.getClass()) {
            object = (MessageFormat)object;
            if (!(Objects.equals(this.ulocale, ((MessageFormat)object).ulocale) && Objects.equals(this.msgPattern, ((MessageFormat)object).msgPattern) && Objects.equals(this.cachedFormatters, ((MessageFormat)object).cachedFormatters) && Objects.equals(this.customFormatArgStarts, ((MessageFormat)object).customFormatArgStarts))) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    @Override
    public final StringBuffer format(Object object, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        this.format(object, new AppendableWrapper(stringBuffer), fieldPosition);
        return stringBuffer;
    }

    public final StringBuffer format(Map<String, Object> map, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        this.format(null, map, new AppendableWrapper(stringBuffer), fieldPosition);
        return stringBuffer;
    }

    public final StringBuffer format(Object[] arrobject, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        this.format(arrobject, null, new AppendableWrapper(stringBuffer), fieldPosition);
        return stringBuffer;
    }

    @Override
    public AttributedCharacterIterator formatToCharacterIterator(Object object) {
        if (object != null) {
            StringBuilder stringBuilder = new StringBuilder();
            Object object22 = new AppendableWrapper(stringBuilder);
            ((AppendableWrapper)object22).useAttributes();
            this.format(object, (AppendableWrapper)object22, null);
            object = new AttributedString(stringBuilder.toString());
            for (Object object22 : ((AppendableWrapper)object22).attributes) {
                ((AttributedString)object).addAttribute(((AttributeAndPosition)object22).key, ((AttributeAndPosition)object22).value, ((AttributeAndPosition)object22).start, ((AttributeAndPosition)object22).limit);
            }
            return ((AttributedString)object).getIterator();
        }
        throw new NullPointerException("formatToCharacterIterator must be passed non-null object");
    }

    public MessagePattern.ApostropheMode getApostropheMode() {
        if (this.msgPattern == null) {
            this.msgPattern = new MessagePattern();
        }
        return this.msgPattern.getApostropheMode();
    }

    public Set<String> getArgumentNames() {
        HashSet<String> hashSet = new HashSet<String>();
        int n = 0;
        do {
            int n2;
            n = n2 = this.nextTopLevelArgStart(n);
            if (n2 < 0) break;
            hashSet.add(this.getArgName(n + 1));
        } while (true);
        return hashSet;
    }

    public Format getFormatByArgumentName(String string) {
        block3 : {
            int n;
            if (this.cachedFormatters == null) {
                return null;
            }
            int n2 = MessagePattern.validateArgumentName(string);
            if (n2 < -1) {
                return null;
            }
            int n3 = 0;
            do {
                n = n3 = this.nextTopLevelArgStart(n3);
                if (n3 < 0) break block3;
                n3 = n;
            } while (!this.argNameMatches(n + 1, string, n2));
            return this.cachedFormatters.get(n);
        }
        return null;
    }

    public Format[] getFormats() {
        ArrayList<Map<Integer, Format>> arrayList = new ArrayList<Map<Integer, Format>>();
        int n = 0;
        do {
            int n2;
            n = n2 = this.nextTopLevelArgStart(n);
            if (n2 < 0) break;
            Map<Integer, Format> map = this.cachedFormatters;
            map = map == null ? null : map.get(n);
            arrayList.add(map);
        } while (true);
        return arrayList.toArray(new Format[arrayList.size()]);
    }

    public Format[] getFormatsByArgumentIndex() {
        if (!this.msgPattern.hasNamedArguments()) {
            ArrayList<Format> arrayList = new ArrayList<Format>();
            int n = 0;
            do {
                Format format;
                int n2;
                n = n2 = this.nextTopLevelArgStart(n);
                if (n2 < 0) break;
                n2 = this.msgPattern.getPart(n + 1).getValue();
                do {
                    int n3 = arrayList.size();
                    format = null;
                    if (n2 < n3) break;
                    arrayList.add(null);
                } while (true);
                Map<Integer, Format> map = this.cachedFormatters;
                if (map != null) {
                    format = map.get(n);
                }
                arrayList.set(n2, format);
            } while (true);
            return arrayList.toArray(new Format[arrayList.size()]);
        }
        throw new IllegalArgumentException("This method is not available in MessageFormat objects that use alphanumeric argument names.");
    }

    public Locale getLocale() {
        return this.ulocale.toLocale();
    }

    public ULocale getULocale() {
        return this.ulocale;
    }

    public int hashCode() {
        return this.msgPattern.getPatternString().hashCode();
    }

    public Object[] parse(String arrobject) throws ParseException {
        ParsePosition parsePosition = new ParsePosition(0);
        arrobject = this.parse((String)arrobject, parsePosition);
        if (parsePosition.getIndex() != 0) {
            return arrobject;
        }
        throw new ParseException("MessageFormat parse error!", parsePosition.getErrorIndex());
    }

    public Object[] parse(String string, ParsePosition parsePosition) {
        if (!this.msgPattern.hasNamedArguments()) {
            int n = -1;
            int n2 = 0;
            do {
                int n3;
                n2 = n3 = this.nextTopLevelArgStart(n2);
                if (n3 < 0) break;
                int n4 = this.msgPattern.getPart(n2 + 1).getValue();
                n3 = n;
                if (n4 > n) {
                    n3 = n4;
                }
                n = n3;
            } while (true);
            Object[] arrobject = new Object[n + 1];
            n2 = parsePosition.getIndex();
            this.parse(0, string, parsePosition, arrobject, null);
            if (parsePosition.getIndex() == n2) {
                return null;
            }
            return arrobject;
        }
        throw new IllegalArgumentException("This method is not available in MessageFormat objects that use named argument.");
    }

    @Override
    public Object parseObject(String string, ParsePosition parsePosition) {
        if (!this.msgPattern.hasNamedArguments()) {
            return this.parse(string, parsePosition);
        }
        return this.parseToMap(string, parsePosition);
    }

    public Map<String, Object> parseToMap(String string) throws ParseException {
        ParsePosition parsePosition = new ParsePosition(0);
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        this.parse(0, string, parsePosition, null, hashMap);
        if (parsePosition.getIndex() != 0) {
            return hashMap;
        }
        throw new ParseException("MessageFormat parse error!", parsePosition.getErrorIndex());
    }

    public Map<String, Object> parseToMap(String string, ParsePosition parsePosition) {
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        int n = parsePosition.getIndex();
        this.parse(0, string, parsePosition, null, hashMap);
        if (parsePosition.getIndex() == n) {
            return null;
        }
        return hashMap;
    }

    public void setFormat(int n, Format format) {
        int n2 = 0;
        int n3 = 0;
        do {
            int n4;
            n3 = n4 = this.nextTopLevelArgStart(n3);
            if (n4 < 0) break;
            if (n2 == n) {
                this.setCustomArgStartFormat(n3, format);
                return;
            }
            ++n2;
        } while (true);
        throw new ArrayIndexOutOfBoundsException(n);
    }

    public void setFormatByArgumentIndex(int n, Format format) {
        if (!this.msgPattern.hasNamedArguments()) {
            int n2 = 0;
            do {
                int n3 = n2 = this.nextTopLevelArgStart(n2);
                if (n2 < 0) break;
                n2 = n3;
                if (this.msgPattern.getPart(n3 + 1).getValue() != n) continue;
                this.setCustomArgStartFormat(n3, format);
                n2 = n3;
            } while (true);
            return;
        }
        throw new IllegalArgumentException("This method is not available in MessageFormat objects that use alphanumeric argument names.");
    }

    public void setFormatByArgumentName(String string, Format format) {
        int n = MessagePattern.validateArgumentName(string);
        if (n < -1) {
            return;
        }
        int n2 = 0;
        do {
            int n3 = n2 = this.nextTopLevelArgStart(n2);
            if (n2 < 0) break;
            n2 = n3;
            if (!this.argNameMatches(n3 + 1, string, n)) continue;
            this.setCustomArgStartFormat(n3, format);
            n2 = n3;
        } while (true);
    }

    public void setFormats(Format[] arrformat) {
        int n = 0;
        for (int i = 0; i < arrformat.length; ++i) {
            int n2;
            n = n2 = this.nextTopLevelArgStart(n);
            if (n2 < 0) break;
            this.setCustomArgStartFormat(n, arrformat[i]);
        }
    }

    public void setFormatsByArgumentIndex(Format[] arrformat) {
        if (!this.msgPattern.hasNamedArguments()) {
            int n = 0;
            do {
                int n2;
                n = n2 = this.nextTopLevelArgStart(n);
                if (n2 < 0) break;
                n2 = this.msgPattern.getPart(n + 1).getValue();
                if (n2 >= arrformat.length) continue;
                this.setCustomArgStartFormat(n, arrformat[n2]);
            } while (true);
            return;
        }
        throw new IllegalArgumentException("This method is not available in MessageFormat objects that use alphanumeric argument names.");
    }

    public void setFormatsByArgumentName(Map<String, Format> map) {
        int n = 0;
        do {
            int n2;
            n = n2 = this.nextTopLevelArgStart(n);
            if (n2 < 0) break;
            String string = this.getArgName(n + 1);
            if (!map.containsKey(string)) continue;
            this.setCustomArgStartFormat(n, map.get(string));
        } while (true);
    }

    public void setLocale(ULocale uLocale) {
        String string = this.toPattern();
        this.ulocale = uLocale;
        this.stockDateFormatter = null;
        this.stockNumberFormatter = null;
        this.pluralProvider = null;
        this.ordinalProvider = null;
        this.applyPattern(string);
    }

    public void setLocale(Locale locale) {
        this.setLocale(ULocale.forLocale(locale));
    }

    public String toPattern() {
        if (this.customFormatArgStarts == null) {
            Object object = this.msgPattern;
            Object object2 = "";
            if (object == null) {
                return "";
            }
            if ((object = ((MessagePattern)object).getPatternString()) != null) {
                object2 = object;
            }
            return object2;
        }
        throw new IllegalStateException("toPattern() is not supported after custom Format objects have been set via setFormat() or similar APIs");
    }

    public boolean usesNamedArguments() {
        return this.msgPattern.hasNamedArguments();
    }

    private static final class AppendableWrapper {
        private Appendable app;
        private List<AttributeAndPosition> attributes;
        private int length;

        public AppendableWrapper(StringBuffer stringBuffer) {
            this.app = stringBuffer;
            this.length = stringBuffer.length();
            this.attributes = null;
        }

        public AppendableWrapper(StringBuilder stringBuilder) {
            this.app = stringBuilder;
            this.length = stringBuilder.length();
            this.attributes = null;
        }

        public static int append(Appendable appendable, CharacterIterator characterIterator) {
            int n;
            int n2;
            block5 : {
                int n3;
                n2 = characterIterator.getBeginIndex();
                n = characterIterator.getEndIndex();
                if (n2 >= n) break block5;
                try {
                    appendable.append(characterIterator.first());
                    n3 = n2;
                }
                catch (IOException iOException) {
                    throw new ICUUncheckedIOException(iOException);
                }
                while (++n3 < n) {
                    appendable.append(characterIterator.next());
                }
            }
            return n - n2;
        }

        public void append(CharSequence charSequence) {
            try {
                this.app.append(charSequence);
                this.length += charSequence.length();
                return;
            }
            catch (IOException iOException) {
                throw new ICUUncheckedIOException(iOException);
            }
        }

        public void append(CharSequence charSequence, int n, int n2) {
            try {
                this.app.append(charSequence, n, n2);
                this.length += n2 - n;
                return;
            }
            catch (IOException iOException) {
                throw new ICUUncheckedIOException(iOException);
            }
        }

        public void append(CharacterIterator characterIterator) {
            this.length += AppendableWrapper.append(this.app, characterIterator);
        }

        public void formatAndAppend(Format cloneable, Object object2) {
            if (this.attributes == null) {
                this.append(((Format)cloneable).format(object2));
            } else {
                cloneable = ((Format)cloneable).formatToCharacterIterator(object2);
                int n = this.length;
                this.append((CharacterIterator)cloneable);
                cloneable.first();
                int n2 = cloneable.getIndex();
                int n3 = cloneable.getEndIndex();
                int n4 = n - n2;
                while (n2 < n3) {
                    Map<AttributedCharacterIterator.Attribute, Object> map = cloneable.getAttributes();
                    n = cloneable.getRunLimit();
                    if (map.size() != 0) {
                        for (Map.Entry<AttributedCharacterIterator.Attribute, Object> entry : map.entrySet()) {
                            this.attributes.add(new AttributeAndPosition(entry.getKey(), entry.getValue(), n4 + n2, n4 + n));
                        }
                    }
                    n2 = n;
                    cloneable.setIndex(n2);
                }
            }
        }

        public void formatAndAppend(Format format, Object object, String string) {
            if (this.attributes == null && string != null) {
                this.append(string);
            } else {
                this.formatAndAppend(format, object);
            }
        }

        public void useAttributes() {
            this.attributes = new ArrayList<AttributeAndPosition>();
        }
    }

    private static final class AttributeAndPosition {
        private AttributedCharacterIterator.Attribute key;
        private int limit;
        private int start;
        private Object value;

        public AttributeAndPosition(Object object, int n, int n2) {
            this.init(Field.ARGUMENT, object, n, n2);
        }

        public AttributeAndPosition(AttributedCharacterIterator.Attribute attribute, Object object, int n, int n2) {
            this.init(attribute, object, n, n2);
        }

        public void init(AttributedCharacterIterator.Attribute attribute, Object object, int n, int n2) {
            this.key = attribute;
            this.value = object;
            this.start = n;
            this.limit = n2;
        }
    }

    public static class Field
    extends Format.Field {
        public static final Field ARGUMENT = new Field("message argument field");
        private static final long serialVersionUID = 7510380454602616157L;

        protected Field(String string) {
            super(string);
        }

        @Override
        protected Object readResolve() throws InvalidObjectException {
            if (this.getClass() == Field.class) {
                if (this.getName().equals(ARGUMENT.getName())) {
                    return ARGUMENT;
                }
                throw new InvalidObjectException("Unknown attribute name.");
            }
            throw new InvalidObjectException("A subclass of MessageFormat.Field must implement readResolve.");
        }
    }

    private static final class PluralSelectorContext {
        String argName;
        boolean forReplaceNumber;
        Format formatter;
        Number number;
        int numberArgIndex;
        String numberString;
        double offset;
        int startIndex;

        private PluralSelectorContext(int n, String string, Number number, double d) {
            this.startIndex = n;
            this.argName = string;
            this.number = d == 0.0 ? (Number)number : (Number)(number.doubleValue() - d);
            this.offset = d;
        }

        public String toString() {
            throw new AssertionError((Object)"PluralSelectorContext being formatted, rather than its number");
        }
    }

    private static final class PluralSelectorProvider
    implements PluralFormat.PluralSelector {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private MessageFormat msgFormat;
        private PluralRules rules;
        private PluralRules.PluralType type;

        public PluralSelectorProvider(MessageFormat messageFormat, PluralRules.PluralType pluralType) {
            this.msgFormat = messageFormat;
            this.type = pluralType;
        }

        @Override
        public String select(Object object, double d) {
            if (this.rules == null) {
                this.rules = PluralRules.forLocale(this.msgFormat.ulocale, this.type);
            }
            object = (PluralSelectorContext)object;
            int n = this.msgFormat.findOtherSubMessage(((PluralSelectorContext)object).startIndex);
            ((PluralSelectorContext)object).numberArgIndex = this.msgFormat.findFirstPluralNumberArg(n, ((PluralSelectorContext)object).argName);
            if (((PluralSelectorContext)object).numberArgIndex > 0 && this.msgFormat.cachedFormatters != null) {
                ((PluralSelectorContext)object).formatter = (Format)this.msgFormat.cachedFormatters.get(((PluralSelectorContext)object).numberArgIndex);
            }
            if (((PluralSelectorContext)object).formatter == null) {
                ((PluralSelectorContext)object).formatter = this.msgFormat.getStockNumberFormatter();
                ((PluralSelectorContext)object).forReplaceNumber = true;
            }
            ((PluralSelectorContext)object).numberString = ((PluralSelectorContext)object).formatter.format(((PluralSelectorContext)object).number);
            if (((PluralSelectorContext)object).formatter instanceof DecimalFormat) {
                object = ((DecimalFormat)((PluralSelectorContext)object).formatter).getFixedDecimal(d);
                return this.rules.select((PluralRules.IFixedDecimal)object);
            }
            return this.rules.select(d);
        }
    }

}

