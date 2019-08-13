/*
 * Decompiled with CFR 0.145.
 */
package android.text;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.view.View;
import java.util.Locale;

public class AutoText {
    private static final int DEFAULT = 14337;
    private static final int INCREMENT = 1024;
    private static final int RIGHT = 9300;
    private static final int TRIE_C = 0;
    private static final int TRIE_CHILD = 2;
    private static final int TRIE_NEXT = 3;
    private static final char TRIE_NULL = '\uffff';
    private static final int TRIE_OFF = 1;
    private static final int TRIE_ROOT = 0;
    private static final int TRIE_SIZEOF = 4;
    private static AutoText sInstance = new AutoText(Resources.getSystem());
    private static Object sLock = new Object();
    private Locale mLocale;
    private int mSize;
    private String mText;
    private char[] mTrie;
    private char mTrieUsed;

    private AutoText(Resources resources) {
        this.mLocale = resources.getConfiguration().locale;
        this.init(resources);
    }

    private void add(String string2, char c) {
        int n = string2.length();
        int n2 = 0;
        ++this.mSize;
        for (int i = 0; i < n; ++i) {
            boolean bl;
            int n3;
            char[] arrc;
            char c2 = string2.charAt(i);
            boolean bl2 = false;
            do {
                arrc = this.mTrie;
                n3 = n2;
                bl = bl2;
                if (arrc[n2] == '\uffff') break;
                if (c2 == arrc[arrc[n2] + '\u0000']) {
                    if (i == n - 1) {
                        arrc[arrc[n2] + '\u0001'] = c;
                        return;
                    }
                    n3 = arrc[n2] + 2;
                    bl = true;
                    break;
                }
                n2 = arrc[n2] + 3;
            } while (true);
            n2 = n3;
            if (bl) continue;
            n2 = this.newTrieNode();
            arrc = this.mTrie;
            arrc[n3] = (char)n2;
            arrc[arrc[n3] + '\u0000'] = c2;
            arrc[arrc[n3] + '\u0001'] = (char)65535;
            arrc[arrc[n3] + 3] = (char)65535;
            arrc[arrc[n3] + 2] = (char)65535;
            if (i == n - 1) {
                arrc[arrc[n3] + '\u0001'] = c;
                return;
            }
            n2 = arrc[n3] + 2;
        }
    }

    public static String get(CharSequence charSequence, int n, int n2, View view) {
        return AutoText.getInstance(view).lookup(charSequence, n, n2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static AutoText getInstance(View object) {
        Resources resources = ((View)object).getContext().getResources();
        Locale locale = resources.getConfiguration().locale;
        Object object2 = sLock;
        synchronized (object2) {
            AutoText autoText = sInstance;
            object = autoText;
            if (!locale.equals(autoText.mLocale)) {
                sInstance = object = new AutoText(resources);
            }
            return object;
        }
    }

    private int getSize() {
        return this.mSize;
    }

    public static int getSize(View view) {
        return AutoText.getInstance(view).getSize();
    }

    /*
     * Exception decompiling
     */
    private void init(Resources var1_1) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [3[CATCHBLOCK]], but top level block is 1[TRYBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    private String lookup(CharSequence charSequence, int n, int n2) {
        int n3 = this.mTrie[0];
        int n4 = n;
        n = n3;
        while (n4 < n2) {
            char c = charSequence.charAt(n4);
            n3 = n;
            do {
                n = n3;
                if (n3 == 65535) break;
                char[] arrc = this.mTrie;
                if (c == arrc[n3 + 0]) {
                    if (n4 == n2 - 1 && arrc[n3 + 1] != '\uffff') {
                        n = arrc[n3 + 1];
                        n2 = this.mText.charAt(n);
                        return this.mText.substring(n + 1, n + 1 + n2);
                    }
                    n = this.mTrie[n3 + 2];
                    break;
                }
                n3 = arrc[n3 + 3];
            } while (true);
            if (n == 65535) {
                return null;
            }
            ++n4;
        }
        return null;
    }

    private char newTrieNode() {
        char c = this.mTrieUsed;
        char[] arrc = this.mTrie;
        if (c + 4 > arrc.length) {
            char[] arrc2 = new char[arrc.length + 1024];
            System.arraycopy(arrc, 0, arrc2, 0, arrc.length);
            this.mTrie = arrc2;
        }
        char c2 = this.mTrieUsed;
        this.mTrieUsed = (char)(this.mTrieUsed + 4);
        return c2;
    }
}

