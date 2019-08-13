/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.icu.text.DecimalFormatSymbols
 *  libcore.icu.LocaleData
 */
package android.text.method;

import android.icu.text.DecimalFormatSymbols;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.format.DateFormat;
import android.text.method.BaseKeyListener;
import android.view.KeyEvent;
import android.view.View;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import libcore.icu.LocaleData;

public abstract class NumberKeyListener
extends BaseKeyListener
implements InputFilter {
    private static final String DATE_TIME_FORMAT_SYMBOLS = "GyYuUrQqMLlwWdDFgEecabBhHKkjJCmsSAzZOvVXx";
    private static final char SINGLE_QUOTE = '\'';

    static boolean addAmPmChars(Collection<Character> collection, Locale arrstring) {
        if (arrstring == null) {
            return false;
        }
        arrstring = LocaleData.get((Locale)arrstring).amPm;
        for (int i = 0; i < arrstring.length; ++i) {
            for (int j = 0; j < arrstring[i].length(); ++j) {
                char c = arrstring[i].charAt(j);
                if (Character.isBmpCodePoint(c)) {
                    collection.add(Character.valueOf(c));
                    continue;
                }
                return false;
            }
        }
        return true;
    }

    static boolean addDigits(Collection<Character> collection, Locale arrstring) {
        if (arrstring == null) {
            return false;
        }
        arrstring = DecimalFormatSymbols.getInstance((Locale)arrstring).getDigitStrings();
        for (int i = 0; i < 10; ++i) {
            if (arrstring[i].length() > 1) {
                return false;
            }
            collection.add(Character.valueOf(arrstring[i].charAt(0)));
        }
        return true;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    static boolean addFormatCharsFromSkeleton(Collection<Character> var0, Locale var1_1, String var2_2, String var3_3) {
        if (var1_1 == null) {
            return false;
        }
        var1_1 = DateFormat.getBestDateTimePattern((Locale)var1_1, var2_2);
        var4_4 = 1;
        var5_5 = 0;
        do {
            block8 : {
                block7 : {
                    var6_6 = var1_1.length();
                    var7_7 = 1;
                    if (var5_5 >= var6_6) return true;
                    var8_8 = var1_1.charAt(var5_5);
                    if (Character.isSurrogate(var8_8)) {
                        return false;
                    }
                    var6_6 = var4_4;
                    if (var8_8 != '\'') break block7;
                    if (var4_4 != 0) {
                        var7_7 = 0;
                    }
                    var4_4 = var7_7;
                    if (var5_5 == 0) break block8;
                    var6_6 = var7_7;
                    if (var1_1.charAt(var5_5 - 1) == '\'') break block7;
                    var4_4 = var7_7;
                    break block8;
                }
                if (var6_6 == 0) ** GOTO lbl30
                if (var3_3.indexOf(var8_8) != -1) {
                    var4_4 = var6_6;
                } else {
                    if ("GyYuUrQqMLlwWdDFgEecabBhHKkjJCmsSAzZOvVXx".indexOf(var8_8) != -1) {
                        return false;
                    }
lbl30: // 3 sources:
                    var0.add(Character.valueOf(var8_8));
                    var4_4 = var6_6;
                }
            }
            ++var5_5;
        } while (true);
    }

    static boolean addFormatCharsFromSkeletons(Collection<Character> collection, Locale locale, String[] arrstring, String string2) {
        for (int i = 0; i < arrstring.length; ++i) {
            if (NumberKeyListener.addFormatCharsFromSkeleton(collection, locale, arrstring[i], string2)) continue;
            return false;
        }
        return true;
    }

    static char[] collectionToArray(Collection<Character> object) {
        char[] arrc = new char[object.size()];
        int n = 0;
        object = object.iterator();
        while (object.hasNext()) {
            arrc[n] = ((Character)object.next()).charValue();
            ++n;
        }
        return arrc;
    }

    protected static boolean ok(char[] arrc, char c) {
        for (int i = arrc.length - 1; i >= 0; --i) {
            if (arrc[i] != c) continue;
            return true;
        }
        return false;
    }

    @Override
    public CharSequence filter(CharSequence charSequence, int n, int n2, Spanned arrc, int n3, int n4) {
        arrc = this.getAcceptedChars();
        for (n3 = n; n3 < n2 && NumberKeyListener.ok(arrc, charSequence.charAt(n3)); ++n3) {
        }
        if (n3 == n2) {
            return null;
        }
        if (n2 - n == 1) {
            return "";
        }
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(charSequence, n, n2);
        n2 -= n;
        --n2;
        while (n2 >= n3 - n) {
            if (!NumberKeyListener.ok(arrc, charSequence.charAt(n2))) {
                spannableStringBuilder.delete(n2, n2 + 1);
            }
            --n2;
        }
        return spannableStringBuilder;
    }

    protected abstract char[] getAcceptedChars();

    protected int lookup(KeyEvent keyEvent, Spannable spannable) {
        return keyEvent.getMatch(this.getAcceptedChars(), NumberKeyListener.getMetaState((CharSequence)spannable, keyEvent));
    }

    @Override
    public boolean onKeyDown(View view, Editable editable, int n, KeyEvent keyEvent) {
        int n2;
        int n3;
        int n4;
        int n5;
        block11 : {
            block10 : {
                n3 = Selection.getSelectionStart(editable);
                n5 = Selection.getSelectionEnd(editable);
                n4 = Math.min(n3, n5);
                n3 = Math.max(n3, n5);
                n2 = 0;
                if (n4 < 0) break block10;
                n5 = n3;
                if (n3 >= 0) break block11;
            }
            n5 = 0;
            n4 = 0;
            Selection.setSelection(editable, 0);
        }
        n3 = keyEvent != null ? this.lookup(keyEvent, editable) : 0;
        if (keyEvent != null) {
            n2 = keyEvent.getRepeatCount();
        }
        if (n2 == 0) {
            if (n3 != 0) {
                if (n4 != n5) {
                    Selection.setSelection(editable, n5);
                }
                editable.replace(n4, n5, String.valueOf((char)n3));
                NumberKeyListener.adjustMetaAfterKeypress(editable);
                return true;
            }
        } else if (n3 == 48 && n2 == 1 && n4 == n5 && n5 > 0 && editable.charAt(n4 - 1) == '0') {
            editable.replace(n4 - 1, n5, String.valueOf('+'));
            NumberKeyListener.adjustMetaAfterKeypress(editable);
            return true;
        }
        NumberKeyListener.adjustMetaAfterKeypress(editable);
        return super.onKeyDown(view, editable, n, keyEvent);
    }
}

