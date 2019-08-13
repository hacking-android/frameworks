/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.impl.Utility;
import android.icu.text.Replaceable;
import android.icu.text.RuleBasedTransliterator;
import android.icu.text.UTF16;
import android.icu.text.UnicodeReplacer;
import android.icu.text.UnicodeSet;

class StringReplacer
implements UnicodeReplacer {
    private int cursorPos;
    private final RuleBasedTransliterator.Data data;
    private boolean hasCursor;
    private boolean isComplex;
    private String output;

    public StringReplacer(String string, int n, RuleBasedTransliterator.Data data) {
        this.output = string;
        this.cursorPos = n;
        this.hasCursor = true;
        this.data = data;
        this.isComplex = true;
    }

    public StringReplacer(String string, RuleBasedTransliterator.Data data) {
        this.output = string;
        this.cursorPos = 0;
        this.hasCursor = false;
        this.data = data;
        this.isComplex = true;
    }

    @Override
    public void addReplacementSetTo(UnicodeSet unicodeSet) {
        int n;
        for (int i = 0; i < this.output.length(); i += UTF16.getCharCount((int)n)) {
            n = UTF16.charAt(this.output, i);
            UnicodeReplacer unicodeReplacer = this.data.lookupReplacer(n);
            if (unicodeReplacer == null) {
                unicodeSet.add(n);
                continue;
            }
            unicodeReplacer.addReplacementSetTo(unicodeSet);
        }
    }

    @Override
    public int replace(Replaceable replaceable, int n, int n2, int[] arrn) {
        int n3;
        int n4 = 0;
        if (!this.isComplex) {
            replaceable.replace(n, n2, this.output);
            n2 = this.output.length();
            n4 = this.cursorPos;
        } else {
            int n5;
            int n6;
            StringBuffer stringBuffer = new StringBuffer();
            this.isComplex = false;
            int n7 = replaceable.length();
            if (n > 0) {
                n3 = UTF16.getCharCount(replaceable.char32At(n - 1));
                replaceable.copy(n - n3, n, n7);
                n6 = n7 + n3;
            } else {
                replaceable.replace(n7, n7, "\uffff");
                n6 = n7 + 1;
            }
            n3 = n6;
            int n8 = 0;
            int n9 = 0;
            while (n9 < this.output.length()) {
                int n10;
                UnicodeReplacer unicodeReplacer;
                if (n9 == this.cursorPos) {
                    n4 = stringBuffer.length() + n3 - n6;
                }
                if ((n5 = UTF16.getCharCount(n10 = UTF16.charAt(this.output, n9)) + n9) == this.output.length()) {
                    n8 = UTF16.getCharCount(replaceable.char32At(n2));
                    replaceable.copy(n2, n2 + n8, n3);
                }
                if ((unicodeReplacer = this.data.lookupReplacer(n10)) == null) {
                    UTF16.append(stringBuffer, n10);
                } else {
                    this.isComplex = true;
                    n9 = n3;
                    if (stringBuffer.length() > 0) {
                        replaceable.replace(n3, n3, stringBuffer.toString());
                        n9 = n3 + stringBuffer.length();
                        stringBuffer.setLength(0);
                    }
                    n3 = n9 + unicodeReplacer.replace(replaceable, n9, n9, arrn);
                }
                n9 = n5;
            }
            n5 = n3;
            if (stringBuffer.length() > 0) {
                replaceable.replace(n3, n3, stringBuffer.toString());
                n5 = n3 + stringBuffer.length();
            }
            if (n9 == this.cursorPos) {
                n4 = n5 - n6;
            }
            n3 = n5 - n6;
            replaceable.copy(n6, n5, n);
            replaceable.replace(n7 + n3, n5 + n8 + n3, "");
            replaceable.replace(n + n3, n2 + n3, "");
            n2 = n3;
        }
        if (this.hasCursor) {
            n3 = this.cursorPos;
            if (n3 < 0) {
                n3 = n;
                for (n = this.cursorPos; n < 0 && n3 > 0; n3 -= UTF16.getCharCount((int)replaceable.char32At((int)(n3 - 1))), ++n) {
                }
                n = n3 + n;
            } else if (n3 > this.output.length()) {
                n3 = n + n2;
                for (n = this.cursorPos - this.output.length(); n > 0 && n3 < replaceable.length(); n3 += UTF16.getCharCount((int)replaceable.char32At((int)n3)), --n) {
                }
                n = n3 + n;
            } else {
                n = n4 + n;
            }
            arrn[0] = n;
        }
        return n2;
    }

    @Override
    public String toReplacerPattern(boolean bl) {
        int n;
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        int n2 = n = this.cursorPos;
        if (this.hasCursor) {
            n2 = n;
            if (n < 0) {
                do {
                    n2 = n + 1;
                    if (n >= 0) break;
                    Utility.appendToRule(stringBuffer, 64, true, bl, stringBuffer2);
                    n = n2;
                } while (true);
            }
        }
        for (n = 0; n < this.output.length(); ++n) {
            char c;
            UnicodeReplacer unicodeReplacer;
            if (this.hasCursor && n == n2) {
                Utility.appendToRule(stringBuffer, 124, true, bl, stringBuffer2);
            }
            if ((unicodeReplacer = this.data.lookupReplacer(c = this.output.charAt(n))) == null) {
                Utility.appendToRule(stringBuffer, c, false, bl, stringBuffer2);
                continue;
            }
            StringBuffer stringBuffer3 = new StringBuffer(" ");
            stringBuffer3.append(unicodeReplacer.toReplacerPattern(bl));
            stringBuffer3.append(' ');
            Utility.appendToRule(stringBuffer, stringBuffer3.toString(), true, bl, stringBuffer2);
        }
        if (this.hasCursor && n2 > this.output.length()) {
            n2 -= this.output.length();
            while (n2 > 0) {
                Utility.appendToRule(stringBuffer, 64, true, bl, stringBuffer2);
                --n2;
            }
            Utility.appendToRule(stringBuffer, 124, true, bl, stringBuffer2);
        }
        Utility.appendToRule(stringBuffer, -1, true, bl, stringBuffer2);
        return stringBuffer.toString();
    }
}

