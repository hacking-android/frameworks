/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.impl.ICUConfig;
import android.icu.impl.PatternProps;
import android.icu.util.Freezable;
import android.icu.util.ICUCloneNotSupportedException;
import java.util.ArrayList;
import java.util.Locale;

public final class MessagePattern
implements Cloneable,
Freezable<MessagePattern> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int ARG_NAME_NOT_NUMBER = -1;
    public static final int ARG_NAME_NOT_VALID = -2;
    private static final int MAX_PREFIX_LENGTH = 24;
    public static final double NO_NUMERIC_VALUE = -1.23456789E8;
    private static final ArgType[] argTypes;
    private static final ApostropheMode defaultAposMode;
    private ApostropheMode aposMode;
    private volatile boolean frozen;
    private boolean hasArgNames;
    private boolean hasArgNumbers;
    private String msg;
    private boolean needsAutoQuoting;
    private ArrayList<Double> numericValues;
    private ArrayList<Part> parts = new ArrayList();

    static {
        defaultAposMode = ApostropheMode.valueOf(ICUConfig.get("android.icu.text.MessagePattern.ApostropheMode", "DOUBLE_OPTIONAL"));
        argTypes = ArgType.values();
    }

    public MessagePattern() {
        this.aposMode = defaultAposMode;
    }

    public MessagePattern(ApostropheMode apostropheMode) {
        this.aposMode = apostropheMode;
    }

    public MessagePattern(String string) {
        this.aposMode = defaultAposMode;
        this.parse(string);
    }

    private void addArgDoublePart(double d, int n, int n2) {
        block4 : {
            int n3;
            block3 : {
                ArrayList<Double> arrayList;
                block2 : {
                    arrayList = this.numericValues;
                    if (arrayList != null) break block2;
                    this.numericValues = new ArrayList();
                    n3 = 0;
                    break block3;
                }
                n3 = arrayList.size();
                if (n3 > 32767) break block4;
            }
            this.numericValues.add(d);
            this.addPart(Part.Type.ARG_DOUBLE, n, n2, n3);
            return;
        }
        throw new IndexOutOfBoundsException("Too many numeric values");
    }

    private void addLimitPart(int n, Part.Type type, int n2, int n3, int n4) {
        this.parts.get(n).limitPartIndex = this.parts.size();
        this.addPart(type, n2, n3, n4);
    }

    private void addPart(Part.Type type, int n, int n2, int n3) {
        this.parts.add(new Part(type, n, n2, n3));
    }

    static void appendReducedApostrophes(String string, int n, int n2, StringBuilder stringBuilder) {
        int n3;
        int n4 = -1;
        while ((n3 = string.indexOf(39, n)) >= 0 && n3 < n2) {
            if (n3 == n4) {
                stringBuilder.append('\'');
                ++n;
                n4 = -1;
                continue;
            }
            stringBuilder.append(string, n, n3);
            n = n4 = n3 + 1;
        }
        stringBuilder.append(string, n, n2);
    }

    private boolean inMessageFormatPattern(int n) {
        boolean bl = false;
        if (n > 0 || this.parts.get(0).type == Part.Type.MSG_START) {
            bl = true;
        }
        return bl;
    }

    private boolean inTopLevelChoiceMessage(int n, ArgType argType) {
        boolean bl;
        block0 : {
            bl = false;
            if (n != 1 || argType != ArgType.CHOICE || this.parts.get(0).type == Part.Type.MSG_START) break block0;
            bl = true;
        }
        return bl;
    }

    private static boolean isArgTypeChar(int n) {
        boolean bl = 97 <= n && n <= 122 || 65 <= n && n <= 90;
        return bl;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean isChoice(int n) {
        String string = this.msg;
        int n2 = n + 1;
        if ((n = (int)string.charAt(n)) != 99) {
            if (n != 67) return false;
        }
        string = this.msg;
        n = n2 + 1;
        if ((n2 = (int)string.charAt(n2)) != 104) {
            if (n2 != 72) return false;
        }
        string = this.msg;
        n2 = n + 1;
        if ((n = (int)string.charAt(n)) != 111) {
            if (n != 79) return false;
        }
        string = this.msg;
        n = n2 + 1;
        if ((n2 = (int)string.charAt(n2)) != 105) {
            if (n2 != 73) return false;
        }
        if ((n2 = (int)this.msg.charAt(n)) != 99) {
            if (n2 != 67) return false;
        }
        if ((n = (int)this.msg.charAt(n + 1)) == 101) return true;
        if (n != 69) return false;
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean isOrdinal(int n) {
        String string = this.msg;
        int n2 = n + 1;
        if ((n = (int)string.charAt(n)) != 111) {
            if (n != 79) return false;
        }
        string = this.msg;
        n = n2 + 1;
        if ((n2 = (int)string.charAt(n2)) != 114) {
            if (n2 != 82) return false;
        }
        string = this.msg;
        n2 = n + 1;
        if ((n = (int)string.charAt(n)) != 100) {
            if (n != 68) return false;
        }
        string = this.msg;
        n = n2 + 1;
        if ((n2 = (int)string.charAt(n2)) != 105) {
            if (n2 != 73) return false;
        }
        string = this.msg;
        n2 = n + 1;
        if ((n = (int)string.charAt(n)) != 110) {
            if (n != 78) return false;
        }
        if ((n = (int)this.msg.charAt(n2)) != 97) {
            if (n != 65) return false;
        }
        if ((n = (int)this.msg.charAt(n2 + 1)) == 108) return true;
        if (n != 76) return false;
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean isPlural(int n) {
        String string = this.msg;
        int n2 = n + 1;
        if ((n = (int)string.charAt(n)) != 112) {
            if (n != 80) return false;
        }
        string = this.msg;
        n = n2 + 1;
        if ((n2 = (int)string.charAt(n2)) != 108) {
            if (n2 != 76) return false;
        }
        string = this.msg;
        n2 = n + 1;
        if ((n = (int)string.charAt(n)) != 117) {
            if (n != 85) return false;
        }
        string = this.msg;
        n = n2 + 1;
        if ((n2 = (int)string.charAt(n2)) != 114) {
            if (n2 != 82) return false;
        }
        if ((n2 = (int)this.msg.charAt(n)) != 97) {
            if (n2 != 65) return false;
        }
        if ((n = (int)this.msg.charAt(n + 1)) == 108) return true;
        if (n != 76) return false;
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean isSelect(int n) {
        String string = this.msg;
        int n2 = n + 1;
        if ((n = (int)string.charAt(n)) != 115) {
            if (n != 83) return false;
        }
        string = this.msg;
        n = n2 + 1;
        if ((n2 = (int)string.charAt(n2)) != 101) {
            if (n2 != 69) return false;
        }
        string = this.msg;
        n2 = n + 1;
        if ((n = (int)string.charAt(n)) != 108) {
            if (n != 76) return false;
        }
        string = this.msg;
        n = n2 + 1;
        if ((n2 = (int)string.charAt(n2)) != 101) {
            if (n2 != 69) return false;
        }
        if ((n2 = (int)this.msg.charAt(n)) != 99) {
            if (n2 != 67) return false;
        }
        if ((n = (int)this.msg.charAt(n + 1)) == 116) return true;
        if (n != 84) return false;
        return true;
    }

    private int parseArg(int n, int n2, int n3) {
        Object object;
        block18 : {
            int n4;
            block22 : {
                block23 : {
                    block24 : {
                        block26 : {
                            block27 : {
                                block28 : {
                                    block29 : {
                                        int n5;
                                        block25 : {
                                            int n6;
                                            char c;
                                            block21 : {
                                                block19 : {
                                                    block20 : {
                                                        n5 = this.parts.size();
                                                        object = ArgType.NONE;
                                                        this.addPart(Part.Type.ARG_START, n, n2, ((Enum)object).ordinal());
                                                        n4 = this.skipWhiteSpace(n + n2);
                                                        if (n4 == this.msg.length()) break block18;
                                                        n = this.skipIdentifier(n4);
                                                        n6 = this.parseArgNumber(n4, n);
                                                        if (n6 < 0) break block19;
                                                        n2 = n - n4;
                                                        if (n2 > 65535 || n6 > 32767) break block20;
                                                        this.hasArgNumbers = true;
                                                        this.addPart(Part.Type.ARG_NUMBER, n4, n2, n6);
                                                        break block21;
                                                    }
                                                    object = new StringBuilder();
                                                    ((StringBuilder)object).append("Argument number too large: ");
                                                    ((StringBuilder)object).append(this.prefix(n4));
                                                    throw new IndexOutOfBoundsException(((StringBuilder)object).toString());
                                                }
                                                if (n6 != -1) break block22;
                                                n2 = n - n4;
                                                if (n2 > 65535) break block23;
                                                this.hasArgNames = true;
                                                this.addPart(Part.Type.ARG_NAME, n4, n2, 0);
                                            }
                                            n = this.skipWhiteSpace(n);
                                            if (n == this.msg.length()) break block24;
                                            n2 = this.msg.charAt(n);
                                            if (n2 == 125) break block25;
                                            if (n2 != 44) break block26;
                                            for (n = n2 = this.skipWhiteSpace((int)(n + 1)); n < this.msg.length() && MessagePattern.isArgTypeChar(this.msg.charAt(n)); ++n) {
                                            }
                                            n6 = n - n2;
                                            if ((n = this.skipWhiteSpace(n)) == this.msg.length()) break block27;
                                            if (n6 == 0 || (c = this.msg.charAt(n)) != ',' && c != '}') break block28;
                                            if (n6 > 65535) break block29;
                                            ArgType argType = ArgType.SIMPLE;
                                            if (n6 == 6) {
                                                if (this.isChoice(n2)) {
                                                    object = ArgType.CHOICE;
                                                } else if (this.isPlural(n2)) {
                                                    object = ArgType.PLURAL;
                                                } else {
                                                    object = argType;
                                                    if (this.isSelect(n2)) {
                                                        object = ArgType.SELECT;
                                                    }
                                                }
                                            } else {
                                                object = argType;
                                                if (n6 == 13) {
                                                    object = argType;
                                                    if (this.isSelect(n2)) {
                                                        object = argType;
                                                        if (this.isOrdinal(n2 + 6)) {
                                                            object = ArgType.SELECTORDINAL;
                                                        }
                                                    }
                                                }
                                            }
                                            this.parts.get(n5).value = (short)((Enum)object).ordinal();
                                            if (object == ArgType.SIMPLE) {
                                                this.addPart(Part.Type.ARG_TYPE, n2, n6, 0);
                                            }
                                            if (c == '}') {
                                                if (object != ArgType.SIMPLE) {
                                                    object = new StringBuilder();
                                                    ((StringBuilder)object).append("No style field for complex argument: ");
                                                    ((StringBuilder)object).append(this.prefix(n4));
                                                    throw new IllegalArgumentException(((StringBuilder)object).toString());
                                                }
                                            } else {
                                                ++n;
                                                n = object == ArgType.SIMPLE ? this.parseSimpleStyle(n) : (object == ArgType.CHOICE ? this.parseChoiceStyle(n, n3) : this.parsePluralOrSelectStyle((ArgType)((Object)object), n, n3));
                                            }
                                        }
                                        this.addLimitPart(n5, Part.Type.ARG_LIMIT, n, 1, ((Enum)object).ordinal());
                                        return n + 1;
                                    }
                                    object = new StringBuilder();
                                    ((StringBuilder)object).append("Argument type name too long: ");
                                    ((StringBuilder)object).append(this.prefix(n4));
                                    throw new IndexOutOfBoundsException(((StringBuilder)object).toString());
                                }
                                object = new StringBuilder();
                                ((StringBuilder)object).append("Bad argument syntax: ");
                                ((StringBuilder)object).append(this.prefix(n4));
                                throw new IllegalArgumentException(((StringBuilder)object).toString());
                            }
                            object = new StringBuilder();
                            ((StringBuilder)object).append("Unmatched '{' braces in message ");
                            ((StringBuilder)object).append(this.prefix());
                            throw new IllegalArgumentException(((StringBuilder)object).toString());
                        }
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Bad argument syntax: ");
                        ((StringBuilder)object).append(this.prefix(n4));
                        throw new IllegalArgumentException(((StringBuilder)object).toString());
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Unmatched '{' braces in message ");
                    ((StringBuilder)object).append(this.prefix());
                    throw new IllegalArgumentException(((StringBuilder)object).toString());
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("Argument name too long: ");
                ((StringBuilder)object).append(this.prefix(n4));
                throw new IndexOutOfBoundsException(((StringBuilder)object).toString());
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Bad argument syntax: ");
            ((StringBuilder)object).append(this.prefix(n4));
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Unmatched '{' braces in message ");
        ((StringBuilder)object).append(this.prefix());
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    private int parseArgNumber(int n, int n2) {
        return MessagePattern.parseArgNumber(this.msg, n, n2);
    }

    private static int parseArgNumber(CharSequence charSequence, int n, int n2) {
        block11 : {
            boolean bl;
            int n3;
            block10 : {
                block9 : {
                    if (n >= n2) {
                        return -2;
                    }
                    n3 = n + 1;
                    if ((n = (int)charSequence.charAt(n)) != 48) break block9;
                    if (n3 == n2) {
                        return 0;
                    }
                    n = 0;
                    bl = true;
                    break block10;
                }
                if (49 > n || n > 57) break block11;
                n -= 48;
                bl = false;
            }
            while (n3 < n2) {
                char c = charSequence.charAt(n3);
                if ('0' <= c && c <= '9') {
                    if (n >= 214748364) {
                        bl = true;
                    }
                    n = n * 10 + (c - 48);
                    ++n3;
                    continue;
                }
                return -1;
            }
            if (bl) {
                return -2;
            }
            return n;
        }
        return -1;
    }

    private int parseChoiceStyle(int n, int n2) {
        int n3 = this.skipWhiteSpace(n);
        if (n3 != this.msg.length() && this.msg.charAt(n3) != '}') {
            int n4;
            int n5;
            while ((n5 = (n4 = this.skipDouble(n3)) - n3) != 0) {
                if (n5 <= 65535) {
                    this.parseDouble(n3, n4, true);
                    n3 = this.skipWhiteSpace(n4);
                    if (n3 != this.msg.length()) {
                        char c = this.msg.charAt(n3);
                        if (c != '#' && c != '<' && c != '\u2264') {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("Expected choice separator (#<\u2264) instead of '");
                            stringBuilder.append(c);
                            stringBuilder.append("' in choice pattern ");
                            stringBuilder.append(this.prefix(n));
                            throw new IllegalArgumentException(stringBuilder.toString());
                        }
                        this.addPart(Part.Type.ARG_SELECTOR, n3, 1, 0);
                        n3 = this.parseMessage(n3 + 1, 0, n2 + 1, ArgType.CHOICE);
                        if (n3 == this.msg.length()) {
                            return n3;
                        }
                        if (this.msg.charAt(n3) == '}') {
                            if (this.inMessageFormatPattern(n2)) {
                                return n3;
                            }
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("Bad choice pattern syntax: ");
                            stringBuilder.append(this.prefix(n));
                            throw new IllegalArgumentException(stringBuilder.toString());
                        }
                        n3 = this.skipWhiteSpace(n3 + 1);
                        continue;
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Bad choice pattern syntax: ");
                    stringBuilder.append(this.prefix(n));
                    throw new IllegalArgumentException(stringBuilder.toString());
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Choice number too long: ");
                stringBuilder.append(this.prefix(n3));
                throw new IndexOutOfBoundsException(stringBuilder.toString());
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Bad choice pattern syntax: ");
            stringBuilder.append(this.prefix(n));
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Missing choice argument pattern in ");
        stringBuilder.append(this.prefix());
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private void parseDouble(int var1_1, int var2_2, boolean var3_3) {
        block8 : {
            block7 : {
                var4_4 = 0;
                var5_5 = 0;
                var6_6 = this.msg;
                var7_12 = var1_1 + 1;
                var8_13 = var6_6.charAt(var1_1);
                if (var8_13 != 45) break block7;
                var5_5 = 1;
                if (var7_12 == var2_2) ** GOTO lbl-1000
                var6_7 = this.msg;
                var9_14 = var7_12 + 1;
                var8_13 = var6_7.charAt(var7_12);
                var7_12 = var9_14;
                break block8;
            }
            if (var8_13 == 43) {
                ** if (var7_12 != var2_2) goto lbl24
            }
            break block8;
lbl-1000: // 4 sources:
            {
                var6_10 = new StringBuilder();
                var6_10.append("Bad syntax for numeric value: ");
                var6_10.append(this.msg.substring(var1_1, var2_2));
                throw new NumberFormatException(var6_10.toString());
            }
lbl24: // 1 sources:
            var6_8 = this.msg;
            var9_14 = var7_12 + 1;
            var8_13 = var6_8.charAt(var7_12);
            var7_12 = var9_14;
        }
        var10_15 = var8_13;
        var9_14 = var7_12;
        if (var8_13 == 8734) {
            if (var3_3 && var7_12 == var2_2) {
                var11_16 = var5_5 != 0 ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
                this.addArgDoublePart(var11_16, var1_1, var2_2 - var1_1);
                return;
            } else {
                ** GOTO lbl-1000
            }
        }
        while (48 <= var10_15 && var10_15 <= 57 && (var4_4 = var4_4 * 10 + (var10_15 - 48)) <= var5_5 + 32767) {
            if (var9_14 == var2_2) {
                var6_11 = Part.Type.ARG_INT;
                var8_13 = var5_5 != 0 ? -var4_4 : var4_4;
                this.addPart(var6_11, var1_1, var2_2 - var1_1, var8_13);
                return;
            }
            var10_15 = this.msg.charAt(var9_14);
            ++var9_14;
        }
        this.addArgDoublePart(Double.parseDouble(this.msg.substring(var1_1, var2_2)), var1_1, var2_2 - var1_1);
    }

    private int parseMessage(int n, int n2, int n3, ArgType object) {
        if (n3 <= 32767) {
            int n4 = this.parts.size();
            this.addPart(Part.Type.MSG_START, n, n2, n3);
            n2 = n + n2;
            while (n2 < this.msg.length()) {
                block19 : {
                    Object object2 = this.msg;
                    n = n2 + 1;
                    if ((n2 = (int)object2.charAt(n2)) == 39) {
                        if (n == this.msg.length()) {
                            this.addPart(Part.Type.INSERT_CHAR, n, 0, 39);
                            this.needsAutoQuoting = true;
                        } else {
                            n2 = this.msg.charAt(n);
                            if (n2 == 39) {
                                this.addPart(Part.Type.SKIP_SYNTAX, n, 1, 0);
                                ++n;
                            } else if (!(this.aposMode == ApostropheMode.DOUBLE_REQUIRED || n2 == 123 || n2 == 125 || object == ArgType.CHOICE && n2 == 124 || ((ArgType)((Object)object)).hasPluralStyle() && n2 == 35)) {
                                this.addPart(Part.Type.INSERT_CHAR, n, 0, 39);
                                this.needsAutoQuoting = true;
                            } else {
                                this.addPart(Part.Type.SKIP_SYNTAX, n - 1, 1, 0);
                                while ((n = this.msg.indexOf(39, n + 1)) >= 0) {
                                    if (n + 1 < this.msg.length() && this.msg.charAt(n + 1) == '\'') {
                                        object2 = Part.Type.SKIP_SYNTAX;
                                        this.addPart((Part.Type)((Object)object2), ++n, 1, 0);
                                        continue;
                                    }
                                    this.addPart(Part.Type.SKIP_SYNTAX, n, 1, 0);
                                    ++n;
                                    break block19;
                                }
                                n = this.msg.length();
                                this.addPart(Part.Type.INSERT_CHAR, n, 0, 39);
                                this.needsAutoQuoting = true;
                            }
                        }
                    } else if (((ArgType)((Object)object)).hasPluralStyle() && n2 == 35) {
                        this.addPart(Part.Type.REPLACE_NUMBER, n - 1, 1, 0);
                    } else if (n2 == 123) {
                        n = this.parseArg(n - 1, 1, n3);
                    } else if (n3 > 0 && n2 == 125 || object == ArgType.CHOICE && n2 == 124) {
                        n2 = object == ArgType.CHOICE && n2 == 125 ? 0 : 1;
                        this.addLimitPart(n4, Part.Type.MSG_LIMIT, n - 1, n2, n3);
                        if (object == ArgType.CHOICE) {
                            return n - 1;
                        }
                        return n;
                    }
                }
                n2 = n;
            }
            if (n3 > 0 && !this.inTopLevelChoiceMessage(n3, (ArgType)((Object)object))) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Unmatched '{' braces in message ");
                ((StringBuilder)object).append(this.prefix());
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
            this.addLimitPart(n4, Part.Type.MSG_LIMIT, n2, 0, n3);
            return n2;
        }
        throw new IndexOutOfBoundsException();
    }

    private int parsePluralOrSelectStyle(ArgType object, int n, int n2) {
        boolean bl;
        int n3;
        boolean bl2 = false;
        int n4 = 1;
        int n5 = n;
        while (!(bl = (n3 = this.skipWhiteSpace(n5)) == this.msg.length()) && this.msg.charAt(n3) != '}') {
            block16 : {
                block17 : {
                    block15 : {
                        block12 : {
                            block13 : {
                                block14 : {
                                    if (!((ArgType)((Object)object)).hasPluralStyle() || this.msg.charAt(n3) != '=') break block12;
                                    n4 = this.skipDouble(n3 + 1);
                                    n5 = n4 - n3;
                                    if (n5 == 1) break block13;
                                    if (n5 > 65535) break block14;
                                    this.addPart(Part.Type.ARG_SELECTOR, n3, n5, 0);
                                    this.parseDouble(n3 + 1, n4, false);
                                    break block15;
                                }
                                object = new StringBuilder();
                                ((StringBuilder)object).append("Argument selector too long: ");
                                ((StringBuilder)object).append(this.prefix(n3));
                                throw new IndexOutOfBoundsException(((StringBuilder)object).toString());
                            }
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("Bad ");
                            stringBuilder.append(((Enum)object).toString().toLowerCase(Locale.ENGLISH));
                            stringBuilder.append(" pattern syntax: ");
                            stringBuilder.append(this.prefix(n));
                            throw new IllegalArgumentException(stringBuilder.toString());
                        }
                        n5 = this.skipIdentifier(n3);
                        int n6 = n5 - n3;
                        if (n6 == 0) break block16;
                        if (((ArgType)((Object)object)).hasPluralStyle() && n6 == 6 && n5 < this.msg.length() && this.msg.regionMatches(n3, "offset:", 0, 7)) {
                            if (n4 != 0) {
                                n4 = this.skipWhiteSpace(n5 + 1);
                                n5 = this.skipDouble(n4);
                                if (n5 != n4) {
                                    if (n5 - n4 <= 65535) {
                                        this.parseDouble(n4, n5, false);
                                        n4 = 0;
                                        continue;
                                    }
                                    object = new StringBuilder();
                                    ((StringBuilder)object).append("Plural offset value too long: ");
                                    ((StringBuilder)object).append(this.prefix(n4));
                                    throw new IndexOutOfBoundsException(((StringBuilder)object).toString());
                                }
                                object = new StringBuilder();
                                ((StringBuilder)object).append("Missing value for plural 'offset:' ");
                                ((StringBuilder)object).append(this.prefix(n));
                                throw new IllegalArgumentException(((StringBuilder)object).toString());
                            }
                            object = new StringBuilder();
                            ((StringBuilder)object).append("Plural argument 'offset:' (if present) must precede key-message pairs: ");
                            ((StringBuilder)object).append(this.prefix(n));
                            throw new IllegalArgumentException(((StringBuilder)object).toString());
                        }
                        if (n6 > 65535) break block17;
                        this.addPart(Part.Type.ARG_SELECTOR, n3, n6, 0);
                        n4 = n5;
                        if (this.msg.regionMatches(n3, "other", 0, n6)) {
                            bl2 = true;
                            n4 = n5;
                        }
                    }
                    n4 = this.skipWhiteSpace(n4);
                    if (n4 != this.msg.length() && this.msg.charAt(n4) == '{') {
                        n5 = this.parseMessage(n4, 1, n2 + 1, (ArgType)((Object)object));
                        n4 = 0;
                        continue;
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("No message fragment after ");
                    stringBuilder.append(((Enum)object).toString().toLowerCase(Locale.ENGLISH));
                    stringBuilder.append(" selector: ");
                    stringBuilder.append(this.prefix(n3));
                    throw new IllegalArgumentException(stringBuilder.toString());
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("Argument selector too long: ");
                ((StringBuilder)object).append(this.prefix(n3));
                throw new IndexOutOfBoundsException(((StringBuilder)object).toString());
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Bad ");
            stringBuilder.append(((Enum)object).toString().toLowerCase(Locale.ENGLISH));
            stringBuilder.append(" pattern syntax: ");
            stringBuilder.append(this.prefix(n));
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        if (bl != this.inMessageFormatPattern(n2)) {
            if (bl2) {
                return n3;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Missing 'other' keyword in ");
            stringBuilder.append(((Enum)object).toString().toLowerCase(Locale.ENGLISH));
            stringBuilder.append(" pattern in ");
            stringBuilder.append(this.prefix());
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Bad ");
        stringBuilder.append(((Enum)object).toString().toLowerCase(Locale.ENGLISH));
        stringBuilder.append(" pattern syntax: ");
        stringBuilder.append(this.prefix(n));
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    private int parseSimpleStyle(int n) {
        CharSequence charSequence;
        int n2;
        int n3 = 0;
        int n4 = n;
        while ((n2 = n4) < this.msg.length()) {
            charSequence = this.msg;
            n4 = n2 + 1;
            if ((n2 = (int)((String)charSequence).charAt(n2)) == 39) {
                if ((n4 = this.msg.indexOf(39, n4)) >= 0) {
                    ++n4;
                    continue;
                }
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Quoted literal argument style text reaches to the end of the message: ");
                ((StringBuilder)charSequence).append(this.prefix(n));
                throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
            }
            if (n2 == 123) {
                ++n3;
                continue;
            }
            if (n2 != 125) continue;
            if (n3 > 0) {
                --n3;
                continue;
            }
            if ((n3 = --n4 - n) <= 65535) {
                this.addPart(Part.Type.ARG_STYLE, n, n3, 0);
                return n4;
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Argument style text too long: ");
            ((StringBuilder)charSequence).append(this.prefix(n));
            throw new IndexOutOfBoundsException(((StringBuilder)charSequence).toString());
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Unmatched '{' braces in message ");
        ((StringBuilder)charSequence).append(this.prefix());
        throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
    }

    private void postParse() {
    }

    private void preParse(String object) {
        if (!this.isFrozen()) {
            this.msg = object;
            this.hasArgNumbers = false;
            this.hasArgNames = false;
            this.needsAutoQuoting = false;
            this.parts.clear();
            object = this.numericValues;
            if (object != null) {
                ((ArrayList)object).clear();
            }
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Attempt to parse(");
        stringBuilder.append(MessagePattern.prefix((String)object));
        stringBuilder.append(") on frozen MessagePattern instance.");
        throw new UnsupportedOperationException(stringBuilder.toString());
    }

    private String prefix() {
        return MessagePattern.prefix(this.msg, 0);
    }

    private String prefix(int n) {
        return MessagePattern.prefix(this.msg, n);
    }

    private static String prefix(String string) {
        return MessagePattern.prefix(string, 0);
    }

    private static String prefix(String string, int n) {
        StringBuilder stringBuilder = new StringBuilder(44);
        if (n == 0) {
            stringBuilder.append("\"");
        } else {
            stringBuilder.append("[at pattern index ");
            stringBuilder.append(n);
            stringBuilder.append("] \"");
        }
        if (string.length() - n <= 24) {
            if (n != 0) {
                string = string.substring(n);
            }
            stringBuilder.append(string);
        } else {
            int n2;
            int n3 = n2 = n + 24 - 4;
            if (Character.isHighSurrogate(string.charAt(n2 - 1))) {
                n3 = n2 - 1;
            }
            stringBuilder.append(string, n, n3);
            stringBuilder.append(" ...");
        }
        stringBuilder.append("\"");
        return stringBuilder.toString();
    }

    private int skipDouble(int n) {
        char c;
        while (!(n >= this.msg.length() || (c = this.msg.charAt(n)) < '0' && "+-.".indexOf(c) < 0 || c > '9' && c != 'e' && c != 'E' && c != '\u221e')) {
            ++n;
        }
        return n;
    }

    private int skipIdentifier(int n) {
        return PatternProps.skipIdentifier(this.msg, n);
    }

    private int skipWhiteSpace(int n) {
        return PatternProps.skipWhiteSpace(this.msg, n);
    }

    public static int validateArgumentName(String string) {
        if (!PatternProps.isIdentifier(string)) {
            return -2;
        }
        return MessagePattern.parseArgNumber(string, 0, string.length());
    }

    public String autoQuoteApostropheDeep() {
        if (!this.needsAutoQuoting) {
            return this.msg;
        }
        StringBuilder stringBuilder = null;
        int n = this.countParts();
        while (n > 0) {
            Part part = this.getPart(--n);
            StringBuilder stringBuilder2 = stringBuilder;
            if (part.getType() == Part.Type.INSERT_CHAR) {
                stringBuilder2 = stringBuilder;
                if (stringBuilder == null) {
                    stringBuilder2 = new StringBuilder(this.msg.length() + 10).append(this.msg);
                }
                stringBuilder2.insert(part.index, (char)part.value);
            }
            stringBuilder = stringBuilder2;
        }
        if (stringBuilder == null) {
            return this.msg;
        }
        return stringBuilder.toString();
    }

    public void clear() {
        if (!this.isFrozen()) {
            this.msg = null;
            this.hasArgNumbers = false;
            this.hasArgNames = false;
            this.needsAutoQuoting = false;
            this.parts.clear();
            ArrayList<Double> arrayList = this.numericValues;
            if (arrayList != null) {
                arrayList.clear();
            }
            return;
        }
        throw new UnsupportedOperationException("Attempt to clear() a frozen MessagePattern instance.");
    }

    public void clearPatternAndSetApostropheMode(ApostropheMode apostropheMode) {
        this.clear();
        this.aposMode = apostropheMode;
    }

    public Object clone() {
        if (this.isFrozen()) {
            return this;
        }
        return this.cloneAsThawed();
    }

    @Override
    public MessagePattern cloneAsThawed() {
        MessagePattern messagePattern;
        try {
            messagePattern = (MessagePattern)super.clone();
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new ICUCloneNotSupportedException(cloneNotSupportedException);
        }
        messagePattern.parts = (ArrayList)this.parts.clone();
        ArrayList<Double> arrayList = this.numericValues;
        if (arrayList != null) {
            messagePattern.numericValues = (ArrayList)arrayList.clone();
        }
        messagePattern.frozen = false;
        return messagePattern;
    }

    public int countParts() {
        return this.parts.size();
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object != null && this.getClass() == object.getClass()) {
            String string;
            object = (MessagePattern)object;
            if (!(this.aposMode.equals((Object)((MessagePattern)object).aposMode) && ((string = this.msg) == null ? ((MessagePattern)object).msg == null : string.equals(((MessagePattern)object).msg)) && this.parts.equals(((MessagePattern)object).parts))) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    @Override
    public MessagePattern freeze() {
        this.frozen = true;
        return this;
    }

    public ApostropheMode getApostropheMode() {
        return this.aposMode;
    }

    public int getLimitPartIndex(int n) {
        int n2 = this.parts.get(n).limitPartIndex;
        if (n2 < n) {
            return n;
        }
        return n2;
    }

    public double getNumericValue(Part part) {
        Part.Type type = part.type;
        if (type == Part.Type.ARG_INT) {
            return part.value;
        }
        if (type == Part.Type.ARG_DOUBLE) {
            return this.numericValues.get(part.value);
        }
        return -1.23456789E8;
    }

    public Part getPart(int n) {
        return this.parts.get(n);
    }

    public Part.Type getPartType(int n) {
        return this.parts.get(n).type;
    }

    public int getPatternIndex(int n) {
        return this.parts.get(n).index;
    }

    public String getPatternString() {
        return this.msg;
    }

    public double getPluralOffset(int n) {
        Part part = this.parts.get(n);
        if (part.type.hasNumericValue()) {
            return this.getNumericValue(part);
        }
        return 0.0;
    }

    public String getSubstring(Part part) {
        int n = part.index;
        return this.msg.substring(n, part.length + n);
    }

    public boolean hasNamedArguments() {
        return this.hasArgNames;
    }

    public boolean hasNumberedArguments() {
        return this.hasArgNumbers;
    }

    public int hashCode() {
        int n = this.aposMode.hashCode();
        String string = this.msg;
        int n2 = string != null ? string.hashCode() : 0;
        return (n * 37 + n2) * 37 + this.parts.hashCode();
    }

    @Override
    public boolean isFrozen() {
        return this.frozen;
    }

    boolean jdkAposMode() {
        boolean bl = this.aposMode == ApostropheMode.DOUBLE_REQUIRED;
        return bl;
    }

    public MessagePattern parse(String string) {
        this.preParse(string);
        this.parseMessage(0, 0, 0, ArgType.NONE);
        this.postParse();
        return this;
    }

    public MessagePattern parseChoiceStyle(String string) {
        this.preParse(string);
        this.parseChoiceStyle(0, 0);
        this.postParse();
        return this;
    }

    public MessagePattern parsePluralStyle(String string) {
        this.preParse(string);
        this.parsePluralOrSelectStyle(ArgType.PLURAL, 0, 0);
        this.postParse();
        return this;
    }

    public MessagePattern parseSelectStyle(String string) {
        this.preParse(string);
        this.parsePluralOrSelectStyle(ArgType.SELECT, 0, 0);
        this.postParse();
        return this;
    }

    public boolean partSubstringMatches(Part part, String string) {
        boolean bl;
        char c = part.length;
        int n = string.length();
        boolean bl2 = bl = false;
        if (c == n) {
            bl2 = bl;
            if (this.msg.regionMatches(part.index, string, 0, part.length)) {
                bl2 = true;
            }
        }
        return bl2;
    }

    public String toString() {
        return this.msg;
    }

    public static enum ApostropheMode {
        DOUBLE_OPTIONAL,
        DOUBLE_REQUIRED;
        
    }

    public static enum ArgType {
        NONE,
        SIMPLE,
        CHOICE,
        PLURAL,
        SELECT,
        SELECTORDINAL;
        

        public boolean hasPluralStyle() {
            boolean bl = this == PLURAL || this == SELECTORDINAL;
            return bl;
        }
    }

    public static final class Part {
        private static final int MAX_LENGTH = 65535;
        private static final int MAX_VALUE = 32767;
        private final int index;
        private final char length;
        private int limitPartIndex;
        private final Type type;
        private short value;

        private Part(Type type, int n, int n2, int n3) {
            this.type = type;
            this.index = n;
            this.length = (char)n2;
            this.value = (short)n3;
        }

        public boolean equals(Object object) {
            boolean bl = true;
            if (this == object) {
                return true;
            }
            if (object != null && this.getClass() == object.getClass()) {
                object = (Part)object;
                if (!this.type.equals((Object)((Part)object).type) || this.index != ((Part)object).index || this.length != ((Part)object).length || this.value != ((Part)object).value || this.limitPartIndex != ((Part)object).limitPartIndex) {
                    bl = false;
                }
                return bl;
            }
            return false;
        }

        public ArgType getArgType() {
            Type type = this.getType();
            if (type != Type.ARG_START && type != Type.ARG_LIMIT) {
                return ArgType.NONE;
            }
            return argTypes[this.value];
        }

        public int getIndex() {
            return this.index;
        }

        public int getLength() {
            return this.length;
        }

        public int getLimit() {
            return this.index + this.length;
        }

        public Type getType() {
            return this.type;
        }

        public int getValue() {
            return this.value;
        }

        public int hashCode() {
            return ((this.type.hashCode() * 37 + this.index) * 37 + this.length) * 37 + this.value;
        }

        public String toString() {
            String string = this.type != Type.ARG_START && this.type != Type.ARG_LIMIT ? Integer.toString(this.value) : this.getArgType().name();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.type.name());
            stringBuilder.append("(");
            stringBuilder.append(string);
            stringBuilder.append(")@");
            stringBuilder.append(this.index);
            return stringBuilder.toString();
        }

        public static enum Type {
            MSG_START,
            MSG_LIMIT,
            SKIP_SYNTAX,
            INSERT_CHAR,
            REPLACE_NUMBER,
            ARG_START,
            ARG_LIMIT,
            ARG_NUMBER,
            ARG_NAME,
            ARG_TYPE,
            ARG_STYLE,
            ARG_SELECTOR,
            ARG_INT,
            ARG_DOUBLE;
            

            public boolean hasNumericValue() {
                boolean bl = this == ARG_INT || this == ARG_DOUBLE;
                return bl;
            }
        }

    }

}

