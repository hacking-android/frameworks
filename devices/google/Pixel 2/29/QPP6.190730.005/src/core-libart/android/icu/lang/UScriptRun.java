/*
 * Decompiled with CFR 0.145.
 */
package android.icu.lang;

import android.icu.lang.UScript;
import android.icu.text.UTF16;

@Deprecated
public final class UScriptRun {
    private static int PAREN_STACK_DEPTH = 32;
    private static int pairedCharExtra;
    private static int pairedCharPower;
    private static int[] pairedChars;
    private static ParenStackEntry[] parenStack;
    private char[] emptyCharArray = new char[0];
    private int fixupCount = 0;
    private int parenSP = -1;
    private int pushCount = 0;
    private int scriptCode;
    private int scriptLimit;
    private int scriptStart;
    private char[] text;
    private int textIndex;
    private int textLimit;
    private int textStart;

    static {
        parenStack = new ParenStackEntry[PAREN_STACK_DEPTH];
        pairedChars = new int[]{40, 41, 60, 62, 91, 93, 123, 125, 171, 187, 8216, 8217, 8220, 8221, 8249, 8250, 12296, 12297, 12298, 12299, 12300, 12301, 12302, 12303, 12304, 12305, 12308, 12309, 12310, 12311, 12312, 12313, 12314, 12315};
        pairedCharPower = 1 << UScriptRun.highBit(pairedChars.length);
        pairedCharExtra = pairedChars.length - pairedCharPower;
    }

    @Deprecated
    public UScriptRun() {
        this.reset((char[])null, 0, 0);
    }

    @Deprecated
    public UScriptRun(String string) {
        this.reset(string);
    }

    @Deprecated
    public UScriptRun(String string, int n, int n2) {
        this.reset(string, n, n2);
    }

    @Deprecated
    public UScriptRun(char[] arrc) {
        this.reset(arrc);
    }

    @Deprecated
    public UScriptRun(char[] arrc, int n, int n2) {
        this.reset(arrc, n, n2);
    }

    private static final int dec(int n) {
        return UScriptRun.dec(n, 1);
    }

    private static final int dec(int n, int n2) {
        return UScriptRun.mod(PAREN_STACK_DEPTH + n - n2);
    }

    private final void fixup(int n) {
        int n2 = UScriptRun.dec(this.parenSP, this.fixupCount);
        do {
            int n3 = this.fixupCount;
            this.fixupCount = n3 - 1;
            if (n3 <= 0) break;
            n2 = UScriptRun.inc(n2);
            UScriptRun.parenStack[n2].scriptCode = n;
        } while (true);
    }

    private static int getPairIndex(int n) {
        int n2 = pairedCharPower;
        int n3 = 0;
        int n4 = n2;
        if (n >= pairedChars[pairedCharExtra]) {
            n3 = pairedCharExtra;
            n4 = n2;
        }
        while (n4 > 1) {
            n4 = n2 = n4 >> 1;
            if (n < pairedChars[n3 + n2]) continue;
            n3 += n2;
            n4 = n2;
        }
        n4 = n3;
        if (pairedChars[n3] != n) {
            n4 = -1;
        }
        return n4;
    }

    private static final byte highBit(int n) {
        if (n <= 0) {
            return -32;
        }
        int n2 = 0;
        int n3 = n;
        if (n >= 65536) {
            n3 = n >> 16;
            n2 = 0 + 16;
        }
        int n4 = n2;
        n = n3;
        if (n3 >= 256) {
            n = n3 >> 8;
            n4 = (byte)(n2 + 8);
        }
        n3 = n4;
        n2 = n;
        if (n >= 16) {
            n2 = n >> 4;
            n3 = (byte)(n4 + 4);
        }
        n4 = n3;
        n = n2;
        if (n2 >= 4) {
            n = n2 >> 2;
            n4 = (byte)(n3 + 2);
        }
        int n5 = n4;
        if (n >= 2) {
            n5 = n = (int)((byte)(n4 + 1));
        }
        return (byte)n5;
    }

    private static final int inc(int n) {
        return UScriptRun.inc(n, 1);
    }

    private static final int inc(int n, int n2) {
        return UScriptRun.mod(n + n2);
    }

    private static final int limitInc(int n) {
        int n2 = n;
        if (n < PAREN_STACK_DEPTH) {
            n2 = n + 1;
        }
        return n2;
    }

    private static final int mod(int n) {
        return n % PAREN_STACK_DEPTH;
    }

    private final void pop() {
        if (this.stackIsEmpty()) {
            return;
        }
        UScriptRun.parenStack[this.parenSP] = null;
        int n = this.fixupCount;
        if (n > 0) {
            this.fixupCount = n - 1;
        }
        --this.pushCount;
        this.parenSP = UScriptRun.dec(this.parenSP);
        if (this.stackIsEmpty()) {
            this.parenSP = -1;
        }
    }

    private final void push(int n, int n2) {
        this.pushCount = UScriptRun.limitInc(this.pushCount);
        this.fixupCount = UScriptRun.limitInc(this.fixupCount);
        this.parenSP = UScriptRun.inc(this.parenSP);
        UScriptRun.parenStack[this.parenSP] = new ParenStackEntry(n, n2);
    }

    private static boolean sameScript(int n, int n2) {
        boolean bl;
        boolean bl2 = bl = true;
        if (n > 1) {
            bl2 = bl;
            if (n2 > 1) {
                bl2 = n == n2 ? bl : false;
            }
        }
        return bl2;
    }

    private final boolean stackIsEmpty() {
        boolean bl = this.pushCount <= 0;
        return bl;
    }

    private final boolean stackIsNotEmpty() {
        return this.stackIsEmpty() ^ true;
    }

    private final void syncFixup() {
        this.fixupCount = 0;
    }

    private final ParenStackEntry top() {
        return parenStack[this.parenSP];
    }

    @Deprecated
    public final int getScriptCode() {
        return this.scriptCode;
    }

    @Deprecated
    public final int getScriptLimit() {
        return this.scriptLimit;
    }

    @Deprecated
    public final int getScriptStart() {
        return this.scriptStart;
    }

    @Deprecated
    public final boolean next() {
        int n;
        int n2 = this.scriptLimit;
        if (n2 >= this.textLimit) {
            return false;
        }
        this.scriptCode = 0;
        this.scriptStart = n2;
        this.syncFixup();
        while ((n = this.textIndex) < (n2 = this.textLimit)) {
            char[] arrc = this.text;
            int n3 = this.textStart;
            n2 = UTF16.charAt(arrc, n3, n2, n - n3);
            n3 = UTF16.getCharCount(n2);
            n = UScript.getScript(n2);
            int n4 = UScriptRun.getPairIndex(n2);
            this.textIndex += n3;
            n2 = n;
            if (n4 >= 0) {
                if ((n4 & 1) == 0) {
                    this.push(n4, this.scriptCode);
                    n2 = n;
                } else {
                    while (this.stackIsNotEmpty() && this.top().pairIndex != (n4 & -2)) {
                        this.pop();
                    }
                    n2 = n;
                    if (this.stackIsNotEmpty()) {
                        n2 = this.top().scriptCode;
                    }
                }
            }
            if (UScriptRun.sameScript(this.scriptCode, n2)) {
                if (this.scriptCode <= 1 && n2 > 1) {
                    this.scriptCode = n2;
                    this.fixup(this.scriptCode);
                }
                if (n4 < 0 || (n4 & 1) == 0) continue;
                this.pop();
                continue;
            }
            this.textIndex -= n3;
            break;
        }
        this.scriptLimit = this.textIndex;
        return true;
    }

    @Deprecated
    public final void reset() {
        int n;
        while (this.stackIsNotEmpty()) {
            this.pop();
        }
        this.scriptStart = n = this.textStart;
        this.scriptLimit = n;
        this.scriptCode = -1;
        this.parenSP = -1;
        this.pushCount = 0;
        this.fixupCount = 0;
        this.textIndex = n;
    }

    @Deprecated
    public final void reset(int n, int n2) throws IllegalArgumentException {
        int n3 = 0;
        char[] arrc = this.text;
        if (arrc != null) {
            n3 = arrc.length;
        }
        if (n >= 0 && n2 >= 0 && n <= n3 - n2) {
            this.textStart = n;
            this.textLimit = n + n2;
            this.reset();
            return;
        }
        throw new IllegalArgumentException();
    }

    @Deprecated
    public final void reset(String string) {
        int n = 0;
        if (string != null) {
            n = string.length();
        }
        this.reset(string, 0, n);
    }

    @Deprecated
    public final void reset(String string, int n, int n2) {
        char[] arrc = null;
        if (string != null) {
            arrc = string.toCharArray();
        }
        this.reset(arrc, n, n2);
    }

    @Deprecated
    public final void reset(char[] arrc) {
        int n = 0;
        if (arrc != null) {
            n = arrc.length;
        }
        this.reset(arrc, 0, n);
    }

    @Deprecated
    public final void reset(char[] arrc, int n, int n2) {
        char[] arrc2 = arrc;
        if (arrc == null) {
            arrc2 = this.emptyCharArray;
        }
        this.text = arrc2;
        this.reset(n, n2);
    }

    private static final class ParenStackEntry {
        int pairIndex;
        int scriptCode;

        public ParenStackEntry(int n, int n2) {
            this.pairIndex = n;
            this.scriptCode = n2;
        }
    }

}

