/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.icu.lang.UCharacter
 *  android.icu.text.DecimalFormatSymbols
 */
package android.text.method;

import android.icu.lang.UCharacter;
import android.icu.text.DecimalFormatSymbols;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.NumberKeyListener;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.util.ArrayUtils;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Locale;

public class DigitsKeyListener
extends NumberKeyListener {
    private static final char[][] COMPATIBILITY_CHARACTERS = new char[][]{{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'}, {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-', '+'}, {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.'}, {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-', '+', '.'}};
    private static final int DECIMAL = 2;
    private static final String DEFAULT_DECIMAL_POINT_CHARS = ".";
    private static final String DEFAULT_SIGN_CHARS = "-+";
    private static final char EN_DASH = '\u2013';
    private static final char HYPHEN_MINUS = '-';
    private static final char MINUS_SIGN = '\u2212';
    private static final int SIGN = 1;
    private static final Object sLocaleCacheLock = new Object();
    @GuardedBy(value={"sLocaleCacheLock"})
    private static final HashMap<Locale, DigitsKeyListener[]> sLocaleInstanceCache = new HashMap();
    private static final Object sStringCacheLock = new Object();
    @GuardedBy(value={"sStringCacheLock"})
    private static final HashMap<String, DigitsKeyListener> sStringInstanceCache = new HashMap();
    private char[] mAccepted;
    private final boolean mDecimal;
    private String mDecimalPointChars = ".";
    private final Locale mLocale;
    private boolean mNeedsAdvancedInput;
    private final boolean mSign;
    private String mSignChars = "-+";
    private final boolean mStringMode;

    @Deprecated
    public DigitsKeyListener() {
        this(null, false, false);
    }

    private DigitsKeyListener(String string2) {
        this.mSign = false;
        this.mDecimal = false;
        this.mStringMode = true;
        this.mLocale = null;
        this.mAccepted = new char[string2.length()];
        string2.getChars(0, string2.length(), this.mAccepted, 0);
        this.mNeedsAdvancedInput = false;
    }

    public DigitsKeyListener(Locale locale) {
        this(locale, false, false);
    }

    public DigitsKeyListener(Locale object, boolean bl, boolean bl2) {
        this.mSign = bl;
        this.mDecimal = bl2;
        this.mStringMode = false;
        this.mLocale = object;
        if (object == null) {
            this.setToCompat();
            return;
        }
        LinkedHashSet<Character> linkedHashSet = new LinkedHashSet<Character>();
        if (!NumberKeyListener.addDigits(linkedHashSet, (Locale)object)) {
            this.setToCompat();
            return;
        }
        if (bl || bl2) {
            object = DecimalFormatSymbols.getInstance((Locale)object);
            if (bl) {
                CharSequence charSequence = DigitsKeyListener.stripBidiControls(object.getMinusSignString());
                String string2 = DigitsKeyListener.stripBidiControls(object.getPlusSignString());
                if (((String)charSequence).length() <= 1 && string2.length() <= 1) {
                    char c = ((String)charSequence).charAt(0);
                    char c2 = string2.charAt(0);
                    linkedHashSet.add(Character.valueOf(c));
                    linkedHashSet.add(Character.valueOf(c2));
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("");
                    ((StringBuilder)charSequence).append(c);
                    ((StringBuilder)charSequence).append(c2);
                    this.mSignChars = ((StringBuilder)charSequence).toString();
                    if (c == '\u2212' || c == '\u2013') {
                        linkedHashSet.add(Character.valueOf('-'));
                        charSequence = new StringBuilder();
                        ((StringBuilder)charSequence).append(this.mSignChars);
                        ((StringBuilder)charSequence).append('-');
                        this.mSignChars = ((StringBuilder)charSequence).toString();
                    }
                } else {
                    this.setToCompat();
                    return;
                }
            }
            if (bl2) {
                if (((String)(object = object.getDecimalSeparatorString())).length() > 1) {
                    this.setToCompat();
                    return;
                }
                object = Character.valueOf(((String)object).charAt(0));
                linkedHashSet.add((Character)object);
                this.mDecimalPointChars = ((Character)object).toString();
            }
        }
        this.mAccepted = NumberKeyListener.collectionToArray(linkedHashSet);
        this.calculateNeedForAdvancedInput();
    }

    @Deprecated
    public DigitsKeyListener(boolean bl, boolean bl2) {
        this(null, bl, bl2);
    }

    private void calculateNeedForAdvancedInput() {
        int n = this.mSign;
        int n2 = this.mDecimal ? 2 : 0;
        this.mNeedsAdvancedInput = ArrayUtils.containsAll(COMPATIBILITY_CHARACTERS[n | n2], this.mAccepted) ^ true;
    }

    @Deprecated
    public static DigitsKeyListener getInstance() {
        return DigitsKeyListener.getInstance(false, false);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static DigitsKeyListener getInstance(String string2) {
        Object object = sStringCacheLock;
        synchronized (object) {
            DigitsKeyListener digitsKeyListener;
            DigitsKeyListener digitsKeyListener2 = digitsKeyListener = sStringInstanceCache.get(string2);
            if (digitsKeyListener == null) {
                digitsKeyListener2 = new DigitsKeyListener(string2);
                sStringInstanceCache.put(string2, digitsKeyListener2);
            }
            return digitsKeyListener2;
        }
    }

    public static DigitsKeyListener getInstance(Locale locale) {
        return DigitsKeyListener.getInstance(locale, false, false);
    }

    public static DigitsKeyListener getInstance(Locale locale, DigitsKeyListener digitsKeyListener) {
        if (digitsKeyListener.mStringMode) {
            return digitsKeyListener;
        }
        return DigitsKeyListener.getInstance(locale, digitsKeyListener.mSign, digitsKeyListener.mDecimal);
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static DigitsKeyListener getInstance(Locale object, boolean bl, boolean bl2) {
        int n = bl2 ? 2 : 0;
        Object object2 = sLocaleCacheLock;
        synchronized (object2) {
            Object object3 = sLocaleInstanceCache.get(object);
            if (object3 != null && object3[n |= bl] != null) {
                return object3[n];
            }
            DigitsKeyListener[] arrdigitsKeyListener = object3;
            if (object3 == null) {
                arrdigitsKeyListener = new DigitsKeyListener[4];
                sLocaleInstanceCache.put((Locale)object, arrdigitsKeyListener);
            }
            arrdigitsKeyListener[n] = object3 = new DigitsKeyListener((Locale)object, bl, bl2);
            return object3;
        }
    }

    @Deprecated
    public static DigitsKeyListener getInstance(boolean bl, boolean bl2) {
        return DigitsKeyListener.getInstance(null, bl, bl2);
    }

    private boolean isDecimalPointChar(char c) {
        boolean bl = this.mDecimalPointChars.indexOf(c) != -1;
        return bl;
    }

    private boolean isSignChar(char c) {
        boolean bl = this.mSignChars.indexOf(c) != -1;
        return bl;
    }

    private void setToCompat() {
        this.mDecimalPointChars = DEFAULT_DECIMAL_POINT_CHARS;
        this.mSignChars = DEFAULT_SIGN_CHARS;
        int n = this.mSign;
        int n2 = this.mDecimal ? 2 : 0;
        this.mAccepted = COMPATIBILITY_CHARACTERS[n | n2];
        this.mNeedsAdvancedInput = false;
    }

    private static String stripBidiControls(String string2) {
        String string3 = "";
        for (int i = 0; i < string2.length(); ++i) {
            char c = string2.charAt(i);
            CharSequence charSequence = string3;
            if (!UCharacter.hasBinaryProperty((int)c, (int)2)) {
                if (string3.isEmpty()) {
                    charSequence = String.valueOf(c);
                } else {
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append(string3);
                    ((StringBuilder)charSequence).append(c);
                    charSequence = ((StringBuilder)charSequence).toString();
                }
            }
            string3 = charSequence;
        }
        return string3;
    }

    @Override
    public CharSequence filter(CharSequence object, int n, int n2, Spanned object2, int n3, int n4) {
        int n5;
        char c;
        CharSequence charSequence;
        int n6;
        int n7;
        DigitsKeyListener digitsKeyListener = this;
        CharSequence charSequence2 = super.filter((CharSequence)object, n, n2, (Spanned)object2, n3, n4);
        if (!digitsKeyListener.mSign && !digitsKeyListener.mDecimal) {
            return charSequence2;
        }
        if (charSequence2 != null) {
            charSequence = charSequence2;
            n7 = 0;
            n6 = charSequence2.length();
        } else {
            n6 = n2;
            n7 = n;
            charSequence = object;
        }
        n2 = -1;
        int n8 = -1;
        int n9 = object2.length();
        for (n = 0; n < n3; ++n) {
            c = object2.charAt(n);
            if (digitsKeyListener.isSignChar(c)) {
                n5 = n;
            } else {
                n5 = n2;
                if (digitsKeyListener.isDecimalPointChar(c)) {
                    n8 = n;
                    n5 = n2;
                }
            }
            n2 = n5;
        }
        for (n = n4; n < n9; ++n) {
            c = object2.charAt(n);
            if (digitsKeyListener.isSignChar(c)) {
                return "";
            }
            if (!digitsKeyListener.isDecimalPointChar(c)) continue;
            n8 = n;
        }
        object = null;
        n = n6 - 1;
        n4 = n2;
        do {
            object2 = this;
            if (n < n7) break;
            c = charSequence.charAt(n);
            int n10 = 0;
            if (DigitsKeyListener.super.isSignChar(c)) {
                if (n == n7 && n3 == 0) {
                    if (n4 >= 0) {
                        n2 = 1;
                        n5 = n4;
                        n9 = n8;
                    } else {
                        n5 = n;
                        n9 = n8;
                        n2 = n10;
                    }
                } else {
                    n2 = 1;
                    n5 = n4;
                    n9 = n8;
                }
            } else {
                n5 = n4;
                n9 = n8;
                n2 = n10;
                if (DigitsKeyListener.super.isDecimalPointChar(c)) {
                    if (n8 >= 0) {
                        n2 = 1;
                        n5 = n4;
                        n9 = n8;
                    } else {
                        n9 = n;
                        n2 = n10;
                        n5 = n4;
                    }
                }
            }
            object2 = object;
            if (n2 != 0) {
                if (n6 == n7 + 1) {
                    return "";
                }
                object2 = object;
                if (object == null) {
                    object2 = new SpannableStringBuilder(charSequence, n7, n6);
                }
                ((SpannableStringBuilder)object2).delete(n - n7, n + 1 - n7);
            }
            --n;
            n4 = n5;
            n8 = n9;
            object = object2;
        } while (true);
        if (object != null) {
            return object;
        }
        if (charSequence2 != null) {
            return charSequence2;
        }
        return null;
    }

    @Override
    protected char[] getAcceptedChars() {
        return this.mAccepted;
    }

    @Override
    public int getInputType() {
        int n;
        if (this.mNeedsAdvancedInput) {
            n = 1;
        } else {
            int n2 = 2;
            if (this.mSign) {
                n2 = 2 | 4096;
            }
            n = n2;
            if (this.mDecimal) {
                n = n2 | 8192;
            }
        }
        return n;
    }
}

